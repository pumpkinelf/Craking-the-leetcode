/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution {
    public Node connect(Node root) {
        Node headNext = null; //head of next level
        Node curNext = null; //current node on next level
        Node cur = root; //current node on current level
        while(cur != null) {
            while(cur != null) {
                if(cur.left!=null) {
                    if(curNext != null) {
                        curNext.next = cur.left;
                    } else {
                        headNext = cur.left;
                    }
                    curNext = cur.left;
                }
                if(cur.right!=null) {
                    if(curNext != null) {
                        curNext.next = cur.right;
                    } else {
                        headNext = cur.right;
                    }
                    curNext = cur.right;
                }
                cur = cur.next;
            }
            cur = headNext;
            headNext = null;
            curNext = null;
        }
        return root;
    }
    
  
}
