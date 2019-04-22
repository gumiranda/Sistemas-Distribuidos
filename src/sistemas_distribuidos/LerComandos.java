package sistemas_distribuidos;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Natan Rodovalho
 */
public class LerComandos implements Runnable {
    private Socket cliente;
    
    public LerComandos(Socket cliente){
        this.cliente = cliente;
    }
    
    public synchronized boolean validarComandos(String comando,PrintStream saida){
        String comandos[] = comando.split(" ");
        System.out.println("Validando Comando: "+comandos[0]);
        saida.println(comando);
        return true;
    }
    
    public void run(){
        System.out.println("Comandos Diponiveis:");
        System.out.println("INSERT,DELETE,SELECT e DELETE");
        System.out.println("Digite o comando:");
        while(true){
            Scanner teclado = new Scanner(System.in);
            PrintStream saida;
            try {
                saida = new PrintStream(this.cliente.getOutputStream());
                
                while(teclado.hasNextLine()){
                    validarComandos(teclado.nextLine(),saida);          
                }
            } catch (IOException ex) {
                Logger.getLogger(LerComandos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }
    
}
