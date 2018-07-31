package uk.artdude.zenstages.stager;

import com.blamejared.recipestages.handlers.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import uk.artdude.zenstages.common.util.Helper;
import uk.artdude.zenstages.stager.type.*;

import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@ZenClass("mods.zenstages.ZenStager")
public class ZenStager {
    private static Map<String, Stage> stageMap = new HashMap<>();
    private static List<TypeCustom> customTypes = new ArrayList<>();

    /*
        List used to Store Overrides set when making an Override Type. This List is used on the Builder for the Mod Type
        to prevent staging of that IIngredient.
    */
    public static List<IIngredient> stagingOverrides = new ArrayList<>();

    @ZenMethod
    public static Stage initStage(String name) {
        if (name == null || name.length() < 1) {
            CraftTweakerAPI.logError(String.format("[ZenStager] Name %s cannot be null or less than one!", name));

            return null;
        }
        String safeName = Helper.cleanName(name);

        if (stageMap.containsKey(safeName)) {
            CraftTweakerAPI.logError(String.format("[ZenStager] Failed to create stage %s due to name already existing.", safeName));

            return null;
        }
        Stage stage = new Stage(safeName);
        stageMap.put(safeName, stage);

        return stage;
    }

    /**
     * Custom Types are used when the designer wants to use Custom Types for things such as events using
     * CraftTweaker/ZenEvents to name one example.
     * <p>
     * This is a new idea and would be built upon after time as needs change for the idea.
     */
    @ZenMethod
    public static TypeCustom initCustomType(String name, String value) {
        return createCustomType(name, value);
    }

    @ZenMethod
    public static TypeCustom initCustomType(String name, String[] value) {
        return createCustomType(name, value);
    }

    @ZenMethod
    public static TypeCustom initCustomType(String name, int value) {
        return createCustomType(name, value);
    }

    @ZenMethod
    public static TypeCustom initCustomType(String name, int[] value) {
        return createCustomType(name, value);
    }

    @ZenMethod
    public static TypeCustom initCustomType(String name, IIngredient value) {
        return createCustomType(name, value);
    }

    @ZenMethod
    public static TypeCustom initCustomType(String name, IIngredient[] value) {
        return createCustomType(name, value);
    }

    @ZenMethod
    public static void addPackage(String packageName, Stage[] stages) {
        for (Stage stage : stages) {
            stage.addPackage(packageName);
        }
    }

    @ZenMethod
    public static void addContainer(String containerName, Stage[] stages) {
        for (Stage stage : stages) {
            stage.addContainer(containerName);
        }
    }

    @ZenMethod
    public TypeCustom getCustomType(String name) {
        for (TypeCustom customType : customTypes) {
            if (customType.getName().endsWith(name)) {
                return customType;
            }
        }

        return null;
    }

    @ZenMethod
    public static Map<String, Stage> getStageMap() {
        return stageMap;
    }

    @ZenMethod
    public static Stage getStage(String stage) {
        return stageMap.get(stage.toLowerCase());
    }

