package client.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Login {

	public static JFrame frame;
	public static JButton btnConnect;
	private JLabel lblTitle;
	public static JLabel lblStatus;
	private JTextField textFieldHostname;
	private JTextField textFieldPort;
	private JTextField textFieldUsername;
	private JLabel lblHostname;
	private JLabel lblPort;
	private JLabel lblUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
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
		gridBagLayout.columnWidths = new int[]{69, 30, 30, 30, 30, 30, 51, 30, 30, 30, 30};
		gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		lblTitle = new JLabel("Connect To Server");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitle.gridwidth = 4;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 3;
		gbc_lblTitle.gridy = 1;
		lblTitle.setForeground(Color.decode("#757EAE")) ;
		frame.getContentPane().add(lblTitle, gbc_lblTitle);
		
		lblHostname = new JLabel("Host Name");
		lblHostname.setFocusCycleRoot(true);
		lblHostname.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_lblHostname = new GridBagConstraints();
		gbc_lblHostname.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostname.anchor = GridBagConstraints.WEST;
		gbc_lblHostname.gridx = 3;
		gbc_lblHostname.gridy = 3;
		lblHostname.setForeground(Color.decode("#757EAE")) ;
		frame.getContentPane().add(lblHostname, gbc_lblHostname);
		
		textFieldHostname = new JTextField();
		textFieldHostname.setText("localhost");
		textFieldHostname.setBorder(new LineBorder(Color.decode("#757EAE")));
		GridBagConstraints gbc_textFieldHostname = new GridBagConstraints();
		gbc_textFieldHostname.gridwidth = 3;
		gbc_textFieldHostname.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHostname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHostname.gridx = 4;
		gbc_textFieldHostname.gridy = 3;
		frame.getContentPane().add(textFieldHostname, gbc_textFieldHostname);
		textFieldHostname.setColumns(10);
		
		lblPort = new JLabel("Port Number");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.gridx = 3;
		gbc_lblPort.gridy = 4;
		lblPort.setForeground(Color.decode("#757EAE")) ;
		frame.getContentPane().add(lblPort, gbc_lblPort);
		
		textFieldPort = new JTextField();
		textFieldPort.setText("1099");
		textFieldPort.setBorder(new LineBorder(Color.decode("#757EAE")));
		GridBagConstraints gbc_textFieldPort = new GridBagConstraints();
		gbc_textFieldPort.gridwidth = 3;
		gbc_textFieldPort.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPort.gridx = 4;
		gbc_textFieldPort.gridy = 4;
		frame.getContentPane().add(textFieldPort, gbc_textFieldPort);
		textFieldPort.setColumns(10);
		
		lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.gridx = 3;
		gbc_lblUsername.gridy = 5;
		lblUsername.setForeground(Color.decode("#757EAE")) ;
		frame.getContentPane().add(lblUsername, gbc_lblUsername);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setText("manager");
		textFieldUsername.setBorder(new LineBorder(Color.decode("#757EAE")));
		GridBagConstraints gbc_textFieldUsername = new GridBagConstraints();
		gbc_textFieldUsername.gridwidth = 3;
		gbc_textFieldUsername.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUsername.gridx = 4;
		gbc_textFieldUsername.gridy = 5;
		frame.getContentPane().add(textFieldUsername, gbc_textFieldUsername);
		textFieldUsername.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// needs to simplify more on validation shouldn't need to trim again
				String hostname = textFieldHostname.getText();
				String result = client.ErrorHandling.verifyStr(hostname);
				if(result != "Valid") {
					lblStatus.setText("Hostname is empty");
					return;
				}
				String portStr = textFieldPort.getText();
				result = client.ErrorHandling.verifyPort(portStr);
				if (result!="Valid") {
					lblStatus.setText(result);
					return;
				}
				String username = textFieldUsername.getText();
				result = client.ErrorHandling.verifyStr(username);
				if (result!="Valid") {
					lblStatus.setText("Username is empty");
					return;
				}
				lblStatus.setText("Connecting to server..."); // not yet implemented
				
				
				
				//temporary validation
				hostname = hostname.trim();
				hostname = hostname.toLowerCase();
				portStr = portStr.trim();
				int port = Integer.parseInt(portStr);
				username = username.trim();
				username = username.toLowerCase();
				client.Navigation.login(hostname, port, username);
				
			}
		});
		btnConnect.setBorder(new LineBorder(Color.decode("#757EAE")));
		btnConnect.setForeground(new Color(255, 255, 255));
		btnConnect.setBackground(Color.decode("#757EAE"));
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.fill = GridBagConstraints.BOTH;
		gbc_btnConnect.gridwidth = 4;
		gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnect.gridx = 3;
		gbc_btnConnect.gridy = 7;
		frame.getContentPane().add(btnConnect, gbc_btnConnect);
		
		lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridwidth = 8;
		gbc_lblStatus.gridx = 1;
		gbc_lblStatus.gridy = 8;
		lblStatus.setForeground(Color.decode("#757EAE")) ;
		frame.getContentPane().add(lblStatus, gbc_lblStatus);
	}

}
