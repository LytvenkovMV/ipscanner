import org.apache.http.conn.util.InetAddressUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

public class MyScanner implements Runnable {

    private String baseIP;
    private int subnetMask;
    private int threadId;
    private int numThreads;
    private String outputPath;


    public MyScanner(String baseIP, int subnetMask, int threadId, int numThreads, String outputPath) {
        this.baseIP = baseIP;
        this.subnetMask = subnetMask;
        this.threadId = threadId;
        this.numThreads = numThreads;
        this.outputPath = outputPath;
    }


    public static boolean checkInput(InputDto inputDto) {
        if (inputDto.getIpByte0() < 0 || inputDto.getIpByte0() > 255) return false;
        if (inputDto.getIpByte1() < 0 || inputDto.getIpByte1() > 255) return false;
        if (inputDto.getIpByte2() < 0 || inputDto.getIpByte2() > 255) return false;
        if (inputDto.getIpByte3() < 0 || inputDto.getIpByte3() > 255) return false;
        if (inputDto.getSubnetMask() < 0 || inputDto.getSubnetMask() > 32) return false;
        if (inputDto.getNumThreads() < 0 || inputDto.getNumThreads() > 10) return false;
        return true;
    }


    @Override
    public void run() {

        int subnetSize = (int) Math.pow(2, (32 - subnetMask));
        int subnetsPerThread = subnetSize / numThreads;

        for (int i = threadId * subnetsPerThread; i < (threadId + 1) * subnetsPerThread; i++) {

//            Long longBaseIp = stringIpToLong(baseIP);
//            Long longCurrIp = longBaseIp + i;
//            String currIp = longIpToString(longCurrIp);


//            //Converts a String that represents an IP to a long.
//            InetAddress inetAddress = null;
//            try {
//                inetAddress = InetAddress.getByName(baseIP);
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
//
//            Long longRepresentation = ByteBuffer.wrap(inetAddress.getAddress()).getLong();


            Long longBaseIp = stringIpToLong(baseIP);
            Long longCurrIp = longBaseIp + i;


            //This converts an int representation of ip back to String
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getByName(String.valueOf(longCurrIp));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String currIp = inetAddress.getHostAddress();


            URL destinationURL = null;
            try {
                destinationURL = new URL("https://" + currIp);
            } catch (MalformedURLException e) {
                System.out.println("Can't create URL");
                /////////////////////////e.printStackTrace();
            }

            HttpsURLConnection conn = null;
            try {
                conn = (HttpsURLConnection) destinationURL.openConnection();
            } catch (IOException e) {
                System.out.println("Can't create conn");
                ///////////////////////////////////////////////////e.printStackTrace();
            }

            try {
                conn.connect();
            } catch (IOException e) {
                System.out.println("Can't connect");
                //////////////////////////////////////////////e.printStackTrace();
            }

            Certificate[] certs = new Certificate[0];
            try {
                certs = conn.getServerCertificates();
            } catch (SSLPeerUnverifiedException e) {
                System.out.println("Can't get certificate");
                ///////////////////////////////////////////e.printStackTrace();
            }

            for (Certificate cert : certs) {

                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("********************************************************");
                System.out.println("********************************************************");
                System.out.println("********************** CERTIFICATE *********************");
                System.out.println("********************************************************");
                System.out.println("********************************************************");
                System.out.println("");
                System.out.println(cert);
            }
        }
    }


    private long stringIpToLong(String ipAddress) {

        String[] ipAddressInArray = ipAddress.split(".");

        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }

        return result;
    }


    private String longIpToString(Long ipAddress) {

        String result = "";


        return result;
    }
}