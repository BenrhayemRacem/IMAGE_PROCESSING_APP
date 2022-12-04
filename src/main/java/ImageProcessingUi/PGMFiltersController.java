package ImageProcessingUi;


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

import java.io.File;
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
	private ImageView imageView;
	
	
	
	
	private FileChooser fileChooser = new FileChooser();
	private String pgmFilePath = null ;
	
	
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
				displayChoosenPGMFile();
				
				
		}
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
