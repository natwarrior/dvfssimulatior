package dvfssimulationtests;

/**
 *
 * @author carlosmagno
 */
public class SimulatedThread extends Thread {

    private final int cIdx;
    private final int jobSize;
    private double consumedEnergy;
    private SimulatedCPU parent;

    public SimulatedThread(int n, int js, SimulatedCPU parent) {
        cIdx = n;
        jobSize = js;
        this.parent = parent;
    }

    /**
     * exercutando as tarefas de formas que cada parte da mesma é exercutada em
     * uma CPU e com a frequencia adequada conforme a estrategia.
     */
    @Override
    public void run() {
        System.out.println("Core " + cIdx + " iniciando tarefa de tamanho " + jobSize + "...");
        for (int doneJob = 0; doneJob <= jobSize; doneJob++) {
            int freq = parent.getFreq(cIdx);
            try {
                Thread.sleep(SimulatedCPU.MAX_FREQ - freq);
            } catch (InterruptedException ex) {
                System.exit(1);
            }
            int percent = (doneJob * 100) / jobSize;
            System.out.println("Core " + cIdx + "(" + freq + ") concluiu " + percent + "% da tarefa["
                    + doneJob + "/" + jobSize + "].");
        }
        parent.endOfJobSignal(cIdx);
    }
}
