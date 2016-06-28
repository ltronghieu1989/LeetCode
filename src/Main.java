import java.util.List;

public class Main {
    private static LeetCode lc = new LeetCode();
    private static TopCoder tc = new TopCoder();

    public static void main(String[] args) {

        TreeNode root = new TreeNode(30);
        root.left = new TreeNode(10);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(50);
        root.right.left = new TreeNode(45);
        root.right.right = new TreeNode(35);

        //lc.printOuterEdges(root);
//        lc.rightSideView(root);
//        System.out.println();
//        lc.leftSideView(root);
//        System.out.println();
//        lc.verticalOrderTraversal(root);
//        System.out.println();
//        lc.topView(root);
//        System.out.println();
//        lc.bottomView(root);
        lc.printOuterEdges(root);
    }

    public static void preorderTraversal(TreeNode root) {
        if (root != null) {
            System.out.printf("%d ", root.val);
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
    }

    public static void postorderTraversal(TreeNode root) {
        if (root != null) {
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            System.out.printf("%d ", root.val);
        }
    }

    public static void inorderTraversal(TreeNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.printf("%d ", root.val);
            inorderTraversal(root.right);
        }
    }
}
