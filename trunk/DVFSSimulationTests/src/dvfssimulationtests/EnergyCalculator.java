package dvfssimulationtests;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author carlosmagno
 */
public class EnergyCalculator {

    private ArrayList<Date> times;
    private ArrayList<Integer> freqs;

    public EnergyCalculator() {
        times = new ArrayList<>();
        freqs = new ArrayList<>();
    }

    /**
     * Metodo que adiciona o tipo Data para ultilizar a tempo de inicio e a
     * frequência de cada tarefa.
     */
    public void addPair(Date d, int freq) {
        times.add(d);
        freqs.add(freq);
    }

    /**
     * Metodo que calcula e retorna o somatorio de energia conforme os valores
     * que foram inseridos no metodo addPair. O somatório é feito da seguinte
     * forma, a cada interacao do laco ele vai somando a diferenca do tempo
     * entre o elemento atual e o proximo elemento guardado, em uma variavel
     * (energy) que será retornada no final.
     */
    public long getEnergy() {
        long energy = 0;
        for (int i = 0; i < times.size() - 1; i++) {
            energy += (times.get(i + 1).getTime() - times.get(i).getTime())* freqs.get(i);
        }
        return energy;
    }
    
    public long getLastTime(){
        return (long)times.get(times.size()-1).getTime();
    }
}
