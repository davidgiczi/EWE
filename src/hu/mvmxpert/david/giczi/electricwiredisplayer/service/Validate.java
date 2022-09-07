package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

import javax.management.InvalidAttributeValueException;

public class Validate {

	
	public static boolean isValidProjectName(String projectName) {
		
		if(projectName == null || projectName.isBlank() || 3 > projectName.length()) {
			return false;
		}
		
		return true;
	}
	
	public static int isValidIntegerValue(String inputValue) throws NumberFormatException {
		int value = Integer.parseInt(inputValue);
		if( 0 > value )
			throw new NumberFormatException();
		return value;
	}
	
	public static double isValidDoubleValue(String inputValue) throws NumberFormatException {
		double value = Double.parseDouble(inputValue.replace(",", "."));
		if( 0 > value)
			throw new NumberFormatException();
		return value;
	}
	
	public static String isValidTextValue(String inputValue) throws InvalidAttributeValueException {
		
		if( inputValue.isBlank() )
			throw new InvalidAttributeValueException();
		
		return inputValue;
	}
	
	public static boolean isValidDistanceValue(double distance, double maxDistance) {
		return distance > maxDistance;
	}
	
	public static boolean isValidElevationValue(double elevation, int minElevation, int maxElevation) {
		return minElevation > elevation || elevation > maxElevation;
	}
}
