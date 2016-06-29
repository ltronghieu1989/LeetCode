import java.lang.reflect.Array;
import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

/*
 297. Serialize and Deserialize Binary Tree
 Print out the Tree as pre-order traversal, command separator
 Use a queue to deserialize the string
 */
class Codec {
    // Encodes a tree to a single string
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) return "null";

//        serializeBinaryTreeRecursion(root, sb);
//        if (sb.charAt(sb.length() - 1) == ',')    // Remove last comma separator
//            sb.deleteCharAt(sb.length() - 1);

        serializeBinaryTreeIteration(root, sb);

        return sb.toString();
    }

    private void serializeBinaryTreeRecursion(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("null").append(",");
        } else {
            sb.append(root.val).append(",");
            serializeBinaryTreeRecursion(root.left, sb);
            serializeBinaryTreeRecursion(root.right, sb);
        }
    }

    private void serializeBinaryTreeIteration(TreeNode root, StringBuilder sb) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        sb.append(root.val).append(",");

        while (!queue.isEmpty()) {
            TreeNode front = queue.poll();

            if (front.left == null)
                sb.append("null").append(",");
            else
                sb.append(front.left.val).append(",");

            if (front.right == null)
                sb.append("null").append(",");
            else
                sb.append(front.right.val).append(",");

            if (front.left != null) queue.add(front.left);
            if (front.right != null) queue.add(front.right);
        }
        sb.deleteCharAt(sb.length() - 1);   // Remove last comma separator
    }

    // Decode your encoded data to tree
    public TreeNode deserialize(String data) {
        TreeNode root = null;
        if (data == null) return root;

        // Recursion style
//        Queue<String> nodes = new LinkedList<>();
//        nodes.addAll(Arrays.asList(data.split(",")));
//        root = deserializeBinaryTreeRecursion(nodes);

        // Iteration style
        root = deserializeBinaryTreeIteration(data);

        return root;
    }

    private TreeNode deserializeBinaryTreeRecursion(Queue<String> nodes) {
        String val = nodes.poll();
        if (val.equals("null")) {
            return null;
        } else {
            TreeNode node = new TreeNode(Integer.parseInt(val));
            node.left = deserializeBinaryTreeRecursion(nodes);
            node.right = deserializeBinaryTreeRecursion(nodes);
            return node;
        }
    }

    private TreeNode deserializeBinaryTreeIteration(String data) {
        TreeNode root = null;
        String[] nodes = data.split(",");
        if (!nodes[0].equals("null"))
            root = new TreeNode(Integer.parseInt(nodes[0]));
        else
            return root;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int idx = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (!nodes[idx].equals("null")) {
                node.left = new TreeNode(Integer.parseInt(nodes[idx]));
                queue.add(node.left);
            }
            if (!nodes[idx + 1].equals("null")) {
                node.right = new TreeNode(Integer.parseInt(nodes[idx + 1]));
                queue.add(node.right);
            }
            idx += 2;
        }

        return root;
    }
}

public class LeetCode {
    /*
    Find least occurrence in a given string
     */
    public char firstLeastOccurence(String s) {
        char ans = ' ';
        if (s == null || s.length() == 0) return ans;

        int[] arr = new int[256];
        Arrays.fill(arr, -1);
        char[] letters = s.toCharArray();

        // Iterate through the input array to acquire key - index
        for (int i = 0; i < letters.length; i++) {
            char c = letters[i];
            if (arr[c - '0'] == -1)
                arr[c - '0'] = i;
        }

        // Sort the input array in ascending order
        Arrays.sort(letters);

        int count = 1;
        int minIdx = Integer.MAX_VALUE;

        // Count forward the sum
        for (int i = 1; i < letters.length; i++) {
            if (letters[i - 1] != letters[i]) {
                if (count == 1) {
                    minIdx = Math.min(minIdx, arr[letters[i - 1] - '0']);
                }
                count = 1;
            } else {
                count++;
            }
        }
        // Count the last letter
        if (count == 1) {
            minIdx = Math.min(minIdx, arr[letters[letters.length - 1] - '0']);
        }
        if (minIdx != Integer.MAX_VALUE) ans = s.charAt(minIdx);

        return ans;
    }

