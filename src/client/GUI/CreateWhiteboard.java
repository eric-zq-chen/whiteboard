package client.GUI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateWhiteboard {

	public static JFrame frame;
	private JLabel lblTitle;
	private JTextField textFieldNew;
	private JComboBox comboBoxWhiteboard;
	private JButton btnNew;
	private JButton btnOpen;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateWhiteboard window = new CreateWhiteboard();
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
	public CreateWhiteboard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#f1f6fe"));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{74, 0, 0, 79, 136, 65, 0, 0};
		gridBagLayout.rowHeights = new int[]{30, 30, 31, 35, 30, 35, 37, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		lblTitle = new JLabel("Create or Open an existing whiteboard");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitle.setForeground(Color.decode("#757EAE")) ;
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 4;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 2;
		gbc_lblTitle.gridy = 1;
		frame.getContentPane().add(lblTitle, gbc_lblTitle);
		
		textFieldNew = new JTextField();
		GridBagConstraints gbc_textFieldNew = new GridBagConstraints();
		gbc_textFieldNew.gridwidth = 3;
		gbc_textFieldNew.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNew.fill = GridBagConstraints.BOTH;
		gbc_textFieldNew.gridx = 2;
		gbc_textFieldNew.gridy = 3;
		frame.getContentPane().add(textFieldNew, gbc_textFieldNew);
		textFieldNew.setColumns(10);
		
		btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String whiteboardName = textFieldNew.getText();
				String result = client.ErrorHandling.verifyStr(whiteboardName);
				if (result!="Valid") {
					lblStatus.setText("Whiteboard name is empty");
					return;
				}
				else {
					//load whiteboard screen...
					lblStatus.setText("Creating Whiteboard...");
					whiteboardName = whiteboardName.trim();
					whiteboardName = whiteboardName.toLowerCase();
//					client.Navigation.createWhiteboard(whiteboardName);
				}
			}
		});
		btnNew.setBorder(new LineBorder(Color.decode("#757EAE")));
		btnNew.setForeground(new Color(255, 255, 255));
		btnNew.setBackground(Color.decode("#757EAE"));
		GridBagConstraints gbc_btnNew = new GridBagConstraints();
		gbc_btnNew.fill = GridBagConstraints.BOTH;
		gbc_btnNew.insets = new Insets(0, 0, 5, 5);
		gbc_btnNew.gridx = 5;
		gbc_btnNew.gridy = 3;
		frame.getContentPane().add(btnNew, gbc_btnNew);
		
		comboBoxWhiteboard = new JComboBox();
		GridBagConstraints gbc_comboBoxWhiteboard = new GridBagConstraints();
		gbc_comboBoxWhiteboard.gridwidth = 3;
		gbc_comboBoxWhiteboard.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxWhiteboard.fill = GridBagConstraints.BOTH;
		gbc_comboBoxWhiteboard.gridx = 2;
		gbc_comboBoxWhiteboard.gridy = 5;
		frame.getContentPane().add(comboBoxWhiteboard, gbc_comboBoxWhiteboard);
		
		btnOpen = new JButton("Open");
		btnOpen.setBorder(new LineBorder(Color.decode("#757EAE")));
		btnOpen.setForeground(new Color(255, 255, 255));
		btnOpen.setBackground(Color.decode("#757EAE"));
		GridBagConstraints gbc_btnOpen = new GridBagConstraints();
		gbc_btnOpen.fill = GridBagConstraints.BOTH;
		gbc_btnOpen.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpen.gridx = 5;
		gbc_btnOpen.gridy = 5;
		frame.getContentPane().add(btnOpen, gbc_btnOpen);
		
		lblStatus = new JLabel("Status:.....");
		lblStatus.setForeground(Color.decode("#757EAE")) ;
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.gridwidth = 2;
		gbc_lblStatus.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatus.gridx = 3;
		gbc_lblStatus.gridy = 7;
		frame.getContentPane().add(lblStatus, gbc_lblStatus);
	}

}
