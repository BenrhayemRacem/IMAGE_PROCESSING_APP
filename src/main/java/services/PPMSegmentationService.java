package services;

import models.PPMModel;

public class PPMSegmentationService {
	private PPMModel ppmImage = new PPMModel();
	
	
	
	
	public PPMModel getPpmImage() {
		return ppmImage;
	}

	public void setPpmImage(PPMModel ppmImage) {
		this.ppmImage = ppmImage;
	}

	public PPMSegmentationService(String filename) {
		try {
			ppmImage.readPPMImage(filename);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
public void seuilNormal(int sRed,int sGreen , int sBlue, String filename) throws Exception {
	short[] greenValues = ppmImage.getGreenValues();
	short[] redValues = ppmImage.getRedValues();
	short[] blueValues = ppmImage.getBlueValues();
	//System.out.println(greenValues.length);
		
		for(int i=0 ; i<greenValues.length ; i++) {
			if(redValues[i]>= sRed) {
				ppmImage.setOnePixelValueForRed(i,(short) 255);
			}
			if(greenValues[i]>= sGreen) {
				ppmImage.setOnePixelValueForGreen(i,(short) 255);
			}
			if(blueValues[i]>= sBlue) {
				ppmImage.setOnePixelValueForBlue(i,(short) 255);
			}
			if(redValues[i]<sRed) {
				ppmImage.setOnePixelValueForRed(i,(short) 0);
			}
			if(blueValues[i]<sBlue) {
				ppmImage.setOnePixelValueForBlue(i,(short) 0);
			}
			if(greenValues[i]<sGreen) {
				ppmImage.setOnePixelValueForGreen(i,(short) 0);
			}
		}
		//System.out.print("aaaaa");
		
			
		
		
		ppmImage.writeImage(filename);
	}


public void seuilET(int sRed,int sGreen , int sBlue,String filename) throws Exception {
	short[] greenValues = ppmImage.getGreenValues();
	short[] redValues = ppmImage.getRedValues();
	short[] blueValues = ppmImage.getBlueValues();
	for(int i=0 ; i<greenValues.length ; i++) {
		if(redValues[i]> sRed && greenValues[i]> sGreen && blueValues[i]> sBlue) {
			//continue;
			ppmImage.setOnePixelValueForRed(i,(short) 255);
			ppmImage.setOnePixelValueForGreen(i,(short) 255);
			ppmImage.setOnePixelValueForBlue(i,(short) 255);
		}else {
			ppmImage.setOnePixelValueForGreen(i,(short) 0);
			ppmImage.setOnePixelValueForRed(i,(short) 0);
			ppmImage.setOnePixelValueForBlue(i,(short) 0);
		}
	}
	ppmImage.writeImage(filename);
}

public void seuilOR (int sRed,int sGreen , int sBlue,String filename) throws Exception {
	short[] greenValues = ppmImage.getGreenValues();
	short[] redValues = ppmImage.getRedValues();
	short[] blueValues = ppmImage.getBlueValues();
	for(int i=0 ; i<greenValues.length ; i++) {
		if(redValues[i]> sRed || greenValues[i]> sGreen || blueValues[i]> sBlue) {
			//continue;
			ppmImage.setOnePixelValueForRed(i,(short) 255);
			ppmImage.setOnePixelValueForGreen(i,(short) 255);
			ppmImage.setOnePixelValueForBlue(i,(short) 255);
		}else {
			ppmImage.setOnePixelValueForGreen(i,(short) 0);
			ppmImage.setOnePixelValueForRed(i,(short) 0);
			ppmImage.setOnePixelValueForBlue(i,(short) 0);
			
		}
	}
	ppmImage.writeImage(filename);
}
	
}