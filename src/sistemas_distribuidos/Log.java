/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas_distribuidos;

/**
 *
 * @author alexa
 */

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log implements Runnable{
    private Fila F2;
    private File arquivo;
    
    public Log(Fila F2){
       this.F2 = F2;
       this.arquivo = new File("Log.txt");
    }
    public void run(){
        while(true){
            Comando c = F2.getFirst();
            String comando = c.getComando();
            String comandos[] = comando.split(" ");
            
            if(!this.arquivo.exists()){
               try {
                    this.arquivo.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            FileWriter fw;
            try {
                FileWriter arq = new FileWriter(this.arquivo,true);
                PrintWriter gravarArq = new PrintWriter(arq);
                
                if(!comandos[0].toLowerCase().equals("select"))
                    gravarArq.println(comando);
                gravarArq.flush();
                arq.close();
                
            } catch (IOException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }

}
