package testPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.OCR;
import org.sikuli.script.Pattern;
import org.testng.IDynamicGraph.Status;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

public class Headline_Alarm extends BasePackage.LYNXBase {
	public Headline_Alarm() {
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
	public static void VerifyValidMatches_HeadlineAlarm(String Source) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","HeadlineAlarm");
			s.find(GetProperty("AddHdlnAlrm")).click();
			test.pass("Clicked Add Headline Alarms link");
			Thread.sleep(4000);
			s.find(GetProperty("AlarmName")).click();
			Scrollinpage(test ,GetProperty("HdlnAlrmSrcs"),"Sources Text box");
			if (s.exists(GetProperty("HdlnAlrmSrcs"))==null) {
				test.fail("Source text box not found");
			}
			else
			{	
				Thread.sleep(2000);
				s.find(GetProperty("HdlnAlrmSrcs")).offset(0, 80).click();
				s.type(Source);
				test.pass("Source text box found and value entered");
				Thread.sleep(2000);
				Pattern pattern=new Pattern(GetProperty("BSESourceList")).exact();
				if(s.exists(pattern)!=null ) {
					test.pass("Matching Source value list found");
					Thread.sleep(6000);
				}
				else {
						test.fail("Matching Source value list not found");
				}
				s.find(GetProperty("FWTabClose")).doubleClick();
				s.find(GetProperty("FWTabClose")).doubleClick();
				Thread.sleep(2000);
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
	public static void VerifyAddSources_HeadlineAlarm(String Source,String Option) throws FindFailed, InterruptedException {
		if(Option.equals("Unsave")){	test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);}
		String nameofCurrMethod = new Throwable()
                 .getStackTrace()[0]
                 .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int sourcecnt=0;
		Pattern deletepattern;
		try {
			String[] arrOfStr = Source.split("@", 0);
			if(Option.equals("Unsave")) {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"FastwirePreferences","HeadlineAlarm");
			}
			s.find(GetProperty("AddHdlnAlrm")).click();
			s.type("TestAlarm");
			test.pass("Clicked Add Headline Alarms link");
			Thread.sleep(4000);
			s.find(GetProperty("AlarmName")).click();
			s.type("TestAlarm");
			Scrollinpage(test ,GetProperty("HdlnAlrmSrcs"),"Sources Text box");
			if (s.exists(GetProperty("HdlnAlrmSrcs"))==null) {
				test.fail("Source text box not found");
			}
			else
			{	
				 for (String a : arrOfStr) {
					EnterNSelectSource(a);
					//Thread.sleep(2000);
				}
				
				  deletepattern= new Pattern(GetProperty("DeleteSource")).exact();
				  Iterator<Match> it = s.findAll(deletepattern); 
				  while(it.hasNext()){
					  sourcecnt=sourcecnt+1;
					  it.next();				  
				  }				 
				if (sourcecnt==arrOfStr.length) {
					test.pass(sourcecnt+" Sources added successfully");
				}
				else
				{	
					test.fail(sourcecnt+" Sources not added successfully");
				}
			}
			if (Option.equals("Unsave")){
			    s.find(GetProperty("FWTabClose")).doubleClick();
				s.find(GetProperty("FWTabClose")).doubleClick();
				Thread.sleep(2000);
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
public static void VerifySaved_HeadlineAlarm(String Source, String Option) throws FindFailed, InterruptedException {
	if(Option.equals("Save")) {test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);}
	String nameofCurrMethod = new Throwable()
             .getStackTrace()[0]
             .getMethodName();
	Pattern deletepattern;
	int sourcecnt=0;
	test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
	try{
		String[] arrOfStr = Source.split("@", 0);
		if(Option.equals("Save")) {
		RelaunchReopenFWTab(test,"Reopen");
		OpenUserPrfrncs(test,"FastwirePreferences","HeadlineAlarm");
		}
		while(s.exists(GetProperty("DeleteAlrm"))!=null) {
			s.wait(GetProperty("DeleteAlrm"),5).click();
			s.wait(GetProperty("CnfrmDelAlrm"),5).click();
			Thread.sleep(2000);
		}
		VerifyAddSources_HeadlineAlarm(Source,"Save");
		Scrollinpage(test ,GetProperty("CreateAlarm"),"Create Alarm Button");
		s.wait(GetProperty("CreateAlarm"),5).click();
		test.pass("Clicked on CreateAlarm Button");
		if(s.exists(GetProperty("AddedAlarm"),20)!=null ) {
			test.pass("Alarm Created Successfully");
		}
		else {
			test.fail("Alarm not created");
		}
		VerifyAlarm(test,arrOfStr.length);
		Thread.sleep(2000);
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
public static void VerifySaved_HeadlineAlarmReopen_Relaunch(String Source, String Option) throws FindFailed, InterruptedException {
	test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
	String nameofCurrMethod = new Throwable()
             .getStackTrace()[0]
             .getMethodName();
	test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
	try{
		String[] arrOfStr = Source.split("@", 0);
		RelaunchReopenFWTab(test,"Reopen");
		OpenUserPrfrncs(test,"FastwirePreferences","HeadlineAlarm");
		VerifySaved_HeadlineAlarm(Source,Option);
		test.log(com.aventstack.extentreports.Status.INFO,"Verifying added Sources after "+Option);
		RelaunchReopenFWTab(test,Option);
		OpenUserPrfrncs(test,"FastwirePreferences","HeadlineAlarm");
		VerifyAlarm(test,arrOfStr.length);
	}
	catch(Exception e) {
		test.fail("Error Occured: "+e.getLocalizedMessage());
	}
	finally {
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
	}
}

public static void VerifyAlarm(ExtentTest test,int lngth) {
	try {	
			Pattern deletepattern;
			int sourcecnt=0;
			s.wait(GetProperty("AddedAlarm"),5).click();
			s.click();
			Scrollinpage(test ,GetProperty("HdlnAlrmSrcs"),"Sources Text box");
			Thread.sleep(2000);
			deletepattern= new Pattern(GetProperty("DeleteSource")).exact();
			Iterator<Match> it = s.findAll(deletepattern); 
			while(it.hasNext()){
				  sourcecnt=sourcecnt+1;
				  it.next();				  
			}				 
			if (sourcecnt==lngth) {
				test.pass(sourcecnt+"Added Sources found in newly created Alarm");
			}
			else
			{	
				test.fail("Added Sources not found in newly created Alarm. Found "+sourcecnt+" sources");
			}
			s.find(GetProperty("FWTabClose")).doubleClick();
			s.find(GetProperty("FWTabClose")).doubleClick();
	}
	catch(Exception e) {
		test.fail("Error Occured: "+e.getLocalizedMessage());
	}
			
}

public static void EnterNSelectSource(String Source) {
	try {
		s.wait(GetProperty("HdlnAlrmSrcs"),5).offset(0, 80).click();
		s.type(Source);
		test.pass("Source text box found and value entered");
		//Thread.sleep(2000);
		s.wait(GetProperty("SourceCheckBox"),5).click();
		s.wait(GetProperty("AddSelected"),5).click();
		test.pass("Added the Source "+Source);
		//Thread.sleep(5000);
	}
	catch(Exception e) {
		test.fail("Error Occured: "+e.getLocalizedMessage());
	}
}

public static void Scrollinpage(ExtentTest test, String scrolltill, String Objname) {
	try {
		while(s.exists(GetProperty("EndOfDownScroll"))!=null) {
			s.keyDown(Key.PAGE_DOWN);
			s.keyUp(Key.PAGE_DOWN);
			if(s.exists(scrolltill)!=null) {
				test.pass("Scrolled page till "+ Objname);
				break;
			}
		}
	}
	catch(Exception e) {
		test.fail("Error Occured: "+e.getLocalizedMessage());
	}	
}


	@Parameters({"param0","param1","param2","param3"})
	@Test
	public void ValidateAddAlarm(String Alarmname,String Keyword1,String Keyword2,String Sourcenm) {
		//test = extent.createTest(BasePackage.MainRunner.TestID, BasePackage.MainRunner.TestDescription);
		test.log(com.aventstack.extentreports.Status.INFO,"ValidateAddAlarm Method called");
		try {
			s.find(GetProperty("LYNXEDITORLOGO")).rightClick();
			test.pass("Right Clicked Fastwire Preferences icon");
			Thread.sleep(4000);
			s.find(GetProperty("Fastwire_Preferences")).click();
			test.pass("Clicked Fastwire Preferences icon");
			Thread.sleep(10000);
			s.find(GetProperty("Menu_FWPrfrncs")).click();
			test.pass("Clicked Menu Preferences");
			Thread.sleep(5000);
			if (s.exists(GetProperty("HdlnAlrms"))!=null) {
				s.find(GetProperty("HdlnAlrms")).click();
				test.pass("Clicked Headline Alarms link");
				Thread.sleep(1000);
			}
			else if(s.exists(GetProperty("HdlnAlrmsred"))!=null) {
				s.find(GetProperty("HdlnAlrmsred")).click();
				test.pass("Clicked Headline Alarms link");
				Thread.sleep(1000);
			}
			else {
				test.fail("Headline Alarms link not found");
			}
			s.find(GetProperty("AddHdlnAlrm")).click();
			test.pass("Clicked Add Headline Alarms link");
			Thread.sleep(4000);
			s.type(GetProperty("AlarmName"), Alarmname);
			test.pass("Entered Alarm Name");
			s.find(GetProperty("AlarmKeywords")).click();
			Thread.sleep(2000);
			s.type(GetProperty("AlarmKeywords"), Keyword1);
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			Thread.sleep(3000);
			s.find(GetProperty("AlarmKeywordEntered")).click();
			s.type(GetProperty("AlarmKeywordEntered"), Keyword2);
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			Thread.sleep(3000);
			test.pass("Entered Keywords for Alarms");
			while(s.exists(GetProperty("EndOfDownScroll"))!=null) {
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
				if(s.exists(GetProperty("HdlnAlrmSrcs"))!=null) {
					break;
				}
			}
			if (s.exists(GetProperty("HdlnAlrmSrcs"))==null) {
				test.fail("Source text box not found");
			}
			else
			{	
				Thread.sleep(2000);
				//s.type(getElement("HdlnAlrmSrcs"), getElement("Source"));
				s.type(GetProperty("HdlnAlrmSrcs"), Sourcenm);
				test.pass("Source text box found and value entered");
				Thread.sleep(2000);
				if(s.exists(GetProperty("BSESourceList"))!=null ) {
					test.pass("Matching Source value list found");
					Thread.sleep(6000);	
					s.find(GetProperty("SourceCheckBox")).click();
					Thread.sleep(3000);
					s.find(GetProperty("AddSelected")).click();
					Thread.sleep(5000);
					test.pass("Added a Source");
					Thread.sleep(5000);
					s.find(GetProperty("BGColorDDN")).click();
					Thread.sleep(3000);
					s.find(GetProperty("BGSlctclr")).click();
					Thread.sleep(3000);
					test.pass("Selected a background color");
					s.find(GetProperty("TextColorDDN")).click();
					Thread.sleep(3000);
					s.find(GetProperty("TxtSlctClr")).click();
					test.pass("Selected a text color");
					Thread.sleep(3000);
					s.find(GetProperty("CreateAlarm")).click();
					test.pass("Clicked on CreateAlarm Button");
					if(s.exists(GetProperty("AddedAlarm"),20)!=null ) {
						test.pass("Alarm Created Successfully");
					}
					else {
						test.fail("Unable to find newly created Alarm");
					}
				}
				else {
					test.fail("Matching Source value list not found");
				}
			}
			s.find(GetProperty("FWTabClose")).doubleClick();
			s.find(GetProperty("FWTabClose")).doubleClick();
			Thread.sleep(2000);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	@Parameters({"param0","param1","param2","param3","param4","param5","param6"})
	@Test
	public static void VerifyAlarmScenarios(String AutoLaunch, String Option, String Andalso, String BUTnot, String Matchwhole, String Matchcase,String Bold) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                 .getStackTrace()[0]
                 .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int sourcecnt=0;
		Pattern deletepattern;
		try {
			RelaunchReopenFWTab(test,"Reopen");
			OpenUserPrfrncs(test,"Preferences","Fastwire-UserPreferences");
			if(AutoLaunch.toUpperCase().equals("ON")) {
				ChangeAutolaunch("ON");
			}
			else
			{
				ChangeAutolaunch("OFF");
			}
			s.click(Patternise("SaveFeed","Moderate"),3);
			OpenUserPrfrncs(test,"FastwirePreferences","HeadlineAlarm");
			while(s.exists(Patternise("DeleteAlrm","Moderate"))!=null) {
				s.wait(Patternise("DeleteAlrm","Moderate"),5).click();
				s.wait(Patternise("CnfrmDelAlrm","Moderate"),5).click();
				Thread.sleep(2000);
			}
			if(s.exists(Patternise("SaveFeed1","Moderate"))!=null) {
				s.wait(Patternise("SaveFeed1","Moderate"),5).click();
			}
			s.find(Patternise("AddHdlnAlrm","Moderate")).click();
			test.pass("Clicked Add Headline Alarms link");
			s.type("TestAlarm");
			test.pass("Entered Alarm Name");
			Thread.sleep(4000);
			s.wait(Patternise("AlarmKeywords","Moderate"),4).click();
			s.type("TestKeyword");
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			if(! Option.toUpperCase().equals("UPDATE") ) {
				ClickRadio(Andalso, BUTnot, Matchwhole, Matchcase,Bold);
			}
			//Scrollinpage(test ,Patternise("BoldOFF","Moderate").toString(),"Bold Radio Button");
			s.keyDown(Key.PAGE_DOWN);
			s.keyUp(Key.PAGE_DOWN);
			if(s.exists(Patternise("AudioRadioOff","Moderate"))!=null) {
				s.click(Patternise("AudioRadioOff","Moderate"));
			}
			if(s.exists(Patternise("SayWordOff","Moderate"))!=null) {
				s.click(Patternise("SayWordOff","Moderate"));
				s.wait(Patternise("SayWordOn","Moderate"),4).offset(100, 0).click();
				s.type("Test");
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);
			}
			
			s.keyDown(Key.PAGE_DOWN);
			s.keyUp(Key.PAGE_DOWN);
			if(Option.toUpperCase().equals("CANCEL")) {
				s.click(Patternise("CanclAlrm","Moderate"));
			}
			else
			{
				s.click(Patternise("CreateAlarm","Moderate"));
				if(s.exists(Patternise("AddedAlarm","Moderate"),8)!=null ) {
					test.pass("Alarm Created Successfully");
				}
				else {
					test.fail("Unable to find newly created Alarm");
				}
			}
			//UPDATE
			if(Option.toUpperCase().equals("UPDATE") ) {
				s.click(Patternise("AddedAlarm","Moderate"));
				s.keyDown(Key.PAGE_UP);
				s.keyUp(Key.PAGE_UP);
				s.keyDown(Key.PAGE_UP);
				s.keyUp(Key.PAGE_UP);
				ClickRadio(Andalso, BUTnot, Matchwhole, Matchcase,Bold);
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
				s.wait(Patternise("SayWordOn","Moderate"),4).offset(200, 0).click();
				s.type("Updated");
				s.click(Patternise("UpdateAlarm","Moderate"));
				if(s.exists(Patternise("AddedAlarm","Moderate"),8)!=null ) {
					test.pass("Alarm Updated Successfully");
				}
				else {
					test.fail("Unable to find Updated Alarm");
				}
				
			}
			// Deleting newly created alarm
			if(Option.toUpperCase().equals("CANCEL")) {
				if(s.exists(Patternise("AddedAlarm","Moderate"))!=null) {
					test.fail(" Alarm created on cancelling");
				}
				else {
					test.pass("Alarm not created on Cancelling");
				}
			}
			else
			{
					test.pass("Reversing the changes");
					while(s.exists(Patternise("DeleteAlrm","Moderate"))!=null) {
						s.wait(Patternise("DeleteAlrm","Moderate"),5).click();
						s.wait(Patternise("CnfrmDelAlrm","Moderate"),5).click();
						Thread.sleep(2000);
					}
					s.wait(Patternise("SaveFeed1","Moderate"),5).click();
					test.pass("Deleted newly created alarm");
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	public static void ClickRadio(String Andalso, String BUTnot, String Matchwhole, String Matchcase,String Bold) throws FindFailed, InterruptedException { 
		if(Andalso.equals("Y") ) {
			s.wait(Patternise("AndAlso","Moderate"),4).offset(0, 30).click();
			s.type("AndAlso");
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			test.pass("Entered And Also Keyword");
		}
		if(BUTnot.equals("Y") ) {
			s.wait(Patternise("ButNot","Moderate"),4).offset(0, 30).click();
			s.type("ButNot");
			s.keyDown(Key.ENTER);
			s.keyUp(Key.ENTER);
			test.pass("Entered But Not Keyword");
		}
		if(Matchwhole.equals("Y")) {
			s.click(Patternise("MatchWholeWordOff","Moderate"));
			test.pass("Clicked Match Whole checkbox");
		}
		if(Matchcase.equals("Y")) {
			s.click(Patternise("MatchCaseOff","Moderate"));
			test.pass("Clicked Match Case checkbox");
		}
		s.keyDown(Key.PAGE_DOWN);
		s.keyUp(Key.PAGE_DOWN);
		if(Bold.equals("Y")) {
			if(s.exists(Patternise("BoldON","Moderate"),3)!=null) {
				test.pass("Bold checkbox already ON");
			}
			else {
				//Scrollinpage(test ,Patternise("BoldOFF","Moderate").toString(),"Bold Radio Button");
				s.click(Patternise("BoldOFF","Moderate"));
				test.pass("Bold checkbox turned ON");
			}
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
}
