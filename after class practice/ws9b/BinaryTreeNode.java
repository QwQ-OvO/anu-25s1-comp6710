package ws9b;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

public class BinaryTreeNode<E> {
    E value;
    BinaryTreeNode<E> left;
    BinaryTreeNode<E> right;
    BinaryTreeNode(E value, BinaryTreeNode<E> left, BinaryTreeNode<E> right)
    {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public static <E> void preorderDFS(BinaryTreeNode<E> root) {
        if (root == null) { // Base case
            return;
        }
        else { // General/Recursive case
            System.out.println(root.value);
            preorderDFS(root.left);
            preorderDFS(root.right);
        }
    }

    public static <E> void BFS(List<BinaryTreeNode<E>> currentDepthNodes) {
        if (currentDepthNodes.isEmpty()) return;
        // General/Recursive case
        List<BinaryTreeNode<E>> nextDepthNodes = new ArrayList<>();
        for (var node : currentDepthNodes) {
            System.out.println(node.value);
            if (!(node.left == null)) {
                nextDepthNodes.add(node.left);
            }
            if (!(node.right == null)) {
                nextDepthNodes.add(node.right);
            }
        }
        BFS(nextDepthNodes);
    }

    public static <E> void iterativeBFS(BinaryTreeNode<E> root) {
        if (root == null) return;
        Queue<BinaryTreeNode<E>> queue = new LinkedList<>();
        queue.add(root);
        // We will finish as the tree has a finite set of nodes
        // (assuming that the tree data structure is consistent)
        while (!queue.isEmpty())
        {
            var currentNode = queue.poll();
            System.out.println(currentNode.value);
            if (!(currentNode.left == null)) {
                queue.add(currentNode.left);
            }
            if (!(currentNode.right == null)) {
                queue.add(currentNode.right);
            }
        }
    }

    public static void main(String[] args) {
        var D = new BinaryTreeNode<String>("D", null, null);
        var E = new BinaryTreeNode<String>("E", null, null);
        var F = new BinaryTreeNode<String>("F", null, null);
        var B = new BinaryTreeNode<String>("B", D, E);
        var C = new BinaryTreeNode<String>("C", null, F);
        var A = new BinaryTreeNode<String>("A", B, C);

        // call to recursive DFS
        System.out.println("Recursive preorder DFS tree traversal");
        preorderDFS(A);

        // what would happen to preorder DFS if we add a loop in the tree?
        // e.g.
        // D.left = A;
        // preorderDFS(A);

        // call to recursive BFS
        System.out.println("Recursive BFS tree traversal ");
        List<BinaryTreeNode<String>> depthZeroNodes = new ArrayList<>();
        depthZeroNodes.add(A);
        BFS(depthZeroNodes);

        // call to iterative BFS
        System.out.println("Iterative BFS");
        iterativeBFS(A);
    }
}
