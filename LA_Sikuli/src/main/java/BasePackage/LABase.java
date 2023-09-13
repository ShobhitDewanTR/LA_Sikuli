package BasePackage;
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
			//folder="C:\\Users\\X023840\\git\\Fastwire_Sikuli\\LA_Automation\\src\\test\\resources\\";
			Reportname="LA_Automation_Report ";
			folder=System.getProperty("user.dir")+"\\src\\test\\resources\\";
			LAReader = new FileReader(folder+"TestData\\LA.properties");
			LAProp = new Properties();
			LAProp.load(LAReader);
			s = new Screen();
			LAapp = new App(LAProp.getProperty("iLA"));
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
	
	public static void RelaunchReopenFWTab(ExtentTest test, String option) throws FindFailed, InterruptedException {
//		Pattern pattern;
//		pattern = new Pattern(GetProperty("LATskBr")).exact();
//		if(s.exists(pattern)!=null) {
//			s.click(pattern);
//		}
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try{
			LAapp.focus();
			int count=0,count2=0,crashcount=0;
			//Thread.sleep(4000);
			if(s.exists(Patternise("HighContrastON","Strict")) != null) {
				s.click(Patternise("HighContrastON","Strict"));
				Thread.sleep(3000);
			}
			// Check for any error popup
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
			
			if(option.equals("Reopen") && s.exists(GetProperty("LAEDITORLOGO"))!=null) {
				System.out.println("Reopen");
	//				if(s.exists(GetProperty("LAEDITORLOGO"))!=null) {
					if(s.exists(GetProperty("Fastwiretab"))!=null) {
						while (s.exists(GetProperty("Fastwiretab"))!=null) {
							if (s.exists(GetProperty("FWTabClose"))!=null) {
								s.click(GetProperty("FWTabClose"));
								LAapp.focus();
							}
							if (s.exists(GetProperty("FWTabCloseunfocused")	)!=null) {
								s.click(GetProperty("FWTabCloseunfocused"));
								LAapp.focus();
							}
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
							if(s.exists(Patternise("ErrorOutofProc","Easy"))!=null) {
								test.fail("Application crashed due to memory issue, restarting the app...");
								RelaunchReopenFWTab(test,"Relaunch");
							}
						}
					}
					LAapp.focus();
				    s.keyDown(Key.CTRL);
					s.keyDown(Key.SHIFT);
					s.type("F");
					s.keyUp(Key.CTRL);
					s.keyUp(Key.SHIFT);
					if(s.wait(GetProperty("Fastwiretab"),10)!=null ) {
						test.pass("Successfully opened new Fastwire tab");
					}
					else {
						test.fail("Unable to relaunch Fastwire tab");
					}
					//Thread.sleep(10000);
					while(s.exists(Patternise("StoryLoading","Easy"),5)!=null && count2 < 6) {
						Thread.sleep(2000);
						count2++;
						System.out.println("Inside story Loading while");
					}
					//test.log(com.aventstack.extentreports.Status.INFO,"RelaunchReopenFWTab Method end");
	//				}
	//				else {
	//					test.fail("Unable to relaunch Fastwire tab as application is not opened");
	//				}
			 }
			 else if ((option.equals("Relaunch")) || (option.equals("Reopen") && s.exists(GetProperty("LAEDITORLOGO"),5)==null)) {
				 	Runtime runtime = Runtime.getRuntime();     //getting Runtime object
					try
			        {	runtime.exec("taskkill /F /IM LA.exe");
			            Thread.sleep(2000);
			            LAapp.open();
					Thread.sleep(10000);
					// Check for any error popup
					System.out.println("Relaunch");
					if(s.exists(Patternise("ErrorMetadataOK_1","Moderate")) != null) {
						System.out.println("In_1");
						s.click(Patternise("ErrorMetadataOK_1","Moderate"));
					}
					if(s.exists(Patternise("ErrorMetadataOK","Moderate")) != null) {
						System.out.println("InOK");
						s.click(Patternise("ErrorMetadataOK","Moderate"));	
					}
					if(s.exists(Patternise("ErrorMetadataOKHC","Moderate")) != null) {
						System.out.println("InOKHC");
						s.click(Patternise("ErrorMetadataOKHC","Moderate"));
					}
					if(s.exists(Patternise("ErrorMetadataOKLGHT","Moderate")) != null) {
						System.out.println("InLGHT");
						s.click(Patternise("ErrorMetadataOKLGHT","Moderate"));
					}
					
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
					if (s.wait(GetProperty("iPass"),30) != null) {
						test.pass("Launched LA Application");
						s.type(GetProperty("iPass"), LAProp.getProperty("tPass"));
						test.pass("Entered Password");
					} else {
						test.fail("Password field doesnot exist");
					}
					if (s.exists(GetProperty("iSignOn")) != null) {
						s.find(GetProperty("iSignOn")).click();
						test.pass("Clicked on Sign on button");
					} else {
						test.fail("Sign on button doesnot exist");
					}
					while(s.exists(GetProperty("Controls"))==null && count!=5) {
						Thread.sleep(5000);
						count++;
					}
					//s.wait(GetProperty("Home"),50);
					Thread.sleep(5000);
					if(s.exists(GetProperty("Home1"))!=null || s.exists(GetProperty("Home2"))!=null ) {
						test.pass("Successfully logged into Fastwire");
						s.keyDown(Key.CTRL);
						s.keyDown(Key.SHIFT);
						s.type("F");
						s.keyUp(Key.CTRL);
						s.keyUp(Key.SHIFT);
						//Thread.sleep(5000);
						while(s.exists(Patternise("StoryLoading","Easy"),5)!=null && count2 < 6) {
							Thread.sleep(2000);
							count2++;
							System.out.println("Inside story Loading while with option selected as"+option);
						}
						if(s.exists(GetProperty("Fastwiretab"),15)!=null || s.exists(GetProperty("Fastwiretabunfocused"),15)!=null ) {
							test.pass("Successfully relaunched and opened new Fastwire tab");
							Thread.sleep(5000);
						}
						else {
							test.fail("Unable to relaunch Fastwire tab");
						}
					}
					else {
						test.fail("Unable to login to Fastwire");
					}
			      }
					catch(Exception e) {
						test.fail("Error Occured: "+e.getLocalizedMessage());
					}
			 }
			 else {
				 System.out.println("Issue.. You Entered Else!!!");
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
	public static void OpenUserPrfrncs(ExtentTest test, String typeofpreference,String Option) {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		Pattern pattern1,pattern2;
		int count=0;
		try {
				s.exists(GetProperty("LAEDITORLOGO"),4).rightClick();
				test.pass("Right Clicked LA Fastwire icon");
				switch(typeofpreference) {
				
				case "FastwirePreferences":
						s.exists(GetProperty("Fastwire_Preferences"),5).click();
						test.pass("Clicked Fastwire Preferences icon");
						if (s.exists(GetProperty("FWPrfrncstab"),10)!=null) {
							test.pass("Fastwire Preference options window loaded");
							Thread.sleep(1000);
							if(s.exists(Patternise("FWWebPreferencesMenuSlctd","Moderate"),15)!=null) {
								break;
							}
							else if (s.exists(GetProperty("LogInZscaler"),10)!=null) {
								s.click(Patternise("LogInZscaler","Moderate"));
								test.pass("Clicked Login for Zscaler authentication");
								Thread.sleep(5000);
							}
							//if(s.exists(GetProperty("BlankFWPreferences"),20)!=null) {
							//	test.fail("No options shown in Fastwire Preference window");
								/*
								 * Thread.sleep(1000); count++; if(count>20) {
								 * RelaunchReopenFWTab(test,"Relaunch");
								 * s.wait(GetProperty("LAEDITORLOGO"),4).rightClick();
								 * s.wait(GetProperty("Fastwire_Preferences"),5).click(); Thread.sleep(1000); }
								 */
							//}
							else{ Thread.sleep(5000);
							}
						}
						else {
							test.fail("Fastwire Preference options window not loaded");
						}
						
						
						break;
				case "Preferences":
						s.wait(GetProperty("Preferences"),4).click();
						test.pass("Clicked Preferences icon");
						//Thread.sleep(8000);
						if (s.exists(GetProperty("LAPreferences"),10)!=null) {
							test.pass("Preference options window loaded");
							Thread.sleep(1000);
						}
						else {
							test.fail("Preference options window not loaded");
						}
						break;
				}
				switch(Option) {
					case "Feeds":
								OpenFilterSources(test);
								int countFeeds=0;
								if (s.exists(GetProperty("FeedsSlctd"),10)!=null) {
									test.pass("Filters Sources - Feeds Option already selected");
								}
								else if(s.exists(GetProperty("Feeds"),10)!=null) {
									s.find(GetProperty("Feeds")).click();
									test.pass("Found and clicked Filters Sources - Feeds Option");
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
									test.fail("Filters Sources - Feeds Option not found");
								}
								pattern1 = new Pattern(GetProperty("EnblFltrs")).exact();
								pattern2 = new Pattern(GetProperty("EnblFltrsSlctd")).exact();
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
								OpenFilterSources(test);
								if (s.exists(Patternise("CmpnyLstSlctd","Moderate"),10)!=null) {
									test.pass("Filters Sources - Company Lists Option already selected");
								}
								else if(s.exists(Patternise("CmpnyLst","Moderate"),10)!=null) {
									s.find(GetProperty("CmpnyLst")).click();
									test.pass("Found and clicked Filters Sources -  Company Lists Option");
									Thread.sleep(8000);
								}
								else {
									test.fail("Filters Sources - Company Lists Option not found");
								}
								break;
					case "Automations":
								OpenFilterSources(test);
								int countAutomations=0;
								if (s.exists(GetProperty("AutmtnSlctd"),5)!=null) {
									test.pass("Filters Sources - Automations Option already selected");
								}
								else if(s.exists(GetProperty("Autmtn"),5)!=null) {
									s.find(GetProperty("Autmtn")).click();
									test.pass("Found and clicked Filters Sources - Automations Option");
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
									test.fail("Filters Sources - Automations Option not found");
								}
								break;
					case "WebWatch":
								OpenFilterSources(test);
								int countWebWatchs=0;
								if (s.exists(Patternise("WebWatchSlctd","Moderate"),5)!=null) {
									test.pass("Filters Sources - Web Watchers Option already selected");
								}
								else if(s.exists(Patternise("WebWatch","Moderate"),5)!=null) {
									s.find(Patternise("WebWatch","Moderate")).click();
									test.pass("Found and clicked Filters Sources - Web Watchers Option");
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
									test.fail("Filters Sources - Web Watchers Option not found");
								}
								break;
					case "HeadlineAlarm":
						int Hdlnalrmcnt=0;
						if (s.exists(GetProperty("HdlnAlrmsred"),5)!=null) {
							test.pass("Headline Alarms Option already selected");
						}
						else if(s.exists(GetProperty("HdlnAlrms"),5)!=null) {
							s.find(GetProperty("HdlnAlrms")).click();
							test.pass("Found and clicked Headline Alarms Option");
							while(s.exists(Patternise("LoadinginPreferences","Strict"))!=null) {
								Thread.sleep(1000);
								Hdlnalrmcnt++;
								if(Hdlnalrmcnt>10) {
									test.fail("Headline ALarms not loaded");
									break;
								}
							}
							Thread.sleep(3000);
						}
						else {
							test.fail("Headline Alarms Option not found");
						}
						break;
					case "JobRole":	
						if (s.exists(Patternise("JobRoles","Easy"),10)!=null) {
							s.click(Patternise("JobRoles","Easy"));
							test.pass("Clicked Job Roles Option");
						}
						else if (s.exists(Patternise("JobRolesSelected","Easy"),5)!=null ) {
							test.pass("Job Roles Option already selected");
						}
						else {
							test.fail("Job Roles Option not found in LA Preferences Window");
						}
						break;
					case "Application":
						if (s.exists(Patternise("Application","Strict"),5)!=null) {
							s.click(Patternise("Application","Strict"));
							test.pass("Clicked Application Option");
						}
						else if (s.exists(Patternise("ApplicationSelected","Strict"),5)!=null ) {
							test.pass("Application Option already selected");
						}
						else {
							test.fail("Application Option not found in LA Preferences Window");
						}
						break;
					case "DefaultCodesBAE":
						if (s.exists(Patternise("FWUsrPrfrncs","Strict"),5)!=null) {
							s.find(Patternise("FWUsrPrfrncs","Strict")).getTopLeft().click();
							test.pass("Expanded Fastwire-User Preferences Option");
							s.click(Patternise("DefaultCodesBAE","Strict"));
						}
						else {
							test.fail("Fastwire-User Preferences Option not found in LA Preferences Window");
						}
						break;
					case "ShortCompanyNames":
						if (s.exists(Patternise("FWGlblSetngs","Strict"),5)!=null) {
							s.find(Patternise("FWGlblSetngs","Strict")).getTopLeft().click();
							test.pass("Expanded Fastwire-Global Settings Option");
							s.click(Patternise("Shrtcmpnynm","Strict"));
							Thread.sleep(3000);
						}
						else {
							test.fail("Fastwire-Global Settings Option not found in LA Preferences Window");
						}
						break;
					case "CompanyListAdmin":
						if (s.exists(Patternise("FWGlblSetngs","Strict"),5)!=null) {
							s.find(Patternise("FWGlblSetngs","Strict")).getTopLeft().click();
							test.pass("Expanded Fastwire-Global Settings Option");
							s.click(Patternise("CmpnyLstAdmn","Strict"));
							Thread.sleep(3000);
						}
						else {
							test.fail("Fastwire-Global Settings Option not found in LA Preferences Window");
						}
						break;
					case "DefaultCodesFeeds":
						if (s.exists(Patternise("FWGlblSetngs","Strict"),5)!=null) {
							s.find(Patternise("FWGlblSetngs","Strict")).getTopLeft().click();
							test.pass("Expanded Fastwire-Global Settings Option");
							s.click(Patternise("Defaultcodesfeeds","Strict"));
							Thread.sleep(3000);
						}
						else {
							test.fail("Fastwire-Global Settings Option not found in LA Preferences Window");
						}
						break;
					case "Fastwire-UserPreferences":
						if (s.exists(Patternise("FWUsrPrfrncs","Strict"),5)!=null) {
							s.find(Patternise("FWUsrPrfrncs","Strict")).click();
							test.pass("Expanded Fastwire-User Preferences Option");
						}
						else {
							test.fail("Fastwire-User Preferences Option not found in LA Preferences Window");
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
	public static void ClearAutomationSelection(ExtentTest test, int NbrOfRows) {
		String nameofCurrMethod = new Throwable()
	            .getStackTrace()[0]
	            .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			int count=0;
			while(s.exists(Patternise("LoadingSources","Easy"),5)!=null && count < 6) {
				Thread.sleep(2000);
				count++;
			}
			if(s.exists(Patternise("LoadingSources","Easy"),2)!=null) {
				test.fail("Automation screen not loaded");
				return;
			}
//			if(s.exists(Patternise("EmptyAutmtnArea","Strict"),5)!=null || s.exists(Patternise("EmptyAutmtnAreaRed","Strict"),5)!=null) {
//				test.pass("No Automations present, already clear.");
//			}
//			else {
//					//pattern1 = new Pattern(GetProperty("AutmtnArea")).exact();
//					s.click(Patternise("AutmtnArea","Moderate"));
//					Thread.sleep(2000);
//					for(int i=0;i<NbrOfRows;i++) {
//						s.type(Key.BACKSPACE);
//					}
//					s.find(GetProperty("SVEnbld")).click();
//					test.pass("Cleared existing Automations and saved.");
//					Thread.sleep(3000);
//			}
			if(s.exists(Patternise("NoAutomations","Strict"),5)!=null) {
				test.pass("No Automations present, already clear.");
			}
			else {
					//pattern1 = new Pattern(GetProperty("AutmtnArea")).exact();
					while (s.exists(Patternise("DeleteAutomations","Moderate"))!=null) {
							s.click(Patternise("DeleteAutomations","Moderate"),3);
							s.click(Patternise("CnfrmDeleteAutomations","Moderate"),3);
					}
					s.find(GetProperty("SVEnbld")).click();
					test.pass("Cleared existing Automations and saved.");
					Thread.sleep(3000);
			}
		}	
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	public static void ClearWebWatchSelection(ExtentTest test, int NbrOfRows) {
		String nameofCurrMethod = new Throwable()
	            .getStackTrace()[0]
	            .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			int count=0;
			while(s.exists(Patternise("LoadingSources","Easy"),5)!=null && count < 6) {
				Thread.sleep(2000);
				count++;
			}
			if(s.exists(Patternise("LoadingSources","Easy"),2)!=null) {
				test.fail("Web Watchers screen not loaded");
				return;
			}
			if(s.exists(Patternise("NoWebWatchers","Strict"),5)!=null) {
				test.pass("No Web Watchers present, already clear.");
			}
			else {
					//pattern1 = new Pattern(GetProperty("AutmtnArea")).exact();
					while (s.exists(Patternise("DeleteWebWatcher","Moderate"))!=null) {
							s.click(Patternise("DeleteWebWatcher","Moderate"),3);
							s.click(Patternise("CnfrmDeleteWebWatcher","Moderate"),3);
					}
					s.find(GetProperty("SVEnbld")).click();
					test.pass("Cleared existing Automations and saved.");
					Thread.sleep(3000);
			}
		}	
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	public static void ValidateAutomationSave(ExtentTest test,String Automation) {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			//if(s.exists(Patternise(Automation+"Delete","Moderate"),5)!=null && s.exists(Patternise("SVDsabld","Moderate"),15)!=null) {
			if(s.exists(Patternise("DeleteAutomations","Moderate"),4)!=null) {
				test.pass("Saved "+Automation+" automation");
			}
			else {
				test.fail(Automation + " not saved");
			}
		}	
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}	
	
	public static void EnterAutomation(ExtentTest test,String Automation) {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
				//pattern=new Pattern(GetProperty("AutmtnArea")).exact();
//				s.click(Patternise("AutmtnArea","Moderate"));
//				Thread.sleep(2000);
//				s.type(Automation);
//				Thread.sleep(3000);
//				if(s.exists(GetProperty(Automation),5)!=null) {
//					s.find(GetProperty(Automation)).offset(-100,0).click();
//					s.find(GetProperty("AddSlctdBtn")).click();
//					test.pass("Added the user entered "+ Automation +" automation");
//				}
//				else {
//					test.fail("Entered Automation ("+ Automation +") not found in dropdown options");
//				}
				s.click(Patternise("Searchicon","Moderate"));
				Thread.sleep(2000);
				s.type(Automation);
				Thread.sleep(3000);
				if(s.exists(Patternise("checkboxAutomations","Easy"),5)!=null) {
					s.click(Patternise("checkboxAutomations","Easy"),3);
					s.click(Patternise("AddSelected","Moderate"),3);
					test.pass("Added the user entered "+ Automation +" automation");
				}
				else {
					test.fail("Entered Automation ("+ Automation +") not found in dropdown options");
				}
		}	
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	public static void EnterWebWatcher(ExtentTest test,String WWname) {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
				//pattern=new Pattern(GetProperty("AutmtnArea")).exact();
				s.click(Patternise("Searchicon","Moderate"));
				Thread.sleep(2000);
				s.type(WWname);
				if(s.exists(Patternise("vagazettecom","Moderate"),5)!=null) {
					//s.click(Patternise("vagazettecom","Moderate"),5);
					//s.click(Patternise("AddSlctdBtn","Moderate"),5);
					s.click(Patternise("checkbox","Moderate"),3);
					s.click(Patternise("AddSelected","Moderate"),3);
					test.pass("Added the user entered "+ WWname +" Web Watcher");
				}
				else {
					test.fail("Entered Web Watcher ("+ WWname +") not found in dropdown options");
				}
		}	
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	public static void NavigatetoPublishHistory() {
		try {
			
			if (s.exists(Patternise("PublishHistoryTabUnselected","Moderate"),2)==null && s.exists(Patternise("PublishHistoryTabSelected","Moderate"),2)==null) {
				s.type("y",Key.ALT);
				Thread.sleep(2000);
				s.find(Patternise("PblshHstryWndwArrw","Moderate")).getBottomRight().click();
				s.click(Patternise("Dock","Moderate"));
			}
			if (s.exists(Patternise("PublishHistoryTabUnselected","Moderate"),2)!=null) {
					s.click(Patternise("PublishHistoryTabUnselected","Moderate"));
				}
				else if (s.exists(Patternise("PublishHistoryTabSelected","Moderate"),2)!=null) {
					s.click(Patternise("PublishHistoryTabSelected","Moderate"));
				}
				Thread.sleep(2000);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	public static void  OpenFilterSources(ExtentTest test) {
		try {
				if (s.exists(Patternise("FltrSrcsArwClsd","Strict"),5)!=null) {
				 	if (s.exists(GetProperty("FltrSrcsSlctd"),5)!=null) {
				 		s.find(GetProperty("FltrSrcsSlctd")).click();
						test.pass("Found and expanded Filters Sources Link");
						Thread.sleep(2000);
					}
				 	else if(s.exists(GetProperty("FltrSrcs"),5)!=null) {
				 		s.find(GetProperty("FltrSrcs")).click();
						test.pass("Found and expanded Filters Sources Link");
						Thread.sleep(2000);
				 	}
					else {
						test.fail("Filter Sources Option not found");
					}
					
				}
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
	}
	public static void ClearMetaData() {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		Region r;
		try {
			if(s.exists(Patternise("Show","Strict"))!=null) {
				s.wait(Patternise("Show","Strict"),5).click();
			}
			s.find(GetProperty("AlertEditorTab")).click();
			if(s.exists(Patternise("BlankAEMetadata","Easy"))!=null) {
				test.pass("Alert editor Metadata already empty");
				return;
			}
			/*
			 * for (int i=s.find(Patternise("RIC_Unslctd","Easy")).getY()+10; i>=
			 * s.find(Patternise("AlertEditorTab","Easy")).getY()+10;i-=18) {
			 * //System.out.println(s.find(GetProperty("RIC")).getY()); r=new Region(980, i,
			 * 1, 1); System.out.println("0"); r.click(); for (int j=0;j<50;j++) {
			 * s.keyDown(Key.BACKSPACE); s.keyUp(Key.BACKSPACE); } Thread.sleep(2000);
			 * if(s.exists(Patternise("RIC","Easy"))!=null) { if
			 * (i>s.find(Patternise("RIC","Easy")).getY()) {
			 * i=s.find(Patternise("RIC","Easy")).getY(); } } else
			 * if(s.exists(Patternise("RIC_Unslctd","Easy"))!=null) { if
			 * (i>s.find(Patternise("RIC_Unslctd","Easy")).getY()) {
			 * i=s.find(Patternise("RIC_Unslctd","Easy")).getY(); } }
			 * if(s.exists(Patternise("BlankTopics","Strict"))!=null &&
			 * s.exists(Patternise("BlankProducts","Strict"))!=null) { break; } }
			 * if(s.exists(Patternise("BlankUSN","Strict"))==null) { r=new
			 * Region(s.find(Patternise("GetUSN","Strict")).getX()-25,s.find(Patternise(
			 * "GetUSN","Strict")).getY()+10 , 1, 1); System.out.println("1"); r.click();
			 * for (int j=0;j<15;j++) { s.keyDown(Key.BACKSPACE); s.keyUp(Key.BACKSPACE); }
			 * Thread.sleep(4000); }
			 * 
			 * if(s.exists(Patternise("BlankNamedItems","Strict"))==null) { r=new
			 * Region(s.find(Patternise("GetUSN","Strict")).getX()-15,s.find(Patternise(
			 * "GetUSN","Strict")).getY()+40, 1, 1); System.out.println("2"); r.click(); for
			 * (int j=0;j<10;j++) { s.keyDown(Key.BACKSPACE); s.keyUp(Key.BACKSPACE); }
			 * s.find(GetProperty("AlertEditorTab")).click(); Thread.sleep(6000); }
			 */
			
			
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			//Move To Products
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			//Delete Products
			for (int i=0;i<=15;i++) {
				s.keyDown(Key.BACKSPACE); 
				s.keyUp(Key.BACKSPACE);
			}
			//Delete Topics
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			for (int i=0;i<=15;i++) {
				s.keyDown(Key.BACKSPACE); 
				s.keyUp(Key.BACKSPACE);
			}
			//Delete RICS
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			for (int i=0;i<=15;i++) {
				s.keyDown(Key.BACKSPACE); 
				s.keyUp(Key.BACKSPACE);
			}
			//Delete USN
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			for (int i=0;i<=15;i++) {
				s.keyDown(Key.BACKSPACE); 
				s.keyUp(Key.BACKSPACE);
			}
			//Delete NAMED ITEMS
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			s.keyDown(Key.TAB);
			s.keyUp(Key.TAB);
			for (int i=0;i<=15;i++) {
				s.keyDown(Key.BACKSPACE); 
				s.keyUp(Key.BACKSPACE);
			}
			s.find(GetProperty("AlertEditorTab")).click();
			if(s.exists(Patternise("BlankAEMetadata","Easy"),2)!=null) {
				test.pass("Alert editor Metadata emptied");
				return;
			}
			for (int i=s.find(Patternise("chars","Moderate")).getY()-5; i>=s.find(Patternise("GetUSN","Moderate")).getY();i-=15) {
			  	r=new Region(s.find(Patternise("GetUSN","Moderate")).getX()-310, i, 1, 1);
				r.click(); 
				for (int j=0;j<20;j++) {
				  s.keyDown(Key.BACKSPACE); 
				  s.keyUp(Key.BACKSPACE); 
				 }
				 Thread.sleep(1000);
				 if (i>s.find(Patternise("chars","Moderate")).getY()-5) {
					 i=s.find(Patternise("chars","Moderate")).getY()-5;
				 }
				 if (s.exists(Patternise("BlankAEMetadata","Moderate"),3) != null) {
						break;
					} 
			}
			s.find(GetProperty("AlertEditorTab")).click();
			if (s.exists(Patternise("BlankProducts","Easy"),5) != null) {
				test.pass("Product cleared");
			}
			else {
				test.fail("Product not cleared");
			}
			if (s.exists(Patternise("BlankTopics","Easy"),5) != null) {
				test.pass("Topics Cleared");
			}
			else {
				test.fail("Topics not cleared");
			}
			if (s.exists(Patternise("BlankRICS","Easy"),5) != null) {
				test.pass("RICs Cleared");
			}
			else {
				test.fail("RICs not cleared");
			}
			if (s.exists(Patternise("BlankUSN","Easy"),5) != null) {
				test.pass("USN Cleared");
			}
			else {
				test.fail("USN not cleared");
			}
			if (s.exists(Patternise("BlankNamedItems","Easy"),5) != null) {
				test.pass("NamedItems Cleared");
			}
			else {
				test.fail("NamedItems not cleared");
			}
			
		} 
		catch (Exception e) {
			test.error("Error occured :"+ e.getMessage());
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
	
	public static String SelectFeed(ExtentTest test,String Country, String Feed) {
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		Pattern pattern1,pattern2;
		Region r;
		String CntryFeedUnSlctd,CntryFeedSlctd,FeedOn,FeedOff;
		CntryFeedUnSlctd=Country+"Feed";
		CntryFeedSlctd=Country+"FeedSlctd";
		FeedOn=Feed+"On";
		FeedOff=Feed+"Off";
		int countCntry=0,countFeed=0,clickcoordinate=0, feedxcoordinate=0,feedycoordinate=0;
		try {
			if (s.exists(GetProperty("LogInZscaler"),10)!=null) {
				s.click(Patternise("LogInZscaler","Moderate"));
				test.pass("Clicked Login for Zscaler authentication");
				Thread.sleep(5000);
			}
			s.find(Patternise("EnblFltrsSlctd","Easy")).offset(0,70).click();
			s.mouseMove(Patternise("EnblFltrsSlctd","Easy").targetOffset(0,35));
			while(s.exists(Patternise("FeedScrollEnd","Strict"))==null) {
				Thread.sleep(1000);
				if (s.exists(Patternise(CntryFeedUnSlctd,"Moderate"))!=null || s.exists(Patternise(CntryFeedSlctd,"Moderate"))!=null || countCntry >10) {
					break;
				}
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
				countCntry++;
			}
			if (s.exists(Patternise(CntryFeedUnSlctd,"Moderate"),2)!=null) {
				s.find(Patternise(CntryFeedUnSlctd,"Moderate")).click();
				test.pass("Found and Selected "+Country+" Country");
				Thread.sleep(1000);
			}
			else if (s.exists(Patternise(CntryFeedSlctd,"Moderate"),2)!=null) {
				s.find(Patternise(CntryFeedSlctd,"Moderate")).click();
				test.pass(Country+" Country already selected");
				Thread.sleep(1000);
			}
			else {
				test.fail("Unable to select "+Country+" Country");
			}
			//pattern1 = new Pattern(GetProperty(FeedOn)).exact();
			//pattern2 = new Pattern(GetProperty(FeedOff)).exact();
			s.click(Patternise("SelectAllFeed","Easy"));
			s.mouseMove(Patternise("EnblFltrsSlctd","Easy").targetOffset(0,35));
			
			//feedxcoordinate=s.find(GetProperty("SearchFeed")).getX(); 
			//feedycoordinate=s.find(GetProperty("SearchFeed")).getY();
			//r=new Region(feedxcoordinate, feedycoordinate,50,s.find(GetProperty("SaveFeedDisabled")).getY()-feedycoordinate );
			//System.out.println("Xcoordinate: "+feedxcoordinate + " Y Coordinate: "+feedycoordinate + " Height: "+(s.find(GetProperty("SaveFeedDisabled")).getY()-feedycoordinate ) );
			//while(r.exists(Patternise("FeedScrollEnd","Strict"))==null) {
			while(countFeed<10){
				Thread.sleep(1000);
				if (s.exists(Patternise(FeedOn,"Moderate"))!=null || s.exists(Patternise(FeedOff,"Moderate"))!=null || countFeed>10) {
					System.out.println("Found");
					break;
				}
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
				countFeed++;
				System.out.println("Page Down");
			}
			if (s.exists(Patternise(FeedOn,"Moderate"),2)!=null) {
				test.pass(Feed+" feed already selected");
			}
			else if(s.exists(Patternise(FeedOff,"Moderate"),2)!=null) {
				s.find(Patternise(FeedOff,"Moderate")).offset(10,10).getTopLeft().click();
				test.pass("Selected "+Feed+" feed");
				Thread.sleep(1000);
			}
			else {
				test.fail(Feed+" feed not found");
			}
			return FeedOn+"ddn";
		}	
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
			return FeedOn;
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	public static void ReverseFeedSelection(ExtentTest test,String Country, String Feed) {
		String CntryFeedSlctd,FeedOn,FeedOff;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		int countCntry=0,countFeed=0,countuncheck=0;
		Region r;
		int clickcoordinate=0,feedxcoordinate=0, feedycoordinate=0;
		try {
			if (s.exists(GetProperty("LogInZscaler"),10)!=null) {
				s.click(Patternise("LogInZscaler","Moderate"));
				test.pass("Clicked Login for Zscaler authentication");
				Thread.sleep(5000);
			}
			CntryFeedSlctd=Country+"FeedSlctd";
			FeedOn=Feed+"On";
			FeedOff=Feed+"Off";
			//Reverse the selections made
			s.find(Patternise("EnblFltrsSlctd","Easy")).offset(0,70).click();
			s.mouseMove(Patternise("EnblFltrsSlctd","Easy").targetOffset(0,35));
			while(s.exists(Patternise("FeedScrollEnd","Strict"))==null) {
				Thread.sleep(1000);
				if (s.exists(Patternise(CntryFeedSlctd,"Moderate"))!=null || countCntry>10) {
					break;
				}
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
				countCntry++;
			}
			s.find(Patternise(CntryFeedSlctd,"Moderate")).click();
			test.pass("Selected "+Country+" Country Feed");
			Thread.sleep(3000);
			s.click(Patternise("SelectAllFeed","Moderate"));
			s.mouseMove(Patternise("EnblFltrsSlctd","Easy").targetOffset(0,35));
			//feedxcoordinate=s.find(GetProperty("SearchFeed")).getX(); 
			//feedycoordinate=s.find(GetProperty("SearchFeed")).getY();
			//r=new Region(feedxcoordinate, feedycoordinate,50,s.find(GetProperty("SaveFeedDisabled")).getY()-feedycoordinate );
			//while(r.exists(Patternise("FeedScrollEnd","Strict"))==null) {
			while(countFeed<10) {
			Thread.sleep(1000);
				if (s.exists(Patternise(FeedOn,"Moderate"))!=null || countFeed>10) {
					break;
				}
				s.keyDown(Key.PAGE_DOWN);
				s.keyUp(Key.PAGE_DOWN);
				countFeed++;
			}
			if(s.exists(Patternise(FeedOn,"Moderate"))!=null) {
				s.find(Patternise(FeedOn,"Moderate")).offset(10,10).getTopLeft().click();
				test.pass("Unselected "+Feed+" feed filter turning it off");
			}
			else if (s.exists(Patternise(FeedOff,"Moderate"))!=null) {
				test.pass(Feed+" already unselected");
			}
			else {
				test.fail("Feed not found");
			}
			
		}	
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
}
