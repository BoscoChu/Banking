
package interfaceForgotPassword;

import interfaceLogin.Banking;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class ForgotPasswordController implements Initializable {
     public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
    
    
          @FXML
    private MFXTextField AccountNo;
               @FXML
    private MFXComboBox<String> SecurityQuestion;
                  @FXML
    private MFXTextField Answer;
ObservableList<String> seQuestion = FXCollections.observableArrayList("What is your pet name?","What was the first exam you failed","What is the name of your favorite childhood friend?","What is your youngest brother’s birthday month and year? (e.g., January 1900)","What was your childhood nickname?");

    
    
    
                         @FXML
     public void recoverButton(MouseEvent event) {
        Connection d = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
 
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM user WHERE AccountNo=? and SecurityQuestion=? and Answer=?";
        ps = d.prepareStatement(sql);
        
         ps.setString(1, AccountNo.getText());
         ps.setString(2, SecurityQuestion.getValue());
         ps.setString(3, Answer.getText());

        rs = ps.executeQuery();
         if(rs.next()){
             Alert a = new Alert(Alert.AlertType.INFORMATION);
              b.insertCSS_Alert(a);
             a.setTitle("Password Recoved");
             a.setHeaderText("Below is your password.");
             a.setContentText("Account No:   "+rs.getString("AccountNo")+"\nPIN:   "+rs.getString("PIN"));
             a.showAndWait();   
             rs.close();
             ps.close();
             d.close();
         }
         else{
             Alert a = new Alert(Alert.AlertType.ERROR);
              b.insertCSS_Alert(a);
             a.setTitle("Unsuccessful");
             a.setHeaderText("Incorrect information.");
             a.setContentText("Please check your information again.");
             a.showAndWait();  
               rs.close();
             ps.close();
             d.close();
         }
            

 
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in recover password!");
            a.setContentText("There is some error. PLEASE TRY AGAIN!"+e.getMessage());
            a.showAndWait();
            
        }
     
    }
    

                  @FXML
   public void backToLogin(MouseEvent event) throws IOException{
   Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/interfaceLogin/LoginScreen.fxml")));

    }
                @FXML
     public void closeButton(MouseEvent event){
                    Platform.exit();
                    System.exit(0);
     }
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       SecurityQuestion.setItems(seQuestion);
    }    
    
}
