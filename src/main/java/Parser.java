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

        if (canContinue()) {
            declarations();
        }

        if (canContinue()) {
            affectations();
        }
        // TODO : what to do if cant continue

        // end of procedure
        // programId
        return status;
    }

    private void identifier() {
        Token t = tokenStream.next();
        if (!(t.getType() == TokenType.IDENTIFIER)) status = new ParserStatus(false, "Identifiant : '" + t.getValue() + "', n'est pas accepté");
    }

    private void variable() {
        Token t = tokenStream.peek();
        identifier();
        if (canContinue()) {
            if (storage.getVariable(t.getValue()) == null) status = new ParserStatus(false, "Variable : '" + t.getValue() + "', n'est pas définie");
        }
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
        } while (canContinue() && (t.getType() == TokenType.KEYWORD && t.getValue().equals("declare")));
    }

    private void declaration() {
        keyword("declare");

        // TODO : could break ?
        Token var = tokenStream.peek();
        if (canContinue()) {
            identifier();
        }

        if (canContinue()) {
            operator(":");
        }

        Token type = tokenStream.peek();
        if (canContinue()) {
            type();
        }

        if (canContinue()) {
            punctuation(";");
        }

        if (canContinue()) {
            storage.add(var.getValue(), type.getValue());
        }
    }

    private void affectations() {
        Token t = tokenStream.peek();
        do {
            if (t.getType() == TokenType.IDENTIFIER) {
                affectation();
            } else if (t.getType() == TokenType.PUNCTUATION && t.getValue().equals(";")) {
                tokenStream.next();
                t = tokenStream.peek();
                if (t.getType() == TokenType.IDENTIFIER) {
                    affectation();
                }
            }
            t = tokenStream.peek();
        } while (canContinue() && (t.getType() == TokenType.PUNCTUATION && t.getValue().equals(";")));
    }

    private void affectation() {
        if (canContinue()) {
            variable();
        }
        if (canContinue()) {
            operator("=");
        }
        if (canContinue()) {
            expression();
        }

    }

    private void expression() {
        term();
        if (canContinue()) {
            Token t = tokenStream.peek();
            while (canContinue() && (t.getType() == TokenType.OPERATOR && t.getValue().equals("+") || t.getValue().equals("-"))) {
                if (t.getValue().equals("+")) {
                    operator("+");
                } else if (t.getValue().equals("-")) {
                    operator("-");
                }
                term();
            }
        }
    }

    private void term() {
        factor();
        if (canContinue()) {
            Token t = tokenStream.peek();
            while (canContinue() && t.getType() == TokenType.OPERATOR && t.getValue().equals("*") || t.getValue().equals("/")) {
                if (t.getValue().equals("*")) {
                    operator("*");
                } else if (t.getValue().equals("/")) {
                    operator("/");
                }
                factor();
            }
        }
    }

    private void factor() {
        Token t = tokenStream.peek();
        variable();
        // if not a variable
        if (!status.isValid() && !tokenStream.endOfStream()) {
            if (t.getType() == TokenType.NUMBER) {
                status = new ParserStatus(true, "");
            } else if (t.getType() == TokenType.PUNCTUATION) {
                status = new ParserStatus(true, "");
                punctuation("("); // TODO : marchera pas faut surement faire un if punctuation et == '('
                if (canContinue()) {
                    expression();
                }
                if (canContinue()) {
                    punctuation(")");
                }
            }
        }
    }

    private boolean canContinue() {
        return status.isValid() && !tokenStream.endOfStream();
    }
}
