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

    public TokenStream lex() throws Exception {
        tokens = new ArrayList<>();

        while (!inputStream.endOfStream()) {
            removeWhitespaces();
            if (inputStream.endOfStream()) return null;
            char c = inputStream.peek();
            if (isDigit(c)) readDigit();
            if (isIdentifier(c)) readIdentifier();
            if (isPunctuation(c)) readPunctuation();
            if (isOperator(c)) readOperator();
        }

        return new TokenStream(tokens);
    }

    private void removeWhitespaces() {
        while (!inputStream.endOfStream() && inputStream.peek() == ' ') {
            inputStream.next();
        }
    }

    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    private void readDigit() throws Exception {
        String digit = getSequence();

        validateNumberConditions(digit);

        // remove end semicolon
        boolean semicolon = hasSemicolon(digit);
        String number = semicolon ? removeEndChar(digit) : digit;

        // remove end parenthesis
        boolean parenthesis = hasEndParenthesis(number);
        if (parenthesis) number = removeEndChar(number);

        tokens.add(new Token(TokenType.NUMBER, number));

        // add tokens of removed chars
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

    private void readIdentifier() throws Exception {
        String identifier = getSequence();

        boolean semicolon = hasSemicolon(identifier);
        String id = semicolon ? removeEndChar(identifier) : identifier;

        boolean parenthesis = hasEndParenthesis(id);
        if (parenthesis) id = removeEndChar(id);

        TokenType tokenType = isKeyword(id) ? TokenType.KEYWORD : isType(id) ? TokenType.TYPE : TokenType.IDENTIFIER;

        if (tokenType == TokenType.IDENTIFIER) {
            validateIdentifierConditions(id);
        }

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

    private String getSequence() {
        StringBuilder digit = new StringBuilder();
        char c = inputStream.peek();
        while (!inputStream.endOfStream() && c != ' ') {
            digit.append(inputStream.next());
            c = inputStream.peek();
        }
        if (inputStream.endOfStream()) digit.append(inputStream.next());
        return digit.toString();
    }

    private void validateNumberConditions(String digit) throws Exception {
        long nbOfLetters = digit.chars().filter(Character::isAlphabetic).count();
        if (nbOfLetters > 0) {
            throw new Exception("Nombre '" + digit + "', invalide");
        }

        long commaAmount = digit.chars().filter(ch -> ch == ',').count();
        if (commaAmount > 1) {
            throw new Exception("Nombre '" + digit + "', invalide");
        }
    }

    private void validateIdentifierConditions(String identifier) throws Exception {
        if (identifier.length() > 8) {
            throw new Exception("Identifiant '" + identifier + "', invalide");
        }
    }
}
