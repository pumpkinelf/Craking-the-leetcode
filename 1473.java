class Solution {
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        int[][] dp = new int[target+1][n];
        int[][] nextHouse = new int[target+1][n];
        for (int i = 0; i <= target; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE);
                Arrays.fill(nextHouse[i], Integer.MAX_VALUE);
        }
        if (houses[0] == 0) {
            for (int i = 0; i < n; i++) {
                dp[1][i] = cost[0][i];
            } 
        } else {
            dp[1][houses[0]-1] = 0;
        }
        
        for (int i = 1; i < m; i++) {
            int current = houses[i];
            for (int j = 1; j <= i + 1 && j <= target; j++) {
                int[] minIndices = getTwoIdsForMinimumCosts(dp[j-1]);
                if (current == 0) {
                    for (int k = 0; k < n; k++) {
                        // to paint the ith house with color k+1, we need either
                        //     the last house is painted with color k+1, for which the cost is dp[j][k]
                        //     or the last house is painted with a different color from k+1 and has the smallest cost within dp[j-1]
                        int min = Math.min(dp[j][k], dp[j-1][k == minIndices[0] ? minIndices[1] : minIndices[0]]);
                        if (min == Integer.MAX_VALUE) {
                            continue;
                        }
                        nextHouse[j][k] = min + cost[i][k];
                    }
                } else {
                    nextHouse[j][current - 1] = Math.min(dp[j][current - 1], dp[j-1][current - 1 == minIndices[0] ? minIndices[1]: minIndices[0]]);
                }
            }
            //int[][] temp = dp;
            dp = nextHouse;
            nextHouse = new int[target+1][n];
            for (int p = 0; p <= target; p++) {
                Arrays.fill(nextHouse[p], Integer.MAX_VALUE);
            }
        }
        int result = dp[target][getTwoIdsForMinimumCosts(dp[target])[0]];
        if (result == Integer.MAX_VALUE) {
            return -1;
        } else {
            return result;
        }
    }
    
    int[] getTwoIdsForMinimumCosts(int[] costs) {
        if (costs.length == 1) {
            return new int[]{0, 0};
        }
        int[] result = new int[2];
        result[0] = costs[0] <= costs[1] ? 0 : 1;
        result[1] = 1 - result[0];
        for (int i = 2; i < costs.length; i++) {
            if (costs[i] < costs[result[0]]) {
                result[1] = result[0];
                result[0] = i;
            } else if (costs[i] < costs[result[1]]) {
                result[1] = i;
            }
        }
        return result;
    }
    
}
