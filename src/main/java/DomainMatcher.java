import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainMatcher {

    private static final String regex = "((?!-)[a-z0-9-]{2,63}(?<!-)\\.)+[a-z]{2,6}";
    private Pattern pattern;

    public DomainMatcher() {
        this.pattern = Pattern.compile(this.regex);
    }

    public DomainMatcher(String userRegex) {
        this.pattern = Pattern.compile(userRegex);
    }

    public StringBuffer find(String input) {

        StringBuffer output = new StringBuffer();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            output.append(matcher.group());
            output.append("\n");
        }
        return output;
    }
}
