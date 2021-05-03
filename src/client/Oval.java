package client;

import java.awt.Color;
import java.awt.Graphics;

public class Oval extends Shape{
	public Oval() {
		super();
	}
	
	public Oval(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}


	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(myColor);
		int w = Math.abs(x2-x1);
		int h = Math.abs(y2-y1);
		g.drawOval(Math.min(x1,x2), Math.min(y1,y2), w, h);
	}
}