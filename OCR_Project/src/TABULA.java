import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

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
	
	 static void text_to_excel() {
		 String fileName = "D://Img-testing//text.txt";
		 String excelFileName = "D://Img-testing//excel.xlsx";

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
		    final String FILENAME="D://Img-testing//ROAD DETAILS M WEST.pdf";
//		    final String FILENAME="D:\\Reconciliation project\\Arif\\Arif__Apr_2022.pdf";

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
		    text_to_excel();
		    
		}

	}


