package database.format;

/**
 * A generic Literal that can be understood regardless of the triplestore backend.
 * Examples include: "I am a String!", 42, 10.1, -100.3E+43, "24/02/2009"^^xsd:Postcode...
 */
public class GenericLiteral extends GenericValue {
    private String literal;

    public GenericLiteral(String literal){
        boolean isImplicitString = literal.matches("\".*\"");

        this.literal = isImplicitString ? literal + "^^<http://www.w3.org/2001/XMLSchema#string>" : literal;
    }

    @Override
    public String get() {
        return literal;
    }

    @Override
    public String toString() {
        return literal;
    }
}
