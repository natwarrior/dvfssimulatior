/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dvfssimulationtests;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosmagno
 */
public class DVFSSimulationTests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int[] jobs = new int[SimulatedCPU.N_CORES];
        for(int i = 0 ; i < SimulatedCPU.N_CORES ; i++){
            jobs[i] = (int)(SimulatedCPU.MAX_JOB_SIZE*Math.random());
        } 
        
        SimulatedCPU cpuSrategy = new SimulatedCPU(jobs, true);
        SimulatedCPU cpuNonStrategy = new SimulatedCPU(jobs, false);
        
        cpuSrategy.start();
        cpuNonStrategy.start();
    }
    
}
