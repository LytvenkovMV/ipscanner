import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.Certificate;

@Slf4j
public class IpScanner implements Runnable {

    private String baseIP;
    private int subnetMask;
    private int threadId;
    private int numThreads;


    public IpScanner(String baseIP, int subnetMask, int threadId, int numThreads) {
        this.baseIP = baseIP;
        this.subnetMask = subnetMask;
        this.threadId = threadId;
        this.numThreads = numThreads;
    }


    public static boolean validateInput(InputDto inputDto) {
        if (inputDto.getIpByte0() < 0 || inputDto.getIpByte0() > 255) return false;
        if (inputDto.getIpByte1() < 0 || inputDto.getIpByte1() > 255) return false;
        if (inputDto.getIpByte2() < 0 || inputDto.getIpByte2() > 255) return false;
        if (inputDto.getIpByte3() < 0 || inputDto.getIpByte3() > 255) return false;
        if (inputDto.getSubnetMask() < 0 || inputDto.getSubnetMask() > 32) return false;
        if (inputDto.getNumThreads() < 1 || inputDto.getNumThreads() > 10) return false;
        return true;
    }


    @Override
    public void run() {

        StringBuffer results = new StringBuffer();
        String outputFile = "ipscanner_output_" + threadId + ".txt";

        int subnetSize = (int) Math.pow(2, (32 - subnetMask));
        int subnetsPerThread = subnetSize / numThreads;

        for (int i = threadId * subnetsPerThread; i < (threadId + 1) * subnetsPerThread; i++) {

            String currIp = baseIP;
            URL destinationURL;
            HttpsURLConnection conn;
            Certificate[] certs = new Certificate[0];

            try {
                currIp = calcCurrIp(baseIP, i);
                log.info("Start scan " + currIp);
                destinationURL = new URL("https://" + currIp);
                conn = (HttpsURLConnection) destinationURL.openConnection();
                conn.connect();
                certs = conn.getServerCertificates();
                log.info("Found certificate on " + currIp);
            } catch (UnknownHostException e) {
                log.info("Can't calculate IP " + currIp);
            } catch (MalformedURLException e) {
                log.info("Can't create URL " + currIp);
            } catch (SSLPeerUnverifiedException e) {
                log.info("Certificate unverified on " + currIp);
            } catch (IllegalStateException e) {
                log.info("Can't get certificate on " + currIp);
            } catch (IOException e) {
                log.info("Can't connect to " + currIp);
            }

            for (Certificate cert : certs) {

                results.append("\n");
                results.append("Thread " + threadId + ": " + "Certificate on " + currIp + "\n");
                results.append("********************************************************" + "\n");
                results.append("********************************************************" + "\n");
                results.append("********************** CERTIFICATE *********************" + "\n");
                results.append("********************************************************" + "\n");
                results.append("********************************************************" + "\n");
                results.append("\n");
                results.append(cert.toString());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(results.toString());
            log.info("Finished");
        } catch (IOException e) {
            log.info("Can't write to file");
        }
    }


    private String calcCurrIp(String baseIp, int currIndex) throws UnknownHostException {

        /* Convert base IP from string to long */
        String[] ipAddressInArray = baseIp.split("\\.");
        long longBaseIp = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            longBaseIp += ip * Math.pow(256, power);
        }

        /* Increment IP */
        long longCurrIp = longBaseIp + currIndex;

        /* Convert IP from long to string */
        InetAddress inetAddress = InetAddress.getByName(String.valueOf(longCurrIp));
        return inetAddress.getHostAddress();
    }
}