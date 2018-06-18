package uk.artdude.zenstages.stager;

import com.blamejared.recipestages.handlers.Recipes;
import com.teamacronymcoders.multiblockstages.MultiBlockStage;
import com.teamacronymcoders.multiblockstages.MultiBlockStages;
import com.teamacronymcoders.multiblockstages.immersiveengineering.IEMultiBlockStages;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import net.darkhax.dimstages.compat.crt.DimensionStagesCrT;
import net.darkhax.itemstages.compat.crt.ItemStagesCrT;
import net.darkhax.mobstages.compat.crt.MobStagesCrT;
import net.darkhax.tinkerstages.compat.crt.TinkerStagesCrT;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import uk.artdude.zenstages.common.util.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister
@ZenClass("mods.zenstages.Stage")
public class Stage {
    private String stage;
    private Map<IIngredient, StagedIngredient> stagedIngredients = new HashMap<>();
    private List<StagedType> stagedTypes = new ArrayList<>();

    Stage(String stage) {
        this.stage = stage;
    }

    @ZenGetter("stage")
    public String getStage() {
        return stage;
    }

    @ZenMethod
    public Map<IIngredient, StagedIngredient> getStagedIngredients() {
        return stagedIngredients;
    }

    @ZenMethod
    public List<StagedType> getStagedTypes() {
        return stagedTypes;
    }

    @ZenMethod
    public List<ILiquidStack> getStagedLiquids() {
        List<ILiquidStack> stackList = new ArrayList<>();
        for (Map.Entry<IIngredient, StagedIngredient> stagedIngredientEntry : stagedIngredients.entrySet()) {
            StagedIngredient ingredient = stagedIngredientEntry.getValue();
            if (ingredient.getIngredient() instanceof ILiquidStack) {
                stackList.add((ILiquidStack) ingredient.getIngredient());
            }
        }

        return stackList;
    }

    @ZenMethod
    public Stage getIngredientStage(IIngredient testIngredient) {
        for (Map.Entry<IIngredient, StagedIngredient> stagedIngredientEntry : stagedIngredients.entrySet()) {
            IIngredient ingredient = stagedIngredientEntry.getValue().getIngredient();

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
            } else if (ingredient.contains(testIngredient)) {
                return this;
            }
        }

