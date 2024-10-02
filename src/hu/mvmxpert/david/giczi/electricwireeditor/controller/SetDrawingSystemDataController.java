package hu.mvmxpert.david.giczi.electricwireeditor.controller;


import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetDrawingSystemDataController {
	
	private Stage stage;
	private HomeController homeController;
		
	public SetDrawingSystemDataController() {
	}
	
	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public TextField startElevationValue;
	@FXML
	public TextField elevationScaleValue;
	@FXML
	public TextField lengthOfHorizontalAxis;
	@FXML
	public TextField horizontalScaleValue;
	
	@FXML
	private void handleSendButtonClick() {
		
		int startElevation;
		int verticalScale;
		double length;
		int horizontalScale;
		int minElevationValue = 0;
		int maxElevationValue = 0;
		
		
		try {
			startElevation = Validate.isValidIntegerValue(startElevationValue.getText());
		} catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő magassági lépték kezdő érték", 
					"A magassági lépték kezdő magasság értéke csak egész szám lehet.");
			return;
		}
		try {
			verticalScale = Validate.isValidPositiveIntegerValue(elevationScaleValue.getText());
		} catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő magassági lépték beosztás érték", 
					"A magassági lépték beosztás értéke csak pozitív egész szám lehet.");
			return;
		}
		try {
			length = Validate.isValidPositiveDoubleValue(lengthOfHorizontalAxis.getText());
		} catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő az oszlopok távolság értéke", 
					"Az oszlopok távolság értéke csak pozitív szám lehet.");
			return;
		}
		try {
			horizontalScale = Validate.isValidPositiveIntegerValue(horizontalScaleValue.getText());
		} catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő vízszintes lépték érték", 
					"A vízszintes lépték értéke csak pozitív egész szám lehet.");
			return;
		}
		
		if( homeController.collectSectionMeasurmentData != null ) {
			minElevationValue = homeController.collectSectionMeasurmentData.getMinElevation();
			maxElevationValue = homeController.collectSectionMeasurmentData.getMaxElevation();
			lengthOfHorizontalAxis.setEditable(true);
		}
		
		if( minElevationValue != 0 && maxElevationValue != 0 ) {
			
		try {
			Validate.isValidPillarSectionElevation(startElevation, verticalScale, minElevationValue, maxElevationValue);
		}catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő magassági lépték érték" , e.getMessage());
			return;
		}
	}
		
		homeController.collectSectionMeasurmentData = null;
		homeController.archivFileBuilder.setSystemData(length, horizontalScale, startElevation, verticalScale);
		if( !homeController.archivFileBuilder.getPillarData().isEmpty() ||
			!homeController.archivFileBuilder.getWireData().isEmpty() ||
			!homeController.archivFileBuilder.getLineData().isEmpty() ||
			!homeController.archivFileBuilder.getTextData().isEmpty()) {
		homeController.init();
		}
		homeController.getDrawer().setElevationStartValue(startElevation);
		homeController.getDrawer().setVerticalScale(verticalScale);
		homeController.getDrawer().setLengthOfHorizontalAxis(length);
		homeController.getDrawer().setHorizontalScale(horizontalScale);
		homeController.getDrawer().drawHorizontalAxis();
		homeController.getDrawer().drawVerticalAxis();
		homeController.getDrawer().writeElevationValueForVerticalAxis();
		homeController.homeWindow.setPillarData.setDisable(false);
		homeController.homeWindow.setWireData.setDisable(false);
		homeController.homeWindow.saveProject.setDisable(false);
		stage.hide();
	}

}
