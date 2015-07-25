package gui;

import java.awt.TextArea;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * This class allows help to capture all application
 * log and manage it in some way.
 */
public class LogHandler extends Handler {
	private String lastLog;
	private JTextArea log;
	
	public LogHandler(JTextArea textArea){
		lastLog = "";
		this.log = textArea;
	}
	
	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
	}

	@Override
	public void publish(LogRecord arg0) {
		// TODO Auto-generated method stub
		if(lastLog.equals("")){
			lastLog = arg0.getMessage();
			log.setText(arg0.getMessage());
			return;
		}
		if (!(arg0.getMessage().equals(lastLog))){
			lastLog = arg0.getMessage();
			log.setText(log.getText() + "\n" + arg0.getMessage());
		}
	}
	

}
