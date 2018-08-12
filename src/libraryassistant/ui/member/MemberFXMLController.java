
package libraryassistant.ui.member;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import libraryassistant.database.DatabaseHandler;


/**
 * FXML Controller class
 *
 * @author Andrei
 */
public class MemberFXMLController implements Initializable {

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    
  
    @FXML
    private Pane rootPane;
    @FXML
    private JFXTextField adress;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField birthDate;
    public java.sql.Date sqlDate;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addMember(ActionEvent event) {
        
        try {
            
            String memberAdress=adress.getText() ;
            String memberName=name.getText() ;
            String memberMobile=mobile.getText() ;
            String memberEmail=email.getText() ;
            String memberBD=birthDate.getText();
            
            java.util.Date  date1=new SimpleDateFormat("dd/MM/yyyy").parse(memberBD);
            java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
            
            
            
            if(memberAdress.isEmpty()||memberName.isEmpty()||memberMobile.isEmpty()||memberEmail.isEmpty()||memberBD.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Enter in all fields");
                alert.showAndWait();
                return ;
            }
            
            try {
                String qu = "INSERT INTO members(name,mobile,email,adress,dob)"
                        + " VALUES (?,?,?,?,?);";
                PreparedStatement pstmt=DatabaseHandler.getInstance().getConnection().prepareStatement(qu);
                pstmt.setString(1,memberName);
                pstmt.setString(2,memberMobile);
                pstmt.setString(3,memberEmail);
                pstmt.setString(4,memberAdress);
               
                pstmt.setDate(5,sqlDate);
                pstmt.executeUpdate();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Success");
                alert.showAndWait();
                
            } catch (SQLException ex) {
                Logger.getLogger(MemberFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
            
            /* if (DatabaseHandler.getInstance().execAction(qu)) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
            

            } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Member could not be loaded into the database");
            alert.showAndWait();
            return ;
            }
            */
        } catch (ParseException ex) {
            Logger.getLogger(MemberFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
                            
        
             
        
      
                            
        
             
        
      /* if (DatabaseHandler.getInstance().execAction(qu)) {
             
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
            
            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Member could not be loaded into the database");
            alert.showAndWait();
            return ; 
       }
       */
    }

    @FXML
    private void cancel(ActionEvent event) {
        
        Stage stage = (Stage)rootPane.getScene().getWindow() ; 
        stage.close() ; 
    }
    
}
