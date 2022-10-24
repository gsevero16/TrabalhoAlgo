package src;

import java.util.EmptyStackException;


public class Calculadora {
    private static final PilhaArray pilhaArray = new PilhaArray();
    private static int maxSizeReached = 0; // maior tamanho que a pilha atingiu
    private static boolean erroSintaxe = false; // usado para checar se ocorreu erro de sintaxe

    // contadores para checar se tem a mesma quantidade de abridores e fechadores
    private static int parentesesAbre = 0;
    private static int colchetesAbre = 0;
    private static int chavesAbre = 0;
    private static int parentesesFecha = 0;
    private static int colchetesFecha = 0;
    private static int chavesFecha = 0;

    public static double fazOperacao(String[] s) {
        double res = 0;

        try { // checa se têm o mesmo número de abridores e fechadores
            if (!checkOpenersAndClosers(s)) {
                throw new Exception();
            }

        } catch (Exception e) {
            setErroSintaxe(true);
            clearCounters(); // limpa os contadores
            return -1;
        }


        if (s.length == 0) { // se pilha estiver vazia, retorna erro de pilha vazia
            throw new EmptyStackException();
        }

        for (String c : s) { // adiciona elementos à pilha até encontrar um fechador
            if (isCloser(c)) {
                res = calculate(c); // remove topo da lista até encontrar o par do fechador e calcula o que tiver no caminho
                pilhaArray.push(Double.toString(res)); // coloca o resultado de volta na pilha
            } else {
                pilhaArray.push(c); // coloca elemento na lista
            }
        }

        pilhaArray.clear(); // limpa a pilha após realizar todos os cálculos
        return res;
    }

    private static double calculator(double num1, double num2, String op) { // realiza os cálculos
        return switch (op) {
            case "+" -> num1 + num2; // soma
            case "-" -> num1 - num2; // subtração
            case "*" -> num1 * num2; // multiplicação
            case "/" -> num1 / num2; // divisão
            case "^" -> Math.pow(num1, num2); // potência
            default -> throw new IllegalArgumentException("operador inválido.");
        };
    }

    private static double calculate(String c) { // limpa (com ‘pop’) a pilha até encontrar o par do char fechador e calcula
        String pair = ""; // par do fechador
        double num1 = 0; // operando antes do operador: (num1 op num2) <- exemplo: 54 * 10 -> o num1 seria o 54
        double num2 = 0; // operando depois do operador: (num1 op num2) <- exemplo: 54 * 10 -> o num2 seria o 10
        String op = ""; // operador
        double res; // resultado
        boolean assignedNum2 = false; // boolean para checar se o num2 (segundo operando) já foi modificado
        boolean assignedNum1 = false; // boolean para checar se foi encontrado o num1 antes do abre
        boolean assignedOperator = false; // boolean para checar se foi encontrado um operador antes do abre
        boolean foundPair = false; // boolean para checar se já chegou no par

        if (pilhaArray.size() > maxSizeReached) { // compara o tamanho atual da pilha e o maior tamanho atingido previamente
            setMaxSizeReached(pilhaArray.size()); // se o tamanho atual for maior que o maior anterior, altera o valor da variável maxSizeReached
        }

        switch (c) { // acha o par do fechador
            case ")" -> pair = "(";
            case "]" -> pair = "[";
            case "}" -> pair = "{";
            default -> System.out.println("Invalid Element"); // default para exceções (não é para cair aqui nunca)
        }


        //TODO: melhorar essa implementação (usando outros métodos) para reduzir complexidade do método (está funcionando, mas não é ideal)
        while (!foundPair) { // enquanto não achar o par do fechador (no caso o abre)
            try {
                if (pilhaArray.top().equals(pair)) { // encontrou o par
                    foundPair = true;
                }

                if ((foundPair && !assignedOperator) || (foundPair && !assignedNum1)) { // se encontrou o par, porém num1, op ou num2 não
                    throw new Exception();                                              // foram encontrados, retorna erro

                } else {
                    if (isNumber(pilhaArray.top())) { // se o elemento for um número
                        if (assignedNum2) { // se o elemento for número e o num2 já fora usado
                            num1 = Double.parseDouble(pilhaArray.pop()); // coloca o outro número na variável num1
                            assignedNum1 = true;

                        } else { // se o num2 (número depois do operador) ainda não tiver sido alterado
                            num2 = Double.parseDouble(pilhaArray.pop()); // coloca o número atual na variável num2
                            assignedNum2 = true;
                        }

                    } else if (isOperator(pilhaArray.top())) { // se o elemento for um operador
                        op = pilhaArray.pop(); // coloca o operador na variável op
                        assignedOperator = true;

                    } else { // se não for número ou operador, continua avançando na pilha
                        pilhaArray.pop();
                    }
                }

            } catch (Exception e) {
                setErroSintaxe(true);
                setMaxSizeReached(0); // se der erro, deixa o tamanho máximo atingido como 0 (isso é opcional,)
                return -1; // retorna -1 genérico (caso o resultado do cálculo seja -1 mesmo, não vai estar acompanhado da mensagem de
                            // erro de sintaxe)
            }

        }
        res = calculator(num1, num2, op); // realiza o cálculo
        return res;
    }

