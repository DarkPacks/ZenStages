package uk.artdude.zenstages.stager.type;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import net.darkhax.itemstages.compat.crt.ItemStagesCrT;

public class TypeEnchantmentDefinition extends TypeBase<IEnchantmentDefinition> {
    public TypeEnchantmentDefinition(IEnchantmentDefinition enchantmentDefinition) {
        super(enchantmentDefinition);
    }

    @Override
    public void build(String stageName) {
        try {
            ItemStagesCrT.stageEnchant(stageName, getValue());
        } catch (Exception err) {
            CraftTweakerAPI.logError(String.format("[Stage] Failed to stage %s to stage %s.", this.getValue().getName(), stageName), err);
        }
    }

    @Override
    public void build(String[] stageNames) {

    }

    @Override
    public void buildRecipe(String stageName) {

    }


}
