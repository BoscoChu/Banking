
package Deposit;


import interfaceLogin.Banking;
import interfaceLogin.LoginScreenController;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;


public class DepositController implements Initializable {
    public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]

                         @FXML
                    private MFXTextField amt_field;
                         @FXML
                    private MFXPasswordField pin_field;
                         @FXML
                    private Label accountNo;
                         @FXML
                    private Label accountType;
                         @FXML
                    private Label balance;
                      Calendar cal = Calendar.getInstance();
                     int year = cal.get(Calendar.YEAR);
                      int month = cal.get(Calendar.MONTH);
                       int day = cal.get(Calendar.DAY_OF_MONTH);
                       int hour = cal.get(Calendar.HOUR);
                       int minutes = cal.get(Calendar.MINUTE);
                        int seconds = cal.get(Calendar.SECOND);
                        int dayNight = cal.get(Calendar.AM_PM);
                        
                        DateFormat dateformat = new SimpleDateFormat("YYYY-MM-dd");
                        Date d = new Date();
                        String date = dateformat.format(d);     //set up the date variable
                        
                       LocalTime localtime = LocalTime.now();
                        DateTimeFormatter dt= DateTimeFormatter.ofPattern("hh:mm:ss a");
                        String time = localtime.format(dt); //set up the time variable

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
             accountNo.setText(rs.getString("AccountNo"));
             accountType.setText(rs.getString("AccountType"));
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
              public void depositButton(){
             Connection d = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
        try{
            if(validateAMT()){
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM user WHERE AccountNo=? and PIN=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1,LoginScreenController.acc);
        ps.setString(2, pin_field.getText());
          rs = ps.executeQuery();
        
        if(rs.next()){
            BigDecimal depositAmt = new BigDecimal(amt_field.getText());
            BigDecimal balance1 = new BigDecimal(balance.getText());
            
                BigDecimal new1 = balance1.add(depositAmt);
                String sql_1 = "UPDATE  user SET Balance='"+new1+"' WHERE AccountNo='"+LoginScreenController.acc+"'" ; 
                ps = d.prepareStatement(sql_1);
                ps.execute();               //update the balance to sql
                
                String sql_2 = "INSERT INTO deposit(AccountNo,DepositAmount,NewAmount,Date,Time) VALUES (?,?,?,?,?)" ; 
                 ps = d.prepareStatement(sql_2);
                 ps.setString(1, LoginScreenController.acc);
                 ps.setString(2, String.valueOf(depositAmt));
                 ps.setString(3, String.valueOf(new1));
                 ps.setString(4, date);
                 ps.setString(5,time );
                 
                 int i = ps.executeUpdate();
                 if(i>0){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            b.insertCSS_Alert(a);
            a.setTitle("successful");
            a.setHeaderText("Deposit successful");
            a.setContentText("Amount "+depositAmt+" has been successfully deposit"+"\n\n\nCurrent Balance: "+new1);
            a.showAndWait();
            setInfo();  //update the value in this page (deposit)
            amt_field.setText("");
            pin_field.setText("");
            rs.close();
            ps.close();
            d.close();
                 }
    
            }
        
        else{
        Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Deposit Unsuccessful");
            a.setContentText("Your password is wrong. \n\nENTER AGAIN!");
            a.showAndWait();
        }
                }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Deeposit Unsuccessful");
            a.setContentText("Your amount is invalid. Maximun decimal number is two decimal places");
            a.showAndWait();
            }
        

        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in deposit.");
            a.setContentText("There is some error. \n\nPLEASE TRY AGAIN!");
            a.showAndWait();
              }
    
    
     }                  
       public boolean validateAMT(){
         Pattern p = Pattern.compile("((\\d*(\\.\\d{1,2})$)|(\\d+$))");  // only accept pattern of  letter+. 
         Matcher m = p.matcher(amt_field.getText());
         if(m.find()&&m.group().equals(amt_field.getText())&&(amt_field.getText().substring(0, 1).equals(".")==false)){
             return true;
         }
         else{
          
             return false;
         }
     }                   
                        

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfo();
    }    
    
}
