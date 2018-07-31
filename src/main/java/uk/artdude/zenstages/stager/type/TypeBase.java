package uk.artdude.zenstages.stager.type;

public abstract class TypeBase<T> {
    private T value;

    TypeBase(T value) {
        this.value = value;
    }

    /**
     * Get the value of the input Type.
     */
    public T getValue() {
        return value;
    }

    /**
     * Checks if the value passed is "staged" or set to the Class value.
     */
    public boolean isStaged(T toCompare) {
        return value == toCompare;
    }

    /**
     * Build method which each Type will implement to tell the Staging Mods what to do.
     */
    public abstract void build(String stageName);

    public abstract void build(String stageNames[]);

    public abstract void buildRecipe(String stageName);
}
