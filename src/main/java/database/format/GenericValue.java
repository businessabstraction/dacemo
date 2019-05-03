package database.format;

/**
 * A value that can either be a GenericLiteral or GenericIRI depending on the context.
 */
public abstract class GenericValue {
    public abstract String get();
    @Override public abstract String toString();
}
