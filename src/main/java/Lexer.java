import java.util.ArrayList;
import java.util.Arrays;

public class Lexer {
    private InputStream inputStream;
//    private final String keywords = " Procedure Fin_Procedure declare entier reel ";
    private final String[] keywords = {"Procedure", "Fin_Procedure", "declare", "entier", "reel"};
    private ArrayList<Token> tokens;

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public TokenStream lex() {
        tokens = new ArrayList<>();

        while (!inputStream.endOfStream()) {
            readWhile(' ');
            if (inputStream.endOfStream()) return null;
            char c = inputStream.peek();
            if (isDigit(c)) tokens.add(readDigit());
            if (isIdentifier(c)) tokens.add(readIdentifier());
            if (isPunctuation(c)) tokens.add(readPunctuation());
            if (isOperator(c)) tokens.add(readOperator());
            // anything else is not part of the language
        }

        return new TokenStream(tokens);
    }

    private void readWhile(char c) {
        while (!inputStream.endOfStream() && inputStream.peek() == c) {
            inputStream.next();
        }
    }

    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    private Token readDigit() {
        StringBuilder digit = new StringBuilder();
        char c = inputStream.peek();
        while (!inputStream.endOfStream() && c != ' ') {
            digit.append(inputStream.next());
            c = inputStream.peek();
        }
        if (inputStream.endOfStream()) digit.append(inputStream.next());
        return new Token(TokenType.NUMBER, digit.toString());
    }

    private boolean isIdentifier(char c) {
        return Character.isAlphabetic(c);
    }

    private String removeSemicolon(String identifier) {
        return identifier.substring(0, identifier.length() - 1);
    }

    private boolean isKeyword(String identifier) {
        // TODO : identifier can be set as a keyword if letter contained in the whole string
        identifier = removeSemicolon(identifier);
        return Arrays.asList(keywords).contains(identifier);
    }

    private Token readIdentifier() {
        StringBuilder identifier = new StringBuilder();
        char c = inputStream.peek();
        while (!inputStream.endOfStream() && c != ' ') {
            identifier.append(inputStream.next());
            c = inputStream.peek();
        }
        if (inputStream.endOfStream()) identifier.append(inputStream.next()); // TODO : ???
        // TODO : look for semicolon
        TokenType tokenType = isKeyword(identifier.toString()) ? TokenType.KEYWORD : TokenType.IDENTIFIER;
        // TODO : identifier ne doit pas etre plus long que 8 char, dans lexical ou syntaxique ?
        return new Token(tokenType, identifier.toString());
    }

    private boolean isPunctuation(char c) {
        return c == '(' || c == ')';
    }

    private Token readPunctuation() {
        return new Token(TokenType.PUNCTUATION, inputStream.next().toString());
    }

    private boolean isOperator(char c) {
        String operators = "+-*/:=";
        return operators.indexOf(c) >= 0;
    }

    private Token readOperator() {
        return new Token(TokenType.OPERATOR, inputStream.next().toString());
    }
}
