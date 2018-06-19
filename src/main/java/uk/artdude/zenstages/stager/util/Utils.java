package uk.artdude.zenstages.stager.util;

import crafttweaker.annotations.ZenRegister;
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
        return String.format("stage%s_%s", StringUtils.capitalize(stage.getStage()), UUID.randomUUID().toString());
    }

    @ZenMethod
    public static String genRecipeName(Stage stage, String name) {
        return String.format("stage%s_%s", StringUtils.capitalize(stage.getStage()), name);
    }
}
