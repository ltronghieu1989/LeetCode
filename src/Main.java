import java.util.List;

public class Main {
    private static LeetCode lc = new LeetCode();
    private static TopCoder tc = new TopCoder();

    public static void main(String[] args) {

//        String fronts = "abc,abc,def,cba,fed";
//        int[] backs = {24, 7, 63, 222, 190};

        String fronts = "aabaa,defg";
        int[] backs = {99, 1};

        int ans = tc.PalindromeGame(fronts.split(","), backs);
        System.out.println(ans);

//        int[] start_times = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
//        int[] finish_times = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
//        List<Integer> ans = tc.ActivitySelector(start_times, finish_times);
//        for (Integer i : ans) {
//            System.out.printf("%d ", i);
//        }
    }
}
