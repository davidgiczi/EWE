package hu.mvmxpert.david.giczi.electricwireeditor.model;

public class MeasPoint implements Comparable<MeasPoint> {
	
	public String pointId;
	public double pointX;
	public double pointY;
	public double pointZ;
	public String pointType;
	public boolean isUpper;
	
	
	public MeasPoint() {
		
	}
	
	public MeasPoint(String pointId, double pointX, double pointY) {
		this.pointId = pointId;
		this.pointX = pointX;
		this.pointY = pointY;
	}
	
	public MeasPoint(double pointX, double pointY) {
		this.pointX = pointX;
		this.pointY = pointY;
	}
	
	public MeasPoint(String pointId, double pointX, double pointY, double pointZ) {
		this.pointId = pointId;
		this.pointX = pointX;
		this.pointY = pointY;
		this.pointZ = pointZ;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public void setPointX(double pointX) {
		this.pointX = pointX;
	}
	public void setPointY(double pointY) {
		this.pointY = pointY;
	}
	public void setPointZ(double pointZ) {
		this.pointZ = pointZ;
	}
	
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public void setUpper(boolean isUpper) {
		this.isUpper = isUpper;
	}
	
	
	@Override
	public String toString() {
		return "MeasPoint [pointId=" + pointId + ", pointX=" + pointX + ", pointY=" + pointY + ", pointZ=" + pointZ
				+ ", pointType=" + pointType + ", isUpper=" + isUpper + "]";
	}

	@Override
	public int compareTo(MeasPoint o) {
		return  this.pointX > o.pointX ? 1 : this.pointX < o.pointX ? -1 : 0;
	}
	
}
