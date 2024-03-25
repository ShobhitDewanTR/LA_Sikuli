package testPackage;

import java.sql.Timestamp;
import java.util.Iterator;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;
import java.time.Instant;

import com.aventstack.extentreports.ExtentTest;

public class CompanyDataCases extends BasePackage.LABase {
	static Pattern pattern, pattern1, pattern2;
	public CompanyDataCases() {
		super();
	}
	@BeforeClass
	public void setup(){
	    extent = BasePackage.LABase.getInstance();
	}
	@AfterTest
	public void flushReportData() {
		extent.flush();
	} 
	
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7"})
	@Test
	public static void VerifyCompanyNumberFormatting(String Lang, String LNF, String ACS, String Unit, String Descr, String Curr, String Prior, String Forecast) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String CheckboxName,Value;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			//System.out.println(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX());
			status=LaunchLA(test,"Open","YES");
			if(status==1) {
				s.wait(Patternise("Tools","Moderate"),2).click();
				s.wait(Patternise("Preferences","Moderate"),5).click();
				s.wait(Patternise("EditingOptions","Moderate"),5).click();
				CheckUncheckBox("LocalNumberFormatting",LNF);
				CheckUncheckBox("AsianCountingSystem",ACS);
				if (s.exists(Patternise("DefaultLanguage","Moderate")) != null) {
					s.wait(Patternise("DefaultLanguage","Moderate"),2).click();
					SelectLanguage(Lang);
					Thread.sleep(3000);
					s.wait(Patternise("Save","Moderate"),2).doubleClick();
				}
				else {
					test.fail("Default language dropdown is not found");
				}
				//Create bundle and Alert and then test formatting
				s.wait(Patternise("NewBundle","Moderate"),2).click();
				test.pass("Clicked on NewBundle button");		
				Thread.sleep(2000);
				s.wait(Patternise("NewCompanyData","Moderate"),2).click();
				test.pass("Clicked on NewCompanyData button");
				Thread.sleep(2000);
				if (s.exists(Patternise("ExpandCompanyData","Moderate")) != null) {
					test.pass("New Company Data Template loaded");
					s.wait(Patternise("NewAlert","Moderate"),2).click();
					test.pass("Clicked on NewAlert button");
					Thread.sleep(2000);
					if (s.exists(Patternise("ExpandAlert","Moderate")) != null) {
						test.pass("New Alert Template loaded");
						s.type("{C0.DESC}{C0.CUR}{C0.PRI}{C0.FYF}{C0.DATE}{C0.PER}");
						s.wait(Patternise("ExpandCompanyDataUnselected","Moderate"),2).click();
						s.type(Descr);
						s.keyDown(Key.TAB);
						s.keyUp(Key.TAB);
						s.type(Curr);
						s.keyDown(Key.TAB);
						s.keyUp(Key.TAB);
						s.type(Prior);
						s.keyDown(Key.TAB);
						s.keyUp(Key.TAB);
						s.type(Forecast);
						s.wait(Patternise("Unitddn","Moderate"),2).offset(50,0).click();
						Thread.sleep(2000);
						s.wait(Patternise(Unit,"Moderate"),2).click();
						Thread.sleep(2000);
						if (s.exists(Patternise("PreviewUnchkd","Moderate")) != null) {
							s.wait(Patternise("PreviewUnchkd","Moderate"),2).click();
						}
						else if (s.exists(Patternise("PreviewChkd","Moderate")) != null) {
							s.wait(Patternise("PreviewChkd","Moderate"),2).click();
							Thread.sleep(2000);
							s.wait(Patternise("PreviewUnchkd","Moderate"),2).click();
						}
						else {
							test.fail("Preview Checkbox not found");
						}
						if (s.exists(Patternise(Lang+"Formatting"+Unit,"Moderate")) != null) {
							test.pass("Formatting correctly done for "+ Lang +" language");
						}
						else {
							test.fail("Incorrect formatting for "+Lang+" language");
						}
					}
					else {
						test.fail("New Alert Template not loaded, Cannot Continue");
				     }
				}
				else {
					test.fail("NewCompanyData Template not loaded, Cannot Continue");
			     }
				//end
			}
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			while(s.exists(Patternise("Delete","Strict")) != null) {
				s.click(Patternise("Delete","Strict"));
			}
			while(s.exists(Patternise("DeleteFocussed","Strict")) != null) {
				s.click(Patternise("DeleteFocussed","Strict"));
			}
			s.wait(Patternise("Tools","Moderate"),2).click();
			s.wait(Patternise("Preferences","Moderate"),5).click();
			s.wait(Patternise("EditingOptions","Moderate"),5).click();
			CheckUncheckBox("LocalNumberFormatting","NO");
			CheckUncheckBox("AsianCountingSystem","NO");
			if (s.exists(Patternise("DefaultLanguage","Moderate")) != null) {
				s.wait(Patternise("DefaultLanguage","Moderate"),2).click();
				SelectLanguage("English");
				s.wait(Patternise("Save","Exact"),2).doubleClick();
			}
			else {
				test.fail("Default language dropdown is not found");
			}
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

		public static void CheckUncheckBox(String CheckboxName, String Value) {
			try {
					switch(Value.toUpperCase()) {
					case "YES":
						if (s.exists(Patternise(CheckboxName+"Checked","Strict")) != null) {
							test.pass(CheckboxName+" checkbox is already checked");
						}
						else {
							s.find(Patternise(CheckboxName+"Unchecked","Strict")).offset(-10,10).getTopRight().click();
							test.pass("Checked "+CheckboxName+ " checkbox");
						}
					    break;
					case "NO":
						if (s.exists(Patternise(CheckboxName+"Unchecked","Strict")) != null) {
							test.pass(CheckboxName+" checkbox is already unchecked");
						}
						else {
							s.find(Patternise(CheckboxName+"Checked","Strict")).offset(-10,10).getTopRight().click();
							test.pass("Unchecked "+CheckboxName+ " checkbox");
						}
					    break;
					default:
					    test.fail("Wrong Value inputted.Please correct the Value in Code and rerun");
					    return;
				}
			}
			catch(Exception e) {
				test.fail("Error Occured: "+e.getLocalizedMessage());
			}
		}
	
		public static void SelectLanguage(String Language) {
				try {
						if (s.exists(Patternise(Language,"Moderate")) != null) {
							s.wait(Patternise(Language,"Moderate"),2).click();
							test.pass("Selected Language "+Language+" from the default language dropdown");
						}
						else if (s.exists(Patternise(Language+"Selected","Moderate")) != null) {
							test.pass("Language "+Language+" already selected in the default language dropdown");
						}
						else {
							test.fail("Selected Language "+Language+ " not found in Default language dropdown");
						}
				}
				catch(Exception e) {
					test.fail("Error Occured: "+e.getLocalizedMessage());
				}
		}
}
