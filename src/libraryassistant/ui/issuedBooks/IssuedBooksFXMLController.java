
package libraryassistant.ui.issuedBooks;

import static com.sun.deploy.util.SessionState.save;
import com.sun.prism.j2d.J2DPipeline;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import libraryassistant.database.DatabaseHandler;
import libraryassistant.ui.main.MainController;


import org.controlsfx.control.table.TableRowExpanderColumn;

/**
 * FXML Controller class
 *
 * @author Andre
 */
public class IssuedBooksFXMLController implements Initializable {

    
    ObservableList<IssuedBooksFXMLController.Book> list = FXCollections.observableArrayList() ; 
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book,String> idCo;
    @FXML
    private TableColumn<Book,String> titleCo;
    @FXML
    private TableColumn<Book,String> authorCo;
    @FXML
    private TableColumn<Book,String> publisherCo;
    @FXML
    private TableColumn<Book,java.util.Date> returnDateCo;
   
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //tableExpander();
        loadData();
        initCol();

    } 

      private void loadData() {
        
        try {
            String qu="SELECT * FROM issue" ;
            ResultSet rs=DatabaseHandler.getInstance().execQuery(qu) ;
            
            while(rs.next()) {
                String id=rs.getString("ISBN");
                Date date=rs.getDate("return_date");
                
                String qu2="SELECT * FROM books where ISBN='"+ id + "'";
                ResultSet rs2=DatabaseHandler.getInstance().execQuery(qu2);
                while(rs2.next()){
                    String title=rs2.getString("title");
                    String author=rs2.getString("author");
                    String publisher=rs2.getString("publisher");
                    
                    list.add(new Book(id,title,author,publisher,date));
                }
                
             }

            
            tableView.getItems().setAll(list) ;
        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooksFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
  /*   private void tableExpander(){
         TableRowExpanderColumn<Book> expander = new TableRowExpanderColumn<>(param -> {
         HBox editor = new HBox(200);
         editor.setPrefHeight(100);
         editor.setSpacing(10);
         Label lab=new Label("Lent to : ") ; 
         
         TextField txt=new TextField("dsadsa");
         
         txt.setOnAction(event -> {
        
         param.toggleExpanded();
     });
     editor.getChildren().addAll( lab,txt);
     return editor;
 });

 tableView.getColumns().add(expander);
      
      } */
      private void initCol(){
        idCo.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCo.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCo.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCo.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        returnDateCo.setCellValueFactory(new PropertyValueFactory<>("tableDate"));
        // makes return_date red if the books wasn't returned on time 
       returnDateCo.setCellFactory(new Callback<TableColumn<Book, Date>, TableCell<Book, Date>>() {
            @Override
            public TableCell<Book, Date> call(TableColumn<Book, Date> param) {
                return new TableCell<Book, Date>() {

                    @Override
                    public void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        Date currentDate=new Date();
                        String s1=String.format("%1$tY-%1$tm-%1$td", item);
                        if (!isEmpty()) {
                            if(item.compareTo(currentDate)<=0){
                            this.setTextFill(Color.RED);
                            String s = String.format("%1$tY-%1$tm-%1$td", item);
                            // Get fancy and change color based on data
                            setText(s);
                            }else {
                            setText(s1);
                            }

                        }
                    }

                };
        
        }
        });

    }
          
    public static class Book {
        private final SimpleStringProperty title ; 
        private final SimpleStringProperty id ; 
        private final SimpleStringProperty author ; 
        private final SimpleStringProperty publisher ; 
        private final SimpleObjectProperty<Date> tableDate; 
        
        
    public Book(String id, String title , String author , String publisher , Date tableDate ) {
        this.id=new SimpleStringProperty(id);
        this.title=new SimpleStringProperty(title) ; 
        this.author=new SimpleStringProperty(author) ; 
        this.publisher = new SimpleStringProperty(publisher);

        this.tableDate=new SimpleObjectProperty<Date>(tableDate);

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
        public Date getTableDate(){
            return tableDate.get();
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
        public void setTableDate(Date date){
            tableDate.set(date);
        }
    }
}
    

