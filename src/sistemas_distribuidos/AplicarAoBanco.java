package sistemas_distribuidos;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Natan Rodovalho
 * 
 */

import java.net.Socket;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.PrintStream;

public class AplicarAoBanco implements Runnable{
    private BaseDados banco;
    private Fila F3;
    private Servidor servidor;
    
    
    public AplicarAoBanco(BaseDados banco,Fila F3,Servidor s){
        this.banco = banco;
        this.F3 = F3;
        this.servidor = s;
    }
    
    
    public void run(){
        while(true){
            System.out.println("AGuardando F3");
            Comando c = F3.getFirst();
            System.out.println("F3 com dados");
            Socket cliente = c.getCliente();
            String comando = c.getComando();
            
            String comandos[] = comando.split(" ");
            BigInteger chave = new BigInteger(comandos[1]);
            byte[] dados = comandos[2].getBytes();
            String retorno = this.banco.add(chave, dados);
            
            try {
  
                PrintStream cliente_retorno = new PrintStream(cliente.getOutputStream());
                cliente_retorno.println(servidor.MandarMensagem(retorno));
         
            }catch(IOException ex) {
                
            }
            
            try {
                this.banco.imprimir();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AplicarAoBanco.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
    
}
