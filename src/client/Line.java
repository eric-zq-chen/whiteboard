package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line extends Shape{
	public Line() {
		super();
	}
	
	public Line(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}


	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(myColor);
//		Graphics2D g2d = (Graphics2D) g;
		try {
			g.drawLine(x1, y1, x2, y2);
		} catch (Exception e) {
			
		}
	}
}
