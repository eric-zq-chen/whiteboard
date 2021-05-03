package client;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

public class shapeData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Type{
		LINE, RECTANGLE, SQUARE, CIRCLE, OVAL, TEXT
	};
	
	public Type type;
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	public Color color;
	public int width;
	public int fontSize;
	public String text;
	
	public shapeData() {
		this.type = Type.LINE;
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		this.color = Color.WHITE;
		this.width = 0;
		this.fontSize = 0;
		this.text = "";
	}
	
	public shapeData(Type t, int x1, int y1, int x2, int y2, Color c, int w, int f, String s) {
		this.type = t;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = c;
		this.width = w;
		this.fontSize = f;
		this.text = s;
		
	}
	
}
