package ImageProcessingUi;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.PGMFiltersService;
import services.PGMStatsService;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.net.URL;

import ij.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PGMFiltersController {
	
	@FXML
	private Button chooseFileButton;
	
	@FXML 
	private Label label;
	
	@FXML 
	private Label meanSNRLabel;
	
	@FXML 
	private Label medianSNRLabel;
	
	@FXML
	private Label filterSizeLabel;
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private Button addNoiseButton;
	
	@FXML
	private ImageView imageViewAfterNoise;
	
	@FXML
	private ImageView imageViewRehausserContour;
	
	@FXML
	private TextField filterSizeText ;
	
	@FXML
	private ImageView imageViewAfterMeanFilter;
	
	@FXML
	private ImageView imageViewAfterMedianFilter;
	
	@FXML
	private Button applyMeanAndMedianFilter;
	
	
	private FileChooser fileChooser = new FileChooser();
	private String pgmFilePath = null ;
	private boolean noiseAdded =false;
	
	public void setPgmFilePath(String s) {
		this.pgmFilePath = s;
	}
	public String getPgmFilePath() {
		return this.pgmFilePath;
	}
	
	@FXML 
	private void handleFileChooseAction(ActionEvent event) {
		try {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file != null) {
				
				setPgmFilePath(file.getAbsolutePath());
			
				label.setText(getPgmFilePath()+ "  selected");
				
				imageView.setImage(null);
				imageViewAfterMeanFilter.setImage(null);
				imageViewAfterMedianFilter.setImage(null);
				imageViewAfterNoise.setImage(null);
				
				displayChoosenPGMFile();
				this.noiseAdded = false;
				filterSizeText.clear();
				filterSizeLabel.setText("current Filter size is 3 * 3 ");
				medianSNRLabel.setText("Signal Noise Ratio: N/A");
				meanSNRLabel.setText("Signal Noise Ratio: N/A");
				
				
				
				
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleAddNoiseAction(ActionEvent event) {
		try {
			URL imageURL = getClass().getResource("afterAddingNoise.pgm");
			 String path = imageURL.getPath() ;
			 PGMFiltersService pgmFiltersService = new PGMFiltersService(getPgmFilePath());
			 pgmFiltersService.bruit(path);
			 ImagePlus imagePlus = new ImagePlus(path);
			 WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
			imageViewAfterNoise.setImage(fxImage);
				this.noiseAdded=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private int filterSizeResolver() {
		try {
			String size = filterSizeText.getText();
			return Integer.parseInt(size);
		}catch(Exception e) {
			e.printStackTrace();
			return 3;
		}
		
		
	}
	@FXML
	private void handleMeanAndMedianFilter(ActionEvent event) {
		try {
			int size = filterSizeResolver();
			
			String imagePath = getPgmFilePath() ;
			if(this.noiseAdded) {
				URL imageURL = getClass().getResource("afterAddingNoise.pgm");
				imagePath=imageURL.getPath() ;
			}
			URL imageURLMean = getClass().getResource("afterMeanFilter.pgm");
			String meanImage = imageURLMean.getPath();
			
			URL imageURLMedian = getClass().getResource("afterMedianFilter.pgm");
			String medianImage = imageURLMedian.getPath();
			
			
			PGMFiltersService pgmFiltersServiceMean = new PGMFiltersService(imagePath);
			PGMFiltersService pgmFiltersServiceMedian = new PGMFiltersService(imagePath);
			
			pgmFiltersServiceMean.meanFilter(size,meanImage);
			ImagePlus imagePlus = new ImagePlus(meanImage);
			WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
			imageViewAfterMeanFilter.setImage(fxImage);
			
			pgmFiltersServiceMedian.medianFilter(size, medianImage);
			ImagePlus imagePlus1 = new ImagePlus(medianImage);
			WritableImage fxImage1 = SwingFXUtils.toFXImage(imagePlus1.getBufferedImage(), null);
			imageViewAfterMedianFilter.setImage(fxImage1);
			
			filterSizeLabel.setText("current Filter size is " +size+" * "+size);
			
			double meanSNR=pgmFiltersServiceMean.SNR(imagePath, meanImage);
			double medianSNR=pgmFiltersServiceMedian.SNR(imagePath, medianImage);
			
			if(meanSNR != -1) {
				meanSNRLabel.setText("Signal Noise Ratio: "+ meanSNR);
			}
			if(medianSNR != -1) {
				medianSNRLabel.setText("Signal Noise Ratio: "+ medianSNR);
			}
			
			URL imageURLContour = getClass().getResource("rehausserContour.pgm");
			 String pathContour = imageURLContour.getPath() ;
			 PGMFiltersService pgmFiltersServiceContour = new PGMFiltersService(getPgmFilePath());
			 pgmFiltersServiceContour.rehausserContourFilter(filterSizeResolver(), pathContour);
				ImagePlus imagePlus2 = new ImagePlus(pathContour);
				 WritableImage fxImage2 = SwingFXUtils.toFXImage(imagePlus2.getBufferedImage(), null);
				imageViewRehausserContour.setImage(fxImage2);
			 
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void displayChoosenPGMFile() {
		String path = this.getPgmFilePath();
		if(path!=null) {
			ImagePlus imagePlus = new ImagePlus(path);
			WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
			imageView.setImage(fxImage);
		}  
	}
	
	
	
	
	public void initialize() {
		
	}
}
