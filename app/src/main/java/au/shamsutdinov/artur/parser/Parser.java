package au.shamsutdinov.artur.parser;

import au.shamsutdinov.artur.parser.interfaces.ElementParser;

public class Parser {
    private final ElementParser[] parsers;

    public Parser(ElementParser[] parsers) {
        this.parsers = parsers;
    }
}
