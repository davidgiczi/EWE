package hu.mvmxpert.david.giczi.electricwireeditor.service;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.mvmxpert.david.giczi.electricwireeditor.model.DrawingSystemData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.LineData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.TextData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WirePoint;
import javafx.scene.layout.BorderPane;

public class ArchivFileBuilder {
	
	public static int id;
	private DrawingSystemData systemData;
	private List<PillarData> pillarData;
	private List<WireData> wireData;
	private List<TextData> textData;
	private List<LineData> lineData;
	
	public DrawingSystemData getSystemData() {
		return systemData;
	}
	
	public void setSystemData(DrawingSystemData systemData) {
		this.systemData = systemData;
	}

	public List<PillarData> getPillarData() {
		return pillarData;
	}

	public List<WireData> getWireData() {
		return wireData;
	}

	public List<TextData> getTextData() {
		return textData;
	}

	public List<LineData> getLineData() {
		return lineData;
	}

	public void init() {
		id = 0;
		systemData = new DrawingSystemData();
		pillarData = new ArrayList<>();
		wireData = new ArrayList<>();
		textData = new ArrayList<>();
		lineData = new ArrayList<>();
	}
	
	public static int addID() {
		id++;
		return id;
	}
	
	public void setSystemData(double lengthOfHorizontalAxis, int horizontalScale, int elevationStartValue, int verticalScale) {
		systemData.setLengthOfHorizontalAxis(lengthOfHorizontalAxis);
		systemData.setHorizontalScale(horizontalScale);
		systemData.setElevationStartValue(elevationStartValue);
		systemData.setVerticalScale(verticalScale);
	}
	
	public void addPillar(PillarData pillar) {
		pillarData.add(pillar);
	}
	
	
	public PillarData getPillarData(int id) {
		
		PillarData data = null;
		
		for (PillarData pillar : pillarData) {
			if( pillar.getId() == id) {
				data = pillar;
			}
		}

		return data;
	}
	
	public PillarData getLastPillar() {
		
		PillarData data = null;
		
		for (PillarData pillar : pillarData) {
			if( pillar.getDistanceOfPillar() == systemData.getLengthOfHorizontalAxis()) {
				data = pillar;
			}
		}
	
		return data;
	}
	
	public void removePillar(int id, BorderPane root) {
		
		PillarData pillar = getPillarData(id);
		if( pillar == null )
			return;
		
		for(TextData pillarText : pillar.getPillarTextList()) {
		
			for(int i = root.getChildren().size() - 1; i >= 0; i--) {
				if(root.getChildren().get(i).getId() != null && 
						(Integer.valueOf(root.getChildren().get(i).getId()) == pillarText.getId() ||
						Integer.valueOf(root.getChildren().get(i).getId()) == id)) {
				root.getChildren().remove(i);
				}
			}
		}
		
		for( int i = pillarData.size() - 1; i >= 0; i-- ) {
			if( pillarData.get(i).getId() == id ) {
				pillarData.remove(i);
			}
	}
}
	public void removePillarText(int id) {
		for (PillarData pillar : pillarData) {
			for(int i = pillar.getPillarTextList().size() - 1; i >= 0; i--) {
				if( pillar.getPillarTextList().get(i).getId() == id ) {
					pillar.getPillarTextList().remove(i);
				}
			}
		}
	}
	
	public void addWire(WireData wire) {
		wireData.add(wire);
	}
	
	public WireData getWireData(int id) {
		
		WireData data = null;
		
		for (WireData wire : wireData) {
			if( wire.getId() == id ) {
				data = wire;
			}
		}
		
		return data;
	}
	
	public void removeWire(int id, BorderPane root) {
		
		WireData wire = getWireData(id);
		if( wire == null )
			return;
		
		for(TextData wireText : wire.getWireTextList()) {
			for(int i = root.getChildren().size() - 1; i >= 0; i--) {
				if( root.getChildren().get(i).getId() != null && 
						(Integer.valueOf(root.getChildren().get(i).getId()) == wireText.getId() ||
						Integer.valueOf(root.getChildren().get(i).getId()) == id)) {
					root.getChildren().remove(i);
				}
				
		}
	}
		for( int i = wireData.size() - 1; i >= 0; i-- ) {
			if( wireData.get(i).getId() == id ) {
				wireData.remove(i);
			}
		}
	}
	
	public void removeWireText(int id) {
		for(WireData wire : wireData) {
			for(int i = wire.getWireTextList().size() - 1; i >= 0; i--) {
				if(  wire.getWireTextList().get(i).getId() == id ) {
					wire.getWireTextList().remove(i);
			}
		}
	}
}
	public void addText(TextData text) {
		textData.add(text);
	}
	
	public void removeText(int id) {
		for(int i = textData.size() - 1; i >= 0; i--) {
			if( textData.get(i).getId() == id ) {
				textData.remove(textData.get(i));
			}
		}
	}
	
