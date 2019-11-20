package com.example.chartroom;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginChatRoomTest {

    @Autowired
    private WebApplicationContext context;

    private WebDriver webDriver;
    private WebClient webClient;

    @BeforeAll
    public void setup() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.webClient = MockMvcWebClientBuilder.webAppContextSetup(context).build();
        this.webDriver = MockMvcHtmlUnitDriverBuilder.webAppContextSetup(context).build();
    }

    @Test
    void Login() throws InterruptedException {
        webDriver.get("http://localhost:8080");
        WebElement usernameInput = webDriver.findElement(By.id("username"));
        usernameInput.sendKeys("gundeep");

        WebElement loginBtn = webDriver.findElement(By.className("submit"));
        loginBtn.click();

        (new WebDriverWait(webDriver, 5)).until(new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver webDriver) {
                WebElement usernamePlaceholder = webDriver.findElement(By.id("username"));
                return usernamePlaceholder.getText().equals("gundeep");
            }
        });
    }
}
