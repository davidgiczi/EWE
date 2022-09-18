package hu.mvmxpert.david.giczi.electricwiredisplayer.model;

public class PillarData {

	
	private String pillarID;
	private double groundElevation;
	private double topElevetaion;
	private double distanceOfPillar;
	private boolean hasCap;
	
	
	
	public String getPillarID() {
		return pillarID;
	}
	public void setPillarID(String pillarID) {
		this.pillarID = pillarID;
	}
	public double getGroundElevation() {
		return groundElevation;
	}
	public void setGroundElevation(double groundElevation) {
		this.groundElevation = groundElevation;
	}
	public double getTopElevetaion() {
		return topElevetaion;
	}
	public void setTopElevetaion(double topElevetaion) {
		this.topElevetaion = topElevetaion;
	}
	public double getDistanceOfPillar() {
		return distanceOfPillar;
	}
	public void setDistanceOfPillar(double distanceOfPillar) {
		this.distanceOfPillar = distanceOfPillar;
	}
	public boolean isHasCap() {
		return hasCap;
	}
	public void setHasCap(boolean hasCap) {
		this.hasCap = hasCap;
	}
	
	
}