	public TextData getTextData(int id) {
		TextData text = null;
		for (TextData textData : textData) {
			if( textData.getId() == id ) {
				text = textData;
			}
		}
		
		for(PillarData pillarData : pillarData) {
			for(TextData textData : pillarData.getPillarTextList()) {
				if( textData.getId() == id ) {
					text = textData;
				}
			}
		}
		
		for(WireData wireData : wireData) {
			for(TextData textData : wireData.getWireTextList()) {
				if( textData.getId() == id ) {
					text = textData;
				}
			}
		}

		return text;
	}
	
	public void addLine(LineData line) {
		lineData.add(line);
	}
	
	public void removeLine(int id, BorderPane root) {
		for(int i = lineData.size() - 1; i >= 0; i--) {
			if( lineData.get(i).getId() == id ) {
				lineData.remove(lineData.get(i));
			}
		}
		for(int i = root.getChildren().size() - 1; i >= 0; i--) {
			if(root.getChildren().get(i).getId() != null && Integer.valueOf(root.getChildren().get(i).getId()) == id) {
				root.getChildren().remove(i);
			}
		}
	}	
	
	public LineData getLineData(int id) {
		
		LineData line = null;
		
		for (LineData lineData : lineData) {
			if( lineData.getId() == id) {
				line = lineData;
			}
		}
	
		return line;
	}
	
	public void changePillarDistanceText(int id,  double distanceRatio) {
		PillarData pillarData = getPillarData(id);
		DecimalFormat df = new DecimalFormat("0.00");
		for (TextData pillarText : pillarData.getPillarTextList()) {
			if(	pillarText.getTextValue().equals(df.format(pillarData.getDistanceOfPillar()).replace(",", ".") + "m")) {
				pillarText.setTextValue(df.format((pillarData.getDistanceOfPillar() * distanceRatio)).replace(",", ".") + "m");
			}
		}
		
	}
	
	public void changeWireDistanceText(int id, double distanceRatio) {
		WireData wireData = getWireData(id);
		DecimalFormat df = new DecimalFormat("0.00");
		for (TextData wireText : wireData.getWireTextList()) {
			if(	wireText.getTextValue().equals(df.format(wireData.getDistanceOfWire()).replace(",", ".") + "m")) {
				wireText.setTextValue(df.format((wireData.getDistanceOfWire() * distanceRatio)).replace(",", ".") + "m");
			}
		}
	}
	
	public int getChosenTextOwnerId(String chosenText) {
		
		int ownerId = -1;
		for ( PillarData pillarData : pillarData ) {
			for( TextData textData : pillarData.getPillarTextList() ) 
					if( textData.getTextValue().equals(chosenText))
							ownerId = pillarData.getId();	
		}
		for(WireData wireData : wireData) {
			for( TextData textData : wireData.getWireTextList() )
					if( textData.getTextValue().equals(chosenText) )
							ownerId = wireData.getId();
		}
		
		for( TextData textData : textData) {
			if(textData.getTextValue().equals(chosenText))
					ownerId = textData.getId();
		}
		
		return ownerId;
	}

	public TextData getChosenTextData(String chosenText) {
		
		TextData chosenTextData = null;
		for ( PillarData pillarData : pillarData ) {
			for( TextData textData : pillarData.getPillarTextList() ) 
					if( textData.getTextValue().equals(chosenText))
							chosenTextData = textData;	
		}
		for(WireData wireData : wireData) {
			for( TextData textData : wireData.getWireTextList() )
					if( textData.getTextValue().equals(chosenText) )
						chosenTextData = textData;
		}
		
		for( TextData textData : textData) {
			if(textData.getTextValue().equals(chosenText))
				chosenTextData = textData;
		}	
		
		return chosenTextData;
	}
	
	public boolean isChosenTextAtTop(String chosenText) {
		
		boolean isAtTop = false;
		
		for ( PillarData pillarData : pillarData ) {
			for( TextData textData : pillarData.getPillarTextList() )
					if( textData.getTextValue().equals(chosenText))
							isAtTop = textData.isAtTop();
		}
		for(WireData wireData : wireData) {
			for( TextData textData : wireData.getWireTextList() )
					if( textData.getTextValue().equals(chosenText) )
							isAtTop = textData.isAtTop();
		}
		
		return isAtTop;
	}
	
	public void addChosenTextToOwnerTextList(TextData chosenTextData, int ownerId) {
		
		chosenTextData.setOnLeftSide(false);
		PillarData pillar = getPillarData(ownerId);
		if( pillar != null) {
			chosenTextData.setType("PillarText");
			pillar.getPillarTextList().add(chosenTextData);
			return;
		}
		WireData wire = getWireData(ownerId);
		if( wire != null ) {
			chosenTextData.setType("WireText");
			wire.getWireTextList().add(chosenTextData);
			return;
		}
		chosenTextData.setType("SingleText");
		chosenTextData.setOnLeftSide(false);
		textData.add(chosenTextData);
	}
	
