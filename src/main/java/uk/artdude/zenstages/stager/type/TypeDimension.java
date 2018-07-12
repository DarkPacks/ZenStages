package uk.artdude.zenstages.stager.type;

import net.darkhax.dimstages.compat.crt.DimensionStagesCrT;

public class TypeDimension extends TypeBase<Integer> {
    public TypeDimension(int dimensionId) {
        super(dimensionId);
    }

    @Override
    public void build(String stageName) {
        DimensionStagesCrT.addDimensionStage(stageName, getValue());
    }

    @Override
    public void build(String[] stageNames) {
    }
}
