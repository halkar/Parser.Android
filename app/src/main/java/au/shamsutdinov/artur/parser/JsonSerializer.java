package au.shamsutdinov.artur.parser;

import org.json.JSONObject;

import java.util.Map;

import au.shamsutdinov.artur.parser.interfaces.Serializer;

public class JsonSerializer implements Serializer {
    @Override
    public String toString(Map value) {
        return new JSONObject(value).toString();
    }
}
