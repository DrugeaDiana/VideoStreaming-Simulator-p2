package inputclasses;

public final class FiltersInput {
    private final SortingInput sort = new SortingInput();
    private final ContainsInput contains = new ContainsInput();

    public SortingInput getSort() {
        return sort;
    }

    public ContainsInput getContains() {
        return contains;
    }
}
