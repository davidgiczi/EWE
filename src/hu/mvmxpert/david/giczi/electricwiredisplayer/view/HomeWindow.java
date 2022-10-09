package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Drawer;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.FileProcess;
import hu.mvmxpert.david.giczi.electricwiredisplayer.service.Validate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeWindow  {

	private HomeController homeController;
	private BorderPane root;
	private FileProcess fileProcess;
	public MenuItem setPillarData;
	public MenuItem setWireData;
	public MenuItem addText;
	public MenuItem addLine;
	public Menu modifyBaseLine;
	public Menu modifyVerticalScale;
	public MenuItem come2ndPillarTo1stPlace;
	public MenuItem exchangePillars;
	public MenuItem saveProject;
	public static String DEFAULT_STAGE_TITLE = "Elektromos távvezeték szabad magasságának dokumentálása";
	
	public BorderPane getRoot() {
		return root;
	}
	
	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public void setFileProcess(FileProcess fileProcess) {
		this.fileProcess = fileProcess;
	}

	public HomeWindow(HomeController homeController) {
			this.homeController = homeController;
			Stage primaryStage = new Stage();
		 	root = new BorderPane();
		 	root.widthProperty().addListener((obs, oldVal, newVal) -> {
		 		Validate.MAX_X_VALUE = (int) (root.getWidth() / Drawer.MILLIMETER);
		 		Drawer.X_DISTANCE = (root.widthProperty().get() - Drawer.A4_WIDTH) / 2 + Drawer.START_X;
		 	 
		 	});
		 	root.heightProperty().addListener((obs, oldVal, newVal) -> {
		 		Validate.MAX_Y_VALUE = (int) (root.getHeight() / Drawer.MILLIMETER);
		 	});
			createMenu();
			homeController.getDrawer().setRoot(root);
			Scene scene = new Scene(root);
			primaryStage.setMinWidth(1000);
			primaryStage.setMinHeight(750);
			primaryStage.setMaximized(true);
			primaryStage.setTitle(DEFAULT_STAGE_TITLE);
			primaryStage.getIcons().add(new Image("/logo/MVM.jpg"));
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	private void createMenu() {
		MenuBar menuBar = new MenuBar();
		menuBar.setCursor(Cursor.HAND);
		Menu projectProcess = new Menu("Projekt műveletek");
		Menu createNewProject = new Menu("Új projekt létrehozása");
		MenuItem setProjectName = new MenuItem("Projekt nevének megadása");
		setProjectName.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				setProjectName();
			}
		});
		MenuItem setProjectFolder = new MenuItem("Projekt mappa megadása");
		setProjectFolder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				fileProcess.setFolder();
			}
		});
		MenuItem setCoordSystem = new MenuItem("Rajzi rendszer beállítása");
		setCoordSystem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
	
				if( !homeController.archivFileBuilder.getPillarData().isEmpty() || 
						!homeController.archivFileBuilder.getWireData().isEmpty() ||
						!homeController.archivFileBuilder.getTextData().isEmpty() ) {
				
				if(HomeController.getConfirmationAlert("Korábbi projekt adatainak mentése szükséges", 
						"Mented a korábbi projekt adatait?")) {
					homeController.saveProject();
				}
		}		
				homeController.init();
			}
		});
		setPillarData = new MenuItem("Távvezeték oszlop adatok megadása");
		setPillarData.setDisable(true);
		setPillarData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.getSetPillarDataWindow();
			}
		});
		setWireData = new MenuItem("Távvezeték adatok megadása");
		setWireData.setDisable(true);
		setWireData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.getSetWireDataWindow();
			}
		});
		
		MenuItem openProject = new MenuItem("Projekt megnyitása");
		openProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			homeController.openProject();
			}
		});
		saveProject = new MenuItem("Projekt mentése");
		saveProject.setDisable(true);
		saveProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.saveProject();
			}
		});
		
		
		MenuItem exitProject = new MenuItem("Kilépés");
		exitProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
			if( !homeController.archivFileBuilder.getPillarData().isEmpty() || 
					!homeController.archivFileBuilder.getWireData().isEmpty() ||
					!homeController.archivFileBuilder.getTextData().isEmpty() ) {
				
				if(HomeController.getConfirmationAlert("A projekt adatainak mentése szükséges", 
						"Mented a projekt adatait?")) {
					homeController.saveProject();
				}
			}
				homeController.exit();
			}
		});
		createNewProject.getItems().addAll(setProjectName, new SeparatorMenuItem(), setProjectFolder, 
				new SeparatorMenuItem(), setCoordSystem, new SeparatorMenuItem(), setPillarData, new SeparatorMenuItem(), setWireData);
		projectProcess.getItems().addAll(createNewProject, new SeparatorMenuItem(), 
				openProject, new SeparatorMenuItem(), saveProject, new SeparatorMenuItem(), exitProject);
		Menu modifyDraw = new Menu("Rajz módosítása");
		addText = new MenuItem("Felirat hozzáadása");
		addText.setDisable(true);
		addText.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.getSetTextWindow();
			}
		});
		addLine = new MenuItem("Vonal hozzáadása");
		addLine.setDisable(true);
		addLine.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			
			
			}
		});
		modifyBaseLine = new Menu("Nyomvonal módosítása");
		modifyBaseLine.setDisable(true);
		MenuItem modifyLengthOfBaseLine = new MenuItem("A nyomvonal hosszának módosítása");
		modifyLengthOfBaseLine.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.modifyLengthOfBaseLine();
			}
		});
		MenuItem modifyHorizontalScale = new MenuItem("Vízszintes lépték módosítása");
		modifyBaseLine.getItems().addAll(modifyLengthOfBaseLine, modifyHorizontalScale);
		modifyVerticalScale = new Menu("Magassági lépték módosítása");
		MenuItem modifyElevationStartValue = new MenuItem("Magassági lépték kezdő magasságának módosítása");
		MenuItem modifyElevationMeasurment = new MenuItem("Magassági lépték beosztás értékének módosítása");
		modifyVerticalScale.getItems().addAll(modifyElevationStartValue, modifyElevationMeasurment);
		modifyVerticalScale.setDisable(true);
		come2ndPillarTo1stPlace = new MenuItem("A második oszlop legyen a kezdő oszlop");
		come2ndPillarTo1stPlace.setDisable(true);
		exchangePillars = new MenuItem("Az oszlopok felcserélése");
		exchangePillars.setDisable(true);
		modifyDraw.getItems().addAll(addText,  new SeparatorMenuItem(), 
				addLine, new SeparatorMenuItem(), 
				modifyBaseLine, new SeparatorMenuItem(),
				modifyVerticalScale,  new SeparatorMenuItem(),
				come2ndPillarTo1stPlace,
				new SeparatorMenuItem(), 
				exchangePillars);
		menuBar.getMenus().addAll(projectProcess, modifyDraw);
		root.setTop(menuBar);
	}
	
	public String setProjectName() {
		
		String projectName = homeController.setInputText("Projekt nevének megadása", "Add meg a projekt nevét:");
		if(projectName == null){
			return null;
		}
		else if( Validate.isValidProjectName(projectName) ) {
			HomeController.PROJECT_NAME = projectName;
		}
		else {
			HomeController.getWarningAlert("Nem megfelelő projektnév", "A projekt neve legalább 3 karakter hosszúságú.");
		}
		homeController.setTitle(root);
		
		return projectName;
}
	
	public void clearRoot(){
		
		for(int i = root.getChildren().size() - 1; i >= 0; i--) {
			if( root.getChildren().get(i) instanceof MenuBar ) {
				continue;
			}
			root.getChildren().remove(i);
		}
	}
}
