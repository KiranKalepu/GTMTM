package GTMTM.TestSuite.testClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import GTMTM.TestSuite.base.GTMTMBasePage;
import GTMTM.TestSuite.pageObjects.DispatchPortal;

public class DispatchPortalTest extends GTMTMBasePage {
	DispatchPortal dispatch;

	@BeforeMethod
	public void setupDispatch() {
		dispatch = new DispatchPortal(getDriver());
	}

	@Test
	public void dispatchLoginTest() throws Exception {
		new MicrosoftLoginTest().microsoftDispatchLoginTest();
		Assert.assertEquals(getDriver().getTitle(), "Access2Care");
		getLogger().info("Login completed successfully");
	}

	@Test(dependsOnMethods = { "dispatchLoginTest" })
	public void dispatchNavigationTest() {
		getLogger().info("Started Navigation test");
		String[] expectedTabs = { "DISPATCH", "LYFT", "OLOS" };
		List<String> expectedTabsList = Arrays.asList(expectedTabs);
		List<String> actualTabList = new ArrayList<>();
		for (WebElement tab : dispatch.getTabsList()) {
			wait.until(ExpectedConditions.visibilityOf(tab));
			actualTabList.add(tab.getText());
			getLogger().info(tab.getText());
			tab.click();
			if (tab.getText().equalsIgnoreCase("LYFT") || tab.getText().equalsIgnoreCase("OLOS")) {
				dispatch.getCloseIcon().click();
				wait.until(ExpectedConditions.visibilityOf(dispatch.getPageTitle()));
				Assert.assertEquals(dispatch.getPageTitle().getText(), "Lyft trips");
			} else if (tab.getText().equalsIgnoreCase("DISPATCH")) {
				wait.until(ExpectedConditions.visibilityOf(dispatch.getPageTitle()));
				Assert.assertEquals(dispatch.getPageTitle().getText(), "Dispatch");
			}
			getLogger().info("Successfully navigated to " + tab.getText());
		}
		Assert.assertEquals(actualTabList, expectedTabsList);
		getLogger().info("Successfully navigated to all tabs");
	}
}