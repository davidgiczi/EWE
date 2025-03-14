package hu.mvmxpert.david.giczi.electricwireeditor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.management.InvalidAttributeValueException;
import javax.naming.directory.InvalidAttributesException;
import hu.mvmxpert.david.giczi.electricwireeditor.model.MeasPoint;
import hu.mvmxpert.david.giczi.electricwireeditor.model.MeasWire;

public class CollectPillarSectionMeasurementData {
	
	
	public String startPillarId;
	public String endPillarId;
	private List<String> measDataList;
	public List<MeasPoint> startPillarPointList;
	public List<MeasPoint> endPillarPointList;
	public List<MeasPoint> leftOutsideWirePointList;
	public List<MeasPoint> leftInsideWirePointList;
	public List<MeasPoint> rightOutsideWirePointList;
	public List<MeasPoint> rightInsideWirePointList;
	public List<MeasPoint> mediumWirePointList;
	public static String[] POINT_TYPE = {"BAL", "JOBB", "KOZEP", "KULSO", "BELSO", "ALAP", "CSUCS", "FEL", "BEF", "VEZ", "SDR", "TEREP"};
	
	

	public CollectPillarSectionMeasurementData(String startPillarId, String endPillarId, List<String> measDataList) 
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
		int isGroundPoint = 0;
		for(MeasPoint measPoint : startPillarPointList) {
			if( measPoint.pointType.equals(POINT_TYPE[5]) ) {
				basePoint++;
			}
			else if( measPoint.isUpper && measPoint.pointType.endsWith(POINT_TYPE[8]) ) {
				isUpperPoint++;
			}
			else if( !measPoint.isUpper && measPoint.pointType.endsWith(POINT_TYPE[8]) ) {
				isGroundPoint++;
			}
		}
		if( basePoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó " +  POINT_TYPE[5] + " pont a(z) " + startPillarId + ". oszlopnál.");
		}
		else if( isUpperPoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó befogás " + POINT_TYPE[8] + " pontok a(z) "  + startPillarId + ". oszlopnál.");
		}
		else if( isGroundPoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó terepi " + POINT_TYPE[8] + " pontok a(z) "  + startPillarId + ". oszlopnál.");
		}
		else if( isUpperPoint > isGroundPoint ) {
			throw new InvalidAttributeValueException("Kevesebb terepi, mint befogás " + POINT_TYPE[8] + " pont a(z) "  + startPillarId + ". oszlopnál.");
		}
		else if( isUpperPoint < isGroundPoint ) {
			throw new InvalidAttributeValueException("Kevesebb befogás, mint terepi " + POINT_TYPE[8] + " pont a(z) "  + startPillarId + ". oszlopnál.");
		}
		
