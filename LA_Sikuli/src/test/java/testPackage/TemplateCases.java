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
					Thread.sleep(3000);
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
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10","param11","param12","param13","param14","param15","param16","param17","param18","param19"})
	@Test
	public static void VerifyBundleTemplate(String type1,String Hdln1, String usn1,String pdct1, String tpc1, String ric1, String slg1, String catcode1,String nmditm1,String bdy1,String type2,String Hdln2, String usn2,String pdct2, String tpc2, String ric2, String slg2, String catcode2,String nmditm2,String bdy2) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
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
					s.wait(Patternise("ExpandBundle","Moderate"),1).click();
					s.type("s", Key.CTRL);
					Thread.sleep(3000);
					s.type("New"+type1+type2+"TemplateDescription");
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.type("New"+type1+type2+"TemplateTag");
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					s.keyDown(Key.TAB);
					s.keyUp(Key.TAB);
					Thread.sleep(3000);
					s.keyDown(Key.ENTER);
					s.keyUp(Key.ENTER);
					if (s.exists(Patternise("PopupYes","Moderate")) != null) {
						s.wait(Patternise("PopupYes","Moderate"),1).click();
					}
					test.pass("Saved "+type1+type2+" data");
					Thread.sleep(2000);
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
					s.type("New"+type1+type2+"TemplateDescription");
					
					if (s.exists(Patternise("New"+type1+type2+"TemplateDescription","Moderate"),3) != null) {
						test.pass(type1+type2+ " template found in template section");
						s.wait(Patternise("New"+type1+type2+"TemplateDescription","Moderate"),1).doubleClick();
						Thread.sleep(3000);
						s.wait(Patternise("ExpandBundle","Moderate"),2).click();
						Thread.sleep(2000);
						if (s.exists(Patternise("TemplateTest"+type1+"Headline1","Moderate")) != null && s.exists(Patternise("TemplateTest"+type2+"Headline2","Moderate")) != null) {
							test.pass("Value of asset is saved when template is opened");
						}
						else
						{ 
							test.fail("Value of asset is not saved when template is opened");
							
						}
						s.wait(Patternise("Templates","Moderate"),2).click();
						Thread.sleep(3000);
						test.info("Navigating to Templates section");
//						if (s.exists(Patternise("FindTemplate","Moderate")) == null) {
//							s.wait(Patternise("SearchTemplates","Moderate"),1).click();
//							test.pass("Clicked Search Template to Search created Template");
//						}
//						s.type("a", Key.CTRL);
//						s.keyDown(Key.DELETE);
//						s.keyUp(Key.DELETE);
//						s.type("New"+type1+type2+"TemplateDescription");
						s.wait(Patternise("Delete","Moderate"),2).click();
						test.pass("Deleted newly created "+type1+type2+" template");
					}
					else {
						test.fail("Bundle template not found");
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
	
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10"})
	@Test
	public static void VerifyTemplateShortcutScenarios(String type,String Hdln, String usn,String pdct, String tpc, String ric, String slg, String catcode,String nmditm,String bdy,String Option) throws FindFailed, InterruptedException {
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
					switch(Option) {
							case "Existing":
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
								Thread.sleep(3000);
								s.wait(Patternise("OKTemplate","Moderate"),1).click();
								if (s.exists(Patternise("PopupYes","Moderate")) != null) {
									s.wait(Patternise("PopupYes","Moderate"),1).click();
								}
								test.pass("Saved "+type+" data template using existing Shortcut");
								//Goto Template Section
								s.wait(Patternise("Templates","Moderate"),2).click();
								Thread.sleep(3000);
								test.pass("Navigated to template section");
								//Check if the Template is saved or not
								if (s.exists(Patternise("FindTemplate","Moderate")) == null) {
									s.wait(Patternise("SearchTemplates","Moderate"),1).click();
									test.pass("Clicked Search Template to Search created Template using existing Shortcut");
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
								break;
							case "Invalid":
								s.type("f", Key.CTRL);
								Thread.sleep(3000);
								s.wait(Patternise("Templates","Moderate"),2).click();
								Thread.sleep(3000);
								test.pass("Navigated to template section");
								//Check if the Template is saved or not
								if (s.exists(Patternise("FindTemplate","Moderate")) == null) {
									s.wait(Patternise("SearchTemplates","Moderate"),1).click();
									test.pass("Clicked Search Template to Search created Template using existing Shortcut");
								}
								s.type("a", Key.CTRL);
								s.keyDown(Key.DELETE);
								s.keyUp(Key.DELETE);
								s.type("New"+type+"TemplateDescription");
								if (s.exists(Patternise("New"+type+"TemplateDescription","Moderate")) != null) {
									test.fail(type+ " template found in template section saved with invalid shortcut");
									s.wait(Patternise("Delete","Moderate"),2).click();
									test.pass("Deleted newly created "+type+" template");
								}
								else {
									test.pass(type+ " template not saved using invalid shortcut");
								}
								break;
							 default:
								    test.fail("Wrong Option inputted.Please correct the Option value in Data Sheet and rerun");
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
