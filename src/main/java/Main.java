
import io.javalin.Javalin;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create();
        JavalinConfig.addRoutes(app);
        app.start(7070);


//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//        /* BY IP */
//        System.out.println("*********************************************************");
//        System.out.println("*********************************************************");
//        System.out.println("*********************************************************");
//        System.out.println("********************BY IP BY IP BY IP********************");
//        System.out.println("*********************************************************");
//        System.out.println("*********************************************************");
//        HttpHost httpHostByIp = null;
//        try {
//            byte[] bytes = {(byte)216 , (byte)239 , (byte)38 , (byte)21};
//            httpHostByIp = new HttpHost(InetAddress.getByAddress(bytes));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        httpClientExecute(httpClient, httpHostByIp);
//
//
//
//        /* BY NAME */
//        System.out.println("*********************************************************");
//        System.out.println("*********************************************************");
//        System.out.println("*********************************************************");
//        System.out.println("*****************BY NAME BY NAME BY NAME*****************");
//        System.out.println("*********************************************************");
//        System.out.println("*********************************************************");
//        HttpHost httpHostByName = null;
//        try {
//            httpHostByName = new HttpHost(InetAddress.getByName("https://www.boredapi.com/"));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        httpClientExecute(httpClient, httpHostByName);


//        String url1 = "https://www.boredapi.com";
//        String add = "216.239.34.21";
//
//        /* GET CERTIFICATE */
//        URL destinationURL = null;
//        try {
//            destinationURL = new URL(add);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        HttpsURLConnection conn = null;
//        try {
//            conn = (HttpsURLConnection) destinationURL.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            conn.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Certificate[] certs = new Certificate[0];
//        try {
//            certs = conn.getServerCertificates();
//        } catch (SSLPeerUnverifiedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("");
//        for (Certificate cert : certs) {
//            System.out.println("");
//            System.out.println("");
//            System.out.println("");
//            System.out.println("********************************************************");
//            System.out.println("********************************************************");
//            System.out.println("");
//            System.out.println("Certificate is: " + cert);
//        }

    }


    private static void httpClientExecute(CloseableHttpClient httpClient, HttpHost httpHost) {
        try {
            httpClient.execute(httpHost, new HttpGet(),
                    response -> {
                        String bodyAsString = EntityUtils.toString(response.getEntity());
                        System.out.println("");
                        System.out.println(bodyAsString);
                        return response;
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
