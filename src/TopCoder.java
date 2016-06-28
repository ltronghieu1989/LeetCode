import java.util.*;

public class TopCoder {
    /*
    PalindromeGame
    Greedy, Simple Search, Iteration

    Given a String[] fronts and int[] backs describing the set of cards. Each card ha a string written on the front and
    a number on the back. The strings are the same length. Pick the cards and place them in a row with the front sides
    visible such that concatenated string is a palindrome. Fix maximum score

    Solution: use recursive method (Greedy algorithm/top-down) to build a boolean[] masks to find the maximum at each recursion

    // PalindromeGame tests
//        String fronts = "abc,abc,def,cba,fed";
//        int[] backs = {24, 7, 63, 222, 190};
//        String fronts = "aaaaaaaaaaaa,nopalindrome,steponnopets,emordnilapon,aaaaaaaaaaaa,steponnopets,nopalindrome,steponnopets,nopalindrome,bbbbbbbbbbbb,cannotbeused,cannotbeused,steponnopets,aaaaaaaaaaaa,nopalindrome,aaaaaaaaaaaa,nopalindrome,emordnilapon,steponnopets,nopalindrome";
//        int[] backs = {4096, 131072, 64, 262144, 512, 1024, 65536, 2048, 32768, 1, 524288, 16384, 32, 4, 16, 2, 8, 128, 8192, 256};
//        String fronts = "aabaa,defg";
//        int[] backs = {99, 1};
//        int ans = tc.PalindromeGame(fronts.split(","), backs);
//        System.out.println("Max: " + ans);
     */
    public int PalindromeGame(String[] fronts, int[] backs) {
        if (fronts.length == 0 || fronts.length != backs.length) return 0;
        boolean[] used = new boolean[fronts.length];

        int ans = GreedyPalindromeGame(fronts, backs, used);
//        int ans = RecursivePalindromeGame(fronts, backs, used);
        return ans;
    }

    /*
    Recursive version
     */
    private int RecursivePalindromeGame(String[] fronts, int[] backs, boolean[] used) {

        int maxi = -1;
        int maxj = -1;
        int m = 0;

        // Greedy
        for (int i = 0; i < fronts.length; ++i) {
            if (used[i]) continue;
            for (int j = 0; j < fronts.length; ++j) {
                if (i == j || used[j]) continue;
                if (isPalindromeStrings(fronts[i], fronts[j]) && backs[i] + backs[j] > m) {
                    maxi = i;
                    maxj = j;
                    m = backs[i] + backs[j];
                }
            }
        }

        // Recursive
        if (maxi != -1) {
            used[maxi] = true;
            used[maxj] = true;
            return m + RecursivePalindromeGame(fronts, backs, used);
        } else {
            m = 0;
            for (int i = 0; i < fronts.length; ++i) {
                if (!used[i] && isPalindromeStrings(fronts[i], fronts[i]) && backs[i] > m) {
                    m = backs[i];
                }
            }
            return m;
        }
    }

    /*
    Iterative version
     */
    private int GreedyPalindromeGame(String[] fronts, int[] backs, boolean[] used) {
        int maxi = -1;
        int maxj = -1;
        int tmp = 0;
        int m = 0;

        // Greedy
        for (int i = 0; i < fronts.length; ++i) {
            if (used[i]) continue;
            tmp = 0;
            for (int j = 0; j < fronts.length; ++j) {
                if (i == j || used[j]) continue;
                if (isPalindromeStrings(fronts[i], fronts[j]) && backs[i] + backs[j] > tmp) {
                    maxi = i;
                    maxj = j;
                    tmp = backs[i] + backs[j];
                }
            }
            if (tmp > 0) {
                m += tmp;
                used[maxi] = true;
                used[maxj] = true;
            }
        }

        // Greedy
        tmp = 0;
        maxi = -1;
        for (int i = 0; i < fronts.length; ++i) {
            if (!used[i] && isPalindromeStrings(fronts[i], fronts[i]) && backs[i] > tmp) {
                tmp = backs[i];
                maxi = i;
            }
        }
        if (tmp > 0) {
            m += tmp;
            used[maxi] = true;
        }

        return m;
    }

