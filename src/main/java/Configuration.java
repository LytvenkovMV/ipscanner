import io.javalin.Javalin;

public class Configuration {

    public static void addRoutes(Javalin app)
    {
        app.get("/", ctx -> {
            ctx.html(Reader.read("src/main/webapp/view/index.html"));
        });

        app.post("/result", ctx -> {
            ctx.html(Reader.read("src/main/webapp/view/result.html"));
        });
    }
}
