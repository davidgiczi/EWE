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
import hu.mvmxpert.david.giczi.electricwireeditor.service.ElectricWireCalculator;
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
	public ElectricWireCalculator calculator;
	
	
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
						 !getConfirmationAlert("L??tez?? projekt f??jl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy fel??l??rod?") ) {
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
					 !getConfirmationAlert("L??tez?? projekt f??jl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy fel??l??rod?") ) {
			 	projectName = setProjectName();
			 }
			if( projectName != null )
			 save();
			 
		}
		
			String projectName = PROJECT_NAME;
			
			if( fileProcess.isProjectFileExist() && 
					 !getConfirmationAlert("L??tez?? projekt f??jl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy fel??l??rod?") ) {
			 	projectName = setProjectName();
			 }
			if( projectName != null )
				save();
}
	
	private boolean save() {
		
		if( fileProcess.saveProject() ) {
			getInfoAlert("Projekt f??jl mentve", "\"" + HomeController.PROJECT_NAME + ".ewd\" projekt f??jl mentve az al??bbi mapp??ba:\n"
					+ FileProcess.FOLDER_PATH + "\\") ;
			return true;
		}
		
		return false;
	}
	
	private boolean saveExistedProjectFile() {
		
		if( HomeController.PROJECT_NAME == null  ||  FileProcess.FOLDER_PATH == null )
			return false;
		
		if( fileProcess.isProjectFileExist() ) {
			
			if( getConfirmationAlert("L??tez?? projekt f??jl", HomeController.PROJECT_NAME + ".ewd\nBiztos, hogy fel??l??rod?") ) {
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
		alert.setTitle("Kil??p??s a programb??l");
		alert.setHeaderText("Biztos, hogy kil??psz a programb??l?");
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
			String inputValue = setInputText("A nyomvonal hossz??nak m??dos??t??sa", "Add meg a nyomvonal hossz??t m??terben:");
			if( inputValue == null )
				return;
			length = Validate.isValidPositiveDoubleValue(inputValue);
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelel?? nyomvonal hossz ??rt??k", "A nyomvonal hossza csak pozit??v sz??m ??rt??k lehet.");
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
			String inputValue = setInputText("A nyomvonal m??retar??ny??nak m??dos??t??sa", 
					"Add meg a nyomvonal m??retar??nyt??nyez??j??t M= 1:");
			if( inputValue == null )
				return;
			scale = Validate.isValidPositiveIntegerValue(inputValue);
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelel?? nyomvonal m??retar??nyt??nyez?? ??rt??k", 
					"A nyomvonal m??retar??nyt??nyez??je csak pozit??v eg??sz sz??m ??rt??k lehet.");
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
					lastPillar.getPillarTextList().get(i).getTextValue().startsWith(WireType.k??z??p.toString()) ||
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
			getWarningAlert("A sorrend nem m??dos??that??", 
					"A m??velethez legal??bb k??t oszlop sz??ks??ges.");
			return;
		}
		Collections.sort(archivFileBuilder.getPillarData());
		PillarData lastPillar = archivFileBuilder.getPillarData().get(archivFileBuilder.getPillarData().size() - 1);
		if(lastPillar.getDistanceOfPillar() > archivFileBuilder.getSystemData().getLengthOfHorizontalAxis()) {
			getWarningAlert("A sorrend nem m??dos??that??", 
					"A z??r??oszlop t??vols??ga: z??r??oszlop t??vols??g =< " 
			+ archivFileBuilder.getSystemData().getLengthOfHorizontalAxis() + "m.");
			return;
		}
		if( !archivFileBuilder.getWireData().isEmpty() ) {
			Collections.sort(archivFileBuilder.getWireData());
			if( archivFileBuilder.getWireData().get(archivFileBuilder.getWireData().size() - 1).getDistanceOfWire() >
			lastPillar.getDistanceOfPillar() ) {
				getWarningAlert("A sorrend nem m??dos??that??", 
						"A vezet??k t??vols??ga legyen: vezet??k t??vols??g =< " 
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
			String inputValue = setInputText("A magass??gi l??pt??k kezd?? ??rt??k??nek megad??sa", 
					"Add meg a magass??gi l??pt??k kezd?? magass??g??t:");
			if( inputValue == null )
				return;
			elevationStartValue = Validate.isValidIntegerValue(inputValue);
			if( elevationStartValue  > archivFileBuilder.getMinElevationStartValue()  ||
					archivFileBuilder.getMaxElevationStartValue() > elevationStartValue + 10 * drawer.getVerticalScale() )
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelel?? magass??gi l??pt??k kezd?? ??rt??k", 
					"Magass??gi l??pt??k kezd?? ??rt??ke eg??sz sz??m lehet ??s "
					+ df.format(archivFileBuilder.getMinElevationStartValue()).replace(",", ".")  + " >= kezd?? ??rt??k"
					+ " ??s kezd?? ??rt??k >= " + df.format(archivFileBuilder.getMaxElevationStartValue() 
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
			String inputValue = setInputText("A magass??gi l??pt??k beoszt??s ??rt??k??nek megad??sa", 
					"Add meg a magass??gi l??pt??k beoszt??s ??rt??k??t:");
			if( inputValue == null )
				return;
			verticalScale = Validate.isValidPositiveIntegerValue(inputValue);
			if( archivFileBuilder.getMinElevationStartValue() > drawer.getElevationStartValue() + 10 * verticalScale ||
				drawer.getElevationStartValue() + 10 * verticalScale < archivFileBuilder.getMaxElevationStartValue())
				throw new NumberFormatException();
				} catch (NumberFormatException e) {
			getWarningAlert("Nem megfelel?? magass??gi l??pt??k beoszt??s ??rt??k", 
					"Magass??gi l??pt??k beoszt??s ??rt??ke eg??sz sz??m lehet ??s " + 
					df.format((archivFileBuilder.getMaxElevationStartValue() - drawer.getElevationStartValue()) / 10).replace(",", ".") + 
					"m  =< beoszt??s ??rt??k");
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
	
	
	public void showCalculatedWire(String wireTypeName, String wireType) {
		 calculator = new ElectricWireCalculator(archivFileBuilder, wireTypeName, wireType);
		 drawer.drawCalculatedWire(calculator.wirePoints, wireType);
	}
	
	
	public void showLeftWire() {
		List<WirePoint> wirePoints = archivFileBuilder.getLeftWirePoints();
		if(wirePoints.size() < 2) {
			getWarningAlert("Sodrony nem rajzolhat??", 
					"Sodrony kirajzol??s??hoz legal??bb k??t k??l??nb??z?? bal oldali oszlop vagy vezet??k pont sz??ks??ges.");
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
			getWarningAlert("M??rt sodrony elt??r??sek nem sz??m??that??k ", 
					"A sodrony elt??r??seinek sz??m??t??s??hoz legal??bb n??gy k??l??nb??z?? bal oldali oszlop vagy vezet??k pont sz??ks??ges.");
			return;
		}
		int minimumPlace = 0;
		try {
			String inputValue = setInputText("Minimum magass??g?? sodrony pont hely??nek megad??sa", 
					"A m??rt helyeket balr??l-jobbra ??rtelmezve 1-t??l " + 
			(wirePoints.size() - 2) +"-ig val?? sz??moz??s alapj??n add meg a minimum pont hely??t:");
			if( inputValue == null )
				return;
				minimumPlace = Validate.isValidPositiveIntegerValue(inputValue);
			if( minimumPlace < 1 || wirePoints.size() - 2 < minimumPlace )
				throw new NumberFormatException();
				} catch (NumberFormatException e) {
			getWarningAlert("Hib??s minimum magass??g?? helyre val?? hivatkoz??s", "A minimum magass??g?? hely sorsz??ma 1-t??l " 
				+ (wirePoints.size() - 2) + "-ig lehet.");
			return;
		}
		drawer.writeDifferenceOfWireCurve(wirePoints, minimumPlace, "-2");
	}
	
	public void showRightWire() { 
		List<WirePoint> wirePoints = archivFileBuilder.getRightWirePoints();
		if(wirePoints.size() < 2) {
			getWarningAlert("Sodrony nem rajzolhat??", 
					"Sodrony kirajzol??s??hoz legal??bb k??t k??l??nb??z?? jobb oldali oszlop vagy vezet??k pont sz??ks??ges.");
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
			getWarningAlert("M??rt sodrony elt??r??sek nem sz??m??that??k ", 
					"A sodrony elt??r??seinek sz??m??t??s??hoz legal??bb n??gy k??l??nb??z?? jobb oldali oszlop vagy vezet??k pont sz??ks??ges.");
			return;
		}
			int minimumPlace = 0;
			try {
				String inputValue = setInputText("Minimum magass??g?? sodrony pont hely??nek megad??sa", 
						"A m??rt helyeket balr??l-jobbra ??rtelmezve 1-t??l " + 
				(wirePoints.size() - 2) +"-ig val?? sz??moz??s alapj??n add meg a minimum pont hely??t:");
				if( inputValue == null )
					return;
					minimumPlace = Validate.isValidPositiveIntegerValue(inputValue);
				if( minimumPlace < 1 || wirePoints.size() - 2 < minimumPlace )
					throw new NumberFormatException();
					} catch (NumberFormatException e) {
				getWarningAlert("Hib??s minimum magass??g?? helyre val?? hivatkoz??s", "A minimum magass??g?? hely sorsz??ma 1-t??l " 
					+ (wirePoints.size() - 2) + "-ig lehet.");
				return;
			}
			drawer.writeDifferenceOfWireCurve(wirePoints, minimumPlace, "-3");
		}
	
	public String setProjectName() {
		
		String projectName = setInputText("Projekt nev??nek megad??sa", "Add meg a projekt nev??t:");
		if(projectName == null){
			return null;
		}
		else if( Validate.isValidInputText(projectName) ) {
			HomeController.PROJECT_NAME = projectName;
		}
		else {
			HomeController.getWarningAlert("Nem megfelel?? projektn??v", "A projekt neve legal??bb 3 karakter hossz??s??g??.");
		}
		setTitle(homeWindow.getRoot());
		return projectName;
}
		
	public void save2DWireCoords() {
		
		showSaveWireCoordsWindow("Sodrony pontok ment??se helyi rendszerben -> 2D", true);
		
	}
	
	public void save3DWireCoords() {
		
		showSaveWireCoordsWindow("Sodrony pontok ment??se orsz??gos rendszerben -> 3D", false);
		
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
			getInfoAlert("K??perny??k??p ment??se", "K??perny??k??p mentve az al??bbi mapp??ba:\n" + 
			FileProcess.FOLDER_PATH + "\\" + PROJECT_NAME + ".png");
		} catch (AWTException | IOException e) {
			getWarningAlert("K??perny??k??p ment??se", "K??perny??k??p ment??se sikeretelen.");
			e.printStackTrace();
		}
	}
	
}
