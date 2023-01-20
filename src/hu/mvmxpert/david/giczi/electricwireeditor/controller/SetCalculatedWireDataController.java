package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import hu.mvmxpert.david.giczi.electricwireeditor.service.FileProcess;
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
	
	
	
	public HomeController getHomeController() {
		return homeController;
	}

	@FXML
	private void handleCountButtonAction() {
	if( homeController.archivFileBuilder == null || 2 >	homeController.archivFileBuilder.getPillarData().size() ) {
		HomeController.getWarningAlert("Nem létező oszlop adatok", "Sodrony számításához a kezdő és végoszlop adatainak megadása szükséges.");
		return;
	}
	if( FileProcess.getWireTypeFileData().isEmpty()) {
		HomeController.getWarningAlert("Hiányzó vagy üres oszlop típus fájl", "Nem létező vagy üres az oszlop típusokat tartalmazó fájl.");
		return;
	}
	Double beginnerPillarElevation = 
			homeController.archivFileBuilder.getPillarElevation(homeController.archivFileBuilder.getBeginnerPillar(), wireTypeTextField.getText());
	Double lastPillarElevation = 
			homeController.archivFileBuilder.getPillarElevation(homeController.archivFileBuilder.getLastPillar(), wireTypeTextField.getText());
		
	if(wireTypeTextField.getText().isBlank() || beginnerPillarElevation == null || lastPillarElevation == null) {
			HomeController.getWarningAlert("Hiányzó vagy nem létező sodrony megnevezés", "Létező sodrony megnevezés megadása szükséges.");
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
		temperatureValue = Double.parseDouble(tempTextField.getText().replace(",", "."));
	} catch (NumberFormatException e) {
		HomeController.getWarningAlert("Hibás hőmérséklet érték", "A hőmérséklet értéke csak szám lehet.");
		return;
	}
	
	if( showWireCheckBox.isSelected() ) {
	homeController.showCalculatedWire(wireTypesComboBox.getValue(), wireTypeTextField.getText(), szigmaValue, temperatureValue);
	}
	if( showPreResultsCheckBox.isSelected() ) {
		homeController.showPreResultsData(wireTypesComboBox.getValue());
	}
	else {
		homeController.getDrawer().deletePreResultsData();
	}
	if( showWireDiffsCheckBox.isSelected() ) {
		
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
	if( !homeController.homeWindow.wireTypeComboBox.getItems().contains(wireTypeTextField.getText()) )
	homeController.homeWindow.wireTypeComboBox.getItems().add(wireTypeTextField.getText());
	homeController.setCalculatedWireDataWindow.getStage().hide();
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
