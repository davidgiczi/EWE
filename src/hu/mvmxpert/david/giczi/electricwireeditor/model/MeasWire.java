package hu.mvmxpert.david.giczi.electricwireeditor.model;

public class MeasWire {

	
	private int wireType;
	private String wireId;
	private MeasPoint groundPoint;
	private MeasPoint VEZPoint;
	private MeasPoint SDRPoint;
	private double distanceOfWire;
	
	
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
	public double getDistanceOfWire() {
		return distanceOfWire;
	}

	public String getWireId() {
		return wireId;
	}
	public void setWireId(String wireId) {
		this.wireId = wireId;
	}
	public void setDistanceOfWire(MeasPoint grabPoint) {
		this.distanceOfWire = Math.sqrt(Math.pow(this.SDRPoint.pointX - grabPoint.pointX, 2) + Math.pow(this.SDRPoint.pointY - grabPoint.pointY, 2));
	}
	public void calcGroundPoint(MeasPoint startGrabPoint, MeasPoint endGrabPoint) {
		this.groundPoint = new MeasPoint(wireId, ((startGrabPoint.pointX + endGrabPoint.pointX) / 2.0),
										((startGrabPoint.pointY + endGrabPoint.pointY) / 2.0), 
										((startGrabPoint.pointZ + endGrabPoint.pointZ) / 2.0));
	}
	
	@Override
	public String toString() {
		return "MeasWire [wireType=" + wireType + ", wireId=" + wireId + ", groundPoint=" + groundPoint + ", VEZPoint="
				+ VEZPoint + ", SDRPoint=" + SDRPoint + ", distanceOfWire=" + distanceOfWire + "]";
	}
	
}
