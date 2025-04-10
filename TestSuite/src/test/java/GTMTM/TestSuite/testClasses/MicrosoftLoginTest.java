package GTMTM.TestSuite.testClasses;

import org.testng.annotations.Test;

import GTMTM.TestSuite.base.GTMTMBasePage;

public class MicrosoftLoginTest extends GTMTMBasePage{

    @Test
    public void microsoftDispatchLoginTest() throws Exception
    {
        getLogger().info("Opening Browser");
        launchUrlWithCookies("https://dispatch-test.mtm-inc.net", "dispatchCookies.json");
        getLogger().info("Launched the Dispatch web application");
    }

    @Test
    public void microsoftSpicePortalLoginTest() throws Exception
    {
        getLogger().info("Opening Browser");
        launchUrlWithCookies("https://spicePortal-test.mtm-inc.net", "spicePortalCookies.json");
        getLogger().info("Launched the Spice Portal web application");
    }
}