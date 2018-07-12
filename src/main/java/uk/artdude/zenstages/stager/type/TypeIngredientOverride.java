package uk.artdude.zenstages.stager.type;

import crafttweaker.api.item.IIngredient;
import uk.artdude.zenstages.stager.ZenStager;

public class TypeIngredientOverride extends TypeIngredient {
    public TypeIngredientOverride(IIngredient ingredient, boolean stageRecipe) {
        super(ingredient, stageRecipe);
        // Add the IIngredient to the Override List for the Mod Type builder method to use.
        ZenStager.stagingOverrides.add(getValue());
    }

    @Override
    public void build(String stageName) {
        // Re-Stage the Item to the new Stage defined in the Override.
        super.build(stageName);
    }
}
