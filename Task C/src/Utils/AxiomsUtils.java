package Utils;

import Operations.TreeNode;
import Vsitors.MatchingToReferenceTreeNodeVisitor;

final public class AxiomsUtils {
    public static Boolean canBeProofedLikeAxiom(TreeNode node) {
        return Axioms.allAxioms.stream()
                .anyMatch(axiom -> node.accept(new MatchingToReferenceTreeNodeVisitor(axiom)));
    }
}
