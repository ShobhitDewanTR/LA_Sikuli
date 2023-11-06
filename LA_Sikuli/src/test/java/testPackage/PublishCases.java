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
			status=LaunchLA(test,"Open");
			if(status==1) {
				s.wait(Patternise("New"+type,"Moderate"),2).click();
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
	
}
