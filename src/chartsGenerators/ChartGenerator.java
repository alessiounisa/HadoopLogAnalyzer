package chartsGenerators;

import static com.googlecode.charts4j.Color.SKYBLUE;

import java.util.ArrayList;
import java.util.logging.Logger;





import chartsManager.Chart;
import chartsManager.Chart_sCollector;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.Plots;

import csvManipluators.Parser;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * This class generates chart.
 */
public class ChartGenerator implements Generator{
	private static final int IF_ZERO = 50;
	private static Logger log = Logger.getLogger("global");
	private String url;
	public static int SUM = 1, SUB = 2, DIV = 3, MULT = 4, MINUTE = 5, SECOND = 6, HOUR = 7;
	
	public String getUrl() {
		return url;
	}

	/**
	 * This method generate the usage's chart.
	 * @param data1 Contains value.
	 * @param interval Interval between each x value(in SECOND)
	 * @param scaleIn could be minute, second or hour and indicates interval to show on x
	 * @param isPercentage if it's true the y value will be scaled in %
	 * @param title title of chart
	 * @param dataTitle name of line
	 * @param yLabel label of y axis
	 * @param isDashedLine 
	 * @param isGrayScale to choice greyscale mode
	 * @param isDashedLine to choice dashed line mode
	 */
	private void generateChart(double[] data,int interval, int scaleIn, boolean isPercentage, String title, String dataTitle, String yLabel, boolean isGreyScale, boolean isDashedLine){
		//plotting data
		Line line;
        AxisLabels yAxis;
		if (isPercentage){
			if (isGreyScale){
				line =  Plots.newLine(DataUtil.scaleWithinRange(0,100,data), Color.newColor("000000"), dataTitle);
			 	yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,100);
			}
			else{
				line =  Plots.newLine(DataUtil.scaleWithinRange(0,100,data), Color.newColor("CA3D05"), dataTitle);
			 	yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,100);
			}
		}
	    else{
	    	if (isGreyScale){
	    		line =  Plots.newLine(DataUtil.scaleWithinRange(0,getMax(data)+(getMax(data)/10),data), Color.newColor("000000"), dataTitle);
				yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,getMax(data));
	    	}
	    	else{
				line =  Plots.newLine(DataUtil.scaleWithinRange(0,getMax(data)+(getMax(data)/10),data), Color.newColor("CA3D05"), dataTitle);
				yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,getMax(data));
	    	}
	    }
		line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));		  
		if(isDashedLine){
		    line.setLineStyle(LineStyle.MEDIUM_DOTTED_LINE);
		}
        // Defining chart.
        LineChart chart = GCharts.newLineChart(line);
        chart.setSize(600, 450);
        chart.setTitle(title, Color.newColor("000000"), 14);
        chart.setGrid(25, 25, 3, 2);

        // Defining axis info and styles
        AxisLabels xAxis, xAxis2;
        if(scaleIn == MINUTE){
        	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data.length/(60/interval));	//to get minute of computation
            xAxis2 = AxisLabelsFactory.newAxisLabels("Minutes", 50.0);
        }
        else if(scaleIn == SECOND){
        	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data.length/(1.0/interval));	//to get second of computation
            xAxis2 = AxisLabelsFactory.newAxisLabels("Seconds", 50.0);
        }
        else if(scaleIn == HOUR){
        	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data.length/(3600/interval));	//to get hour of computation
            xAxis2 = AxisLabelsFactory.newAxisLabels("Hours", 50.0);
        }
        else{
        	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data.length/(1.0/interval));	//to get second of computation
            xAxis2 = AxisLabelsFactory.newAxisLabels("Seconds", 50.0);
            log.severe("Scale in parameter: " + scaleIn + " not valid; possible use: " + MINUTE + " for minute, "
        			+ SECOND + " for seconds and " + HOUR + " for hour. Used seconds.");
        }
        xAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.newColor("000000"), 14, AxisTextAlignment.CENTER));
        AxisLabels yAxis2 = AxisLabelsFactory.newAxisLabels(yLabel, 50.0);
        yAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.newColor("000000"), 14, AxisTextAlignment.CENTER));
        
        // Adding axis info to chart.
        chart.addXAxisLabels(xAxis);
        chart.addXAxisLabels(xAxis2);
        chart.addYAxisLabels(yAxis);
        chart.addYAxisLabels(yAxis2);

        // Defining background and chart fills.
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("e8e8e8")));
        chart.setAreaFill(Fills.newSolidFill(Color.newColor("ffffff")));
        this.url = chart.toURLString();    
        log.info("Url for Chart generated...");
	}
	
	/**
	 * This method generate the chart.
	 * @param data1 first dataset
	 * @param interval Interval between each x value(in SECOND)
	 * @param scaleIn could be minute, second or hour and indicates interval to show on x
	 * @param isPercentage if it's true the y value will be scaled in %
	 * @param data2 second dataset
	 * @param title title of chart
	 * @param data1Title name of first line
	 * @param data2Title name of second line
	 * @param yLabel label of y axis
	 * @param isGrayScale to choice greyscale mode
	 * @param isDashedLine to choice dashed line mode
	 */
	private void generateChart(double[] data1, int interval, int scaleIn, boolean isPercentage, double[] data2, String title, String data1Title, String data2Title, String yLabel, boolean isGrayScale, boolean isDashedLine){
		if (data1.length != data2.length){
			log.warning("data1 and data2 has not same size! Check for time consistence!");
		}
		//plotting data
		Line line, line2;
        AxisLabels yAxis;
		if (isPercentage){
			if (isGrayScale){
				line =  Plots.newLine(DataUtil.scaleWithinRange(0,100,data1), Color.newColor("000000"), data1Title);
				line2 =  Plots.newLine(DataUtil.scaleWithinRange(0,100,data2), Color.newColor("bbbbbb"), data2Title);
				yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,100);
			}
			else{
				line =  Plots.newLine(DataUtil.scaleWithinRange(0,100,data1), Color.newColor("CA3D05"), data1Title);
				line2 =  Plots.newLine(DataUtil.scaleWithinRange(0,100,data2), SKYBLUE, data2Title);
				yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,100);
			}
		}
	    else{
	    	if (isGrayScale){
	    		line =  Plots.newLine(DataUtil.scaleWithinRange(0,getMax(data1,data2)+getMax(data1,data2)/10,data1), Color.newColor("000000"), data1Title);
		    	line2 = Plots.newLine(DataUtil.scaleWithinRange(0,getMax(data1,data2)+getMax(data1,data2)/10,data2), Color.newColor("bbbbbb"), data2Title);
		    	yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,getMax(data1,data2));
	    	}
	    	else{
	    		line =  Plots.newLine(DataUtil.scaleWithinRange(0,getMax(data1,data2)+getMax(data1,data2)/10,data1), Color.newColor("CA3D05"), data1Title);
		    	line2 = Plots.newLine(DataUtil.scaleWithinRange(0,getMax(data1,data2)+getMax(data1,data2)/10,data2), SKYBLUE, data2Title);
		    	yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0,getMax(data1,data2));
	    	}
	    }  
	    line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line2.setLineStyle(LineStyle.newLineStyle(3, 1, 0));        
  		if(isDashedLine){
  		    line.setLineStyle(LineStyle.MEDIUM_DOTTED_LINE);
  	        line2.setLineStyle(LineStyle.MEDIUM_DOTTED_LINE);
  		}
        // Defining chart.
        LineChart chart = GCharts.newLineChart(line,line2);
        chart.setSize(600, 450);
        chart.setTitle(title, Color.newColor("000000"), 14);
        chart.setGrid(25, 25, 3, 2);

        // Defining axis info and styles
        AxisLabels xAxis, xAxis2;
        if (data1.length > data2.length){
             if(scaleIn == MINUTE){
             	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data1.length/(60/interval));	//to get minute of computation
                xAxis2 = AxisLabelsFactory.newAxisLabels("Minutes", 50.0);
             }
             else if(scaleIn == SECOND){
             	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data1.length/(1.0/interval));	//to get second of computation
                xAxis2 = AxisLabelsFactory.newAxisLabels("Seconds", 50.0);
             }
             else if(scaleIn == HOUR){
             	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data1.length/(3600/interval));	//to get hour of computation
                xAxis2 = AxisLabelsFactory.newAxisLabels("Hours", 50.0);
             }
             else{
         	  	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data1.length/(1/interval));	//to get second of computation
             	xAxis2 = AxisLabelsFactory.newAxisLabels("Seconds", 50.0);
         		log.severe("Scale in parameter: " + scaleIn + " not valid; possible use: " + MINUTE + " for minute, "
         				+ SECOND + " for seconds and " + HOUR + " for hour. Used Seconds.");          
             }
        }
        else{
        	if(scaleIn == MINUTE){
             	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data2.length/(60/interval));	//to get minute of computation
                xAxis2 = AxisLabelsFactory.newAxisLabels("Minutes", 50.0);
             }
             else if(scaleIn == SECOND){
             	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data2.length/(1.0/interval));	//to get second of computation
                xAxis2 = AxisLabelsFactory.newAxisLabels("Seconds", 50.0);
             }
             else if(scaleIn == HOUR){
             	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data2.length/(3600/interval));	//to get hour of computation
                xAxis2 = AxisLabelsFactory.newAxisLabels("Hours", 50.0);
             }
             else{
        	  	xAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, data2.length/(1.0/interval));	//to get second of computation
                xAxis2 = AxisLabelsFactory.newAxisLabels("Seconds", 50.0);
             	log.severe("Scale in parameter: " + scaleIn + " not valid; possible use: " + MINUTE + " for minute, "
             			+ SECOND + " for seconds and " + HOUR + " for hour. Used Seconds."); 
             }
        }
        xAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.newColor("000000"), 14, AxisTextAlignment.CENTER));
        AxisLabels yAxis2 = AxisLabelsFactory.newAxisLabels(yLabel, 50.0);
        yAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.newColor("000000"), 14, AxisTextAlignment.CENTER));
        
        // Adding axis info to chart.   
        chart.addXAxisLabels(xAxis);
        chart.addXAxisLabels(xAxis2);
        chart.addYAxisLabels(yAxis);
        chart.addYAxisLabels(yAxis2);

        // Defining background and chart fills.
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("e8e8e8")));
        chart.setAreaFill(Fills.newSolidFill(Color.newColor("ffffff")));
        this.url = chart.toURLString();    
        log.info("Url for Chart generated...");
	}
	
	/**
	 * Generates all chart(data1 and all data2) in one chart.
	 * To generate complex charts the library 'charts4j'
	 * has problem becouse max request that googleApi could
	 * support is 2048 URL length. Then for multilines chart
	 * This class uses JFreeChart(http://www.jfree.org/).
	 * @param data1
	 * @param interval
	 * @param scaleIn
	 * @param isPercentage
	 * @param data2
	 * @param title
	 * @param data1Title
	 * @param yLabel
	 * @param isGrayScale
	 * @param isDashedLine
	 * @param fileName 
	 */
	private void generateChart(double[] data1, int interval, int scaleIn, boolean isPercentage, ArrayList<double[]> data2, String title, String data1Title, String yLabel, boolean isGrayScale, boolean isDashedLine,String output, String fileName, ArrayList<String> dstatFileList){
		JFreeChartGenerator myJFreeChart = new JFreeChartGenerator();
		this.url = myJFreeChart.getChart(data1, interval, scaleIn, isPercentage, data2, title, data1Title, yLabel, isGrayScale, isDashedLine, output, fileName, dstatFileList);
	}
	
	/**
	 * This method return max lenght between data1 and
	 * all list in data2.
	 * @param data1
	 * @param data2
	 * @return
	 */
	private int getMax(double[] data1, ArrayList<double[]> data2) {
		int lengthMax = data1.length;
		for(double[] data: data2){
			if(data.length > lengthMax)
				lengthMax = data1.length;
		}
		return lengthMax;
	}

	/**
	 * This method create chart from dstat Csv input.
	 * @param dstatFilePath
	 * @param columnDesired
	 * @param oneForLine if it's true the columns desired will be plot in different lines;
	 * 		else they will be sum.
	 * @param title
	 * @param lineName
	 * @param isPercentage if it's true the y value will be scaled in %
	 * @param yLabel label of y axis
	 * @param scaleIn could be minute, second or hour and indicates interval to show on x
	 * @param isGrayScale to choice greyscale mode
	 * @param isDashedLine to choice dashed line mode
	 * @return URL of generated chart
	 */
	public String getChart(String dstatFilePath, ArrayList<String> columnDesired, String title, String lineName, boolean isPercentage, String yLabel, int scaleIn, boolean isGreyScale, boolean isDashedLine){
		Parser p = new Parser();
		ArrayList<String> result = new ArrayList<String>();
		result = p.getDataFromCsv(dstatFilePath, columnDesired);	
		double[] dataGenerated = p.generateDoubleArrayFrom(result, ChartGenerator.SUM);
		generateChart(dataGenerated, p.getRecordsIntervalFromCsv(dstatFilePath), scaleIn, isPercentage, title, lineName, yLabel, isGreyScale, isGreyScale);
		Chart_sCollector.addChart(new Chart(title, lineName, dataGenerated, yLabel, getUrl()));
		return getUrl();
	}
	
	/**
	 * Create chart for dstatFilePath and add one line for 
	 * each columnDesired.
	 * @param dstatFilePath
	 * @param columnDesired
	 * @param oneForLine
	 * @param title
	 * @param lineName
	 * @param lineName2
	 * @param isPercentage
	 * @param yLabel
	 * @param scaleIn
	 * @param isGreyScale
	 * @param isDashedLine
	 * @return
	 */
	public String getChart(String dstatFilePath, ArrayList<String> columnDesired, boolean oneForLine, String title, String lineName, String lineName2, boolean isPercentage, double toDivide, String yLabel, int scaleIn, boolean isGreyScale, boolean isDashedLine){
		Parser p = new Parser();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> result2 = new ArrayList<String>();
		//*****************************//
		//to use method already present I define 2 arraylist for each line
		//one for first columnDesired and another for the other.
		ArrayList<String> columnDesiredFirst = new ArrayList<String>();
		columnDesiredFirst.add(columnDesired.get(0));
		ArrayList<String> columnDesiredSecond = new ArrayList<String>();
		columnDesiredSecond.add(columnDesired.get(1));
		//****************************//
		result = p.getDataFromCsv(dstatFilePath, columnDesiredFirst);	
		result2 = p.getDataFromCsv(dstatFilePath, columnDesiredSecond);
		double[] usageFirstCol = p.generateDoubleArrayFrom(result, ChartGenerator.SUM);
		usageFirstCol = divideAllFor(usageFirstCol, toDivide);
		double[] usageSecondCol = p.generateDoubleArrayFrom(result2, ChartGenerator.SUM);
		usageSecondCol = divideAllFor(usageSecondCol, toDivide);
		generateChart(usageFirstCol, p.getRecordsIntervalFromCsv(dstatFilePath), scaleIn, isPercentage, usageSecondCol, title, lineName, lineName2, yLabel, isGreyScale, isDashedLine);
		return getUrl();
	}

	/**
	 * This method generate chart from 2 csv input file.
	 * @param dstatFilePath1
	 * @param dstatFilePath2
	 * @param columnDesired
	 * @param title
	 * @param line1Name
	 * @param line2Name
	 * @param isPercentage if it's true the y value will be scaled in %
	 * @param yLabel label of y axis
	 * @param scaleIn could be minute, second or hour and indicates interval to show on x
	 * @param isGrayScale to choice greyscale mode
	 * @param isDashedLine to choice dashed line mode
	 * @return URL of generated chart
	 */
	public String getChart(String dstatFilePath1, String dstatFilePath2, ArrayList<String> columnDesired, String title, String line1Name, String line2Name, boolean isPercentage, double toDivide, String yLabel, int scaleIn, boolean isGreyScale, boolean isDashedLine){
		Parser p = new Parser();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> result2 = new ArrayList<String>();
		result = p.getDataFromCsv(dstatFilePath1, columnDesired);	
		result2 = p.getDataFromCsv(dstatFilePath2, columnDesired);
		double[] cpuUsageValueMaster = p.generateDoubleArrayFrom(result, ChartGenerator.SUM);
		cpuUsageValueMaster = divideAllFor(cpuUsageValueMaster, toDivide);
		double[] cpuUsageValueSlave = p.generateDoubleArrayFrom(result2, ChartGenerator.SUM);
		cpuUsageValueSlave = divideAllFor(cpuUsageValueSlave, toDivide);
		generateChart(cpuUsageValueMaster, p.getRecordsIntervalFromCsv(dstatFilePath1), scaleIn, isPercentage, cpuUsageValueSlave, title, line1Name, line2Name, yLabel, isGreyScale, isDashedLine);
		Chart_sCollector.addChart(new Chart(title, dstatFilePath1, cpuUsageValueMaster, yLabel, getUrl()));
		Chart_sCollector.addChart(new Chart(title, dstatFilePath2, cpuUsageValueSlave, yLabel, getUrl()));
		return getUrl();
	}
	
	/**
	 * This method compare first dstatFile with the average of dstatFileList.
	 * all lines.
	 * @param dstatFilePath1
	 * @param dstatFileList
	 * @param columnDesired
	 * @param title
	 * @param line1Name
	 * @param line2Name
	 * @param isPercentage if it's true the y value will be scaled in %
	 * @param yLabel label of y axis
	 * @param scaleIn could be minute, second or hour and indicates interval to show on x
	 * @param isGrayScale to choice grey scale mode
	 * @param isDashedLine to choice dashed line mode
	 * @return URL of generated chart
	 */
	public String getChart(String dstatFilePath1, ArrayList<String> dstatFileList, ArrayList<String> columnDesired, String title, String line1Name, String line2Name, boolean isPercentage, double toDivide, String yLabel, int scaleIn, boolean isGreyScale, boolean isDashedLine){
		Parser p = new Parser();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> result2 = new ArrayList<String>();
		result = p.getDataFromCsv(dstatFilePath1, columnDesired);
		double[] cpuUsageValueMaster = p.generateDoubleArrayFrom(result, ChartGenerator.SUM);
		cpuUsageValueMaster = divideAllFor(cpuUsageValueMaster, toDivide);
		ArrayList<double[]> slave_sUsage = new ArrayList<double[]>();
		for (String dstatFile: dstatFileList){
			result2 = p.getDataFromCsv(dstatFile, columnDesired);
			double[] tmpSlave = p.generateDoubleArrayFrom(result2, ChartGenerator.SUM);
			tmpSlave = divideAllFor(tmpSlave, toDivide);
			slave_sUsage.add(tmpSlave);
		}
		double[] slaveDouble_Average = getAverage(p, slave_sUsage);
		generateChart(cpuUsageValueMaster, p.getRecordsIntervalFromCsv(dstatFilePath1), scaleIn, isPercentage, slaveDouble_Average , title, line1Name, line2Name, yLabel, isGreyScale, isDashedLine);
		return getUrl();
	}
	
	/**
	 * This method will generate all charts in one.
	 * @param dstatFilePath1
	 * @param dstatFileList
	 * @param columnDesired
	 * @param title
	 * @param line1Name
	 * @param line2Name
	 * @param isPercentage
	 * @param yLabel
	 * @param scaleIn
	 * @param isGreyScale
	 * @param isDashedLine
	 * @param outputPath
	 * @param outputFileName
	 * @return
	 */
	public String getChart(String dstatFilePath1, ArrayList<String> dstatFileList, ArrayList<String> columnDesired, String title, String line1Name, String line2Name, boolean isPercentage, double toDivide, String yLabel, int scaleIn, boolean isGreyScale, boolean isDashedLine, String outputPath, String fileName){
		Parser p = new Parser();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> result2 = new ArrayList<String>();
		result = p.getDataFromCsv(dstatFilePath1, columnDesired);
		double[] cpuUsageValueMaster = p.generateDoubleArrayFrom(result, ChartGenerator.SUM);
		cpuUsageValueMaster = divideAllFor(cpuUsageValueMaster, toDivide);
		ArrayList<double[]> slave_sUsage = new ArrayList<double[]>();
		for (String dstatFile: dstatFileList){
			result2 = p.getDataFromCsv(dstatFile, columnDesired);
			double[] tmpSlave = p.generateDoubleArrayFrom(result2, ChartGenerator.SUM);
			tmpSlave = divideAllFor(tmpSlave, toDivide);
			slave_sUsage.add(tmpSlave);
		}
		generateChart(cpuUsageValueMaster, p.getRecordsIntervalFromCsv(dstatFilePath1), scaleIn, isPercentage, slave_sUsage, title, line1Name, yLabel, isGreyScale, isDashedLine, outputPath, fileName, dstatFileList);
		return getUrl();
	}
	
	/**
	 * Divides all element in input array for
	 * double toDivide value.
	 * @param doubleList
	 * @param toDivide
	 * @return
	 */
	private double[] divideAllFor(double[] doubleList, double toDivide) {
		for(int i=0; i<doubleList.length; i++)
			doubleList[i] = doubleList[i]/toDivide;
		return doubleList;
	}

	private double[] getAverage(Parser p, ArrayList<double[]> slave_sUsage) {
		//get size of min slave file(we need first of the max)
		int maxRecordSize = 0;
		for(double[] record: slave_sUsage){
			if (record.length > maxRecordSize)	maxRecordSize = record.length;
		}
		int minRecordSize = maxRecordSize;
		for(double[] record: slave_sUsage){
			if (record.length < minRecordSize)	minRecordSize = record.length;
		}
		ArrayList<String> averageSlave_s = new ArrayList<String>();
		for (int i=0; i<minRecordSize; i++){
			double average_i = 0;
			for(int k=0; k<slave_sUsage.size(); k++){
				average_i += slave_sUsage.get(k)[i];
			}
			average_i = average_i/slave_sUsage.size();
			averageSlave_s.add(average_i + "");
		}
		double[] cpuUsageValueSlave = p.generateDoubleArrayFrom(averageSlave_s);
		return cpuUsageValueSlave;
	}
	
	/**
	 * Return max value of input array.
	 * @param data array of double.
	 * @return
	 */
	private double getMax(double[] data){
		if (data.length == 0)
			return 0;
		double max = data[0];
		for(int i=0; i<data.length; i++){
			if(data[i]>max)
				max = data[i];
		}
		if(max == 0)
			return IF_ZERO;
		return max;
	}
	
	/**
	 * Return max value of inputs array.
	 * @param data1 array of double.
	 * @param data2 array of double.
	 * @return
	 */
	private double getMax(double[] data1, double[] data2) {
		double max1 = getMax(data1);
		double max2 = getMax(data2);
		if(max1 > max2){
			if(max1 == 0)
				return IF_ZERO;
			return max1;
		}
		if(max2 == 0)
			return IF_ZERO;
		return max2;
	}
}
