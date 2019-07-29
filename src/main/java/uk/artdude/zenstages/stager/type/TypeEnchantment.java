package uk.artdude.zenstages.stager.type;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.enchantments.IEnchantment;
import net.darkhax.itemstages.compat.crt.ItemStagesCrT;

public class TypeEnchantment extends TypeBase<IEnchantment> {
    public TypeEnchantment(IEnchantment enchantment) {
        super(enchantment);
    }

    @Override
    public void build(String stageName) {
        try {
            ItemStagesCrT.stageEnchantByLevel(stageName, getValue());
        } catch (Exception err) {
            CraftTweakerAPI.logError(String.format("[Stage] Failed to stage %s to stage %s.", this.getValue().displayName(), stageName), err);
        }
    }

    @Override
    public void build(String[] stageNames) {

    }

    @Override
    public void buildRecipe(String stageName) {

    }


}
