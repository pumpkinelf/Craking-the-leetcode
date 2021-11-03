package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class accessLog {
    public static List<List<String>> findMinMax(List<List<String>> logs) {
        Map<String, List<Integer>> userMap = new HashMap<>();
        for(List<String> log : logs) {
            String user = log.get(1);
            Integer time = Integer.valueOf(log.get(0));
            userMap.putIfAbsent(user, new ArrayList<>());
            userMap.get(user).add(time);
        }
        List<List<String>> res = new ArrayList<>();
        for(String user : userMap.keySet()) {
            List<Integer> times = userMap.get(user);
            List<Integer> minMax = findMinMaxList(times);
            res.add(List.of(user, String.valueOf(minMax.get(0)), String.valueOf(minMax.get(1))));
        }
        return res;
    }
    
    public static  List<Integer> findMinMaxList(List<Integer> times) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < times.size(); i++) {
            if(times.get(i) > max) {max = times.get(i);}
            if(times.get(i) < min) {min = times.get(i);}
        }
        return List.of(min,max);
    }
    
    public static List<String> findFreqIn5Mins(List<List<String>> logs) {
        Collections.sort(logs, new Comparator<List<String>>() {
            @Override 
            public int compare(List<String> s1, List<String> s2) {
                Integer t1 = Integer.valueOf(s1.get(0));
                Integer t2 = Integer.valueOf(s2.get(0));
                if(t1.equals(t2)) {
                    return s1.get(1).compareTo(s2.get(1));
                } else {
                    return t1.compareTo(t2);
                }
            }
        });
        
        String topResource = "";
        int maxFreq = 0;
        Map<String, Integer> freqMap = new HashMap<>();
        int i = 0;
        int j = 0;
        while(i < logs.size()) {
            while(j < logs.size() && isWithin5Min(logs.get(i), logs.get(j))) {
                String resName = logs.get(j).get(2);
                if(!freqMap.containsKey(resName)) {
                    freqMap.put(resName, 0);
                }
                freqMap.put(resName, freqMap.get(resName) + 1);
                
                if(maxFreq < freqMap.get(resName)) {
                    maxFreq = freqMap.get(resName);
                    topResource = resName;
                }
                j++;
            }

            for (String key : freqMap.keySet()) {
                if (maxFreq < freqMap.get(key)) {
                    maxFreq = freqMap.get(key);
                    topResource = key;
                }
            }
            
            String resIName = logs.get(i).get(2);
            if(freqMap.get(resIName) == 1) {
                freqMap.remove(resIName);
            } else {
                freqMap.put(resIName, freqMap.get(resIName) - 1);
                
            }
            i++;
        }
        return List.of(topResource, String.valueOf(maxFreq));
    }

    static boolean isWithin5Min(List<String> l1, List<String> l2) {
        if(Integer.valueOf(l1.get(0)) + 300 >= Integer.valueOf(l2.get(0))) {
            return true;
        }
        return false;
    }

    public static Map<String, Map<String, Double>> buildResourceMap(List<List<String>> logs) {
        //sort the logs based on time then userId
        Collections.sort(logs, new Comparator<List<String>>(){
            @Override
            public int compare(List<String> s1, List<String> s2) {
                Integer t1 = Integer.valueOf(s1.get(0));
                Integer t2 = Integer.valueOf(s2.get(0));
                if(t1.equals(t2)) {
                    return s1.get(1).compareTo(s2.get(1));
                }
                return t1.compareTo(t2);
            }
        });
        
        Map<String, List<String>> userRes = new HashMap<>();
        for(List<String> log : logs) {
            String userId = log.get(1);
            userRes.putIfAbsent(userId, new ArrayList<>());
            userRes.get(userId).add(log.get(2));
        }
        
        Map<String, Map<String, Integer>> resNextFreq = new HashMap<>();
        resNextFreq.put("START", new HashMap<>());
        for(String userId : userRes.keySet()) {
            List<String> resSteps = userRes.get(userId);
            resNextFreq.get("START").put(resSteps.get(0), resNextFreq.get("START").getOrDefault(resSteps.get(0), 0)+1);
            for(int i = 0 ;i < resSteps.size(); i++) {
                String curStep = resSteps.get(i);
                String nextStep = (i + 1) == resSteps.size() ? "END" : resSteps.get(i+1);
                resNextFreq.putIfAbsent(curStep, new HashMap<>());
                resNextFreq.get(curStep).put(nextStep, resNextFreq.get(curStep).getOrDefault(nextStep, 0) + 1);
            }
        }

        Map<String, Integer> resTotalPossibility = new HashMap<>();
        for(String resId : resNextFreq.keySet()) {
            int totalPos = 0;
            for(Integer freq : resNextFreq.get(resId).values()) {
                totalPos += freq;
            }
            resTotalPossibility.put(resId, totalPos);
        }

        Map<String, Map<String, Double>> res = new HashMap<>();
        for(String resId : resNextFreq.keySet()) {
            res.put(resId, new HashMap<>());
            Map<String, Integer> nextStep = resNextFreq.get(resId);
            int totalPos = resTotalPossibility.get(resId);
            for(String next : nextStep.keySet()) {
                double pos = (double) nextStep.get(next) / (double)totalPos;
                res.get(resId).put(next, pos);
            }
        }
        
        return res;
    }
    
    public static void main(String[] args) { 
        List<List<String>> logs1 = new ArrayList<>();
        logs1.add(List.of("58523", "user_1", "resource_1"));
        logs1.add(List.of("62314", "user_2", "resource_2"));
        logs1.add(List.of("54001", "user_1", "resource_3"));
        logs1.add(List.of("200", "user_6", "resource_5"));
        logs1.add(List.of("215", "user_6", "resource_4"));
        logs1.add(List.of("54060", "user_2", "resource_3"));
        logs1.add(List.of("53760", "user_3", "resource_3"));
        logs1.add(List.of("58522", "user_22", "resource_1"));
        logs1.add(List.of("53651", "user_5", "resource_3"));
        logs1.add(List.of("2", "user_6", "resource_1"));
        logs1.add(List.of("100", "user_6", "resource_6"));
        logs1.add(List.of("400", "user_7", "resource_2"));
        logs1.add(List.of("100", "user_8", "resource_6"));
        logs1.add(List.of("54359", "user_1", "resource_3"));
        
        //[["58523", "user_1", "resource_1"],
        // ["62314", "user_2", "resource_2"],
        // ["54001", "user_1", "resource_3"],
        // ["200", "user_6", "resource_5"],
        // ["215", "user_6", "resource_4"],
        // ["54060", "user_2", "resource_3"],
        // ["53760", "user_3", "resource_3"],
        // ["58522", "user_22", "resource_1"],
        // ["53651", "user_5", "resource_3"],
        // ["2", "user_6", "resource_1"],
        // ["100", "user_6", "resource_6"],
        // ["400", "user_7", "resource_2"],
        // ["100", "user_8", "resource_6"],
        // ["54359", "user_1", "resource_3"]]

        List<List<String>> logs2 = new ArrayList<>();
//        logs2.add(List.of("300", "user_1", "resource_3"));
//        logs2.add(List.of("599", "user_1", "resource_3"));
//        logs2.add(List.of("900", "user_1", "resource_3"));
//        logs2.add(List.of("1199", "user_1", "resource_3"));
//        logs2.add(List.of("1201", "user_1", "resource_3"));
//        logs2.add(List.of("1202", "user_1", "resource_3"));
//        logs2.add(List.of("1200", "user_1", "resource_3"));
         logs2.add(List.of("300", "user_10", "resource_5"));
        
        
        
//        List<List<String>> res = findMinMax(logs1);
//        System.out.println(res);
//
//        List<String> res2 = findFreqIn5Mins(logs1);
//        System.out.println(res2);
//        res2 = findFreqIn5Mins(logs2);
//        System.out.println(res2);

//        {'START': {'resource_1': 0.25, 'resource_6': 0.125, 'resource_2': 0.125, 'resource_3': 0.5}, 
//            'resource_1': {'resource_6': 0.3333333333333333, 'END': 0.6666666666666666},
//            'resource_6': {'resource_5': 0.5, 'END': 0.5},
//            'resource_5': {'resource_4': 1.0}, 
//            'resource_4': {'END': 1.0}, 
//            'resource_2': {'END': 1.0}, 
//            'resource_3': {'END': 0.4, 'resource_3': 0.2, 'resource_1': 0.2, 'resource_2': 0.2}}

        Map<String, Map<String, Double>>  res = buildResourceMap(logs1);
        System.out.println(res);

    }
}
