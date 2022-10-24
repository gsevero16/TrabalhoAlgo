package src;

import java.util.EmptyStackException;

public class Calculadora {
    private static final PilhaArray pilhaArray = new PilhaArray();
    private static int maxSizeReached = 0; // maior tamanho que a pilha atingiu
    // contadores para checar se tem a mesma quantidade de abridores e fechadores
    private static int parentesesAbre = 0;
    private static int colchetesAbre = 0;
    private static int chavesAbre = 0;
    private static int parentesesFecha = 0;
    private static int colchetesFecha = 0;
    private static int chavesFecha = 0;

    public static double fazOperacao(String[] s){
        double res = 0;

        try{
            if(!checkOpenersAndClosers(s)){
                throw new Exception();
            }

        }catch(Exception e){
            System.out.println("\nErro de sintaxe.");
            return -1;
        }


        if(s.length == 0){ // se pilha estiver vazia, retorna erro de pilha vazia
            throw new EmptyStackException();
        }

        for (String c : s){ // adiciona elementos à pilha até encontrar um fechador
            if(isCloser(c)){
                res = findPairAndCalculate(c); // remove topo da lista até encontrar o par do fechador e calcula o que tiver no caminho
                pilhaArray.push(Double.toString(res)); // coloca o resultado de volta na pilha
            }

            else{
                pilhaArray.push(c); // coloca elemento na lista
            }
        }

        pilhaArray.clear(); // limpa a pilha após realizar todos os cálculos
        return res;
    }

    private static boolean isCloser(String c){ // checa se o char é um fechador (de parenteses, chaves ou colchetes)
        return c.equals(")") || c.equals("]") || c.equals("}");
    }

    private static boolean isOpener(String c){
        return c.equals("(") || c.equals("[") || c.equals("{");
    }

    private static boolean isNumber(String string){ // checa se o char atual é um número
        try {
            Double.parseDouble(string);
            return true;
          } catch(NumberFormatException e){
            return false;
          }
    }

    private static boolean isOperator(String string){ // checa se o char atual é um operador
        return string.equals("+") || string.equals("-") || string.equals("*") || string.equals("/") || string.equals("^");
    }

    private static double calculator(double num1, double num2, String op){ // realiza os cálculos
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

        if(pilhaArray.size() > maxSizeReached){ // compara o tamanho atual da pilha e o maior tamanho atingido previamente
            setMaxSizeReached(pilhaArray.size()); // se o tamanho atual for maior que o maior anterior, altera o valor da variável maxSizeReached
        }

        switch (c) {
            case ")" -> pair = "(";
            case "]" -> pair = "[";
            case "}" -> pair = "{";
            default -> System.out.println("Invalid Element"); // se o char não for um fechador, retorna um erro
        }

        while(!pilhaArray.top().equals(pair)){
            if(isNumber(pilhaArray.top())){ // se o elemento for um número
                if(assignedNum2){ // se o elemento for número e o num2 já fora usado
                    num1 = Double.parseDouble(pilhaArray.pop()); // coloca o outro número na variável num1
                }

                else{ // se o num2 (número depois do operador) ainda não tiver sido alterado
                    num2 = Double.parseDouble(pilhaArray.pop()); // coloca o número atual na variável num2
                    assignedNum2 = true;
                }
            }

            else if(isOperator(pilhaArray.top())) { // se o elemento for um operador
                    op = pilhaArray.pop(); // coloca o operador na variável op
            }

            else{ // se não for número ou operador, continua avançando na pilha
               pilhaArray.pop();
            }

        }
        res = calculator(num1, num2, op); // realiza o cálculo
        return res;
    }

    private static boolean checkOpenersAndClosers(String[] str){
        for(String s : str){
            if(isOpener(s)){
                if(isParentesesAbre(s)){
                    parentesesAbre++;
                }
                else if(isColchetesAbre(s)){
                    colchetesAbre++;
                }
                else{
                    chavesAbre++;
                }
            }

            else if(isCloser(s)){
                if(isParentesesFecha(s)){
                    parentesesFecha++;
                }
                else if(isColchetesFecha(s)){
                    colchetesFecha++;
                }
                else{
                    chavesFecha++;
                }
            }
        }
        return (parentesesAbre == parentesesFecha) && (chavesAbre == chavesFecha) && (colchetesAbre == colchetesFecha);
    }

    private static boolean isParentesesAbre(String str){
        return str.equals("(");
    }
    private static boolean isParentesesFecha(String str){
        return str.equals(")");
    }
    private static boolean isColchetesAbre(String str){
        return str.equals("[");
    }
    private static boolean isColchetesFecha(String str){
        return str.equals("]");
    }


    // Getters e setters
    public static int getMaxSizeReached() {
        return maxSizeReached;
    }

    public static void setMaxSizeReached(int maxSizeReached) {
        Calculadora.maxSizeReached = maxSizeReached;
    }

}
