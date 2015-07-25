package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Canvas;
import java.awt.GridLayout;

import com.orsoncharts.graphics3d.swing.Panel3D;
import com.orsoncharts.graphics3d.Drawable3D;

import javax.swing.Icon;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This jFrame contains information about tool.
 * 
 * @author Romano Simone - www.sromano.altervista.org
 * 
 */
public class About extends JFrame {
	private static String ABOUT = "Hadoop Analyzer JR\n\n\n"
			+ "This tool has been realized by Amedeo Leo, Alessio Petrozziello and "
			+ "Simone Romano as project for 'Sistemi Operativi Avanzati' exam teached "
			+ "by Giuseppe Cattaneo, under the supervision of Gianluca Roscigno.\n\n\n"
			+ "How to contact us:\n" + "Amedeo Leo: amedeo.leo92@gmail.com\n"
			+ "Alessio Petrozziello: alessio92p@gmail.com\n"
			+ "Simone Romano: s.romano1992@gmail.com";

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public About() {
		setResizable(false);
		setBounds(100, 100, 596, 376);
		contentPane = new JPanel();
		this.setTitle("About");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_Img = new JPanel();
		panel.add(panel_Img);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		ImageIcon icon = new ImageIcon(
				About.class.getResource("/gui/cluster.png"));
		Image scaleImage = icon.getImage().getScaledInstance(200, 200,
				Image.SCALE_DEFAULT);
		panel_Img.setLayout(new GridLayout(0, 1, 0, 0));

		lblNewLabel.setIcon(new ImageIcon(scaleImage));
		panel_Img.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		JTextPane txtpnHereDescription = new JTextPane();
		txtpnHereDescription.setText(this.ABOUT);
		txtpnHereDescription.setEditable(false);
		txtpnHereDescription.setBackground(new Color(215, 215, 215));
		panel_2.add(txtpnHereDescription);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 5, 0, 0));

		JButton btnNewButton = new JButton("Java");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI("http://java.com/en/"));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setIcon(new ImageIcon(About.class
				.getResource("/gui/JavaIcon.gif")));
		panel_1.add(btnNewButton);

		JButton btnPython = new JButton("Python");
		btnPython.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI("http://www.python.it/"));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnPython.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnPython.setIcon(new ImageIcon(About.class
				.getResource("/gui/python.png")));
		panel_1.add(btnPython);

		JButton btnC = new JButton("Dstat");
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI("http://dag.wiee.rs/home-made/dstat/"));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}				
			}
		});
		btnC.setIcon(new ImageIcon(About.class.getResource("/gui/dstat.png")));
		panel_1.add(btnC);

		JButton btnHadoop = new JButton("Hadoop");
		btnHadoop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI("http://hadoop.apache.org/"));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}				
			}
		});
		btnHadoop.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnHadoop.setIcon(new ImageIcon(About.class
				.getResource("/gui/hadoop-logo-16x16.png")));
		panel_1.add(btnHadoop);

		JButton btnR = new JButton("R");
		btnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI("http://www.r-project.org/"));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}	
			}
		});
		btnR.setIcon(new ImageIcon(About.class.getResource("/gui/r.png")));
		panel_1.add(btnR);
	}

}
