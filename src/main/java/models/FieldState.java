package models;

/*
    This enum is almost identical to 'Mark' enum but FieldState can be empty. Because player can't make 'empty'
    I decided to split this into two enums - FieldState and Mark
 */
public enum FieldState {
    X,
    O,
    EMPTY
}
