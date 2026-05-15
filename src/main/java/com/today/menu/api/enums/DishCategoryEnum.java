package com.today.menu.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DishCategoryEnum {

    SPECIAL(1, "特色菜"),
    MEAT(2, "荤菜"),
    VEGETABLE(3, "素菜"),
    DRINK(4, "酒水"),
    STAPLE(5, "主食"),
    OTHER(6, "其他");

    private final Integer category;
    private final String name;

    public static String getNameByCategory(Integer category) {
        if (category == null) {
            return null;
        }
        for (DishCategoryEnum value : values()) {
            if (value.getCategory().equals(category)) {
                return value.getName();
            }
        }
        return null;
    }

    public static DishCategoryEnum getByCategory(Integer category) {
        if (category == null) {
            return null;
        }
        for (DishCategoryEnum value : values()) {
            if (value.getCategory().equals(category)) {
                return value;
            }
        }
        return null;
    }
}