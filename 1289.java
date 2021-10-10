class Solution {
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        //dp[i][j] minimum sum of path till element on row i using color j
        int[][] dp = new int[n][n];
        int minimumIndex = n;
        int minimum = 20100;
        int secondMinimum = 20100;
            
        for(int row = 0; row < n; row++) {
            for(int color = 0; color < n; color++) {
                if(row == 0) {
                    dp[row][color] = grid[0][color];
                } else {
                    if(minimumIndex == color) {
                       dp[row][color] = grid[row][color] + secondMinimum;  
                    } else {
                       dp[row][color] = grid[row][color] + minimum;  
                    }
                }
            }
            minimumIndex = n;
            minimum = 20100;
            secondMinimum = 20100;
            for(int i = 0; i < n; i++) {
                if(dp[row][i] < minimum) {
                    minimumIndex = i;
                    secondMinimum = minimum;
                    minimum = dp[row][i];
                } else if(dp[row][i] < secondMinimum) {
                    secondMinimum = dp[row][i];
                }
            }  
        }
        
        return minimum;
    }
}
