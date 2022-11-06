package hu.mvmxpert.david.giczi.electricwireeditor.service;


import java.text.DecimalFormat;
import java.util.List;
import javax.naming.directory.InvalidAttributesException;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.model.LineData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.TextData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WirePoint;
import hu.mvmxpert.david.giczi.electricwireeditor.view.ModifyTextWindow;
import javafx.scene.Cursor;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class Drawer {
	
	private BorderPane root;
	public static final double MILLIMETER = 1000 / 224.0;
	public static final double A4_WIDTH =  211 * MILLIMETER;
	public static final double START_X = 45 * MILLIMETER;
	public static final double HOR_SHIFT = 12;
	public static final double START_Y = 550.0;
	public static final double PAGE_Y = 25;
	private final double MARGIN = 156 * MILLIMETER;
	public static double X_DISTANCE;
	private final double VER_SHIFT = 5;
	private double lengthOfHorizontalAxis;
	private int horizontalScale;
	private int verticalScale;
	private int elevationStartValue;
	public ModifyTextWindow modifyTextWindow;
	private HomeController homeController;
	private DecimalFormat df = new DecimalFormat("0.00");
	private ArchivFileBuilder archivFileBuilder;
	
	
	public void setArchivFileBuilder(ArchivFileBuilder archivFileBuilder) {
		this.archivFileBuilder = archivFileBuilder;
	}

	public void setLengthOfHorizontalAxis(double lengthOfHorizontalAxis) {
		this.lengthOfHorizontalAxis = lengthOfHorizontalAxis;
	}
	
	public int getHorizontalScale() {
		return horizontalScale;
	}

	public void setHorizontalScale(int horizontalScale) {
		this.horizontalScale = horizontalScale;
	}

	public void setVerticalScale(int verticalScale) {
		this.verticalScale = verticalScale;
	}
	
	public int getVerticalScale() {
		return verticalScale;
	}

	public void setElevationStartValue(int elevationStartValue) {
		this.elevationStartValue = elevationStartValue;
	}
	
	public int getElevationStartValue() {
		return elevationStartValue;
	}

	public double getLengthOfHorizontalAxis() {
		return lengthOfHorizontalAxis;
	}
	

	public BorderPane getRoot() {
		return root;
	}

	public void setRoot(BorderPane root) {
		this.root = root;
	}
	
	
	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	public void clearRoot(){
		
		for(int i = root.getChildren().size() - 1; i >= 0; i--) {
			if( root.getChildren().get(i) instanceof MenuBar ) {
				continue;
			}
			root.getChildren().remove(i);
		}
	}
	
	public void drawPage() {
		Rectangle page = new Rectangle();
		page.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2));
		page.setY(PAGE_Y);
		page.setWidth(A4_WIDTH);
		page.heightProperty().bind(root.widthProperty().subtract(50));
		Line leftMargin = new Line();
		leftMargin.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add((A4_WIDTH - MARGIN) / 2));
		leftMargin.setStartY(PAGE_Y);
		leftMargin.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add((A4_WIDTH - MARGIN) / 2));
		leftMargin.endYProperty().bind(root.heightProperty().add(20));
		Line rightMargin = new Line();
		rightMargin.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add((A4_WIDTH - MARGIN) / 2).add(MARGIN));
		rightMargin.setStartY(PAGE_Y);
		rightMargin.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add((A4_WIDTH - MARGIN) / 2).add(MARGIN));
		rightMargin.endYProperty().bind(root.heightProperty().add(20));
		leftMargin.setStroke(Color.LIGHTGRAY);
		leftMargin.getStrokeDashArray().addAll(4d);
		rightMargin.setStroke(Color.LIGHTGRAY);
		rightMargin.getStrokeDashArray().addAll(4d);
		page.setFill(Color.WHITE);
		root.getChildren().addAll(page, leftMargin, rightMargin);
	}
	
	public void drawVerticalAxis() {
		double startY = START_Y - 10 * MILLIMETER;
		Line leftBorder = new Line();
		leftBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X));
		leftBorder.setStartY(PAGE_Y + START_Y);
		leftBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X));
		leftBorder.setEndY(PAGE_Y + START_Y - 100  * MILLIMETER);
		Line rightBorder = new Line();
		rightBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(2 * MILLIMETER));
		rightBorder.setStartY(PAGE_Y + START_Y);
		rightBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(2 * MILLIMETER));
		rightBorder.setEndY(PAGE_Y + START_Y - 100  * MILLIMETER);
		Line topBorder = new Line();
		topBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X));
		topBorder.setStartY(PAGE_Y + START_Y - 100 * MILLIMETER);
		topBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(2 * MILLIMETER));
		topBorder.setEndY(PAGE_Y + START_Y - 100 * MILLIMETER);
		root.getChildren().addAll(leftBorder, rightBorder, topBorder);
		for(int i = 1; i < 11; i++) {
		Rectangle axisComponent = new Rectangle();
		axisComponent.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X));
		axisComponent.setY(PAGE_Y + startY);
		axisComponent.setWidth(2 * MILLIMETER);
		axisComponent.setHeight(10 * MILLIMETER);
		if( i % 2 == 0) {
			axisComponent.setFill(Color.WHITE);
		}
		startY -= 10 * MILLIMETER;
		root.getChildren().add(axisComponent);
		}
	}
	
	public void drawHorizontalAxis() {
		Line topBorder = new Line();
		topBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER));
		topBorder.setStartY(PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		topBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		topBorder.setEndY(PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		Line rightBorder = new Line();
		rightBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		rightBorder.setStartY(PAGE_Y + START_Y + (VER_SHIFT  + 1 ) * MILLIMETER);
		rightBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		rightBorder.setEndY(PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		Line downBorder = new Line();
		downBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER));
		downBorder.setStartY(PAGE_Y + START_Y + (VER_SHIFT + 1) * MILLIMETER);
		downBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		downBorder.setEndY(PAGE_Y + START_Y + (VER_SHIFT + 1) * MILLIMETER);
		Line leftBorder = new Line();
		leftBorder.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER));
		leftBorder.setStartY(PAGE_Y + START_Y + VER_SHIFT * MILLIMETER);
		leftBorder.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER));
		leftBorder.setEndY(PAGE_Y + START_Y + (VER_SHIFT + 1) * MILLIMETER);
		root.getChildren().addAll(topBorder, rightBorder, downBorder, leftBorder);
	}
	
	public void writeElevationValueForVerticalAxis() {
		double startY = START_Y;
		int startValue = elevationStartValue;
		for(int i = 0; i <= 10; i++) {
		setText(-1, String.valueOf(startValue) + "m", - 70, PAGE_Y + startY, 18, 0, false, false);
		startY -= 10 * MILLIMETER;
		startValue += verticalScale;
		}
		setText(-1, "Oszlopszám:", - 17 * MILLIMETER, PAGE_Y + START_Y + 30 * MILLIMETER, 18, 0, false, false);
	}
	
	public void writeDistanceValueForHorizontalAxis() {
		setText(-1, "0", (HOR_SHIFT - 1) * MILLIMETER, PAGE_Y + START_Y + 50, 18, 0, false, false);
		setText(-1, String.valueOf(lengthOfHorizontalAxis) + "m", 
				getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER, 
				PAGE_Y + START_Y + 50, 18, 0, false, false);
	}
	
	public void drawPillar(String id, double groundElevation, double topElevation, double distance, boolean isHooded) {
		Line pillar = new Line();
		pillar.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(groundElevation - elevationStartValue) * MILLIMETER);
		pillar.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
		pillar.setStroke(Color.BLUE);
		pillar.setStrokeWidth(3);
		pillar.setCursor(Cursor.HAND);
		pillar.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		root.getChildren().add(pillar);
		
		PillarData pillarData = new PillarData(groundElevation, topElevation, distance, isHooded);
		pillarData.setId(ArchivFileBuilder.addID());
		pillar.setId(String.valueOf(pillarData.getId()));
		archivFileBuilder.addPillar(pillarData);
		
		if( isHooded ) {
			Line hood = new Line();
			hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
					.add((HOR_SHIFT - 1) * MILLIMETER));
			hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
					.add((HOR_SHIFT + 1) * MILLIMETER));
			hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> {
				Line line = (Line) h.getSource();
				setDrawLineWindowData(line);
				deleteLine(line);
				});
			hood.setId(String.valueOf(pillarData.getId()));
			hood.setStroke(Color.BLUE);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
		
		setText(pillarData.getId(), id + ".", 
				(getHorizontalScaledDownLengthValue(distance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 30 * MILLIMETER, 18, 0, false, false);	
		setText(pillarData.getId(), "bal ak.: Bf. " + df.format(groundElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, pillar.getStartY(), 18, -90, true, false);
		setText(pillarData.getId(), "bal ak.: Bf. " + df.format(topElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, pillar.getEndY(), 18, -90, true, true);
		if( distance != 0 && distance != lengthOfHorizontalAxis )
		setText(pillarData.getId(), df.format(distance).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER, PAGE_Y + START_Y + 50, 18, 0, false, false);
	}
	
	public void drawElectricWire(String text, double groundElevation, double topElevation, double distance, boolean isHooded) {
		Line wire = new Line();
		wire.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		wire.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(groundElevation - elevationStartValue) * MILLIMETER);
		wire.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		wire.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
		wire.setStroke(Color.RED);
		wire.setStrokeWidth(3);
		wire.setCursor(Cursor.HAND);
		wire.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		root.getChildren().add(wire);
		
		WireData wireData = new WireData(groundElevation, topElevation, distance, isHooded);
		wireData.setId(ArchivFileBuilder.addID());
		wire.setId(String.valueOf(wireData.getId()));
		archivFileBuilder.addWire(wireData);
		
		if( isHooded ) {
			Line hood = new Line();
			hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
					.add((HOR_SHIFT - 1) * MILLIMETER));
			hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(distance) * MILLIMETER)
					.add((HOR_SHIFT + 1) * MILLIMETER));
			hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(topElevation - elevationStartValue) * MILLIMETER);
			hood.setCursor(Cursor.HAND);
			hood.setStroke(Color.RED);
			hood.setId(String.valueOf(wireData.getId()));
			hood.setStrokeWidth(3);
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> {
				Line line = (Line) h.getSource();
				setDrawLineWindowData(line);
				deleteLine(line);
				});
			root.getChildren().add(hood);
		}
		
		setText(wireData.getId(), "bal af.: Bf. " + df.format(groundElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, wire.getStartY(), 18, -90, true, false);
		setText(wireData.getId(), "bal af.: Bf. " + df.format(topElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, wire.getEndY(), 18, -90, true, true);
		if( distance != 0 && distance != lengthOfHorizontalAxis )
		setText(wireData.getId(), df.format(distance).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance) + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 50, 18, 0, false, false);
		setText(wireData.getId(), text, (getHorizontalScaledDownLengthValue(distance) + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 65, 18, 0, false, false);
	}
	
	public void writeText(String text, double startX, double startY, double rotate, int ownerId, boolean isAtTop) {
		Text txt = new Text(text);
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 18));
		TextData textData = new TextData();
		textData.setTextValue(text);
		textData.setAtTop(isAtTop);
		textData.setSize(18);
		double xDistance;
		if( rotate == -90 ) {
			txt.setRotationAxis(Rotate.Z_AXIS);
			txt.setRotate(-90);
			textData.setDirection(-90);
			xDistance = startX * MILLIMETER - (root.widthProperty().get() - A4_WIDTH) / 2 - START_X + (HOR_SHIFT - 4) * MILLIMETER;
		}
		else {
			textData.setDirection(0);
			xDistance = startX * MILLIMETER - (root.widthProperty().get() - A4_WIDTH) / 2 - START_X;
		}
		txt.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(xDistance));
		txt.setY(startY * MILLIMETER);
		textData.setX(txt.xProperty().get() - X_DISTANCE);
		textData.setY(txt.yProperty().get());
		textData.setId(ArchivFileBuilder.addID());
		archivFileBuilder.addChosenTextToOwnerTextList(textData, ownerId);
		txt.setCursor(Cursor.HAND);
		txt.setId(String.valueOf(textData.getId()));
		txt.setOnMouseClicked( t -> {
		Text inputText = (Text) t.getSource();
		showModifyTextWindow(inputText); });
		root.getChildren().add(txt);
	}
	
	public void setText(int id, String text, double startX, double startY, int size, int rotate, boolean isOnLeftSide, boolean isAtTop) {
		Text txt = new Text(text);
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, size));
		if( rotate == -90 ) {
		txt.setRotationAxis(Rotate.Z_AXIS);
		txt.setRotate(-90);
		}
		txt.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(startX));
		txt.setY(startY);
		
		int pillarTextId = getPillarTextId(id, text, startX, startY, size, rotate, isOnLeftSide, isAtTop);
		int wireTextId = getWireTextId(id, text, startX, startY, size, rotate, isOnLeftSide, isAtTop);
		
		if( pillarTextId != -1 ) {
			txt.setId(String.valueOf(pillarTextId));
		}
		else if ( wireTextId != -1 ) {
			txt.setId(String.valueOf(wireTextId));
		}
		else if ( id != - 1){
		TextData singleText = new TextData(text, startX, startY, size, rotate, "SingleText");
		singleText.setId(ArchivFileBuilder.addID());
		archivFileBuilder.addText(singleText);
		txt.setId(String.valueOf(singleText.getId()));
		}
		else if( id == -1 ) {
		TextData baseText = new TextData(text, startX, startY, size, rotate, "SingleText");
		baseText.setId(id);
		archivFileBuilder.addText(baseText);
		txt.setId("-1");
		}
		txt.setCursor(Cursor.HAND); 
		txt.setOnMouseClicked( t -> {
		Text inputText = (Text) t.getSource();
		showModifyTextWindow(inputText); });
		root.getChildren().add(txt);
	}
	
	private int getPillarTextId(int id, String text, double startX, double startY, int size, int rotate, boolean isOnLeftSide, boolean isAtTop) {
		PillarData pillar = archivFileBuilder.getPillarData(id);
		if( pillar == null )
			return -1;
		TextData pillarText = new TextData(text, startX, startY, size, rotate, "PillarText");
		pillarText.setId(ArchivFileBuilder.addID());
		pillarText.setOnLeftSide(isOnLeftSide);
		pillarText.setAtTop(isAtTop);
		pillar.getPillarTextList().add(pillarText);
		return pillarText.getId();
	}
	
	private int getWireTextId(int id, String text, double startX, double startY, int size, int rotate, boolean isOnLeftSide, boolean isAtTop) {
		WireData wire = archivFileBuilder.getWireData(id);
		if( wire == null )
			return -1;
		TextData wireText = new TextData(text, startX, startY, size, rotate, "WireText");
		wireText.setId(ArchivFileBuilder.addID());
		wireText.setOnLeftSide(isOnLeftSide);
		wireText.setAtTop(isAtTop);
		wire.getWireTextList().add(wireText);
		return wireText.getId();
	}
	
	public boolean deleteText(Text text) {
		if( HomeController.getConfirmationAlert("Felirat törlése", "Biztos, hogy törlöd a kiválaszott feliratot?") ) {
			int id = Integer.valueOf(text.getId());
			archivFileBuilder.removeText(id);
			archivFileBuilder.removeWireText(id);
			archivFileBuilder.removePillarText(id);
			root.getChildren().remove(text);
			return true;
		}
		return false;
		}
	
	public void rotateText(Text text) {
		double xDistance = text.getX() - root.widthProperty().divide(2).subtract(A4_WIDTH / 2).get();
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		text.setRotationAxis(Rotate.Z_AXIS);
		if( text.getRotate() == -90) {
			text.setRotate(0);
			if( textData != null )
			text.setRotate(0);
		}
		else {
			text.setRotate(-90);
			if( textData != null )
				textData.setDirection(-90);
		}
		text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(xDistance));	
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());	
	}
	
	public void modifyText(Text text, String txt) {
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		if( textData != null ) {
		textData.setTextValue(txt);
		}
		text.setText(txt);
	}
	
	public void moveTextLeft(Text text) {
		double actualXPosition = text.getX();
		double xDistance = actualXPosition - root.widthProperty().divide(2).subtract(A4_WIDTH / 2).get();
		text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(xDistance).subtract(MILLIMETER));
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
	}
	
	public void moveTextRight(Text text) {
		double actualXPosition = text.getX();
		double xDistance = actualXPosition - root.widthProperty().divide(2).subtract(A4_WIDTH / 2).get();
		text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(xDistance).add(MILLIMETER));
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
	}
	
	public void moveTextUp(Text text) {
		text.setY(text.getY() - MILLIMETER);
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
	}
	
	public void moveTextDown(Text text) {
		text.setY(text.getY() + MILLIMETER);
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		textData.setX(text.xProperty().get() - X_DISTANCE);
		textData.setY(text.yProperty().get());
	}
	
	public void setTextSize(Text text, int size) {
		Font font = Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, size);
		text.setFont(font);
		TextData textData = archivFileBuilder.getTextData(Integer.valueOf(text.getId()));
		if( textData != null ) {
			textData.setSize(size);
		}
	}
	
	public void drawInputPillar(int id) {
		
		PillarData pD = archivFileBuilder.getPillarData(id);
		Line pillar = new Line();
		pillar.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setStartY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(pD.getGroundElevation() - 
						archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		pillar.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		pillar.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pD.getTopElevetaion() - 
				archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		pillar.setStroke(Color.BLUE);
		pillar.setStrokeWidth(3);
		pillar.setCursor(Cursor.HAND);
		pillar.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		pillar.setId(String.valueOf(id));
		root.getChildren().add(pillar);
		
		if( pD.isHasCap() ) {
			Line hood = new Line();
			hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
					.add((HOR_SHIFT - 1) * MILLIMETER));
			hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pD.getTopElevetaion() - 
					archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
			hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(pD.getDistanceOfPillar()) * MILLIMETER)
					.add((HOR_SHIFT + 1) * MILLIMETER));
			hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pD.getTopElevetaion() - 
					archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> {
				Line line = (Line) h.getSource();
				setDrawLineWindowData(line);
				deleteLine(line);
				});
			hood.setId(String.valueOf(id));
			hood.setStroke(Color.BLUE);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
		
	}
	
	public void drawInputPillarText(PillarData pillarData, double shiftY1) {
		
		for (TextData textData : pillarData.getPillarTextList()) {
			if( textData.getDirection() == -90 && textData.isOnLeftSide())
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) - HOR_SHIFT) * MILLIMETER);
			else if( textData.getDirection() == -90 && !textData.isOnLeftSide())
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) - VER_SHIFT) * MILLIMETER);
			else
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) + HOR_SHIFT - VER_SHIFT) * MILLIMETER);
			Text text = new Text(textData.getTextValue());
			text.setId(String.valueOf(textData.getId()));
			text.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, textData.getSize()));
			if( textData.getDirection() == -90 ) {
				text.setRotationAxis(Rotate.Z_AXIS);
				text.setRotate(-90);
				}
			text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(textData.getX()));
				
			if( textData.getTextValue().startsWith("bal") && shiftY1 != 0 )
				textData.setY(textData.getY() + shiftY1);
			else if( textData.getTextValue().startsWith("jobb") && shiftY1 != 0)
				textData.setY(textData.getY() + shiftY1);
			else if( textData.getTextValue().startsWith("bal") && textData.isAtTop())
				textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
			else if( textData.getTextValue().startsWith("bal") && !textData.isAtTop())
				textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getGroundElevation() - elevationStartValue) * MILLIMETER);
			else if( textData.getTextValue().startsWith("jobb") && textData.isAtTop())
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
			else if( textData.getTextValue().startsWith("jobb") && !textData.isAtTop())
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(pillarData.getGroundElevation() - elevationStartValue) * MILLIMETER);	
				text.setY(textData.getY());
				text.setOnMouseClicked( t -> {
					Text inputText = (Text) t.getSource();
					showModifyTextWindow(inputText); });
				text.setCursor(Cursor.HAND);
				root.getChildren().add(text);
		}
	}
	
	
	public void drawInputWire(int id) {
		
		WireData wD = archivFileBuilder.getWireData(id);
		Line wire = new Line();
		wire.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		wire.setStartY(PAGE_Y + START_Y - 
				getVerticalScaledDownHeightValue(wD.getGroundElevation() - 
						archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		wire.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X)
				.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
				.add(HOR_SHIFT * MILLIMETER));
		wire.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(wD.getTopElevetaion() - 
				archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
		wire.setStroke(Color.RED);
		wire.setStrokeWidth(3);
		wire.setCursor(Cursor.HAND);
		wire.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		wire.setId(String.valueOf(id));
		root.getChildren().add(wire);
		
		if( wD.isHasCap() ) {
			Line hood = new Line();
			hood.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
					.add((HOR_SHIFT - 1) * MILLIMETER));
			hood.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(wD.getTopElevetaion() - 
					archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
			hood.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X)
					.add(getHorizontalScaledDownLengthValue(wD.getDistanceOfWire()) * MILLIMETER)
					.add((HOR_SHIFT + 1) * MILLIMETER));
			hood.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(wD.getTopElevetaion() - 
					archivFileBuilder.getSystemData().getElevationStartValue()) * MILLIMETER);
			hood.setCursor(Cursor.HAND);
			hood.setOnMouseClicked( h -> {
				Line line = (Line) h.getSource();
				setDrawLineWindowData(line);
				deleteLine(line);
				});
			hood.setId(String.valueOf(id));
			hood.setStroke(Color.RED);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
	}
	
	public void drawInputWireText(WireData wireData, double shiftY) {
		
		for (TextData textData : wireData.getWireTextList()) {
			if( textData.getDirection() == -90 && textData.isOnLeftSide())
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) - HOR_SHIFT) * MILLIMETER);
			else if( textData.getDirection() == -90 && !textData.isOnLeftSide())
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) - VER_SHIFT) * MILLIMETER);
			else
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) + HOR_SHIFT - VER_SHIFT) * MILLIMETER);
			Text text = new Text(textData.getTextValue());
			text.setId(String.valueOf(textData.getId()));
			text.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, textData.getSize()));
			if( textData.getDirection() == -90 ) {
				text.setRotationAxis(Rotate.Z_AXIS);
				text.setRotate(-90);
				}
				text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(textData.getX()));
				if( textData.getTextValue().startsWith("bal") && shiftY != 0 )
				textData.setY(textData.getY() + shiftY);
				else if( textData.getTextValue().startsWith("jobb") && shiftY != 0 )
				textData.setY(textData.getY() + shiftY);
				else if( textData.getTextValue().startsWith("bal") && textData.isAtTop())
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
				else if( textData.getTextValue().startsWith("bal") && !textData.isAtTop())
					textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getGroundElevation() - elevationStartValue) * MILLIMETER);
				else if( textData.getTextValue().startsWith("jobb") && textData.isAtTop())
						textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getTopElevetaion() - elevationStartValue) * MILLIMETER);
				else if( textData.getTextValue().startsWith("jobb") && !textData.isAtTop())
						textData.setY(START_Y + PAGE_Y - getVerticalScaledDownHeightValue(wireData.getGroundElevation() - elevationStartValue) * MILLIMETER);
				
				text.setY(textData.getY());
				text.setOnMouseClicked( t -> {
					Text inputText = (Text) t.getSource();
					showModifyTextWindow(inputText); });
				text.setCursor(Cursor.HAND);
				root.getChildren().add(text);
		}
	}
	
	
	public void drawInputText(TextData textData) {
		Text text = new Text(textData.getTextValue());
		text.setId(String.valueOf(textData.getId()));
		text.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, textData.getSize()));
		if( textData.getDirection() == -90 ) {
			text.setRotationAxis(Rotate.Z_AXIS);
			text.setRotate(-90);
			}
			text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(textData.getX()));
			text.setY(textData.getY());
			text.setCursor(Cursor.HAND);
			text.setOnMouseClicked( t -> {
				Text inputText = (Text) t.getSource();
				showModifyTextWindow(inputText); });
			root.getChildren().add(text);
	}
	
	public void drawLeftWireCurve(List<WirePoint> pointsOfWire) {
				
		if( pointsOfWire.size() == 2) {
			drawWireByTwoPoints(pointsOfWire, "-2");
		}
		else {
			drawWire(pointsOfWire, "-2");
		}
	}
	
	public void deleteLeftWire() {
		
		for(int i = root.getChildren().size() - 1; i >= 0; i--) {
			if( "-2".equals(root.getChildren().get(i).getId()) ) {
				root.getChildren().remove(i);
			}
		}
	}
	
	private void drawWireByTwoPoints(List<WirePoint> pointsOfWire, String id) {
		Line wire = new Line();
		if( "-2".equals(id) )
		wire.setStroke(Color.MAGENTA);
		else
		wire.setStroke(Color.GREEN);	
		wire.setStrokeWidth(1.5);
		wire.getStrokeDashArray().addAll(1d, 4d);
		wire.setId(id);
		wire.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X).add(HOR_SHIFT * MILLIMETER)
				.add(getHorizontalScaledDownLengthValue(pointsOfWire.get(0).getDistanceOfWirePoint()) * MILLIMETER));
		wire.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pointsOfWire.get(0).getElevationOfWirePoint()) * MILLIMETER);
		wire.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X).add(HOR_SHIFT * MILLIMETER)
				.add(getHorizontalScaledDownLengthValue(pointsOfWire.get(1).getDistanceOfWirePoint())* MILLIMETER));
		wire.setEndY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(pointsOfWire.get(1).getElevationOfWirePoint()) * MILLIMETER);
		root.getChildren().add(wire);
	}
	
	private void drawWire(List<WirePoint> pointsOfWire, String id) { 
			try {
				double verticalShift =  PAGE_Y + START_Y;
				double scale = horizontalScale / 1000d;
				for(int i = 0; i < pointsOfWire.size() - 1; i++) {
					
				HalfParabola parabola = new HalfParabola(pointsOfWire.get(i), pointsOfWire.get(i + 1));
				
				if( pointsOfWire.get(i).getElevationOfWirePoint() - pointsOfWire.get(i + 1).getElevationOfWirePoint() > 0) {
					for(double y = 0; y <= Math.abs(parabola.getOrigo().getDistanceOfWirePoint()); y += scale) {
					Circle dot = new Circle();
					dot.centerXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER)
					.add(getHorizontalScaledDownLengthValue(pointsOfWire.get(i + 1).getDistanceOfWirePoint()) * MILLIMETER)
					.subtract(getHorizontalScaledDownLengthValue(y) * MILLIMETER));
					dot.setCenterY(verticalShift - 
					getVerticalScaledDownHeightValue(pointsOfWire.get(i + 1).getElevationOfWirePoint()) * MILLIMETER -
					getVerticalScaledDownHeightValue(parabola.getElevationOfHalfParabolaPoint(y)) * MILLIMETER);
					dot.setRadius(1);
					dot.setId(id);
					if( "-2".equals(id) )
					dot.setStroke(Color.MAGENTA);
					else
					dot.setStroke(Color.GREEN);
					root.getChildren().add(dot);
					
					}
				}
				else {	
					parabola = new HalfParabola(pointsOfWire.get(i + 1), pointsOfWire.get(i));
					for(double y = 0; y <= Math.abs(parabola.getOrigo().getDistanceOfWirePoint()); y += scale) {
						Circle dot = new Circle();
						dot.centerXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(HOR_SHIFT * MILLIMETER)
					.add(getHorizontalScaledDownLengthValue(pointsOfWire.get(i).getDistanceOfWirePoint()) * MILLIMETER)
					.add(getHorizontalScaledDownLengthValue(y) * MILLIMETER));
					dot.setCenterY(verticalShift - 
					getVerticalScaledDownHeightValue(pointsOfWire.get(i).getElevationOfWirePoint()) * MILLIMETER -
					getVerticalScaledDownHeightValue(parabola.getElevationOfHalfParabolaPoint(y)) * MILLIMETER);
					dot.setRadius(1);
					dot.setId(id);
					if( "-2".equals(id) )
					dot.setStroke(Color.MAGENTA);
					else
					dot.setStroke(Color.GREEN);	
					root.getChildren().add(dot);
					}
				}
			}
		} 
			catch (InvalidAttributesException e) {
				HomeController.getWarningAlert("Hibás sodrony adatok", "A megadott bemeneti adatokból sodrony nem rajzolható.");
			}
	}
	
	public void drawRightWireCurve(List<WirePoint> pointsOfWire) {
		
		if( pointsOfWire.size() == 2) {
			drawWireByTwoPoints(pointsOfWire, "-3");
		}
		else {
			drawWire(pointsOfWire, "-3");
		}
	}
	
	 public void deleteRightWire() {
			
			for(int i = root.getChildren().size() - 1; i >= 0; i--) {
				if( "-3".equals(root.getChildren().get(i).getId()) ) {
					root.getChildren().remove(i);
				}
			}
		} 
	 
	 
	 public void writeDifferenceOfWireCurve(List<WirePoint> pointsOfWire, int minimumPlace, String id)  {
		 try {
			HalfParabola leftCurve = new HalfParabola(pointsOfWire.get(0), pointsOfWire.get(minimumPlace));
			HalfParabola rightCurve = new HalfParabola(pointsOfWire.get(pointsOfWire.size() - 1), pointsOfWire.get(minimumPlace));
			DecimalFormat df = new DecimalFormat("+0.00;-0.00");
			int vShift = "-2".equals(id) ? 12 : 5;
			for(int i = 1; i < pointsOfWire.size() - 1; i++) {
				if( i != minimumPlace && pointsOfWire.get(i).getDistanceOfWirePoint() < 
						(pointsOfWire.get(0).getDistanceOfWirePoint() - pointsOfWire.get(pointsOfWire.size() - 1).getDistanceOfWirePoint()) / 2) {
			Text diffText =  new Text(df.format(leftCurve
				.getElevationOfHalfParabolaPoint(
				pointsOfWire.get(i).getDistanceOfWirePoint() 
				- pointsOfWire.get(minimumPlace).getDistanceOfWirePoint())
				- pointsOfWire.get(i).getElevationOfWirePoint()
				+ pointsOfWire.get(minimumPlace).getElevationOfWirePoint()).replace(',', '.'));
				diffText.setId(id);
				if( "-2".equals(id))
					diffText.setStroke(Color.MAGENTA);
				else
				diffText.setStroke(Color.GREEN);
				diffText.setRotationAxis(Rotate.Z_AXIS);
				diffText.setRotate(-90);
				diffText.xProperty()
				.bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
					.add(START_X).add((getHorizontalScaledDownLengthValue(pointsOfWire.get(i).getDistanceOfWirePoint()) + 9) * MILLIMETER));
				diffText.setY(PAGE_Y + START_Y 
						- (getVerticalScaledDownHeightValue(pointsOfWire.get(i).getElevationOfWirePoint()) + vShift) * MILLIMETER);
				root.getChildren().add(diffText);
				}
				else if( i != minimumPlace && pointsOfWire.get(i).getDistanceOfWirePoint() >= 
						(pointsOfWire.get(0).getDistanceOfWirePoint() - pointsOfWire.get(pointsOfWire.size() - 1).getDistanceOfWirePoint()) / 2) {
				Text diffText = new Text(df.format(rightCurve
					.getElevationOfHalfParabolaPoint(
					pointsOfWire.get(i).getDistanceOfWirePoint() 
					- pointsOfWire.get(minimumPlace).getDistanceOfWirePoint()) 
					- pointsOfWire.get(i).getElevationOfWirePoint()
					+ pointsOfWire.get(minimumPlace).getElevationOfWirePoint()).replace(',', '.'));
				diffText.setId(id);
				if( "-2".equals(id))
				diffText.setStroke(Color.MAGENTA);
				else
				diffText.setStroke(Color.GREEN);
				diffText.setRotationAxis(Rotate.Z_AXIS);
				diffText.setRotate(-90);
				diffText.xProperty()
				.bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
						.add(START_X).add((getHorizontalScaledDownLengthValue(pointsOfWire.get(i).getDistanceOfWirePoint()) + 9) * MILLIMETER));
				diffText.setY(PAGE_Y + START_Y 
						- (getVerticalScaledDownHeightValue(pointsOfWire.get(i).getElevationOfWirePoint()) + vShift) * MILLIMETER);
				root.getChildren().add(diffText);
				}
				
			}
			
		} catch (InvalidAttributesException e) {
			HomeController.getWarningAlert("Hibás sodrony adatok", "A megadott bemeneti adatokból sodrony nem rajzolható.");
		}
	 }
	
	private void deleteLine(Line line) {
	if( HomeController.getConfirmationAlert("Vonal törlése", "Biztos, hogy törlöd a kiválasztott vonalat?") ) {
		int id = Integer.valueOf(line.getId());
		archivFileBuilder.removePillar(id, root);
		archivFileBuilder.removeWire(id, root);
		archivFileBuilder.removeLine(id, root);
	}
}
	private void setDrawLineWindowData(Line line) {
		homeController.showSetLineDataWindow();
		LineData lineData = archivFileBuilder.getLineData(Integer.valueOf(line.getId()));
		homeController.setLineWindow.getController().getStartXTextField().setText(String.valueOf(lineData.getStartX()));
		homeController.setLineWindow.getController().getStartYTextField().setText(String.valueOf(lineData.getStartY()));
		homeController.setLineWindow.getController().getEndXTextField().setText(String.valueOf(lineData.getEndX()));
		homeController.setLineWindow.getController().getEndYTextField().setText(String.valueOf(lineData.getEndY()));
		homeController.setLineWindow.getController().getLineTypeComboBox().setValue(lineData.getType());
		homeController.setLineWindow.getController().getLineColorPicker()
		.setValue(new Color(lineData.getRed(), lineData.getGreen(), lineData.getBlue(), lineData.getOpacity()));
		homeController.setLineWindow.getController().getLineWidthComboBox().setValue(lineData.getWidth());
	}
	
	public void drawLine(double startX, double startY, double endX, double endY, String type, Color color, String width) {
		
		if( 0 > startX || startX > lengthOfHorizontalAxis ) {
			HomeController.getWarningAlert("Nem megfelelő megfelelő StartX koordináta érték",
					"Az X koordináta értéke: X >= 0 és " + lengthOfHorizontalAxis + " >= X");
			return;
		}
		if( 0 > endX || endX > lengthOfHorizontalAxis ) {
			HomeController.getWarningAlert("Nem megfelelő VégeX koordináta érték",
					"Az X koordináta értéke: X >= 0 és " + lengthOfHorizontalAxis + " >= X");
			return;
		}
		if( elevationStartValue > startY  ||  startY > elevationStartValue + 10 * verticalScale) {
			HomeController.getWarningAlert("Nem megfelelő StartY koordináta érték", 
					"Az Y koordináta értéke: Y >= " + elevationStartValue + " és " +
				(elevationStartValue + 10 * verticalScale) + " >= Y");
			return;
		}
		if( elevationStartValue > endY  ||  endY > elevationStartValue + 10 * verticalScale) {
			HomeController.getWarningAlert("Nem megfelelő VégeY koordináta érték", 
					"Az Y koordináta értéke: Y >= " + elevationStartValue + " és " +
				(elevationStartValue + 10 * verticalScale) + " >= Y");
			return;
		}
		LineData lineData = new LineData(startX, startY, endX, endY, type, 
				color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity(), width);
		archivFileBuilder.addLine(lineData);
		Line newLine = new Line();
		newLine.setStrokeWidth(1);
		switch (width) {
		case "0.5":
			newLine.setStrokeWidth(0.5);
			break;
		case "3":
			newLine.setStrokeWidth(3);
			break;
		
		}
		switch (type) {
		case "szaggatott":
			newLine.getStrokeDashArray().addAll(4d, 5d);
			break;
		case "pontozott":
			newLine.getStrokeDashArray().addAll(1d, 4d);
			break;
		
		}
		newLine.setStyle("-fx-stroke:" + toHexString(color) + ";");
		newLine.setCursor(Cursor.HAND);
		newLine.setId(String.valueOf(lineData.getId()));
		newLine.setOnMouseClicked( h -> {
			Line line = (Line) h.getSource();
			setDrawLineWindowData(line);
			deleteLine(line);
			});
		newLine.startXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X).add((getHorizontalScaledDownLengthValue(startX) + HOR_SHIFT) * MILLIMETER));
		newLine.setStartY(PAGE_Y + START_Y - getVerticalScaledDownHeightValue(startY - elevationStartValue) * MILLIMETER);
		newLine.endXProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2)
				.add(START_X).add((getHorizontalScaledDownLengthValue(endX) + HOR_SHIFT) * MILLIMETER));
		newLine.setEndY(PAGE_Y + START_Y  - getVerticalScaledDownHeightValue(endY - elevationStartValue) * MILLIMETER);
		root.getChildren().add(newLine);
	}
	
	private String toHexString(Color color) {
		  int r = ((int) Math.round(color.getRed()     * 255)) << 24;
		  int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
		  int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
		  int a = ((int) Math.round(color.getOpacity() * 255));
		  return String.format("#%08X", (r + g + b + a));
		}
	
	private void showModifyTextWindow(Text text) {
		
		addChosenTextToSetTextWindow(text);
		
		if( modifyTextWindow == null ) {
			modifyTextWindow = new ModifyTextWindow(this);
		}
		else {
			modifyTextWindow.getStage().show();
		}
		modifyTextWindow.getStage().setAlwaysOnTop(true);
		modifyTextWindow.setInputText(text);
	}

	public double getHorizontalScaledDownLengthValue(double length) {
		return horizontalScale == 1000 ? length : 1000.0  * length / horizontalScale;
	}
	
	public double getVerticalScaledDownHeightValue(double height) {
		return verticalScale == 10 ? height : 10.0 * height / verticalScale;
	}
	
	private void addChosenTextToSetTextWindow(Text text) {
		homeController.showSetTextWindow();
		homeController.setTextWindow.getInputTextField().setText(text.getText());
		if( text.getRotate() == -90 && text.getText().startsWith("bal"))
		homeController.setTextWindow.getInputTextField().setText("jobb" + text.getText().substring(3));
		homeController.setTextWindow.getController().setRotate(text.getRotate());
		homeController.setTextWindow.getController().setChosenText(text.getText());
		DecimalFormat df = new DecimalFormat("0.0");
		String XPosition = df.format(text.xProperty().get() / MILLIMETER).replace(',', '.');
		String YPosition = df.format(text.yProperty().get() / MILLIMETER).replace(',', '.');
		homeController.setTextWindow.getInputTextXField().setText(XPosition);
		homeController.setTextWindow.getInputTextYField().setText(YPosition);
	}
	
}
