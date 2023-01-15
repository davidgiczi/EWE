package hu.mvmxpert.david.giczi.electricwireeditor.view;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.service.Drawer;
import hu.mvmxpert.david.giczi.electricwireeditor.service.FileProcess;
import hu.mvmxpert.david.giczi.electricwireeditor.service.Validate;
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
import javafx.stage.WindowEvent;

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
	public MenuItem toBeLastPillarTheBeginner;
	public MenuItem backwardsOrder;
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
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					if(homeController.setCoordSystemWindow != null )
						homeController.setCoordSystemWindow.getStage().hide();
					if(homeController.setPillarDataWindow != null ) 
						homeController.setPillarDataWindow.getStage().hide();
					if(homeController.setWireDataWindow != null ) 
						homeController.setWireDataWindow.getStage().hide();
					if(homeController.setTextWindow != null ) 
						homeController.setTextWindow.getStage().hide();
					if(homeController.getDrawer().modifyTextWindow != null )
						homeController.getDrawer().modifyTextWindow.getStage().hide();
					if(homeController.setLineWindow != null )
						homeController.setLineWindow.getStage().hide();
					if(homeController.saveWireCoordsWindow != null )
						homeController.saveWireCoordsWindow.getStage().hide();
					if(homeController.setCalculatedWireDataWindow != null )
						homeController.setCalculatedWireDataWindow.getStage().hide();
					if(homeController.showCalculatedWireDataWindow != null )
						homeController.showCalculatedWireDataWindow.getStage().hide();
				}
			});
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
			homeController.setProjectName();
			}
		});
		MenuItem setProjectFolder = new MenuItem("Projekt mappa megadása");
		setProjectFolder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				fileProcess.setFolder();
				homeController.setTitle(root);
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
				homeController.showSetPillarDataWindow();
			}
		});
		setWireData = new MenuItem("Távvezeték adatok megadása");
		setWireData.setDisable(true);
		setWireData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.showSetWireDataWindow();
			}
		});
		
		MenuItem openProject = new MenuItem("Projekt megnyitása");
		openProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			if( homeController.openProject() )
			homeController.showInputDrawingSystemDataOnCoordSystemDataWindow();
			}
		});
		saveProject = new MenuItem("Projekt mentése");
		saveProject.setDisable(true);
		saveProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.saveProject();
				homeController.setTitle(root);
			}
		});
		MenuItem printScreen = new MenuItem("Képernyőkép mentése");
		printScreen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.printScreen();
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
				openProject, new SeparatorMenuItem()/*, printScreen*/, saveProject, new SeparatorMenuItem(), exitProject);
		Menu modifyDraw = new Menu("Rajz módosítása");
		addText = new MenuItem("Felirat hozzáadása");
		addText.setDisable(true);
		addText.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.showSetTextWindow();
			}
		});
		addLine = new MenuItem("Vonal hozzáadása");
		addLine.setDisable(true);
		addLine.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			homeController.showSetLineDataWindow();
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
		modifyHorizontalScale.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.modifyScaleOfBaseLine();
			}
		});
		modifyBaseLine.getItems().addAll(modifyLengthOfBaseLine, modifyHorizontalScale);
		modifyVerticalScale = new Menu("Magassági lépték módosítása");
		MenuItem modifyElevationStartValue = new MenuItem("Magassági lépték kezdő magasságának módosítása");
		modifyElevationStartValue.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.modifyElevationStartValue();
			}
		});
		MenuItem modifyElevationMeasurment = new MenuItem("Magassági lépték beosztás értékének módosítása");
		modifyElevationMeasurment.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.modifyVerticalScale();
			}
		});
		modifyVerticalScale.getItems().addAll(modifyElevationStartValue, modifyElevationMeasurment);
		modifyVerticalScale.setDisable(true);
		toBeLastPillarTheBeginner = new MenuItem("Az utolsó oszlop legyen a kezdő oszlop");
		toBeLastPillarTheBeginner.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.toBeTheLastPillarTheBeginner();
			}
		});
		toBeLastPillarTheBeginner.setDisable(true);
		backwardsOrder = new MenuItem("Fordított sorrend");
		backwardsOrder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.backwardsOrder();
			}
		});
		backwardsOrder.setDisable(true);
		modifyDraw.getItems().addAll(addText,  new SeparatorMenuItem(), 
				addLine, new SeparatorMenuItem(), 
				modifyBaseLine, new SeparatorMenuItem(),
				modifyVerticalScale,  new SeparatorMenuItem(), 
				backwardsOrder, new SeparatorMenuItem(),
				toBeLastPillarTheBeginner);
		Menu drawWire = new Menu("Sodrony műveletek");
		MenuItem setWireData = new MenuItem("Sodrony adatok megadása");
		setWireData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
			homeController.showShowCalculatedWireDataWindow();
			
			}
	
		});
		Menu leftWire = new Menu("Bal sodrony");
		MenuItem visibleLeftWire = new MenuItem("Bal sodrony látható");
		visibleLeftWire.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.showLeftWire();
			}
		});
		MenuItem invisibleLeftWire = new MenuItem("Sodrony és különbségek törlése");
		invisibleLeftWire.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.deleteLeftWire();
			}
		});
		MenuItem showDeltaDifferenceOfLeftWire = new MenuItem("Különbségek kiírása");
		showDeltaDifferenceOfLeftWire.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.showDifferenceOfCurveOfLeftWire();
				
			}
		});
		leftWire.getItems().addAll(visibleLeftWire, showDeltaDifferenceOfLeftWire, new SeparatorMenuItem(), invisibleLeftWire);
		Menu rightWire = new Menu("Jobb sodrony");
		MenuItem visibleRightWire = new MenuItem("Jobb sodrony látható");
		visibleRightWire.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.showRightWire();
			}
		});
		MenuItem invisibleRightWire = new MenuItem("Sodrony és különbségek törlése");
		invisibleRightWire.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				homeController.deleteRightWire();
			}
		});
		MenuItem showDeltaDifferenceOfRightWire = new MenuItem("Különbségek kiírása");
		showDeltaDifferenceOfRightWire.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.showDifferenceOfCurveOfRightWire();
			}
		});
		Menu saveWireCoords = new Menu("Mért sodrony pontok mentése");
		MenuItem localSystem = new MenuItem("Helyi rendszerben -> 2D");
		localSystem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.save2DWireCoords();
			
			}
		});
		MenuItem countrySystem = new MenuItem("Országos rendszerben -> 3D");
		countrySystem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				homeController.save3DWireCoords();
			}
		});
		rightWire.getItems().addAll(visibleRightWire, showDeltaDifferenceOfRightWire, new SeparatorMenuItem(), invisibleRightWire);
		saveWireCoords.getItems().addAll(localSystem, countrySystem);
		drawWire.getItems().addAll(setWireData, new SeparatorMenuItem(), saveWireCoords);
		menuBar.getMenus().addAll(projectProcess, modifyDraw, drawWire);
		root.setTop(menuBar);
	}
	
}
