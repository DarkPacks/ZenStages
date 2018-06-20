package uk.artdude.zenstages.stager.wrappers;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.zenstages.wrappers.StagedType")
public class StagedType {
    private String value;
    private String subValue;
    private Types type;

    public StagedType(String stagedString, Types type, String subValue) {
        this.value = stagedString;
        this.type = type;
        this.subValue = subValue;
    }

    @ZenMethod("value")
    public String getValue() {
        return this.value;
    }

    @ZenMethod("subValue")
    public String getSubValue() {
        return this.subValue;
    }

    @ZenMethod("typeName")
    public String getTypeName() {
        return type.name();
    }

    public Types getType() {
        return type;
    }
}