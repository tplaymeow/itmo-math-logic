package Utils;

import LexemeModel.Lexeme;
import LexemeModel.LexemeType;
import Operations.*;

import java.util.List;
import java.util.ListIterator;

/**
 * Граматика:
 *
 *          ⟨Файл⟩ ::= ⟨Выражение⟩
 *     ⟨Выражение⟩ ::= ⟨Дизъюнкция⟩ | ⟨Дизъюнкция⟩ ‘->’ ⟨Выражение⟩
 *    ⟨Дизъюнкция⟩ ::= ⟨Конъюнкция⟩ | ⟨Дизъюнкция⟩ ‘|’ ⟨Конъюнкция⟩
 *    ⟨Конъюнкция⟩ ::= ⟨Отрицание⟩ | ⟨Конъюнкция⟩ ‘&’ ⟨Отрицание⟩
 *     ⟨Отрицание⟩ ::= ‘!’ ⟨Отрицание⟩ | ⟨Переменная⟩ | ‘(’ ⟨Выражение⟩ ‘)’
 *    ⟨Переменная⟩ ::= (‘A’...‘Z’) {‘A’...‘Z’ | ‘0’...‘9’ | ‘’’}∗
 */
public class TreeBuilder {
   public TreeNode buildTree(List<Lexeme> lexemes) {
      return expression(lexemes.listIterator());
   }

   private static TreeNode expression(ListIterator<Lexeme> lexemes) {
      TreeNode node = or(lexemes);
      while (lexemes.hasNext() && lexemes.next().type == LexemeType.IMPL) {
         node = new ImplNode(node, or(lexemes));
      }
      lexemes.previous();
      return node;
   }

   private static TreeNode or(ListIterator<Lexeme> lexemes) {
      TreeNode node = and(lexemes);
      while (lexemes.hasNext() && lexemes.next().type == LexemeType.OR) {
         node = new OrNode(node, and(lexemes));
      }
      lexemes.previous();
      return node;
   }

   private static TreeNode and(ListIterator<Lexeme> lexemes) {
      TreeNode node = not(lexemes);
      while (lexemes.hasNext() && lexemes.next().type == LexemeType.AND) {
         node = new AndNode(node, not(lexemes));
      }
      lexemes.previous();
      return node;
   }

   private static TreeNode not(ListIterator<Lexeme> lexemes) {
      Lexeme lexeme = lexemes.next();
      if (lexeme.type == LexemeType.LEFT_BRACKET) {
         TreeNode result = expression(lexemes);
         if (lexemes.hasNext()) lexemes.next();
         return result;
      }
      else if (lexeme.type == LexemeType.NOT) return new NotNode(not(lexemes));
      else { lexemes.previous(); return variable(lexemes); }
   }

   private static TreeNode variable(ListIterator<Lexeme> lexemes) {
      Lexeme lexeme = lexemes.next();
      return new VariableNode(lexeme.value);
   }
}
