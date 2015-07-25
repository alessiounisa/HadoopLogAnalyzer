package hadoopManager;

import java.util.ArrayList;
import java.util.logging.Logger;

import chartsGenerators.ChartGenerator;

/**
 * @author Romano Simonewww.sromano.altervista.org
 * This class creates chart with all data in one(master and all slaves).
 */
public class AllInUrlGenerator implements UrlGenerator {
	private String masterCsvPath;
	private ArrayList<String> slave_sCsvPath;
	private String outputPath;
	private static Logger log = Logger.getLogger("global");
	public static int MINUTE = 5, SECOND = 6, HOUR = 7;
	
	public AllInUrlGenerator(String masterCsvPath,	ArrayList<String> slave_sCsvPath, String outputPath) {
		super();
		this.masterCsvPath = masterCsvPath;
		this.slave_sCsvPath = slave_sCsvPath;
		this.outputPath = outputPath;
	}

	public String getMasterCsvPath() {
		return masterCsvPath;
	}

	public void setMasterCsvPath(String masterCsvPath) {
		this.masterCsvPath = masterCsvPath;
	}

	public ArrayList<String> getSlave_sCsvPath() {
		return slave_sCsvPath;
	}

	public void setSlave_sCsvPath(ArrayList<String> slave_sCsvPath) {
		this.slave_sCsvPath = slave_sCsvPath;
	}	

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getNetwork(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getNetwork(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myNetAvRecGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("recvTotPkt");
		columnDesired.add("sendTotPkt");
		log.info("Getting network package");
		if (showTitle){
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("recvTotPkt"),"Network packets received","Master","Slave", false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"recvTotPkt"));
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("sendTotPkt"),"Network packets send","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"sendTotPkt"));
		}
		else{
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("recvTotPkt"),"","Master","Slave", false,1048576, "MByte",scaleIn, isGrayScale, isDashedLine, outputPath,"recvTotPkt"));
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("sendTotPkt"),"","Master","Slave", false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"sendTotPkt"));
		}
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getNetworkThroughput(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getNetworkThroughput(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myNetAvRecGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("recv");
		columnDesired.add("send");
		log.info("Getting network throughput package");
		if (showTitle){
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("recv"),"Network throughput received","Master","Slave", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"recv"));
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("send"),"Network throughput send","Master","Slave", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"send"));
		}
		else{
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("recv"),"","Master","Slave", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"recv"));
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("send"),"","Master","Slave", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"send"));
		}
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getCpu(java.util.ArrayList, int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getCpu(ArrayList<String> columnDesired, int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator myCpuGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		log.info("Getting cpu...");
		if (showTitle){
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("usr"),"CPU usage","Master","Slave", true, 1, "% usr", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_usr"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("sys"),"CPU usage","Master","Slave", true, 1, "% sys", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_sys"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("idl"),"CPU usage","Master","Slave", true, 1, "% idl", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_idl"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("wai"),"CPU usage","Master","Slave", true, 1, "% wai", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_wai"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("hiq"),"CPU usage","Master","Slave", true, 1, "% hiq", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_hiq"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("siq"),"CPU usage","Master","Slave", true, 1, "% siq", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_siq"));
			String userDefinedLabel ="";
			for(String s: columnDesired)
				userDefinedLabel += s +",";
			userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"CPU usage","Master","Slave", true, 1, userDefinedLabel, scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_" + userDefinedLabel));
			return toReturn;
		}
		else{
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("usr"),"","Master","Slave", true, 1, "% usr", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_usr"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("sys"),"","Master","Slave", true, 1, "% sys", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_sys"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("idl"),"","Master","Slave", true, 1, "% idl", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_idl"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("wai"),"","Master","Slave", true, 1, "% wai", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_wai"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("hiq"),"","Master","Slave", true, 1, "% hiq", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_hiq"));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("siq"),"","Master","Slave", true, 1, "% siq", scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_siq"));
			String userDefinedLabel ="";
			for(String s: columnDesired)
				userDefinedLabel += s +",";
			userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"","Master","Slave", true, 1,  userDefinedLabel, scaleIn, isGrayScale, isDashedLine, outputPath,"CPU_" + userDefinedLabel));
			return toReturn;
		}
	}
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getMemory(java.util.ArrayList, int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getMemory(ArrayList<String> columnDesired, int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator myMemoryGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		log.info("Getting memory");
		if (showTitle){
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("used"),"RAM usage","Master","Slave", false, 1048576, "used", scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_used"));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("buff"),"RAM usage","Master","Slave", false, 1048576, "buff", scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_buff"));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("cach"),"RAM usage","Master","Slave", false, 1048576, "cach", scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_cach"));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("free"),"RAM usage","Master","Slave", false, 1048576, "free", scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_free"));
			String userDefinedLabel ="";
			for(String s: columnDesired)
				userDefinedLabel += s +",";
			userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"RAM usage","Master","Slave", false, 1048576, userDefinedLabel, scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_" + userDefinedLabel));
			return toReturn;
		}
		else{
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("used"),"","Master","Slave", false, 1048576, "used", scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_used"));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("buff"),"","Master","Slave", false, 1048576, "buff", scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_buff"));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("cach"),"","Master","Slave", false, 1048576, "cach", scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_cach"));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("free"),"","Master","Slave", false, 1048576, "free", scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_free"));
			String userDefinedLabel ="";
			for(String s: columnDesired)
				userDefinedLabel += s +",";
			userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"","Master","Slave", false, 1048576,  userDefinedLabel, scaleIn, isGrayScale, isDashedLine, outputPath,"MEM_" + userDefinedLabel));
			return toReturn;
		}
	}

	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getSwap(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getSwap(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator mySwapGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("usedSwap");
		log.info("Getting used swap");
		if (showTitle){
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"Used swap","Master","Slave", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"SWAP_used"));
		}
		else{
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"","Master","Slave", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"SWAP_used"));
		}
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getDisk(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getDisk(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myDiskGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("read");
		columnDesired.add("writ");
		log.info("Getting I/O read/write");
		if (showTitle){
			toReturn.add(myDiskGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("read"),"Disk Input","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"DISK_read"));
			toReturn.add(myDiskGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("writ"),"Disk Output","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"DISK_write"));
		}
		else{
			toReturn.add(myDiskGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("read"),"","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"DISK_read"));
			toReturn.add(myDiskGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("writ"),"","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"DISK_write"));
		}
		return toReturn;
	}	
	
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getPaging(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getPaging(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myPagingGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("in");
		columnDesired.add("out");
		log.info("Getting Paging read/write");
		if (showTitle){
			toReturn.add(myPagingGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("in"),"In paging","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"PAGING_in"));
			toReturn.add(myPagingGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("out"),"Output paging","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"PAGING_out"));
		}
		else{
			toReturn.add(myPagingGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("in"),"","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine, outputPath,"PAGING_in"));
			toReturn.add(myPagingGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("out"),"","Master","Slave", false,1048576,"MByte", scaleIn, isGrayScale, isDashedLine,  outputPath,"PAGING_out"));
		}
		return toReturn;
	}	
	

	public ArrayList<String> getInterrupts(int scaleIn, boolean isGrayScale,
			boolean isDashedLine, boolean showTitle) {
		ChartGenerator mySwapGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("int");
		log.info("Getting interrupts");
		if (showTitle){
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"Used interrupts","Master","Slave", false,1,"#", scaleIn, isGrayScale, isDashedLine, outputPath,"interrupts_used"));
		}
		else{
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"","Master","Slave", false,1, "#", scaleIn, isGrayScale, isDashedLine, outputPath,"interrupts_used"));
		}
		return toReturn;
	}

	public ArrayList<String> getContextSwitches(int scaleIn, boolean isGrayScale,
			boolean isDashedLine, boolean showTitle) {
		ChartGenerator mySwapGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("cntsw");
		log.info("Getting context switches");
		if (showTitle){
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"Context switches","Master","Slave", false,1,"#", scaleIn, isGrayScale, isDashedLine, outputPath,"context_switches_used"));
		}
		else{
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired,"","Master","Slave", false,1,"#", scaleIn, isGrayScale, isDashedLine, outputPath,"context_switches_used"));
		}
		return toReturn;
	}

	private ArrayList<String> getOnly(String string) {
		ArrayList<String> toReturn = new ArrayList<String>();
		toReturn.add(string);
		return toReturn;
	}
}


