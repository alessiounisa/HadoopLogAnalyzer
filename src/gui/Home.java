package gui;

import hadoopManager.OSValidator;
import hadoopManager.WebPageGenerator;

import java.awt.EventQueue;

import javax.naming.ldap.Rdn;
import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JFileChooser;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;

import javax.swing.JPanel;

import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.awt.SystemColor;

import javax.swing.JRadioButton;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import javax.swing.ImageIcon;

import java.awt.Button;

import javax.swing.JSlider;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import chartsManager.Chart_sCollector;

import java.awt.FlowLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Insets;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


/**
 * 
 * @author Romano Simone - www.sromano.altervista.org
 *
 */
public class Home {
	public static final int R_PROCESS_WAIT = 1;
	private static final String NO_INPUT_OUTPUT = "Add input and output path!";
	private JFrame frmHadoopChartTool;
	private Button btnGenerate;
	private String masterPath, outputPath, profilerPath, logHadoopDirPath, logHadoopOutputPath, confFilePath, inputPath;
	private ArrayList<String> slave_sPath;
	private LogHandler myConsole;
	private static Object[][] speedUpValue;
	private static double sequentialTime;
	private static Logger log = Logger.getLogger("global");	
	
	public Home(String masterPath) {
		super();
		this.masterPath = "";
		this.slave_sPath = new ArrayList<String>();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frmHadoopChartTool.setVisible(true);
					window.frmHadoopChartTool.setLocation(0, 0);
					window.frmHadoopChartTool.setExtendedState(window.frmHadoopChartTool.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHadoopChartTool = new JFrame();
		//frmHadoopChartTool.setResizable(false);
		frmHadoopChartTool.setTitle("Hadoop Analyzer JR");
		frmHadoopChartTool.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/gui/cluster.png")));
		
		frmHadoopChartTool.setBounds(100, 100, 1300, 700);
		//frmHadoopChartTool.setBounds(100, 100, xSize, ySize);
		frmHadoopChartTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		

		//pdf viewer
		final SwingController controller = new SwingController();
        SwingViewBuilder factory = new SwingViewBuilder(controller);
        JPanel panel_pdf = factory.buildViewerPanel();
        // add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        controller.getDocumentViewController()));
		
		ButtonGroup groupTime = new ButtonGroup();

		ButtonGroup chartsType = new ButtonGroup();
		
		ButtonGroup typeOutput = new ButtonGroup();
		

		ButtonGroup groupLibrary = new ButtonGroup();
		
		ButtonGroup comSin = new ButtonGroup();
		
		ButtonGroup comSin_1 = new ButtonGroup();

		ButtonGroup comSin_2 = new ButtonGroup();

		ButtonGroup comSin_3 = new ButtonGroup();
		//Create the label table
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("0%") );
		labelTable.put( new Integer( 50 ), new JLabel("50%") );
		labelTable.put( new Integer( 100 ), new JLabel("100%") );
		//Create the label table
		Hashtable labelTable_1 = new Hashtable();
		labelTable_1.put( new Integer( 0 ), new JLabel("0") );
		labelTable_1.put( new Integer( 25 ), new JLabel("10") );
		labelTable_1.put( new Integer( 50 ), new JLabel("20") );
		labelTable_1.put( new Integer( 75 ), new JLabel("30") );
		labelTable_1.put( new Integer( 100 ), new JLabel("40") );
		//Create the label table
		Hashtable labelTable_2 = new Hashtable();
		labelTable_2.put( new Integer( 0 ), new JLabel("0") );
		labelTable_2.put( new Integer( 25 ), new JLabel("8") );
		labelTable_2.put( new Integer( 50 ), new JLabel("16") );
		labelTable_2.put( new Integer( 75 ), new JLabel("24") );
		labelTable_2.put( new Integer( 100 ), new JLabel("32") );
		//Create the label table
		Hashtable labelTable_3 = new Hashtable();
		labelTable_3.put( new Integer( 0 ), new JLabel("0") );
		labelTable_3.put( new Integer( 25 ), new JLabel("8") );
		labelTable_3.put( new Integer( 50 ), new JLabel("16") );
		labelTable_3.put( new Integer( 75 ), new JLabel("24") );
		labelTable_3.put( new Integer( 100 ), new JLabel("32") );
		//Create the label table
		Hashtable labelTable_4 = new Hashtable();
		labelTable_4.put( new Integer( 0 ), new JLabel("0") );
		labelTable_4.put( new Integer( 25 ), new JLabel("8") );
		labelTable_4.put( new Integer( 50 ), new JLabel("16") );
		labelTable_4.put( new Integer( 75 ), new JLabel("24") );
		labelTable_4.put( new Integer( 100 ), new JLabel("32") );
		//Create the label table
		Hashtable labelTable_5 = new Hashtable();
		labelTable_5.put( new Integer( 0 ), new JLabel("0") );
		labelTable_5.put( new Integer( 25 ), new JLabel("8") );
		labelTable_5.put( new Integer( 50 ), new JLabel("16") );
		labelTable_5.put( new Integer( 75 ), new JLabel("24") );
		labelTable_5.put( new Integer( 100 ), new JLabel("32") );
		frmHadoopChartTool.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 200, 0, 0));
		frmHadoopChartTool.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		final JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setEnabled(false);
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenuItem mntmSpeedUp = new JMenuItem("Speedup");
		mntmSpeedUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(outputPath == null || outputPath.equals("") || outputPath.equals("null")){
					JOptionPane.showMessageDialog(null, "Please insert output path!");
				}
				else{
					JFrame speedUp = new FrameSpeedup(speedUpValue, sequentialTime, outputPath);
					speedUp.setLocationRelativeTo(null);  
					speedUp.setVisible(true);
				}
			}
		});
		mnTools.add(mntmSpeedUp);
		
		JMenu mnAbout = new JMenu("Help");
		menuBar.add(mnAbout);
		
		JMenuItem mntmManual = new JMenuItem("Manual");
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Desktop.isDesktopSupported()) {
					try {
						String absoluteCurrentPath = System.getProperty("user.dir");				
						String resourcePath = absoluteCurrentPath + File.separator + "ManualeUtente.pdf";
						Desktop.getDesktop().open(new File(resourcePath));
					} catch (IOException e) {
						log.severe("Exception opening user manual: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		});
		mnAbout.add(mntmManual);
		
		JMenuItem mntmInfo = new JMenuItem("About");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame about = new About();
				about.setLocationRelativeTo(null);  
				about.setVisible(true);
			}
		});
		mnAbout.add(mntmInfo);
		
		JPanel panel_46 = new JPanel();
		frmHadoopChartTool.getContentPane().add(panel_46);
		panel_46.setLayout(new GridLayout(0, 2, 0, 0));
		final JTextArea textArea = new JTextArea();
		
		JPanel panel = new JPanel();
		panel_46.add(panel);
		panel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_2.add(panel_7, BorderLayout.SOUTH);
		panel_7.setLayout(new GridLayout(0, 4, 0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.LIGHT_GRAY);
		panel_7.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCpu = new JLabel("CPU");
		lblCpu.setHorizontalAlignment(SwingConstants.CENTER);
		panel_8.add(lblCpu, BorderLayout.NORTH);
		
		JPanel panel_16 = new JPanel();
		panel_8.add(panel_16, BorderLayout.CENTER);
		panel_16.setLayout(new GridLayout(0, 3, 0, 0));
		
		final JCheckBox chckbxUsr = new JCheckBox("usr");
		panel_16.add(chckbxUsr);
		chckbxUsr.setSelected(true);
		
		final JCheckBox chckbxSys = new JCheckBox("sys");
		panel_16.add(chckbxSys);
		chckbxSys.setSelected(true);
		
		final JCheckBox chckbxIdl = new JCheckBox("idl");
		panel_16.add(chckbxIdl);
		
		final JCheckBox chckbxWai = new JCheckBox("wai");
		panel_16.add(chckbxWai);
		
		final JCheckBox chckbxHiq = new JCheckBox("hiq");
		panel_16.add(chckbxHiq);
		
		final JCheckBox chckbxSiq = new JCheckBox("siq");
		panel_16.add(chckbxSiq);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.DARK_GRAY);
		panel_7.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRam = new JLabel("Ram");
		lblRam.setForeground(Color.LIGHT_GRAY);
		lblRam.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(lblRam, BorderLayout.NORTH);
		
		JPanel panel_20 = new JPanel();
		panel_9.add(panel_20, BorderLayout.CENTER);
		panel_20.setLayout(new GridLayout(0, 2, 0, 0));
		
		final JCheckBox chckbxUsed = new JCheckBox("Used");
		panel_20.add(chckbxUsed);
		chckbxUsed.setSelected(true);
		
		final JCheckBox chckbxBuff = new JCheckBox("Buff");
		panel_20.add(chckbxBuff);
		chckbxBuff.setSelected(true);
		
		final JCheckBox chckbxCach = new JCheckBox("Cache");
		panel_20.add(chckbxCach);
		
		final JCheckBox chckbxFree = new JCheckBox("Free");
		panel_20.add(chckbxFree);
		
		JLabel lblRamUpperBound = new JLabel("Upper bound");
		panel_20.add(lblRamUpperBound);
		
		JPanel panel_23 = new JPanel();
		panel_20.add(panel_23);
		panel_23.setLayout(new GridLayout(0, 2, 0, 0));
		
		final JSpinner spinner_ram = new JSpinner();
		final JCheckBox checkBoxUpperBound = new JCheckBox("");
		checkBoxUpperBound.setEnabled(false);
		checkBoxUpperBound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checkBoxUpperBound.isSelected())
					spinner_ram.setEnabled(true);
				else
					spinner_ram.setEnabled(false);
			}
		});
		checkBoxUpperBound.setHorizontalAlignment(SwingConstants.CENTER);
		panel_23.add(checkBoxUpperBound);
		
		spinner_ram.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(100)));
		spinner_ram.setEnabled(false);
		panel_23.add(spinner_ram);
		
		
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.LIGHT_GRAY);
		panel_7.add(panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		panel_10.add(lblTime, BorderLayout.NORTH);
		
		JPanel panel_21 = new JPanel();
		panel_10.add(panel_21, BorderLayout.CENTER);
		panel_21.setLayout(new GridLayout(0, 3, 0, 0));
		
		final JRadioButton rdbtnHour = new JRadioButton("Hour");
		panel_21.add(rdbtnHour);
		
		final JRadioButton rdbtnMin = new JRadioButton("Min");
		panel_21.add(rdbtnMin);
		rdbtnMin.setSelected(true);
		
		final JRadioButton rdbtnSec = new JRadioButton("Sec");
		panel_21.add(rdbtnSec);
		groupTime.add(rdbtnHour);
		groupTime.add(rdbtnMin);
		groupTime.add(rdbtnSec);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.DARK_GRAY);
		panel_7.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTime_1 = new JLabel("Charts type");
		lblTime_1.setForeground(Color.LIGHT_GRAY);
		lblTime_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_12.add(lblTime_1, BorderLayout.NORTH);
		
		JPanel panel_6 = new JPanel();
		panel_12.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new GridLayout(3, 1, 0, 0));
		
		final JRadioButton rdbtnSlavesSingles = new JRadioButton("Singles");
		rdbtnSlavesSingles.setSelected(true);
		panel_6.add(rdbtnSlavesSingles);
		
		final JLabel lblwarning = new JLabel("(Warning)");
		lblwarning.setToolTipText("Warning charts are available in Google/JFreeChart mode");
		panel_6.add(lblwarning);
		
		final JRadioButton rdbtnAllIn = new JRadioButton("All in");
		panel_6.add(rdbtnAllIn);
		
		JLabel label_1 = new JLabel("");
		panel_6.add(label_1);
		
		final JRadioButton rdbtnSlaveAverage = new JRadioButton("Average");		
		panel_6.add(rdbtnSlaveAverage);
		rdbtnSlaveAverage.setSelected(true);
		chartsType.add(rdbtnSlavesSingles);
		chartsType.add(rdbtnAllIn);
		chartsType.add(rdbtnSlaveAverage);
		
		JPanel panel_13 = new JPanel();
		panel_13.setBackground(Color.LIGHT_GRAY);
		panel_7.add(panel_13);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGreyScale = new JLabel("Charts colors");
		lblGreyScale.setHorizontalAlignment(SwingConstants.CENTER);
		panel_13.add(lblGreyScale, BorderLayout.NORTH);
		
		JPanel panel_54 = new JPanel();
		panel_13.add(panel_54, BorderLayout.CENTER);
		panel_54.setLayout(new GridLayout(1, 0, 0, 0));
		
		final JCheckBox chckbxGreyScale = new JCheckBox("Grey scale");
		panel_54.add(chckbxGreyScale);
		
				
				
				
				
				JPanel panel_14 = new JPanel();
				panel_14.setBackground(Color.DARK_GRAY);
				panel_7.add(panel_14);
				panel_14.setLayout(new BorderLayout(0, 0));
				
				JLabel lblLinesStyle = new JLabel("Lines style");
				lblLinesStyle.setForeground(Color.LIGHT_GRAY);
				lblLinesStyle.setHorizontalAlignment(SwingConstants.CENTER);
				panel_14.add(lblLinesStyle, BorderLayout.NORTH);
				
				JPanel panel_55 = new JPanel();
				panel_14.add(panel_55, BorderLayout.CENTER);
				panel_55.setLayout(new GridLayout(0, 1, 0, 0));
				
				final JCheckBox chckbxDashedLine = new JCheckBox("Dashed line");
				panel_55.add(chckbxDashedLine);
				
				JPanel panel_15 = new JPanel();
				panel_15.setBackground(Color.LIGHT_GRAY);
				panel_7.add(panel_15);
				panel_15.setLayout(new BorderLayout(0, 0));
				
				JLabel lblShowTitles = new JLabel("Charts titles");
				lblShowTitles.setHorizontalAlignment(SwingConstants.CENTER);
				panel_15.add(lblShowTitles, BorderLayout.NORTH);
				
				JPanel panel_56 = new JPanel();
				panel_15.add(panel_56, BorderLayout.CENTER);
				panel_56.setLayout(new GridLayout(1, 0, 0, 0));
				
				final JCheckBox chckbxTitles = new JCheckBox("Show titles");
				panel_56.add(chckbxTitles);
				chckbxTitles.setSelected(true);
				
				JPanel panel_33 = new JPanel();
				panel_33.setBackground(Color.DARK_GRAY);
				panel_7.add(panel_33);
				panel_33.setLayout(new BorderLayout(0, 0));
				
				JLabel lblFontSize = new JLabel("Others");
				lblFontSize.setForeground(Color.LIGHT_GRAY);
				lblFontSize.setHorizontalAlignment(SwingConstants.CENTER);
				panel_33.add(lblFontSize, BorderLayout.NORTH);
				
				JPanel panel_39 = new JPanel();
				panel_33.add(panel_39, BorderLayout.CENTER);
				panel_39.setLayout(new GridLayout(2, 2, 0, 0));
				
				JLabel lblFontSize_1 = new JLabel("Font size");
				lblFontSize_1.setHorizontalAlignment(SwingConstants.CENTER);
				panel_39.add(lblFontSize_1);
				
				final JSpinner spinner_font_size = new JSpinner();
				spinner_font_size.setModel(new SpinnerNumberModel(10, 10, 24, 1));
				panel_39.add(spinner_font_size);
				final JRadioButton rdbtnSingleFile = new JRadioButton("Single file");
				rdbtnSingleFile.setSelected(true);
				rdbtnSingleFile.setEnabled(false);
				panel_39.add(rdbtnSingleFile);
				
				final JRadioButton rdbtnMultifiles = new JRadioButton("Multi-files");
				rdbtnMultifiles.setEnabled(false);
				panel_39.add(rdbtnMultifiles);
				typeOutput.add(rdbtnSingleFile);
				typeOutput.add(rdbtnMultifiles);
				
				JPanel panel_3 = new JPanel();
				panel_2.add(panel_3, BorderLayout.CENTER);
				panel_3.setLayout(new GridLayout(3, 4, 0, 0));
				
				JLabel lblMasterCvs = new JLabel("Input:");
				lblMasterCvs.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblMasterCvs.setHorizontalAlignment(SwingConstants.CENTER);
				panel_3.add(lblMasterCvs);
				
				final JLabel labelInputPath = new JLabel("...");
				labelInputPath.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						labelInputPath.setToolTipText(labelInputPath.getText());
					}
				});
				panel_3.add(labelInputPath);
				
				JPanel panel_17 = new JPanel();
				panel_3.add(panel_17);
				panel_17.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
				Button btnBrowseMaster = new Button("Browse...");
				panel_17.add(btnBrowseMaster);
				btnBrowseMaster.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
						fileChooser.showOpenDialog(null);
						File[] file_s = fileChooser.getSelectedFile().listFiles();
						labelInputPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
						inputPath = labelInputPath.getText();
						for (File f:file_s)
							if (f != null && f.getAbsolutePath() != null){
								if(f.getAbsolutePath().contains("out_dstat_master") && 
										!f.getAbsolutePath().contains(".hla")){
									masterPath = f.getAbsolutePath();
								}
								else if(f.getAbsolutePath().contains("prof_interval"))
									profilerPath = f.getAbsolutePath();
								else{
									if(!f.getAbsolutePath().contains(".hla")){
										if(slave_sPath == null){
											slave_sPath = new ArrayList<String>();
										}
										slave_sPath.add(f.getAbsolutePath());
									}
								}
							}
					}
				});
				
				JLabel lblOuput = new JLabel("Ouput:");
				lblOuput.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblOuput.setHorizontalAlignment(SwingConstants.CENTER);
				panel_3.add(lblOuput);
				
				final JLabel labelOutputPath = new JLabel("...");
				labelOutputPath.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						labelOutputPath.setToolTipText(labelOutputPath.getText());
					}
				});
				panel_3.add(labelOutputPath);
				
				JPanel panel_19 = new JPanel();
				panel_3.add(panel_19);
				panel_19.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
				Button btnBrowseOutput = new Button("Browse...");
				panel_19.add(btnBrowseOutput);
				btnBrowseOutput.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						fileChooser.showOpenDialog(null);
						File f = fileChooser.getSelectedFile();
						if (f != null && f.getAbsolutePath() != null){
							labelOutputPath.setText(f.getAbsolutePath());
							outputPath = f.getAbsolutePath();
						}
					}
				});
				
				final JPanel panel_11 = new JPanel();
				panel.add(panel_11);
				panel_11.setLayout(new GridLayout(2, 0, 0, 0));
				
				final JButton btnNextPdf = new JButton("");
				btnNextPdf.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						final int mode;
						if (rdbtnAllIn.isSelected())
							mode = WebPageGenerator.ALL_IN_CHARTS;
						else if(rdbtnSlaveAverage.isSelected())
							mode = WebPageGenerator.AVERAGE_CHARTS;
						else
							mode = WebPageGenerator.SINGLE_CHARTS;
						
						if(mode == WebPageGenerator.ALL_IN_CHARTS){
							String numToString = nextPrevPdf(controller, 1);	
							if(!new File(outputPath + File.separator + "R_allInCharts" + File.separator + "AllNodesPlot" + numToString + ".pdf").exists())
								log.severe("Last file reached!");
							else{
								controller.openDocument(outputPath + File.separator + "R_allInCharts" + File.separator + "AllNodesPlot" + numToString + ".pdf");
								controller.setZoom(new Float(0.9));
							}
						}
						else if(mode == WebPageGenerator.AVERAGE_CHARTS){
							String numToString = nextPrevPdf(controller, 1);
							if(!new File(outputPath + File.separator + "R_averageCharts" + File.separator + "AveragePlot" + numToString + ".pdf").exists())
								log.severe("Last file reached!");
							else{	
								controller.openDocument(outputPath + File.separator + "R_averageCharts" + File.separator + "AveragePlot" + numToString + ".pdf");	
								controller.setZoom(new Float(0.9));				
							}
						}
						else{
							String numToString = nextPrevPdf(controller, 1);	
							if(!new File(outputPath + File.separator + "R_singleCharts" + File.separator + "SinglePlot" + numToString + ".pdf").exists())
								log.severe("Last file reached!");
							else{
								controller.openDocument(outputPath + File.separator + "R_singleCharts" + File.separator + "SinglePlot" + numToString + ".pdf");
								controller.setZoom(new Float(0.9));	
							}
						}
					}

					/**
					 * This method generate next/prev pdf path.
					 * @param controller
					 * @param mode 0 to decrement; 1 to increment
					 * @return
					 */
					private String nextPrevPdf(final SwingController controller, int mode) {
						String actualPdfPath = controller.getDocument().getDocumentLocation();
						actualPdfPath = actualPdfPath.substring(actualPdfPath.length()-8, actualPdfPath.length()-4);
						int pdfNumber = Integer.parseInt(actualPdfPath);
						String numToString = "";
						if(mode == 1){
							pdfNumber++;
							numToString = pdfNumber + "";
							while(numToString.length() < 4)
								numToString = "0" + numToString;
						}
						else{
							pdfNumber--;
							if(pdfNumber <= 0)
								return actualPdfPath;
							numToString = pdfNumber + "";
							while(numToString.length() < 4)
								numToString = "0" + numToString;
						}
						return numToString;
					}
				});
				final JButton btnPrevPdf = new JButton("");
				btnPrevPdf.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						final int mode;
						if (rdbtnAllIn.isSelected())
							mode = WebPageGenerator.ALL_IN_CHARTS;
						else if(rdbtnSlaveAverage.isSelected())
							mode = WebPageGenerator.AVERAGE_CHARTS;
						else
							mode = WebPageGenerator.SINGLE_CHARTS;
						
						if(mode == WebPageGenerator.ALL_IN_CHARTS){
							String numToString = nextPrevPdf(controller, 0);							
							controller.openDocument(outputPath + File.separator + "R_allInCharts" + File.separator + "AllNodesPlot" + numToString + ".pdf");
							controller.setZoom(new Float(0.9));
						}
						else if(mode == WebPageGenerator.AVERAGE_CHARTS){
							String numToString = nextPrevPdf(controller, 0);	
							controller.openDocument(outputPath + File.separator + "R_averageCharts" + File.separator + "AveragePlot" + numToString + ".pdf");	
							controller.setZoom(new Float(0.9));														
						}
						else{
							String numToString = nextPrevPdf(controller, 0);	
							controller.openDocument(outputPath + File.separator + "R_singleCharts" + File.separator + "SinglePlot" + numToString + ".pdf");
							controller.setZoom(new Float(0.9));											
						}
					}

					/**
					 * This method generate next/prev pdf path.
					 * @param controller
					 * @param mode 0 to decrement; 1 to increment
					 * @return
					 */
					private String nextPrevPdf(final SwingController controller, int mode) {
						String actualPdfPath = controller.getDocument().getDocumentLocation();
						actualPdfPath = actualPdfPath.substring(actualPdfPath.length()-8, actualPdfPath.length()-4);
						int pdfNumber = Integer.parseInt(actualPdfPath);
						String numToString = "";
						if(mode == 1){
							pdfNumber++;
							numToString = pdfNumber + "";
							while(numToString.length() < 4)
								numToString = "0" + numToString;
						}
						else{
							pdfNumber--;
							if(pdfNumber <= 0)
								return actualPdfPath;
							numToString = pdfNumber + "";
							while(numToString.length() < 4)
								numToString = "0" + numToString;
						}
						return numToString;
					}
				});
				
				JPanel panel_18 = new JPanel();
				panel_11.add(panel_18);
				panel_18.setLayout(new GridLayout(2, 0, 0, 0));
				
				final JPanel panel_24 = new JPanel();
				panel_18.add(panel_24);
				panel_24.setLayout(new GridLayout(0, 4, 0, 0));
				
				JPanel panel_25 = new JPanel();
				panel_25.setBackground(Color.LIGHT_GRAY);
				panel_24.add(panel_25);
				panel_25.setLayout(new BorderLayout(0, 0));
				
				JLabel lblIo = new JLabel("Disk I/O");
				lblIo.setHorizontalAlignment(SwingConstants.CENTER);
				panel_25.add(lblIo, BorderLayout.NORTH);
				
				JPanel panel_34 = new JPanel();
				panel_25.add(panel_34, BorderLayout.CENTER);
				panel_34.setLayout(new GridLayout(2, 0, 0, 0));
				final JRadioButton rdbtnCombinedDisk = new JRadioButton("Combined");
				rdbtnCombinedDisk.setEnabled(false);
				rdbtnCombinedDisk.setSelected(true);
				panel_34.add(rdbtnCombinedDisk);
				
				final JRadioButton rdbtnSingleDisk = new JRadioButton("Single");
				rdbtnSingleDisk.setEnabled(false);
				panel_34.add(rdbtnSingleDisk);
				comSin.add(rdbtnCombinedDisk);
				comSin.add(rdbtnSingleDisk);
				
				JPanel panel_26 = new JPanel();
				panel_26.setBackground(Color.DARK_GRAY);
				panel_24.add(panel_26);
				panel_26.setLayout(new BorderLayout(0, 0));
				
				JPanel panel_35 = new JPanel();
				panel_26.add(panel_35, BorderLayout.CENTER);
				panel_35.setLayout(new GridLayout(2, 0, 0, 0));
				final JRadioButton rdbtnCombinedPaging = new JRadioButton("Combined");
				rdbtnCombinedPaging.setEnabled(false);
				rdbtnCombinedPaging.setSelected(true);
				panel_35.add(rdbtnCombinedPaging);
				
				final JRadioButton rdbtnSinglePaging = new JRadioButton("Single");
				rdbtnSinglePaging.setEnabled(false);
				panel_35.add(rdbtnSinglePaging);
				comSin_1.add(rdbtnCombinedPaging);
				comSin_1.add(rdbtnSinglePaging);
				
				JLabel lblPaging = new JLabel("Paging");
				lblPaging.setForeground(Color.LIGHT_GRAY);
				lblPaging.setHorizontalAlignment(SwingConstants.CENTER);
				panel_26.add(lblPaging, BorderLayout.NORTH);
				
				JPanel panel_27 = new JPanel();
				panel_27.setBackground(Color.LIGHT_GRAY);
				panel_24.add(panel_27);
				panel_27.setLayout(new BorderLayout(0, 0));
				
				JLabel lblNetworkPackets = new JLabel("Network Packets");
				lblNetworkPackets.setHorizontalAlignment(SwingConstants.CENTER);
				panel_27.add(lblNetworkPackets, BorderLayout.NORTH);
				
				JPanel panel_36 = new JPanel();
				panel_27.add(panel_36, BorderLayout.CENTER);
				panel_36.setLayout(new GridLayout(2, 0, 0, 0));
				final JRadioButton rdbtnCombinedNetworkPackets = new JRadioButton("Combined");
				rdbtnCombinedNetworkPackets.setEnabled(false);
				rdbtnCombinedNetworkPackets.setSelected(true);
				panel_36.add(rdbtnCombinedNetworkPackets);
				
				final JRadioButton rdbtnSingleNetworkPackets = new JRadioButton("Single");
				rdbtnSingleNetworkPackets.setEnabled(false);
				panel_36.add(rdbtnSingleNetworkPackets);
				comSin_2.add(rdbtnCombinedNetworkPackets);
				comSin_2.add(rdbtnSingleNetworkPackets);
				
				JPanel panel_28 = new JPanel();
				panel_28.setBackground(Color.DARK_GRAY);
				panel_24.add(panel_28);
				panel_28.setLayout(new BorderLayout(0, 0));
				
				JLabel lblNetworkThroughput = new JLabel("Network Throughput");
				lblNetworkThroughput.setForeground(Color.LIGHT_GRAY);
				lblNetworkThroughput.setHorizontalAlignment(SwingConstants.CENTER);
				panel_28.add(lblNetworkThroughput, BorderLayout.NORTH);
				
				JPanel panel_37 = new JPanel();
				panel_28.add(panel_37, BorderLayout.CENTER);
				panel_37.setLayout(new GridLayout(2, 0, 0, 0));
				final JRadioButton rdbtnCombinedNetworkThroughput = new JRadioButton("Combined");
				rdbtnCombinedNetworkThroughput.setEnabled(false);
				rdbtnCombinedNetworkThroughput.setSelected(true);
				panel_37.add(rdbtnCombinedNetworkThroughput);
				
				final JRadioButton rdbtnSingleNetworkThroughput = new JRadioButton("Single");
				rdbtnSingleNetworkThroughput.setEnabled(false);
				panel_37.add(rdbtnSingleNetworkThroughput);
				comSin_3.add(rdbtnCombinedNetworkThroughput);
				comSin_3.add(rdbtnSingleNetworkThroughput);
				
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.DARK_GRAY);
				panel_18.add(panel_1);
				panel_1.setLayout(new BorderLayout(0, 0));
				
				JLabel lblWarningCharts = new JLabel("Warning charts");
				lblWarningCharts.setBackground(Color.DARK_GRAY);
				lblWarningCharts.setForeground(Color.WHITE);
				lblWarningCharts.setHorizontalAlignment(SwingConstants.CENTER);
				panel_1.add(lblWarningCharts, BorderLayout.NORTH);
				
				JPanel panel_5 = new JPanel();
				panel_1.add(panel_5, BorderLayout.CENTER);
				panel_5.setLayout(new GridLayout(0, 2, 0, 0));
				
				JPanel panel_40 = new JPanel();
				panel_40.setBackground(new Color(0, 100, 0));
				panel_5.add(panel_40);
				panel_40.setLayout(new BorderLayout(0, 0));
				
				JPanel panel_22 = new JPanel();
				panel_40.add(panel_22, BorderLayout.CENTER);
				panel_22.setLayout(new GridLayout(0, 3, 0, 0));
				
				JPanel panel_30 = new JPanel();
				panel_22.add(panel_30);
				
				final JSlider slider_CPU_Positive = new JSlider();
				slider_CPU_Positive.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						log.info("New cpu warning value = " + slider_CPU_Positive.getValue());
					}
				});
				//slider.addChangeListener(this);
				slider_CPU_Positive.setMajorTickSpacing(10);
				slider_CPU_Positive.setPaintTicks(true);
				slider_CPU_Positive.setPaintLabels(true);
				slider_CPU_Positive.setLabelTable( labelTable );
				panel_30.setLayout(new BorderLayout(0, 0));
				
				panel_30.add(slider_CPU_Positive);
				
				JLabel label_CPU_Positive = new JLabel("CPU %");
				label_CPU_Positive.setHorizontalAlignment(SwingConstants.CENTER);
				panel_30.add(label_CPU_Positive, BorderLayout.NORTH);
				
				JPanel panel_32 = new JPanel();
				panel_22.add(panel_32);
				panel_32.setLayout(new BorderLayout(0, 0));
				
				JLabel lblDiskIo_Positive = new JLabel("Disk I/O MB");
				lblDiskIo_Positive.setHorizontalAlignment(SwingConstants.CENTER);
				panel_32.add(lblDiskIo_Positive, BorderLayout.NORTH);
				
				final JSlider slider_Disk_Positive = new JSlider();
				slider_Disk_Positive.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						log.info("New Disk value: " + slider_Disk_Positive.getValue()*40/100);
					}
				});
				//slider.addChangeListener(this);
				slider_Disk_Positive.setMajorTickSpacing(10);
				slider_Disk_Positive.setPaintTicks(true);
				slider_Disk_Positive.setLabelTable( labelTable_1 );
				slider_Disk_Positive.setPaintLabels(true);
				panel_32.add(slider_Disk_Positive, BorderLayout.CENTER);

				final JRadioButton rdbtnR = new JRadioButton("R");
				
				JPanel panel_38 = new JPanel();
				panel_22.add(panel_38);
				panel_38.setLayout(new BorderLayout(0, 0));
				
				JLabel lblRamMb = new JLabel("RAM GB");
				lblRamMb.setHorizontalAlignment(SwingConstants.CENTER);
				final JSlider slider_RAM_Positive = new JSlider();
				slider_RAM_Positive.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						log.info("New ram warning value = " + ((0.00 + slider_RAM_Positive.getValue())*32/100));
					}
				});
				//slider.addChangeListener(this);
				slider_RAM_Positive.setMajorTickSpacing(10);
				slider_RAM_Positive.setPaintTicks(true);
				slider_RAM_Positive.setLabelTable( labelTable_2 );
				slider_RAM_Positive.setPaintLabels(true);
				panel_38.add(slider_RAM_Positive, BorderLayout.CENTER);
				panel_38.add(lblRamMb, BorderLayout.NORTH);
				
				JLabel lblPositiveWarning = new JLabel("Positive warning");
				lblPositiveWarning.setForeground(new Color(152, 251, 152));
				lblPositiveWarning.setHorizontalAlignment(SwingConstants.CENTER);
				panel_40.add(lblPositiveWarning, BorderLayout.NORTH);
				
				JPanel panel_29 = new JPanel();
				panel_29.setBackground(new Color(139, 0, 0));
				panel_5.add(panel_29);
				panel_29.setLayout(new BorderLayout(0, 0));
				
				JPanel panel_41 = new JPanel();
				panel_29.add(panel_41, BorderLayout.CENTER);
				panel_41.setLayout(new GridLayout(0, 3, 0, 0));
				
				JPanel panel_42 = new JPanel();
				panel_41.add(panel_42);
				panel_42.setLayout(new BorderLayout(0, 0));
				
				final JSlider slider_SWAP_Negative = new JSlider();
				slider_SWAP_Negative.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						log.info("New Swap warning value = " + (0.00 + slider_SWAP_Negative.getValue())*32/100);
					}
				});
				slider_SWAP_Negative.setPaintTicks(true);
				slider_SWAP_Negative.setPaintLabels(true);
				slider_SWAP_Negative.setMajorTickSpacing(10);
				panel_42.add(slider_SWAP_Negative, BorderLayout.CENTER);
				
				JLabel lblSwapGb = new JLabel("SWAP GB");
				//slider.addChangeListener(this);
				slider_SWAP_Negative.setMajorTickSpacing(10);
				slider_SWAP_Negative.setPaintTicks(true);
				slider_SWAP_Negative.setLabelTable( labelTable_2 );
				slider_SWAP_Negative.setPaintLabels(true);
				lblSwapGb.setHorizontalAlignment(SwingConstants.CENTER);
				panel_42.add(lblSwapGb, BorderLayout.NORTH);
				

				final JCheckBox chckbxShowYLabel = new JCheckBox("Show y label");
				ButtonGroup logHad_time = new ButtonGroup();
				final JRadioButton radioButtonHour_LogHdadoop = new JRadioButton("Hour");
				final JRadioButton rdbtnMin_LogHadoop = new JRadioButton("Min");
				final JRadioButton rdbtnSec_LogHadoop = new JRadioButton("Sec");
				logHad_time.add(radioButtonHour_LogHdadoop);
				logHad_time.add(rdbtnSec_LogHadoop);
				logHad_time.add(rdbtnMin_LogHadoop);
				
				JPanel panel_43 = new JPanel();
				panel_41.add(panel_43);
				panel_43.setLayout(new BorderLayout(0, 0));
				
				final JSlider slider_NETWORK_Negative = new JSlider();
				slider_NETWORK_Negative.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						log.info("New Network warning value = " + (0.00 + slider_NETWORK_Negative.getValue())*32/100);
					}
				});
				slider_NETWORK_Negative.setPaintTicks(true);
				slider_NETWORK_Negative.setPaintLabels(true);
				slider_NETWORK_Negative.setMajorTickSpacing(10);
				//slider.addChangeListener(this);
				slider_SWAP_Negative.setMajorTickSpacing(10);
				slider_SWAP_Negative.setPaintTicks(true);
				slider_NETWORK_Negative.setLabelTable( labelTable_2 );
				slider_NETWORK_Negative.setPaintLabels(true);
				
						panel_43.add(slider_NETWORK_Negative, BorderLayout.CENTER);
						
						JLabel lblNetworkGb = new JLabel("NETWORK GB");
						lblNetworkGb.setHorizontalAlignment(SwingConstants.CENTER);
						panel_43.add(lblNetworkGb, BorderLayout.NORTH);
						
						JPanel panel_44 = new JPanel();
						panel_41.add(panel_44);
						panel_44.setLayout(new BorderLayout(0, 0));
						
						final JSlider slider_RAM_Negative = new JSlider();
						slider_RAM_Negative.addChangeListener(new ChangeListener() {
							public void stateChanged(ChangeEvent arg0) {
								log.info("New Ram warning value = " + (0.00 + slider_RAM_Negative.getValue())*32/100);
							}
						});
						slider_RAM_Negative.setPaintTicks(true);
						slider_RAM_Negative.setPaintLabels(true);
						slider_RAM_Negative.setMajorTickSpacing(10);
						
								//slider.addChangeListener(this);
								slider_RAM_Negative.setMajorTickSpacing(10);
								slider_RAM_Negative.setPaintTicks(true);
								slider_RAM_Negative.setLabelTable( labelTable_2 );
								slider_RAM_Negative.setPaintLabels(true);
								panel_44.add(slider_RAM_Negative, BorderLayout.CENTER);
								
								JLabel label_5 = new JLabel("RAM GB");
								label_5.setHorizontalAlignment(SwingConstants.CENTER);
								panel_44.add(label_5, BorderLayout.NORTH);
								
								JLabel lblNegativeWarning = new JLabel("Negative warning");
								lblNegativeWarning.setBackground(new Color(238, 232, 170));
								lblNegativeWarning.setHorizontalAlignment(SwingConstants.CENTER);
								lblNegativeWarning.setForeground(new Color(250, 235, 215));
								panel_29.add(lblNegativeWarning, BorderLayout.NORTH);
								
								JPanel panel_100 = new JPanel();
								final JScrollPane scrollPane = new JScrollPane();
								panel_11.add(panel_100);
								panel_100.setLayout(new BorderLayout(0, 0));
								panel_100.add(scrollPane);
								scrollPane.setVisible(true);
								
								textArea.setEditable(false);
								textArea.setBackground(Color.BLACK);
								textArea.setForeground(Color.GREEN);
								DefaultCaret caret = (DefaultCaret)textArea.getCaret();
								caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
								scrollPane.setViewportView(textArea);
								
								JPanel panel_31 = new JPanel();
								panel_100.add(panel_31, BorderLayout.NORTH);
								panel_31.setLayout(new GridLayout(0, 8, 0, 0));
								
								JLabel label_2 = new JLabel("");
								panel_31.add(label_2);
								
								JLabel label_3 = new JLabel("");
								panel_31.add(label_3);
								
								JLabel label_7 = new JLabel("");
								panel_31.add(label_7);
								
								JLabel label_11 = new JLabel("");
								panel_31.add(label_11);
								
								JLabel label_8 = new JLabel("");
								panel_31.add(label_8);
								
								JLabel label_10 = new JLabel("");
								panel_31.add(label_10);
								
								JLabel label_9 = new JLabel("");
								panel_31.add(label_9);
								
								final JButton btnClear = new JButton("Clear");
								btnClear.setIcon(new ImageIcon(Home.class.getResource("/gui/clearIco.png")));
								panel_31.add(btnClear);
								btnClear.setVisible(true);
								btnClear.addActionListener(new ActionListener() {			
									public void actionPerformed(ActionEvent arg0) {
										textArea.setText("");				
									}
								});
								
								rdbtnAllIn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if(rdbtnAllIn.isSelected()){
											chckbxGreyScale.setEnabled(false);
											
											rdbtnCombinedDisk.setEnabled(false);
											rdbtnSingleDisk.setEnabled(false);
											
											rdbtnCombinedPaging.setEnabled(false);
											rdbtnSinglePaging.setEnabled(false);
	
											rdbtnCombinedNetworkPackets.setEnabled(false);
											rdbtnSingleNetworkThroughput.setEnabled(false);
	
											rdbtnCombinedNetworkThroughput.setEnabled(false);
											rdbtnSingleNetworkPackets.setEnabled(false);
											
											if(!rdbtnR.isSelected()){
												rdbtnSingleFile.setEnabled(false);
												rdbtnMultifiles.setEnabled(false);
											}
										}
									}
								});
								

								rdbtnSlaveAverage.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										chckbxGreyScale.setEnabled(true);	
										
										rdbtnCombinedDisk.setEnabled(false);
										rdbtnSingleDisk.setEnabled(false);
										
										rdbtnCombinedPaging.setEnabled(false);
										rdbtnSinglePaging.setEnabled(false);

										rdbtnCombinedNetworkPackets.setEnabled(false);
										rdbtnSingleNetworkThroughput.setEnabled(false);

										rdbtnCombinedNetworkThroughput.setEnabled(false);
										rdbtnSingleNetworkPackets.setEnabled(false);
										
										if(!rdbtnR.isSelected()){
											rdbtnSingleFile.setEnabled(false);
											rdbtnMultifiles.setEnabled(false);
										}
									}
								});
								
										final JRadioButton rdbtnGooglejfreechart = new JRadioButton("Google/JFreeChart");
										rdbtnGooglejfreechart.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												
											}
										});
										rdbtnGooglejfreechart.setSelected(true);
										rdbtnGooglejfreechart.setFont(new Font("Tahoma", Font.PLAIN, 8));
										
										rdbtnSlavesSingles.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												chckbxGreyScale.setEnabled(true);
												
												if(!rdbtnGooglejfreechart.isSelected()){
													rdbtnCombinedDisk.setEnabled(true);
													rdbtnSingleDisk.setEnabled(true);
													
													rdbtnCombinedPaging.setEnabled(true);
													rdbtnSinglePaging.setEnabled(true);
	
													rdbtnCombinedNetworkPackets.setEnabled(true);
													rdbtnSingleNetworkThroughput.setEnabled(true);
	
													rdbtnCombinedNetworkThroughput.setEnabled(true);
													rdbtnSingleNetworkPackets.setEnabled(true);
													
													rdbtnSingleFile.setEnabled(true);
													rdbtnMultifiles.setEnabled(true);
												}
											}
										});
										
										btnGenerate = new Button("Generate");
										btnGenerate.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												scrollPane.setVisible(true);
												btnClear.setVisible(true);
												if(myConsole == null){
													myConsole = new LogHandler(textArea);
													log.addHandler(myConsole);
												}
												log.info("Start...");
												final WebPageGenerator myHadoop = new WebPageGenerator();
												//creating cpu column desired usr,sys,idl,wai,hiq,siq
												final ArrayList<String> cpuColumnDesired = new ArrayList<String>();
												if(chckbxSys.isSelected())
													cpuColumnDesired.add("sys");
												if(chckbxUsr.isSelected())
													cpuColumnDesired.add("usr");
												if(chckbxIdl.isSelected())
													cpuColumnDesired.add("idl");
												if(chckbxWai.isSelected())
													cpuColumnDesired.add("wai");
												if(chckbxHiq.isSelected())
													cpuColumnDesired.add("hiq");
												if(chckbxSiq.isSelected())
													cpuColumnDesired.add("siq");
												//creating ram column desired used,buff,cach,free
												final ArrayList<String> ramColumnDesired = new ArrayList<String>();
												if(chckbxUsed.isSelected())
													ramColumnDesired.add("used");
												if(chckbxBuff.isSelected())
													ramColumnDesired.add("buff");
												if(chckbxCach.isSelected())
													ramColumnDesired.add("cach");
												if(chckbxFree.isSelected())
													ramColumnDesired.add("free");
												//check scaleIn
												final int scaleIn;
												if(rdbtnHour.isSelected())
													scaleIn = WebPageGenerator.HOUR;
												else if(rdbtnMin.isSelected())
													scaleIn = WebPageGenerator.MINUTE;
												else
													scaleIn = WebPageGenerator.SECOND;
												//check style
												final boolean isGreyScale = chckbxGreyScale.isSelected();
												final boolean isDashedLine = chckbxDashedLine.isSelected();
												final boolean showTitle = chckbxTitles.isSelected();
												//check charts mode
												final int mode;
												if (rdbtnAllIn.isSelected())
													mode = WebPageGenerator.ALL_IN_CHARTS;
												else if(rdbtnSlaveAverage.isSelected())
													mode = WebPageGenerator.AVERAGE_CHARTS;
												else
													mode = WebPageGenerator.SINGLE_CHARTS;
												//run
												if(rdbtnGooglejfreechart.isSelected())
													runWithGoogle_JFreeChart(myHadoop, cpuColumnDesired,ramColumnDesired, scaleIn, isGreyScale, isDashedLine,showTitle, mode);
												else{
													runWithR(myHadoop, cpuColumnDesired,ramColumnDesired, scaleIn, isGreyScale, isDashedLine,showTitle, mode);
												}
											}

											private void runWithR(WebPageGenerator myHadoop, final ArrayList<String> cpuColumnDesired, final ArrayList<String> ramColumnDesired, final int scaleIn, final boolean isGreyScale, final boolean isDashedLine, final boolean showTitle, final int mode) {
												new Thread(new Runnable() {													
													public void run() {
														// TODO Auto-generated method stub
														String R_input_file = "";
														if (scaleIn == WebPageGenerator.HOUR)
															R_input_file += "h\n";
														else if(scaleIn == WebPageGenerator.MINUTE)
															R_input_file += "m\n";
														else
															R_input_file += "s\n";
														if(isGreyScale)
															R_input_file += "0\n";
														else
															R_input_file += "1\n";
														if(isDashedLine)
															R_input_file += "TRUE\n";
														else
															R_input_file += "FALSE\n";
														if(showTitle)
															R_input_file += "TRUE\n";
														else
															R_input_file += "FALSE\n";
														if(rdbtnSingleFile.isSelected())
															R_input_file += "TRUE\n";
														else
															R_input_file += "FALSE\n";
														if(mode == WebPageGenerator.SINGLE_CHARTS)
															R_input_file += "1\n";
														else if(mode == WebPageGenerator.AVERAGE_CHARTS)
															R_input_file += "2\n";
														else
															R_input_file += "3\n";
														//CPU
														if(cpuColumnDesired.size() == 0)
															R_input_file += "NULL\n";
														else{
															for(String cpu_filed:cpuColumnDesired)
																R_input_file += "dstat$" + cpu_filed + " ";
															R_input_file += "\n";
														}
														//RAM
														if(ramColumnDesired.size() == 0)
															R_input_file += "NULL\n";					
														else{
															for(String ram_field:ramColumnDesired)
																R_input_file += "dstat$" + ram_field + "/1048576 ";		
															R_input_file += "\n";
														}
														if(!spinner_ram.isEnabled())
															R_input_file += "NULL\n";
														else
															R_input_file += spinner_ram.getModel().getValue().toString() + "\n";
														if(rdbtnCombinedDisk.isSelected())
															R_input_file += "2\n";
														else
															R_input_file += "1\n";
														if(rdbtnCombinedPaging.isSelected())
															R_input_file += "2\n";
														else
															R_input_file += "1\n";
														if(rdbtnCombinedNetworkPackets.isSelected())
															R_input_file += "2\n";
														else
															R_input_file += "1\n";
														if(rdbtnCombinedNetworkThroughput.isSelected())
															R_input_file += "2\n";
														else
															R_input_file += "1\n";
														R_input_file += spinner_font_size.getModel().getValue().toString() + "\n";
														//input path
														if(inputPath == null || inputPath == ""){
															log.severe(NO_INPUT_OUTPUT);
															return;
														}
														R_input_file += inputPath + File.separator + "\n";
														//output path
														if(mode == WebPageGenerator.ALL_IN_CHARTS){
															new File(outputPath + File.separator + "R_allInCharts").mkdirs();
															R_input_file += outputPath + File.separator + "R_allInCharts" + File.separator + "\n";													
														}
														else if(mode == WebPageGenerator.AVERAGE_CHARTS){
															new File(outputPath + File.separator + "R_averageCharts").mkdirs();
															R_input_file += outputPath + File.separator + "R_averageCharts" + File.separator + "\n";													
														}
														else{
															new File(outputPath + File.separator + "R_singleCharts").mkdirs();
															R_input_file += outputPath + File.separator + "R_singleCharts" + File.separator + "\n";													
														}
														//R input file generation
														String absoluteCurrentPath = System.getProperty("user.dir");				
														String resourcePath = absoluteCurrentPath + File.separator + "R_script";
														String rScriptPath = "\"" + resourcePath + File.separator + "chart_generator.R" + "\"";
														String outputFilePath = resourcePath + File.separator + "input.hla" ;
														PrintWriter writer;
														try {
															writer = new PrintWriter(outputFilePath, "UTF-8");
															writer.print(R_input_file);
															writer.close();

															log.info("Running R with: " + R_input_file.replace("\n", " "));
														
															String mes = "";
															if(OSValidator.isMac()){																
																String[] command = {"Rscript",rScriptPath.replaceAll("\"", "")};
																mes = executeCommand(command);	
															}
															else
																mes = executeCommand("Rscript " + rScriptPath);		
															log.info(mes);
															//open pdf in viewer
															if(mode == WebPageGenerator.ALL_IN_CHARTS){
																if(rdbtnMultifiles.isSelected())
																	controller.openDocument(outputPath + File.separator + "R_allInCharts" + File.separator + "AllNodesPlot0001.pdf");
																else
																	controller.openDocument(outputPath + File.separator + "R_allInCharts" + File.separator + "AllNodesPlot.pdf");
																controller.setZoom(new Float(0.9));
															}
															else if(mode == WebPageGenerator.AVERAGE_CHARTS){
																if(rdbtnMultifiles.isSelected())
																	controller.openDocument(outputPath + File.separator + "R_averageCharts" + File.separator + "AveragePlot0001.pdf");	
																else
																	controller.openDocument(outputPath + File.separator + "R_averageCharts" + File.separator + "AveragePlot.pdf");	
																controller.setZoom(new Float(0.9));														
															}
															else{
																if(rdbtnMultifiles.isSelected())
																	controller.openDocument(outputPath + File.separator + "R_singleCharts" + File.separator + "SinglePlot0001.pdf");															
																else
																	controller.openDocument(outputPath + File.separator + "R_singleCharts" + File.separator + "SinglePlot.pdf");
																controller.setZoom(new Float(0.9));											
															}
															if(rdbtnMultifiles.isSelected()){
																btnPrevPdf.setEnabled(true);
																btnNextPdf.setEnabled(true);
															}
															else{
																btnPrevPdf.setEnabled(false);
																btnNextPdf.setEnabled(false);
															}
														} catch (FileNotFoundException e) {
															log.severe("Exception creating R input file: " + e.getMessage());
															e.printStackTrace();
														} catch (UnsupportedEncodingException e) {
															log.severe("Exception creating R input file: " + e.getMessage());
															e.printStackTrace();
														}			
													}
												}).start();																		
											}

											private void runWithGoogle_JFreeChart(
													final WebPageGenerator myHadoop,
													final ArrayList<String> cpuColumnDesired,
													final ArrayList<String> ramColumnDesired,
													final int scaleIn, final boolean isGreyScale,
													final boolean isDashedLine, final boolean showTitle,
													final int mode) {
												new Thread(new Runnable() {					
													public void run() {
														if(masterPath == null || masterPath == "" ||
																slave_sPath == null || outputPath == null || outputPath == ""){
															log.severe(NO_INPUT_OUTPUT);
															return;
														}
														setWarningValue();
														myHadoop.generateWebPage(outputPath, masterPath, slave_sPath, profilerPath, cpuColumnDesired, ramColumnDesired, scaleIn, isGreyScale, isDashedLine, showTitle, mode);
														String htmlFilePath = outputPath + File.separator + "index.html";
														File htmlFile = new File(htmlFilePath);
														try {
															Desktop.getDesktop().browse(htmlFile.toURI());
														} catch (IOException e) {
															log.severe("Exception opening browser: " + e.getMessage());
															e.printStackTrace();
														}
														log.info("Operation ended");
													}

													private void setWarningValue() {
														//positive warning value
														Chart_sCollector.setCPU_POSITIVE_LIMIT(slider_CPU_Positive.getValue());
														Chart_sCollector.setIO_POSITIVE_LIMIT(slider_Disk_Positive.getValue()*40/100);
														Chart_sCollector.setRAM_POSITIVE_LIMIT((0.00 + slider_RAM_Positive.getValue())*32/100);
														//negative warning value
														Chart_sCollector.setNETWORK_NEGATIVE_LIMIT((0.00 + slider_NETWORK_Negative.getValue())*32/100);
														Chart_sCollector.setRAM_NEGATIVE_LIMIT((0.00 + slider_RAM_Negative.getValue())*32/100);
														Chart_sCollector.setSWAP_NEGATIVE_LIMIT((0.00 + slider_SWAP_Negative.getValue())*32/100);
													}
												}).start();
											}
											
											public String executeCommand(String command){
												StringBuffer output = new StringBuffer();
												 
												Process p;
												try {
													log.info("Running R with command: " + command);
													if(OSValidator.isMac())
														p = Runtime.getRuntime().exec(command.split(" "));
													else
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
										
										JPanel panel_4 = new JPanel();
										panel_3.add(panel_4);
										panel_4.setLayout(new GridLayout(1, 2, 0, 0));
										panel_4.add(rdbtnGooglejfreechart);
										
										
										groupLibrary.add(rdbtnGooglejfreechart);
										groupLibrary.add(rdbtnR);
										panel_4.add(rdbtnR);
										
												rdbtnGooglejfreechart.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														//enable warning components
														lblwarning.setForeground(Color.BLACK);
														slider_CPU_Positive.setEnabled(true);
														slider_Disk_Positive.setEnabled(true);
														slider_NETWORK_Negative.setEnabled(true);
														slider_RAM_Negative.setEnabled(true);
														slider_RAM_Positive.setEnabled(true);
														slider_SWAP_Negative.setEnabled(true);
														//disable ram upper bound
														checkBoxUpperBound.setEnabled(false);
														spinner_ram.setEnabled(false);
														
														rdbtnCombinedDisk.setSelected(true);
														rdbtnCombinedDisk.setEnabled(false);
														rdbtnSingleDisk.setEnabled(false);
														
														rdbtnCombinedPaging.setSelected(true);
														rdbtnCombinedPaging.setEnabled(false);
														rdbtnSinglePaging.setEnabled(false);
										
														rdbtnCombinedNetworkPackets.setSelected(true);
														rdbtnCombinedNetworkPackets.setEnabled(false);
														rdbtnSingleNetworkThroughput.setEnabled(false);
										
														rdbtnCombinedNetworkThroughput.setSelected(true);
														rdbtnCombinedNetworkThroughput.setEnabled(false);
														rdbtnSingleNetworkPackets.setEnabled(false);
														
														rdbtnSingleFile.setEnabled(false);
														rdbtnMultifiles.setEnabled(false);
														
														chckbxBuff.setSelected(true);
														chckbxUsed.setSelected(true);
														chckbxSys.setSelected(true);
														chckbxUsr.setSelected(true);
													}
												});
												
												rdbtnR.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														//disable warning components
														lblwarning.setForeground(Color.GRAY);
														slider_CPU_Positive.setEnabled(false);
														slider_Disk_Positive.setEnabled(false);
														slider_NETWORK_Negative.setEnabled(false);
														slider_RAM_Negative.setEnabled(false);
														slider_RAM_Positive.setEnabled(false);
														slider_SWAP_Negative.setEnabled(false);
														rdbtnSingleFile.setEnabled(true);
														rdbtnMultifiles.setEnabled(true);
														//enable ram upper bound
														checkBoxUpperBound.setEnabled(true);
														if(checkBoxUpperBound.isSelected())
															spinner_ram.setEnabled(true);
														
														if(rdbtnSlavesSingles.isSelected()){
															rdbtnCombinedDisk.setEnabled(true);
															rdbtnSingleDisk.setEnabled(true);
															
															rdbtnCombinedPaging.setEnabled(true);
															rdbtnSinglePaging.setEnabled(true);
	
															rdbtnCombinedNetworkPackets.setEnabled(true);
															rdbtnSingleNetworkThroughput.setEnabled(true);
	
															rdbtnCombinedNetworkThroughput.setEnabled(true);
															rdbtnSingleNetworkPackets.setEnabled(true);
															
															rdbtnSingleFile.setEnabled(true);
															rdbtnMultifiles.setEnabled(true);
															
															chckbxBuff.setSelected(false);
															chckbxCach.setSelected(false);
															chckbxFree.setSelected(false);
															chckbxHiq.setSelected(false);
															chckbxIdl.setSelected(false);
															chckbxUsed.setSelected(false);
															chckbxWai.setSelected(false);
															chckbxSys.setSelected(false);
															chckbxUsr.setSelected(false);
															chckbxSiq.setSelected(false);
														}
													}
												});
												
												JPanel panel_52 = new JPanel();
												panel_3.add(panel_52);
												panel_52.setLayout(new GridLayout(0, 2, 0, 0));
												
												JLabel label_16 = new JLabel("");
												panel_52.add(label_16);
												
												JPanel panel_53 = new JPanel();
												panel_52.add(panel_53);
												
												JButton btnGenerateExcel = new JButton("Excel");
												btnGenerateExcel.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {	
														if(inputPath == null || inputPath.equals("null") ||
																outputPath == null || outputPath.equals("null")){
															log.severe(NO_INPUT_OUTPUT);
														}
														else{
															new Thread(new Runnable() {
																public void run() {
																	try {
																	String absoluteCurrentPath = System.getProperty("user.dir");				
																	String resourcePath = absoluteCurrentPath + File.separator + "R_script";
																	String rScriptPath = "\"" + resourcePath + File.separator + "excel.R" + "\"";
																	
																	PrintWriter writer = new PrintWriter(resourcePath + File.separator +"inputExcel.hla", "UTF-8");
																	writer.println(inputPath + File.separator);
																	writer.println(outputPath + File.separator);
																	writer.close();
																	
																	String log_message = "";
																	if(OSValidator.isMac()){																
																		String[] command = {"Rscript",rScriptPath.replaceAll("\"", "")};
																		log_message = executeCommand(command);	
																	}
																	else
																		log_message = executeCommand("Rscript " + rScriptPath);
																	log.info(log_message);
																	
																	//open generated file
																	try {
																		Desktop dt = Desktop.getDesktop();
																		dt.open(new File(outputPath + File.separator + "excelDstat.xlsx"));
																		log.info("Opening excel generated file...");
																	} catch (IOException e) {
																		log.severe("Exception opening excel generated file: " + e.getMessage());
																		e.printStackTrace();
																	}
																} catch (FileNotFoundException e) {
																	log.severe("Exception generating R input file: " + e.getMessage());
																	e.printStackTrace();
																} catch (UnsupportedEncodingException e) {
																	log.severe("Exception generating R input file: " + e.getMessage());
																	e.printStackTrace();
																}
																}
															}).start();
														}
													}
														
													public String executeCommand(String command){
														StringBuffer output = new StringBuffer();
														 
														Process p;
														try {
															log.info("Executing shell command: " + command);
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
												panel_53.add(btnGenerateExcel);
												btnGenerateExcel.setIcon(new ImageIcon(Home.class.getResource("/gui/excel-icon.png")));
												btnGenerateExcel.setFont(new Font("Tahoma", Font.PLAIN, 10));
												panel_3.add(btnGenerate);
												
												JLabel lblNewLabel_1 = new JLabel("DSTAT");
												lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
												lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
												panel_2.add(lblNewLabel_1, BorderLayout.NORTH);
		
		JPanel panel_45 = new JPanel();
		panel_46.add(panel_45);
		panel_45.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_47 = new JPanel();
		panel_47.setBackground(Color.LIGHT_GRAY);
		panel_45.add(panel_47, BorderLayout.NORTH);
		panel_47.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_49 = new JPanel();
		panel_47.add(panel_49, BorderLayout.CENTER);
		panel_49.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel label_13 = new JLabel("Input:");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_49.add(label_13);
		
		final JLabel labelInputHadoopLog = new JLabel("...");
		labelInputHadoopLog.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				labelInputHadoopLog.setToolTipText(labelInputHadoopLog.getText());
			}
		});
		panel_49.add(labelInputHadoopLog);
		
		JPanel panel_50 = new JPanel();
		panel_49.add(panel_50);
		
		Button buttonHadoopLogBrowse = new Button("Browse...");
		buttonHadoopLogBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				fileChooser.showOpenDialog(null);
				File f = fileChooser.getSelectedFile();
				if (f != null && f.getAbsolutePath() != null){
					labelInputHadoopLog.setText(f.getAbsolutePath());
					logHadoopDirPath = f.getAbsolutePath();
				}
					
			}			
		});
		panel_50.add(buttonHadoopLogBrowse);
		
		JLabel label_4 = new JLabel("Ouput:");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_49.add(label_4);
		
		final JLabel labelHadoopLogOutput = new JLabel("...");
		labelHadoopLogOutput.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				labelHadoopLogOutput.setToolTipText(labelHadoopLogOutput.getText());
			}
		});
		panel_49.add(labelHadoopLogOutput);
		
		JPanel panel_51 = new JPanel();
		panel_49.add(panel_51);
		
		Button buttonBrowseOutput = new Button("Browse...");
		buttonBrowseOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				fileChooser.showOpenDialog(null);
				File f = fileChooser.getSelectedFile();
				if (f != null && f.getAbsolutePath() != null){
					logHadoopOutputPath = f.getAbsolutePath();
					labelHadoopLogOutput.setText(f.getAbsolutePath());
				}
			}
		});
		panel_51.add(buttonBrowseOutput);
		
		Button buttonGenerateHadoopLog = new Button("Generate");
		buttonGenerateHadoopLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnPrevPdf.setEnabled(false);
				btnNextPdf.setEnabled(false);
				if(myConsole == null){
					myConsole = new LogHandler(textArea);
					log.addHandler(myConsole);
				}
				log.info("Start...");
				if(labelInputHadoopLog.getText().equals("...") ||
						labelHadoopLogOutput.getText().equals("..."))
					log.info("Select input path of html log!");				
				else{
					//R input file generation
					String absoluteCurrentPath = System.getProperty("user.dir");				
					String resourcePath = absoluteCurrentPath + File.separator + "R_script";
					String rScriptPath = "\"" + resourcePath + File.separator + "graphHadoopGenerator.R" + "\"";
					
					String log_message;
					String showYLab = "FALSE";
					String m_h_s = "m";
					if(chckbxShowYLabel.isSelected())
						showYLab = "TRUE";
					if(rdbtnSec_LogHadoop.isSelected())
						m_h_s = "s";
					if(radioButtonHour_LogHdadoop.isSelected())
						m_h_s = "h";
					if(OSValidator.isMac()){						
						String[] command = {"Rscript",rScriptPath.replaceAll("\"", ""),labelInputHadoopLog.getText()+File.separator,labelHadoopLogOutput.getText() + File.separator, m_h_s, showYLab};
						log_message = executeCommand(command);	
					}
					else
						log_message = executeCommand("Rscript " + rScriptPath + " " + labelInputHadoopLog.getText() + File.separator + " " + labelHadoopLogOutput.getText() + File.separator + " " + m_h_s + " " + showYLab);
					log.info(log_message);
					controller.openDocument(labelHadoopLogOutput.getText() + File.separator + "hadoopGraph.pdf");
					controller.setZoom(new Float(0.90));
				}
			}
			
			public String executeCommand(String command){
				StringBuffer output = new StringBuffer();
				 
				Process p;
				try {
					log.info("Executing shell command: " + command);
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
		
		JPanel panel_58 = new JPanel();
		panel_49.add(panel_58);
		panel_58.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label_14 = new JLabel("");
		panel_58.add(label_14);
		
		panel_58.add(chckbxShowYLabel);
		
		JPanel panel_57 = new JPanel();
		panel_49.add(panel_57);
		panel_57.setLayout(new GridLayout(0, 3, 0, 0));
		
		panel_57.add(radioButtonHour_LogHdadoop);
		
		rdbtnMin_LogHadoop.setSelected(true);
		panel_57.add(rdbtnMin_LogHadoop);
		
		panel_57.add(rdbtnSec_LogHadoop);
		panel_49.add(buttonGenerateHadoopLog);
		
		JLabel lblHadoopLog = new JLabel("Hadoop Log");
		lblHadoopLog.setHorizontalAlignment(SwingConstants.CENTER);
		lblHadoopLog.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_47.add(lblHadoopLog, BorderLayout.NORTH);
		
		
		panel_45.add(panel_pdf, BorderLayout.CENTER);
		
		
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Salva"); 	
				FileFilter filter = new FileNameExtensionFilter("hlaConf file", new String[] {"hlaConf"});
				fileChooser.setFileFilter(filter);
				int userSelection = fileChooser.showSaveDialog(frmHadoopChartTool);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    if(!fileToSave.getAbsolutePath().contains(".hlaConf"))
				    	confFilePath = fileToSave.getAbsolutePath() + ".hlaConf";
				    else
				    	confFilePath = fileToSave.getAbsolutePath();
				    	
				    log.info("Saving file: " + confFilePath);
				    try {
						PrintWriter writer = new PrintWriter(confFilePath, "UTF-8");
						writer.println(inputPath);	
						writer.println(outputPath);
						writer.println(rdbtnGooglejfreechart.isSelected());
						writer.println(logHadoopDirPath);
						writer.println(logHadoopOutputPath);
						//write cpu info
						writer.println(chckbxUsr.isSelected());
						writer.println(chckbxSys.isSelected());
						writer.println(chckbxIdl.isSelected());
						writer.println(chckbxWai.isSelected());
						writer.println(chckbxHiq.isSelected());
						writer.println(chckbxSiq.isSelected());
						//write ram info
						writer.println(chckbxUsed.isSelected());
						writer.println(chckbxBuff.isSelected());
						writer.println(chckbxCach.isSelected());
						writer.println(chckbxFree.isSelected());
						writer.println(checkBoxUpperBound.isSelected());
						writer.println(spinner_ram.getValue());
						//write time info
						writer.println(rdbtnSec.isSelected());
						writer.println(rdbtnMin.isSelected());
						writer.println(rdbtnHour.isSelected());
						//write chart type info
						writer.println(rdbtnSlavesSingles.isSelected());
						writer.println(rdbtnSlaveAverage.isSelected());
						writer.println(rdbtnAllIn.isSelected());
						//write chart color info
						writer.println(chckbxGreyScale.isSelected());
						//write chart dashed info
						writer.println(chckbxDashedLine.isSelected());
						//write chart show title info
						writer.println(chckbxTitles.isSelected());
						//write chart font size info
						writer.println(spinner_font_size.getValue());
						//write chart file output type
						writer.println(rdbtnSingleFile.isSelected());
						writer.println(rdbtnMultifiles.isSelected());
						//write combined/single informations
						writer.println(rdbtnCombinedDisk.isSelected());
						writer.println(rdbtnSingleDisk.isSelected());
						writer.println(rdbtnCombinedPaging.isSelected());
						writer.println(rdbtnSinglePaging.isSelected());
						writer.println(rdbtnCombinedNetworkPackets.isSelected());
						writer.println(rdbtnSingleNetworkPackets.isSelected());
						writer.println(rdbtnCombinedNetworkThroughput.isSelected());
						writer.println(rdbtnSingleNetworkThroughput.isSelected());
						//write warning charts information
						writer.println(slider_CPU_Positive.getValue());
						writer.println(slider_Disk_Positive.getValue());
						writer.println(slider_RAM_Positive.getValue());
						writer.println(slider_NETWORK_Negative.getValue());
						writer.println(slider_RAM_Negative.getValue());
						writer.println(slider_SWAP_Negative.getValue());
						//write speedUp informations
						writer.println(sequentialTime);
						if(speedUpValue == null)
							writer.println("0");
						else{
							writer.println(speedUpValue.length);
							for(int i=0; i<speedUpValue.length; i++){
								writer.println(speedUpValue[i][0]+","+speedUpValue[i][1]);
							}
						}
						//end
						writer.close();
						mntmSave.setEnabled(true);						
						log.info("Saved configuration file.");
					} catch (FileNotFoundException e) {
						log.severe("Exception saving file: " + e.getMessage());
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						log.severe("Exception saving file: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		});
		
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.info("Saving file: " + confFilePath);
			    try {
					PrintWriter writer = new PrintWriter(confFilePath, "UTF-8");
					writer.println(inputPath);	
					writer.println(outputPath);
					writer.println(rdbtnGooglejfreechart.isSelected());
					writer.println(logHadoopDirPath);
					writer.println(logHadoopOutputPath);
					//write cpu info
					writer.println(chckbxUsr.isSelected());
					writer.println(chckbxSys.isSelected());
					writer.println(chckbxIdl.isSelected());
					writer.println(chckbxWai.isSelected());
					writer.println(chckbxHiq.isSelected());
					writer.println(chckbxSiq.isSelected());
					//write ram info
					writer.println(chckbxUsed.isSelected());
					writer.println(chckbxBuff.isSelected());
					writer.println(chckbxCach.isSelected());
					writer.println(chckbxFree.isSelected());
					writer.println(checkBoxUpperBound.isSelected());
					writer.println(spinner_ram.getValue());
					//write time info
					writer.println(rdbtnSec.isSelected());
					writer.println(rdbtnMin.isSelected());
					writer.println(rdbtnHour.isSelected());
					//write chart type info
					writer.println(rdbtnSlavesSingles.isSelected());
					writer.println(rdbtnSlaveAverage.isSelected());
					writer.println(rdbtnAllIn.isSelected());
					//write chart color info
					writer.println(chckbxGreyScale.isSelected());
					//write chart dashed info
					writer.println(chckbxDashedLine.isSelected());
					//write chart show title info
					writer.println(chckbxTitles.isSelected());
					//write chart font size info
					writer.println(spinner_font_size.getValue());
					//write chart file output type
					writer.println(rdbtnSingleFile.isSelected());
					writer.println(rdbtnMultifiles.isSelected());
					//write combined/single informations
					writer.println(rdbtnCombinedDisk.isSelected());
					writer.println(rdbtnSingleDisk.isSelected());
					writer.println(rdbtnCombinedPaging.isSelected());
					writer.println(rdbtnSinglePaging.isSelected());
					writer.println(rdbtnCombinedNetworkPackets.isSelected());
					writer.println(rdbtnSingleNetworkPackets.isSelected());
					writer.println(rdbtnCombinedNetworkThroughput.isSelected());
					writer.println(rdbtnSingleNetworkThroughput.isSelected());
					//write warning charts information
					writer.println(slider_CPU_Positive.getValue());
					writer.println(slider_Disk_Positive.getValue());
					writer.println(slider_RAM_Positive.getValue());
					writer.println(slider_NETWORK_Negative.getValue());
					writer.println(slider_RAM_Negative.getValue());
					writer.println(slider_SWAP_Negative.getValue());
					//write speedUp informations
					writer.println(sequentialTime);
					if(speedUpValue == null)
						writer.println("0");
					else{
						writer.println(speedUpValue.length);
						for(int i=0; i<speedUpValue.length; i++){
							writer.println(speedUpValue[i][0]+","+speedUpValue[i][1]);
						}
					}
					//end
					writer.close();
					mntmSave.setEnabled(true);						
					log.info("Saved configuration file.");
				} catch (FileNotFoundException e) {
					log.severe("Exception saving file: " + e.getMessage());
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					log.severe("Exception saving file: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
		

		mntmOpen.addActionListener(new ActionListener() {
			private int sizeSpeedUp;

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
				FileFilter filter = new FileNameExtensionFilter("hlaConf file", new String[] {"hlaConf"});
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog(null);
				File file = fileChooser.getSelectedFile();
				confFilePath = file.getAbsolutePath();
				mntmSave.setEnabled(true);
				try {
					List<String> line_s = Files.readAllLines(Paths.get(file.getAbsolutePath()));
					int lineNum = 1;
					for (String line :line_s){
					    if(lineNum == 1){
					    	inputPath = line;
					    	if(!inputPath.equals("null")){
					    		labelInputPath.setText(inputPath);
					    		File[] file_s = new File(inputPath).listFiles();
						    	for (File f:file_s){
									if (f != null && f.getAbsolutePath() != null){
										if(f.getAbsolutePath().contains("out_dstat_master") && 
												!f.getAbsolutePath().contains(".hla")){
											masterPath = f.getAbsolutePath();
										}
										else if(f.getAbsolutePath().contains("prof_interval"))
											profilerPath = f.getAbsolutePath();
										else{
											if(!f.getAbsolutePath().contains(".hla")){
												if(slave_sPath == null){
													slave_sPath = new ArrayList<String>();
												}
												slave_sPath.add(f.getAbsolutePath());
											}
										}
									}
						    	}
					    	}
					    }
					    if(lineNum == 2){
					    	outputPath = line;
					    	if(!outputPath.equals("null"))
					    		labelOutputPath.setText(outputPath);
					    }
					    if(lineNum == 3){
					    	if(line.equals("true")){
					    		rdbtnGooglejfreechart.doClick();	
					    	}
					    	else
					    		rdbtnR.doClick();					    		
					    }
					    if(lineNum == 4){
					    	logHadoopDirPath = line;
					    	if(!logHadoopDirPath.equals("null"))
					    		labelInputHadoopLog.setText(logHadoopDirPath);
					    }
					    if(lineNum == 5){
					    	logHadoopOutputPath = line;
					    	if(!logHadoopOutputPath.equals("null"))
					    		labelHadoopLogOutput.setText(logHadoopOutputPath);
					    }
					    if(lineNum == 6){
					    	if(line.equals("true"))
					    		chckbxUsr.setSelected(true);
					    	else
					    		chckbxUsr.setSelected(false);
					    }
					    if(lineNum == 7){
					    	if(line.equals("true"))
					    		chckbxSys.setSelected(true);
					    	else
					    		chckbxSys.setSelected(false);
					    }
					    if(lineNum == 8){
					    	if(line.equals("true"))
					    		chckbxIdl.setSelected(true);
					    	else
					    		chckbxIdl.setSelected(false);
					    }
					    if(lineNum == 9){
					    	if(line.equals("true"))
					    		chckbxWai.setSelected(true);
					    	else
					    		chckbxWai.setSelected(false);
					    }
					    if(lineNum == 10){
					    	if(line.equals("true"))
					    		chckbxHiq.setSelected(true);
					    	else
					    		chckbxHiq.setSelected(false);
					    }
					    if(lineNum == 11){
					    	if(line.equals("true"))
					    		chckbxSiq.setSelected(true);
					    	else
					    		chckbxSiq.setSelected(false);
					    }
					    if(lineNum == 12){
					    	if(line.equals("true"))
					    		chckbxUsed.setSelected(true);
					    	else
					    		chckbxUsed.setSelected(false);
					    }
					    if(lineNum == 13){
					    	if(line.equals("true"))
					    		chckbxBuff.setSelected(true);
					    	else
					    		chckbxBuff.setSelected(false);
					    }
					    if(lineNum == 14){
					    	if(line.equals("true"))
					    		chckbxCach.setSelected(true);
					    	else
					    		chckbxCach.setSelected(false);
					    }
					    if(lineNum == 15){
					    	if(line.equals("true"))
					    		chckbxFree.setSelected(true);
					    	else
					    		chckbxFree.setSelected(false);
					    }
					    if(lineNum == 16){
					    	if(line.equals("true")){
					    		checkBoxUpperBound.setSelected(true);
					    	}
					    	else
					    		checkBoxUpperBound.setSelected(false);
					    }
					    if(lineNum == 17){
					    	spinner_ram.setValue(Integer.parseInt(line));
					    }
					    if(lineNum == 18){
					    	if(line.equals("true"))
					    		rdbtnSec.setSelected(true);
					    	else
					    		rdbtnSec.setSelected(false);
					    }
					    if(lineNum == 19){
					    	if(line.equals("true"))
					    		rdbtnMin.setSelected(true);
					    	else
					    		rdbtnMin.setSelected(false);
					    }
					    if(lineNum == 20){
					    	if(line.equals("true"))
					    		rdbtnHour.setSelected(true);
					    	else
					    		rdbtnHour.setSelected(false);
					    }
					    if(lineNum == 21){
					    	if(line.equals("true"))
					    		rdbtnSlavesSingles.setSelected(true);
					    	else
					    		rdbtnSlavesSingles.setSelected(false);
					    }
					    if(lineNum == 22){
					    	if(line.equals("true"))
					    		rdbtnSlaveAverage.setSelected(true);
					    	else
					    		rdbtnSlaveAverage.setSelected(false);
					    }
					    if(lineNum == 23){
					    	if(line.equals("true"))
					    		rdbtnAllIn.setSelected(true);
					    	else
					    		rdbtnAllIn.setSelected(false);
					    }
					    if(lineNum == 24){
					    	if(line.equals("true"))
					    		chckbxGreyScale.setSelected(true);
					    	else
					    		chckbxGreyScale.setSelected(false);
					    }
					    if(lineNum == 25){
					    	if(line.equals("true"))
					    		chckbxDashedLine.setSelected(true);
					    	else
					    		chckbxDashedLine.setSelected(false);
					    }
					    if(lineNum == 26){
					    	if(line.equals("true"))
					    		chckbxTitles.setSelected(true);
					    	else
					    		chckbxTitles.setSelected(false);
					    }
					    if(lineNum == 27){
					    	spinner_font_size.setValue(Integer.parseInt(line));
					    }
					    if(lineNum == 28){
					    	if(line.equals("true"))
					    		rdbtnSingleFile.setSelected(true);
					    	else
					    		rdbtnSingleFile.setSelected(false);
					    }
					    if(lineNum == 29){
					    	if(line.equals("true"))
					    		rdbtnMultifiles.setSelected(true);
					    	else
					    		rdbtnMultifiles.setSelected(false);
					    }
					    if(lineNum == 30){
					    	if(line.equals("true"))
					    		rdbtnCombinedDisk.setSelected(true);
					    	else
					    		rdbtnCombinedDisk.setSelected(false);
					    }
					    if(lineNum == 31){
					    	if(line.equals("true"))
					    		rdbtnSingleDisk.setSelected(true);
					    	else
					    		rdbtnSingleDisk.setSelected(false);
					    }
					    if(lineNum == 32){
					    	if(line.equals("true"))
					    		rdbtnCombinedPaging.setSelected(true);
					    	else
					    		rdbtnCombinedPaging.setSelected(false);
					    }
					    if(lineNum == 33){
					    	if(line.equals("true"))
					    		rdbtnSinglePaging.setSelected(true);
					    	else
					    		rdbtnSinglePaging.setSelected(false);
					    }
					    if(lineNum == 34){
					    	if(line.equals("true"))
					    		rdbtnCombinedNetworkPackets.setSelected(true);
					    	else
					    		rdbtnCombinedNetworkPackets.setSelected(false);
					    }
					    if(lineNum == 35){
					    	if(line.equals("true"))
					    		rdbtnSingleNetworkPackets.setSelected(true);
					    	else
					    		rdbtnSingleNetworkPackets.setSelected(false);
					    }
					    if(lineNum == 36){
					    	if(line.equals("true"))
					    		rdbtnCombinedNetworkThroughput.setSelected(true);
					    	else
					    		rdbtnCombinedNetworkThroughput.setSelected(false);
					    }
					    if(lineNum == 37){
					    	if(line.equals("true"))
					    		rdbtnSingleNetworkThroughput.setSelected(true);
					    	else
					    		rdbtnSingleNetworkThroughput.setSelected(false);
					    }
					    if(lineNum == 38){
					    	slider_CPU_Positive.setValue(Integer.parseInt(line));
					    }
					    if(lineNum == 39){
					    	slider_Disk_Positive.setValue(Integer.parseInt(line));
					    }
					    if(lineNum == 40){
					    	slider_RAM_Positive.setValue(Integer.parseInt(line));
					    }
					    if(lineNum == 41){
					    	slider_NETWORK_Negative.setValue(Integer.parseInt(line));
					    }
					    if(lineNum == 42){
					    	slider_RAM_Negative.setValue(Integer.parseInt(line));
					    }
					    if(lineNum == 43){
					    	slider_SWAP_Negative.setValue(Integer.parseInt(line));
					    }
					    if(lineNum == 44){
					    	sequentialTime = Double.parseDouble(line);
					    }
					    if(lineNum == 45){
					    	sizeSpeedUp = Integer.parseInt(line);
					    	speedUpValue = new Object[sizeSpeedUp][2];
					    }
					    if(lineNum > 45 && lineNum < (46+sizeSpeedUp)){
					    	String[] row = line.split(",");
					    	speedUpValue[lineNum - 46][0] = row[0];
					    	speedUpValue[lineNum - 46][1] = row[1];
					    }
					    lineNum++;
					}
				log.info("Configuration file " + confFilePath + " read successfully");
				} catch (IOException e) {
					log.severe("Exception reading configuration file: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
		
		if(myConsole == null){
			myConsole = new LogHandler(textArea);
			log.addHandler(myConsole);
		}
		
		labelInputPath.setToolTipText(labelInputPath.getText());
		labelOutputPath.setToolTipText(labelOutputPath.getText());
		labelInputHadoopLog.setToolTipText(labelInputHadoopLog.getText());
		labelHadoopLogOutput.setToolTipText(labelHadoopLogOutput.getText());
		
		JPanel panel_48 = new JPanel();
		panel_45.add(panel_48, BorderLayout.SOUTH);
		panel_48.setLayout(new GridLayout(0, 8, 0, 0));
		
		JLabel label_12 = new JLabel("");
		panel_48.add(label_12);
		
		JLabel label = new JLabel("");
		panel_48.add(label);
		
		JLabel label_6 = new JLabel("");
		panel_48.add(label_6);
		
		btnPrevPdf.setEnabled(false);
		btnPrevPdf.setIcon(new ImageIcon(Home.class.getResource("/gui/sq_br_prev.png")));
		panel_48.add(btnPrevPdf);
		
		btnNextPdf.setEnabled(false);
		btnNextPdf.setIcon(new ImageIcon(Home.class.getResource("/gui/sq_br_next.png")));
		panel_48.add(btnNextPdf);
	}

	public static Object[][] getSpeedUpValue() {
		return speedUpValue;
	}

	public static void setSpeedUpValue(Object[][] newSpeedUpValue) {
		speedUpValue = newSpeedUpValue;
	}

	public static double getSequentialTime() {
		return sequentialTime;
	}

	public static void setSequentialTime(double newSequentialTime) {
		sequentialTime = newSequentialTime;
	}
	
}
