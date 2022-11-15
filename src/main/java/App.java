public class App {
    public static void main(String[] args) {
        // Prendre d'un fichier ou bien d'une entrée en argument ou console ?
        InputStream inputStream = InputFormatter.format("Procedure Toto declare a : entier; declare B : reel; a = 42; B = a Fin_Procedure Toto");
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        for (Token token : tokenStream.getTokens()) {
            System.out.println(token);
        }
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }
}
