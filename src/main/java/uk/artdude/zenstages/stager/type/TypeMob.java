package uk.artdude.zenstages.stager.type;

import net.darkhax.mobstages.compat.crt.MobStagesCrT;

public class TypeMob extends TypeBase<String> {
    private Integer dimensionId;

    public TypeMob(String mobId) {
        super(mobId);
    }

    public TypeMob(String mobId, int dimensionId) {
        super(mobId);

        this.dimensionId = dimensionId;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    @Override
    public void build(String stageName) {
        if (dimensionId == null) {
            MobStagesCrT.addStage(stageName, getValue());
        } else {
            MobStagesCrT.addStage(stageName, getValue(), getDimensionId());
        }
    }

    @Override
    public void build(String[] stageNames) {
    }
}
