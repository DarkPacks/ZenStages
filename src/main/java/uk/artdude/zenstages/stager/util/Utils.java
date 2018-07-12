package uk.artdude.zenstages.stager.util;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import org.apache.commons.lang3.StringUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import uk.artdude.zenstages.stager.Stage;

import java.util.UUID;

@ZenRegister
@ZenClass("mods.zenstages.Utils")
public class Utils {
    @ZenMethod
    public static String genRecipeName() {
        return UUID.randomUUID().toString();
    }

    @ZenMethod
    public static String genRecipeName(Stage stage) {
        return String.format("stage%s_%s", formatStage(stage), UUID.randomUUID().toString());
    }

    @ZenMethod
    public static String genRecipeName(Stage stage, String name) {
        return String.format("stage%s_%s", formatStage(stage), name);
    }

    @ZenMethod
    public static String genRecipeName(Stage stage, IItemStack itemStack) {
        return String.format("stage%s_%s", formatStage(stage), formatItem(itemStack));
    }

    @ZenMethod
    public static String genRecipeName(Stage stage, IItemStack itemStack, String name) {
        return String.format("stage%s_%s_%s", formatStage(stage), formatItem(itemStack), name);
    }

    private static String formatStage(Stage stage) {
        return StringUtils.capitalize(stage.getStage());
    }

    private static String formatItem(IItemStack itemStack) {
        return itemStack.getDisplayName()
                .replaceAll("\\s+", "_")
                .replaceAll("[()]", "")
                .toLowerCase();
    }
}
