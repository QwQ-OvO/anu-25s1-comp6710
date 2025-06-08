import comp1110.lib.*;
import comp1110.lib.Date;
import static comp1110.lib.Functions.*;

sealed interface Tree permits Leaf, Node {}

record Leaf() implements Tree {}

record Node(int value, Tree left, Tree right) implements Tree {}

boolean treeContains(Tree tree, int number) {
    return switch(tree) {
        case Leaf() -> false;
        case Node(int value, Tree left, Tree right) -> {
            if (value == number) {
                yield true;
            } else {
                yield treeContains(left, number) || treeContains(right, number);
            }
        }
    };
}

void main() {}