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
import java.util.ArrayList;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
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
            
            if(!this.arquivo.exists()){
               try {
                    this.arquivo.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            FileWriter fw;
            try {
                System.out.println("Começa a escrever no log");
                fw = new FileWriter(this.arquivo, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(comando);
            } catch (IOException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }

}
