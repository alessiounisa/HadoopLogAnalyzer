package gui;

import hadoopManager.OSValidator;
import hadoopManager.WebPageGenerator;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;























import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;

import java.awt.Font;

public class FrameSpeedup extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private double newSequentialTime;
	private Logger log = Logger.getLogger("global");

	/**
	 * Create the frame.
	 */
	public FrameSpeedup(Object[][] speedUp, final double oldSequentialTime, final String outputPath) {
		setResizable(false);
		this.setTitle("Speedup");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 1027, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				Vector data = ((DefaultTableModel)table.getModel()).getDataVector();
				Object[][] toSet = new Object[data.size()][2];
				for(int i=0; i<data.size(); i++){
					Vector el = (Vector) data.get(i);
					toSet[i][0] = el.get(0);
					toSet[i][1] = el.get(1);
				}
				Home.setSpeedUpValue(toSet);
				Home.setSequentialTime(newSequentialTime);
			}
		});
		
		final SwingController controller = new SwingController();
        SwingViewBuilder factory = new SwingViewBuilder(controller);
        // add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        controller.getDocumentViewController()));
        JPanel panel_pdf = factory.buildViewerPanel();
        contentPane.setLayout(new GridLayout(0, 2, 0, 0));
        
        JPanel panel_5 = new JPanel();
        contentPane.add(panel_5);
        panel_5.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        panel_5.add(scrollPane);
        
        JPanel panel_2 = new JPanel();
        scrollPane.setViewportView(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
        	speedUp,
        	new String[] {
        		"Nodes number", "Time"
        	}
        ));
        table.getColumnModel().getColumn(0).setPreferredWidth(83);
        panel_2.add(table.getTableHeader(), BorderLayout.PAGE_START);
        panel_2.add(table, BorderLayout.CENTER);
        
        JPanel panel_4 = new JPanel();
        panel_2.add(panel_4, BorderLayout.NORTH);
        
        JPanel panel_3 = new JPanel();
        panel_5.add(panel_3, BorderLayout.SOUTH);
        panel_3.setLayout(new GridLayout(0, 5, 0, 0));
        
        JButton btnAddElement = new JButton("");
        btnAddElement.setIcon(new ImageIcon(FrameSpeedup.class.getResource("/gui/Add.png")));
        btnAddElement.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		DefaultTableModel dm = (DefaultTableModel) table.getModel();
        		dm.addRow(new Object[]{"",""});
        		log.info("New job added");
        	}
        });
        panel_3.add(btnAddElement);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnNewButton.setIcon(new ImageIcon(FrameSpeedup.class.getResource("/gui/remove.png")));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		DefaultTableModel dm = (DefaultTableModel) table.getModel();
        		dm.removeRow(dm.getRowCount() - 1);
        		log.info("Job removed");
        	}
        });
        panel_3.add(btnNewButton);
        
        JLabel lblSequentialTime = new JLabel("Sequential time:");
        panel_3.add(lblSequentialTime);
        
        final JSpinner spinnerSequentialTime = new JSpinner();
        spinnerSequentialTime.addChangeListener(new ChangeListener() {			
        	public void stateChanged(ChangeEvent arg0) {
        		newSequentialTime = Double.parseDouble(spinnerSequentialTime.getModel().getValue().toString());				
        	}
        });
        spinnerSequentialTime.addPropertyChangeListener(new PropertyChangeListener() {
        	public void propertyChange(PropertyChangeEvent arg0) {
        		newSequentialTime = Double.parseDouble(spinnerSequentialTime.getModel().getValue().toString());
        	}
        });
        spinnerSequentialTime.setModel(new SpinnerNumberModel(oldSequentialTime, null, null, new Double(1)));
        panel_3.add(spinnerSequentialTime);
        
        JButton btnGenerateSpeedUp = new JButton("Generate");
        btnGenerateSpeedUp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(table.getModel().getRowCount() == 0){
        			log.info("Add at least one row!");
        			JOptionPane.showMessageDialog(null, "Add at least one row!");
        			return;
        		}
        		//R input file generation
				String absoluteCurrentPath = System.getProperty("user.dir");				
				String resourcePath = absoluteCurrentPath + File.separator + "R_script";
				String rScriptPath = "\"" + resourcePath + File.separator + "speedup.R" + "\"";
				String outputFilePath = resourcePath + File.separator + "speedup.hla" ;
				try {
					PrintWriter speedUpFile = new PrintWriter(outputFilePath, "UTF-8");
					String nodesNumber = "";
					String time = "";
	        		Vector data = ((DefaultTableModel)table.getModel()).getDataVector();
					for(int i=0; i<data.size(); i++){
						Vector el = (Vector) data.get(i);
						nodesNumber += el.get(0) + ",";
						time += el.get(1) + ",";
					}
					nodesNumber = nodesNumber.substring(0, nodesNumber.length() - 1);
					time = time.substring(0, time.length() - 1);
					speedUpFile.println(nodesNumber);
					speedUpFile.println(time);
					speedUpFile.println(newSequentialTime);
					speedUpFile.println();
					speedUpFile.close();
					log.info("Speed up file written...");
					log.info("Running R_script to gneerate speedUp chart...");
				
					String mes = "";
					if(OSValidator.isMac()){																
						String[] command = {"Rscript",rScriptPath.replaceAll("\"", ""), outputPath + File.separator};
						mes = executeCommand(command);	
					}
					else
						mes = executeCommand("Rscript " + rScriptPath + " " + outputPath + File.separator);
					
					log.info(mes);
					controller.openDocument(outputPath + File.separator + "speedup_efficiency.pdf");
					controller.setZoom(new Float(0.54));
				} catch (FileNotFoundException e) {
					log.severe("Exception generating speedup.hla file: " + e.getMessage());
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					log.severe("Exception generating speedup.hla file: " + e.getMessage());
					e.printStackTrace();
				}
        	}
        	
        	public String executeCommand(String command){
				StringBuffer output = new StringBuffer();
				 
				Process p;
				try {
					log.info("Running R with command: " + command);
					p = Runtime.getRuntime().exec(command);
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));		 
		            String line = "";			
					while ((line = reader.readLine())!= null) {
						output.append(line + "\n");
					}		 
				} catch (Exception e) {
					log.severe("Exception executing r_script: " + e.getMessage());
					e.printStackTrace();
				}
		 
				return output.toString();
		 
			}
        	
        	public String executeCommand(String[] command){
				StringBuffer output = new StringBuffer();
				 
				Process p;
				try {
					String argv = "";
					for(String s:command)
						argv = argv+ " " + s;
					log.info("Running R with command: " + argv);
					p = Runtime.getRuntime().exec(command);
					p.waitFor();
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));		 
		            String line = "";			
					while ((line = reader.readLine())!= null) {
						output.append(line + "\n");
					}		 
				} catch (Exception e) {
					log.severe("Exception executing r_script: " + e.getMessage());
					e.printStackTrace();
				}
		 
				return output.toString();
		 
			}
        });
        panel_3.add(btnGenerateSpeedUp);
        
        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_1 = new JPanel();
        panel.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));
        panel_1.add(panel_pdf, BorderLayout.CENTER);
	}
	
}
