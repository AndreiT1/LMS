

package libraryassistant.ui.login;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libraryassistant.database.DatabaseHandler;

/**
 *
 * @author Andrei
 */
public class Login extends Application {
    public static Boolean isSplashLoaded= false; 
    @Override
    public void start(Stage stage) throws Exception {
       
       Parent root=FXMLLoader.load(getClass().getResource("login.fxml")) ;
       Scene scene=new Scene(root) ; 
       stage.setScene(scene) ; 
      
       stage.show() ; 
       new Thread(() -> {
           DatabaseHandler.getInstance();
       }).start();
       
      
    }
    public static void main(String[] args) {
    launch(args);
    }
    
}
