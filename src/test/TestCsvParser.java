package test;

import java.util.ArrayList;
import chartsGenerators.ChartGenerator;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 *
 */
public class TestCsvParser {
	
	public static void main(String[] args) {		
		ChartGenerator myCpuGen = new ChartGenerator();
		String inputPath = "C:\\Users\\Simone\\Google Drive\\Magistrale_IntelligenzaComputazionale\\sistemi_operativi_avanzati\\progetto\\workspace\\data\\DSTAT\\out_dstat_master.csv";
		ArrayList<String> columnDesired = new ArrayList<String>();
		columnDesired.add("usr");
		System.out.println(myCpuGen.getChart(inputPath, columnDesired, "Cpu", "Cpu usage", true, "%"));		
	}
}
