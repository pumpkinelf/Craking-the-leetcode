class Solution {
    public String removeDuplicates(String s, int k) {
        Stack<Node> st = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            if(st.isEmpty()) {
                st.add(new Node(1, s.charAt(i)));
            } else {
                if(s.charAt(i) == st.peek().val) {
                    if(st.peek().freq == k-1) {
                        st.pop();
                    } else {
                        st.peek().freq = st.peek().freq + 1;
                    }
                } else {
                    st.add(new Node(1, s.charAt(i)));
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!st.isEmpty()) {
            Node cur = st.pop();
            for(int i = 0; i < cur.freq; i++)
                sb.insert(0, cur.val);
        }
        return sb.toString();
    }
    class Node {
        int freq;
        Character val;
        Node(int freq, Character val) {
            this.freq = freq;
            this.val = val;
        }
    }
}
