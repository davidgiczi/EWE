package hu.mvmxpert.david.giczi.electricwireeditor.model;

public class WireDifference {
	
	private String id;
	private double difference;
	
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

	@Override
	public String toString() {
		return "WireDifference [id=" + id + ", difference=" + difference + "]";
	}
	

}
