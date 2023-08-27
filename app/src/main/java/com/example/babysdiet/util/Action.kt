package com.example.babysdiet.util

enum class Action {
    ADD_DIARY,
    ADD_PRODUCT,
    DELETE_DIARY,
    DELETE_PRODUCT,
    UPDATE_DIARY,
    UPDATE_PRODUCT,
    DELETE_ALL_DIARIES,
    DELETE_ALL_PRODUCTS,
    UNDO_DIARY,
    UNDO_PRODUCT,
    NO_ACTION
}
fun String?.toAction(): Action =
    if (this.isNullOrEmpty()) Action.NO_ACTION else Action.valueOf(this)
