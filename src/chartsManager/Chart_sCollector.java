package chartsManager;

import hadoopManager.SingleUrlGenerator;
import hadoopManager.UrlGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.util.Log;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * This class groups each created chart.
 */
public class Chart_sCollector {
	private static final int DATA_PERCENTAGE = 60;
	private static final String RAM_COL_REF = "used";
	private static final String CPU_COL_REF = "% usr";
	private static HashMap<String,ArrayList<Chart>> chart_s;
	private static HashMap<String,ArrayList<Chart>> positiveWarningChart_s, negativeWarningChart_s;
	private static double CPU_POSITIVE_LIMIT, RAM_POSITIVE_LIMIT, IO_POSITIVE_LIMIT;
	private static double SWAP_NEGATIVE_LIMIT, NETWORK_NEGATIVE_LIMIT, RAM_NEGATIVE_LIMIT;

	public static void addChart(Chart toAdd){
		if(chart_s == null)
			chart_s = new HashMap<String,ArrayList<Chart>>();
		if(chart_s.get(toAdd.getSlave()) == null)
				chart_s.put(toAdd.getSlave(), new ArrayList<Chart>());
		if(!alreadyIn(chart_s.get(toAdd.getSlave()), toAdd))
			chart_s.get(toAdd.getSlave()).add(toAdd);			
		Log.info("New chart added to collection: " + toAdd.getTitle() + "_" + toAdd.getSlave());
	}	
	
	private static boolean alreadyIn(ArrayList<Chart> arrayList, Chart toAdd) {
		for(Chart c:arrayList)
			if(c.equals(toAdd))
				return true;
		return false;
	}

	public static double getCPU_POSITIVE_LIMIT() {
		return CPU_POSITIVE_LIMIT;
	}

	public static void setCPU_POSITIVE_LIMIT(double cPU_POSITIVE_LIMIT) {
		CPU_POSITIVE_LIMIT = cPU_POSITIVE_LIMIT;
	}

	public static double getRAM_POSITIVE_LIMIT() {
		return RAM_POSITIVE_LIMIT;
	}

	public static void setRAM_POSITIVE_LIMIT(double rAM_POSITIVE_LIMIT) {
		RAM_POSITIVE_LIMIT = rAM_POSITIVE_LIMIT;
	}

	public static double getIO_POSITIVE_LIMIT() {
		return IO_POSITIVE_LIMIT;
	}

	public static void setIO_POSITIVE_LIMIT(double iO_POSITIVE_LIMIT) {
		IO_POSITIVE_LIMIT = iO_POSITIVE_LIMIT;
	}

	public static double getSWAP_NEGATIVE_LIMIT() {
		return SWAP_NEGATIVE_LIMIT;
	}

	public static void setSWAP_NEGATIVE_LIMIT(double sWAP_NEGATIVE_LIMIT) {
		SWAP_NEGATIVE_LIMIT = sWAP_NEGATIVE_LIMIT;
	}

	public static double getNETWORK_NEGATIVE_LIMIT() {
		return NETWORK_NEGATIVE_LIMIT;
	}

	public static void setNETWORK_NEGATIVE_LIMIT(double nETWORK_NEGATIVE_LIMIT) {
		NETWORK_NEGATIVE_LIMIT = nETWORK_NEGATIVE_LIMIT;
	}

	public static double getRAM_NEGATIVE_LIMIT() {
		return RAM_NEGATIVE_LIMIT;
	}

	public static void setRAM_NEGATIVE_LIMIT(double rAM_NEGATIVE_LIMIT) {
		RAM_NEGATIVE_LIMIT = rAM_NEGATIVE_LIMIT;
	}

	public static HashMap<String, ArrayList<Chart>> getPositiveWarningChart_s(){
		positiveWarningChart_s = new HashMap<String, ArrayList<Chart>>();
		Set<Entry<String, ArrayList<Chart>>> setChart = chart_s.entrySet();
		for (Entry<String, ArrayList<Chart>> c:setChart)
			if(isPositiveWarningChart(c)){
				positiveWarningChart_s.put(c.getKey(), c.getValue());
				Log.info("Positive warning chart found: " + c.getKey());
			}
		return positiveWarningChart_s;
	}

	public static HashMap<String, ArrayList<Chart>> getNegativeWarningChart_s(){
		negativeWarningChart_s = new HashMap<String, ArrayList<Chart>>();
		Set<Entry<String, ArrayList<Chart>>> setChart = chart_s.entrySet();
		for (Entry<String, ArrayList<Chart>> c:setChart)
			if(isNegativeWarningChart(c))
				negativeWarningChart_s.put(c.getKey(), c.getValue());
		return negativeWarningChart_s;
	}

