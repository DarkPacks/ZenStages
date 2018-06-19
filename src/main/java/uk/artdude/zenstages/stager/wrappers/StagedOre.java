package uk.artdude.zenstages.stager.wrappers;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;

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

    public IIngredient getBlockToHide() {
        return blockToHide;
    }

    public IItemStack getBlockToShow() {
        return blockToShow;
    }

    public boolean isNonDefaulting() {
        return isNonDefaulting;
    }
}
