package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.mc1120.block.MCItemBlock;
import net.darkhax.bookshelf.util.ModUtils;
import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.itemstages.compat.crt.ItemStagesCrT;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Ingredient Type
 * <p>
 * Any kind of Staging of IItemStack or IIngredient would be created into this class.
 * This is for storage purpose as well as adding util methods for comparing or getting data.
 */
public class TypeIngredient extends TypeBase<IIngredient> {
    private boolean stageRecipe;

    public TypeIngredient(IIngredient ingredient, boolean stageRecipe) {
        super(ingredient);

        this.stageRecipe = stageRecipe;
    }

    private IIngredient getIngredient() {
        return getValue();
    }

    private boolean shouldStageRecipe() {
        return stageRecipe;
    }

    @Override
    public boolean isStaged(IIngredient toCompare) {
        return false;
    }

    @Override
    public void build(String stageName) {
        try {
            if (getIngredient() instanceof ILiquidStack) {
                ItemStagesCrT.stageLiquid(stageName, (ILiquidStack) getIngredient());
            } else {
                ItemStagesCrT.addItemStage(stageName, getIngredient());
                if (shouldStageRecipe()) {
                    Recipes.setRecipeStage(stageName, getIngredient());
                }
            }
        } catch (Exception err) {
            CraftTweakerAPI.logError(String.format("[Stage] Failed to stage %s to stage %s.", this.getIngredient().toString(), stageName), err);
        }
    }

    @Override
    public void build(String[] stageNames) {
    }
}
