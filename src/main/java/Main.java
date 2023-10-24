import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create();
        Configuration.addRoutes(app);
        app.start(7070);
    }
}
