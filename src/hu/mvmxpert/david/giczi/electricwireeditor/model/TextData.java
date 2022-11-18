package hu.mvmxpert.david.giczi.electricwireeditor.model;

public class TextData {

	private int id;
	private String textValue;
	private double X;
	private double Y;
	private int size;
	private int direction;
	private String type;
	private boolean isOnLeftSide;
	private boolean isAtTop;
	private double red;
	private double blue;
	private double green;
	private double opacity = 1d;
	
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
		return type + "#" + textValue + "#" + X + "#" + Y + "#" + size + "#" + direction + "#" + isOnLeftSide + "#" + isAtTop + 
				"#" + red + "#" + green + "#" + blue + "#" + opacity;
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

	public boolean isOnLeftSide() {
		return isOnLeftSide;
	}

	public void setOnLeftSide(boolean isOnLeftSide) {
		this.isOnLeftSide = isOnLeftSide;
	}

	public boolean isAtTop() {
		return isAtTop;
	}

	public void setAtTop(boolean isAtTop) {
		this.isAtTop = isAtTop;
	}

	public double getRed() {
		return red;
	}

	public void setRed(double red) {
		this.red = red;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(double blue) {
		this.blue = blue;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(double green) {
		this.green = green;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
	
}
