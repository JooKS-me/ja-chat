import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 守护线程，监听终端输入，并处理
 */
public class ReadTerminalWorker implements Runnable{

    private final DataOutputStream outputStream;

    private final BufferedReader terminalReader;

    public ReadTerminalWorker(BufferedReader reader, DataOutputStream outputStream) {
        this.outputStream = outputStream;
        this.terminalReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            String strWord = null;
            try {
                strWord = terminalReader.readLine();
                outputStream.writeBytes(strWord + System.getProperty("line.separator"));
                if (strWord.equalsIgnoreCase("quit")) {
                    break;
                } else {
                    System.out.println("send: " + strWord);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
