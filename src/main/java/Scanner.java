public class Scanner implements Runnable {

    private String baseIP;
    private int subnetMask;
    private int threadId;
    private int numThreads;
    private String outputPath;

    public Scanner(String baseIP, int subnetMask, int threadId, int numThreads, String outputPath) {
        this.baseIP = baseIP;
        this.subnetMask = subnetMask;
        this.threadId = threadId;
        this.numThreads = numThreads;
        this.outputPath = outputPath;
    }

    @Override
    public void run() {
//        try {
//            Pattern domainPattern = Pattern.compile("CN=([^,]*)");
//            ArrayList<String> results = new ArrayList<>();
//            String outputFile = "output_" + threadId + ".txt";
//
//            int subnetSize = (int) Math.pow(2, (32 - subnetMask));
//            int subnetsPerThread = subnetSize / numThreads;
//
//            for (int i = threadId * subnetsPerThread; i < (threadId + 1) * subnetsPerThread; i++) {
//                int subnetAddress = InetAddressUtils.ipStringToInt(baseIP) + i;
//                String ip = InetAddressUtils.intToIp(subnetAddress);
//
//                CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContext.getDefault(), NoopHostnameVerifier.INSTANCE)).build();
//                HttpGet httpGet = new HttpGet("https://" + ip);
//
//                try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
//                    X509Certificate[] certs = (X509Certificate[]) response.getSSLSession().getPeerCertificates();
//                    for (X509Certificate cert : certs) {
//                        String subjectDN = cert.getSubjectDN().getName();
//                        Matcher matcher = domainPattern.matcher(subjectDN);
//                        while (matcher.find()) {
//                            results.add(matcher.group(1));
//                        }
//                    }
//                } catch (IOException e) {
//                    // Handle connection errors or certificates not found
//                }
//            }
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
//                for (String result : results) {
//                    writer.write(result);
//                    writer.newLine();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}