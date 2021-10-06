class Solution {
    public int deleteAndEarn(int[] nums) {
        int[] buckets = new int[20001];
        for(int i : nums) {
            buckets[i] += i;
        }
        int take = buckets[0];
        int skip = 0;
        // i+1 take = skip(i-1) + buckets[i+1]
        // i+1 skip = Math.max(take(i-1), skip(i-1))
        for(int i = 1; i < buckets.length; i++) {
            int takei = skip + buckets[i];
            int skipi = Math.max(skip, take);
            take = takei;
            skip = skipi;
        }
        return Math.max(take, skip);
    }
}
