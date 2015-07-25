package test;

import java.util.ArrayList;

import hadoopManager.WebPageGenerator;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 */
public class TestHadoopGlobal {
	private static final int NUM_SLAVE = 32;

	public static void main(String[] args) {
		//Defining input
		String masterCsv = "C:\\Users\\Simone\\Google Drive\\Magistrale_IntelligenzaComputazionale\\sistemi_operativi_avanzati\\progetto\\workspace\\data\\DSTAT_1\\out_dstat_master.csv";
		ArrayList<String> slaveCsv = new ArrayList<String>();
		for(int i=1; i<=NUM_SLAVE; i++){
			slaveCsv.add("C:\\Users\\Simone\\Google Drive\\Magistrale_IntelligenzaComputazionale\\sistemi_operativi_avanzati\\progetto\\workspace\\data\\DSTAT_1\\50\\out_dstat_" + i  + ".csv");
		}
		String myOutputPath = "C:\\Users\\Simone\\Desktop\\output";
		ArrayList<String> cpuColumnDesired = new ArrayList<String>();
		cpuColumnDesired.add("usr");
		cpuColumnDesired.add("sys");
		ArrayList<String> ramColumnDesired = new ArrayList<String>();
		ramColumnDesired.add("cach");
		ramColumnDesired.add("free");
		//
		
		WebPageGenerator myHadoop = new WebPageGenerator();
		//String outputPath, String masterCvsPath, ArrayList<String> slave_sCvsPath, ArrayList<String> cpuColumnDesired, ArrayList<String> ramColumnDesired, int scaleIn, boolean isGrayScale, boolean isDashedLine, boolean showTitle, int mode
		//myHadoop.generateWebPage(myOutputPath, masterCsv, slaveCsv, cpuColumnDesired, ramColumnDesired, WebPageGenerator.MINUTE, false, false, true, WebPageGenerator.AVERAGE_CHARTS);
		//myHadoop.generateWebPage(myOutputPath, masterCsv, slaveCsv, cpuColumnDesired, ramColumnDesired, WebPageGenerator.MINUTE, false, false, true, WebPageGenerator.SINGLE_CHARTS);
		myHadoop.generateWebPage(myOutputPath, masterCsv, slaveCsv, cpuColumnDesired, ramColumnDesired, WebPageGenerator.MINUTE, false, true, true, WebPageGenerator.ALL_IN_CHARTS);
	}
}
