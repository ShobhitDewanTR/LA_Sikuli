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

public class AssetCases extends BasePackage.LABase {
	static Pattern pattern, pattern1, pattern2;
	public AssetCases() {
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
	
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10"})
	@Test
	public static void VerifyAssetSave(String type,String Hdln, String usn,String pdct, String tpc, String ric, String slg, String catcode,String nmditm,String bdy,String Option) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String msg = null;
		String icon;
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
					//check for option
					switch(Option) {
					  case "Relaunch":
						  status=LaunchLA(test,"Relaunch","YES");
						  if(status==1) {
							  test.pass("Relaunched the application successfully");
							  msg=" reopening the application";
						  }
						  else {
							  test.fail("Unable to launch the application");
							  return;
							  }
						  icon="Expand"+type+"Relaunch";
						  break;
					  case "Wait":
						  Thread.sleep(180000);
						  test.pass("Navigated back to workspace screen");
						  msg=" waiting for 3 minutes";
						  icon="Expand"+type;
						  break;
					  case "Navigate":
						  s.wait(Patternise("Published","Moderate"),2).click();
						  test.pass("Navigated to published screen");
						  s.click(Patternise("WorkspaceDefaultunfocussed","Moderate"));
						  test.pass("Navigated back to workspace screen");
						  msg=" navigating back to screen from other Screen";
						  icon="Expand"+type;
						  break;
					  case "NewWorkspace":
						  s.wait(Patternise("NewWorkspace","Moderate"),2).click();
						  test.pass("Navigated to New Asset screen");
						  s.wait(Patternise("New"+type,"Moderate"),2).click();
						  test.pass("Clicked on New"+type+" button on New Asset Screen");
						  s.wait(Patternise("CloseWorkspace","Moderate"),2).click();
						  s.wait(Patternise("WorkspaceDefaultunfocussed","Moderate"),2).click();
						  test.pass("Navigated back to workspace screen");
						  msg=" navigating back to screen from New Asset Screen";
						  icon="Expand"+type;
					  	  break;
					  default:
					    test.fail("Wrong Option inputted.Please correct the Option in Data Sheet and rerun");
					    return;
					}
					
					if (s.exists(Patternise(icon,"Moderate")) != null) {
						test.pass("Asset is saved after "+msg);
					}
					else {
						test.fail("Asset is not saved after "+msg);
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

	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10","param11"})
	@Test
	public static void VerifyAssetUpdate(String type1,String Hdln1, String usn1,String pdct1, String tpc1, String ric1, String slg1, String catcode1,String nmditm1,String bdy1,String Hdln2, String Option) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String msg = null;
		String icon;
		String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
		test.log(com.aventstack.extentreports.Status.INFO,nameofCurrMethod+" Method begin");
		try {
			//System.out.println(s.find(Patternise("EconAlertStoryHeadline","Moderate")).getX());
			status=LaunchLA(test,"Open","YES");
			if(status==1) {
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
					    return;
					}
					//Update the data
					UpdateAssetData(test,Hdln2.toUpperCase(),type1);
					//check for option
					switch(Option) {
					  case "Relaunch":
						  status=LaunchLA(test,"Relaunch","NO");
						  if(status==1) {
							  test.pass("Relaunched the application successfully");
							  msg=" reopening the application";
						  }
						  else {
							  test.fail("Unable to launch the application");
							  return;
							  }
						  icon="Expand"+type1+"Relaunch";
						  break;
					  case "Navigate":
						  s.wait(Patternise("Published","Moderate"),2).click();
						  test.pass("Navigated to published screen");
						  s.click(Patternise("WorkspaceDefaultunfocussed","Moderate"));
						  test.pass("Navigated back to workspace screen");
						  msg=" navigating back to screen from other Screen";
						  icon="Expand"+type1;
						  break;
					  case "CreateNew":
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
								    return;
								}
							}
							else {
								test.fail("New "+type1+" Template not loaded, Cannot Continue to create additional "+type1);
							}
							msg=" creating additional "+type1;
							icon="Expand"+type1;
						  break;
					  case "Wait":
						  Thread.sleep(180000);
							msg=" waiting for 3 minutes";
							icon="Expand"+type1;
						  break;
					  default:
					    test.fail("Wrong Option inputted.Please correct the Option in Data Sheet and rerun");
					    return;
					}
					
					if (s.exists(Patternise("UpdatedHeadline","Moderate")) != null) {
						test.pass("Asset is saved after "+msg);
					}
					else {
						test.fail("Asset is not saved after "+msg);
					}
					
					while(s.exists(Patternise("Delete","Strict")) != null) {
						s.click(Patternise("Delete","Strict"));
					}
					while(s.exists(Patternise("DeleteFocussed","Strict")) != null) {
						s.click(Patternise("DeleteFocussed","Strict"));
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
	@Parameters({"param0","param1","param2","param3"})
	@Test
	public static void VerifyAssetField(String type,String Field, String Value, String Option) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String msg = null;
		String icon;
		String Hdln1,usn1,pdct1,tpc1,ric1,slg1,catcode1,nmditm1,bdy1;
		Hdln1="";
		usn1="";
		pdct1="";
		tpc1="";
		ric1="";
		slg1="";
		catcode1="";
		nmditm1="";
		bdy1="";
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
					switch(Field) {
					  case "Headline":
						  Hdln1=Value;
						  usn1="";
						  pdct1="";
						  tpc1="";
						  ric1="";
						  slg1="";
						  catcode1="";
						  nmditm1="";
						  bdy1="";
					      break;
					  case "Product":
						  Hdln1="";
						  usn1="";
						  pdct1=Value;
						  tpc1="";
						  ric1="";
						  slg1="";
						  catcode1="";
						  nmditm1="";
						  bdy1="";
						  break;
					  case "Address":
						  Hdln1="";
						  usn1=Value;
						  pdct1="";
						  tpc1="";
						  ric1="";
						  slg1="";
						  catcode1="";
						  nmditm1="";
						  bdy1="";
						  break;
					  case "NamedItems":
						  Hdln1="";
						  usn1="";
						  pdct1="";
						  tpc1="";
						  ric1="";
						  slg1="";
						  catcode1="";
						  nmditm1=Value;
						  bdy1="";
						  break;
					  default:
					    test.fail("Wrong  Field Value, please check the data.");
					}
					switch(type) {
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
					switch(Option) {
					  case "Normal":
						  if (s.exists(Patternise(Field+"ErrorMessage","Moderate")) != null) {
							    test.fail("Unable to enter value within character limit, error message is seen");
							}
							else {
								test.pass("Able to enter value within character limit, error message is not seen");
						  }
					      break;
					  case "Boundary":
						  if (s.exists(Patternise(Field+"ErrorMessage","Moderate")) != null) {
							  	test.pass("Unable to enter value exceeding character limit, error message is seen");
							}
							else {
								test.fail("Able to enter value exceeding character limit, error message is not seen");
						  }
					      break;
					   default:
					      test.fail("Wrong Option inputted.Please correct the Option in Data Sheet and rerun");
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
	
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10"})
	@Test
	public static void VerifyAssetCopy(String type,String Hdln, String usn,String pdct, String tpc, String ric, String slg, String catcode,String nmditm,String bdy,String Option) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String msg = null;
		String icon, icon2;
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
					//check for option
					switch(Option) {
					  case "Check":
						  s.wait(Patternise("Expand"+type+"Relaunch","Moderate"),2).click();
						  icon="Expand"+type+"Relaunch";
						  icon2="Expand"+type+"Copy";
						  msg=" copying the existing "+type;
						  break;
					  case "NewWorkspace":
						  s.wait(Patternise("NewWorkspace","Moderate"),2).click();
						  test.pass("Navigated to New Asset screen");
						  s.wait(Patternise("New"+type,"Moderate"),2).click();
						  test.pass("Clicked on New"+type+" button on New Asset Screen");
						  s.wait(Patternise("CloseWorkspace","Moderate"),2).click();
						  s.wait(Patternise("WorkspaceDefaultunfocussed","Moderate"),2).click();
						  test.pass("Navigated back to workspace screen");
						  msg=" navigating back to screen from New Asset Screen";
						  icon="Expand"+type;
						  icon2="Expand"+type+"Copy";
					  	  break;
					  default:
					    test.fail("Wrong Option inputted.Please correct the Option in Data Sheet and rerun");
					    return;
					}
					//Copy and paste the asset
					s.type("c", Key.CTRL+ Key.ALT);
					Thread.sleep(2000);
					test.pass("Successfully Copied the existing asset");
					s.type("v", Key.CTRL+ Key.ALT);
					test.pass("Pasted the copy of existing asset successfully");
					Thread.sleep(3000);
					if (s.exists(Patternise(icon,"Moderate")) != null && s.exists(Patternise(icon2,"Moderate"))!=null) {
						test.pass("New Asset is saved after "+msg);
					}
					else {
						test.fail("New Asset is not saved after "+msg);
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
			while(s.exists(Patternise("Delete","Strict")) != null) {
				s.click(Patternise("Delete","Strict"));
				s.mouseMove(Patternise("NewWorkspace","Moderate").targetOffset(0,-15));
			}
		}
	}
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10","param11","param12","param13","param14","param15","param16","param17","param18","param19"})
	@Test
	public static void VerifyBundleCopy(String type1,String Hdln1, String usn1,String pdct1, String tpc1, String ric1, String slg1, String catcode1,String nmditm1,String bdy1,String type2,String Hdln2, String usn2,String pdct2, String tpc2, String ric2, String slg2, String catcode2,String nmditm2,String bdy2) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String icon,icon2,msg;
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
					//Copy and paste the asset
					s.type("c", Key.CTRL+ Key.ALT);
					Thread.sleep(2000);
					test.pass("Successfully Copied the existing asset");
					s.type("v", Key.CTRL+ Key.ALT);
					test.pass("Pasted the copy of existing asset successfully");
					msg=" Copying the Bundle";
					icon="ExpandBundle";
					icon2="ExpandBundleCopy";
					//Check if two bundles are copied
					if (s.exists(Patternise(icon,"Moderate")) != null && s.exists(Patternise(icon2,"Moderate"))!=null) {
						test.pass("New Asset is saved after "+msg);
					}
					else {
						test.fail("New Asset is not saved after "+msg);
					}
					s.wait(Patternise("ExpandBundle","Moderate"),1).click();
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
			while(s.exists(Patternise("Delete","Strict")) != null) {
				s.click(Patternise("Delete","Strict"));
				s.mouseMove(Patternise("NewWorkspace","Moderate").targetOffset(0,-15));
			}
		
		}
	}
	
	@Parameters({"param0","param1","param2","param3","param4","param5","param6","param7","param8","param9","param10"})
	@Test
	public static void VerifyEconScenarios(String type,String Hdln, String usn,String pdct, String tpc, String ric, String slg, String catcode,String nmditm,String bdy,String Option) throws FindFailed, InterruptedException {
		test = extent.createTest(MainRunner.TestID,MainRunner.TestDescription);
		int status;
		String msg = null;
		String icon;
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
					//check for option
					switch(Option) {
					  case "PublishDisabled":
						  if (s.exists(Patternise("PublishDisabled","Moderate")) != null || s.exists(Patternise("PublishDisabled2","Moderate")) != null) {
							  test.pass("Publish button is disabled");
						 }
						  else {
							  test.fail("Publish button is not disabled");
							  }
						 break;
					  case "PublishEnabled":
						  if (s.exists(Patternise("Publish","Moderate")) != null) {
							  test.pass("Publish button is enabled");
						 }
						  else {
							  test.fail("Publish button is not enabled");
							  }
						 break;
					  case "Description":
						  if (s.exists(Patternise(Hdln.strip(),"Moderate")) != null) {
							  test.pass("Description is Auto Populated");
						 }
						  else {
							  test.fail("Description is not Auto Populated");
							  }
						 break;
					  case "HandbrakeR":
						  s.type(Key.F8, KeyModifier.SHIFT); 
						  Thread.sleep(3000);
						  if (s.exists(Patternise("PublishHandbrake","Moderate")) != null) {
							  test.pass("Publish button is enabled");
						 }
						  else {
							  test.fail("Publish button is not enabled");
							  }
						 break;
					  case "HandbrakeAR":
						  s.type(Key.F8, KeyModifier.SHIFT); 
						  Thread.sleep(3000);
						  s.keyDown(Key.TAB+ KeyModifier.SHIFT);
						  s.keyUp(Key.TAB+ KeyModifier.SHIFT);
						  s.keyDown(Key.TAB+ KeyModifier.SHIFT);
						  s.keyUp(Key.TAB+ KeyModifier.SHIFT);
						  Thread.sleep(2000);
						  s.type(Key.F8, KeyModifier.SHIFT); 
						  Thread.sleep(3000);
						  if (s.exists(Patternise("PublishHandbrake","Moderate")) != null) {
							  test.pass("Publish button is enabled");
						 }
						  else {
							  test.fail("Publish button is not enabled");
							  }
						 break;
					  default:
					    test.fail("Wrong Option inputted.Please correct the Option in Data Sheet and rerun");
					    return;
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
			while(s.exists(Patternise("Delete","Strict")) != null) {
				s.click(Patternise("Delete","Strict"));
				s.mouseMove(Patternise("NewWorkspace","Moderate").targetOffset(0,-15));
			}
		
		}
	}
}
