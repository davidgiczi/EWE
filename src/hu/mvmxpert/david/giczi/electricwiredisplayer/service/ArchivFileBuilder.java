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
	public static DrawingSystemData systemData;
	public static List<PillarData> pillarData;
	public static List<WireData> wireData;
	public static List<TextData> textData;
	public static List<LineData> lineData;
	
	public static void init() {
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
	
	public static void addPillar() {
		
	}
	
	public static void removePillar() {
		
	}
	
	public static void addWire() {
		
	}
	
	public static void removeWire() {
		
	}
	
	public static void addText() {
		
	}
	
	public static void removeText() {
		
	}
	
	public static void addLine() {
		
	}
	
	public static void removeLine() {
		
	}	
	
	
	
	
	
}
