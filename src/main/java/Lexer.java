public class Lexer {
    private InputStream inputStream;
    private final String keywords = " Procedure Fin_Procedure declare entier reel ";

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public TokenStream lex() {
        return new TokenStream();
    }
}
