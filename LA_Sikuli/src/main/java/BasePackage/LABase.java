package BasePackage;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentHtmlReporterConfiguration;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.collect.Iterators;

import jnr.posix.util.Finder;

public class LABase {

	public static FileReader LAReader;
	public static Properties LAProp;
	public static Screen s;
	public static ExtentHtmlReporter htmlReport;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static App LAapp;
	public static String folder;
	public static String Reportname;
	
	public  LABase() {
		
		try {
			Reportname="LA_Automation_Report ";
			folder=System.getProperty("user.dir")+"\\src\\test\\resources\\";
			LAReader = new FileReader(folder+"TestData\\LA.properties");
			LAProp = new Properties();
			LAProp.load(LAReader);
			s = new Screen();
			LAapp = new App(LAProp.getProperty("LAexe"));
		    //LAapp = new App("C:\\Program Files (x86)\\Thomson Reuters\\Lynx Alerting\\LynxAlerting.exe");
			//htmlReport = new ExtentHtmlReporter("LA_Automation_Report "+java.time.LocalDate.now()+".html");
			htmlReport = new ExtentHtmlReporter(Reportname+java.time.LocalDate.now()+".html");
			htmlReport.setAppendExisting(true);
			File reportconfig=new File(folder+"TestData\\extent-report-config.xml");
			htmlReport.loadXMLConfig(reportconfig);
			/*ExtentHtmlReporterConfiguration config = htmlReport.config();
			config.setTheme(Theme.DARK);
			config.setReportName("LA Fastwire Automation Test Report");
			config.setDocumentTitle(LAProp.getProperty("LAVersion"));
			config.setTimeStampFormat("dd-MM-yyyy hh:mm:ss");
			*/
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static ExtentReports getInstance() {
        if(extent == null) {
            InitialiseLABase();
        }   
        return extent;
	}

	private static void InitialiseLABase() {
		extent = new ExtentReports();
		extent.setSystemInfo("Author", LAProp.getProperty("TestAuthor")); 
		extent.setSystemInfo("User Name",LAProp.getProperty("tUser")); 
		extent.setSystemInfo("Environment",LAProp.getProperty("Environment")); 
		extent.attachReporter(htmlReport);
	}
	
	//@SuppressWarnings("deprecation")
	public static void LaunchLA(ExtentTest test, String Option) throws FindFailed, InterruptedException {
//		Pattern pattern;
		int count=0;
//		pattern = new Pattern(GetProperty("LATskBr")).exact();
//		if(s.exists(pattern)!=null) {
//			s.click(pattern);
//		}
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		//LAapp.focus();
		try{
			if(Option.equals("Relaunch")){
					Runtime runtime = Runtime.getRuntime();     //getting Runtime object
				    runtime.exec("taskkill /F /IM LynxAlerting.exe");
	                Thread.sleep(2000);
	                Runtime.getRuntime().exec("C:\\Program Files (x86)\\Thomson Reuters\\Lynx Alerting\\LynxAlerting.exe", null, new File("C:\\Program Files (x86)\\Thomson Reuters\\Lynx Alerting\\"));
		            //App.open("C:\\Program Files (x86)\\Thomson Reuters\\Lynx Alerting\\LynxAlerting.exe");
		            //runtime.exec("cmd /c C:\\Program Files (x86)\\Thomson Reuters\\Lynx Alerting\\LynxAlerting.exe");
					//File file = new File("C:\\Program Files (x86)\\Thomson Reuters\\Lynx Alerting\\LynxAlerting.exe");
					//Desktop.getDesktop().open(file);
		            //LAapp.open();
		            Thread.sleep(10000);
					// Check for any error popup
					if (s.wait(GetProperty("iUser"),30) != null) {
							s.find(GetProperty("iUser")).offset(80, 0).click();
							Thread.sleep(2000);
							s.keyDown(Key.CTRL);
							s.type("a");
							Thread.sleep(2000);
							s.keyDown(Key.DELETE);
							s.keyUp(Key.CTRL);
							s.keyUp(Key.DELETE);
							s.type(LAProp.getProperty("tUser"));
							test.pass("Entered User name");
					} else {
							test.fail("User name field doesnot exist");
					}
					if (s.wait(Patternise("iPass","Moderate"),30) != null) {
							//test.pass("Launched LA Application");
						    s.find(Patternise("iPass","Moderate")).offset(80, 0).click();
							s.type(LAProp.getProperty("tPass"));
							test.pass("Entered Password");
					} else {
							test.fail("Password field doesnot exist");
					}
					if (s.exists(Patternise("iSignOn","Moderate")) != null) {
							s.find(Patternise("iSignOn","Moderate")).click();
							test.pass("Clicked on Sign on button");
					} else {
							test.fail("Sign on button doesnot exist");
					}
					Thread.sleep(5000);
					while (count<10) {
						Thread.sleep(5000);
						if(s.exists(Patternise("LAEDITORLOGO","Moderate"))!=null) {
							break;
						}
						else {
								count++;
						}
					}
					if(s.exists(Patternise("LAEDITORLOGO","Moderate"))!=null) {
							test.pass("Successfully logged into LA");
							s.find(Patternise("LAEDITORLOGO","Moderate")).doubleClick();
							LAapp.focus();
					}
					else {
							test.fail("Unable to login to LA");
					}
			}
			else if (Option.equals("Open")){
				if(s.exists(Patternise("LAEDITORLOGO","Moderate"))!=null) {
					LAapp.focus();
					test.pass("LA Window opened and focussed");					
			}
			else {
				LaunchLA(test,"Relaunch");
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
	public static String GetProperty(String Prop) {
		return folder+LAProp.getProperty(Prop);		
	}
	
	public static void CloseLA(ExtentTest test) throws IOException, InterruptedException {
		Runtime runtime = Runtime.getRuntime();     //getting Runtime object
	    runtime.exec("taskkill /F /IM LynxAlerting.exe");
        Thread.sleep(5000);
        if(s.exists(Patternise("LAEDITORLOGO","Moderate"))!=null) {
			test.fail("Unable to close LA Window");
        }
        else {
        	test.pass("LA Window closed Successfully");
        }
	}
	public static void UncheckBoxes(ExtentTest test) {
		Pattern pattern;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			pattern = new Pattern(GetProperty("CheckedBox")).exact();
			while (s.exists(pattern) != null) {
					s.click(pattern);
					Thread.sleep(4000);
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	public static void ValidateObjectDisplayed(ExtentTest test, String Objectref,String Objectname, int Click, String mode) {
		Pattern pattern;
		int count=0,found=0;
		try {
			//test.log(com.aventstack.extentreports.Status.INFO,"ValidateObjectDisplayed method begin");
			pattern = new Pattern(GetProperty(Objectref)).exact();
			while (s.exists(pattern) == null && count<=5) {
				if(s.exists(pattern) != null) {
					found=1;
					break;
				}
				else {
					count++;
					Thread.sleep(2000);
				}
			}
			switch (mode)
			{
			case "Normal":
							if((found==1 || s.exists(pattern) != null) && Click==1) {
								s.click(pattern);
								test.pass("Clicked on "+Objectname);
									//Thread.sleep(2000);
							}
							else if((found==1 || s.exists(pattern) != null) && Click==0) {
								test.pass(Objectname +" is displayed correctly on screen");
							}
							else {
								test.fail(Objectname+" is not displayed on the screen");
							}
							break;
			case "Reverse":
				
				if((found==1 || s.exists(pattern) != null)) {
					test.fail(Objectname +" is displayed on screen");
				}
				else {
					test.pass(Objectname+" is not displayed on the screen");
				}
				break;
			}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			//test.log(com.aventstack.extentreports.Status.INFO,"ValidateObjectDisplayed method end");
		}
		
	}
	public static void ClickonOccurence(String imagename, int occurence) {
		int i=1;
		try {	
		Pattern findpattern= new Pattern(GetProperty(imagename));//.exact();
		Iterator<Match> it = s.findAll(findpattern);
		//System.out.println("Size of iterator "+Iterators.size(it));
		while(it.hasNext()){
		    	if (i==occurence) {
		    		    //System.out.println("Inside if");
				    	//it.next().highlight();
				    	it.next().click(occurence);
				    	break;
		    	}
		    	it.next();
		    	i++;
		    }
		}
		catch(Exception e) {
			test.fail("Unable to find selected occurence of object to click");
		}
	}
	public static void Scrolltoend(String Value) {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try{
			while(s.exists(GetProperty("EndOfDownScroll"))!=null ) {
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
				if(s.exists(GetProperty("UpdateAlarm"))!=null && Value=="Update") {
					test.pass("Scrolled to end of Page");
					break;
						
				}
			    if(s.exists(GetProperty("CreateAlarm"))!=null && Value=="Create") {
			    	test.pass("Scrolled to end of Page");
					break;
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
	
	@SuppressWarnings("finally")
	public static Pattern Patternise(String Obj, String mode) {
		Pattern pattern = null;
		try {
			if(mode.equals("Strict")) {
				pattern=new Pattern(GetProperty(Obj)).exact();
			}
			else if(mode.equals("Easy")) {
				pattern=new Pattern(GetProperty(Obj)).similar(0.7f);
			}
			else if(mode.equals("Moderate")) {
				pattern=new Pattern(GetProperty(Obj)).similar(0.8f);
			}
			else {
				pattern=new Pattern(GetProperty(Obj)).similar(0.5f);
			}
			return pattern;
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());			
		}
		finally {
			return pattern;
		}
	}
	}
