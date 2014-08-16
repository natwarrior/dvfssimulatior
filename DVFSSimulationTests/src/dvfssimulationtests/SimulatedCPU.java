package dvfssimulationtests;

import java.util.Date;

/**
 *
 * @author carlosmagno
 */
public class SimulatedCPU {
    
    //******CPU-SETTINGS*******
    
    // PLUBIC & STATIC
    public static final int N_CORES = 4;
    public final static int MIN_JOB_SIZE = 1;
    public final static int MAX_JOB_SIZE = 15;
    public final static int MAX_FREQ = 5000;
    
    // PRIVATE & STATIC
    private static final double INCREASE_FREQ_PERCENT = 0.75;
    private static final double NON_STRATEGY_FREQ_PERCENT = 0.8;
    
    // PRIVATE
    private boolean strategy;
    private int counter = N_CORES;
    private int[] jobs = new int[N_CORES];
    private int[] freqs = new int[N_CORES];
    private boolean[] exec = new boolean[N_CORES];
    
    //FINAL PRIVATE
    private final SimulatedCore[] cores = new SimulatedCore[N_CORES];
    private final EnergyCalculator[] energys = new EnergyCalculator[N_CORES];
    private final static int MIN_FREQ = 1000;
    
    /**
    * Construtor que recebe as tarefas e uma flag para verificar se a simulacao 
    * será com estrategia. Caso seja com estrategia, cada CPU comecará com a fre-
    * quencia minima, senao iniciará com a frenquencia maxima multiplicado pela
    * porcentagem para a CPU sem estratégia.
    */
    
    public SimulatedCPU(int jobs[], boolean strategy) {
        this.jobs = jobs;
        this.strategy = strategy;
        for (int i = 0; i < N_CORES; i++) {
            freqs[i] = strategy ? MIN_FREQ : (int)(MAX_FREQ*NON_STRATEGY_FREQ_PERCENT);
            energys[i] = new EnergyCalculator();
        }
    }
    
    /**
    * Esse método inicia cada thread, no entanto antes da iniciação ele verifica 
    * se o tamanho da tarefa é menor do que o tamanho minimo aceito da tarefa 
    * caso seja menor, ele ira trocar pela valor da constante MIN_JOB_SIZE caso 
    * não seja, ele irá continuar com o valor atual. O Metodo tambem coloca true 
    * em cada flag que esta simbolizando se o core esta exercutando uma   
    */
    
    public void start() {
        for (int i = 0; i < N_CORES; i++) {
            exec[i] = true;
            cores[i] = new SimulatedCore(i, (int) (jobs[i] < MIN_JOB_SIZE ? MIN_JOB_SIZE : jobs[i]), this);
            energys[i].addPair(new Date(), freqs[i]);
            cores[i].start();
        }
        for(SimulatedCore s : cores){
            try {
                s.join();
            } catch (InterruptedException ex) {
                System.err.println("join() erro");
            }
        }
    }
    
    /**
    * Metodo responsavel 
    */
    
    public synchronized void endOfJobSignal(int cIdx) {
        System.out.println("Core " + cIdx + " terminou sua tarefa.");
        exec[cIdx] = false;
        counter--;
        if (counter == 0) {
            long totalEnergy = 0, thisEnergy;
            for (int i = 0; i < N_CORES; i++) {
                energys[i].addPair(new Date(), freqs[i]);
                thisEnergy = energys[i].getEnergy();
                totalEnergy += thisEnergy;
                System.out.println("Consumo do core " + i + " : " + thisEnergy);
            }
            System.err.println("Consumo total " + (strategy ? "com" : "sem") + " estratégia : " + totalEnergy);
        }
        if (strategy) {
            freqs[cIdx] = MIN_FREQ;
            energys[cIdx].addPair(new Date(), MIN_FREQ);
            for (int i = 0; i < N_CORES; i++) {
                if (exec[i]) {
                    freqs[i] *= 1 + INCREASE_FREQ_PERCENT;
                    if (freqs[i] > MAX_FREQ) {
                        freqs[i] = MAX_FREQ;
                    }
                    energys[i].addPair(new Date(), freqs[i]);
                    System.out.println("Core " + i + " teve a frequência "
                            + "aumentada em " + INCREASE_FREQ_PERCENT * 100 + "%.");
                }
            }
        }

    }
    
    int getFreq(int idx){
        return freqs[idx];
    }
}
