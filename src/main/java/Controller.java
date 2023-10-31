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
        StringBuffer output = new StringBuffer();
        InputDto inputDto = new InputDto();
        try {
            inputDto.setIpByte3(Integer.parseInt(ctx.formParam("ip_byte_3")));
            inputDto.setIpByte2(Integer.parseInt(ctx.formParam("ip_byte_2")));
            inputDto.setIpByte1(Integer.parseInt(ctx.formParam("ip_byte_1")));
            inputDto.setIpByte0(Integer.parseInt(ctx.formParam("ip_byte_0")));
            inputDto.setSubnetMask(Integer.parseInt(ctx.formParam("mask")));
            inputDto.setNumThreads(Integer.parseInt(ctx.formParam("threats")));
            inputDto.setDisk(ctx.formParam("disk"));
            if (!validateInput(inputDto)) throw new Exception();
        } catch (Exception e) {
            log.info("Invalid input");
            ctx.html(Reader.read("src/main/webapp/view/error1.html"));
            return;
        }

        String ipByte3 = inputDto.getIpByte3().toString();
        String ipByte2 = inputDto.getIpByte2().toString();
        String ipByte1 = inputDto.getIpByte1().toString();
        String ipByte0 = inputDto.getIpByte0().toString();
        String baseIP = ipByte3 + "." + ipByte2 + "." + ipByte1 + "." + ipByte0;
        String disk = inputDto.getDisk();
        int numThreads = inputDto.getNumThreads();
        int subnetMask = inputDto.getSubnetMask();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executor.execute(new IpScanner(baseIP, subnetMask, i, numThreads));
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        for (int i = 0; i < numThreads; i++) {
            String path = "output_" + i + ".txt";
            output.append(Reader.read(path));
        }
        System.out.println("SCANNER OUTPUT:");
        System.out.println(output);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(disk + ":/ipscanner_output.txt"))) {
            writer.write(output.toString());
        } catch (IOException e) {
            log.info("Can't create output file");
            ctx.html(Reader.read("src/main/webapp/view/error2.html"));
            return;
        }

        ctx.html(Reader.read("src/main/webapp/view/result.html"));
        log.info("All done");
    };


    private static boolean validateInput(InputDto inputDto) {
        if (inputDto.getIpByte0() == null) return false;
        if (inputDto.getIpByte1() == null) return false;
        if (inputDto.getIpByte2() == null) return false;
        if (inputDto.getIpByte3() == null) return false;
        if (inputDto.getSubnetMask() == null) return false;
        if (inputDto.getNumThreads() == null) return false;
        if (inputDto.getDisk() == null) return false;

        if (inputDto.getIpByte0() < 0 || inputDto.getIpByte0() > 255) return false;
        if (inputDto.getIpByte1() < 0 || inputDto.getIpByte1() > 255) return false;
        if (inputDto.getIpByte2() < 0 || inputDto.getIpByte2() > 255) return false;
        if (inputDto.getIpByte3() < 0 || inputDto.getIpByte3() > 255) return false;
        if (inputDto.getSubnetMask() < 0 || inputDto.getSubnetMask() > 32) return false;
        if (inputDto.getNumThreads() < 1 || inputDto.getNumThreads() > 10) return false;
        switch (inputDto.getDisk()) {
            case "C":
            case "D":
            case "E":
            case "F":
            case "G":
            case "H":
            case "I":
            case "J":
            case "K":
                break;
            default:
                return false;
        }
        return true;
    }
}