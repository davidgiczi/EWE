package hu.mvmxpert.david.giczi.electricwireeditor.controller;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import hu.mvmxpert.david.giczi.electricwireeditor.model.LineData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.TextData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WirePoint;
import hu.mvmxpert.david.giczi.electricwireeditor.service.ArchivFileBuilder;
import hu.mvmxpert.david.giczi.electricwireeditor.service.Drawer;
import hu.mvmxpert.david.giczi.electricwireeditor.service.FileProcess;
import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
import hu.mvmxpert.david.giczi.electricwireeditor.view.HomeWindow;
import hu.mvmxpert.david.giczi.electricwireeditor.view.SaveWireCoordsWindow;
import hu.mvmxpert.david.giczi.electricwireeditor.view.SetDrawingSystemDataWindow;
import hu.mvmxpert.david.giczi.electricwireeditor.view.SetLineWindow;
import hu.mvmxpert.david.giczi.electricwireeditor.view.SetPillarDataWindow;
import hu.mvmxpert.david.giczi.electricwireeditor.view.SetTextWindow;
import hu.mvmxpert.david.giczi.electricwireeditor.view.SetWireDataWindow;
import hu.mvmxpert.david.giczi.electricwireeditor.wiretype.WireType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeController {

	public HomeWindow homeWindow;
	public ArchivFileBuilder archivFileBuilder;
	public static String PROJECT_NAME;
	private Drawer drawer;
	public FileProcess fileProcess;
	public SetDrawingSystemDataWindow setCoordSystemWindow;
	public SetPillarDataWindow setPillarDataWindow;
	public SetWireDataWindow setWireDataWindow;
	public SetTextWindow setTextWindow;
	public SetLineWindow setLineWindow;
	public SaveWireCoordsWindow saveWireCoordsWindow;
	
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
		showSetCoordSystemWindow();
		drawer.clearRoot();
		homeWindow.setPillarData.setDisable(true);
		homeWindow.setWireData.setDisable(true);
		homeWindow.addText.setDisable(false);
		setTitle(drawer.getRoot());
		drawer.drawPage();	
	}
	
	public void showSetLineDataWindow() {
		if( setLineWindow == null ) {
			setLineWindow = new SetLineWindow(this);
		}
		else {
			setLineWindow.getStage().show();
		}
	}
	
	public void showSetCoordSystemWindow() {
		if( setCoordSystemWindow == null ) {
			setCoordSystemWindow = new SetDrawingSystemDataWindow(this);
		}
		else {
			setCoordSystemWindow.getStage().show();
		}
	}
	
	public void showSetPillarDataWindow() {
		if( setPillarDataWindow == null ) {
			setPillarDataWindow = new SetPillarDataWindow(this);
		}
		else {
			setPillarDataWindow.getStage().show();
		}
	}
	
	public void showSetWireDataWindow() {
		if( setWireDataWindow == null ) {
			
			setWireDataWindow = new SetWireDataWindow(this);
		}
		else {
			setWireDataWindow.getStage().show();
		}
	}
	
	public void showSetTextWindow() {
		if( setTextWindow == null) {
			setTextWindow = new SetTextWindow(this);
		}
		else {
			setTextWindow.getStage().show();
		}
		setTextWindow.getStage().setAlwaysOnTop(true);
		setTextWindow.getInputTextField().requestFocus();
	}
	
	
	public void showSaveWireCoordsWindow(String title, boolean is2DWindow) {
		if( saveWireCoordsWindow == null) {
			saveWireCoordsWindow = new SaveWireCoordsWindow(this);
		}
		else {
			saveWireCoordsWindow.getStage().show();
		}
		saveWireCoordsWindow.getStage().setTitle(title);
		saveWireCoordsWindow.controller.setIs2DWindow(is2DWindow);
		if( is2DWindow ) {
		saveWireCoordsWindow.controller.startX.setEditable(false);
		saveWireCoordsWindow.controller.startY.setEditable(false);
		saveWireCoordsWindow.controller.endX.setEditable(false);
		saveWireCoordsWindow.controller.endY.setEditable(false);
		}
		else {
		saveWireCoordsWindow.controller.startX.setEditable(true);
		saveWireCoordsWindow.controller.startY.setEditable(true);
		saveWireCoordsWindow.controller.endX.setEditable(true);
		saveWireCoordsWindow.controller.endY.setEditable(true);
		}
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
		if( PROJECT_NAME == null && FileProcess.FOLDER_PATH == null )
			((Stage) root.getScene().getWindow()).setTitle(HomeWindow.DEFAULT_STAGE_TITLE);
		else if( Validate.isValidInputText(PROJECT_NAME) && FileProcess.FOLDER_PATH == null )
			((Stage) root.getScene().getWindow()).setTitle(PROJECT_NAME + ".ewe" + " - " + HomeWindow.DEFAULT_STAGE_TITLE);
		else if( Validate.isValidInputText(PROJECT_NAME) && FileProcess.FOLDER_PATH != null )
			((Stage) root.getScene().getWindow()).setTitle(FileProcess.FOLDER_PATH + "\\" + PROJECT_NAME + ".ewe");
	}
	
	public void saveProject() {
					
		if( PROJECT_NAME == null ) {
			String projectName = null;
			projectName = setProjectName();
			 if( projectName ==  null )
				 return;
			 if( !saveExistedProjectFile() ) {
				 fileProcess.setFolder();

				 if( fileProcess.isProjectFileExist() && 
						 !getConfirmationAlert("Létező projekt fájl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy felülírod?") ) {
				 	projectName = setProjectName();
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
			 	projectName = setProjectName();
			 }
			if( projectName != null )
			 save();
			 
		}
		
			String projectName = PROJECT_NAME;
			
			if( fileProcess.isProjectFileExist() && 
					 !getConfirmationAlert("Létező projekt fájl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy felülírod?") ) {
			 	projectName = setProjectName();
			 }
			if( projectName != null )
				save();
}
	
	private boolean save() {
		
		if( fileProcess.saveProject() ) {
			getInfoAlert("Projekt fájl mentve", "\"" + HomeController.PROJECT_NAME + ".ewd\" projekt fájl mentve az alábbi mappába:\n"
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
	
	public boolean openProject() {
	List<String> projectData = fileProcess.openProject();
	setTitle(homeWindow.getRoot());
	if( !projectData.isEmpty() ) {
	loadDrawingSystemData(projectData);
	drawSystem();
	loadPillarData(projectData);
	loadWireData(projectData);
	loadTextData(projectData);
	loadLineData(projectData);
	drawer.removeLenghtOfBaseLineText();
	homeWindow.addLine.setDisable(false);
	homeWindow.modifyBaseLine.setDisable(false);
	homeWindow.modifyVerticalScale.setDisable(false);
	homeWindow.toBeLastPillarTheBeginner.setDisable(false);
	homeWindow.backwardsOrder.setDisable(false);
	}
	return  !projectData.isEmpty(); 
}
	
	public void showInputDrawingSystemDataOnCoordSystemDataWindow() {
		
		showSetCoordSystemWindow();
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
						Boolean.parseBoolean(data[6]),
						Boolean.parseBoolean(data[7]),
						Double.parseDouble(data[8]),
						Double.parseDouble(data[9]),
						Double.parseDouble(data[10]),
						Double.parseDouble(data[11])
						);
						
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
						Boolean.parseBoolean(data[6]),
						Boolean.parseBoolean(data[7]),
						Double.parseDouble(data[8]),
						Double.parseDouble(data[9]),
						Double.parseDouble(data[10]),
						Double.parseDouble(data[11]));
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
						Boolean.parseBoolean(textData[6]),
						Boolean.parseBoolean(textData[7]),
						Double.parseDouble(textData[8]),
						Double.parseDouble(textData[9]),
						Double.parseDouble(textData[10]),
						Double.parseDouble(textData[11]));
			}
		}
}
	
	private void loadLineData(List<String> projectData) {
		for(String dataLine : projectData) {
			String[] lineData = dataLine.split("#");
			if( "Line".equals(lineData[0])) {
				drawer.drawLine(Double.parseDouble(lineData[1]),
						Double.parseDouble(lineData[2]),
						Double.parseDouble(lineData[3]),
						Double.parseDouble(lineData[4]),
						lineData[5],
						new Color(Double.parseDouble(lineData[6]),
								Double.parseDouble(lineData[7]),
								Double.parseDouble(lineData[8]),
								Double.parseDouble(lineData[9])),
						lineData[10]);
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
		drawer.removeLenghtOfBaseLineText();
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData, 0);
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData, 0);
		}
		for (TextData textData : archivFileBuilder.getTextData()) {
			if(textData.getId() != -1)
			drawer.drawInputText(textData);
		}
		for (LineData lineData : archivFileBuilder.getLineData()) {
			drawer.drawInputLine(lineData.getId(),lineData.getStartX(), lineData.getStartY(), 
							lineData.getEndX(), lineData.getEndY(), 
							lineData.getType(),
							new Color(lineData.getRed(), lineData.getGreen(), lineData.getBlue(), lineData.getOpacity()), 
							lineData.getWidth());
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
			getWarningAlert("Nem megfelelő nyomvonal méretaránytényező érték", 
					"A nyomvonal méretaránytényezője csak pozitív egész szám érték lehet.");
			return;
		}	
		
		archivFileBuilder.setSystemData(drawer.getLengthOfHorizontalAxis(), 
										scale,
										drawer.getElevationStartValue(), 
										drawer.getVerticalScale());
		
		drawSystem();
		drawer.removeLenghtOfBaseLineText();
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData, 0);
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData, 0);
		}
		for (TextData textData : archivFileBuilder.getTextData()) {
			if(textData.getId() != -1)
			drawer.drawInputText(textData);
		}
		for (LineData lineData : archivFileBuilder.getLineData()) {
			drawer.drawInputLine(lineData.getId(), lineData.getStartX(), lineData.getStartY(), 
							lineData.getEndX(), lineData.getEndY(), 
							lineData.getType(),
							new Color(lineData.getRed(), lineData.getGreen(), lineData.getBlue(), lineData.getOpacity()), 
							lineData.getWidth());
		}
	}
	
	public void toBeTheLastPillarTheBeginner() {
		
		PillarData lastPillar = archivFileBuilder.getLastPillar();
		if( lastPillar == null )
			return;
		List<TextData> textList = archivFileBuilder.getTextData();
		List<LineData> lineList = archivFileBuilder.getLineData();
		lastPillar.setDistanceOfPillar(0);
		
		for (int i = lastPillar.getPillarTextList().size() - 1; i >= 0; i--) {
			String[] values = lastPillar.getPillarTextList().get(i).getTextValue().split("\\s+");
			if( (lastPillar.getPillarTextList().get(i).getTextValue().startsWith(WireType.bal.toString()) ||
					lastPillar.getPillarTextList().get(i).getTextValue().startsWith(WireType.közép.toString()) ||
						lastPillar.getPillarTextList().get(i).getTextValue().startsWith(WireType.jobb.toString())) && values.length == 2 ) {
				lastPillar.getPillarTextList().remove(i);
			}
		}
		archivFileBuilder.init();
		drawer.clearRoot();
		archivFileBuilder.setSystemData(drawer.getLengthOfHorizontalAxis(), 
										drawer.getHorizontalScale(),
										drawer.getElevationStartValue(), 
										drawer.getVerticalScale());
		lastPillar.setId(ArchivFileBuilder.addID());
		archivFileBuilder.addPillar(lastPillar);
		drawSystem();
		for (TextData textData : textList) {
			if( textData.getId() !=  -1 ) {
			textData.setId(ArchivFileBuilder.addID());
			archivFileBuilder.getTextData().add(textData);
			drawer.drawInputText(textData);
			}
		}
		for (LineData lineData : lineList) {
			lineData.setId(ArchivFileBuilder.addID());
			archivFileBuilder.getLineData().add(lineData);
			drawer.drawInputLine(lineData.getId(), lineData.getStartX(), lineData.getStartY(), 
					lineData.getEndX(), lineData.getEndY(), lineData.getType(), 
					new Color(lineData.getRed(), lineData.getGreen(), lineData.getBlue(), lineData.getOpacity()), lineData.getWidth());
		}
		drawer.drawInputPillar(lastPillar.getId());
		drawer.drawInputPillarText(lastPillar, 0);
		
		}
	
	public void backwardsOrder() {
		if(archivFileBuilder.getPillarData().size() < 2 ) {
			getWarningAlert("A sorrend nem módosítható", 
					"A művelethez legalább két oszlop szükséges.");
			return;
		}
		Collections.sort(archivFileBuilder.getPillarData());
		PillarData lastPillar = archivFileBuilder.getPillarData().get(archivFileBuilder.getPillarData().size() - 1);
		if(lastPillar.getDistanceOfPillar() > archivFileBuilder.getSystemData().getLengthOfHorizontalAxis()) {
			getWarningAlert("A sorrend nem módosítható", 
					"A záróoszlop távolsága: záróoszlop távolság =< " 
			+ archivFileBuilder.getSystemData().getLengthOfHorizontalAxis() + "m.");
			return;
		}
		if( !archivFileBuilder.getWireData().isEmpty() ) {
			Collections.sort(archivFileBuilder.getWireData());
			if( archivFileBuilder.getWireData().get(archivFileBuilder.getWireData().size() - 1).getDistanceOfWire() >
			lastPillar.getDistanceOfPillar() ) {
				getWarningAlert("A sorrend nem módosítható", 
						"A vezeték távolsága legyen: vezeték távolság =< " 
				+ lastPillar.getDistanceOfPillar() + "m.");
			return;
			}
		}
		Collections.sort(archivFileBuilder.getWireData());
		for (PillarData pillar: archivFileBuilder.getPillarData()) {
			archivFileBuilder.reorderPillar(pillar, lastPillar);
		}
		for(WireData wire : archivFileBuilder.getWireData()) {
			archivFileBuilder.reorderWire(wire, lastPillar);
		}
		drawer.clearRoot();
		drawSystem();
		drawer.removeLenghtOfBaseLineText();
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData, 0);
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData, 0);
		}
		for (TextData textData : archivFileBuilder.getTextData()) {
			if(textData.getId() != -1)
			drawer.drawInputText(textData);
		}
		for (LineData lineData : archivFileBuilder.getLineData()) {
			drawer.drawInputLine(lineData.getId(), lineData.getStartX(), lineData.getStartY(), 
							lineData.getEndX(), lineData.getEndY(), 
							lineData.getType(),
							new Color(lineData.getRed(), lineData.getGreen(), lineData.getBlue(), lineData.getOpacity()), 
							lineData.getWidth());
		}
	}
	
	public void modifyElevationStartValue() {
		DecimalFormat df = new DecimalFormat("0.0");
		int elevationStartValue;
		try {
			String inputValue = setInputText("A magassági lépték kezdő értékének megadása", 
					"Add meg a magassági lépték kezdő magasságát:");
			if( inputValue == null )
				return;
			elevationStartValue = Validate.isValidIntegerValue(inputValue);
			if( elevationStartValue  > archivFileBuilder.getMinElevationStartValue()  ||
					archivFileBuilder.getMaxElevationStartValue() > elevationStartValue + 10 * drawer.getVerticalScale() )
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelelő magassági lépték kezdő érték", 
					"Magassági lépték kezdő értéke egész szám lehet és "
					+ df.format(archivFileBuilder.getMinElevationStartValue()).replace(",", ".")  + " >= kezdő érték"
					+ " és kezdő érték >= " + df.format(archivFileBuilder.getMaxElevationStartValue() 
							- 10 * drawer.getVerticalScale()).replace(",", "."));
			return;
		}
		double shiftY = (elevationStartValue - drawer.getElevationStartValue()) * Drawer.MILLIMETER;
		archivFileBuilder.setSystemData(drawer.getLengthOfHorizontalAxis(), 
				drawer.getHorizontalScale(),
				elevationStartValue,
				drawer.getVerticalScale());
		drawer.clearRoot();
		drawSystem();
		
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData, shiftY);
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData, shiftY);
		}
		for (TextData textData : archivFileBuilder.getTextData()) {
			if(textData.getId() != -1)
			drawer.drawInputText(textData);
		}
		for (LineData lineData : archivFileBuilder.getLineData()) {
			drawer.drawInputLine(lineData.getId(), lineData.getStartX(), lineData.getStartY(), 
							lineData.getEndX(), lineData.getEndY(), 
							lineData.getType(),
							new Color(lineData.getRed(), lineData.getGreen(), lineData.getBlue(), lineData.getOpacity()), 
							lineData.getWidth());
		}
	}
	
	public void modifyVerticalScale() {
		DecimalFormat df = new DecimalFormat("0.0");
		int verticalScale = drawer.getVerticalScale();
		try {
			String inputValue = setInputText("A magassági lépték beosztás értékének megadása", 
					"Add meg a magassági lépték beosztás értékét:");
			if( inputValue == null )
				return;
			verticalScale = Validate.isValidPositiveIntegerValue(inputValue);
			if( archivFileBuilder.getMinElevationStartValue() > drawer.getElevationStartValue() + 10 * verticalScale ||
				drawer.getElevationStartValue() + 10 * verticalScale < archivFileBuilder.getMaxElevationStartValue())
				throw new NumberFormatException();
				} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelelő magassági lépték beosztás érték", 
					"Magassági lépték beosztás értéke egész szám lehet és " + 
					df.format((archivFileBuilder.getMaxElevationStartValue() - drawer.getElevationStartValue()) / 10).replace(",", ".") + 
					"m  =< beosztás érték");
			return;
		}
		archivFileBuilder.setSystemData(drawer.getLengthOfHorizontalAxis(), 
				drawer.getHorizontalScale(),
				drawer.getElevationStartValue(),
				verticalScale);
		drawer.clearRoot();
		drawSystem();
		for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			drawer.drawInputPillar(pillarData.getId());
			drawer.drawInputPillarText(pillarData, 0);
		}
		for (WireData wireData : archivFileBuilder.getWireData()) {
			drawer.drawInputWire(wireData.getId());
			drawer.drawInputWireText(wireData, 0);
		}
		for (TextData textData : archivFileBuilder.getTextData()) {
			if(textData.getId() != -1)
			drawer.drawInputText(textData);
		}
		for (LineData lineData : archivFileBuilder.getLineData()) {
			drawer.drawInputLine(lineData.getId(), lineData.getStartX(), lineData.getStartY(), 
							lineData.getEndX(), lineData.getEndY(), 
							lineData.getType(),
							new Color(lineData.getRed(), lineData.getGreen(), lineData.getBlue(), lineData.getOpacity()), 
							lineData.getWidth());
		}
	}
	
	public void showLeftWire() {
		List<WirePoint> wirePoints = archivFileBuilder.getLeftWirePoints();
		if(wirePoints.size() < 2) {
			getWarningAlert("Sodrony nem rajzolható", 
					"Sodrony kirajzolásához legalább két különböző bal oldali oszlop vagy vezeték pont szükséges.");
			return;
		}	
		drawer.drawLeftWireCurve(wirePoints);
	}
	
	public void deleteLeftWire() {
		drawer.deleteLeftWire();
	}
	
	public void showDifferenceOfCurveOfLeftWire() {
		List<WirePoint> wirePoints = archivFileBuilder.getLeftWirePoints();
		if(wirePoints.size() < 4) {
			getWarningAlert("Mért sodrony eltérések nem számíthatók ", 
					"A sodrony eltéréseinek számításához legalább négy különböző bal oldali oszlop vagy vezeték pont szükséges.");
			return;
		}
		int minimumPlace = 0;
		try {
			String inputValue = setInputText("Minimum magasságú sodrony pont helyének megadása", 
					"A mért helyeket balról-jobbra értelmezve 1-től " + 
			(wirePoints.size() - 2) +"-ig való számozás alapján add meg a minimum pont helyét:");
			if( inputValue == null )
				return;
				minimumPlace = Validate.isValidPositiveIntegerValue(inputValue);
			if( minimumPlace < 1 || wirePoints.size() - 2 < minimumPlace )
				throw new NumberFormatException();
				} catch (NumberFormatException e) {
			getWarningAlert("Hibás minimum magasságú helyre való hivatkozás", "A minimum magasságú hely sorszáma 1-től " 
				+ (wirePoints.size() - 2) + "-ig lehet.");
			return;
		}
		drawer.writeDifferenceOfWireCurve(wirePoints, minimumPlace, "-2");
	}
	
	public void showRightWire() { 
		List<WirePoint> wirePoints = archivFileBuilder.getRightWirePoints();
		if(wirePoints.size() < 2) {
			getWarningAlert("Sodrony nem rajzolható", 
					"Sodrony kirajzolásához legalább két különböző jobb oldali oszlop vagy vezeték pont szükséges.");
			return;
		}
		drawer.drawRightWireCurve(wirePoints);
	}
	
	public void deleteRightWire() {
		drawer.deleteRightWire();
	}
	
	public void showDifferenceOfCurveOfRightWire() {
		List<WirePoint> wirePoints = archivFileBuilder.getRightWirePoints();
		if(wirePoints.size() < 4) {
			getWarningAlert("Mért sodrony eltérések nem számíthatók ", 
					"A sodrony eltéréseinek számításához legalább négy különböző jobb oldali oszlop vagy vezeték pont szükséges.");
			return;
		}
			int minimumPlace = 0;
			try {
				String inputValue = setInputText("Minimum magasságú sodrony pont helyének megadása", 
						"A mért helyeket balról-jobbra értelmezve 1-től " + 
				(wirePoints.size() - 2) +"-ig való számozás alapján add meg a minimum pont helyét:");
				if( inputValue == null )
					return;
					minimumPlace = Validate.isValidPositiveIntegerValue(inputValue);
				if( minimumPlace < 1 || wirePoints.size() - 2 < minimumPlace )
					throw new NumberFormatException();
					} catch (NumberFormatException e) {
				getWarningAlert("Hibás minimum magasságú helyre való hivatkozás", "A minimum magasságú hely sorszáma 1-től " 
					+ (wirePoints.size() - 2) + "-ig lehet.");
				return;
			}
			drawer.writeDifferenceOfWireCurve(wirePoints, minimumPlace, "-3");
		}
	
	public String setProjectName() {
		
		String projectName = setInputText("Projekt nevének megadása", "Add meg a projekt nevét:");
		if(projectName == null){
			return null;
		}
		else if( Validate.isValidInputText(projectName) ) {
			HomeController.PROJECT_NAME = projectName;
		}
		else {
			HomeController.getWarningAlert("Nem megfelelő projektnév", "A projekt neve legalább 3 karakter hosszúságú.");
		}
		setTitle(homeWindow.getRoot());
		return projectName;
}
		
	public void save2DWireCoords() {
		
		showSaveWireCoordsWindow("Sodrony pontok mentése helyi rendszerben -> 2D", true);
		
	}
	
	public void save3DWireCoords() {
		
		showSaveWireCoordsWindow("Sodrony pontok mentése országos rendszerben -> 3D", false);
		
	}
	
	
	public void printScreen() {
		if( FileProcess.FOLDER_PATH == null )
			fileProcess.setFolder();
		if( PROJECT_NAME == null )
			setProjectName();
		if( FileProcess.FOLDER_PATH == null || PROJECT_NAME == null )
			return;
			
		try {
			Robot rob = new Robot();
			int targetWidth = (int) (137 * Drawer.MILLIMETER * 1.25);
			int targetHeight = (int) (150 * Drawer.MILLIMETER * 1.25);
			Rectangle capture = new Rectangle(new Dimension((int) (137 * Drawer.MILLIMETER), (int) (150 * Drawer.MILLIMETER)));
			capture.setLocation((int) (94 * Drawer.MILLIMETER), (int) (17 * Drawer.MILLIMETER));
			BufferedImage originalImage = rob.createScreenCapture(capture);
			java.awt.Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_DEFAULT);
		    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
			ImageIO.write(outputImage, "png", new File(FileProcess.FOLDER_PATH + "\\" + PROJECT_NAME + ".png"));
			getInfoAlert("Képernyőkép mentése", "Képernyőkép mentve az alábbi mappába:\n" + 
			FileProcess.FOLDER_PATH + "\\" + PROJECT_NAME + ".png");
		} catch (AWTException | IOException e) {
			getWarningAlert("Képernyőkép mentése", "Képernyőkép mentése sikeretelen.");
			e.printStackTrace();
		}
	}
	
}
