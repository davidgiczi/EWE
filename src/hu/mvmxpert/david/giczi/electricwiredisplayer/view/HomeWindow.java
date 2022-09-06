package hu.mvmxpert.david.giczi.electricwiredisplayer.view;

import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.fileprocess.FileProcess;
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
		
	public HomeWindow(HomeController homeController) {
			this.homeController = homeController;
		 	Stage primaryStage = new Stage();
		 	root = new BorderPane();
			createMenu();
			homeController.getDrawer().setRoot(root);
			Scene scene = new Scene(root);
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
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
				homeController.getWarningAlert("Nem megfelelő projektnév", "A projekt neve legalább 3 karakter hosszúságú.");
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
				homeController.createSetCoordSystemWindow();
				clearRoot();
				setPillarData.setDisable(true);
				setWireData.setDisable(true);
				homeController.getDrawer().drawPage();
			}
		});
		setPillarData = new MenuItem("Távvezeték oszlop adatok megadása");
		setPillarData.setDisable(true);
		setPillarData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
		
			}
		});
		setWireData = new MenuItem("Távvezeték adatok megadása");
		setWireData.setDisable(true);
		setWireData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			
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
		MenuItem saveAsProject = new MenuItem("Projekt mentése másként");
		MenuItem exitProject = new MenuItem("Kilépés");
		exitProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.exit();
			}
		});
		createNewProject.getItems().addAll(setProjectName, new SeparatorMenuItem(), setProjectFolder, 
				new SeparatorMenuItem(), setCoordSystem, new SeparatorMenuItem(), setPillarData, new SeparatorMenuItem(), setWireData);
		projectProcess.getItems().addAll(createNewProject, new SeparatorMenuItem(), 
				openProject, new SeparatorMenuItem(), saveProject, saveAsProject, new SeparatorMenuItem(), exitProject);
		Menu modifyDraw = new Menu("Rajz módosítása");
		MenuItem modifyCoordSystem = new MenuItem("Rajzi rendszer beállítások módosítása");
		modifyCoordSystem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
			}
		});
		MenuItem setText = new MenuItem("Felirat hozzáadása");
		setText.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			
			}
		});
		modifyDraw.getItems().addAll(modifyCoordSystem, new SeparatorMenuItem(), setText);
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