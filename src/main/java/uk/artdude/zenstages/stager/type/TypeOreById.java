package uk.artdude.zenstages.stager.type;

import net.darkhax.orestages.compat.crt.OreTiersCrT;

public class TypeOreById extends TypeBase<String> {
    private String replacement;

    public TypeOreById(String original, String replacement) {
        super(original);

        this.replacement = replacement;
    }

    String getReplacement() {
        return replacement;
    }

    @Override
    public void build(String stageName) {
        OreTiersCrT.addNonDefaultingReplacementById(stageName, getValue(), getReplacement());
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
