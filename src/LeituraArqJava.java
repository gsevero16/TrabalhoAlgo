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
        String[] v = new String[100]; //criei com espaço de 100; sabe como pegar esse espaço com um lenght?
         
        try {
           reader = Files.newBufferedReader(path1, Charset.defaultCharset());
            String line = null;
            while( (line = reader.readLine()) != null) {
                v = line.split(" ");
            }
            reader.close();
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }
        
        //para cada string, ele chama o metodo que faz a operação
        for(String s : v) {
            int aux = 1; // apenas pra enumerar as operações
            int result = fazOperacao(s); //o tryparse é dentro do método
            System.out.println("O resultado da expressão " + aux + "é:" + result); //tem q exibir o resultado?
            aux++;
        }
        
    }
    
    //criei o método aqui mas dá pra depois botar em outro arquivo
    public static int fazOperacao(String s){
        int res = 0; //é o resultado da operação
        int num1 = Integer.parseInt(s);
        int num2 = Integer.parseInt(s);
        char op = ''; //serve pra saber a operação
        PilhaArray pilha = new PilhaArray(); //é a antepenultima resposta do forum sobre pilha
        
        for(int i = 0; i < s.lenght(); i++){
            if(s.lenght() == 0){ //verifica se a string tá vazia
                return 0;       //acho q daria pra mudar, botar tipo s.lenght() > 0, 
            }                   //e colocar o codigo de baixo no lugar do return da linha 45
            char c = s.charAt(i);
            
            //se abre,  na pilha (acho q tem q separar a condição dentro do for, né?)
            if(c == '{' || c == '('){
                pilha.push(c);
            }
            
            //se for numero, coloca na primeira vairavel
            //tem q ver se precisa desse try, se sim, teria que colocar um catch?
            if(c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' ||
                c == '7' || c == '8' || c == '9' || c == '0'){
                try{
                    num1 = Character.getNumericValue(c);
                }
            }
            //
            if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^'){
                op = c;
            }
            
            //se for numero, coloca na segunda vairavel
            //tem q ver se precisa desse try, se sim, teria que colocar um catch?
            if(c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' ||
                c == '7' || c == '8' || c == '9' || c == '0'){
                try{
                    num2 = Character.getNumericValue(c);
                }
            }
            
            if(c == '}' || c == ')'){
                
                //faz a operação
                //falta aquele contador de ate onde a pilha cresceu
                switch (op) {
                    case '+': 
                        res = num1 + num2; 
                        pilha.pop(); //teria como fazer tres pops para num1, num2 e c?
                        break;
                    case '-': 
                        res = num1 - num2; 
                        pilha.pop();
                        break;
                    case '*': 
                        res = num1 * num2; 
                        pilha.pop();
                        break;
                    case '/': 
                        res = num1 / num2; 
                        pilha.pop();
                        break;
                    case '^': 
                        res = num1 ^ num2; 
                        pilha.pop();
                        break;
                    break: //não sei o q botar aqui
                }
            }
            return res;
        }
        
        return 0;
    }
}
