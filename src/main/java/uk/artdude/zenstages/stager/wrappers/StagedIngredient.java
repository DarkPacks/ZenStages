package uk.artdude.zenstages.stager.wrappers;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.zenstages.wrappers.StagedIngredient")
public class StagedIngredient {
    private IIngredient ingredient;
    private boolean stageRecipe;

    public StagedIngredient(IIngredient ingredient, boolean stageRecipe) {
        this.ingredient = ingredient;
        this.stageRecipe = stageRecipe;
    }

    @ZenMethod("ingredient")
    public IIngredient getIngredient() {
        return ingredient;
    }

    @ZenMethod("shouldStage")
    public boolean shouldStageRecipe() {
        return stageRecipe;
    }
}
