package test;

import chartsGenerators.ChartGenerator;
/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 *
 */
public class testChart {
	public static void main(String[] args) {
		ChartGenerator myCpuGen = new ChartGenerator();
		double[] cpuData = {1,23,54,3.6,30,43.52,100,32,44,45};
		myCpuGen.generateChart(cpuData, 1, true, "Cpu","cpu usage", "%");
		System.out.println(myCpuGen.getUrl());
	}
}
