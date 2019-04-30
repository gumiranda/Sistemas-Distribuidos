
/**
 *
 * @author Natan Rodovalho
 */

import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.io.ObjectInputStream;

public class ReceberMensagemTeste implements Runnable {
    private Socket cliente;
    private Servidor servidor;
    private Fila F1;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public ReceberMensagemTeste(Socket cliente, Servidor servidor, Fila F1) {
        this.cliente = cliente;
        this.servidor = servidor;
        this.F1 = F1;
    }

    public void run() {
        Scanner s;
        System.out.println("passou o run ");

        try {
            outToClient = new ObjectOutputStream(this.cliente.getOutputStream());
            inFromClient = new ObjectInputStream(this.cliente.getInputStream());
            String comando = (String) inFromClient.readObject();

            Comando c = new Comando(this.cliente, comando);
            F1.put(c);

        } catch (IOException ex) {
            Logger.getLogger(ReceberMensagem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {

        }

    }
}
