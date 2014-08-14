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
    private static final double INCREASE_FREQ_PERCENT = 0.75;
    public final static int MAX_FREQ = 5000;
    public final static int MIN_FREQ = 1000;
    public final static int MAX_JOB_SIZE = 10;
    public final static int MIN_JOB_SIZE = 1;
    private static int counter = N_CORES;
    
    
    public static int[] freqs = new int[N_CORES];
    private static boolean[] exec = new boolean[N_CORES];
    
    private SimulatedCore[] cores = new SimulatedCore[N_CORES];
    
    public SimulatedCPU(){
        for(int i = 0; i < N_CORES; i++){
            freqs[i] = MIN_FREQ;
        }
    }
    
    public void start(){
        int idx = 0;
        for(SimulatedCore c : cores){
            exec[idx] = true;
            int jobSize = (int)(MAX_JOB_SIZE*Math.random());
            c = new SimulatedCore(idx++, 
                                  (int)(jobSize == 0 ? MIN_JOB_SIZE : jobSize)
                                 );
            c.start();            
        }
    }
    
    public synchronized static void endOfJobSignal(int cIdx){
        System.out.println("Core " + cIdx + " terminou sua tarefa.");
        counter--;
        exec[cIdx] = false;
        if(counter == 0){
            //Trata os tempos
            System.exit(0);
        }else{
            freqs[cIdx] = MIN_FREQ;
            for(int i = 0; i < N_CORES; i++){
                if(exec[i]){
                    freqs[i] *= 1 + INCREASE_FREQ_PERCENT;
                    if(freqs[i] > MAX_FREQ) freqs[i] = MAX_FREQ;
                    System.out.println("Core " + i + " teve a frequência "
                                     + "aumentada em " + INCREASE_FREQ_PERCENT*100 + "%.");
                }
            }
        }
    }
}
