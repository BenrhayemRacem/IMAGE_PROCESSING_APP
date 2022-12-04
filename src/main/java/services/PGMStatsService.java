package services;

import java.util.Vector;

import models.PGMModel;

public class PGMStatsService {

	private double mean = 0;
	private double standardDeviation = 0;
	private short[] greyLevelHistogram = null;
	private int[] cumulativeHistogram = null;
	private double[] probabiliteCumule = null;
	private int[] n1Array = null;
	private Vector<Integer> heg = new Vector<Integer>();
	private int[] LUT_contraste = new int[256];
	private PGMModel pgmImage = new PGMModel();

	public PGMModel getPgmImage() {
		return pgmImage;
	}

	public void setPgmImage(PGMModel pgmImage) {
		this.pgmImage = pgmImage;
	}

	public double[] getProbabiliteCumule() {
		return probabiliteCumule;
	}

	public void setProbabiliteCumule(double[] probabiliteCumule) {
		this.probabiliteCumule = probabiliteCumule;
	}

	public int[] getN1Array() {
		return n1Array;
	}

	public void setN1Array(int[] n1Array) {
		this.n1Array = n1Array;
	}

	public Vector<Integer> getHeg() {
		return heg;
	}

	public void setHeg(Vector<Integer> heg) {
		this.heg = heg;
	}

	public int[] getLUT_contraste() {
		return LUT_contraste;
	}

	public void setLUT_contraste(int[] lUT_contraste) {
		LUT_contraste = lUT_contraste;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public short[] getGreyLevelHistogram() {
		return greyLevelHistogram;
	}

	public void setGreyLevelHistogram(short[] greyLevelHistogram) {
		this.greyLevelHistogram = greyLevelHistogram;
	}

	public int[] getCumulativeHistogram() {
		return cumulativeHistogram;
	}

	public void setCumulativeHistogram(int[] cumulativeHistogram) {
		this.cumulativeHistogram = cumulativeHistogram;
	}

	// Constructor
	public PGMStatsService(String filename) {
		try {
			pgmImage.readPGMImage(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void calculateMean() {
		int lx = this.pgmImage.getLx();
		int ly = this.pgmImage.getLy();
		long pixels = lx * ly;
		double sum = 0;
		for (int i = 0; i < lx; i++) {
			for (int j = 0; j < ly; j++) {
				sum += pgmImage.getImage()[i][j];
			}
		}
		this.mean = sum / pixels;

	}

	public void calculateStandardDesviation() {
		int lx = this.pgmImage.getLx();
		int ly = this.pgmImage.getLy();
		long pixels = lx * ly;
		double sum = 0;
		for (int i = 0; i < lx; i++) {
			for (int j = 0; j < ly; j++) {

				sum += Math.pow(pgmImage.getImage()[i][j] - this.mean, 2);
			}
		}
		this.standardDeviation = Math.pow(sum / pixels, 0.5);

	}

	public void calculateHistogram() {
		int maxPixelValue = this.pgmImage.getMaxPixelValue();
		int lx = this.pgmImage.getLx();
		int ly = this.pgmImage.getLy();
		this.greyLevelHistogram = new short[maxPixelValue + 1];

		for (int i = 0; i < lx; i++) {
			for (int j = 0; j < ly; j++) {

				this.greyLevelHistogram[pgmImage.getImage()[i][j]] += 1;
			}
		}

	}

	// we should calculate grey level histogram before calling this method
	public void calculateCumulativeHistogram() {
		int maxPixelValue = this.pgmImage.getMaxPixelValue();
		this.cumulativeHistogram = new int[maxPixelValue + 1];
		this.cumulativeHistogram[0] = this.greyLevelHistogram[0];
		for (int i = 1; i < this.cumulativeHistogram.length; i++) {
			this.cumulativeHistogram[i] = this.greyLevelHistogram[i] + this.cumulativeHistogram[i - 1];

		}

	}

	// we should call method calculateHistogram , calculateCumulativeHistogram
	// before
	public void calculerProbabiliteCumulee() {
		int maxPixelValue = this.pgmImage.getMaxPixelValue();
		int lx = this.pgmImage.getLx();
		int ly = this.pgmImage.getLy();

		probabiliteCumule = new double[maxPixelValue + 1];
		n1Array = new int[maxPixelValue + 1];
		for (int i = 0; i < probabiliteCumule.length; i++) {
			probabiliteCumule[i] = (double) cumulativeHistogram[i] / (double) (lx * ly);
			n1Array[i] = (int) (Math.floor(probabiliteCumule[i] * maxPixelValue));
		}
	}

	public void calculerHistogrammeEgalise() {

		int somme = greyLevelHistogram[0];
		for (int i = 1; i < n1Array.length; i++) {
			int k = n1Array[i] - n1Array[i - 1];
			while (k > 1) {
				heg.add(0);
				k--;
			}
			if (n1Array[i] == n1Array[i - 1]) {
				somme += greyLevelHistogram[i];
			} else {
				heg.add(somme);
				somme = greyLevelHistogram[i];
			}

		}

	}
	//call probabilite cumulee first
	public void newImageAfterHeg(String filename) throws Exception {
		int lx = this.pgmImage.getLx();
		int ly = this.pgmImage.getLy();
		int n = lx * ly;
		for (int i = 0; i < lx; i++) {
			for (int k = 0; k <ly; k++) {
				this.pgmImage.setOnePixelValueForImage(i, k, (short) n1Array[pgmImage.getImage()[i][k]]);
			}
			

		}
		pgmImage.writeImage(filename);
	}
	
	
	public void contraste_LUT(int x1 , int y1 , int x2 , int y2) {
		//int x_pt0 , y_pt0 = 0 ;
		//int x_pt255 , y_pt255 = 255 ;
		
		int a_prePoint1 = 0 ;
		if(x1 != 0) {
			  a_prePoint1=y1/x1 ;
		}
		int b_prePoint1 = 0 ;
		
		int a_entre1_2 =  (y2 - y1) /(x2 - x1) ;
		int b_entre1_2 = y1 - a_entre1_2 * x1 ;
		
		int a_postPoint2 = (255-y2) /(255-y1) ;
		int b_postPoint2 =  255 - 255 * a_postPoint2 ;
		
		 
		for(int i =0 ; i< LUT_contraste.length ; i++) {
			//int value = pgmImage.greyLevelHistogram[i] ;
			if(i<=x1) {
				LUT_contraste[i] = a_prePoint1 * i + b_prePoint1 ;
			} if((i>x1) && (i<=x2)) {
				LUT_contraste[i] = a_entre1_2 * i + b_entre1_2 ;
			}if(i>x2) {
				LUT_contraste[i] = a_postPoint2 * i + b_postPoint2 ;
			}
		}
		
		
	}
	
	// call calculate LUT before this
	public void newImageAfterContraste(String filename) throws Exception {
		int lx = this.pgmImage.getLx();
		int ly = this.pgmImage.getLy();
		for (int i = 0; i < lx; i++) {
			for (int k = 0; k < ly; k++) {
				this.pgmImage.setOnePixelValueForImage(i, k, (short) LUT_contraste[pgmImage.getImage()[i][k]]);
			}
			

		}
		pgmImage.writeImage(filename);
	}

}
