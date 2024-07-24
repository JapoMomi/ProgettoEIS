package it.unipd.dei.eis_MFCD;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe che gestisce le interazioni tra utente e sistema e permette:
 * <ul>
 * <li> Permette l'ispezione dell'explorer tramite il metodo {@link #Manager()}, chiamato dalla funzione {@link InputHandler#ask(String, boolean, boolean, Scanner)} </li>
 * <li> Permette l'apertura del file di output tramite il metodo {@link #openFile()}, chiamato dalla funzione {@link InputHandler#ask(String, boolean, boolean, Scanner)} </li>
 * </ul>
 *
 * @author Caporin Edoardo / Defend Enrico / Ferro Riccardo / Momesso Jacopo
 */
public abstract class ShellManager {

    /**
     * Metodo che gestisce l'input dell'utente e apre l'explorer della cartella di origine JSON o CSV.
     *
     * @throws RuntimeException se si verifica un'IOException durante l'apertura del file.
     */
    public static void Manager() {
        // Legge l'input dell'utente.
        String query = InputHandler.ask("", true, false, new Scanner(System.in));

        // Controlla se l'input corrisponde a "json_source" o "csv_source".
        if (query.equals("json_source") || query.equals("csv_source")) {
            try {
                // Apre il file specificato utilizzando l'applicazione predefinita associata al tipo di file.
                Desktop.getDesktop().open(new File("src/main/" + query));
            } catch (IOException e) {
                // Se si verifica un'IOException durante l'apertura del file, solleva un'eccezione RuntimeException.
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Metodo che apre il file "output.txt" utilizzando l'applicazione predefinita associata al tipo di file.
     * Se il file esiste, viene aperto utilizzando il metodo `Desktop.open()`.
     * Se il file non esiste, viene stampato un messaggio di avviso.
     *
     * @throws RuntimeException se si verifica un'IOException durante l'apertura del file.
     */
    public static void openFile() {
        // Crea un oggetto File che rappresenta il file "output.txt".
        File file = new File("output.txt");

        try {
            // Ottiene l'istanza di Desktop per aprire il file utilizzando l'applicazione predefinita associata al tipo di file.
            Desktop desktop = Desktop.getDesktop();

            // Verifica se il file esiste.
            if (file.exists()) {
                // Apre il file utilizzando il metodo `Desktop.open()`.
                desktop.open(file);
            } else {
                // Stampa un messaggio di avviso se il file non esiste.
                System.out.println("File not found: output.txt");
            }
        } catch (IOException e) {
            // Stampa un messaggio di errore se si verifica un'IOException durante l'apertura del file.
            System.out.println("An error occurred while opening the file: " + e.getMessage());
        }
    }

}
