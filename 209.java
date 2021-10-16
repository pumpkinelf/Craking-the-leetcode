class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int minLen = nums.length + 1;
        int sum = 0;
        while(right < nums.length) {
            sum += nums[right];
            right++;
            
            while(sum >= target) {
                minLen = Math.min(minLen, right - left);
                sum -= nums[left];
                left++;
            }
        }
        return minLen == nums.length + 1 ? 0 : minLen;
    }
}
