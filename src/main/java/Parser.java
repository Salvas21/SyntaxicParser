import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    private final TokenStream tokenStream;
    private ParserStatus status;
    private VariableStorage storage;

    private String[] types = {"entier", "reel"};
    public Parser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }

    public ParserStatus parse() {
        status = new ParserStatus(true, "");
        storage = new VariableStorage();
        return procedure();
    }

    private ParserStatus procedure() {
        Token t = tokenStream.peek();
        if (!(t.getType() == TokenType.KEYWORD && t.getValue().equals("Procedure"))) {
            status = new ParserStatus(false, "Keyword : '" + t.getValue() + "', ne correspond pas à un début de procédure");
        }

        tokenStream.next();
        Token programId = tokenStream.peek();
        identifier();

        if (status.isValid()) {
            declarations();
        }

        if (status.isValid()) {
            affectations();
        }

        // end of procedure
        // programId
        return status;
    }

    private void identifier() {
        Token t = tokenStream.next();
        if (!(t.getType() == TokenType.IDENTIFIER)) status = new ParserStatus(false, "Identifiant : '" + t.getValue() + "', n'est pas accepté");
    }

    private void keyword(String keyword) {
        Token t = tokenStream.next();
        if (t.getType() != TokenType.KEYWORD || !t.getValue().equals(keyword)) status = new ParserStatus(false, "Keyword : '" + t.getValue() + "', n'est pas reconnu");
    }

    private void punctuation(String punc) {
        Token t = tokenStream.next();
        if (t.getType() != TokenType.PUNCTUATION || !t.getValue().equals(punc)) status = new ParserStatus(false, "Ponctuation : '" + t.getValue() + "', n'est pas reconnue");
    }

    private void operator(String op) {
        Token t = tokenStream.next();
        if (t.getType() != TokenType.OPERATOR || !t.getValue().equals(op)) status = new ParserStatus(false, "Operateur : '" + t.getValue() + "', n'est pas reconnu");
    }

    private void type() {
        Token t = tokenStream.next();
        if (t.getType() != TokenType.TYPE || notIn(types, t.getValue())) status = new ParserStatus(false, "Type : '" + t.getValue() + "', n'est pas reconnu");
    }

    private boolean notIn(String[] a, String s) {
        return !Arrays.asList(a).contains(s);
    }

    private void declarations() {
        Token t = tokenStream.peek();
        do {
            if (t.getType() == TokenType.KEYWORD && t.getValue().equals("declare")) {
                declaration();
            }
            t = tokenStream.peek();
        } while (!tokenStream.endOfStream() && (t.getType() == TokenType.KEYWORD && t.getValue().equals("declare")));
    }

    private void declaration() {
        keyword("declare");

        Token var = tokenStream.peek();
        if (status.isValid()) {
            identifier();
        }

        if (status.isValid()) {
            operator(":");
        }

        Token type = tokenStream.peek();
        if (status.isValid()) {
            type();
        }

        if (status.isValid()) {
            punctuation(";");
        }

        if (status.isValid()) {
            storage.add(var.getValue(), type.getValue());
        }
    }

    private void affectations() {

    }
}
