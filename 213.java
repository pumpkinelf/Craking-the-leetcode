class Solution {
    public int rob(int[] nums) {
        //dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i])
        if(nums.length == 1) return nums[0];
        
        return Math.max(rob(nums, 0, nums.length-2) , rob(nums, 1, nums.length-1));
    }
    public int rob(int[] nums, int lo, int hi) {
        int include = 0, exclude = 0;
        for (int j = lo; j <= hi; j++) {
            int i = include, e = exclude;
            include = e + nums[j];
            exclude = Math.max(e, i);
        }
        return Math.max(include, exclude);
    }
}
