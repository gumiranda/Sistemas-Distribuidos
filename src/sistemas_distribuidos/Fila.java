
package sistemas_distribuidos;

/**
 *
 * @author Natan Rodovalho
 */

import java.util.ArrayList;
import java.util.List;

public class Fila {
    private ArrayList<Comando> comandos = new ArrayList<Comando>();
    private boolean possui_dados = false;
    
    public synchronized ArrayList getAll(){
        ArrayList<Comando> aux;
        while(!this.possui_dados){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        this.possui_dados = false;
        notifyAll();
        aux = this.comandos;
        for(int i=0;i<this.comandos.size();i++){
            this.comandos.remove(i);
        }
        return this.comandos;
    }
    
    public synchronized void put(Comando c){
        this.possui_dados = true;
        this.comandos.add(c);
    } 
    
     public synchronized Comando getFirst(){
        while(!this.possui_dados){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        notifyAll();
        Comando c = this.comandos.get(0);
        this.comandos.remove(0);
        return c;
    }
}
