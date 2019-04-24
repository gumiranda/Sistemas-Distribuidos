package sistemas_distribuidos;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 *
 * @author Natan Rodovalho
 */
public class LerComandos implements Runnable {
    private Socket cliente;

    
    public LerComandos(Socket cliente){
        this.cliente = cliente;
    }
    
    public synchronized void validarComandos(String comando,PrintStream saida){
        String aux;
        aux = comando.toLowerCase();
        
        String comandos[] = aux.split(" ");
        
        //if(comandos[0] == "select" || comandos[0] == "read" || comandos[0] == "delete"){
            saida.println(comando);
        
        //}else{
            // System.out.println("Comando Invalido: "+aux);
        //}
            
  
    }
    
    public void run(){
        System.out.println("Comandos Diponiveis:");
        System.out.println("INSERT,DELETE,READ e DELETE");
        
        while(true){
            
            Scanner teclado = new Scanner(System.in);
            PrintStream saida;
            try {
                saida = new PrintStream(this.cliente.getOutputStream());
                System.out.print("Digite o comando: ");
                
                while(teclado.hasNextLine()){
                    
                    validarComandos(teclado.nextLine(),saida);          
                }
            } catch (IOException ex) {
                Logger.getLogger(LerComandos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }
    
}
