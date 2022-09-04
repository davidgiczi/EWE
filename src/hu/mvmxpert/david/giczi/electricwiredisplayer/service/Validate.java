package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

public class Validate {

	
	public static boolean isValidProjectName(String projectName) {
		
		if(projectName == null || projectName.isBlank() || 3 > projectName.length()) {
			return false;
		}
		
		return true;
	}
	
}
