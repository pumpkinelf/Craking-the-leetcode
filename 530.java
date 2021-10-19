class Solution {
    int minDiff = Integer.MAX_VALUE;
    TreeNode preNode = null;
    public int getMinimumDifference(TreeNode root) {
        inorder(root);
        return minDiff;
    }
    public void inorder(TreeNode root) {
        if(root == null) return;
        inorder(root.left);
        if(preNode != null)
            minDiff = Math.min(minDiff, Math.abs(root.val - preNode.val));
        preNode = root;
        inorder(root.right);
    }
}
