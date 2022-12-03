package ImageProcessingUi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class WelcomeController {

    //@FXML
   // private Label label;
    
    @FXML
    private Button buttonDemo ;
    
    @FXML
    private Button buttonStats ;

    public void initialize() {
        //label.setText("Hello, JavaFX");
    	buttonDemo.getStyleClass().add("buttonDemo");
    	
    	buttonDemo.setOnAction(e->{
    		try {
    			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stats.fxml"));
        	    Parent root1 = (Parent) fxmlLoader.load();
        	    Stage stage = new Stage();
        	   // stage.initModality(Modality.APPLICATION_MODAL);
        	    //stage.initStyle(StageStyle.UNDECORATED);
        	    stage.setTitle("ABC");
        	    stage.setScene(new Scene(root1));  
        	    stage.show();
    		}catch(Exception ex) {
    			
    		}
    		
    	});
    	
    	buttonStats.setOnAction(e->{
    		try {
    			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stats.fxml"));
    			 Parent root = FXMLLoader.load(getClass().getResource("stats.fxml"));
    		       //root.getChildren().add(pr);
//    		        
    		        Scene scene = new Scene(root , 900,600);
    		        scene.getStylesheets().add(getClass().getResource("stats.css").toExternalForm());
    		        Stage stage = new Stage();
    		        stage.setTitle("Image Processing app -stats page-");
    		        stage.setMaximized(true);
    		        stage.setScene(scene);
    		        stage.show();
    		}catch(Exception exception) {
    		
    		}
    	});
    }
}