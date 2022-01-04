package Vsitors;

import Operations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Да-да это выглядит как говнокод, возможно это им и является, но я оправдываю себя тем,
 * что оно хотя бы работает и говнокод находится только внутри класса.
 * При использование извне выглядит достаточно чисто
 */
final public class MatchingToReferenceTreeNodeVisitor implements TreeNodeVisitor<Boolean> {
    private final TreeNode reference;

    public MatchingToReferenceTreeNodeVisitor(TreeNode reference) {
        this.reference = reference;
    }

    @Override
    public Boolean visit(AndNode node) {
        return commonVisit(node);
    }

    @Override
    public Boolean visit(ImplNode node) {
        return commonVisit(node);
    }

    @Override
    public Boolean visit(NotNode node) {
        return commonVisit(node);
    }

    @Override
    public Boolean visit(OrNode node) {
        return commonVisit(node);
    }

    @Override
    public Boolean visit(VariableNode node) {
        return commonVisit(node);
    }

    private Boolean commonVisit(TreeNode node) {
        return this.reference.accept(
                new MatchingToReferenceTreeNodeVisitor_Reversed(node)
        );
    }
}

final class MatchingToReferenceTreeNodeVisitor_Reversed implements TreeNodeVisitor<Boolean> {
    private TreeNode comparableNode;
    private final Map<String, TreeNode> variableNameToNode = new HashMap<>();

    MatchingToReferenceTreeNodeVisitor_Reversed(TreeNode comparableNode) {
        this.comparableNode = comparableNode;
    }

    @Override
    public Boolean visit(AndNode node) {
        if (!Objects.equals(AndNode.class, this.comparableNode.getClass()))
            return false;

        AndNode andNode = (AndNode) this.comparableNode;

        this.comparableNode = andNode.leftSubNode;
        if (!node.leftSubNode.accept(this)) return false;
        this.comparableNode = andNode.rightSubNode;
        return node.rightSubNode.accept(this);
    }

    @Override
    public Boolean visit(ImplNode node) {
        if (!Objects.equals(ImplNode.class, this.comparableNode.getClass()))
            return false;

        ImplNode implNode = (ImplNode) this.comparableNode;

        this.comparableNode = implNode.leftSubNode;
        if (!node.leftSubNode.accept(this)) return false;
        this.comparableNode = implNode.rightSubNode;
        return node.rightSubNode.accept(this);
    }

    @Override
    public Boolean visit(NotNode node) {
        if (!Objects.equals(NotNode.class, this.comparableNode.getClass()))
            return false;

        NotNode notNode = (NotNode) this.comparableNode;

        this.comparableNode = notNode.subNode;
        return node.subNode.accept(this);
    }

    @Override
    public Boolean visit(OrNode node) {
        if (!Objects.equals(OrNode.class, this.comparableNode.getClass()))
            return false;

        OrNode orNode = (OrNode) this.comparableNode;

        this.comparableNode = orNode.leftSubNode;
        if (!node.leftSubNode.accept(this)) return false;
        this.comparableNode = orNode.rightSubNode;
        return node.rightSubNode.accept(this);
    }

    @Override
    public Boolean visit(VariableNode node) {
        if (this.variableNameToNode.containsKey(node.name))
            return Objects.equals(
                    variableNameToNode.get(node.name),
                    this.comparableNode
            );

        this.variableNameToNode.put(node.name, this.comparableNode);
        return true;
    }
}
