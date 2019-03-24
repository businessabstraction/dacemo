package database.format;

/**
 * A generic Statement that can be understood regardless of the triplestore backend.
 * Examples include: <http://something.org/Christmas> rdfs:label "Christmas"...
 */
public class GenericStatement {
    private GenericIRI subject, predicate;
    private GenericValue object;

    public GenericStatement(GenericIRI subject, GenericIRI predicate, GenericValue object){
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public GenericIRI getSubject() {
        return subject;
    }

    public GenericIRI getPredicate() {
        return predicate;
    }

    public GenericValue getObject() {
        return object;
    }

    @Override public String toString(){
        return subject.toString() + " " + predicate.toString() + " " + object.toString();
    }
}
