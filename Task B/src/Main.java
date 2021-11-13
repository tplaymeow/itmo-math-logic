import LexemeModel.Lexeme;
import Operations.TreeNode;
import Utils.LexemeAnalyzer;
import Utils.TreeBuilder;
import Vsitors.AllNamesTreeNodeVisitor;
import Vsitors.CalculateTreeNodeVisitor;
import Vsitors.PrettyStringTreeNodeVisitor;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        task2();
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

    private static void task2() {
        TreeNode rootNode = readTreeNode();

        // Get array of variable names
        Set<String> variableNamesSet = rootNode.accept(new AllNamesTreeNodeVisitor());
        String[] variableNames = new String[variableNamesSet.size()];
        variableNamesSet.toArray(variableNames);

        HashMap<String, Boolean> values = new HashMap<>();
        CalculateTreeNodeVisitor calculateVisitor = new CalculateTreeNodeVisitor(values);

        int truesCount = 0, falsesCount = 0;

        for (int i = 0; i < (1 << variableNames.length); i++) {
            for (int j = 0; j < variableNames.length; j++) {
                Boolean value = (i >> j) % 2 == 0;
                values.put(variableNames[j], value);
            }
            boolean result = rootNode.accept(calculateVisitor);
            truesCount += result ? 1 : 0;
            falsesCount += !result ? 1 : 0;
        }

        if (truesCount != 0 && falsesCount == 0)
            System.out.println("Valid");
        else if (truesCount != 0 && falsesCount != 0)
            System.out.println("Satisfiable and invalid, " + truesCount + " true and " + falsesCount + " false cases");
        else if (truesCount == 0 && falsesCount != 0)
            System.out.println("Unsatisfiable");
        else throw new RuntimeException();
    }

    private static TreeNode readTreeNode() {
        LexemeAnalyzer analyzer = new LexemeAnalyzer();
        TreeBuilder builder = new TreeBuilder();

        String exp = (new Scanner(System.in)).nextLine();

        return builder.buildTree(analyzer.analyze(exp));
    }
}
