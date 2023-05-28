
package interfaceCreateAccount;

import com.mysql.cj.conf.PropertyDefinitions;
import java.net.URL;
import  interfaceLogin.Banking;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import  interfaceLogin.Banking;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class CreateAccountController implements Initializable {
     public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
     private ArrayList<String> oldUser = new ArrayList<String>();
     private FileChooser fileChooser = new FileChooser();
     private File file;
     public String message1 ="Invalidation: ";
    public ArrayList<String> validationMessages = new ArrayList<String>();
    private FileInputStream fis;
             @FXML
    private ImageView pic;
            @FXML
    private MFXTextField Name;
            @FXML
    private MFXTextField ID;
            @FXML
    private MFXTextField MobileNo;
             @FXML
    private MFXTextField Address;
            @FXML
    private MFXTextField AccountNo;
             @FXML
    private MFXTextField PIN;
            @FXML
    private MFXTextField Balance;
            @FXML
    private MFXTextField Answer;
            @FXML
    private MFXDatePicker DOB;
             @FXML
    private MFXComboBox<String> Gender;
             @FXML
    private MFXComboBox<String> MartialStatus;
             @FXML
    private MFXTextField Nationality;
             @FXML
    private MFXComboBox<String> AccountType;
             @FXML
    private MFXComboBox<String> SecurityQuestion;
            
    ObservableList<String> list1 = FXCollections.observableArrayList("Male","Female");
     ObservableList<String> list2 = FXCollections.observableArrayList("Single","Married");
       ObservableList<String> list3 = FXCollections.observableArrayList("Saving","Current");
        ObservableList<String> list4 = FXCollections.observableArrayList("What is your pet name?","What was the first exam you failed","What is the name of your favorite childhood friend?","What is your youngest brother’s birthday month and year? (e.g., January 1900)","What was your childhood nickname?");
  
 
                 @FXML
   public void backToLogin(MouseEvent event) throws IOException{
   Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/interfaceLogin/LoginScreen.fxml")));

    }
                @FXML
     public void closeButton(MouseEvent event){
                    Platform.exit();
                    System.exit(0);
     }
      @FXML
     public void uploadPic(MouseEvent event){
         fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images File","*.png","*.jpg"));
         file = fileChooser.showOpenDialog(null);
         if (file!=null){
             Image img = new Image (file.toURI().toString(),150,150,true,true);
             pic.setImage(img);
             pic.setPreserveRatio(true);
         }
         
     }

     
                    @FXML
     public void creatButton(MouseEvent event){
         getOldAccNo();
        Connection d = null;
        PreparedStatement ps = null;
        try{
            if(checking()==true&&oldUser.contains(AccountNo.getText())==false){
           
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://192.168.0.103:3306/bank","root","");
        String sql = "INSERT INTO user(Name,ID,MobileNo,Gender,MartialStatus,Nationality,DOB,Address,AccountNo,PIN,AccountType,Balance,SecurityQuestion,Answer,ProfilePic)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ps = d.prepareStatement(sql);
        
        ps.setString(1, Name.getText());
         ps.setString(2, ID.getText());
         ps.setString(3, MobileNo.getText());
         ps.setString(4, Gender.getValue());
         ps.setString(5, MartialStatus.getValue());
         ps.setString(6, Nationality.getText());
         ps.setString(7, DOB.getValue().toString());
         ps.setString(8, Address.getText());
         ps.setString(9, AccountNo.getText());
         ps.setString(10, PIN.getText());
         ps.setString(11, AccountType.getValue());
         ps.setString(12, Balance.getText());
         ps.setString(13, SecurityQuestion.getValue());
         ps.setString(14, Answer.getText());
         fis = new FileInputStream(file);
         ps.setBinaryStream(15, (InputStream)fis, (int) file.length());
  
         int i = ps.executeUpdate();
         if(i>0){
             Alert a = new Alert(AlertType.INFORMATION);
              b.insertCSS_Alert(a);
             a.setTitle("Account Created");
             a.setHeaderText("Account Created successfully.");
             a.setContentText("Your account has been created successfully. \n\nYou can login with your Account No and PIN now");
             a.showAndWait();    
         }
          Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/interfaceLogin/LoginScreen.fxml")));
            ps.close();
            d.close();

           } 
           if (oldUser.contains(AccountNo.getText())==true){
            Alert q = new Alert(AlertType.ERROR);
            b.insertCSS_Alert(q);
            q.setTitle("Invalid Account No!");
            q.setContentText("Account No :"+AccountNo.getText()+" has been created! \nPlease choose other Account No.");
            q.showAndWait(); 
           }
            
            
            if(checking()==false&& (DOB.getValue().toString())!=null){
          Alert q = new Alert(AlertType.ERROR);
            b.insertCSS_Alert(q);
            q.setTitle("Below is Invalidation of Information!");
            q.setContentText(message1);
            q.showAndWait();    
            
         }
            
            
            
        }catch(Exception ex){
            Alert a = new Alert(AlertType.ERROR);
             b.insertCSS_Alert(a);
            a.setTitle("Missing Information!");
            a.setHeaderText("Error in creating account!");
            a.setContentText("Your account is not created. \n\nPlease complete all the information. \n\nTRY AGAIN!"+ex.getMessage());
            a.showAndWait();
            
            
        }
         }
      

     
     
     public boolean validateAccountName(){
         Pattern p = Pattern.compile("[a-zA-Z0-9]{4,15}$");  // only accept pattern of 4-15 number+letter 
         Matcher m = p.matcher(AccountNo.getText());
         if(m.find()&&m.group().equals(AccountNo.getText())){
             return true;
         }
         else{
             validationMessages.add("AccountNO,");
             return false;
             
         }
     }
      public boolean validatePIN(){
         Pattern p = Pattern.compile("[a-zA-Z0-9_.-]{6,20}$");  // only accept pattern of 6-20 number+letter+"."+"_"+"-"
         Matcher m = p.matcher(PIN.getText());
         if(m.find()&&m.group().equals(PIN.getText())){
             return true;
         }
         else{
             validationMessages.add("PIN,");
             return false;
             
         }
     }
        public boolean validateName(){
         Pattern p = Pattern.compile("[a-zA-Z ,-]+");  // only accept pattern of letter +"-"+","
         Matcher m = p.matcher(Name.getText());
         if(m.find()&&m.group().equals(Name.getText())){
             return true;
         }
         else{
             validationMessages.add("Name,");
             return false;
             
         }
     }
     public boolean validateID(){
         Pattern p = Pattern.compile("[a-zA-Z0-9]+");  // only accept pattern of  number+letter 
         Matcher m = p.matcher(ID.getText());
         if(m.find()&&m.group().equals(ID.getText())){
             return true;
         }
         else{
             validationMessages.add("ID,");
             return false;
         }
     }
      public boolean validateMobileNo(){
         Pattern p = Pattern.compile("[0-9]+");  // only accept pattern of  number
         Matcher m = p.matcher(MobileNo.getText());
         if(m.find()&&m.group().equals(MobileNo.getText())){
             return true;
         }
         else{
             validationMessages.add("MobileNo,");
             return false;
         }
     }
      public boolean validateNationality(){
         Pattern p = Pattern.compile("[a-zA-Z ]+");  // only accept pattern of  letter 
         Matcher m = p.matcher(Nationality.getText());
         if(m.find()&&m.group().equals(Nationality.getText())){
             return true;
         }
         else{
             validationMessages.add("Nationality,");
             return false;
         }
     }
      public boolean validateAddress(){
         Pattern p = Pattern.compile("[a-zA-Z0-9 ,/-]+");  // only accept pattern of  letter+number+(,/-) 
         Matcher m = p.matcher(Address.getText());
         if(m.find()&&m.group().equals(Address.getText())){
             return true;
         }
         else{
             validationMessages.add("Address,");
             return false;
         }
     }
          public boolean validateAnswer(){
         Pattern p = Pattern.compile("[a-zA-Z0-9 ]+");  // only accept pattern of  letter+number 
         Matcher m = p.matcher(Answer.getText());
         if(m.find()&&m.group().equals(Answer.getText())){
             return true;
         }
         else{
             validationMessages.add("Answer,");
             return false;
         }
     }
     
       public boolean validateBalance(){
         Pattern p = Pattern.compile("((\\d*(\\.\\d{1,2})$)|(\\d+$))");  // only accept pattern of  letter+. 
         Matcher m = p.matcher(Balance.getText());
         if(m.find()&&m.group().equals(Balance.getText())&&(Balance.getText().substring(0, 1).equals(".")==false)){
             return true;
         }
         else{
             validationMessages.add("Balance,");
             return false;
         }
     }

       public boolean validateGender(){
     
         if(Gender.getValue()==null){
             validationMessages.add("Gender,");
             return false;
         }
         else{
             return true;
         }
     }
              public boolean validateMartialStatus(){
     
         if(MartialStatus.getValue()==null){
             validationMessages.add("MartialStatus,");
             return false;
         }
         else{
             return true;
         }
     }
       
                  public boolean validateDOB(){
     
         if((DOB.getValue().toString())==null){
             validationMessages.add("Date Of Birthday,");
             return false;
         }
         else{
             return true;
         }
     }
                   public boolean validateAccountType(){
     
         if(AccountType.getValue()==null){
             validationMessages.add("AccountType,");
             return false;
         }
         else{
             return true;
         }
     }        
                  
           public boolean validateSecurityQuestion(){
     
         if(SecurityQuestion.getValue()==null){
             validationMessages.add("SecurityQuestion,");
             return false;
         }
         else{
             return true;
         }
     }          
                  

       
      public boolean checking(){
          try {
         validationMessages.clear(); 
         validateAccountName();
         validatePIN();
         validateAddress();
         validateAnswer();
         validateBalance();
         validateMobileNo();
         validateName();
         validateNationality();
         validateID();
         validateGender();
         validateMartialStatus();
         validateDOB();
         validateAccountType();
         validateSecurityQuestion();
         if(validationMessages.isEmpty()){
             return true;
         }
         else{
            message1 ="Invalidation: ";
              for (String i : validationMessages) {
                    message1+=i;
            }

            message1+="Check it again.";
             return false;
         }
         } catch (Exception e) {

             return false;
          }
      }
        public void getOldAccNo(){
            
                 Connection d = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://192.168.0.103:3306/bank","root","");
        String sql = "SELECT AccountNo FROM user" ;
        ps = d.prepareStatement(sql);
          rs = ps.executeQuery();
         while(rs.next()){
            oldUser.add(rs.getString("AccountNo"));
         }
        }catch(Exception e){
            System.out.println(e.getMessage());
              }
        }
      
      
      
      
                    @FXML
      public void generateAccNo(MouseEvent event){
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //4 - 15 length
        int length = (int)(4+Math.random()*(15-4+1));
        
        
        for(int i = 0; i < length; i++) {
            
            int chatTypa = random.nextInt(3);
            switch (chatTypa){
                case 0:
                    //number
                    val.append(random.nextInt(10));
                    break;
                case 1:
                    //lower case
                    val.append((char) (random.nextInt(26) + 97));
                    break;
                case 2:
                    //capital letter
                    val.append((char) (random.nextInt(26) + 65));
                    break;
            }
        }
        AccountNo.setText(val.toString());
    }
            @FXML
      public void generatePIN(MouseEvent event){
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //4 - 15 length
        int length = (int)(6+Math.random()*(20-6+1));
        
        
        for(int i = 0; i < length; i++) {
            
            int chatTypa = random.nextInt(3);
            switch (chatTypa){
                case 0:
                    //number
                    val.append(random.nextInt(10));
                    break;
                case 1:
                    //lower case
                    val.append((char) (random.nextInt(26) + 97));
                    break;
                case 2:
                    //capital letter
                    val.append((char) (random.nextInt(26) + 65));
                    break;
            }
        }
        PIN.setText(val.toString());
    }

      
        
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Gender.setItems(list1);
        MartialStatus.setItems(list2);
        AccountType.setItems(list3);
        SecurityQuestion.setItems(list4);
    }    
    
}
