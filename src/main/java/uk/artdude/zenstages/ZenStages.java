package uk.artdude.zenstages;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import uk.artdude.zenstages.common.util.References;

@Mod(modid = References.modID, name = References.modName, version = References.modVersion,
    acceptedMinecraftVersions = References.mcVersion,
    dependencies =
        "required-after:crafttweaker@[1.12-4.1.8.470,];" +
                "after:itemstages@[2.0.35,];after:gamestages@[2.0.91,];" +
                "after:dimstages@[2.0.20,];after:tinkerstages@[2.0.15,];" +
                "after:mobstages@[2.0.8,];after:recipestages@[1.1.1,];" +
                "after:multiblockstages@[1.1.1,];")
public class ZenStages {
    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Post Completed!");
    }
}
