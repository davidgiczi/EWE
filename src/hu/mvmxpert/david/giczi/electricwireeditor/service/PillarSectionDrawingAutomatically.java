package hu.mvmxpert.david.giczi.electricwireeditor.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;
import javax.naming.directory.InvalidAttributesException;

import hu.mvmxpert.david.giczi.electricwireeditor.model.MeasPoint;

public class PillarSectionDrawingAutomatically {
	
	
	private String startPillarId;
	private String endPillarId;
	private List<String> measDataList;
	public List<MeasPoint> startPillarPointList;
	public List<MeasPoint> endPillarPointList;
	public List<MeasPoint> leftOutsideWirePointList;
	public List<MeasPoint> leftInsideWirePointList;
	public List<MeasPoint> rightOutsideWirePointList;
	public List<MeasPoint> rightInsideWirePointList;
	public List<MeasPoint> mediumWirePointList;
	private static String[] POINT_TYPE = {"BAL", "JOBB", "KOZEP", "KULSO", "BELSO", "ALAP", "CSUCS", "FEL", "BEF", "VEZ", "SDR"};
	

	public PillarSectionDrawingAutomatically(String startPillarId, String endPillarId, List<String> measDataList) 
			throws InvalidAttributeValueException, InvalidAttributesException {
		this.startPillarId = startPillarId.trim().toUpperCase();
		this.endPillarId = endPillarId.trim().toUpperCase();
		this.measDataList = measDataList;
		parseStartPillarPointListData();
		parseEndPillarPointListData();
		parseLeftOutSideWirePointList();
		parseLeftSideWirePointList();
		parseLeftInSideWirePointList();
		parseRightInSideWirePointList();
		parseRightOutSideWirePointList();
		parseRightSideWirePointList();
		parseMediumWirePointList();
		validatePillarInputData();
		validateInputWireData();
	}
	
	
	
	private void validatePillarInputData() throws InvalidAttributeValueException {
		if( startPillarPointList.isEmpty() ) {
			throw new InvalidAttributeValueException("Nem található mérési adat a(z) " + startPillarId + ". oszlopra vonatkozóan.");
		}	
		int basePoint = 0;
		int isUpperPoint = 0;
		for(MeasPoint measPoint : startPillarPointList) {
			if( measPoint.pointType.equals(POINT_TYPE[5]) ) {
				basePoint++;
			}
			else if( measPoint.isUpper && measPoint.pointType.endsWith(POINT_TYPE[8]) ) {
				isUpperPoint++;
			}
		}
		if( basePoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó " +  POINT_TYPE[5] + " pontok a(z) " + startPillarId + ". oszlopnál.");
		}
		else if( isUpperPoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó " + POINT_TYPE[8] + " pontok a(z) "  + startPillarId + ". oszlopnál.");
		}
		
		if( endPillarPointList.isEmpty() ) {
			throw new InvalidAttributeValueException("Nem található mérési adat a(z) " + endPillarId + ". oszlopra vonatkozóan.");
		}
		
		for(MeasPoint measPoint : endPillarPointList) {
			if( measPoint.pointType.equals(POINT_TYPE[5]) ) {
				basePoint++;
			}
			else if( measPoint.isUpper && measPoint.pointType.endsWith(POINT_TYPE[8]) ) {
				isUpperPoint++;
			}
		}
		if( basePoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó " +  POINT_TYPE[5] + " pontok a(z) " + endPillarId + ". oszlopnál.");
		}
		else if( isUpperPoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó " + POINT_TYPE[8] + " pontok a(z) "  + endPillarId + ". oszlopnál.");
		}
			
	}
	
	private void validateInputWireData() throws InvalidAttributesException, InvalidAttributeValueException {
		if( leftOutsideWirePointList.isEmpty() &&
			leftInsideWirePointList.isEmpty() &&
			mediumWirePointList.isEmpty() &&
			rightInsideWirePointList.isEmpty() &&
			rightOutsideWirePointList.isEmpty() ) {
			throw new InvalidAttributesException("Sodronyokra vontkozó mérési adatok nem találhatók.");
		}
		isValidListFor(leftOutsideWirePointList);
		isValidListFor(leftInsideWirePointList);
		isValidListFor(mediumWirePointList);
		isValidListFor(rightInsideWirePointList);
		isValidListFor(rightOutsideWirePointList);
	}
	
