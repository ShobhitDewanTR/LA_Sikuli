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

public class QueueCases extends BasePackage.LABase {
	static Pattern pattern, pattern1, pattern2;
	public QueueCases() {
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
	
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10","param11","param12","param13","param14","param15","param16","param17","param18","param19"})
	@Test
	public static void VerifyBundleQueue(String type1,String Hdln1, String usn1,String pdct1, String tpc1, String ric1, String slg1, String catcode1,String nmditm1,String bdy1,String type2,String Hdln2, String usn2,String pdct2, String tpc2, String ric2, String slg2, String catcode2,String nmditm2,String bdy2) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		Region r;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
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
					//Saved the data
					s.wait(Patternise("ExpandBundle","Moderate"),2).click();
					s.wait(Patternise("ExpandBundle","Moderate"),2).offset(200, 0).doubleClick();
					s.type("TestBundleQueue");
					Thread.sleep(2000);
					//s.wait(Patternise("Embargo","Moderate"),3).click();
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.keyDown(Key.SPACE);
					s.keyUp(Key.SPACE);
					Thread.sleep(2000);
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.keyDown(Key.RIGHT);
					s.keyUp(Key.RIGHT);
					s.keyDown(Key.UP);
					s.keyUp(Key.UP);
					s.keyDown(Key.UP);
					s.keyUp(Key.UP);
					if (s.exists(Patternise("QueueBundle","Moderate"),2) != null) {
						s.wait(Patternise("QueueBundle","Moderate"),1).click();
						test.pass("Clicked on Queue Bundle button");
					}
					else {
						test.fail("Unable to click on Queue Bundle button");
					}
					//Goto Template Section
					s.wait(Patternise("Queued","Moderate"),2).click();
					//Thread.sleep(1000);
					test.pass("Navigated to Queued section");
					//Check if the Template is saved or not
					if (s.exists(Patternise("TestBundleQueue","Moderate"),3) != null) {
						test.pass("Queued Bundle found in Queued section");
						Thread.sleep(3000);
						//Goto Published Section
						s.wait(Patternise("Published","Moderate"),2).click();
						test.pass("Navigated to Published Section");
						Thread.sleep(6000);
						//Check if the story is published or not
						r=new Region(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX()-350, s.find(Patternise("EconAlertStoryHeadline","Moderate")).getY(), 520, 80);
						//r.highlight();
						//Check for 1st type publish
						if (s.exists(Patternise("PublishedTest"+type1+"Headline","Moderate")) != null) {
							test.pass(Hdln1+ " "+type1+ " published and found in published section");
						}
						else {
							test.fail(Hdln1+ " "+type1+ " not published");
						}
						//Check for 2nd type publish
						if (s.exists(Patternise("PublishedTest"+type2+"Headline","Moderate")) != null) {
							test.pass(Hdln2+ " "+type2+ " published and found in published section");
						}
						else {
							test.fail(Hdln2+ " "+type2+ " not published");
						}
					}
					else {
						test.fail("Queued Bundle not found in Queued section");
					     }
				
				} 
				else {
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
}
