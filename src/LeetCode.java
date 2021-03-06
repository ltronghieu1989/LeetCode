import sun.reflect.generics.tree.Tree;

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
    public boolean isBalanced(TreeNode root) {
        return isBalancedDFSHeight(root) != -1;
    }

    private int isBalancedDFSHeight(TreeNode node) {
        if (node == null) return 0;
        int left, right;

        if ((left = isBalancedDFSHeight(node.left)) == -1
                || (right = isBalancedDFSHeight(node.right)) == -1
                || Math.abs(left - right) > 1) {
            return -1;
        }

        return Math.max(left, right) + 1;
    }

    /*
    94. Binary Tree Inorder Traversal (Iterative)

    Approach: use a stack to store visited nodes. Going to the most left. If left is null, pop a node, add the node
    value and going to the right one node.
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (true) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else if (!stack.isEmpty()) {
                current = stack.pop();
                ans.add(current.val);
                current = current.right;
            } else {
                break;
            }

        }

        return ans;
    }

    /*
    145. Binary Tree Post-order Traversal (Iterative)
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode last = null;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                if (stack.peek().right != null && stack.peek().right != last) {
                    current = stack.peek().right;
                } else {
                    last = stack.pop();
                    ans.add(last.val);
                }
            }
        }

        return ans;
    }

    /*
    144. Binary Tree Pre-order Traversal (Iterative);
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            ans.add(node.val);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }

        return ans;
    }

    /*
    102. Binary Tree Level Order Traversal (top down)

    Approach: use a queue to store nodes in one level
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int length = queue.size();
            List<Integer> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                TreeNode node = queue.poll();
                if (node.right != null) queue.add(node.left);
                if (node.left != null) queue.add(node.right);
                list.add(node.val);
            }
            ans.add(list);
        }

        return ans;
    }

    /*
    107. Binary Tree Level Order Traversal (bottom up)
    Reverse the result of 'levelOrder'
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int length = queue.size();
            List<Integer> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
                list.add(node.val);
            }
            ans.add(0, list); // Instead of appending to the back, we insert at the start of the result list
        }

        return ans;
    }

    /*
    103. Binary Tree ZigZag Level Order Traversal
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        boolean flag = false;

        while (!deque.isEmpty()) {
            int length = deque.size();
            List<Integer> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                TreeNode node = null;
                if (!flag) {    // Left -> Right
                    // Poll head and append to the back
                    node = deque.pollFirst();
                    if (node.left != null) deque.addLast(node.left);
                    if (node.right != null) deque.addLast(node.right);
                } else {        // Right -> Left
                    // Poll tail and append to the front
                    node = deque.pollLast();
                    if (node.right != null) deque.addFirst(node.right);
                    if (node.left != null) deque.addFirst(node.left);
                }
                list.add(node.val);
            }
            ans.add(list);
            flag = !flag;
        }

        return ans;
    }

    /*
    Construct Binary Tree from Inorder and Post-order Traversal
     */
    private int pIn;
    private int pPost;

    public TreeNode buildTreeInPost(int[] inorder, int[] postorder) {
//        pIn = inorder.length - 1;
//        pPost = postorder.length - 1;
//        return buildTreeInPostRecursion(inorder, postorder, null);

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreeInPostRecursion2(inorder, 0, inorder.length - 1,
                postorder, 0, postorder.length - 1, map);
    }

    private TreeNode buildTreeInPostRecursion(int[] inorder, int[] postorder, TreeNode end) {
        if (pPost < 0) return null;

        // Create root node
        TreeNode node = new TreeNode(postorder[pPost--]);

        // Create right subtree
        if (node.val != inorder[pIn]) {
            node.right = buildTreeInPostRecursion(inorder, postorder, node);
        }
        pIn--;

        // Create left subtree
        if (end == null || end.val != inorder[pIn]) {
            node.left = buildTreeInPostRecursion(inorder, postorder, end);
        }

        return node;
    }

    private TreeNode buildTreeInPostRecursion2(int[] inorder, int inStart, int inEnd,
                                               int[] postorder, int postStart, int postEnd,
                                               Map<Integer, Integer> inorderMap) {
        if (inStart > inEnd || postStart > postEnd) return null;

        TreeNode node = new TreeNode(postorder[postEnd]);
        int mid = inorderMap.get(postorder[postEnd]);
        node.left = buildTreeInPostRecursion2(inorder, inStart, mid - 1,
                postorder, postStart, postEnd - (inEnd - mid) - 1,
                inorderMap);
        node.right = buildTreeInPostRecursion2(inorder, mid + 1, inEnd,
                postorder, postEnd - (inEnd - mid), postEnd - 1, inorderMap);
        return node;
    }

    /*
    Construct Binary Tree from Inorder and Pre-order Traversal
     */
    public TreeNode buildTreeInPre(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreeInPreRecursion(inorder, 0, inorder.length - 1,
                preorder, 0, preorder.length - 1, map);
    }

    private TreeNode buildTreeInPreRecursion(int[] inorder, int inStart, int inEnd,
                                             int[] preorder, int preStart, int preEnd,
                                             Map<Integer, Integer> inorderMap) {
        if (preStart > preEnd || inStart > inEnd) return null;
        TreeNode node = new TreeNode(preorder[preStart]);
        int mid = inorderMap.get(preorder[preStart]);
        node.left = buildTreeInPreRecursion(inorder, inStart, mid - 1,
                preorder, preStart + 1, preEnd - (inEnd - mid), inorderMap);
        node.right = buildTreeInPreRecursion(inorder, mid + 1, inEnd,
                preorder, preEnd - (inEnd - mid) + 1, preEnd, inorderMap);

        return node;
    }

    /*
    366. Find Leaves of Binary Tree
    Given a binary tree, find all leaves and then remove those leaves. Then repeat the previous steps until the
    tree is empty.
     */
    public List<List<Integer>> findLeavesBinaryTree(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        findLeavesRecursion(root, res);
        return res;
    }

    private int findLeavesRecursion(TreeNode node, List<List<Integer>> res) {
        if (node == null) return -1;
        int level = 1 + Math.max(findLeavesRecursion(node.left, res), findLeavesRecursion(node.right, res));
        if (level + 1 > res.size()) {
            res.add(new ArrayList<>());
        }
        res.get(level).add(node.val);
        return level;
    }

    /*
    114. Flatten Binary Tree to LinkedList
    Approach: Similar to pre-order traversal
     */

    public void flatten(TreeNode root) {
        prev = null;
        flattenRecursion(root);
    }

    private TreeNode prev = null;

    // Recursion method: "reversed" pre-order traversal
    private void flattenRecursion(TreeNode root) {
        if (root != null) {
            flattenRecursion(root.right);
            flattenRecursion(root.left);
            root.right = prev;
            root.left = null;
            prev = root;
        }
    }

    // Iteration method
    public void flatten2(TreeNode root) {
        TreeNode current = root;
        while (current != null) {
            if (current.left != null) {
                TreeNode left = current.left;
                while (left.right != null) left = left.right;
                left.right = current.right;
                current.right = current.left;
                current.left = null;
            }
            current = current.right;
        }
    }

    /*
    226. Invert Binary Tree
    The idea is easy. The approach is as simple as swapping two integers
     */
    public TreeNode invertTree(TreeNode node) {
        if (node != null) {
            TreeNode tmp = invertTree(node.left);
            node.left = invertTree(node.right);
            node.right = tmp;
        }
        return node;
    }

    /*
    236. Lowest Command Ancestor of a Binary Tree
    Approach: recursively going to left/right subtrees from root
    If the current node equals to either left or right, return the current node as common ancestor
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        return (left != null && right != null) ? root : (left != null) ? left : right;
    }

    /*
    104. Maximum Depth of Binary Tree
     */
    public int maxDepth(TreeNode root) {

        // Recursive DFS
//        return (root == null) ? 0 : Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;

        // Iterative approach: similar to "Level Order Traversal"
        if (root == null) return 0;

        Deque<TreeNode> stack = new LinkedList<>();
        stack.offer(root);
        int level = 0;

        while (!stack.isEmpty()) {
            level++;
            int length = stack.size();
            for (int i = 0; i < length; i++) {
                TreeNode top = stack.poll();
                if (top.left != null) stack.offer(top.left);
                if (top.right != null) stack.offer(top.right);
            }
        }
        return level;
    }

    /*
    111. Minimum Depth of Binary Tree
    Breadth First Search
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;

        // Recursive BFS
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        return (left != 0 && right != 0) ? Math.min(left, right) + 1 : (left != 0) ? left + 1 : right + 1;
    }

    /*
    331. Verify Pre-order Serialization of Binary Tree
    Terminology:
    in-degree of a node : 1
    out-degree of a non-null node: 2
     */
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() == 0) return false;
        String[] nodes = preorder.split(",");
        int degree = -1;
        for (String node : nodes) {
            if (++degree > 0) return false;
            if (!node.equals("#")) degree -= 2;
        }
        return degree == 0;
    }

    /*
    Graph Valid Tree
     */

    /*
    Reconstruct Itinerary
     */

    /*
    Binary Tree Vertical Order Traversal
     */
    public List<Integer> verticalOrderTraversal(TreeNode root) {
        TreeMap<Integer, List<Integer>> ht = new TreeMap<>();
        List<Integer> a1 = new ArrayList<>();
        int level = 0;  // '0' is the root vertical order

        // Start recursive method
        verticalRecursion(root, level, ht, a1);

        // Return result
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

    /*
    Go down on the right branch first
    Add the node value if it's the first node of the new level
     */
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
    Binary Search Tree Iterator
     */

    /*
    Closest Binary Search Tree Value I
     */

    /*
    Closest Binary Search Tree Value II
     */

    /*
    Inorder Successor in BST
     */

    /*
    Convert Sorted Array to Binary Search Tree
     */

    /*
    Convert Sorted Linked List to Binary Search Tree
     */

    /*
    Lowest Common Ancestor of a Binary Search Tree
     */

    /*
    Recover Binary Search Tree
     */

    /*
    Unique Binary Search Trees
     */

    /*
    Unique Binary Search Trees II
     */

    /*
    Validate Binary Search Tree
     */

    /*
    Verify Pre-order Sequence in Binary Search Tree
     */

    /*
    89. Gray Code
    Math problem: G(i) = i ^ (i/2)
     */
    public List<Integer> grayCode(int n) {
        List<Integer> ans = new ArrayList<>();
        int combinations = 1 << n;
        for (int i = 0; i < combinations; i++) {
            ans.add(i ^ i >> 1);    // i-th element and 'next' element by right shifting 1 bit
        }
        return ans;
    }

    /*
    77. Combinations
    Given two integers n and k return all possible combinations of k numbers out i...n
    Input n = 4, k = 2
    {
        {2,4},
        {3,4},
        {2,3},
        {1,2},
        {1,3},
        {1,4}
    }
    Math problem: C(n,k) = C(n-1,k-1) + C(n-1,k)
     */

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();

        if (k > n || k < 0) return ans;

        if (k == 0) {
            ans.add(new ArrayList<>());
            return ans;
        }

        ans = combine(n - 1, k - 1);
        for (List<Integer> list : ans) {
            list.add(n);
        }

        ans.addAll(combine(n - 1, k));

        return ans;
    }

    /*
    39. Combination Sum
    Approach: cannot apply 3Sum solution because we don't have a definite number of candidates
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (candidates.length == 0 || candidates == null) return ans;
        Arrays.sort(candidates);
        combinationSumRecursion(candidates, target, ans, new ArrayList<>(), 0);
        return ans;
    }

    private void combinationSumRecursion(int[] candidates, int target, List<List<Integer>> ans,
                                         List<Integer> list, int startPos) {
        if (target == 0) {
            ans.add(new ArrayList<>(list));
        } else {
            for (int i = startPos; i < candidates.length && target >= candidates[i]; i++) {
                int curVal = target - candidates[i];
                list.add(candidates[i]);
                combinationSumRecursion(candidates, curVal, ans, list, i);
                list.remove(list.size() - 1);
            }
        }
    }

    /*
    40. Combination Sum II
    Candidates can only be used once
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (candidates == null && candidates.length == 0) return ans;
        Arrays.sort(candidates);
        combinationSum2Recursion(candidates, target, ans, new ArrayList<>(), 0);
        return ans;
    }

    private void combinationSum2Recursion(int[] candidates, int target, List<List<Integer>> ans, List<Integer> list,
                                          int start) {
        if (target == 0) {
            ans.add(new ArrayList<>(list));
        } else {
            for (int i = start; i < candidates.length && target >= candidates[i]; i++) {
                if (i != start && candidates[i] == candidates[i - 1]) continue;   // Skip
                int curVal = target - candidates[i];
                list.add(candidates[i]);
                combinationSum2Recursion(candidates, curVal, ans, list, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }

    /*
    418. Combination Sum III
    Find all possible combination of k numbers that add up to a number n, given that only numbers from 1  to 9 can be
    used and each combination should be a unique set of numbers
    Given k = 3, n = 7

    Approach: Use an internal 'candidates' array 1-9, n is the 'target' and k is an additional condition
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        if (k == 0 || n == 0) return ans;

        int[] candidates = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        combinationSum3Recursion(k, n, candidates, ans, new ArrayList<>(), 0);
        return ans;
    }

    private void combinationSum3Recursion(int k, int target, int[] candidates, List<List<Integer>> ans,
                                          List<Integer> list, int start) {
        if (k == 0 && target == 0) {
            ans.add(new ArrayList<>(list));
        } else {
            for (int i = start; i < candidates.length && k > 0; i++) {
                if (i != start && candidates[i] == candidates[i - 1]) continue;
                list.add(candidates[i]);
                combinationSum3Recursion(k - 1, target - candidates[i], candidates, ans, list, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }

    /*
    1. Two Sum to zero
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) return new int[]{};
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                if (i < map.get(target - nums[i])) {
                    list.add(i);
                    list.add(map.get(target - nums[i]));
                    break;
                }
                if (i > map.get(target - nums[i])) {
                    list.add(map.get(target - nums[i]));
                    list.add(i);
                    break;
                }
            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    /*
    15. Three Sum to zero
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) return res;
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;  // Skip duplicates
            int target = 0 - nums[i];
            int head = i + 1;
            int tail = nums.length - 1;

            // TODO
            // Consider the two possible largest/smallest numbers to make an early exit

            while (head < tail) {
                if (nums[head] + nums[tail] == target) {
                    res.add(Arrays.asList(nums[i], nums[head], nums[tail]));
                    // Skip through duplicates
                    while (head < tail && nums[head] == nums[head + 1]) head++;
                    while (head < tail && nums[tail - 1] == nums[tail]) tail--;
                    head++;
                    tail--;
                } else if (nums[head] + nums[tail] < target) {
                    head++;
                } else if (nums[head] + nums[tail] > target) {
                    tail--;
                }
            }
        }
        return res;
    }

    /*
    16. Three Sum Closest
    Return the sum is closest to a given number
     */
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) return 0;
        int res = 0;
        int distance_from_target = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) continue;
            int head = i + 1;
            int tail = nums.length - 1;
            while (head < tail) {
                int sum = nums[i] + nums[head] + nums[tail];
                if (sum != target) {
                    int tmp = distance_from_target;
                    distance_from_target = Math.min(distance_from_target, Math.abs(target - sum));
                    if (distance_from_target != tmp)
                        res = sum;
                    if (sum > target) tail--;
                    else if (sum < target) head++;
                } else {
                    return sum;
                }
            }
        }
        return res;
    }

    /*
    18. Four Sum
    Apply threeSum and twoSum solutions. Make early exit check to improve run-time complexity
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 4) return res;
        Arrays.sort(nums);
        // Early check
        if (4 * nums[0] > target || 4 * nums[nums.length - 1] < target) return res; // Too small
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) continue;  // Skip duplicate
            if (nums[i] + 3 * nums[nums.length - 1] < target) continue;  // Too small, skip
            if (4 * nums[i] > target) break;    // Too big, early exit
            if (4 * nums[i] == target) {    // Just right, early exit
                if (i + 3 < nums.length && nums[i] == nums[i + 3]) {
                    res.add(Arrays.asList(nums[i], nums[i + 1], nums[i + 2], nums[3]));
                }
                break;
            }
            // Otherwise, leverage the current pick to look for sub-sequence sum
            threeSumHelper(nums, target - nums[i], i + 1, nums.length - 1, res, nums[i]);
        }
        return res;
    }

    private void threeSumHelper(int[] nums, int target, int lo, int hi, List<List<Integer>> res, int pick1) {
        if (lo > hi || (hi - lo + 1) < 3) return;
        // Early check
        if (3 * nums[lo] > target || 3 * nums[hi] < target) return;  // Too small, early exit
        for (int i = lo; i < hi - 1; i++) {
            if (i > lo && nums[i - 1] == nums[i]) continue;
            if (nums[i] + 2 * nums[hi] < target) continue;  // Too small, skip
            if (3 * nums[i] > target) break;    // Too big, skip
            if (3 * nums[i] == target) {
                if (i + 1 < hi && nums[i] == nums[i + 2]) {
                    res.add(Arrays.asList(pick1, nums[i], nums[i], nums[i]));
                }
                break;
            }
            // Otherwise, leverage the current pick to look for sub-sequence sum
            twoSumHelper(nums, target - nums[i], i + 1, hi, res, pick1, nums[i]);
        }
    }

    private void twoSumHelper(int[] nums, int target, int lo, int hi, List<List<Integer>> res, int pick1, int pick2) {
        if (lo > hi || (hi - lo + 1) < 2) return;
        // Early check
        if (2 * nums[lo] > target || 2 * nums[hi] < target) return;
        int i = lo;
        int j = hi;
        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum == target) {
                res.add(Arrays.asList(pick1, pick2, nums[i], nums[j]));
                while (i < j && nums[i] == nums[i - 1]) i++;
                while (i < j && nums[j - 1] == nums[j]) j--;
                i++;
                j--;
            } else if (sum < target) {
                i++;
            } else if (sum > target) {
                j--;
            }
        }
    }

    /*
    373. Find K Pairs with Smallest Sums
    Given two integer arrays sorted in ascending order and an integer k
    Define a pair (u,v) which consist of one element from the 1st array and one element from the 2nd array
    1st array = {1,7,11}
    2nd array = {2,4,6} with k = 3
    All sequences: [1,2],[1,4],[1,6] [7,2],[7,4],[11,2] [7,6],[11,4],[11,6]
    Returns: [1,2],[1,4],[1,6]

    Test 1: {1,1,2} {1,2,3}

    Approach: Use "PriorityQueue", "Find nth ugly number", "Merge K sorted arrays"
     */
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> ans = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k == 0) return ans;

        int counter = 0;
        int[] indexes = new int[nums1.length];
        while (counter++ < k) {
            int min_sum = Integer.MAX_VALUE;
            int curr = -1;
            for (int i = 0; i < nums1.length; i++) {
                if (indexes[i] >= nums2.length) continue; // Out of bound in 2nd array
                if (nums1[i] + nums2[indexes[i]] < min_sum) {
                    min_sum = nums1[i] + nums2[indexes[i]];
                    curr = i;
                }
            }
            if (curr == -1) break;  // Early exit
            ans.add(new int[]{nums1[curr], nums2[indexes[curr]]});
            indexes[curr]++;
        }
        return ans;
    }

    /*
    239. Sliding Window Maximum
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        //TODO
        return new int[]{};
    }

    /*
    263. Ugly Number
     */
    public boolean isUgly(int num) {
        if (num == 0) return false;
        while (num % 2 == 0) num /= 2;
        while (num % 3 == 0) num /= 3;
        while (num % 5 == 0) num /= 5;
        return num == 1;
    }

    /*
    264. Ugly Number 2
     */
    public int nthUglyNumber(int n) {
        if (n < 1) return 0;
        int idx2 = 0, idx3 = 0, idx5 = 0;
        int factor2 = 1, factor3 = 1, factor5 = 1;
        int[] ugly = new int[n];
        int next = 1;
        for (int i = 0; i < n; i++) {
            ugly[i] = next;
            next = Integer.MAX_VALUE;
            if (factor2 == ugly[i]) factor2 = 2 * ugly[idx2++];
            if (factor3 == ugly[i]) factor3 = 3 * ugly[idx3++];
            if (factor5 == ugly[i]) factor5 = 5 * ugly[idx5++];
            next = Math.min(factor2, Math.min(factor3, factor5));
        }
        return ugly[n - 1];
    }

    /*
    313. Super Ugly Number
    Expand Ugly Number 2 solution to array of indexes and array of factors
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        if (n < 1 || primes.length == 0) return 0;
        int[] index = new int[primes.length];
        int[] factors = new int[primes.length];
        Arrays.fill(factors, 1);
        int[] ugly = new int[n];
        int next = 1;
        for (int i = 0; i < n; i++) {
            ugly[i] = next;
            next = Integer.MAX_VALUE;
            for (int k = 0; k < primes.length; k++) {
                if (factors[k] == ugly[i]) factors[k] = primes[k] * ugly[index[k]++];
                next = Math.min(next, factors[k]);
            }
        }
        return ugly[n - 1];
    }

    /*
    279. Perfect Squares
     */
    public int numSquares(int n) {
        //TODO
        return -1;
    }

    /*
    Merge K sorted arrays
     */

    /*
    338. Counting Bits
    Apply bit-wise comparison

    For instance: num = 5
    000 base case
    001 from 000 (0)
    010 from 000 (0)
    011 from 010 (2)
    100 from 000 (0)
    101 from 100 (4)
    Use bitwise operation i - (i & (-i)) to find previous index/value and increment 1
     */
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            int prevIdx = i - (i & (-i));
            System.out.printf("current index = %d\tprevious index = %d\n", i, prevIdx);
            res[i] = res[prevIdx] + 1;
        }
        return res;
    }

    /*
    136. Single Number
    Given an array of integers, every element appears twice except for one.
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int n : nums) {
            res ^= n;
        }
        return res;
    }

    /*
    137. Single Number 2
    Given an array of integers, every element appears three times except for one.
     */
    public int singleNumber2(int[] nums) {
        int mask1 = 0, mask2 = 0;
        for (int i = 0; i < nums.length; i++) {
            mask1 = (mask1 ^ nums[i] & ~mask2);
            mask2 = (mask2 ^ nums[i] & ~mask1);
        }
        return mask1;
    }

    /*
    260. Single Number 3
    Given an array of integers, exactly two elements appear only once and all the other elements appear exactly twice
     */
    public int[] singleNumber3(int[] nums) {
        int x_xor_y = 0;
        for (int n : nums) {
            x_xor_y ^= n;
        }
        int diff_mask = x_xor_y &~ (x_xor_y - 1);   // Find the last bit set different between x and y
        int[] res = new int[2];
        for (int n : nums) {
            if ((n & diff_mask) != 0) {
                res[0] ^= n;
            } else {
                res[1] ^= n;
            }
        }
        return res;
    }
}

/*
#0#3.\|4M!6~\/c
 */