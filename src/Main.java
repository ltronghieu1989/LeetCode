import java.util.List;

public class Main {
    private static LeetCode lc = new LeetCode();
    private static TopCoder tc = new TopCoder();

    private static void preorderTraversal(TreeNode root) {
        if (root != null) {
            System.out.printf("%d ", root.val);
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
    }

    private static void postorderTraversal(TreeNode root) {
        if (root != null) {
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            System.out.printf("%d ", root.val);
        }
    }

    private static void inorderTraversal(TreeNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.printf("%d ", root.val);
            inorderTraversal(root.right);
        }
    }

    private static String DecToBinary(int i) {
        StringBuilder sb = new StringBuilder();
        while (i > 0) {
            sb.insert(0, (i & 1) == 1 ? "1" : "0");
            i = i >> 1;
        }
        while (sb.length() < 4) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        List<List<Integer>> res = lc.findLeavesBinaryTree(root);
        for (List<Integer> lis : res) {
            System.out.println(lis);
        }
    }
}
