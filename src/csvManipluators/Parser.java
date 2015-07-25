package csvManipluators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * This class allows you to manipulate csv file.
 */
public class Parser {
	private static Logger log = Logger.getLogger("global");
	private static String csvHeader = "time,usr,sys,idl,wai,hiq,siq,used,buff,cach,free,in,out,usedSwap,freeSwap,read,writ,recv,send,int,cntsw,recvTotPkt,sendTotPkt";
	private static String MY_FILE_EXTENSION = "_Cleared.hla";
	
	/**
	 * Return an arraylist of record with only column defined in toGet array.
	 * @param csvFilePath	file to read
	 * @param toGet	value to return 
	 * @return arraylist of record with only column defined in toGet array.
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public ArrayList<String> getDataFromCsv(String csvFilePath, ArrayList<String> toGet){
		String clearedFilePath = csvFilePath + MY_FILE_EXTENSION;
		clearCsvFile(csvFilePath, clearedFilePath);
		ArrayList<String> toReturn = new ArrayList<String>();
		CSVParser parser;
		try{
			parser = new CSVParser(new FileReader(clearedFilePath), CSVFormat.DEFAULT.withHeader());
			for (CSVRecord record : parser) {
				String subRecord = "";
				for (String column:toGet){
				    if (subRecord.length() > 0)
				    	subRecord = subRecord + "," + record.get(column);
				    else subRecord = subRecord + record.get(column);
				}
			    toReturn.add(subRecord);
			  }
			parser.close();
		}catch(Exception e){
			log.severe("Exception parsing csv File: " + e.getMessage());
			e.printStackTrace();
		}
		return toReturn;
	}

	/**
	 * This method will extract delay between 2 record
	 * in csv file in input.
	 * @param csvFilePath	Path of csv cleared file.
	 * @return	interval(in seconds) between 2 records.
	 */
	public int getRecordsIntervalFromCsv(String csvFilePath){
		CSVParser parser;
		try{
			parser = new CSVParser(new FileReader(csvFilePath + MY_FILE_EXTENSION), CSVFormat.DEFAULT.withHeader());
			List<CSVRecord> records = parser.getRecords();
			if (records.size() == 0 || records.size() == 1)
				return 1;
			String firstTime = records.get(0).get("time").split(" ")[1].split(":")[2];	//get seconds
			String secondTime = records.get(1).get("time").split(" ")[1].split(":")[2];	//get seconds
			parser.close();
			int firstTimeVal = Integer.parseInt(firstTime);
			int secondTimeVal = Integer.parseInt(secondTime);
			int toReturn = secondTimeVal - firstTimeVal;
			if (toReturn < 0)
				toReturn = (secondTimeVal + 60) - firstTimeVal;
			log.info("Finding interval between records: " + toReturn);
			return toReturn;
		}catch(Exception e){
			log.severe("Exception parsing csv File: " + e.getMessage());
			e.printStackTrace();
		}
		log.info("Finding interval between records: " + 0);
		return 0;
	}
	
	/**
	 * This method will clear csv dstat file from 
	 * extra comments.
	 * @param filePath path of file to clear
	 * @param clearedFilePath path of new file cleared
	 * @throws FileNotFoundException if file doesn't exist
	 * @throws UnsupportedEncodingException 
	 */
	private void clearCsvFile(String filePath, String clearedFilePath){
		try{
			File file = new File(filePath);
			Scanner input;
			try {
				input = new Scanner(file);
				PrintWriter writer = new PrintWriter(clearedFilePath, "UTF-8");
				boolean writingData = false;
				while(input.hasNext()) {
				    String nextLine = input.nextLine();
				    if (writingData){
				    	writer.println(nextLine);
				    }
				    else{	//we need to delete first dstat row???
					    if (nextLine.equals("\"time\",\"usr\",\"sys\",\"idl\",\"wai\",\"hiq\",\"siq\",\"used\",\"buff\",\"cach\",\"free\",\"in\",\"out\",\"used\",\"free\",\"read\",\"writ\",\"recv\",\"send\",\"int\",\"csw\",\"#recv\",\"#send\"") ||
					    		nextLine.equals("\"time\",\"usr\",\"sys\",\"idl\",\"wai\",\"hiq\",\"siq\",\"used\",\"buff\",\"cach\",\"free\",\"in\",\"out\",\"used\",\"free\",\"read\",\"writ\",\"recv\",\"send\",\"int\",\"csw\",\"#recv\",\"#send\",")){
					    	writer.println(csvHeader);
					    	writingData = true;		    	
					    }
				    }
				}
				writer.close();
				input.close();
			} catch (FileNotFoundException e) {
				log.severe(e.getMessage());
				e.printStackTrace();
				return;
			} catch (UnsupportedEncodingException e) {
				log.severe(e.getMessage());
				e.printStackTrace();
				return;
			}		
			log.info("Created cleared file: " + clearedFilePath);
		}catch(Exception e){
			log.severe("Exception parsing file: " + filePath);
		}
	}
	
	/**
	 * This method makes an array of double with each string 
	 * contained in data.
	 * @param data	list of data as String.
	 * @return	list of data as double array to use it as Generator method input.
	 */
	public double[] generateDoubleArrayFrom(ArrayList<String> data){
		double[] toReturn = new double[1];
		int position = 0;
		for(String r:data){
			//creating cpu dataset(array of double) for ChartGeneratorCpu
			toReturn[position] = Double.parseDouble(r);
			position++;
			if (position > toReturn.length - 1){
				if(!(position > data.size()-1)){
					double[] tmp = new double[position + 1];
					System.arraycopy(toReturn, 0, tmp, 0, toReturn.length);
					toReturn = tmp;
				}
			}
		}
		return toReturn;
	}
	
	/**
	 * This method makes an array of double with each string 
	 * contained in data. 
	 * @param data	list of data as String where each element could contains more data separated by ','.
	 * @param OPERATION is the operation to join each string.
	 * @return	list of data as double array to use it as Generator method input.
	 */
	public double[] generateDoubleArrayFrom(ArrayList<String> data, int OPERATION){	
		ArrayList<String> toReturn = new ArrayList<String>();
		for(String r:data){
			String[] row = r.split(",");
			double toAdd = 0;
			switch (OPERATION){
				case 1:	//SUM
					for (String s:row){
						toAdd += Double.parseDouble(s);
					}
					toReturn.add(toAdd + "");
					break;
				case 2:	//SUB
					for (String s:row){
						toAdd -= Double.parseDouble(s);
					}
					toReturn.add(toAdd + "");
					break;
				case 3:	//DIV
					for (String s:row){
						toAdd /= Double.parseDouble(s);
					}
					toReturn.add(toAdd + "");
					break;
				case 4:	//MULT
					for (String s:row){
						toAdd *= Double.parseDouble(s);
					}
					toReturn.add(toAdd + "");
					break;
			}
		}
		return generateDoubleArrayFrom(toReturn);
	}
}
