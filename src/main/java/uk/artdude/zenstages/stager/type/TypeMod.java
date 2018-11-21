package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.item.MCItemStack;
import net.darkhax.bookshelf.util.ModUtils;
import net.darkhax.itemstages.compat.crt.ActionAddItemRestriction;
import net.darkhax.itemstages.compat.crt.ItemStagesCrT;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TypeMod extends TypeBase<String> {
    private boolean stageRecipeWithItem;

    public TypeMod(String modId, boolean stageRecipeWithItem) {
        super(modId);

        this.stageRecipeWithItem = stageRecipeWithItem;
    }

    @Override
    public void build(String stageName) {
        if (!stageRecipeWithItem) {
            ItemStagesCrT.stageModItems(stageName, getValue());
            Recipes.setRecipeStageByMod(stageName, getValue());
            return;
        }

        for (final Item item : ModUtils.getSortedEntries(ForgeRegistries.ITEMS).get(getValue())) {
            if (item != null && item != Items.AIR) {
                // Stage Item
                CraftTweakerAPI.apply(new ActionAddItemRestriction(stageName, item));

                // Stage Item's recipes
                Recipes.setRecipeStage(stageName, MCItemStack.createNonCopy(new ItemStack(item)));
            }
        }
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
