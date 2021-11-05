package Operations;

import Vsitors.TreeNodeVisitor;

public interface TreeNode {
    <T> T accept(TreeNodeVisitor<T> visitor);
}
