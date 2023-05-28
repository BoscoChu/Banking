
package Transfer;

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
import java.sql.SQLException;
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


public class TransferController implements Initializable {
 public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
                                @FXML
                    private MFXTextField transferAc_field;
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

                        @FXML   
              public void searchButton(){
                  Connection d = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql0 = "SELECT* FROM user WHERE AccountNo=?" ;
        ps = d.prepareStatement(sql0);
        
        ps.setString(1,transferAc_field.getText());
          rs = ps.executeQuery();
         if(rs.next()){
          String name = rs.getString("Name");
           String mob_No =rs.getString("MobileNo" );
           Alert a = new Alert(Alert.AlertType.INFORMATION);
            b.insertCSS_Alert(a);
            a.setTitle("Searching Successfully");
            a.setHeaderText("Below is the information of account:");
            a.setContentText("\nAccount: "+transferAc_field.getText()+"\nName: "+name+"\nMobile No: "+mob_No);
            a.showAndWait();
            }
         else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Searching Unsuccessfully.");
            a.setContentText("Account Data Not Found. \n\nPLEASE CHECK THE ACCOUNT NUMBER AGAIN!");
            a.showAndWait();
         }
  

        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Searching.");
            a.setContentText("There is some error. \n\nPLEASE TRY AGAIN!");
            a.showAndWait();
              }
 
              }
                
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
              public void transferButton() throws SQLException{
               
             Connection d = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
           String transferAc = transferAc_field.getText();
         try{
              if(validateAMT()){
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM user WHERE AccountNo=? and PIN=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1,LoginScreenController.acc);
        ps.setString(2, pin_field.getText());
          rs = ps.executeQuery();
        if(LoginScreenController.acc.equals(transferAc)){
            Alert a = new Alert(Alert.AlertType.ERROR);
              b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Transfer Unsuccessful");
            a.setContentText("You cannot transfer to yourself!");
            a.showAndWait();
        }
        
        else{
        if(rs.next()){
            BigDecimal transferAmt = new BigDecimal(amt_field.getText());
            BigDecimal balance1 = new BigDecimal(balance.getText());
            BigDecimal transferAcBalance;
            
            if(balance1.compareTo(transferAmt)==-1){           //if there is no enough balance to withdraw
            Alert a = new Alert(Alert.AlertType.ERROR);
              b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Transfer Unsuccessful");
            a.setContentText("You don't have enough balance. \n\nENTER AGAIN!");
            a.showAndWait();    
            }
            if((balance1.compareTo(transferAmt)==1)||(balance1.compareTo(transferAmt)==0)){
                
                  String sql_0 ="SELECT* FROM user WHERE AccountNo=?" ;
                   ps = d.prepareStatement(sql_0);
                   ps.setString(1,transferAc);
                    rs = ps.executeQuery();
  
                
                     if(rs.next()){
                     balance1 = balance1.subtract(transferAmt);    
                String sql_1 = "UPDATE  user SET Balance='"+balance1+"' WHERE AccountNo='"+LoginScreenController.acc+"'" ; 
                ps = d.prepareStatement(sql_1);
                ps.execute();               //update the balance of user 
                      transferAcBalance = new BigDecimal(rs.getString("Balance"));
                     transferAcBalance = transferAcBalance.add(transferAmt);
                String sql_3 = "UPDATE  user SET Balance='"+transferAcBalance+"' WHERE AccountNo='"+transferAc+"'" ; 
                ps = d.prepareStatement(sql_3);
                ps.execute();               //update the balance of transfer_ac 
                
                 
                
                
                
                
                String sql_2 = "INSERT INTO transfer(AccountNo,TransferAmount,Sendto,Date,Time) VALUES (?,?,?,?,?)" ; 
                 ps = d.prepareStatement(sql_2);
                 ps.setString(1, LoginScreenController.acc);
                 ps.setString(2, String.valueOf(transferAmt));
                 ps.setString(3, String.valueOf(transferAc));
                 ps.setString(4, date);
                 ps.setString(5,time );
                 
                 int i = ps.executeUpdate();
                 if(i>0){
                 
                String sql_5 = "INSERT INTO receive(AccountNo,TransferAmount,ReceivefFrom,Date,Time) VALUES (?,?,?,?,?)" ; 
                 ps = d.prepareStatement(sql_5);
                 ps.setString(1, transferAc);
                 ps.setString(2, String.valueOf(transferAmt));
                 ps.setString(3, String.valueOf(LoginScreenController.acc));
                 ps.setString(4, date);
                 ps.setString(5,time );
                 
                 int i1 = ps.executeUpdate();
                 if(i1>0){
                 
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            b.insertCSS_Alert(a);
            a.setTitle("successful");
            a.setHeaderText("Transfer successful");
            a.setContentText("Amount "+transferAmt+" has been successfully transfer "+"\nto the account: "+transferAc+"\n\n\nCurrent Balance: "+balance1);
            a.showAndWait();
            setInfo();  //update the value in this page (withdraw)
            amt_field.setText("");
            pin_field.setText("");
            transferAc_field.setText("");
            rs.close();
            ps.close();
            d.close();}
                  else{
            Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Transfer Unsuccessfully");
            a.setContentText("Your password is wrong. \n\nENTER AGAIN!");
            a.showAndWait();
            }   
                 }

                 else{
            Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Transfer Unsuccessfully");
            a.setContentText("Your password is wrong. \n\nENTER AGAIN!");
            a.showAndWait();
            }
                 
 } 
                   else{
                    Alert a = new Alert(Alert.AlertType.ERROR);
                     b.insertCSS_Alert(a);
                    a.setTitle("Unsuccessful");
                    a.setHeaderText("Transfer Unsuccessful");
                    a.setContentText("Account number does not exist. \n\nPlease Check It AGAIN!");
                    a.showAndWait(); 
                         }
    
            }
  
         }
        else{
        Alert a = new Alert(Alert.AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Transfer Unsuccessfully");
            a.setContentText("Your password is wrong. \n\nENTER AGAIN!");
            a.showAndWait();
        }}}else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Unsuccessful");
            a.setHeaderText("Transfer Unsuccessful");
            a.setContentText("Your amount is invalid. Maximun decimal number is two decimal places");
            a.showAndWait();
            }
        
        
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Transfer.");
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
