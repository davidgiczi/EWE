package hu.mvmxpert.david.giczi.electricwiredisplayer.controller;

import java.util.List;
import java.util.Optional;

import hu.mvmxpert.david.giczi.electricwiredisplayer.model.PillarData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.TextData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.WireData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.ArchivFileBuilder;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.FileProcess;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.HomeWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetDrawingSystemDataWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetPillarDataWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetTextWindow;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.SetWireDataWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {

	public HomeWindow homeWindow;
	public ArchivFileBuilder archivFileBuilder;
	public static String PROJECT_NAME;
	private Drawer drawer;
	private FileProcess fileProcess;
	private SetDrawingSystemDataWindow setCoordSystemWindow;
	private SetPillarDataWindow setPillarDataWindow;
	private SetWireDataWindow setWireDataWindow;
	public SetTextWindow setTextWindow;
	
	public HomeController() {
		drawer = new Drawer();
		homeWindow = new HomeWindow(this);
		drawer.setHomeController(this);
		fileProcess = new FileProcess();
		homeWindow.setFileProcess(fileProcess);
		archivFileBuilder = new ArchivFileBuilder();
		archivFileBuilder.init();
		drawer.setArchivFileBuilder(archivFileBuilder);
		fileProcess.setArchivFileBuilder(archivFileBuilder);
	}
	
	public Drawer getDrawer() {
		return drawer;
	}
	
	public void init() {
		archivFileBuilder.init();
		getSetCoordSystemWindow();
		drawer.clearRoot();
		homeWindow.setPillarData.setDisable(true);
		homeWindow.setWireData.setDisable(true);
		homeWindow.addText.setDisable(false);
		PROJECT_NAME = null;
		setTitle(drawer.getRoot());
		drawer.drawPage();	
	}
	
	public void getSetCoordSystemWindow() {
		if( setCoordSystemWindow == null ) {
			setCoordSystemWindow = new SetDrawingSystemDataWindow(this);
		}
		else {
			setCoordSystemWindow.getStage().show();
		}
	}
	
	public void getSetPillarDataWindow() {
		if( setPillarDataWindow == null ) {
			setPillarDataWindow = new SetPillarDataWindow(this);
		}
		else {
			setPillarDataWindow.getStage().show();
		}
	}
	
	public void getSetWireDataWindow() {
		if( setWireDataWindow == null ) {
			
			setWireDataWindow = new SetWireDataWindow(this);
		}
		else {
			setWireDataWindow.getStage().show();
		}
	}
	
	public void getSetTextWindow() {
		if( setTextWindow == null) {
			setTextWindow = new SetTextWindow(this);
		}
		else {
			setTextWindow.getStage().show();
		}
		setTextWindow.getStage().setAlwaysOnTop(true);
		setTextWindow.getInputTextField().requestFocus();
	}
	
	public String setInputText(String title, String text) {
		TextInputDialog input = new TextInputDialog();
		Stage stage = (Stage) input.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		input.setTitle(title);
		input.setHeaderText(text);
		return input.showAndWait().isPresent() ? input.getEditor().getText() : null;
	}
	
	public void setTitle(BorderPane root) {
		((Stage) root.getScene().getWindow()).setTitle(Validate.isValidProjectName(PROJECT_NAME) ? 
				PROJECT_NAME + " - " + HomeWindow.DEFAULT_STAGE_TITLE : HomeWindow.DEFAULT_STAGE_TITLE);
	}
	
	public void saveProject() {
					
		if( PROJECT_NAME == null ) {
			String projectName = null;
			projectName = homeWindow.setProjectName();
			 if( projectName ==  null )
				 return;
			 if( !saveExistedProjectFile() ) {
				 fileProcess.setFolder();

				 if( fileProcess.isProjectFileExist() && 
						 !getConfirmationAlert("Létező projekt fájl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy felülírod?") ) {
				 	projectName = homeWindow.setProjectName();
				 }
				if( projectName != null )
				 save();
		}
			 return;
		} 
			
		if( FileProcess.FOLDER_PATH == null ) {
			 String projectName = null;
			 fileProcess.setFolder();
			 if( FileProcess.FOLDER_PATH == null )
				 return;
			 if( fileProcess.isProjectFileExist() && 
					 !getConfirmationAlert("Létező projekt fájl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy felülírod?") ) {
			 	projectName = homeWindow.setProjectName();
			 }
			if( projectName != null )
			 save();
			 
		}
		
			String projectName = PROJECT_NAME;
			
			if( fileProcess.isProjectFileExist() && 
					 !getConfirmationAlert("Létező projekt fájl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy felülírod?") ) {
			 	projectName = homeWindow.setProjectName();
			 }
			if( projectName != null )
				save();
}
	
	private boolean save() {
		
		if( fileProcess.saveProject() ) {
			getInfoAlert("Projekt fájl mentve", "\"" + HomeController.PROJECT_NAME + ".ewd\" projekt fájl mentve az alábbi mappába: \n"
					+ FileProcess.FOLDER_PATH + "\\") ;
			return true;
		}
		
		return false;
	}
	
	private boolean saveExistedProjectFile() {
		
		if( HomeController.PROJECT_NAME == null  ||  FileProcess.FOLDER_PATH == null )
			return false;
		
		if( fileProcess.isProjectFileExist() ) {
			
			if( getConfirmationAlert("Létező projekt fájl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy felülírod?") ) {
				save();
				return true;
			}
		}
		
		return false;
	}
	
	public void openProject() {
	List<String> projectData = fileProcess.openProject();
	setTitle(homeWindow.getRoot());
	if( !projectData.isEmpty() ) {
	loadDrawingSystemData(projectData);
	drawSystem();
	loadPillarData(projectData);
	loadWireData(projectData);
	loadTextData(projectData);
	homeWindow.addLine.setDisable(false);
	homeWindow.modifyBaseLine.setDisable(false);
	homeWindow.modifyVerticalScale.setDisable(false);
	homeWindow.toBeLastPillarTheBeginner.setDisable(false);
	homeWindow.exchangePillars.setDisable(false);
	}
}
	
	public void showInputDrawingSystemDataOnCoordSystemDataWindow() {
		
		getSetCoordSystemWindow();
		if( drawer.getElevationStartValue() != 0)
		setCoordSystemWindow.getController().startElevationValue.setText(String.valueOf(drawer.getElevationStartValue()));
		if( drawer.getVerticalScale() != 0)
		setCoordSystemWindow.getController().elevationScaleValue.setText(String.valueOf(drawer.getVerticalScale()));
		if( drawer.getLengthOfHorizontalAxis() != 0d)
		setCoordSystemWindow.getController().lengthOfPillars.setText(String.valueOf(drawer.getLengthOfHorizontalAxis()));
		if( drawer.getHorizontalScale() != 0)
		setCoordSystemWindow.getController().horizontalScaleValue.setText(String.valueOf(drawer.getHorizontalScale()));
	}
	
	private void loadDrawingSystemData(List<String> projectData) {
		
		if( !projectData.isEmpty() ) {
			String[] data = projectData.get(0).split("#");
			archivFileBuilder.init();
			archivFileBuilder.setSystemData(
				Double.parseDouble(data[1]),
				Integer.valueOf(data[2]),
				Integer.parseInt(data[3]), 
				Integer.parseInt(data[4]));
			}	
	}
	
	
	private void loadPillarData(List<String> projectData) {
		
		PillarData pillar = null;
		
		for (String dataLine : projectData) {
			String[] data = dataLine.split("#");
			if( "Pillar".equals(data[0]) ) {
			pillar = new PillarData(
					Double.parseDouble(data[1]),
					Double.parseDouble(data[2]),
					Double.parseDouble(data[3]), 
					Boolean.parseBoolean(data[4]));
			pillar.setId(ArchivFileBuilder.addID());
			archivFileBuilder.addPillar(pillar);
			drawer.drawInputPillar(pillar.getId());
			}
			else if( "PillarText".equals(data[0]) ) {
				
				drawer.setText(pillar.getId(), 
						data[1], 
						Double.parseDouble(data[2]), 
						Double.parseDouble(data[3]), 
						Integer.parseInt(data[4]), 
						Integer.parseInt(data[5]),
						Boolean.parseBoolean(data[6]));			
			}
		}
	}
	
	private void loadWireData(List<String> projectData) {
		
		WireData wire = null;
		
		for (String dataLine : projectData) {
			String[] data = dataLine.split("#");
			if( "Wire".equals(data[0]) ) {
			wire = new WireData(Double.parseDouble(data[1]), 
					Double.parseDouble(data[2]),
					Double.parseDouble(data[3]), 
					Boolean.parseBoolean(data[4]));
			wire.setId(ArchivFileBuilder.addID());
			archivFileBuilder.addWire(wire);
			drawer.drawInputWire(wire.getId());
			}
			else if( "WireText".equals(data[0]) ) {
				drawer.setText(wire.getId(), 
						data[1], 
						Double.parseDouble(data[2]), 
						Double.parseDouble(data[3]), 
						Integer.parseInt(data[4]), 
						Integer.parseInt(data[5]),
						Boolean.parseBoolean(data[6]));
			}
		}
		
	}	
	
	private void loadTextData(List<String> projectData) {
		
		for (String dataLine : projectData) {
			String[] textData = dataLine.split("#"); 
			if( "SingleText".equals(textData[0]) ) {
				drawer.setText(0,
						textData[1], 
						Double.parseDouble(textData[2]),
						Double.parseDouble(textData[3]),
						Integer.parseInt(textData[4]),
						Integer.parseInt(textData[5]),
						false);
			}
		}
}

	private void drawSystem() {
		
		drawer.setLengthOfHorizontalAxis(archivFileBuilder.getSystemData().getLengthOfHorizontalAxis());
		drawer.setHorizontalScale(archivFileBuilder.getSystemData().getHorizontalScale());
		drawer.setElevationStartValue(archivFileBuilder.getSystemData().getElevationStartValue());
		drawer.setVerticalScale(archivFileBuilder.getSystemData().getVerticalScale());
		drawer.clearRoot();
		homeWindow.setPillarData.setDisable(false);
		homeWindow.setWireData.setDisable(false);
		homeWindow.addText.setDisable(false);
		homeWindow.saveProject.setDisable(false);
		drawer.drawPage();
		if( drawer.getLengthOfHorizontalAxis() != 0 &&
				drawer.getHorizontalScale() != 0 &&
				drawer.getElevationStartValue() != 0 && 
				drawer.getVerticalScale()!= 0) {
		drawer.drawHorizontalAxis();
		drawer.writeDistanceValueForHorizontalAxis();
		drawer.drawVerticalAxis();
		drawer.writeElevationValueForVerticalAxis();
		}
	}
	
	public void exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		alert.setTitle("Kilépés a programból");
		alert.setHeaderText("Biztos, hogy kilépsz a programból?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    System.exit(0);
		}
	}
	
	public static void getInfoAlert(String title, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.show();
	}
	
	public static void getWarningAlert(String title, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.show();
	}
	
	public static boolean getConfirmationAlert(String title, String text) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/logo/MVM.jpg"));
		alert.setTitle(title);
		alert.setHeaderText(text);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get() == ButtonType.OK) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void modifyLengthOfBaseLine() {
		Double length;
		try {
			String inputValue = setInputText("A nyomvonal hosszának módosítása", "Add meg a nyomvonal hosszát méterben:");
			if( inputValue == null )
				return;
			length = Validate.isValidPositiveDoubleValue(inputValue);
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelelő nyomvonal hossz érték", "A nyomvonal hossza csak pozitív szám érték lehet.");
			return;
		}	
		
		archivFileBuilder.setSystemData(length, 
										drawer.getHorizontalScale(),
										drawer.getElevationStartValue(), 
										drawer.getVerticalScale());
		drawSystem();
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData);
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData);
		}
		for (TextData textData : archivFileBuilder.getTextData()) {
			if(textData.getId() != -1)
			drawer.drawInputText(textData);
		}
	}

	public void modifyScaleOfBaseLine() {
		int scale;
		try {
			String inputValue = setInputText("A nyomvonal méretarányának módosítása", 
					"Add meg a nyomvonal méretaránytényezőjét M= 1:");
			if( inputValue == null )
				return;
			scale = Validate.isValidPositiveIntegerValue(inputValue);
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelelő nyomvonal méretaránytényező érték", "A nyomvonal méretaránytényezője csak pozitív egész szám érték lehet.");
			return;
		}	
		
		archivFileBuilder.setSystemData(drawer.getLengthOfHorizontalAxis(), 
										scale,
										drawer.getElevationStartValue(), 
										drawer.getVerticalScale());
		
		drawSystem();
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData);
		}
		
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData);
		}
		for (TextData textData : archivFileBuilder.getTextData()) {
			if(textData.getId() != -1)
			drawer.drawInputText(textData);
		}
	}
	
	public void toBeTheLastPillarTheBeginner() {
		
		PillarData lastPillar = archivFileBuilder.getLastPillar();
		if( lastPillar == null )
			return;
		lastPillar.setDistanceOfPillar(0);
		archivFileBuilder.init();
		drawer.clearRoot();
		archivFileBuilder.setSystemData(drawer.getLengthOfHorizontalAxis(), 
										drawer.getHorizontalScale(),
										drawer.getElevationStartValue(), 
										drawer.getVerticalScale());
		archivFileBuilder.addPillar(lastPillar);
		drawSystem();
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData);
		}
	}
	
	public void exchangePillars() {
		
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			double distanceRatio = (drawer.getLengthOfHorizontalAxis() - pillarData.getDistanceOfPillar() ) / pillarData.getDistanceOfPillar();
			archivFileBuilder.changePillarDistanceText(pillarData.getId(), distanceRatio);
			pillarData.setDistanceOfPillar(drawer.getLengthOfHorizontalAxis() - pillarData.getDistanceOfPillar());
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			double distanceRatio = (drawer.getLengthOfHorizontalAxis() - wireData.getDistanceOfWire() ) / wireData.getDistanceOfWire();
			archivFileBuilder.changeWireDistanceText(wireData.getId(), distanceRatio);
			wireData.setDistanceOfWire(drawer.getLengthOfHorizontalAxis() - wireData.getDistanceOfWire());
		}
		drawer.clearRoot();
		drawSystem();
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData);
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData);
		}
	}
	
	public void modifyElevationStartValue() {
		int elevationStartValue;
		try {
			String inputValue = setInputText("A magassági lépték kezdő értékének megadása", 
					"Add meg a magassági lépték kezdő magasságát:");
			if( inputValue == null )
				return;
			elevationStartValue = Validate.isValidIntegerValue(inputValue);
			if( elevationStartValue  > archivFileBuilder.getMinElevationStartValue()  ||
					archivFileBuilder.getMaxElevationStartValue() > elevationStartValue + 100 )
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelelő magassági lépték kezdő érték", 
					"Magassági lépték kezdő értéke: "
					+ archivFileBuilder.getMinElevationStartValue()  + " >= kezdő érték"
					+ " és kezdő érték >= " + (archivFileBuilder.getMaxElevationStartValue() - 100));
			return;
		} 
		archivFileBuilder.setSystemData(drawer.getLengthOfHorizontalAxis(), 
				drawer.getHorizontalScale(),
				elevationStartValue,
				drawer.getVerticalScale());
		drawer.clearRoot();
		drawSystem();
		
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData);
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData);
		}
		
	}
}
