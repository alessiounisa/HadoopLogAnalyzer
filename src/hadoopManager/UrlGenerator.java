package hadoopManager;

import java.util.ArrayList;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * Interface of Url Generator.
 */
public interface UrlGenerator {
	public static final String INTERRUPTS = "Interrupts";
	public static final String CONTEXT_SWITCHES = "Context-switches";
	public static final String PAGING_I_O = "Paging I/O";
	public static final String OUTPUT_PAGING = "Output paging";
	public static final String IN_PAGING = "In paging";
	public static final String DISK_I_O = "Disk I/O";
	public static final String DISK_OUTPUT = "Disk Output";
	public static final String DISK_INPUT = "Disk Input";
	public static final String USED_SWAP = "Used swap";
	public static final String NETWORK_THROUGHPUT = "Network throughput";
	public static final String NETWORK_THROUGHPUT_SEND = "Network throughput send";
	public static final String NETWORK_THROUGHPUT_RECEIVED = "Network throughput received";
	public static final String NETWORK_PACKETS = "Network packets";
	public static final String NETWORK_PACKETS_SEND = "Network packets send";
	public static final String NETWORK_PACKETS_RECEIVED = "Network packets received";
	public static final String CPU_USAGE = "CPU usage";
	public static final String RAM_USAGE = "RAM usage";

	public abstract ArrayList<String> getNetwork(int scaleIn,
			boolean isGrayScale, boolean isDashedLine, boolean showTitle);

	public abstract ArrayList<String> getNetworkThroughput(int scaleIn,
			boolean isGrayScale, boolean isDashedLine, boolean showTitle);

	public abstract ArrayList<String> getCpu(
			ArrayList<String> columnDesired, int scaleIn, boolean isGrayScale,
			boolean isDashedLine, boolean showTitle);

	public abstract ArrayList<String> getMemory(
			ArrayList<String> columnDesired, int scaleIn, boolean isGrayScale,
			boolean isDashedLine, boolean showTitle);

	public abstract ArrayList<String> getSwap(int scaleIn,
			boolean isGrayScale, boolean isDashedLine, boolean showTitle);

	public abstract ArrayList<String> getDisk(int scaleIn,
			boolean isGrayScale, boolean isDashedLine, boolean showTitle);

	public abstract ArrayList<String> getPaging(int scaleIn,
			boolean isGrayScale, boolean isDashedLine, boolean showTitle);

	public abstract ArrayList<String> getInterrupts(int scaleIn,
			boolean isGrayScale, boolean isDashedLine, boolean showTitle);

	public abstract ArrayList<String> getContextSwitches(int scaleIn,
			boolean isGrayScale, boolean isDashedLine, boolean showTitle);

}