    private boolean isPalindromeStrings(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(b.length() - i - 1)) return false;
        }
        return true;
    }

    /*
    Greedy Algorithm
    Example 1: Activity-selection problem
    Select a maximum size subset of mutually compatible activities. Each activity will have start time and finish time
    Assume the activities are sorted in monotonically increasing order of finish time

    Note: Add a fictitious activity with s(0) = 0 and f(0) = 0 to the original start/finish arrays so that the first
    sub-problem S(0) is the entire set.

    // Activity Selector (Greedy Algorithm) tests
//        int[] start_times = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
//        int[] finish_times = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
//        List<Integer> ans = tc.ActivitySelector(start_times, finish_times);
//        for (Integer i : ans) {
//            System.out.printf("%d ", i);
//        }

     */
    public List<Integer> ActivitySelector(int[] start_times, int[] finish_times) {
        List<Integer> ans = new ArrayList<>();
        if (start_times.length == 0 || start_times.length != finish_times.length) return ans;
        int[] s = new int[start_times.length + 1];
        int[] f = new int[finish_times.length + 1];
        System.arraycopy(start_times, 0, s, 1, start_times.length);
        System.arraycopy(finish_times, 0, f, 1, finish_times.length);
//        RecursiveActivitySelector(s, f, 0, f.length, ans);
        GreedyActivitySelector(s, f, ans);
        return ans;
    }

    private void RecursiveActivitySelector(int[] s, int[] f, int k, int n, List<Integer> ans) {
        int m = k + 1;
        while (m < n && s[m] < f[k])   // Find the first activity in Sk to finish
            m++;
        if (m < n) {
            ans.add(m);
            RecursiveActivitySelector(s, f, m, n, ans);
        }
    }

    /*
    Iterative version of RecursiveActivitySelector
      */
    private void GreedyActivitySelector(int[] s, int[] f, List<Integer> ans) {
        int n = f.length;
        ans.add(1);
        int k = 1;
        for (int m = 2; m < n; m++) {
            if (s[m] > f[k]) {
                ans.add(m);
                k = m;
            }
        }
    }

    /*
    1. Determine the optimal substructure of the problem
    2. Develop a recursive solution
    3. Show that we make greedy choice, then only one sub-problem remains
    4. Show that it's safe to make greedy choice
    5. Develop a recursive algorithm that implements the greedy strategy
    6. Convert the recursive algorithm to an iterative algorithm
     */

    /*
    LampGrid
    Search

    A row in the grid is considered lit if all the lamps in that row are 'on', Jack must make exactly K flips.
    The K flips do not necessarily have to be performed on K distinct switches. his goal is to have as many lit rows
    as possible after making those flips.

    Given a String[] initial, where j-th character of the i-th element is '1' if the lamp i row i, column j is initially
    'on' and '0' otherwise. Return the maximal number of rows that can be lit after performing exactly K flips.

    // LampsGrid tests
//        String[] initial = {"001", "101", "001", "000", "111", "001", "101", "111", "110", "000", "111", "010", "110", "001"};
//        int ans = tc.LampsGrid(initial, 6);
//        System.out.println(ans);
//        String[] initial2 = {"01", "10", "01", "01", "10"};
//        ans = tc.LampsGrid(initial2, 1);
//        System.out.println(ans);
     */
    public int LampsGrid(String[] initial, int K) {
        int res = 0;
        Set<String> mySet = new HashSet<>();
        for (String s : initial) {
            mySet.add(s);
        }
        for (String s : mySet) {
            if (checkLampsGrid(s, K)) {
                int count = 0;
                for (String it : initial) {
                    if (s.equals(it)) count++;
                }
                res = Math.max(res, count);
            }
        }
        return res;
    }

    private boolean checkLampsGrid(String s, int k) {
        int zeros = 0;
        for (char c : s.toCharArray()) {
            if (c == '0') zeros++;
        }
        if (k < zeros)
            return false;
        else if (k == zeros)
            return true;
        k -= zeros;
        return k % 2 == 0;
    }

}
