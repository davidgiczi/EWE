package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

import javax.management.InvalidAttributeValueException;
import javafx.scene.layout.BorderPane;

public class Validate {

		public static int MAX_X_VALUE;
		public static int MAX_Y_VALUE;
		public static int MIN_X_VALUE = 1;
		public static int MIN_Y_VALUE = 10;
		private static BorderPane root;
		
	public static void setRoot(BorderPane root) {
			Validate.root = root;
		}

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
	
	public static boolean isValidInputTextXPosition(String textX) throws NumberFormatException{
		int text_X = Integer.parseInt(textX);
		MAX_X_VALUE = (int) (root.getWidth() / Drawer.MILLIMETER);
		return MIN_X_VALUE > text_X || text_X > MAX_X_VALUE ;
	}
	
	public static boolean isValidInputTextYPosition(String textY) throws NumberFormatException{
		int text_Y = Integer.parseInt(textY);
		MAX_Y_VALUE = (int) (root.getHeight() / Drawer.MILLIMETER);
		return MIN_Y_VALUE > text_Y || text_Y >  MAX_Y_VALUE;
	}
}
