package com.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class badgeAccess {
    public static Map<String, List<Integer>> findFreqAccess (List<List<String>> logs) {
        Map<String, List<Integer>> userHistory = new HashMap<>();
        for(List<String> entry : logs) {
            userHistory.putIfAbsent(entry.get(0), new ArrayList<>());
            userHistory.get(entry.get(0)).add(Integer.valueOf(entry.get(1)));
        }
        Map<String, List<Integer>> res = new HashMap<>();
        for(String user : userHistory.keySet()) {
            List<Integer> history = userHistory.get(user);
            if(history.size() < 3)
                continue;
            List<Integer> freqAccess = new ArrayList<>();
            
            Collections.sort(history, (a, b) -> (a -b));
            int i = 0;
            Integer startTime = history.get(0);
            Integer endTime = history.get(0) + 100;
            List<Integer> tmp = new ArrayList<>();
            while(i < history.size()) {
                if(history.get(i) <= endTime) {
                    tmp.add(history.get(i));
                    i++;
                    continue;
                } 
                if(tmp.size() >=3) {
                    freqAccess.addAll(tmp);
                } 
                
                if(history.get(i) > tmp.get(tmp.size()-1) + 100) {
                    startTime = history.get(i);
                    tmp.clear();
                    tmp.add(history.get(i));
                } else {
                    startTime = tmp.get(tmp.size()-1);
                    tmp.clear();
                    tmp.add(startTime);
                    tmp.add(history.get(i));
                }
                endTime = startTime + 100;
                
                i++;
            }
            if(tmp.size() >= 3) {
                freqAccess.addAll(tmp);
            }
            if(!freqAccess.isEmpty())
                res.put(user, freqAccess);
        }
        return res;
    }
    
    public static void main(String[] args) {
        List<List<String>> logs = new ArrayList<>();
        logs.add(List.of("Jennifer", "1910"));
        logs.add(List.of("Jennifer", "1335"));
        logs.add(List.of("Jennifer", "730"));

        logs.add(List.of("Paul", "1300"));
        logs.add(List.of("Paul", "1315"));
        logs.add(List.of("Paul", "1405"));
        logs.add(List.of("Paul", "1355"));

        logs.add(List.of("John", "1615"));
        logs.add(List.of("John", "1640"));
        logs.add(List.of("John", "830"));
        logs.add(List.of("John", "835"));
        logs.add(List.of("John", "930"));
        logs.add(List.of("John", "915"));
        logs.add(List.of("John", "730"));
        logs.add(List.of("John", "1630"));

        Map<String, List<Integer>> res = findFreqAccess(logs);
        System.out.print(res);
    }
}
