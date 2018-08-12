/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant.ui.main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libraryassistant.database.DatabaseHandler;

/**
 *
 * @author Andrei
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       
       Parent root=FXMLLoader.load(getClass().getResource("mainFXML.fxml")) ;
       Scene scene=new Scene(root) ; 
       stage.setScene(scene) ; 
       stage.show() ; 
      
    }
    public static void main(String[] args) {
    launch(args);
    }
    
}
