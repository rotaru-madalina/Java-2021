import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Program03_ServerTCPMultithreaded {

    public static void main(String[] args) throws IOException {
        final int PORT_NUMBER = 8293;
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            while (true) {
                System.out.println("Așteptăm un client...");
                Socket socket = serverSocket.accept();
                new Thread(() -> procesareCerere(socket)).start();
                // Runnable este o interfata functionala, a se urmari link-ul de mai jos:
                // https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html#:~:text=Interface%20Runnable&text=This%20is%20a%20functional%20interface,lambda%20expression%20or%20method%20reference.&text=The%20Runnable%20interface%20should%20be,be%20executed%20by%20a%20thread.&text=For%20example%2C%20Runnable%20is%20implemented%20by%20class%20Thread%20.
            }
        }
    }

    private static void procesareCerere(Socket socket) {
        try {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                System.out.println("Am stabilit conexiunea. Așteptăm mesajul...");

                String mesajPrimit = in.readLine();
                System.out.println("Mesaj primit - " + mesajPrimit);
                out.printf("Mesajul de raspuns server - am primit: %s%n", mesajPrimit);

                System.out.println("Am terminat de procesat cererea - închidem conexiunea.");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
            }
        }
    }
}