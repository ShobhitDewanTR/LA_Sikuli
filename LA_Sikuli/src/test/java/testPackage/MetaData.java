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

public class MetaData extends BasePackage.LYNXBase {
	static Pattern pattern, pattern1, pattern2;
	public MetaData() {
		super();
	}
	@BeforeClass
	public void setup(){
	    extent = BasePackage.LYNXBase.getInstance();
	}
	@AfterTest
	public void flushReportData() {
		extent.flush();
	} 
	@Parameters({"param0"})
	@Test
	public static void VerifyAlertEditor(String Market) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			ValidateObjectDisplayed(test,"AlertEditorTab","Alert Editor section",0,"Normal");
			ValidateObjectDisplayed(test,"Market","Market Dropdown",1,"Normal");
			ValidateObjectDisplayed(test,"Australia","Australia CheckBox",1,"Normal");
			ValidateObjectDisplayed(test,"Apply","Apply Button",1,"Normal");
			Thread.sleep(2000);
			ValidateObjectDisplayed(test,"MarketsChkd","Market Dropdown shown as checked",0,"Normal");
			if(s.exists(GetProperty("Show"),5)!=null) {
				s.find(GetProperty("Show")).click();
			}
			ValidateObjectDisplayed(test,"BlankProducts","Blank Products textbox",0,"Reverse");
			ValidateObjectDisplayed(test,"BlankTopics","Blank Topic textbox",0,"Reverse");
			ValidateObjectDisplayed(test,"BlankRICS","Blank RIC textbox",0,"Reverse");

