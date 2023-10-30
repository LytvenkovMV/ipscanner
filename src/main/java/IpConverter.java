import java.net.InetAddress;
import java.net.UnknownHostException;

public final class IpConverter {

    public static long stringToLong(String stringIp) {
        String[] ipAddressInArray = stringIp.split("\\.");
        long longIp = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            longIp += ip * Math.pow(256, power);
        }
        return longIp;
    }

    public static String longToString(long longIp) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(String.valueOf(longIp));
        return inetAddress.getHostAddress();
    }
}
