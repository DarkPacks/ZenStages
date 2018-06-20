package uk.artdude.zenstages.stager.wrappers;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.zenstages.wrappers.StagedOre")
public class StagedOre {
    private IIngredient blockToHide;
    private IItemStack blockToShow = null;
    private boolean isNonDefaulting;

    public StagedOre(IIngredient blockToHide) {
        this.blockToHide = blockToHide;
    }

    public StagedOre(IIngredient blockToHide, IItemStack blockToShow) {
        this.blockToHide = blockToHide;
        this.blockToShow = blockToShow;
    }

    public void setNonDefaulting(boolean nonDefaulting) {
        isNonDefaulting = nonDefaulting;
    }

    @ZenMethod("blockToHide")
    public IIngredient getBlockToHide() {
        return blockToHide;
    }

    @ZenMethod("blockToShow")
    public IItemStack getBlockToShow() {
        return blockToShow;
    }

    @ZenMethod("nonDefaulting")
    public boolean isNonDefaulting() {
        return isNonDefaulting;
    }
}
