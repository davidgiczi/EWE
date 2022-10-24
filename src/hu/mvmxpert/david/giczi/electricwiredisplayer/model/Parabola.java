package hu.mvmxpert.david.giczi.electricwiredisplayer.model;

import javax.naming.directory.InvalidAttributesException;

public class Parabola {

	private double paramA;
	private double paramB;
	private double paramC;
	private WirePoint point1;
	private WirePoint point2;
	private WirePoint point3;
	
	public Parabola(WirePoint point1, WirePoint point2, WirePoint point3) throws InvalidAttributesException {
		
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
		calcParams();
	}
	
	private void calcParams() throws InvalidAttributesException {
		
	if(point2.getDistanceOfWirePoint() - point1.getDistanceOfWirePoint() == 0)
		throw new InvalidAttributesException();
	if(point2.getDistanceOfWirePoint() - point3.getDistanceOfWirePoint() == 0)
		throw new InvalidAttributesException();
	if(point3.getDistanceOfWirePoint() - point1.getDistanceOfWirePoint() == 0)
		throw new InvalidAttributesException();
	
	paramA = (point2.getElevationOfWirePoint() - point1.getElevationOfWirePoint()) / 
			(point2.getDistanceOfWirePoint() - point1.getDistanceOfWirePoint()) * (point2.getDistanceOfWirePoint() - point3.getDistanceOfWirePoint()) -
			(point3.getElevationOfWirePoint() - point1.getElevationOfWirePoint()) / 
			(point3.getDistanceOfWirePoint() - point1.getDistanceOfWirePoint()) * (point2.getDistanceOfWirePoint() - point3.getDistanceOfWirePoint());
	double paramB1 = (point2.getElevationOfWirePoint() - point1.getElevationOfWirePoint()) / (point2.getDistanceOfWirePoint() - point1.getDistanceOfWirePoint()) -
			paramA * (point2.getDistanceOfWirePoint() + point1.getDistanceOfWirePoint());
	double paramB2 = (point3.getElevationOfWirePoint() - point1.getElevationOfWirePoint()) / (point3.getDistanceOfWirePoint() - point1.getDistanceOfWirePoint()) -
			paramA * (point3.getDistanceOfWirePoint() + point1.getDistanceOfWirePoint());
	paramB = (paramB1 + paramB2) / 2;	
	double paramC1 = point1.getElevationOfWirePoint() - paramA * point1.getDistanceOfWirePoint() * point1.getDistanceOfWirePoint() -
			paramB * point1.getDistanceOfWirePoint();
	double paramC2 = point2.getElevationOfWirePoint() - paramA * point2.getDistanceOfWirePoint() * point2.getDistanceOfWirePoint() -
			paramB * point2.getDistanceOfWirePoint();
	double paramC3 = point3.getElevationOfWirePoint() - paramA * point3.getDistanceOfWirePoint() * point3.getDistanceOfWirePoint() -
			paramB * point3.getDistanceOfWirePoint();
	paramC = (paramC1 + paramC2 + paramC3) / 3;
	}
	
	public double getElevation(double distance) {
		return paramA * distance * distance + paramB * distance + paramC;
	}

	public double getParamA() {
		return paramA;
	}

	public double getParamB() {
		return paramB;
	}

	public double getParamC() {
		return paramC;
	}
	
}
