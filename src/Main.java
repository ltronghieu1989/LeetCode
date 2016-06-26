import java.util.List;

public class Main {
    private static LeetCode lc = new LeetCode();
    private static TopCoder tc = new TopCoder();

    public static void main (String[] args) {

//        String fronts = "abc,abc,def,cba,fed";
//        int[] backs = {24, 7, 63, 222, 190};

        String fronts = "aaaaaaaaaaaa,nopalindrome,steponnopets,emordnilapon,aaaaaaaaaaaa,steponnopets,nopalindrome,steponnopets,nopalindrome,bbbbbbbbbbbb,cannotbeused,cannotbeused,steponnopets,aaaaaaaaaaaa,nopalindrome,aaaaaaaaaaaa,nopalindrome,emordnilapon,steponnopets,nopalindrome";
        int[] backs = {4096, 131072, 64, 262144, 512, 1024, 65536, 2048, 32768, 1, 524288, 16384, 32, 4, 16, 2, 8, 128, 8192, 256};

//        String fronts = "aabaa,defg";
//        int[] backs = {99, 1};

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
