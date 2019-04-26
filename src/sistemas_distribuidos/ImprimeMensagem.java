
package sistemas_distribuidos;

/**
 *
 * @author Natan Rodovalho
 */

import  java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImprimeMensagem implements Runnable{
    private InputStream servidor;
       
    public ImprimeMensagem(InputStream servidor){
        this.servidor = servidor;
    }
    
    //Imprime mensagem ao receber do servidor
    public void run(){
        Scanner s = new Scanner(this.servidor);
        
        while(s.hasNextLine()){
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ImprimeMensagem.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(s.nextLine());
            
        }
        
        
    }
}
