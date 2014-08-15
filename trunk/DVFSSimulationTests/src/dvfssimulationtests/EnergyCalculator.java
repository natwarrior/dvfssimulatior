package dvfssimulationtests;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author carlosmagno
 */
public class EnergyCalculator {
    
    private ArrayList<Date> times;
    private ArrayList<Integer> freqs;
    
    public EnergyCalculator(){
        times = new ArrayList<>();
        freqs = new ArrayList<>();
    }
    
    public void addPair(Date d, int freq){
        times.add(d);
        freqs.add(freq);
    }
    
    public long getEnergy(){
        long energy = 0;
        for(int i = 0 ; i < times.size()-1 ; i++){
            energy += (times.get(i+1).getTime() - times.get(i).getTime())*freqs.get(i);
        }
        return energy;
    }
}
