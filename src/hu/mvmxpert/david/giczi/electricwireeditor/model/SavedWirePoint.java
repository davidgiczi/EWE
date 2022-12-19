package hu.mvmxpert.david.giczi.electricwireeditor.model;

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
	
	public SavedWirePoint(String pointName, double x_2D, double elevation) {
		this.pointName = pointName;
		this.x_2D = x_2D;
		this.elevation = elevation;
		this.h_2D = elevation - START_ELEVATION;
		calcCoords();
	}

	private void calcCoords() {
		if( START_X == 0 && START_Y == 0 && END_X == 0 && END_Y == 0 )
			return;
		X_3D = (int) (PolarPoint.calcX(x_2D) * 1000.0) / 1000.0;
		Y_3D = (int) (PolarPoint.calcY(x_2D) * 1000.0) / 1000.0;
	}

	public String get3DCoordDataWithID() {
		return pointName + ","  + X_3D + "," + Y_3D + "," + elevation;
	}
	
	public String get2DCoordDataWithID() {
		return pointName + ","  + x_2D + "," + h_2D;
	}
	
	public String get3DCoordDataWithoutID() {
		return X_3D + "," + Y_3D + "," + elevation;
	}
	
	public String get2DCoordDataWithoutID() {
		return x_2D + "," + h_2D;
	}

}
