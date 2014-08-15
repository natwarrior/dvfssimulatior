/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dvfssimulationtests;

import java.util.Date;

/**
 *
 * @author carlosmagno
 */
public class SimulatedCPU {

    private static final int N_CORES = 4;
    private static int counter = N_CORES;
    private static final double INCREASE_FREQ_PERCENT = 0.75;
    private static final double NON_STRATEGY_FREQ_PERCENT = 0.8;
    private static final boolean[] exec = new boolean[N_CORES];
    private final SimulatedCore[] cores = new SimulatedCore[N_CORES];
    private final int[] jobs = new int[N_CORES];
    private final static EnergyCalculator[] energys = new EnergyCalculator[N_CORES];
    private final static int MIN_JOB_SIZE = 1;
    private final static int MAX_JOB_SIZE = 15;
    private final static int MIN_FREQ = 1000;
    public static boolean STRATEGY = false;

    public final static int MAX_FREQ = 5000;
    public static int[] freqs = new int[N_CORES];

    public SimulatedCPU() {
        for (int i = 0; i < N_CORES; i++) {
            jobs[i] = (int) (MAX_JOB_SIZE * Math.random());
        }
    }

    public void start() {
        
        for (int i = 0; i < N_CORES; i++) {
            freqs[i] = STRATEGY ? MIN_FREQ : (int)(MAX_FREQ*NON_STRATEGY_FREQ_PERCENT);
            energys[i] = new EnergyCalculator();
            exec[i] = true;
            cores[i] = new SimulatedCore(i, (int) (jobs[i] < MIN_JOB_SIZE ? MIN_JOB_SIZE : jobs[i]));
            energys[i].addPair(new Date(), freqs[i]);
            cores[i].start();
            cores[i].join();
        }
    }

    public synchronized static void endOfJobSignal(int cIdx) {
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
            System.out.println("Consumo total: " + totalEnergy);
            System.exit(0);
        }
        if (STRATEGY) {
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
}
