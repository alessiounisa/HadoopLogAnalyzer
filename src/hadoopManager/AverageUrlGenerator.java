package hadoopManager;

import java.util.ArrayList;
import java.util.logging.Logger;

import chartsGenerators.ChartGenerator;

/**
 * @author Romano Simone - www.sromano.altervista.org
 *
 * This class generate average chart  
 * to evaluate hadoop experiments performance.
 */
public class AverageUrlGenerator implements UrlGenerator {
	private String masterCsvPath;
	private ArrayList<String> slave_sCsvPath;
	private static Logger log = Logger.getLogger("global");
	public static int MINUTE = 5, SECOND = 6, HOUR = 7;
	
	public AverageUrlGenerator(String masterCsvPath,	ArrayList<String> slave_sCsvPath) {
		super();
		this.masterCsvPath = masterCsvPath;
		this.slave_sCsvPath = slave_sCsvPath;
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

	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getNetworkAverage(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getNetwork(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myNetAvRecGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("recvTotPkt");
		columnDesired.add("sendTotPkt");
		log.info("Getting network package average");
		if (showTitle){
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("recvTotPkt"), "Network packets received - average", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("sendTotPkt"), "Network packets send - average", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		else{
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("recvTotPkt"), "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("sendTotPkt"), "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getNetworkThroughputAverage(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getNetworkThroughput(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myNetAvRecGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("recv");
		columnDesired.add("send");
		log.info("Getting network throughput package average");
		if (showTitle){
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("recv"), "Network throughput received - average", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("send"), "Network throughput send - average", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		else{
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("recv"), "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("send"), "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getCpuAverage(java.util.ArrayList, int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getCpu(ArrayList<String> columnDesired, int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator myCpuGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		log.info("Getting cpu average");
		if (showTitle){
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("usr"), "CPU usage - average", "Master", "Slave Average", true,1, "% usr", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("sys"), "CPU usage - average", "Master", "Slave Average", true,1, "% sys", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("idl"), "CPU usage - average", "Master", "Slave Average", true,1, "% idl", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("wai"), "CPU usage - average", "Master", "Slave Average", true,1, "% wai", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("hiq"), "CPU usage - average", "Master", "Slave Average", true,1, "% hiq", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("siq"), "CPU usage - average", "Master", "Slave Average", true,1, "% siq", scaleIn, isGrayScale, isDashedLine));
			String userDefinedLabel = "";
			for(String s: columnDesired)
				userDefinedLabel += s + ",";
			userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "CPU usage - average", "Master", "Slave Average", true,1, userDefinedLabel, scaleIn, isGrayScale, isDashedLine));
			return toReturn;
		}
		else{
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("usr"), "", "Master", "Slave Average", true,1, "% usr", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("sys"), "", "Master", "Slave Average", true,1, "% sys", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("idl"), "", "Master", "Slave Average", true,1, "% idl", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("wai"), "", "Master", "Slave Average", true,1, "% wai", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("hiq"), "", "Master", "Slave Average", true,1, "% hiq", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("siq"), "", "Master", "Slave Average", true,1, "% siq", scaleIn, isGrayScale, isDashedLine));
			String userDefinedLabel = "";
			for(String s: columnDesired)
				userDefinedLabel += s + ",";
			userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
			toReturn.add(myCpuGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "", "Master", "Slave Average", true,1, userDefinedLabel, scaleIn, isGrayScale, isDashedLine));
			return toReturn;
		}
	}
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getMemoryAverage(java.util.ArrayList, int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getMemory(ArrayList<String> columnDesired, int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator myMemoryGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		log.info("Getting memory average");
		if (showTitle){
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("used"), "RAM usage - average", "Master", "Slave Average", false, 1048576, "used", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("buff"), "RAM usage - average", "Master", "Slave Average", false, 1048576, "buff", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("cach"), "RAM usage - average", "Master", "Slave Average", false, 1048576, "cach", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("free"), "RAM usage - average", "Master", "Slave Average", false, 1048576, "free", scaleIn, isGrayScale, isDashedLine));
			String userDefinedLabel = "";
			for(String s: columnDesired)
				userDefinedLabel += s + ",";
			userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "RAM usage - average", "Master", "Slave Average", false, 1048576, userDefinedLabel, scaleIn, isGrayScale, isDashedLine));
			return toReturn;
		}
		else{
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("used"), "", "Master", "Slave Average", false, 1048576, "used", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("buff"), "", "Master", "Slave Average", false, 1048576, "buff", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("cach"), "", "Master", "Slave Average", false, 1048576, "cach", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("free"), "", "Master", "Slave Average", false, 1048576, "free", scaleIn, isGrayScale, isDashedLine));
			String userDefinedLabel = "";
			for(String s: columnDesired)
				userDefinedLabel += s + ",";
			userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
			toReturn.add(myMemoryGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "", "Master", "Slave Average", false, 1048576, userDefinedLabel, scaleIn, isGrayScale, isDashedLine));
			return toReturn;
		}
	}

	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getSwapAverage(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getSwap(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator mySwapGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("usedSwap");
		log.info("Getting used swap average");
		if (showTitle){
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "Used swap - average", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		else{
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getDiskAverage(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getDisk(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myDiskGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("read");
		columnDesired.add("writ");
		log.info("Getting I/O read/write average");
		if (showTitle){
			toReturn.add(myDiskGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("read"), "Disk Input - average", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myDiskGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("writ"), "Disk Output - average", "Master", "Slave Average", false, 1048576,  "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		else{
			toReturn.add(myDiskGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("read"), "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myDiskGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("writ"), "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		return toReturn;
	}	
	
	/* (non-Javadoc)
	 * @see hadoopManager.UrlGenerator#getPagingAverage(int, boolean, boolean, boolean)
	 */
	public ArrayList<String> getPaging(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myPagingGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("in");
		columnDesired.add("out");
		log.info("Getting Paging read/write average");
		if (showTitle){
			toReturn.add(myPagingGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("in"), "In paging - average", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myPagingGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("out"), "Output paging - average", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
		}
		else{
			toReturn.add(myPagingGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("in"), "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			toReturn.add(myPagingGen.getChart(masterCsvPath, slave_sCsvPath, getOnly("out"), "", "Master", "Slave Average", false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
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
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "Interrupts - average", "Master", "Slave Average", false,1, "#", scaleIn, isGrayScale, isDashedLine));
		}
		else{
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "", "Master", "Interrupts - Slave Average", false,1, "#", scaleIn, isGrayScale, isDashedLine));
		}
		return toReturn;
	}

	public ArrayList<String> getContextSwitches(int scaleIn,
			boolean isGrayScale, boolean isDashedLine, boolean showTitle) {
		ChartGenerator mySwapGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("cntsw");
		log.info("Getting context switches");
		if (showTitle){
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "Context switches - average", "Master", "Slave Average", false,1, "#", scaleIn, isGrayScale, isDashedLine));
		}
		else{
			toReturn.add(mySwapGen.getChart(masterCsvPath, slave_sCsvPath, columnDesired, "", "Master", "context switches - Slave Average", false,1, "#", scaleIn, isGrayScale, isDashedLine));
		}
		return toReturn;
	}
	
	private ArrayList<String> getOnly(String string) {
		ArrayList<String> toReturn = new ArrayList<String>();
		toReturn.add(string);
		return toReturn;
	}
}
