class Solution {
    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        Map<Integer, List<Integer>> adjMap = buildGraph(graph);
        boolean[] visited = new boolean[graph.length];
        findAllPath(0, graph.length-1, graph, visited, new ArrayList<Integer>(List.of(0)));
        return res;
    }
    
    public void findAllPath(int src, int dst, int[][] graph, boolean[] visited, List<Integer> curPath) {
        if(src == dst) {
            res.add(new ArrayList(curPath));
        }
        visited[src] = true;
        int[] neighbors = graph[src];
        for(int i : neighbors) {
            if(visited[i]) continue;
            curPath.add(i);
            findAllPath(i, dst, graph, visited, curPath);
            curPath.remove(curPath.size()-1);
        }
        visited[src] = false;
    }
    
    public Map<Integer, List<Integer>> buildGraph(int[][] graph) {
        Map<Integer, List<Integer>> graphMap = new HashMap<>();
        for(int i = 0; i < graph.length; i++) {
            graphMap.putIfAbsent(i, new ArrayList<Integer>());
            for(int j = 0; j < graph[i].length; j++) {
                //System.out.println(graphMap.get(0));
                List<Integer> tmp = graphMap.get(i);
                tmp.add(Integer.valueOf(graph[i][j]));
            }               
        }
        return graphMap;
    }
}
