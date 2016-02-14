package au.shamsutdinov.artur.parser;

import com.google.gson.Gson;

import au.shamsutdinov.artur.parser.interfaces.Serializer;

public class JsonSerializer implements Serializer {
    private static final Gson gson = new Gson();
    @Override
    public String toString(Object value) {
        return gson.toJson(value);
    }
}
