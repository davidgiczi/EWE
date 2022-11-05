package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import javax.management.InvalidAttributeValueException;

import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetTextController {

	private Stage stage;
	private HomeController homeController;
	private double rotate;
	private String chosenText;
	
	public SetTextController() {
	}

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

	public void setChosenText(String chosenText) {
		this.chosenText = chosenText;
	}

	@FXML
	public TextField inputTextField;
	@FXML
	public TextField inputTextXField;
	@FXML
	public TextField inputTextYField;
	
	
	@FXML
	public void handleSetTextButtonClick() {
		int ownerId = homeController.archivFileBuilder.getChosenTextOwnerId(chosenText);
		boolean isAtTop = homeController.archivFileBuilder.isChosenTextAtTop(chosenText);
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
					"Az X koordináta értéke: X >= "+ Validate.MIN_X_VALUE + "mm és " + Validate.MAX_X_VALUE + "mm >= X.");
			return;
		}
		try {
			if( Validate.isValidInputTextYPosition(inputTextYField.getText()) ) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			HomeController.getWarningAlert("Nem megfelelő Y koordináta érték", 
					"Az Y koordináta értéke: Y >= " + Validate.MIN_Y_VALUE + "mm és " + Validate.MAX_Y_VALUE + "mm >= Y.");
			return;
		}
		
		homeController.getDrawer().writeText(inputText, Double.parseDouble(inputTextXField.getText().replace(',', '.')),
				Double.parseDouble(inputTextYField.getText().replace(',', '.')), rotate, ownerId, isAtTop);
		rotate = 0;
		stage.hide();
	}
	
	
}