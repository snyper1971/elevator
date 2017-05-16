/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LeoIvanSpenc
 */
public class ElevatorDriver {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<String> fileContents = new ArrayList<>();
        String mode;
        List<Map> elevatorProperties = new ArrayList<>();
        
        Elevator elevator;        
        
        if (args.length == 2) {
            if (args[0] instanceof String) {
                if (args[1] instanceof String) {
                    mode = args[1].toUpperCase();
                    fileContents = readFileContentsByLine(new BufferedReader(new FileReader(new File(args[0]))));    
                    elevatorProperties = elevatorInitialConfig(fileContents);
                    for (Map config : elevatorProperties) {
                        elevator = new Elevator(config);
                        elevator.startElevator(mode);
                    }
                }
            }
        }
    }
    
    public static List<String> readFileContentsByLine(BufferedReader br) throws IOException {
        String line;
        List<String> lineList = new ArrayList<>();
        while((line = br.readLine()) != null) {
            lineList.add(line);
        }
        return lineList;
    }
    
    public static List<Map> elevatorInitialConfig(List<String> lines) {
        
        String[] lineTokens;
        Map<String,Object> config;
        List<Map> configListMap = new ArrayList<>();
        int maxFloor=0;
        int minFloor=0;
        int startFloor;
        
        for(String line : lines) {
            lineTokens = line.split(":");
            startFloor = new Integer(lineTokens[0]);
            config = new HashMap<>();
            config.put("floor", startFloor);
            String[] orderTuplesSet = lineTokens[1].split(",");
            
            Map<Integer,Integer> orderMap = new HashMap<>();
            for (String tuple : orderTuplesSet) {
                String[] orders = tuple.split("-");  
                int onFloor = new Integer(orders[0]);
                int offFloor = new Integer(orders[1]);
                orderMap.put(onFloor, offFloor);
                minFloor = determineMin(minFloor, onFloor, offFloor);
                maxFloor = determineMax(startFloor, onFloor, offFloor);

            }   
            config.put("orders", orderMap);
            config.put("maxFloor", maxFloor);
            config.put("minFloor", minFloor);
            configListMap.add(config);
        }
        return configListMap;
    }
    
    public static int determineMax(int val1, int val2, int val3){
        int max = val1;
        if (max < val2) max = val2;
        if (max < val3) max = val3;
        return max;
    }
     
    public static int determineMin(int val1, int val2, int val3) {
        int min = val1;
        if (min > val2) min = val2;
        if (min > val3) min = val3;
        return min;
    }
}
