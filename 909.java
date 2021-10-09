class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        Set<Integer> visited = new HashSet<>();
        visited.add(1);
        int step = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int t = 0; t <size; t++) {
                int cur = queue.poll();
                if(cur == n*n) return step;
                for(int i = 1; i <= 6 ; i++) {
                    int next = cur + i;
                    if(next > n*n) continue;
                    int row = boardNumToRow(next, n);
                    int col = boardNumToCol(next, n);
                    if(board[row][col] != -1) 
                        next = board[row][col];
                    if(!visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }            
            step++;
        }
        return -1;
    }
    
    public int boardNumToRow(int num, int n) {
        return n - 1 - (num-1)/n;
    }
    public int boardNumToCol(int num, int n) {
        if((num-1)/n % 2 == 0) //from left to right
            return (num-1) % n;
        else 
            return n - 1 - (num - 1) % n;
    }
    public int boardRowColToNum(int row, int col, int n) {
        if(row % 2 == 0) 
            return n * (n - row) - col;
        else 
            return n * (n - row) - (n - 1 - col);
    }
}
