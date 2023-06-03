package hu.mvmxpert.david.giczi.electricwireeditor.model;

import java.text.DecimalFormat;

public class DistanceValue {

	public String preTag;
	public Double distanceValue;
	private DecimalFormat df;
	
	public DistanceValue(String preTag, Double distanceValue) {
		this.preTag = preTag;
		this.distanceValue = distanceValue;
	}
	
	public boolean isEqual(String inputDistanceValue) {
		df = new DecimalFormat("0.000");
		if( inputDistanceValue.equals(preTag + " " +  df.format(distanceValue).replace(",", ".") + "m"))
			return true;
		if( inputDistanceValue.equals(df.format(distanceValue).replace(",", ".") + "m"))
			return true;
		df.applyPattern("0.00");
		if( inputDistanceValue.equals(preTag + " " + df.format(distanceValue).replace(",", ".") + "m"))
			return true;
		if( inputDistanceValue.equals(df.format(distanceValue).replace(",", ".") + "m"))
			return true;
		df.applyPattern("0.0");
		if( inputDistanceValue.equals(preTag + " " + df.format(distanceValue).replace(",", ".") + "m"))
			return true;
		if( inputDistanceValue.equals(df.format(distanceValue).replace(",", ".") + "m"))
			return true;
	
		return false;
	}

}