	/**
	 *  to be positive a set of chart must have:
	 *  	Cpu, Ram and disk IO under a specific threshold
	 */
	public static boolean isPositiveWarningChart(Entry<String, ArrayList<Chart>> allChart_s) {
		ArrayList<Chart> CPU_Usage_Chart_s = getCpuUsageChart_s(allChart_s);
		ArrayList<Chart> RAM_Usage_Chart_s = getRamUSageChart_s(allChart_s);
		ArrayList<Chart> IO_Usage_Chart_s = getIOUsageChart_s(allChart_s);
		if(underCpuLimit(CPU_Usage_Chart_s) && underRamLimit(RAM_Usage_Chart_s) && underIOLimit(IO_Usage_Chart_s))
			return true;
		return false;
	}
	
	/**
	 * This method extract from all input chart only
	 * the one relative to IO usage.
	 * @param allChart_s
	 * @return
	 */
	private static ArrayList<Chart> getIOUsageChart_s(Entry<String, ArrayList<Chart>> allChart_s) {
		ArrayList<Chart> toReturn = new ArrayList<Chart>();
		for(Chart chart:allChart_s.getValue()){
			if(chart.getTitle() == UrlGenerator.DISK_INPUT || chart.getTitle() == UrlGenerator.DISK_OUTPUT)
				toReturn.add(chart);
		}
		return toReturn;
	}

	/**
	 * This method extract from all input chart only
	 * the one relative to RAM usage.
	 * @param allChart_s
	 * @return
	 */
	private static ArrayList<Chart> getRamUSageChart_s(Entry<String, ArrayList<Chart>> allChart_s) {
		ArrayList<Chart> toReturn = new ArrayList<Chart>();
		for(Chart chart:allChart_s.getValue()){
			if(chart.getTitle() == UrlGenerator.RAM_USAGE)
				toReturn.add(chart);
		}
		return toReturn;
	}
	
	/**
	 * This method extract from all input chart only
	 * the one relative to cpu usage.
	 * @param allChart_s
	 * @return
	 */
	private static ArrayList<Chart> getCpuUsageChart_s(Entry<String, ArrayList<Chart>> allChart_s){
		ArrayList<Chart> toReturn = new ArrayList<Chart>();
		for(Chart chart:allChart_s.getValue()){
			if(chart.getTitle() == UrlGenerator.CPU_USAGE)
				toReturn.add(chart);
		}
		return toReturn;
	}
	
	private static boolean underIOLimit(ArrayList<Chart> iO_Usage_Chart_s) {
		for(Chart c:iO_Usage_Chart_s){
			if(c.getTitle().equals(SingleUrlGenerator.DISK_INPUT)){
				if(under(DATA_PERCENTAGE, c.getData(), IO_POSITIVE_LIMIT))
					return true;
			}
			if(c.getTitle().equals(SingleUrlGenerator.DISK_OUTPUT)){
				if(under(DATA_PERCENTAGE, c.getData(), IO_POSITIVE_LIMIT))
					return true;
			}
		}
		return false;
	}

	private static boolean underRamLimit(ArrayList<Chart> rAM_Usage_Chart_s) {
		for(Chart c:rAM_Usage_Chart_s)
			if(c.getyLabel().equals(RAM_COL_REF)){
				if(under(DATA_PERCENTAGE, c.getData(), RAM_POSITIVE_LIMIT*1000))
					return true;
			}
		return false;
	}

	private static boolean underCpuLimit(ArrayList<Chart> cPU_Usage_Chart_s) {
		for(Chart c:cPU_Usage_Chart_s)
			if(c.getyLabel().equals(CPU_COL_REF)){
				if(under(DATA_PERCENTAGE,c.getData(),CPU_POSITIVE_LIMIT))
						return true;
				return false;
			}
		return false;
	}

	private static boolean under(int percentageOfElemUnderLimit, double[] data, double limit) {
		double numOfEl = (double)data.length/100*percentageOfElemUnderLimit;
		int num = 0;
		for(double d:data){
			if(d<limit)
				num++;
			if(num>numOfEl)
				return true;
		}
		return false;
	}

	public static boolean isNegativeWarningChart(Entry<String, ArrayList<Chart>> allChart_s) {
		ArrayList<Chart> SWAP_Usage_Chart_s = getSwapUsageChart_s(allChart_s);
		ArrayList<Chart> RAM_Usage_Chart_s = getRamUSageChart_s(allChart_s);
		ArrayList<Chart> NETWORK_Usage_Chart_s = getNetworkUsageChart_s(allChart_s);
		boolean upperSwap = upperSwapLimit(SWAP_Usage_Chart_s);
		boolean upperRam = upperRamLimit(RAM_Usage_Chart_s);
		boolean upperNet = upperNetworkLimit(NETWORK_Usage_Chart_s);
		if(upperSwap || upperRam || upperNet)
			return true;
		return false;
	}

