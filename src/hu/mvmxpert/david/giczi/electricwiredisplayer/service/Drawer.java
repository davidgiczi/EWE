package hu.mvmxpert.david.giczi.electricwiredisplayer.service;


import java.text.DecimalFormat;
import hu.mvmxpert.david.giczi.electricwiredisplayer.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.PillarData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.TextData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.WireData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.view.ModifyTextWindow;
import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
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
	public static double X_DISTANCE;
	private final double MARGIN = 156 * MILLIMETER;
	private final double HOR_SHIFT = 12;
	private final double PAGE_Y = 25;
	private final double VER_SHIFT = 5;
	private final double START_Y = 550.0;
	private double lengthOfHorizontalAxis;
	private int horizontalScale;
	private int verticalScale;
	private int elevationStartValue;
	private ModifyTextWindow modifyTextWindow;
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
		setText(-1, String.valueOf(startValue) + "m", - 70, PAGE_Y + startY, 18, 0);
		startY -= 10 * MILLIMETER;
		startValue += verticalScale;
		}
		setText(-1, "Oszlopszám:", - 17 * MILLIMETER, PAGE_Y + START_Y + 30 * MILLIMETER, 18, 0);
	}
	
	public void writeDistanceValueForHorizontalAxis() {
		setText(-1, "0", (HOR_SHIFT - 1) * MILLIMETER, PAGE_Y + START_Y + 50, 18, 0);
		setText(-1, String.valueOf(lengthOfHorizontalAxis) + "m", 
				getHorizontalScaledDownLengthValue(lengthOfHorizontalAxis) * MILLIMETER, 
				PAGE_Y + START_Y + 50, 18, 0);
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
			deletePillarOrWire(line);
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
				deletePillarOrWire(line);
				});
			hood.setId(String.valueOf(pillarData.getId()));
			hood.setStroke(Color.BLUE);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
		
		setText(pillarData.getId(), id + ".", 
				(getHorizontalScaledDownLengthValue(distance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 30 * MILLIMETER, 18, 0);	
		setText(pillarData.getId(), "bal ak.: Bf. " + df.format(groundElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, pillar.getStartY(), 18, -90);
		setText(pillarData.getId(), "bal ak.: Bf. " + df.format(topElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, pillar.getEndY(), 18, -90);
		if( distance != 0 && distance != lengthOfHorizontalAxis )
		setText(pillarData.getId(), df.format(distance).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  + HOR_SHIFT - VER_SHIFT) * MILLIMETER, PAGE_Y + START_Y + 50, 18, 0);
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
			deletePillarOrWire(line);
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
				deletePillarOrWire(line);
				});
			root.getChildren().add(hood);
		}
		
		setText(wireData.getId(), "bal af.: Bf. " + df.format(groundElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, wire.getStartY(), 18, -90);
		setText(wireData.getId(), "bal af.: Bf. " + df.format(topElevation).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance)  - HOR_SHIFT) * MILLIMETER, wire.getEndY(), 18, -90);
		if( distance != 0 && distance != lengthOfHorizontalAxis )
		setText(wireData.getId(), df.format(distance).replace(",", ".") + "m", 
				(getHorizontalScaledDownLengthValue(distance) + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 50, 18, 0);
		setText(wireData.getId(), text, (getHorizontalScaledDownLengthValue(distance) + HOR_SHIFT - VER_SHIFT) * MILLIMETER, 
				PAGE_Y + START_Y + 65, 18, 0);
	}
	
	public void writeText(String text, double startX, double startY, double rotate) {
		Text txt = new Text(text);
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 18));
		TextData textData = new TextData();
		textData.setTextValue(text);
		textData.setSize(18);
		textData.setType("SingleText");
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
		txt.setCursor(Cursor.HAND);
		textData.setId(ArchivFileBuilder.addID());
		archivFileBuilder.addText(textData);
		txt.setId(String.valueOf(textData.getId()));
		txt.setOnMouseClicked( t -> {
		Text inputText = (Text) t.getSource();
		getModifyTextWindow(inputText); });
		root.getChildren().add(txt);
	}
	
	public void setText(int id, String text, double startX, double startY, int size, int rotate) {
		Text txt = new Text(text);
		txt.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, size));
		if( rotate == -90 ) {
		txt.setRotationAxis(Rotate.Z_AXIS);
		txt.setRotate(-90);
		}
		txt.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(startX));
		txt.setY(startY);
		
		int pillarTextId = getPillarTextId(id, text, startX, startY, size, rotate);
		int wireTextId = getWireTextId(id, text, startX, startY, size, rotate);
		
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
		getModifyTextWindow(inputText); });
		root.getChildren().add(txt);
	}
	
	private int getPillarTextId(int id, String text, double startX, double startY, int size, int rotate) {
		PillarData pillar = archivFileBuilder.getPillarData(id);
		if( pillar == null )
			return -1;
		TextData pillarText = new TextData(text, startX, startY, size, rotate, "PillarText");
		pillarText.setId(ArchivFileBuilder.addID());
		pillar.getPillarTextList().add(pillarText);
		return pillarText.getId();
	}
	
	private int getWireTextId(int id, String text, double startX, double startY, int size, int rotate) {
		WireData wire = archivFileBuilder.getWireData(id);
		if( wire == null )
			return -1;
		TextData wireText = new TextData(text, startX, startY, size, rotate, "WireText");
		wireText.setId(ArchivFileBuilder.addID());
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
			deletePillarOrWire(line);
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
				deletePillarOrWire(line);
				});
			hood.setId(String.valueOf(id));
			hood.setStroke(Color.BLUE);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
		
	}
	
	public void drawInputPillarText(PillarData pillarData) {
		
		for (TextData textData : pillarData.getPillarTextList()) {
			if( textData.getDirection() == -90)
			textData.setX((getHorizontalScaledDownLengthValue(pillarData.getDistanceOfPillar()) - HOR_SHIFT) * MILLIMETER);
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
				text.setY(textData.getY());
				text.setOnMouseClicked( t -> {
					Text inputText = (Text) t.getSource();
					getModifyTextWindow(inputText); });
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
			deletePillarOrWire(line);
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
				deletePillarOrWire(line);
				});
			hood.setId(String.valueOf(id));
			hood.setStroke(Color.RED);
			hood.setStrokeWidth(3);
			root.getChildren().add(hood);
		}
	}
	
	public void drawInputWireText(WireData wireData) {
		
		for (TextData textData : wireData.getWireTextList()) {
			if( textData.getDirection() == -90)
			textData.setX((getHorizontalScaledDownLengthValue(wireData.getDistanceOfWire()) - HOR_SHIFT) * MILLIMETER);
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
				text.setY(textData.getY());
				text.setOnMouseClicked( t -> {
					Text inputText = (Text) t.getSource();
					getModifyTextWindow(inputText); });
				text.setCursor(Cursor.HAND);
				root.getChildren().add(text);
		}
	}
	
	
	public void drawInputText(TextData textData) {
		Text text = new Text(textData.getTextValue());
		text.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, textData.getSize()));
		if( textData.getDirection() == -90 ) {
			text.setRotationAxis(Rotate.Z_AXIS);
			text.setRotate(-90);
			}
			text.xProperty().bind(root.widthProperty().divide(2).subtract(A4_WIDTH / 2).add(START_X).add(textData.getX()));
			text.setY(textData.getY());
			root.getChildren().add(text);
	}
	
	private void deletePillarOrWire(Line line) {
	if( HomeController.getConfirmationAlert("Oszlop/vezeték törlése", "Biztos, hogy törlöd a kiválasztott oszlopot/vezetéket?") ) {
		int id = Integer.valueOf(line.getId());
		archivFileBuilder.removePillar(id, root);
		archivFileBuilder.removeWire(id, root);
	}
}
	
	private void getModifyTextWindow(Text text) {
		
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

	private double getHorizontalScaledDownLengthValue(double length) {
		return horizontalScale == 1000 ? length : 1000.0  * length / horizontalScale;
	}
	
	private double getVerticalScaledDownHeightValue(double height) {
		return verticalScale == 10 ? height : 10.0 * height / verticalScale;
	}
	
	private void addChosenTextToSetTextWindow(Text text) {
		homeController.getSetTextWindow();
		homeController.setTextWindow.getInputTextField().setText(text.getText());
		homeController.setTextWindow.getController().setRotate(text.getRotate());
		DecimalFormat df = new DecimalFormat("0.0");
		String XPosition = df.format(text.xProperty().get() / MILLIMETER).replace(',', '.');
		String YPosition = df.format(text.yProperty().get() / MILLIMETER).replace(',', '.');
		homeController.setTextWindow.getInputTextXField().setText(XPosition);
		homeController.setTextWindow.getInputTextYField().setText(YPosition);
	}
		
}
