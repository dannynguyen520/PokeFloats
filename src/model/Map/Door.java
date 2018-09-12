package model.Map;

import java.awt.Point;

public class Door {

	private Map to;
	private Point from;
	
	public Door(Map to, Point from) {
		this.to = to;
		this.from = from;
	}
	
	public Map getToMap(){
		return to;
	}
	
	public Point getFromPoint() {
		return from;
	}
	
}