    private static void clearCounters() { // limpa os contadores
        setParentesesAbre(0);
        setColchetesAbre(0);
        setChavesAbre(0);
        setParentesesFecha(0);
        setColchetesFecha(0);
        setChavesFecha(0);
    }

    // métodos de checagem
    private static boolean checkOpenersAndClosers(String[] str) { // faz a checagem dos abres e fechas, usando contadores para comparar
        for (String s : str) {
            if (isOpener(s)) {
                if (isParentesesAbre(s)) {
                    parentesesAbre++;
                } else if (isColchetesAbre(s)) {
                    colchetesAbre++;
                } else {
                    chavesAbre++;
                }
            } else if (isCloser(s)) {
                if (isParentesesFecha(s)) {
                    parentesesFecha++;
                } else if (isColchetesFecha(s)) {
                    colchetesFecha++;
                } else {
                    chavesFecha++;
                }
            }
        }
        return (parentesesAbre == parentesesFecha) && (chavesAbre == chavesFecha) && (colchetesAbre == colchetesFecha);
    }
    private static boolean isCloser(String c) { // checa se o elemento é um fechador (de parenteses, chaves ou colchetes)
        return c.equals(")") || c.equals("]") || c.equals("}");
    }

    private static boolean isOpener(String c) { // checa se o elemento é um abridor (de parentes, chaves ou colchetes)
        return c.equals("(") || c.equals("[") || c.equals("{");
    }

    private static boolean isNumber(String string) { // checa se o char atual é um número
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String string) { // checa se o char atual é um operador
        return string.equals("+") || string.equals("-") || string.equals("*") || string.equals("/") || string.equals("^");
    }

    private static boolean isParentesesAbre(String str) {
        return str.equals("(");
    }

    private static boolean isParentesesFecha(String str) {
        return str.equals(")");
    }

    private static boolean isColchetesAbre(String str) {
        return str.equals("[");
    }

    private static boolean isColchetesFecha(String str) {
        return str.equals("]");
    }

    // Getters e setters
    public static int getMaxSizeReached() {
        return maxSizeReached;
    }

    public static void setMaxSizeReached(int maxSizeReached) {
        Calculadora.maxSizeReached = maxSizeReached;
    }

    public static void setParentesesAbre(int parentesesAbre) {
        Calculadora.parentesesAbre = parentesesAbre;
    }

    public static void setColchetesAbre(int colchetesAbre) {
        Calculadora.colchetesAbre = colchetesAbre;
    }

    public static void setChavesAbre(int chavesAbre) {
        Calculadora.chavesAbre = chavesAbre;
    }

    public static void setParentesesFecha(int parentesesFecha) {
        Calculadora.parentesesFecha = parentesesFecha;
    }

    public static void setColchetesFecha(int colchetesFecha) {
        Calculadora.colchetesFecha = colchetesFecha;
    }

    public static void setChavesFecha(int chavesFecha) {
        Calculadora.chavesFecha = chavesFecha;
    }

    public static boolean isErroSintaxe() {
        return erroSintaxe;
    }

    public static void setErroSintaxe(boolean erroSintaxe) {
        Calculadora.erroSintaxe = erroSintaxe;
    }
}
