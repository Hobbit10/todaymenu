package com.today.menu.exception;

public interface ErrorCodeConstants {

    int PARAM_ERROR = 400001;
    int DISH_NAME_EMPTY = 400002;
    int DISH_CATEGORY_EMPTY = 400003;
    int DISH_IMAGE_EMPTY = 400004;
    int DISH_NOT_FOUND = 404001;
    int DISH_ALREADY_IN_MENU = 409001;
    int INTERNAL_ERROR = 500001;
}