	private void isValidListFor(List<MeasPoint> wirePointList) throws InvalidAttributeValueException{
		int groundWirePoint = 0;
		int upperWirePoint = 0;
		for( MeasPoint wirePoint1 : wirePointList ) {
			
			for( MeasPoint wirePoint2 : wirePointList ) {
			
			
		}
	}
}	
	
	private void parseMediumWirePointList() {
		mediumWirePointList = new ArrayList<>();
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length < 4 ) {
				continue;
			}
			
			String[] pointIdData = rowData[0].split("-");
			String pointId = POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-";		
			
			switch (pointIdData.length) {
			case 4:
				pointId += pointIdData[3];
				break;
			case 5:
				pointId += pointIdData[3] + "-" + pointIdData[4];
				break;
			case 6:
				pointId += pointIdData[3] + "-" + pointIdData[4] + "-" + pointIdData[5];
				break;
			default:
					continue;
			}
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint mediumWirePoint = new MeasPoint();
				mediumWirePoint.setPointId(rowData[0].toUpperCase());
				mediumWirePoint.setPointX(Double.parseDouble(rowData[1]));
				mediumWirePoint.setPointY(Double.parseDouble(rowData[2]));
				mediumWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumWirePoint.setPointType(POINT_TYPE[2]);
				mediumWirePoint.setUpper(false);
				mediumWirePointList.add(mediumWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint mediumWirePoint = new MeasPoint();
				mediumWirePoint.setPointId(rowData[0].toUpperCase());
				mediumWirePoint.setPointX(Double.parseDouble(rowData[1]));
				mediumWirePoint.setPointY(Double.parseDouble(rowData[2]));
				mediumWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				if( rowData[0].contains(POINT_TYPE[9]) ) {
					mediumWirePoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[9]);
				}
				else if( rowData[0].contains(POINT_TYPE[10]) ) {
					mediumWirePoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[10]);
				}
				else {
					mediumWirePoint.setPointType(POINT_TYPE[2]);
				}
				mediumWirePoint.setUpper(true);
				mediumWirePointList.add(mediumWirePoint);
			}
}
	}
	
	private void parseLeftSideWirePointList() {
		
		if( !leftOutsideWirePointList.isEmpty() ) {
			return;
		}
		
		
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length < 4 ) {
				continue;
			}
			
			String[] pointIdData = rowData[0].split("-");
			String pointId = POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-";		
			
			switch (pointIdData.length) {
			case 4:
				pointId += pointIdData[3];
				break;
			case 5:
				pointId += pointIdData[3] + "-" + pointIdData[4];
				break;
			case 6:
				pointId += pointIdData[3] + "-" + pointIdData[4] + "-" + pointIdData[5];
				break;
			default:
					continue;
			}
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0]);
				leftWirePoint.setUpper(false);
				leftOutsideWirePointList.add(leftWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				if( rowData[0].contains(POINT_TYPE[9]) ) {
					leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[9]);
				}
				else if( rowData[0].contains(POINT_TYPE[10]) ) {
					leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[10]);
				}
				else {
					leftWirePoint.setPointType(POINT_TYPE[0]);
				}
				leftWirePoint.setUpper(true);
				leftOutsideWirePointList.add(leftWirePoint);
			}
}
	}
	
	private void parseLeftOutSideWirePointList() {
		leftOutsideWirePointList = new ArrayList<>();
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length < 4 ) {
				continue;
			}
			
			String[] pointIdData = rowData[0].split("-");
			String pointId = POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-";		
			
			switch (pointIdData.length) {
			case 5:
				pointId += pointIdData[4];
				break;
			case 6:
				pointId += pointIdData[4] + "-" + pointIdData[5];
				break;
			case 7:
				pointId += pointIdData[4] + "-" + pointIdData[5] + "-" + pointIdData[6];
				break;
			default:
					continue;
			}
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3]);
				leftWirePoint.setUpper(false);
				leftOutsideWirePointList.add(leftWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				if( rowData[0].contains(POINT_TYPE[9]) ) {
					leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[9]);
				}
				else if( rowData[0].contains(POINT_TYPE[10]) ) {
					leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[10]);
				}
				else {
					leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3]);
				}
				leftWirePoint.setUpper(true);
				leftOutsideWirePointList.add(leftWirePoint);
			}
}
	}
		
	private void parseRightSideWirePointList() {
		
		if( !rightOutsideWirePointList.isEmpty() ) {
			return;
		}
		
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length < 4 ) {
				continue;
			}
			
			String[] pointIdData = rowData[0].split("-");
			String pointId = POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-";		
			
			switch (pointIdData.length) {
			case 4:
				pointId += pointIdData[3];
				break;
			case 5:
				pointId += pointIdData[3] + "-" + pointIdData[4];
				break;
			case 6:
				pointId += pointIdData[3] + "-" + pointIdData[4] + "-" + pointIdData[5];
				break;
			default:
					continue;
			}
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[1]);
				rightWirePoint.setUpper(false);
				rightOutsideWirePointList.add(rightWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				if( rowData[0].contains(POINT_TYPE[9]) ) {
					rightWirePoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[9]);
				}
				else if( rowData[0].contains(POINT_TYPE[10]) ) {
					rightWirePoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[10]);
				}
				else {
					rightWirePoint.setPointType(POINT_TYPE[1]);
				}
				rightWirePoint.setUpper(true);
				rightOutsideWirePointList.add(rightWirePoint);
			}
}
	}
	
	private void parseRightOutSideWirePointList() {
		rightOutsideWirePointList = new ArrayList<>();
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length < 4 ) {
				continue;
			}
			
			String[] pointIdData = rowData[0].split("-");
			String pointId = POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-";		
			
			switch (pointIdData.length) {
			case 4:
				pointId += pointIdData[3];
				break;
			case 5:
				pointId += pointIdData[3] + "-" + pointIdData[4];
				break;
			case 6:
				pointId += pointIdData[3] + "-" + pointIdData[4] + "-" + pointIdData[5];
				break;
			default:
					continue;
			}
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3]);
				rightWirePoint.setUpper(false);
				rightOutsideWirePointList.add(rightWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				if( rowData[0].contains(POINT_TYPE[9]) ) {
					rightWirePoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[9]);
				}
				else if( rowData[0].contains(POINT_TYPE[10]) ) {
					rightWirePoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[10]);
				}
				else {
					rightWirePoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3]);
				}
				rightWirePoint.setUpper(true);
				rightOutsideWirePointList.add(rightWirePoint);
			}
}
	}
	
	
	private void parseLeftInSideWirePointList() {
		leftInsideWirePointList = new ArrayList<>();
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length < 4 ) {
				continue;
			}
			
			String[] pointIdData = rowData[0].split("-");
			String pointId = POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-";		
			
			switch (pointIdData.length) {
			case 5:
				pointId += pointIdData[4];
				break;
			case 6:
				pointId += pointIdData[4] + "-" + pointIdData[5];
				break;
			case 7:
				pointId += pointIdData[4] + "-" + pointIdData[5] + "-" + pointIdData[6];
				break;
			default:
					continue;
			}
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4]);
				leftWirePoint.setUpper(false);
				leftInsideWirePointList.add(leftWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				if( rowData[0].contains(POINT_TYPE[9]) ) {
					leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[9]);
				}
				else if( rowData[0].contains(POINT_TYPE[10]) ) {
					leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[10]);
				}
				else {
					leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4]);
				}
				leftWirePoint.setUpper(true);
				leftInsideWirePointList.add(leftWirePoint);
			}
}
	}
	
	private void parseRightInSideWirePointList() {
		rightInsideWirePointList = new ArrayList<>();
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length < 4 ) {
				continue;
			}
			
			String[] pointIdData = rowData[0].split("-");
			String pointId = POINT_TYPE[0] + "-" + POINT_TYPE[5] + "-" + startPillarId + "-" + endPillarId + "-";		
			
			switch (pointIdData.length) {
			case 5:
				pointId += pointIdData[4];
				break;
			case 6:
				pointId += pointIdData[4] + "-" + pointIdData[5];
				break;
			case 7:
				pointId += pointIdData[4] + "-" + pointIdData[5] + "-" + pointIdData[6];
				break;
			default:
					continue;
			}
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[5]);
				rightWirePoint.setUpper(false);
				rightInsideWirePointList.add(rightWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) ) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				if( rowData[0].contains(POINT_TYPE[9]) ) {
					rightWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[5] + "-" + POINT_TYPE[9]);
				}
				else if( rowData[0].contains(POINT_TYPE[10]) ) {
					rightWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[5] + "-" + POINT_TYPE[10]);
				}
				else {
					rightWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[5]);
				}
				rightWirePoint.setUpper(true);
				rightInsideWirePointList.add(rightWirePoint);
			}
}
	}
		
	
	private void parseStartPillarPointListData() {
		startPillarPointList = new ArrayList<>();
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length < 4 ) {
				continue;
			}
			
			String[] pillarId = rowData[0].split("_");
					
			if( pillarId[0].equalsIgnoreCase(startPillarId) && 
					rowData[rowData.length - 1].equalsIgnoreCase(POINT_TYPE[5])) {
				MeasPoint basePoint = new MeasPoint();
				basePoint.setPointId(rowData[0].toUpperCase());
				basePoint.setPointX(Double.parseDouble(rowData[1]));
				basePoint.setPointY(Double.parseDouble(rowData[2]));
				basePoint.setPointZ(Double.parseDouble(rowData[3]));
				basePoint.setPointType(POINT_TYPE[5]);
				startPillarPointList.add(basePoint);
			}
			else if( pillarId[0].equalsIgnoreCase(startPillarId) && 
					rowData[rowData.length - 1].equalsIgnoreCase(POINT_TYPE[6])) {
				MeasPoint topPoint = new MeasPoint();
				topPoint.setPointId(rowData[0].toUpperCase());
				topPoint.setPointX(Double.parseDouble(rowData[1]));
				topPoint.setPointY(Double.parseDouble(rowData[2]));
				topPoint.setPointZ(Double.parseDouble(rowData[3]));
				topPoint.setUpper(true);
				topPoint.setPointType(POINT_TYPE[6]);
				startPillarPointList.add(topPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[0] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftPoint = new MeasPoint();
				leftPoint.setPointId(POINT_TYPE[0] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				leftPoint.setPointX(Double.parseDouble(rowData[1]));
				leftPoint.setPointY(Double.parseDouble(rowData[2]));
				leftPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[8]);
				leftPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[1] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightPoint = new MeasPoint();
				rightPoint.setPointId(POINT_TYPE[1] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightPoint.setPointX(Double.parseDouble(rowData[1]));
				rightPoint.setPointY(Double.parseDouble(rowData[2]));
				rightPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[8]);
				rightPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[2] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint mediumPoint = new MeasPoint();
				mediumPoint.setPointId(POINT_TYPE[2] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				mediumPoint.setPointX(Double.parseDouble(rowData[1]));
				mediumPoint.setPointY(Double.parseDouble(rowData[2]));
				mediumPoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumPoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[8]);
				mediumPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(mediumPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftOutPoint = new MeasPoint();
				leftOutPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				leftOutPoint.setPointX(Double.parseDouble(rowData[1]));
				leftOutPoint.setPointY(Double.parseDouble(rowData[2]));
				leftOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftOutPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				leftOutPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftOutPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftInPoint = new MeasPoint();
				leftInPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				leftInPoint.setPointX(Double.parseDouble(rowData[1]));
				leftInPoint.setPointY(Double.parseDouble(rowData[2]));
				leftInPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftInPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				leftInPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftInPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[1] + "-" +  POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightInPoint = new MeasPoint();
				rightInPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightInPoint.setPointX(Double.parseDouble(rowData[1]));
				rightInPoint.setPointY(Double.parseDouble(rowData[2]));
				rightInPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightInPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				rightInPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightInPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightOutPoint = new MeasPoint();
				rightOutPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightOutPoint.setPointX(Double.parseDouble(rowData[1]));
				rightOutPoint.setPointY(Double.parseDouble(rowData[2]));
				rightOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightOutPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				rightOutPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightOutPoint);
			}
				
		}
		
	}
	

	private void parseEndPillarPointListData() {
		endPillarPointList = new ArrayList<>();
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length  < 4 ) {
				continue;
			}
			
			String[] pillarId = rowData[0].split("_");
					
			if( pillarId[0].equalsIgnoreCase(endPillarId) && 
					rowData[rowData.length - 1].equalsIgnoreCase(POINT_TYPE[5])) {
				MeasPoint basePoint = new MeasPoint();
				basePoint.setPointId(rowData[0].toUpperCase());
				basePoint.setPointX(Double.parseDouble(rowData[1]));
				basePoint.setPointY(Double.parseDouble(rowData[2]));
				basePoint.setPointZ(Double.parseDouble(rowData[3]));
				basePoint.setPointType(POINT_TYPE[5]);
				endPillarPointList.add(basePoint);
			}
			else if( pillarId[0].equalsIgnoreCase(endPillarId) && 
					rowData[rowData.length - 1].equalsIgnoreCase(POINT_TYPE[6])) {
				MeasPoint topPoint = new MeasPoint();
				topPoint.setPointId(rowData[0].toUpperCase());
				topPoint.setPointX(Double.parseDouble(rowData[1]));
				topPoint.setPointY(Double.parseDouble(rowData[2]));
				topPoint.setPointZ(Double.parseDouble(rowData[3]));
				topPoint.setUpper(true);
				topPoint.setPointType(POINT_TYPE[6]);
				endPillarPointList.add(topPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[0] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftPoint = new MeasPoint();
				leftPoint.setPointId(POINT_TYPE[0] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				leftPoint.setPointX(Double.parseDouble(rowData[1]));
				leftPoint.setPointY(Double.parseDouble(rowData[2]));
				leftPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[8]);
				leftPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[1] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightPoint = new MeasPoint();
				rightPoint.setPointId(POINT_TYPE[1] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				rightPoint.setPointX(Double.parseDouble(rowData[1]));
				rightPoint.setPointY(Double.parseDouble(rowData[2]));
				rightPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[8]);
				rightPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[2] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint mediumPoint = new MeasPoint();
				mediumPoint.setPointId(POINT_TYPE[2] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				mediumPoint.setPointX(Double.parseDouble(rowData[1]));
				mediumPoint.setPointY(Double.parseDouble(rowData[2]));
				mediumPoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumPoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[8]);
				mediumPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(mediumPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftOutPoint = new MeasPoint();
				leftOutPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				leftOutPoint.setPointX(Double.parseDouble(rowData[1]));
				leftOutPoint.setPointY(Double.parseDouble(rowData[2]));
				leftOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftOutPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				leftOutPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftOutPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftInPoint = new MeasPoint();
				leftInPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				leftInPoint.setPointX(Double.parseDouble(rowData[1]));
				leftInPoint.setPointY(Double.parseDouble(rowData[2]));
				leftInPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftInPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				leftInPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftInPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[1] + "-" +  POINT_TYPE[4] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightInPoint = new MeasPoint();
				rightInPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				rightInPoint.setPointX(Double.parseDouble(rowData[1]));
				rightInPoint.setPointY(Double.parseDouble(rowData[2]));
				rightInPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightInPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				rightInPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightInPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightOutPoint = new MeasPoint();
				rightOutPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				rightOutPoint.setPointX(Double.parseDouble(rowData[1]));
				rightOutPoint.setPointY(Double.parseDouble(rowData[2]));
				rightOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightOutPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				rightOutPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightOutPoint);
			}
				
		}
		
	}
	

}
