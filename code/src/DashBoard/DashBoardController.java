
package DashBoard;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import interfaceLogin.Banking;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import interfaceLogin.LoginScreenController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;



public class DashBoardController implements Initializable {
                  public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
                  private double xoffset = 0;         //  setup the xoffset
                   private double yoffset = 0;         //  setup the yoffset w
                    @FXML
                   private Text name;
                   
                    @FXML
                   private Circle ProfilePic;
                    
                    
                    @FXML
                   private Pane dashboard_main;
                    
                        @FXML
                   private FontAwesomeIconView ico;

                    @FXML
  private void closeButton(MouseEvent event){
             Platform.exit();
             System.exit(0);  
        }
    
     @FXML
  private void minimizeButton(MouseEvent event){
           Stage stage = (Stage) ico.getScene().getWindow();
           stage.setIconified(true);       //minimize + show the icon into taskbar;

  }
    
        private void setData(){
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
                InputStream is = rs.getBinaryStream("ProfilePic");
                OutputStream os = new FileOutputStream(new File("pic.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = is.read(content))!= -1) {                
                os.write(content,0,size);
            }
            os.close();
            is.close();
            Image img = new Image("file:pic.jpg", false);
            ProfilePic.setFill(new ImagePattern(img));
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
   public void click(MouseEvent event) {
                xoffset = event.getSceneX();        //set xoffset to the x intercept of MousePressed
                yoffset = event.getSceneY();        //set xoffset to the y intercept of MousePressed
            }
    @FXML
  public void drag(MouseEvent event) {
                 LoginScreenController.stage.setX(event.getScreenX() - xoffset);       //set the location
                 LoginScreenController.stage.setY(event.getScreenY() - yoffset);      //set the location 
  }
  
    @FXML
  public void accountInformation(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/AccountInfo/AccountInfo.fxml"));
            dashboard_main.getChildren().removeAll();
            dashboard_main.getChildren().addAll(fxml);
        }
          @FXML
  public void mainhome() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("DashBoard_MainHome.fxml"));
            dashboard_main.getChildren().removeAll();
            dashboard_main.getChildren().addAll(fxml);
        }
  
      @FXML
  public void withdraw(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Withdraw/Withdraw.fxml"));
            dashboard_main.getChildren().removeAll();
            dashboard_main.getChildren().addAll(fxml);
        }
  
  
    @FXML
  public void history(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/TransactionHistory/TransactionHistory.fxml"));
            dashboard_main.getChildren().removeAll();
            dashboard_main.getChildren().addAll(fxml);
        }
  
  
  
      @FXML
  public void deposit(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Deposit/Deposit.fxml"));
            dashboard_main.getChildren().removeAll();
            dashboard_main.getChildren().addAll(fxml);
        }
        @FXML
  public void transfer(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Transfer/Transfer.fxml"));
            dashboard_main.getChildren().removeAll();
            dashboard_main.getChildren().addAll(fxml);
        }
  
  
                @FXML
  public void changePIN(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/ChangePIN/ChangePIN.fxml"));
            dashboard_main.getChildren().removeAll();
            dashboard_main.getChildren().addAll(fxml);
        }

                @FXML
   public void logout(MouseEvent event) throws IOException{
                ((Node)event.getSource()).getScene().getWindow().hide();
              Parent root = FXMLLoader.load(getClass().getResource("/interfaceLogin/LoginScreen.fxml"));
              Scene scene = new Scene(root);
              scene.getStylesheets().add(getClass().getResource("/DesignCSS/design.css").toExternalForm());
              Stage stage = new Stage();
              stage.initStyle(StageStyle.UNDECORATED);  // close the os decoration(close,minimize,maximize)         
              stage.setScene(scene);
              stage.show();
                
              root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xoffset = event.getSceneX();        //set xoffset to the x intercept of MousePressed
                 yoffset = event.getSceneY();        //set xoffset to the y intercept of MousePressed
            }
        });
        
      
             //move the app. window
          root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xoffset);       //set the location
                stage.setY(event.getScreenY() - yoffset);      //set the location 
            }
        });
         
}
  
  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setData();
                     try {
                         mainhome();
                     } catch (IOException ex) {
                         Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     Banking.stage.close();
    }    
    
}
