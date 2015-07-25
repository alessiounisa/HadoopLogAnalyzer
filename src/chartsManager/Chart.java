package chartsManager;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * This class represent a chart.
 */
public class Chart {
	private String title, slave, yLabel, url;
	private double[] data;
	private static Logger log = Logger.getLogger("global");
	private boolean SWAP_Negative_Warning, NETWORK_Negative_Warning, RAM_Negative_Warning = false;
	
	public Chart(String title, String slave, double[] data, String yLabel, String url) {
		super();
		this.title = title;
		this.slave = slave;
		this.data = data;
		this.yLabel = yLabel;
		this.url = url;
		log.info("New chart added: " + title + ", " + slave);
	}

	public boolean equals(Chart toCompare){
		if(this.title.equals(toCompare.getTitle()) &&
			this.slave.equals(toCompare.getSlave()) &&
			this.yLabel.equals(toCompare.getyLabel()) &&
			Arrays.equals(this.data, toCompare.getData()))
				return true;
		return false;		
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlave() {
		return slave;
	}

	public void setSlave(String slave) {
		this.slave = slave;
	}

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSWAP_Negative_Warning() {
		return SWAP_Negative_Warning;
	}

	public void setSWAP_Negative_Warning(boolean sWAP_Negative_Warning) {
		SWAP_Negative_Warning = sWAP_Negative_Warning;
	}

	public boolean isNETWORK_Negative_Warning() {
		return NETWORK_Negative_Warning;
	}

	public void setNETWORK_Negative_Warning(boolean nETWORK_Negative_Warning) {
		NETWORK_Negative_Warning = nETWORK_Negative_Warning;
	}

	public boolean isRAM_Negative_Warning() {
		return RAM_Negative_Warning;
	}

	public void setRAM_Negative_Warning(boolean rAM_Negative_Warning) {
		RAM_Negative_Warning = rAM_Negative_Warning;
	}	
}
