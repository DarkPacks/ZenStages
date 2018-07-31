package uk.artdude.zenstages.stager.type;

import com.teamacronymcoders.multiblockstages.immersiveengineering.IEMultiBlockStages;
import uk.artdude.zenstages.stager.type.enums.MultiBlockType;

public class TypeMultiBlock extends TypeBase<String> {
    private MultiBlockType multiBlockType;

    public TypeMultiBlock(MultiBlockType multiBlockType, String multiblockName) {
        super(multiblockName);

        this.multiBlockType = multiBlockType;
    }

    @Override
    public void build(String stageName) {
        switch (multiBlockType) {
            case IE:
                IEMultiBlockStages.addStage(stageName, getValue());
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
