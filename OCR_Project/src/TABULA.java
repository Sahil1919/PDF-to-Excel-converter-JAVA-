import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.jbig2.segments.Table;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

public class TABULA {
	
	 static void text_to_excel(String excel_name) {
		 String fileName = "D://Img-testing//text.txt";
		 String excelFileName = excel_name +".xlsx";

		 // Create a Workbook and a sheet in it
		 XSSFWorkbook workbook = new XSSFWorkbook();
		 XSSFSheet sheet = workbook.createSheet("Sheet1");

		 // Read your input file and make cells into the workbook
		 try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		     String line;
		     Row row;
		     Cell cell;
		     int rowIndex = 0;
		     while ((line = br.readLine()) != null) {
		         row = sheet.createRow(rowIndex);
		         String[] tokens = line.split("[|]");
		         for(int iToken = 0; iToken < tokens.length; iToken++) {
		             cell = row.createCell(iToken);
		             cell.setCellValue(tokens[iToken]);
		         }
		         rowIndex++;
		     }
		 } catch(Exception e) {
		     e.printStackTrace();
		 }

		 // Write your xlsx file
		 try (FileOutputStream outputStream = new FileOutputStream(excelFileName)) {
		     workbook.write(outputStream);
		     workbook.close();
		 } catch (IOException e) {
		     e.printStackTrace();
		 }
	}
	 public static void main(String[] args) throws IOException {
		 JFileChooser chooser = new JFileChooser();
		 FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf", "PDF");
	        chooser.setFileFilter(filter);
	        int returnVal = chooser.showOpenDialog(null);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	            String source_file = chooser.getSelectedFile().getAbsolutePath();
	            final String FILENAME= source_file;
	            System.out.println(source_file);
	            PDDocument pd = PDDocument.load(new File(FILENAME));

			    int totalPages = pd.getNumberOfPages();
			    System.out.println("Total Pages in Document: "+totalPages);

			    ObjectExtractor oe = new ObjectExtractor(pd);
			    SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
			    Page page = oe.extract(1);
			    FileWriter fWriter = new FileWriter(
	                    "D://Img-testing//text.txt");
			    // extract text from the table after detecting
			    List<technology.tabula.Table> table = sea.extract(page);
			    for(technology.tabula.Table tables: table) {
			        List<List<RectangularTextContainer>> rows = tables.getRows();

			        for(int i=0; i<rows.size(); i++) {

			            List<RectangularTextContainer> cells = rows.get(i);

			            for(int j=0; j<cells.size(); j++) {
			            	String text = cells.get(j).getText()+"|";
			  
			                    fWriter.write(text);
			         
			                    // Printing the contents of a file
			                    System.out.print(text);
			         
			                    // Closing the file writing connection
			                    
			            }

			            System.out.println();
			            fWriter.write('\n');
			        }
			       
			    }
			    fWriter.close();
			    String[] source_excel_name = source_file.split(".pdf",2);
	            String excel_name = source_excel_name[0];
			    text_to_excel(excel_name);
	        }
		   
		    
		}

	}


