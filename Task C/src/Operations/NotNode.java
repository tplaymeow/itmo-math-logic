package Operations;

import Vsitors.TreeNodeVisitor;

import java.util.Objects;

final public class NotNode implements TreeNode {
    public final TreeNode subNode;

    public NotNode(TreeNode subNode) {
        this.subNode = subNode;
    }

    @Override
    public <T> T accept(TreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotNode notNode = (NotNode) o;
        return Objects.equals(subNode, notNode.subNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subNode);
    }
}
