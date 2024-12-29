package hu.mvmxpert.david.giczi.electricwireeditor.service;

import hu.mvmxpert.david.giczi.electricwireeditor.model.MeasPoint;

public class AzimuthAndDistance {
	
	private MeasPoint pointA;
	private MeasPoint pointB;
	
	public AzimuthAndDistance(MeasPoint pointA, MeasPoint pointB) {	
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	public double calcAzimuth() {
		
		double deltaX = pointB.pointX - pointA.pointX;
		double deltaY = pointB.pointY - pointA.pointY;
		
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
	 
	public double calcDistance() {
		return Math.sqrt(Math.pow(pointA.pointX - pointB.pointX, 2)
				+ Math.pow(pointA.pointY - pointB.pointY, 2));
	}

	public MeasPoint getPointA() {
		return pointA;
	}

	public MeasPoint getPointB() {
		return pointB;
	}
	
	
}
