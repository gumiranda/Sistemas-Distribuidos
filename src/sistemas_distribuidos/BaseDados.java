package sistemas_distribuidos;

/**
 *
 * @author Natan Rodovalho
 */

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.math.BigInteger;

public class BaseDados{
    private Map<BigInteger,byte[]> Banco;
    
    public BaseDados(){
        this.Banco = new HashMap<BigInteger,byte[]>();
    }
    
    
    public synchronized String update(BigInteger chave,byte[] dado){
        if(!verifica(chave)){
            return "Chave nao existe";
        }
        try {
            this.Banco.put(chave, dado);
            return "UPDATE Com Sucesso";
        }catch(Exception e){
            return "UPDATE Falhou";
        }
        
    }
    
    public synchronized String add(BigInteger chave,byte[] dado){
        if(verifica(chave)){
            return "Chave ja existe";
        }
            
        try {
            this.Banco.put(chave, dado);
            return "INSERT realizado com Sucesso";
        }catch(Exception e){
            return "INSERT FALHOU";
        }
        
    }
    
    public synchronized String Deletar(BigInteger chave){
        if(!verifica(chave)){
            return "Chave nao existe";
        }
            
        try {
            this.Banco.remove(chave);
            return "Deletado com Sucesso";
        }catch(Exception e){
            return "Delete FALHOU";
        }
        
    }
    
    public synchronized boolean verifica(BigInteger chave){
        return this.Banco.containsKey(chave);
    }
    
    public synchronized byte[] get(BigInteger chave){
        byte[] dados = this.Banco.get(chave);
        return dados;
        
    }
    
    public synchronized void imprimir() throws UnsupportedEncodingException{
        for (BigInteger key : this.Banco.keySet()) {
                     
             //Capturamos o valor a partir da chave
              byte[] value = this.Banco.get(key);
              
              String texto = new String(value, "UTF-8");

               System.out.println(key + " = " + texto);
        }
    }
    
}
