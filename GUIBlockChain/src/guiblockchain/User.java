/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiblockchain;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrerib
 */
public class User extends Thread {

    private final BlockChain blockchain;

    public User(BlockChain bc) {
        blockchain = bc;
    }

    /**
     * Il thread tenta all'infinito di aggiungere un blocco alla blockchain
     */
    @Override
    public void run() {
        while (true) {
            blockchain.add(new Block(blockchain.getLastHash()));
            try {
                sleep((int) (Math.random() * 15000));
            } catch (InterruptedException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
