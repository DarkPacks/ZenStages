package uk.artdude.zenstages.stager.type;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.darkhax.orestages.compat.crt.OreTiersCrT;

/**
 * OreStages Type
 * <p>
 * Ore Stages is the mod used here to hide "Ores" (Blocks) with other ones until the player has got the Stage
 * needed to see said block. This requires for minimum the block to hide which the mod will default to CobbleStone
 * to show to the user. Otherwise you can given an additional parameter to custom set the block to show to the user.
 */
public class TypeOre extends TypeBase<IIngredient> {
    private IItemStack blockToShow = null;
    private boolean isNonDefaulting;

    public TypeOre(IIngredient blockToHide) {
        this(blockToHide, null);
    }

    public TypeOre(IIngredient ingredient, IItemStack blockToShow) {
        super(ingredient);

        this.blockToShow = blockToShow;
    }

    private IIngredient getBlockToHide() {
        return this.getValue();
    }

    private IItemStack getBlockToShow() {
        return blockToShow;
    }

    private boolean isNonDefaulting() {
        return isNonDefaulting;
    }

    public void setNonDefaulting(boolean nonDefaulting) {
        isNonDefaulting = nonDefaulting;
    }

    @Override
    public void build(String stageName) {
        if (isNonDefaulting()) {
            if (getBlockToShow() != null) {
                OreTiersCrT.addNonDefaultingReplacement(stageName, getBlockToHide(), getBlockToShow());
            } else {
                OreTiersCrT.addNonDefaultingReplacement(stageName, getBlockToHide());
            }
        } else {
            if (getBlockToShow() != null) {
                OreTiersCrT.addReplacement(stageName, getBlockToHide(), getBlockToShow());
            } else {
                OreTiersCrT.addReplacement(stageName, getBlockToHide());
            }
        }
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
