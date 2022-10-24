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
        String[] phrase = new String[200];
        String[] phraseWithoutSpace = new String[200];

        try {
            reader = Files.newBufferedReader(path1, Charset.defaultCharset());
            String line = null;
            while ((line = reader.readLine()) != null) {
                int i = 0;
                String v[] = line.split("\n"); // divide a string pelo espaco em branco
                for (String s : v) {
                    String removeSpace = "";
                    phrase = s.split(" ");
                    for (String t : phrase) {
                        removeSpace += t;
                    }
                    phraseWithoutSpace[i] = removeSpace;
                    i++;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

        //para cada string, ele chama o metodo que faz a operação
        int aux = 1;
        for (String s : phraseWithoutSpace) {
            // apenas pra enumerar as operações
            int result = Calculadora.fazOperacao(s); // o tryparse é dentro do método
            System.out.println("O resultado da expressão " + aux + " é: " + result);
            // tem q exibir o resultado?
            aux++;
        }
        System.out.println("Maior tamanho que a pilha alcançou: " +
                Calculadora.getMaxSize());
    }
}
