package Operations;

import Vsitors.TreeNodeVisitor;

public class ImplNode implements TreeNode {
    public TreeNode leftSubNode;
    public TreeNode rightSubNode;

    public ImplNode(TreeNode leftSubNode, TreeNode rightSubNode) {
        this.leftSubNode = leftSubNode;
        this.rightSubNode = rightSubNode;
    }

    @Override
    public <T> T accept(TreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
