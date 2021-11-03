package LexemeModel;

public enum LexemeType {
    // Скобки
    LEFT_BRACKET, RIGHT_BRACKET,

    // Унарные операторы
    NOT,

    // Бинарные операторы
    AND, OR, IMPL,

    // Переменная
    VAR;

    @Override
    public String toString() {
        switch (this) {
            case LEFT_BRACKET: return "(";
            case RIGHT_BRACKET: return ")";
            case NOT: return "!";
            case AND: return "&";
            case OR: return "|";
            case IMPL: return "->";
            case VAR: return "$";
        }
        return "";
    }
}
