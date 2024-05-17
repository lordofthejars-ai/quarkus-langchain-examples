import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;

import static org.awaitility.Awaitility.*;

import java.util.concurrent.TimeUnit;

@QuarkusMain
@ActivateRequestContext
public class HelloWorldMain implements QuarkusApplication {

    @Inject
    ValidateResponse validateResponse;

    @Override
    public int run(String... args) throws Exception {

        try (Playwright playwright = Playwright.create()) {
            
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            Page page = browser.newPage();
            BookPage bookPage = new BookPage(page);

            bookPage.navigate();
            bookPage.sendChat("Hi I want to get info on my booking");
            bookPage.waitUntilAgentResponse();

            AiAssert.assertThat(bookPage.lastLineOfChat())
                    .isSimilarTo("It asks for some extra info like booking number, customer name, and customer surname");
            
            bookPage.sendChat("I'm Klaus Heisler");
            bookPage.waitUntilAgentResponse();
            
            AiAssert.assertThat(bookPage.lastLineOfChat())
                    .isSimilarTo("It misses the booking number");
        }

        return 0;
    }
}