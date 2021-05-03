package client.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import client.ChatApplication;
import client.DrawPanel;
import client.DrawPanel.ShapeType;
import client.ErrorHandling;
import client.RMIManager;
import client.UserList;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Manager {

	public JFrame frame;
	private JButton btnApprove;
	public static DrawPanel graphicsPanel;
	private Boolean save;
	private File saveLocation;
	public static JTextArea textAreaChatOutput;
	public static JList listApprovedUsers;
	public static JList listPendingUsers;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager window = new Manager(args[0]);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	String username;
	public Manager(String user) {
		username = user;
		initialize();
		save = false;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.decode("#f2f2f2"));
		frame.setBounds(100, 100, 1182, 615);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {75, 75, 0, 0, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 47, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 73, 73, 43, 43, 0};
		gridBagLayout.rowHeights = new int[]{24, 19, 220, 0, 40, 123, 86, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.out.println("System closed");
    			// notify Server that client is going to exit
		    	UserList.ManagerOnExit(username);

		    }
		});
		
		JButton btnText = new JButton("");
		btnText.setMinimumSize(new Dimension(30, 29));
		btnText.setToolTipText("text");
		btnText.setPreferredSize(new Dimension(40, 40));
		btnText.setOpaque(true);
		btnText.setMargin(new Insets(0, 0, 0, 0));
		btnText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String text = (String) JOptionPane.showInputDialog(graphicsPanel, "Enter Text:");
				
				if (text != null && !text.isEmpty()) {
					graphicsPanel.setText(text);
					graphicsPanel.setShapeType(ShapeType.TEXT);
				} else {
					//user did not input text
				}
			}
		});
		ImageIcon imgtext = new ImageIcon(Manager.class.getResource("/icons/text1.png"));
		btnText.setIcon(imgtext);
		btnText.setForeground(Color.WHITE);
		btnText.setBorderPainted(false);
		btnText.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnText = new GridBagConstraints();
		gbc_btnText.gridheight = 2;
		gbc_btnText.fill = GridBagConstraints.BOTH;
		gbc_btnText.insets = new Insets(0, 0, 2, 2);
		gbc_btnText.gridx = 13;
		gbc_btnText.gridy = 0;
		frame.getContentPane().add(btnText, gbc_btnText);
		
		JSpinner spinnerFontSize = new JSpinner();
		spinnerFontSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int fontSize = (Integer) spinnerFontSize.getValue();
				graphicsPanel.setFontSize(fontSize);
			}
		});
		spinnerFontSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerFontSize.setModel(new SpinnerNumberModel(24, 8, 99, 1));
		spinnerFontSize.setToolTipText("font size");
		spinnerFontSize.setMinimumSize(new Dimension(29, 26));
		GridBagConstraints gbc_spinnerFontSize = new GridBagConstraints();
		gbc_spinnerFontSize.fill = GridBagConstraints.BOTH;
		spinnerFontSize.setForeground(Color.WHITE);
		spinnerFontSize.setOpaque(true);
		spinnerFontSize.setBackground(Color.white);
		gbc_spinnerFontSize.gridheight = 2;
		gbc_spinnerFontSize.insets = new Insets(0, 0, 2, 2);
		gbc_spinnerFontSize.gridx = 14;
		gbc_spinnerFontSize.gridy = 0;
		frame.getContentPane().add(spinnerFontSize, gbc_spinnerFontSize);
		
		JButton buttonClose = new JButton("");
		buttonClose.setToolTipText("close whiteboard");
		buttonClose.setPreferredSize(new Dimension(40, 40));
		buttonClose.setOpaque(true);
		buttonClose.setMinimumSize(new Dimension(20, 20));
		buttonClose.setMargin(new Insets(0, 0, 0, 0));
		buttonClose.setForeground(Color.WHITE);
		buttonClose.setBorderPainted(false);
		buttonClose.setBackground(Color.WHITE);
		GridBagConstraints gbc_buttonClose = new GridBagConstraints();
		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> tempQuery = new HashMap<String, String>();
				tempQuery.put("queryType", "CloseWhiteboard");
				RMIManager.query = tempQuery;
			}
		});
		ImageIcon imgclose= new ImageIcon(Manager.class.getResource("/icons/close.png"));
		buttonClose.setIcon(imgclose);
		gbc_buttonClose.fill = GridBagConstraints.BOTH;
		gbc_buttonClose.gridheight = 2;
		gbc_buttonClose.insets = new Insets(0, 0, 2, 2);
		gbc_buttonClose.gridx = 30;
		gbc_buttonClose.gridy = 0;
		frame.getContentPane().add(buttonClose, gbc_buttonClose);
		
		graphicsPanel = new DrawPanel();
