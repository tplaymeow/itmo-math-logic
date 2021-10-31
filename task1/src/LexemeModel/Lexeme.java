package LexemeModel;

import LexemeModel.LexemeType;

import java.util.Objects;

/**
 * Абстракция над лексемами в логических выражениях
 */
public class Lexeme {
    /** Прост тип */
    public LexemeType type;
    /** Значение -- для лексем описывающих операции имеет смысл value == null */
    public String value;

    public Lexeme(LexemeType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Lexeme(LexemeType type) {
        this.type = type;
        this.value = null;
    }

    // Debugging
    @Override
    public String toString() {
        if (Objects.nonNull(value)) {
            return value;
        } else return type.toString();
    }
}
