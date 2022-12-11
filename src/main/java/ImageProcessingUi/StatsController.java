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
import javafx.scene.control.TextField;
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
    
    @FXML
    private Label meanLabel;
    
    @FXML 
    private Label standardDeviationLabel;
    
    @FXML
    private ImageView imageViewAfterContrastLUT;
    
    @FXML
    private LineChart<String, Number> lineChartLUT ;
    
    @FXML 
	private NumberAxis xAxisLUT ;
	
    @FXML 
    private NumberAxis yAxisLUT ;
    
    @FXML
    private TextField x1 ;
    
    @FXML
    private TextField y1 ;
    
    @FXML
    private TextField x2 ;
    
    @FXML
    private TextField y2 ;
    
    @FXML
	private Button chooseLinearParameters;
    
	

	private FileChooser fileChooser = new FileChooser();
	
	private String pgmFilePath = null ;
	private PGMStatsService pgmStatsService = null;
	private PGMStatsService pgmStatsServiceLUT = null ;
	
	
	
	
	
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
	
	public void calculateStats() {
		//PGMStatsService pgmStatsService = new PGMStatsService(this.getPgmFilePath());
		pgmStatsService.calculateMean();
		pgmStatsService.calculateStandardDesviation();
		
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
	
	private void drawImageAfterContrasteLUT(int x1 , int y1 , int x2 , int y2 ,PGMStatsService pgmFile) {
		try {
			URL imageURL = getClass().getResource("afterContrasteLUT.pgm");
			 String path = imageURL.getPath() ;
			 
			 //pgmStatsServiceLUT.contraste_LUT(x1, y1, x2, y2);
			 //pgmStatsServiceLUT.newImageAfterContraste(path);
			 pgmFile.contraste_LUT(x1, y1, x2, y2);
			 pgmFile.newImageAfterContraste(path);
			 ImagePlus imagePlus = new ImagePlus(path);
				WritableImage fxImage = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
				imageViewAfterContrastLUT.setImage(fxImage);
				lineChartLUT.getData().clear();
				XYChart.Series series1 = new XYChart.Series();
				series1.setName("original Line");
				series1.getData().add(new XYChart.Data( 0,0));
				series1.getData().add(new XYChart.Data( 255,255));
				XYChart.Series series2 = new XYChart.Series();
				series2.setName("Linear transformation");
				
				series2.getData().add(new XYChart.Data(0,0));
				series2.getData().add(new XYChart.Data( 255,255));
				series2.getData().add(new XYChart.Data(  x1,y1));
				series2.getData().add(new XYChart.Data(  x2,y2));
				lineChartLUT.getData().addAll(series1 ,series2);
				//System.out.println(String.valueOf(x1).length());
				
				
			
		}catch(Exception e) {
			
		}
	}
	
	
	@FXML 
	private void handleFileChooseAction(ActionEvent event) {
		try {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file != null) {
				
				setPgmFilePath(file.getAbsolutePath());
				//label.setText(getPgmFilePath() + "  selected");
				pgmStatsService = new PGMStatsService(pgmFilePath);
				pgmStatsServiceLUT = new PGMStatsService(pgmFilePath);
				displayChoosenPGMFile();
				lineChart.getData().clear();
				
				label.setText(getPgmFilePath()+ "  selected");
				
				calculateStats();
				meanLabel.setText("Mean: " + pgmStatsService.getMean());
				standardDeviationLabel.setText("Standard deviation: " + pgmStatsService.getStandardDeviation());
				drawHistograms();
				drawImageAfterHeg();
				drawImageAfterContrasteLUT(0, 0, 255, 255,pgmStatsServiceLUT);
				
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML 
	private void handleChangeLinearParameters(ActionEvent event) {
		try {
			//pgmStatsService = new PGMStatsService(getPgmFilePath());
			PGMStatsService pgmFile = new PGMStatsService(pgmFilePath);
			int x1 = Integer.parseInt(this.x1.getText());
			int x2 = Integer.parseInt(this.x2.getText());
			int y1 = Integer.parseInt(this.y1.getText());
			int y2 = Integer.parseInt(this.y2.getText());
			
			drawImageAfterContrasteLUT(x1,y1,x2,y2 ,pgmFile);
			
			
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public void initialize() {
		lineChart.setAnimated(false);
		lineChartLUT.setAnimated(false);

		
	}
	
	
}
