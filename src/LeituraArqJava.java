package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LeituraArqJava {

    public static void main(String[] args) {
        BufferedReader reader;
        Path path1 = Paths.get("expressoes2.txt");
        String[] phrase;

        try {
            reader = Files.newBufferedReader(path1, Charset.defaultCharset());
            String line = null;
            int aux = 1;
            while ((line = reader.readLine()) != null) {
                String[] v = line.split("\n"); // divide a ‘string’ pelo espaço em branco
                for (String s : v) {
                    phrase = s.split(" ");
                    double result = Calculadora.fazOperacao(phrase);
                    System.out.println("O resultado da expressão " + aux + " é: " + result + " - tamanho máximo da pilha: " + Calculadora.getMaxSizeReached() + "\n");
                    Calculadora.setMaxSizeReached(0);
                    aux++;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
    }
}
