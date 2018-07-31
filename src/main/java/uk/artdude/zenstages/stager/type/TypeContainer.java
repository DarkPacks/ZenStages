package uk.artdude.zenstages.stager.type;

public class TypeContainer extends TypeBase<String> {
    public TypeContainer(String containerName) {
        super(containerName);
    }

    @Override
    public void build(String stageName) {
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
