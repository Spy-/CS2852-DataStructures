package bretzj;

public abstract class BaseAutoCompleter implements AutoCompleter {

    protected long start;
    protected long end;
    protected boolean dictionaryLoaded;

    @Override
    public long getLastOperationTime() {
        if (dictionaryLoaded) {
            return end - start;
        }
        throw new IllegalStateException("Must call Initialize() prior to calling this method");
    }
}
