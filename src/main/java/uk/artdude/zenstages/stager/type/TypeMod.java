package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;
import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.darkhax.bookshelf.util.ModUtils;
import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.itemstages.compat.crt.ActionAddItemRestriction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import uk.artdude.zenstages.stager.ZenStager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeMod extends TypeBase<String> {
    private boolean stageRecipesWithItems;
    private Map<ResourceLocation, List<IItemStack>> overrides;

    public TypeMod(String modId, boolean stageRecipesWithItems, IIngredient... overrides) {
        super(modId);

        this.overrides = new HashMap<>();

        for (IIngredient override : overrides) {
            for (IItemStack item : override.getItems()) {
                ResourceLocation resourceLocation = new ResourceLocation(item.getDefinition().getId());
                if (this.overrides.containsKey(resourceLocation))
                    this.overrides.get(resourceLocation).add(item);
                else
                    this.overrides.put(resourceLocation, Lists.newArrayList(item));
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
        for (final MCItemStack mcItemStack : ModUtils.getSortedEntries(ForgeRegistries.ITEMS).get(getValue()).stream().flatMap(item -> Arrays.stream(StackUtils.getAllItems(item))).map(MCItemStack::createNonCopy).collect(Collectors.toList())) {
            ResourceLocation resourceLocation = new ResourceLocation(mcItemStack.getDefinition().getId());
            if (this.overrides.containsKey(resourceLocation)
                    && this.overrides.get(resourceLocation).stream().anyMatch(iItemStack -> iItemStack.matches(mcItemStack))
            ) continue;

            if (ZenStager.modItemOverrides.containsKey(getValue())
                    && ZenStager.modItemOverrides.get(getValue()).containsKey(resourceLocation)
                    && ZenStager.modItemOverrides.get(getValue()).get(resourceLocation).stream().anyMatch(iItemStack -> iItemStack.matches(mcItemStack))
            ) continue;

            // Stage Item
            CraftTweakerAPI.apply(new ActionAddItemRestriction(stageName, mcItemStack));

            // Stage Item's recipes
            if (stageRecipesWithItems)
                Recipes.setRecipeStage(stageName, mcItemStack);
        }
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
