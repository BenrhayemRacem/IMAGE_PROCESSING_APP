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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;

import ij.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.fxml.FXMLLoader;

public class StatsController {

	@FXML
	private Button chooseFileButton;
	
	@FXML 
	private Label label;
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private ImageView imageViewAfterHeg;
	
	@FXML 
	private CategoryAxis xAxis ;
	
    @FXML 
    private NumberAxis yAxis ;
    
    @FXML 
    private LineChart<String, Number> lineChart ;
    
	

	private FileChooser fileChooser = new FileChooser();
	
	private String pgmFilePath = null ;
	private PGMStatsService pgmStatsService = null;
	
	
	
	
	
	public void setPgmFilePath(String s) {
		this.pgmFilePath = s;
	}
	public String getPgmFilePath() {
		return this.pgmFilePath;
	}
	
	public void displayChoosenPGMFile() {
		String path = this.getPgmFilePath();
		if(path!=null) {
			ImagePlus imagePlus = new ImagePlus(path);
			WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
			imageView.setImage(fxImage);
		}
		 
		  
	}
	
	public double calculateStats() {
		//PGMStatsService pgmStatsService = new PGMStatsService(this.getPgmFilePath());
		pgmStatsService.calculateMean();
		return pgmStatsService.getMean();
	}
	
	public void drawHistograms() {
		//PGMStatsService pgmStatsService = new PGMStatsService(this.getPgmFilePath());
		pgmStatsService.calculateHistogram();
		pgmStatsService.calculateCumulativeHistogram();
		pgmStatsService.calculerProbabiliteCumulee();
		pgmStatsService.calculerHistogrammeEgalise();
		this.xAxis.setLabel("pixel values");
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("grey level histogram");
		for (int i=0 ; i< pgmStatsService.getGreyLevelHistogram().length ; i++) {
			series1.getData().add(new XYChart.Data( String.valueOf(i),pgmStatsService.getGreyLevelHistogram()[i]));
		}
		
		
		
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("histogramme egalise");
		for (int i=0 ; i< pgmStatsService.getHeg().size() ; i++) {
			series2.getData().add(new XYChart.Data( String.valueOf(i),pgmStatsService.getHeg().get(i)));
		}
		
		//this.xAxis.setAutoRanging(true);
		
		lineChart.getData().addAll(series1,series2);
		
		
	}
	
	private void drawImageAfterHeg() {
		 URL imageURL = getClass().getResource("afterHeg.pgm");
		 String path = imageURL.getPath() ;
		 pgmStatsService.calculerProbabiliteCumulee();
		 try {
			 pgmStatsService.newImageAfterHeg(path);
			 ImagePlus imagePlus = new ImagePlus(path);
			WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
			imageViewAfterHeg.setImage(fxImage);
			 
		 }catch(Exception e) {
			 
		 }
		//System.out.println(imageURL.getPath());
	}
	
	
	@FXML 
	private void handleFileChooseAction(ActionEvent event) {
		try {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file != null) {
				
				setPgmFilePath(file.getAbsolutePath());
				//label.setText(getPgmFilePath() + "  selected");
				pgmStatsService = new PGMStatsService(pgmFilePath);
				displayChoosenPGMFile();
				lineChart.getData().clear();
				double mean = calculateStats();
				label.setText(mean+ "  selected");
				drawHistograms();
				drawImageAfterHeg();
				
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public void initialize() {
		lineChart.setAnimated(false);
		
	}
	
	
}
