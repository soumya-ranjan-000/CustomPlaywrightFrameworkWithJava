package srg.extentreports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.CucumberRunner;
import srg.playwright.custom.LocateElementFromPage;

import java.util.Base64;

public class LogToConsoleAndHTMLReport {

    final Logger LOGGER = LoggerFactory.getLogger(LocateElementFromPage.class);
    private ExtentTest htmlLogger;

    public LogToConsoleAndHTMLReport() {
        htmlLogger = CucumberRunner.testRunner.get().getExtentLogger();
    }
    public LogToConsoleAndHTMLReport(ExtentTest extentTest) {
        htmlLogger = extentTest;
    }

    // info
    public void info(String message) {
        LOGGER.info(message);
        htmlLogger.log(Status.INFO, message);
    }

    public void info(String message, String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.info("%s | %s".formatted(message, codeBlock));
        htmlLogger.log(Status.INFO, message);
        htmlLogger.log(Status.INFO, m);
    }

    public void info(String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.info("Code Block: {}", codeBlock);
        htmlLogger.log(Status.INFO, m);
    }

    public void info(String stepName, String message) {
        LOGGER.info("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.INFO, message);
    }

    public void info(String stepName, String message, String codeblock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeblock, codeLanguage);
        LOGGER.info("%s | %s".formatted(stepName, message));
        LOGGER.info("Code Block: {}", codeblock);
        htmlLogger.createNode(stepName).log(Status.INFO, message).log(Status.INFO, m);
    }

