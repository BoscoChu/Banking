
package interfaceLogin;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
BoscoChu March 2023 - side project

 */





public class Banking extends Application {
    
    
     public static void main(String[] args) {
        launch(args);
    }
    public static Stage stage = null;
    private double xoffset = 0;         //  setup the xoffset
    private double yoffset = 0;         //  setup the yoffset 

    public void insertCSS_Alert(Alert a){
    a.initStyle(StageStyle.UNDECORATED);
    DialogPane dialogPane = a.getDialogPane();
    dialogPane.getStylesheets().add(getClass().getResource("/DesignCSS/dialog.css").toExternalForm());
    dialogPane.getStyleClass().add("dialog");
}
    
    
    
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/interfaceLogin/LoginScreen.fxml")); //load the fxml file
        Scene scene = new Scene(root);  
        scene.getStylesheets().add(getClass().getResource("/DesignCSS/design.css").toExternalForm());
         stage.initStyle(StageStyle.UNDECORATED);       // close the os decoration(close,minimize,maximize)
         stage.setScene(scene);
   
         
         
         //move the app. window
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
     
         this.stage = stage;
         stage.show();

    
    }


   
    
}
