
package DashBoard;


import interfaceLogin.Banking;
import interfaceLogin.LoginScreenController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class DashBoard_MainHomeController implements Initializable {
    
public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
                         @FXML
                    private Label name;
                         @FXML
                    private Label body;
                       @FXML
                     private Pane dashboard_main1;  
                         
        public void sethome(){
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
                name.setText(rs.getString("Name"));
            rs.close();
            ps.close();
            d.close();

         }
        
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Login Unsuccessful");
            a.setContentText("Your account number or password is wrong. ENTER AGAIN!");
            a.showAndWait();
        }

 
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Login.");
            a.setContentText("There is some error. PLEASE TRY AGAIN!");
            a.showAndWait();
              }
 }
     @FXML
  public void history(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/TransactionHistory/TransactionHistory.fxml"));
            dashboard_main1.getChildren().removeAll();
            dashboard_main1.getChildren().addAll(fxml);
        }
  @FXML
    public void transfer(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Transfer/Transfer.fxml"));
            dashboard_main1.getChildren().removeAll();
            dashboard_main1.getChildren().addAll(fxml);
        }
    
    
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sethome();
    }    
    
}
