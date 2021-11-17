import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 8001);
            System.out.println("Connect success...");
            System.out.println("You can enter your lines now! And you can enter 'quit' to quit");

            InputStream ips = s.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ips));
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());

            Thread terminalThread = new Thread(new ReadTerminalWorker(bufferedReader, outputStream));
            terminalThread.setDaemon(true);
            terminalThread.start();

            while (true) {
                String strWord = bufferedReader.readLine();
                if (strWord == null || strWord.equalsIgnoreCase("quit")) {
                    System.out.println("Chat going to done...");
                    break;
                }
                System.out.println("server said: " + strWord);
            }

            outputStream.close();
            bufferedReader.close();
            s.close();
        } catch (Exception e) {
            System.out.println("发生未知异常，也许你应该先开启Server！");
            System.out.println("Client error with exception: " + e.getMessage());
        }
    }
}
