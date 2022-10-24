package src;

import java.util.EmptyStackException;

public class Calculadora {
    static PilhaArray pilhaArray = new PilhaArray();
    static int maxSize = 0;

    public static double fazOperacao(String[] s){
        double res = 0;

        if(s.length == 0){ // se pilha estiver vazia, retorna erro de pilha vazia
            throw new EmptyStackException();
        }

        for (String c : s){ // adiciona elementos à pilha até encontrar um fechador
            //String c = t;
            

            if(isCloser(c)){
                res = findPairAndCalculate(c); // remove topo da lista até encontrar o par do fechador e calcula o que tiver no caminho
                pilhaArray.push(Double.toString(res)); // coloca o resultado de volta na pilha (não acho que essa seja a forma correta)
            }

            else{
                pilhaArray.push(c);
                if(pilhaArray.size() > maxSize){
                    setMaxSize(pilhaArray.size());
                }
                else{
                    maxSize++;
                }
            }
            
        }
        pilhaArray.clear();
        return res;
    }

    private static boolean isCloser(String c){ // checa se o char é um fechador (de parenteses, chaves ou colchetes)
        
        return c.equals(")") || c.equals("]" )||  c.equals("}");
    }

    private static boolean isNumber(String string){ // checa se o char atual é um número
        //return Double.parseInt(string) ? true: false;

        //return string.chars().allMatch(Character::isDigit);
        try {
            Double.parseDouble(string);
            return true;
          } catch(NumberFormatException e){
            return false;
          }
    }

    private static boolean isOperator(String string){ // checa se o char atual é um operador
        return string.equals("+") || string.equals("-") ||
        string.equals("*") || string.equals("/") || string.equals("^");
    }

    private static double calculator(double num1, double num2, String op){
        return switch (op) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            case "^" -> Math.pow(num1, num2);
            default -> -1;
        };
    }

    private static double findPairAndCalculate(String c){ // limpa (com ‘pop’) a pilha até encontrar o par do char fechador e calcula
        String pair = "";
        double num1 = 0;
        double num2 = 0;
        String op = "";
        double res;
        boolean assignedNum2 = false; // boolean para checar se o num2 (segundo operando) já foi modificado

        switch (c) {
            case ")" -> pair = "(";
            case "]" -> pair = "[";
            case "}" -> pair = "{";
            default -> System.out.println("Invalid Element"); // se o char não for um fechador, retorna um erro
        }

        while(!pilhaArray.top().equals(pair)){
            if(isNumber(pilhaArray.top())){ // se o char for um número
                if(assignedNum2){ // se o char for número e o num2 já fora usado
                    num1 = Double.parseDouble(pilhaArray.pop()); // coloca o outro número na variável num1
                }

                else{ // se o num2 (número depois do operador) ainda não tiver sido alterado
                    num2 = Double.parseDouble(pilhaArray.pop()); // coloca o número atual na variável num2
                    assignedNum2 = true;
                }
            }

            else if(isOperator(pilhaArray.top())){
                op = pilhaArray.pop();
            }
            else{
               pilhaArray.pop();
            }
        }
        res = calculator(num1, num2, op);
        
        return res;
    }

    public static int getMaxSize() {
        return maxSize;
    }

    public static void setMaxSize(int maxSize) {
        Calculadora.maxSize = maxSize;
    }
}
