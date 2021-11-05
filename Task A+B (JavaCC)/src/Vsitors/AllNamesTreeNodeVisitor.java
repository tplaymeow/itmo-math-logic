package Vsitors;

import Operations.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class AllNamesTreeNodeVisitor implements TreeNodeVisitor<Set<String>> {
    @Override
    public Set<String> visit(AndNode node) {
        Set<String> result = node.leftSubNode.accept(this);
        result.addAll(node.rightSubNode.accept(this));
        return result;
    }

    @Override
    public Set<String> visit(ImplNode node) {
        Set<String> result = node.leftSubNode.accept(this);
        result.addAll(node.rightSubNode.accept(this));
        return result;
    }

    @Override
    public Set<String> visit(NotNode node) {
        return node.subNode.accept(this);
    }

    @Override
    public Set<String> visit(OrNode node) {
        Set<String> result = node.leftSubNode.accept(this);
        result.addAll(node.rightSubNode.accept(this));
        return result;
    }

    @Override
    public Set<String> visit(VariableNode node) {
        LinkedHashSet<String> result = new LinkedHashSet<>();
        result.add(node.name);
        return result;
    }
}
