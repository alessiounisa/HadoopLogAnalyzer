package chartsGenerators;

import hadoopManager.SingleUrlGenerator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.orsoncharts.util.Scale2D;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * To generate complex charts the library 'charts4j'
 * has problem becouse max request that googleApi could
 * support is 2048 URL length. Then for multilines chart
 * This class uses JFreeChart(http://www.jfree.org/).
 */
public class JFreeChartGenerator{
	private static String OUTPUT_FOLDER_NAME = "png_charts";
	private static Logger log = Logger.getLogger("global");
	public static int MINUTE = 5, SECOND = 6, HOUR = 7;
	
	/**
     * Creates a chart dataset.     
     * @return chart dataset.
     */
    private XYDataset createDataset(double[] data1, ArrayList<double[]> data2, int interval, int scaleIn, String data1Title, ArrayList<String> dstatFilePath) {   
    	XYSeriesCollection dataset = new XYSeriesCollection(); 
		XYSeries firstChart = new XYSeries(data1Title);
		double distanceBetweenPoints = 0.0;
    	if (scaleIn == MINUTE)
    		distanceBetweenPoints = 100.0/60.0/(double)interval;
    	if (scaleIn == SECOND)
    		distanceBetweenPoints = 100.0/1.0/(double)interval;
    	if (scaleIn == HOUR)
    		distanceBetweenPoints = 100.0/3600.0/(double)interval;
    	
		//creating dataset for first chart
		double actualPoint = 0.0;
		for (double y:data1){
			firstChart.add(actualPoint, y);
			actualPoint += distanceBetweenPoints;
		}
        dataset.addSeries(firstChart);
        
      //creating dataset for others dataset
		actualPoint = 0.0;
		int i=0;
		for(double[] data:data2){
			XYSeries tmp = new XYSeries(SingleUrlGenerator.getLineName(dstatFilePath.get(i)));
			i++;
			for(double y:data){
				tmp.add(actualPoint, y);
				actualPoint += distanceBetweenPoints;
			}
			dataset.addSeries(tmp);
			actualPoint = 0.0;
		}    
        return dataset;
    }
    
    /**
     * Create JFreeChart from input dataset
     * @param dataset
     * @return
     */
    private JFreeChart createChart(XYDataset dataset, String title, int scaleIn, String yLabel, boolean isPercentage, boolean isDashedLine) {        
    	 // create the chart...
    	String xAxis = checkXAxis(scaleIn);
    	
        final JFreeChart chart = ChartFactory.createXYLineChart(
        	title,      // chart title
        	xAxis,                      // x axis label
        	yLabel,                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        if (isDashedLine){
	        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
	        for (int i=0; i<dataset.getSeriesCount(); i++)
		    renderer.setSeriesStroke(
		          i, new BasicStroke(
		              1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
		              1.0f, new float[] {5.0f, 3.0f}, 0.0f
		          )
		      );
        }
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setLowerBound(0);
        
        if (isPercentage)
        	plot.getRangeAxis().setRange(new Range(0, 100), false, true);
        return chart;
    }

	private String checkXAxis(int scaleIn) {
		String xAxis = "";
		if (scaleIn == MINUTE)
    		xAxis = "Minutes";
    	if (scaleIn == SECOND)
    		xAxis = "Seconds";
    	if (scaleIn == HOUR)
    		xAxis = "Hours";
    	return xAxis;
	}
    
    private String createImg(JFreeChart chart, String outputPath, String fileName) {
		//createOutputDir
		File dir = new File(outputPath + File.separator + JFreeChartGenerator.OUTPUT_FOLDER_NAME);
		dir.mkdir();
		String outputFilePath =outputPath + File.separator + OUTPUT_FOLDER_NAME + File.separator + fileName + ".png";
		File fileImg = new File(outputFilePath);
		try {
			ChartUtilities.saveChartAsPNG(fileImg, chart, 600, 400);
		} catch (IOException e) {
			log.severe("Error creating JFreeChart img: " + e.getMessage());
			e.printStackTrace();
		}
		log.info("JFreeChart img created...");
		return outputFilePath;
	}
	
	/**
	 * This method make chart and returns its path.
	 * @return
	 */
	public String getChart(double[] data1, int interval, int scaleIn, boolean isPercentage, ArrayList<double[]> data2, String title, String data1Title, String yLabel, boolean isGrayScale, boolean isDashedLine, String outputPath, String fileName, ArrayList<String> dstatFilePath){
		XYDataset dataset = createDataset(data1, data2, interval, scaleIn, data1Title, dstatFilePath);
		JFreeChart chart = createChart(dataset, title, scaleIn, yLabel, isPercentage, isDashedLine);	
		String outputFile = createImg(chart, outputPath, fileName);
		return outputFile;
	}
}
