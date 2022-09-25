package hu.mvmxpert.david.giczi.electricwiredisplayer.model;

public class DrawingSystemData {


	private int elevationStartValue;
	private int verticalScale;
	private double lengthOfHorizontalAxis;
	private int horizontalScale;
	
	
	public DrawingSystemData() {
	}
	
	public DrawingSystemData(int elevationStartValue, int verticalScale,
			double lengthOfHorizontalAxis, int horizontalScale) {
		this.elevationStartValue = elevationStartValue;
		this.verticalScale = verticalScale;
		this.lengthOfHorizontalAxis = lengthOfHorizontalAxis;
		this.horizontalScale = horizontalScale;
	}

	public String getDrawingSystemData() {
		return "System\t" + lengthOfHorizontalAxis + "\t" + horizontalScale + "\t" + elevationStartValue + "\t" + verticalScale;
	}
	
	public int getElevationStartValue() {
		return elevationStartValue;
	}
	public void setElevationStartValue(int elevationStartValue) {
		this.elevationStartValue = elevationStartValue;
	}
	public int getVerticalScale() {
		return verticalScale;
	}
	public void setVerticalScale(int verticalScale) {
		this.verticalScale = verticalScale;
	}
	public double getLengthOfHorizontalAxis() {
		return lengthOfHorizontalAxis;
	}
	public void setLengthOfHorizontalAxis(double lengthOfHorizontalAxis) {
		this.lengthOfHorizontalAxis = lengthOfHorizontalAxis;
	}
	public int getHorizontalScale() {
		return horizontalScale;
	}
	public void setHorizontalScale(int horizontalScale) {
		this.horizontalScale = horizontalScale;
	}
	
}
