package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.darkhax.bookshelf.util.ModUtils;
import net.darkhax.itemstages.compat.crt.ActionAddItemRestriction;
import net.darkhax.itemstages.compat.crt.ItemStagesCrT;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class TypeMod extends TypeBase<String> {
    private boolean stageRecipesWithItems;
    private Set<ResourceLocation> overrides;

    public TypeMod(String modId, boolean stageRecipesWithItems, IIngredient... overrides) {
        super(modId);

        this.overrides = new HashSet<>();

        for (IIngredient override : overrides) {
            for (IItemStack item : override.getItems()) {
                this.overrides.add(new ResourceLocation(item.getDefinition().getId()));
            }

        }

        this.stageRecipesWithItems = stageRecipesWithItems;
    }

    /**
     * Both true and false for stageRecipesWithItems will result in recipes AND items being staged for the mod ID.
     * When `stageRecipesWithItems == true`, to keep all the logic within a single loop, Item Staging is done
     * individually rather than using `ItemStagesCrT.stageModItems(stageName, getValue())`
     * <p>
     * When `Recipes.setRecipeStageByMod()` is called (when stageRecipesWithItems == false),
     * RecipeStages searches the recipes registry for all recipes under the mod specified.
     * <p>
     * Alternatively, with `stageRecipesWithItems == true`, the output item is passed to `Recipes.setRecipeStage`
     * which results in RecipeStages searching for all recipes that output that item.
     * This causes OTHER MOD's recipes for that item to ALSO get staged.
     * This is ideal when modifying an item's recipes using CraftTweaker for example
     * This behaviour is exactly the same as when calling ZenStages' addIngredient method.
     */
    @Override
    public void build(String stageName) {
        if (!stageRecipesWithItems) {
            ItemStagesCrT.stageModItems(stageName, getValue());
            Recipes.setRecipeStageByMod(stageName, getValue());
            return;
        }

        for (final Item item : ModUtils.getSortedEntries(ForgeRegistries.ITEMS).get(getValue())) {
            if (item != null && item != Items.AIR) {
                if (!this.overrides.isEmpty() && this.overrides.contains(item.getRegistryName())) continue;
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