    /*---------------------------------------------------------
                            BINARY TREE
     ---------------------------------------------------------*/

    /*
    Balanced Binary Tree
     */


    /*
    Binary Tree Inorder Traversal (Iterative)
     */

    /*
    Binary Tree Post-order Traversal (Iterative)
     */

    /*
    Binary Tree Pre-order Traversal (Iterative);
     */

    /*
    Binary Tree Level Order Traversal (top down)
     */

    /*
    Binary Tree Level Order Traversal (bottom up)
     */

    /*
    Binary Tree ZigZag Level Order Traversal
     */

    /*
    Construct Binary Tree from Inorder and Post-order Traversal
     */

    /*
    Construct Binary Tree from Inorder and Pre-order Traversal
     */

    /*
    Find Leaves of Binary Tree
     */

    /*
    Flatten Binary Tree to LinkedList
     */

    /*
    Invert Binary Tree
     */

    /*
    Lowest Command Ancestor of a Binary Tree
     */

    /*
    Maximum Depth of Binary Tree
     */

    /*
    Minimum Depth of Binary Tree
     */

    /*
    Serialize and Deserialize Binary Tree
     */

    /*
    Verify Pre-order Serialization of Binary Tree
     */

    /*
    Binary Tree Vertical Order Traversal
     */
    public List<Integer> verticalOrderTraversal(TreeNode root) {
        TreeMap<Integer, List<Integer>> ht = new TreeMap<>();
        List<Integer> a1 = new ArrayList<>();
        int level = 0;  // '0' is the root vertical order
        verticalRecursion(root, level, ht, a1);

        Set<Integer> i = ht.keySet();
        a1 = new ArrayList<>();
        for (int keys : i) {
            a1.add(ht.get(keys).get(0));
        }

        return a1;
    }

    // 1. Do inorder-traversal
    // 2. Use a 'level' -- going left, ++ going right to separate out the level vertically
    // 3. Store elements of each level, create a TreeMap and the key-value pair will be (level, [])
    private TreeNode verticalRecursion(TreeNode root, int level, TreeMap<Integer, List<Integer>> ht, List<Integer> a1) {
        if (root == null) {
            return null;
        }

        // Going left
        TreeNode left = verticalRecursion(root.left, --level, ht, a1);

        // No left node found
        if (left == null) {
            level++;
        }

        if (ht.get(level) != null) {
            List<Integer> x = ht.get(level);
            x.add(root.val);
            ht.put(level, x);
        } else {
            a1 = new ArrayList<>();
            a1.add(root.val);
            ht.put(level, a1);
        }

        // Going right and return
        return verticalRecursion(root.right, ++level, ht, a1);
    }

    /*
    Binary Tree Top View
    Approach: similar ot the vertical order traversal. Modify the code so that it will print only the first element
    it will encounter in the vertical order
     */
    public List<Integer> topView(TreeNode root) {
        TreeMap<Integer, List<Integer>> ht = new TreeMap<>();
        List<Integer> a1 = new ArrayList<>();
        int level = 0;  // '0' is the root vertical order
        topViewRecursion(root, level, ht, a1);

        Set<Integer> i = ht.keySet();
        a1 = new ArrayList<>();
        for (int keys : i) {
            a1.add(ht.get(keys).get(0));
        }

        return a1;
    }

    private TreeNode topViewRecursion(TreeNode root, int level, TreeMap<Integer, List<Integer>> ht, List<Integer> a1) {
        if (root == null) return null;

        // Going left
        TreeNode left = topViewRecursion(root.left, --level, ht, a1);

        // Not found
        if (left == null) level++;

        if (!ht.containsKey(level)) {
            a1 = new ArrayList<>();
            a1.add(root.val);
            ht.put(level, a1);
        }

        // Going right
        return topViewRecursion(root.right, ++level, ht, a1);
    }

    /*
    Binary Tree Bottom View
     */
    public List<Integer> bottomView(TreeNode root) {
        TreeMap<Integer, List<Integer>> ht = new TreeMap<>();
        List<Integer> a1 = new ArrayList<>();
        int level = 0;  // '0' is the root vertical order
        bottomViewRecursion(root, level, ht, a1);

        Set<Integer> i = ht.keySet();
        a1 = new ArrayList<>();
        for (int keys : i) {
            a1.add(ht.get(keys).get(0));
        }

        return a1;
    }

