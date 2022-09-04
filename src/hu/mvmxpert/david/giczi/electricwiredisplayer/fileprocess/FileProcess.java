package hu.mvmxpert.david.giczi.electricwiredisplayer.fileprocess;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

public class FileProcess {
	
	public static String FOLDER_PATH;
	
	public static void setFolder() {
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
		jfc.setDialogTitle("Válassz mentési mappát a projektnek.");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			FOLDER_PATH = selectedFile.getAbsolutePath();
		}
		else {
			FOLDER_PATH = null;
		}
	}
	
	public static String setProject() {
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
		String projectName = null;
		jfc.setCurrentDirectory(FOLDER_PATH == null ? FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
		jfc.setDialogTitle("Válassz projekt fájlt.");
		jfc.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "*.ewd fájlok";
			}
			
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".ewd");
			}
		});
		
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			FOLDER_PATH = selectedFile.getParent();
			
				projectName = selectedFile.getName().substring(0, selectedFile.getName().indexOf('.'));
		}
		
		return projectName;
	}
	
	public static boolean isProjectFileExist() {
		
		String[] pcc = new File(FOLDER_PATH).list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".ewd");
			}
		});

		return Arrays.asList(pcc).contains(null);
	}
	
	
}
