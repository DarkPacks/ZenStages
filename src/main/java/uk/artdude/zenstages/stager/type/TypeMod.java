package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.darkhax.bookshelf.util.ModUtils;
import net.darkhax.itemstages.compat.crt.ActionAddItemRestriction;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import uk.artdude.zenstages.stager.ZenStager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeMod extends TypeBase<String> {
    private List<IIngredient> unstagedItems = new ArrayList<>();

    public TypeMod(String modId) {
        super(modId);
    }

    public TypeMod(String modId, IIngredient[] ignoreStaging) {
        super(modId);

        Collections.addAll(unstagedItems, ignoreStaging);
    }

    @Override
    public void build(String stageName) {
        for (Item item : ModUtils.getSortedEntries(ForgeRegistries.ITEMS).get(getValue())) {
            if (item == null || item == Items.AIR) {
                continue;
            }
            IItemStack itemStack = new MCItemStack(new ItemStack(item));

            // If an Item from a mod needs to be staged another Stage. Check if an override was provided.
            boolean stagedOverride = ZenStager.stagingOverrides.contains(itemStack);
            if (stagedOverride) {
                continue;
            }
            // Check that the current Item is not "blacklisted" from being Staged.
            if (unstagedItems.contains(itemStack)) {
                continue;
            }
            CraftTweakerAPI.apply(new ActionAddItemRestriction(stageName, item));
            Recipes.setRecipeStage(stageName, itemStack);
        }
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