    @ZenMethod
    public static Stage getIngredientStage(IIngredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        for (Stage stage : stageMap.values()) {
            if (stage.isIngredientStaged(ingredient)) {
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
    public static Stage getRecipeNameStage(String recipeName) {
        for (Stage stage : stageMap.values()) {
            if (stage.getRecipeNameStage(recipeName) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    public static Stage getDimensionStage(int dimension) {
        for (Stage stage : stageMap.values()) {
            if (stage.getDimensionStage(dimension) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    public static List<Stage> getContainerStages(String container) {
        Map<String, List<String>> stagesForType = new HashMap<>();
        getStagesForType(TypeContainer.class, stagesForType);

        List<String> stages = stagesForType.get(container);
        if (stages == null) {
            return null;
        }

        return stages.stream().map(t -> stageMap.get(t)).collect(Collectors.toList());
    }

    @ZenMethod
    public static List<Stage> getPackageStages(String packageName) {
        Map<String, List<String>> stagesForType = new HashMap<>();
        getStagesForType(TypePackage.class, stagesForType);

        List<String> stages = stagesForType.get(packageName);
        if (stages == null) {
            return null;
        }

        return stages.stream().map(t -> stageMap.get(t)).collect(Collectors.toList());
    }

    @ZenMethod
    public static Stage getMobStage(String mobName) {
        for (Stage stage : stageMap.values()) {
            if (stage.getMobStage(mobName) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    public static Stage getTiCMaterialStage(String material) {
        for (Stage stage : stageMap.values()) {
            if (stage.getTiCMaterialStage(material) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    public static Stage getTiCToolStage(String toolName) {
        for (Stage stage : stageMap.values()) {
            if (stage.getTiCToolStage(toolName) != null) {
                return stage;
            }
        }

        return null;
    }

    @ZenMethod
    public static Stage getCustomStage(String slug, String value) {
        return getCustomTypeStage(slug, value);
    }

    @ZenMethod
    public static Stage getCustomStage(String slug, int value) {
        return getCustomTypeStage(slug, value);
    }

    @ZenMethod
    public static Stage getCustomStage(String slug, IIngredient value) {
        return getCustomTypeStage(slug, value);
    }

    @ZenMethod
    public static boolean isStaged(String slug, String value) {
        switch (slug.toLowerCase()) {
            case "container":
                return getStageForType(TypeContainer.class, value) != null;
            case "mod":
                return getStageForType(TypeMod.class, value) != null;
            case "multiblock":
                return getStageForType(TypeMultiBlock.class, value) != null;
            case "package":
                return getStageForType(TypePackage.class, value) != null;
            case "recipename":
                return getStageForType(TypeRecipeName.class, value) != null;
            case "tinker":
                return getStageForType(TypeTinker.class, value) != null;
            case "mob":
                return getStageForType(TypeMob.class, value) != null;
        }

        return hasCustomType(slug, value);
    }

    @ZenMethod
    public static boolean isStaged(String slug, int value) {
        switch (slug.toLowerCase()) {
            case "dimension":
                return getStageForType(TypeDimension.class, value) != null;
        }

        return hasCustomType(slug, value);
    }

    @ZenMethod
    public static boolean isStaged(String slug, IIngredient value) {
        switch (slug.toLowerCase()) {
            case "ingredient":
                return getStageForType(TypeIngredient.class, value) != null;
            case "ore":
                return getStageForType(TypeOre.class, value) != null;
        }

        return hasCustomType(slug, value);
    }

    @ZenMethod
    public static void checkConflicts() {
        CraftTweakerAPI.logInfo("[Stage Duplicate] Starting duplicate checks....");

        DupeChecker.checkForDupes(stageMap);
        DupeChecker.logDupes();

        CraftTweakerAPI.logInfo("[Stage Duplicate] Completed duplicate checks!");
    }

    @ZenMethod
    public static void buildAll() {
        CraftTweakerAPI.logInfo(String.format("[ZenStager] Starting build for %s stages...", stageMap.size()));

        Map<String, Map<String, List<String>>> stagedTypes = new HashMap<>();
        stagedTypes.put("CONTAINER", new HashMap<>());
        stagedTypes.put("PACKAGE", new HashMap<>());

        // Handle the staging of the Types.
        stageMap.forEach((s, stage) -> {
            getStagesForType(TypeContainer.class, stagedTypes.get("CONTAINER"), stage);
            getStagesForType(TypePackage.class, stagedTypes.get("PACKAGE"), stage);

            stage.build();
        });

        // Setting of the Recipe Regex's needs to happen after the fact of items being staged etc...
        CraftTweakerAPI.logInfo("[ZenStager] Starting building the recipe stage setting...");
        stageMap.forEach((s, stage) -> stage.buildRecipe());
        CraftTweakerAPI.logInfo("[ZenStager] Completed building the recipe stage setting!");

        // Stage the Containers based on the built stage mapping.
        Map<String, List<String>> stagedContainers = stagedTypes.get("CONTAINER");
        stagedContainers.forEach((container, stages) -> {
            String[] forStages = new String[]{};
            forStages = stages.toArray(forStages);

            Recipes.setContainerStage(container, forStages);
        });

        // Stage the Packages based on the built stage mapping.
        Map<String, List<String>> stagedPackages = stagedTypes.get("PACKAGE");
        stagedPackages.forEach((packageName, stages) -> {
            String[] forStages = new String[]{};
            forStages = stages.toArray(forStages);

            Recipes.setPackageStage(packageName, forStages);
        });

        CraftTweakerAPI.logInfo(String.format("[ZenStager] Completed build for %s stages!", stageMap.size()));
    }

    /**
     * Create a Custom Type based on the Args provided.
     */
    private static <T> TypeCustom createCustomType(String name, T value) {
        String safeName = Helper.cleanName(name);
        CraftTweakerAPI.logInfo(String.format("[ZenStager] Custom Type has been created with the name `%s` use this when accessing a type via the scripts!", safeName));
        TypeCustom<T> customType = new TypeCustom<>(safeName, value);
        customTypes.add(customType);

        return customType;
    }

    /**
     * Get the Stage in which a Custom Type was Staged too.
     */
    @SuppressWarnings("unchecked")
    private static <T> Stage getCustomTypeStage(String slug, T value) {
        List<TypeCustom> customTypes = filterCustomByName(slug);
        for (TypeCustom customType : customTypes) {
            if (customType != null && customType.getStage() != null) {
                if (customType.getValue() instanceof IIngredient[] && Arrays.asList((T[]) customType.getValue()).contains(value)) {
                    return customType.getStage();
                } else if (customType.getValue().equals(value)) {
                    return customType.getStage();
                }
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> boolean hasCustomType(String slug, T value) {
        List<TypeCustom> customTypes = filterCustomByName(slug);
        for (TypeCustom customType : customTypes) {
            if (customType.getValue() instanceof IIngredient[] && Arrays.asList((T[]) customType.getValue()).contains(value)) {
                return true;
            } else if (customType.getValue().equals(value)) {
                return true;
            }
        }

        return false;
    }

    static List<TypeCustom> filterCustomByStage(Stage stage, String slug) {
        return customTypes.stream()
                .filter(t -> t.getStage() == stage)
                .collect(Collectors.toList()).stream()
                .filter(k -> k.getName().equalsIgnoreCase(slug))
                .collect(Collectors.toList());
    }

    private static List<TypeCustom> filterCustomByName(String name) {
        return customTypes.stream().filter(t -> t.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    /**
     * Update a passed Map of the staged types to another Map listing which contains that type
     * and a String List of the stages which are for that Staged Type.
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    private static <T extends TypeBase> void getStagesForType(Class<T> clazz, Map<String, List<String>> stringListMap) {
        stageMap.forEach((s, stage) -> getStagesForType(clazz, stringListMap, stage));
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private static <T extends TypeBase> void getStagesForType(Class<T> clazz, Map<String, List<String>> stringListMap, Stage stage) {
        for (T stagedType : stage.filterEntries(clazz)) {
            if (stringListMap.containsKey(stagedType.getValue())) {
                List<String> currentStages = stringListMap.get(stagedType.getValue());
                currentStages.add(stage.getStage());
            } else {
                List<String> stages = new ArrayList<>();
                stages.add(stage.getStage());
                stringListMap.put(stagedType.getValue().toString(), stages);
            }
        }
    }

    /**
     * Return the Stage in which the value is set in. Otherwise returns Null.
     */
    private static <T extends TypeBase, J> Stage getStageForType(Class<T> clazz, J value) {
        for (Stage stage : stageMap.values()) {
            if (stage.getStage(clazz, value) != null) {
                return stage;
            }
        }

        return null;
    }
}
