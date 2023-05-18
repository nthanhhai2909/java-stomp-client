import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {
//        String path = Main.class.getClassLoader().getResource("client.jar").getPath();
        String path = "C:\\Projects\\learns\\websocket\\client\\batch\\client.jar";
        new ProcessBuilder("java -jar " + path + " 34.214.76.213 8081").start();
    }
}
