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
            String line;
            int aux = 1; // número da expressão/equação atual, começando na 1 (primeira)

            while ((line = reader.readLine()) != null) {
                String[] v = line.split("\n"); // divide a ‘string’ pelas linhas (cada linha é uma expressão diferente)
                for (String s : v) {
                    phrase = s.split(" "); // divide a 'string' pelos espaços em branco

                    double result = Calculadora.fazOperacao(phrase); // calcula o resultado

                    if(Calculadora.isErroSintaxe()){ // se a equação apresentou erro de sintaxe
                        if(Calculadora.isErroAbreFecha()){
                            System.out.println("Expressão " + aux + " apresentou um erro nos abres e fechas.\n");
                        }
                        else if(Calculadora.isErroOperando()){
                            System.out.println("Expressão " + aux + " apresentou um erro em um dos operandos.\n");
                        }
                        else if(Calculadora.isErroOperador()){
                            System.out.println("Expressão " + aux + " apresentou um erro no operador.\n");
                        }
                        else{
                            System.out.println("Expressão " + aux + " apresentou um erro de sintaxe.\n");
                        }
                    }
                    else{ // se não apresentou erro de sintaxe
                        System.out.println("O resultado da expressão " + aux + " é: " + result + " - tamanho máximo da pilha: " + Calculadora.getMaxSizeReached() + "\n");
                    }

                    Calculadora.setMaxSizeReached(0); // reseta o tamanho máximo da pilha (para ir para a próxima expressão)
                    Calculadora.clearErros(); // reset dos estados de erro
                    aux++;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
    }
}
