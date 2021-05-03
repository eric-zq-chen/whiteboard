package client.GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import client.DrawPanel;
import client.DrawPanel.ShapeType;

public class DrawFrame extends JFrame{
	private final int WIDTH = 500;
	private final int HEIGHT = 300;
	
	private JButton undo;
	private JButton clear;
	
	private JComboBox colorsbox;
	private JComboBox shapesbox;
	
	private JCheckBox filledbox;
	
	private JPanel selectionPanel;
	
	private static final String [] shapesToDraw = {"Line", "Freepen", "Oval", "Rectangle","Circle", "Square"};
	private static final String [] colorChoice = {"Black"};
	private static final Color [] colors = {Color.BLACK};
	
	private JPanel panelText;
	private DrawPanel graphicsPanel;
	
	public DrawFrame() {
		createSelectionPanel();
		createGraphicsPanel();
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void createSelectionPanel() {
		selectionPanel = new JPanel();
		undo = new JButton("Undo");
		clear = new JButton("Clear");
		
		colorsbox = new JComboBox(colorChoice);
		shapesbox = new JComboBox(shapesToDraw);
		
		selectionPanel.add(undo);
		selectionPanel.add(clear);
		selectionPanel.add(colorsbox);
		selectionPanel.add(shapesbox);
		this.add(selectionPanel,BorderLayout.NORTH);
		
		ShapesBoxHandler actionListener = new ShapesBoxHandler();
		shapesbox.addActionListener(actionListener);
	}
	
	private void createGraphicsPanel() {
		graphicsPanel = new DrawPanel();
		add(graphicsPanel, BorderLayout.CENTER);
		
	}
	
	private class UndoButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	private class ClearButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	private class ColorBoxHandler implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			
		}
	}
	
	private class ShapesBoxHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
			System.out.println(cb.getSelectedItem());
			if (shapesbox.getSelectedItem() == "Line") {				
				graphicsPanel.setShapeType(ShapeType.LINE);
			}else if (shapesbox.getSelectedItem() == "Freepen") {
				graphicsPanel.setShapeType(ShapeType.FREEPEN);
			}else if (shapesbox.getSelectedItem() == "Oval") {
				graphicsPanel.setShapeType(ShapeType.OVAL);
			}else if (shapesbox.getSelectedItem() == "Circle") {
				graphicsPanel.setShapeType(ShapeType.CIRCLE);
			}else if (shapesbox.getSelectedItem() == "Rectangle") {
				graphicsPanel.setShapeType(ShapeType.RECTANGLE);
			}else if (shapesbox.getSelectedItem() == "Square") {
				graphicsPanel.setShapeType(ShapeType.SQUARE);
			}
		}
		
	}
	
	public static void main(String args[]) {
		DrawFrame draw;
		draw = new DrawFrame();
	}
}
