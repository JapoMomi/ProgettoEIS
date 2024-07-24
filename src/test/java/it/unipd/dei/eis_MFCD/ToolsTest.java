package it.unipd.dei.eis_MFCD;

import com.apiguardian.bean.Response;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.apiguardian.GuardianContentApi;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.*;
import java.io.*;
import java.util.*;


/**
 * Class di test dei metodi di {@Link it.unipd.dei.eis_MFCD.Tools}
 * <p>
 *
 * <strong> Summar: </strong> La classe testa il funzionamento di tutti i metodi di {@Link it.unipd.dei.eis_MFCD.Tools}. <br>
 * I metodi sono divisi in 4 sezioni principali:
 *
 * <ol>
 * <li> Serializzatore
 * <ul>
 * <li> {@Link #testSerializer} </li>
 * </ul>
 * </li>
 * <li> Deserializzatore
 * <ul>
 * <li> {@Link #testGenericDeserializer} </li>
 * </ul>
 * </li>
 * <li> Download
 * <ul>
 * <li> {@Link #testDownload} </li>
 * </ul>
 * </li>
 * <li> Words counter
 * <ul>
 * <li> {@Link #testWordsCounter} </li>
 * <li> {@Link #testExtraction} </li>
 * </ul>
 * </li>
 * </ol>
 * <strong>Test Case Design:</strong> Ogni metodo di questa classe testa il
 * rispettivo metodo della classe {@link Tools}, quindi il
 * test case si occupa di controllare il corretto funzionamento di ogni suo
 * metodo.
 *
 * <strong>Expected Results:</strong>
 * Il test case e' da considerarsi positivo
 * quando ogni test method risulta corretto, quindi quando terminano senza
 * errori.<br>
 *
 * @author Caporin Edoardo / Defend Enrico / Ferro Riccardo / Momesso Jacopo
 * @see Tools
 */

public class ToolsTest extends TestCase {

