package hu.mvmxpert.david.giczi.electricwireeditor.model;

import hu.mvmxpert.david.giczi.electricwireeditor.service.ArchivFileBuilder;


public class LineData {

	private int id;
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private double red;
	private double green;
	private double blue;
	private double opacity;
	private String width;
	private String type;
	
	
	public LineData(double startX, double startY, double endX, double endY, String type, 
			double red, double green, double blue, double opacity, String width) {
		this.id = ArchivFileBuilder.addID();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.type = type;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.opacity = opacity;
		this.width = width;
	}

	public String getLineData() {
		return  "Line#" + startX + "#" + startY + "#" + endX + "#" + endY + 
				"#" + type + "#" + red + "#" + green + "#" + blue + "#" + opacity + "#" + width;
 	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	public double getEndX() {
		return endX;
	}

	public void setEndX(double endX) {
		this.endX = endX;
	}

	public double getEndY() {
		return endY;
	}

	public void setEndY(double endY) {
		this.endY = endY;
	}
	
	public double getRed() {
		return red;
	}

	public void setRed(double red) {
		this.red = red;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(double green) {
		this.green = green;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(double blue) {
		this.blue = blue;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
