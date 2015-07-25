package hadoopManager;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;




















import chartsGenerators.ChartGenerator;

/**
 * @author Romano Simone - www.sromano.altervista.org
 *
 * This class generate single charts
 * to evaluate hadoop experiments performance.
 */
public class SingleUrlGenerator implements UrlGenerator{
	private String masterCsvPath;
	private ArrayList<String> slave_sCsvPath;
	private static Logger log = Logger.getLogger("global");
	public static int MINUTE = 5, SECOND = 6, HOUR = 7;
	
	public SingleUrlGenerator(String masterCsvPath,	ArrayList<String> slave_sCsvPath) {
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

	public ArrayList<String> getNetwork(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myNetAvRecGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("recvTotPkt");
		columnDesired.add("sendTotPkt");
		log.info("Getting network package...");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slavePath, getOnly("recvTotPkt"), NETWORK_PACKETS_RECEIVED, "Master", slaveLabel, false, 1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slavePath, getOnly("sendTotPkt"), NETWORK_PACKETS_SEND, "Master", slaveLabel, false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myNetAvRecGen.getChart(slavePath, columnDesired, true, NETWORK_PACKETS, slaveLabel + "_IN", slaveLabel + "_OUT", false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		else{
			for (String slavePath:slave_sCsvPath){		
				String slaveLabel = getLineName(slavePath);	
				toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slavePath, getOnly("recvTotPkt"), NETWORK_PACKETS_RECEIVED, "Master", "", false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slavePath, getOnly("sendTotPkt"), NETWORK_PACKETS_SEND, "Master", "", false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myNetAvRecGen.getChart(slavePath, columnDesired, true, "", slaveLabel + "_IN", slaveLabel + "_OUT", false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		return toReturn;
	}
	
	public ArrayList<String> getNetworkThroughput(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myNetAvRecGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("recv");
		columnDesired.add("send");
		log.info("Getting network throughput package");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slavePath, getOnly("recv"), NETWORK_THROUGHPUT_RECEIVED, "Master", slaveLabel, false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slavePath, getOnly("send"), NETWORK_THROUGHPUT_SEND, "Master", slaveLabel, false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myNetAvRecGen.getChart(slavePath, columnDesired, true, NETWORK_THROUGHPUT, slaveLabel + "_IN", slaveLabel + "_OUT", false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		else{
			for (String slavePath:slave_sCsvPath){		
				String slaveLabel = getLineName(slavePath);	
				toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slavePath, getOnly("recv"), "", "Master", slaveLabel, false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myNetAvRecGen.getChart(masterCsvPath, slavePath, getOnly("send"), "", "Master", slaveLabel, false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myNetAvRecGen.getChart(slavePath, columnDesired, true, "", slaveLabel + "_IN", slaveLabel + "_OUT", false, 1048576,"MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		return toReturn;
	}
	
	public ArrayList<String> getCpu(ArrayList<String> columnDesired, int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator myCpuGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		log.info("Getting cpu");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("usr"), CPU_USAGE, "Master", slaveLabel, true,1, "% usr", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("sys"), CPU_USAGE, "Master", slaveLabel, true,1, "% sys", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("idl"), CPU_USAGE, "Master", slaveLabel, true,1, "% idl", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("wai"), CPU_USAGE, "Master", slaveLabel, true,1, "% wai", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("hiq"), CPU_USAGE, "Master", slaveLabel, true,1, "% hiq", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("siq"), CPU_USAGE, "Master", slaveLabel, true,1, "% siq", scaleIn, isGrayScale, isDashedLine));
				String userDefinedLabel = "";
				for(String s: columnDesired)
					userDefinedLabel += s + ",";
				userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, columnDesired, CPU_USAGE, "Master", slaveLabel, true,1, userDefinedLabel, scaleIn, isGrayScale, isDashedLine));
			}
			return toReturn;
		}
		else{
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("usr"), "", "Master", slaveLabel, true,1, "% usr", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("sys"), "", "Master", slaveLabel, true,1,"% sys", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("idl"), "", "Master", slaveLabel, true,1, "% idl", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("wai"), "", "Master", slaveLabel, true,1, "% wai", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("hiq"), "", "Master", slaveLabel, true,1, "% hiq", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, getOnly("siq"), "", "Master", slaveLabel, true,1, "% siq", scaleIn, isGrayScale, isDashedLine));
				String userDefinedLabel = "";
				for(String s: columnDesired)
					userDefinedLabel += s + ",";
				userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
				toReturn.add(myCpuGen.getChart(masterCsvPath, slavePath, columnDesired, "", "Master", slaveLabel, true,1, userDefinedLabel, scaleIn, isGrayScale, isDashedLine));
			}
			return toReturn;
		}
	}
	public ArrayList<String> getMemory(ArrayList<String> columnDesired, int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator myMemoryGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		log.info("Getting memory");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, getOnly("used"), RAM_USAGE, "Master", slaveLabel, false, 1048576,"used", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, getOnly("buff"), RAM_USAGE, "Master", slaveLabel, false, 1048576,"buff", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, getOnly("cach"), RAM_USAGE, "Master", slaveLabel, false, 1048576,"cach", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, getOnly("free"), RAM_USAGE, "Master", slaveLabel, false, 1048576,"free", scaleIn, isGrayScale, isDashedLine));
				String userDefinedLabel = "";
				for(String s: columnDesired)
					userDefinedLabel += s + ",";
				userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, columnDesired, RAM_USAGE, "Master", slaveLabel, false, 1048576,userDefinedLabel, scaleIn, isGrayScale, isDashedLine));
			}
			return toReturn;
		}
		else{
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, getOnly("used"), "", "Master", slaveLabel, false,1048576, "used", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, getOnly("buff"), "", "Master", slaveLabel, false,1048576, "buff", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, getOnly("cach"), "", "Master", slaveLabel, false,1048576, "cach", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, getOnly("free"), "", "Master", slaveLabel, false,1048576, "free", scaleIn, isGrayScale, isDashedLine));
				String userDefinedLabel = "";
				for(String s: columnDesired)
					userDefinedLabel += s + ",";
				userDefinedLabel = userDefinedLabel.substring(0, userDefinedLabel.length()-1);
				toReturn.add(myMemoryGen.getChart(masterCsvPath, slavePath, columnDesired, "", "Master", slaveLabel, false,1048576, userDefinedLabel, scaleIn, isGrayScale, isDashedLine));
			}
			return toReturn;
		}
	}

	public ArrayList<String> getSwap(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ChartGenerator mySwapGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("usedSwap");
		log.info("Getting used swap");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(mySwapGen.getChart(masterCsvPath, slavePath, columnDesired, USED_SWAP, "Master", slaveLabel, false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		else{
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);			
				toReturn.add(mySwapGen.getChart(masterCsvPath, slavePath, columnDesired, "", "Master", slaveLabel, false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		return toReturn;
	}
	
	public ArrayList<String> getDisk(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myDiskGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("read");
		columnDesired.add("writ");
		log.info("Getting I/O read/write");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myDiskGen.getChart(masterCsvPath, slavePath, getOnly("read"), DISK_INPUT, "Master", slaveLabel, false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myDiskGen.getChart(masterCsvPath, slavePath, getOnly("writ"), DISK_OUTPUT, "Master", slaveLabel, false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myDiskGen.getChart(slavePath, columnDesired, true, DISK_I_O, slaveLabel + "_IN", slaveLabel + "_OUT", false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		else{
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myDiskGen.getChart(masterCsvPath, slavePath, getOnly("read"), "", "Master", slaveLabel, false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myDiskGen.getChart(masterCsvPath, slavePath, getOnly("writ"), "", "Master", slaveLabel, false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myDiskGen.getChart(slavePath, columnDesired, true, "", slaveLabel + "_IN", slaveLabel + "_OUT", false,1048576, "MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		return toReturn;
	}	
	
	public ArrayList<String> getPaging(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle){
		ArrayList<String> toReturn = new ArrayList<String>();
		ChartGenerator myPagingGen = new ChartGenerator();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("in");
		columnDesired.add("out");
		log.info("Getting Paging read/write");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myPagingGen.getChart(masterCsvPath, slavePath, getOnly("in"), IN_PAGING, "Master", slaveLabel, false,1048576,  "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myPagingGen.getChart(masterCsvPath, slavePath, getOnly("out"), OUTPUT_PAGING, "Master", slaveLabel, false,1048576,  "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myPagingGen.getChart(slavePath, columnDesired, true, PAGING_I_O, slaveLabel + "_IN", slaveLabel + "_OUT", false,1048576,  "MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		else{
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myPagingGen.getChart(masterCsvPath, slavePath, getOnly("in"), "", "Master", slaveLabel, false,1048576,  "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myPagingGen.getChart(masterCsvPath, slavePath, getOnly("out"), "", "Master", slaveLabel, false,1048576,  "MByte", scaleIn, isGrayScale, isDashedLine));
				toReturn.add(myPagingGen.getChart(slavePath, columnDesired, true, "", slaveLabel + "_IN", slaveLabel + "_OUT", false,1048576,  "MByte", scaleIn, isGrayScale, isDashedLine));
			}
		}
		return toReturn;
	}	
	
	public ArrayList<String> getContextSwitches(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle) {
		ChartGenerator myInterruptsGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("cntsw");
		log.info("Getting context switches");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myInterruptsGen.getChart(masterCsvPath, slavePath, columnDesired, CONTEXT_SWITCHES, "Master", slaveLabel, false,1, "#", scaleIn, isGrayScale, isDashedLine));
			}
		}
		else{
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);			
				toReturn.add(myInterruptsGen.getChart(masterCsvPath, slavePath, columnDesired, "", "Master", slaveLabel, false,1, "#", scaleIn, isGrayScale, isDashedLine));
			}
		}
		return toReturn;
	}

	public ArrayList<String> getInterrupts(int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle) {
		ChartGenerator myContextSwitchesGen = new ChartGenerator();
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("int");
		log.info("Getting interrupts");
		if (showTitle){
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);
				toReturn.add(myContextSwitchesGen.getChart(masterCsvPath, slavePath, columnDesired, INTERRUPTS, "Master", slaveLabel, false,1, "#", scaleIn, isGrayScale, isDashedLine));
			}
		}
		else{
			for (String slavePath:slave_sCsvPath){			
				String slaveLabel = getLineName(slavePath);			
				toReturn.add(myContextSwitchesGen.getChart(masterCsvPath, slavePath, columnDesired, "", "Master", slaveLabel, false,1, "#", scaleIn, isGrayScale, isDashedLine));
			}
		}
		return toReturn;
	}

	private ArrayList<String> getOnly(String string) {
		ArrayList<String> toReturn = new ArrayList<String>();
		toReturn.add(string);
		return toReturn;
	}
	
	public static String getLineName(String slavePath) {
		String slaveLabel = slavePath.substring(slavePath.lastIndexOf(File.separator) + 1, slavePath.length());
		slaveLabel = slaveLabel.substring(0, slaveLabel.lastIndexOf("."));
		return slaveLabel;
	}
}
