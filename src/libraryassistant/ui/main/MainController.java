/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant.ui.main;


import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libraryassistant.database.DatabaseHandler;
import org.controlsfx.control.textfield.TextFields;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * FXML Controller class
 *
 * @author Andrei
 */
public class MainController implements Initializable {

    @FXML
    private HBox book_info;
    @FXML
    private HBox member_info;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;
    
   
    @FXML
    private Text memberName;
    @FXML
    private Text memberContact;
    @FXML
    private TextField bookNameSearch;
    @FXML
    private TextField bookIDSearch;
    @FXML
    private TextField memberIDSearch;
    @FXML
    private TextField memberNameSearch;
    @FXML
    private Text bookID;
    @FXML
    private Text memberID;
    @FXML
    private TextField bookID2;
    @FXML
    private ListView<String> issueDataList;
    boolean isReadyForSubmission=false ; 
    private static String returnDate ;
    public Boolean isAv ;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(book_info,1);
        JFXDepthManager.setDepth(member_info,1);
        setReturnDate();
        autoCompleteBooks();
        autoCompleteMembers();
        
       
        JFXDepthManager.setDepth(issueDataList,2);

    }    
    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/libraryassistant/ui/member/memberFXML.fxml","Add  Member");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/libraryassistant/ui/addbook/addBook.fxml","Add Book");
    }

    @FXML
    private void loadViewMembers(ActionEvent event) {
        loadWindow("/libraryassistant/ui/listmember/listMember.fxml","Members");
       
        }
    @FXML
    private void loadStatistics(ActionEvent event) {
           loadWindow("/libraryassistant/ui/statistics/statisticsFXML.fxml","Statistics");
    }
          

    @FXML
    private void loadViewBooks(ActionEvent event) {
        loadWindow("/libraryassistant/ui/listbook/bookList.fxml","Books");
    }
    
    @FXML
    private void loadIssueBooks(ActionEvent event) {
        loadWindow("/libraryassistant/ui/issuedBooks/issuedBooksFXML.fxml","Issued Books");
    }
    private void autoCompleteMembers(){
        
        String query = "SELECT * FROM members";
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs= DatabaseHandler.getInstance().execQuery(query);
        try {
            while(rs.next()){
                String name=rs.getString("name");
                list.add(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextFields.bindAutoCompletion(memberNameSearch, list);
    
    }
     private void autoCompleteBooks(){
        
        String query = "SELECT * FROM books";
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs= DatabaseHandler.getInstance().execQuery(query);
        try {
            while(rs.next()){
                String title=rs.getString("title");
                list.add(title);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextFields.bindAutoCompletion(bookNameSearch, list);
    
    }
    private void loadWindow(String loc , String title)  {
        try {
            Parent parent=FXMLLoader.load(getClass().getResource(loc));
            Stage stage =new Stage(StageStyle.DECORATED); 
            stage.setTitle(title);
            stage.setScene(new Scene(parent)) ; 
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadBookID(ActionEvent event) {
        String id=bookIDSearch.getText();
        String query="SELECT * FROM BOOKS WHERE ISBN='" + id + "'" ;
        ResultSet rs= DatabaseHandler.getInstance().execQuery(query);
        Boolean flag=false;
        try {
            while(rs.next()) {
                String bName=rs.getString("title");
                String bAuthor=rs.getString("author");
                String bID=rs.getString("ISBN");
                Boolean bStatus=rs.getBoolean("isAvailable");
                bookID.setText(bID);
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status=(bStatus)?"Available":"Not Available";
                bookStatus.setText(status);
                flag=true; 
            }
            if(!flag) {
            bookName.setText("Book not found") ; 
            bookAuthor.setText("");
            bookStatus.setText("");
            bookID.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      @FXML
    private void loadBookName(ActionEvent event) {
        String name=bookNameSearch.getText();
        String query="SELECT * FROM BOOKS WHERE title='" + name + "'" ;
        ResultSet rs= DatabaseHandler.getInstance().execQuery(query);
        Boolean flag=false;
        try {
            while(rs.next()) {
                String bName=rs.getString("title");
                String bAuthor=rs.getString("author");
                Boolean bStatus=rs.getBoolean("isAvailable");
                String bID=rs.getString("ISBN");
                bookID.setText(bID);
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status=(bStatus)?"Available":"Not Available";
                bookStatus.setText(status);
                flag=true; 
            }
            if(!flag) {
            bookName.setText("Book not found") ; 
            bookAuthor.setText("");
            bookStatus.setText("");
            bookID.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void loadMemberID(ActionEvent event) {
         String id=memberIDSearch.getText();
        String query="SELECT * FROM members WHERE id='" + id + "'" ;
        ResultSet rs= DatabaseHandler.getInstance().execQuery(query);
        Boolean flag=false;
        try {
            while(rs.next()) {
                String mName=rs.getString("name");
                String mMobile=rs.getString("mobile");
                String mID=rs.getString("id");
                memberName.setText( mName);
                memberContact.setText( mMobile);
                memberID.setText(id);
               
                flag=true; 
            }
            if(!flag) {
            memberName.setText("Member not found") ; 
            memberContact.setText("");
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     @FXML
    private void loadMemberName(ActionEvent event) {
         String name=memberNameSearch.getText();
        String query="SELECT * FROM members WHERE name='" + name + "'" ;
        ResultSet rs= DatabaseHandler.getInstance().execQuery(query);
        Boolean flag=false;
        try {
            while(rs.next()) {
                String mName=rs.getString("name");
                String mMobile=rs.getString("mobile");
                String mID=rs.getString("id");
                memberName.setText(mName);
                memberContact.setText(mMobile);
                memberID.setText(mID);
                
               
                flag=true; 
            }
            if(!flag) {
            memberName.setText("Member not found") ; 
            memberContact.setText("");
            memberID.setText("");
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    private void setReturnDate(){
        //get issueTime from DB and update return_date to issueTime+14Days
        String query="SELECT * from issue";
        ResultSet rs=DatabaseHandler.getInstance().execQuery(query);
        try {
            while(rs.next()){
                String getID=rs.getString("ISBN");
                java.sql.Date dbTime=rs.getDate("issueTime");
                java.sql.Date initialDBTime=dbTime;
                java.util.Date utilDate = new java.util.Date(dbTime.getTime());
                Calendar cal=Calendar.getInstance();
                cal.setTime(utilDate);
                cal.add(Calendar.DATE,14);
                utilDate= cal.getTime();
                dbTime=new java.sql.Date(utilDate.getTime());
                
               String qu = "UPDATE issue SET return_date=? where ISBN=?";
                
                PreparedStatement pstmt=DatabaseHandler.getInstance().getConnection().prepareStatement(qu);
                pstmt.setDate(1,dbTime);
                pstmt.setString(2,getID);

                pstmt.executeUpdate();

               /*String qu = "UPDATE issue SET return_date='"+ dbTime+"'WHERE issueTime='"+initialDBTime+"'";
               DatabaseHandler.getInstance().execAction(qu);*/
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadIssue(ActionEvent event) {
        String mID=memberID.getText();
        String bID=bookID.getText();
        String qu3="SELECT isAvailable FROM books WHERE ISBN='"+ bID + "'";
        ResultSet rs = DatabaseHandler.getInstance().execQuery(qu3);
        try {
            while(rs.next()){
                 isAv=rs.getBoolean("isAvailable");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm issue operation");
        alert.setHeaderText(null);
     
        alert.setContentText("Are you sure you wanna issue the book " + bookName.getText() + "\n to " + memberName.getText() + "? ");
        Optional<ButtonType> response= alert.showAndWait();
        System.out.println(isAv);
        if(response.get()==ButtonType.OK&&(isAv==true)) {
            
            String query = "INSERT INTO issue(memberID,ISBN)"
                    + " VALUES ('"+mID+"','"+bID +"');";
            String qu= " UPDATE members SET book_count=book_count+1 where id='" + mID + "'";     
            String query2= "UPDATE books SET isAvailable = false WHERE ISBN = '" + bID + "'";
            if(DatabaseHandler.getInstance().execAction(query)&&DatabaseHandler.getInstance().execAction(query2)&&DatabaseHandler.getInstance().execAction(qu)){
                 Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
                 alert1.setTitle("Succes");
                 alert1.setHeaderText(null);
                 alert1.setContentText("Book issue complete");
                 alert1.showAndWait();
            }else {
                Alert alert2=new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Failed");
                alert2.setHeaderText(null);
                alert2.setContentText("Operation failed.");
                alert2.showAndWait();
            }
        }else {
            Alert alert2=new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Failed");
                alert2.setHeaderText(null);
                alert2.setContentText("Book is not available");
                alert2.showAndWait();
        }
    }

    @FXML
    private void bookIDInfo(ActionEvent event) {
        
        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmission=false ;
        String bID=bookID2.getText();
        String qu = "SELECT * FROM issue WHERE ISBN ='" + bID + "'";
        ResultSet rs= DatabaseHandler.getInstance().execQuery(qu);
        
        try {
            while(rs.next()){
                String mID=rs.getString("memberID");
                Timestamp mIssueTime=rs.getTimestamp("issueTime");
                int mRenewCount=rs.getInt("renew_count");
                issueData.add("Issue date and time : " + mIssueTime.toGMTString());
                issueData.add("Renew count : "+ mRenewCount);
                issueData.add("Book information : ");
                
                
                  qu="SELECT issueTime from issue where memberID = '"+mID + "'";
                ResultSet r3=DatabaseHandler.getInstance().execQuery(qu);
                while(r3.next()){
                String issueDate= r3.getString("issueTime");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                    try {
                     
                       
                        Calendar c = Calendar.getInstance();
                        c.setTime(format.parse(issueDate));
                        c.add(Calendar.DATE, 14);  // number of days to add
                        
                        returnDate = format.format(c.getTime());
                        
                    } catch (ParseException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 
                }
                qu="SELECT * FROM books where ISBN = '"+bID+ "'";
                ResultSet r1=DatabaseHandler.getInstance().execQuery(qu);
                while(r1.next()){
                    issueData.add("Book name : " + r1.getString("title"));
                    issueData.add("Author : " + r1.getString("author"));
                    issueData.add("Publisher : " + r1.getString("publisher"));
                    issueData.add("Return date : " + returnDate);
                }
                qu="SELECT * FROM members WHERE id = '"+mID + "'";
                ResultSet r2=DatabaseHandler.getInstance().execQuery(qu);
                issueData.add("Member information : ");
                while(r2.next()){
                    issueData.add("Name : " + r2.getString("name"));
                    issueData.add("Mobile : " + r2.getString("mobile"));
                    issueData.add("Email : " + r2.getString("email"));
                    issueData.add("Adress : " + r2.getString("adress"));
                    
                }
              
                isReadyForSubmission=true ;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        issueDataList.getItems().setAll(issueData);
    }

    @FXML
    private void loadSubmission(ActionEvent event) {
        
        if(!isReadyForSubmission){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to submit");
            alert.showAndWait();
            return ; 
        }
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm submission");
        alert.setHeaderText(null);
     
        alert.setContentText("Are you sure you want to return the book?");
        Optional<ButtonType> response= alert.showAndWait();
        if(response.get()==ButtonType.OK) {
             String bID=bookID2.getText();
             String action = "DELETE FROM issue WHERE ISBN = '" + bID + "'";
             String action2 = "UPDATE books SET isAvailable = true where ISBN= '" + bID + "'";
             if(DatabaseHandler.getInstance().execAction(action)&&DatabaseHandler.getInstance().execAction(action2)){
                 Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                 alert1.setTitle("Succes");
                 alert1.setHeaderText(null);
                 alert1.setContentText("Book has been submitted");
                 alert1.showAndWait();
            }else {
                 Alert alert2=new Alert(Alert.AlertType.ERROR);
                 alert2.setTitle("Failed");
                 alert2.setHeaderText(null);
                 alert2.setContentText("Submission has failed.");
                 alert2.showAndWait();
        }
    }
}

    @FXML
    private void loadRenew(ActionEvent event) {
        if(!isReadyForSubmission){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to submit");
            alert.showAndWait();
            return ; 
        }
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm renew operation");
        alert.setHeaderText(null);
     
        alert.setContentText("Are you sure you want to renew the book?");
        Optional<ButtonType> response= alert.showAndWait();
        if(response.get()==ButtonType.OK) {
            String bID=bookID2.getText();
            String action= "UPDATE issue SET issueTime= CURRENT_TIMESTAMP ,renew_count=renew_count+1 WHERE ISBN ='"+bID+"'";
            if(DatabaseHandler.getInstance().execAction(action)){
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Succes");
                alert1.setHeaderText(null);
                alert1.setContentText("Book has been renewed");
                alert1.showAndWait();
            } else {
                Alert alert2=new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Failed");
                alert2.setHeaderText(null);
                alert2.setContentText("Operation has failed");
                alert2.showAndWait();
            
            }
    }
   
 }

    @FXML
    private void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    

    

    
}
    

