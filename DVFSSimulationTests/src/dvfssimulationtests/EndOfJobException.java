package dvfssimulationtests;

/**
 *
 * @author carlosmagno
 */

/**
* Classe que trata a exceção quando chega no final da tarefa.
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
