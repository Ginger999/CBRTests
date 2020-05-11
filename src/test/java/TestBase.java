import org.junit.After;
import org.junit.Before;

public class TestBase {

    public static ThreadLocal<Application> tlApp = new ThreadLocal<>();
    public Application app;

    @Before
    public void start() {
        // if (tlApp.get() != null) {
        //     System.out.println("********** TestBase tlApp.get() != null");
        //     app = tlApp.get();
        //     return;
        // }
        app = new Application();
        tlApp.set(app);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!(app.equals(null))) {
                app.quit();
                app = null;
            }
    }));

    }
    @After
    public void stop() {
        app.quit();
        app = null;
    }
}
