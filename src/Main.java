import java.util.List;

public class Main {
    private static LeetCode lc = new LeetCode();
    private static TopCoder tc = new TopCoder();

    public static void main(String[] args) {

        // PalindromeGame tests
//        String fronts = "abc,abc,def,cba,fed";
//        int[] backs = {24, 7, 63, 222, 190};
//        String fronts = "aaaaaaaaaaaa,nopalindrome,steponnopets,emordnilapon,aaaaaaaaaaaa,steponnopets,nopalindrome,steponnopets,nopalindrome,bbbbbbbbbbbb,cannotbeused,cannotbeused,steponnopets,aaaaaaaaaaaa,nopalindrome,aaaaaaaaaaaa,nopalindrome,emordnilapon,steponnopets,nopalindrome";
//        int[] backs = {4096, 131072, 64, 262144, 512, 1024, 65536, 2048, 32768, 1, 524288, 16384, 32, 4, 16, 2, 8, 128, 8192, 256};
//        String fronts = "aabaa,defg";
//        int[] backs = {99, 1};
//        int ans = tc.PalindromeGame(fronts.split(","), backs);
//        System.out.println("Max: " + ans);

        // Activity Selector (Greedy Algorithm) tests
//        int[] start_times = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
//        int[] finish_times = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
//        List<Integer> ans = tc.ActivitySelector(start_times, finish_times);
//        for (Integer i : ans) {
//            System.out.printf("%d ", i);
//        }

        // LampsGrid tests
//        String[] initial = {"001", "101", "001", "000", "111", "001", "101", "111", "110", "000", "111", "010", "110", "001"};
//        int ans = tc.LampsGrid(initial, 6);
//        System.out.println(ans);
//        String[] initial2 = {"01", "10", "01", "01", "10"};
//        ans = tc.LampsGrid(initial2, 1);
//        System.out.println(ans);

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        Codec codec = new Codec();
        String data = codec.serializeBinaryTree(root);
        System.out.println(data);

        TreeNode ans = codec.deserialize(data);
        preorderTraversal(ans);
        System.out.println();
        postorderTraversal(ans);
        System.out.println();
        inorderTraversal(ans);
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
