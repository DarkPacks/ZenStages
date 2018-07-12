package uk.artdude.zenstages.stager;

import crafttweaker.CraftTweakerAPI;
import uk.artdude.zenstages.common.util.Helper;
import uk.artdude.zenstages.stager.type.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DupeChecker {
    private static HashMap<Class<? extends TypeBase>, Map<?, List<String>>> allowedClasses = new HashMap<>();

    static {
        allowedClasses.put(TypeDimension.class, new HashMap<>());
        allowedClasses.put(TypeIngredient.class, new HashMap<>());
        allowedClasses.put(TypeMob.class, new HashMap<>());
        allowedClasses.put(TypeMod.class, new HashMap<>());
        allowedClasses.put(TypeOre.class, new HashMap<>());
        allowedClasses.put(TypeRecipeName.class, new HashMap<>());
        allowedClasses.put(TypeTinker.class, new HashMap<>());
    }

    /**
     * Checks for duplications via the valid Types which support Dupe Checking.
     */
    static void checkForDupes(Map<String, Stage> stageMap) {
        for (Stage currStage : stageMap.values()) {
            for (Stage checkStage : stageMap.values()) {
                if (currStage.getStage().equals(checkStage.getStage())) {
                    // TODO: Look into adding support for checking for dupes on a single Stage.
                    continue;
                }
                allowedClasses.forEach((clazz, entries) -> {
                    Helper.getDuplicates(entries, currStage.getStage(), currStage.filterEntries(clazz), checkStage.filterEntries(clazz));
                });
            }
        }
    }

    /**
     * Log the duplications form the Types.
     */
    static void logDupes() {
        allowedClasses.forEach((typeClass, typeEntries) -> {
            typeEntries.forEach((value, stages) -> {
                CraftTweakerAPI.logError(String.format("[Stage Duplicate] Found a duplicate %s stage for `%s` for stages %s", typeClass.getSimpleName(), value, stages));
            });
        });
    }
}
