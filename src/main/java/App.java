public class App {
    public static void main(String[] args) {
        System.out.println();
        simpleAnalysis();
        System.out.println();
        mediumAnalysis();
        System.out.println();
        hardAnalysis();
        System.out.println();
        simpleBadAnalysis();
        System.out.println();
        mediumBadAnalysis();
        System.out.println();
        hardBadAnalysis();
    }

    private static void simpleAnalysis() {
        String input = "Procedure Toto declare a : entier; a = 42 Fin_Procedure Toto";
        System.out.println(input);
        InputStream inputStream = InputFormatter.format(input);
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }

    private static void mediumAnalysis() {
        String input = "Procedure sandwich declare toto : reel; declare martin : entier; toto = 47.5; martin = 42 * 2 Fin_Procedure sandwich";
        System.out.println(input);
        InputStream inputStream = InputFormatter.format(input);
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }

    private static void hardAnalysis() {
        String input = "Procedure Toto declare a : entier; declare B : reel; declare c : reel; a = 42; B = a; c = 2 * B + 2 - (2 * 4); a = (B + c) Fin_Procedure Toto";
        System.out.println(input);
        InputStream inputStream = InputFormatter.format(input);
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }

    /**
     * Erreur : Keyword : 'Toto', n'est pas reconnu
     * Parce que le code est rendu à la fin du programme (donc a passé par les blocs de déclarations et
     * d'affectations avec succès), il s'attend à recevoir le keyword Fin_Procedure pour pouvoir clore
     * la procedure, et reçoit au lieu l'identifiant de procedure Toto par le manque de Fin_Procedure
     */
    private static void simpleBadAnalysis() {
        String input = "Procedure Toto declare a : entier; declare B : reel; declare c : reel; a = 42; B = a; c = 2 * B + 2 - (2 * 4); a = (B + c) Toto";
        System.out.println(input);
        InputStream inputStream = InputFormatter.format(input);
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }

    /**
     * Erreur : Token invalide : 'a' dans le bloc de déclarations
     * Parce que le code ne contient pas de déclarations et contient une affectation. Le code tente de lire
     * le début d'une declaration (s'attend au keyword declare obligatoirement), et obtient un identifiant.
     *
     */
    private static void mediumBadAnalysis() {
        String input = "Procedure Toto a = 42 Fin_Procedure Toto";
        System.out.println(input);
        InputStream inputStream = InputFormatter.format(input);
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }

    /**
     * Erreur : Fin abrupte du code dans le bloc d'affectation
     * Parce que le code ne contient pas de Fin_Procedure, le bloc d'affectation commence
     * sa vérification à cause de l'identifiant Toto, qui est considéré comme une variable,
     * lorsque le code arrête (fin de l'input), l'analyseur n'a pas complété le bloc
     * d'affectation (aucune affectation operateur =) donc il retourne l'erreur que le bloc n'est pas
     * valide dans sa totalité.
     */
    private static void hardBadAnalysis() {
        String input = "Procedure Toto declare a : entier; Toto";
        System.out.println(input);
        InputStream inputStream = InputFormatter.format(input);
        TokenStream tokenStream = (new Lexer(inputStream)).lex();
        ParserStatus status = (new Parser(tokenStream)).parse();
        System.out.println(status);
    }
}
