package sistemas_distribuidos;

/**
 *
 * @author Natan Rodovalho
 */

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;


public class Cliente{
    private int porta;
    private String host;
    
    public Cliente(String host,int porta){
        this.porta = porta;
        this.host = host;
    }
     public static void main(String[] args) throws IOException{
       new Cliente("127.0.0.1", 1234).executa();
    }
    
    public void executa() throws IOException{
        Socket cliente = new Socket(this.host,this.porta);
        ImprimeMensagem imprimir = new ImprimeMensagem(cliente.getInputStream());
        new Thread(imprimir).start();
        
        //Lendo mensagem do teclado e mandando para o servidor
        
        LerComandos comandos = new LerComandos(cliente);
        new Thread(comandos).start();
        //cliente.close();
        
    }
    

}
