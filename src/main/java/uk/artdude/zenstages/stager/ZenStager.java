package uk.artdude.zenstages.stager;

import com.blamejared.recipestages.handlers.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import uk.artdude.zenstages.common.util.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ZenRegister
@ZenClass("mods.zenstages.ZenStager")
public class ZenStager {
    private static Map<String, Stage> stageMap = new HashMap<>();

    @ZenMethod
    public static Stage initStage(String name) {
        if (name == null || name.length() < 1) {
            CraftTweakerAPI.logError(String.format("[ZenStager] Name %s cannot be null or less than one!", name));

            return null;
        }
        name = name.toLowerCase();
        if (stageMap.containsKey(name)) {
            CraftTweakerAPI.logError(String.format("[ZenStager] Failed to create builder for %s due to name already existing.", name));

            return null;
        }

        Stage stage = new Stage(name);
        stageMap.put(name, stage);

        return stage;
    }

    @ZenMethod
    public void addPackage(String packageName, Stage[] stages) {
        for (Stage stage : stages) {
            stage.addPackage(packageName);
        }
    }

    @ZenMethod
    public void addContainer(String containerName, Stage[] stages) {
        for (Stage stage : stages) {
            stage.addContainer(containerName);
        }
    }

    @ZenMethod
    public static Map<String, Stage> getStageMap() {
        return stageMap;
    }

    @ZenMethod
    public static Stage getStage(String stage) {
        return stageMap.get(stage);
    }

