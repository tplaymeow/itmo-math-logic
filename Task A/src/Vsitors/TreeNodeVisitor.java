package Vsitors;

import Operations.*;

public interface TreeNodeVisitor<T> {
    T visit(AndNode node);
    T visit(ImplNode node);
    T visit(NotNode node);
    T visit(OrNode node);
    T visit(VariableNode node);
}
