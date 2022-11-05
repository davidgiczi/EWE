package hu.mvmxpert.david.giczi.electricwireeditor.model;

public class WirePoint implements Comparable<WirePoint> {

		
	private double distanceOfWirePoint;
	private double elevationOfWirePoint;


	public WirePoint(double distanceOfWirePoint, double elevationOfWirePoint) {
		this.distanceOfWirePoint = distanceOfWirePoint;
		this.elevationOfWirePoint = elevationOfWirePoint;
	}

	public double getDistanceOfWirePoint() {
		return distanceOfWirePoint;
	}

	public void setDistanceOfWirePoint(double distanceOfWirePoint) {
		this.distanceOfWirePoint = distanceOfWirePoint;
	}

	public double getElevationOfWirePoint() {
		return elevationOfWirePoint;
	}

	public void setElevationOfWirePoint(double elevationOfWirePoint) {
		this.elevationOfWirePoint = elevationOfWirePoint;
	}


	@Override
	public int compareTo(WirePoint o) {
		return this.distanceOfWirePoint > o.distanceOfWirePoint ? 1 : this.distanceOfWirePoint < o.distanceOfWirePoint ? -1 : 0;
	}

	@Override
	public String toString() {
		return "WirePoint [distanceOfWirePoint=" + distanceOfWirePoint + ", elevationOfWirePoint="
				+ elevationOfWirePoint + "]";
	}

}
