package client;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
	protected int x1;
	protected int y1;
	protected int x2;
	protected int y2;
	protected Color myColor;
	
	public Shape() {
		this(0,0,0,0,Color.BLACK);		
	}
	
	public Shape(int x1, int y1, int x2, int y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.myColor = color;
	}
	
	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	public void setY1(int y1) {
		this.y1 = y1;
	}
	
	public void setX2(int x2) {
		this.x2 = x2;
	}
	
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public int getX1() {
		return this.x1;
	}
	
	public int getY1() {
		return this.y1;
	}
	
	public int getX2() {
		return this.x2;
	}
	
	public int getY2() {
		return this.y2;
	}
	public abstract void draw(Graphics g);

}
