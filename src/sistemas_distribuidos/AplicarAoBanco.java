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
    
    public BigInteger getChave(String chave){   
        BigInteger chavei = new BigInteger(chave);
        return chavei;
    }
    
    public byte[] getDados(String comandos[]){
        String d= null;
 
                for(int i=2;i<comandos.length;i++){
                    if(i == 2){
                        d = comandos[i];
                    }else{
                    d = d +" " +comandos[i];
                    }
                
                }
                
        return d.getBytes();
    }
    
    
    public void run(){
        while(true){
            Comando c = F3.getFirst();
            Socket cliente = c.getCliente();
            String comando = c.getComando();
            String comandos[] = comando.split(" ");
            byte[] dados = null;
            String retorno = null;
            
            
            BigInteger chave = getChave(comandos[1]);

            if(comandos.length >=3 )
                dados = getDados(comandos);
 
            byte[] retorno_select = null;
            switch(comandos[0].toLowerCase()){
                case "insert":
                    retorno = this.banco.add(chave, dados);
                    break;
                case "delete":
                    retorno = this.banco.Deletar(chave);
                    break;
                case "select":
                    if(this.banco.verifica(chave)){
                         retorno_select = this.banco.get(chave);
                          try {
                            retorno = new String(retorno_select, "UTF-8");
                            
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(AplicarAoBanco.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        retorno = "Chave nao existe";
                    }
                    break;
                case "update":
                    retorno = this.banco.update(chave, dados);
                    break;
             
            }
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
            
            //Tratamento para tentar evitar memoria Leak:
            c = null;
            cliente = null;
            comando = null;
            comandos = null;
            dados = null;
            retorno = null;
            chave = null;
            System.gc();
            
        }
    }
    
    
    
}
