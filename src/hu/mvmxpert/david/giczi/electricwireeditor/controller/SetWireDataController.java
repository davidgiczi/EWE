package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import javax.management.InvalidAttributeValueException;

import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SetWireDataController {

	
	private HomeController homeController;
	
	@FXML
	public TextField wireDistance;
	@FXML
	private TextField wireID;
	@FXML
	private TextField groundElevation;
	@FXML
	private TextField wireElevation;
	@FXML
	private CheckBox hasCap;

	public SetWireDataController() {
	}

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	@FXML
	public void handleSendButtonClick() {
		
		String id;
		double distance;
		double groundElev;
		double wireElev;
		
		try {
			id = Validate.isValidTextValue(wireID.getText());
		} catch (InvalidAttributeValueException e) {
		homeController.getWarningAlert("Nem megfelelő a fázis azonosítója", "A fázis azonosítója legalább egy betű vagy szám karakter lehet.");
			return;
		}
		try {
			distance = Validate.isValidDoubleValue(wireDistance.getText());
			if( Validate.isValidDistanceValue(distance, homeController.getDrawer().getLengthOfHorizontalAxis()) )
				throw new NumberFormatException();
			
		} catch (NumberFormatException e) {
		homeController.getWarningAlert("Nem megfelelő a fázis helyének távolság értéke", 
			"A fázis helyének távolsága: távolság >= 0 és " + homeController.getDrawer().getLengthOfHorizontalAxis() + "m >= távolság.");
			return;
		}
		try {
			groundElev = Validate.isValidDoubleValue(groundElevation.getText());
			
			if( Validate.isValidElevationValue(groundElev, homeController.getDrawer().getElevationStartValue(), 
					homeController.getDrawer().getElevationStartValue() + 10 * homeController.getDrawer().getVerticalScale() ) )
				throw new NumberFormatException();
			
		} catch (NumberFormatException e) {
		homeController.getWarningAlert("Nem megfelelő terep balti magasság érték", 
					"A terepi balti magasság: magasság >= " + homeController.getDrawer().getElevationStartValue()  + "m és " 
			+ (homeController.getDrawer().getElevationStartValue() + 10 * homeController.getDrawer().getVerticalScale()) + "m >= magasság.");
			return;
		}
		try {
			wireElev = Validate.isValidDoubleValue(wireElevation.getText());
			
			if( Validate.isValidElevationValue(wireElev, homeController.getDrawer().getElevationStartValue(), 
					homeController.getDrawer().getElevationStartValue() + 10 * homeController.getDrawer().getVerticalScale() ) )
				throw new NumberFormatException();
			else if( groundElev >= wireElev ) {
		homeController.getWarningAlert("Nem megfelelő a fázis balti magasság értéke", 
						"A fázis terepi balti magasság értéke nem lehet nagyobb vagy egyenlő, mint a fázis balti magasság értéke.");
				return;
			}
			
		} catch (NumberFormatException e) {
		homeController.getWarningAlert("Nem megfelelő a fázis balti magasság értéke", 
					"Fázis balti magasság: magasság >= " + homeController.getDrawer().getElevationStartValue()  + "m és " 
			+ (homeController.getDrawer().getElevationStartValue() + 10 * homeController.getDrawer().getVerticalScale()) + "m >= magasság.");
			return;
		}
		
		if( hasCap.isSelected() ) {
			homeController.getDrawer().drawElectricWire(id, groundElev, wireElev, distance, true);
		}
		else {
			homeController.getDrawer().drawElectricWire(id, groundElev, wireElev, distance, false);
		}
		
	}
	
	
	
}
