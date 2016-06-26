import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopCoder {
    /*
    PalindromeGame
    Greedy, Simple Search, Iteration

    Given a String[] fronts and int[] backs describing the set of cards. Each card ha a string written on the front and
    a number on the back. The strings are the same length. Pick the cards and place them in a row with the front sides
    visible such that concatenated string is a palindrome. Fix maximum score

    Solution: use recursive method (Greedy algorithm/top-down) to build a boolean[] masks to find the maximum at each recursion
     */
    public int PalindromeGame(String[] fronts, int[] backs) {
        if (fronts.length == 0 || fronts.length != backs.length) return 0;
        boolean[] used = new boolean[fronts.length];
//        return RecursivePalindromeGame(fronts, backs, used);
        return GreedyPalindromeGame(fronts, backs, used);
    }

    // TODO convert this recursive procedure to Greedy/Iterative procedure
    private int RecursivePalindromeGame(String[] fronts, int[] backs, boolean[] used) {

        int maxi = -1;
        int maxj = -1;
        int m = -1;

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
                if (!used[i] && isPalindromeStrings(fronts[i], fronts[i]) && backs[i] > m)
                    m = backs[i];
            }
            return m;
        }
    }

    private int GreedyPalindromeGame(String[] fronts, int[] backs, boolean[] used) {
        int maxi = -1;
        int maxj = -1;
        int m = 0;

        for (int i = 0; i < fronts.length; ++i) {
            if (used[i]) continue;
            for (int j = 0; j < fronts.length; ++j) {
                if (i == j || used[j]) continue;
                if (isPalindromeStrings(fronts[i], fronts[j])) {
                    maxi = i;
                    maxj = j;
                    m += backs[i] + backs[j];
                    used[maxi] = true;
                    used[maxj] = true;
                }
            }
        }

        if (maxi == -1) {
            m = 0;
            for (int i = 0; i < fronts.length; ++i) {
                if (!used[i] && isPalindromeStrings(fronts[i], fronts[i]) && backs[i] > m)
                    m = backs[i];
            }
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
}
