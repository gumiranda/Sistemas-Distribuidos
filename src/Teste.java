import java.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.io.FileOutputStream;

public class Teste {
    private static ObjectOutputStream outToServer;
    private static ObjectInputStream inFromServer;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Socket c1;
        c1 = new Socket("127.0.0.1", 1234);
        ComunicaThread com = new ComunicaThread();
        outToServer = new ObjectOutputStream(c1.getOutputStream());
        inFromServer = new ObjectInputStream(c1.getInputStream());

        String comando = "INSERT 484 5";
        outToServer.writeObject(comando);
        System.out.println((String) inFromServer.readObject());

    }
}