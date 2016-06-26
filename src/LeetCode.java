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


}

