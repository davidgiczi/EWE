package hu.mvmxpert.david.giczi.electricwireeditor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.TextData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireDifference;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WirePoint;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireTypeData;
import hu.mvmxpert.david.giczi.electricwireeditor.wiretype.WireType;

public class ElectricWireCalculator {

	public static final double g = 9.81;
	public List<WirePoint> wirePoints;
	private ArchivFileBuilder archivFileBuilder;
	private List<WireTypeData> wireTypes;
	private WireTypeData wireData;
	public String wireType;
	public double t = 20;
	public double t0;
	public double szigma_b = 80;
	public double szigma_hz;
	public double szigma_k;
	public double szigma_kz;
	public double oszlopkoz_hossza;
	public double magassag_kulonbseg;
	public double felfuggesztesi_koz;
	public double kozepes_ferdeseg;
	public double mertekado_oszlopkoz;
	public double kritikus_oszlopkoz;
	public double sodrony_hossza;
	public double belogas;
	public double potteher;
	public double upszilon;
	public double upszilon_z;
	public double G;
	public double G_z;
	public double T;
	public double b;
	public double d;
	public double A;
	public double B;
	public double delta;
	public double p;
	public double at;
	public double ar;
	public double XA;
	public double XB;
	
	public ElectricWireCalculator(ArchivFileBuilder archivFileBuilder, String wireTypeName, String wireType) {
		this.archivFileBuilder = archivFileBuilder;
		this.wireType = wireType;
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
		getG_z();
		get_d();
		getT();
		get_b();
		getA();
		getB();
		getDelta();
		getSzigma_k();
		get_p();
		get_at();
		get_ar();
		getXA();
		getXB();
		calcWirePoints();
		getBelogas();
		getSodronyHossza();
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
	
	private void getG_z() {
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
	
	private void getT() {
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
		
		this.szigma_k = this.delta > 0 ? (- this.b / 3) +
				Math.pow(((- this.B / 2) + Math.sqrt(this.delta)), 1/3.0) + 
				Math.pow(((- this.B / 2) - Math.sqrt(this.delta)), 1/3.0)
		:
		 - this.b / 3 - 2 * Math.sqrt(- this.A / 3) * 
				Math.cos(2 * Math.PI / 3 + Math.acos((this.B / 2) / 
						Math.sqrt(Math.pow(- this.A / 3, 3))) / 3);
	}
	
	private void get_p() {
		this.p = this.szigma_k / this.kozepes_ferdeseg / this.upszilon;
	}
	
	private void get_at(){
		double x = this.magassag_kulonbseg / (2 * this.p * Math.sinh(this.oszlopkoz_hossza / ( 2 * this.p )));
		this.at = this.oszlopkoz_hossza + 2 * this.p * Math.log(x + Math.sqrt(Math.pow(x, 2) + 1));
		}
	
	private void get_ar() {
		double x = this.magassag_kulonbseg / (2 * this.p * Math.sinh(this.oszlopkoz_hossza / ( 2 * this.p )));
		this.ar = this.oszlopkoz_hossza - 2 * this.p * Math.log(x + Math.sqrt(Math.pow(x, 2) + 1));
	}
	
	private void getXA() {
		this.XA = - this.ar / 2;
	}
	
	private void getXB() {
		this.XB = this.at / 2;
	}
	
	private void calcWirePoints(){
		
		 wirePoints = new ArrayList<>();
		for(double i = 0; i < this.oszlopkoz_hossza; i++) {
			WirePoint wirePoint = 
					new WirePoint((int) (i * 1000.0) / 1000.0, 
							(int)((10 * this.p * Math.cosh((this.XA + i) / this.p) + -10 * this.p * Math.cosh(this.XA / this.p)) * 100.0) / 1000.0);
			wirePoints.add(wirePoint);
		}	
	}
	
	private void getBelogas() {
		this.belogas = (this.oszlopkoz_hossza * this.oszlopkoz_hossza * this.kozepes_ferdeseg * this.upszilon * this.kozepes_ferdeseg) / 
				(8 * this.szigma_k) +  (Math.pow(this.oszlopkoz_hossza, 3) * this.oszlopkoz_hossza * 
		this.kozepes_ferdeseg * Math.pow(this.kozepes_ferdeseg, 3) * Math.pow(this.upszilon, 3)) /
				(384 * Math.pow(this.szigma_k, 3));
	}
	
	private void getSodronyHossza() {
		this.sodrony_hossza = (this.oszlopkoz_hossza + (Math.pow(this.belogas, 2) / this.oszlopkoz_hossza) * 8 / 3 + 
				(Math.pow(this.belogas, 4) / Math.pow(this.oszlopkoz_hossza, 3) * 32 / 15)) * this.kozepes_ferdeseg;
	}
	
	public List<WireDifference> getElevationDifferenceOfWires(List<WireData> wires, String type) {
		List<WireDifference> differrences = new ArrayList<>();
		Collections.sort(wires);
		for (WireData wire: wires) {
			double distance = -1;
			double elevation = -1;
			for(TextData wireText : wire.getWireTextList()) {
				String[] values = wireText.getTextValue().split("\\s+");
				
				if( values.length == 1) {
					try {
						distance = Double.parseDouble(values[0].substring(0, values[0].indexOf("m")));
						
					} catch (Exception e) {
					}
					
				}
				else if(values.length == 2 && type.equals(values[0])) {
					
					try {
						distance = Double.parseDouble(values[1].substring(0, values[1].indexOf("m")));
						
					} catch (Exception e) {
					}
				}
				else if(values.length == 4 && type.equals(values[0]) && wireText.isAtTop()) {
					
					try {
						elevation = Double.parseDouble(values[3].substring(0, values[3].indexOf("m")));
						
					} catch (Exception e) {
					}
			}
	}
		if( distance != -1 && elevation != -1) {
				differrences.add(new WireDifference(wire.getWireTextList().get(0).getTextValue() + "_" + type, 
				(int) ((archivFileBuilder.getBeginnerPillar().getTopElevetaion() +
				(int)((10 * this.p * Math.cosh((this.XA + distance) / this.p) + -10 * this.p * Math.cosh(this.XA / this.p)) * 100.0) / 1000.0
									 - elevation) * 100.0) / 100.0));
			}
		else if(distance == -1 && elevation == -1) {
		differrences.add(new WireDifference(wire.getWireTextList().get(0).getTextValue() + "_" + type, 
		(int) ((archivFileBuilder.getBeginnerPillar().getTopElevetaion() +
		(int)((10 * this.p * Math.cosh((this.XA + wire.getDistanceOfWire()) / this.p) + -10 * this.p * Math.cosh(this.XA / this.p)) * 100.0) / 1000.0
							- wire.getTopElevetaion()) * 100.0) / 100.0));
		}
				
	}
		return differrences;
	}

}
