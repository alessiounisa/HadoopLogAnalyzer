package test;

import java.util.ArrayList;

import chartsGenerators.ChartGenerator;

public class TestAverageAndMaster {
	private static final int NUM_SLAVE = 32;

	public static void main(String[] args) {
		ChartGenerator myCpuGen = new ChartGenerator();
		String masterCsv = "C:\\Users\\Simone\\Google Drive\\Magistrale_IntelligenzaComputazionale\\sistemi_operativi_avanzati\\progetto\\workspace\\data\\DSTAT\\out_dstat_master.csv";
		ArrayList<String> slaveCsv = new ArrayList<String>();
		for(int i=1; i<=NUM_SLAVE; i++){
			slaveCsv.add("C:\\Users\\Simone\\Google Drive\\Magistrale_IntelligenzaComputazionale\\sistemi_operativi_avanzati\\progetto\\workspace\\data\\DSTAT\\out_dstat_" + i  + ".csv");
		}
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("usr");
		columnDesired.add("sys");
		System.out.println(myCpuGen.getChart(masterCsv, slaveCsv, columnDesired, "CPU usage", "Master", "Slave Average", true, "%"));
	
	}
}
