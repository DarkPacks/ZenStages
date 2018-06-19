package uk.artdude.zenstages.stager.wrappers;

import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.zenstages.StagedIngredient")
public class StagedIngredient {
    private IIngredient ingredient;
    private boolean stageRecipe;

    public StagedIngredient(IIngredient ingredient, boolean stageRecipe) {
        this.ingredient = ingredient;
        this.stageRecipe = stageRecipe;
    }

    public IIngredient getIngredient() {
        return ingredient;
    }

    public boolean shouldStageRecipe() {
        return stageRecipe;
    }
}
