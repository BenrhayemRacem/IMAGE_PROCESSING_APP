package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class PPMModel {
	private String format = "";
	private int lx = 0;
	private int ly = 0;
	private int maxPixelValue = 0;
	private short[][] image = null;
	
	private short[] redValues=null;
	private short[]greenValues=null;
	private short[] blueValues=null;
	
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

	public short[] getRedValues() {
		return redValues;
	}

	public void setRedValues(short[] redValues) {
		this.redValues = redValues;
	}

	public short[] getGreenValues() {
		return greenValues;
	}

	public void setGreenValues(short[] greenValues) {
		this.greenValues = greenValues;
	}

	public short[] getBlueValues() {
		return blueValues;
	}

	public void setBlueValues(short[] blueValues) {
		this.blueValues = blueValues;
	}

	public void readPPMImage(String filename) throws Exception {
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
		//image = new short[lx][ly];
		Vector<Short> allValues = new Vector<Short>();

		for (int ligne = j; ligne < list.size(); ligne++) {
			String[] lineStr = list.get(ligne).split("\\s+");

			for (int k = 0; k < lineStr.length; k++) {
				//System.out.println(ligne-j);
				if(lineStr[k].equals("\\s+") || lineStr[k].length()<1) {
					continue;
				}
				String[] inputStr = lineStr[k].split("\\s+");
				allValues.add(Short.parseShort(lineStr[k]));
				//image[ligne - j][k] = Short.parseShort(lineStr[k]);

			}

		}
//		System.out.println(allValues.size());
//		for(int iLigne=0 ; iLigne<lx ; iLigne++) {
//			for(int iColonne=0 ; iColonne<ly ; iColonne++) {
//				image[iLigne][iColonne] = allValues.get(iLigne*ly+iColonne);
//			}
//		}
		
		blueValues = new short[lx*ly*3];
		redValues = new short[lx*ly*3];
		greenValues = new short[lx*ly*3];
		int blueIndex=0;
		int redIndex=0;
		int greenIndex=0;
		for(int val=0 ; val< allValues.size();val++) {
			if(val %3 ==0) {
				redValues[redIndex] = allValues.get(val);
				redIndex++ ;
			}else if(val %3 ==1) {
				greenValues[greenIndex] = allValues.get(val);
				greenIndex++ ;
			}else {
				blueValues[blueIndex] = allValues.get(val);
				blueIndex++ ;
			}
			
		}
		//System.out.println(allValues.size());
	}
	
	public void writeImage(String filename ) throws Exception {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(filename), "US-ASCII"))) {
	   writer.write(format);writer.write("\n");
	   writer.write(ly+" "+lx+"\n");
	   writer.write(maxPixelValue+"\n");
	  // System.out.println(format);
	   
	  int length = ly*3;
	   for(int i=0 ; i<lx ; i++) {
		   String line="";
		   for (int k=0 ; k<length ; k++) {
			   int currentIndex = i* length + k ;
			   line+= redValues[currentIndex]+" "+greenValues[currentIndex]+" "+blueValues[currentIndex]+" " ;
			   
		   }
		   line = line.substring(0, line.length()-1);
		   writer.write(line); writer.write("\n");
	   }
	   writer.flush();
	   writer.close();
	   System.out.println(format);
	   System.out.println("done writing");

	}
		
	}
	
	public void setOnePixelValueForRed(int i , short value) {
		this.redValues[i] = value;
	}
	public void setOnePixelValueForGreen(int i , short value) {
		this.greenValues[i] = value;
	}
	public void setOnePixelValueForBlue(int i , short value) {
		this.blueValues[i] = value;
	}
}
