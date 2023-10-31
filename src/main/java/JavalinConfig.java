import io.javalin.Javalin;

public class JavalinConfig {

    public static void addRoutes(Javalin app) {
        app.get("/", Controller.showStartPage);
        app.post("/result", Controller.showResultPage);
    }
}
