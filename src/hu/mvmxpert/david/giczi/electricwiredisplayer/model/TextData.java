package hu.mvmxpert.david.giczi.electricwiredisplayer.model;

public class TextData {

	private int id;
	private String textValue;
	private double X;
	private double Y;
	private int size;
	private int direction;
	
	public TextData(String textValue, double x, double y, int size, int direction) {
		
		this.textValue = textValue;
		X = x;
		Y = y;
		this.size = size;
		this.direction = direction;
	}

	public String getTextData() {
		return "Text\t" + textValue + "\t" + X + "\t" + Y + "\t" + size + "\t" + direction;
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
	
	
	
	
	
}
