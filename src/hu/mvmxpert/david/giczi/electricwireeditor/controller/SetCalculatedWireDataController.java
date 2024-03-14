package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import hu.mvmxpert.david.giczi.electricwireeditor.view.SaveWireCoordsWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SetCalculatedWireDataController implements Initializable {

	private HomeController homeController;
	
	@FXML
	public TextField wireTypeTextField;
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
	

	public SetCalculatedWireDataController(HomeController homeController) {
		this.homeController = homeController;
	}

	@FXML
	private void handleCountButtonAction() {
	if( homeController.archivFileBuilder == null || 2 >	homeController.archivFileBuilder.getPillarData().size() ) {
		homeController.getWarningAlert("Nem létező oszlop adatok", "Sodrony számításához a kezdő és végoszlop adatainak megadása szükséges.");
		return;
	}
	if(homeController.fileProcess.getWireTypeFileData().isEmpty()) {
		homeController.getWarningAlert("Hiányzó vagy üres oszlop típus fájl", "Nem létező vagy üres az oszlop típusokat tartalmazó fájl.");
		return;
	}
	Double beginnerPillarElevation = 
			homeController.archivFileBuilder
			.getElevation(homeController.archivFileBuilder.getBeginnerPillar().getPillarTextList(), wireTypeTextField.getText());
	Double lastPillarElevation = 
			homeController.archivFileBuilder
			.getElevation(homeController.archivFileBuilder.getLastPillar().getPillarTextList(), wireTypeTextField.getText());
		
	if(wireTypeTextField.getText().isBlank() || beginnerPillarElevation == null || lastPillarElevation == null) {
			homeController.getWarningAlert("Nem létező sodrony, vagy nem megfelelő sodrony hivatkozás", 
					"Növekvő oszlopszám szerinti oldal (bal, közép, jobb) és a kar helyének (ak, kk, fk) megadása szükséges.");
			return;
	}
	if(wireTypesComboBox.getValue() == null) {
		homeController.getWarningAlert("Hiányzó sodrony típus", "Sodrony típus választása szükséges.");
		return;
	}
	double szigmaValue;
	try {
	szigmaValue = Validate.isValidPositiveDoubleValue(szigmaTextField.getText());
	} catch (NumberFormatException e) {
		homeController.getWarningAlert("Hibás szigma érték", "A σ értéke csak nem negatív szám lehet.");
		return;
	}	
	double temperatureValue;
	
	try {
		temperatureValue = Double.parseDouble(tempTextField.getText().replace(",", "."));
	} catch (NumberFormatException e) {
		homeController.getWarningAlert("Hibás hőmérséklet érték", "A hőmérséklet értéke csak szám lehet.");
		return;
	}
	
	if( showWireCheckBox.isSelected() ) {
	homeController.showCalculatedWire(wireTypesComboBox.getValue(), wireTypeTextField.getText(), szigmaValue, temperatureValue);
	}
	if( showPreResultsCheckBox.isSelected() ) {
		homeController.showPreResultsData();
	}
	else {
		homeController.getDrawer().deletePreResultsData();
	}
	if( showWireDiffsCheckBox.isSelected() ) {
		homeController.showDifferencesOfWires();
	}
	
	if( saveForAutoCadCheckBox.isSelected() ) {
		homeController.fileProcess.saveCalulatedWirePointsInAutoCadFormat(homeController.calculator.wirePoints, wireTypeTextField.getText());
	}
	if( saveForTxtCheckBox.isSelected() ) {
		homeController.fileProcess.saveCalulatedWirePointsInTextFormat(homeController.calculator.wirePoints, wireTypeTextField.getText());
	}
	if( homeController.saveWireCoordsWindow == null ) {
		homeController.saveWireCoordsWindow = new SaveWireCoordsWindow(homeController);
		homeController.saveWireCoordsWindow.controller.getTypeOfWirePoint().setText(wireTypeTextField.getText());
		homeController.saveWireCoordsWindow.getStage().hide();
	}
	homeController.setCalculatedWireDataWindow.getStage().hide();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	List<String> wireType =	homeController.fileProcess.getWireTypeFileData();
	for(int i = 2; i < wireType.size(); i++) {
		String[] data = wireType.get(i).split(";");
		wireTypesComboBox.getItems().add(data[0]);
	}
	}
	
}
