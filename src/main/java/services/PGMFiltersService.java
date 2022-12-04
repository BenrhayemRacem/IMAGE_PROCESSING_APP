package services;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import models.PGMModel;


public class PGMFiltersService {
	private PGMModel pgmImage = new PGMModel();
	
	
	// Constructor
		public PGMFiltersService(String filename) {
			try {
				pgmImage.readPGMImage(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void bruit(String filename) throws Exception {
			int lx = this.pgmImage.getLx();
			int ly = this.pgmImage.getLy();
			Random rand = new Random();
			for (int i = 0; i < lx; i++) {
				for (int k = 0; k < ly; k++) {
					int random_number = rand.nextInt(21);
					if(random_number == 0) {
						pgmImage.setOnePixelValueForImage(i, k, (short) 0);
					}
					if(random_number ==20) {
						pgmImage.setOnePixelValueForImage(i, k, (short) 20);
					}
					
				}

			}
			pgmImage.writeImage(filename);	
		}
		
		public void meanFilter(int taille ,String filename) {
			int boundary = (taille -1) /2 ;
			int lx = this.pgmImage.getLx();
			int ly = this.pgmImage.getLy();
			for(int i=0 ; i< lx; i++) {
				for(int j=0 ; j<ly ; j++) {
					int rowStart = i -boundary ; 
					int rowEnd = i+ boundary;
					int columnStart = j-boundary;
					int columnEnd = j+boundary ;
					if((rowStart <0)||(rowEnd >= lx) || (columnStart<0) 
							||(columnEnd>=ly)) {
						continue;
					}
					
					Vector<Short> values = new Vector<Short>();
					for (int ii=rowStart ; ii<=rowEnd ; ii++) {
						for(int jj=columnStart ; jj <=columnEnd ; jj++) {
							values.add(pgmImage.getImage()[ii][jj]);
						}
					}
					short meanValue =this.calculerMean(values);
					pgmImage.setOnePixelValueForImage(i, j, meanValue);
				}
			}
			
			try {
				pgmImage.writeImage(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public short calculerMean(Vector<Short> values) {
			short sum =0 ;
			for(int i=0 ; i<values.size() ; i++) {
				sum+=values.get(i);
			}
			return (short) (sum/values.size());
		}
		
		public void medianFilter(int taille ,String filename) {
			int lx = this.pgmImage.getLx();
			int ly = this.pgmImage.getLy();
			int boundary = (taille -1) /2 ;
			for(int i=0 ; i< lx; i++) {
				for(int j=0 ; j<ly ; j++) {
					int rowStart = i -boundary ; 
					int rowEnd = i+ boundary;
					int columnStart = j-boundary;
					int columnEnd = j+boundary ;
					if((rowStart <0)||(rowEnd >= lx) || (columnStart<0) 
							||(columnEnd>=ly)) {
						continue;
					}
					;
					Vector<Short> values = new Vector<Short>();
					for (int ii=rowStart ; ii<=rowEnd ; ii++) {
						for(int jj=columnStart ; jj <=columnEnd ; jj++) {
							values.add(pgmImage.getImage()[ii][jj]);
						}
					}
					short medianValue =this.calculerMedian(values);
					pgmImage.setOnePixelValueForImage(i, j, medianValue);
				}
			}
			
			try {
				pgmImage.writeImage(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public short calculerMedian(Vector<Short> values) {
			Collections.sort(values);
			return values.get(values.size()/2);
		}
		
		public double SNR(String filenameOriginal ,String filenameFiltered) {
			
			try {
				
				PGMStatsService pgmOriginal = new PGMStatsService(filenameOriginal) ;
				PGMStatsService pgmFiltered = new PGMStatsService(filenameFiltered);
				double numerator =0 ;
				double denominator =0 ;
				 pgmOriginal.calculateMean();
				 double mean  = pgmOriginal.getMean() ;
				 int lx = pgmOriginal.getPgmImage().getLx();
				 int ly = pgmOriginal.getPgmImage().getLy();
				 
				 for(int i =0 ; i<lx ; i++) {
					 for (int j=0 ; j< ly ; j++) {
						numerator += Math.pow( pgmOriginal.getPgmImage().getImage()[i][j] - mean , 2 );
						denominator+= Math.pow(pgmFiltered.getPgmImage().getImage()[i][j] - pgmOriginal.getPgmImage().getImage()[i][j],2);
					}
				}
				
				System.out.println(Math.sqrt(numerator/denominator));
				return Math.sqrt(numerator/denominator);
			}catch(Exception e) {
				e.printStackTrace();
				return -1;
			}
			
		}
		
		public void rehausserContourFilter(int taille ,String filename) {
		
			int boundary = (taille -1) /2 ;
			int lx = this.pgmImage.getLx();
			int ly = this.pgmImage.getLy();
			for(int i=0 ; i< lx; i++) {
				for(int j=0 ; j<ly ; j++) {
					int rowStart = i -boundary ; 
					int rowEnd = i+ boundary;
					int columnStart = j-boundary;
					int columnEnd = j+boundary ;
					if((rowStart <0)||(rowEnd >= lx) || (columnStart<0) 
							||(columnEnd>=ly)) {
						continue;
					}
					
					Vector<Short> values = new Vector<Short>();
					for (int ii=rowStart ; ii<=rowEnd ; ii++) {
						for(int jj=columnStart ; jj <=columnEnd ; jj++) {
							values.add(pgmImage.getImage()[ii][jj]);
						}
					}
					
					short medianValue =this.calculerMedian(values);
					
						pgmImage.setOnePixelValueForImage(i, j, (short) (pgmImage.getImage()[i][j]- medianValue));
					
				}
			}
			try {
				pgmImage.writeImage(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
