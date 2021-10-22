class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for(String str : strs) {
            char[] freq = new char[26];
            for(int i = 0; i < str.length(); i++) {
                freq[str.charAt(i) - 'a']++;
            }
            String encodedStr = Arrays.toString(freq);
            if(map.containsKey(encodedStr)) {
                map.get(encodedStr).add(str);
            } else {
                map.put(encodedStr, new ArrayList<>(List.of(str)));
            }
        }
        return new ArrayList<>(map.values());
    }
}
