package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;


import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
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
	public TextField lengthOfPillars;
	@FXML
	public TextField horizontalScaleValue;
	
	@FXML
	private void handleSendButtonClick() {
		
		int startElevation;
		int verticalScale;
		double length;
		int horizontalScale;
		
		try {
			startElevation = Validate.isValidIntegerValue(startElevationValue.getText());
		} catch (NumberFormatException e) {
			HomeController.getWarningAlert("Nem megfelelő magassági lépték kezdő érték", "A magassági lépték kezdő magasság értéke csak pozitív egész szám lehet.");
			return;
		}
		try {
			verticalScale = Validate.isValidIntegerValue(elevationScaleValue.getText());
		} catch (NumberFormatException e) {
			HomeController.getWarningAlert("Nem megfelelő magassági lépték beosztás érték", "A magassági lépték beosztás értéke csak pozitív egész szám lehet.");
			return;
		}
		try {
			length = Validate.isValidDoubleValue(lengthOfPillars.getText());
		} catch (NumberFormatException e) {
			HomeController.getWarningAlert("Nem megfelelő az oszlopok távolság értéke", "Az oszlopok távolság értéke csak pozitív szám lehet.");
			return;
		}
		try {
			horizontalScale = Validate.isValidIntegerValue(horizontalScaleValue.getText());
		} catch (NumberFormatException e) {
			HomeController.getWarningAlert("Nem megfelelő vízszintes lépték érték", "A vízszintes lépték értéke csak pozitív egész szám lehet.");
			return;
		}
		homeController.archivFileBuilder.setSystemData(length, horizontalScale, startElevation, verticalScale);
		homeController.getDrawer().setElevationStartValue(startElevation);
		homeController.getDrawer().setVerticalScale(verticalScale);
		homeController.getDrawer().setLengthOfHorizontalAxis(length);
		homeController.getDrawer().setHorizontalScale(horizontalScale);
		homeController.getDrawer().drawHorizontalAxis();
		homeController.getDrawer().writeDistanceValueForHorizontalAxis();
		homeController.getDrawer().drawVerticalAxis();
		homeController.getDrawer().writeElevationValueForVerticalAxis();
		homeController.homeWindow.setPillarData.setDisable(false);
		homeController.homeWindow.setWireData.setDisable(false);
		homeController.homeWindow.saveProject.setDisable(false);
		stage.hide();
	}

}
