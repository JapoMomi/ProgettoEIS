package it.unipd.dei.eis_MFCD;

import java.util.Scanner;
import java.lang.reflect.*;

/** Classe che gestisce l'interazione tra utente e applicativo, tramite il metodo {@link #ask(String, boolean, boolean, Scanner)}
 *
 * @author Caporin Edoardo / Defend Enrico / Ferro Riccardo / Momesso Jacopo
 */
public abstract class InputHandler {

    /**
     * Richiede l'inserimento di un input corrispondente alla query per il Download dal TheGuardianAPI o
     * l'inserimento di un input corrispondente all'operazione che si vuole eseguire:
     * <ol>
     * <li>D: Download</li>
     * <li>E: Extraction</li>
     * <li>B: Both</li>
     * <li>M: Manage file directory</li>
     * <li>V: View file output</li>
     * <li>Q: Quit</li>
     * </ol>
     *
     * @param question       Stringa contenente il testo della domanda che appare a schermo
     * @param isAskingFormat variabile booleana per richiedere il formato del file
     * @param isAskingAction variabile booleana per richiedere un'istruizione
     * @param sc             oggetto Scanner
     * @return stringa contenente la query che sarà vuota se si sceglierà di non fare alcuna query
     *
     */
    public static String ask(String question, boolean isAskingFormat, boolean isAskingAction, Scanner sc) {
        // Creo l'oggetto scanner
        String query;
        boolean condition;

        try {

            do {

                if (isAskingFormat) {
                    System.out.println("1) 'j -> Json'");
                    System.out.println("2) 'C -> CSV'");
                } else if (isAskingAction) {
                    System.out.println(question);
                    System.out.println("1)'D' --> Download");
                    System.out.println("2)'E' --> Extraction");
                    System.out.println("3)'B' --> Both");
                    System.out.println("4)'M' --> Manage file directory");
                    System.out.println("5)'V' --> View file output");
                    System.out.println("6)'Q' --> Quit");
                } else {
                    System.out.println(question);
                    System.out.println("1) 'y' --> Yes");
                    System.out.println("2) 'n' --> No");
                }

                System.out.println("INPUT: ");

                String input_query = sc.nextLine();
                System.out.println("----------------------------------------------------------------------");

                // Controlli per verificare che si inserisca un carattere e non una String vuota o no
                if (input_query.length() == 0)
                    throw new StringIndexOutOfBoundsException();
                else if (input_query.length() > 1)
                    throw new NotACharacterException();
                else {
                    Class<?> tools = Tools.class;
                    Method method;

                    char choice_query = input_query.toUpperCase().charAt(0);

                    switch (choice_query) {

                        case 'J':
                            return "json_source";

                        case 'C':
                            return "csv_source";

                        case 'Y':
                            System.out.println("Query: ");
                            return sc.next();

                        case 'N':
                            return "";

                        case 'D':
                            condition = true;
                            Tools.invokeMethod(Tools.class, "DownloadSource");
                            break;

                        case 'E':
                            condition = true;
                            Tools.invokeMethod(Tools.class, "ExtractionSource");
                            break;

                        case 'B':
                            condition = true;
                            Tools.invokeMethod(Tools.class, "DownloadExtraction");
                            break;

                        case 'M':
                            condition = true;
                            Tools.invokeMethod(ShellManager.class, "Manager");
                            break;

                        case 'V':
                            condition = true;
                            Tools.invokeMethod(ShellManager.class, "openFile");
                            break;

                        case 'Q':
                            condition = true;
                            System.out.println("You are quitting the program...");
                            return "quit";

                        default:
                            System.out.println("The entered character doesn't match any possible choice. Retry.\n");
                            condition = false;
                            break;
                    }

                }

            } while (!condition);

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("You have to digit a character. Retry.\n");
            ask(question, isAskingFormat, isAskingAction, sc);
        } catch (NotACharacterException e) {
            System.out.println("You have just entered a String. Retry.\n");
            ask(question, isAskingFormat, isAskingAction, sc);
        }

        return "";
    }
}
