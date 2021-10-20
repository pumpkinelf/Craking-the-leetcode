class RandomizedCollection {
    Random random;
    Map<Integer, Set<Integer>> valToIndex;
    List<Integer> nums;
    public RandomizedCollection() {
        valToIndex = new HashMap<>();
        nums = new ArrayList<>();
        random = new Random();
    }
    
    public boolean insert(int val) {
        nums.add(val);
        if(valToIndex.containsKey(val)) {
            valToIndex.get(val).add(nums.size()-1);
            return false;
        }
        else {
            valToIndex.put(val, new HashSet<Integer>());
            valToIndex.get(val).add(nums.size()-1);
            return true;
        }
    }
    
    public boolean remove(int val) {
        if(!valToIndex.containsKey(val)) {
            return false;
        } else {
            //int size = valToIndex.get(val).size();
            //System.out.println("size:" + size);
            int indexRemoved = valToIndex.get(val).iterator().next();
            if(valToIndex.get(val).size() == 1) {
                valToIndex.remove(val);
            } else {
                valToIndex.get(val).remove(indexRemoved);
            } 
            if(indexRemoved == nums.size()-1) {
                nums.remove(indexRemoved);

            } else {
                int lastVal = nums.get(nums.size() - 1);
                valToIndex.get(lastVal).remove(nums.size()-1);
                valToIndex.get(lastVal).add(indexRemoved);

                nums.set(indexRemoved, lastVal);
                nums.remove(nums.size() -1);
            }
            return true;
        }
    }
    
    public int getRandom() {
        int index = random.nextInt(nums.size());
        // System.out.println("index: " + index);
        // for(int i = 0; i < nums.size(); i++) {
        //     System.out.println(nums.get(i));
        // }
        return nums.get(index);
    }
}