    /**
     * Test per il metodo Serializer() il quale verifica la corretta creazione di un file .json.
     * Il test effettua una richiesta all'API TheGuardian utilizzando una parola chiave di test ("bitcoin")
     * e ottiene una risposta. Viene successivamente invocato il metodo Serializer() per salvare
     * la risposta in un file .json. Il test verifica che il file sia stato creato correttamente
     * nel percorso specificato ed infine elimina il file creato per pulire l'ambiente di test
     */
    @Test
    public void testSerializer() throws UnirestException {

        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                // Ignora l'output
            }
        }));

        // Effettuo una richiesta all'API TheGuardian che mi restituirà una risposta
        GuardianContentApi api = new GuardianContentApi("test");
        Response response = api.getContent("bitcoin");

        // Esegui il metodo Serializer
        Tools.Serializer(response);

        String sample = response.getResults()[0].getId();
        Scanner sc = new Scanner(sample).useDelimiter("-");
        // Verifica che il file sia stato creato correttamente
        String expectedFilename = "response";
        expectedFilename += "_" + sc.next().replace("/", "_");
        String expectedPath = "src/main/json_source/" + expectedFilename + ".json";

        // Verifica che il file sia stato creato correttamente nel percorso atteso
        File file = new File(expectedPath);
        Assert.assertTrue(file.exists());

        // Elimino il file creato per il test
        if (file.exists())
            file.delete();
    }

    /**
     * Test per il metodo GenericDeserializer() il quale verifica la corretta deserializzazione di file .json e .csv.
     * Il test esegue la deserializzazione dei file presenti nelle directory specificate per i file .json e .csv.
     * Per entrambe le directory dei file, il test verifica che il risultato della deserializzazione non sia nullo
     * e che contenga almeno un elemento
     */
    @Test
    public void testGenericDeserializer() {

        // Path di test per la directory JSON
        String jsonPath = "src/main/json_source";
        // Path di test per la directory CSV
        String csvPath = "src/main/csv_to_json";

        File directory = new File(jsonPath);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                Object[] jsonResult = Tools.GenericDeserializer(jsonPath);
                assertNotNull(jsonResult);
                assertTrue(jsonResult.length > 0);
            }
        }

        directory = new File(csvPath);
        files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // Esegui il test per la directory CSV
                Object[] csvResult = Tools.GenericDeserializer(csvPath);
                assertNotNull(csvResult);
                assertTrue(csvResult.length > 0);
            }
        }

    }

    /**
     * Test per il metodo Download() che verifica il corretto download dei file .json e .csv.
     * Il test simula l'input e verifica che i file vengano scaricati correttamente.
     * <p>
     * Per il download dei file .json:
     * - Viene impostato l'input di test per la fonte "J" (.json).
     * - Prima del download, viene creato un array di file nella directory "src/main/json_source".
     * - Viene eseguito il metodo Download() per scaricare i file .json.
     * - Dopo il download, viene creato un nuovo array di file nella directory "src/main/json_source".
     * - Viene verificato che la lunghezza dell'array dopo il download sia diversa da quella precedente.
     * - Viene controllato che il primo file nell'array dopo il download sia diverso dal primo file nell'array precedente,
     * confermando così che un file è stato sovrascritto o creato durante il download.
     * - Se un nuovo file è stato creato durante il download, viene eliminato per ripristinare lo stato precedente.
     * - Se nessun nuovo file è stato creato, viene verificato che il primo file nell'array dopo il download sia uguale
     * al primo file nell'array precedente, confermando che non ci sono stati cambiamenti.
     * <p>
     * Per il download dei file .csv:
     * - Viene creato un array di file nella directory "src/main/csv_source" come input di partenza.
     * - Viene eseguito il metodo Download() per scaricare i file .csv.
     * - Viene creato un array di file nella directory "src/main/csv_to_json" dopo il download.
     * - Viene verificato che la lunghezza dell'array dei file di output sia uguale a quella dell'array di input,
     * confermando che non ci sono stati cambiamenti nel numero di file.
     * - Se l'array di file di output iniziale è vuoto, vengono eliminati tutti i file nella directory "src/main/csv_to_json"
     * per ripristinare lo stato precedente
     */
    @Test
    public void testDownload() {
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                // Ignora l'output
            }
        }));

        // Imposta l'input di test per la fonte "J"
        String testInput = "n";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);

        // Creo array di file della directory src/main/json_source prima del Download
        File directory1 = new File("src/main/json_source");
        File[] beforeJ = directory1.listFiles();
        // Ordina i file in base alla data di modifica (in ordine decrescente)
        Arrays.sort(beforeJ, Comparator.comparingLong(File::lastModified).reversed());

        // Esegui il metodo Download
        Tools.Download("JSON");

        // Creo array di file della directory src/main/json_source dopo del Download
        File[] afterJ = directory1.listFiles();
        // Ordina i file in base alla data di modifica (in ordine decrescente)
        Arrays.sort(afterJ, Comparator.comparingLong(File::lastModified).reversed());

        // Se effettivamente la lunghezza di afterJ è uguale a before allora ha sovrascritto un file già presente
        if (afterJ.length != beforeJ.length) {
            // Controllo che i primi elementi dei due array siano diversi per verificare che effettivamente ci sia stato il download
            assertNotEquals(beforeJ[0], afterJ[0]);
            // Elimino il file creato per il test
            if (afterJ[0].exists())
                afterJ[0].delete();
        } else {
            //Ha sovrascritto un file --> c'era già
            assertEquals(beforeJ[0], afterJ[0]);
        }

        // Ripristina l'input standard da tastiera
        System.setIn(System.in);

        // Creo gli array per verificare il metodo Download a partire dai file .csv
        File inputC = new File("src/main/csv_source");
        File targetC = new File("src/main/csv_to_json");

        File[] sourceC = inputC.listFiles();
        File[] outputB = targetC.listFiles();

        Tools.Download("C");

        File[] outputC = targetC.listFiles();

        assertEquals(sourceC.length, outputC.length);

        if (outputB.length < 1)
            for (File output : outputC)
                if (output.exists())
                    output.delete();
    }

    /**
     * Metodo di test per la funzione Extraction(), che verifica l'estrazione di parole chiave dai file .json
     * Verifica se il metodo di Extraction funziona correttamente analizzando la directory di input e controllando se l'output contiene la stringa desiderata.
     * Il test esegue i seguenti passaggi:
     * Verifica se il file "output.txt" esiste nella directory corrente.
     * Se il file esiste, ne effettua una copia in "temp.txt".
     * Prepara l'input specificando la directory di origine, ovvero "src/main/json_source".
     * Reindirizza lo stream di output per catturare l'output del metodo Extraction.
     * Esegue il metodo di Extraction.
     * Aggiorna la lista dei file nella directory corrente.
     * Ripristina lo stream di output originale.
     * Ottiene l'output del metodo Extraction.
     * Verifica se l'output contiene la stringa desiderata.
     * Elimina il file "output.txt" se presente.
     * Rinomina il file "temp.txt" in "output.txt" se il file originale esisteva in precedenza
     */
    @Test
    public void testExtraction() {
        boolean outputExists = false;
        File directory = new File(Paths.get("").toAbsolutePath().toString());
        File[] files = directory.listFiles();

        for (File temp : files) {
            if (temp.getName().equals("output.txt")) {
                outputExists = true;
                Path sourcePath = Paths.get(Paths.get("").toAbsolutePath().toString() + "/output.txt");
                Path destinationPath = Paths.get(Paths.get("").toAbsolutePath().toString() + "/temp.txt");

                try {
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                }
            }
        }
        // Preparazione dell'input
        String path = "src/main/json_source";

        // Reindirizzamento dello stream di output per catturare l'output del metodo Extraction
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Esecuzione del metodo Extraction
        Tools.Extraction(path);

        //Aggiorna lista files
        files = directory.listFiles();

        // Ripristino dello stream di output originale
        System.setOut(originalOut);

        // Ottenimento dell'output del metodo Extraction
        String output = outputStream.toString();

        // Assert per verificare se l'output contiene la stringa desiderata
        assertTrue(output.contains("The 50 keys that occur most often within the articles analyzed"));

        for (File temp : files) {
            if (temp.getName().equals("output.txt"))
                temp.delete();
        }

        for (File temp : files) {
            if (temp.getName().equals("temp.txt") && outputExists)
                temp.renameTo(new File("output.txt"));
        }
    }

    /**
     * Metodo di test per la funzione WordsCounter() della classe Tools per verificare se conta correttamente il numero di occorrenze delle parole in un testo.
     * Utilizza un testo di input predefinito e confronta il risultato atteso con il risultato ottenuto.
     * Se i due risultati corrispondono, il test viene considerato superato, altrimenti fallito
     */
    @Test
    public void testWordsCounter() {
        // Test input
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        // Risultato aspettato
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("lorem", 1);
        expectedMap.put("ipsum", 1);
        expectedMap.put("dolor", 1);
        expectedMap.put("sit", 1);
        expectedMap.put("amet", 1);
        expectedMap.put("consectetur", 1);
        expectedMap.put("adipiscing", 1);
        expectedMap.put("elit", 1);

        Tools.WordsCounter(text);

        // Verifica del risultato
        Assert.assertEquals(expectedMap, Tools.getResultMap());
    }

}