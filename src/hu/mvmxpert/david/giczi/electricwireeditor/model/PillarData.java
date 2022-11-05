package hu.mvmxpert.david.giczi.electricwireeditor.model;

import java.util.ArrayList;
import java.util.List;

public class PillarData {

	
	private int id;
	private double groundElevation;
	private double topElevetaion;
	private double distanceOfPillar;
	private List<TextData> pillarTextList;
	private boolean hasCap;
	
	public PillarData(double groundElevation, double topElevetaion, double distanceOfPillar, boolean hasCap) {

		this.groundElevation = groundElevation;
		this.topElevetaion = topElevetaion;
		this.distanceOfPillar = distanceOfPillar;
		this.hasCap = hasCap;
		pillarTextList = new ArrayList<>();
	}

	public String getPillarData() {
		return "Pillar#" + groundElevation + "#" + topElevetaion + "#" + distanceOfPillar + "#" + hasCap;
	}
	
	public String getPillarTexts() {
		StringBuilder bd = new StringBuilder();
		for (TextData textData : pillarTextList) {
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
	public double getDistanceOfPillar() {
		return distanceOfPillar;
	}
	public void setDistanceOfPillar(double distanceOfPillar) {
		this.distanceOfPillar = distanceOfPillar;
	}
	
	public List<TextData> getPillarTextList() {
		return pillarTextList;
	}
	public void setPillarTextList(List<TextData> pillarTextList) {
		this.pillarTextList = pillarTextList;
	}
	public boolean isHasCap() {
		return hasCap;
	}
	public void setHasCap(boolean hasCap) {
		this.hasCap = hasCap;
	}
	
	
}