    public void infoWithScreenshot(String stepName, String message, String screenshotFilePath) {
        LOGGER.info("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.INFO, message).log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
    }

    public void infoWithScreenshot(String stepName, String message, String screenshotFilePath, String title) {
        LOGGER.info("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.INFO, message).log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath, title).build());
    }

    public void infoWithScreenshot(String stepName, String message, byte[] bytes) {
        String result = Base64.getEncoder().encodeToString(bytes);
        LOGGER.info("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.INFO, message).log(Status.INFO,
                MediaEntityBuilder.createVideoFromBase64String(result).build());
    }

    public void infoWithScreenshot(String stepName, String message, byte[] bytes, String title) {
        String result = Base64.getEncoder().encodeToString(bytes);
        LOGGER.info("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.INFO, message).log(Status.INFO,
                MediaEntityBuilder.createVideoFromBase64String(result, title).build());
    }

    public void infoWithVideo(String stepName, String message, String videoFilePath) {
        LOGGER.info("%s | %s".formatted(stepName, message));
        logVideoUsingFile(Status.INFO, "%s | %s".formatted(stepName, message), videoFilePath);
    }

    public void infoWithVideo(String stepName, String message, byte[] bytes) {
        LOGGER.info("%s | %s".formatted(stepName, message));
        String result = Base64.getEncoder().encodeToString(bytes);
        logVideoUsingBase64String(Status.INFO, "%s | %s".formatted(stepName, message), result);
    }

    // warn
    public void warn(String message) {
        LOGGER.warn(message);
        htmlLogger.log(Status.WARNING, message);
    }

    public void warn(String stepName, String message) {
        LOGGER.warn("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.WARNING, message);
    }

    public void warn(String message, String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        warn("%s | %s".formatted(message, codeBlock));
        htmlLogger.log(Status.WARNING, m);
    }

    public void warn(String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.warn("{}", codeBlock);
        htmlLogger.log(Status.WARNING, m);
    }

    public void warnWithScreenshot(String stepName, String message, String screenshotFilePath) {
        LOGGER.warn("%s | %s".formatted(stepName, message));
        logScreenshotUsingFile(Status.WARNING, "%s | %s".formatted(stepName, message), screenshotFilePath);
    }

    public void warnWithScreenshot(String stepName, String message, byte[] bytes) {
        LOGGER.warn("%s | %s".formatted(stepName, message));
        String result = Base64.getEncoder().encodeToString(bytes);
        logScreenshotUsingBase64String(Status.WARNING, "%s | %s".formatted(stepName, message), result);
    }

    public void warnWithVideo(String stepName, String message, String videoFilePath) {
        LOGGER.warn("%s | %s".formatted(stepName, message));
        logVideoUsingFile(Status.WARNING, "%s | %s".formatted(stepName, message), videoFilePath);
    }

    public void warnWithVideo(String stepName, String message, byte[] bytes) {
        LOGGER.warn("%s | %s".formatted(stepName, message));
        String result = Base64.getEncoder().encodeToString(bytes);
        logVideoUsingBase64String(Status.WARNING, "%s | %s".formatted(stepName, message), result);
    }

    // pass
    public void pass(String message) {
        LOGGER.info(message);
        htmlLogger.log(Status.PASS, message);
    }

    public void pass(ExtentTest node, String message) {
        LOGGER.info(message);
        node.log(Status.PASS, message);
    }

    public void pass(String stepName, String message) {
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        htmlLogger.log(Status.PASS, "Step: " + stepName);
        htmlLogger.log(Status.PASS, "Message: " + message);
    }

    public void pass(ExtentTest node, String stepName, String message) {
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        node.log(Status.PASS, stepName).log(Status.PASS, message);
    }

    public void pass(String stepName, String... messages) {
        LOGGER.info("%s - PASSED".formatted(stepName));
        for (String message : messages) {
            LOGGER.info(message);
            htmlLogger.log(Status.PASS, message);
        }
    }

    public void pass(ExtentTest node, String stepName, String... messages) {
        LOGGER.info("%s - PASSED".formatted(stepName));
        for (String message : messages) {
            LOGGER.info(message);
            node.log(Status.PASS, message);
        }
    }

    public void pass(String stepName, String message, String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.info("Codeblock: %s ".formatted(codeBlock));
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        htmlLogger.log(Status.PASS, stepName).log(Status.PASS, message).log(Status.INFO, m);
    }

    public void pass(ExtentTest node, String stepName, String message, String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.info("Codeblock: %s ".formatted(codeBlock));
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        node.log(Status.PASS, stepName).log(Status.PASS, message).log(Status.INFO, m);
    }

    public void pass(String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.info("{}", codeBlock);
        htmlLogger.log(Status.PASS, m);
    }

    public void pass(ExtentTest node, String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.info("{}", codeBlock);
        node.log(Status.PASS, m);
    }

    public void passWithScreenshot(String stepName, String message, String screenshotFilePath) {
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        htmlLogger.log(Status.PASS, stepName).log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
    }

    public void passWithScreenshot(ExtentTest node, String stepName, String message, String screenshotFilePath) {
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        node.log(Status.PASS, stepName).log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
    }

    public void passWithScreenshot(String stepName, String message, String screenshotFilePath, String title) {
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        htmlLogger.log(Status.PASS, stepName).log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath, title).build());
    }

    public void passWithScreenshot(ExtentTest node, String stepName, String message, String screenshotFilePath, String title) {
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        node.log(Status.PASS, stepName).log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath, title).build());
    }

    public void passWithScreenshot(String stepName, String message, byte[] bytes) {
        String result = Base64.getEncoder().encodeToString(bytes);
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        htmlLogger.log(Status.PASS, stepName).log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromBase64String(result).build());
    }

    public void passWithScreenshot(ExtentTest node, String stepName, String message, byte[] bytes) {
        String result = Base64.getEncoder().encodeToString(bytes);
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        node.log(Status.PASS, stepName).log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromBase64String(result).build());
    }

    public void passWithScreenshot(String stepName, String message, byte[] bytes, String title) {
        String result = Base64.getEncoder().encodeToString(bytes);
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        htmlLogger.log(Status.PASS, stepName).log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromBase64String(result, title).build());
    }

    public void passWithScreenshot(ExtentTest node, String stepName, String message, byte[] bytes, String title) {
        String result = Base64.getEncoder().encodeToString(bytes);
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        node.log(Status.PASS, stepName).log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromBase64String(result, title).build());
    }

    public void passWithVideo(String stepName, String message, String videoFilePath) {
        LOGGER.info("%s - PASSED | %s".formatted(stepName, message));
        logVideoUsingFile(Status.PASS, "%s | %s".formatted(stepName, message), videoFilePath);
    }

    public void passWithVideo(String stepName, String message, byte[] bytes) {
        LOGGER.warn("%s - PASSED | %s".formatted(stepName, message));
        String result = Base64.getEncoder().encodeToString(bytes);
        logVideoUsingBase64String(Status.PASS, "%s | %s".formatted(stepName, message), result);
    }

    // fail
    public void fail(String message) {
        LOGGER.error(message);
        htmlLogger.log(Status.FAIL, message);
    }

    public void fail(ExtentTest node, String message) {
        LOGGER.error(message);
        node.log(Status.FAIL, message);
    }

    public void fail(Throwable e) {
        LOGGER.error(e.getMessage(), e);
        htmlLogger.log(Status.FAIL, e);
    }

    public void fail(ExtentTest node, Throwable e) {
        LOGGER.error(e.getMessage(), e);
        node.log(Status.FAIL, e);
    }

    public void fail(String stepName, String message, Throwable e) {
        LOGGER.error("{} - FAILED | {}", stepName, message, e);
        htmlLogger.log(Status.FAIL, stepName).log(Status.FAIL, message).log(Status.FAIL, e);
    }

    public void fail(ExtentTest node, String stepName, String message, Throwable e) {
        LOGGER.error("{} - FAILED | {}", stepName, message, e);
        node.log(Status.FAIL, stepName).log(Status.FAIL, message).log(Status.FAIL, e);
    }

    public void fail(String stepName, Throwable e, String... messages) {
        LOGGER.info("%s - FAILED".formatted(stepName));
        for (String message : messages) {
            LOGGER.error(message);
            htmlLogger.log(Status.FAIL, message);
        }
        LOGGER.error(e.getMessage());
        htmlLogger.log(Status.FAIL, e);

    }

    public void fail(ExtentTest node, String stepName, Throwable e, String... messages) {
        LOGGER.info("%s - FAILED".formatted(stepName));
        node.log(Status.FAIL, "Step Name: " + stepName);
        for (String message : messages) {
            LOGGER.error(message);
            node.log(Status.FAIL, message);
        }
    }

    public void fail(String message, Throwable e) {
        LOGGER.error(message, e);
        htmlLogger.log(Status.FAIL, message);
        htmlLogger.log(Status.FAIL, e);
    }

    public void fail(ExtentTest node, String message, Throwable e) {
        LOGGER.error(message, e);
        node.log(Status.FAIL, message);
        node.log(Status.FAIL, e);
    }

    public void fail(String message, Throwable e, Media media) {
        LOGGER.error(message, e);
        htmlLogger.log(Status.FAIL, message, e, media);
    }

    public void fail(ExtentTest node, String message, Throwable e, Media media) {
        LOGGER.error(message, e);
        node.log(Status.FAIL, message, e, media);
    }

    public void fail(String stepName, String message, Throwable e, Media media) {
        LOGGER.error("{} - FAILED | {}", stepName, message, e);
        htmlLogger.log(Status.FAIL, stepName).log(Status.FAIL, message).log(Status.FAIL, e);
    }

    public void fail(ExtentTest node, String stepName, String message, Throwable e, Media media) {
        LOGGER.error("{} - FAILED | {}", stepName, message, e);
        node.log(Status.FAIL, stepName).log(Status.FAIL, message).log(Status.FAIL, e);
    }

    public void fail(String stepName, String message) {
        LOGGER.error("%s | %s".formatted(stepName, message));
        htmlLogger.log(Status.FAIL, stepName).log(Status.FAIL, message);
    }

    public void fail(ExtentTest node, String stepName, String message) {
        LOGGER.error("%s | %s".formatted(stepName, message));
        node.log(Status.FAIL, stepName).log(Status.FAIL, message);
    }

    public void fail(String message, String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.error("%s | %s".formatted(message, codeBlock));
        htmlLogger.log(Status.FAIL, message).log(Status.FAIL, m);
    }

    public void fail(ExtentTest node, String message, String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.error("%s | %s".formatted(message, codeBlock));
        node.log(Status.FAIL, message).log(Status.FAIL, m);
    }

    public void fail(String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.error("Code Block {}", codeBlock);
        htmlLogger.log(Status.FAIL, m);
    }

    public void fail(ExtentTest node, String codeBlock, CodeLanguage codeLanguage) {
        Markup m = MarkupHelper.createCodeBlock(codeBlock, codeLanguage);
        LOGGER.error("Code Block {}", codeBlock);
        node.log(Status.FAIL, m);
    }

    public void failWithScreenshot(String screenshotFilePath) {
        logScreenshotUsingFile(Status.FAIL, screenshotFilePath);
    }

    public void failWithScreenshot(byte[] bytes) {
        String result = Base64.getEncoder().encodeToString(bytes);
        logScreenshotUsingBase64String(Status.FAIL, result);
    }

    public void failWithScreenshot(String stepName, String message, String screenshotFilePath) {
        LOGGER.error("%s | %s".formatted(stepName, message));
        htmlLogger.log(Status.FAIL, stepName).log(Status.FAIL, message).
                log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
    }

    public void failWithScreenshot(ExtentTest node, String stepName, String message, String screenshotFilePath) {
        LOGGER.error("%s | %s".formatted(stepName, message));
        node.log(Status.FAIL, stepName).log(Status.FAIL, message).
                log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
    }

    //continue from here
    public void failWithScreenshot(String stepName, String message, String screenshotFilePath, String title) {
        LOGGER.error("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.FAIL, message).
                log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath, title).build());
    }

    public void failWithScreenshot(String stepName, String message, byte[] bytes) {
        String result = Base64.getEncoder().encodeToString(bytes);
        LOGGER.error("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.FAIL, message).
                log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(result).build());
    }

    public void failWithScreenshot(String stepName, String message, byte[] bytes, String title) {
        String result = Base64.getEncoder().encodeToString(bytes);
        LOGGER.error("%s | %s".formatted(stepName, message));
        htmlLogger.createNode(stepName).log(Status.FAIL, message).
                log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(result, title).build());
    }

    public void failWithVideo(String stepName, String message, String videoFilePath) {
        LOGGER.error("%s | %s".formatted(stepName, message));
        logVideoUsingFile(Status.FAIL, "%s | %s".formatted(stepName, message), videoFilePath);
    }

    public void failWithVideo(String stepName, String message, byte[] bytes) {
        LOGGER.error("%s | %s".formatted(stepName, message));
        String result = Base64.getEncoder().encodeToString(bytes);
        logVideoUsingBase64String(Status.FAIL, "%s | %s".formatted(stepName, message), result);
    }


    //common methods
    public void logScreenshotUsingFile(Status status, String screenshotFilePath) {
        Media media = MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build();
        htmlLogger.log(status, media);
    }

    public void logScreenshotUsingFile(Status status, String screenshotFilePath, String title) {
        Media media = MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath, title).build();
        htmlLogger.log(status, media);
    }

    public void logScreenshotUsingBase64String(Status status, String base64String) {
        Media media = MediaEntityBuilder.createScreenCaptureFromBase64String(base64String).build();
        htmlLogger.log(status, media);
    }

    public void logScreenshotUsingBase64String(Status status, String base64String, String title) {
        Media media = MediaEntityBuilder.createScreenCaptureFromBase64String(base64String, title).build();
        htmlLogger.log(status, media);
    }

    public void logVideoUsingFile(Status status, String videoFilePath, String title) {
        Media media = MediaEntityBuilder.createVideoFromPath(videoFilePath, title).build();
        htmlLogger.log(status, media);
    }

    public void logVideoUsingFile(Status status, String videoFilePath) {
        Media media = MediaEntityBuilder.createVideoFromPath(videoFilePath).build();
        htmlLogger.log(status, media);
    }

    public void logVideoUsingBase64String(Status status, String base64String, String title) {
        Media media = MediaEntityBuilder.createVideoFromBase64String(base64String, title).build();
        htmlLogger.log(status, media);
    }

    public void logVideoUsingBase64String(Status status, String base64String) {
        Media media = MediaEntityBuilder.createVideoFromPath(base64String).build();
        htmlLogger.log(status, media);
    }
}
