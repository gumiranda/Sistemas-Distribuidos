
/**
 *
 * @author Natan Rodovalho
 */

import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class ReceberMensagem implements Runnable {
    private Socket cliente;
    private Servidor servidor;
    private Fila F1;

    public ReceberMensagem(Socket cliente, Servidor servidor, Fila F1) {
        this.cliente = cliente;
        this.servidor = servidor;
        this.F1 = F1;
    }

    public void run() {
        Scanner s;
        System.out.println("passou o run ");

        try {
            s = new Scanner(this.cliente.getInputStream());
            // PrintStream cliente_retorno = new
            // PrintStream(this.cliente.getOutputStream());
            String comando;
            while (s.hasNextLine()) {
                comando = s.nextLine();
                System.out.println("Chegou o comando " + comando);
                Comando c = new Comando(this.cliente, comando);
                F1.put(c);
                // cliente_retorno.println(servidor.processaComando(s.nextLine()));
            }
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(ReceberMensagem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
