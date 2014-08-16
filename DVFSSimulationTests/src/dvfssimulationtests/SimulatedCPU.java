package dvfssimulationtests;

import java.util.Date;

/**
 *
 * @author carlosmagno
 */
public class SimulatedCPU {

    //******CPU-SETTINGS*******
    // PLUBIC & STATIC
    public static final int N_THREADS = 4;
    public final static int MIN_JOB_SIZE = 1;
    public final static int MAX_JOB_SIZE = 15;
    public final static int MAX_FREQ = 2400;
    public final static int MIN_FREQ = 1200;

    // PRIVATE & STATIC
    private static final int INCREASE_FREQ_GAP_PART = 3;

    // PRIVATE
    private boolean strategy;
    private int counter = N_THREADS;
    private int[] jobs = new int[N_THREADS];
    private int[] freqs = new int[N_THREADS];
    private Date[][] data = new Date[N_THREADS][2];
    private boolean[] exec = new boolean[N_THREADS];

    //FINAL PRIVATE
    private final SimulatedThread[] cores = new SimulatedThread[N_THREADS];
    private final EnergyCalculator[] energys = new EnergyCalculator[N_THREADS];
    

    /**
     * Construtor que recebe as tarefas e uma flag para verificar se a simulacao
     * será com estrategia. Caso seja com estrategia, cada CPU comecará com a
     * frequencia minima, senao iniciará com a frenquencia maxima multiplicado
     * pela porcentagem para a CPU sem estratégia.
     * @param jobs
     * @param strategy
     */
    public SimulatedCPU(int jobs[], boolean strategy) {
        this.jobs = jobs;
        this.strategy = strategy;
        for (int i = 0; i < N_THREADS; i++) {
            freqs[i] = strategy ? MIN_FREQ : MAX_FREQ;
            energys[i] = new EnergyCalculator();
        }
    }

    /**
     * Esse método inicia cada thread, no entanto, antes da iniciação ele
     * verifica se o tamanho da tarefa é menor do que o tamanho minimo aceito da
     * tarefa caso seja menor, ele ira trocar pela valor da constante
     * MIN_JOB_SIZE caso não seja, ele irá continuar com o valor atual. O Metodo
     * tambem coloca true em cada flag que esta simbolizando se o thread esta
     * exercutando e guarda cada valor de Data atual e a frequencia de cada uma.
     */
    public void start() {
        for (int i = 0; i < N_THREADS; i++) {
            exec[i] = true;
            cores[i] = new SimulatedThread(i, (int) (jobs[i] < MIN_JOB_SIZE ? MIN_JOB_SIZE : jobs[i]), this);
            energys[i].addPair(new Date(), freqs[i]);
            data[i][0]=new Date();
            cores[i].start();
        }
        for (SimulatedThread s : cores) {
            try {
                s.join();
            } catch (InterruptedException ex) {
                System.err.println("join() erro");
            }
        }
    }

    /**
     * Metodo e chamado quando uma tarefe é finalizada, de tal forma que temos
     * duas situações uma e caso ainda possua threads (tarefas) exercutando
     * representada pela variavel counter, nesse caso, ele irá aumentar a
     * velocidade das threads restantes, caso o counter seja zero, ou seja não
     * tem thread em exercucao ele ira calcular o somatorio de todas as tarefas
     * feitas.
     * @param cIdx
     */
    public synchronized void endOfJobSignal(int cIdx) {
        System.out.println("Core " + cIdx + " terminou sua tarefa.");
        exec[cIdx] = false;
        data[cIdx][1]=new Date();
        counter--;      //retirando outra thread
        if (counter == 0) {
            long totalEnergy = 0, thisEnergy, totalTime=0;
            Date last = new Date();
            for (int i = 0; i < N_THREADS; i++) {
                energys[i].addPair(last, freqs[i]);
                thisEnergy = energys[i].getEnergy();
                totalEnergy += thisEnergy;
                totalTime+=gapTime(i);
                System.out.println("Consumo do core " + i + " : " + thisEnergy);
            }
            System.err.println("Consumo total " + (strategy ? "com" : "sem") + " estratégia : " + totalEnergy);
            System.err.println("O tempo total " + (strategy ? "com" : "sem") + " estratégia : " + totalTime);
        }
        if (strategy) {
            freqs[cIdx] = MIN_FREQ;
            energys[cIdx].addPair(new Date(), MIN_FREQ);
            for (int i = 0; i < N_THREADS; i++) {
                if (exec[i]) {
                    freqs[i] += (MAX_FREQ - MIN_FREQ) / INCREASE_FREQ_GAP_PART;
                    if (freqs[i] > MAX_FREQ) {
                        freqs[i] = MAX_FREQ;
                    }
                    energys[i].addPair(new Date(), freqs[i]);
                    System.out.println("Core " + i + " teve a frequência "
                            + "aumentada para " + freqs[i] + ".");
                }
            }
        }

    }

    int getFreq(int idx) {
        return freqs[idx];
    }
    
    /**
     * Metodo que calcula a diferenca entre os tempo final o tempo inicial. 
     * @param i
     * @return o tempo relativo a diferenca entre os valores final e inicial 
     */
    
    public long gapTime(int i){
        return data[i][1].getTime()-data[i][0].getTime();
    } 
}
