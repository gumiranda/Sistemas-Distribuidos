/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Natan Rodovalho
 */

//Classe para fazer thread q ler comandos conversar com a q imprimi dados
public class ComunicaThread {
    public boolean finalizada = false;
    
     public synchronized void tentaExecutar(){
        while(!this.finalizada){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        notifyAll();
    }
     
      public synchronized void indicaFinal(){
       this.finalizada = true;
       notifyAll();
    }
      
    public synchronized void FinalLeitura(){
       this.finalizada = false;
       notifyAll();
    }
      
}
