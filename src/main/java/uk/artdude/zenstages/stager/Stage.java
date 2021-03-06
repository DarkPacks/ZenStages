package uk.artdude.zenstages.stager;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantment;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import uk.artdude.zenstages.common.util.Helper;
import uk.artdude.zenstages.stager.type.*;
import uk.artdude.zenstages.stager.type.enums.MultiBlockType;
import uk.artdude.zenstages.stager.type.enums.TinkerType;
import uk.artdude.zenstages.stager.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenClass("mods.zenstages.Stage")
public class Stage {
    private String stage;
    private List<TypeBase> stagedEntries = new ArrayList<>();

    Stage(String stage) {
        this.stage = stage;
    }

    @ZenGetter("stage")
    public String getStage() {
        return stage;
    }

    @ZenGetter("recipeRegex")
    public String getRecipeRegex() {
        return String.format(Utils.craftTweakerRegex, Utils.formatStage(this), ".*");
    }

    List<ILiquidStack> getStagedLiquids() {
        List<TypeIngredient> stagedIngredients = filterEntries(TypeIngredient.class);
        List<ILiquidStack> stackList = new ArrayList<>();

        for (TypeIngredient stagedIngredientEntry : stagedIngredients) {
            IIngredient ingredient = stagedIngredientEntry.getValue();
            if (ingredient instanceof ILiquidStack) {
                stackList.add((ILiquidStack) ingredient);
            }
        }

        return stackList;
    }

    Stage getRecipeNameStage(String recipeName) {
        return getStage(TypeRecipeName.class, recipeName);
    }

    Stage getDimensionStage(int dimension) {
        return getStage(TypeDimension.class, dimension);
    }

    Stage getMobStage(String mobName) {
        return getStage(TypeMob.class, mobName);
    }

    Stage getModStage(String modId) {
        return getStage(TypeMod.class, modId);
    }

    Stage getTiCMaterialStage(String material) {
        return getStage(TypeTinker.class, material);
    }

    Stage getTiCToolStage(String toolName) {
        return getStage(TypeTinker.class, toolName);
    }

    boolean isIngredientStaged(IIngredient ingredient) {
        return this.getIngredientStage(ingredient) != null;
    }

    @ZenMethod
    public boolean isCustomStaged(String slug, String value) {
        return Helper.getCustomTypeByValue(ZenStager.filterCustomByStage(this, slug), value) != null;
    }

    @ZenMethod
    public boolean isCustomStaged(String slug, String[] values) {
        return Helper.getCustomTypeByValue(ZenStager.filterCustomByStage(this, slug), values) != null;
    }

    @ZenMethod
    public boolean isCustomStaged(String slug, int value) {
        return Helper.getCustomTypeByValue(ZenStager.filterCustomByStage(this, slug), value) != null;
    }

    @ZenMethod
    public boolean isCustomStaged(String slug, int[] values) {
        return Helper.getCustomTypeByValue(ZenStager.filterCustomByStage(this, slug), values) != null;
    }

    @ZenMethod
    public boolean isCustomStaged(String slug, IIngredient value) {
        return Helper.getCustomTypeByValue(ZenStager.filterCustomByStage(this, slug), value) != null;
    }

