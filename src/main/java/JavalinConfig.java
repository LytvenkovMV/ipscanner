import io.javalin.Javalin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JavalinConfig {

    public static void addRoutes(Javalin app) {
        app.get("/", ctx -> {
            ctx.html(Reader.read("src/main/webapp/view/index.html"));
        });

        app.post("/request", ctx -> {

            InputDto inputDto = new InputDto();
            try {
                inputDto.setIpByte3(Integer.parseInt(ctx.formParam("ip_byte_3")));
                inputDto.setIpByte2(Integer.parseInt(ctx.formParam("ip_byte_2")));
                inputDto.setIpByte1(Integer.parseInt(ctx.formParam("ip_byte_1")));
                inputDto.setIpByte0(Integer.parseInt(ctx.formParam("ip_byte_0")));
                inputDto.setSubnetMask(Integer.parseInt(ctx.formParam("mask")));
                inputDto.setNumThreads(Integer.parseInt(ctx.formParam("threats")));
            } catch (Exception e) {
                e.printStackTrace();
                ctx.html(Reader.read("src/main/webapp/view/error.html"));
                return;
            }

            if (!IpScanner.validateInput(inputDto)) {
                ctx.html(Reader.read("src/main/webapp/view/error.html"));
                return;
            }

            String ipByte3 = inputDto.getIpByte3().toString();
            String ipByte2 = inputDto.getIpByte2().toString();
            String ipByte1 = inputDto.getIpByte1().toString();
            String ipByte0 = inputDto.getIpByte0().toString();
            String baseIP = ipByte3 + "." + ipByte2 + "." + ipByte1 + "." + ipByte0;
            int numThreads = inputDto.getNumThreads();
            int subnetMask = inputDto.getSubnetMask();

            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            for (int i = 0; i < numThreads; i++) {
                executor.execute(new IpScanner(baseIP, subnetMask, i, numThreads));
            }
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            ctx.html(Reader.read("src/main/webapp/view/result.html"));
        });
    }
}
