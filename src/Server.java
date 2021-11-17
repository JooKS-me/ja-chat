import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String [] args) {

        try {
            ServerSocket ss = new ServerSocket(8001);
            Socket s = ss.accept();
            System.out.println("Accept a connect...");
            System.out.println("You can enter your lines now! And you can enter 'quit' to quit");

            InputStream ips = s.getInputStream();
            OutputStream ops = s.getOutputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ips));
            DataOutputStream outputStream = new DataOutputStream(ops);

            Thread terminalThread = new Thread(new ReadTerminalWorker(bufferedReader, outputStream));
            terminalThread.setDaemon(true);
            terminalThread.start();

            while (true) {
                String strWord = bufferedReader.readLine();
                if (strWord == null || strWord.equalsIgnoreCase("quit")) {
                    System.out.println("Chat going to done...");
                    break;
                }
                System.out.println("client said:" + strWord);
            }

            bufferedReader.close();
            outputStream.close();
            s.close();
        } catch(Exception e) {
            System.out.println("Server error with exception: " + e.getMessage());
        }
    }
}
