/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiblockchain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author andrerib
 */
public class GUIBlockChain extends Application {

    public static Stage stage;
    public static BlockChain bc;

    @Override
    public void start(Stage primaryStage) {
        primaryStage = stage;
        bc = new BlockChain();
        User[] users = new User[5];
        for (int i = 0; i < users.length; i++) {
            users[i] = new User(bc);
            users[i].start();
        }
        stage = new Stage();
        Image icon = null;
        try {
            icon = new Image(new FileInputStream("icon.png"));
        } catch (FileNotFoundException e) {
            System.out.println("e");
        }
        stage.getIcons().add(icon);
        stage.setScene(bc.show());
        stage.setTitle("Blockchain");
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
