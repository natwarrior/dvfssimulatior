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
class EndOfJobException extends Exception {
    
    private int cNumber;
    
    public EndOfJobException(int n){
        this.cNumber = n;
    }
    
    public int getCore(){
        return cNumber;
    }
}
