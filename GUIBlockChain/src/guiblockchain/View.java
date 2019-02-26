/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiblockchain;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author andrerib
 */
public class View {
    BlockChain bc;
    static SplitPane layout;
    ScrollPane scroll;
    VBox vbox;
    static VBox selected;
    Scene scene;
    
    public View(BlockChain bc){
        this.bc = bc;
        selected = new VBox();
        vbox = new VBox();
        scroll = new ScrollPane(vbox);
        vbox.setPrefSize(500, 600);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10));
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("BlockInfo.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        layout = new SplitPane(scroll, root);
        layout.setDividerPositions(0.71);
        scene = new Scene(layout,1000,600);
    }
    
    public void add(Block b){
        Platform.runLater(() -> {
            vbox.getChildren().add(0,b.getGraphics());
        });
    }
    
    public Scene getScene(){
        return scene;
    }
}
