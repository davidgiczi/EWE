package hu.mvmxpert.david.giczi.electricwireeditor.service;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import hu.mvmxpert.david.giczi.electricwireeditor.controller.HomeController;
import hu.mvmxpert.david.giczi.electricwireeditor.model.LineData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.PillarData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.SavedWirePoint;
import hu.mvmxpert.david.giczi.electricwireeditor.model.TextData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WireData;
import hu.mvmxpert.david.giczi.electricwireeditor.model.WirePoint;

public class FileProcess {
	
	public static String FOLDER_PATH;
	private ArchivFileBuilder archivFileBuilder;
	
	public void setArchivFileBuilder(ArchivFileBuilder archivFileBuilder) {
		this.archivFileBuilder = archivFileBuilder;
	}

	public void setFolder() {
		JFileChooser jfc = new JFileChooser(){
		    
			private static final long serialVersionUID = 1L;

			@Override
		    protected JDialog createDialog( Component parent ) throws HeadlessException {
		        JDialog dialog = super.createDialog( parent );
		        dialog.setAlwaysOnTop(true);
		        try {
					byte[] imageSource = this.getClass()
							.getResourceAsStream("/logo/MVM.jpg").readAllBytes();
					dialog.setIconImage( new ImageIcon(imageSource).getImage() );
				} catch (IOException e) {
					e.printStackTrace();
				} 
		        return dialog;
		    }
		};
		jfc.setCurrentDirectory(FOLDER_PATH == null ? FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
		jfc.setDialogTitle("V??lassz ment??si mapp??t");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			FOLDER_PATH = selectedFile.getAbsolutePath();
		}
	}
	
