package client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import client.shapeData.Type;

public class DrawPanel extends JPanel implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	private ArrayList<Shape> shapes;
	public enum ShapeType {
		LINE, OVAL, RECTANGLE, CIRCLE, FREEPEN, SQUARE, ERASER, TEXT, TEXTBOX
	};

	private ShapeType shapeType;
	private Shape currentShape;
	public Color currentColor;
	private int currentSize;
	private String text;
	private int currentFontSize;
	private Color backgroundColor;
//	private JLabel statusLabel;
	private int freeX1, freeY1, freeX2, freeY2;
	private int textX, textY;
	private Image image;
	public Graphics2D g2d;
	private Boolean IsDragged;
	public static shapeData lastShape;
	public static boolean isAction;
//	public static shapeData penShape;
	public static List<shapeData> penList = new ArrayList<shapeData>();
	public static boolean isPen;
	public static List testlist = new ArrayList();
	public MouseHandler mouseHandler;
	public DrawPanel() {
//		shapes = new ArrayList<Shape>();
		shapeType = ShapeType.FREEPEN;
		currentShape = null;
		currentColor = Color.BLACK;
		backgroundColor = Color.WHITE;
		currentSize = 1;
		currentFontSize = 24;
		IsDragged = false;
		lastShape = new shapeData();
		isAction = false;
//		penShape = new shapeData();
//		penList = new ArrayList<shapeData>();
		isPen = false;
		setBackground(Color.WHITE);
		mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
		
	}
	
	private class MouseHandler extends MouseAdapter implements MouseMotionListener {
		public void mousePressed(MouseEvent e) {
			switch (shapeType) {
			case LINE:
				currentShape = new Line(e.getX(), e.getY(), e.getX(), e.getY(), currentColor);
				break;
			case FREEPEN:
				currentShape = null;
				freeX1 = e.getX();
				freeY1 = e.getY();
				break;
			case OVAL:
				currentShape = new Oval(e.getX(), e.getY(), e.getX(), e.getY(), currentColor);
				break;
			case CIRCLE:
				currentShape = new Circle(e.getX(), e.getY(), e.getX(), e.getY(), currentColor);
				break;
			case RECTANGLE:
				currentShape = new Rect(e.getX(), e.getY(), e.getX(), e.getY(), currentColor);
				break;
			case SQUARE:
				currentShape = new Square(e.getX(), e.getY(), e.getX(), e.getY(), currentColor);
				break;
			case ERASER:
				currentShape = null;
				freeX1 = e.getX();
				freeY1 = e.getY();
				break;
			case TEXT:
				currentShape = null;
				textX = e.getX();
				textY = e.getY();
//					currentShape = new Rect(e.getX(), e.getY(),e.getX(), e.getY(), currentColor);
//			case TEXTBOX:
//				currentShape = new Rect(e.getX(), e.getY(),e.getX(), e.getY(), currentColor);
				
				break;

			}
		}

		public void mouseReleased(MouseEvent e) {
			if (shapeType == ShapeType.FREEPEN || shapeType == ShapeType.ERASER) {
				if (penList.size()>0) {
					penList.remove(0);
					isPen = true;
					IsDragged = false;
				}
			} else {
				switch (shapeType) {
				case SQUARE:
					lastShape.type = Type.SQUARE;
					lastShape.color = currentColor;
					lastShape.x1 = currentShape.getX1();
					lastShape.y1 = currentShape.getY1();
					lastShape.x2 = currentShape.getX2();
					lastShape.y2 = currentShape.getY2();
					lastShape.width = currentSize;
					break;
				case RECTANGLE:
					lastShape.type = Type.RECTANGLE;
					lastShape.color = currentColor;
					lastShape.x1 = currentShape.getX1();
					lastShape.y1 = currentShape.getY1();
					lastShape.x2 = currentShape.getX2();
					lastShape.y2 = currentShape.getY2();
					lastShape.width = currentSize;
					break;
				case OVAL:
					lastShape.type = Type.OVAL;
					lastShape.color = currentColor;
					lastShape.x1 = currentShape.getX1();
					lastShape.y1 = currentShape.getY1();
					lastShape.x2 = currentShape.getX2();
					lastShape.y2 = currentShape.getY2();
					lastShape.width = currentSize;
					break;
				case CIRCLE:
					lastShape.type = Type.CIRCLE;
					lastShape.color = currentColor;
					lastShape.x1 = currentShape.getX1();
					lastShape.y1 = currentShape.getY1();
					lastShape.x2 = currentShape.getX2();
					lastShape.y2 = currentShape.getY2();
					lastShape.width = currentSize;
					break;
				case LINE:
					lastShape.type = Type.LINE;
					lastShape.color = currentColor;
					lastShape.x1 = currentShape.getX1();
					lastShape.y1 = currentShape.getY1();
					lastShape.x2 = currentShape.getX2();
					lastShape.y2 = currentShape.getY2();
					lastShape.width = currentSize;
					break;
				case TEXT:
					lastShape.type = Type.TEXT;
					lastShape.color = currentColor;
					lastShape.text = text;
					lastShape.x1 = textX;
					lastShape.y1 = textY;
					lastShape.fontSize = currentFontSize;
				}
				if (currentShape != null) {
					currentShape.setX2(e.getX());
					currentShape.setY2(e.getY());
					currentShape.draw(g2d);
					currentShape = null;
					repaint();
				} else if (shapeType == ShapeType.TEXT) {
	//				addTextBox(currentShape.x1,currentShape.y1,currentShape.x2,currentShape.y2);
					g2d.setFont(new Font("TimesRoman", Font.PLAIN, currentFontSize));
					g2d.drawString(text, textX, textY);
					repaint();
				}
	//			else if (shapeType == ShapeType.TEXT) {
	//				addTextBox(currentShape.x1,currentShape.y1,currentShape.x2,currentShape.y2);
	//				g2d.setFont(new Font("TimesRoman", Font.PLAIN, currentFontSize));
	//				g2d.drawString(text, textX, textY);
	//				repaint();
	//			}
				isAction = true;
				IsDragged = false;
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (currentShape != null) {
				currentShape.setX2(e.getX());
				currentShape.setY2(e.getY());
				repaint();
			} else if (shapeType == ShapeType.FREEPEN || shapeType == ShapeType.ERASER) {
				freeX2 = e.getX();
				freeY2 = e.getY();
				
//				lastShape.type = Type.LINE;
//				lastShape.x1 = freeX1;
//				lastShape.y1 = freeY1;
//				lastShape.x2 = freeX2;
//				lastShape.y2 = freeY2;
//				lastShape.width = currentSize;
//				if (shapeType == ShapeType.FREEPEN){
//					lastShape.color = currentColor;
//				} else {
//					lastShape.color = backgroundColor;
//				}
//				
//				
//				isAction = true;
				shapeData penShape = new shapeData();
				penShape.type = Type.LINE;
				penShape.x1 = freeX1;
				penShape.y1 = freeY1;
				penShape.x2 = freeX2;
				penShape.y2 = freeY2;
				penShape.width = currentSize;

				if (shapeType == ShapeType.FREEPEN){
					penShape.color = currentColor;
				} else {
					penShape.color = backgroundColor;
				}
				penList.add(penShape);
				repaint();
				
			} else if (shapeType == ShapeType.TEXT) {
				textX = e.getX();
				textY = e.getY();
				repaint();
			}
			
			IsDragged = true;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (image == null) {

			image = createImage(getSize().width, getSize().height);
			System.out.println(image);
			g2d = (Graphics2D) image.getGraphics();

			clear();
		}

		g2d.setStroke(new BasicStroke(currentSize));
		g2d.setColor(currentColor);
		if (shapeType == ShapeType.FREEPEN && !(freeX1 == freeX2 && freeY1 == freeY2)) {
			g2d.drawLine(freeX1, freeY1, freeX2, freeY2);
			freeX1 = freeX2;
			freeY1 = freeY2;
		} else if (shapeType == ShapeType.ERASER) {
			g2d.setColor(backgroundColor);
			g2d.drawLine(freeX1, freeY1, freeX2, freeY2);
			freeX1 = freeX2;
			freeY1 = freeY2;
			g2d.setColor(currentColor);
		}

		Graphics2D g2Temp = (Graphics2D) g;
		g2Temp.setStroke(new BasicStroke(currentSize));
		g2Temp.setColor(currentColor);

		g.drawImage(image, 0, 0, null);
		if (IsDragged){
			if (shapeType == ShapeType.TEXT) {
				g.setFont(new Font("TimesRoman", Font.PLAIN, currentFontSize));
				g.drawString(text, textX, textY);
			}
			if (shapeType == ShapeType.FREEPEN || shapeType == ShapeType.ERASER) {
				g.setColor(currentColor);
				if (shapeType == ShapeType.ERASER) {
					g.setColor(backgroundColor);
				}
				boolean firstItem = true;
				for (shapeData sd: penList) {
					if (firstItem) {
						firstItem = false;
					} else {
						g.drawLine(sd.x1, sd.y1, sd.x2, sd.y2);
					}
				}
			}
			if (currentShape != null) {
				currentShape.draw(g);
			}
		}

	}

	public void clear() {
		g2d.setPaint(Color.white);
		// draw white on entire draw area to clear
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		g2d.setPaint(Color.black);
		repaint();
	}

	public void setShapeType(ShapeType s) {
		shapeType = s;
	}

	public void setPenColor(Color c) {
		currentColor = c;
	}

	public void setStrokeSize(int w) {
		currentSize = w;
	}

	public void setFontSize(int f) {
		currentFontSize = f;
	}

	public void setBackground(Color c) {

	}

	public void save(File f) throws IOException {
		BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();
		ImageIO.write(bi, "png", f);
	}

	public void load(File f) throws Exception {
		image = ImageIO.read(f);
		g2d = (Graphics2D) image.getGraphics();
		repaint();
	}

	public void setText(String t) {
		text = t;
	}

	public void addTextBox(int x1, int y1, int x2, int y2) {
		JTextArea ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setEditable(true);
		Border border = BorderFactory.createLineBorder(currentColor, currentSize);
		ta.setBounds(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
		ta.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		ta.setOpaque(false);
		ta.setForeground(Color.WHITE);
		this.add(ta);
//		JScrollPane scroll = new JScrollPane(ta);		
//		scroll.setBounds(Math.min(x1,x2), Math.min(y1,y2), Math.abs(x2-x1), Math.abs(y2-y1));	
//		scroll.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//		scroll.getViewport().setBackground(Color.WHITE);
//		scroll.setOpaque(false);
//		this.add(scroll);

	}
	
	public synchronized void copy(DrawPanel d) {
		image = d.image;
		if (image == null) {

			image = createImage(getSize().width, getSize().height);
			g2d = (Graphics2D) image.getGraphics();

			clear();
		} else {
			g2d = (Graphics2D) image.getGraphics();
			
			repaint();
		}
		
	}
	
	public byte[] getImageArray() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write((RenderedImage) image,"png", output);
		byte[] data = output.toByteArray();
		return data;
	}
	
	public void loadImageArray(byte[] ia) throws IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(ia);
	    image = ImageIO.read(bis);
	    repaint();
	}
	
	public void drawData(shapeData sd) {
		
		switch(sd.type) {
		case LINE:
//			System.out.println(sd.type);
//			System.out.println(sd.x1);
			repaint();
			
			g2d.setColor(sd.color);
			g2d.setStroke(new BasicStroke(sd.width));
			Shape current = new Line(sd.x1, sd.y1, sd.x2, sd.y2, sd.color);
			current.draw(g2d);
			repaint();
			
			g2d.setColor(currentColor);
			g2d.setStroke(new BasicStroke(currentSize));
			break;
		case RECTANGLE:
			g2d.setColor(sd.color);
			g2d.setStroke(new BasicStroke(sd.width));
			current = new Rect(sd.x1, sd.y1, sd.x2, sd.y2, sd.color);
			current.draw(g2d);
			repaint();
			
			g2d.setColor(currentColor);
			g2d.setStroke(new BasicStroke(currentSize));
			break;
		case SQUARE:
			g2d.setColor(sd.color);
			g2d.setStroke(new BasicStroke(sd.width));
			current = new Square(sd.x1, sd.y1, sd.x2, sd.y2, sd.color);
			current.draw(g2d);
			repaint();
			
			g2d.setColor(currentColor);
			g2d.setStroke(new BasicStroke(currentSize));
			break;
		case OVAL:
			g2d.setColor(sd.color);
			g2d.setStroke(new BasicStroke(sd.width));
			current = new Oval(sd.x1, sd.y1, sd.x2, sd.y2, sd.color);
			current.draw(g2d);
			repaint();
			
			g2d.setColor(currentColor);
			g2d.setStroke(new BasicStroke(currentSize));
			break;
		case CIRCLE:
			g2d.setColor(sd.color);
			g2d.setStroke(new BasicStroke(sd.width));
			current = new Circle(sd.x1, sd.y1, sd.x2, sd.y2, sd.color);
			current.draw(g2d);
			repaint();
			
			g2d.setColor(currentColor);
			g2d.setStroke(new BasicStroke(currentSize));
			break;
		case TEXT:
			g2d.setColor(sd.color);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, sd.fontSize));
			g2d.drawString(sd.text, sd.x1, sd.y1);
			repaint();
			System.out.println(sd.text + " " + sd.x1 + " " + sd.y1);
			g2d.setColor(currentColor);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, currentFontSize));
			break;
		}
	}

}
