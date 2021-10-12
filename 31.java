class Solution {
    public void nextPermutation(int[] nums) {
        if(nums.length == 1) return;
        int i = nums.length - 2;
        while(i >= 0 && nums[i] >= nums[i+1]) i--; // 2,4,3,2,1
        if(i >= 0) {
            int j = nums.length - 1;
            while(nums[j] <= nums[i]) j--;
            swap(nums,i,j);   //3,4,2,2,1
        }
        reverse(nums, i+1);
    }
    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    public void reverse(int[] nums, int i) {
        int j = nums.length - 1;
        int tmp = nums[i];
        while(i < j) {
            tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
            i++;
            j--;
        }
    }
}
