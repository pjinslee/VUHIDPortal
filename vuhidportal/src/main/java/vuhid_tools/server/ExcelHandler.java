/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package vuhid_tools.server;

import java.io.File;
import java.io.IOException;
import java.util.Locale;


import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * This class writes to Excel files
 * 
 * @author Lars Vogel <vogella.com> modified by Long Phan <long2@pdx.edu>
 * @version 1.2
 */
public class ExcelHandler
{
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private WritableSheet excelSheet;
	private String outputFileName;
	
	/**
	 * Writes the excel file.
	 * 
	 * @param Month				The (integer) month that the report will be generated on.
	 * @param Year				The (integer) year that the report will be generated on.
	 * @param inputLabels		The (array of String) labels on the report (Column A of the Excel file, starts from row 2).
	 * @param inputContents		The (2-D array of String) contents on the report (Column B and C of the Excel file, starts from row 2).
	 */
	public void write(int Month, int Year, String[] inputLabels, String[][] inputContents) throws IOException, WriteException
	{
		//File file = new File(new File(inputFileName).getAbsolutePath().replaceAll("/", "\\\\"));
		new File(Config.SERVER_PATH + "reports/").mkdirs(); //Create the output directory if not exist
		File file = new File(Config.SERVER_PATH + "reports/" + outputFileName);
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Report", 0);
		excelSheet = workbook.getSheet(0);
		createLabel(excelSheet, 0, 0, Month + "/" + Year);
		createLabel(excelSheet, 1, 0, "Monthly total");
		createLabel(excelSheet, 2, 0, "Year-to-date total");
		for(int i = 0; i < inputLabels.length; i++)
		{
			createLabel(excelSheet, 0, i + 1, inputLabels[i]);
			for(int j = 0; j < inputContents[0].length; j++)
			{
				addLabel(excelSheet, j + 1, i + 1, inputContents[i][j]);
			}
		}

		workbook.write();
		workbook.close();
	}
	
	/**
	 * Sets the local output file name.
	 */
	public void setOutputFile(String outputFileName)
	{
		this.outputFileName = outputFileName;
	}
	
	/**
	 * Creates a label style in the excel file.
	 * 
	 * @param sheet			The (WritableSheet) current opened excel sheet.
	 * @param column		The (integer) column.
	 * @param row			The (integer) row.
	 * @param inputLabel	The (String) input label.
	 */
	public void createLabel(WritableSheet sheet, int column, int row, String inputLabel) throws WriteException
	{
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false, UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write a few headers
		addCaption(sheet, column, row, inputLabel);
	}
	
	/**
	 * Adds a caption to a slot on the excel sheet (Different font from addLabel).
	 * 
	 * @param sheet			The (WritableSheet) current opened excel sheet.
	 * @param column		The (integer) column.
	 * @param row			The (integer) row.
	 * @param s				The (String) input caption.
	 */
	private void addCaption(WritableSheet sheet, int column, int row, String s) throws RowsExceededException, WriteException
	{
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}
	
	/**
	 * Adds a number to a slot on the excel sheet.
	 * 
	 * @param sheet			The (WritableSheet) current opened excel sheet.
	 * @param column		The (integer) column.
	 * @param row			The (integer) row.
	 * @param s				The (String) input label.
	 */
	private void addNumber(WritableSheet sheet, int column, int row, Integer integer) throws WriteException, RowsExceededException
	{
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}
	
	/**
	 * Adds a label to a slot on the excel sheet (Different font from addCaption).
	 * 
	 * @param sheet			The (WritableSheet) current opened excel sheet.
	 * @param column		The (integer) column.
	 * @param row			The (integer) row.
	 * @param s				The (String) input label.
	 */
	private void addLabel(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException
	{
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}
	/*public void createContent(WritableSheet sheet) throws WriteException, RowsExceededException
	{
		// Write a few number
		for (int i = 1; i < 10; i++)
		{
			// First column
			addNumber(sheet, 0, i, i + 10);
			// Second column
			addNumber(sheet, 1, i, i * i);
		}
		// Lets calculate the sum of it
		StringBuffer buf = new StringBuffer();
		buf.append("SUM(A2:A10)");
		Formula f = new Formula(0, 10, buf.toString());
		sheet.addCell(f);
		buf = new StringBuffer();
		buf.append("SUM(B2:B10)");
		f = new Formula(1, 10, buf.toString());
		sheet.addCell(f);

		// Now a bit of text
		for (int i = 12; i < 20; i++)
		{
			// First column
			addLabel(sheet, 0, i, "Boring text " + i);
			// Second column
			addLabel(sheet, 1, i, "Another text");
	    }
	}
	public static void main(String[] args) throws WriteException, IOException
	{
		ExcelHandler test = new ExcelHandler();
		test.setOutputFile("c:/temp/lars.xls");
		test.write();
		System.out.println("Please check the result file under c:/temp/lars.xls ");
	}*/
}