package database.format;

/**
 * A generic IRI that can be understood regardless of the triplestore backend.
 * Examples include: http://something.org/prefix#isSubclassOf, mailto:example@thing.com, foaf:knows...
 */
public class GenericIRI extends GenericValue {
    private String iri;

    public GenericIRI(String iri){
        this.iri = iri;
    }

    @Override
    public String get() {
        return iri;
    }

    @Override public String toString(){
        return "<" + iri + ">";
    }
}
