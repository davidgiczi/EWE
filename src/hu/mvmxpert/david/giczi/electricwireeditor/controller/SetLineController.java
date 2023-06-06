package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.net.URL;
import java.util.ResourceBundle;

import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class SetLineController implements Initializable {


	private HomeController homeController;

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	@FXML
	private TextField startXTextField;
	@FXML
	private TextField startYTextField;
	@FXML
	private TextField endXTextField;
	@FXML
	private TextField endYTextField;
	@FXML
	private ComboBox<String> lineTypeComboBox;
	@FXML
	private ColorPicker lineColorPicker;
	@FXML
	private ComboBox<String> lineWidthComboBox;
	@FXML
	private Button setButton;

	public TextField getStartXTextField() {
		return startXTextField;
	}

	public TextField getStartYTextField() {
		return startYTextField;
	}

	public TextField getEndXTextField() {
		return endXTextField;
	}

	public TextField getEndYTextField() {
		return endYTextField;
	}

	public ComboBox<String> getLineTypeComboBox() {
		return lineTypeComboBox;
	}

	public ColorPicker getLineColorPicker() {
		return lineColorPicker;
	}

	public ComboBox<String> getLineWidthComboBox() {
		return lineWidthComboBox;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lineTypeComboBox.getItems().add("folyamatos");
		lineTypeComboBox.getItems().add("szaggatott");
		lineTypeComboBox.getItems().add("pontozott");
		lineWidthComboBox.getItems().add("0.5");
		lineWidthComboBox.getItems().add("1");
		lineWidthComboBox.getItems().add("3");
	}
	
	@FXML
	private void handleSettingLineButtonClick() {
		double startX;
		double startY;
		double endX;
		double endY;
		try {
			startX = Validate.isValidDoubleValue(startXTextField.getText().replace(',', '.'));
			startY = Validate.isValidDoubleValue(startYTextField.getText().replace(',', '.'));
			endX = Validate.isValidDoubleValue(endXTextField.getText().replace(',', '.'));
			endY = Validate.isValidDoubleValue(endYTextField.getText().replace(',', '.'));
			
		} catch (NumberFormatException e) {
			homeController.getWarningAlert("Nem megfelelő koordináta bementi érték", "Vonal koordináta érték csak 0 vagy pozitív szám lehet.");
			return;
		}
		if(lineWidthComboBox.getValue() == null)
			 lineWidthComboBox.setValue("1");
		if(lineTypeComboBox.getValue() == null)
			 lineTypeComboBox.setValue("folyamatos");
		
		homeController.getDrawer().drawLine(startX, startY, endX, endY, lineTypeComboBox.getValue(), 
				lineColorPicker.getValue(), lineWidthComboBox.getValue());
	}
	
}
