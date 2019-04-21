package sistemas_distribuidos;

/**
 *
 * @author Natan Rodovalho
 */

import java.util.ArrayList;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.PrintStream;
import java.io.OutputStream;

public class Servidor {
    private int porta;
    private ArrayList<PrintStream> clientes;
    
     public static void main(String[] args) throws IOException{
        new Servidor(1234).executa();
       
    }
    
    public Servidor(int porta){
        this.porta = porta;
        this.clientes = new ArrayList<PrintStream>();
    }
    
    public void executa() throws IOException{
        ServerSocket servidor = new ServerSocket(this.porta);
        while(true){
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " +cliente.getInetAddress().getHostAddress());
            
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            this.clientes.add(ps);
            ReceberMensagem msg = new ReceberMensagem(cliente,this,ps);
            new Thread(msg).start();
        }
    }
    
    public String processaComando(String comando){
        //Validar comando ->fazer filas ...
        return "Sucesso";
    }
    
    
}