//		graphicsPanel = RMIClient.panel;
		graphicsPanel.setLayout(null);
		GridBagConstraints gbc_graphicsPanel = new GridBagConstraints();
		gbc_graphicsPanel.fill = GridBagConstraints.BOTH;
		gbc_graphicsPanel.insets = new Insets(0, 0, 2, 2);
		gbc_graphicsPanel.gridx = 2;
		gbc_graphicsPanel.gridy = 2;
		gbc_graphicsPanel.gridwidth = 29;
		gbc_graphicsPanel.gridheight = 6;
		frame.getContentPane().add(graphicsPanel, gbc_graphicsPanel);
		
		JLabel lblNewLabel = new JLabel("Online User");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.decode("#757EAE"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 2;
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 2, 2);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JButton btnSave = new JButton("");
		btnSave.setMinimumSize(new Dimension(30, 29));
		btnSave.setToolTipText("save");
		btnSave.setPreferredSize(new Dimension(40, 40));
		btnSave.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgsave = new ImageIcon(Manager.class.getResource("/icons/save1.png"));
		
		JButton btnNew = new JButton("");
		btnNew.setMinimumSize(new Dimension(30, 29));
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				graphicsPanel.clear();
				save = false;
				saveLocation = null;
				HashMap<String, String> tempQuery = new HashMap<String, String>();
				tempQuery.put("queryType", "ClearWhiteboard");
				RMIManager.query = tempQuery;
				graphicsPanel.setVisible(true);
				graphicsPanel.setEnabled(true);
			}
		});
		btnNew.setToolTipText("create");
		btnNew.setPreferredSize(new Dimension(40, 40));
		ImageIcon imgnew = new ImageIcon(Manager.class.getResource("/icons/new.png"));
		btnNew.setIcon(imgnew);
		btnNew.setOpaque(true);
		btnNew.setMargin(new Insets(0, 0, 0, 0));
		btnNew.setForeground(Color.WHITE);
		btnNew.setBorderPainted(false);
		btnNew.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.fill = GridBagConstraints.VERTICAL;
		gbc_btnNew.gridheight = 2;
		gbc_btnNew.insets = new Insets(0, 0, 2, 2);
		gbc_btnNew.gridx = 2;
		gbc_btnNew.gridy = 0;
		frame.getContentPane().add(btnNew, gbc_btnNew);
		
		JButton btnOpen = new JButton("");
		btnOpen.setMinimumSize(new Dimension(30, 29));
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();

				// File Extension filter when browsing directory
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.png", "png");
				fc.setFileFilter(filter);

				// Handle open button action.
				if (e.getSource() == btnOpen) {
					int returnVal = fc.showOpenDialog(fc);
					fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						save = true;
						saveLocation = file;
						RMIManager.file = file;
//						System.out.println(file);
//						try {
//							
//							graphicsPanel.clear();
//							graphicsPanel.load(file);
//							JOptionPane.showMessageDialog(null, "File Loaded successfully.");
//							save = true;
//							saveLocation = file;
//							graphicsPanel.setVisible(true);
//							graphicsPanel.setEnabled(true);
//						} catch (Exception e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//							JOptionPane.showMessageDialog(null, "Error Loading File");
//						}
					} else {
						System.out.println("Open command cancelled by user");
					}
				}
			}
		});
		btnOpen.setToolTipText("open");
		btnOpen.setPreferredSize(new Dimension(40, 40));
		ImageIcon imgopen = new ImageIcon(Manager.class.getResource("/icons/open.png"));
		btnOpen.setIcon(imgopen);
		btnOpen.setOpaque(true);
		btnOpen.setMargin(new Insets(0, 0, 0, 0));
//		btnOpen.setIcon(imgclose);
		btnOpen.setForeground(Color.WHITE);
		btnOpen.setBorderPainted(false);
		btnOpen.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnOpen = new GridBagConstraints();
		gbc_btnOpen.gridheight = 2;
		gbc_btnOpen.fill = GridBagConstraints.BOTH;
		gbc_btnOpen.insets = new Insets(0, 0, 2, 2);
		gbc_btnOpen.gridx = 3;
		gbc_btnOpen.gridy = 0;
		frame.getContentPane().add(btnOpen, gbc_btnOpen);
		btnSave.setIcon(imgsave);
		btnSave.setForeground(Color.WHITE);
		btnSave.setOpaque(true);
		btnSave.setBackground(Color.decode("#ffffff")) ;
		btnSave.setBorderPainted(false);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (save) {
					try {
						graphicsPanel.save(saveLocation);
						JOptionPane.showMessageDialog(null, "File saved successfully.");							
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error Saving File.");
					}
				} else {

					final JFileChooser fc = new JFileChooser();

					// File Extension filter when browsing directory
//				FileNameExtensionFilter filter = new FileNameExtensionFilter(
//					    "JPG & GIF Images", "jpg", "gif");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("*.png", "png");
					fc.setFileFilter(filter);

					// Handle save button action.
					if (e.getSource() == btnSave) {
						int returnVal = fc.showSaveDialog(fc);
						fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							file = new File(file.toString() + ".png");

							try {
								graphicsPanel.save(file);
								JOptionPane.showMessageDialog(null, "File saved successfully.");
								save = true;
								saveLocation = file;								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "Error Saving File.");
							}

						} else {
							System.out.println("Save command cancelled by user");
						}
					}
				}
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridheight = 2;
		gbc_btnSave.fill = GridBagConstraints.BOTH;
		gbc_btnSave.insets = new Insets(0, 0, 2, 2);
		gbc_btnSave.gridx = 4;
		gbc_btnSave.gridy = 0;
		frame.getContentPane().add(btnSave, gbc_btnSave);
		
		JButton btnSaveas = new JButton("");
		btnSaveas.setMinimumSize(new Dimension(30, 29));
		btnSaveas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();

				// File Extension filter when browsing directory
