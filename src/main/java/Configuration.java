import io.javalin.Javalin;

public class Configuration {

    public static void addRoutes(Javalin app)
    {
        app.get("/", ctx -> {
            ctx.html(Reader.read("D:/GIT/ipscanner/src/main/webapp/index.html"));
        });

        app.post("/result", ctx -> {
            ctx.html(Reader.read("D:/GIT/ipscanner/src/main/webapp/result.html"));
        });
    }
}
