package uk.artdude.zenstages.stager.type;

import com.blamejared.recipestages.handlers.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.darkhax.bookshelf.util.ModUtils;
import net.darkhax.itemstages.compat.crt.ActionAddItemRestriction;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import uk.artdude.zenstages.stager.ZenStager;

public class TypeMod extends TypeBase<String> {
    public TypeMod(String modId) {
        super(modId);
    }

    @Override
    public void build(String stageName) {
        for (Item item : ModUtils.getSortedEntries(ForgeRegistries.ITEMS).get(getValue())) {
            if (item == null || item == Items.AIR) {
                continue;
            }
            IItemStack itemStack = new MCItemStack(new ItemStack(item));

            boolean stagedOverride = ZenStager.stagingOverrides.contains(itemStack);
            if (stagedOverride) {
                continue;
            }
            CraftTweakerAPI.apply(new ActionAddItemRestriction(stageName, item));
            Recipes.setRecipeStage(stageName, itemStack);
        }
    }

    @Override
    public void build(String[] stageNames) {
    }
}