	public double getMinElevationStartValue() {
		
	double minValue = systemData.getElevationStartValue() + 10 * systemData.getVerticalScale();
	
	for (PillarData pillarData : pillarData) {
		if( minValue > pillarData.getGroundElevation() )
			minValue = pillarData.getGroundElevation();
	}
	for(WireData wireData : wireData) {
		if( minValue > wireData.getGroundElevation() )
			minValue = wireData.getGroundElevation();
	}
		
		return minValue;
	}
	
	public double getMaxElevationStartValue() {
		
		double maxValue = 0;
		
		for (PillarData pillarData : pillarData) {
			if( maxValue < pillarData.getTopElevetaion() )
				maxValue = pillarData.getTopElevetaion();
		}
		for(WireData wireData : wireData) {
			if( maxValue < wireData.getTopElevetaion() )
				maxValue = wireData.getTopElevetaion();
		}
		
		return maxValue;
	}
	
	public List<WirePoint> getLeftWirePoints(){
		List<WirePoint> leftWirePoints = new ArrayList<>();
		
		for (PillarData pillarData : pillarData) {
			if( !isRightPillar(pillarData) )
			leftWirePoints.add(new WirePoint(pillarData.getDistanceOfPillar(), 
					pillarData.getTopElevetaion() - systemData.getElevationStartValue()));
		}
		for (WireData wireData : wireData) {
			if( !isRightWire(wireData) )
			leftWirePoints.add(new WirePoint(wireData.getDistanceOfWire(), 
					wireData.getTopElevetaion() - systemData.getElevationStartValue()));
		}
		Collections.sort(leftWirePoints);
		
		for(int i = leftWirePoints.size() - 1; i > 0; i--) {
			
			if( leftWirePoints.get(i).getDistanceOfWirePoint() == leftWirePoints.get(i - 1).getDistanceOfWirePoint()) {
				if(leftWirePoints.get(i).getElevationOfWirePoint() < leftWirePoints.get(i - 1).getElevationOfWirePoint()) {
					leftWirePoints.remove(i);
				}
				else {
					leftWirePoints.remove(--i);
				}
			}
			
		}		
		return leftWirePoints;
	}
	
	private boolean isRightPillar(PillarData pillar) {
		int leftPillarText = 0;
		for (TextData text : pillar.getPillarTextList()) {
			if(text.getTextValue().startsWith("bal"))
				leftPillarText++;
		}
		return leftPillarText == 0;
	}
	
	
	private boolean isRightWire(WireData wire) {
		int leftWireText = 0;
		for (TextData text : wire.getWireTextList()) {
			if(text.getTextValue().startsWith("bal"))
				leftWireText++;
		}
		return leftWireText == 0;
	}
	
	public List<WirePoint> getRightWirePoints(){
		List<WirePoint> rightWirePoints = new ArrayList<>();
		for (PillarData pillarData : pillarData) {
			if( !isLeftPillar(pillarData)) {
				for (TextData text : pillarData.getPillarTextList()) {
					if( text.getTextValue().startsWith("jobb") && text.isAtTop()) {
						rightWirePoints.add(new WirePoint(pillarData.getDistanceOfPillar(), 
								Double.parseDouble(text.getTextValue().substring(14, text.getTextValue().indexOf('m')))
								- systemData.getElevationStartValue()));
					}
				}	
			}
		}
		for (WireData wireData : wireData) {
			if( !isLeftWire(wireData)) {
				for (TextData text : wireData.getWireTextList()) {
					if( text.getTextValue().startsWith("jobb") && text.isAtTop()) {
						rightWirePoints.add(new WirePoint(wireData.getDistanceOfWire(), 
								Double.parseDouble(text.getTextValue().substring(14, text.getTextValue().indexOf('m')))
								- systemData.getElevationStartValue()));
					}
				}	
			}
		}
		Collections.sort(rightWirePoints);
		for(int i = rightWirePoints.size() - 1; i > 0; i--) {
			
			if( rightWirePoints.get(i).getDistanceOfWirePoint() == rightWirePoints.get(i - 1).getDistanceOfWirePoint()) {
				if(rightWirePoints.get(i).getElevationOfWirePoint() < rightWirePoints.get(i - 1).getElevationOfWirePoint()) {
					rightWirePoints.remove(i);
				}
				else {
					rightWirePoints.remove(--i);
				}
			}
		}
		
		return rightWirePoints;
	}
	
	private boolean isLeftPillar(PillarData pillar) {
		int rightPillarText = 0;
		for (TextData text : pillar.getPillarTextList()) {
			if(text.getTextValue().startsWith("jobb"))
				rightPillarText++;
		}
		return rightPillarText == 0;
	}
	
	
	private boolean isLeftWire(WireData wire) {
		int rightWireText = 0;
		for (TextData text : wire.getWireTextList()) {
			if(text.getTextValue().startsWith("jobb"))
				rightWireText++;
		}
		return rightWireText == 0;
	}
	
	
}
