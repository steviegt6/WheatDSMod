package io.github.steviegt6.wheatdsmod.items.crops;

/**
 * Represents a type of crop.
 */
public class CropType {
    private String name; // The name of the type. This is used to suffix the ID of crop items as they get registered
    private int craftCount; // The amount of the crop needed to craft its target material

    public CropType(String name, int craftCount) {
        this.name = name;
        this.craftCount = craftCount;
    }

    public String getName() {
        return name;
    }

    public int getCraftCount() {
        return craftCount;
    }
}
