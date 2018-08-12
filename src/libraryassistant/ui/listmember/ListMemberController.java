/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant.ui.listmember;

import static com.sun.deploy.util.SessionState.save;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import libraryassistant.ui.listbook.BookListController;
import libraryassistant.ui.addbook.AddBookController;
import libraryassistant.database.DatabaseHandler;
import org.controlsfx.control.table.TableRowExpanderColumn;

/**
 * FXML Controller class
 *
 * @author Andrei
 */
public class ListMemberController implements Initializable {
        
    ObservableList<Member> list = FXCollections.observableArrayList() ; 
    @FXML
    private TableColumn<Member, String> idCo;
    @FXML
    private TableColumn<Member, String> nameCo;
    @FXML
    private TableColumn<Member, String> mobileCo;
    @FXML
    private TableColumn<Member, String> emailCo;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Member> tableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol() ; 
        loadData();
       //test();
    }    
    private void initCol() {
    idCo.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameCo.setCellValueFactory(new PropertyValueFactory<>("name"));
    mobileCo.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    emailCo.setCellValueFactory(new PropertyValueFactory<>("email"));
    tableView.setEditable(true);
    nameCo.setCellFactory(TextFieldTableCell.forTableColumn());
    mobileCo.setCellFactory(TextFieldTableCell.forTableColumn());
    emailCo.setCellFactory(TextFieldTableCell.forTableColumn());
     
    mobileCo.setOnEditCommit(
    new EventHandler<CellEditEvent<Member, String>>() {
        @Override
        @SuppressWarnings("empty-statement")
        public void handle(CellEditEvent<Member, String> t) {
            TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
             int index = pos.getRow();
             String id = idCo.getCellObservableValue(index).getValue();;
            
            
            ((Member) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setMobile(t.getNewValue());
             
             String newMobile = mobileCo.getCellObservableValue(index).getValue();;
             
              
            String action = "UPDATE members SET mobile ='" + newMobile + "' where id ='" + id + "'";
            if(DatabaseHandler.getInstance().execAction(action)) {
                return ; 
            }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Failed");
                 alert.setHeaderText(null);
                 alert.setContentText("Member can't be updated");
                 alert.showAndWait();
            }
        }
    }
);
    
    nameCo.setOnEditCommit(
    new EventHandler<CellEditEvent<Member, String>>() {
        @Override
        public void handle(CellEditEvent<Member, String> t) {
            TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
             int index = pos.getRow();
             String id = idCo.getCellObservableValue(index).getValue();;
            
            
            ((Member) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setName(t.getNewValue());
             
             String newName = nameCo.getCellObservableValue(index).getValue();;
              
            String action = "UPDATE members SET name ='" + newName + "' where id ='" + id + "'";
            if(DatabaseHandler.getInstance().execAction(action)) {
                return ; 
            }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Failed");
                 alert.setHeaderText(null);
                 alert.setContentText("Member can't be updated");
                 alert.showAndWait();
            }
        }
    }
);
    
    
    
    emailCo.setOnEditCommit(
    new EventHandler<CellEditEvent<Member, String>>() {
        @Override
        public void handle(CellEditEvent<Member, String> t) {
            TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
             int index = pos.getRow();
             String id = idCo.getCellObservableValue(index).getValue();;
            
            
            ((Member) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setEmail(t.getNewValue());
             
             
             String newEmail = emailCo.getCellObservableValue(index).getValue();;
          
            
            String action = "UPDATE members SET email ='" + newEmail + "' where id ='" + id + "'";
            if(DatabaseHandler.getInstance().execAction(action)) {
                return ; 
            }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Failed");
                 alert.setHeaderText(null);
                 alert.setContentText("Member can't be updated");
                 alert.showAndWait();
            }
        }
    }
);
             

    }
   
    private void loadData() {
        
        String qu="SELECT * FROM members" ; 
       ResultSet rs=DatabaseHandler.getInstance().execQuery(qu) ; 
       
        try {
            while(rs.next()) {
                String name=rs.getString("name") ;
                String id=rs.getString("id") ;
                String mobile=rs.getString("mobile") ;
                String email=rs.getString("email") ;
                
                list.add(new Member(name,id,mobile,email)) ;
                
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list) ;
       
    }

    
    public class Member {
    private final SimpleStringProperty name ; 
    private final SimpleStringProperty id ; 
    private final SimpleStringProperty mobile ; 
    private final SimpleStringProperty email ; 
   
    
   public Member(String name , String id , String mobile , String email ) {
        this.name=new SimpleStringProperty(name) ; 
        this.id=new SimpleStringProperty(id) ; 
        this.mobile=new SimpleStringProperty(mobile) ; 
        this.email=new SimpleStringProperty(email) ; 
    
    }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }
        public void setName(String newName){
            name.set(newName);
            
        }
        public void setEmail(String newEmail){
            email.set(newEmail);
        }   
        public void setMobile(String newMobile){
            mobile.set(newMobile);
        }
    
    }
    /*public void test(){
         TableRowExpanderColumn<Member> expander = new TableRowExpanderColumn<>(param -> {
     HBox editor = new HBox(10);
     TextField text = new TextField(param.getValue().getName());
     Button save = new Button("Save customer");
     save.setOnAction(event -> {
         save();
         param.toggleExpanded();
     });
     editor.getChildren().addAll(text, save);
     return editor;
 });

 tableView.getColumns().add(expander);
   */
    }

