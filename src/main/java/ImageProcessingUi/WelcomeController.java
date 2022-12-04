package ImageProcessingUi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class WelcomeController {

    //@FXML
   // private Label label;
    
    @FXML
    private Button buttonFiltersPGM ;
    
    @FXML
    private Button buttonStats ;
    
    @FXML
    private void navigateToFiltersPGMWindow(ActionEvent event) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("filtersPGM.fxml"));
    		 Scene scene = new Scene(root , 900,600);
		        scene.getStylesheets().add(getClass().getResource("filtersPGM.css").toExternalForm());
		        Stage stage = new Stage();
		        stage.setTitle("Image Processing app -Filters page-");
		        stage.setMaximized(true);
		        stage.setScene(scene);
		        stage.show();
    	}catch(Exception e) {
    		
    	}
    }

    public void initialize() {
        //label.setText("Hello, JavaFX");
  
    	
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