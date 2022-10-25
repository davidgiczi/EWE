package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

import javax.naming.directory.InvalidAttributesException;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.WirePoint;

public class Parabola {

	private double leftA;
	private double rightA;
	private WirePoint startPoint;
	private WirePoint mediumPoint;
	private WirePoint endPoint;
	private WirePoint leftPoint;
	private WirePoint rightPoint;
	
	public Parabola(WirePoint startPoint, WirePoint mediumPoint, WirePoint lastPoint) throws InvalidAttributesException {
		
		this.startPoint = startPoint;
		this.mediumPoint = mediumPoint;
		this.endPoint = lastPoint;
		calcParams();
	}

	private void calcParams() throws InvalidAttributesException {
		
		if( mediumPoint.getDistanceOfWirePoint() - startPoint.getDistanceOfWirePoint() == 0 )
			throw new InvalidAttributesException();
		if( endPoint.getDistanceOfWirePoint() - mediumPoint.getDistanceOfWirePoint() == 0 )
			throw new InvalidAttributesException();
		
		leftPoint = new WirePoint(startPoint.getDistanceOfWirePoint() - mediumPoint.getDistanceOfWirePoint(), 
				startPoint.getElevationOfWirePoint() - mediumPoint.getElevationOfWirePoint());
		rightPoint = new WirePoint(endPoint.getDistanceOfWirePoint() - mediumPoint.getDistanceOfWirePoint(), 
				endPoint.getElevationOfWirePoint() - mediumPoint.getElevationOfWirePoint());
		
		leftA = leftPoint.getElevationOfWirePoint() /  (leftPoint.getDistanceOfWirePoint() * leftPoint.getDistanceOfWirePoint());
		rightA = rightPoint.getElevationOfWirePoint() / (rightPoint.getDistanceOfWirePoint() * rightPoint.getDistanceOfWirePoint());
	}
	
	public double getElevationOfLeftSideOfParabola(double distance) {
		return leftA * distance * distance;
	}
	
	public double getElevationOfRightSideOfParabola(double distance) {
		return rightA * distance * distance;
	}

	public WirePoint getLeftPoint() {
		return leftPoint;
	}

	public void setLeftPoint(WirePoint leftPoint) {
		this.leftPoint = leftPoint;
	}

	public WirePoint getRightPoint() {
		return rightPoint;
	}

	public void setRightPoint(WirePoint rightPoint) {
		this.rightPoint = rightPoint;
	}

}
