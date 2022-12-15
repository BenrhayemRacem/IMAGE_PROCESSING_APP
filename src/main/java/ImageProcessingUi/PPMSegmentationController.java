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
import java.awt.image.BufferedImage;


import ij.*;
import ij.plugin.PGM_Reader;


import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import org.opencv.core.Point;
import org.opencv.core.Size;



public class PPMSegmentationController {

	@FXML
	private Button chooseFileButton;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private Button kernelSizeButton;
	
	@FXML 
	private Label label;
	
	@FXML 
	private Label labelColors;
	
	@FXML 
	private Label labelOtsu;
	
	@FXML
	private ImageView imageView;
	@FXML
	private ImageView imageViewAfterThresholdNormal;
	@FXML
	private ImageView imageViewAfterThresholdAnd;
	@FXML
	private ImageView imageViewAfterThresholdOr;
	
	@FXML
	private ImageView imageViewAfterThresholdOtsu;
	
	@FXML
	private ImageView imageViewErode;
	
	@FXML
	private ImageView imageViewErode2;
	
	@FXML
	private ImageView imageViewErode3;
	
	@FXML
	private ImageView imageViewDilate;
	
	@FXML
	private ImageView imageViewDilate1;
	
	@FXML
	private ImageView imageViewDilate2;
	
	 @FXML
	 private TextField red ;
	 @FXML
	 private TextField green ;
	 @FXML
	 private TextField blue ;
	 
	 @FXML
	 private TextField kernel ;
	
	private FileChooser fileChooser = new FileChooser();
	private String ppmFilePath = null ;
	private PPMSegmentationService ppmSegServiceNormal = null;
	
	 private ViewerUpdateThread viewerUpdateThread;
	 private ViewerUpdateThread viewerUpdateThreadAnd;

	 private ViewerUpdateThread viewerUpdateThreadOr;
	 
	 private static final String[] ELEMENT_TYPE = { "Rectangle", "Cross", "Ellipse" };
	    private int elementType = Imgproc.CV_SHAPE_RECT;
	    private int kernelSize = 3;
	    

	
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
			 	otsuAlgo();
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
			//e.printStackTrace();
			return 100;
		}	
	}
	
	private int greenThresholdResolver() {
		try {
			String size = green.getText();
			return Integer.parseInt(size);
		}catch(Exception e) {
			//e.printStackTrace();
			return 80;
		}	
	}
	private int blueThresholdResolver() {
		try {
			String size = blue.getText();
			return Integer.parseInt(size);
		}catch(Exception e) {
			//e.printStackTrace();
			return 100;
		}	
	}
	
	private int kernelSizeResolver() {
		try {
			String size = kernel.getText();
			return Integer.parseInt(size);
		}catch(Exception e) {
			//e.printStackTrace();
			return 3;
		}	
	}
	
	private void otsuAlgo() {
		Mat src = Imgcodecs.imread(getPpmFilePath(), Imgcodecs.IMREAD_GRAYSCALE);
		Mat dst = new Mat(src.rows(), src.cols(), src.type());
		double threshold =Imgproc.threshold(src, dst, 0, 255, Imgproc.THRESH_OTSU);
		java.awt.Image img = HighGui.toBufferedImage(dst);
	      WritableImage writableImage= SwingFXUtils.toFXImage((BufferedImage) img, null);
	      imageViewAfterThresholdOtsu.setImage(writableImage);
	      labelOtsu.setText("otsu threshold: "+threshold);
	        Mat element = Imgproc.getStructuringElement(elementType, new Size(2 * kernelSize + 1, 2 * kernelSize + 1),
	                new Point(kernelSize, kernelSize));
	      Mat matImgDst = new Mat();
	      Imgproc.erode(src, matImgDst, element);
	      
	      java.awt.Image imgErod = HighGui.toBufferedImage(matImgDst);
	      WritableImage writableImageErod= SwingFXUtils.toFXImage((BufferedImage) imgErod, null);
	      imageViewErode.setImage(writableImageErod);
	      
	      Mat matImgDstDilate = new Mat();
	      Imgproc.dilate(src,matImgDstDilate,element);
	      
	      java.awt.Image imgDilate = HighGui.toBufferedImage(matImgDstDilate);
	      WritableImage writableImageDilate= SwingFXUtils.toFXImage((BufferedImage) imgDilate, null);
	      imageViewDilate.setImage(writableImageDilate);
	      
	      Mat element1 = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_CROSS, new Size(2 * kernelSize + 1, 2 * kernelSize + 1),
	                new Point(kernelSize, kernelSize));
	      
	      Mat matImgDstErode2 = new Mat();
	      Imgproc.erode(src, matImgDstErode2, element1);
	      java.awt.Image imgErod2 = HighGui.toBufferedImage(matImgDstErode2);
	      WritableImage writableImageErod2= SwingFXUtils.toFXImage((BufferedImage) imgErod2, null);
	      imageViewErode2.setImage(writableImageErod2);
	      
	      Mat matImgDstDilate1 = new Mat();
	      Imgproc.dilate(src,matImgDstDilate1,element1);
	      java.awt.Image imgDilate1 = HighGui.toBufferedImage(matImgDstDilate1);
	      WritableImage writableImageDilate1= SwingFXUtils.toFXImage((BufferedImage) imgDilate1, null);
	      imageViewDilate1.setImage(writableImageDilate1);
	      
	      
	      
	      Mat element2 = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_ELLIPSE, new Size(2 * kernelSize + 1, 2 * kernelSize + 1),
	                new Point(kernelSize, kernelSize));
	      Mat matImgDstErode3 = new Mat();
	      Imgproc.erode(src, matImgDstErode3, element2);
	      java.awt.Image imgErod3 = HighGui.toBufferedImage(matImgDstErode3);
	      WritableImage writableImageErod3= SwingFXUtils.toFXImage((BufferedImage) imgErod3, null);
	      imageViewErode3.setImage(writableImageErod3);
	      
	      Mat matImgDstDilate2 = new Mat();
	      Imgproc.dilate(src,matImgDstDilate2,element2);
	      java.awt.Image imgDilate2 = HighGui.toBufferedImage(matImgDstDilate2);
	      WritableImage writableImageDilate2= SwingFXUtils.toFXImage((BufferedImage) imgDilate2, null);
	      imageViewDilate2.setImage(writableImageDilate2);
	      
	    
	      
	}
	
	@FXML
	private void handleChangeKernel(ActionEvent event) {
		try {
			kernelSize = kernelSizeResolver();
			otsuAlgo();
			
		}catch(Exception e) {
			
		}
	}
	public void initialize() {
		
	}
}
