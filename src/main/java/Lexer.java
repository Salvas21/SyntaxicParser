import java.util.ArrayList;
import java.util.Arrays;

public class Lexer {
    private final InputStream inputStream;
    private final String[] keywords = {"Procedure", "Fin_Procedure", "declare"};
    private final String[] types = {"entier", "reel"};
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
            if (isDigit(c)) readDigit();
            if (isIdentifier(c)) readIdentifier();
            if (isPunctuation(c)) readPunctuation();
            if (isOperator(c)) readOperator();
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

    private void readDigit() {
        StringBuilder digit = new StringBuilder();
        char c = inputStream.peek();
        while (!inputStream.endOfStream() && c != ' ') {
            digit.append(inputStream.next());
            c = inputStream.peek();
        }
        if (inputStream.endOfStream()) digit.append(inputStream.next());
        // TODO : check if digit has only 1 ,

        boolean semicolon = hasSemicolon(digit.toString());
        String number = semicolon ? removeEndChar(digit.toString()) : digit.toString();

        boolean parenthesis = hasEndParenthesis(number);
        if (parenthesis) number = removeEndChar(number);

        tokens.add(new Token(TokenType.NUMBER, number));

        if (parenthesis) tokens.add(new Token(TokenType.PUNCTUATION, ")"));
        if (semicolon) tokens.add(new Token(TokenType.PUNCTUATION, ";"));
    }

    private boolean isIdentifier(char c) {
        return Character.isAlphabetic(c);
    }

    private boolean hasSemicolon(String s) {
        return s.contains(";");
    }

    private String removeEndChar(String identifier) {
        return identifier.substring(0, identifier.length() - 1);
    }

    private boolean hasEndParenthesis(String s) {
        return s.contains(")");
    }

    private boolean isKeyword(String identifier) {
        return Arrays.asList(keywords).contains(identifier);
    }

    private boolean isType(String identifier) {
        return Arrays.asList(types).contains(identifier);
    }

    private void readIdentifier() {
        StringBuilder identifier = new StringBuilder();
        char c = inputStream.peek();
        while (!inputStream.endOfStream() && c != ' ') {
            identifier.append(inputStream.next());
            c = inputStream.peek();
        }
        if (inputStream.endOfStream()) identifier.append(inputStream.next());

        boolean semicolon = hasSemicolon(identifier.toString());
        String id = semicolon ? removeEndChar(identifier.toString()) : identifier.toString();

        boolean parenthesis = hasEndParenthesis(id);
        if (parenthesis) id = removeEndChar(id);

        TokenType tokenType = isKeyword(id) ? TokenType.KEYWORD : isType(id) ? TokenType.TYPE : TokenType.IDENTIFIER;

        // TODO : identifier ne doit pas etre plus long que 8 char, dans lexical ou syntaxique ?
        tokens.add(new Token(tokenType, id));

        if (parenthesis) tokens.add(new Token(TokenType.PUNCTUATION, ")"));
        if (semicolon) tokens.add(new Token(TokenType.PUNCTUATION, ";"));
    }

    private boolean isPunctuation(char c) {
        return c == '(' || c == ')';
    }

    private void readPunctuation() {
        tokens.add(new Token(TokenType.PUNCTUATION, inputStream.next().toString()));
    }

    private boolean isOperator(char c) {
        String operators = "+-*/:=";
        return operators.indexOf(c) >= 0;
    }

    private void readOperator() {
        tokens.add(new Token(TokenType.OPERATOR, inputStream.next().toString()));
    }
}