    @ZenMethod
    public static Stage getIngredientStage(IIngredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        for (Stage stage : stageMap.values()) {
            if (stage.isStaged(ingredient)) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    public static Stage getLiquidStage(ILiquidStack liquidStack) {
        return getIngredientStage(liquidStack);
    }

    @ZenMethod
    public static Map<Stage, ILiquidStack[]> getStagedLiquids() {
        Map<Stage, ILiquidStack[]> liquidStacks = new HashMap<>();
        for (Stage stage : stageMap.values()) {
            List<ILiquidStack> liquidStackList = stage.getStagedLiquids();
            if (liquidStackList.size() < 1) {
                continue;
            }
            ILiquidStack[] fluidStacks = new ILiquidStack[]{};
            fluidStacks = liquidStackList.toArray(fluidStacks);

            liquidStacks.put(stage, fluidStacks);
        }

        return liquidStacks;
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public static Stage getRecipeNameStage(String recipeName) {
        for (Stage stage : stageMap.values()) {
            if (stage.getRecipeNameStage(recipeName) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    @Method(modid = "dimstages")
    public static Stage getDimensionStage(int dimension) {
        for (Stage stage : stageMap.values()) {
            if (stage.getDimensionStage(dimension) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public static List<Stage> getContainerStages(String container) {
        return getTypeStages(Types.CONTAINER, container);
    }

    @ZenMethod
    @Method(modid = "recipestages")
    public static List<Stage> getPackageStages(String packageName) {
        return getTypeStages(Types.PACKAGE, packageName);
    }

    @ZenMethod
    @Method(modid = "mobstages")
    public static Stage getMobStage(String mobName) {
        for (Stage stage : stageMap.values()) {
            if (stage.getMobStage(mobName) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    @Method(modid = "tinkerstages")
    public static Stage getTiCMaterialStage(String material) {
        for (Stage stage : stageMap.values()) {
            if (stage.getTiCMaterialStage(material) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    public static boolean isStaged(IIngredient ingredient) {
        return getIngredientStage(ingredient) != null;
    }

    @ZenMethod
    public static boolean isStaged(String string) {
        return getTypeStage(string) != null;
    }

    @ZenMethod
    @Method(modid = "dimstages")
    public static boolean isStaged(int dimension) {
        return getDimensionStage(dimension) != null;
    }

    @ZenMethod
    public static void checkConflicts() {
        CraftTweakerAPI.logInfo("[Stage Duplicate] Starting duplicate checks....");

        /*
          Handle checking for duplicates on Types which are supported for duplication checking.
         */
        for (Types type : Types.values()) {
            if (!type.canDupeCheck()) {
                continue;
            }
            Map<String, List<String>> duplicateTypes = new HashMap<>();
            for (Stage currStage : stageMap.values()) {
                for (Stage checkStage : stageMap.values()) {
                    if (currStage.getStage().equals(checkStage.getStage())) {
                        continue;
                    }
                    Helper.getDuplicates(duplicateTypes, currStage.getStage(), currStage.getStagedTypes(type), checkStage.getStagedTypes(type));
                }
            }
            duplicateTypes.forEach((dupedType, stages) -> CraftTweakerAPI.logError(String.format("[Stage Duplicate] Found a duplicate %s stage for `%s` for stages %s", type.name(), dupedType, stages)));
        }

        /*
            Handle duplication checking for Ingredient.
         */
        Map<IIngredient, List<String>> duplicateIngredient = new HashMap<>();
        for (Stage currStage : stageMap.values()) {
            for (Stage checkStage : stageMap.values()) {
                if (currStage.getStage().equals(checkStage.getStage())) {
                    continue;
                }
                Helper.getIngredientDuplicates(duplicateIngredient, currStage.getStage(), currStage.getStagedIngredients(), checkStage.getStagedIngredients());
            }
        }
        duplicateIngredient.forEach((ingredient, stages) -> CraftTweakerAPI.logError(String.format("[Stage Duplicate] Found a duplicate ingredient stage for `%s` for stages %s", ingredient, stages)));

        CraftTweakerAPI.logInfo("[Stage Duplicate] Completed duplicate checks!");
    }

    @ZenMethod
    public static void buildAll() {
        Map<Types, Map<String, List<String>>> stagedTypes = new HashMap<>();
        stagedTypes.put(Types.CONTAINER, new HashMap<>());
        stagedTypes.put(Types.PACKAGE, new HashMap<>());

        stageMap.forEach((s, stage) -> {
            getStagedTypes(Types.CONTAINER, stagedTypes.get(Types.CONTAINER), stage);
            getStagedTypes(Types.PACKAGE, stagedTypes.get(Types.PACKAGE), stage);

            stage.build();
        });

        // Stage the Containers based on the built stage mapping.
        Map<String, List<String>> stagedContainers = stagedTypes.get(Types.CONTAINER);
        stagedContainers.forEach((container, stages) -> {
            String[] forStages = new String[]{};
            forStages = stages.toArray(forStages);

            Recipes.setContainerStage(container, forStages);
        });

        // Stage the Packages based on the built stage mapping.
        Map<String, List<String>> stagedPackages = stagedTypes.get(Types.PACKAGE);
        stagedPackages.forEach((packageName, stages) -> {
            String[] forStages = new String[]{};
            forStages = stages.toArray(forStages);

            Recipes.setPackageStage(packageName, forStages);
        });
    }

    /**
     * Update a passed Map of the staged types to another Map listing which contains that type
     * and a String List of the stages which are for that Staged Type.
     */
    private static void getStagedTypes(Types type, Map<String, List<String>> stringListMap, Stage stage) {
        for (StagedType stagedType : stage.getStagedTypes(type)) {
            if (stringListMap.containsKey(stagedType.getValue())) {
                List<String> currentStages = stringListMap.get(stagedType.getValue());
                currentStages.add(stage.getStage());
            } else {
                List<String> stages = new ArrayList<>();
                stages.add(stage.getStage());
                stringListMap.put(stagedType.getValue(), stages);
            }
        }
    }

    private static String getTypeStage(String value) {
        if (value == null || value.length() < 1) {
            return null;
        }

        for (Stage stage : stageMap.values()) {
            if (stage.isStaged(value)) {
                return stage.getStage();
            }
        }

        return null;
    }

    private static List<Stage> getTypeStages(Types type, String value) {
        List<Stage> stages = new ArrayList<>();
        if (value == null || value.length() < 1) {
            return null;
        }

        for (Stage stage : stageMap.values()) {
            List<StagedType> stagedTypes = stage.getStagedTypes(type);
            for (StagedType stagedType : stagedTypes) {
                if (stagedType.getValue().equalsIgnoreCase(value)) {
                    stages.add(stage);
                }
            }
        }

        return stages;
    }
}