    @ZenMethod
    public boolean isCustomStaged(String slug, IIngredient[] values) {
        return Helper.getCustomTypeByValue(ZenStager.filterCustomByStage(this, slug), values) != null;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addIngredient(IIngredient ingredient, @Optional(valueBoolean = true) boolean recipeStage) {
        if (ingredient == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Ingredient can not be null!", getStage()));

            return this;
        }
        this.stagedEntries.add(new TypeIngredient(ingredient, recipeStage));

        return this;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addIngredients(IIngredient[] ingredients, @Optional(valueBoolean = true) boolean recipeStage) {
        if (ingredients == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Ingredients can not be null!", getStage()));

            return this;
        }
        for (IIngredient ingredient : ingredients) {
            addIngredient(ingredient, recipeStage);
        }

        return this;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addEnchantment(IEnchantmentDefinition enchantmentDefinition) {
        if (enchantmentDefinition == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] EnchantmentDefinition can not be null!", getStage()));

            return this;
        }
        this.stagedEntries.add(new TypeEnchantmentDefinition(enchantmentDefinition));

        return this;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addEnchantments(IEnchantmentDefinition[] enchantmentDefinitions) {
        if (enchantmentDefinitions == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Enchantment can not be null!", getStage()));

            return this;
        }
        for (IEnchantmentDefinition enchantmentDefinition : enchantmentDefinitions) {
            addEnchantment(enchantmentDefinition);
        }

        return this;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addEnchantmentByLevel(IEnchantment enchantment) {
        if (enchantment == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Enchantment can not be null!", getStage()));

            return this;
        }
        this.stagedEntries.add(new TypeEnchantment(enchantment));

        return this;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addEnchantmentsByLevel(IEnchantment[] enchantments) {
        if (enchantments == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Enchantment can not be null!", getStage()));

            return this;
        }
        for (IEnchantment enchantment : enchantments) {
            addEnchantmentByLevel(enchantment);
        }

        return this;
    }

    @ZenMethod
    public Stage addIngredientOverride(IIngredient ingredient, @Optional(valueBoolean = true) boolean recipeStage) {
        if (ingredient == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Ingredient can not be null!", getStage()));

            return this;
        }
        if (ZenStager.stagingOverrides.contains(ingredient)) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Failed to add override for %s as it's already been told to override!", getStage(), ingredient.toString()));
        }
        this.stagedEntries.add(new TypeIngredientOverride(ingredient, recipeStage));

        return this;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addModId(String modId, @Optional(valueBoolean = false) boolean stageRecipesWithItems, @Optional IIngredient... overrides) {
        if (!Loader.isModLoaded(modId)) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Failed to add `%s` as the mod is not even loaded?", getStage(), modId));

            return this;
        }
        this.stagedEntries.add(new TypeMod(modId, stageRecipesWithItems, overrides));

        return this;
    }

    @ZenMethod
    public Stage addModId(String[] modIds, @Optional(valueBoolean = false) boolean stageRecipesWithItems) {
        for (String modId : modIds) {
            addModId(modId, stageRecipesWithItems);
        }

        return this;
    }

    @ZenMethod
    public Stage addLiquid(ILiquidStack liquidStack) {
        addIngredient(liquidStack, false);

        return this;
    }

    @ZenMethod
    public Stage addLiquids(ILiquidStack[] liquidStacks) {
        addIngredients(liquidStacks, false);

        return this;
    }

    @ZenMethod
    @Method(modid = "dimstages")
    public Stage addDimension(int dimension) {
        this.stagedEntries.add(new TypeDimension(dimension));

        return this;
    }

    @Method(modid = "recipestages")
    @SuppressWarnings("UnusedReturnValue")
    Stage addContainer(String container) {
        this.stagedEntries.add(new TypeContainer(container));

        return this;
    }

    @Method(modid = "recipestages")
    @SuppressWarnings("UnusedReturnValue")
    Stage addPackage(String container) {
        this.stagedEntries.add(new TypePackage(container));

        return this;
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public Stage addRecipeName(String recipeName) {
        if (Helper.validateRecipeName(recipeName) == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Recipe name `%s` is not valid! Example: minecraft:boat", getStage(), recipeName));

            return this;
        }
        this.stagedEntries.add(new TypeRecipeName(recipeName));

        return this;
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public Stage addRecipeRegex(String recipeRegex) {
        this.stagedEntries.add(new TypeRecipeRegex(recipeRegex));

        return this;
    }

    @ZenMethod
    @Method(modid = "mobstages")
    public Stage addMobs(String[] mobNames) {
        for (String mobName : mobNames) {
            addMob(mobName);
        }

        return this;
    }

    @ZenMethod
    @Method(modid = "mobstages")
    @SuppressWarnings("UnusedReturnValue")
    public Stage addMob(String mobName) {
        this.stagedEntries.add(new TypeMob(mobName));

        return this;
    }

    @ZenMethod
    @Method(modid = "mobstages")
    public Stage addMobs(String[] mobNames, int dimension) {
        for (String mobName : mobNames) {
            addMob(mobName, dimension);
        }

        return this;
    }

    @ZenMethod
    @Method(modid = "mobstages")
    @SuppressWarnings("UnusedReturnValue")
    public Stage addMob(String mobName, int dimensionId) {
        this.stagedEntries.add(new TypeMob(mobName, dimensionId));

        return this;
    }

    @ZenMethod
    @Method(modid = "tinkerstages")
    public Stage addTiCMaterials(String[] materialNames) {
        for (String materialName : materialNames) {
            addTiCMaterial(materialName);
        }

        return this;
    }

    @ZenMethod
    @Method(modid = "tinkerstages")
    @SuppressWarnings("UnusedReturnValue")
    public Stage addTiCMaterial(String materialName) {
        this.stagedEntries.add(new TypeTinker(TinkerType.MATERIAL, materialName));

        return this;
    }

    @ZenMethod
    @Method(modid = "tinkerstages")
    @SuppressWarnings("UnusedReturnValue")
    public Stage addTiCModifier(String modifierName) {
        this.stagedEntries.add(new TypeTinker(TinkerType.MODIFIER, modifierName));

        return this;
    }

    @ZenMethod
    @Method(modid = "tinkerstages")
    public Stage addTiCModifier(String modifierNames[]) {
        for (String modifierName : modifierNames) {
            addTiCModifier(modifierName);
        }

        return this;
    }

    @ZenMethod
    @Method(modid = "tinkerstages")
    public Stage addTiCToolTypes(String[] toolTypes) {
        for (String toolType : toolTypes) {
            addTiCToolType(toolType);
        }

        return this;
    }

    @ZenMethod
    @Method(modid = "tinkerstages")
    @SuppressWarnings("UnusedReturnValue")
    public Stage addTiCToolType(String toolType) {
        this.stagedEntries.add(new TypeTinker(TinkerType.TOOL, toolType));

        return this;
    }

    @ZenMethod
    @Method(modid = "multiblockstages")
    public Stage addIEMultiBlocks(String[] multiblocks) {
        for (String multiblock : multiblocks) {
            addIEMultiBlock(multiblock);
        }

        return this;
    }

    @ZenMethod
    @Method(modid = "multiblockstages")
    @SuppressWarnings("UnusedReturnValue")
    public Stage addIEMultiBlock(String multiblock) {
        this.stagedEntries.add(new TypeMultiBlock(MultiBlockType.IE, multiblock));

        return this;
    }

    @ZenMethod
    @Method(modid = "orestages")
    public Stage addOreReplacement(IIngredient blockToHide, @Optional(valueBoolean = false) boolean isNonDefaulting) {
        if (blockToHide == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Ore can not be null!", this.getStage()));

            return this;
        }

        TypeOre typeOre = new TypeOre(blockToHide);
        typeOre.setNonDefaulting(isNonDefaulting);
        this.stagedEntries.add(typeOre);

        return this;
    }

    @ZenMethod
    @Method(modid = "orestages")
    public Stage addOreReplacement(IIngredient blockToHide, IItemStack blockToShow, @Optional(valueBoolean = false) boolean isNonDefaulting) {
        if (blockToHide == null || blockToShow == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Ore can not be null!", getStage()));

            return this;
        }

        TypeOre typeOre = new TypeOre(blockToHide, blockToShow);
        typeOre.setNonDefaulting(isNonDefaulting);
        this.stagedEntries.add(typeOre);

        return this;
    }

    @ZenMethod
    @Method(modid = "orestages")
    public Stage addOreReplacement(String original, String replacement) {
        if (original == null || original.length() < 1 || replacement == null || replacement.length() < 1) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Ore original or replacement can not be null or empty!", getStage()));

            return this;
        }
        this.stagedEntries.add(new TypeOreById(original, replacement));

        return this;
    }

    /**
     * Build loops over all the entries for things to stage and calls the build method on each other entry.
     */
    void build() {
        for (TypeBase newType : stagedEntries) {
            // These are built else where so don't call the build method.
            if (newType instanceof TypeRecipeRegex) {
                continue;
            }
            newType.build(getStage());
        }
    }

    /**
     * Build loops over all the entries for staging recipes if support by the type.
     */
    void buildRecipe() {
        filterEntries(TypeRecipeRegex.class).forEach(t -> t.buildRecipe(getStage()));
    }

    /**
     * Filter the Staged Entries by the class given.
     */
    <T> List<T> filterEntries(Class<T> clazz) {
        return stagedEntries.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
    }

    /**
     * Return the Stage in that the Value is Staged in.
     */
    @SuppressWarnings("unchecked")
    <T extends TypeBase, R> Stage getStage(Class<T> clazz, R value) {
        return this.filterEntries(clazz).stream()
                .filter(t -> t.isStaged(value))
                .collect(Collectors.toSet())
                .size() > 0 ? this : null;
    }

    /**
     * Get the Stage of what an IIngredient is in.
     */
    private Stage getIngredientStage(IIngredient testIngredient) {
        List<TypeIngredient> stagedIngredients = filterEntries(TypeIngredient.class);

        for (TypeIngredient stagedIngredientEntry : stagedIngredients) {
            IIngredient ingredient = stagedIngredientEntry.getValue();

            if (testIngredient instanceof MCOreDictEntry) {
                if (ingredient instanceof MCOreDictEntry) {
                    if (((MCOreDictEntry) testIngredient).getName().equals(((MCOreDictEntry) ingredient).getName())) {
                        return this;
                    }
                }
            } else if (ingredient instanceof MCOreDictEntry) {
                if (ingredient.contains(testIngredient)) {
                    return this;
                }
            } else if (testIngredient instanceof ILiquidStack) {
                if (ingredient.matches((ILiquidStack) testIngredient)) {
                    return this;
                }
            } else if (testIngredient instanceof IItemStack) {
                if (ingredient.matchesExact((IItemStack) testIngredient)) {
                    return this;
                }
            } else if (ingredient.contains(testIngredient)) {
                return this;
            }
        }

        return null;
    }
}
