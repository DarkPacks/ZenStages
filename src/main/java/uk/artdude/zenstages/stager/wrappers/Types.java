package uk.artdude.zenstages.stager.wrappers;

public enum Types {
    // Standard Types
    CONTAINER(false),
    DIMENSION,
    MOB,
    PACKAGE(false),
    RECIPE_NAME,
    RECIPE_REGEX,

    // Mod Specific Types
    IE_MULTIBLOCK,
    TINKER_MATERIAL,
    TINKER_MODIFIER,
    TINKER_TOOL;

    private boolean dupeCheck = true;

    Types() {}
    Types(boolean dupeCheck) {
        this.dupeCheck = dupeCheck;
    }

    public boolean canDupeCheck() {
        return dupeCheck;
    }
}
