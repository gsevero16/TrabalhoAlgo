package src;

import java.util.EmptyStackException;

public class Calculadora {
    static PilhaArray pilhaArray = new PilhaArray();

    public static int fazOperacao(String s){
        int res = 0;
        char aux; // guarda o fechador (para poder comparar com o abridor '(' ou '[' ou '{' )

        if(s.length() == 0){ // se pilha estiver vazia, retorna erro de pilha vazia
            throw new EmptyStackException();
        }

        for(int i = 0; i < s.length(); i++){ // adiciona elementos à pilha até encontrar um fechador
            char c = s.charAt(i);

            if(isCloser(c)){
                aux = c;
                res = findPairAndCalculate(aux); // remove topo da lista até encontrar o par do fechador e calcula o que tiver no caminho
                pilhaArray.push((char) res); // coloca o resultado de volta na pilha (não acho que essa seja a forma correta)
            }

            else{
                pilhaArray.push(c);
            }
        }



        return res;
    }

    private static boolean isCloser(char c){ // checa se o char é um fechador (de parenteses, chaves ou colchetes)
        return c == ')' || c == ']' || c == '}';
    }

    private static boolean isNumber(char c){ // checa se o char atual é um número
        return Character.isDigit(c);
    }

    private static boolean isOperator(char c){ // checa se o char atual é um operador
        return c == '+' || c == '-' || c == '*' || c == '/' || c =='^';
    }

    private static int calculator(int num1, int num2, char op){
        return switch (op) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            case '^' -> num1 ^ num2;
            default -> throw new IllegalArgumentException("Invalid Operator");
        };
    }

    private static int findPairAndCalculate(char closer){ // limpa (com ‘pop’) a pilha até encontrar o par do char fechador e calcula
        char pair;
        int num1 = 0;
        int num2 = 0;
        char op = '\0';
        int res = 0;
        boolean assignedNum2 = false; // boolean para checar se o num2 (segundo operando) já foi modificado

        switch (closer) {
            case ')' -> pair = '(';
            case ']' -> pair = '[';
            case '}' -> pair = '{';
            default -> throw new IllegalArgumentException("Invalid Element"); // se o char não for um fechador, retorna um erro
        }

        while(pilhaArray.top() != pair){
            if(isNumber(pilhaArray.top())){ // se o char for um número
                if(assignedNum2){ // se o char for número e o num2 já fora usado
                    num1 = pilhaArray.pop(); // coloca o outro número na variável num1
                }

                else{ // se o num2 (número depois do operador) ainda não tiver sido alterado
                    num2 = pilhaArray.pop(); // coloca o número atual na variável num2
                    assignedNum2 = true;
                }
            }

            else if(isOperator(pilhaArray.top())){
                op = pilhaArray.pop();
            }
            else{
               pilhaArray.pop();
            }

            pilhaArray.pop();
        }
        res = calculator(num1, num2, op);
        return res;
    }



}
