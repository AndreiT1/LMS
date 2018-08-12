/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant.ui.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import libraryassistant.database.DatabaseHandler;


/**
 * FXML Controller class
 *
 * @author Andrei
 */
public class SignupController implements Initializable {

    @FXML
    private JFXTextField userName;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField confirmPassword;
    @FXML
    private JFXTextField email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    

    @FXML
    private void signUP(ActionEvent event) {
        String user=userName.getText();
        String pass=password.getText();
        String confirmPass=confirmPassword.getText();
        
        
        if(user.isEmpty()||pass.isEmpty()||confirmPass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
        } else { 
        
        String query="SELECT * FROM users";
        int maxID=0;
        ResultSet rs=DatabaseHandler.getInstance().execQuery(query) ; 
        try {
            while(rs.next()){
                maxID=rs.getInt("id");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        maxID++;
        System.out.println(maxID);
       if(pass.equals(confirmPass)){
      String qu = "INSERT INTO users(id , username,password)"
                             + " VALUES ('"+maxID+"','"+user+"','"+pass +"');" ; 
                    
        
             
       
       if (DatabaseHandler.getInstance().execAction(qu)) {
             
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setContentText("Account created succesfully.");
            alert1.showAndWait();
            
            
            
        } else //Error
        {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setHeaderText(null);
            alert2.setContentText("Failed");
            alert2.showAndWait();
        }
       }
       else {
           Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setHeaderText(null);
            alert3.setContentText("Passwords do not match.");
            alert3.showAndWait();
       }
        
 
     
   }  
}  
    
}

    

