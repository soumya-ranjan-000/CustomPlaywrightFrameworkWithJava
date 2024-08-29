package srg.playright.base;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import srg.CucumberRunner;
import srg.util.ResourceHandler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LambdaTest {
    public static Browser connect(BrowserType browserType) throws UnsupportedEncodingException {
//        String caps = URLEncoder.encode(getCapabilities().toString(), "utf-8");
        String ws_endpoint = "wss://cdp.lambdatest.com/playwright?capabilities=" + getCapabilities();
        return browserType.connect(ws_endpoint);
    }

    private static JsonObject getCapabilities() {
        File file = new File(ResourceHandler.getYmlFilePath("lambdatest.yml"));
        Map<String, Object> config = ResourceHandler.convertYamlFileToMap(file, new HashMap<>());
        String testName = CucumberRunner.testRunner.get().getScenario().getName();

        JsonObject capabilitiesObject = new JsonObject();
        JsonObject ltOptions = new JsonObject();
        ltOptions.addProperty("platform", String.valueOf(config.get("platform")));
        ltOptions.addProperty("name", testName);
        ltOptions.addProperty("build", String.valueOf(config.get("build")));
        ltOptions.addProperty("user", String.valueOf(config.get("LT_USERNAME")));
        ltOptions.addProperty("accessKey", String.valueOf(config.get("LT_ACCESS_KEY")));
        capabilitiesObject.addProperty("browserName", String.valueOf(config.get("browserName")));    // allowed browsers are `chrome`, `edge`, `playwright-chromium`, `playwright-firefox` and `playwright-webkit`
        capabilitiesObject.addProperty("browserVersion", String.valueOf(config.get("browserVersion")));
//        capabilitiesObject.addProperty("geoLocation", String.valueOf(config.get("geoLocation")));
//        capabilitiesObject.addProperty("project", String.valueOf(config.get("projectName")));
//        capabilitiesObject.addProperty("tags", String.valueOf(config.get("tags")));
//        capabilitiesObject.addProperty("resolution", String.valueOf(config.get("resolution")));
//        capabilitiesObject.addProperty("video", String.valueOf(config.get("video")));
//        capabilitiesObject.addProperty("console", String.valueOf(config.get("console")));
//        capabilitiesObject.addProperty("network", String.valueOf(config.get("network")));
        capabilitiesObject.add("LT:Options", ltOptions);
        return capabilitiesObject;
    }
}
