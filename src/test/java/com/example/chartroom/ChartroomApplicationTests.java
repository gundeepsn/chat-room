package com.example.chartroom;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChartroomApplicationTests {

    @Autowired
    private WebApplicationContext context;
    @LocalServerPort
    int randomServerPort;

    private WebDriver webDriver;
    private WebClient webClient;

    @BeforeAll
    void setup() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.webClient = MockMvcWebClientBuilder.webAppContextSetup(context).build();
        this.webDriver = MockMvcHtmlUnitDriverBuilder.webAppContextSetup(context).build();
    }

    @BeforeEach
    void loadPage(){
        webDriver.get("http://localhost:" + randomServerPort + "/index?username=gundeep");
    }

    @Test
    void sendMessageAndCheckItAppears() throws Exception {
        WebElement messageInput = webDriver.findElement(By.id("msg"));
        messageInput.sendKeys("Hi, this is testing");
        WebElement sendBtn = webDriver.findElement(By.id("send-msg"));
        if (webDriver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) webDriver).executeScript("initWebSocket();");
        }

        Thread.sleep(5000);

        sendBtn.click();

        Thread.sleep(3000);
        List<WebElement> messages = webDriver.findElements(By.cssSelector(".message-container .mdui-card"));
        WebElement msgElemToTest = messages.get(messages.size() - 1);
        String[] msgInfo = msgElemToTest.findElement(By.className("message-content")).getText().split(":");
        assertEquals(msgInfo[0].trim(), "gundeep");
        assertEquals(msgInfo[1].trim(), "Hi, this is testing");
    }

    @Test
    void logout() {
        WebElement logoutBtn = webDriver.findElement(By.id("logout"));
        logoutBtn.click();
        assertEquals(webDriver.getCurrentUrl(), "http://localhost:" + String.valueOf(randomServerPort)+"/");
    }
}
