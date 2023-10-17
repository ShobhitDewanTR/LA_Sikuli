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

public class StoryCases extends BasePackage.LABase {
	static Pattern pattern, pattern1, pattern2;
	public StoryCases() {
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
	//@Parameters({"param0"})
	@Test
	public static void VerifyMultipleStoryPublish() throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			LaunchLA(test,"Open");
			CloseLA(test);
		}
		catch(Exception e) {
			test.fail("Error Occured: "+e.getLocalizedMessage());
		}
		finally {
			test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" method end");
		}
	}
	
}
