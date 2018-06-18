package uk.artdude.zenstages.stager;

import stanhebben.zenscript.annotations.ZenClass;

enum Types {
    // Standard Types
    CONTAINER(false),
    DIMENSION,
    MOB,
    PACKAGE(false),
    RECIPE_NAME,
    RECIPE_REGEX,

    // Mod Specific Types
    IE_MULTIBLOCK,
    TINKER_MATERIAL,
    TINKER_MODIFIER,
    TINKER_TOOL;

    private boolean dupeCheck = true;

    Types() {}
    Types(boolean dupeCheck) {
        this.dupeCheck = dupeCheck;
    }

    public boolean canDupeCheck() {
        return dupeCheck;
    }
}

@ZenClass("mods.zenstages.StagedType")
public class StagedType {
    private String value;
    private String subValue;
    private Types type;

    StagedType(String stagedString, Types type, String subValue) {
        this.value = stagedString;
        this.type = type;
        this.subValue = subValue;
    }

    public String getValue() {
        return value;
    }

    String getSubValue() {
        return subValue;
    }

    Types getType() {
        return type;
    }
}