package hu.mvmxpert.david.giczi.electricwireeditor.service;

import hu.mvmxpert.david.giczi.electricwireeditor.model.SavedWirePoint;


public class PolarPoint {

	
public static double calcX(double distance) {
	double azimuth = calcAzimuth();
	return azimuth != Double.NaN ? SavedWirePoint.START_X + Math.sin(azimuth) * distance : Double.NaN;
}

public static double calcY(double distance) {
	double azimuth = calcAzimuth();
	return azimuth != Double.NaN ? SavedWirePoint.START_Y + Math.cos(azimuth) * distance : Double.NaN;
}

private static double calcAzimuth() {
	
	double deltaX = SavedWirePoint.END_X - SavedWirePoint.START_X;
	double deltaY = SavedWirePoint.END_Y - SavedWirePoint.START_Y;
	
	if( deltaX >= 0 && deltaY > 0 ) {
		return Math.atan(deltaX / deltaY);
	}
	else if( deltaX >= 0 &&  0 > deltaY ) {
		return Math.PI - Math.atan(deltaX / Math.abs(deltaY));
	}
	else if( 0 >= deltaX && 0 > deltaY ) {
		return Math.PI + Math.atan(Math.abs(deltaX) / Math.abs(deltaY));
	}
	else if( 0 >= deltaX && deltaY > 0 ) {
		return 2 * Math.PI - Math.atan(Math.abs(deltaX) / deltaY);
	}
	else if( deltaX > 0 && deltaY == 0 ) {
		return Math.PI / 2;
	}
	else if( 0 > deltaX && deltaY == 0 ) {
		return 3 * Math.PI / 2;
	}
	
	return Double.NaN;
}

	
}
