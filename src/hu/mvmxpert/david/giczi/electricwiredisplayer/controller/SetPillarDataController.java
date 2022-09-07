package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;

import javax.management.InvalidAttributeValueException;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class SetPillarDataController {

	
	private HomeController homeController;
	
	@FXML
	private TextField pillarID;
	@FXML
	private TextField pillarDistance;
	@FXML
	private TextField groundElevation;
	@FXML
	private TextField pillarElevation;
	@FXML
	private CheckBox hasCap;

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	@FXML
	public void handleSendButtonClick() {
		
		String id;
		double distance;
		double groundElev;
		double pillarElev;
		
		try {
			id = Validate.isValidTextValue(pillarID.getText());
		} catch (InvalidAttributeValueException e) {
			getWarningAlert("Nem megfelelő oszlop azonosító", "Az oszlop azonosítója legalább egy betű vagy szám karakter lehet.");
			return;
		}
		try {
			distance = Validate.isValidDoubleValue(pillarDistance.getText());
			if( Validate.isValidDistanceValue(distance, homeController.getDrawer().getLengthOfHorizontalAxis()) )
				throw new NumberFormatException();
			
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelelő az oszlop távolság értéke", 
			"Az oszlop távolsága: távolság >= 0 és " + homeController.getDrawer().getLengthOfHorizontalAxis() + "m >= távolság.");
			return;
		}
		try {
			groundElev = Validate.isValidDoubleValue(groundElevation.getText());
			
			if( Validate.isValidElevationValue(groundElev, homeController.getDrawer().getElevationStartValue(), 
					homeController.getDrawer().getElevationStartValue() + 10 * homeController.getDrawer().getVerticalScale() ) )
				throw new NumberFormatException();
			
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelelő terep balti magasság érték", 
					"A terepi balti magasság: magasság >= " + homeController.getDrawer().getElevationStartValue()  + "m és " 
			+ (homeController.getDrawer().getElevationStartValue() + 10 * homeController.getDrawer().getVerticalScale()) + "m >= magasság.");
			return;
		}
		try {
			pillarElev = Validate.isValidDoubleValue(pillarElevation.getText());
			
			if( Validate.isValidElevationValue(pillarElev, homeController.getDrawer().getElevationStartValue(), 
					homeController.getDrawer().getElevationStartValue() + 10 * homeController.getDrawer().getVerticalScale() ) )
				throw new NumberFormatException();
			else if( groundElev >= pillarElev ) {
				getWarningAlert("Nem megfelelő oszlop balti magasság érték", 
						"A terep balti magasság érték nem lehet nagyobb, mint az oszlop balti magasság értéke.");
				return;
			}
			
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelelő oszlop balti magasság érték", 
					"Az oszlop balti magasság: magasság >= " + homeController.getDrawer().getElevationStartValue()  + "m és " 
			+ (homeController.getDrawer().getElevationStartValue() + 10 * homeController.getDrawer().getVerticalScale()) + "m >= magasság.");
			return;
		}
		
		if( hasCap.isSelected() ) {
			homeController.getDrawer().drawPillar(id, groundElev, pillarElev, distance, true);
		}
		else {
			homeController.getDrawer().drawPillar(id, groundElev, pillarElev, distance, false);
		}
		
	}
	
	private void getWarningAlert(String title, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.show();
	}
}
