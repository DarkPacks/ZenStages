package uk.artdude.zenstages.common.command;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import uk.artdude.zenstages.ZenStages;
import uk.artdude.zenstages.stager.ZenStager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Commands extends CommandBase {
    private final String[] COMMANDS = new String[]{
            "stages",
            "giveAll",
            "removeAll"
    };

    @Override
    @Nonnull
    public String getName() {
        return "zenstages";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/zenstages <action> [arguments...]";
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos blockPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, COMMANDS);
        }

        return Collections.emptyList();
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("§cNot enough arguments."));
        } else {
            String identifier = args[0];
            List<String> stages = new ArrayList<>();
            ZenStager.getStageMap().forEach((name, stage) -> stages.add(stage.getStage()));

            try {
                EntityPlayerMP playerMP = getCommandSenderAsPlayer(sender);
                switch (identifier.toLowerCase()) {
                    case "giveall":
                        stages.forEach(stage -> GameStageHelper.addStage(playerMP, stage));
                        GameStageHelper.syncPlayer(playerMP);

                        sender.sendMessage(new TextComponentTranslation("commands.zenstages.giveAll.success", Integer.toString(stages.size()), sender.getName()));
                        break;
                    case "removeall":
                        stages.forEach(stage -> GameStageHelper.removeStage(playerMP, stage));
                        GameStageHelper.syncPlayer(playerMP);

                        sender.sendMessage(new TextComponentTranslation("commands.zenstages.removeAll.success", Integer.toString(stages.size()), sender.getName()));
                        break;
                    case "stages":
                        sender.sendMessage(new TextComponentTranslation("commands.zenstages.stages.success", Arrays.toString(stages.toArray())));
                        break;
                    default:
                        throw new CommandException("Invalid Option!");
                }
            } catch (Exception err) {
                ZenStages.logger.error(String.format("Failed to give all stages to player%s", sender.getName()), err);
            }
        }
    }
}
