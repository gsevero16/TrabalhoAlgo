
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Isabel H. Manssour
 */
public class LeituraArqJava {

    public static void main(String[] args) {
        BufferedReader reader;
         Path path1 = Paths.get("expressoes2.txt");
        try {
           reader = Files.newBufferedReader(path1, Charset.defaultCharset());
            String line = null;
            while( (line = reader.readLine()) != null) {
                System.out.println("--- Inicio expressao");
                String v[] = line.split(" "); // divide a string pelo espaco em branco
                for(String s : v) {
                    System.out.println(s);   
                }    
                System.out.println("--- Fim expressao");
            }
            reader.close();
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }        
        
    }
}
