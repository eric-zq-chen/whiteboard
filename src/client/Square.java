package client;

import java.awt.Color;
import java.awt.Graphics;

public class Square extends Shape{
	public Square() {
		super();
	}
	
	public Square(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}


	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(myColor);
		int w = Math.abs(x2-x1);
		int h = Math.abs(y2-y1);
//		int d = (int)Math.sqrt(Math.pow(w, 2)+Math.pow(h, 2));
		int d = Math.min(w,h);
		int x = x1<x2?x1:x1-d;
		int y = y1<y2?y1:y1-d;
		g.drawRect(x,y, d, d);
	}
}