			test.log(com.aventstack.extentreports.Status.INFO,"Reversing User made Changes");
			ValidateObjectDisplayed(test,"MarketsChkd","Market Dropdown",1,"Normal");
			ValidateObjectDisplayed(test,"AustraliaChkd","Australia Checkbox making it unchecked",1,"Normal");
			ValidateObjectDisplayed(test,"Apply","Apply Button",1,"Normal");
			ValidateObjectDisplayed(test,"Market","Market Dropdown shown as unchecked",0,"Normal");
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0"})
	@Test
	public static void Verify_Storylist(String Option) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(s.exists(GetProperty(Option),20)!=null) {
				test.pass(Option+" column found in story list");
			}
			else {
				test.fail(Option+" column not found in story list");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0"})
	@Test
	public static void Verify_Display_Metadata_RIC(String Option) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(Option.equals("Company_RIC") && s.exists(GetProperty("SelectCompany"),7)==null) {
				test.pass("Company and RIC displayed in story body");
				return;
			}
			if (s.exists(Patternise("BlankRICS","Strict"),3)==null) {
			ClearMetaData();
			}
			//s.wait(GetProperty("BlankRICS"),5).click();
			s.wait(Patternise("BlankRICS","Strict"),5).click();
			Thread.sleep(2000);
			EnterMetadata("H.N");
			test.pass("Entered RIC");
			ValidateMetadata(Option);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void Verify_Alert_Publish(String DirectRun, String Option,String Alerttext, String USN) throws FindFailed, InterruptedException {
		if(DirectRun.equalsIgnoreCase("YES")) {
			test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		}
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		Region r;
		String timeNdate;
		try {
			if(!Alerttext.contains("DOMESTIC PRODUCT")){
			RelaunchReopenFWTab(test,"Reopen");
			}
			if(s.exists(Patternise("NoHeadlines","Moderate"),4)!=null || s.exists(Patternise("NoHeadlinesHC","Moderate"),4)!=null ) {
				test.skip("No headlines found unable to proceed");
				return;
			}
			if(Alerttext.toUpperCase().contains("RELEASE BODY WEB VIEW")){
				ClickReleaseBodyWebView();
			}
			if(Alerttext.toUpperCase().contains("AUTOLAUNCH")){
			OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
			ChangeAutolaunch("ON");
			s.click(Patternise("SaveFeed","Moderate"),3);
			}
			ClearMetaData();
			s.wait(Patternise("BlankRICS","Easy"),5).click();
			Thread.sleep(2000);
			EnterMetadata("H.N");
			test.pass("Entered RIC");
			s.wait(Patternise("GetUSN","Easy"),5).offset(-50,0).click();
			s.type(USN);
			test.pass("Entered Custom USN");
			Thread.sleep(5000);
			/*r=new Region(s.find(GetProperty("chars")).getX()-80, s.find(GetProperty("chars")).getY()+32, 1, 1);
			r.click();
			Thread.sleep(3000);
			s.type("a", KeyModifier.CTRL);
			Thread.sleep(3000);
			s.keyDown(Key.BACKSPACE);
			s.keyUp(Key.BACKSPACE);
			Thread.sleep(3000);*/
			switch(Option.toUpperCase()) {
			case "PUBLISH":
				/*Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				timeNdate=(timestamp+"").replace(":","");
				r.type(Alerttext+" "+timeNdate);
				test.pass("Entered custom Alert text");*/
				EnterAlert(Alerttext);
				//r=new Region(s.find(GetProperty("charsselected")).getX()+80, s.find(GetProperty("charsselected")).getY()+10, 1, 1);
				//r.click();
				s.keyDown(Key.TAB);
				s.keyUp(Key.TAB);
				//Move To Publish
				Thread.sleep(2000);
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
				//s.wait(Patternise("Publish","Strict"),5).click();
				test.pass("Clicked Publish Button");
				NavigatetoPublishHistory();
				if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)&& USN_Flavors(test,USN)) {
					test.pass("Alert successfully Published");
				}
				else {
						test.fail("Alert not Published");
				}
				break;
			case "BLANKALERTTEXT":
				EnterAlert("");
				if(s.exists(Patternise("PublishBtnDsbld","Strict"),5)!=null) {
					test.pass("Publish button is disabled if no alert text is entered");
				}
				else {
					test.fail("Publish button is enabled if no alert text is entered");
				}
				break;
			case "PLACEHOLDER":
				//r.type(Alerttext);
				EnterAlert(Alerttext);
				test.pass("Entered custom Alert text "+Alerttext);
				if(s.exists(Patternise("PublishBtnDsbld","Moderate"),5)!=null) {
					test.pass("Publish button disabled after entering place holders in alert text");
				}
				else {
					test.fail("Publish button not disabled after entering place holders in alert text");
				}
				break;
			case "VERIFYUSN":
				if(s.exists(Patternise("9CHARUSN","Moderate"),5)!=null) {
					test.pass("User able to enter 9 digit USN");
				}
				else {
					test.fail("User unable to enter 9 digit USN");
				}
				break;
			
			case "USNFAILS":
				/*Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
				timeNdate=(timestamp2+"").replace(":","");
				r.type(Alerttext+" "+timeNdate);
				test.pass("Entered custom Alert text");*/
				EnterAlert(Alerttext);
				s.keyDown(Key.TAB);
				s.keyUp(Key.TAB);
				//Move To Publish
				Thread.sleep(2000);
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
				//s.wait(Patternise("Publish","Strict"),5).click();
				test.pass("Clicked Publish Button");
				NavigatetoPublishHistory();
				if(s.exists(Patternise("PublishedAlert","Strict"),5)!=null && USN_Flavors(test,USN)) {
					test.fail("Alert successfully Published");
				}
				else {
						test.pass("Alert not Published");
				}
				break;
			case "HIGHCONTRAST":
				SetHighContrast("ON");
				EnterAlert(Alerttext);
				SetHighContrast("OFF");
				break;
			case "HIGHCONTRASTPUBLISH":
				//s.wait(Patternise("PublishHC","Easy"),5).click();
				SetHighContrast("ON");
				EnterAlert(Alerttext);
				s.keyDown(Key.TAB);
				s.keyUp(Key.TAB);
				//Move To Publish
				Thread.sleep(2000);
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
				test.pass("Clicked Publish Button");
				NavigatetoPublishHistory();
				if(s.exists(Patternise("PublishedAlertHC","Easy"),5)!=null || s.exists(Patternise("PublishedAlert_UnselectedHC","Easy"),5)!=null) {
						test.pass("Alert successfully Published in High Contrast");
				}
				else {
						test.fail("Alert not Published in High Contrast");
				}
				SetHighContrast("OFF");
				break;
			case "HIGHCONTRASTQUICKPUBLISH":
				SetHighContrast("ON");
				s.wait(Patternise("ReleaseBody","Easy"),5).offset(0,34).rightClick();
				Thread.sleep(3000);
				EnterAlert(Alerttext);
				s.keyDown(Key.TAB);
				s.keyUp(Key.TAB);
				//Move To Publish
				Thread.sleep(2000);
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
				//s.wait(Patternise("QPublishHC","Easy"),5).click();
				test.pass("Clicked Quick Publish Button");
				NavigatetoPublishHistory();
				if(s.exists(Patternise("PublishedAlertHC","Easy"),5)!=null || s.exists(Patternise("PublishedAlert_UnselectedHC","Easy"),5)!=null) {
						test.pass("Alert successfully Published in High Contrast using Quick Publish");
				}
				else {
						test.fail("Alert not Published in High Contrast using Quick Publish");
				}
				SetHighContrast("OFF");
				break;
			}	
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			if(Alerttext.toUpperCase().contains("RELEASE BODY WEB VIEW") && s.exists(Patternise("ReleaseBodyUnslctd","Moderate"),3)!=null) {
				s.click(Patternise("ReleaseBodyUnslctd","Moderate"),5);
			}
			if(Alerttext.toUpperCase().contains("AUTOLAUNCH")){
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("OFF");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	public static void ClickReleaseBodyWebView() {
			try{
				//click on Release body web view
					if(s.exists(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),5)!=null) {
						s.click(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),5);
						test.pass("Clicked on Release Body Web View");
					}
					else if(s.exists(Patternise("ReleaseBodyWebViewSlctd","Moderate"),5)!=null)
					{
						test.pass("Release Body Web View already activated on screen");
					}
					else {
						test.fail("Release Body Web View not found");
					}
			}
			catch(Exception e) {
				test.fail("Error Occured: "+e.getLocalizedMessage());
			}
	}
	@Parameters({"param0"})
	@Test
	public static void Verify_StoryID_Format(String mode) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int found=0;
		String StoryIDFormat;
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(mode.toUpperCase().equals("HIGHCONTRAST")) {
				StoryIDFormat=GetProperty("StoryIDFormatHC");
				SetHighContrast("ON");
			}
			else if(mode.toUpperCase().equals("AUTOLAUNCHHIGHCONTRAST")) {
				StoryIDFormat=GetProperty("StoryIDFormatHC");
				SetHighContrast("ON");
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("ON");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			else {
				StoryIDFormat=GetProperty("StoryIDFormat");
			}
			
			s.wait(Patternise("ID","Strict"),5).offset(0,30).click();
			while(s.exists(Patternise("EOHdlnScroll","Strict"))==null) {
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
				if(s.exists(StoryIDFormat,2)!=null) {
					found=1;
					break;
				}
			}
			if(mode.toUpperCase().equals("HIGHCONTRAST")) {
				SetHighContrast("OFF");
			}
			if(mode.toUpperCase().equals("AUTOLAUNCHHIGHCONTRAST")) { 
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("OFF");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			if(found==1) {
				test.pass("Story ID in format 0 to 999 in story list in "+mode+" mode");
			}
			else {
				test.fail("Story ID not in format 0 to 999 in story list in "+mode+" mode");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0"})
	@Test
	public static void Verify_Multiplecodes(String Metadatatype) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			String finalMetadatatype;
			RelaunchReopenFWTab(test,"Reopen");
			if(Metadatatype.toUpperCase().contains("RELEASE BODY WEB VIEW")) {
				finalMetadatatype=Metadatatype.replace("RELEASE BODY WEB VIEW", "").trim();
				ClickReleaseBodyWebView();
			}
			else if(Metadatatype.toUpperCase().contains("AUTOLAUNCH")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("ON");
				s.click(Patternise("SaveFeed","Moderate"),3);
				finalMetadatatype=Metadatatype.replace("AUTOLAUNCH", "").trim();
			}
			else {
				finalMetadatatype=Metadatatype;
			}
			ClearMetaData();
			s.wait(Patternise("Blank"+finalMetadatatype,"Strict"),5).click();
			Thread.sleep(2000);
			EnterMetadata("AAA");
			test.pass("Entered "+finalMetadatatype+" AAA");
			EnterMetadata("BA");
			test.pass("Entered "+finalMetadatatype+" BA");
			if(s.exists(Patternise("Multiple"+finalMetadatatype,"Strict"))!=null) {
				test.pass("User is able to enter multiple "+finalMetadatatype);
			}
			else {
				test.fail("User is unable to enter multiple "+finalMetadatatype);
			}
			if(Metadatatype.toUpperCase().contains("REMOVE")) {
				for (int i=0;i<10;i++) {
					s.keyDown(Key.BACKSPACE);
					s.keyUp(Key.BACKSPACE);
				}
				if(s.exists(Patternise("Multiple"+finalMetadatatype,"Strict"))==null) {
					test.pass("User is able to remove multiple "+finalMetadatatype);
				}
				else {
					test.fail("User is unable to remove multiple "+finalMetadatatype);
				}
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			if(Metadatatype.toUpperCase().contains("RELEASE BODY WEB VIEW") && s.exists(Patternise("ReleaseBodyUnslctd","Moderate"),3)!=null) {
				s.click(Patternise("ReleaseBodyUnslctd","Moderate"),5);
			}
			else if(Metadatatype.toUpperCase().contains("AUTOLAUNCH")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("OFF");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0"})
	@Test
	public static void Verify_USN(String Metadata) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(Metadata.toUpperCase().contains("RELEASE BODY WEB VIEW")) {
				ClickReleaseBodyWebView();
			}
			s.wait(Patternise("GetUSN","Moderate"),5).click();
			s.find(GetProperty("AlertEditorTab")).click();
			Thread.sleep(2000);
			ValidateMetadata("USN");
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			if(Metadata.toUpperCase().contains("RELEASE BODY WEB VIEW") && s.exists(Patternise("ReleaseBodyUnslctd","Moderate"),3)!=null) {
				s.click(Patternise("ReleaseBodyUnslctd","Moderate"),5);
			}
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0","param1","param2"})
	@Test
	public static void Verify_MetaData_Inputs(String Metadata,String Data, String validation) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		String[] arrOfStr=null;
		String finalMetadatatype;
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(Metadata.toUpperCase().contains("RELEASE BODY WEB VIEW")) {
				finalMetadatatype=Metadata.replace("RELEASE BODY WEB VIEW", "").trim();
				ClickReleaseBodyWebView();
			}
			else if(Metadata.toUpperCase().contains("AUTOLAUNCH")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("ON");
				s.click(Patternise("SaveFeed","Moderate"),3);
				finalMetadatatype=Metadata.replace("AUTOLAUNCH", "").trim();
			}
			else {
				finalMetadatatype=Metadata;
			}
			ClearMetaData();
			if(validation.toUpperCase().contains("HIGHCONTRAST")) {
				SetHighContrast("ON");
				Thread.sleep(2000);
			}
			s.wait(Patternise("Blank"+finalMetadatatype,"Moderate"),5).click();
			Thread.sleep(2000);
			if(Data.contains(";")) {
				arrOfStr = Data.split(";", 0);
				for (String a : arrOfStr) {
					EnterMetadata(a.toLowerCase());
					Thread.sleep(2000);
				}
				test.pass("Entered multiple "+finalMetadatatype);
			}
			else {
					EnterMetadata(Data.toLowerCase());
					test.pass("Entered data");
					Thread.sleep(2000);
			}
			switch(validation) {
					case"lesslimit":
						if(s.exists(Patternise("Lesslimit","Strict"),5)!=null || s.exists(Patternise("Lesslimit_1","Strict"),5)!=null) {
							test.pass("Less limit characters allowed in "+finalMetadatatype+" field");
						}
						else {
								test.fail("Less limit characters not allowed in "+finalMetadatatype+" field");
						}
						break;
					case"exactlimit":
						if(s.exists(Patternise("Exactlimit","Strict"),5)!=null) {
							test.pass("Exact limit characters allowed in "+finalMetadatatype+" field");
						}
						else {
								test.fail("Exact limit characters not allowed in "+finalMetadatatype+" field");
						}
						break;
					case"Numeric":
						if(s.exists(Patternise("Numeric","Easy"),5)!=null || s.exists(Patternise("Numeric_1","Easy"),5)!=null ) {
							test.pass("Numerics are allowed in "+finalMetadatatype+" field");
						}
						else {
								test.fail("Numerics are not allowed in "+finalMetadatatype+" field");
						}
						break;
					case"Character":
						s.wait(Patternise("AlertEditorTab","Strict"),5).click();
						if(s.exists(Patternise("Character","Strict"),5)!=null || s.exists(Patternise("Character_1","Easy"),5)!=null) {
							test.pass("Characters are allowed in "+finalMetadatatype+" field");
						}
						else {
								test.fail("Characters are not allowed in "+finalMetadatatype+" field");
						}
						break;
					case"Alphanumeric":
						s.wait(Patternise("AlertEditorTab","Strict"),5).click();
						if(s.exists(Patternise("AplhanumericCharacter","Strict"),5)!=null || s.exists(Patternise("AplhanumericCharacter_1","Easy"),5)!=null || s.exists(Patternise("AplhanumericCharacter_2","Strict"),5)!=null) {
							test.pass("Alphanumeric Characters are allowed in "+finalMetadatatype+" field");
						}
						else {
								test.fail("Alphanumeric Characters are not allowed in "+finalMetadatatype+" field");
						}
						break;
					case"Remove":
						for (int i=0;i<10;i++) {
							s.keyDown(Key.BACKSPACE);
							s.keyUp(Key.BACKSPACE);
						}
						s.wait(Patternise("AlertEditorTab","Strict"),5).click();
						if(s.exists(Patternise("AplhanumericCharacter","Strict"),5)==null || s.exists(Patternise("AplhanumericCharacter_1","Easy"),5)==null || s.exists(Patternise("AplhanumericCharacter_2","Strict"),5)==null) {
							test.pass("Entered Data removed from "+finalMetadatatype+" field");
						}
						else {
								test.fail("Entered Data is not removed from "+finalMetadatatype+" field");
						}
						break;
					case"Special":
						s.wait(Patternise("AlertEditorTab","Strict"),5).click();
						if(s.exists(Patternise("SpecialCharacter","Strict"),5)!=null || s.exists(Patternise("SpecialCharacter_1","Easy"),5)!=null) {
							test.pass("Special Characters are allowed in "+finalMetadatatype+" field");
						}
						else {
								test.fail("Special Characters are not allowed in "+finalMetadatatype+" field");
						}
						break;
					case"Publish":
						if(s.exists(Patternise("BlankRICS","Strict"),5)!=null) {
							s.click(Patternise("BlankRICS","Strict"));
							EnterMetadata("H.N");
							test.pass("Entered RIC");
							Thread.sleep(5000);
						}
						EnterAlert("TEST PUBLISH ");
						if(finalMetadatatype.equals("NamedItems")) {
							s.click(Patternise("BlankNamedItems","Strict"));
							for (String a : arrOfStr) {
								EnterMetadata(a.toLowerCase());
								Thread.sleep(2000);
							}
						}
						s.wait(Patternise("Publish","Strict"),5).click();
						test.pass("Clicked Publish Button");
						if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)) {
							test.pass("Alert successfully Published");
						}
						else {
								test.fail("Alert not Published");
						}
						break;
					case"ShortcutPublishF12":
						EnterAlert("TEST PUBLISH ");
						s.keyDown(Key.F12);
						s.keyUp(Key.F12);
						test.pass("Clicked F12 key to initiate publish");
						if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)) {
							test.pass("Alert successfully Published");
						}
						else {
								test.fail("Alert not Published");
						}
						break;
		
					case"ShortcutPublishSF12":
						EnterAlert("TEST PUBLISH ");
						s.keyDown(Key.SHIFT);
						s.keyDown(Key.F12);
						s.keyUp(Key.F12);
						s.keyUp(Key.SHIFT);
						test.pass("Clicked Shift F12 key to initiate publish");
						if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)) {
							test.pass("Alert successfully Published");
						}
						else {
								test.fail("Alert not Published");
						}
						break;
					case"BasketPublish":
						EnterAlert("TEST PUBLISH ");
						s.wait(Patternise("Publish","Strict"),5).click();
						test.pass("Clicked Publish Button");
						if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)) {
							test.pass("Alert successfully Published in Publish history window");
						}
						else {
								test.fail("Alert not Published in Publish history window");
						}
						break;
						//VerifyInBasket("TEST PUBLISH ");
					case "HIGHCONTRAST":
						if(s.exists(Patternise(finalMetadatatype,"Easy"),5)!=null) {
							test.pass("Custom "+finalMetadatatype.replace("HC","")+" entered successfully in Dark Mode");
						}
						else {
								test.fail("Custom "+finalMetadatatype.replace("HC","")+" entered not found in Dark Mode");
						}
						SetHighContrast("OFF");
						break;
			}
			
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			
			if(Metadata.toUpperCase().contains("RELEASE BODY WEB VIEW") && s.exists(Patternise("ReleaseBodyUnslctd","Moderate"),3)!=null) {
				s.click(Patternise("ReleaseBodyUnslctd","Moderate"),5);
			}
			else if(Metadata.toUpperCase().contains("AUTOLAUNCH")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("OFF");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1","param2"})
	@Test
	public static void Verify_Lang_Publish(String SearchType,String AddDomesticCode, String language) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int statuslang;
		String finallanguage;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(language.toUpperCase().contains("RELEASE BODY WEB VIEW")) {
				finallanguage=language.replace("RELEASE BODY WEB VIEW", "").trim();
				ClickReleaseBodyWebView();
			}
			else if(language.toUpperCase().contains("AUTOLAUNCH")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("ON");
				s.click(Patternise("SaveFeed","Moderate"),3);
				finallanguage=language.replace("AUTOLAUNCH", "").trim();
			}
			else {
				finallanguage=language;
			}
			OpenUserPrfrncs(test,"Preferences","Application");
			statuslang=SelectLanguage(test,finallanguage);
			if(statuslang==2) {
				s.click(Patternise("Cancel","Strict"),5);
				s.click(Patternise("Cancel","Strict"),5);
				test.fail("Language "+finallanguage + " not found in Default Language dropdown");
			}
			else if(statuslang==3) {
				s.click(Patternise("Cancel","Strict"),5);
				test.fail("Default Language dropdown not found");
				
			}
			else {
				//Add code for Domestic codes
				if(AddDomesticCode.toUpperCase().equals("YES")) {
					if (s.exists(Patternise("FWUsrPrfrncs","Strict"),5)!=null) {
						s.find(Patternise("FWUsrPrfrncs","Strict")).getTopLeft().click();
						test.pass("Expanded Fastwire-User Preferences Option");
						s.click(Patternise("DefaultCodesDomestic","Strict"),3);
						s.click(Patternise("ApplyDomesticCodes","Strict"),3);
						//add code to enter default domestic code
						DelNEntrDomCode("TEST");
					}
					else {
						test.fail("Fastwire-User Preferences Option not found in Lynx Preferences Window");
					}
				}
				s.wait(Patternise("Save","Strict"),5).click();
				test.pass("Default Language dropdown found and language "+finallanguage + " selected and saved");
			}
			SelectLiveFeedOrFullsearch(SearchType);
			if(SearchType.toUpperCase().equals("FULLSEARCH")){
				ClickFullSearchbutton();
			}
			
			Verify_Alert_Publish("NO","PUBLISH","TEST PUBLISH DOMESTIC PRODUCT","TESTUSN");
			test.log(com.aventstack.extentreports.Status.INFO,"Reversing to default language English US");
			OpenUserPrfrncs(test,"Preferences","Application");
			statuslang=SelectLanguage(test,"EnglishUS");
			if(statuslang==2) {
				s.click(Patternise("Cancel","Strict"),5);
				s.click(Patternise("Cancel","Strict"),5);
				test.fail("Language English - US not found in Default Language dropdown");
			}
			else if(statuslang==3) {
				s.click(Patternise("Cancel","Strict"),5);
				test.fail("Default Language dropdown not found");
			}
			else {
				if(AddDomesticCode.toUpperCase().equals("YES")) {
					if (s.exists(Patternise("FWUsrPrfrncs","Strict"),5)!=null) {
						s.find(Patternise("FWUsrPrfrncs","Strict")).getTopLeft().click();
						test.pass("Expanded Fastwire-User Preferences Option");
						s.click(Patternise("DefaultCodesDomestic","Strict"),3);
						s.click(Patternise("ApplyDomesticCodes","Strict"),3);
						//add code to enter default domestic code
						DelNEntrDomCode("");
					}
					else {
						test.fail("Fastwire-User Preferences Option not found in Lynx Preferences Window");
					}
				}
				s.wait(Patternise("Save","Strict"),5).click();
				test.pass("Default Language dropdown found and language English - US selected and saved");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			if(language.toUpperCase().contains("RELEASE BODY WEB VIEW") && s.exists(Patternise("ReleaseBodyUnslctd","Moderate"),3)!=null) {
				s.click(Patternise("ReleaseBodyUnslctd","Moderate"),5);
			}
			if(language.toUpperCase().contains("AUTOLAUNCH")) {
					OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
					ChangeAutolaunch("OFF");
					s.click(Patternise("SaveFeed","Moderate"),3);
			}
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1"})
	@Test
	public static void Verify_BAEMetadataAutolaunch(String Metadatatype,String Option) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
			ChangeAutolaunch("ON");
			s.click(Patternise("SaveFeed","Moderate"),3);
			ClearMetaData();
			//open BAE window
			if(s.exists(Patternise("MoreActions","Easy"),5) != null) {
				s.click(Patternise("MoreActions","Easy"),3);
				if(s.exists(Patternise("DDNOptnOpnBAE","Moderate"))!=null) {
					s.click(Patternise("DDNOptnOpnBAE","Moderate"),3);
					test.pass("Clicked on BAE option in More Actions Dropdown");
				}
				else 
				{
					test.fail("BAE option not found in More Actions Dropdown");
				}
			}
			else {
				test.fail("MoreActions dropdown not found");
			}
			if(Metadatatype.equalsIgnoreCase("PRODUCTS")||Metadatatype.equalsIgnoreCase("TOPICS")) {
					s.wait(Patternise("Blank"+Metadatatype,"Strict"),5).click();
			}
			Thread.sleep(2000);
			EnterMetadata("AAA");
			test.pass("Entered "+Metadatatype+" AAA");
			EnterMetadata("BA");
			test.pass("Entered "+Metadatatype+" BA");
			if(s.exists(Patternise("Multiple"+Metadatatype,"Strict"))!=null) {
				test.pass("User is able to enter multiple "+Metadatatype+"in BAE window");
			}
			else {
				test.fail("User is unable to enter multiple "+Metadatatype+"in BAE window");
			}
			if(Metadatatype.toUpperCase().contains("REMOVE")) {
				for (int i=0;i<10;i++) {
					s.keyDown(Key.BACKSPACE);
					s.keyUp(Key.BACKSPACE);
				}
				if(s.exists(Patternise("Multiple"+Metadatatype,"Strict"))==null) {
					test.pass("User is able to remove multiple "+Metadatatype);
				}
				else {
					test.fail("User is unable to remove multiple "+Metadatatype);
				}
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			if(Metadatatype.toUpperCase().contains("AUTOLAUNCH")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("OFF");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	//Validate Feeds for autoalert and Fastfacts
	@Parameters({"param0","param1"})
	@Test
	public static void Verify_AutoalertFastfactFeeds(String Feed,String AUTOorFF) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			s.click(Patternise("SearchFeed","Moderate"),3);
			s.click(Patternise("SearchFeedCriteria","Moderate"),3);
			s.type(Feed);
			s.click(Patternise(Feed.replace(":", "").toUpperCase()+"Off","Easy"),3);
			s.click(Patternise("AddSelected","Moderate"),3);
			s.click(Patternise("SaveFeed1","Moderate"),3);
			RelaunchReopenFWTab(test,"Reopen");
			s.click(Patternise("Statusddn","Moderate"),3);
			if(AUTOorFF.equals("Autoalert")) {
				s.click(Patternise("AutoAlerted","Moderate"),3);
				s.click(Patternise("ApplyButton","Moderate"),3);
			}
			else if(AUTOorFF.equals("FastFacts"))  {
				s.click(Patternise("FastFacts","Moderate"),3);
				s.click(Patternise("ApplyButton","Moderate"),3);
			}
			else {
				test.fail("Wrong Status entered, please pass argument as Autoalert or FastFacts");
			}
			if(s.exists(Patternise("NoHeadlines","Easy"),5) != null) {
					test.fail("No feeds found for "+Feed +" feed in "+AUTOorFF+ " Status");
			}
			else {
				test.pass("Feeds found for "+Feed +" feed in "+AUTOorFF+ " Status");
			}
			//Reverse the selections
			s.click(Patternise("StatusddnSlctd","Moderate"),3);
			s.click(Patternise("ShowAllHeadlines","Moderate"),3);
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			s.click(Patternise("SearchFeed","Moderate"),3);
			s.click(Patternise("SearchFeedCriteria","Moderate"),3);
			s.type(Feed);
			s.click(Patternise(Feed.replace(":", "").toUpperCase()+"On","Easy"),3);
			s.click(Patternise("AddSelected","Moderate"),3);
			Thread.sleep(3000);
			s.click(Patternise("EnblFltrsSlctd","Moderate"),3);
			s.click(Patternise("SaveFeed1","Moderate"),3);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	//Validate Release body web View for WebWatcher feeds
		@Parameters({"param0"})
		@Test
		public static void Verify_ReleaseBodyWebView(String Feed) throws FindFailed, InterruptedException {
			test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
			String nameofCurrMethod = new Throwable()
	                .getStackTrace()[0]
	                .getMethodName();
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
			try {
				RelaunchReopenFWTab(test,"Reopen");
				OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
				
				while(s.exists(Patternise("DeleteWebWatcher","Moderate"),5)!=null)
				{
					s.click(Patternise("DeleteWebWatcher","Moderate"),3);
					s.click(Patternise("CnfrmDeleteWebWatcher","Moderate"),3);
				}
				s.click(Patternise("SearchFeed","Moderate"),3);
				s.type(Feed);
				Thread.sleep(4000);
				s.wait(Patternise("URL","Strict"),5).getTopLeft().click();
				s.click(Patternise("AddSelected","Moderate"),3);
				s.click(Patternise("SaveFeed1","Moderate"),3);
				RelaunchReopenFWTab(test,"Reopen");
				if(s.exists(Patternise("ReleaseBodyWebViewUnslctd","Exact"),5)!=null) {
					s.click(Patternise("ReleaseBodyWebViewUnslctd","Exact"));
					test.pass("Clicked on Release Body Web View");
				}
				else if(s.exists(Patternise("ReleaseBodyWebViewSlctd","Exact"),5)!=null) {
					test.pass("Release Body Web View already selected");
				}
				else {
					test.fail("Release Body Web View not found");
				}
				if(s.exists(Patternise("NoHeadlines","Moderate"),5)==null) {			
							s.wait(Patternise("DATE","Exact")).offset(0,20).click();
							if(s.exists(Patternise("BlankReleaseBodyWebView","Moderate"),5) != null) {
									test.fail("No data found in Release Body Web View for "+Feed+" feeds");
							}
							else {
								test.pass("Data Found for Release Body Web View for "+Feed+" feeds");
							}
				}
				else {
								test.fail("No Headlines found for "+Feed+" feeds");
							}
				//Reverse the selections
				test.info("Reversing the changes");
				OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
				
				while(s.exists(Patternise("DeleteWebWatcher","Moderate"),5)!=null)
				{
					s.click(Patternise("DeleteWebWatcher","Moderate"),3);
					s.click(Patternise("CnfrmDeleteWebWatcher","Moderate"),3);
				}
				s.click(Patternise("SaveFeed1","Moderate"),3);
			}
			catch(Exception e) {
				test.fail("Error Occured: "+e.getLocalizedMessage());
			}
			finally {
				test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
			}
		}
	
	public static void DelNEntrDomCode(String code) {
		try {
			s.wait(Patternise("DefaultDomProductcode","Strict"),5).offset(430,0).click();
			for (int i=0;i<10;i++) {
				s.keyDown(Key.BACKSPACE);
				s.keyUp(Key.BACKSPACE);
			}
			if(! code.isBlank()) {
				s.type(code);
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	
	public static int SelectLanguage(ExtentTest test, String language)
	{ 	
		int count=0;
		
		try{
				if(s.exists(Patternise("DefaultLanguage","Strict"),5)!=null) {
					s.wait(Patternise("DefaultLanguage","Strict"),5).offset(80,0).click();
					Thread.sleep(3000);
					s.keyDown(Key.HOME);
					s.keyUp(Key.HOME);
					while(count<2) {
						if(s.exists(Patternise(language,"Moderate"),5)!=null) {
							s.click(Patternise(language,"Moderate"),5);
							break;
						}
						s.keyDown(Key.END);
						s.keyUp(Key.END);
						count++;
					}
				}
				else {
					return 3;
				}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		
		return count;
	}
	@SuppressWarnings("finally")
	public static boolean USN_Flavors(ExtentTest test, String USN) {
		boolean val=false;
		try {
			switch(USN) {
			case"TESTUSN":
				if(s.exists(Patternise("PublishedUSN","Strict"),5)!=null) {
					val=true;
				}
				break;
			case"1234567890":
				Thread.sleep(3000);
				System.out.println("inside 10char");
				if(s.exists(Patternise("10CHARUSN","Strict"),3)!=null) {
					val=true;
				}
				break;
			case"123456789":
				Thread.sleep(3000);
				System.out.println("inside 9char");
				if(s.exists(Patternise("9CHARUSN","Strict"),3)!=null) {
					val=true;
					System.out.println("inside 9char if condition");
				}
				break;
			case"12345678":
				System.out.println("inside 8char");
				if(s.exists(Patternise("8CHARUSN","Strict"),5)!=null) {
					val=true;
				}
				break;
			case "!@#$%^&":
				System.out.println("inside specialchar");
				if(s.exists(Patternise("SPCLUSN","Strict"),5)!=null) {
					val=true;
				}
				break;
			}
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			return val;
		}
		
	}
		
	public static void ValidateMetadata(String Option) {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			switch(Option) {
			case"Company_RIC":
				if(s.exists(Patternise("HNRIC","Strict"),7)!=null) {
					test.pass("Company and RIC are displayed in story body after entering RIC");
				}
				else {
					test.fail("Company and RIC not displayed in story body even after entering RIC");
				}
				break;
			case"Product_Topic":
				Thread.sleep(3000);
				if(s.exists(Patternise("BlankProducts","Strict"),3)==null) {
					test.pass("Products are displayed after entering RIC");
				}
				else {
					test.fail("No Products are displayed after entering RIC");
				}
				if(s.exists(Patternise("BlankTopics","Strict"),3)==null) {
					test.pass("Topics are displayed after entering RIC");
				}
				else {
					test.fail("No Topics are displayed after entering RIC");
				}
				break;
			
			case"USN":
				if(s.exists(Patternise("BlankUSN","Strict"),5)==null) {
					test.pass("Valid USN number is displayed after clicking Get USN button");
				}
				else {
					test.fail("USN field is empty even after clicking Get USN button");
				}
				break;
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0","param1","param2"})
	@Test
	public static void Verify_RIC_Correction(String RIC,String Mode,String Publish) {
		if(Mode.equals("NewTest")) {
			test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		}
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			if(Mode.equals("NewTest")) {
				RelaunchReopenFWTab(test,"Reopen");
				ClearMetaData();
			}
			if(Publish.contains("AutoLaunch")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("ON");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			if(Publish.equals("HCVerify") || Publish.equals("HCPublish")) {
				SetHighContrast("ON");
				s.wait(Patternise("BlankRICSHC","Strict"),5).click();
				
			}
			else {
					s.wait(Patternise("BlankRICS","Strict"),5).click();
			}
			Thread.sleep(2000);
			s.type(RIC);
			Thread.sleep(2000);
			if(s.exists(Patternise("HNRIC_AE","Strict"),3)!=null || s.exists(Patternise("HNRIC_AEHC","Strict"),3)!=null  ) {
				test.pass("Company shown correctly in Alert Editor for typed RIC");
			}
			else {
				test.fail("Company not shown correctly in  Alert Editor for typed RIC");
			}
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			test.pass("Entered RIC");
			Thread.sleep(2000);
			if(s.exists(Patternise("HNRIC","Strict"),3)!=null || s.exists(Patternise("HNRICHC","Strict"),3)!=null) {
				test.pass("Company shown correctly in Story header for entered RIC");
			}
			else {
				test.fail("Company not shown correctly in Story header for entered RIC");
			}
			if(Publish.equals("Publish")) {
				EnterAlert("TEST PUBLISH ");
				s.wait(Patternise("Publish","Strict"),5).click();
				test.pass("Clicked Publish Button");
				if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)) {
					test.pass("Alert successfully Published in Publish history window");
				}
				else {
						test.fail("Alert not Published in Publish history window");
				}
			}
			if(Publish.equals("HCVerify")) {
				SetHighContrast("OFF");
			}
			if(Publish.equals("HCPublish")) {
				EnterAlert("TEST PUBLISH HIGHCONTRAST");
				s.wait(Patternise("PublishHC","Easy"),5).click();
				test.pass("Clicked Publish Button");
				if((s.exists(Patternise("PublishedAlertHC","Easy"),5)!=null || s.exists(Patternise("PublishedAlert_UnselectedHC","Easy"),5)!=null)) {
					test.pass("Alert successfully Published in Publish history window");
				}
				else {
						test.fail("Alert not Published in Publish history window");
				}
				SetHighContrast("OFF");
			}
			if(Publish.toUpperCase().contains("AUTOLAUNCH")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("OFF");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0","param1","param2"})
	@Test
	public static void Verify_NonEnglish_Publish_BSKT(String RIC,String Language,String Publish) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"Preferences","Application");
			SelectLanguage(test,Language);
			s.click(Patternise("SaveFeed","Moderate"),3);
			ClearMetaData();
			Verify_RIC_Correction(RIC,"ExistingTest",Publish);
			OpenUserPrfrncs(test,"Preferences","Application");
			SelectLanguage(test,"EnglishUS");
			s.click(Patternise("SaveFeed","Moderate"),3);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void Verify_MoreActions(String Precondition,String Option,String mode, String type) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		String Shortcut="";
		Pattern window=null;
		Pattern window2=null;
		Pattern ddnoption=null;
		Pattern ddnoption1=null;
		try {
			switch(Precondition.toUpperCase().trim()){
				case "AUTOLAUNCHON":
					OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
					ChangeAutolaunch("ON");
					s.click(Patternise("SaveFeed","Moderate"),3);
					break;
			}
			
			switch(type.toUpperCase().trim()) {
				case"TEMPLATE":
					Shortcut="t";
					window=Patternise("TemplateEditorWindow","Easy");
					ddnoption=Patternise("DDNOptn-ShowTemplates","Easy");
					break;
				case"PUBLISHHISTORY":
					Shortcut="y";
					window=Patternise("PublishHistoryWindow","Easy");
					window2=Patternise("PublishHistoryWindow_1","Easy");
					ddnoption=Patternise("DDNOptn-PublishedHistory","Easy");
					ddnoption1=Patternise("DDNOptn-PublishedHistory_1","Easy");
					break;
				case"MOREACTIONS":
					window=Patternise("MoreActionsWindow","Easy");
					Shortcut="r";
					break;
				case"COMPANYLIST":
					window=Patternise("CompanyListsWindow","Easy");
					Shortcut="l";
					break;
				case"NEWSFEED":
					window=Patternise("FeedsWindow","Easy");
					Shortcut="f";
					break;
				case"HEADLINE":
					window=Patternise("HeadlineActivityWindow","Easy");
					ddnoption=Patternise("DDNOptn-HeadlineActivity","Easy");
					break;			
				case"BAE":
					window=Patternise("BAEWindow","Easy");
					ddnoption=Patternise("DDNOptnOpnBAE","Easy");
					break;
				case"MARKETS":
					window=Patternise("ApplyButton","Easy");
					Shortcut="m";
					break;	
				case"STATUS":
					window=Patternise("ApplyButton","Easy");
					Shortcut="a";
					break;	
				case"FASTFACTS":
					window=Patternise("FastFactWindow","Easy");
					window2=Patternise("FastFactWindow_1","Easy");
					ddnoption=Patternise("DDNOptn-FastFact","Easy");
					break;
			}
			RelaunchReopenFWTab(test,"Reopen");
			SelectLiveFeedOrFullsearch(Option);
			if(Option.toUpperCase().equals("FULLSEARCH")){
				ClickFullSearchbutton();
			}
			switch(mode.toUpperCase().trim()){
			case"SHORTCUT":
				s.type(Shortcut,Key.ALT);
				if(s.exists(window,5) != null || s.exists(window2,5) != null) {
					test.pass("Successfully launched "+ type+" Window using Alt + "+Shortcut+" shortcut in "+Option);
					if(s.exists(Patternise("WindowClose","Easy"),5) != null) {
						s.find(Patternise("WindowClose","Easy")).offset(7,0).click();
					}
				}
				else {
					test.fail("Unable to launch "+type+" Window using Alt + "+Shortcut+" shortcut in "+Option);
				}
				Thread.sleep(3000);
				break;
			case"SHORTCUTBAE":
				s.keyDown(Key.SHIFT);
				s.keyDown(Key.F2);
				s.keyUp(Key.SHIFT);
				s.keyUp(Key.F2);
				if(s.exists(window,5) != null || s.exists(window2,5) != null) {
					test.pass("Successfully launched "+ type+" Window using Shift F2 shortcut in "+Option);
					if(s.exists(Patternise("WindowClose","Easy"),5) != null) {
						s.find(Patternise("WindowClose","Easy")).offset(7,0).click();
					}
				}
				else {
					test.fail("Unable to launch "+type+" Window using Shift F2 shortcut in "+Option);
				}
				Thread.sleep(3000);
				break;
			case"MOREACTION":
				if(s.exists(Patternise("MoreActions","Easy"),5) != null) {
					s.click(Patternise("MoreActions","Easy"),3);
					if(s.exists(ddnoption)!=null) {
						s.click(ddnoption,3);
					}
					else if(s.exists(ddnoption1)!=null)
					{
						s.click(ddnoption1,3);
					}
				}
				else {
					test.fail("MoreActions dropdown not found");
				}
				if(s.exists(window,5) != null || s.exists(window2,5) != null) {
					test.pass("Successfully launched "+type+" Window using more actions in "+Option+ " mode");
					if(s.exists(Patternise("WindowClose","Easy"),5) != null) {
						s.find(Patternise("WindowClose","Easy")).offset(7,0).click();
					}
				}
				else {
					test.fail("Unable to launch "+type+" Window using more actions in "+Option+" mode");
				}
				Thread.sleep(3000);
				break;
			case "COLUMN":
				if(s.exists(Patternise(type.toUpperCase(),"Easy")) != null ) {
					test.pass("Able to find column "+type+" in "+Option+ " mode");
				}
				else {
					test.fail("Column "+type+" not found in "+Option+ " mode");
				}
				break;
			case "ADDCOLUMN":
				s.find(Patternise("DATE","Easy")).rightClick();
				Thread.sleep(1000);
				s.wait(Patternise(type.toUpperCase(),"Easy")).click();
				if(s.exists(Patternise(type.toUpperCase()+"icon","Easy")) != null ) {
					test.pass("Able to find column "+type+" in "+Option+ " mode");
				}
				else {
					test.fail("Column "+type+" not found in "+Option+ " mode");
				}
				s.find(Patternise("DATE","Easy")).rightClick();
				Thread.sleep(1000);
				s.wait(Patternise(type.toUpperCase(),"Easy")).click();
				break;
			case "DIRECTSHORTCUT":
				
				break;
			default:
				test.fail("Invalid option passed- Pass either SHORTCUT or MOREACTION or COLUMN or ADDCOLUMN");
			}
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0"})
	@Test
	public static void Verify_Topics_FullSearch(String topic) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			SelectLiveFeedOrFullsearch("FULLSEARCH");
			s.find(Patternise("topiccodes","Easy")).offset(50,0).click();
			s.type(topic);
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			ClickFullSearchbutton();
			if(s.exists(Patternise("SearchResultBlue","Easy"),6) != null) {
				test.pass("Able to search with the topic code "+topic+" in Full Search");
			}
			else {
				test.fail("Unable to search with the topic code "+topic+"  in Full Search");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void VerifyPNAC_UCDP(String Country,String Feed, String RIC, String BriefPublish) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			SelectFeed(test,Country,Feed);
			if (s.exists(Patternise("SaveFeed","Easy"),3) != null) {
				s.find(GetProperty("SaveFeed")).click();
			}
			test.pass("Saved the user selected feed");
			Thread.sleep(1000);
			Verify_Alert_Publish("No","PUBLISH","TEST PUBLISH","TESTUSN");
			if(BriefPublish.toUpperCase().equals("YES")) {
					s.wait(Patternise("Brief","Strict"),8).click();
					s.wait(Patternise("PublishBrief","Strict"),8).click();
					if(s.exists(Patternise("ViewBrief","Strict"),20)!=null) {
						s.click(Patternise("ViewBrief","Strict"));
						if(s.exists(Patternise("PNACID","Strict"),10)!=null) {
							test.fail("PNAC ID is not generated for published Brief for UCDP Feed");
						}
						else {
							test.pass("PNAC ID is generated successfully for published Brief for UCDP Feed");
						}
					}
					else
					{
						test.fail("Published Brief (View Brief) not seen in Publish History");
					}
			}
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			ReverseFeedSelection(test,Country,Feed);
			s.click(Patternise("EnblFltrsSlctd","Exact"));
			test.pass("Enable Filters turned off");
			s.wait(GetProperty("SaveFeed"),5).click();
			test.pass("Reversed the changes made and saved");
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void VerifyMetadata_BAE(String Metadata,String Code, String mode, String Option) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"Preferences","DefaultCodesBAE");
			ManipulateBAEmetadata(Metadata,Code);
			s.find(GetProperty("SaveFeed")).click();
			test.pass("Saved the user entered data");
			Thread.sleep(1000);
			SelectLiveFeedOrFullsearch(mode);
			switch(Option) {
			case "MOREACTION":
				if(s.exists(Patternise("MoreActions","Easy"),5) != null) {
					s.click(Patternise("MoreActions","Easy"),3);
					s.click(Patternise("DDNOptnOpnBAE","Easy"),3);
				}
				else {
					test.fail("MoreActions dropdown not found");
				}
				break;
			case "SHORTCUT":
				s.keyDown(Key.SHIFT);
				s.keyDown(Key.F2);
				s.keyUp(Key.F2);
				s.keyUp(Key.SHIFT);
				Thread.sleep(3000);
				break;
			}
			if(s.exists(Patternise("BAE"+Code+Metadata,"Easy"),5) != null) {
				test.pass("Default "+Metadata+" code: "+Code+" found in Blank Alert Editor");
			}
			else {
				test.fail("Default "+Metadata+" code: "+Code+"  not found");
			}
			s.click(Patternise("FWTabCloseRed","Easy"),3);
			OpenUserPrfrncs(test,"Preferences","DefaultCodesBAE");
			ManipulateBAEmetadata(Metadata,"");
			test.pass("Deleted entered data");
			s.wait(GetProperty("SaveFeed"),5).click();
			test.pass("Reversed the changes made and saved");
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0","param1","param2"})
	@Test
	public static void VerifyFeedFilterCountryCode(String Feed,String Country, String Code) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			SelectFeed(test,Country,Feed);
			if (s.exists(Patternise("SaveFeed","Easy"),3) != null) {
				s.find(GetProperty("SaveFeed")).click();
			}
			test.pass("Saved the user selected feed");
			Thread.sleep(1000);
			OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
			s.find(Patternise("NoodlCntryCd","Moderate")).offset(100,15).click();
			for (int i=0;i<10;i++) {
				s.keyDown(Key.BACKSPACE);
				s.keyUp(Key.BACKSPACE);
			}
			test.pass("Cleared existing Country Codes");
			s.type(Code.toUpperCase());
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			s.wait(Patternise("Save","Strict"),5).click();
			RelaunchReopenFWTab(test,"Reopen");
			if (s.exists(Patternise("NOODLFilterby"+Code,"Moderate"),5)!=null){
				test.pass("Filter by Country Code "+Code + " seen on Story body");
			}
			else {
				test.fail("Filter by Country Code "+Code + " not seen on Story body");
			}
			//Reverse the changes
			OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
			s.find(Patternise("NoodlCntryCd","Moderate")).offset(100,15).click();
			for (int i=0;i<10;i++) {
				s.keyDown(Key.BACKSPACE);
				s.keyUp(Key.BACKSPACE);
			}
			test.pass("Cleared entered Country Code");
			s.wait(Patternise("Save","Strict"),5).click();
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			ReverseFeedSelection(test,Country,Feed);
			s.click(Patternise("EnblFltrsSlctd","Exact"));
			test.pass("Enable Filters turned off");
			s.wait(GetProperty("SaveFeed"),5).click();
			test.pass("Reversed the changes made and saved");
			
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1"})
	@Test
	public static void VerifyShortCmpnyName(String RIC,String Shortname) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int status,found=0;
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"Preferences","ShortCompanyNames");
			status=ManipulateShrtCmpnyData(RIC,Shortname,"DELETE");
			if (status==1) {
				s.find(GetProperty("SaveFeed")).click();
				OpenUserPrfrncs(test,"Preferences","ShortCompanyNames");
			}
			ManipulateShrtCmpnyData(RIC,Shortname,"ADD");
			Thread.sleep(3000);
			s.find(GetProperty("SaveFeed")).click();
			test.pass("Saved the user entered data");
			Thread.sleep(1000);
			OpenUserPrfrncs(test,"Preferences","ShortCompanyNames");
			ManipulateShrtCmpnyData(RIC,Shortname,"VERIFY");
			s.find(GetProperty("CancelShrtCmpnyName")).click();
			ClearMetaData();
			if (s.exists(Patternise("BlankRICS","Easy"),5) != null) {
					s.type(Patternise("BlankRICS","Easy"),RIC.replace("_", " "));
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					test.pass("Entered RIC in RIC Field");
					s.find(Patternise("RIC","Moderate")).offset(0,-20).click();
					for (int i=0;i<6;i++) {
							s.keyDown(Key.PAGE_UP);
							s.keyUp(Key.PAGE_UP);
					}
					for (int i=0;i<6;i++) {
							if (s.exists(Patternise(RIC+"AE","Moderate"))!=null){
										test.pass("Short Company Name Found in Alert Editor");
										found=1;
										for (int j=0;j<6;j++) {
											s.keyDown(Key.PAGE_UP);
											s.keyUp(Key.PAGE_UP);
										}
										break;
							}
							s.keyDown(Key.PAGE_DOWN);
							s.keyUp(Key.PAGE_DOWN);
					}	
					for (int i=0;i<6;i++) {
							s.keyDown(Key.PAGE_UP);
							s.keyUp(Key.PAGE_UP);
					}
					if(found==0) {
						test.fail("Short Company Name not found in Alert Editor");
					}
			}
			else {
					test.fail("RIC not blank, unable to enter in RIC field");
			}
			//Reverse the changes
			OpenUserPrfrncs(test,"Preferences","ShortCompanyNames");
			ManipulateShrtCmpnyData(RIC,Shortname,"DELETE");
			Thread.sleep(3000);
			s.find(GetProperty("SaveFeed")).click();
			test.pass("Saved the user entered data");
			Thread.sleep(1000);
			test.pass("Reversed the changes made and saved");
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void VerifyDefaultcodefeeds(String Country,String Feed, String Product, String Topic) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int status;
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"Preferences","DefaultCodesFeeds");
			status=ManipulateDefaultFeedsData(Feed,"DELETE", Product, Topic);
			if (status==1) {
				s.find(GetProperty("SaveLynxPreferencesDark")).click();
				OpenUserPrfrncs(test,"Preferences","DefaultCodesFeeds");
			}
			ManipulateDefaultFeedsData(Feed,"ADD", Product, Topic);
			Thread.sleep(3000);
			s.find(GetProperty("SaveLynxPreferencesLight")).click();
			test.pass("Saved the user entered data");
			Thread.sleep(1000);
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			SelectFeed(test,Country,Feed);
			if (s.exists(Patternise("SaveFeed","Easy"),3) != null) {
				s.find(GetProperty("SaveFeed")).click();
			}
			test.pass("Saved the user selected feed");
			Thread.sleep(1000);
			RelaunchReopenFWTab(test,"Reopen");
			if(s.exists(Patternise("NoHeadlines","Moderate"),4)!=null || s.exists(Patternise("NoHeadlinesHC","Moderate"),4)!=null ) {
				test.skip("No headlines found,unable to proceed");
			}
			else {
					if(s.exists(Patternise(Product+"_AE","Moderate"))!=null && s.exists(Patternise(Topic+"_AE","Moderate"))!=null) {
						test.pass("Default Product Code and Default Topic Code found");
					}
					else if(s.exists(Patternise(Product+"_AE","Moderate"))!=null && s.exists(Patternise(Topic+"_AE","Moderate"))==null) {
						test.fail("Default Product Code found, but default Topic Code not found");
					}
					else if(s.exists(Patternise(Product+"_AE","Moderate"))==null && s.exists(Patternise(Topic+"_AE","Moderate"))!=null) {
						test.fail("Default Topic Code found, but Default Product Code not found");
					}
					else {
						test.fail("Default Product Code and Default Topic Code not found");
					}
			}
			//Reversing the changes
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			ReverseFeedSelection(test,Country,Feed);
			s.click(Patternise("EnblFltrsSlctd","Exact"));
			test.pass("Enable Filters turned off");
			s.wait(GetProperty("SaveFeed"),5).click();
			OpenUserPrfrncs(test,"Preferences","DefaultCodesFeeds");
			ManipulateDefaultFeedsData(Feed,"DELETE", Product, Topic);
			Thread.sleep(3000);
			s.find(GetProperty("SaveLynxPreferencesDark")).click();
			test.pass("Reversed the changes made and saved");
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void VerifyIBESEPS_Estimate(String RIC, String CurrType, String Quarter, String Basket) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int X1coordinate,X2coordinate;
		try {
			RelaunchReopenFWTab(test,"Reopen");
			ClearMetaData();
			if (s.exists(Patternise("BlankRICS","Easy"),5) != null) {
				s.type(Patternise("BlankRICS","Easy"),RIC);
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
				test.pass("Entered RIC in RIC Field");
			}
			else {
				test.fail("RIC not blank, unable to enter in RIC field");
			}
			s.find(Patternise("RIC","Moderate")).offset(0,-20).click();
			for (int i=0;i<10;i++) {
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
			}
			s.click(Patternise("GetEstimates","Moderate"));
			test.pass("Clicked the Get Estimates button");
			Thread.sleep(2000);
			for (int i=0;i<10;i++) {
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
			}
			if(s.exists(Patternise(Quarter.toUpperCase()+"_Estimates","Moderate"))!=null && s.exists(Patternise(CurrType.toUpperCase()+"_Estimates","Moderate"))!=null) {
						X2coordinate=s.find(Patternise(Quarter.toUpperCase()+"_Estimates","Moderate")).getX();
						X1coordinate=s.find(Patternise(CurrType.toUpperCase()+"_Estimates","Moderate")).getX();
						//s.find(Patternise("GetEstimates","Easy")).offset(0,93).click();
						s.find(Patternise(CurrType.toUpperCase()+"_Estimates","Moderate")).right(X2coordinate-X1coordinate).click();
						Thread.sleep(3000);
						test.pass("Clicked the Quarter "+Quarter+" and Type "+CurrType+"cell");
						s.find(Patternise("GetEstimates","Easy")).offset(0,33).click();
						test.pass("Clicked the Publish button");
						for (int i=0;i<10;i++) {
							s.keyDown(Key.PAGE_UP);
							s.keyUp(Key.PAGE_UP);
						}
						Thread.sleep(3000);
						s.click(Patternise("Home1","Moderate"));
						Thread.sleep(3000);
						//Safer side paging up in case control is somewhere down
						s.find(Patternise("Home2","Moderate")).offset(0,57).click();
						for (int i=0;i<10;i++) {
							s.keyDown(Key.PAGE_UP);
							s.keyUp(Key.PAGE_UP);
						}
						s.type(Patternise("HomeSearch","Moderate"),Basket);
						test.pass("Entered the Basket Name "+Basket+" in Search button");
						Thread.sleep(3000);
						s.keyDown(Key.ENTER);
						s.keyUp(Key.ENTER);
						if (s.exists(Patternise(RIC.replace(".","")+"1","Easy"),5) != null || s.exists(Patternise(RIC.replace(".","")+"2","Easy"),5) != null) {
							test.pass("Published Alert found in Basket");
						}
						else {
							test.fail("Published Alert not found in Basket");
						}
			}
			else if(s.exists(Patternise(Quarter.toUpperCase()+"_Estimates","Moderate"))==null) {
				test.fail(" Entered Quarter doesnot exist in Estimate Table. Please check the quarter");
				for (int i=0;i<10;i++) {
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
				}
			}
			else if(s.exists(Patternise(CurrType.toUpperCase()+"_Estimates","Moderate"))==null) {
				test.fail(" Entered Type doesnot exist in Estimate Table. Please check the Type");
				for (int i=0;i<10;i++) {
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
				}
			}
			else {
				test.fail("Entered Quarter and Type doesnot exist in Estimate Table. Please check");
				for (int i=0;i<10;i++) {
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
				}
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1"})
	@Test
	public static void Verify_Auto_Alerts(String Country,String Feed) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			SelectFeed(test,Country,Feed);
			if (s.exists(Patternise("SaveFeed","Easy"),3) != null) {
				s.find(GetProperty("SaveFeed")).click();
			}
			test.pass("Saved the user selected feed");
			Thread.sleep(1000);
			RelaunchReopenFWTab(test,"Reopen");
			s.find(Patternise("StatusDdn","Moderate")).click();
			Thread.sleep(3000);
			s.find(Patternise("AutoAlerted","Moderate")).click();
			s.find(Patternise("ApplyButton","Moderate")).click();
			Thread.sleep(3000);
			s.find(Patternise("DATE","Moderate")).offset(0,50).click();
			Thread.sleep(3000);
			if(s.exists(Patternise("NoHeadlines","Moderate"),4)!=null || s.exists(Patternise("NoHeadlinesHC","Moderate"),4)!=null ) {
				test.skip("No headlines found,unable to proceed");
			}
			else {
					if(s.exists(Patternise("AutomatedAlerts","Moderate"),4)!=null) {
						test.pass("Automated alert found in publish history");
					}
					else {
						test.fail("Automated alert not found in publish history");
					}
			}
			//Reversing the changes
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			ReverseFeedSelection(test,Country,Feed);
			s.click(Patternise("EnblFltrsSlctd","Exact"));
			test.pass("Enable Filters turned off");
			s.wait(GetProperty("SaveFeed"),5).click();
			test.pass("Reversed the changes made and saved");
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1","param2", "param3", "param4"})
	@Test
	public static void VerifyCompanyList(String ListName,String RIC, String Privacy, String UpdateField, String UpdateData) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int status;
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"Preferences","CompanyListAdmin");
			status=ManipulateCompanyListData(ListName,"INITIALDELETE", RIC, Privacy, UpdateField, UpdateData);
			if (status==1) {
				s.find(GetProperty("SaveLynxPreferencesLight")).click();
				OpenUserPrfrncs(test,"Preferences","CompanyListAdmin");
			}
			ManipulateCompanyListData(ListName,"ADD", RIC, Privacy, UpdateField, UpdateData);
			Thread.sleep(3000);
			s.find(GetProperty("SaveLynxPreferencesLight")).click();
			test.pass("Saved the user entered data");
			Thread.sleep(1000);
			OpenUserPrfrncs(test,"Preferences","CompanyListAdmin");
			status=ManipulateCompanyListData(ListName,"VERIFY", RIC, Privacy, UpdateField, UpdateData);
			System.out.println("status "+status);
			if(status==0) {
				test.fail("Added Company List not found in Saved list, unable to proceed");
			}
			else {
					if(!UpdateField.toUpperCase().equals("N")) {
						status=ManipulateCompanyListData(ListName,"UPDATE", RIC, Privacy, UpdateField, UpdateData);
						s.wait(Patternise("SaveLynxPreferencesDark","Moderate"),3).click();
						test.pass("Updated and Saved the user entered new data");
						OpenUserPrfrncs(test,"Preferences","CompanyListAdmin");
						test.info("Verifying after update");
						status=ManipulateCompanyListData(ListName+"_UPDATED","VERIFYUPDATE", RIC, Privacy, UpdateField, UpdateData);
					}
					//Reversing the changes
					status=ManipulateCompanyListData(ListName,"DELETE", RIC, Privacy, UpdateField, UpdateData);
					if (status==1) {
						s.wait(GetProperty("SaveLynxPreferencesLight"),3).click();
					}
					else {
						s.click(Patternise("SaveLynxPreferencesDark","Moderate"),3);
						test.fail("Unable to delete added Company list");
					}
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void Validate_AEText_AfterMetadataUpdate(String MetaData,String MetadataType, String Mode, String SearchType) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			SelectLiveFeedOrFullsearch(SearchType);
			if(SearchType.toUpperCase().equals("FULLSEARCH")){
				ClickFullSearchbutton();
			}
			EnterAlert("DUMMY ALERT TEXT");
			if (s.exists(Patternise("BlankAEMetadata","Strict"),3)==null) {
				ClearMetaData();
			}
			switch(MetadataType.toUpperCase()) {
			case"RIC":
				s.wait(Patternise("BlankRICS","Strict"),5).click();
				break;
			case "PRODUCT":
				s.wait(Patternise("BlankProducts","Strict"),5).click();
				break;
			case "TOPIC":
				s.wait(Patternise("BlankTopics","Strict"),5).click();
				break;
			case "NAMEDITEM":
				s.wait(Patternise("BlankNamedItems","Strict"),5).click();
				break;
			}
			Thread.sleep(2000);
			EnterMetadata(MetaData);
			test.pass("Entered "+MetaData+" in " +MetadataType+" field");
			switch(Mode.toUpperCase()) {
			case "ADD":
				if(s.exists(Patternise("DummyAlert","Strict"),3)!=null) {
					test.pass("Alert text entered persists even after entering "+MetaData +" in " +MetadataType+" field");
				}
				else {
					test.fail("Alert text entered, not found after entering "+MetaData +" in " +MetadataType+" field");
				}
				break;
			case "DELETE":
				//Code for deleting
				for (int i=0;i<20;i++) {
					s.keyDown(Key.BACKSPACE);
					s.keyUp(Key.BACKSPACE);
				}				
				if(s.exists(Patternise("DummyAlert","Strict"),3)!=null) {
					test.pass("Alert text entered persists even after deleting data from " +MetadataType+" field");
				}
				else {
					test.fail("Alert text entered, not found after deleting data from" +MetadataType+" field");
				}
				break;
			}
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

	
	@Parameters({"param0"})
	@Test
	public static void VerifyStatusBasedHeadlines(String StatusOption) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(s.exists(Patternise("StatusDdn","Moderate"),5)!=null) {
				s.click(Patternise("StatusDdn","Moderate"),3);
				if(s.exists(Patternise(StatusOption,"Moderate"),5)!=null) {
					s.click(Patternise(StatusOption,"Moderate"),3);
					s.click(Patternise("ApplyButton","Moderate"),3);
					if(s.exists(Patternise("NoHeadlines","Moderate"),4)!=null || s.exists(Patternise("NoHeadlinesHC","Moderate"),4)!=null ) {
						test.skip("No headlines found,unable to proceed");
					}
					else {
						test.pass("Headlines filtered as per option "+StatusOption+" selected in Status dropdown");
					}
					s.click(Patternise("StatusDdn","Moderate"),3);
					s.click(Patternise("ShowAllHeadlines","Moderate"),3);
				}
				else {
					test.fail("Option "+StatusOption+" not found in Status dropdown");
				}
			}
			else {
				test.fail("Status dropdown not found");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

	@Test
	public static void VerifyKYDOHeadline() {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			SelectFeed(test,"Japan","KYDO");
			if (s.exists(Patternise("SaveFeed","Easy"),3) != null) {
				s.find(GetProperty("SaveFeed")).click();
			}
			test.pass("Saved the user selected feed");
			Thread.sleep(1000);
			RelaunchReopenFWTab(test,"Reopen");
			if(s.exists(Patternise("KYDOHeadline","Exact"),3)!=null)
			{
				test.pass("KYDO Headlines seen, Story list is filtered correctly");
			}
			else if(s.exists(Patternise("NoHeadlines","Moderate"),4)!=null || s.exists(Patternise("NoHeadlinesHC","Moderate"),4)!=null ) {
				test.skip("No headlines found,unable to proceed");
			}
			else {
				test.fail("Story list is not filtered with KYDO Headlines");
			}
			OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
			ReverseFeedSelection(test,"Japan","KYDO");
			s.click(Patternise("EnblFltrsSlctd","Exact"));
			test.pass("Enable Filters turned off");
			s.wait(GetProperty("SaveFeed"),5).click();
			test.pass("Reversed the changes made and saved");
	
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

	@Parameters({"param0"})
	@Test
	public static void VerifyMarketFeeds(String Market) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(s.exists(Patternise("Market","Moderate"),3)!=null) {
				s.click(Patternise("Market","Moderate"),3);
				test.pass("Clicked Market Dropdown");
				if(s.exists(Patternise(Market,"Moderate"),3)!=null) {
					s.click(Patternise(Market,"Moderate"),3);
					s.click(Patternise("Apply","Moderate"),3);
					Thread.sleep(4000);
					//Select in Feeds
					s.click(Patternise("FeedsDDN","Moderate"),3);
					Thread.sleep(4000);
					if(s.exists(Patternise("UncheckedBox","Strict"),3)!=null) {
						test.fail("Not All Feeds are selected for "+Market+" Market");
					}
					else {
						test.pass("All feeds shown as selected for "+Market+" Market");
					}
					//Reverse Selections
					s.click(Patternise("Market","Moderate"),3);
					s.click(Patternise("Market","Moderate"),3);
					s.click(Patternise(Market,"Moderate"),3);
					s.click(Patternise("Apply","Moderate"),3);
				}
				else {
					test.fail(" Market "+Market+" not found in market dropdown");
				}
			}
			else {
				test.fail("Markets dropdown not found");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

	@Parameters({"param0","param1"})
	@Test
	public static void VerifyAutoLaunchCases(String Autolaunch,String Option) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int i=0;
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
			ChangeAutolaunch(Autolaunch);
			s.click(Patternise("SaveFeed","Moderate"),3);
			switch(Option.toUpperCase()) {
			case "RELAUNCH":
							RelaunchReopenFWTab(test,"Relaunch");
							test.pass("Successfully launched fastwire tab with Autolaunch "+Autolaunch);
							break;
			case "VERIFYLANGUAGE":
							String[] finallanguage;
							int statuslang;
							OpenUserPrfrncs(test,"Preferences","Application");
							finallanguage=Option.split("VERIFYLANGUAGE");
							statuslang=SelectLanguage(test,finallanguage[0]);
							if(statuslang==2) {
								s.click(Patternise("Cancel","Strict"),5);
								s.click(Patternise("Cancel","Strict"),5);
								test.fail("Language "+finallanguage[0] + " not found in Default Language dropdown");
							}
							else if(statuslang==3) {
								s.click(Patternise("Cancel","Strict"),5);
								test.fail("Default Language dropdown not found");
							}
							else {
								s.wait(Patternise("Save","Strict"),5).click();
								test.pass("Default Language dropdown found and language "+finallanguage[0] + " selected and saved");
							}
							OpenUserPrfrncs(test,"Preferences","Application");
							if(s.exists(Patternise(finallanguage[0]+"Selected","Moderate"),3)!=null) {
								test.pass("Able to enter language "+finallanguage[0]);
							}
							else {
								test.fail("Unable to enter language "+finallanguage[0]);
							}
							// change to default language
							OpenUserPrfrncs(test,"Preferences","Application");
							finallanguage=Option.split("VERIFYLANGUAGE");
							statuslang=SelectLanguage(test,"EnglishUS");
							if(statuslang==2) {
								s.click(Patternise("Cancel","Strict"),5);
								s.click(Patternise("Cancel","Strict"),5);
								test.fail("Language "+finallanguage + " not found in Default Language dropdown");
							}
							else if(statuslang==3) {
								s.click(Patternise("Cancel","Strict"),5);
								test.fail("Default Language dropdown not found");
							}
							else {
								s.wait(Patternise("Save","Strict"),5).click();
								test.pass("Default Language dropdown found and language "+finallanguage + " selected and saved");
							}
							break;
			case "NEWTABSHORTCUT":
							s.keyDown(Key.CTRL);
							s.keyDown(Key.SHIFT);
							s.type("F");
							s.keyUp(Key.CTRL);
							s.keyUp(Key.SHIFT);
							if(s.exists(Patternise("Fastwiretabunfocused","Moderate"),5)!=null && s.exists(Patternise("Fastwiretab","Moderate"),5)!=null) {
								test.pass("Successfully opened a new Fastwire tab with Autolaunch on using shortcut");
							}
							else {
								test.fail("Unable to launch a new Fastwire tab with Autolaunch on using shortcut");
							}
							break;
			case "NEWTABMENU":
							s.click(Patternise("Newtab","Moderate"),3);
							s.click(Patternise("Open","Moderate"),3);
							s.click(Patternise("LynxFastwireOption","Moderate"),3);
							if(s.exists(Patternise("Fastwiretabunfocused","Moderate"),5)!=null && s.exists(Patternise("Fastwiretab","Moderate"),5)!=null) {
								test.pass("Successfully opened a new Fastwire tab with Autolaunch on using Menu option");
							}
							else {
								test.fail("Unable to launch a new Fastwire tab with Autolaunch on using Menu option");
							}
							break;
			case "FEEDFILTERS":
							SelectAutoLaunchfield("FEEDFILTERS");
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("FeedsDropdown","Moderate"),3);
							if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
								test.pass("Able to see user saved Feed after relaunch");
							}
							else {
								test.fail("Unable to see user saved Feed after relaunch");
							}
							SelectAutoLaunchfield("FEEDFILTERSREVERSE");
							break;
			case "MARKETS":
							SelectAutoLaunchfield("MARKETS");
							RelaunchReopenFWTab(test,"Relaunch");
							Thread.sleep(4000);
							s.click(Patternise("Market","Moderate"),3);
							if(s.exists(Patternise("MarketsChkd","Moderate"),5)!=null) {
								test.pass("Able to see user saved Market after relaunch");
							}
							else {
								test.fail("Unable to see user saved Market after relaunch");
							}
							s.click(Patternise("Market","Moderate"),3);
							SelectAutoLaunchfield("MARKETSREVERSE");
							break;
			case "COMPANYLIST":
							SelectAutoLaunchfield("COMPANYLIST");
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
								test.pass("Able to see user saved Company in Company List after relaunch");
							}
							else {
								test.fail("Unable to see user saved Company in Company List after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("COMPANYLISTREVERSE");
							break;	
			case "AUTOMATIONS":
							SelectAutoLaunchfield("AUTOMATIONS");
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							break;				
			case "MARKETFEED":
							SelectAutoLaunchfield("MARKETS");
							RelaunchReopenFWTab(test,"Relaunch");
							Thread.sleep(4000);
							s.click(Patternise("Market","Moderate"),3);
							s.click(Patternise("FeedsDdn","Moderate"),3);
							if(s.exists(Patternise("MarketsChkd","Moderate"),5)!=null && s.exists(Patternise("ASXddnSelected","Moderate"),5)!=null) {
								test.pass("Able to see user saved Market and Feeds after relaunch");
							}
							else {
								test.fail("Unable to see user saved Market and Feeds after relaunch");
							}
							SelectAutoLaunchfield("MARKETSREVERSE");
							break;
			case "WEBWATCH":
							SelectAutoLaunchfield("WEBWATCH");
							RelaunchReopenFWTab(test,"Relaunch");
							Thread.sleep(4000);
							OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
							if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
								test.pass("Able to see user saved Web Watchers after relaunch");
							}
							else {
								test.fail("Unable to see user saved Web Watchers after relaunch");
							}
							SelectAutoLaunchfield("WEBWATCHREVERSE");
							break;
			case "MARKETWEBWATCH":
							SelectAutoLaunchfield("MARKETS");
							SelectAutoLaunchfield("WEBWATCH");
							RelaunchReopenFWTab(test,"Relaunch");
							Thread.sleep(4000);
							s.click(Patternise("Market","Moderate"),3);
							if(s.exists(Patternise("MarketsChkd","Moderate"),5)!=null) {
								test.pass("Able to see user saved Market after relaunch");
							}
							else {
								test.fail("Unable to see user saved Market after relaunch");
							}
							s.click(Patternise("Market","Moderate"),3);
							SelectAutoLaunchfield("MARKETSREVERSE");
							OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
							if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
								test.pass("Able to see user saved Web Watchers after relaunch");
							}
							else {
								test.fail("Unable to see user saved Web Watchers after relaunch");
							}
							SelectAutoLaunchfield("WEBWATCHREVERSE");
							break;
			case "FEEDWEBWATCH":
							SelectAutoLaunchfield("FEEDFILTERS");
							SelectAutoLaunchfield("WEBWATCH");
							RelaunchReopenFWTab(test,"Relaunch");
							Thread.sleep(4000);
							s.click(Patternise("FeedsDropdown","Moderate"),3);
							if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
								test.pass("Able to see user saved Feed after relaunch");
							}
							else {
								test.fail("Unable to see user saved Feed after relaunch");
							}
							OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
							if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
								test.pass("Able to see user saved Web Watchers after relaunch");
							}
							else {
								test.fail("Unable to see user saved Web Watchers after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("FEEDFILTERSREVERSE");
							SelectAutoLaunchfield("WEBWATCHREVERSE");
							break;
			case "COMPANYWEBWATCH":
							SelectAutoLaunchfield("COMPANYLIST");
							SelectAutoLaunchfield("WEBWATCH");
							RelaunchReopenFWTab(test,"Relaunch");
							Thread.sleep(4000);
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
								test.pass("Able to see user saved Company in Company List after relaunch");
							}
							else {
								test.fail("Unable to see user saved Company in Company List after relaunch");
							}
							OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
							if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
								test.pass("Able to see user saved Web Watchers after relaunch");
							}
							else {
								test.fail("Unable to see user saved Web Watchers after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("COMPANYLISTREVERSE");
							SelectAutoLaunchfield("WEBWATCHREVERSE");
							break;
			case "MARKETFEEDCOMPANY":
							SelectAutoLaunchfield("MARKETS");			
							SelectAutoLaunchfield("COMPANYLIST");
							//Check After Relaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),5);
							if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
								test.pass("Able to see user saved Company in Company List after relaunch");
							}
							else {
								test.fail("Unable to see user saved Company in Company List after relaunch");
							}
							s.click(Patternise("FeedsDdn","Moderate"),3);
							s.click(Patternise("FeedsDdn","Moderate"),3);
							if(s.exists(Patternise("MarketsChkd","Moderate"),5)!=null && s.exists(Patternise("ASXddnSelected","Moderate"),5)!=null ) {
								test.pass("Able to see user saved Market and Feeds after relaunch");
							}
							else {
								test.fail("Unable to see user saved Market and Feeds after relaunch");
							}
							//Reversing the changes
							s.click(Patternise("Market","Moderate"),3);
							SelectAutoLaunchfield("MARKETSREVERSE");
							SelectAutoLaunchfield("COMPANYLISTREVERSE");
							break;
			case "AUTOMATIONSWEBWATCH":
							SelectAutoLaunchfield("AUTOMATIONS");
							SelectAutoLaunchfield("WEBWATCH");
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
							if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
								test.pass("Able to see user saved Web Watchers after relaunch");
							}
							else {
								test.fail("Unable to see user saved Web Watchers after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("WEBWATCHREVERSE");
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							break;
			case "MARKETAUTOMATIONS":
							SelectAutoLaunchfield("MARKETS");			
							SelectAutoLaunchfield("AUTOMATIONS");
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							if(s.exists(Patternise("MarketsChkd","Moderate"),5)!=null) {
								test.pass("Able to see user saved Market after relaunch");
							}
							else {
								test.fail("Unable to see user saved Market after relaunch");
							}
							//Reversing the changes
							s.click(Patternise("Market","Moderate"),3);
							SelectAutoLaunchfield("MARKETSREVERSE");
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							break;
			case "MARKETAUTOMATIONSWEBWATCH":
							SelectAutoLaunchfield("MARKETS");			
							SelectAutoLaunchfield("AUTOMATIONS");
							SelectAutoLaunchfield("WEBWATCH");
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							if(s.exists(Patternise("MarketsChkd","Moderate"),5)!=null ) {
								test.pass("Able to see user saved Market after relaunch");
							}
							else {
								test.fail("Unable to see user saved Market after relaunch");
							}
							s.click(Patternise("Market","Moderate"),3);
							SelectAutoLaunchfield("MARKETSREVERSE");
							OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
							if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
								test.pass("Able to see user saved Web Watchers after relaunch");
							}
							else {
								test.fail("Unable to see user saved Web Watchers after relaunch");
							}
							//Reversing the changes
							
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							SelectAutoLaunchfield("WEBWATCHREVERSE");
							break;
				
			case "MARKETCOMPANY":
							SelectAutoLaunchfield("MARKETS");			
							SelectAutoLaunchfield("COMPANYLIST");
							//Check After Relaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
								test.pass("Able to see user saved Company in Company List after relaunch");
							}
							else {
								test.fail("Unable to see user saved Company in Company List after relaunch");
							}
							if(s.exists(Patternise("MarketsChkd","Moderate"),5)!=null) {
								test.pass("Able to see user saved Market after relaunch");
							}
							else {
								test.fail("Unable to see user saved Market after relaunch");
							}
							//Reversing the changes
							s.click(Patternise("Market","Moderate"),3);
							SelectAutoLaunchfield("MARKETSREVERSE");
							SelectAutoLaunchfield("COMPANYLISTREVERSE");
							break;
			case "FEEDCOMPANY":
							SelectAutoLaunchfield("COMPANYLIST");
							SelectAutoLaunchfield("FEEDFILTERS");
							//Check After Relaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
								test.pass("Able to see user saved Company in Company List after relaunch");
							}
							else {
								test.fail("Unable to see user saved Company in Company List after relaunch");
							}
							s.click(Patternise("FeedsDropdown","Moderate"),3);
							s.click(Patternise("FeedsDropdown","Moderate"),3);
							if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
								test.pass("Able to see user saved Feed after relaunch");
							}
							else {
								test.fail("Unable to see user saved Feed after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("FEEDFILTERSREVERSE");
							SelectAutoLaunchfield("COMPANYLISTREVERSE");
							break;
			case "FEEDAUTOMATIONS":
							SelectAutoLaunchfield("AUTOMATIONS");
							SelectAutoLaunchfield("FEEDFILTERS");
							//Check After Relaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							s.click(Patternise("FeedsDropdown","Moderate"),3);
							s.click(Patternise("FeedsDropdown","Moderate"),3);
							if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
								test.pass("Able to see user saved Feed after relaunch");
							}
							else {
								test.fail("Unable to see user saved Feed after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							SelectAutoLaunchfield("FEEDFILTERSREVERSE");
							break;
			case "FEEDAUTOMATIONSWEBWATCH":
							SelectAutoLaunchfield("AUTOMATIONS");
							SelectAutoLaunchfield("FEEDFILTERS");
							SelectAutoLaunchfield("WEBWATCH");
							//Check After Relaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							s.click(Patternise("FeedsDropdown","Moderate"),3);
							s.click(Patternise("FeedsDropdown","Moderate"),3);
							if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
								test.pass("Able to see user saved Feed after relaunch");
							}
							else {
								test.fail("Unable to see user saved Feed after relaunch");
							}
							OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
							if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
								test.pass("Able to see user saved Web Watchers after relaunch");
							}
							else {
								test.fail("Unable to see user saved Web Watchers after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							SelectAutoLaunchfield("FEEDFILTERSREVERSE");
							SelectAutoLaunchfield("WEBWATCHREVERSE");
							break;
			case "COMPANYAUTOMATIONS":
							SelectAutoLaunchfield("AUTOMATIONS");
							SelectAutoLaunchfield("COMPANYLIST");
							//Check After Relaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
								test.pass("Able to see user saved Company in Company List after relaunch");
							}
							else {
								test.fail("Unable to see user saved Company in Company List after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							SelectAutoLaunchfield("COMPANYLISTREVERSE");
							break;
			case "COMPANYAUTOMATIONSWEBWATCH":
							SelectAutoLaunchfield("AUTOMATIONS");
							SelectAutoLaunchfield("COMPANYLIST");
							SelectAutoLaunchfield("WEBWATCH");
							//Check After Relaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
								test.pass("Able to see user saved Company in Company List after relaunch");
							}
							else {
								test.fail("Unable to see user saved Company in Company List after relaunch");
							}
							OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
							if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
								test.pass("Able to see user saved Web Watchers after relaunch");
							}
							else {
								test.fail("Unable to see user saved Web Watchers after relaunch");
							}
							//Reversing the changes
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							SelectAutoLaunchfield("COMPANYLISTREVERSE");
							SelectAutoLaunchfield("WEBWATCHREVERSE");
							break;
			case "MARKETCOMPANYAUTOMATIONS":
							SelectAutoLaunchfield("MARKETS");			
							SelectAutoLaunchfield("AUTOMATIONS");
							SelectAutoLaunchfield("COMPANYLIST");
							//Check After Relaunch
							RelaunchReopenFWTab(test,"Relaunch");
							s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
								test.pass("Able to see user saved Automation in Automation Dropdown after relaunch");
							}
							else {
								test.fail("Unable to see user saved Automation in Automation Dropdown after relaunch");
							}
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
							if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
								test.pass("Able to see user saved Company in Company List after relaunch");
							}
							else {
								test.fail("Unable to see user saved Company in Company List after relaunch");
							}
							if(s.exists(Patternise("MarketsChkd","Moderate"),5)!=null) {
								test.pass("Able to see user saved Market after relaunch");
							}
							else {
								test.fail("Unable to see user saved Market after relaunch");
							}
							//Reversing the changes
							s.click(Patternise("Market","Moderate"),3);
							SelectAutoLaunchfield("MARKETSREVERSE");
							SelectAutoLaunchfield("AUTOMATIONSREVERSE");
							SelectAutoLaunchfield("COMPANYLISTREVERSE");
							break;
			case "PUBLISHBUTTON":
							Verify_Alert_Publish("No","PUBLISH","TEST PUBLISH","TESTUSN");
							break;
			case "PUBLISHSHORTCUT":
							ClearMetaData();
							s.wait(Patternise("BlankRICS","Easy"),5).click();
							Thread.sleep(2000);
							EnterMetadata("H.N");
							test.pass("Entered RIC");
							EnterAlert("TEST PUBLISH");
							s.keyDown(Key.SHIFT);
							s.keyDown(Key.F12);
							s.keyUp(Key.F12);
							s.keyUp(Key.SHIFT);
							test.pass("Entered Shortcut for Publish Button");
							NavigatetoPublishHistory();
							if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)) {
								test.pass("Alert successfully Published using shortcut");
							}
							else {
									test.fail("Alert not Published using shortcut");
							}
							break;
			case "TWOPUBLISHSHORTCUT":
							ClearMetaData();
							s.wait(Patternise("BlankRICS","Easy"),5).click();
							Thread.sleep(2000);
							EnterMetadata("H.N");
							test.pass("Entered RIC");
							EnterAlert("TEST PUBLISH");
							s.keyDown(Key.SHIFT);
							s.keyDown(Key.F12);
							s.keyUp(Key.F12);
							s.keyUp(Key.SHIFT);
							test.pass("Entered Shortcut for Publish Button");
							NavigatetoPublishHistory();
							if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)) {
								test.pass("First Alert successfully Published using shortcut");
							}
							else {
									test.fail("First Alert not Published using shortcut");
							}
							EnterAlert("TEST PUBLISH");
							s.keyDown(Key.SHIFT);
							s.keyDown(Key.F12);
							s.keyUp(Key.F12);
							s.keyUp(Key.SHIFT);
							test.pass("Entered Shortcut for Publish Button");
							NavigatetoPublishHistory();
							if((s.exists(Patternise("PublishedAlert","Strict"),5)!=null || s.exists(Patternise("PublishedAlert_Unselected","Strict"),5)!=null)) {
								test.pass("Second Alert successfully Published using shortcut");
							}
							else {
									test.fail("Second Alert not Published using shortcut");
							}
							break;
				case "DEALTWITHBUTTON":
							s.wait(Patternise("AlertEditorTab","Strict"),3).click();
							s.wait(Patternise("Dealtwith","Moderate"),3).click();
							if((s.exists(Patternise("CancelDealtwith","Strict"),5)!=null)) {
								test.pass("Successfully able to click Dealtwith button");
								s.wait(Patternise("CancelDealtwith","Moderate"),3).click();
							}
							else {
									test.fail("Unable to click on Dealtwith button");
							}
							break;
				case "DEALTWITHSHORTCUT":
							s.wait(Patternise("AlertEditorTab","Strict"),3).click();
							s.keyDown(Key.F8);
							s.keyUp(Key.F8);
							if((s.exists(Patternise("CancelDealtwith","Strict"),5)!=null)) {
								test.pass("Successfully able to do Dealtwith using shortcut");
								s.wait(Patternise("CancelDealtwith","Moderate"),3).click();
							}
							else {
									test.fail("Unable to do Dealtwith using shortcut");
							}
							break;
				case "CANCELDEALTWITHBUTTON":
							s.wait(Patternise("AlertEditorTab","Strict"),3).click();
							s.wait(Patternise("Dealtwith","Moderate"),3).click();
							s.wait(Patternise("CancelDealtwith","Moderate"),3).click();
							if((s.exists(Patternise("Dealtwith","Strict"),5)!=null)) {
								test.pass("Successfully able to click CancelDealtwith button");
							}
							else {
									test.fail("Unable to click on CancelDealtwith button");
							}
							break;
				case "CANCELDEALTWITHSHORTCUT":
							s.wait(Patternise("AlertEditorTab","Strict"),3).click();
							s.wait(Patternise("Dealtwith","Moderate"),3).click();
							Thread.sleep(3000);
							s.keyDown(Key.SHIFT);
							s.keyDown(Key.F8);
							s.keyUp(Key.F8);
							s.keyUp(Key.SHIFT);
							if((s.exists(Patternise("Dealtwith","Strict"),5)!=null)) {
								test.pass("Successfully able to do CancelDealtwith using shortcut");
							}
							else {
									test.fail("Unable to do CancelDealtwith using shortcut");
							}
							break;
			}
			//Reversing changes turning autolaunch off
			OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
			ChangeAutolaunch("OFF");
			s.click(Patternise("SaveFeed","Moderate"),3);
			test.pass("Reversed the changes made and saved");
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void VerifyGotoField(String Autolaunch,String Field,String Mode,String Area) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int found=0;
		String Shortcut="",ClickField="";
		try {
			RelaunchReopenFWTab(test,"Reopen");
			if(Autolaunch.toUpperCase().equals("AUTOLAUNCH")) {
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("ON");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			//Open BAE
			if(Area.toUpperCase().equals("BAE")) {
				if(s.exists(Patternise("MoreActions","Easy"),5) != null) {
					s.click(Patternise("MoreActions","Easy"),3);
					s.click(Patternise("DDNOptnOpnBAE","Easy"),3);
					test.pass("Clicked Open BAE window option");
				}
				else {
					test.fail("MoreActions dropdown not found");
				}
			}
			switch(Field.toUpperCase().trim()) {
					case"PRODUCTS":
						Shortcut="u";
						ClickField="BlankProducts";
						break;
					case"TOPICS":
						Shortcut="o";
						ClickField="BlankTopics";
						break;
					case"RICS":
						Shortcut="i";
						ClickField="BlankRICS";
						break;
					case"NAMEDITEMS":
						Shortcut="h";
						ClickField="BlankNamedItems";
						break;
					case"USN":
						Shortcut="s";
						ClickField="BlankUSN";
						break;
					case "else":
						test.fail("Enter valid Metadata Field value to act on");
					
					}
			switch(Mode.toUpperCase().trim()){
			case"SHORTCUT":
				s.type(Shortcut,Key.ALT);
				if (Field.toUpperCase().trim()=="NAMEDITEMS") {
					s.type("N");
				}
				s.type("TESTIT");
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
				if(s.exists(Patternise("TESTIT","Moderate"),5) != null || s.exists(Patternise("TESTIT_1","Moderate"),5) != null || s.exists(Patternise("TESTIT_2","Moderate"),5) != null) {
					test.pass("Able to goto field"+Field.toUpperCase().trim()+" using shortcut");
				}
				else {
					test.fail("Unable to goto field"+Field.toUpperCase().trim()+" using shortcut");
				}
				break;
			case "CLICK":
				//Add code to do click on respective field
				if(!Area.toUpperCase().equals("BAE")) {
					ClearMetaData();
				}
				s.click(Patternise(ClickField,"Moderate"),3);
				s.type("TESTIT");
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
				if(s.exists(Patternise("TESTIT","Moderate"),5) != null || s.exists(Patternise("TESTIT_1","Moderate"),5) != null || s.exists(Patternise("TESTIT_2","Moderate"),5) != null) {
					test.pass("Able to goto field"+Field.toUpperCase().trim()+" using click");
				}
				else {
					test.fail("Unable to goto field"+Field.toUpperCase().trim()+" using click");
				}
				break;
			}
			//Reversing changes made
			if(Area.toUpperCase().equals("BAE")) {
				s.click(Patternise("FWTabCloseRed","Moderate"),3);
			}
			if(Autolaunch.toUpperCase().equals("AUTOLAUNCH")) { 
				OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
				ChangeAutolaunch("OFF");
				s.click(Patternise("SaveFeed","Moderate"),3);
			}
			
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	public static void SelectAutoLaunchfield(String Option) {
		try {
				switch(Option.toUpperCase()) {
				case "FEEDFILTERS":
									OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
									SelectFeed(test,"Japan","KYDO");
									if (s.exists(Patternise("SaveFeed","Easy"),3) != null) {
										s.find(GetProperty("SaveFeed")).click();
									}
									test.pass("Saved the user selected feed");
									Thread.sleep(1000);
									break;
				case "FEEDFILTERSREVERSE":
									OpenUserPrfrncs(test,"FastwirePreferences","Feeds");
									ReverseFeedSelection(test,"Japan","KYDO");
									s.click(Patternise("EnblFltrsSlctd","Exact"));
									test.pass("Enable Filters turned off");
									s.wait(GetProperty("SaveFeed"),5).click();
									break;
				case "MARKETS":
									s.click(Patternise("Market","Moderate"),3);
									s.click(Patternise("Australia","Moderate"),3);
									s.click(Patternise("Apply","Moderate"),3);
									Thread.sleep(4000);
									test.pass("Selected Australia Marked and applied the filter");
									break;
				case "MARKETSREVERSE":	
									s.click(Patternise("Market","Moderate"),3);				
									if(s.exists(Patternise("AustraliaChkd","Strict"),3)!=null) {
										s.click(Patternise("AustraliaChkd","Strict"),3);
										s.click(Patternise("Apply","Moderate"),3);
									}
									break;
				case "COMPANYLIST":
									int status=0;
									OpenUserPrfrncs(test,"Preferences","CompanyListAdmin");
									status=ManipulateCompanyListData("1TestCompanyAdmin","VERIFY", "RE.N", "PUBLIC","N","N");
									if (status==1) {
										s.find(GetProperty("SaveLynxPreferencesDark")).click();
									}
									else {
									ManipulateCompanyListData("1TestCompany","ADD",  "RE.N", "PUBLIC","N","N");
									Thread.sleep(3000);
									s.find(GetProperty("SaveLynxPreferencesLight")).click();
									test.pass("Saved the user entered data");
									Thread.sleep(1000);
									}
									OpenUserPrfrncs(test,"FastwirePreferences","CompanyLists");
									if(s.exists(Patternise("1TestCompanyPrfrncs","Exact"),5)!=null) {
										s.click(Patternise("1TestCompanyPrfrncs","Exact"));
										if(s.exists(Patternise("Checkboxoff","Exact"),5)!=null) {
											s.click(Patternise("Checkboxoff","Exact"));
											test.pass("Turned on company filtering for 1TestCompany");
											s.click(Patternise("SaveCompanyLists","Moderate"),5);
										}
										else if(s.exists(Patternise("Checkboxon","Exact"),5)!=null) {
											test.pass("Company filtering already on for 1TestCompany");
										}
										else {
											test.fail("Filtering Checkbox not found for 1TestCompany");
										}
									}
									else {
										test.fail("1TestCompany not found");
									}
									break;
				case "COMPANYLISTREVERSE":
									//Reversing the changes
									OpenUserPrfrncs(test,"FastwirePreferences","CompanyLists");
									if(s.exists(Patternise("1TestCompanyPrfrncs","Moderate"),5)!=null) {
										s.click(Patternise("1TestCompanyPrfrncs","Exact"));
										if(s.exists(Patternise("Checkboxon","Exact"),5)!=null) {
											while(s.exists(Patternise("Checkboxon","Exact"))!=null) {
												s.click(Patternise("Checkboxon","Exact"));
												Thread.sleep(3000);
											}
											test.pass("Turned off company filtering for 1TestCompany");
											s.click(Patternise("SaveCompanyLists","Moderate"),5);
										}
										else if(s.exists(Patternise("Checkboxoff","Moderate"),5)!=null) {
											test.pass("Company filtering already off for 1TestCompany");
										}
										else {
											test.fail("Filtering Checkbox not found for 1TestCompany");
										}
									}
									else {
										test.fail("1TestCompany not found");
									}
									break;
				case "AUTOMATIONS":
									OpenUserPrfrncs(test,"FastwirePreferences","Automations");
									ClearAutomationSelection(test,50);
									EnterAutomation(test,"AustrianCPI");
									Thread.sleep(1000);
									s.find(GetProperty("SVEnbld")).click();
									test.pass("Clicked Save button");
									ValidateAutomationSave(test,"AustrianCPI");
									break;
				case "AUTOMATIONSREVERSE":
									OpenUserPrfrncs(test,"FastwirePreferences","Automations");
									ClearAutomationSelection(test,50);
									Thread.sleep(4000);
									if(s.exists(Patternise("NoAutomations","Moderate"),5)!=null) {
										test.pass("Reversed the changes made and saved");
									}
									else {
										test.fail("Reversal and saving Failed.");
									}
									break;
				case "WEBWATCH":
									OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
									ClearWebWatchSelection(test,50);
									Thread.sleep(4000);
									EnterWebWatcher(test,"vagazette.com");
									Thread.sleep(1000);
									s.find(GetProperty("SVEnbld")).click();
									test.pass("Clicked Save button");
									break;
				case "WEBWATCHREVERSE":
									OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
									ClearWebWatchSelection(test,50);
									Thread.sleep(4000);
									if(s.exists(Patternise("NoWebWatchers","Moderate"),5)!=null) {
										test.pass("Reversed the changes made and saved");
									}
									else {
										test.fail("Reversal and saving Failed.");
									}
					break;
				}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	
	public static void ChangeAutolaunch(String Option) {
		try {
				switch(Option.toUpperCase()) {
				case "ON":
					if (s.exists(Patternise("AutoLaunchFastwireon","Strict"),3)!=null){
						test.pass("Auto launch Fastwire already on");
					}
					else if (s.exists(Patternise("AutoLaunchFastwireoff","Strict"),3)!=null){
						s.click(Patternise("AutoLaunchFastwireoff","Strict"),3);
						test.pass("Clicked on Auto launch Fastwire, turning it on");
					}
					else {
						test.fail("Auto Launch Fastwire checkbox not found");
					}
					break;
				case "OFF":
					
					if (s.exists(Patternise("AutoLaunchFastwireoff","Strict"),3)!=null){
						test.pass("Auto launch Fastwire already off");
					}
					else if (s.exists(Patternise("AutoLaunchFastwireon","Strict"),3)!=null){
						s.click(Patternise("AutoLaunchFastwireon","Strict"),3);
						test.pass("Clicked on Auto launch Fastwire, turning it off");
					}
					else {
						test.fail("Auto Launch Fastwire checkbox not found");
					}
					break;
				}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	
	public static int ManipulateCompanyListData(String ListName,String Mode, String RIC, String Privacy, String UpdateField, String UpdateData) {
		try {
			switch(Mode.toUpperCase()) {
			case "ADD":
				if (s.exists(Patternise("NewDefaultCodeFeed","Moderate"),5)!=null){
					s.click(Patternise("NewDefaultCodeFeed","Moderate"),3);
					s.type(Patternise("CompanyListName","Moderate"),ListName);
					test.pass("Entered the Company List name");
					if(Privacy.equals("PRIVATE")) {
						s.wait(Patternise("PrivacyDDN","Moderate"),3).right(10).click();
						s.click(Patternise(Privacy.toUpperCase()+"_DDNOption","Moderate"),3);
						test.pass("Selected Privacy as "+Privacy);
					}
					s.type(Patternise("CompanyNameinList","Moderate"),RIC);
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					test.pass("Entered the RIC");
					s.click(Patternise("AddShrtcmpny","Moderate"),3);
					test.pass("Clicked Add Button");
				}
				else {
					test.fail("New button not found");
				}
				break;
			case "UPDATE":
//				s.find(Patternise("NewDefaultCodeFeed","Moderate")).offset(0,-50).click();
//				for (int i=0;i<20;i++) {
//					s.keyDown(Key.PAGE_UP);
//					s.keyUp(Key.PAGE_UP);
//				}
//				s.mouseMove(Patternise("NewDefaultCodeFeed","Moderate").targetOffset(0,15));
//				for (int i=0;i<20;i++) {
//					if (s.exists(Patternise(ListName.toUpperCase()+"_COMPANY","Moderate"))!=null){
//						s.click(Patternise(ListName.toUpperCase()+"_COMPANY","Moderate"));
						switch(UpdateField.toUpperCase()) {
						case "NAME":
							s.wait(Patternise("CompanyListNameLabel","Moderate"),5).right(20).click();
							s.type("a", Key.CTRL);
							s.keyDown(Key.DELETE);
							s.keyUp(Key.DELETE);
							s.type(UpdateData);
							s.wait(Patternise("UpdateButton","Moderate"),5).click();
							test.pass("Entered the Updated Company List Name "+UpdateData);
							break;
						case "COMPANY":
							s.wait(Patternise("CompanyNameinList","Moderate"),5).click();
							for (int j=0;j<10;j++) {
								s.keyDown(Key.BACKSPACE);
								s.keyUp(Key.BACKSPACE);
							}
							s.type(UpdateData);
							s.keyDown(Key.ENTER);
							s.keyUp(Key.ENTER);
							s.wait(Patternise("UpdateButton","Moderate"),5).click();
							test.pass("Entered the Updated Company "+UpdateData);
							break;
						}
						return 1;
//					}
//					s.keyDown(Key.PAGE_DOWN);
//					s.keyUp(Key.PAGE_DOWN);
//					s.keyDown(Key.PAGE_DOWN);
//					s.keyUp(Key.PAGE_DOWN);
//					s.keyDown(Key.PAGE_UP);
//					s.keyUp(Key.PAGE_UP);
//				}
//				break;
			case "DELETE":
				s.click(Patternise("DeleteShrtcmpny","Moderate"),3);
				s.click(Patternise("DeleteConfirmYes","Moderate"),5);
				test.pass("Deleted the CompanyList from Saved list");
				return 1;
				
			case "INITIALDELETE":
				s.wait(Patternise("NewDefaultCodeFeed","Moderate"),3).offset(0,-50).click();
				for (int i=0;i<20;i++) {
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
				}
				s.mouseMove(Patternise("NewDefaultCodeFeed","Moderate").targetOffset(0,15));
				for (int i=0;i<20;i++) {
					if (s.exists(Patternise(ListName.toUpperCase()+"_COMPANY","Strict"),5)!=null){
						s.click(Patternise(ListName.toUpperCase()+"_COMPANY","Strict"),3);
						s.click(Patternise("DeleteShrtcmpny","Moderate"),3);
						s.click(Patternise("DeleteConfirmYes","Moderate"),5);
						test.pass("Deleted the CompanyList from Saved list");
						return 1;
					}
					else if (s.exists(Patternise(ListName.toUpperCase()+"_COMPANY_BLUE","Strict"),5)!=null){
						s.wait(Patternise(ListName.toUpperCase()+"_COMPANY_BLUE","Strict"),3).click();
						s.click(Patternise("DeleteShrtcmpny","Moderate"),3);
						s.click(Patternise("DeleteConfirmYes","Moderate"),5);
						test.pass("Deleted the CompanyList from Saved list");
						return 1;
					}
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
					
				}
				break;
			case "VERIFY":
				s.wait(Patternise("NewDefaultCodeFeed","Moderate"),3).offset(0,-50).click();
				for (int i=0;i<20;i++) {
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
				}
				s.mouseMove(Patternise("NewDefaultCodeFeed","Moderate").targetOffset(0,15));
				for (int i=0;i<20;i++) {
						if (s.exists(Patternise(ListName.toUpperCase()+"_COMPANY","Moderate"),3)!=null){
								test.pass("CompanyList "+ListName +" found in normal Saved list");
								s.click(Patternise(ListName.toUpperCase()+"_COMPANY","Strict"),3);
								return 1;
						}
						else if (s.exists(Patternise(ListName.toUpperCase()+"_COMPANY_BLUE","Moderate"),3)!=null){
							test.pass("CompanyList "+ListName +" found in blue Saved list");
							s.wait(Patternise(ListName.toUpperCase()+"_COMPANY_BLUE","Strict"),3).click();
							return 1;
						}
						s.keyDown(Key.PAGE_DOWN);
						s.keyUp(Key.PAGE_DOWN);
						s.keyDown(Key.PAGE_DOWN);
						s.keyUp(Key.PAGE_DOWN);
						s.keyDown(Key.PAGE_UP);
						s.keyUp(Key.PAGE_UP);
				}
				break;
			case "VERIFYUPDATE":
							s.wait(Patternise("NewDefaultCodeFeed","Moderate"),3).offset(0,-50).click();
							for (int i=0;i<20;i++) {
								s.keyDown(Key.PAGE_UP);
								s.keyUp(Key.PAGE_UP);
							}
							s.mouseMove(Patternise("NewDefaultCodeFeed","Moderate").targetOffset(0,15));
							for (int i=0;i<20;i++) {
								switch(UpdateField.toUpperCase()) {
									case "NAME":
										if (s.exists(Patternise(UpdateData.toUpperCase()+"_COMPANY","Moderate"),3)!=null){
											test.pass("Updated Name "+UpdateData +" found in Saved list");
											s.click(Patternise(UpdateData.toUpperCase()+"_COMPANY","Strict"),3);
											return 1;
										}
										else if (s.exists(Patternise(UpdateData.toUpperCase()+"_COMPANY_BLUE","Moderate"),3)!=null){
											test.pass("Updated Name "+UpdateData +" found in Saved list");
											s.wait(Patternise(UpdateData.toUpperCase()+"_COMPANY_BLUE","Strict"),3).click();
											return 1;
										}
										break;
									case "COMPANY":
										if (s.exists(Patternise(ListName.toUpperCase()+"_COMPANY","Moderate"),3)!=null){
											s.click(Patternise(ListName.toUpperCase()+"_COMPANY","Strict"),3);
											if(s.exists(Patternise(UpdateData.toUpperCase().replace(".",""),"Moderate"),3)!=null) {
												test.pass("Updated Company "+UpdateData+" found in the user added list for Company list "+ListName);
												return 1;
											}
										}
										else if (s.exists(Patternise(ListName.toUpperCase()+"_COMPANY_BLUE","Moderate"),3)!=null){
											s.wait(Patternise(ListName.toUpperCase()+"_COMPANY_BLUE","Strict"),3).click();
											if(s.exists(Patternise(UpdateData.toUpperCase().replace(".",""),"Moderate"),3)!=null) {
												test.pass("Updated Company "+UpdateData+" found in the user added list for Company list "+ListName);
												return 1;
											}
										}
										break;
					}
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
			}	
				break;
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		return 0;
	}
	public static int ManipulateDefaultFeedsData(String Feed,String Mode, String Product, String Topic) {
		try {
			switch(Mode.toUpperCase()) {
			case "ADD":
				if (s.exists(Patternise("NewDefaultCodeFeed","Moderate"),5)!=null){
					s.click(Patternise("NewDefaultCodeFeed","Moderate"));
					s.type(Patternise("NewFeedTxtbox","Moderate"),Feed);
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					test.pass("Entered the Feed name");
					s.type(Patternise("NewFeedProductCode","Moderate"),Product);
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					test.pass("Entered the Product Code");
					s.type(Patternise("NewFeedTopicCode","Moderate"),Topic);
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					test.pass("Entered the Topic Code");
					s.click(Patternise("AddShrtcmpny","Moderate"));
					test.pass("Clicked Add Button");
				}
				else {
					test.fail("New button not found");
				}
				break;
			case "DELETE":
				s.find(Patternise("NewDefaultCodeFeed","Moderate")).offset(0,-50).click();
				for (int i=0;i<5;i++) {
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
				}
				s.mouseMove(Patternise("NewDefaultCodeFeed","Moderate").targetOffset(0,15));
				for (int i=0;i<4;i++) {
					if (s.exists(Patternise(Feed+"Delete","Moderate"))!=null){
						s.click(Patternise(Feed+"Delete","Moderate"));
						s.click(Patternise("DeleteShrtcmpny","Moderate"));
						s.click(Patternise("DeleteConfirmYes","Moderate"),5);
						test.pass("Deleted the Feed from Saved list");
						return 1;
					}
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
					
				}
				break;
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		return 0;
	}
	
	public static int ManipulateShrtCmpnyData(String RIC,String Shortname, String Mode) {
		try {
			switch(Mode.toUpperCase()) {
			case "ADD":
				if (s.exists(Patternise("AddNewNameShrtcmpny","Moderate"),5)!=null){
					s.click(Patternise("AddNewNameShrtcmpny","Moderate"));
					s.find(Patternise("FullNameShrtcmpny","Moderate")).offset(100,0).click();
					s.type(RIC.replace("_", " "));
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					test.pass("Entered the Full name");
					s.find(Patternise("ShortNameShrtcmpny","Moderate")).offset(100,0).click();
					s.type(Shortname);
					s.click(Patternise("AddShrtcmpny","Moderate"));
					test.pass("Entered the Short name");
				}
				else {
					test.fail("Add New Name button not found");
				}
				break;
			case "DELETE":
				s.find(Patternise("AddNewNameShrtcmpny","Moderate")).offset(0,-200).click();
				for (int i=0;i<5;i++) {
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
				}
				s.mouseMove(Patternise("AddNewNameShrtcmpny","Easy").targetOffset(0,15));
				for (int i=0;i<4;i++) {
					if (s.exists(Patternise(RIC,"Moderate"))!=null){
						s.click(Patternise(RIC,"Moderate"));
						s.click(Patternise("DeleteShrtcmpny","Moderate"));
						test.pass("Deleted the RIC from Saved list");
						return 1;
					}
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
					
				}
				break;
			case "VERIFY":
				s.find(Patternise("AddNewNameShrtcmpny","Moderate")).offset(0,-200).click();
				for (int i=0;i<6;i++) {
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
				}
				s.mouseMove(Patternise("AddNewNameShrtcmpny","Easy").targetOffset(0,15));
				for (int i=0;i<6;i++) {
					System.out.println("Value of i "+i);
					if (s.exists(Patternise(RIC,"Moderate"))!=null){
						test.pass("Short Company Name Found in Saved list");
						return 1;
					}
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_DOWN);
					s.keyUp(Key.PAGE_DOWN);
					s.keyDown(Key.PAGE_UP);
					s.keyUp(Key.PAGE_UP);
					
				}
				test.fail("Short Company Name not found in Saved list");
				break;
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		return 0;
	}
	public static void ManipulateBAEmetadata(String Metadata,String Code) {
		try {
			
			switch(Metadata.toUpperCase()) {
			case "PRODUCT":
				if (s.exists(Patternise("EmptyBAEProduct","Moderate"),5)!=null){
					s.find(Patternise("ProductBAE","Moderate")).offset(300,0).click();
					test.pass("Product field already empty");
				}
				else {
					s.find(Patternise("ProductBAE","Moderate")).offset(300,0).click();
					for (int i=0;i<10;i++) {
						s.keyDown(Key.BACKSPACE);
						s.keyUp(Key.BACKSPACE);
					}
					test.pass("Cleared existing products from Product field");
				}
				if(!Code.equals("")) {
					EnterMetadata(Code);
					test.pass("Entered Data in Product field");
				}
				break;
			case "TOPIC":
				if (s.exists(Patternise("EmptyBAETopic","Moderate"),5)!=null){
					s.find(Patternise("TopicBAE","Moderate")).offset(300,0).click();
					test.pass("Topic field already empty");
				}
				else {
					s.find(Patternise("TopicBAE","Moderate")).offset(300,0).click();
					for (int i=0;i<10;i++) {
						s.keyDown(Key.BACKSPACE);
						s.keyUp(Key.BACKSPACE);
					}
					test.pass("Cleared existing topics from Topic field");
				}
				if(!Code.equals("")) {
					EnterMetadata(Code);
					test.pass("Entered Data in Topic field");
				}
				break;
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	public static void SelectLiveFeedOrFullsearch(String Option) {
		try {
			switch(Option.toUpperCase().trim()){
			case"LIVEFEEDS":
				if(s.exists(Patternise("LiveFeedsUnslctd","Strict")) != null) {
					s.click(Patternise("LiveFeedsUnslctd","Strict"));
					Thread.sleep(2000);
					test.pass("Switched to Live Feeds");
				}
				else if(s.exists(Patternise("LiveFeeds","Strict")) != null) {
					s.click(Patternise("LiveFeeds","Strict"));
					test.pass("Live Feeds already displayed");
					Thread.sleep(2000);
				}
				else {
					test.fail("Live Feeds not found");
				}
				Thread.sleep(3000);
				break;
			case"FULLSEARCH":
				if(s.exists(Patternise("FullSearchUnslctd","Strict")) != null) {
					s.click(Patternise("FullSearchUnslctd","Strict"));
					Thread.sleep(2000);
					test.pass("Switched to Full Search");
				}
				else if(s.exists(Patternise("FullSearch","Strict")) != null) {
					s.click(Patternise("FullSearch","Strict"));
					test.pass("Full Search already displayed");
					Thread.sleep(2000);
				}
				else {
					test.fail("Full Search not found");
				}
				Thread.sleep(3000);
				break;
			default:
				test.fail("Invalid option passed, please pass either of LIVEFEEDS or FULLSEARCH");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	public static void ClickFullSearchbutton(){
		try {
			int Count=0;
			s.click(Patternise("SearchBtnFullSearch","Strict"));
			while(s.exists(Patternise("SearchResultBlue","Strict")) != null) {
				Thread.sleep(2000);
				Count++;
				if(Count>6) {
					break;
				}
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	public static void EnterMetadata(String Code) {
		try {
			s.type(Code);
			Thread.sleep(2000);
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	public static void SetHighContrast(String OnOff) {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		try {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
			switch(OnOff.toUpperCase()) {
			case "ON":
				if(s.exists(Patternise("HighContrastOFF","Strict")) != null) {
					s.click(Patternise("HighContrastOFF","Strict"));
					test.pass("High Contrast checkbox turned ON");
				}
				else if(s.exists(Patternise("HighContrastON","Strict")) != null) {
					test.pass("High Contrast checkbox already ON");
				}
				else {
					test.fail("High Contrast checkbox not found");
				}
				Thread.sleep(3000);
				break;
			case "OFF":
				if(s.exists(Patternise("HighContrastON","Strict")) != null) {
					s.click(Patternise("HighContrastON","Strict"));
					test.pass("High Contrast checkbox turned OFF");
				}
				else if(s.exists(Patternise("HighContrastOFF","Strict")) != null) {
					test.pass("High Contrast checkbox already OFF");
				}
				else {
					test.fail("High Contrast checkbox not found");
				}
				Thread.sleep(3000);
				break;
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	public static void EnterAlert(String Alerttext) {
		try {
				Region r;
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				String timeNdate=(timestamp+"").replace(":","");
				if(s.exists(Patternise("NoHeadlines","Moderate"),4)!=null || s.exists(Patternise("NoHeadlinesHC","Moderate"),4)!=null ) {
					test.fail("No headlines found, No templates seen to enter Alert text");
				}
				else {
						if(Alerttext.toUpperCase().contains("HIGHCONTRASTQUICKPUBLISH")) {
							r=new Region(s.find(Patternise("QPcharsHC","Strict")).getX()-80, s.find(Patternise("QPcharsHC","Strict")).getY()+5, 1, 1);
						}
						else if(Alerttext.toUpperCase().contains("HIGHCONTRAST")) {
							 r=new Region(s.find(GetProperty("charsHC")).getX()-80, s.find(GetProperty("charsHC")).getY()+32, 1, 1);	
						}
						else {
							r=new Region(s.find(GetProperty("chars")).getX()-80, s.find(GetProperty("chars")).getY()+32, 1, 1);
						}
						r.click();
						Thread.sleep(2000);
						s.type("a", KeyModifier.CTRL);
						Thread.sleep(2000);
						s.keyDown(Key.BACKSPACE);
						s.keyUp(Key.BACKSPACE);
						if(Alerttext.equals("")) {
							s.type(Alerttext);
							test.pass("Entered Alert text: "+Alerttext);
						}
						else {
							s.type(Alerttext+timeNdate);
							test.pass("Entered Alert text: "+Alerttext+timeNdate);
						}
						Thread.sleep(3000);
				}
			}
			
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	@Test
	public static void Verify_MemoryLeak() throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			int error=0;
			Instant start = Instant.now();
			long userentrdmins = 2;
			RelaunchReopenFWTab(test,"Reopen");
			if(s.exists(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),20)!=null) {
				s.dragDrop(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),Patternise("Newtab","Moderate"));
			}
			else if(s.exists(Patternise("ReleaseBodyWebViewSlctd","Moderate"),20)!=null) {
				s.dragDrop(Patternise("ReleaseBodyWebViewSlctd","Moderate"),Patternise("Newtab","Moderate"));
			} 
			Thread.sleep(4000);
			Duration timeElapsed;
			Instant end ;
			do{
				s.find(Patternise("DATE","Moderate")).offset(0,50).click();
				Thread.sleep(2000);
				s.find(Patternise("DATE","Moderate")).offset(0,70).click();
				Thread.sleep(2000);
				s.find(Patternise("DATE","Moderate")).offset(0,90).click();
				Thread.sleep(2000);
				end = Instant.now();
				timeElapsed = Duration.between(start, end);
				if(s.exists(Patternise("ErrorOutofProc","Moderate"))!=null) {
					error=1;
				}
				
			}
			while(userentrdmins!=timeElapsed.toMinutes() && error !=1);
			if(error==1) {
				test.fail("Error occured-Memory leaked");
			}
			else {
				test.pass("No memory leak found for run for " + userentrdmins + " mins.");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
			//RelaunchReopenFWTab(test,"Relaunch");
			if(s.exists(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),20)!=null) {
				s.dragDrop(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),Patternise("ReleaseBody","Moderate"));
			}
			else if(s.exists(Patternise("ReleaseBodyWebViewSlctd","Moderate"),20)!=null) {
				s.dragDrop(Patternise("ReleaseBodyWebViewSlctd","Moderate"),Patternise("ReleaseBody","Moderate"));
			}
			if(s.exists(Patternise("ReleaseBodyUnslctd","Moderate"),10)!=null) {
				s.click(Patternise("ReleaseBodyUnslctd","Moderate"));
			}
		}
	}

	@Test
	public static void Verify_PublishMemoryLeak() throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			int error=0;
			Instant start = Instant.now();
			long userentrdmins = 3;
			RelaunchReopenFWTab(test,"Reopen");
			Thread.sleep(4000);
			if(s.exists(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),20)!=null) {
				s.dragDrop(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),Patternise("COMPANY","Moderate"));
			}
			else if(s.exists(Patternise("ReleaseBodyWebViewSlctd","Moderate"),20)!=null) {
				s.dragDrop(Patternise("ReleaseBodyWebViewSlctd","Moderate"),Patternise("COMPANY","Moderate"));
			} 
			Thread.sleep(4000);
			Duration timeElapsed;
			Instant end ;
			do{
				s.find(Patternise("DATE","Moderate")).offset(0,50).click();
				Thread.sleep(2000);
				if(s.exists(Patternise("Publish","Strict"))!=null) {
					s.click(Patternise("Publish","Strict"));
				}
				else {
					s.find(Patternise("AlertEditorTab","Moderate")).click();
					s.type("i",Key.ALT);
					Thread.sleep(2000);
					s.type("K.N");
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					Thread.sleep(3000);
					if(s.exists(Patternise("Publish","Strict"))!=null) {
						s.click(Patternise("Publish","Strict"));
					}
				}
				end = Instant.now();
				timeElapsed = Duration.between(start, end);
				if(s.exists(Patternise("ErrorOutofProc","Moderate"))!=null) {
					error=1;
				}
				
			}
			while(userentrdmins!=timeElapsed.toMinutes() && error !=1);
			if(error==1) {
				test.fail("Error occured-Memory leaked");
			}
			else {
				test.pass("No memory leak found for publish run for " + userentrdmins + " mins.");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
			//RelaunchReopenFWTab(test,"Relaunch");
			if(s.exists(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),20)!=null) {
				s.dragDrop(Patternise("ReleaseBodyWebViewUnslctd","Moderate"),Patternise("ReleaseBody","Moderate"));
			}
			else if(s.exists(Patternise("ReleaseBodyWebViewSlctd","Moderate"),20)!=null) {
				s.dragDrop(Patternise("ReleaseBodyWebViewSlctd","Moderate"),Patternise("ReleaseBody","Moderate"));
			} 
			if(s.exists(Patternise("ReleaseBodyUnslctd","Moderate"),10)!=null) {
				s.click(Patternise("ReleaseBodyUnslctd","Moderate"));
			}
		}
	}
	
	@Parameters({"param0","param1"})
	@Test
	public static void VerifyWWScenarios(String RelaunchReopen,String Option) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","JobRole");
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		}
	
	@Parameters({"param0","param1"})
	@Test
	public static void VerifyJobRolesCases(String RelaunchReopen,String Option) {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","JobRole");
			s.wait(Patternise("CreateJobRole","Moderate"),20).click();
			Thread.sleep(3000);
			s.find(Patternise("HomeBureau","Moderate")).offset(0,20).click();
			s.type("Breaking News");
			test.pass("Selected Home bureau");
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			s.type("Details");
			test.pass("Entered Details");
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			s.type("US");
			s.click(Patternise("USc","Moderate"),5);
			test.pass("Selected Region");
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			switch(Option.toUpperCase()) {
					
					case "AUTOMATIONS":
						SelectJobRolefield("AUTOMATIONS");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						Thread.sleep(4000);
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
							test.pass("Able to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						break;
					case "COMPANYLIST":
						SelectJobRolefield("COMPANYLIST");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
						//Check After ReLaunch
						RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
							RelaunchReopenFWTab(test,"Reopen");
							Thread.sleep(4000);
						}
						s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
							test.pass("Able to see user saved Company in Company List after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Company in Company List after "+RelaunchReopen);
						}
						break;
					case "FEEDFILTERS":
						SelectJobRolefield("FEEDFILTERS");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						s.click(Patternise("FeedsDropdown","Moderate"),3);
						if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
							test.pass("Able to see user saved Feed after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Feed after "+RelaunchReopen);
						}
						break;
					case "WEBWATCH":
						SelectJobRolefield("WEBWATCH");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						Thread.sleep(4000);
						OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
						if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
							test.pass("Able to see user saved Web Watchers after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Web Watchers after "+RelaunchReopen);
						}
						break;
					case "FEEDWEBWATCH":
						SelectJobRolefield("FEEDFILTERS");
						SelectJobRolefield("WEBWATCH");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						Thread.sleep(4000);
						s.click(Patternise("FeedsDropdown","Moderate"),3);
						if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
							test.pass("Able to see user saved Feed after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Feed after "+RelaunchReopen);
						}
						OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
						if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
							test.pass("Able to see user saved Web Watchers after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Web Watchers after "+RelaunchReopen);
						}
						break;
					case "COMPANYWEBWATCH":
						SelectJobRolefield("COMPANYLIST");
						SelectJobRolefield("WEBWATCH");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						Thread.sleep(4000);
						s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
							test.pass("Able to see user saved Company in Company List after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Company in Company List after "+RelaunchReopen);
						}
						OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
						if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
							test.pass("Able to see user saved Web Watchers after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Web Watchers after "+RelaunchReopen);
						}
						break;
					case "AUTOMATIONSWEBWATCH":
						SelectJobRolefield("AUTOMATIONS");
						SelectJobRolefield("WEBWATCH");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
							test.pass("Able to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
						if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
							test.pass("Able to see user saved Web Watchers after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Web Watchers after "+RelaunchReopen);
						}
						break;
					case "FEEDCOMPANY":
						SelectJobRolefield("COMPANYLIST");
						SelectJobRolefield("FEEDFILTERS");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
							test.pass("Able to see user saved Company in Company List after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Company in Company List after "+RelaunchReopen);
						}
						s.click(Patternise("FeedsDropdown","Moderate"),3);
						s.click(Patternise("FeedsDropdown","Moderate"),3);
						if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
							test.pass("Able to see user saved Feed after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Feed after "+RelaunchReopen);
						}
						break;
					case "FEEDAUTOMATIONS":
						SelectJobRolefield("AUTOMATIONS");
						SelectJobRolefield("FEEDFILTERS");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
							test.pass("Able to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						s.click(Patternise("FeedsDropdown","Moderate"),3);
						s.click(Patternise("FeedsDropdown","Moderate"),3);
						if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
							test.pass("Able to see user saved Feed after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Feed after "+RelaunchReopen);
						}
						break;
					case "FEEDAUTOMATIONSWEBWATCH":
						SelectJobRolefield("AUTOMATIONS");
						SelectJobRolefield("FEEDFILTERS");
						SelectJobRolefield("WEBWATCH");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
							test.pass("Able to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						s.click(Patternise("FeedsDropdown","Moderate"),3);
						s.click(Patternise("FeedsDropdown","Moderate"),3);
						if(s.exists(Patternise("KYDOFeedDDN","Moderate"),5)!=null) {
							test.pass("Able to see user saved Feed after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Feed after "+RelaunchReopen);
						}
						OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
						if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
							test.pass("Able to see user saved Web Watchers after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Web Watchers after "+RelaunchReopen);
						}
						break;
					case "COMPANYAUTOMATIONS":
						SelectJobRolefield("AUTOMATIONS");
						SelectJobRolefield("COMPANYLIST");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
							test.pass("Able to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
						s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
							test.pass("Able to see user saved Company in Company List after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Company in Company List after "+RelaunchReopen);
						}
						break;
					case "COMPANYAUTOMATIONSWEBWATCH":
						SelectJobRolefield("AUTOMATIONS");
						SelectJobRolefield("COMPANYLIST");
						SelectJobRolefield("WEBWATCH");
						s.click(Patternise("BacktoJobRole","Moderate"),5);
						SaveNFollowJobRole();
						if(RelaunchReopen.toUpperCase().equals("RELAUNCH")) {
							//Check After ReLaunch
							RelaunchReopenFWTab(test,"Relaunch");
						}
						else {
								RelaunchReopenFWTab(test,"Reopen");
								Thread.sleep(4000);
						}
						s.click(Patternise("AutomationsDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("AustrianCPI","Moderate"),5)!=null) {
							test.pass("Able to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Automation in Automation Dropdown after "+RelaunchReopen);
						}
						s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
						s.click(Patternise("CompanyListDDNUnslctd","Moderate"),3);
						if(s.exists(Patternise("1TestCompany","Moderate"),5)!=null) {
							test.pass("Able to see user saved Company in Company List after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Company in Company List after "+RelaunchReopen);
						}
						OpenUserPrfrncs(test,"FastwirePreferences","WebWatch");
						if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
							test.pass("Able to see user saved Web Watchers after "+RelaunchReopen);
						}
						else {
							test.fail("Unable to see user saved Web Watchers after "+RelaunchReopen);
						}
						break;
			}
			//Reversing changes 
			OpenUserPrfrncs(test,"FastwirePreferences","JobRole");
			//Deleting the Job Role
			if (s.exists(Patternise("BreakingNewsJobRole","Moderate"),5)!=null) {
				s.mouseMove(Patternise("US","Easy"));
				//s.click(Patternise("US","Moderate"),5);
				s.click(Patternise("UnFollowJobRole","Moderate"),5);
				test.pass("Unfollowed the newly created Job Role");
				Thread.sleep(4000);
				s.click(Patternise("BreakingNewsJobRole","Moderate"));
				test.pass("Clicked newly created Job Role- Breaking News");
				s.wait(Patternise("DeleteJobRole","Moderate"),4).click();
				s.wait(Patternise("CnfrmDelAlrm","Moderate"),4).click();
				if (s.exists(Patternise("BreakingNewsJobRole","Moderate"),5)==null) {
					test.pass("Successfully deleted newly created Job Role");
				}
				else {
					test.pass("Unable to delete newly created Job Role");
				}
			}
			else {
				test.fail("No newly created Job Role found. Unable to delete.");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	public static void SelectJobRolefield(String Option) {
		try {
				switch(Option.toUpperCase()) {
				case "FEEDFILTERS":
									OpenJobRoleField("Feeds");
									SelectFeed(test,"Japan","KYDO");
//									if (s.exists(Patternise("SaveFeed","Easy"),3) != null) {
//										s.find(GetProperty("SaveFeed")).click();
//									}
//									test.pass("Saved the user selected feed");
									Thread.sleep(1000);
									break;
				case "COMPANYLIST":
//									int status=0;
//									OpenUserPrfrncs(test,"Preferences","CompanyListAdmin");
//									status=ManipulateCompanyListData("1TestCompanyAdmin","VERIFY", "RE.N", "PUBLIC","N","N");
//									if (status==1) {
//										s.find(GetProperty("SaveLynxPreferencesDark")).click();
//									}
//									else {
//									ManipulateCompanyListData("1TestCompany","ADD",  "RE.N", "PUBLIC","N","N");
//									Thread.sleep(3000);
//									s.find(GetProperty("SaveLynxPreferencesLight")).click();
//									test.pass("Saved the user entered data");
//									Thread.sleep(1000);
//									}
									OpenJobRoleField("CompanyLists");
									if(s.exists(Patternise("Filtering","Moderate"),5)!=null) {
										s.find(Patternise("Filtering","Moderate")).offset(0,50).click();
										if(s.exists(Patternise("Checkboxon","Moderate"),5)!=null) {
											//s.click(Patternise("Checkboxoff","Exact"));
											test.pass("Turned on company filtering");
											//s.click(Patternise("SaveCompanyLists","Moderate"),5);
										}
										//else if(s.exists(Patternise("Checkboxff","Exact"),5)!=null) {
										//	test.pass("Company filtering turned off");
										//}
										else {
											test.fail("Filtering Checkbox not found");
										}
									}
									else {
										test.fail("Company List screen not found");
									}
									break;
				case "AUTOMATIONS":
									OpenJobRoleField("Automations");
									ClearAutomationSelection(test,50);
									EnterAutomation(test,"AustrianCPI");
									Thread.sleep(1000);
									s.keyDown(Key.TAB);
									s.keyUp(Key.TAB);
									s.keyDown(Key.TAB);
									s.keyUp(Key.TAB);
									
//									s.find(GetProperty("SVEnbld")).click();
//									test.pass("Clicked Save button");
//									ValidateAutomationSave(test,"AustrianCPI");
									break;
				case "WEBWATCH":
									OpenJobRoleField("WebWatch");
									ClearWebWatchSelection(test,50);
									Thread.sleep(4000);
									EnterWebWatcher(test,"vagazette.com");
									Thread.sleep(1000);
//									s.find(GetProperty("SVEnbld")).click();
//									test.pass("Clicked Save button");
									break;
				}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	public static void OpenJobRoleField(String Option) {
		try {
				switch(Option) {
					case "Feeds":
								int countFeeds=0;
								if (s.exists(Patternise("JRFeedsSlctd","Easy"),10)!=null) {
									test.pass("Filters Sources - Feeds Option already selected");
								}
								else if(s.exists(Patternise("JRFeeds","Easy"),10)!=null) {
									s.find(Patternise("JRFeeds","Easy")).click();
									test.pass("Found and clicked Feeds Option");
									while(s.exists(Patternise("LoadinginPreferences","Strict"))!=null) {
										Thread.sleep(1000);
										countFeeds++;
										if(countFeeds>10) {
											test.fail("Feeds not loaded");
											break;
										}
									}
									Thread.sleep(2000);
								}
								else {
									test.fail("Feeds Option not found");
								}
								pattern1 = new Pattern(Patternise("EnblFltrs","Easy")).exact();
								pattern2 = new Pattern(Patternise("EnblFltrsSlctd","Easy")).exact();
								//if(s.exists(GetProperty("EnblFltrOff"))!=null) {
								//s.find(GetProperty("EnblFltrOff")).click();
								if(s.exists(Patternise("EnblFltrs","Easy"),10)!=null) {
									s.click(Patternise("EnblFltrs","Easy"));
									test.pass("Enabled Filters to select feeds");
									Thread.sleep(1000);
								}
								//else if (s.exists(GetProperty("EnblFltrOn"))!=null) {
								else if (s.exists(Patternise("EnblFltrsSlctd","Easy"),10)!=null) {
									test.pass("Enable Filters already on");
								}
								
								else {
									test.fail("Enable Filters option not found");
								}
				                break;
					case "CompanyLists":
								if (s.exists(Patternise("JRCmpnyLstSlctd","Moderate"),10)!=null) {
									test.pass("Company Lists Option already selected");
								}
								else if(s.exists(Patternise("JRCmpnyLst","Moderate"),10)!=null) {
									s.find(GetProperty("JRCmpnyLst")).click();
									test.pass("Found and clicked Company Lists Option");
									Thread.sleep(8000);
								}
								else {
									test.fail("Company Lists Option not found");
								}
								break;
					case "Automations":
								int countAutomations=0;
								if (s.exists(GetProperty("JRAutmtnSlctd"),5)!=null) {
									test.pass("Automations Option already selected");
								}
								else if(s.exists(GetProperty("JRAutmtn"),5)!=null) {
									s.find(GetProperty("JRAutmtn")).click();
									test.pass("Found and clicked Automations Option");
									while(s.exists(Patternise("LoadinginPreferences","Strict"))!=null) {
										Thread.sleep(1000);
										countAutomations++;
										if(countAutomations>10) {
											test.fail("Automations not loaded");
											break;
										}
									}
									
									Thread.sleep(3000);
								}
								else {
									test.fail("Automations Option not found");
								}
								break;
					case "WebWatch":
								int countWebWatchs=0;
								if (s.exists(Patternise("JRWebWatchSlctd","Moderate"),5)!=null) {
									test.pass("Web Watchers Option already selected");
								}
								else if(s.exists(Patternise("JRWebWatch","Moderate"),5)!=null) {
									s.find(Patternise("JRWebWatch","Moderate")).click();
									test.pass("Found and clicked Web Watchers Option");
									while(s.exists(Patternise("LoadinginPreferences","Strict"))!=null) {
										Thread.sleep(1000);
										countWebWatchs++;
										if(countWebWatchs>10) {
											test.fail("WebWatchers not loaded");
											break;
										}
									}
									
									Thread.sleep(3000);
								}
								else {
									test.fail("Web Watchers Option not found");
								}
								break;
				}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}	
	
	public static void SaveNFollowJobRole() {
		try{
			s.click(Patternise("SaveFeed","Easy"),5);
			Thread.sleep(4000);
			test.pass("Saved the Job Role with the details enetered in previous screens");
			//s.click(Patternise("US","Easy"),5);
			s.mouseMove(Patternise("US","Easy"));
			s.click(Patternise("FollowJobRole","Easy"),5);
			test.pass("Followed the newly created Job Role");
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
}
