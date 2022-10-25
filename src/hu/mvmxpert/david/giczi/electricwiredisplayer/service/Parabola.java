package hu.mvmxpert.david.giczi.electricwiredisplayer.service;


import java.text.DecimalFormat;

import javax.naming.directory.InvalidAttributesException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.model.WirePoint;

public class Parabola {

	private double leftA;
	private double rightA;
	private WirePoint startPoint;
	private WirePoint mediumPoint;
	private WirePoint lastPoint;
	
	public Parabola(WirePoint startPoint, WirePoint mediumPoint, WirePoint lastPoint) throws InvalidAttributesException {
		
		this.startPoint = startPoint;
		this.mediumPoint = mediumPoint;
		this.lastPoint = lastPoint;
		calcParams();
	}

	private void calcParams() throws InvalidAttributesException {
		
		if( mediumPoint.getDistanceOfWirePoint() - startPoint.getDistanceOfWirePoint() == 0 )
			throw new InvalidAttributesException();
		if( lastPoint.getDistanceOfWirePoint() - mediumPoint.getDistanceOfWirePoint() == 0 )
			throw new InvalidAttributesException();
		
		WirePoint left = new WirePoint(startPoint.getDistanceOfWirePoint() - mediumPoint.getDistanceOfWirePoint(), 
				startPoint.getElevationOfWirePoint() - mediumPoint.getElevationOfWirePoint());
		WirePoint right = new WirePoint(lastPoint.getDistanceOfWirePoint() - mediumPoint.getDistanceOfWirePoint(), 
				lastPoint.getElevationOfWirePoint() - mediumPoint.getElevationOfWirePoint());
		
		leftA = left.getElevationOfWirePoint() /  (left.getDistanceOfWirePoint() * left.getDistanceOfWirePoint());
		rightA = right.getElevationOfWirePoint() / (right.getDistanceOfWirePoint() * right.getDistanceOfWirePoint());
	}
	
	public double getElevationOfLeftSideOfParabola(double distance) {
		return leftA * distance * distance;
	}
	
	public double getElevationOfRightSideOfParabola(double distance) {
		return rightA * distance * distance;
	}

	public static void main(String[] args) throws InvalidAttributesException {
		Parabola p = new Parabola(new WirePoint(0, 180), new WirePoint(60, 160), new WirePoint(100, 180));
		DecimalFormat f = new DecimalFormat("0.00");
		for(int i = 0; i >= -60; i--) {
			System.out.println(i + " " + f.format(p.getElevationOfLeftSideOfParabola(i)) + " 0");
		}
		for(int i = 0; i <= 40; i++) {
			System.out.println(i + " " + f.format(p.getElevationOfRightSideOfParabola(i)) + " 0");
		}
	}
	
}
