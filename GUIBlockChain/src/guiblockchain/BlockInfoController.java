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
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author andrerib
 */
public class BlockInfoController implements Initializable {

    @FXML
    private TextArea hash;
    @FXML
    private TextArea hashprec;
    @FXML
    private TextArea data;
    @FXML
    private TextArea msg;

    public static Block blocco;
    private static TextArea _hash, _phash, _data, _msg;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _hash = hash;
        _phash = hashprec;
        _data = data;
        _msg = msg;
        update();
    }

    public static void update() {
        try {
            _hash.setText(blocco.getHash());
            _phash.setText(blocco.getPrevHash());
            _data.setText(blocco.getTimeStamp2());
            _msg.setText(blocco.getData());
        } catch (Exception e) {
            _hash.setText("Hash non disponibile");
            _phash.setText("hash precedente non disponibile");
            _data.setText("Data non disponibile");
            _msg.setText("Messaggio non disponibile");
        }
    }

    private File askPath() {
        FileChooser fc = new FileChooser();
        return fc.showSaveDialog(GUIBlockChain.stage);
    }

    @FXML
    private void saveBlock(ActionEvent event) {
        File path = askPath();
        try {
            path = new File(path.getPath() + ".txt");
            path.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                try {
                    bw.write(blocco.toString());
                } catch (NullPointerException e) {
                    bw.write("No blocks have been selected.");
                }
            }        } catch (IOException ex) {
            System.out.println("Errore");
        }
    }

    @FXML
    private void saveBlockchain(ActionEvent event) {
        GUIBlockChain.bc.export(askPath());
    }

}