    private TreeNode bottomViewRecursion(TreeNode root, int level, TreeMap<Integer, List<Integer>> ht, List<Integer> a1) {
        if (root == null) return null;

        // Going left
        TreeNode left = bottomViewRecursion(root.left, --level, ht, a1);

        // Not found
        if (left == null) level++;

        if (ht.containsKey(level)) {
            List<Integer> list = ht.get(level);
            list.remove(0);
            list.add(root.val);
            ht.put(level, list);
        } else {
            a1 = new ArrayList<>();
            a1.add(root.val);
            ht.put(level, a1);
        }

        // Going right
        return bottomViewRecursion(root.right, ++level, ht, a1);
    }

    /*
    Binary Tree Right Side View
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        rightSideViewRecursion(root, list, 0);
        return list;
    }

    private void rightSideViewRecursion(TreeNode node, List<Integer> list, int currDepth) {
        if (node == null) return;
        if (currDepth == list.size()) {
            list.add(node.val);
        }
        rightSideViewRecursion(node.right, list, currDepth + 1);
        rightSideViewRecursion(node.left, list, currDepth + 1);
    }

    /*
    Binary Tree Left Side View
     */
    public List<Integer> leftSideView(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        leftSideViewRecursion(root, list, 0);
        return list;
    }

    private void leftSideViewRecursion(TreeNode node, List<Integer> list, int currDepth) {
        if (node == null) return;
        if (currDepth == list.size()) {
            list.add(node.val);
        }
        rightSideViewRecursion(node.left, list, currDepth + 1);
        rightSideViewRecursion(node.right, list, currDepth + 1);
    }


    /*
    Print Edge Nodes (Boundary) of a Binary Tree
    http://articles.leetcode.com/print-edge-nodes-boundary-of-binary/
    Given: {30,10,20,50,null,45,35} should return 30,10,50,45,35,20
     */
    public List<Integer> printOuterEdges(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;

        List<Integer> leftSide = new ArrayList<>();
        leftSide = leftSideView(root);
        ListIterator li = leftSide.listIterator();
        while (li.hasNext()) {
            ans.add((Integer) li.next());
        }

        List<Integer> bottom = new ArrayList<>();
        bottom = bottomView(root);
        bottom.remove(0);
        bottom.remove(bottom.size() - 1);
        li = bottom.listIterator();
        while (li.hasNext()) {
            ans.add((Integer) li.next());
        }

        List<Integer> rightSide = new ArrayList<>();
        rightSide = rightSideView(root);
        rightSide.remove(0);
        li = rightSide.listIterator(rightSide.size());
        while (li.hasPrevious()) {
            ans.add((Integer) li.previous());
        }

        return ans;
    }

    /*

    Approach: Divide the tree into two subtrees. One if the left subtree and the other is the right subtree.
    For left subtree, we print the leftmost edges from top to bottom, then we print its leaves from left to right
    For right subtree, we print its leaves left to right, then its rightmost edges from bottom to top

    Bad code didn't handle all cases

    public void printOuterEdges(TreeNode root) {
        if (root == null) return;
        System.out.printf("%d ", root.val);
        printLeftEdges(root.left, true);
        printRightEdges(root.right, true);
    }

    private void printLeftEdges(TreeNode p, boolean print) {
        if (p == null) return;
        if (print || (p.left == null && p.right == null)) System.out.printf("%d ", p.val);
        printLeftEdges(p.left, print);
        printLeftEdges(p.right, (print && p.left == null) ? true : false);
    }

    private void printRightEdges(TreeNode p, boolean print) {
        if (p == null) return;
        printRightEdges(p.left, (print && p.right == null) ? true : false);
        printRightEdges(p.right, print);
        if (print || (p.left == null && p.right == null))
            System.out.printf("%d ", p.val);
    }
    */

    /*
    89. Gray Code
    Math problem: G(i) = i ^ (i/2)
     */
    public List<Integer> grayCode(int n) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            ans.add(i ^ i >> 1);
        }
        return ans;
    }

}

