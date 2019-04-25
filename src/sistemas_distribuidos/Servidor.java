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
import java.util.concurrent.*;

public class Servidor {
    private int porta;
    private ArrayList<Socket> clientes;
    private Fila F2;
    private Fila F3;
    private Fila F1;
    private int quantidade_threads = 15;
    private BaseDados Banco;
    
     public static void main(String[] args) throws IOException{
        new Servidor(1234).executa();
       
    }
         
    public Servidor(int porta){
        this.porta = porta;
        this.clientes = new ArrayList<Socket>();
        this.F1 = new Fila();
        this.F2 = new Fila();
        this.F3 = new Fila();
        this.Banco = new BaseDados();
    }
    
    public void executa() throws IOException{
        
        ServerSocket servidor = new ServerSocket(this.porta);
        
        //Devinindo POOL de threads
        ExecutorService thds = Executors.newFixedThreadPool(this.quantidade_threads);
        
        //Para copiar de F1 para F2 e para F3
        CopiarLista copy = new CopiarLista(this.F1,this.F2,this.F3);
        new Thread(copy).start();
        
        //Criando Log
        Log log = new Log(this.F2);  
        new Thread(log).start();
        
        
        //Thread para aplicar operacoes ao Banco de Dados
        AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco,this.F3,this);
        new Thread(bancoDados).start();
     
        while(true){
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " +cliente.getInetAddress().getHostAddress());
            this.clientes.add(cliente);
            ReceberMensagem msg = new ReceberMensagem(cliente,this,this.F1);
            thds.execute(msg);
        }
    }
    
    public synchronized String MandarMensagem(String mensagem){
        //Mandar mensagem para os clientes
        System.out.println(mensagem);
        return mensagem;
    }
    
    
}
