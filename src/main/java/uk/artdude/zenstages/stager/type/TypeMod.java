package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;
import net.darkhax.itemstages.compat.crt.ItemStagesCrT;

public class TypeMod extends TypeBase<String> {

    public TypeMod(String modId) {
        super(modId);
    }

    @Override
    public void build(String stageName) {
        ItemStagesCrT.stageModItems(stageName, getValue());
        Recipes.setRecipeStageByMod(stageName, getValue());
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
