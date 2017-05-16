/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author LeoIvanSpenc
 */
public class Elevator {
    
    private int startFloor;
    private Map orders;
    private int maxFloor;
    private int minFloor;
    Integer[] allFloors;
    
    
    public Elevator() {};
    
    public Elevator(Map config) {
        this.startFloor = (Integer)config.get("floor");
        this.orders = (Map)config.get("orders");
        this.maxFloor = (Integer)config.get("maxFloor");
        this.minFloor = (Integer)config.get("minFloor");
        allFloors = new Integer[this.maxFloor+1];
        
        
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void startElevator(String opMode) {
        int direction = -1;
        int i = this.startFloor;
        int totalFloorsVisited = 0;
        Set<Integer> ridersOnElevator = new HashSet<>();
        
        System.out.println();
        System.out.print(this.startFloor);
        System.out.print(" ");
        
        while(this.orders.keySet().size() > 0 && ridersOnElevator.isEmpty()) {

            while(direction < 0) {

                if (ridersGetOn(i)) {
                    if (!ridersOnElevator.contains(i)) {
                        if ("A".equals(opMode.toUpperCase())) {
                            if (ridersOnElevator.isEmpty()) {
                                allowRidersToBoard(ridersOnElevator, i);
                            }
                        }
                        else allowRidersToBoard(ridersOnElevator, i);   
                        System.out.print(i);
                        System.out.print(" ");
                    }
                }
                if (ridersGetOff(i)) {
                    this.allFloors[i] = null;
                    ridersOnElevator.remove(i);
                    System.out.print(i);
                    System.out.print(" ");
                    
                }

                if (i > this.minFloor) i--;
                else direction = direction + 2;                     

            }
            while(direction > 0) {
                if (ridersGetOn(i)) {
                    if (!ridersOnElevator.contains(i)) {
                        if ("A".equals(opMode.toUpperCase())) {
                            if (ridersOnElevator.isEmpty()) {
                                allowRidersToBoard(ridersOnElevator, i);
                            }
                        }
                        else allowRidersToBoard(ridersOnElevator, i);  
                        System.out.print(i);
                        System.out.print(" ");  
                    }
                }
                if (ridersGetOff(i)) {
                    this.allFloors[i] = null;
                    ridersOnElevator.remove(i);                       
                    System.out.print(i);
                    System.out.print(" ");
                }

                if (i < this.maxFloor) i++;
                else direction = direction - 2;    

                totalFloorsVisited++;

            }
        }
        
        System.out.print("(" + totalFloorsVisited + ")");
        System.out.println();
    }
    
    private boolean ridersGetOn(int floor) {
        if (this.orders.containsKey(floor)) return true;
        else return false;
    }
    
    private boolean ridersGetOff(int floor) {
        if (this.allFloors[floor] != null) {
            this.orders.remove(this.allFloors[floor]);
            return true;
        }
        else return false;
    }
    
    private void allowRidersToBoard(Set<Integer> board, int atFloor) {
        this.allFloors[atFloor] = (Integer)this.orders.get(atFloor);
        board.add(atFloor);
    }

}
