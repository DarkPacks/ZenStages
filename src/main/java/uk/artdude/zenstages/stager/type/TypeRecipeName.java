package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;

public class TypeRecipeName extends TypeBase<String> {
    private boolean regexRecipe = false;

    public TypeRecipeName(String recipeName) {
        super(recipeName);
    }

    public TypeRecipeName(String regexName, boolean regex) {
        super(regexName);

        this.regexRecipe = regex;
    }

    @Override
    public void build(String stageName) {
        if (regexRecipe) {
            Recipes.setRecipeStageByRegex(stageName, getValue());
        } else {
            Recipes.setRecipeStage(stageName, getValue());
        }
    }

    @Override
    public void build(String[] stageNames) {
    }
}
