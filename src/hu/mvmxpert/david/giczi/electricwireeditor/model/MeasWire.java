package hu.mvmxpert.david.giczi.electricwireeditor.model;

public class MeasWire {

	
	private int wireType;
	private MeasPoint groundPoint;
	private MeasPoint VEZPoint;
	private MeasPoint SDRPoint;
	private double distanceOfwire;
	
	
	public int getWireType() {
		return wireType;
	}
	public void setWireType(int wireType) {
		this.wireType = wireType;
	}
	public MeasPoint getGroundPoint() {
		return groundPoint;
	}
	public void setGroundPoint(MeasPoint groundPoint) {
		this.groundPoint = groundPoint;
	}
	public MeasPoint getVEZPoint() {
		return VEZPoint;
	}
	public void setVEZPoint(MeasPoint vEZPoint) {
		VEZPoint = vEZPoint;
	}
	public MeasPoint getSDRPoint() {
		return SDRPoint;
	}
	public void setSDRPoint(MeasPoint sDRPoint) {
		SDRPoint = sDRPoint;
	}
	public double getDistanceOfwire() {
		return distanceOfwire;
	}
	public void setDistanceOfwire(MeasPoint grabPoint) {
		this.distanceOfwire = Math.sqrt(Math.pow(this.SDRPoint.pointX - grabPoint.pointX, 2) + Math.pow(this.SDRPoint.pointY - grabPoint.pointY, 2));
	}
	@Override
	public String toString() {
		return "MaesWire [wireType=" + wireType + ", groundPoint=" + groundPoint + ", VEZPoint=" + VEZPoint
				+ ", SDRPoint=" + SDRPoint + ", distanceOfwire=" + distanceOfwire + "]";
	}
	

}
