package Operations;

import Vsitors.TreeNodeVisitor;

import java.util.Objects;

final public class ImplNode implements TreeNode {
    public final TreeNode leftSubNode;
    public final TreeNode rightSubNode;

    public ImplNode(TreeNode leftSubNode, TreeNode rightSubNode) {
        this.leftSubNode = leftSubNode;
        this.rightSubNode = rightSubNode;
    }

    @Override
    public <T> T accept(TreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImplNode implNode = (ImplNode) o;
        return Objects.equals(leftSubNode, implNode.leftSubNode) && Objects.equals(rightSubNode, implNode.rightSubNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftSubNode, rightSubNode);
    }
}
