/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiblockchain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.Scene;

/**
 *
 * @author andrerib
 */
public class BlockChain {

    private final ArrayList<Block> blockchain;
    private View view;

    /**
     * Costruttore
     *
     * @param chain specifica tutti i blocchi da aggiungere in partenza
     */
    public BlockChain(Block... chain) {
        blockchain = new ArrayList<>();
        view = new View(this);
        for (Block b : chain) {
            blockchain.add(b);
            view.add(b);
        }
    }

    /**
     * Costruttore. Aggiunge un primo blocco autonomamente
     */
    public BlockChain() {
        this(new Block(Crypto.SHA256("BL0CKCH41N"), "I'm the first block."));
    }

    /**
     * Ritorna la blockchain sottoforma di GUI
     *
     * @return Scene - Blockchain
     */
    public Scene show() {
        return view.getScene();
    }

    /**
     * Ritorna l'hash dell'ultimo blocco aggiunto
     *
     * @return hash
     */
    protected synchronized String getLastHash() {
        try {
            return blockchain.get(blockchain.size() - 1).hash;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Aggiunge un blocco alla blockchain.
     *
     * @param b blocco da aggiungere
     * @return TRUE se il blocco è stato aggiunto FALSE altrimenti
     */
    public synchronized boolean add(Block b) {
        if (b.PrevHash.equals(getLastHash())) {
            blockchain.add(b);
//            System.out.println(b);
            view.add(b);
            return true;
        }
        return false;
    }

    /**
     * Specifica il punto di rottura della catena: Se la lista è valida fino al
     * blocco #5, ritorna 5
     *
     * @return indice del punto di rottura
     */
    private int getBrokePoint() {
        boolean cont = true;
        for (int i = 1; i < blockchain.size() && cont; i++) {
            if (!blockchain.get(i - 1).hash.equals(blockchain.get(i).PrevHash)) {
                return i;
            }
        }
        return blockchain.size();
    }

    /**
     * Ripulisce la blockchain dai blocchi non validi, che non sarebbero stati
     * da aggiungere [Just in case]
     *
     * @return numero di blocchi cancellati
     */
    public synchronized int clean() {
        int removed = 1, totalremoved = 0;
        while (removed > 0) {
            int unvalid = getBrokePoint();
            removed = 0;
            for (int i = unvalid + 1; i < blockchain.size(); i++) {
                String prev = blockchain.get(i).PrevHash;
                if (prev.equals(blockchain.get(unvalid - 1).hash)) {
                    blockchain.remove(unvalid);
                    removed++;
                    totalremoved++;
                }
            }
        }
        return totalremoved;
    }

    /**
     * Ritorna la blockchain sottoforma di stringa
     *
     * @return consecutivamente, ogni blocco sottoforma di stringa
     */
    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        blockchain.forEach((b) -> {
            sb.append(b).append("\n");
        });
        sb.append("\nBC Length: ").append(blockchain.size()).append("\n");
        return sb.toString();
    }

    /**
     * Esporta la blockchain su un file specificato
     *
     * @param path percorso cartella
     */
    public synchronized void export(File path) {
        BufferedWriter out = null;
        try {
            path = new File(path.getPath() + ".txt");
            path.createNewFile();
            out = new BufferedWriter(new FileWriter(path, true));
            out.write("---Blockchain information---");
            out.newLine();
            out.append(toString());
            out.newLine();
            out.append("---end---");
        } catch (IOException ex) {
            System.out.println("Errore");
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                System.out.println("Errore");
            }
        }
    }

}
