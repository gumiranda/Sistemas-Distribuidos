
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

public class ImprimeMensagem implements Runnable{
    private InputStream servidor;
       
    public ImprimeMensagem(InputStream servidor){
        this.servidor = servidor;
    }
    
    //Atende comando do cliente e repassa para o servidor
    public void run(){
        Scanner s = new Scanner(this.servidor);
        while(s.hasNextLine()){
            System.out.println(s.nextLine());
        }
    }
}