//				FileNameExtensionFilter filter = new FileNameExtensionFilter(
//					    "JPG & GIF Images", "jpg", "gif");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.png", "png");
				fc.setFileFilter(filter);

				// Handle save button action.
				if (e.getSource() == btnSaveas) {
					int returnVal = fc.showSaveDialog(fc);
					fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						file = new File(file.toString() + ".png");

						try {
							graphicsPanel.save(file);
							JOptionPane.showMessageDialog(null, "File saved successfully.");
							save = true;
							saveLocation = file;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error Saving File.");
						}

					} else {
						System.out.println("Save command cancelled by user");
					}
				}
			}
		});
		btnSaveas.setToolTipText("save as");
		btnSaveas.setPreferredSize(new Dimension(40, 40));
		btnSaveas.setOpaque(true);
		btnSaveas.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgsaveas = new ImageIcon(Manager.class.getResource("/icons/saveas2.png"));
		btnSaveas.setIcon(imgsaveas);
		btnSaveas.setForeground(Color.WHITE);
		btnSaveas.setBorderPainted(false);
		btnSaveas.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnSaveas = new GridBagConstraints();
		gbc_btnSaveas.gridheight = 2;
		gbc_btnSaveas.fill = GridBagConstraints.BOTH;
		gbc_btnSaveas.insets = new Insets(0, 0, 2, 2);
		gbc_btnSaveas.gridx = 5;
		gbc_btnSaveas.gridy = 0;
		frame.getContentPane().add(btnSaveas, gbc_btnSaveas);
		
		JButton btnRectangle = new JButton("");
		btnRectangle.setMinimumSize(new Dimension(30, 29));
		btnRectangle.setToolTipText("rectangle");
		btnRectangle.setPreferredSize(new Dimension(40, 40));
		btnRectangle.setOpaque(true);
		btnRectangle.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgrectangle = new ImageIcon(Manager.class.getResource("/icons/rectangle1.png"));
		btnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setShapeType(ShapeType.RECTANGLE);
			}
		});
		btnRectangle.setIcon(imgrectangle);
		btnRectangle.setForeground(Color.WHITE);
		btnRectangle.setBorderPainted(false);
		btnRectangle.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnRectangle = new GridBagConstraints();
		gbc_btnRectangle.gridheight = 2;
		gbc_btnRectangle.fill = GridBagConstraints.BOTH;
		gbc_btnRectangle.insets = new Insets(0, 0, 2, 2);
		gbc_btnRectangle.gridx = 6;
		gbc_btnRectangle.gridy = 0;
		frame.getContentPane().add(btnRectangle, gbc_btnRectangle);
		
		JButton btnSquare = new JButton("");
		btnSquare.setMinimumSize(new Dimension(30, 29));
		btnSquare.setToolTipText("square");
		btnSquare.setPreferredSize(new Dimension(40, 40));
		btnSquare.setOpaque(true);
		btnSquare.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgsquare = new ImageIcon(Manager.class.getResource("/icons/cub1.png"));
		btnSquare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setShapeType(ShapeType.SQUARE);
			}
		});
		btnSquare.setIcon(imgsquare);
		btnSquare.setForeground(Color.WHITE);
		btnSquare.setBorderPainted(false);
		btnSquare.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnSquare = new GridBagConstraints();
		gbc_btnSquare.gridheight = 2;
		gbc_btnSquare.fill = GridBagConstraints.VERTICAL;
		gbc_btnSquare.insets = new Insets(0, 0, 2, 2);
		gbc_btnSquare.gridx = 7;
		gbc_btnSquare.gridy = 0;
		frame.getContentPane().add(btnSquare, gbc_btnSquare);
		
		
		JButton btnLine = new JButton("");
		btnLine.setMinimumSize(new Dimension(30, 29));
		btnLine.setToolTipText("line");
		btnLine.setPreferredSize(new Dimension(40, 40));
		btnLine.setOpaque(true);
		btnLine.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgline = new ImageIcon(Manager.class.getResource("/icons/line1.png"));
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setShapeType(ShapeType.LINE);
			}
		});
		btnLine.setIcon(imgline);
		btnLine.setForeground(Color.WHITE);
		btnLine.setBorderPainted(false);
		btnLine.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnLine = new GridBagConstraints();
		gbc_btnLine.gridheight = 2;
		gbc_btnLine.fill = GridBagConstraints.BOTH;
		gbc_btnLine.insets = new Insets(0, 0, 2, 2);
		gbc_btnLine.gridx = 8;
		gbc_btnLine.gridy = 0;
		frame.getContentPane().add(btnLine, gbc_btnLine);
		
		JButton btnCircle = new JButton("");
		btnCircle.setMinimumSize(new Dimension(30, 29));
		btnCircle.setToolTipText("circle");
		btnCircle.setPreferredSize(new Dimension(40, 40));
		btnCircle.setOpaque(true);
		btnCircle.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgcircle = new ImageIcon(Manager.class.getResource("/icons/circle2.png"));
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setShapeType(ShapeType.CIRCLE);
			}
		});
		btnCircle.setIcon(imgcircle);
		btnCircle.setForeground(Color.WHITE);
		btnCircle.setBorderPainted(false);
		btnCircle.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnCircle = new GridBagConstraints();
		gbc_btnCircle.gridheight = 2;
		gbc_btnCircle.fill = GridBagConstraints.BOTH;
		gbc_btnCircle.insets = new Insets(0, 0, 2, 2);
		gbc_btnCircle.gridx = 9;
		gbc_btnCircle.gridy = 0;
		frame.getContentPane().add(btnCircle, gbc_btnCircle);
		
		JButton btnOval = new JButton("");
		btnOval.setMinimumSize(new Dimension(30, 29));
		btnOval.setToolTipText("oval");
		btnOval.setPreferredSize(new Dimension(40, 40));
		btnOval.setOpaque(true);
		btnOval.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgoval = new ImageIcon(Manager.class.getResource("/icons/oval1.png"));
		btnOval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setShapeType(ShapeType.OVAL);
			}
		});
		btnOval.setIcon(imgoval);
		btnOval.setForeground(Color.WHITE);
		btnOval.setBorderPainted(false);
		btnOval.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnOval = new GridBagConstraints();
		gbc_btnOval.gridheight = 2;
		gbc_btnOval.fill = GridBagConstraints.BOTH;
		gbc_btnOval.insets = new Insets(0, 0, 2, 2);
		gbc_btnOval.gridx = 10;
		gbc_btnOval.gridy = 0;
		frame.getContentPane().add(btnOval, gbc_btnOval);
		
		JButton btnPen = new JButton("");
		btnPen.setMinimumSize(new Dimension(30, 29));
		btnPen.setToolTipText("pen");
		btnPen.setPreferredSize(new Dimension(40, 40));
		btnPen.setOpaque(true);
		btnPen.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgpen = new ImageIcon(Manager.class.getResource("/icons/pen1.png"));
		btnPen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setShapeType(ShapeType.FREEPEN);
			}
		});
		btnPen.setIcon(imgpen);
		btnPen.setForeground(Color.WHITE);
		btnPen.setBorderPainted(false);
		btnPen.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnPen = new GridBagConstraints();
		gbc_btnPen.gridheight = 2;
		gbc_btnPen.fill = GridBagConstraints.BOTH;
		gbc_btnPen.insets = new Insets(0, 0, 2, 2);
		gbc_btnPen.gridx = 11;
		gbc_btnPen.gridy = 0;
		frame.getContentPane().add(btnPen, gbc_btnPen);
		ImageIcon imgcolor = new ImageIcon(Manager.class.getResource("/icons/color.png"));
		
		JButton btnEraser = new JButton("");
		btnEraser.setMinimumSize(new Dimension(30, 29));
		btnEraser.setToolTipText("eraser");
		btnEraser.setPreferredSize(new Dimension(40, 40));
		btnEraser.setOpaque(true);
		btnEraser.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon imgeraser = new ImageIcon(Manager.class.getResource("/icons/eraser2.png"));
		btnEraser.setIcon(imgeraser);
		btnEraser.setForeground(Color.WHITE);
		btnEraser.setBorderPainted(false);
		btnEraser.setBackground(Color.WHITE);
		btnEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setShapeType(ShapeType.ERASER);
			}
		});
		GridBagConstraints gbc_btnEraser = new GridBagConstraints();
		gbc_btnEraser.gridheight = 2;
		gbc_btnEraser.fill = GridBagConstraints.BOTH;
		gbc_btnEraser.insets = new Insets(0, 0, 2, 2);
		gbc_btnEraser.gridx = 12;
		gbc_btnEraser.gridy = 0;
		frame.getContentPane().add(btnEraser, gbc_btnEraser);
		ImageIcon imgsize1 = new ImageIcon(Manager.class.getResource("/icons/dot01.png"));
		ImageIcon imgsize2 = new ImageIcon(Manager.class.getResource("/icons/dot02.png"));
		ImageIcon imgsize3 = new ImageIcon(Manager.class.getResource("/icons/dot03.png"));
		ImageIcon imgsize4 = new ImageIcon(Manager.class.getResource("/icons/dot04.png"));
		ImageIcon imgsize5 = new ImageIcon(Manager.class.getResource("/icons/dot05.png"));
		
		JButton btncolor1 = new JButton("");
		btncolor1.setMinimumSize(new Dimension(20, 20));
		btncolor1.setToolTipText("");
		btncolor1.setPreferredSize(new Dimension(40, 40));
		btncolor1.setOpaque(true);
		btncolor1.setMargin(new Insets(0, 0, 0, 0));
		btncolor1.setBackground(Color.decode("#000000"));		//background color
		btncolor1.setForeground(Color.WHITE);
		btncolor1.setBorderPainted(false);
		btncolor1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#000000"));
			}
		});
		GridBagConstraints gbc_btncolor1 = new GridBagConstraints();
		gbc_btncolor1.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor1.gridx = 15;
		gbc_btncolor1.gridy = 0;
		frame.getContentPane().add(btncolor1, gbc_btncolor1);
		
		JButton btncolor2 = new JButton("");
		btncolor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#7f7f7f"));
			}
		});
		btncolor2.setToolTipText("");
		btncolor2.setPreferredSize(new Dimension(40, 40));
		btncolor2.setOpaque(true);
		btncolor2.setMinimumSize(new Dimension(20, 20));
		btncolor2.setMargin(new Insets(0, 0, 0, 0));
		btncolor2.setForeground(Color.WHITE);
		btncolor2.setBorderPainted(false);
		btncolor2.setBackground(Color.decode("#7f7f7f"));
		GridBagConstraints gbc_btncolor2 = new GridBagConstraints();
		gbc_btncolor2.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor2.gridx = 16;
		gbc_btncolor2.gridy = 0;
		frame.getContentPane().add(btncolor2, gbc_btncolor2);
		
		JButton btncolor3 = new JButton("");
		btncolor3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#860419"));
			}
		});
		btncolor3.setToolTipText("");
		btncolor3.setPreferredSize(new Dimension(40, 40));
		btncolor3.setOpaque(true);
		btncolor3.setMinimumSize(new Dimension(20, 20));
		btncolor3.setMargin(new Insets(0, 0, 0, 0));
		btncolor3.setForeground(Color.WHITE);
		btncolor3.setBorderPainted(false);
		btncolor3.setBackground(Color.decode("#860419"));
		GridBagConstraints gbc_btncolor3 = new GridBagConstraints();
		gbc_btncolor3.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor3.gridx = 17;
		gbc_btncolor3.gridy = 0;
		frame.getContentPane().add(btncolor3, gbc_btncolor3);
		
		JButton btncolor4 = new JButton("");
		btncolor4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#ea212d"));
			}
		});
		btncolor4.setToolTipText("");
		btncolor4.setPreferredSize(new Dimension(40, 40));
		btncolor4.setOpaque(true);
		btncolor4.setMinimumSize(new Dimension(20, 20));
		btncolor4.setMargin(new Insets(0, 0, 0, 0));
		btncolor4.setForeground(Color.WHITE);
		btncolor4.setBorderPainted(false);
		btncolor4.setBackground(Color.decode("#ea212d"));
		GridBagConstraints gbc_btncolor4 = new GridBagConstraints();
		gbc_btncolor4.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor4.gridx = 18;
		gbc_btncolor4.gridy = 0;
		frame.getContentPane().add(btncolor4, gbc_btncolor4);
		
		JButton btncolor5 = new JButton("");
		btncolor5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#fd7f36"));
			}
		});
		btncolor5.setToolTipText("");
		btncolor5.setPreferredSize(new Dimension(40, 40));
		btncolor5.setOpaque(true);
		btncolor5.setMinimumSize(new Dimension(20, 20));
		btncolor5.setMargin(new Insets(0, 0, 0, 0));
		btncolor5.setForeground(Color.WHITE);
		btncolor5.setBorderPainted(false);
		btncolor5.setBackground(Color.decode("#fd7f36"));
		GridBagConstraints gbc_btncolor5 = new GridBagConstraints();
		gbc_btncolor5.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor5.gridx = 19;
		gbc_btncolor5.gridy = 0;
		frame.getContentPane().add(btncolor5, gbc_btncolor5);
		
		JButton btncolor6 = new JButton("");
		btncolor6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#fef035"));
			}
		});
		btncolor6.setToolTipText("");
		btncolor6.setPreferredSize(new Dimension(40, 40));
		btncolor6.setOpaque(true);
		btncolor6.setMinimumSize(new Dimension(20, 20));
		btncolor6.setMargin(new Insets(0, 0, 0, 0));
		btncolor6.setForeground(Color.WHITE);
		btncolor6.setBorderPainted(false);
		btncolor6.setBackground(Color.decode("#fef035"));
		GridBagConstraints gbc_btncolor6 = new GridBagConstraints();
		gbc_btncolor6.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor6.gridx = 20;
		gbc_btncolor6.gridy = 0;
		frame.getContentPane().add(btncolor6, gbc_btncolor6);
		
		JButton btncolor7 = new JButton("");
		btncolor7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#2caf51"));
			}
		});
		btncolor7.setToolTipText("");
		btncolor7.setPreferredSize(new Dimension(40, 40));
		btncolor7.setOpaque(true);
		btncolor7.setMinimumSize(new Dimension(20, 20));
		btncolor7.setMargin(new Insets(0, 0, 0, 0));
		btncolor7.setForeground(Color.WHITE);
		btncolor7.setBorderPainted(false);
		btncolor7.setBackground(Color.decode("#2caf51"));
		GridBagConstraints gbc_btncolor7 = new GridBagConstraints();
		gbc_btncolor7.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor7.gridx = 21;
		gbc_btncolor7.gridy = 0;
		frame.getContentPane().add(btncolor7, gbc_btncolor7);
		
		JButton btncolor8 = new JButton("");
		btncolor8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#1ba3e5"));
			}
		});
		btncolor8.setToolTipText("");
		btncolor8.setPreferredSize(new Dimension(40, 40));
		btncolor8.setOpaque(true);
		btncolor8.setMinimumSize(new Dimension(20, 20));
		btncolor8.setMargin(new Insets(0, 0, 0, 0));
		btncolor8.setForeground(Color.WHITE);
		btncolor8.setBorderPainted(false);
		btncolor8.setBackground(Color.decode("#1ba3e5"));
		GridBagConstraints gbc_btncolor8 = new GridBagConstraints();
		gbc_btncolor8.insets = new Insets(0, 0, 5, 2);
		gbc_btncolor8.gridx = 22;
		gbc_btncolor8.gridy = 0;
		frame.getContentPane().add(btncolor8, gbc_btncolor8);
		
		JButton btncolor9 = new JButton("");
		btncolor9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#404dc9"));
			}
		});
		btncolor9.setToolTipText("");
		btncolor9.setPreferredSize(new Dimension(40, 40));
		btncolor9.setOpaque(true);
		btncolor9.setMinimumSize(new Dimension(20, 20));
		btncolor9.setMargin(new Insets(0, 0, 0, 0));
		btncolor9.setForeground(Color.WHITE);
		btncolor9.setBorderPainted(false);
		btncolor9.setBackground(Color.decode("#404dc9"));
		GridBagConstraints gbc_btncolor9 = new GridBagConstraints();
		gbc_btncolor9.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor9.gridx = 23;
		gbc_btncolor9.gridy = 0;
		frame.getContentPane().add(btncolor9, gbc_btncolor9);
		
		JButton btncolor10 = new JButton("");
		btncolor10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#a24ca2"));
			}
		});
		btncolor10.setToolTipText("");
		btncolor10.setPreferredSize(new Dimension(40, 40));
		btncolor10.setOpaque(true);
		btncolor10.setMinimumSize(new Dimension(20, 20));
		btncolor10.setMargin(new Insets(0, 0, 0, 0));
		btncolor10.setForeground(Color.WHITE);
		btncolor10.setBorderPainted(false);
		btncolor10.setBackground(Color.decode("#a24ca2"));
		GridBagConstraints gbc_btncolor10 = new GridBagConstraints();
		gbc_btncolor10.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor10.gridx = 24;
		gbc_btncolor10.gridy = 0;
		frame.getContentPane().add(btncolor10, gbc_btncolor10);
		
		String[] item = {"1","2","3","4","5","6","7","8","9","10"};
		
		JButton btnsize_1 = new JButton("");
		btnsize_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setStrokeSize(1);
			}
		});
		btnsize_1.setMinimumSize(new Dimension(20, 20));
		btnsize_1.setToolTipText("1px");
		btnsize_1.setPreferredSize(new Dimension(40, 40));
		btnsize_1.setIcon(imgsize1);
		btnsize_1.setOpaque(true);
		btnsize_1.setMargin(new Insets(0, 0, 0, 0));
		btnsize_1.setForeground(Color.WHITE);
		btnsize_1.setBorderPainted(false);
		btnsize_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnsize_1 = new GridBagConstraints();
		gbc_btnsize_1.fill = GridBagConstraints.BOTH;
		gbc_btnsize_1.gridheight = 2;
		gbc_btnsize_1.insets = new Insets(0, 0, 2, 2);
		gbc_btnsize_1.gridx = 25;
		gbc_btnsize_1.gridy = 0;
		frame.getContentPane().add(btnsize_1, gbc_btnsize_1);
		
		JButton btnsize_2 = new JButton("");
		btnsize_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setStrokeSize(2);
			}
		});
		btnsize_2.setToolTipText("2px");
		btnsize_2.setPreferredSize(new Dimension(40, 40));
		btnsize_2.setPreferredSize(new Dimension(40, 40));
		btnsize_2.setIcon(imgsize2);
		btnsize_2.setOpaque(true);
		btnsize_2.setMinimumSize(new Dimension(20, 20));
		btnsize_2.setMargin(new Insets(0, 0, 0, 0));
		btnsize_2.setForeground(Color.WHITE);
		btnsize_2.setBorderPainted(false);
		btnsize_2.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnsize_2 = new GridBagConstraints();
		gbc_btnsize_2.fill = GridBagConstraints.BOTH;
		gbc_btnsize_2.gridheight = 2;
		gbc_btnsize_2.insets = new Insets(0, 0, 2, 2);
		gbc_btnsize_2.gridx = 26;
		gbc_btnsize_2.gridy = 0;
		frame.getContentPane().add(btnsize_2, gbc_btnsize_2);
		
		JButton btnsize_3 = new JButton("");
		btnsize_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setStrokeSize(4);
			}
		});
		btnsize_3.setToolTipText("4px");
		btnsize_3.setPreferredSize(new Dimension(40, 40));
		btnsize_3.setIcon(imgsize3);
		btnsize_3.setOpaque(true);
		btnsize_3.setMinimumSize(new Dimension(20, 20));
		btnsize_3.setMargin(new Insets(0, 0, 0, 0));
		btnsize_3.setForeground(Color.WHITE);
		btnsize_3.setBorderPainted(false);
		btnsize_3.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnsize_3 = new GridBagConstraints();
		gbc_btnsize_3.fill = GridBagConstraints.BOTH;
		gbc_btnsize_3.gridheight = 2;
		gbc_btnsize_3.insets = new Insets(0, 0, 2, 2);
		gbc_btnsize_3.gridx = 27;
		gbc_btnsize_3.gridy = 0;
		frame.getContentPane().add(btnsize_3, gbc_btnsize_3);
		
		JButton btnsize_4 = new JButton("");
		btnsize_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setStrokeSize(8);
			}
		});
		btnsize_4.setToolTipText("8px");
		btnsize_4.setPreferredSize(new Dimension(40, 40));
		btnsize_4.setIcon(imgsize4);
		btnsize_4.setOpaque(true);
		btnsize_4.setMinimumSize(new Dimension(20, 20));
		btnsize_4.setMargin(new Insets(0, 0, 0, 0));
		btnsize_4.setForeground(Color.WHITE);
		btnsize_4.setBorderPainted(false);
		btnsize_4.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnsize_4 = new GridBagConstraints();
		gbc_btnsize_4.fill = GridBagConstraints.BOTH;
		gbc_btnsize_4.gridheight = 2;
		gbc_btnsize_4.insets = new Insets(0, 0, 2, 2);
		gbc_btnsize_4.gridx = 28;
		gbc_btnsize_4.gridy = 0;
		frame.getContentPane().add(btnsize_4, gbc_btnsize_4);
		
		JButton btnsize_5 = new JButton("");
		btnsize_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setStrokeSize(16);
			}
		});
		btnsize_5.setToolTipText("16px");
		btnsize_5.setPreferredSize(new Dimension(40, 40));
		btnsize_5.setIcon(imgsize5);
		btnsize_5.setOpaque(true);
		btnsize_5.setMinimumSize(new Dimension(20, 20));
		btnsize_5.setMargin(new Insets(0, 0, 0, 0));
		btnsize_5.setForeground(Color.WHITE);
		btnsize_5.setBorderPainted(false);
		btnsize_5.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnsize_5 = new GridBagConstraints();
		gbc_btnsize_5.fill = GridBagConstraints.BOTH;
		gbc_btnsize_5.gridheight = 2;
		gbc_btnsize_5.insets = new Insets(0, 0, 2, 2);
		gbc_btnsize_5.gridx = 29;
		gbc_btnsize_5.gridy = 0;
		frame.getContentPane().add(btnsize_5, gbc_btnsize_5);
		
		JLabel lblNewLabel_2 = new JLabel("Chat");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridheight = 2;
		gbc_lblNewLabel_2.gridwidth = 4;
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(Color.decode("#757EAE"));
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 2, 0);
		gbc_lblNewLabel_2.gridx = 31;
		gbc_lblNewLabel_2.gridy = 0;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
