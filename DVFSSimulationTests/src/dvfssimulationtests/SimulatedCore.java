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
public class SimulatedCore extends Thread{
    
    private int cIdx;
    private int jobSize;
    private double consumedEnergy;
    
    public SimulatedCore(int n, int js){
        cIdx = n;
        jobSize = js;
    }
    
    @Override
    public void run(){
        System.out.println("Core " + cIdx + " iniciando tarefa de tamanho " + jobSize + "...");
        for(int doneJob = 0; doneJob <= jobSize ; doneJob++){
            int freq = SimulatedCPU.freqs[cIdx];
            try {
                Thread.sleep(SimulatedCPU.MAX_FREQ - freq);
            } catch (InterruptedException ex) {
                System.exit(1);
            }
            int percent =  (doneJob*100)/jobSize;
            System.out.println("Core " + cIdx + "(" + freq + ") concluiu " + percent +"% da tarefa["
                    + doneJob +"/"+ jobSize +"].");
        }
        SimulatedCPU.endOfJobSignal(cIdx);
    }
}
