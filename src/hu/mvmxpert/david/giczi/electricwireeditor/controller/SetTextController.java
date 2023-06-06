package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import javax.management.InvalidAttributeValueException;

import hu.mvmxpert.david.giczi.electricwireeditor.model.TextData;
import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetTextController {

	private Stage stage;
	private HomeController homeController;
	private int chosenTextID;
	
	public SetTextController() {
	}

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setChosenTextID(int chosenTextID) {
		this.chosenTextID = chosenTextID;
	}

	@FXML
	public TextField inputTextField;
	@FXML
	public TextField inputTextXField;
	@FXML
	public TextField inputTextYField;
	
	
	@FXML
	public void handleSetTextButtonClick() {
		TextData chosenTextData = homeController.archivFileBuilder.getTextData(chosenTextID);
		String inputText;
		try {
			inputText = Validate.isValidTextValue(inputTextField.getText());
		} catch (InvalidAttributeValueException e) {
			homeController.getWarningAlert("Nem látható felirat", "Nem látható felirat nem helyezhető el.");
			return;
		}
		try {
			if( Validate.isValidInputTextXPosition(inputTextXField.getText()) ) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő X koordináta érték", 
					"Az X koordináta értéke: X >= "+ Validate.MIN_X_VALUE + "mm és " + Validate.MAX_X_VALUE + "mm >= X.");
			return;
		}
		try {
			if( Validate.isValidInputTextYPosition(inputTextYField.getText()) ) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő Y koordináta érték", 
					"Az Y koordináta értéke: Y >= " + Validate.MIN_Y_VALUE + "mm és " + Validate.MAX_Y_VALUE + "mm >= Y.");
			return;
		}
		
		homeController.getDrawer().writeText(inputText, Double.parseDouble(inputTextXField.getText().replace(',', '.')),
				Double.parseDouble(inputTextYField.getText().replace(',', '.')), chosenTextData);
		stage.hide();
	}
	
	
}
