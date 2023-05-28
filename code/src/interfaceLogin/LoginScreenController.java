
package interfaceLogin;

import java.io.IOException;
import  interfaceLogin.Banking;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

        
public class LoginScreenController implements Initializable {
    public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
               public static Stage stage = null;
               public static String acc ;
                      @FXML
        private Pane loginArea;
                     @FXML
        private MFXTextField loginAc;
                      @FXML
        private MFXPasswordField pin1;
                    
                    
    
            @FXML
        private void closeButton(MouseEvent event){
             Platform.exit();
             System.exit(0);  
        }
    
    @FXML
        private void createAccButton(MouseEvent event) throws IOException{
      Parent fxml = FXMLLoader.load(getClass().getResource("/interfaceCreateAccount/CreateAccount.fxml"));
            loginArea.getChildren().removeAll();
            loginArea.getChildren().addAll(fxml);
        }
        
           @FXML
        private void forgotButton(MouseEvent event) throws IOException{
      Parent fxml = FXMLLoader.load(getClass().getResource("/interfaceForgotPassword/ForgotPassword.fxml"));
            loginArea.getChildren().removeAll();
            loginArea.getChildren().addAll(fxml);
        }
        
        
        
        
                     
        public void loginButton(MouseEvent event) throws SQLException{
             acc = loginAc.getText();
                Connection d = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM user WHERE AccountNo=? and PIN=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1, loginAc.getText());
         ps.setString(2, pin1.getText());

          rs = ps.executeQuery();
        
        if(rs.next()){
             ((Node)event.getSource()).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/DashBoard/DashBoard.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/DesignCSS/design.css").toExternalForm());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);  // close the os decoration(close,minimize,maximize)         
            stage.setScene(scene);
            stage.show();
            this.stage = stage;
            
         }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Login Unsuccessful");
            a.setContentText("Your account number or password is wrong. \n\nENTER AGAIN!");
            a.showAndWait();
            pin1.setText("");

        }

 
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Login.");
            a.setContentText("MySQL server not starting. \n\nPLEASE CONTACT US TO START SERVER.");
            a.showAndWait();

              }
        finally{
            rs.close();
            ps.close();
            d.close();
            
        }

        }
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
