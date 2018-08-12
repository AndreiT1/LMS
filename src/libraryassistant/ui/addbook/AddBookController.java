/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import libraryassistant.database.DatabaseHandler;

/**
 *
 * @author Andrei
 */
public class AddBookController implements Initializable {
    
    
    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextField publisher;

   
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    @FXML
    void addBook(ActionEvent event) {
        String bookID=id.getText() ;
        String bookTitle=title.getText() ; 
        String bookAuthor=author.getText() ; 
        String bookPublisher=publisher.getText() ; 
        if(bookID.isEmpty()||bookTitle.isEmpty()||bookAuthor.isEmpty()||bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return ; 
            
        }
        
        
      String qu = "INSERT INTO books(ISBN,title,author,publisher)"
                             + " VALUES ('"+bookID+"','"+bookTitle+"','" +bookAuthor+"','"+bookPublisher+"');";
                            
        
             
        
       if (DatabaseHandler.getInstance().execAction(qu)) {
             
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
            
            
        } else //Error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)rootPane.getScene().getWindow() ; 
        stage.close() ; 

    }
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    
}
    

