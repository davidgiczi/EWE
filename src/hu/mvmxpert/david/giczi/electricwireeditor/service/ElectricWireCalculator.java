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
	private double t = 17.0;
	private double t0;
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
		getFelfuggesztesiKoz();
		getPotteher();
		getUpszilon();
		getUpszilonZ();
		getSzigmaHz();
		getKozepesFerdeseg();
		getSzigmaKz();
		getMertekadoOszlopkoz();
		getG();
		getKritikusOszlopkoz();
		get_t0();
		get_G_z();
		get_d();
		get_T();
		get_b();
		getA();
		getB();
		getDelta();
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
	
	private void getFelfuggesztesiKoz() {
	this.felfuggesztesi_koz = Math.sqrt(Math.pow(oszlopkoz_hossza, 2) + Math.pow(magassag_kulonbseg, 2));
	}
	
	private void getPotteher() {
		this.potteher = 3.25 + 0.25 * this.wireData.getAtmero();
	}
	
	private void getUpszilon() {
		this.upszilon = g * this.wireData.getSuly_kgPerMeter() / this.wireData.getKeresztMetszet();
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
	
	private void getSzigmaKz() {
		this.szigma_kz = this.szigma_hz * this.kozepes_ferdeseg;
	}
	
	private void getMertekadoOszlopkoz() {
		this.mertekado_oszlopkoz = 0.6 * this.szigma_b / this.upszilon_z;
	}
	
	private void getG() {
		this.G = Math.pow(this.oszlopkoz_hossza, 2) * Math.pow(this.upszilon, 2) / 24.0;
	}
	
	private void get_G_z() {
		this.G_z = this.t0 == -20.0 ? this.G : Math.pow(this.oszlopkoz_hossza, 2) * Math.pow(this.upszilon_z, 2) / 24;
	}
	
	private void getKritikusOszlopkoz() {
		this.kritikus_oszlopkoz = this.G > 0 ? 
				this.szigma_kz * Math.sqrt(360 * this.wireData.getHofokTenyezo() / ((Math.pow(this.upszilon_z, 2) - Math.pow(this.upszilon, 2)))) :
				this.szigma_kz * Math.sqrt(1560 * this.wireData.getHofokTenyezo() / ((Math.pow(this.upszilon, 2) - Math.pow(this.upszilon_z, 2))));
	}
	
	private void get_t0() {
		if(this.wireData.getHofokTenyezo() > 0) {
			if(this.oszlopkoz_hossza > this.kritikus_oszlopkoz )
				this.t0 = -5.0;
			else
				this.t0 = -20.0;
		}
		
		else {
			if(this.oszlopkoz_hossza > this.kritikus_oszlopkoz )
				this.t0 = -5.0;
			else
				this.t0 = 60.0;
		}
	}
	
	private void get_d() {
		this.d = - this.G * this.wireData.getRugalmassagiModulusz();
	}
	
	private void get_T() {
		this.T = (this.t - this.t0) * this.wireData.getHofokTenyezo();
	}
	
	private void get_b() {
		this.b = (this.T - this.szigma_kz / this.wireData.getRugalmassagiModulusz() + this.G_z / Math.pow(this.szigma_kz, 2)) 
				* this.wireData.getRugalmassagiModulusz();
	}
	
	private void getA() {
		this.A = -9.0 * Math.pow(this.b, 2) / 27.0;
	}
	
	private void getB() {
		this.B = (2 * Math.pow(this.b, 3) + 27.0 * this.d) / 27.0;
	}
	
	private void getDelta() {
		this.delta = Math.pow(this.B / 2, 2) + Math.pow(this.A / 3, 3);
	}
	
	private void getSzigma_k() {
		
	}
}
