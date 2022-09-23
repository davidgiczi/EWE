package hu.mvmxpert.david.giczi.electricwiredisplayer.service;

import java.util.ArrayList;
import java.util.List;

import hu.mvmxpert.david.giczi.electricwiredisplayer.model.DrawingSystemData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.LineData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.PillarData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.TextData;
import hu.mvmxpert.david.giczi.electricwiredisplayer.model.WireData;

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
	
	public static void addPillar(PillarData pillar) {
		
	}
	
	public static void removePillar(int id) {
		
	}
	
	public static void addWire(WireData wire) {
		
	}
	
	public static void removeWire(int id) {
		
	}
	
	public static void addText(TextData text) {
		
	}
	
	public static void removeText(int id) {
		
	}
	
	public static void addLine(LineData line) {
		
	}
	
	public static void removeLine(int line) {
		
	}	
	
	
	
	
	
}
