package hu.mvmxpert.david.giczi.electricwireeditor.model;

import java.text.DecimalFormat;

import hu.mvmxpert.david.giczi.electricwireeditor.service.PolarPoint;

public class SavedWirePoint {

	private String pointName;
	public static double START_X;
	public static double START_Y;
	public static double END_X;
	public static double END_Y;
	public static double START_ELEVATION;
	private double X_3D;
	private double Y_3D;
	private double elevation;
	private double x_2D;
	private double h_2D;
	private DecimalFormat format;
	
	public SavedWirePoint(String pointName, double x_2D, double elevation) {
		this.pointName = pointName;
		this.x_2D = x_2D;
		this.elevation = elevation;
		this.h_2D = elevation - START_ELEVATION;
		this.format = new DecimalFormat("0.00");
	}

	public void calcCoords() {
		X_3D = (int) (PolarPoint.calcX(x_2D) * 1000.0) / 1000.0;
		Y_3D = (int) (PolarPoint.calcY(x_2D) * 1000.0) / 1000.0;
	}

	public String get3DCoordDataWithID() {
		return pointName + ","  + format.format(X_3D).replace(',', '.') + "," + format.format(Y_3D).replace(',', '.') + "," + 
	format.format(elevation).replace(',', '.');
	}
	
	public String get2DCoordDataWithID() {
		return pointName + ","  + format.format(x_2D).replace(',', '.') + "," + format.format(h_2D).replace(',', '.');
	}
	
	public String get3DCoordDataWithoutID() {
		return format.format(X_3D).replace(',', '.') + "," + format.format(Y_3D).replace(',', '.') + "," + 
	format.format(elevation).replace(',', '.');
	}
	
	public String get2DCoordDataWithoutID() {
		return format.format(x_2D).replace(',', '.') + "," + format.format(h_2D).replace(',', '.');
	}

}
