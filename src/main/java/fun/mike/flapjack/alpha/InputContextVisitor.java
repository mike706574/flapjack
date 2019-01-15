package fun.mike.flapjack.alpha;

public interface InputContextVisitor {
    void accept(FlatFileInputContext inputContext);

    void accept(IterableInputContext inputContext);

    void accept(CollectionInputContext collectionInputContext);
}
