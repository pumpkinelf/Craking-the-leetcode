class Solution {
   public boolean splitArraySameAverage(int[] A) {
        Arrays.sort(A);
        int cTot = 0;
        for(int i=0; i<A.length; i++) {
            cTot += A[i];
        }
        return bt(A, 0, 0, cTot, 0, A.length);
    }
    
    private boolean bt(int[] A, int start, int bTot, int cTot, int bLen, int cLen) {
        if(bTot != 0 && cTot != 0 && (double)bTot/bLen == (double)cTot/cLen) return true;
        
        for(int i=start; i<A.length; i++) {
		    // Avoid duplicates
            if(i != start && A[i] == A[i-1]) continue;
			
            if(bt(A, i+1, bTot+A[i], cTot-A[i], bLen+1, cLen-1)) return true;
        }
        return false;
    }
}
