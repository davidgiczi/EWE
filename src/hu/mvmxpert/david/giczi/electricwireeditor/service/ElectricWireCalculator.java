package hu.mvmxpert.david.giczi.electricwireeditor.service;

import java.util.ArrayList;
import java.util.List;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireTypeData;
import hu.mvmxpert.david.giczi.electricwireeditor.wiretype.WireType;

public class ElectricWireCalculator {

	public static final double G = 9.81;
	private ArchivFileBuilder archivFileBuilder;
	private List<WireTypeData> wireTypes;
	private WireTypeData wireData;
	private double a;
	private double m;
	private double p;
	private double at;
	private double ar;
	
	
	public ElectricWireCalculator(ArchivFileBuilder archivFileBuilder, String wireTypeName, String wireType) {
		this.archivFileBuilder = archivFileBuilder;
		parseWireTypeData();
		if( Validate.isValidInputText(wireTypeName) )
		this.wireData = wireTypes.stream().filter( w -> wireTypeName.equals(w.getType())).findFirst().get();
		getHorizontalDistanceOfPillar(wireType);
		getDifferenceOfElevationsBetweenPillars(wireType);
	}
	
	public WireTypeData getWireData() {
		return wireData;
	}

	public double getA() {
		return a;
	}

	public double getM() {
		return m;
	}

	public double getP() {
		return p;
	}

	public double getAt() {
		return at;
	}

	public double getAr() {
		return ar;
	}

	private void parseWireTypeData() {
		this.wireTypes = new ArrayList<>();
		if( FileProcess.getWireTypeFileData().isEmpty() )
			return;
		for (int i = 2; i < FileProcess.getWireTypeFileData().size(); i++) {
			String[] wireData = FileProcess.getWireTypeFileData().get(i).split(";");
			WireTypeData wireTypeData = new WireTypeData();
			wireTypeData.setType(wireData[0]);
			wireTypeData.setKeresztMetszet(Double.parseDouble(wireData[1]));
			wireTypeData.setAtmero(Double.parseDouble(wireData[2]));
			wireTypeData.setSuly_NPerMeter(Double.parseDouble(wireData[3]));
			wireTypeData.setSuly_kgPerMeter(Double.parseDouble(wireData[4]));
			wireTypeData.setPotTeher(Double.parseDouble(wireData[5]));
			wireTypeData.setRugalmassagiModulusz(Double.parseDouble(wireData[6]));
			wireTypeData.setHofokTenyezo(Double.parseDouble(wireData[7]));
			wireTypes.add(wireTypeData);
		}
	}
	
	private void getHorizontalDistanceOfPillar(String type) {
		if( archivFileBuilder == null )
			return;
		if( archivFileBuilder.getPillarData().isEmpty() )
			return;
		PillarData lastPillar = archivFileBuilder.getLastPillar();
		if( Validate.isValidInputText(type) ) {
			Double distance = archivFileBuilder.getDistance(lastPillar.getPillarTextList(), WireType.getWireType(type));
			this.a = distance == null ? lastPillar.getDistanceOfPillar() : distance;
		}
		
	}
	private void getDifferenceOfElevationsBetweenPillars(String type) {
		if( archivFileBuilder == null )
			return;
		if( archivFileBuilder.getPillarData().isEmpty() )
			return;
		PillarData beginnerPillar = archivFileBuilder.getBeginnerPillar();
		PillarData lastPillar = archivFileBuilder.getLastPillar();
		if( beginnerPillar != null && lastPillar != null && Validate.isValidInputText(type) ) {
			double beginnerPillarElevation = archivFileBuilder.getPillarElevation(beginnerPillar, WireType.getWireType(type));
			double lastPillarElevation = archivFileBuilder.getPillarElevation(lastPillar, WireType.getWireType(type));
			this.m = lastPillarElevation - beginnerPillarElevation;
		}
			
	}
	
}
