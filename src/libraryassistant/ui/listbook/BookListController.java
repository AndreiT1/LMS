
package libraryassistant.ui.listbook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import libraryassistant.ui.addbook.AddBookController;
import libraryassistant.database.DatabaseHandler;
import libraryassistant.ui.listmember.ListMemberController;

public class BookListController implements Initializable {
     
    
    ObservableList<Book> list = FXCollections.observableArrayList() ; 
    
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book,String> titleCo;
    @FXML
    private TableColumn<Book,String> idCo;
    @FXML
    private TableColumn<Book,String> authorCo;
    @FXML
    private TableColumn<Book,String> publisherCo;
    @FXML
    private TableColumn<Book,Boolean> availableCo;

  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol() ;
        
        loadData() ; 
        
    }    

    private void initCol() {
        idCo.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCo.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCo.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCo.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availableCo.setCellValueFactory(new PropertyValueFactory<>("available"));
        tableView.setEditable(true);
        titleCo.setCellFactory(TextFieldTableCell.forTableColumn());
        authorCo.setCellFactory(TextFieldTableCell.forTableColumn());
        publisherCo.setCellFactory(TextFieldTableCell.forTableColumn());
        
        titleCo.setOnEditCommit(
    new EventHandler<TableColumn.CellEditEvent<BookListController.Book, String>>() {
        @Override
        @SuppressWarnings("empty-statement")
        public void handle(TableColumn.CellEditEvent<BookListController.Book, String> t) {
            TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
             int index = pos.getRow();
             
             String id = idCo.getCellObservableValue(index).getValue();;
             
             
            
            
            ((BookListController.Book) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setTitle(t.getNewValue());
             
             String newTitle = titleCo.getCellObservableValue(index).getValue();;
             
              
            String action = "UPDATE books SET title ='" + newTitle + "' where ISBN ='" + id + "'";
            if(DatabaseHandler.getInstance().execAction(action)) {
                return ; 
            }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Failed");
                 alert.setHeaderText(null);
                 alert.setContentText("Book can't be updated");
                 alert.showAndWait();
            }
        }
    }
);
      authorCo.setOnEditCommit(
    new EventHandler<TableColumn.CellEditEvent<BookListController.Book, String>>() {
        @Override
        @SuppressWarnings("empty-statement")
        public void handle(TableColumn.CellEditEvent<BookListController.Book, String> t) {
            TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
             int index = pos.getRow();
             
             String id = idCo.getCellObservableValue(index).getValue();;
             
            
            ((BookListController.Book) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setAuthor(t.getNewValue());
             
             String newAuthor = authorCo.getCellObservableValue(index).getValue();;
             
              
            String action = "UPDATE books SET author ='" + newAuthor + "' where ISBN ='" + id + "'";
            if(DatabaseHandler.getInstance().execAction(action)) {
                return ; 
            }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Failed");
                 alert.setHeaderText(null);
                 alert.setContentText("Book can't be updated");
                 alert.showAndWait();
            }
        }
    }
);
        publisherCo.setOnEditCommit(
    new EventHandler<TableColumn.CellEditEvent<BookListController.Book, String>>() {
        @Override
        @SuppressWarnings("empty-statement")
        public void handle(TableColumn.CellEditEvent<BookListController.Book, String> t) {
            TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
             int index = pos.getRow();
             
             String id = idCo.getCellObservableValue(index).getValue();;
             
            
            ((BookListController.Book) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setPublisher(t.getNewValue());
             
             String newPublisher = publisherCo.getCellObservableValue(index).getValue();;
             
              
            String action = "UPDATE books SET publisher ='" + newPublisher + "' where ISBN ='" + id + "'";
            if(DatabaseHandler.getInstance().execAction(action)) {
                return ; 
            }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Failed");
                 alert.setHeaderText(null);
                 alert.setContentText("Book can't be updated");
                 alert.showAndWait();
            }
        }
    }
);
          
        
    }

    private void loadData() {
        
       String qu="SELECT * FROM books" ; 
       ResultSet rs=DatabaseHandler.getInstance().execQuery(qu) ; 
        try {
            while(rs.next()) {
                String title=rs.getString("title") ;
                String id=rs.getString("ISBN") ;
                String author=rs.getString("author") ;
                String publisher=rs.getString("publisher") ;
                Boolean available=rs.getBoolean("isAvailable");
                list.add(new Book(id,title,author,publisher,available)) ;
                
            }} catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list) ;
    }
    public static class Book {
        private final SimpleStringProperty title ; 
        private final SimpleStringProperty id ; 
        private final SimpleStringProperty author ; 
        private final SimpleStringProperty publisher ; 
        private final SimpleBooleanProperty available ; 
    
    Book( String id, String title  , String author , String publisher , Boolean available) {
        this.id=new SimpleStringProperty(id);
        this.title=new SimpleStringProperty(title) ; 
        this.author=new SimpleStringProperty(author) ; 
        this.publisher = new SimpleStringProperty(publisher);
        this.available=new SimpleBooleanProperty(available) ; 
    }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getAvailable() {
            return available.get();
        }
        public void setTitle(String newTitle){
        title.set(newTitle);
        }
        public void setAuthor(String newAuthor){
        author.set(newAuthor);
        }
        public void setPublisher(String newPublisher){
        publisher.set(newPublisher);
        }
    
    
   }
}
