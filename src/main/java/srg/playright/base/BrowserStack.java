package srg.playright.base;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.CucumberRunner;
import srg.util.ResourceHandler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BrowserStack {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserStack.class);

    public static Browser connect(BrowserType browserType) throws UnsupportedEncodingException {
        String caps = URLEncoder.encode(getCapabilities().toString(), StandardCharsets.UTF_8);
        String ws_endpoint = "wss://cdp.browserstack.com/playwright?caps=" + caps;
        return browserType.connect(ws_endpoint);
    }

    private static JsonObject getCapabilities() {
        File file = new File(ResourceHandler.getYmlFilePath("browserstack.yml"));
        Map<String, Object> config = ResourceHandler.convertYamlFileToMap(file, new HashMap<>());
        JsonObject capabilitiesObject = new JsonObject();

        for (LinkedHashMap<String, Object> platform : (ArrayList<LinkedHashMap<String, Object>>) config.get("platforms")) {
            capabilitiesObject.addProperty("os", String.valueOf(platform.get("os")));
            capabilitiesObject.addProperty("os_version", String.valueOf(platform.get("osVersion")));
            capabilitiesObject.addProperty("browser", String.valueOf(platform.get("browserName")));    // allowed browsers are `chrome`, `edge`, `playwright-chromium`, `playwright-firefox` and `playwright-webkit`
            capabilitiesObject.addProperty("browser_version", String.valueOf(platform.get("browserVersion")));
            LOGGER.info("OS: {},OS_version: {}, Browser: {}, Browser_Version: {}",
                    platform.get("os"), platform.get("osVersion"), platform.get("browserName"), platform.get("browserVersion"));
        }
        String userName = config.containsKey("userName") ? String.valueOf(config.get("userName")) : System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = config.containsKey("accessKey") ? String.valueOf(config.get("accessKey")) : System.getenv("BROWSERSTACK_PASSWORD");
        capabilitiesObject.addProperty("browserstack.username", userName);
        capabilitiesObject.addProperty("browserstack.accessKey", accessKey);
        capabilitiesObject.addProperty("browserstack.geoLocation", String.valueOf(config.get("geoLocation")));
        capabilitiesObject.addProperty("project", String.valueOf(config.get("projectName")));
        capabilitiesObject.addProperty("build", String.valueOf(config.get("buildName")));
        String testName = CucumberRunner.testRunner.get().getScenario().getName();
        capabilitiesObject.addProperty("name", testName);
        capabilitiesObject.addProperty("buildTag", String.valueOf(config.get("buildTag")));
        capabilitiesObject.addProperty("resolution", "1280x1024");
        capabilitiesObject.addProperty("browserstack.local", "true");
        capabilitiesObject.addProperty("browserstack.localIdentifier", "local_connection_name");
        capabilitiesObject.addProperty("browserstack.playwrightVersion", "1.latest");
        capabilitiesObject.addProperty("client.playwrightVersion", "1.latest");
        capabilitiesObject.addProperty("browserstack.debug", String.valueOf(config.get("debug")));
        capabilitiesObject.addProperty("browserstack.console", String.valueOf(config.get("consoleLogs")));
        capabilitiesObject.addProperty("browserstack.networkLogs", String.valueOf(config.get("networkLogs")));
        return capabilitiesObject;
    }
}
