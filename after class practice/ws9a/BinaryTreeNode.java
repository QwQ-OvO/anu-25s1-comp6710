public class BinaryTreeNode<E> {

    E value;
    BinaryTreeNode<E> left;
    BinaryTreeNode<E> right;

    public BinaryTreeNode(E value, BinaryTreeNode<E> left, BinaryTreeNode<E> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public static <E> void preorderDFS(BinaryTreeNode<E> root) {
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        preorderDFS(root.left);
        preorderDFS(root.right);
    }
}
