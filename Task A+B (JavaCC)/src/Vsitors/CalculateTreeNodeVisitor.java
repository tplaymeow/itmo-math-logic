package Vsitors;

import Operations.*;

import java.util.Map;

public class CalculateTreeNodeVisitor implements TreeNodeVisitor<Boolean> {
    private final Map<String, Boolean> values;

    public CalculateTreeNodeVisitor(Map<String, Boolean> values) {
        this.values = values;
    }

    @Override
    public Boolean visit(AndNode node) {
        return node.leftSubNode.accept(this)
                && node.rightSubNode.accept(this);
    }

    @Override
    public Boolean visit(ImplNode node) {
        return !node.leftSubNode.accept(this)
                || node.rightSubNode.accept(this);
    }

    @Override
    public Boolean visit(NotNode node) {
        return !node.subNode.accept(this);
    }

    @Override
    public Boolean visit(OrNode node) {
        return node.leftSubNode.accept(this)
                || node.rightSubNode.accept(this);
    }

    @Override
    public Boolean visit(VariableNode node) {
        return this.values.get(node.name);
    }
}
