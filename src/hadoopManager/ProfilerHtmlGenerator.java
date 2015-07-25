package hadoopManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import hprof.*;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * This class generate html code to view information
 * about profiler input file.
 */
public class ProfilerHtmlGenerator {
	private static Traces myTrace_s;
	private String color_s[] = {"#5cb85c;","#ff0000;", "#758ef4;", "#f7552d"};
	private Logger log = Logger.getLogger("global");
	
	public ProfilerHtmlGenerator(String profilerFilePath){
		myTrace_s = new Traces(profilerFilePath);
		log.info("Profiler trace created with path: " + profilerFilePath);
	}
	
	/**
	 * This method will return list of button for each trace id.
	 * @return	html button list
	 */
	public String getButtonsRow_Html(){
		String toReturn = "";
		int col = 0;
		for(Trace t:myTrace_s.getTraces()){
			toReturn += "<a href='" + "profiler" + File.separator + t.getId() + ".html" + "' ><button type=\"button\" style=\"background: " + color_s[col] +"\" class=\"btn btn-default\">"+t.getId()+"</button></a>\n";
			col++;
			if(col == color_s.length -1)
				col = 0;
			log.info("Generated button for trace: " + t.getId());
		}
		return toReturn;
	}

	public void generateHtml_s(String pathProfiler) {
		for(Trace t:myTrace_s.getTraces()){
			String webPage = "<html><head>"
					+ "<link rel=\"stylesheet\" href=\"http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\"/>" 
					+ "</head><body>";
			webPage += "<table class='table table-bordered' border=1>";
			webPage += "<tr><th colspan=2>" + t.getId() + "</th></tr>";
			webPage += "<tr><th>Thread</th><td>" + t.getThread() + "</td></tr>";
			webPage += "<tr><th rowspan=" + (t.getMetodi().size()+1) + ">Methods</th></tr>";
			for(String method:t.getMetodi()){
				webPage += "<tr><td>" + method + "</td></tr>";
			}
			webPage += "<tr><th>Class name</th><td>" + t.getClassName() + "</td></tr>";
			webPage += "<tr><th>Self</th><td>" + t.getSelf() + "</td></tr>";
			webPage += "<tr><th>Live objects bytes</th><td>" + t.getLiveObject().getBytes() + "</td></tr>";
			webPage += "<tr><th>Live objects #</th><td>" + t.getLiveObject().getObjs() + "</td></tr>";
			webPage += "<tr><th>Allocated objects bytes</th><td>" + t.getAllocatedObject().getBytes() + "</td></tr>";
			webPage += "<tr><th>Allocated objects #</th><td>" + t.getAllocatedObject().getObjs() + "</td></tr>";
			webPage += "</table>";
			webPage += "</body></html>";
			try {
				PrintWriter writer = new PrintWriter(pathProfiler + File.separator + t.getId() + ".html", "UTF-8");
				writer.println(webPage);
				writer.close();
			} catch (FileNotFoundException e) {
				log.severe("Exception creating writer for html profiler: " + e.getMessage());
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				log.severe("Exception creating writer for html profiler: " + e.getMessage());
				e.printStackTrace();
			}
			log.info("Html for profiler " + t.getId() + " generated");
		}
	}
		
	public String getProfilerDescription(){
		return "This section contains informations about hprof. Next buttons have been ordered "
				+ "by increasing cluster usage.<br><hr>";
	}

}
