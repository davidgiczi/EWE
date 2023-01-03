package hu.mvmxpert.david.giczi.electricwireeditor.service;

import java.util.ArrayList;
import java.util.List;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireTypeData;
import hu.mvmxpert.david.giczi.electricwireeditor.wiretype.WireType;

public class ElectricWireCalculator {

	public static final double g = 9.81;
	private ArchivFileBuilder archivFileBuilder;
	private List<WireTypeData> wireTypes;
	private WireTypeData wireData;
	private double temperature = 17.0;
	public double szigma_b = 10.0;
	public double oszlopkoz_hossza;
	public double magassag_kulonbseg;
	public double felfuggesztesi_koz;
	public double kozepes_ferdeseg;
	public double mertekado_oszlopkoz;
	public double kritikus_oszlopkoz;
	public double potteher;
	public double upszilon;
	public double upszilon_z;
	public double szigma_hz;
	public double szigma_k;
	public double szigma_kz;
	public double G;
	public double G_z;
	public double T;
	public double b;
	public double d;
	public double A;
	public double B;
	public double delta;
	
	
	public ElectricWireCalculator(ArchivFileBuilder archivFileBuilder, String wireTypeName, String wireType) {
		this.archivFileBuilder = archivFileBuilder;
		parseWireTypeData();
		if( Validate.isValidInputText(wireTypeName) )
		this.wireData = wireTypes.stream().filter( w -> wireTypeName.equals(w.getType())).findFirst().get();
		getHorizontalDistanceOfPillar(wireType);
		getDifferenceOfElevationsBetweenPillars(wireType);
		getSlopeDistanceBetweenPillars();
		getPotteher();
		getUpszilonZ();
		getSzigmaHz();
		getKozepesFerdeseg();
		getSzigmaKz();
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
			wireTypeData.setSuly_kgPerMeter(Double.parseDouble(wireData[3]));
			wireTypeData.setSuly_NPerMeter(Double.parseDouble(wireData[4]));
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
			this.oszlopkoz_hossza = distance == null ? lastPillar.getDistanceOfPillar() : distance;
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
			this.magassag_kulonbseg = lastPillarElevation - beginnerPillarElevation;
		}	
	}
	
	private void getSlopeDistanceBetweenPillars() {
	this.felfuggesztesi_koz = Math.sqrt(Math.pow(oszlopkoz_hossza, 2) + Math.pow(magassag_kulonbseg, 2));
	}
	
	private void getPotteher() {
		this.potteher = 3.25 + 0.25 * this.wireData.getAtmero();
	}
	
	private void getUpszilonZ() {
		this.upszilon_z = (g * this.wireData.getSuly_kgPerMeter() + this.potteher) / this.wireData.getKeresztMetszet();
	}
	
	private void getSzigmaHz() {
		this.szigma_hz = (this.oszlopkoz_hossza / this.felfuggesztesi_koz) * (this.szigma_b  - 
				(this.upszilon_z * (Math.abs(magassag_kulonbseg) / 2 + 
				/*b'z*/		(Math.pow(this.oszlopkoz_hossza, 2) * this.upszilon_z) / (8 * this.szigma_b))));
	}
	
	private void getKozepesFerdeseg() {
		this.kozepes_ferdeseg = (Math.pow(felfuggesztesi_koz, 3) / Math.pow(oszlopkoz_hossza, 2)) / 
				(Math.pow(felfuggesztesi_koz, 2) / oszlopkoz_hossza) ;
	}
	
	public void getSzigmaKz() {
		this.szigma_kz = this.szigma_hz * this.kozepes_ferdeseg;
	}
	
}
