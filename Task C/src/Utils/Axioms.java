package Utils;

import Operations.TreeNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Axioms {
    static List<TreeNode> allAxioms = makeAxioms();

    private static List<TreeNode> makeAxioms() {
        LexemeAnalyzer analyzer = new LexemeAnalyzer();
        TreeBuilder builder = new TreeBuilder();

        return Arrays.stream(new String[]{
                "A->(B->A)",
                "(A->B)->(A->B->C)->(A->C)",
                "A->B->A&B",
                "A&B->A",
                "A&B->B",
                "A->A|B",
                "B->A|B",
                "(A->C)->(B->C)->(A|B->C)",
                "(A->B)->(A->!B)->!A",
                "(!!A)->A"
        }).map(str -> builder.buildTree(analyzer.analyze(str))).collect(Collectors.toList());
    }
}