	private static boolean upperNetworkLimit(ArrayList<Chart> nETWORK_Usage_Chart_s) {
		boolean toReturn = false;
		for(Chart c:nETWORK_Usage_Chart_s){
			if(c.getTitle().equals(SingleUrlGenerator.NETWORK_THROUGHPUT_RECEIVED)){
				if(upper(DATA_PERCENTAGE, c.getData(), NETWORK_NEGATIVE_LIMIT)){
					c.setNETWORK_Negative_Warning(true);
					toReturn = true;
				}
				else
					c.setNETWORK_Negative_Warning(false);
			}
			if(c.getTitle().equals(SingleUrlGenerator.NETWORK_THROUGHPUT_SEND)){
				if(upper(DATA_PERCENTAGE, c.getData(), NETWORK_NEGATIVE_LIMIT)){
					c.setNETWORK_Negative_Warning(true);
					toReturn = true;
				}
				else
					c.setNETWORK_Negative_Warning(false);
			}
		}
		return toReturn;
	}

	private static boolean upperRamLimit(ArrayList<Chart> rAM_Usage_Chart_s) {
		for(Chart c:rAM_Usage_Chart_s)
			if(c.getyLabel().equals(RAM_COL_REF)){
				if(upper(DATA_PERCENTAGE, c.getData(), RAM_NEGATIVE_LIMIT*1000)){
					c.setRAM_Negative_Warning(true);
					return true;
				}
				c.setRAM_Negative_Warning(false);
			}
		return false;
	}

	private static boolean upperSwapLimit(ArrayList<Chart> sWAP_Usage_Chart_s) {
		for(Chart c:sWAP_Usage_Chart_s)
			if(c.getTitle().equals(SingleUrlGenerator.USED_SWAP)){
				if(upper(DATA_PERCENTAGE, c.getData(), SWAP_NEGATIVE_LIMIT*1000)){
					c.setSWAP_Negative_Warning(true);
					return true;
				}
				c.setSWAP_Negative_Warning(false);
			}
		return false;
	}
	
	private static boolean upper(int percentageOfElemUnderLimit, double[] data,	double limit) {
		double numOfEl = (double)data.length/100*percentageOfElemUnderLimit;
		int num = 0;
		for(double d:data){
			if(d>limit)
				num++;
			if(num>numOfEl)
				return true;
		}
		return false;
	}

	private static ArrayList<Chart> getNetworkUsageChart_s(Entry<String, ArrayList<Chart>> allChart_s) {
		ArrayList<Chart> toReturn = new ArrayList<Chart>();
		for(Chart chart:allChart_s.getValue()){
			if(chart.getTitle() == UrlGenerator.NETWORK_THROUGHPUT_RECEIVED || chart.getTitle() == UrlGenerator.NETWORK_THROUGHPUT_SEND)
				toReturn.add(chart);
		}
		return toReturn;
	}

	private static ArrayList<Chart> getSwapUsageChart_s(Entry<String, ArrayList<Chart>> allChart_s) {
		ArrayList<Chart> toReturn = new ArrayList<Chart>();
		for(Chart chart:allChart_s.getValue()){
			if(chart.getTitle() == UrlGenerator.USED_SWAP)
				toReturn.add(chart);
		}
		return toReturn;
	}	
	
	/**
	 * This method return an html code describing 
	 * positive warning criteria.
	 * @return positive warning criteria
	 */
	public static String getPositiveWarningHtml(){
		return("In this section there are slave in which: <br>"
				+ "<ul><li>Cpu usage has been under " + Chart_sCollector.CPU_POSITIVE_LIMIT 
				+ " in the column " + Chart_sCollector.CPU_COL_REF + " for the " 
				+ Chart_sCollector.DATA_PERCENTAGE +"% of run <b>AND</b></li>"
				+ "<li>I/O usage has been under " + Chart_sCollector.IO_POSITIVE_LIMIT + " for the " 
						+ Chart_sCollector.DATA_PERCENTAGE +"% of run <b>AND</b></li>"
				+ "<li>Ram usage has been under " + Chart_sCollector.RAM_POSITIVE_LIMIT 
				+ " in the column " + Chart_sCollector.RAM_COL_REF + " for the " 
				+ Chart_sCollector.DATA_PERCENTAGE +"% of run</li></lu>");
	}
	
	/**
	 * This method return an html code describing 
	 * negative warning criteria.
	 * @return negative warning criteria
	 */
	public static String getNegativeWarningHtml(){
		return("In this section there are slave in which: <br>"
				+ "<ul><li>Swap usage has been upper " + Chart_sCollector.SWAP_NEGATIVE_LIMIT + " for the " 
				+ Chart_sCollector.DATA_PERCENTAGE +"% of run <b>OR</b></li>"
				+ "<li>Network usage has been upper " + Chart_sCollector.NETWORK_NEGATIVE_LIMIT + " for the " 
						+ Chart_sCollector.DATA_PERCENTAGE +"% of run <b>OR</b></li>"
				+ "<li>Ram usage has been upper " + Chart_sCollector.RAM_NEGATIVE_LIMIT
				+ " in the column " + Chart_sCollector.RAM_COL_REF + " for the " 
				+ Chart_sCollector.DATA_PERCENTAGE +"% of run</li></lu>");
	}
}
