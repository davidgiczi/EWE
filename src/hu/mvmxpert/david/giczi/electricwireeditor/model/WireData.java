package hu.mvmxpert.david.giczi.electricwireeditor.model;

import java.util.ArrayList;
import java.util.List;

public class WireData implements Comparable<WireData> {

	private int id;
	private double groundElevation;
	private double topElevetaion;
	private double distanceOfWire;
	private List<TextData> wireTextList;
	private boolean hasCap;
	
	public WireData(double groundElevation, double topElevetaion, double distanceOfWire, boolean hasCap) {
		
		this.groundElevation = groundElevation;
		this.topElevetaion = topElevetaion;
		this.distanceOfWire = distanceOfWire;
		this.hasCap = hasCap;
		wireTextList = new ArrayList<>();
	}
	
	public String getWireData() {
		return "Wire#"+  groundElevation + "#" + topElevetaion + "#" + distanceOfWire + "#" + hasCap;
	}
	
	public String getWireTexts() {
		StringBuilder bd = new StringBuilder();
		for (TextData textData : wireTextList) {
			bd.append(textData.getTextData())
			.append("\n");
		}
		return bd.substring(0, bd.length() - 1);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public double getDistanceOfWire() {
		return distanceOfWire;
	}

	public void setDistanceOfWire(double distanceOfWire) {
		this.distanceOfWire = distanceOfWire;
	}

	public List<TextData> getWireTextList() {
		return wireTextList;
	}
	public void setWireTextList(List<TextData> wireTextList) {
		this.wireTextList = wireTextList;
	}
	
	public boolean isHasCap() {
		return hasCap;
	}
	public void setHasCap(boolean hasCap) {
		this.hasCap = hasCap;
	}

	@Override
	public int compareTo(WireData o) {
		return this.getDistanceOfWire() > o.getDistanceOfWire() ? 1 : this.getDistanceOfWire() < o.getDistanceOfWire() ? -1 : 0;
	}

}
