package hu.mvmxpert.david.giczi.electricwireeditor.model;

public class WireDifference {
	
	private String id;
	private double difference;
	private double differenceLimit;
	public WireDifference() {
		
	}
	
	public WireDifference(String id, double difference) {
		
		this.id = id;
		this.difference = difference;
	}

	public String getId() {
		return id;
	}

	public double getDifference() {
		return difference;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setDifference(double difference) {
		this.difference = difference;
	}

	public double getDifferenceLimit() {
		return differenceLimit;
	}

	public void setDifferenceLimit(double differenceLimit) {
		this.differenceLimit = differenceLimit;
	}

	@Override
	public String toString() {
		return "WireDifference [id=" + id + ", difference=" + difference + ", differenceLimit=" + differenceLimit + "]";
	}

	
	

}
