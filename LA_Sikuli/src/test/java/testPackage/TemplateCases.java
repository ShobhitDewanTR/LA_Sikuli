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

public class TemplateCases extends BasePackage.LABase {
	static Pattern pattern, pattern1, pattern2;
	public TemplateCases() {
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
	public static void VerifyTemplateScenarios(String type,String Hdln, String usn,String pdct, String tpc, String ric, String slg, String catcode,String nmditm,String bdy) throws FindFailed, InterruptedException {
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
					//Saved the data
					s.type("s", Key.CTRL);
					Thread.sleep(3000);
					s.type("New"+type+"TemplateDescription");
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.type("New"+type+"TemplateTag");
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.keyDown(Key.ENTER);
					if (s.exists(Patternise("PopupYes","Moderate")) != null) {
						s.wait(Patternise("PopupYes","Moderate"),1).click();
					}
					test.pass("Saved "+type+" data");
					//Goto Template Section
					s.wait(Patternise("Templates","Moderate"),2).click();
					Thread.sleep(3000);
					test.pass("Navigated to template section");
					//Check if the Template is saved or not
					if (s.exists(Patternise("FindTemplate","Moderate")) == null) {
						s.wait(Patternise("SearchTemplates","Moderate"),1).click();
						test.pass("Clicked Search Template to Search created Template");
					}
					s.type("a", Key.CTRL);
					s.keyDown(Key.DELETE);
					s.keyUp(Key.DELETE);
					s.type("New"+type+"TemplateDescription");
					if (s.exists(Patternise("New"+type+"TemplateDescription","Moderate")) != null) {
						test.pass(type+ " template found in template section");
						s.wait(Patternise("Delete","Moderate"),2).click();
						test.pass("Deleted newly created "+type+" template");
					}
					else {
						test.fail(type+ " template not found");
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
