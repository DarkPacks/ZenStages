package uk.artdude.zenstages.stager.wrappers;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.zenstages.StagedType")
public class StagedType {
    private String value;
    private String subValue;
    private Types type;

    public StagedType(String stagedString, Types type, String subValue) {
        this.value = stagedString;
        this.type = type;
        this.subValue = subValue;
    }

    public String getValue() {
        return value;
    }

    public String getSubValue() {
        return subValue;
    }

    public Types getType() {
        return type;
    }
}