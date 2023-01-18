package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import hu.mvmxpert.david.giczi.electricwireeditor.service.FileProcess;
import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SetCalculatedWireDataController implements Initializable {

	private HomeController homeController;
	
	@FXML
	private TextField wireTypeTextField;
	@FXML
	private ComboBox<String> wireTypesComboBox;
	@FXML
	private TextField szigmaTextField;
	@FXML
	private TextField tempTextField;
	@FXML
	public CheckBox showWireCheckBox;
	@FXML
	private CheckBox showPreResultsCheckBox;
	@FXML
	private CheckBox showWireDiffsCheckBox;
	@FXML
	private CheckBox saveForAutoCadCheckBox;
	@FXML
	private CheckBox saveForTxtCheckBox;
	

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	
	@FXML
	private void handleCountButtonAction() {
	
	if(wireTypeTextField.getText().isBlank()) {
			HomeController.getWarningAlert("Hiányzó sodrony megnevezés", "Sodrony megnevezés megadása szükséges.");
			return;
	}
	if(wireTypesComboBox.getValue() == null) {
		HomeController.getWarningAlert("Hiányzó sodrony típus", "Sodrony típus választása szükséges.");
		return;
	}
	double szigmaValue;
	try {
	szigmaValue = Validate.isValidDoubleValue(szigmaTextField.getText());
	} catch (NumberFormatException e) {
		HomeController.getWarningAlert("Hibás szigma érték", "A σ értéke csak nem negatív szám lehet.");
		return;
	}	
	double temperatureValue;
	
	try {
		temperatureValue = Double.parseDouble(tempTextField.getText());
	} catch (NumberFormatException e) {
		HomeController.getWarningAlert("Hibás hőmérséklet érték", "A hőmérséklet értéke csak szám lehet.");
		return;
	}
		
	homeController.showCalculatedWire(wireTypesComboBox.getValue(), wireTypeTextField.getText(), szigmaValue, temperatureValue);
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	List<String> wireType =	FileProcess.getWireTypeFileData();
	for(int i = 2; i < wireType.size(); i++) {
		String[] data = wireType.get(i).split(";");
		wireTypesComboBox.getItems().add(data[0]);
	}
	}
	

}
