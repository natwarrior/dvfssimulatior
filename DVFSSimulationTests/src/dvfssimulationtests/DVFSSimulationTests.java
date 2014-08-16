package dvfssimulationtests;

/**
 *
 * @author carlosmagno
 */
public class DVFSSimulationTests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
         * Criando as tarefas's com o tamanho entre 0 e o valor maximo da
         * tarefa.
         */
        int[] jobs = new int[SimulatedCPU.N_THREADS];
        for (int i = 0; i < SimulatedCPU.N_THREADS; i++) {
            jobs[i] = (int) (SimulatedCPU.MAX_JOB_SIZE * Math.random());
        }

        /**
         * Criando e iniciando a Simulação das CPU's com e sem estrategia
         */
        SimulatedCPU cpuSrategy = new SimulatedCPU(jobs, true);
        SimulatedCPU cpuNonStrategy = new SimulatedCPU(jobs, false);

        cpuSrategy.start();
        cpuNonStrategy.start();
    }

}
