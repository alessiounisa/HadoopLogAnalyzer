package test;

import java.util.ArrayList;
import chartsGenerators.ChartGenerator;
import csvManipluators.Parser;

public class TwoLinesTest {
	public static void main(String[] args) {
		ChartGenerator myCpuGen = new ChartGenerator();
		String inputPath = "C:\\Users\\Simone\\Google Drive\\Magistrale_IntelligenzaComputazionale\\sistemi_operativi_avanzati\\progetto\\workspace\\data\\DSTAT\\out_dstat_master.csv";
		String inputPath2 = "C:\\Users\\Simone\\Google Drive\\Magistrale_IntelligenzaComputazionale\\sistemi_operativi_avanzati\\progetto\\workspace\\data\\DSTAT\\out_dstat_1.csv";
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("usr");
		columnDesired.add("sys");
		System.out.println(myCpuGen.getChart(inputPath, inputPath2, columnDesired, "CPU usage", "Master", "Slave 1", true, "%"));
	}
}
