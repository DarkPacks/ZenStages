package uk.artdude.zenstages;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import uk.artdude.zenstages.common.util.References;

@Mod(modid = References.modID, name = References.modName, version = References.modVersion,
        acceptedMinecraftVersions = References.mcVersion, dependencies = References.dependencies,
        updateJSON = References.updateChangelog)
public class ZenStages {
    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Post Loading Completed!");
    }
}
