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
public class DVFSSimulationTests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SimulatedCPU cpu = new SimulatedCPU();
        SimulatedCPU.STRATEGY = true;
        cpu.start();
        
        System.err.println("minino");
        SimulatedCPU.STRATEGY = false;
        cpu.start();
    }
    
}
