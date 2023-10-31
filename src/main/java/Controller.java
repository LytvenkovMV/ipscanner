import io.javalin.http.Handler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Controller {

    public static Handler showStartPage = ctx -> {
        ctx.html(Reader.read("src/main/webapp/view/index.html"));
    };


    public static Handler showResultPage = ctx -> {

        log.info("Scanner start");

        InputDto inputDto = new InputDto();
        try {
            inputDto.setIpByte3(Integer.parseInt(ctx.formParam("ip_byte_3")));
            inputDto.setIpByte2(Integer.parseInt(ctx.formParam("ip_byte_2")));
            inputDto.setIpByte1(Integer.parseInt(ctx.formParam("ip_byte_1")));
            inputDto.setIpByte0(Integer.parseInt(ctx.formParam("ip_byte_0")));
            inputDto.setSubnetMask(Integer.parseInt(ctx.formParam("mask")));
            inputDto.setNumThreads(Integer.parseInt(ctx.formParam("threats")));
            if (!IpScanner.validateInput(inputDto)) throw new Exception();
        } catch (Exception e) {
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

        String output = new String();
        for (int i = 0; i < numThreads; i++) {
            String path = "/ipscanner_output_" + i + ".txt";
            output.concat(Reader.read(path));




            log.info(i+":");
            log.info(output);




        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ipscanner_output.txt"))) {
            writer.write(output);
            log.info("All done");
        } catch (IOException e) {
            log.info("Can't write to file");
        }

        ctx.html(Reader.read("src/main/webapp/view/result.html"));
    };
}