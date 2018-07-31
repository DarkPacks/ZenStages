package uk.artdude.zenstages.common.util;

import crafttweaker.api.item.IIngredient;
import uk.artdude.zenstages.stager.type.TypeBase;
import uk.artdude.zenstages.stager.type.TypeCustom;

import java.util.*;
import java.util.regex.Pattern;

public class Helper {
    @SuppressWarnings("unchecked")
    public static <T> void getDuplicates(Map<T, List<String>> duplicates, String stage, List<? extends TypeBase> currStage, List<? extends TypeBase> checkStage) {
        Collection<T> similar = new HashSet<>();
        Collection<T> checkSimilar = new HashSet<>();
        currStage.forEach((stagedIngredient) -> similar.add((T) stagedIngredient.getValue()));
        checkStage.forEach((stagedIngredient) -> checkSimilar.add((T) stagedIngredient.getValue()));
        similar.retainAll(checkSimilar);

        for (T s : similar) {
            List<String> list = new ArrayList<>();
            if (duplicates.containsKey(s)) {
                if (!duplicates.get(s).contains(stage)) {
                    list.addAll(duplicates.get(s));
                    list.add(stage);
                    duplicates.put(s, list);
                }
            } else {
                list.add(stage);
                duplicates.put(s, list);
            }
        }
    }

    /**
     * Get the CustomType from a List of Types which matches the value passed.
     */
    @SuppressWarnings("unchecked")
    public static <T> TypeCustom getCustomTypeByValue(List<TypeCustom> customTypes, T value) {
        for (TypeCustom customType : customTypes) {
            if (customType != null && customType.getStage() != null) {
                if (customType.getValue() instanceof IIngredient[] && Arrays.asList((T[]) customType.getValue()).contains(value)) {
                    return customType;
                } else if (customType.getValue().equals(value)) {
                    return customType;
                }
            }
        }

        return null;
    }

    public static String validateRecipeName(String name) {
        Pattern withoutModId = Pattern.compile("^([a-zA-Z_]*)$");
        if (withoutModId.matcher(name).matches()) {
            return String.format("minecraft:%s", name);
        }

        Pattern pattern = Pattern.compile("^([a-z-A-Z]\\w+):([a-zA-Z_]*)$");
        return pattern.matcher(name).matches() ? name : null;
    }

    /**
     * Clean the String name. This is to prevent any possible issues and to keep things clean.
     */
    public static String cleanName(String uncleaned) {
        return uncleaned
                .replaceAll("[^A-Za-z_\\s]", "")
                .replaceAll("\\s+", " ")
                .replaceAll("\\s+", "_")
                .toLowerCase();
    }
}
