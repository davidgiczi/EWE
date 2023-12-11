package hu.mvmxpert.david.giczi.electricwireeditor.model;

import java.util.ArrayList;
import java.util.List;

public class PillarData implements Comparable<PillarData> {

	
	private int id;
	private double groundElevation;
	private double topElevetaion;
	private double distanceOfPillar;
	private List<TextData> pillarTextList;
	private boolean leftHand;
	private boolean rightHand;
	
	public PillarData(double groundElevation, double topElevetaion, double distanceOfPillar, boolean leftHand, boolean rightHand) {

		this.groundElevation = groundElevation;
		this.topElevetaion = topElevetaion;
		this.distanceOfPillar = distanceOfPillar;
		this.leftHand = leftHand;
		this.rightHand = rightHand;
		pillarTextList = new ArrayList<>();
	}

	public String getPillarData() {
		return "Pillar#" + groundElevation + "#" + topElevetaion + "#" + distanceOfPillar + "#" + leftHand + "#" + rightHand;
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
	
	public boolean isLeftHand() {
		return leftHand;
	}

	public void setLeftHand(boolean leftHand) {
		this.leftHand = leftHand;
	}

	public boolean isRightHand() {
		return rightHand;
	}

	public void setRightHand(boolean rightHand) {
		this.rightHand = rightHand;
	}

	@Override
	public int compareTo(PillarData o) {
		return this.getDistanceOfPillar() > o.getDistanceOfPillar() ? 1 : this.getDistanceOfPillar() < o.getDistanceOfPillar() ? -1 : 0;
	}
	
	
}
