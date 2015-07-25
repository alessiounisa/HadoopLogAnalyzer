package hadoopManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import chartsManager.Chart;
import chartsManager.Chart_sCollector;


/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * This class allows you to generate webPage with yor hadoop 
 * charts.
 */
public class WebPageGenerator {
	private static Logger log = Logger.getLogger("global");
	private String img = "<div class=\"col-lg-3 col-md-4 col-xs-6 thumb\">"
						+ "<a class=\"thumbnail\" href=\"";
	private String img1 = "><img class=\"img-responsive\" src=\"";
	private String img2 = " alt=\"\"> </a></div>";	
	public static int MINUTE = 5, SECOND = 6, HOUR = 7, SINGLE_CHARTS = 8, AVERAGE_CHARTS = 9, ALL_IN_CHARTS = 10;
	
	public void generateWebPage(String outputPath, String masterCvsPath, ArrayList<String> slave_sCvsPath, String profilerPath, ArrayList<String> cpuColumnDesired, ArrayList<String> ramColumnDesired, int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle, int mode){
		String absoluteCurrentPath = System.getProperty("user.dir");
		String resourcePath = absoluteCurrentPath + File.separator + "webPage";
		UrlGenerator myChartGenerator = null;
		if (mode == AVERAGE_CHARTS)
			myChartGenerator = new AverageUrlGenerator(masterCvsPath, slave_sCvsPath);
		else if(mode == SINGLE_CHARTS)
			myChartGenerator = new SingleUrlGenerator(masterCvsPath, slave_sCvsPath);
		else if(mode == ALL_IN_CHARTS)
			myChartGenerator = new AllInUrlGenerator(masterCvsPath, slave_sCvsPath, outputPath);
		//Getting CPU charts
		ArrayList<String> cpuUrl_s = myChartGenerator.getCpu(cpuColumnDesired, scaleIn, isGrayScale, isDashedLine, showTitle);
		//Getting Ram charts
		ArrayList<String> ramUrl_s = myChartGenerator.getMemory(ramColumnDesired, scaleIn, isGrayScale, isDashedLine, showTitle);
		//Getting IO charts
		ArrayList<String> IO_url_s = myChartGenerator.getDisk(scaleIn, isGrayScale, isDashedLine, showTitle);
		//Getting network charts
		ArrayList<String> network_url_s = myChartGenerator.getNetwork(scaleIn, isGrayScale, isDashedLine, showTitle);
		//Getting network charts
		ArrayList<String> paging_url_s = myChartGenerator.getPaging(scaleIn, isGrayScale, isDashedLine, showTitle);
		//Getting network throughput charts
		ArrayList<String> throughput_url_s = myChartGenerator.getNetworkThroughput(scaleIn, isGrayScale, isDashedLine, showTitle);
		//Getting interrupts charts
		ArrayList<String> interrupts_url_s = myChartGenerator.getInterrupts(scaleIn, isGrayScale, isDashedLine, showTitle);
		//Getting context switches charts
		ArrayList<String> switches_url_s = myChartGenerator.getContextSwitches(scaleIn, isGrayScale, isDashedLine, showTitle);
		//Getting swap chart
		ArrayList<String> swap_url = myChartGenerator.getSwap(scaleIn, isGrayScale, isDashedLine, showTitle);
		dirCopy(resourcePath, outputPath);
		String outputFilePath = outputPath + File.separator + "index.html";
		//copying first index part
		File file = new File(outputPath + File.separator + "index1.html");
		Scanner input;
		try {
			input = new Scanner(file);
			PrintWriter writer;
			writer = new PrintWriter(outputFilePath, "UTF-8");
			while(input.hasNext()) {
			    String nextLine = input.nextLine();
			    writer.println(nextLine);
			}
			//adding generated charts for CPU
			writer.println(getSectionTitleHtml("Cpu", true));
			for(int i=0; i<cpuUrl_s.size(); i++){
				String nextLine = img + cpuUrl_s.get(i) + "\""
								+ img1 + cpuUrl_s.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding generated charts for RAM
			writer.println(getSectionTitleHtml("Ram", true));
			for(int i=0; i<ramUrl_s.size(); i++){
				String nextLine = img + ramUrl_s.get(i) + "\""
								+ img1 + ramUrl_s.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding generated charts for IO
			writer.println(getSectionTitleHtml("Disk", true));
			for(int i=0; i<IO_url_s.size(); i++){
				String nextLine = img + IO_url_s.get(i) + "\""
								+ img1 + IO_url_s.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding generated charts for network
			writer.println(getSectionTitleHtml("Network packet", true));
			for(int i=0; i<network_url_s.size(); i++){
				String nextLine = img + network_url_s.get(i) + "\""
								+ img1 + network_url_s.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding generated charts for network throughput
			writer.println(getSectionTitleHtml("Network throughput", true));
			for(int i=0; i<throughput_url_s.size(); i++){
				String nextLine = img + throughput_url_s.get(i) + "\""
								+ img1 + throughput_url_s.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding generated charts for paging
			writer.println(getSectionTitleHtml("Paging", true));
			for(int i=0; i<paging_url_s.size(); i++){
				String nextLine = img + paging_url_s.get(i) + "\""
								+ img1 + paging_url_s.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding generated charts for Interrutps
			writer.println(getSectionTitleHtml("Interrupts", true));
			for(int i=0; i<interrupts_url_s.size(); i++){
				String nextLine = img + interrupts_url_s.get(i) + "\""
								+ img1 + interrupts_url_s.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding generated charts for Context switches
			writer.println(getSectionTitleHtml("Context switches", true));
			for(int i=0; i<switches_url_s.size(); i++){
				String nextLine = img + switches_url_s.get(i) + "\""
								+ img1 + switches_url_s.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding swap charts
			writer.println(getSectionTitleHtml("Swap", true));
			for(int i=0; i<swap_url.size(); i++){
				String nextLine = img + swap_url.get(i) + "\""
								+ img1 + swap_url.get(i) + "\"" + img2;
				writer.println(nextLine);
			}
			//adding warning charts
			writer.println(getSectionTitleHtml("Warning charts", false));
			writer.println(getWarningHtml(mode));
			writer.println("</div>");
			//adding profiler info
			if(profilerPath == null){
				log.info("No profiler input file found!");
				writer.println(getSectionTitleHtml("Profiler informations", false));
				writer.println("To generate profiler informations add file 'prof_interval' in input path.</div>");
			}
			else{
				ProfilerHtmlGenerator profGenerator = new ProfilerHtmlGenerator(profilerPath);
				writer.println(getSectionTitleHtml("Profiler informations", false));
				writer.println(profGenerator.getProfilerDescription());
				writer.println(profGenerator.getButtonsRow_Html());
				writer.println("</div>");
				String pathProfiler = outputPath + File.separator + "profiler";
				profGenerator.generateHtml_s(pathProfiler);
			}
			//copy last part of file
			File file2 = new File(outputPath + File.separator + "index2.html");
			Scanner input2;
			input2 = new Scanner(file2);
			PrintWriter writer2;
			while(input2.hasNext()) {
			    String nextLine = input2.nextLine();
			    writer.println(nextLine);
			}
			//
			log.info("Web page generated: " + outputFilePath);
			input.close();
			writer.close();
		} catch (FileNotFoundException e1) {
			log.severe("Error making web page: " + e1.getMessage());
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			log.severe("Error making web page: " + e.getMessage());
			e.printStackTrace();
		}		
	}

	/**
	 * This method generate html code for warning charts.
	 * If user didn't select "Single_Charts" mode then warning
	 * chart will be not generated.
	 * @param mode	
	 * @return	html code
	 */
	private String getWarningHtml(int mode) {
		try{
			if(mode == SINGLE_CHARTS){
				HashMap<String, ArrayList<Chart>> positive_chart_s = Chart_sCollector.getPositiveWarningChart_s();
				HashMap<String, ArrayList<Chart>> negative_chart_s = Chart_sCollector.getNegativeWarningChart_s();
				Set<Entry<String, ArrayList<Chart>>> SET_pos_chart_s = null;
				Set<Entry<String, ArrayList<Chart>>> SET_neg_chart_s = null;
				if(positive_chart_s.size() > 0)
					SET_pos_chart_s = positive_chart_s.entrySet();
				if(negative_chart_s.size() > 0)
					SET_neg_chart_s = negative_chart_s.entrySet();
				String toReturn = "";
				//****************Positive Warning****************//
				if(SET_pos_chart_s != null){
					toReturn += getSectionTitleHtml("Positive Warning", "green");
					toReturn += Chart_sCollector.getPositiveWarningHtml();
					toReturn += "<ul>";
					for(Entry<String, ArrayList<Chart>> posChart:SET_pos_chart_s){
						toReturn += "<li>";
						toReturn += "<h1>" + posChart.getKey() + "</h1>";
						toReturn += "<ul>";
						for(Chart c:posChart.getValue()){
							toReturn += "<li><a href='" + c.getUrl() + "'>" + c.getTitle() +"</a></li>";				
						}
						toReturn += "</ul>";
						toReturn += "</li>";
					}
					toReturn += "</ul>";	
				}
				//****************Negative Warning****************//
				if(SET_neg_chart_s != null){
					toReturn += getSectionTitleHtml("Negative Warning", "red");
					toReturn += Chart_sCollector.getNegativeWarningHtml();
					toReturn += "<ul>";
					for(Entry<String, ArrayList<Chart>> negChart:SET_neg_chart_s){
						toReturn += "<li>";
						toReturn += "<h1>" + negChart.getKey() + "</h1>";
						toReturn += "<ul>";
						for(Chart c:negChart.getValue()){
							if(c.isNETWORK_Negative_Warning() || c.isRAM_Negative_Warning() || c.isSWAP_Negative_Warning())
								toReturn += "<li><a href='" + c.getUrl() + "'>" + c.getTitle() +"</a></li>";				
						}
						toReturn += "</ul>";
						toReturn += "</li>";
					}
					toReturn += "</ul>";	
				}
				return toReturn;
			}
			else{
				return "<div>To generate warning chart please select 'Singles' chart_type mode from "
						+ "tool's gui.<br></div>";
			}
		} catch(Exception e){
			log.severe("Exception getting warning charts: " + e.getMessage());
		}
		return "";
	}

	public void dirCopy(String src, String dest){	
    	File srcFolder = new File(src);
    	File destFolder = new File(dest); 
    	//make sure source exists
    	if(!srcFolder.exists()){
    		log.severe("Directory " + src + " does not exist.");
    		return;
    	}
        else{ 
           try{
        	copyFolder(srcFolder,destFolder);
           }catch(IOException e){
        	   log.severe("Exception copying dir: " + e.getMessage());
        	   e.printStackTrace();
           }
        }
    	log.info("Directory copy ok");
    }
 
    private void copyFolder(File src, File dest)
    	throws IOException{ 
    	if(src.isDirectory()){ 
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		} 
    		//list all the directory contents
    		String files[] = src.list();
 
    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
 
    	}else{
    			//if file, then copy it
    			//Use bytes stream to support all file types
    			InputStream in = new FileInputStream(src);
    			OutputStream out = new FileOutputStream(dest);  
    	        byte[] buffer = new byte[1024];
 
    	        int length;
    	        //copy the file content in bytes 
    	        while ((length = in.read(buffer)) > 0)
    	    	   out.write(buffer, 0, length);    	         
    	        in.close();
    	        out.close();
    	}
    }
    
    public String getSectionTitleHtml(String title, boolean closeDiv){
    	if(closeDiv)
    		return "<div id =\"" +title+ "\" class=\"col-lg-12\"><h1 class=\"page-header\">" + title + "</h1></div>";
    	else
    		return "<div id =\"" +title+ "\" class=\"col-lg-12\"><h1 class=\"page-header\">" + title + "</h1>";
    }
    
    public String getSectionTitleHtml(String title, String color){
    	String htmlTitle = "<div id =\"" +title+ "\" class=\"col-lg-12\"><h1 class=\"page-header\" style='color:" + color + "'>" + title + "</h1></div>";
    	return htmlTitle;
    }
}