        return null;
    }

    @ZenMethod
    public Stage getContainerStage(String container) {
        return getStageFromType(Types.CONTAINER, container);
    }

    @ZenMethod
    public Stage getRecipeNameStage(String recipeName) {
        return getStageFromType(Types.RECIPE_NAME, recipeName);
    }

    @ZenMethod
    public Stage getDimensionStage(int dimension) {
        return getStageFromType(Types.DIMENSION, Integer.toString(dimension));
    }

    @ZenMethod
    public Stage getMobStage(String mobName) {
        return getStageFromType(Types.MOB, mobName);
    }

    @ZenMethod
    public Stage getTiCMaterialStage(String material) {
        return getStageFromType(Types.TINKER_MATERIAL, material);
    }

    @ZenMethod
    public Stage getTiCToolStage(String toolName) {
        return getStageFromType(Types.TINKER_TOOL, toolName);
    }

    @ZenMethod
    public boolean isStaged(IIngredient ingredient) {
        return this.getIngredientStage(ingredient) != null;
    }

    @ZenMethod
    public boolean isStaged(String name) {
        if (this.getContainerStage(name) != null) {
            return true;
        }

        if (this.getMobStage(name) != null) {
            return true;
        }

        if (this.getTiCMaterialStage(name) != null) {
            return true;
        }

        if (this.getTiCToolStage(name) != null) {
            return true;
        }

        if (Helper.validateRecipeName(name) == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Recipe name `%s` is not valid! Example: minecraft:boat", this.getStage(), name));

            return false;
        }

        return this.getRecipeNameStage(name) != null;
    }

    @ZenMethod
    @Method(modid = "dimstages")
    public boolean isStaged(int dimension) {
        return this.getDimensionStage(dimension) != null;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addIngredient(IIngredient ingredient, @Optional(valueBoolean = true) boolean recipeStage) {
        if (ingredient == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Ingredient can not be null!", this.getStage()));

            return this;
        }
        if (stagedIngredients.containsKey(ingredient)) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Failed to add the ingredient `%s` due to already being added.", this.getStage(), ingredient.toString()));

            return this;
        }
        stagedIngredients.put(ingredient, new StagedIngredient(ingredient, recipeStage));

        return this;
    }

    @ZenMethod
    @SuppressWarnings("UnusedReturnValue")
    public Stage addIngredients(IIngredient[] ingredients, @Optional(valueBoolean = true) boolean recipeStage) {
        for (IIngredient ingredient : ingredients) {
            addIngredient(ingredient, recipeStage);
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
        stageType(Types.DIMENSION, Integer.toString(dimension));

        return this;
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public Stage addContainer(String container) {
        stageType(Types.CONTAINER, container);

        return this;
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public Stage addPackage(String container) {
        stageType(Types.PACKAGE, container);

        return this;
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public Stage addRecipeName(String recipeName) {
        if (Helper.validateRecipeName(recipeName) == null) {
            CraftTweakerAPI.logError(String.format("[Stage %s] Recipe name `%s` is not valid! Example: minecraft:boat", this.getStage(), recipeName));

            return this;
        }

        stageType(Types.RECIPE_NAME, recipeName);

        return this;
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public Stage addRecipeRegex(String recipeRegex) {
        stageType(Types.RECIPE_REGEX, recipeRegex);

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
        stageType(Types.MOB, mobName);

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
    public Stage addMob(String mobName, int dimension) {
        stageType(Types.MOB, mobName, Integer.toString(dimension));

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
        stageType(Types.TINKER_MATERIAL, materialName);

        return this;
    }

    @ZenMethod
    @Method(modid = "tinkerstages")
    public Stage addTiCModifier(String modifierName) {
        stageType(Types.TINKER_MODIFIER, modifierName);

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
        stageType(Types.TINKER_TOOL, toolType);

        return this;
    }

    @ZenMethod
    @Method(modid = "multiblockstages")
    @SuppressWarnings("UnusedReturnValue")
    public Stage addIEMultiBlock(String multiblock) {
        stageType(Types.IE_MULTIBLOCK, multiblock);

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
    @SuppressWarnings("UnusedReturnValue")
    public Stage build() {
        for (Map.Entry<IIngredient, StagedIngredient> stagedIngredient : stagedIngredients.entrySet()) {
            StagedIngredient ingredient = stagedIngredient.getValue();
            if (ingredient.getIngredient() instanceof ILiquidStack) {
                ItemStagesCrT.stageLiquid(getStage(), (ILiquidStack) ingredient.getIngredient());
            } else {
                ItemStagesCrT.addItemStage(getStage(), ingredient.getIngredient());
                if (ingredient.shouldStageRecipe()) {
                    Recipes.setRecipeStage(getStage(), ingredient.getIngredient());
                }
            }
        }

        for (StagedType stagedType : stagedTypes) {
            switch (stagedType.getType()) {
                case RECIPE_NAME:
                    Recipes.setRecipeStage(getStage(), stagedType.getValue());
                    break;
                case RECIPE_REGEX:
                    Recipes.setRecipeStageByRegex(getStage(), stagedType.getValue());
                case DIMENSION:
                    DimensionStagesCrT.addDimensionStage(getStage(), Integer.parseInt(stagedType.getValue()));
                    break;
                case MOB:
                    if (stagedType.getSubValue() != null) {
                        MobStagesCrT.addStage(getStage(), stagedType.getValue(), Integer.parseInt(stagedType.getSubValue()));
                    } else {
                        MobStagesCrT.addStage(getStage(), stagedType.getValue());
                    }
                    break;
                case TINKER_MATERIAL:
                    TinkerStagesCrT.addMaterialStage(getStage(), stagedType.getValue());
                    break;
                case TINKER_MODIFIER:
                    TinkerStagesCrT.addModifierStage(getStage(), stagedType.getValue());
                    break;
                case TINKER_TOOL:
                    TinkerStagesCrT.addToolTypeStage(getStage(), stagedType.getValue());
                    break;
                case IE_MULTIBLOCK:
                    IEMultiBlockStages.addStage(getStage(), stagedType.getValue());
                    break;
            }
        }

        return this;
    }

    /**
     * Get the Staged Types by filtering by a Type.
     */
    List<StagedType> getStagedTypes(Types type) {
        return stagedTypes.stream().filter(t -> t.getType() == type).collect(Collectors.toList());
    }

    /**
     * Add the Stage Type to the map listing. Also check if that type to stage is not already staged.
     */
    private void stageType(Types type, String value) {
        stageType(type, value, null);
    }
    private void stageType(Types type, String value, String subValue) {
        for (StagedType stagedType : getStagedTypes(type)) {
            if (stagedType.getValue().equalsIgnoreCase(value)) {
                CraftTweakerAPI.logError(String.format("[Stage %s] Failed to add the %s `%s` due to already being added.", this.getStage(), type.name(), value));

                return;
            }
        }

        stagedTypes.add(new StagedType(value, type, subValue));
    }

    /**
     * Check if the Stage Type and the value given is staged in this "Stage".
     */
    private Stage getStageFromType(Types type, String value) {
        for (StagedType stagedDimension : getStagedTypes(type)) {
            if (stagedDimension.getValue().equalsIgnoreCase(value)) {
                return this;
            }
        }

        return null;
    }
}
