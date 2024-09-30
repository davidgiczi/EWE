package hu.mvmxpert.david.giczi.electricwireeditor.service;

import java.util.ArrayList;
import java.util.List;
import hu.mvmxpert.david.giczi.electricwireeditor.model.MeasPoint;

public class PillarSectionDrawingAutomatically {
	
	
	private String startPillarId;
	private String endPillarId;
	private List<String> measDataList;
	public List<MeasPoint> startPillarPointList;
	public List<MeasPoint> endPillarPointList;
	public List<MeasPoint> leftOutSideWirePointList;
	public List<MeasPoint> leftInsideWirePointList;
	public List<MeasPoint> rightOutSideWirePointList;
	public List<MeasPoint> rightInsideWirePointList;
	private static String[] POINT_TYPE = 
		{"BAL", "JOBB", "KOZEP", "BAL-KULSO", "BAL-BELSO", "JOBB-BELSO", "JOBB-KULSO", "ALAP", "CSUCS", "FEL", "BEF", "VEZ", "SDR"};
	

	public PillarSectionDrawingAutomatically(String startPillarId, String endPillarId, List<String> measDataList) {
		this.startPillarId = startPillarId.toUpperCase();
		this.endPillarId = endPillarId.toUpperCase();
		this.measDataList = measDataList;
		startPillarPointList = new ArrayList<>();
		parseStartPillarPointListData();
		startPillarPointList.forEach(System.out::println);
	}
	
	private void parseStartPillarPointListData() {
		for( String measDataRow : measDataList ) {
			
			boolean isTPSMeasure = false;
			
			String[] rowData = measDataRow.split(",");
			
			if( rowData.length == 1 ) {
				rowData = measDataRow.split(";");
				isTPSMeasure = true;
			}
			
			if( rowData.length == 1 ) {
				continue;
			}
					
			if( rowData[0].toUpperCase().startsWith(startPillarId) && 
					rowData[rowData.length - 1].equalsIgnoreCase(POINT_TYPE[7])) {
				MeasPoint basePoint = new MeasPoint();
				basePoint.setPointId(rowData[0].toUpperCase());
				basePoint.setPointX(Double.parseDouble(rowData[1]));
				basePoint.setPointY(Double.parseDouble(rowData[2]));
				basePoint.setPointZ(Double.parseDouble(rowData[3]));
				basePoint.setPointType(POINT_TYPE[7]);
				startPillarPointList.add(basePoint);
			}
			else if( rowData[0].toUpperCase().startsWith(startPillarId) && 
					rowData[rowData.length - 1].equalsIgnoreCase(POINT_TYPE[8])) {
				MeasPoint topPoint = new MeasPoint();
				topPoint.setPointId(rowData[0].toUpperCase());
				topPoint.setPointX(Double.parseDouble(rowData[1]));
				topPoint.setPointY(Double.parseDouble(rowData[2]));
				topPoint.setPointZ(Double.parseDouble(rowData[3]));
				topPoint.setPointType(POINT_TYPE[8]);
				startPillarPointList.add(topPoint);
			}
			else if( rowData[0].toUpperCase().startsWith(POINT_TYPE[0] + "-" + startPillarId + "-" + POINT_TYPE[10]) ) {
				MeasPoint leftPoint = new MeasPoint();
				leftPoint.setPointId(POINT_TYPE[0] + "-" + startPillarId + "-" +  POINT_TYPE[10]);
				leftPoint.setPointX(Double.parseDouble(rowData[1]));
				leftPoint.setPointY(Double.parseDouble(rowData[2]));
				leftPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[10]);
				leftPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[1] + "-" + startPillarId + "-" + POINT_TYPE[10]) ) {
				MeasPoint rightPoint = new MeasPoint();
				rightPoint.setPointId(POINT_TYPE[1] + "-" + startPillarId + "-" +  POINT_TYPE[10]);
				rightPoint.setPointX(Double.parseDouble(rowData[1]));
				rightPoint.setPointY(Double.parseDouble(rowData[2]));
				rightPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightPoint.setPointType(POINT_TYPE[0] + "-" + POINT_TYPE[10]);
				rightPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[2] + "-" + startPillarId + "-" + POINT_TYPE[10]) ) {
				MeasPoint mediumPoint = new MeasPoint();
				mediumPoint.setPointId(POINT_TYPE[2] + "-" + startPillarId + "-" +  POINT_TYPE[10]);
				mediumPoint.setPointX(Double.parseDouble(rowData[1]));
				mediumPoint.setPointY(Double.parseDouble(rowData[2]));
				mediumPoint.setPointZ(Double.parseDouble(rowData[3]));
				mediumPoint.setPointType(POINT_TYPE[2] + "-" + POINT_TYPE[2]);
				mediumPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(mediumPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[3] + "-" + startPillarId + "-" + POINT_TYPE[10]) ) {
				MeasPoint leftOutPoint = new MeasPoint();
				leftOutPoint.setPointId(POINT_TYPE[3] + "-" + startPillarId + "-" +  POINT_TYPE[10]);
				leftOutPoint.setPointX(Double.parseDouble(rowData[1]));
				leftOutPoint.setPointY(Double.parseDouble(rowData[2]));
				leftOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftOutPoint.setPointType(POINT_TYPE[3] + "-" + POINT_TYPE[10]);
				leftOutPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftOutPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[4] + "-" + startPillarId + "-" + POINT_TYPE[10]) ) {
				MeasPoint leftInPoint = new MeasPoint();
				leftInPoint.setPointId(POINT_TYPE[4] + "-" + startPillarId + "-" +  POINT_TYPE[10]);
				leftInPoint.setPointX(Double.parseDouble(rowData[1]));
				leftInPoint.setPointY(Double.parseDouble(rowData[2]));
				leftInPoint.setPointZ(Double.parseDouble(rowData[3]));
				leftInPoint.setPointType(POINT_TYPE[4] + "-" + POINT_TYPE[10]);
				leftInPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(leftInPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[5] + "-" + startPillarId + "-" + POINT_TYPE[10]) ) {
				MeasPoint rightInPoint = new MeasPoint();
				rightInPoint.setPointId(POINT_TYPE[5] + "-" + startPillarId + "-" +  POINT_TYPE[10]);
				rightInPoint.setPointX(Double.parseDouble(rowData[1]));
				rightInPoint.setPointY(Double.parseDouble(rowData[2]));
				rightInPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightInPoint.setPointType(POINT_TYPE[5] + "-" + POINT_TYPE[10]);
				rightInPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightInPoint);
			}
			else if( rowData[0].toUpperCase().startsWith( POINT_TYPE[6] + "-" + startPillarId + "-" + POINT_TYPE[10]) ) {
				MeasPoint rightOutPoint = new MeasPoint();
				rightOutPoint.setPointId(POINT_TYPE[6] + "-" + startPillarId + "-" +  POINT_TYPE[10]);
				rightOutPoint.setPointX(Double.parseDouble(rowData[1]));
				rightOutPoint.setPointY(Double.parseDouble(rowData[2]));
				rightOutPoint.setPointZ(Double.parseDouble(rowData[3]));
				rightOutPoint.setPointType(POINT_TYPE[6] + "-" + POINT_TYPE[10]);
				rightOutPoint.setUpper(isTPSMeasure ? true : false);
				startPillarPointList.add(rightOutPoint);
			}
				
		}
		
	}
	

	

}
