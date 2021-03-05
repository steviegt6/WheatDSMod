package io.github.steviegt6.wheatdsmod.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

// https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
public class ReflectionHelper {
    /**
     * Method that removes the "final" modifier from a specified field.
     * @param field The field to modify.
     * @return Returns the modified field.
     * @throws NoSuchFieldException ignore
     * @throws IllegalAccessException ignore
     */
    public static Field removeFinalModifier(Field field) throws NoSuchFieldException, IllegalAccessException {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);

        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.setAccessible(isAccessible);
        return field;
    }

    /**
     * Sets a field to be accessible and removes the "final" modifier if it has one.
     * @param field The field to make modifiable.
     * @return Returns the modified field.
     * @throws NoSuchFieldException ignore
     * @throws IllegalAccessException Thrown if a field is for some reason inaccessible through reflection.
     */
    public static Field makeModifiable(Field field) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        return removeFinalModifier(field);
    }
}
