package au.shamsutdinov.artur.parser.interfaces;

/**
 * Interface encapsulating converting parsing outcomes into string representation.
 */
public interface Serializer {
    String toString(Object value);
}
