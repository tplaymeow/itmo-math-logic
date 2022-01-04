import LexemeModel.Lexeme;
import Operations.ImplNode;
import Operations.TreeNode;
import Operations.VariableNode;
import Utils.AxiomsUtils;
import Utils.LexemeAnalyzer;
import Utils.TreeBuilder;
import Vsitors.AllNamesTreeNodeVisitor;
import Vsitors.CalculateTreeNodeVisitor;
import Vsitors.PrettyStringTreeNodeVisitor;
import Vsitors.StringLikeExpressionTreeNodeVisitor;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        task3();
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

    /**
     * Возможно этот код заставит кого-то плакать, не переживайте меня тоже
     */
    private static void task3() {
        LexemeAnalyzer analyzer = new LexemeAnalyzer();
        TreeBuilder builder = new TreeBuilder();
        Scanner scanner = new Scanner(System.in);

        String[] firstLineComponents = scanner.nextLine().split("\\|-");

        List<TreeNode> hypotheses = Arrays
                .stream(firstLineComponents[0].split(","))
                .map(str -> builder.buildTree(analyzer.analyze(str)))
                .collect(Collectors.toList());
        TreeNode alpha = hypotheses.remove(hypotheses.size() - 1);

        Set<TreeNode> inputProofList = new HashSet<>();
        LinkedList<TreeNode> resultProofList = new LinkedList<>();

        while (scanner.hasNextLine()) {
            TreeNode deltaI = builder.buildTree(analyzer.analyze(scanner.nextLine()));

            // Первый случай: утверждение принадлежит списку гипотез или является аксиомой
            if (hypotheses.contains(deltaI) || AxiomsUtils.canBeProofedLikeAxiom(deltaI)) {
                // Добаляем: d_i -> a -> d_i
                resultProofList.add(new ImplNode(deltaI, new ImplNode(alpha, deltaI)));
                // Добавляем: d_i
                resultProofList.add(deltaI);
                // Добавляем: a -> d_i
                resultProofList.add(new ImplNode(alpha, deltaI));
            }
            // Второй случай: утверждение совпадает с a
            else if (Objects.equals(alpha, deltaI)) {
                // Обозначим: t1 = (a -> a -> a),
                //            t2 = (a -> (a -> a) -> a),
                //            t3 = (a -> a)
                TreeNode t1 = new ImplNode(alpha, new ImplNode(alpha, alpha));
                TreeNode t2 = new ImplNode(alpha, new ImplNode(new ImplNode(alpha, alpha), alpha));
                TreeNode t3 = new ImplNode(alpha, alpha);
                // Добавляем: t1 == a -> a -> a
                resultProofList.add(t1);
                // Добавляем: t1 -> t2 -> t3 == (a -> a -> a) -> (a -> (a -> a) -> a) -> (a -> a)
                resultProofList.add(new ImplNode(t1, new ImplNode(t2, t3)));
                // Добавляем: t2 -> t3 == (a -> (a -> a) -> a) -> (a -> a)
                resultProofList.add(new ImplNode(t2, t3));
                // Добавляем: t2 == (a -> (a -> a) -> a)
                resultProofList.add(t2);
                // Добавляем: t3 == a -> a
                resultProofList.add(t3);
            }
            // Третий случай: утверждение полученно как modus ponens от двух других
            else {
                // Находим: d_k
                ImplNode deltaK = inputProofList.stream()
                        .filter(treeNode -> treeNode instanceof ImplNode)
                        .map(treeNode -> (ImplNode) treeNode)
                        .filter(implNode -> Objects.equals(implNode.rightSubNode, deltaI))
                        .filter(implNode -> inputProofList.contains(implNode.leftSubNode))
                        .findFirst().get();
                // Находим: d_j
                TreeNode deltaJ = deltaK.leftSubNode;

                // Обозначим: t1 = (a -> d_j),
                //            t2 = (a -> d_j -> d_i),
                //            t3 = (a -> d_i)
                TreeNode t1 = new ImplNode(alpha, deltaJ);
                TreeNode t2 = new ImplNode(alpha, new ImplNode(deltaJ, deltaI));
                TreeNode t3 = new ImplNode(alpha, deltaI);
                // Добавляем: t1 -> t2 -> t3 == (a -> d_j) -> (a -> d_j -> d_i) -> (a -> d_i)
                resultProofList.add(new ImplNode(t1, new ImplNode(t2, t3)));
                // Добавляем: t2 -> t3 == (a -> d_j -> d_i) -> (a -> d_i)
                resultProofList.add(new ImplNode(t2, t3));
                // Добавляем: t3 == a -> d_i
                resultProofList.add(t3);
            }

            inputProofList.add(deltaI);
        }

        StringLikeExpressionTreeNodeVisitor strVis = new StringLikeExpressionTreeNodeVisitor();

        if (!Objects.equals(
                new ImplNode(alpha, builder.buildTree(analyzer.analyze(firstLineComponents[1]))),
                resultProofList.peekLast()
        )) throw new RuntimeException();

        System.out.println(
                hypotheses.stream()
                        .map(treeNode -> treeNode.accept(strVis))
                        .collect(Collectors.joining(",")) +
                        "|-" + resultProofList.peekLast().accept(strVis)
                );

        resultProofList.forEach(treeNode -> System.out.println(treeNode.accept(strVis)));
    }

    private static TreeNode readTreeNode() {
        LexemeAnalyzer analyzer = new LexemeAnalyzer();
        TreeBuilder builder = new TreeBuilder();

        String exp = (new Scanner(System.in)).nextLine();

        return builder.buildTree(analyzer.analyze(exp));
    }
}