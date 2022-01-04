package Vsitors;

import Operations.*;

final public class PrettyStringTreeNodeVisitor implements TreeNodeVisitor<String> {
    @Override
    public String visit(AndNode node) {
        return "(&,"
                + node.leftSubNode.accept(this) + ","
                +  node.rightSubNode.accept(this) + ")";
    }

    @Override
    public String visit(ImplNode node) {
        return "(->,"
                + node.leftSubNode.accept(this) + ","
                +  node.rightSubNode.accept(this) + ")";
    }

    @Override
    public String visit(NotNode node) {
        return "(!" + node.subNode.accept(this) + ")";
    }

    @Override
    public String visit(OrNode node) {
        return "(|,"
                + node.leftSubNode.accept(this) + ","
                +  node.rightSubNode.accept(this) + ")";
    }

    @Override
    public String visit(VariableNode node) {
        return node.name;
    }
}
