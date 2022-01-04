package Operations;

import Vsitors.TreeNodeVisitor;

import java.util.Objects;

final public class VariableNode implements TreeNode {
    public final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(TreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableNode that = (VariableNode) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
