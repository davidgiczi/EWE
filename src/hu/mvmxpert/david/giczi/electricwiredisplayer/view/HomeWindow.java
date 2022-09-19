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
	public MenuItem setPillarData;
	public MenuItem setWireData;
	public MenuItem addText;
	public MenuItem addLine;
	public MenuItem modifyLengthOfBaseLine;
	public MenuItem modifyHorizontalScale;
	public MenuItem modifyElevationStartValue;
	public MenuItem modifyElevationMeasurment;
	public MenuItem exchangePillars; 
	
	
	public BorderPane getRoot() {
		return root;
	}

	public HomeWindow(HomeController homeController) {
			this.homeController = homeController;
			Stage primaryStage = new Stage();
		 	root = new BorderPane();
		 	root.widthProperty().addListener((obs, oldVal, newVal) -> {
		 		Validate.MAX_X_VALUE = (int) (root.getWidth() / Drawer.MILLIMETER);
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
			primaryStage.setTitle("Elektromos távvezeték szabad magasságának dokumentálása");
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
			String projectName = homeController.setInputText("Projekt nevének megadása", "Add meg a projekt nevét:");
			if( projectName.isEmpty() ) {
				return;
			}
			else if( Validate.isValidProjectName(projectName) ) {
				HomeController.PROJECT_NAME = projectName;
			}
			else {
				HomeController.getWarningAlert("Nem megfelelő projektnév", "A projekt neve legalább 3 karakter hosszúságú.");
			}
			homeController.setTitle(root);
			}
		});
		MenuItem setProjectFolder = new MenuItem("Projekt mappa megadása");
		setProjectFolder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileProcess.setFolder();
			}
		});
		MenuItem setCoordSystem = new MenuItem("Rajzi rendszer beállítása");
		setCoordSystem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
//				if(HomeController.getConfirmationAlert("Korábbi projekt adatainak mentése szükséges", 
//						"Mented a korábbi projekt adatait?")) {
//				}
				
				homeController.getSetCoordSystemWindow();
				clearRoot();
				setPillarData.setDisable(true);
				setWireData.setDisable(true);
				addText.setDisable(false);
				homeController.getDrawer().drawPage();
				
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
			FileProcess.setProject();	
			}
		});
		MenuItem saveProject = new MenuItem("Projekt mentése");
		MenuItem exitProject = new MenuItem("Kilépés");
		exitProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(HomeController.getConfirmationAlert("A projekt adatainak mentése szükséges", 
						"Mented a projekt adatait?")) {
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
		//addLine.setDisable(true);
		Menu modifyBaseLine = new Menu("Nyomvonal módosítása");
		modifyLengthOfBaseLine = new MenuItem("Az oszlopok távolságának módosítása");
		modifyHorizontalScale = new MenuItem("Vízszintes lépték módosítása");
		modifyBaseLine.getItems().addAll(modifyLengthOfBaseLine, modifyHorizontalScale);
		Menu modifyVerticalScale = new Menu("Magassági lépték módosítása");
		modifyElevationStartValue = new MenuItem("Magassági lépték kezdő magasságának módosítása");
		modifyElevationMeasurment = new MenuItem("Magassági lépték beosztás értékének módosítása");
		modifyVerticalScale.getItems().addAll(modifyElevationStartValue, modifyElevationMeasurment);
		exchangePillars = new MenuItem("Az oszlopok felcserélése");
		modifyDraw.getItems().addAll(addText,  new SeparatorMenuItem(), 
				addLine, new SeparatorMenuItem(), 
				modifyBaseLine, new SeparatorMenuItem(),
				modifyVerticalScale,  new SeparatorMenuItem(),
				exchangePillars);
		menuBar.getMenus().addAll(projectProcess, modifyDraw);
		root.setTop(menuBar);
	}

	private void clearRoot(){
		
		for(int i = root.getChildren().size() - 1; i >= 0; i--) {
			if( root.getChildren().get(i) instanceof MenuBar ) {
				continue;
			}
			root.getChildren().remove(i);
		}
	}
}
