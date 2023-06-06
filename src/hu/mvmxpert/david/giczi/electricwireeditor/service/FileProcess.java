package hu.mvmxpert.david.giczi.electricwireeditor.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.model.LineData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.SavedWirePoint;
import hu.mvmxpert.david.giczi.electricwireeditor.model.TextData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WirePoint;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class FileProcess {
	
	public static String FOLDER_PATH;
	private ArchivFileBuilder archivFileBuilder;
	private static HomeController homeController;
	
	public void setArchivFileBuilder(ArchivFileBuilder archivFileBuilder) {
		this.archivFileBuilder = archivFileBuilder;
	}
	
	
	public static void setHomeController(HomeController homeController) {
		FileProcess.homeController = homeController;
	}

	public void setFolder() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(FOLDER_PATH == null ? new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		directoryChooser.setTitle("Válassz mentési mappát");
		File selectedFile =	directoryChooser.showDialog(homeController.homeWindow.primaryStage);
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getAbsolutePath();
		}	
	}
	
	public List<String> openProject() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ? new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz projekt fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Projekt fájlok (*.ewe)", "*.ewe");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(homeController.homeWindow.primaryStage);
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getParent();
			HomeController.PROJECT_NAME = selectedFile.getName().substring(0, selectedFile.getName().indexOf('.'));
		}
		return getProjectFileData();
	}
	
	private List<String> getProjectFileData(){
	
		List<String> projectData = new ArrayList<>();
		
		if(HomeController.PROJECT_NAME == null || FOLDER_PATH == null)
			return projectData;
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME+ ".ewe");
		
		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {
			
				String row = reader.readLine();
				while( row != null ) {
					projectData.add(row);
					row = reader.readLine();
				}
		}
			catch (IOException e) {
			homeController.getWarningAlert("Fájl megnyitása sikertelen", "\"" + file.getName() + "\" projekt fájl megnyitása sikertelen.");
			} 
		
		return projectData;
	}
	
	public static List<String> getWireTypeFileData(){
		
		List<String> wireTypeData = new ArrayList<>();
		
		File file = new File("./wiretype/sodronyok.txt");
		
		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {
			
				String row = reader.readLine();
				while( row != null ) {
					wireTypeData.add(row);
					row = reader.readLine();
				}
		}
			catch (IOException e) {
			homeController.getWarningAlert("Fájl megnyitása sikertelen", "\"" + file.getName() + "\" projekt fájl megnyitása sikertelen.");
			} 
		
		return wireTypeData;
	}
	
	
	public boolean isProjectFileExist() {
		
		if( FOLDER_PATH == null )
			return false;
		
		String[] ewd = new File(FOLDER_PATH).list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".ewe");
			}
		});

		return Arrays.asList(ewd).contains(HomeController.PROJECT_NAME + ".ewe");
	}
	
	public boolean saveProject() {
		
		if( FOLDER_PATH == null || HomeController.PROJECT_NAME == null) {
			return false;
		}
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + ".ewe");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			writer.write(archivFileBuilder.getSystemData().getDrawingSystemData());
			writer.newLine();
			for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			writer.write(pillarData.getPillarData());
			writer.newLine();
			writer.write(pillarData.getPillarTexts());
			writer.newLine();
			}
			for (WireData wireData : archivFileBuilder.getWireData()) {
				writer.write(wireData.getWireData());
				writer.newLine();
				writer.write(wireData.getWireTexts());
				writer.newLine();
				}
			for(LineData lineData : archivFileBuilder.getLineData()) {
				writer.write(lineData.getLineData());
				writer.newLine();
			}
			for(TextData textData : archivFileBuilder.getTextData()) {
				if( textData.getId() != -1 ) {
				writer.write(textData.getTextData());
				writer.newLine();
			}
		}
			
		} catch (IOException e) {
			homeController.getWarningAlert("Fájl mentése sikertelen", "\"" + file.getName() + "\" projekt fájl mentése sikertelen.");
			return false;
		} 
		
		return true;
	}
	
	public void save2DWirePointsInAutoCadFormat(List<SavedWirePoint> points, String type) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_" + type +  "_sodrony_2D" + ".scr");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			writer.write("_MULTIPLE _POINT");
			writer.newLine();
			for (SavedWirePoint savedWirePoint : points) {
				writer.write(savedWirePoint.get2DCoordDataWithoutID());
				writer.newLine();
			}
			
		} catch (IOException e) {
			homeController.getWarningAlert("Fájl mentése sikertelen", "\"" + file.getName() + "\" projekt fájl mentése sikertelen.");
		} 
	}
	
	public void save3DWirePointsInAutoCadFormat(List<SavedWirePoint> points, String type) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_" + type + "_sodrony_3D" + ".scr");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			writer.write("_MULTIPLE _POINT");
			writer.newLine();
			for (SavedWirePoint savedWirePoint : points) {
				writer.write(savedWirePoint.get3DCoordDataWithoutID());
				writer.newLine();
			}
			
		} catch (IOException e) {
			homeController.getWarningAlert("Fájl mentése sikertelen", "\"" + file.getName() + "\" projekt fájl mentése sikertelen.");
		} 
	}
	
	public void save2DWirePointsInTextFormat(List<SavedWirePoint> points) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_2D" + ".txt");
	
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8, true))) {

			for (SavedWirePoint savedWirePoint : points) {
				writer.write(savedWirePoint.get2DCoordDataWithID());
				writer.newLine();
			}
			
		} catch (IOException e) {
			homeController.getWarningAlert("Fájl mentése sikertelen", "\"" + file.getName() + "\" projekt fájl mentése sikertelen.");
		} 
	}
	
	public void save3DWirePointsInTextFormat(List<SavedWirePoint> points) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_3D" + ".txt");
	
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8, true))) {
			
			for (SavedWirePoint savedWirePoint : points) {
				writer.write(savedWirePoint.get3DCoordDataWithID());
				writer.newLine();
			}
			
		} catch (IOException e) {
			homeController.getWarningAlert("Fájl mentése sikertelen", "\"" + file.getName() + "\" projekt fájl mentése sikertelen.");
		} 
	}
	
	public void saveCalulatedWirePointsInTextFormat(List<WirePoint> wirePoints, String type) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_" + type  + "_sodrony_pontok.txt");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			for (int i = 0; i < wirePoints.size(); i++) {
				writer.write((i + 1) + " " + wirePoints.get(i).getWirePointDataForTxtFormat());
				writer.newLine();
			}
			
		} catch (IOException e) {
			homeController.getWarningAlert("Fájl mentése sikertelen", "\"" + file.getName() + "\" projekt fájl mentése sikertelen.");
		} 
	}
	
	public void saveCalulatedWirePointsInAutoCadFormat(List<WirePoint> wirePoints, String type) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_" + type  + "_sodrony_pontok.scr");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			writer.write("_MULTIPLE _POINT");
			writer.newLine();
			for (int i = 0; i < wirePoints.size(); i++) {
				writer.write(wirePoints.get(i).getWirePointDataForAutoCadFormat());
				writer.newLine();
			}
			
		} catch (IOException e) {
			homeController.getWarningAlert("Fájl mentése sikertelen", "\"" + file.getName() + "\" projekt fájl mentése sikertelen.");
		} 
	}
		
}
