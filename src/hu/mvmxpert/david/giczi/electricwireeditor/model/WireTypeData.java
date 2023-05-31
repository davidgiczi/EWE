package hu.mvmxpert.david.giczi.electricwireeditor.model;


public class WireTypeData {
	
	
	private String type;
	private double keresztMetszet;
	public double atmero;
	private double suly_kgPerMeter;
	private double suly_NPerMeter;
	private double potTeher;
	private double rugalmassagiModulusz;
	private double hofokTenyezo;
	
	public WireTypeData() {
	
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getKeresztMetszet() {
		return keresztMetszet;
	}

	public void setKeresztMetszet(double keresztMetszet) {
		this.keresztMetszet = keresztMetszet;
	}

	public double getAtmero() {
		return atmero;
	}

	public void setAtmero(double atmero) {
		this.atmero = atmero;
	}

	public double getSuly_kgPerMeter() {
		return suly_kgPerMeter;
	}

	public void setSuly_kgPerMeter(double suly_kgPerMeter) {
		this.suly_kgPerMeter = suly_kgPerMeter;
	}

	public double getSuly_NPerMeter() {
		return suly_NPerMeter;
	}

	public void setSuly_NPerMeter(double suly_NPerMeter) {
		this.suly_NPerMeter = suly_NPerMeter;
	}

	public double getPotTeher() {
		return potTeher;
	}

	public void setPotTeher(double potTeher) {
		this.potTeher = potTeher;
	}

	public double getRugalmassagiModulusz() {
		return rugalmassagiModulusz;
	}

	public void setRugalmassagiModulusz(double rugalmassagiModulusz) {
		this.rugalmassagiModulusz = rugalmassagiModulusz;
	}

	public double getHofokTenyezo() {
		return hofokTenyezo;
	}

	public void setHofokTenyezo(double hofokTenyezo) {
		this.hofokTenyezo = hofokTenyezo;
	}

	@Override
	public String toString() {
		return "WireTypeData [type=" + type + ", keresztMetszet=" + keresztMetszet + ", atmero=" + atmero
				+ ", suly_kgPerMeter=" + suly_kgPerMeter + ", suly_NPerMeter=" + suly_NPerMeter + ", potTeher="
				+ potTeher + ", rugalmassagiModulusz=" + rugalmassagiModulusz + ", hofokTenyezo=" + hofokTenyezo + "]";
	}
	
}
