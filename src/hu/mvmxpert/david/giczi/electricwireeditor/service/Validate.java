package hu.mvmxpert.david.giczi.electricwireeditor.service;

import javax.management.InvalidAttributeValueException;

public class Validate {

		public static int MAX_X_VALUE;
		public static int MAX_Y_VALUE;
		public static int MIN_X_VALUE = 1;
		public static int MIN_Y_VALUE = 10;
		
	public static boolean isValidInputText(String inputText) {
		
		if( inputText == null || inputText.isBlank() || 3 > inputText.length() ) {
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
	
	public static int isValidPositiveIntegerValue(String inputValue) throws NumberFormatException {
		int value = Integer.parseInt(inputValue);
		if( 0 >= value )
			throw new NumberFormatException();
		return value;
	}
	
	public static double isValidDoubleValue(String inputValue) throws NumberFormatException {
		double value = Double.parseDouble(inputValue.replace(",", "."));
		
		return value;
	}
	
	public static double isValidPositiveDoubleValue(String inputValue) throws NumberFormatException {
		double value = Double.parseDouble(inputValue.replace(",", "."));
		if( 0 >= value)
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
	
	public static boolean isValidInputTextXPosition(String textX) throws NumberFormatException{
		double text_X = Double.parseDouble(textX);
		return MIN_X_VALUE > text_X || text_X > MAX_X_VALUE ;
	}
	
	public static boolean isValidInputTextYPosition(String textY) throws NumberFormatException{
		double text_Y = Double.parseDouble(textY);
		return MIN_Y_VALUE > text_Y || text_Y >  MAX_Y_VALUE;
	}
	public static int isValidTextRotateValue(String inputValue) throws NumberFormatException {
		int rotateValue = Integer.parseInt(inputValue);
		if( 0 > rotateValue || rotateValue > 359)
			throw new NumberFormatException();
		return rotateValue;
	}
}
