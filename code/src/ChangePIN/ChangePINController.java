
package ChangePIN;

import DashBoard.DashBoardController;
import interfaceLogin.Banking;
import interfaceLogin.LoginScreenController;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class ChangePINController implements Initializable {
        public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
        public DashBoardController db = new DashBoardController();   // create the object b to use the method[logout()]
                            
                            @FXML
                    private MFXPasswordField oldpin_field;
                         @FXML
                    private MFXPasswordField newpin_field1;
                         @FXML
                    private MFXPasswordField newpin_field2;
                         @FXML
                    private Label accountNo;
    
                        @FXML   
              public void changeButton(MouseEvent event){
             Connection d = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM user WHERE AccountNo=? and PIN=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1,LoginScreenController.acc);
        ps.setString(2, oldpin_field.getText());
          rs = ps.executeQuery();
        
        if(rs.next()){
            if(newpin_field1.getText().equals(newpin_field2.getText())!=true){       //if two new passwards were inconsistent
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");                             //show the messages
            a.setHeaderText("Changing unsuccessfully");
            a.setContentText("The two new passwords you entered were inconsistent! \n\n\nPlease Check it again! ");
            a.showAndWait();
            }
            else{
                String newPW = newpin_field2.getText();
             String sql_1 = "UPDATE  user SET PIN='"+newPW+"' WHERE AccountNo='"+LoginScreenController.acc+"'" ; 
             ps = d.prepareStatement(sql_1);
             ps.execute();               //update the pin 
             int i = ps.executeUpdate();
             if(i>0){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            b.insertCSS_Alert(a);
            a.setTitle("Successful");
            a.setHeaderText("Changing Successfully");
            a.setContentText("Your PIN has been changed.  \n\nNow you have to login again with the new password!");
            a.showAndWait();
          
            rs.close();
            ps.close();
            d.close();
            db.logout(event);
             }
                 }
            }

        else{
        Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Changing Unsuccessfully");
            a.setContentText("Your old password is wrong. \n\nENTER AGAIN!");
            a.showAndWait();
        }
        
        

        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in changing.");
            a.setContentText("There is some error. \n\nPLEASE TRY AGAIN!");
            a.showAndWait();
              }
    
    
     }                  
                         

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       accountNo.setText(LoginScreenController.acc);
    }    
    
}
