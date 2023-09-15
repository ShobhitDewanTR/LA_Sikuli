package testPackage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class MainRunner extends BasePackage.LABase {
	public static String TestName, TestDescription, TestID, TestParams;

	public void TestNgXmlSuite(String classname) {
		try {
			TestNG tng = new TestNG();
			XmlSuite suite = new XmlSuite();
			suite.setName("Suite");
			suite.setParallel(XmlSuite.ParallelMode.METHODS);
			// suite.addListener("test.Listener1");
			XmlTest test = new XmlTest(suite);
			test.setName("Test");
			test.setPreserveOrder(true);
			List<String> parameters = new ArrayList<>();
			parameters = Arrays.asList(TestParams.split(","));
			// if (parameters.size()>1) {
			HashMap<String, String> parametersMap = new HashMap<String, String>();
			for (int i = 0; i < parameters.size(); i++) {
				parametersMap.put("param" + i, parameters.get(i));
				test.setParameters(parametersMap);
			}
			// }
			XmlClass class1 = new XmlClass("testPackage." + classname);
			List<XmlInclude> includeMethods = new ArrayList<>();
			includeMethods.add(new XmlInclude(TestName));
			class1.setIncludedMethods(includeMethods);
			test.getClasses().add(class1);
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			tng.setXmlSuites(suites);
			// Settings.AutoWaitTimeout=10;
			// Settings.Highlight=true;
			Settings.setShowActions(true);
			tng.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public static void main(String[] args) throws FindFailed, Exception {
		MainRunner rc = new MainRunner();
		String DataFilepath;
		Row row = null;
		Runtime rt = Runtime.getRuntime();
		DataFilepath = GetProperty("DataFile");
		FileInputStream fis = new FileInputStream(DataFilepath);
		Workbook wb = null;
		int count=0;
		try {
			String WrkngDrctry;
			WrkngDrctry=System.getProperty("user.dir");
			File workingfolder = new File(WrkngDrctry);
		    File[] listOfFiles = workingfolder.listFiles();
		    for (File f: listOfFiles) {
		          if(f.getName().contains(".html") && !f.getName().equals(Reportname+java.time.LocalDate.now()+".html")) {
		          Path temp = Files.move
					        (Paths.get(System.getProperty("user.dir") +"\\"+ f.getName()),
					        Paths.get(System.getProperty("user.dir") +"\\Archived-Results\\"+ f.getName()),StandardCopyOption.REPLACE_EXISTING);
				 }
		    }
			wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();
			while (itr.hasNext()) {
				row = itr.next();
				String Runornot = row.getCell(0).getStringCellValue();
				if (Runornot.equalsIgnoreCase("YES")) {
					TestID = row.getCell(1).getStringCellValue();
					TestName = row.getCell(2).getStringCellValue();
					TestParams = row.getCell(3).getStringCellValue();
					TestDescription = row.getCell(5).getStringCellValue();
					rc.TestNgXmlSuite(row.getCell(4).getStringCellValue());
					while(s.exists(Patternise("ErrorMetadataHC","Easy"))!=null || s.exists(Patternise("ErrorMetadata","Easy"))!=null || s.exists(Patternise("ErrorOutofProc","Easy"))!=null) {
						if(s.exists(Patternise("ErrorMetadataOK_1","Easy")) != null) {
							s.click(Patternise("ErrorMetadataOK_1","Easy"));
						}
						if(s.exists(Patternise("ErrorMetadataOK","Easy")) != null) {
							s.click(Patternise("ErrorMetadataOK","Easy"));	
						}
						if(s.exists(Patternise("ErrorMetadataOKHC","Easy")) != null) {
							s.click(Patternise("ErrorMetadataOKHC","Easy"));
						}
						if(s.exists(Patternise("ErrorMetadataOKLGHT","Moderate")) != null) {
							s.click(Patternise("ErrorMetadataOKLGHT","Moderate"));
						}
						
						else if (s.exists(Patternise("ErrorOutofProc", "Easy")) != null) {
							if(s.exists(Patternise("ErrorMetadataOK_1","Moderate")) != null) {
								s.click(Patternise("ErrorMetadataOK_1","Moderate"));
							}
							if(s.exists(Patternise("ErrorMetadataOK","Moderate")) != null) {
								s.click(Patternise("ErrorMetadataOK","Moderate"));	
							}
							if(s.exists(Patternise("ErrorMetadataOKHC","Moderate")) != null) {
								s.click(Patternise("ErrorMetadataOKHC","Moderate"));
							}
							if(s.exists(Patternise("ErrorMetadataOKLGHT","Moderate")) != null) {
								s.click(Patternise("ErrorMetadataOKLGHT","Moderate"));
							}
							
							RelaunchReopenFWTab(test, "Relaunch");
						}
						if (count>10) {
							test.fail("Application error has occured");
							RelaunchReopenFWTab(test,"Relaunch");
							break;
							
						}
						count++;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {
			wb.close();
			// rt.exec("taskkill /F /IM LYNX.exe");
		}

	}

}