package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;

public class TypeRecipeRegex extends TypeBase<String> {

    public TypeRecipeRegex(String recipeName) {
        super(recipeName);
    }

    @Override
    public void build(String stageName) {}

    @Override
    public void build(String[] stageNames) {}

    public void buildRecipe(String stageName) {
        Recipes.setRecipeStageByRegex(stageName, getValue());
    }
}
