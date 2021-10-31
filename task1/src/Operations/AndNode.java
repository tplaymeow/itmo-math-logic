package Operations;

import Vsitors.TreeNodeVisitor;

public class AndNode implements TreeNode {
    public TreeNode leftSubNode;
    public TreeNode rightSubNode;

    public AndNode(TreeNode leftSubNode, TreeNode rightSubNode) {
        this.leftSubNode = leftSubNode;
        this.rightSubNode = rightSubNode;
    }

    @Override
    public <T> T accept(TreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
