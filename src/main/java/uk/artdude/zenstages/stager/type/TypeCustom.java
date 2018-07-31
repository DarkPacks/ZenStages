package uk.artdude.zenstages.stager.type;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import uk.artdude.zenstages.stager.Stage;

@ZenRegister
@ZenClass("mods.zenstages.type.CustomStageType")
public class TypeCustom<T> extends TypeBase<T> {
    private String name;
    private Stage stage = null;

    public TypeCustom(String name, T value) {
        super(value);

        this.name = name;
    }

    @ZenMethod
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @ZenGetter("stage")
    public Stage getStage() {
        return stage;
    }

    /**
     * Get the slug name which is used when a user wants to check is a thing is Staged to a Custom Type.
     */
    @ZenGetter("name")
    public String getName() {
        return name;
    }

    @ZenMethod
    public String getString() {
        return (String) this.getValue();
    }

    @ZenMethod
    public String[] getStringList() {
        return (String[]) this.getValue();
    }

    @ZenMethod
    public Integer getInt() {
        return (Integer) this.getValue();
    }

    @ZenMethod
    public Integer[] getIntList() {
        return (Integer[]) this.getValue();
    }

    @ZenMethod
    public IIngredient getIngredient() {
        return (IIngredient) this.getValue();
    }

    @ZenMethod
    public IIngredient[] getIngredientList() {
        return (IIngredient[]) this.getValue();
    }

    @Override
    public void build(String stageName) {
    }

    @Override
    public void build(String[] stageNames) {
    }

    @Override
    public void buildRecipe(String stageName) {
    }
}
