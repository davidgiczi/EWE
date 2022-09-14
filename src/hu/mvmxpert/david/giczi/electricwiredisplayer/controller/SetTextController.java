package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;

import javax.management.InvalidAttributeValueException;

import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetTextController {

	private Stage stage;
	private HomeController homeController;
	
	public SetTextController() {
	}

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public TextField inputTextField;
	@FXML
	private TextField inputTextXField;
	@FXML
	private TextField inputTextYField;
	
	
	@FXML
	public void handleSetTextButtonClick() {
		
		String inputText;
		try {
			inputText = Validate.isValidTextValue(inputTextField.getText());
		} catch (InvalidAttributeValueException e) {
			HomeController.getWarningAlert("Nem látható felirat", "Nem látható felirat nem helyezhető el.");
			return;
		}
		try {
			if( Validate.isValidInputTextXPosition(inputTextXField.getText()) ) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			HomeController.getWarningAlert("Nem megfelelő X koordináta érték", 
					"Az X koordináta csak egész szám lehet: X >=" + Validate.MIN_X_VALUE  + "mm és " + Validate.MAX_X_VALUE + "mm >= X.");
			return;
		}
		try {
			if( Validate.isValidInputTextYPosition(inputTextYField.getText()) ) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			HomeController.getWarningAlert("Nem megfelelő Y koordináta érték", 
					"Az Y koordináta csak egész szám lehet: Y >=" + Validate.MIN_Y_VALUE  + "mm és " + Validate.MAX_Y_VALUE + "mm >= Y.");
			return;
		}
		homeController.getDrawer().writeText(inputText, Integer.parseInt(inputTextXField.getText()),
				Integer.parseInt(inputTextYField.getText()), 18, 0);
		stage.hide();
	}
	
	
}