	public List<String> openProject() {
		JFileChooser jfc = new JFileChooser(){
		    
			private static final long serialVersionUID = 1L;

			@Override
		    protected JDialog createDialog( Component parent ) throws HeadlessException {
		        JDialog dialog = super.createDialog( parent );
		        dialog.setAlwaysOnTop(true);
		        try {
					byte[] imageSource = this.getClass()
							.getResourceAsStream("/logo/MVM.jpg").readAllBytes();
					dialog.setIconImage( new ImageIcon(imageSource).getImage() );
				} catch (IOException e) {
					e.printStackTrace();
				} 
		        return dialog;
		    }
		};
		jfc.setCurrentDirectory(FOLDER_PATH == null ? FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
		jfc.setDialogTitle("V??lassz projekt f??jlt");
		jfc.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "*.ewe f??jlok";
			}
			
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".ewe");
			}
		});
		
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			FOLDER_PATH = selectedFile.getParent();
			HomeController.PROJECT_NAME = selectedFile.getName().substring(0, selectedFile.getName().indexOf('.'));
		}
		
		return getProjectFileData();
	}
	
	private List<String> getProjectFileData(){
	
		List<String> projectData = new ArrayList<>();
		
		if(HomeController.PROJECT_NAME == null || FOLDER_PATH == null)
			return projectData;
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME+ ".ewe");
		
		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {
			
				String row = reader.readLine();
				while( row != null ) {
					projectData.add(row);
					row = reader.readLine();
				}
		}
			catch (IOException e) {
			HomeController.getWarningAlert("F??jl megnyit??sa sikertelen", "\"" + file.getName() + "\" projekt f??jl megnyit??sa sikertelen.");
			} 
		
		return projectData;
	}
	
	public static List<String> getWireTypeFileData(){
		
		List<String> wireTypeData = new ArrayList<>();
		
		File file = new File(ClassLoader.getSystemResource("./wiretype/sodronyok.txt").getFile());
		
		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {
			
				String row = reader.readLine();
				while( row != null ) {
					wireTypeData.add(row);
					row = reader.readLine();
				}
		}
			catch (IOException e) {
			HomeController.getWarningAlert("F??jl megnyit??sa sikertelen", "\"" + file.getName() + "\" projekt f??jl megnyit??sa sikertelen.");
			} 
		
		return wireTypeData;
	}
	
	
	public boolean isProjectFileExist() {
		
		if( FOLDER_PATH == null )
			return false;
		
		String[] ewd = new File(FOLDER_PATH).list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".ewe");
			}
		});

		return Arrays.asList(ewd).contains(HomeController.PROJECT_NAME + ".ewe");
	}
	
	public boolean saveProject() {
		
		if( FOLDER_PATH == null || HomeController.PROJECT_NAME == null) {
			return false;
		}
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + ".ewe");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			writer.write(archivFileBuilder.getSystemData().getDrawingSystemData());
			writer.newLine();
			for (PillarData pillarData : archivFileBuilder.getPillarData()) {
			writer.write(pillarData.getPillarData());
			writer.newLine();
			writer.write(pillarData.getPillarTexts());
			writer.newLine();
			}
			for (WireData wireData : archivFileBuilder.getWireData()) {
				writer.write(wireData.getWireData());
				writer.newLine();
				writer.write(wireData.getWireTexts());
				writer.newLine();
				}
			for(LineData lineData : archivFileBuilder.getLineData()) {
				writer.write(lineData.getLineData());
				writer.newLine();
			}
			for(TextData textData : archivFileBuilder.getTextData()) {
				if( textData.getId() != -1 ) {
				writer.write(textData.getTextData());
				writer.newLine();
			}
		}
			
		} catch (IOException e) {
			HomeController.getWarningAlert("F??jl ment??se sikertelen", "\"" + file.getName() + "\" projekt f??jl ment??se sikertelen.");
			return false;
		} 
		
		return true;
	}
	
	public void save2DWirePointsInAutoCadFormat(List<SavedWirePoint> points, String type) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_" + type +  "_sodrony_2D" + ".scr");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			writer.write("_MULTIPLE _POINT");
			writer.newLine();
			for (SavedWirePoint savedWirePoint : points) {
				writer.write(savedWirePoint.get2DCoordDataWithoutID());
				writer.newLine();
			}
			
		} catch (IOException e) {
			HomeController.getWarningAlert("F??jl ment??se sikertelen", "\"" + file.getName() + "\" projekt f??jl ment??se sikertelen.");
		} 
	}
	
	public void save3DWirePointsInAutoCadFormat(List<SavedWirePoint> points, String type) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_" + type + "_sodrony_3D" + ".scr");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			writer.write("_MULTIPLE _POINT");
			writer.newLine();
			for (SavedWirePoint savedWirePoint : points) {
				writer.write(savedWirePoint.get3DCoordDataWithoutID());
				writer.newLine();
			}
			
		} catch (IOException e) {
			HomeController.getWarningAlert("F??jl ment??se sikertelen", "\"" + file.getName() + "\" projekt f??jl ment??se sikertelen.");
		} 
	}
	
	public void save2DWirePointsInTextFormat(List<SavedWirePoint> points) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_2D" + ".txt");
	
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8, true))) {

			for (SavedWirePoint savedWirePoint : points) {
				writer.write(savedWirePoint.get2DCoordDataWithID());
				writer.newLine();
			}
			
		} catch (IOException e) {
			HomeController.getWarningAlert("F??jl ment??se sikertelen", "\"" + file.getName() + "\" projekt f??jl ment??se sikertelen.");
		} 
	}
	
	public void save3DWirePointsInTextFormat(List<SavedWirePoint> points) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_3D" + ".txt");
	
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8, true))) {
			
			for (SavedWirePoint savedWirePoint : points) {
				writer.write(savedWirePoint.get3DCoordDataWithID());
				writer.newLine();
			}
			
		} catch (IOException e) {
			HomeController.getWarningAlert("F??jl ment??se sikertelen", "\"" + file.getName() + "\" projekt f??jl ment??se sikertelen.");
		} 
	}
	
	public void saveCalulatedWirePointsInTextFormat(List<WirePoint> wirePoints, String type) {
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_" + type  + "_sodrony_pontok.txt");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			for (int i = 1; i <= wirePoints.size(); i++) {
				writer.write(i + " " + wirePoints.get(i - 1).getWirePoint());
				writer.newLine();
			}
			
		} catch (IOException e) {
			HomeController.getWarningAlert("F??jl ment??se sikertelen", "\"" + file.getName() + "\" projekt f??jl ment??se sikertelen.");
		} 
	}
		
}
