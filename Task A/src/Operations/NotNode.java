package Operations;

import Vsitors.TreeNodeVisitor;

public class NotNode implements TreeNode {
    public TreeNode subNode;

    public NotNode(TreeNode subNode) {
        this.subNode = subNode;
    }

    @Override
    public <T> T accept(TreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
