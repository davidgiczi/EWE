package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

import javax.naming.directory.InvalidAttributesException;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.WirePoint;

public class HalfParabola {

	private double paramA;
	private WirePoint zeroPoint;
	private WirePoint origoPoint;
	private WirePoint origo;
	
	public HalfParabola(WirePoint zeroPoint, WirePoint origoPoint) throws InvalidAttributesException {
		this.zeroPoint = zeroPoint;
		this.origoPoint = origoPoint;
		calcParamA();
	}

	private void calcParamA() throws InvalidAttributesException {
		
		if( origoPoint.getDistanceOfWirePoint() - zeroPoint.getDistanceOfWirePoint() == 0 )
			throw new InvalidAttributesException();
		
		origo = new WirePoint(zeroPoint.getDistanceOfWirePoint() - origoPoint.getDistanceOfWirePoint(), 
							 zeroPoint.getElevationOfWirePoint() - origoPoint.getElevationOfWirePoint());
		
		paramA = origo.getElevationOfWirePoint() /  ( origo.getDistanceOfWirePoint() * origo.getDistanceOfWirePoint() );
	}
	
	public double getElevationOfHalfParabolaPoint(double distance) {
		return paramA * distance * distance;
	}

	public WirePoint getOrigo() {
		return origo;
	}
}
