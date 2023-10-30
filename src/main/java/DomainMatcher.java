import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainMatcher {

    private static final String regex = "\\s((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}";

    public static StringBuffer find(String input) {

        StringBuffer output = new StringBuffer();
        Pattern domainPattern = Pattern.compile(regex);
        Matcher matcher = domainPattern.matcher(input);
        while (matcher.find()) {
            output.append(matcher.group());
            output.append("\n");
        }
        return output;
    }
}
