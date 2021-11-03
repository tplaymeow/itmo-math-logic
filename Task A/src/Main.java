import LexemeModel.Lexeme;
import Operations.TreeNode;
import Utils.LexemeAnalyzer;
import Utils.TreeBuilder;
import Vsitors.PrettyStringTreeNodeVisitor;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        task1();
    }

    private static void task1() {
        LexemeAnalyzer analyzer = new LexemeAnalyzer();
        TreeBuilder builder = new TreeBuilder();
        PrettyStringTreeNodeVisitor stringVisitor = new PrettyStringTreeNodeVisitor();

        String exp = (new Scanner(System.in)).nextLine();
        List<Lexeme> lexemes = analyzer.analyze(exp);

        TreeNode rootNode = builder.buildTree(lexemes);
        System.out.println(rootNode.accept(stringVisitor));
    }
}
