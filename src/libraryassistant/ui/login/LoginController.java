/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant.ui.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import libraryassistant.database.DatabaseHandler;
import libraryassistant.ui.main.MainController;

/**
 * FXML Controller class
 *
 * @author Andrei
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField userName;
    @FXML
    private JFXPasswordField password;
  
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          

    }    
   
    
    @FXML
    private void login(ActionEvent event)  {
        String userN=userName.getText(); // Luam valoarea din TextField-ul username si o stocam in userN
        String passW=password.getText();// Luam valoarea din TextField-ul password si o stocam in passW
        
         if(userN.isEmpty()||passW.isEmpty()) { // Verificam campurile sa nu fie goale , afisam un mesaj de alerta 
            Alert alert = new Alert(Alert.AlertType.ERROR); // in caz contrat
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
        }
        else {
        
        String query="SELECT * FROM users" ;
        ResultSet rs= DatabaseHandler.getInstance().execQuery(query);
        Boolean flag=false;
        try {
            while(rs.next()){
                String user= rs.getString("username");
                String pass = rs.getString("password");
               
                if(user.equals(userN)&&pass.equals(passW)){
                    
                     Parent parent=FXMLLoader.load(getClass().getResource("/libraryassistant/ui/main/mainFXML.fxml"));
                     Stage stage =new Stage(StageStyle.DECORATED); 
                     stage.setTitle("Library Assitant");
                     stage.setScene(new Scene(parent)) ; 
                     stage.show();
                     flag=true;
                     
                     //Closing the Login frame
                     Node  source = (Node)  event.getSource(); 
                     Stage stage1  = (Stage) source.getScene().getWindow();
                     stage1.close();
                }
                
            }
            
          if(!flag) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Incorrect username or password.");
                alert.showAndWait();
                }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

       
    }

    @FXML
    private void signUp(ActionEvent event) {
         Parent parent;
        try {
         parent = FXMLLoader.load(getClass().getResource("/libraryassistant/ui/login/Signup.fxml"));
         Scene scene=new Scene(parent) ; 
         
         Stage stage =new Stage(StageStyle.DECORATED); 
         
        
         stage.setTitle("Library Assitant");
         stage.setScene(scene) ;
         
         
         stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
        
    }
}

   
    

