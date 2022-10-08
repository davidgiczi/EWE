package hu.mvmxpert.david.giczi.electricwiredisplayer.model;

public class TextData {

	private int id;
	private String textValue;
	private double X;
	private double Y;
	private int size;
	private int direction;
	private String type;
	
	public TextData() {
	}
	
	public TextData(String textValue, double x, double y, int size, int direction, String type) {
		
		this.textValue = textValue;
		X = x;
		Y = y;
		this.size = size;
		this.direction = direction;
		this.type = type;
	}

	public String getTextData() {
		return type + "#" + textValue + "#" + X + "#" + Y + "#" + size + "#" + direction;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTextValue() {
		return textValue;
	}
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	
	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
