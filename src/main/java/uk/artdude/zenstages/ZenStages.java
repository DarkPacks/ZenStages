package uk.artdude.zenstages;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import uk.artdude.zenstages.common.command.Commands;
import uk.artdude.zenstages.common.util.References;

@Mod(modid = References.modID, name = References.modName, version = References.modVersion,
        acceptedMinecraftVersions = References.mcVersion, dependencies = References.dependencies,
        updateJSON = References.updateChangelog)
public class ZenStages {
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void onSeverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new Commands());
    }
}
