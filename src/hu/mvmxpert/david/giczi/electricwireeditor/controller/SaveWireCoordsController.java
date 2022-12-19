package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.util.List;

import hu.mvmxpert.david.giczi.electricwireeditor.model.SavedWirePoint;
import hu.mvmxpert.david.giczi.electricwireeditor.service.FileProcess;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SaveWireCoordsController {

	
	@FXML
	private TextField typeOfWirePoint;
	@FXML
	public TextField startX;
	@FXML
	public TextField startY;
	@FXML
	public TextField endX;
	@FXML
	public TextField endY;
	@FXML
	private CheckBox scrCheckBox;
	@FXML
	private CheckBox txtCheckBox;
	
	private boolean is2DWindow;
	private HomeController homeController;
	
	
	public void setIs2DWindow(boolean is2dWindow) {
		is2DWindow = is2dWindow;
	}
	
	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	@FXML
	public void handleSaveButtonClick() {
		
		if( FileProcess.FOLDER_PATH == null )
		homeController.fileProcess.setFolder();
		
		if( HomeController.PROJECT_NAME == null )
		homeController.setProjectName();
		
		String type = typeOfWirePoint.getText();
		if( type == null || type.isBlank() ) {
			HomeController.getWarningAlert("Nem megfelelő sodrony megnevezés", 
					"Sodrony megnevezése legalább egy látható karakter érték lehet.");
			return;
		}
		List<SavedWirePoint> savedWirePoints = homeController.archivFileBuilder.getWirePointsForSaving(type);
		
		if( savedWirePoints.isEmpty() ) {
		HomeController.getWarningAlert("Sodrony pontjai nem menthetők", 
				"Sodrony pontjainak mentéséhez legalább egy \"" + type + "\" megnevezésű vezeték pont szükséges.");
		return;
		}
		if( !scrCheckBox.isSelected() && !txtCheckBox.isSelected()) {
			HomeController.getWarningAlert("Kimeneti fájl típus választása szükséges", 
					"Add meg a kimeneti fájl típusát (*.scr, *.txt).");
			return;
		}	
		
		if( is2DWindow && scrCheckBox.isSelected() && txtCheckBox.isSelected() ) {
			
			homeController.fileProcess.save2DWirePointsInAutoCadFormat(savedWirePoints);
			homeController.fileProcess.save2DWirePointsInTextFormat(savedWirePoints);
			HomeController.getInfoAlert(savedWirePoints.size() + " db pont mentve",
			FileProcess.FOLDER_PATH + "\\" + HomeController.PROJECT_NAME + "_2D" + ".scr\n" + 
			FileProcess.FOLDER_PATH + "\\" + HomeController.PROJECT_NAME + "_2D" + ".txt" );
		}
		else if( is2DWindow && scrCheckBox.isSelected() ) {
			
			homeController.fileProcess.save2DWirePointsInAutoCadFormat(savedWirePoints);
			HomeController.getInfoAlert(savedWirePoints.size() + " db pont mentve", 
			FileProcess.FOLDER_PATH + "\\" + HomeController.PROJECT_NAME + "_2D" + ".scr");
			 
		}
		else if( is2DWindow && txtCheckBox.isSelected() ){
			homeController.fileProcess.save2DWirePointsInTextFormat(savedWirePoints);
			HomeController.getInfoAlert(savedWirePoints.size() + " db pont mentve", 
			FileProcess.FOLDER_PATH + "\\" + HomeController.PROJECT_NAME + "_2D" + ".txt");
		}
	}
	
}