		if( endPillarPointList.isEmpty() ) {
			throw new InvalidAttributeValueException("Nem található mérési adat a(z) " + endPillarId + ". oszlopra vonatkozóan.");
		}
		basePoint = 0;
		isUpperPoint = 0;
		isGroundPoint = 0;
		for(MeasPoint measPoint : endPillarPointList) {
			if( measPoint.pointType.equals(POINT_TYPE[5]) ) {
				basePoint++;
			}
			else if( measPoint.isUpper && measPoint.pointType.endsWith(POINT_TYPE[8]) ) {
				isUpperPoint++;
			}
			else if( !measPoint.isUpper && measPoint.pointType.endsWith(POINT_TYPE[8]) ) {
				isGroundPoint++;
			}
		}
		if( basePoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó " +  POINT_TYPE[5] + " pont a(z) " + endPillarId + ". oszlopnál.");
		}
		else if( isUpperPoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó befogás " + POINT_TYPE[8] + " pontok a(z) "  + endPillarId + ". oszlopnál.");
		}
		else if( isGroundPoint == 0 ) {
			throw new InvalidAttributeValueException("Hiányzó terepi " + POINT_TYPE[8] + " pontok a(z) "  + endPillarId + ". oszlopnál.");
		}
		else if( isUpperPoint > isGroundPoint ) {
			throw new InvalidAttributeValueException("Kevesebb terepi, mint befogás " + POINT_TYPE[8] + " pont a(z) "  + endPillarId + ". oszlopnál.");
		}
		else if( isUpperPoint < isGroundPoint ) {
			throw new InvalidAttributeValueException("Kevesebb befogás, mint terepi " + POINT_TYPE[8] + " pont a(z) "  + endPillarId + ". oszlopnál.");
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
		boolean isSingle;
		for (MeasPoint wirePoint1 : wirePointList) {
			isSingle = true;
			for (MeasPoint wirePoint2 : wirePointList) {
				
				if( wirePoint1.pointId.endsWith(POINT_TYPE[7]) ) {
					isSingle = false;
				}
				else if( wirePoint1.pointId.endsWith(POINT_TYPE[9]) ) {
					isSingle = false;
				}
				else if( wirePoint1.pointId.endsWith(POINT_TYPE[10]) && 
						wirePoint1.pointId.substring(0, wirePoint1.pointId.length() - 4).equals(wirePoint2.pointId) &&
						wirePoint1.isUpper != wirePoint2.isUpper) {
					isSingle = false;
				}
				else if( (wirePoint1.pointId + "-" + POINT_TYPE[10]).equals(wirePoint2.pointId) 
						&& wirePoint1.isUpper != wirePoint2.isUpper) {
					isSingle = false;
				}
				else if( wirePoint1.pointId.equals(wirePoint2.pointId) && wirePoint1.isUpper != wirePoint2.isUpper ) {
					isSingle = false;
				}
			}
			
			if( isSingle ) {
				throw new InvalidAttributeValueException("Hiányzó vagy hibás sodrony mérés: " + wirePoint1.pointId);
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
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint mediumWirePoint = new MeasPoint();
				mediumWirePoint.setPointId(rowData[0].toUpperCase());
				mediumWirePoint.setPointX(Double.parseDouble(rowData[1]));
				mediumWirePoint.setPointY(Double.parseDouble(rowData[2]));
				mediumWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumWirePoint.setPointType(POINT_TYPE[2]);
				mediumWirePoint.setUpper(false);
				mediumWirePointList.add(mediumWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint mediumWirePoint = new MeasPoint();
				mediumWirePoint.setPointId(rowData[0].toUpperCase());
				mediumWirePoint.setPointX(Double.parseDouble(rowData[1]));
				mediumWirePoint.setPointY(Double.parseDouble(rowData[2]));
				mediumWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumWirePoint.setPointType(POINT_TYPE[2]);
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
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0]);
				leftWirePoint.setUpper(false);
				leftOutsideWirePointList.add(leftWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0]);
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
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3]);
				leftWirePoint.setUpper(false);
				leftOutsideWirePointList.add(leftWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3]);
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
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[1]);
				rightWirePoint.setUpper(false);
				rightOutsideWirePointList.add(rightWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[1]);
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
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3]);
				rightWirePoint.setUpper(false);
				rightOutsideWirePointList.add(rightWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3]);
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
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4]);
				leftWirePoint.setUpper(false);
				leftInsideWirePointList.add(leftWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint leftWirePoint = new MeasPoint();
				leftWirePoint.setPointId(rowData[0].toUpperCase());
				leftWirePoint.setPointX(Double.parseDouble(rowData[1]));
				leftWirePoint.setPointY(Double.parseDouble(rowData[2]));
				leftWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				leftWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4]);
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
			
			if( !isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[5]);
				rightWirePoint.setUpper(false);
				rightInsideWirePointList.add(rightWirePoint);
			}
			else if( isTPSMeasure && rowData[0].toUpperCase().startsWith(pointId) &&
					!rowData[0].toUpperCase().endsWith(POINT_TYPE[8])) {
				MeasPoint rightWirePoint = new MeasPoint();
				rightWirePoint.setPointId(rowData[0].toUpperCase());
				rightWirePoint.setPointX(Double.parseDouble(rowData[1]));
				rightWirePoint.setPointY(Double.parseDouble(rowData[2]));
				rightWirePoint.setPointZ(Double.parseDouble(rowData[3]));
				rightWirePoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[5]);
				rightWirePoint.setUpper(true);
				rightInsideWirePointList.add(rightWirePoint);
			}
}
	}
		
	
	private void parseStartPillarPointListData() {
		startPillarPointList = new ArrayList<>();
		boolean isTPSMeasure;
		boolean isGrabbedId;
		for( String measDataRow : measDataList ) {
			
			 isTPSMeasure = false;
			 isGrabbedId = false;
			
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
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftPoint = new MeasPoint();
				leftPoint.setPointId(POINT_TYPE[0] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				leftPoint.setPointX(Double.parseDouble(rowData[1]));
				leftPoint.setPointY(Double.parseDouble(rowData[2]));
				leftPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[8]);
				leftPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase( POINT_TYPE[1] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightPoint = new MeasPoint();
				rightPoint.setPointId(POINT_TYPE[1] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightPoint.setPointX(Double.parseDouble(rowData[1]));
				rightPoint.setPointY(Double.parseDouble(rowData[2]));
				rightPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[8]);
				rightPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase( POINT_TYPE[2] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint mediumPoint = new MeasPoint();
				mediumPoint.setPointId(POINT_TYPE[2] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				mediumPoint.setPointX(Double.parseDouble(rowData[1]));
				mediumPoint.setPointY(Double.parseDouble(rowData[2]));
				mediumPoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumPoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[8]);
				mediumPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(mediumPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftOutPoint = new MeasPoint();
				leftOutPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				leftOutPoint.setPointX(Double.parseDouble(rowData[1]));
				leftOutPoint.setPointY(Double.parseDouble(rowData[2]));
				leftOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftOutPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				leftOutPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftOutPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftInPoint = new MeasPoint();
				leftInPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				leftInPoint.setPointX(Double.parseDouble(rowData[1]));
				leftInPoint.setPointY(Double.parseDouble(rowData[2]));
				leftInPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftInPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				leftInPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftInPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[1] + "-" +  POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightInPoint = new MeasPoint();
				rightInPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightInPoint.setPointX(Double.parseDouble(rowData[1]));
				rightInPoint.setPointY(Double.parseDouble(rowData[2]));
				rightInPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightInPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				rightInPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightInPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightOutPoint = new MeasPoint();
				rightOutPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightOutPoint.setPointX(Double.parseDouble(rowData[1]));
				rightOutPoint.setPointY(Double.parseDouble(rowData[2]));
				rightOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightOutPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				rightOutPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightOutPoint);
				isGrabbedId = true;
			}
			 
			if( isGrabbedId ) {
				continue;
			}
			
			
			if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-"  + POINT_TYPE[8]) ) {
				MeasPoint leftPoint = new MeasPoint();
				leftPoint.setPointId(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-"  +  POINT_TYPE[8]);
				leftPoint.setPointX(Double.parseDouble(rowData[1]));
				leftPoint.setPointY(Double.parseDouble(rowData[2]));
				leftPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[8]);
				leftPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftPoint);
			}
			else if( rowData[0].equalsIgnoreCase( POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-"  + POINT_TYPE[8]) ) {
				MeasPoint rightPoint = new MeasPoint();
				rightPoint.setPointId(POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-"  +  POINT_TYPE[8]);
				rightPoint.setPointX(Double.parseDouble(rowData[1]));
				rightPoint.setPointY(Double.parseDouble(rowData[2]));
				rightPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[8]);
				rightPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightPoint);
			}
			else if( rowData[0].equalsIgnoreCase( POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-"  + POINT_TYPE[8]) ) {
				MeasPoint mediumPoint = new MeasPoint();
				mediumPoint.setPointId(POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-"  +  POINT_TYPE[8]);
				mediumPoint.setPointX(Double.parseDouble(rowData[1]));
				mediumPoint.setPointY(Double.parseDouble(rowData[2]));
				mediumPoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumPoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[8]);
				mediumPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(mediumPoint);
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + 
			startPillarId + "-" + endPillarId + "-"  + POINT_TYPE[8]) ) {
				MeasPoint leftOutPoint = new MeasPoint();
				leftOutPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-"  +  POINT_TYPE[8]);
				leftOutPoint.setPointX(Double.parseDouble(rowData[1]));
				leftOutPoint.setPointY(Double.parseDouble(rowData[2]));
				leftOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftOutPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				leftOutPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftOutPoint);
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + 
			startPillarId + "-" + endPillarId + "-"  + POINT_TYPE[8]) ) {
				MeasPoint leftInPoint = new MeasPoint();
				leftInPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-"  +  POINT_TYPE[8]);
				leftInPoint.setPointX(Double.parseDouble(rowData[1]));
				leftInPoint.setPointY(Double.parseDouble(rowData[2]));
				leftInPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftInPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				leftInPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftInPoint);
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[1] + "-" +  POINT_TYPE[4] + "-" + 
			startPillarId + "-" + endPillarId + "-"  + POINT_TYPE[8]) ) {
				MeasPoint rightInPoint = new MeasPoint();
				rightInPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-"  +  POINT_TYPE[8]);
				rightInPoint.setPointX(Double.parseDouble(rowData[1]));
				rightInPoint.setPointY(Double.parseDouble(rowData[2]));
				rightInPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightInPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				rightInPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightInPoint);
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + 
			startPillarId + "-" + endPillarId + "-"  + POINT_TYPE[8]) ) {
				MeasPoint rightOutPoint = new MeasPoint();
				rightOutPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-"  +  POINT_TYPE[8]);
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
		boolean isTPSMeasure;
		boolean isGrabbedId;
		for( String measDataRow : measDataList ) {
			
			isTPSMeasure = false;
			isGrabbedId = false;
			
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
				isGrabbedId = true;
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
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftPoint = new MeasPoint();
				leftPoint.setPointId(POINT_TYPE[0] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				leftPoint.setPointX(Double.parseDouble(rowData[1]));
				leftPoint.setPointY(Double.parseDouble(rowData[2]));
				leftPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[8]);
				leftPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase( POINT_TYPE[1] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightPoint = new MeasPoint();
				rightPoint.setPointId(POINT_TYPE[1] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				rightPoint.setPointX(Double.parseDouble(rowData[1]));
				rightPoint.setPointY(Double.parseDouble(rowData[2]));
				rightPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[8]);
				rightPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase( POINT_TYPE[2] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint mediumPoint = new MeasPoint();
				mediumPoint.setPointId(POINT_TYPE[2] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				mediumPoint.setPointX(Double.parseDouble(rowData[1]));
				mediumPoint.setPointY(Double.parseDouble(rowData[2]));
				mediumPoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumPoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[8]);
				mediumPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(mediumPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftOutPoint = new MeasPoint();
				leftOutPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				leftOutPoint.setPointX(Double.parseDouble(rowData[1]));
				leftOutPoint.setPointY(Double.parseDouble(rowData[2]));
				leftOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftOutPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				leftOutPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftOutPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftInPoint = new MeasPoint();
				leftInPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				leftInPoint.setPointX(Double.parseDouble(rowData[1]));
				leftInPoint.setPointY(Double.parseDouble(rowData[2]));
				leftInPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftInPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				leftInPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftInPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[1] + "-" +  POINT_TYPE[4] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightInPoint = new MeasPoint();
				rightInPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				rightInPoint.setPointX(Double.parseDouble(rowData[1]));
				rightInPoint.setPointY(Double.parseDouble(rowData[2]));
				rightInPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightInPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				rightInPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightInPoint);
				isGrabbedId = true;
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightOutPoint = new MeasPoint();
				rightOutPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" +  POINT_TYPE[8]);
				rightOutPoint.setPointX(Double.parseDouble(rowData[1]));
				rightOutPoint.setPointY(Double.parseDouble(rowData[2]));
				rightOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightOutPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				rightOutPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightOutPoint);
				isGrabbedId = true;
			}
			
			
			if( isGrabbedId ) {
				continue;
			}
			
			
			if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftPoint = new MeasPoint();
				leftPoint.setPointId(POINT_TYPE[0] + "-" + endPillarId + "-" + startPillarId + "-" +   POINT_TYPE[8]);
				leftPoint.setPointX(Double.parseDouble(rowData[1]));
				leftPoint.setPointY(Double.parseDouble(rowData[2]));
				leftPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[8]);
				leftPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftPoint);
			}
			else if( rowData[0].equalsIgnoreCase( POINT_TYPE[1] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightPoint = new MeasPoint();
				rightPoint.setPointId(POINT_TYPE[1] + "-" + endPillarId + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightPoint.setPointX(Double.parseDouble(rowData[1]));
				rightPoint.setPointY(Double.parseDouble(rowData[2]));
				rightPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[8]);
				rightPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightPoint);
			}
			else if( rowData[0].equalsIgnoreCase( POINT_TYPE[2] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint mediumPoint = new MeasPoint();
				mediumPoint.setPointId(POINT_TYPE[2] + "-" + endPillarId + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				mediumPoint.setPointX(Double.parseDouble(rowData[1]));
				mediumPoint.setPointY(Double.parseDouble(rowData[2]));
				mediumPoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumPoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[8]);
				mediumPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(mediumPoint);
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + 
			endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftOutPoint = new MeasPoint();
				leftOutPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				leftOutPoint.setPointX(Double.parseDouble(rowData[1]));
				leftOutPoint.setPointY(Double.parseDouble(rowData[2]));
				leftOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftOutPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				leftOutPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftOutPoint);
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + 
			endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint leftInPoint = new MeasPoint();
				leftInPoint.setPointId(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				leftInPoint.setPointX(Double.parseDouble(rowData[1]));
				leftInPoint.setPointY(Double.parseDouble(rowData[2]));
				leftInPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftInPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				leftInPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(leftInPoint);
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[1] + "-" +  POINT_TYPE[4] + "-" + 
			endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightInPoint = new MeasPoint();
				rightInPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightInPoint.setPointX(Double.parseDouble(rowData[1]));
				rightInPoint.setPointY(Double.parseDouble(rowData[2]));
				rightInPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightInPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + POINT_TYPE[8]);
				rightInPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightInPoint);
			}
			else if( rowData[0].equalsIgnoreCase(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + 
			endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8]) ) {
				MeasPoint rightOutPoint = new MeasPoint();
				rightOutPoint.setPointId(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + startPillarId + "-" +  POINT_TYPE[8]);
				rightOutPoint.setPointX(Double.parseDouble(rowData[1]));
				rightOutPoint.setPointY(Double.parseDouble(rowData[2]));
				rightOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightOutPoint.setPointType(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + POINT_TYPE[8]);
				rightOutPoint.setUpper(isTPSMeasure ? true : false);
				endPillarPointList.add(rightOutPoint);
			}	
		}
		
	}
	
	
	public int getMinElevation() {
	List<Double> minValueList = new ArrayList<>();
	double startPillarMinValue = startPillarPointList.stream().mapToDouble( e -> e.pointZ).min().orElse(0d);
	if( startPillarMinValue != 0) {
		minValueList.add(startPillarMinValue);
	}
	double endPillarMinValue = endPillarPointList.stream().mapToDouble( e -> e.pointZ).min().orElse(0d);
	if( endPillarMinValue != 0 ) {
		minValueList.add(endPillarMinValue);
	}
	double leftOutsideMinValue = leftOutsideWirePointList.stream().mapToDouble( e -> e.pointZ).min().orElse(0d);
	if( leftOutsideMinValue != 0 ) {
		minValueList.add(leftOutsideMinValue);
	}
	double leftInsideMinValue = leftInsideWirePointList.stream().mapToDouble( e -> e.pointZ).min().orElse(0d);
	if( leftInsideMinValue != 0 ) {
		minValueList.add(leftInsideMinValue);
	}
	double mediumMinValue = mediumWirePointList.stream().mapToDouble( e -> e.pointZ).min().orElse(0d);
	if( mediumMinValue != 0 ) {
		minValueList.add(mediumMinValue);
	}
	double rightInsideMinValue = rightInsideWirePointList.stream().mapToDouble( e -> e.pointZ).min().orElse(0d);
	if( rightInsideMinValue != 0 ) {
		minValueList.add(rightInsideMinValue);
	}
	double rightOutsideMinValue = rightOutsideWirePointList.stream().mapToDouble( e -> e.pointZ).min().orElse(0d);
	if( rightInsideMinValue != 0 ) {
		minValueList.add(rightOutsideMinValue);	
	}
	return (int) minValueList.stream().mapToDouble(v -> v).min().orElse(-1d);
	}
	
	
	
	public int getMaxElevation() {
		List<Double> maxValueList = new ArrayList<>();
		double startPillarMaxValue = startPillarPointList.stream().mapToDouble( e -> e.pointZ).max().orElse(0d);
		if( startPillarMaxValue != 0) {
			maxValueList.add(startPillarMaxValue);
		}
		double endPillarMaxValue = endPillarPointList.stream().mapToDouble( e -> e.pointZ).max().orElse(0d);
		if( endPillarMaxValue != 0 ) {
			maxValueList.add(endPillarMaxValue);
		}
		double leftOutsideMaxValue = leftOutsideWirePointList.stream().mapToDouble( e -> e.pointZ).max().orElse(0d);
		if( leftOutsideMaxValue != 0 ) {
			maxValueList.add(leftOutsideMaxValue);
		}
		double leftInsideMaxValue = leftInsideWirePointList.stream().mapToDouble( e -> e.pointZ).max().orElse(0d);
		if( leftInsideMaxValue != 0 ) {
			maxValueList.add(leftInsideMaxValue);
		}
		double mediumMaxValue = mediumWirePointList.stream().mapToDouble( e -> e.pointZ).max().orElse(0d);
		if( mediumMaxValue != 0 ) {
			maxValueList.add(mediumMaxValue);
		}
		double rightInsideMaxValue = rightInsideWirePointList.stream().mapToDouble( e -> e.pointZ).max().orElse(0d);
		if( rightInsideMaxValue != 0 ) {
			maxValueList.add(rightInsideMaxValue);
		}
		double rightOutsideMaxValue = rightOutsideWirePointList.stream().mapToDouble( e -> e.pointZ).max().orElse(0d);
		if( rightInsideMaxValue != 0 ) {
			maxValueList.add(rightOutsideMaxValue);	
		}
		return (int) (maxValueList.stream().mapToDouble(v -> v).max().orElse(-2d) + 1);
	}
	
	public double getLengthOfMainPillarSection() {
		
		double aveStartX = startPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[5]))
				.mapToDouble(a -> a.pointX)
				.average().orElse(0d);
		double aveStartY = startPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[5]))
				.mapToDouble(a -> a.pointY)
				.average().orElse(0d);
		double aveEndX = endPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[5]))
				.mapToDouble(a -> a.pointX)
				.average().orElse(0d);
		double aveEndY = endPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[5]))
				.mapToDouble(a -> a.pointY)
				.average().orElse(0d);
			
		return (int) (1000 * calcHorizonatlDistance(new MeasPoint(aveStartX, aveStartY), new MeasPoint(aveEndX, aveEndY))) / 1000.0;
	}
	
	private double calcHorizonatlDistance(MeasPoint pointA, MeasPoint pointB) {
		if( pointA == null || pointB == null ) {
			return 0.0;
		}
		return Math.sqrt(Math.pow(pointA.pointX - pointB.pointX, 2) + Math.pow(pointA.pointY - pointB.pointY, 2));
	}
	
	
	private MeasPoint getStartPillarPointById(String pointId, boolean isUpper) {
		
		MeasPoint point = null;
		
		for (MeasPoint measPoint : startPillarPointList) {
			if( pointId.equals(measPoint.pointId) && measPoint.isUpper == isUpper) {
				point = measPoint;
			}
		}
		
		return point;
		
	}
	
	private MeasPoint getEndPillarPointById(String pointId, boolean isUpper) {
		
		MeasPoint point = null;
		
		for (MeasPoint measPoint : endPillarPointList) {
			if( pointId.equals(measPoint.pointId) && measPoint.isUpper == isUpper) {
				point = measPoint;
			}
		}
			
		return point;	
	}
	
	private List<MeasPoint> getStartPillarLeftMeasPointList() {	
		MeasPoint startUpPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		if( startUpPoint == null ){
			startUpPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		}
		MeasPoint startDownPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		if( startDownPoint == null ){
			startDownPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		}
		return Arrays.asList(startDownPoint, startUpPoint);
	}
	
	private List<MeasPoint> getEndPillarLeftMeasPointList() {	
		MeasPoint endUpPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		if( endUpPoint == null ) {
			endUpPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		}
		MeasPoint endDownPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		if( endDownPoint == null ) {
			endDownPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		}
		return Arrays.asList(endDownPoint, endUpPoint);
	}
	
	private List<MeasPoint> getStartPillarRightMeasPointList() {	
		MeasPoint startUpPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		if( startUpPoint == null ) {
			startUpPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		}
		MeasPoint startDownPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		if( startDownPoint == null ) {
			startDownPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		}
		return Arrays.asList(startDownPoint, startUpPoint);
	}
	
	private List<MeasPoint> getEndPillarRightMeasPointList() {	
		MeasPoint endUpPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		if( endUpPoint == null ){
			endUpPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		}
		MeasPoint endDownPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		if( endDownPoint == null ){
			endDownPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		}
		return Arrays.asList(endDownPoint, endUpPoint);
	}
	
	private List<MeasPoint> getStartPillarLeftOutsideMeasPointList() {	
		MeasPoint startUpPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		if( startUpPoint == null ) {
			startUpPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + 
		startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		}
		MeasPoint startDownPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		if( startDownPoint == null ) {
			startDownPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + 
		startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		}
		return Arrays.asList(startDownPoint, startUpPoint);
	}
	
	private List<MeasPoint> getEndPillarLeftOutsideMeasPointList() {	
		MeasPoint endUpPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		if( endUpPoint == null ) {
			endUpPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" +
					endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		}
		MeasPoint endDownPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		if( endDownPoint == null ) {
			endDownPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" +
					endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		}		
		return Arrays.asList(endDownPoint, endUpPoint);
	}
	
	private List<MeasPoint> getStartPillarLeftInsideMeasPointList() {
		MeasPoint startUpPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		if( startUpPoint == null ) {
			startUpPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + 
		startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		}
		MeasPoint startDownPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		if( startDownPoint == null ) {
			startDownPoint = getStartPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + 
		startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		}
		
		return Arrays.asList(startDownPoint, startUpPoint);
	}
	
	private List<MeasPoint> getEndPillarLeftInsideMeasPointList() {
		MeasPoint endUpPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		if( endUpPoint == null ) {
			endUpPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" +
		endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		}
		MeasPoint endDownPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		if( endDownPoint == null ) {
			endDownPoint = getEndPillarPointById(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" +
		endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		}
		
		return Arrays.asList(endDownPoint, endUpPoint);
	}
	
   private List<MeasPoint> getStartPillarMediumMeasPointList() {
	   MeasPoint startUpPoint = getStartPillarPointById(POINT_TYPE[2] + "-" + startPillarId + "-" + POINT_TYPE[8], true);
	   if( startUpPoint == null ) {
		   startUpPoint = getStartPillarPointById(POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], true);
	   }
	   MeasPoint startDownPoint = getStartPillarPointById(POINT_TYPE[2] + "-" + startPillarId + "-" + POINT_TYPE[8], false);
	   if( startDownPoint == null ) {
		   startDownPoint = getStartPillarPointById(POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], false);
	   }
	   return Arrays.asList(startDownPoint, startUpPoint);
	}	
	
   private List<MeasPoint> getEndPillarMediumMeasPointList() {
	   MeasPoint endUpPoint = getEndPillarPointById(POINT_TYPE[2] + "-" + endPillarId + "-" + POINT_TYPE[8], true);
	   if( endUpPoint == null ) {
		   endUpPoint = getEndPillarPointById(POINT_TYPE[2] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], true);
	   }
	   MeasPoint endDownPoint = getEndPillarPointById(POINT_TYPE[2] + "-" + endPillarId + "-" + POINT_TYPE[8], false);
	   if( endDownPoint == null ) {
		   endDownPoint = getEndPillarPointById(POINT_TYPE[2] + "-" + endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], false);
	   }
	   return Arrays.asList(endDownPoint, endUpPoint);
   }
     
	private List<MeasPoint> getStartPillarRightInsideMeasPointList() {
		 MeasPoint startUpPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		 if( startUpPoint == null ) {
			 startUpPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + 
		 startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		 }
		 MeasPoint startDownPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		 if( startDownPoint == null ) {
			 startDownPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + 
		 startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		 }
		 return Arrays.asList(startDownPoint, startUpPoint);
	}
	
	private List<MeasPoint> getEndPillarRightInsideMeasPointList() {
		 MeasPoint endUpPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		 if( endUpPoint == null ) {
			 endUpPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + 
		 endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		 }
		 MeasPoint endDownPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		 if( endDownPoint == null ) {
			 endDownPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + 
		 endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		 }
		 return Arrays.asList(endDownPoint, endUpPoint);
		
	}

   private List<MeasPoint> getStartPillarRightOutsideMeasPointList() {
	   	 MeasPoint startUpPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[8], true);
	   	 if( startUpPoint == null ) {
	   		 startUpPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + 
	   	 startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], true);
	   	 }
	   	MeasPoint startDownPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[8], false);
	   	 if( startDownPoint == null ) {
	   		 startDownPoint = getStartPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + 
	   	 startPillarId + "-" + endPillarId + "-" + POINT_TYPE[8], false);
	   	 }
		 return Arrays.asList(startDownPoint, startUpPoint);
	}
   
   
   private List<MeasPoint> getEndPillarRightOutsideMeasPointList() {
	   MeasPoint endUpPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + POINT_TYPE[8], true);
		 if( endUpPoint == null ) {
			 endUpPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" +
		 endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], true);
		 }
		 MeasPoint endDownPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + endPillarId + "-" + POINT_TYPE[8], false);
		 if( endDownPoint == null ) {
			 endDownPoint = getEndPillarPointById(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" +
		 endPillarId + "-" + startPillarId + "-" + POINT_TYPE[8], false);
		 }
		 return Arrays.asList(endDownPoint, endUpPoint);
   }
   
 public List<MeasPoint> getStartPillarMeasPointList(){
	   
	   List<MeasPoint> measPointList = getStartPillarBaseTopData();
	   
	   for (MeasPoint measPoint : getStartPillarLeftMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getStartPillarMediumMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getStartPillarRightMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   
	   for (MeasPoint measPoint : getStartPillarLeftOutsideMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getStartPillarLeftInsideMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   
	   for (MeasPoint measPoint : getStartPillarRightInsideMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getStartPillarRightOutsideMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   
	   return measPointList;
   }
   
   private List<MeasPoint> getStartPillarBaseTopData(){
	   List<MeasPoint> startPillarData = new ArrayList<>();
	   double startBaseX = startPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[5]))
				.mapToDouble(a -> a.pointX)
				.average().orElse(0d);
		double startBaseY = startPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[5]))
				.mapToDouble(a -> a.pointY)
				.average().orElse(0d);
	   double startBaseZ = startPillarPointList.stream()
			   	.filter(b -> b.pointType.equals(POINT_TYPE[5]))
			   	.mapToDouble(b -> b.pointZ).average().orElse(0d);
	   double startTopX = startPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[6]))
				.mapToDouble(a -> a.pointX)
				.average().orElse(0d);
		double startTopY = startPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[6]))
				.mapToDouble(a -> a.pointY)
				.average().orElse(0d);
	   double startTopZ = startPillarPointList.stream()
					.filter(b -> b.pointType.equals(POINT_TYPE[6]))
					.mapToDouble(b -> b.pointZ).average().orElse(0d);
	   MeasPoint startBase = null;
	   if( startBaseX != 0 && startBaseY != 0 && startBaseZ != 0 ) {
		   startBase = new MeasPoint("StartBaseCenter", startBaseX, startBaseY, startBaseZ);
	   }
	   MeasPoint startTop = null;
	   if( startTopX != 0 && startTopY != 0 && startTopZ != 0 ) {
		   startTop = new MeasPoint("EndTopCenter", startTopX, startTopY, startTopZ);
	   }
	   startPillarData.add(startBase);
	   startPillarData.add(startTop);
	   return startPillarData;
   }
   
   private List<MeasPoint> getEndPillarBaseTopData(){
	   List<MeasPoint> endPillarData = new ArrayList<>();
	   double endBaseX = endPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[5]))
				.mapToDouble(a -> a.pointX)
				.average().orElse(0d);
		double endBaseY = endPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[5]))
				.mapToDouble(a -> a.pointY)
				.average().orElse(0d);
	   double endBaseZ = endPillarPointList.stream()
			   	.filter(b -> b.pointType.equals(POINT_TYPE[5]))
			   	.mapToDouble(b -> b.pointZ).average().orElse(0d);
	   double endTopX = endPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[6]))
				.mapToDouble(a -> a.pointX)
				.average().orElse(0d);
		double endTopY = endPillarPointList.stream()
				.filter(a -> a.pointType.equals(POINT_TYPE[6]))
				.mapToDouble(a -> a.pointY)
				.average().orElse(0d);
	   double endTopZ = endPillarPointList.stream()
					.filter(b -> b.pointType.equals(POINT_TYPE[6]))
					.mapToDouble(b -> b.pointZ).average().orElse(0d);
	   MeasPoint endBase = null;
	   if( endBaseX != 0 && endBaseY != 0 && endBaseZ != 0 ) {
		   endBase = new MeasPoint("EndBaseCenter", endBaseX, endBaseY, endBaseZ);
	   }
	   MeasPoint endTop = null;
	   if( endTopX != 0 && endTopY != 0 && endTopZ != 0 ) {
		   endTop = new MeasPoint("EndTopCenter", endTopX, endTopY, endTopZ);
	   }
	   endPillarData.add(endBase);
	   endPillarData.add(endTop);
	   return endPillarData;
   }
  
 public List<MeasPoint> getEndPillarMeasPointList(){
	   
	   List<MeasPoint> measPointList = getEndPillarBaseTopData();
	   
	   for (MeasPoint measPoint : getEndPillarLeftOutsideMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getEndPillarLeftInsideMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   
	   for (MeasPoint measPoint : getEndPillarRightInsideMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getEndPillarRightOutsideMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getEndPillarLeftMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getEndPillarMediumMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   for (MeasPoint measPoint : getEndPillarRightMeasPointList()) {
		   if( measPoint != null ) {
			   measPointList.add(measPoint);
		   }
	}
	   
	   return measPointList;
   }

 public List<Double> getLengthOfSectionBetweenPillars(){
	 
	 List<MeasPoint> leftStart = getStartPillarLeftMeasPointList();
	 List<MeasPoint> leftEnd = getEndPillarLeftMeasPointList();
	 List<MeasPoint> mediumStart = getStartPillarMediumMeasPointList();
	 List<MeasPoint> mediumEnd = getEndPillarMediumMeasPointList();
	 List<MeasPoint> rightStart = getStartPillarRightMeasPointList();
	 List<MeasPoint> rightEnd = getEndPillarRightMeasPointList();
	 List<MeasPoint> leftOutsideStart = getStartPillarLeftOutsideMeasPointList();
	 List<MeasPoint> leftOutsideEnd = getEndPillarLeftOutsideMeasPointList();
	 List<MeasPoint> leftInsideStart = getStartPillarLeftInsideMeasPointList();
	 List<MeasPoint> leftInsideEnd = getEndPillarLeftInsideMeasPointList();
	 List<MeasPoint> rightInsideStart = getStartPillarRightInsideMeasPointList();
	 List<MeasPoint> rightInsideEnd = getEndPillarRightInsideMeasPointList();
	 List<MeasPoint> rightOutsideStart = getStartPillarRightOutsideMeasPointList();
	 List<MeasPoint> rightOutsideEnd = getEndPillarRightOutsideMeasPointList(); 
	 return Arrays.asList(calcHorizonatlDistance(leftStart.get(1), leftEnd.get(1)),
			 			  calcHorizonatlDistance(mediumStart.get(1), mediumEnd.get(1)),
			 			  calcHorizonatlDistance(rightStart.get(1), rightEnd.get(1)),
			  calcHorizonatlDistance(leftOutsideStart.get(1), leftOutsideEnd.get(1)),
			  calcHorizonatlDistance(leftInsideStart.get(1), leftInsideEnd.get(1)),
			  calcHorizonatlDistance(rightInsideStart.get(1), rightInsideEnd.get(1)),
			  calcHorizonatlDistance(rightOutsideStart.get(1), rightOutsideEnd.get(1)));
 }
 
 
 public List<MeasWire> getMeasWirePointList(){
	 	List<MeasWire> measWire = new ArrayList<>();
	 	
	 if( !leftOutsideWirePointList.isEmpty() ) {
		 HashSet<String> idSet = getWireIdSet(leftOutsideWirePointList);
		 measWire.addAll(parseMeasWireData(idSet, 0));
			 }
	 if( !mediumWirePointList.isEmpty() ) {
		 HashSet<String> idSet = getWireIdSet(mediumWirePointList);
		 measWire.addAll(parseMeasWireData(idSet, 3));
		 }
	 if( !rightOutsideWirePointList.isEmpty() ) {
		 HashSet<String> idSet = getWireIdSet(rightOutsideWirePointList);
		 measWire.addAll(parseMeasWireData(idSet, 5));
		 }
	 if( !leftOutsideWirePointList.isEmpty() ) {
		 HashSet<String> idSet = getWireIdSet(leftOutsideWirePointList);
		 measWire.addAll(parseMeasWireData(idSet, 1));
		 }
	 if( !leftInsideWirePointList.isEmpty() ) {
		 HashSet<String> idSet = getWireIdSet(leftInsideWirePointList);
		 measWire = parseMeasWireData(idSet, 2);
	 }
	 if( !rightInsideWirePointList.isEmpty() ) {
		 HashSet<String> idSet = getWireIdSet(rightInsideWirePointList);
		 measWire.addAll(parseMeasWireData(idSet, 4));
	 }
	 if( !rightOutsideWirePointList.isEmpty() ) {
		 HashSet<String> idSet = getWireIdSet(rightOutsideWirePointList);
		 measWire.addAll(parseMeasWireData(idSet, 6));
	 }
	 
	 return measWire;
 }
 

 private HashSet<String> getWireIdSet(List<MeasPoint> wirePointList){
	 HashSet<String> idSet = new HashSet<>();
	 
	 for (MeasPoint wirePoint : wirePointList) {
		 
		 String[] idComponents = wirePoint.pointId.split("-");
		 
		 if( idComponents.length == 4 ) {
			 idSet.add(idComponents[3]);
		 }
		 else if( idComponents.length == 5 && startPillarId.equals(idComponents[1]) && !idComponents[4].equals(POINT_TYPE[9])
				 && !idComponents[4].equals(POINT_TYPE[10]) ) {
			 idSet.add(idComponents[3] + "-" + idComponents[4]);
		 }
		 else if( idComponents.length == 5 && startPillarId.equals(idComponents[2]) && !idComponents[4].equals(POINT_TYPE[9])
				 && !idComponents[4].equals(POINT_TYPE[10]) ) {
			 idSet.add(idComponents[3] + "-" + idComponents[4]);
		 }
		 else if( idComponents.length == 5 && idComponents[4].equals(POINT_TYPE[9])) {
			 idSet.add(idComponents[3]);
		 }
		 else if( idComponents.length == 5 && idComponents[4].equals(POINT_TYPE[10])) {
			 idSet.add(idComponents[3]);
		 }
		 else if( idComponents.length == 6 && startPillarId.equals(idComponents[2]) ) {
			 idSet.add(idComponents[4] + "-" + idComponents[5]);
		 }
		 else if( idComponents.length == 6 && startPillarId.equals(idComponents[1]) && idComponents[5].equals(POINT_TYPE[9])) {
			 idSet.add(idComponents[3] + "-" + idComponents[4]);
		 }
		 else if( idComponents.length == 6 && startPillarId.equals(idComponents[2]) &&  idComponents[5].equals(POINT_TYPE[9])) {
			 idSet.add(idComponents[4]);
		 }
		 else if( idComponents.length == 6 && startPillarId.equals(idComponents[1]) && idComponents[5].equals(POINT_TYPE[10])) {
			 idSet.add(idComponents[3] + "-" + idComponents[4]);
		}
		 else if( idComponents.length == 6 && startPillarId.equals(idComponents[2]) && idComponents[5].equals(POINT_TYPE[10])) {
			 idSet.add(idComponents[4]);
		 }
		 else if( idComponents.length == 7 && idComponents[6].equals(POINT_TYPE[9])) {
			 idSet.add(idComponents[4] + "-" + idComponents[5]);
		 }
		 else if( idComponents.length == 7 && idComponents[6].equals(POINT_TYPE[10])) {
			 idSet.add(idComponents[4] + "-" + idComponents[5]);
		 } 
	}
	 
	 return idSet;
 }
 
 private List<MeasWire> parseMeasWireData(HashSet<String> wireIdSet, int wireDataType){
	 
	 List<MeasWire> measWireList = new ArrayList<>();
	 
	 if (wireDataType == 0) {
		 		 
		for (String wireId : wireIdSet) {
			MeasWire measWire = new MeasWire();
			measWire.setWireType(wireDataType);
			measWire.setWireId(wireId);
			for (MeasPoint measPoint : leftOutsideWirePointList){
			
			 if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && !measPoint.isUpper) {
				 measWire.setGroundPoint(measPoint);
			 } 
			 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId + "-" + POINT_TYPE[9]) && 
					 measPoint.pointId.endsWith(POINT_TYPE[9])) {
				 measWire.setVEZPoint(measPoint);
			 }
			 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId + "-" + POINT_TYPE[10]) && 
					 measPoint.pointId.endsWith(POINT_TYPE[10])) {
				 measWire.setSDRPoint(measPoint);
			 }
			 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && measPoint.isUpper) {
				 measWire.setSDRPoint(measPoint);
			 } 		 
		}		
			
			
			MeasPoint grabPoint = getStartPillarLeftMeasPointList().get(1);
			if( grabPoint != null ) {
			measWire.setDistanceOfWire(grabPoint);
			}
			if( measWire.getGroundPoint() == null ) {
				measWire.setWireId(POINT_TYPE[0] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[7]);
				measWire.calcHalfPillarSectionGroundPoint(getStartPillarLeftMeasPointList().get(0), 
						getEndPillarLeftMeasPointList().get(0));
			}
			if( measWire.getSDRPoint() != null ) {
				measWireList.add(measWire);
			}
	}
}	else if (wireDataType == 3) {
	 
	for (String wireId : wireIdSet) {
		MeasWire measWire = new MeasWire();
		measWire.setWireType(wireDataType);
		measWire.setWireId(wireId);
		for (MeasPoint measPoint : mediumWirePointList){
		
		 if( measPoint.pointId.equals(POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && !measPoint.isUpper ) {
			 measWire.setGroundPoint(measPoint);
		 }
		 else if( measPoint.pointId.equals(POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId + "-" + POINT_TYPE[9] ) && 
				 measPoint.pointId.endsWith(POINT_TYPE[9]) ) {
			 measWire.setVEZPoint(measPoint);
		 }
		 else if( measPoint.pointId.equals(POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId + "-" + POINT_TYPE[10]) && 
				 measPoint.pointId.endsWith(POINT_TYPE[10]) ) {
			 measWire.setSDRPoint(measPoint);
		 }
		 else if( measPoint.pointId.equals(POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && measPoint.isUpper ) {
			 measWire.setSDRPoint(measPoint);
		 } 		 
	}
		MeasPoint grabPoint = getStartPillarMediumMeasPointList().get(1);
		if( grabPoint != null ) {
		measWire.setDistanceOfWire(grabPoint);
		}
		if( measWire.getGroundPoint() == null ) {
			measWire.setWireId(POINT_TYPE[2] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[7]);
			measWire.calcHalfPillarSectionGroundPoint(getStartPillarMediumMeasPointList().get(0), 
					getEndPillarMediumMeasPointList().get(0));
		}
		if( measWire.getSDRPoint() != null ) {
			measWireList.add(measWire);
		}
	}
}
		else if (wireDataType == 6) {
			 
			for (String wireId : wireIdSet) {
				MeasWire measWire = new MeasWire();
				measWire.setWireType(wireDataType);
				measWire.setWireId(wireId);
				for (MeasPoint measPoint : rightOutsideWirePointList){
				
				 if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && !measPoint.isUpper ) {
					 measWire.setGroundPoint(measPoint);
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId + "-" + POINT_TYPE[9]) && 
						 measPoint.pointId.endsWith(POINT_TYPE[9]) ) {
					 measWire.setVEZPoint(measPoint);
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId + "-" + POINT_TYPE[10]) && 
						 measPoint.pointId.endsWith(POINT_TYPE[10]) ) {
					 measWire.setSDRPoint(measPoint);
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && measPoint.isUpper ) {
					 measWire.setSDRPoint(measPoint);
				 } 		 
			}
				MeasPoint grabPoint = getStartPillarRightMeasPointList().get(1);
				if( grabPoint != null ) {
				measWire.setDistanceOfWire(grabPoint);
				}
				if( measWire.getGroundPoint() == null ) {
					measWire.setWireId(POINT_TYPE[1] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[7]);
					measWire.calcHalfPillarSectionGroundPoint(getStartPillarRightMeasPointList().get(0), 
							getEndPillarRightMeasPointList().get(0));
				}
				if( measWire.getSDRPoint() != null ) {
					measWireList.add(measWire);
				}
	}
}		
		else if (  wireDataType == 1 ) {
			 
			for (String wireId : wireIdSet) {
				MeasWire measWire = new MeasWire();
				measWire.setWireType(wireDataType);
				measWire.setWireId(wireId);
				for (MeasPoint measPoint : leftOutsideWirePointList){
				
				 if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && 
						 !measPoint.isUpper ) {
					 measWire.setGroundPoint(measPoint); 
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId
						 + "-" + POINT_TYPE[9]) && 
						 measPoint.pointId.endsWith(POINT_TYPE[9]) ) {
					 measWire.setVEZPoint(measPoint);
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId
						 + "-" + POINT_TYPE[10]) && 
						 measPoint.pointId.endsWith(POINT_TYPE[10]) ) {
					 measWire.setSDRPoint(measPoint);
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && 
						 measPoint.isUpper ) {
					 measWire.setSDRPoint(measPoint);
				 } 		 
			}
				MeasPoint grabPoint = getStartPillarLeftOutsideMeasPointList().get(1);
				if( grabPoint != null ) {
				measWire.setDistanceOfWire(grabPoint);
				}
				if( measWire.getGroundPoint() == null ) {
					measWire.setWireId(POINT_TYPE[0] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[7]);
					measWire.calcHalfPillarSectionGroundPoint(getStartPillarLeftOutsideMeasPointList().get(0), 
							getEndPillarLeftOutsideMeasPointList().get(0));
				}
				if( measWire.getSDRPoint() != null ) {
					measWireList.add(measWire);
				}
	}
}
		else if (wireDataType == 2) {
			 
			for (String wireId : wireIdSet) {
				MeasWire measWire = new MeasWire();
				measWire.setWireType(wireDataType);
				measWire.setWireId(wireId);
				for (MeasPoint measPoint : leftInsideWirePointList){
				
				 if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && 
						 !measPoint.isUpper ) {
					 measWire.setGroundPoint(measPoint);
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId
						 + "-" + POINT_TYPE[9]) && 
						 measPoint.pointId.endsWith(POINT_TYPE[9]) ) {
					 measWire.setVEZPoint(measPoint);
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId
						 + "-" + POINT_TYPE[10]) && 
						 measPoint.pointId.endsWith(POINT_TYPE[10]) ) {
					 measWire.setSDRPoint(measPoint);
				 }
				 else if( measPoint.pointId.equals(POINT_TYPE[0] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && 
						 measPoint.isUpper ) {
					 measWire.setSDRPoint(measPoint);
				 } 		 
			}
				MeasPoint grabPoint = getStartPillarLeftInsideMeasPointList().get(1);
				if( grabPoint != null ) {
				measWire.setDistanceOfWire(grabPoint);
				}
				if( measWire.getGroundPoint() == null ) {
					measWire.setWireId(POINT_TYPE[0] + "-" + POINT_TYPE[4]  + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[7]);
					measWire.calcHalfPillarSectionGroundPoint(getStartPillarLeftInsideMeasPointList().get(0), 
							getEndPillarLeftInsideMeasPointList().get(0));
				}
				if( measWire.getSDRPoint() != null ) {
					measWireList.add(measWire);
				}
	}
}	
	else if (wireDataType == 4) {
	 
	for (String wireId : wireIdSet) {
		MeasWire measWire = new MeasWire();
		measWire.setWireType(wireDataType);
		measWire.setWireId(wireId);
		for (MeasPoint measPoint : rightInsideWirePointList){
		
		 if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && 
				 !measPoint.isUpper ) {
			 measWire.setGroundPoint(measPoint);
		 }
		 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId
				 + "-" + POINT_TYPE[9]) && 
				 measPoint.pointId.endsWith(POINT_TYPE[9]) ) {
			 measWire.setVEZPoint(measPoint);
		 }
		 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId
				 + "-" + POINT_TYPE[10]) && 
				 measPoint.pointId.endsWith(POINT_TYPE[10]) ) {
			 measWire.setSDRPoint(measPoint);
		 }
		 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + POINT_TYPE[4] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && 
				 measPoint.isUpper ) {
			 measWire.setSDRPoint(measPoint);
		 } 		 
	}
		MeasPoint grabPoint = getStartPillarRightInsideMeasPointList().get(1);
		if( grabPoint != null ) {
		measWire.setDistanceOfWire(grabPoint);
		}
		if( measWire.getGroundPoint() == null ) {
			measWire.setWireId(POINT_TYPE[1] + "-" + POINT_TYPE[4]  + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[7]);
			measWire.calcHalfPillarSectionGroundPoint(getStartPillarRightInsideMeasPointList().get(0), 
					getEndPillarRightInsideMeasPointList().get(0));
		}
		if( measWire.getSDRPoint() != null ) {
			measWireList.add(measWire);
		}
	}
}
	else if (wireDataType == 5) {
		 
		for (String wireId : wireIdSet) {
			MeasWire measWire = new MeasWire();
			measWire.setWireType(wireDataType);
			measWire.setWireId(wireId);
			for (MeasPoint measPoint : rightOutsideWirePointList){
			
			 if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && 
					 !measPoint.isUpper ) {
				 measWire.setGroundPoint(measPoint);
			 }
			 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId
					 + "-" + POINT_TYPE[9]) && 
					 measPoint.pointId.endsWith(POINT_TYPE[9]) ) {
				 measWire.setVEZPoint(measPoint);
			 }
			 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId
					 + "-" + POINT_TYPE[10]) && 
					 measPoint.pointId.endsWith(POINT_TYPE[10]) ) {
				 measWire.setSDRPoint(measPoint);
			 }
			 else if( measPoint.pointId.equals(POINT_TYPE[1] + "-" + POINT_TYPE[3] + "-" + startPillarId + "-" + endPillarId + "-" +  wireId) && 
					 measPoint.isUpper ) {
				 measWire.setSDRPoint(measPoint);
			 } 		 
		}
			MeasPoint grabPoint = getStartPillarRightOutsideMeasPointList().get(1);
			if( grabPoint != null ) {
			measWire.setDistanceOfWire(grabPoint);
			}
			if( measWire.getGroundPoint() == null ) {
				measWire.setWireId(POINT_TYPE[1] + "-" + POINT_TYPE[3]  + "-" + startPillarId + "-" + endPillarId + "-" + POINT_TYPE[7]);
				measWire.calcHalfPillarSectionGroundPoint(getStartPillarRightOutsideMeasPointList().get(0), 
						getEndPillarRightOutsideMeasPointList().get(0));
			}
			if( measWire.getSDRPoint() != null ) {
				measWireList.add(measWire);
			}
		}
	}
	 
	 return measWireList;
 }
 
 public double getMainLineAzimuth() {
	 MeasPoint startPillarBaseCenter = getStartPillarBaseTopData().get(0);
	 MeasPoint endPillarBaseCenter = getEndPillarBaseTopData().get(0);
	 if( startPillarBaseCenter == null || endPillarBaseCenter == null ) {
		 return 0;
	 }
	 return new AzimuthAndDistance(startPillarBaseCenter, endPillarBaseCenter).calcAzimuth();
 }
 
 public List<MeasPoint> getMeasGroundPointList(){
	 MeasPoint startPillarBaseCenter = getStartPillarBaseTopData().get(0);
	 MeasPoint endPillarBaseCenter = getEndPillarBaseTopData().get(0);
	 List<MeasPoint> groundMeasPointList = new ArrayList<>();
	 if( startPillarBaseCenter == null || endPillarBaseCenter == null ) {
		 return groundMeasPointList;
	 }
	 groundMeasPointList.add(new MeasPoint(startPillarBaseCenter.pointId, 0.0, startPillarBaseCenter.pointZ));
	 AzimuthAndDistance mainLineData = new AzimuthAndDistance(startPillarBaseCenter, endPillarBaseCenter);
	 for (String measDataRow : measDataList) {
		String[] rowData = measDataRow.split(",");
		if( rowData.length == 1 ) {
			rowData = measDataRow.split(";");
		}
		if( rowData[rowData.length - 1].equalsIgnoreCase(POINT_TYPE[11]) ) {
			
			MeasPoint measedGroundPoint = new MeasPoint("MeasGroundPoint", 
					Double.parseDouble(rowData[1]), Double.parseDouble(rowData[2]), Double.parseDouble(rowData[3]));
			AzimuthAndDistance groundPointData = new AzimuthAndDistance(startPillarBaseCenter, measedGroundPoint);
			double alfa = mainLineData.calcAzimuth() - groundPointData.calcAzimuth();
			double distance = Math.cos(alfa) * groundPointData.calcDistance();
			if( 0 < distance && distance < mainLineData.calcDistance() ) {
				groundMeasPointList.add(new MeasPoint(rowData[0], distance, measedGroundPoint.pointZ));
			}	
		}
	}
	 groundMeasPointList.add(new MeasPoint(endPillarBaseCenter.pointId, 
			 calcHorizonatlDistance(startPillarBaseCenter, endPillarBaseCenter), endPillarBaseCenter.pointZ));
	 Collections.sort(groundMeasPointList);
	 return groundMeasPointList;
 }
 
 public List<Double> getAbscissaForWireLineProjection(){
	 List<Double> diffs = new ArrayList<>();
	 MeasPoint leftStart = getStartPillarLeftMeasPointList().get(1);
	 MeasPoint mediumStart = getStartPillarMediumMeasPointList().get(1);
	 MeasPoint rightStart = getStartPillarRightMeasPointList().get(1);
	 MeasPoint leftOutsideStart = getStartPillarLeftOutsideMeasPointList().get(1);
	 MeasPoint leftInsideStart = getStartPillarLeftInsideMeasPointList().get(1);
	 MeasPoint rightInsideStart = getStartPillarRightInsideMeasPointList().get(1);
	 MeasPoint rightOutsideStart = getStartPillarRightOutsideMeasPointList().get(1);
	 MeasPoint startPillarBaseCenter = getStartPillarBaseTopData().get(0);
	 Arrays.asList(leftStart, mediumStart, rightStart, leftOutsideStart, leftInsideStart, rightInsideStart, rightOutsideStart)
	 .forEach(measPoint -> {
	if( measPoint != null ) {
	 AzimuthAndDistance pointData = new AzimuthAndDistance(startPillarBaseCenter, measPoint);
	 double alfa = getMainLineAzimuth() - pointData.calcAzimuth();
	 diffs.add(Math.cos(alfa) * pointData.calcDistance());
}
	else {
		diffs.add(0.0);
	}
}); 
	 return diffs;
 }
 
}
