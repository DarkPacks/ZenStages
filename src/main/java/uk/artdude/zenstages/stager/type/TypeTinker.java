package uk.artdude.zenstages.stager.type;

import net.darkhax.tinkerstages.compat.crt.TinkerStagesCrT;
import uk.artdude.zenstages.stager.type.enums.TinkerType;

public class TypeTinker extends TypeBase<String> {
    private TinkerType tinkerType;

    public TypeTinker(TinkerType tinkerType, String value) {
        super(value);

        this.tinkerType = tinkerType;
    }

    @Override
    public void build(String stageName) {
        switch (tinkerType) {
            case MATERIAL:
                TinkerStagesCrT.addMaterialStage(stageName, getValue());
                break;
            case MODIFIER:
                TinkerStagesCrT.addToolTypeStage(stageName, getValue());
                break;
            case TOOL:
                TinkerStagesCrT.addToolTypeStage(stageName, getValue());
                break;
        }
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
