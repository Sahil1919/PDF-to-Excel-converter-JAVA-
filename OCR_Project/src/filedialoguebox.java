import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class filedialoguebox {

	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf", "PDF");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	 String source_file = chooser.getSelectedFile().getAbsolutePath();
	            System.out.println(source_file);
	            
	           
        }
	}

}
