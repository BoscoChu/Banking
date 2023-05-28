
package AccountInfo;

import java.net.URL;
import DashBoard.DashBoardController;
import  interfaceLogin.Banking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import interfaceLogin.LoginScreenController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AccountInfoController implements Initializable {
                     public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
                    @FXML
                    private Text account_no;
                    @FXML
                    private Text gender;
                     @FXML
                    private Text account_type;
                     @FXML
                    private Text nationality;
                     @FXML
                    private Label balance;
                    @FXML
                    private Pane account_information;

            
        public void setInfo(){
             Connection d = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM user WHERE AccountNo=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1,LoginScreenController.acc);

          rs = ps.executeQuery();
        
        if(rs.next()){
                 balance.setText(rs.getString("Balance")); //show the balance amount on the top bar
                account_no.setText(rs.getString("AccountNo"));
                 gender.setText(rs.getString("Gender"));
                 account_type.setText(rs.getString("AccountType"));
                 nationality.setText(rs.getString("Nationality"));
                    rs.close();
                    ps.close();
                    d.close();
                 
                
         }
        
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Login Unsuccessful");
            a.setContentText("Your account number or password is wrong. \n\nENTER AGAIN!");
            a.showAndWait();
        }

 
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Login.");
            a.setContentText("There is some error. \n\nPLEASE TRY AGAIN!");
            a.showAndWait();
              }
 }
      @FXML
  public void withdraw(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Withdraw/Withdraw.fxml"));
            account_information.getChildren().removeAll();
            account_information.getChildren().addAll(fxml);
        }
  
      @FXML
  public void deposit(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Deposit/Deposit.fxml"));
            account_information.getChildren().removeAll();
            account_information.getChildren().addAll(fxml);
        }
    
    
    
    
    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfo();
    }    
    
}
