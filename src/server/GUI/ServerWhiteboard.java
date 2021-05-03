package server.GUI;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;

import client.DrawPanel;
import client.DrawPanelServer;

import java.awt.GridBagLayout;
import java.awt.Insets;

public class ServerWhiteboard {

	public JFrame frame;
	public static DrawPanel graphicsPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWhiteboard window = new ServerWhiteboard();
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
	public ServerWhiteboard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 615);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {792};
		gridBagLayout.rowHeights = new int[]{537};
		gridBagLayout.columnWeights = new double[]{0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		graphicsPanel = new DrawPanel();
		graphicsPanel.setLayout(null);
		GridBagConstraints gbc_graphicsPanel = new GridBagConstraints();
		gbc_graphicsPanel.fill = GridBagConstraints.BOTH;
		gbc_graphicsPanel.insets = new Insets(0, 0, 2, 2);
		gbc_graphicsPanel.gridx = 0;
		gbc_graphicsPanel.gridy = 0;
		gbc_graphicsPanel.gridwidth = 1;
		gbc_graphicsPanel.gridheight = 1;
		frame.getContentPane().add(graphicsPanel, gbc_graphicsPanel);
		graphicsPanel.removeMouseListener(graphicsPanel.mouseHandler);
		graphicsPanel.removeMouseMotionListener(graphicsPanel.mouseHandler);
	}

}
