package fun.mike.flapjack.beta;

public interface InputContextVisitor {
    void accept(FlatFileInputContext inputContext);

    void accept(FlatReaderInputContext inputContext);

    void accept(IterableInputContext inputContext);

    void accept(CollectionInputContext collectionInputContext);
}
