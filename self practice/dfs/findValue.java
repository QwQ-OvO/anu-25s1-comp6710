package DFS;

public class findValue<E> {

    E value;
    findValue<E> left;
    findValue<E> right;

    public findValue(E value, findValue<E> left, findValue<E> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
    public static <E> findValue<E> findNode(findValue<E> root, E target) {
        if (root == null) {
            return null;
        }

        if (root.value.equals(target)) {
            return root;
        }

        findValue<E> leftResult = findNode(root.left, target);
        if (leftResult != null) {
            return leftResult;
        }
        return findNode(root.right, target);
    }

}