//		ImageIcon imgyuan = new ImageIcon(Manager.class.getResource("/icons/circle1.png"));
		
		JButton btncolor11 = new JButton("");
		btncolor11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#ffffff"));
			}
		});
		btncolor11.setBorder(new LineBorder(Color.LIGHT_GRAY));
		btncolor11.setMinimumSize(new Dimension(20, 20));
		btncolor11.setToolTipText("");
		btncolor11.setPreferredSize(new Dimension(40, 40));
		btncolor11.setOpaque(true);
		btncolor11.setMargin(new Insets(0, 0, 0, 0));
		btncolor11.setForeground(Color.WHITE);
		btncolor11.setBorderPainted(false);
		btncolor11.setBackground(Color.decode("#ffffff"));
		GridBagConstraints gbc_btncolor11 = new GridBagConstraints();
		gbc_btncolor11.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor11.gridx = 15;
		gbc_btncolor11.gridy = 1;
		frame.getContentPane().add(btncolor11, gbc_btncolor11);
		
		JButton btncolor12 = new JButton("");
		btncolor12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#c3c3c3"));
			}
		});
		btncolor12.setToolTipText("");
		btncolor12.setPreferredSize(new Dimension(40, 40));
		btncolor12.setOpaque(true);
		btncolor12.setMinimumSize(new Dimension(20, 20));
		btncolor12.setMargin(new Insets(0, 0, 0, 0));
		btncolor12.setForeground(Color.WHITE);
		btncolor12.setBorderPainted(false);
		btncolor12.setBackground(Color.decode("#c3c3c3"));
		GridBagConstraints gbc_btncolor12 = new GridBagConstraints();
		gbc_btncolor12.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor12.gridx = 16;
		gbc_btncolor12.gridy = 1;
		frame.getContentPane().add(btncolor12, gbc_btncolor12);
		
		JButton btncolor13 = new JButton("");
		btncolor13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#b87a5a"));
			}
		});
		btncolor13.setToolTipText("");
		btncolor13.setPreferredSize(new Dimension(40, 40));
		btncolor13.setOpaque(true);
		btncolor13.setMinimumSize(new Dimension(20, 20));
		btncolor13.setMargin(new Insets(0, 0, 0, 0));
		btncolor13.setForeground(Color.WHITE);
		btncolor13.setBorderPainted(false);
		btncolor13.setBackground(Color.decode("#b87a5a"));
		GridBagConstraints gbc_btncolor13 = new GridBagConstraints();
		gbc_btncolor13.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor13.gridx = 17;
		gbc_btncolor13.gridy = 1;
		frame.getContentPane().add(btncolor13, gbc_btncolor13);
		
		JButton btncolor14 = new JButton("");
		btncolor14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#fdafc9"));
			}
		});
		btncolor14.setToolTipText("");
		btncolor14.setPreferredSize(new Dimension(40, 40));
		btncolor14.setOpaque(true);
		btncolor14.setMinimumSize(new Dimension(20, 20));
		btncolor14.setMargin(new Insets(0, 0, 0, 0));
		btncolor14.setForeground(Color.WHITE);
		btncolor14.setBorderPainted(false);
		btncolor14.setBackground(Color.decode("#fdafc9"));
		GridBagConstraints gbc_btncolor14 = new GridBagConstraints();
		gbc_btncolor14.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor14.gridx = 18;
		gbc_btncolor14.gridy = 1;
		frame.getContentPane().add(btncolor14, gbc_btncolor14);
		
		JButton btncolor15 = new JButton("");
		btncolor15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#fec832"));
			}
		});
		btncolor15.setToolTipText("");
		btncolor15.setPreferredSize(new Dimension(40, 40));
		btncolor15.setOpaque(true);
		btncolor15.setMinimumSize(new Dimension(20, 20));
		btncolor15.setMargin(new Insets(0, 0, 0, 0));
		btncolor15.setForeground(Color.WHITE);
		btncolor15.setBorderPainted(false);
		btncolor15.setBackground(Color.decode("#fec832"));
		GridBagConstraints gbc_btncolor15 = new GridBagConstraints();
		gbc_btncolor15.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor15.gridx = 19;
		gbc_btncolor15.gridy = 1;
		frame.getContentPane().add(btncolor15, gbc_btncolor15);
		
		JButton btncolor16 = new JButton("");
		btncolor16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#efe3b3"));
			}
		});
		btncolor16.setToolTipText("");
		btncolor16.setPreferredSize(new Dimension(40, 40));
		btncolor16.setOpaque(true);
		btncolor16.setMinimumSize(new Dimension(20, 20));
		btncolor16.setMargin(new Insets(0, 0, 0, 0));
		btncolor16.setForeground(Color.WHITE);
		btncolor16.setBorderPainted(false);
		btncolor16.setBackground(Color.decode("#efe3b3"));
		GridBagConstraints gbc_btncolor16 = new GridBagConstraints();
		gbc_btncolor16.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor16.gridx = 20;
		gbc_btncolor16.gridy = 1;
		frame.getContentPane().add(btncolor16, gbc_btncolor16);
		
		JButton btncolor17 = new JButton("");
		btncolor17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#b6e438"));
			}
		});
		btncolor17.setToolTipText("");
		btncolor17.setPreferredSize(new Dimension(40, 40));
		btncolor17.setOpaque(true);
		btncolor17.setMinimumSize(new Dimension(20, 20));
		btncolor17.setMargin(new Insets(0, 0, 0, 0));
		btncolor17.setForeground(Color.WHITE);
		btncolor17.setBorderPainted(false);
		btncolor17.setBackground(Color.decode("#b6e438"));
		GridBagConstraints gbc_btncolor17 = new GridBagConstraints();
		gbc_btncolor17.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor17.gridx = 21;
		gbc_btncolor17.gridy = 1;
		frame.getContentPane().add(btncolor17, gbc_btncolor17);
		
		JButton btncolor18 = new JButton("");
		btncolor18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#9bd9e9"));
			}
		});
		btncolor18.setToolTipText("");
		btncolor18.setPreferredSize(new Dimension(40, 40));
		btncolor18.setOpaque(true);
		btncolor18.setMinimumSize(new Dimension(20, 20));
		btncolor18.setMargin(new Insets(0, 0, 0, 0));
		btncolor18.setForeground(Color.WHITE);
		btncolor18.setBorderPainted(false);
		btncolor18.setBackground(Color.decode("#9bd9e9"));
		GridBagConstraints gbc_btncolor18 = new GridBagConstraints();
		gbc_btncolor18.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor18.gridx = 22;
		gbc_btncolor18.gridy = 1;
		frame.getContentPane().add(btncolor18, gbc_btncolor18);
		
		JButton btncolor19 = new JButton("");
		btncolor19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#7193bc"));
			}
		});
		btncolor19.setToolTipText("");
		btncolor19.setPreferredSize(new Dimension(40, 40));
		btncolor19.setOpaque(true);
		btncolor19.setMinimumSize(new Dimension(20, 20));
		btncolor19.setMargin(new Insets(0, 0, 0, 0));
		btncolor19.setForeground(Color.WHITE);
		btncolor19.setBorderPainted(false);
		btncolor19.setBackground(Color.decode("#7193bc"));
		GridBagConstraints gbc_btncolor19 = new GridBagConstraints();
		gbc_btncolor19.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor19.gridx = 23;
		gbc_btncolor19.gridy = 1;
		frame.getContentPane().add(btncolor19, gbc_btncolor19);
		
		JButton btncolor20 = new JButton("");
		btncolor20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicsPanel.setPenColor(Color.decode("#c8c0e6"));
			}
		});
		btncolor20.setToolTipText("");
		btncolor20.setPreferredSize(new Dimension(40, 40));
		btncolor20.setOpaque(true);
		btncolor20.setMinimumSize(new Dimension(20, 20));
		btncolor20.setMargin(new Insets(0, 0, 0, 0));
		btncolor20.setForeground(Color.WHITE);
		btncolor20.setBorderPainted(false);
		btncolor20.setBackground(Color.decode("#c8c0e6"));
		GridBagConstraints gbc_btncolor20 = new GridBagConstraints();
		gbc_btncolor20.insets = new Insets(0, 0, 2, 2);
		gbc_btncolor20.gridx = 24;
		gbc_btncolor20.gridy = 1;
		frame.getContentPane().add(btncolor20, gbc_btncolor20);
		
		listApprovedUsers = new JList();
		listApprovedUsers.setToolTipText("online user list");
		GridBagConstraints gbc_listApprovedUsers = new GridBagConstraints();
		listApprovedUsers.setBackground(Color.decode("#F0F2FB"));
		gbc_listApprovedUsers.gridwidth = 2;
		gbc_listApprovedUsers.insets = new Insets(0, 0, 2, 2);
		gbc_listApprovedUsers.fill = GridBagConstraints.BOTH;
		gbc_listApprovedUsers.gridx = 0;
		gbc_listApprovedUsers.gridy = 2;
		frame.getContentPane().add(listApprovedUsers, gbc_listApprovedUsers);
		
		JButton btnKick = new JButton("Kick Out");
		btnKick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedusername = (String)listApprovedUsers.getSelectedValue();
				if (selectedusername.equals(username)) {
					JOptionPane.showMessageDialog(null, "You can't kick yourself");
				} else if (selectedusername != null) {
					UserList.Kick(selectedusername);
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 2, 2);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 31;
		gbc_scrollPane.gridy = 2;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		textAreaChatOutput = new JTextArea();
		textAreaChatOutput.setBackground(Color.decode("#E5E9FD"));
		textAreaChatOutput.setEditable(false);
		textAreaChatOutput.setLineWrap(true);
		scrollPane.setViewportView(textAreaChatOutput);
		
		
		
		GridBagConstraints gbc_btnKick = new GridBagConstraints();
		btnKick.setForeground(Color.WHITE);
		btnKick.setOpaque(true);
		btnKick.setBackground(Color.decode("#757EAE")) ;
		btnKick.setBorderPainted(false);
		gbc_btnKick.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnKick.gridwidth = 2;
		gbc_btnKick.insets = new Insets(0, 0, 2, 2);
		gbc_btnKick.gridx = 0;
		gbc_btnKick.gridy = 3;
		frame.getContentPane().add(btnKick, gbc_btnKick);
		
		JLabel lblNewLabel_3 = new JLabel("Requests");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(Color.decode("#939AC3")) ;
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 2, 2);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 4;
		frame.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		listPendingUsers = new JList();
		listPendingUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPendingUsers.setToolTipText("request user list");
		GridBagConstraints gbc_listPendingUsers = new GridBagConstraints();
		gbc_listPendingUsers.gridheight = 2;
		listPendingUsers.setBackground(Color.decode("#F0F2FB"));
		gbc_listPendingUsers.gridwidth = 2;
		gbc_listPendingUsers.insets = new Insets(0, 0, 2, 2);
		gbc_listPendingUsers.fill = GridBagConstraints.BOTH;
		gbc_listPendingUsers.gridx = 0;
		gbc_listPendingUsers.gridy = 5;
		frame.getContentPane().add(listPendingUsers, gbc_listPendingUsers);
		

		
		JTextArea textAreaChatInput = new JTextArea();
		textAreaChatInput.setToolTipText("say something to friends");
		textAreaChatInput.setLineWrap(true);
		textAreaChatInput.setBackground(Color.decode("#F0F2FB"));
		GridBagConstraints gbc_textAreaChatInput = new GridBagConstraints();
		gbc_textAreaChatInput.insets = new Insets(0, 0, 0, 2);
		gbc_textAreaChatInput.gridheight = 2;
		gbc_textAreaChatInput.gridwidth = 2;
		gbc_textAreaChatInput.fill = GridBagConstraints.BOTH;
		gbc_textAreaChatInput.gridx = 31;
		gbc_textAreaChatInput.gridy = 6;
		frame.getContentPane().add(textAreaChatInput, gbc_textAreaChatInput);
		btnApprove = new JButton("Agree");
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = (String)listPendingUsers.getSelectedValue();
				if (username != null) {
					UserList.Approve(username);
				}
			}
		});
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String inputStr = textAreaChatInput.getText();
				if (ErrorHandling.verifyStr(inputStr).equals("Valid")) {
					ChatApplication.managerChatInput(inputStr);
					textAreaChatInput.setText(null);
				}
			}
		});
		btnSend.setMinimumSize(new Dimension(40, 29));
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.gridwidth = 2;
		gbc_btnSend.fill = GridBagConstraints.BOTH;
		gbc_btnSend.gridheight = 2;
		gbc_btnSend.gridx = 33;
		gbc_btnSend.gridy = 6;
		btnSend.setHorizontalAlignment(SwingConstants.CENTER);
		btnSend.setForeground(Color.WHITE);
		btnSend.setOpaque(true);
		btnSend.setBackground(Color.decode("#757EAE"));
		btnSend.setBorderPainted(false);
		frame.getContentPane().add(btnSend, gbc_btnSend);
		btnApprove.setMargin(new Insets(0, 0, 0, 0));
		GridBagConstraints gbc_btnApprove = new GridBagConstraints();
		
		btnApprove.setForeground(Color.WHITE);
		btnApprove.setOpaque(true);
		btnApprove.setBackground(Color.decode("#939AC3")) ;
		btnApprove.setBorderPainted(false);
		gbc_btnApprove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApprove.insets = new Insets(0, 0, 0, 2);
		gbc_btnApprove.gridx = 0;
		gbc_btnApprove.gridy = 7;
		frame.getContentPane().add(btnApprove, gbc_btnApprove);
		
		JButton btnReject = new JButton("Reject\n");
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = (String)listPendingUsers.getSelectedValue();
				if (username != null) {
					UserList.Reject(username);
				}
			}
		});
		GridBagConstraints gbc_btnReject = new GridBagConstraints();
		btnReject.setForeground(Color.decode("#939AC3"));
		btnReject.setOpaque(true);
		btnReject.setBackground(Color.white) ;
		btnReject.setBorderPainted(false);
//		btnNewButton_2.setB;
		gbc_btnReject.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReject.insets = new Insets(0, 0, 0, 2);
		gbc_btnReject.gridx = 1;
		gbc_btnReject.gridy = 7;
		frame.getContentPane().add(btnReject, gbc_btnReject);
	}

}


