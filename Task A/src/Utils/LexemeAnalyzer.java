package Utils;

import LexemeModel.Lexeme;
import LexemeModel.LexemeType;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LexemeAnalyzer {
    public List<Lexeme> analyze(String expressionText) {
        ArrayList<Lexeme> result = new ArrayList<>();

        StringCharacterIterator charIterator = new StringCharacterIterator(expressionText);
        while (charIterator.current() != CharacterIterator.DONE) {
            char currentChar = charIterator.current();

            switch (currentChar) {
                case '(': result.add(new Lexeme(LexemeType.LEFT_BRACKET)); break;
                case ')': result.add(new Lexeme(LexemeType.RIGHT_BRACKET)); break;
                case '!': result.add(new Lexeme(LexemeType.NOT)); break;
                case '|': result.add(new Lexeme(LexemeType.OR)); break;
                case '&': result.add(new Lexeme(LexemeType.AND)); break;
                case '-':
                    if (charIterator.next() == '>') result.add(new Lexeme(LexemeType.IMPL));
                    else throw new IllegalArgumentException();
                    break;
                default:
                    // Парсим пробел
                    if (Character.isWhitespace(currentChar)) { charIterator.next(); continue; }

                    // Парсим переменную
                    String endString = expressionText.substring(charIterator.getIndex());
                    Matcher matcher = variablePattern.matcher(endString);
                    if (matcher.find()) {
                        result.add(new Lexeme(LexemeType.VAR, matcher.group()));
                        charIterator.setIndex(charIterator.getIndex() + matcher.end() - 1);
                    } else throw new IllegalArgumentException();
                    break;
            }
            charIterator.next();
        }
        return result;
    }

    private final Pattern variablePattern = Pattern.compile("^[A-Z][A-Z0-9’]*");
}
