/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dvfssimulationtests;

/**
 *
 * @author carlosmagno
 */
public class SimulatedCPU {
    
    private static int N_CORES = 4;
    public final static int MAX_FREQ = 5000;
    public final static int MIN_FREQ = 1000;
    public final static int MAX_JOB_SIZE = 30;
    
    public static int[] freqs = new int[N_CORES];
    
    private SimulatedCore[] cores = new SimulatedCore[N_CORES];
    
    public SimulatedCPU(){
        for(int i = 0; i < N_CORES; i++){
            freqs[i] = MIN_FREQ;
        }
    }
    
    public void start(){
    }
}
