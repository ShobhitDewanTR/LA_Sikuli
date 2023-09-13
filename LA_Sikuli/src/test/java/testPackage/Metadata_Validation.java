package testPackage;

import java.io.IOException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.util.SystemOutLogger;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Metadata_Validation extends BasePackage.LYNXBase {
	public Metadata_Validation() {
		super();
	}
	 @BeforeClass
	 public void setup() throws InterruptedException, IOException {
	        extent = BasePackage.LYNXBase.getInstance();
	 }
	@AfterTest
	public void flushReportData() {
		extent.flush();
	}
	
	@Test
	public void ValidateStoryPublish() throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		test.log(com.aventstack.extentreports.Status.INFO,"ValidateStoryPublish Method called");
		String AEUSN, PHUSN,PHALRTTXT,tempalerttext,alerttext;
		Region r;
		Pattern pattern;
		lynxapp.focus();
		//System.out.println(s.selectRegion().text());
		try {  
			    RelaunchReopenFWTab(test,"Reopen");
				pattern = new Pattern(GetProperty("Market"));//.similar(0.7f);
				s.click(pattern);
				test.pass("ClickedMarket Dropdown");
				//Region region = s.exists(pattern);
				//region.click(pattern);
				//s.find(GetProperty("LYNXEDITORLOGO")).click();
				//s.find(GetProperty("Market")).click();
				Thread.sleep(3000);
				pattern = new Pattern(GetProperty("Australia"));//.similar(0.7f);
				test.pass("Checked Australia checkbox");
				s.click(pattern);
				pattern = new Pattern(GetProperty("Apply")).similar(0.7f);//.exact();
				test.pass("Clicked Apply Button");
				s.click(pattern);
				//s.find(GetProperty("Australia")).click();
				//s.find(GetProperty("Apply")).click();
				Thread.sleep(8000);
				pattern = new Pattern(GetProperty("GetUSN"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Clicked Get USN button");
				//s.find(GetProperty("GetUSN")).click();
				Thread.sleep(5000);
				r=new Region(s.find(GetProperty("chars")).getX()-80, s.find(GetProperty("chars")).getY()+32, 1, 1);
				r.click();
				Thread.sleep(3000);
				s.type("a", KeyModifier.CTRL);
//				s.keyDown(Key.CTRL);
//				Thread.sleep(5000);
//				s.type("A");
				Thread.sleep(3000);
				s.keyDown(Key.BACKSPACE);
//				Thread.sleep(3000);
//				s.keyUp(Key.CTRL);
				s.keyUp(Key.BACKSPACE);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				tempalerttext=timestamp+"";
				alerttext=tempalerttext.replace(":","");
				r.type(alerttext);
				test.pass("Entered custom Alert text");
				pattern = new Pattern(GetProperty("Publish"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Clicked Publish Button");
				Thread.sleep(8000);
				//s.find(GetProperty("Publish")).click();
				//s.find(GetProperty("Publish")).click();
				r=new Region(1125, 451, 106, 40);
				AEUSN=r.text();
				//r=new Region(14, 461, 656, 89);
				r=new Region(14, 461, 133, 45);
				PHUSN=r.text();
				r=new Region(490, 480, 170, 28);
				PHALRTTXT=r.text();
				//AEUSN=s.setRect(1125, 451, 106, 40).text();
				//PHUSN=s.setRect(14, 461, 133, 45).text();
				System.out.println("AE:"+AEUSN);
				System.out.println("PH:"+PHUSN);
				System.out.println("PHALRTTXT: "+PHALRTTXT);
				
//				if(s.exists(GetProperty("SuccessPublish"))!=null){ 
//					test.pass("Alert successfully published, successfuly published message seen after publish");
//				}
//				else {
//					test.fail("Alert not successfully published, No successfuly published message seen after publish");
//				}
				if(s.exists(GetProperty("BlankAEMetadata"))==null) {
						test.pass("Alert successfully published, No Blank publish history seen after publish");
				}
				else {
					test.fail("Alert not successfully published, Blank publish history seen after publish");
				}
				if(PHUSN.contains(AEUSN)){
					test.pass("Alert successfully published, USN in Alert Editor matches with USN in Publish history");
				}
				else {
					test.fail("Alert not successfully published,USN in Alert Editor("+AEUSN +") doesnot matches with USN in Publish history"+PHUSN);
					
				}
				if(PHALRTTXT.contains(alerttext)){
					test.pass("Alert successfully published, Alert text in Alert Editor matches with Alert text in Publish history");
				}
				else {
					test.fail("Alert not successfully published,Alert text in Alert Editor("+alerttext+") doesnot matches with Alert text in Publish history("+PHALRTTXT+")"); 
				}
				//s.find(GetProperty("Market")).click();
				pattern = new Pattern(GetProperty("MarketsChkd"));//.similar(0.9);
				test.pass("Clicked Markets dropdown");
				//System.out.println("Score is :"+s.find(pattern).getScore());
				s.click(pattern);
				Thread.sleep(1000);
				//s.find(GetProperty("AustraliaChkd")).click();
				//s.find(GetProperty("Apply")).click();
				pattern = new Pattern(GetProperty("AustraliaChkd"));//.similar(0.7f);
				test.pass("Unchecked Australia checkbox");
				s.click(pattern);
				Thread.sleep(2000);
				pattern = new Pattern(GetProperty("Apply")).similar(0.7f);
				test.pass("Clicked Apply Button");
				s.click(pattern);
				Thread.sleep(3000);
		}
		catch(Exception e) {
			test.fail("Error occured:"+e.getMessage());
			System.out.println("Error occured:"+e.getMessage());
		}
//		if(s.wait(getElement("BSESourceList"),10)!=null ) {
//			System.out.println("Matching Source value list found");
//		}
//		else {
//			System.out.println("Matching Source value list not found");
//		}

	}

	@Test
	public void ValidateBriefPublish() throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		test.log(com.aventstack.extentreports.Status.INFO,"ValidateBriefPublish Method called");
		String AEUSN,alerttext,tempalerttext,Baskettext,metadatacntnt;
		int hdlnfnd,xval,yval;
		hdlnfnd=0;
		Pattern pattern;
		Region r;
		lynxapp.focus();
		//System.out.println(s.selectRegion().text());
		try {  
				RelaunchReopenFWTab(test,"Reopen");
				pattern = new Pattern(GetProperty("Market"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Clicked Market dropdown");
				//Region region = s.exists(pattern);
				//region.click(pattern);
				//s.find(GetProperty("LYNXEDITORLOGO")).click();
				//s.find(GetProperty("Market")).click();
				Thread.sleep(3000);
				pattern = new Pattern(GetProperty("Australia"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Checked Autralia checkbox");
				pattern = new Pattern(GetProperty("Apply")).similar(0.7f);//.exact();
				s.click(pattern);
				test.pass("Clicked Apply Button");
				//s.find(GetProperty("Australia")).click();
				//s.find(GetProperty("Apply")).click();
				Thread.sleep(8000);
				pattern = new Pattern(GetProperty("GetUSN"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Clicked Get USN Button");
				Thread.sleep(4000);
				r=new Region(1125, 451, 106, 40);
				AEUSN=r.text();
				r=new Region(706, 460, s.find(GetProperty("chars")).getX()-876, s.find(GetProperty("chars")).getY());
				metadatacntnt=r.text();
				System.out.println("Metadata is :"+metadatacntnt);
				Thread.sleep(5000);
				//s.click(GetProperty("chars"));
				//Thread.sleep(4000);
				r=new Region(s.find(GetProperty("chars")).getX()-80, s.find(GetProperty("chars")).getY()+32, 1, 1);
				r.click();
				Thread.sleep(3000);
				s.type("a", KeyModifier.CTRL);
//				s.keyDown(Key.CTRL);
//				Thread.sleep(5000);
//				s.type("A");
				Thread.sleep(3000);
				s.keyDown(Key.BACKSPACE);
//				Thread.sleep(3000);
//				s.keyUp(Key.CTRL);
				s.keyUp(Key.BACKSPACE);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				tempalerttext="ALERT "+timestamp;
				alerttext=tempalerttext.replace(":","");
				r.type(alerttext);
				test.pass("Entered custom Alert text");
				pattern = new Pattern(GetProperty("Publish"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Clicked Publish Button");
//				System.out.println("X position is:"+s.find(GetProperty("Publish")).getX());
//				System.out.println("Y position is:"+s.find(GetProperty("Publish")).getY());
				Thread.sleep(8000);
//				s.click(pattern);
//				System.out.println("2nd X position is:"+s.find(GetProperty("Publish")).getX());
//				System.out.println("2nd Y position is:"+s.find(GetProperty("Publish")).getY());
//				
				pattern = new Pattern(GetProperty("Brief"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Clicked on Brief menu");
				//s.find(GetProperty("GetUSN")).click();
				Thread.sleep(1000);
				pattern = new Pattern(GetProperty("PublishBrief"));//.similar(0.7f);
				s.click(pattern); 
				test.pass("Clicked on Publish Brief Option on Brief menu");
				Thread.sleep(5000);
				pattern = new Pattern(GetProperty("Home1"));//.similar(0.7f);
				if (s.exists(pattern) != null) {
					s.click(pattern);
					test.pass("Clicked on Home tab");
					Thread.sleep(5000);
				}
				else {
					pattern = new Pattern(GetProperty("Home2"));//.similar(0.7f);
					if (s.exists(pattern) != null) {
						s.click(pattern);
						test.pass("Clicked on Home tab");
						Thread.sleep(5000);
					}
					else {
						test.fail("Home tab doesn't exists");
					}
				}
				//pattern = new Pattern(GetProperty("HomeSearch"));//.similar(0.7f);
				//s.click(GetProperty("HomeSearch"));
				if (s.exists(GetProperty("HomeBack")) != null) {
					s.click(GetProperty("HomeBack"));
					Thread.sleep(4000);
				}
				s.type(GetProperty("HomeSearch"),"EMEA-Companies-Alerting-Tools");
				test.pass("Entered Basket Name (EMEA-Companies-Alerting-Tools) in quick search");
				Thread.sleep(8000);
				s.keyDown(Key.ENTER);
				s.keyUp(Key.ENTER);	
				Thread.sleep(8000);
				System.out.println(alerttext.substring(0,21));
				if (s.existsText(alerttext.substring(0,21),5) != null) {
					s.click(alerttext.substring(0,21));
					Thread.sleep(5000);
					test.pass("Published Brief found in EMEA-Companies-Alerting-Tools basket");
					hdlnfnd=1;
					r=new Region(847, 180, 513, 320);
					Baskettext=r.text();
					if (Baskettext.contains(alerttext)) {
						test.pass("Published Brief contains entered alert text from alert editor");
					}
					else {
						test.fail("Published Brief does not contain entered alert text from alert editor. Found: "+Baskettext+ " Expected: "+alerttext);
					}
					if (Baskettext.contains(alerttext.substring(5,16))) {
						test.pass("Published Brief contains correct edited date");
					}
					else {
						test.fail("Published Brief does not contain correct edited date. Found: "+Baskettext +" Expected: "+alerttext.substring(5,16));
					}
					if (Baskettext.contains(AEUSN)) {
						test.pass("Published Brief contains USN from alert editor");
					}
					else {
						test.fail("Published Brief does not contain USN from alert editor.Found: "+Baskettext +" Expected: "+AEUSN);
					}
					if (Baskettext.contains(AEUSN)) {
						test.pass("Published Brief contains Product and RIC from alert editor");
					}
					else {
						test.fail("Published Brief does not contain Product and RIC from alert editor");
					}
					if (Baskettext.contains("Basket: EMEA-Companies-Alerting-Tools")) {
						test.pass("Published Brief contains correct Basket");
					}
					else {
						test.fail("Published Brief does not contain correct Basket");
					}
					Thread.sleep(4000);
				}
				else {
					s.click(GetProperty("HomeBack"));
					Thread.sleep(4000);
					s.type(GetProperty("HomeSearch"),"AMERS-Companies-Alerting-Tools");
					test.pass("Entered Basket Name (AMERS-Companies-Alerting-Tools) in quick search");
					Thread.sleep(8000);
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					Thread.sleep(8000);
					System.out.println(alerttext.substring(0,21));
					if (s.existsText(alerttext.substring(0,21),5) != null) {
						s.click(alerttext.substring(0,21));
						Thread.sleep(5000);
						test.pass("Published Brief found in AMERS-Companies-Alerting-Tools basket");
						hdlnfnd=1;
						r=new Region(847, 180, 513, 320);
						Baskettext=r.text();
						if (Baskettext.contains(alerttext)) {
							test.pass("Published Brief contains entered alert text from alert editor");
						}
						else {
							test.fail("Published Brief does not contain entered alert text from alert editor. Found: "+Baskettext+ "Expected: "+alerttext);
						}
						if (Baskettext.contains("Edited: "+alerttext.substring(0,11))) {
							test.pass("Published Brief contains correct edited date");
						}
						else {
							test.fail("Published Brief does not contain correct edited date. Found: "+Baskettext +"Expected: "+alerttext.substring(1,11));
						}
						if (Baskettext.contains(AEUSN)) {
							test.pass("Published Brief contains USN from alert editor");
						}
						else {
							test.fail("Published Brief does not contain USN from alert editor.Found: "+Baskettext +"Expected: "+AEUSN);
						}
						if (Baskettext.contains("Basket: AMERS-Companies-Alerting-Tools")) {
							test.pass("Published Brief contains correct Basket");
						}
						else {
							test.fail("Published Brief does not contain correct Basket");
						}
						Thread.sleep(4000);
					}
				}
				if (hdlnfnd==0) {
					test.pass("Published Brief not found in the baskets");
				}
				s.find(GetProperty("Fastwiretab")).click();
				Thread.sleep(3000);
				pattern = new Pattern(GetProperty("Market"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Clicked market Dropdown");
				//Region region = s.exists(pattern);
				//region.click(pattern);
				//s.find(GetProperty("LYNXEDITORLOGO")).click();
				//s.find(GetProperty("Market")).click();
				Thread.sleep(3000);
				pattern = new Pattern(GetProperty("AustraliaChkd"));//.similar(0.7f);
				s.click(pattern);
				test.pass("Unchecked Australia checkbox");
				pattern = new Pattern(GetProperty("Apply")).similar(0.7f);//.exact();
				s.click(pattern);
				test.pass("Clicked Apply button");
				s.find(GetProperty("FWTabClose")).click();
				test.pass("Closed Fastwire Tab");
		}
		catch(Exception e) {
			test.error("Error occured:"+e.getMessage());
			System.out.println("Error occured:"+e.getMessage());
		}
//		if(s.wait(getElement("BSESourceList"),10)!=null ) {
//			System.out.println("Matching Source value list found");
//		}
//		else {
//			System.out.println("Matching Source value list not found");
//		}

	}
	@Test
	public void ValidateStoryListColumns() {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		test.log(com.aventstack.extentreports.Status.INFO,"ValidateStoryListColumns Method called");
		lynxapp.focus();
		try {  
				RelaunchReopenFWTab(test,"Reopen");
				if (s.exists(GetProperty("Date")) != null) {
					test.pass("Date Column found in Story List");
				}
				else {
					test.fail("Date Column not found in Story List");
				}
				if (s.exists(GetProperty("Source")) != null) {
					test.pass("Source Column found in Story List");
				}
				else {
					test.fail("Source Column not found in Story List");
				}
				if (s.exists(GetProperty("ID")) != null) {
					test.pass("StoryID Column found in Story List");
				}
				else {
					test.fail("StoryID Column not found in Story List");
				}
				if (s.exists(GetProperty("Headline")) != null) {
					test.pass("Headline Column found in Story List");
				}
				else {
					test.fail("Headline Column not found in Story List");
				}
				ClearMetaData();
		}
		catch(Exception e)
		{
			test.error("Error occured :"+ e.getMessage());
		}
	}
	
		
}


