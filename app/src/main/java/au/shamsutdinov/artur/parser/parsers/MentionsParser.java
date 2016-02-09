package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Pattern;

public class MentionsParser extends RegexParser {
    public MentionsParser() {
        super(Pattern.compile("@(\\w+)"));
    }
}
