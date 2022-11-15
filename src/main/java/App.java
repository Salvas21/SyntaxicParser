public class App {
    public static void main(String[] args) {
        System.out.println();
        simpleAnalysis();
        System.out.println();
        mediumAnalysis();
    }

    private static void simpleAnalysis() {
        String input = "Procedure Toto declare a : entier; declare B : reel; a = 42; B = a Fin_Procedure Toto";
        System.out.println(input);
        InputStream inputStream = InputFormatter.format(input);
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }

    private static void mediumAnalysis() {
        String input = "Procedure Toto declare a : entier; declare B : reel; declare c : reel; a = 42; B = a; c = 2 * B + 2 - (2 * 4); a = (B + c) Fin_Procedure Toto";
        System.out.println(input);
        InputStream inputStream = InputFormatter.format(input);
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }

    private static void printTokens(TokenStream stream) {
        for (Token token : stream.getTokens()) {
            System.out.println(token);
        }
    }
}
