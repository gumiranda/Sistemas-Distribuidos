package sistemas_distribuidos.Cliente;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

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
    
    
    //Retorna false caso a thread tenha q parar ou false caso contrario
    public synchronized  boolean validarComandos(String comando,PrintStream saida){
        String aux;
        aux = comando.toLowerCase();
        boolean flag = true;
        boolean fi = false;
        String comandos[] = aux.split(" ");
        
        
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
                    
                case "quit":
                    System.out.println("Programa finalizado");
                    this.com.Matar();
                    this.com.indicaFinal();
                    break;
                    
                default:
                    flag = false;
                    break;
                
            }
            if(flag){
               this.com.indicaFinal();
               saida.println(comando);
           
            }
            else{
                System.out.println("Comando Invalido: "+aux);
                System.out.println("Comando nao executado tente novamente");
              
            }
            return fi;
    }
            
            
  
    
    
    public void run(){
        System.out.println("Comandos Diponiveis:");
        System.out.println("INSERT,DELETE,READ e DELETE");
        
        while(!this.exit){
            
            Scanner teclado = new Scanner(System.in);
            PrintStream saida;
             boolean verifica = false;
            try {
                saida = new PrintStream(this.cliente.getOutputStream());
                System.out.print("Digite o comando: ");
               
                while(teclado.hasNextLine()){
                    
                    verifica = validarComandos(teclado.nextLine(),saida); 
                }
            } catch (IOException ex) {
                Logger.getLogger(LerComandos.class.getName()).log(Level.SEVERE, null, ex);
            }
              if(verifica){
                 this.stop();
              }
            
        }

    }
    
    public void stop() 
    { 
        this.exit = true; 
    } 
    
}
