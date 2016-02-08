package au.shamsutdinov.artur.parser.parsers;

import java.util.regex.Pattern;

public class EmoticonsParser extends RegexParser {
    public EmoticonsParser() {
        super(Pattern.compile("\\(([a-zA-Z0-9]{1,15})\\)"));
    }
}
