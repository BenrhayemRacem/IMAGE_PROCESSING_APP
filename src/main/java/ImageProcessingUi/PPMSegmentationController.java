package ImageProcessingUi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.PGMStatsService;
import services.PPMSegmentationService;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;

import ij.*;
import ij.plugin.PGM_Reader;


public class PPMSegmentationController {

	@FXML
	private Button chooseFileButton;
	
	@FXML
	private Button submitButton;
	
	@FXML 
	private Label label;
	@FXML 
	private Label labelColors;
	
	@FXML
	private ImageView imageView;
	@FXML
	private ImageView imageViewAfterThresholdNormal;
	@FXML
	private ImageView imageViewAfterThresholdAnd;
	@FXML
	private ImageView imageViewAfterThresholdOr;
	
	 @FXML
	 private TextField red ;
	 @FXML
	 private TextField green ;
	 @FXML
	 private TextField blue ;
	
	private FileChooser fileChooser = new FileChooser();
	private String ppmFilePath = null ;
	private PPMSegmentationService ppmSegServiceNormal = null;
	
	 private ViewerUpdateThread viewerUpdateThread;
	 private ViewerUpdateThread viewerUpdateThreadAnd;

	 private ViewerUpdateThread viewerUpdateThreadOr;

	
	public void setPpmFilePath(String s) {
		this.ppmFilePath = s;
	}
	public String getPpmFilePath() {
		return this.ppmFilePath;
	}
	
	public void displayChoosenPPMFile() {
		String path = this.getPpmFilePath();
		if(path!=null) {
			ImagePlus imagePlus = new ImagePlus(path);
			WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
			imageView.setImage(fxImage);
		}
		 
		  
	}
	
	@FXML
	private void handleSubmit(ActionEvent event) {
		try {
			URL imageURL = getClass().getResource("afterNormalThreshold.ppm");
			 String path = imageURL.getPath() ;
			 
			 URL imageURLAnd = getClass().getResource("afterAndThreshold.ppm");
			 String pathAnd = imageURLAnd.getPath() ;
			 
			 URL imageURLOr = getClass().getResource("afterOrThreshold.ppm");
			 String pathOr = imageURLOr.getPath() ;
			 //System.out.println(path);
			 //System.out.println(getPpmFilePath());
			 int redVal = redThresholdResolver();
			 int greenVal = greenThresholdResolver();
			 int blueVal = blueThresholdResolver();
			 labelColors.setText("default values : red="+redVal+" , green="+greenVal+" , blue="+blueVal);
			 
			 PPMSegmentationService ppmService = new PPMSegmentationService(getPpmFilePath());
			 PPMSegmentationService ppmServiceAnd = new PPMSegmentationService(getPpmFilePath());
			 PPMSegmentationService ppmServiceOr = new PPMSegmentationService(getPpmFilePath());
			 ppmService.seuilNormal(redVal, greenVal, blueVal, path);
			 ppmServiceAnd.seuilET(redVal, greenVal, blueVal, pathAnd);
			 ppmServiceOr.seuilOR(redVal, greenVal, blueVal, pathOr);
			 File file = new File(path);
			 File fileAnd = new File(pathAnd);
			 File fileOr = new File(pathOr);
			 	imageViewAfterThresholdNormal.setImage(PpmConverter.convert(file));
			 	imageViewAfterThresholdAnd.setImage(PpmConverter.convert(fileAnd));
			 	imageViewAfterThresholdOr.setImage(PpmConverter.convert(fileOr));
			 //bindImageFileToViewer(file,imageViewAfterThresholdNormal);
			// bindImageFileToViewer(fileAnd,imageViewAfterThresholdAnd);
			// bindImageFileToViewer(fileOr,imageViewAfterThresholdOr);


			 //ppmService.getPpmImage().writeImage(path);
			 //PGM_Reader reader = new PGM_Reader();
			
			// ImagePlus imagePlus = new ImagePlus(path);
					// System.out.println(imageURL.getPath());
			// ImagePlus imagePlus = new ImagePlus(path);
		//WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
				//imageViewAfterSueilNormal.setImage(fxImage);
			 //Image image = new Image(new File(path).toURI().toString());
			 //imageViewAfterSueilNormal.setImage(image);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML 
	private void handleFileChooseAction(ActionEvent event) {
		try {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file != null) {
					
				setPpmFilePath(file.getAbsolutePath());
				//label.setText(getPgmFilePath() + "  selected");
				ppmSegServiceNormal = new PPMSegmentationService(ppmFilePath);
				//pgmStatsServiceLUT = new PGMStatsService(pgmFilePath);
				displayChoosenPPMFile();
				
				
				label.setText(getPpmFilePath()+ "  selected");
				
				
	
				
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void bindImageFileToViewer(File imageFile ,ImageView imageView) {
        // Halt update thread if one exists
        if (viewerUpdateThread != null) {
            viewerUpdateThread.interrupt();
        }
        // Set the current image to the new image
        imageView.setImage(PpmConverter.convert(imageFile));

        // Create a new thread for this image
        viewerUpdateThread = new ViewerUpdateThread(imageView, imageFile);
        viewerUpdateThread.start();
    }
	
	private int redThresholdResolver() {
		try {
			String size = red.getText();
			return Integer.parseInt(size);
		}catch(Exception e) {
			e.printStackTrace();
			return 100;
		}	
	}
	
	private int greenThresholdResolver() {
		try {
			String size = green.getText();
			return Integer.parseInt(size);
		}catch(Exception e) {
			e.printStackTrace();
			return 80;
		}	
	}
	private int blueThresholdResolver() {
		try {
			String size = blue.getText();
			return Integer.parseInt(size);
		}catch(Exception e) {
			e.printStackTrace();
			return 100;
		}	
	}
	public void initialize() {
		
	}
}
