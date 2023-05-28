
package TransactionHistory;

import interfaceLogin.Banking;
import interfaceLogin.LoginScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import io.github.palexdev.materialfx.utils.FXCollectors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class TransactionHistoryController implements Initializable {
      public Banking b = new Banking();  // create the object b to use the method [insertCSS_Alert()]
                Connection d = null;
                PreparedStatement ps = null;
                 ResultSet rs = null;
                      @FXML
                  private MFXLegacyTableView<History> type_table;
                     @FXML
                  private TableColumn<History, String> type_accountNo;
                        @FXML
                  private TableColumn<History, String> type_amt;
                        @FXML
                  private TableColumn<History, String> type_balance;
                        @FXML
                  private TableColumn<History, String> type_date;
                        @FXML
                  private TableColumn<History, String> type_time;
                        @FXML
                  private MFXLegacyTableView<History> type_table2;
                     @FXML
                  private TableColumn<History, String> type_accountNo2;
                        @FXML
                  private TableColumn<History, String> type_amt2;
                        @FXML
                  private TableColumn<History, String> type_balance2;
                        @FXML
                  private TableColumn<History, String> type_date2;
                        @FXML
                  private TableColumn<History, String> type_time2;
                            @FXML
                  private MFXLegacyTableView<History> type_table3;
                     @FXML
                  private TableColumn<History, String> type_accountNo3;
                        @FXML
                  private TableColumn<History, String> type_amt3;
                        @FXML
                  private TableColumn<History, String> type_balance3;
                        @FXML
                  private TableColumn<History, String> type_date3;
                        @FXML
                  private TableColumn<History, String> type_time3;
                                              @FXML
                  private MFXLegacyTableView<History> type_table4;
                     @FXML
                  private TableColumn<History, String> type_accountNo4;
                        @FXML
                  private TableColumn<History, String> type_amt4;
                        @FXML
                  private TableColumn<History, String> type_balance4;
                        @FXML
                  private TableColumn<History, String> type_date4;
                        @FXML
                  private TableColumn<History, String> type_time4;
                         @FXML
                  private MFXLegacyTableView<History> type_table5;
                     @FXML
                  private TableColumn<History, String> type_accountNo5;
                        @FXML
                  private TableColumn<History, String> type_amt5;
                        @FXML
                  private TableColumn<History, String> type_person5;
                        @FXML
                  private TableColumn<History, String> type_date5;
                        @FXML
                  private TableColumn<History, String> type_time5;
            private ObservableList<History> list2 = FXCollections.observableArrayList();//for comprehensive history
 
                @FXML
              public void withdrawMethod(){
       type_accountNo.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_balance.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
       type_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
       type_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
        type_accountNo5.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt5.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_person5.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
      type_date5.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
      type_time5.setCellValueFactory(new PropertyValueFactory<History,String>("time"));      
        ObservableList<History> list = FXCollections.observableArrayList();
       
              try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM withdraw WHERE AccountNo=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1,LoginScreenController.acc);

          rs = ps.executeQuery();
        
        while(rs.next()){
     list.add(new History(rs.getString("AccountNo"),rs.getString("WithdrawAmount"),rs.getString("RemainingAmount"),rs.getString("Date"),rs.getString("Time")));
     list2.add(new History(rs.getString("AccountNo"),"-"+rs.getString("WithdrawAmount"),"Withdraw Money",rs.getString("Date"),rs.getString("Time")));

  
         }

        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data.");
            a.setContentText("There is some error in Fetching Data. \n\nPLEASE TRY AGAIN!");
            a.showAndWait();
              }
   
             type_table.setItems(list);
             type_table5.setItems(list2);
              }
                        
              @FXML
              public void depositMethod(){
       type_accountNo2.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt2.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_balance2.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
       type_date2.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
       type_time2.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
       
       type_accountNo5.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt5.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_person5.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
      type_date5.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
      type_time5.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
     

              
        ObservableList<History> list = FXCollections.observableArrayList();
         
              try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM deposit WHERE AccountNo=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1,LoginScreenController.acc);

          rs = ps.executeQuery();
        
        while(rs.next()){
     list.add(new History(rs.getString("AccountNo"),rs.getString("DepositAmount"),rs.getString("NewAmount"),rs.getString("Date"),rs.getString("Time")));
     list2.add(new History(rs.getString("AccountNo"),"+"+rs.getString("DepositAmount"),"Saving a deposit",rs.getString("Date"),rs.getString("Time")));
         }

        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data.");
            a.setContentText("There is some error in Fetching Data. \n\nPLEASE TRY AGAIN!");
            a.showAndWait();
              }
   
             type_table2.setItems(list);
             type_table5.setItems(list2);
              }
               @FXML
              public void transferMethod(){
       type_accountNo3.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt3.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_balance3.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
       type_date3.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
       type_time3.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
         type_accountNo5.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt5.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_person5.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
      type_date5.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
      type_time5.setCellValueFactory(new PropertyValueFactory<History,String>("time"));      
        ObservableList<History> list = FXCollections.observableArrayList();
         
              try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM transfer WHERE AccountNo=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1,LoginScreenController.acc);

          rs = ps.executeQuery();
        
        while(rs.next()){
     list.add(new History(rs.getString("AccountNo"),rs.getString("TransferAmount"),rs.getString("Sendto"),rs.getString("Date"),rs.getString("Time")));
     list2.add(new History(rs.getString("AccountNo"),"-"+rs.getString("TransferAmount"),"Transfer to "+rs.getString("Sendto"),rs.getString("Date"),rs.getString("Time")));

         }

        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data.");
            a.setContentText("There is some error in Fetching Data. \n\nPLEASE TRY AGAIN!");
            a.showAndWait();
              }
   
             type_table3.setItems(list);
              type_table5.setItems(list2);
              }
              
              @FXML
              public void receivedMethod(){
       type_accountNo4.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt4.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_balance4.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
       type_date4.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
       type_time4.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
        type_accountNo5.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt5.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_person5.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
      type_date5.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
      type_time5.setCellValueFactory(new PropertyValueFactory<History,String>("time"));      
        ObservableList<History> list = FXCollections.observableArrayList();
         
   
              try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        d = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
        String sql = "SELECT* FROM receive WHERE AccountNo=?" ;
        ps = d.prepareStatement(sql);
        
        ps.setString(1,LoginScreenController.acc);

          rs = ps.executeQuery();
        
        while(rs.next()){
     list.add(new History(rs.getString("AccountNo"),rs.getString("TransferAmount"),rs.getString("ReceivefFrom"),rs.getString("Date"),rs.getString("Time")));
     list2.add(new History(rs.getString("AccountNo"),"+"+rs.getString("TransferAmount"),"Receive from "+rs.getString("ReceivefFrom"),rs.getString("Date"),rs.getString("Time")));

         }

        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            b.insertCSS_Alert(a);
            a.setTitle("Error");
            a.setHeaderText("Error in Fetching Data.");
            a.setContentText("There is some error in Fetching Data. \n\nPLEASE TRY AGAIN!");
            a.showAndWait();
              }
   
             type_table4.setItems(list);
             type_table5.setItems(list2);
              }
              
              
               @FXML
              public void comprehensiveMethod(){
     
        type_accountNo5.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
       type_amt5.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
       type_person5.setCellValueFactory(new PropertyValueFactory<History,String>("balance"));
      type_date5.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
      type_time5.setCellValueFactory(new PropertyValueFactory<History,String>("time"));      
        
             type_table5.setItems(list2);
              }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        withdrawMethod();
        depositMethod();
        transferMethod();
        receivedMethod();
        comprehensiveMethod();
    }    
    
}
