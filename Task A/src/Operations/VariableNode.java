package Operations;

import Vsitors.TreeNodeVisitor;

public class VariableNode implements TreeNode {
    public String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(TreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
