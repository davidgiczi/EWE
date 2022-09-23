package hu.mvmxpert.david.giczi.electricwiredisplayer.model;

public class LineData {

	private int id;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int lenghtOfLine;
	private String typeOfLine;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getEndX() {
		return endX;
	}
	public void setEndX(int endX) {
		this.endX = endX;
	}
	public int getEndY() {
		return endY;
	}
	public void setEndY(int endY) {
		this.endY = endY;
	}
	public int getLenghtOfLine() {
		return lenghtOfLine;
	}
	public void setLenghtOfLine(int lenghtOfLine) {
		this.lenghtOfLine = lenghtOfLine;
	}
	public String getTypeOfLine() {
		return typeOfLine;
	}
	public void setTypeOfLine(String typeOfLine) {
		this.typeOfLine = typeOfLine;
	}

}
