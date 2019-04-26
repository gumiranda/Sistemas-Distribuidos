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
    private ComunicaThread com;
    private boolean exit = false;

    public synchronized boolean verificaNumero(String str2){
        double valor;
        
        try {
	    valor = (Double.parseDouble(str2));
            return true;
	} catch (NumberFormatException e) {	  
            return false;
	}
    }
    
    public LerComandos(Socket cliente,ComunicaThread com){
        this.cliente = cliente;
        this.com = com;
    }
    
    public synchronized void validarComandos(String comando,PrintStream saida){
        String aux;
        aux = comando.toLowerCase();
        boolean flag = true;
        String comandos[] = aux.split(" ");
        
        if(comandos[0].equals("select") || comandos[0].equals("insert") || comandos[0].equals("delete") || comandos[0].equals("update")){
            switch(comandos[0]){
                case "select":
                    if(comandos.length < 2){
                        System.out.println("Quantidade de comandos invalido: Minimo de  2");
                        flag = false;
                        
                    }else{
                        if(!this.verificaNumero(comandos[1])){
                            System.out.println("Tipo de chave Invalida");
                            flag = false;
                        }
                    }
                    break;
                case "insert":
                    if(comandos.length < 3){
                        System.out.println("Quantidade de comandos invalido: Minimo de  3");
                        flag = false;
                        
                    }else{
                        if(!this.verificaNumero(comandos[1])){
                            System.out.println("Tipo de chave Invalida");
                            flag = false;
                        }
                    }
                    break;
                case "delete":
                    if(comandos.length < 2){
                        System.out.println("Quantidade de comandos invalido: Minimo de  2");
                        flag = false;
                        
                    }else{
                        if(!this.verificaNumero(comandos[1])){
                            System.out.println("Tipo de chave Invalida");
                            flag = false;
                        }
                    }
                    break;
                    
                case "update":
                    if(comandos.length < 3){
                        System.out.println("Quantidade de comandos invalido: Minimo de  3");
                        flag = false;
                        
                    }else{
                        if(!this.verificaNumero(comandos[1])){
                            System.out.println("Tipo de chave Invalida");
                            flag = false;
                        }
                    }
                    break;
                
            }
            if(flag){
               this.com.indicaFinal();
               saida.println(comando);
            }
            else{
                System.out.println("Comando nao executado tente novamente");
            }
            
        
        }else{
            System.out.println("Comando Invalido: "+aux);
        }
            
  
    }
    
    public void run(){
        System.out.println("Comandos Diponiveis:");
        System.out.println("INSERT,DELETE,READ e DELETE");
        
        while(!this.exit){
            
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
    
    public void stop() 
    { 
        this.exit = true; 
    } 
    
}
