package uk.artdude.zenstages.stager.util;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import uk.artdude.zenstages.stager.Stage;

import java.util.UUID;

@ZenRegister
@ZenClass("mods.zenstages.Utils")
public class Utils {
    private static final String recipeString = "stage_%s_%s";
    public static final String craftTweakerRegex = String.format("%s:%s", "crafttweaker", recipeString);

    @Deprecated
    @ZenMethod
    public static String genRecipeName() {
        CraftTweakerAPI.logError("genRecipeName() is now deprecated. Please use one of the other alternatives. Will be removed in 0.5.x");

        return UUID.randomUUID().toString();
    }

    @Deprecated
    @ZenMethod
    public static String genRecipeName(Stage stage) {
        CraftTweakerAPI.logError("genRecipeName(Stage stage) is now deprecated. Please use one of the other alternatives. Will be removed in 0.5.x");

        return String.format(recipeString, formatStage(stage), UUID.randomUUID().toString());
    }

    @ZenMethod
    public static String genRecipeName(Stage stage, String name) {
        return String.format(recipeString, formatStage(stage), name);
    }

    @ZenMethod
    public static String genRecipeName(Stage stage, IItemStack itemStack) {
        return String.format(recipeString, formatStage(stage), formatItem(itemStack));
    }

    @ZenMethod
    public static String genRecipeName(Stage stage, IItemStack itemStack, String name) {
        return String.format(recipeString + "_%s", formatStage(stage), formatItem(itemStack), name);
    }

    public static String formatStage(Stage stage) {
        return stage.getStage()
                .replaceAll("\\s+", "_");
    }

    private static String formatItem(IItemStack itemStack) {
        return itemStack.getDisplayName()
                .replaceAll("\\s+", "_")
                .replaceAll("[()]", "")
                .toLowerCase();
    }
}
