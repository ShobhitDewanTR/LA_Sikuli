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

public class PublishCases extends BasePackage.LABase {
	static Pattern pattern, pattern1, pattern2;
	public PublishCases() {
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
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9"})
	@Test
	public static void VerifyPublishScenarios(String type,String Hdln, String usn,String pdct, String tpc, String ric, String slg, String catcode,String nmditm,String bdy) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		Region r;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			//System.out.println(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX());
			status=LaunchLA(test,"Open","YES");
			if(status==1) {
				s.wait(Patternise("New"+type,"Moderate"),2).click();
				test.pass("Clicked on New"+type+" button");
				Thread.sleep(2000);
				if (s.exists(Patternise("Expand"+type,"Moderate")) != null) {
					s.wait(Patternise("Expand"+type,"Moderate"),2).click();
					test.pass("Clicked on Expand "+ type +" button");
					switch(type) {
					  case "Story":
					    EnterStoryData(test,Hdln,usn,pdct,tpc,ric,slg,catcode,nmditm,bdy);
					    break;
					  case "Alert":
						EnterAlertData(test,Hdln,usn,pdct,tpc,ric,nmditm);
						break;
					  case "Econ":
							EnterEconData(test,Hdln,usn,pdct);
							break;
						  
					  default:
					    test.fail("Wrong type inputted.Please correct the type in Data Sheet and rerun");
					}
					
					if (s.exists(Patternise("Publish","Moderate")) != null) {
						s.wait(Patternise("Publish","Moderate"),2).click();
						test.pass("Clicked on Publish button");
						//Goto Published Section
						s.wait(Patternise("Published","Moderate"),2).click();
						test.pass("Navigated to Published Section");
						Thread.sleep(3000);
						//Check if the story is published or not
						r=new Region(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX()-350, s.find(Patternise("EconAlertStoryHeadline","Moderate")).getY(), 520, 40);
						//r.highlight();
						if (s.exists(Patternise("PublishedTest"+type+"Headline","Moderate")) != null) {
							test.pass(type+ " published and found in published section");
						}
						else {
							test.fail(type+ " not published");
						}
					} else {
							test.fail("Publish Not Enabled");
					}
				} else {
						test.fail("New "+type+" Template not loaded, Cannot Continue");
				}
			}
			//CloseLA(test);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9"})
	@Test
	public static void VerifyPublishNegativeScenarios(String type,String Hdln, String usn,String pdct, String tpc, String ric, String slg, String catcode,String nmditm,String bdy) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			//System.out.println(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX());
			status=LaunchLA(test,"Open","YES");
			if(status==1) {
				s.wait(Patternise("New"+type,"Moderate"),2).click();
				test.pass("Clicked on New"+type+" button");
				Thread.sleep(2000);
				if (s.exists(Patternise("Expand"+type,"Moderate")) != null) {
					s.wait(Patternise("Expand"+type,"Moderate"),2).click();
					test.pass("Clicked on Expand "+ type +" button");
					switch(type) {
					  case "Story":
					    EnterStoryData(test,Hdln,usn,pdct,tpc,ric,slg,catcode,nmditm,bdy);
					    break;
					  case "Alert":
						EnterAlertData(test,Hdln,usn,pdct,tpc,ric,nmditm);
						break;
					  case "Econ":
						EnterEconData(test,Hdln,usn,pdct);
						break;
					  default:
					    test.fail("Wrong type inputted.Please correct the type in Data Sheet and rerun");
					}
					if (s.exists(Patternise("PublishDisabled","Strict"),2) != null || s.exists(Patternise("PublishDisabled2","Strict"),2) != null ) {
						test.pass("Publish button is disabled without mandatory fields entered");
					} else {
							test.fail("Publish is enabled without mandatory fields entered");
					}
				} else {
						test.fail("New "+type+" Template not loaded, Cannot Continue");
				}
			}
			//CloseLA(test);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10","param11","param12","param13","param14","param15","param16","param17","param18","param19"})
	@Test
	public static void VerifyBundlePublish(String type1,String Hdln1, String usn1,String pdct1, String tpc1, String ric1, String slg1, String catcode1,String nmditm1,String bdy1,String type2,String Hdln2, String usn2,String pdct2, String tpc2, String ric2, String slg2, String catcode2,String nmditm2,String bdy2) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		Region r;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			//System.out.println(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX());
			status=LaunchLA(test,"Open","YES");
			if(status==1) {
				s.wait(Patternise("NewBundle","Moderate"),2).click();
				test.pass("Clicked on NewBundle button");		
				Thread.sleep(2000);
				test.info("Entering Details for first "+type1);
				s.wait(Patternise("New"+type1,"Moderate"),2).click();
				test.pass("Clicked on New"+type1+" button");
				Thread.sleep(2000);
				if (s.exists(Patternise("Expand"+type1,"Moderate")) != null) {
					s.wait(Patternise("Expand"+type1,"Moderate"),2).click();
					test.pass("Clicked on Expand "+ type1 +" button");
					switch(type1) {
					  case "Story":
					    EnterStoryData(test,Hdln1,usn1,pdct1,tpc1,ric1,slg1,catcode1,nmditm1,bdy1);
					    break;
					  case "Alert":
						EnterAlertData(test,Hdln1,usn1,pdct1,tpc1,ric1,nmditm1);
						break;
					  case "Econ":
						EnterEconData(test,Hdln1,usn1,pdct1);
						break;
					  default:
					    test.fail("Wrong type inputted.Please correct the type in Data Sheet and rerun");
					}
					if (s.exists(Patternise("Expand"+type1,"Moderate")) != null) {
						s.wait(Patternise("Expand"+type1,"Moderate"),2).click();
					}
					//Enter 2nd Story,Alert,Econ
					test.info("Entering Details for second "+type2);
					s.wait(Patternise("New"+type2,"Moderate"),2).click();
					test.pass("Clicked on New"+type2+" button");
					Thread.sleep(2000);
					if (s.exists(Patternise("Expand"+type2,"Moderate")) != null) {
						s.wait(Patternise("Expand"+type2,"Moderate"),2).click();
						test.pass("Clicked on Expand "+ type2 +" button");
						switch(type2) {
						  case "Story":
						    EnterStoryData(test,Hdln2,usn2,pdct2,tpc2,ric2,slg2,catcode2,nmditm2,bdy2);
						    break;
						  case "Alert":
							EnterAlertData(test,Hdln2,usn2,pdct2,tpc2,ric2,nmditm2);
							break;
						  case "Econ":
							EnterEconData(test,Hdln2,usn2,pdct2);
							break;
						  default:
						    test.fail("Wrong type inputted.Please correct the type in Data Sheet and rerun");
						}
						if (s.exists(Patternise("Expand"+type2,"Moderate")) != null) {
							s.wait(Patternise("Expand"+type2,"Moderate"),2).click();
						}
					}
					 else {
							test.fail("New "+type2+" Template not loaded, Cannot Continue");
					}
					if (s.exists(Patternise("Publish","Moderate")) != null) {
						s.wait(Patternise("Publish","Moderate"),2).click();
						test.pass("Clicked on Publish Bundle button");
						//Goto Published Section
						s.wait(Patternise("Published","Moderate"),2).click();
						test.pass("Navigated to Published Section");
						Thread.sleep(3000);
						//Check if the story is published or not
						r=new Region(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX()-500, s.find(Patternise("EconAlertStoryHeadline","Moderate")).getY(), 520, 80);
						//r.highlight();
						if (s.exists(Patternise("PublishedTest"+type1+"Headline1","Moderate")) != null && s.exists(Patternise("PublishedTest"+type2+"Headline2","Moderate")) != null) {
							test.pass("Bundle published and " +type1+ " and " +type2+" found in published section");
						}
						else {
							test.fail("Bundle not published");
						}
					} else {
							test.fail("Publish Not Enabled");
					}
				} else {
						test.fail("New "+type1+" Template not loaded, Cannot Continue");
				}
			}
			//CloseLA(test);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9"})
	@Test
	public static void VerifyDelete(String type,String Hdln, String usn,String pdct, String tpc, String ric, String slg, String catcode,String nmditm,String bdy) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			//System.out.println(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX());
			status=LaunchLA(test,"Open","YES");
			if(status==1) {
				s.wait(Patternise("New"+type,"Moderate"),2).click();
				test.pass("Clicked on New"+type+" button");
				Thread.sleep(2000);
				if (s.exists(Patternise("Expand"+type,"Moderate")) != null) {
					s.wait(Patternise("Expand"+type,"Moderate"),2).click();
					test.pass("Clicked on Expand "+ type +" button");
					switch(type) {
					  case "Story":
					    EnterStoryData(test,Hdln,usn,pdct,tpc,ric,slg,catcode,nmditm,bdy);
					    break;
					  case "Alert":
						EnterAlertData(test,Hdln,usn,pdct,tpc,ric,nmditm);
						break;
					  case "Econ":
						EnterEconData(test,Hdln,usn,pdct);
						break;
					  default:
					    test.fail("Wrong type inputted.Please correct the type in Data Sheet and rerun");
					}
					s.wait(Patternise("Delete","Moderate"),2).click();
					test.pass("Clicked on delete button");
					if (s.exists(Patternise("Expand"+type,"Moderate"),3) == null) {
						test.pass(type +" deleted successfully");
					} else {
							test.fail(type + " not deleted");
					}
				} else {
						test.fail("New "+type+" Template not loaded, Cannot Continue");
				}
			}
			//CloseLA(test);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10","param11","param12","param13","param14","param15","param16","param17","param18","param19"})
	@Test
	public static void VerifyBundleDelete(String type1,String Hdln1, String usn1,String pdct1, String tpc1, String ric1, String slg1, String catcode1,String nmditm1,String bdy1,String type2,String Hdln2, String usn2,String pdct2, String tpc2, String ric2, String slg2, String catcode2,String nmditm2,String bdy2) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			//System.out.println(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX());
			status=LaunchLA(test,"Open","YES");
			if(status==1) {
				s.wait(Patternise("NewBundle","Moderate"),2).click();
				test.pass("Clicked on NewBundle button");		
				Thread.sleep(2000);
				test.info("Entering Details for first "+type1);
				s.wait(Patternise("New"+type1,"Moderate"),2).click();
				test.pass("Clicked on New"+type1+" button");
				Thread.sleep(2000);
				if (s.exists(Patternise("Expand"+type1,"Moderate")) != null) {
					s.wait(Patternise("Expand"+type1,"Moderate"),2).click();
					test.pass("Clicked on Expand "+ type1 +" button");
					switch(type1) {
					  case "Story":
					    EnterStoryData(test,Hdln1,usn1,pdct1,tpc1,ric1,slg1,catcode1,nmditm1,bdy1);
					    break;
					  case "Alert":
						EnterAlertData(test,Hdln1,usn1,pdct1,tpc1,ric1,nmditm1);
						break;
					  case "Econ":
						EnterEconData(test,Hdln1,usn1,pdct1);
						break;
					  default:
					    test.fail("Wrong type inputted.Please correct the type in Data Sheet and rerun");
					}
					if (s.exists(Patternise("Expand"+type1,"Moderate")) != null) {
						s.wait(Patternise("Expand"+type1,"Moderate"),2).click();
					}
					//Enter 2nd Story,Alert,Econ
					test.info("Entering Details for second "+type2);
					s.wait(Patternise("New"+type2,"Moderate"),2).click();
					test.pass("Clicked on New"+type2+" button");
					Thread.sleep(2000);
					if (s.exists(Patternise("Expand"+type2,"Moderate")) != null) {
						s.wait(Patternise("Expand"+type2,"Moderate"),2).click();
						test.pass("Clicked on Expand "+ type2 +" button");
						switch(type2) {
						  case "Story":
						    EnterStoryData(test,Hdln2,usn2,pdct2,tpc2,ric2,slg2,catcode2,nmditm2,bdy2);
						    break;
						  case "Alert":
							EnterAlertData(test,Hdln2,usn2,pdct2,tpc2,ric2,nmditm2);
							break;
						  case "Econ":
							EnterEconData(test,Hdln2,usn2,pdct2);
							break;
						  default:
						    test.fail("Wrong type inputted.Please correct the type in Data Sheet and rerun");
						}
						if (s.exists(Patternise("Expand"+type2,"Moderate")) != null) {
							s.wait(Patternise("Expand"+type2,"Moderate"),2).click();
						}
					}
					 else {
							test.fail("New "+type2+" Template not loaded, Cannot Continue");
					}
					if (s.exists(Patternise("Delete","Moderate")) != null) {
						s.wait(Patternise("Delete","Moderate"),2).click();
						test.pass("Deleted 1st "+type1);
						Thread.sleep(3000);
						s.wait(Patternise("Delete","Moderate"),2).click();
						test.pass("Deleted 2nd "+type2);
						Thread.sleep(2000);
						if (s.exists(Patternise("Expand"+type1,"Moderate")) != null && s.exists(Patternise("Expand"+type2,"Moderate")) != null) {
							test.fail(type1+ " and " + type2 + "not deleted from the bundle");
						}
						else {
							test.pass("Deleted "+type1+ " and " + type2 + " from the bundle");
						}
					}
					else {
							test.fail("Delete Not Enabled");
					}
				} else {
						test.fail("New "+type1+" Template not loaded, Cannot Continue");
				}
			}
			//CloseLA(test);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	@Parameters({"param0","param1","param2","param3","param4","param5","param6"})
	@Test
	public static void VerifyCyclicAlertLanguage(String Lang,String Hdln, String usn,String pdct, String tpc, String ric, String nmditm) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		Region r;
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
				Thread.sleep(3000);
				if (s.exists(Patternise("DefaultLanguage","Moderate"),6) != null) {
					s.click(Patternise("DefaultLanguage","Moderate"),2);
					SelectLanguage(Lang);
					Thread.sleep(3000);
					s.wait(Patternise("Save","Moderate"),2).doubleClick();
				}
				else {
					test.fail("Default language dropdown is not found");
				}
				s.wait(Patternise("NewAlert","Moderate"),2).click();
				test.pass("Clicked on NewAlert button");
				Thread.sleep(2000);
				if (s.exists(Patternise("ExpandAlert","Moderate")) != null) {
					s.wait(Patternise("ExpandAlert","Moderate"),2).click();
					test.pass("Clicked on Expand Alert button");
					EnterAlertData(test,Hdln,usn,pdct,tpc,ric,nmditm);
					if (s.exists(Patternise(Lang+"AlertLang","Moderate")) != null) {
						test.pass("Selected Language "+ Lang+ " found in Alert language dropdown before publish" );
					}
					else {
						test.fail("Selected Language "+ Lang+ " not found in Alert language dropdown before publish");
					}
					if (s.exists(Patternise("Publish","Moderate")) != null) {
						s.wait(Patternise("Publish","Moderate"),2).click();
						test.pass("Clicked on Publish button");
						Thread.sleep(3000);
						//Check for New blank alert template generated or not
						if (s.exists(Patternise("ExpandCyclicalAlert","Easy"),5) != null) {
							s.wait(Patternise("ExpandCyclicalAlert","Easy"),2).click();
							if (s.exists(Patternise(Lang+"AlertLang","Moderate")) != null) {
								test.pass("Selected Language "+ Lang+ " found in cyclic Alert language dropdown after publish" );
							}
							else {
								test.fail("Selected Language "+ Lang+ " not found in cyclic Alert language dropdown after publish");
							}
						}
						else {
							test.fail("New Cyclic Alert template not generated");
						}
						
					} else {
							test.fail("Publish Not Enabled");
					}
				} else {
						test.fail("New Alert Template not loaded, Cannot Continue");
				}
			}
			//CloseLA(test);
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
			Thread.sleep(3000);
			if (s.exists(Patternise("DefaultLanguage","Moderate"),5) != null) {
				s.wait(Patternise("DefaultLanguage","Moderate"),2).click();
				SelectLanguage("English");
				Thread.sleep(3000);
				s.wait(Patternise("Save","Moderate"),2).doubleClick();
			}
			else {
				test.fail("Default language dropdown is not found");
			}
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}

}
