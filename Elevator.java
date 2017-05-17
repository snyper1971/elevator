/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.util.HashSet;
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

                if (ridersGetOn(i, ridersOnElevator)) {
                    allowRidersToBoard(ridersOnElevator, i, opMode);                }
                if (ridersGetOff(i, ridersOnElevator)) {
                    System.out.print(this.orders.get(this.allFloors[i]));
                    System.out.print(" ");
                    this.orders.remove(this.allFloors[i]);
                    this.allFloors[i] = null;
                    ridersOnElevator.remove(i);
                }

                if (i > 1) i--;
                else direction = direction + 2;                     

                if(this.orders.size()==0 && ridersOnElevator.isEmpty()) break;
            }
            while(direction > 0) {
                if (ridersGetOn(i, ridersOnElevator)) {
                    allowRidersToBoard(ridersOnElevator, i, opMode);
                }
                if (ridersGetOff(i ,ridersOnElevator)) {
                    System.out.print(this.orders.get(this.allFloors[i]));
                    System.out.print(" ");
                    this.orders.remove(this.allFloors[i]);
                    this.allFloors[i] = null;
                    ridersOnElevator.remove(i);
                }

                if (i < this.maxFloor) i++;
                else direction = direction - 2;    

                totalFloorsVisited++;
                
                if(this.orders.size()==0 && ridersOnElevator.isEmpty()) break;
            }
        }
        
        System.out.print("(" + totalFloorsVisited + ")");
        System.out.println();
    }
    
    private boolean ridersGetOn(int floor, Set<Integer> onElevator) {
        if (this.orders.containsKey(floor)) {
            if (!onElevator.contains(this.orders.containsValue(floor))) {
                return true;
            }
            else return false;
        }
        else return false;
    }
    
    private boolean ridersGetOff(int floor, Set<Integer> onElevator) {
        if (this.allFloors[floor] != null) {
            if (onElevator.contains(this.orders.get(this.allFloors[floor]))) {
                return true;
            }
            else return false;
        }
        else return false;
    }
    
    private void allowRidersToBoard(Set<Integer> board, int atFloor, String mode) {
        if (!"A".equals(mode.toUpperCase())) {
            this.allFloors[(Integer)this.orders.get(atFloor)] = atFloor;
            board.add((Integer)this.orders.get(atFloor));
            System.out.print(atFloor);
            System.out.print(" ");
        }
        else {
            if (board.isEmpty()) {
                this.allFloors[(Integer)this.orders.get(atFloor)] = atFloor;
                board.add((Integer)this.orders.get(atFloor));    
                System.out.print(atFloor);
                System.out.print(" ");
           }
        }
    }

}
