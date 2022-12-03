package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class PGMModel {
	
	private String format ="";
	private int lx=0 ;
	private int ly=0 ;
	private int maxPixelValue = 0;
	private short[][] image = null;
	
	
	
	public void readPGMImage(String filename) throws Exception {
		int i = 0;
		File file = new File(filename);
		Scanner input = new Scanner(file);
		List<String> list = new ArrayList<String>();

		while (input.hasNextLine()) {
			list.add(input.nextLine());
		}
		format = list.get(0);
		for (i = 0; i < list.size(); i++) {
			if (i != 0) {
				if (list.get(i).charAt(0) != '#') {
					break;
				}
			}
		}

		ly = Integer.parseInt(list.get(i).substring(0, list.get(i).indexOf(' ')));
		lx = Integer.parseInt(list.get(i).substring(list.get(i).indexOf(' ') + 1));
		maxPixelValue = Integer.parseInt(list.get(i + 1));

		//System.out.println(lx + "aaa" + ly + "aaaa" + maxPixelValue);

		int j = i + 2;
		image = new short[lx][ly];
		Vector<Short> allValues = new Vector<Short>();

		for (int ligne = j; ligne < list.size(); ligne++) {
			String[] lineStr = list.get(ligne).split("\\s+");

			for (int k = 0; k < lineStr.length; k++) {
				//System.out.println(ligne-j);
				allValues.add(Short.parseShort(lineStr[k]));
				//image[ligne - j][k] = Short.parseShort(lineStr[k]);

			}

		}
		System.out.println(allValues.size());
		for(int iLigne=0 ; iLigne<lx ; iLigne++) {
			for(int iColonne=0 ; iColonne<ly ; iColonne++) {
				image[iLigne][iColonne] = allValues.get(iLigne*ly+iColonne);
			}
		}
	}
	
	public void writeImage(String filename ) throws Exception {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(filename), "utf-8"))) {
	   writer.write(format);writer.write("\n");
	   writer.write(ly+" "+lx+"\n");
	   writer.write(maxPixelValue+"\n");
	   
	  
	   for(int i=0 ; i<lx ; i++) {
		   String line="";
		   for (int k=0 ; k<ly ; k++) {
			   line+= image[i][k]+" " ;
		   }
		   line = line.substring(0, line.length()-1);
		   writer.write(line); writer.write("\n");
	   }

	}
	}
	//surcharge 
	public void writeImage(String filename ,int lx ,int ly ,short[][] image ) throws Exception {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(filename), "utf-8"))) {
	   writer.write(format);writer.write("\n");
	   writer.write(ly+" "+lx+"\n");
	   writer.write(maxPixelValue+"\n");
	  // System.out.println(ly);
	  
	   for(int i=0 ; i<lx ; i++) {
		   String line="";
		   for (int k=0 ; k<ly ; k++) {
			   line+= image[i][k]+" " ;
		   }
		   line = line.substring(0, line.length()-1);
		   writer.write(line); writer.write("\n");
	   }

	}
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getLx() {
		return lx;
	}

	public void setLx(int lx) {
		this.lx = lx;
	}

	public int getLy() {
		return ly;
	}

	public void setLy(int ly) {
		this.ly = ly;
	}

	public int getMaxPixelValue() {
		return maxPixelValue;
	}

	public void setMaxPixelValue(int maxPixelValue) {
		this.maxPixelValue = maxPixelValue;
	}

	public short[][] getImage() {
		return image;
	}

	public void setImage(short[][] image) {
		this.image = image;
	}
	
	public void setOnePixelValueForImage(int i , int j , short value) {
		this.image[i][j] = value;
	}

	

}
