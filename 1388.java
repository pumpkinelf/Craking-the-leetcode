class Solution {
    public int maxSizeSlices(int[] slices) {
        int m = slices.length, n = m / 3;
	    int[] slices1 = Arrays.copyOfRange(slices, 0, m-1);
	    int[] slices2 = Arrays.copyOfRange(slices, 1, m);
	    return Math.max(maxSum(slices1, n), maxSum(slices2, n));
    }
    public int maxSum(int[] slices, int n) {
        int[][] dp = new int[slices.length+1][n+1]; //maxium using first i elements and chooes j out of it
        for(int i = 1; i <= slices.length; i++) {
            for(int j = 1; j <= n; j++){
                if(i == 1)
                    dp[i][j] = slices[0];
                else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-2][j-1] + slices[i-1]);
                }
            }
        }
        return dp[slices.length][n];
    }
}
