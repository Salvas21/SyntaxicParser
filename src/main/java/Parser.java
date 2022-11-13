import java.util.ArrayList;

public class Parser {
    private final TokenStream tokenStream;
    private ParserStatus status;
    private VariableStorage storage;
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
            status = new ParserStatus(false, "Keyword : " + t.getValue() + ", ne correspond pas à un début de procédure");
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
        return status;
    }

    private void identifier() {
        Token t = tokenStream.next();
        if (!(t.getType() == TokenType.IDENTIFIER)) status = new ParserStatus(false, "Identifiant : " + t.getValue() + ", n'est pas accepté");
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
        Token declare = tokenStream.next();
        if (declare.getType() != TokenType.KEYWORD || !declare.getValue().equals("declare")) status = new ParserStatus(false, "Keyword : " + declare.getValue() + ", n'est pas reconnu");
//        keyword();
//        if (status.isValid()) {}
        Token var = tokenStream.next();
//        identifier();

        Token is = tokenStream.next();
        Token type = tokenStream.next();
        Token semicolon = tokenStream.next();

//        createVariable();
    }

    private void affectations() {

    }

    /*
        Token{type=KEYWORD, value='declare'}
        Token{type=IDENTIFIER, value='a'}
        Token{type=OPERATOR, value=':'}
        Token{type=KEYWORD, value='entier'}
        Token{type=PUNCTUATION, value=';'}
     */
}
