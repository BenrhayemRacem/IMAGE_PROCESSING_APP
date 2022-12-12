package ImageProcessingUi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;

import ij.*;
import org.opencv.core.Core;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	nu.pattern.OpenCV.loadShared();
    	//ImagePlus imagePlus = new ImagePlus("C:\\Users\\MSI\\Desktop\\chat2.pgm");
    	//WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    	//ImageView imageView = new ImageView(fxImage);
    	//creating the image object
        //InputStream stream = new FileInputStream("C:\\Users\\MSI\\Desktop\\chat2.pgm");
        //Image image = new Image(stream);
        //Creating the image view
       // ImageView imageView = new ImageView();
        //Setting image to the image view
        //imageView.setImage(image);
        //Setting the image view parameters
       // imageView.setX(10);
//        imageView.setY(10);
//        imageView.setFitWidth(575);
//        imageView.setPreserveRatio(true);
//        
      // Group root = new Group(imageView);
        //Scene scene = new Scene(root, 800, 800);
//        stage.setTitle("Displaying Image");
//        stage.setScene(scene);
//        stage.show();
       Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
       //root.getChildren().add(pr);
//        
        Scene scene = new Scene(root , 800,800);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
//        
        stage.setTitle("Image Processing app -welcome page-");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
