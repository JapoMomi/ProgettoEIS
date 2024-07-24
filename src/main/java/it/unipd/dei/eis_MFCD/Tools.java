package it.unipd.dei.eis_MFCD;

import com.apiguardian.bean.Article;
import com.apiguardian.bean.ArticleCsv;
import com.apiguardian.bean.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonIOException;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.apiguardian.GuardianContentApi;
import org.apache.commons.csv.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Classe che permette:
 * <ul>
 * <li> Serializzazione di stream di bit in oggetti java quali {@link Article}, {@link Response} e {@link ArticleCsv} mediante il metodo: {@link #Serializer(Response)}</li>
 * <li> Deserializzazione di oggetti java in file .json mediante il metodo: {@link #GenericDeserializer(String)}</li>
 * <li> Inserimento scelta dell'operazione che si vuole eseguire mediante i metodi: {@link InputHandler#ask(String, boolean, boolean, Scanner)} } {@link #ExtractionSource()} {@link #DownloadSource()} </li>
 * <li> Download degli articoli da diverse sorgenti mediante il metodo: {@link #Download(String)} </li>
 * <li> Conteggio delle parole presente negli articoli mediante i metodi: {@link #WordsCounter(String)} {@link #Extraction(String)} </li>
 * <li> Gestione generale dell'esecuzione dell'applicazione mediante i metodi: {@link #Runner} </li>
 * </ul>
 *
 *
 * @author Caporin Edoardo / Defend Enrico / Ferro Riccardo / Momesso Jacopo
 */
public abstract class Tools {

    /**
     * SortedMap contenente il numero di parole presenti in un singolo articolo
     */
    private final static SortedMap<String, Integer> sortedMap = new TreeMap<>();

    /**
     * SortedMap contenente il numero di articoli in cui e' presente una parola
     */
    private final static SortedMap<String, Integer> resultMap = new TreeMap<>();

    /**
     * Array di stringhe che contiene tutte le parole da non considerare per il conteggio negli articoli
     */
    private static final String[] stopWords = {"A", "Able", "About", "Above", "According", "Accordingly", "Across", "Actually", "After", "Afterwards", "Again", "Against", "All", "Allow", "Allows", "Almost", "Alone", "Along", "Already", "Also", "Although", "Always", "Am", "Among", "Amongst", "An", "And", "Another", "Any", "Anybody", "Anyhow", "Anyone", "Anything", "Anyway", "Anyways", "Anywhere", "Apart", "Appear", "Appreciate", "Appropriate", "Are", "Around", "As", "Aside", "Ask", "Asking", "Associated", "At", "Available", "Away", "Awfully", "B", "Be", "Became", "Because", "Become", "Becomes", "Becoming", "Been", "Before", "Beforehand", "Behind", "Being", "Believe", "Below", "Beside", "Besides", "Best", "Better", "Between", "Beyond", "Both", "Brief", "But", "By", "C", "Came", "Can", "Cannot", "Cant", "Cause", "Causes", "Certain", "Certainly", "Changes", "Clearly", "Co", "Com", "Come", "Comes", "Concerning", "Consequently", "Consider", "Considering", "Contain", "Containing", "Contains", "Corresponding", "Could", "Course", "Currently", "D", "Definitely", "Described", "Despite", "Did", "Different", "Do", "Does", "Doing", "Done", "Down", "Downwards", "During", "E", "Each", "Edu", "Eg", "Eight", "Either", "Else", "Elsewhere", "Enough", "Entirely", "Especially", "Et", "Etc", "Even", "Ever", "Every", "Everybody", "Everyone", "Everything", "Everywhere", "Ex", "Exactly", "Example", "Except", "F", "Far", "Few", "Fifth", "First", "Five", "Followed", "Following", "Follows", "For", "Former", "Formerly", "Forth", "Four", "From", "Further", "Furthermore", "G", "Get", "Gets", "Getting", "Given", "Gives", "Go", "Goes", "Going", "Gone", "Got", "Gotten", "Greetings", "H", "Had", "Happens", "Hardly", "Has", "Have", "Having", "He", "Hello", "Help", "Hence", "Her", "Here", "Hereafter", "Hereby", "Herein", "Hereupon", "Hers", "Herself", "Hi", "Him", "Himself", "His", "Hither", "Hopefully", "How", "Howbeit", "However", "I", "Ie", "If", "Ignored", "Immediate", "In", "Inasmuch", "Inc", "Indeed", "Indicate", "Indicated", "Indicates", "Inner", "Insofar", "Instead", "Into", "Inward", "Is", "It", "Its", "Itself", "J", "Just", "K", "Keep", "Keeps", "Kept", "Know", "Knows", "Known", "L", "Last", "Lately", "Later", "Latter", "Latterly", "Least", "Less", "Lest", "Let", "Like", "Liked", "Likely", "Little", "Look", "Looking", "Looks", "Ltd", "M", "Mainly", "Many", "May", "Maybe", "Me", "Mean", "Meanwhile", "Merely", "Might", "More", "Moreover", "Most", "Mostly", "Much", "Must", "My", "Myself", "N", "Name", "Namely", "Nd", "Near", "Nearly", "Necessary", "Need", "Needs", "Neither", "Never", "Nevertheless", "New", "Next", "Nine", "No", "Nobody", "Non", "None", "Noone", "Nor", "Normally", "Not", "Nothing", "Novel", "Now", "Nowhere", "O", "Obviously", "Of", "Off", "Often", "Oh", "Ok", "Okay", "Old", "On", "Once", "One", "Ones", "Only", "Onto", "Or", "Other", "Others", "Otherwise", "Ought", "Our", "Ours", "Ourselves", "Out", "Outside", "Over", "Overall", "Own", "P", "Particular", "Particularly", "Per", "Perhaps", "Placed", "Please", "Plus", "Possible", "Presumably", "Probably", "Provides", "Q", "Que", "Quite", "Qv", "R", "Rather", "Rd", "Re", "Really", "Reasonably", "Regarding", "Regardless", "Regards", "Relatively", "Respectively", "Right", "S", "Said", "Same", "Saw", "Say", "Saying", "Says", "Second", "Secondly", "See", "Seeing", "Seem", "Seemed", "Seeming", "Seems", "Seen", "Self", "Selves", "Sensible", "Sent", "Serious", "Seriously", "Seven", "Several", "Shall", "She", "Should", "Since", "Six", "So", "Some", "Somebody", "Somehow", "Someone", "Something", "Sometime", "Sometimes", "Somewhat", "Somewhere", "Soon", "Sorry", "Specified", "Specify", "Specifying", "Still", "Sub", "Such", "Sup", "Sure", "T", "Take", "Taken", "Tell", "Tends", "Th", "Than", "Thank", "Thanks", "Thanx", "That", "Thats", "The", "Their", "Theirs", "Them", "Themselves", "Then", "Thence", "There", "Thereafter", "Thereby", "Therefore", "Therein", "Theres", "Thereupon", "These", "They", "Think", "Third", "This", "Thorough", "Thoroughly", "Those", "Though", "Three", "Through", "Throughout", "Thru", "Thus", "To", "Together", "Too", "Took", "Toward", "Towards", "Tried", "Tries", "Truly", "Try", "Trying", "Twice", "Two", "U", "Un", "Under", "Unfortunately", "Unless", "Unlikely", "Until", "Unto", "Up", "Upon", "Us", "Use", "Used", "Useful", "Uses", "Using", "Usually", "Uucp", "V", "Value", "Various", "Very", "Via", "Viz", "Vs", "W", "Want", "Wants", "Was", "Way", "We", "Welcome", "Well", "Went", "Were", "What", "Whatever", "When", "Whence", "Whenever", "Where", "Whereafter", "Whereas", "Whereby", "Wherein", "Whereupon", "Wherever", "Whether", "Which", "While", "Whither", "Who", "Whoever", "Whole", "Whom", "Whose", "Why", "Will", "Willing", "Wish", "With", "Within", "Without", "Wonder", "Would", "Would", "X", "Y", "Yes", "Yet", "You", "Your", "Yours", "Yourself", "Yourselves", "Z", "Zero", "a", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "b", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "c", "came", "can", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "course", "currently", "d", "definitely", "described", "despite", "did", "different", "do", "does", "doing", "done", "down", "downwards", "during", "e", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "f", "far", "few", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "g", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "h", "had", "happens", "hardly", "has", "have", "having", "he", "hello", "help", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "it", "its", "itself", "j", "just", "k", "keep", "keeps", "kept", "know", "knows", "known", "l", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "m", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "n", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "o", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "p", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "q", "que", "quite", "qv", "r", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "s", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "t", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "u", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "uucp", "v", "value", "various", "very", "via", "viz", "vs", "w", "want", "wants", "was", "way", "we", "welcome", "well", "went", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wonder", "would", "would", "x", "y", "yes", "yet", "you", "your", "yours", "yourself", "yourselves", "z", "zero", "m", "ll", "t", "ve", "re", "s", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~", "–", "…", "'t", "'s", "'m", "'ve", "'re"};

    /**
     * Ritorna l'array di stopWords
     *
     * @return stopWords
     */
    public static String[] getStopWords() {
        return stopWords;
    }


    /**
     * Ritorna la mappa di appoggio per il conteggio delle parole
     *
     * @return sortedMap
     */
    public static SortedMap<String, Integer> getSortedMap() {
        return sortedMap;
    }


    /**
     * Ritorna la mappa contenente il conteggio degli articoli contenenti una determinata parola
     *
     * @return resultMap
     */
    public static SortedMap<String, Integer> getResultMap() {
        return resultMap;
    }


    /**
     * Serializza un file e lo salva nella cartella <code>source_json</code> con il nome
     * <code>response_...</code>seguito dalle prime informazioni dell'id del primo articolo
     *
     * @param r un Response contenente gli articoli scaricati dalla TheGuardianApi
     */
    protected static void Serializer(Response r) {

        //Dichiaro e definisco filename con il prefisso "article". Conterrà poi il filename completo
        String filename = "response";
        //Stringa contenente l'id dell'articolo corrente. Il nome del file conterrà parte dell'id
        String sample = r.getResults()[0].getId();

        //Creo un oggetto scanner sulla stringa sample. Come delimitatore devo usare / perchè le parole sono separate da /
        Scanner sc = new Scanner(sample).useDelimiter("-");
        //Salvo nel filename la parola rimpiazzando i "/" con dei caratteri accettabili
        filename += "_" + sc.next().replace("/", "_");

        //Chiudo lo scanner
        sc.close();

        //Creo il Serializzatore Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(r);

        //Scrivo il file.json serializzato in un file in json_source con nome "filename"
        try (FileWriter fileWriter = new FileWriter("src/main/json_source/" + filename + ".json")) {
            fileWriter.write(json);
            System.out.println("\nFile JSON creato correttamente con il nome: " + filename + ".json\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Deserializza un file sulla base del percorso passato come parametro.
     * Nel caso in cui il percorso file corrisponde a <i>src/main/json_source</i> deserializziamo un json scaricato direttamente da TheGuardian.
     * Nel caso invece in cui il persorso file sia <i>src/main/csv_to_json</i> deserializziamo un file json precedentemente convertito da csv
     *
     * @param path Percorso dei file da deserializzare
     * @return L'array di object contenente o le Response o gli ArticleCsv
     */
    protected static Object[] GenericDeserializer(String path) {

        if (path.equals("src/main/json_source")) {

            // Creazione di un oggetto Gson
            Gson gson = new Gson();
            // Ottenere la lista dei file nella directory
            File directory = new File(path);
            File[] files = directory.listFiles();
            assert files != null;

            // Creo array di Response per memorizzare tutte le response nei file
            Response[] output = new Response[files.length];

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    try (FileReader reader = new FileReader(files[i])) {
                        // Deserializza l'oggetto Java dal file JSON utilizzando Gson
                        output[i] = gson.fromJson(reader, Response.class);
                    } catch (JsonSyntaxException | JsonIOException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return output;

        } else if (path.equals("src/main/csv_to_json")) {

            Gson gson = new GsonBuilder().create();
            File directory = new File(path);
            File[] files = directory.listFiles();
            assert files != null;

            // Creo un array di ArticleCsv dove memorizzare tutti gli articoli presenti in files
            ArticleCsv[] output = new ArticleCsv[1000 * files.length];

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(files[i]))) {
                        ArticleCsv[] tmp = gson.fromJson(reader, ArticleCsv[].class);
                        System.arraycopy(tmp, 0, output, 1000 * i, tmp.length);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return output;

        }

        return new Object[0];

    }


    /**
     * Effettua il Download degli articoli dalle sorgenti.
     * Nel caso in cui source sia <i>j</i> allora il download verrà effettuato interrogando la TheGuardianApi.
     * Nel caso invece in cui source sia <i>c</i> allora il download verrà effettuato da file in locale (percorso: <i>src/main/csv_source</i>) in formato .csv .
     *
     * @param source Scelta fatta dall'utente per decidere se fare Download da TheGuardianApi o da file .csv .
     */
    protected static void Download(String source) {

        if (source.equals("JSON")) {
            // Effettuo una richiesta all'API TheGuardian che mi restituirà una risposta
            GuardianContentApi api = new GuardianContentApi("test");
            Response response;

            try {

                String query = InputHandler.ask("Do you want to make a query?", false, false, new Scanner(System.in));
                response = api.getContent(query);

                if (response.getLength() == 0) {
                    System.out.println("Error 404, articles not found, we're downloading the most recent articles");
                    response = api.getContent("");
                }

            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }

            // Memorizzo source un file la risposta fornita dal TheGuardian
            Serializer(response);


        } else if (source.equals("CSV")) {
            File directory = new File("src/main/csv_source");
            File[] files = directory.listFiles();
            assert files != null;

            if (files.length == 0) {
                System.out.println("La cartella è vuota, bisogna inserire un file csv per poter fare il download, scegliere altre opzioni");
                return;
            }

            for (File file : files) {

                // Mi memorizzo il nome del file che creerò
                String filename = file.getName().replace("csv", "json");

                try (FileReader fileReader = new FileReader("src/main/csv_source/" + file.getName()); //da modificare inserendo array di file (vedi deserializzatore)
                     CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);
                     FileWriter fileWriter = new FileWriter("src/main/csv_to_json/" + filename)) {

                    JSONArray jsonArray = new JSONArray();

                    for (CSVRecord csvRecord : csvParser) {
                        JSONObject jsonObject = new JSONObject();
                        for (String header : csvParser.getHeaderMap().keySet()) {
                            jsonObject.put(header, csvRecord.get(header));
                        }
                        jsonArray.put(jsonObject);
                    }

                    fileWriter.write(jsonArray.toString(4));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("File json inserito nella cartella csv_to_json");
        }


    }


    /**
     * Effettua l'estrazione dei termini più presenti negli articoli dei files presenti nella directory specificata nel parametro path
     * Nel caso in cui il percorso file corrisponde a <i>src/main/json_source</i> estraiamo i termini dai file .json scaricati dalla TheGuardianApi.
     * Nel caso invece in cui il persorso file sia <i>src/main/csv_to_json</i> estraiamo i termini deai file .json prodotti dal Download da files .csv .
     *
     * @param path Percorso dei file da cui effettuare l'estrazione dei termini
     */
    protected static void Extraction(String path) {

        // Memorizzo in un array di Object gli oggetti Java deserializzati a partire da file .json.
        // Utilizzo un array di Object poichè non so a priori che tipo di oggetti mi restituirà il metodo GenericDeserializer()
        Object[] r = GenericDeserializer(path);

        // Verifico che l'array di Response è vuoto. Se lo è faccio reinserire il carattere per effettuare prima il Download
        if (r.length == 0) {
            InputHandler.ask("There are no file in the directory. Please select the Download (D) or the Both (B) option", false, true, new Scanner(System.in));
        }

        // Inizio l'estrazione delle parole nei file presenti nella directory json_source
        System.out.println("\nProcessing ...\n");

        for (int j = 0; j < r.length; j++) {

            // Verifico che tipo di oggetti sono presenti dell'array di Object
            // Mi basta controllare il primo elemento perchè tutti gli altri saranno uguali
            if (r[j] instanceof Response) {

                // Effettuo il cast a Response[] dato che gli oggetti contenuti in r sono certamente oggetti Response
                Response[] response = (Response[]) r;

                for (int i = 0; i < response[j].getLength(); i++) {

                    // Mi memorizzo in text il testo da cui devo estrarre i termini
                    String text = response[j].getResults()[i].getWebTitle() + " " + response[j].getResults()[i].getTrailText() + " " + response[j].getResults()[i].getBodyText();

                    // Chiamo il metodo per il conteggio delle parole
                    WordsCounter(text);

                }

            } else if (r[j] instanceof ArticleCsv) {

                // Effettuo il cast a ArticleCsv[] dato che gli oggetti contenuti in r sono certamente oggetti ArticleCsv
                ArticleCsv[] prova = (ArticleCsv[]) r;

                // Mi memorizzo in text il testo da cui devo estrarre i termini
                String text = prova[j].getTitle() + " " + prova[j].getBody();

                // Chiamo il metodo per il conteggio delle parole
                WordsCounter(text);

            }

        }

        // Comparatore
        Comparator<String> valueComparator = (key1, key2) -> {
            Integer value1 = resultMap.get(key1);
            Integer value2 = resultMap.get(key2);
            return value2.compareTo(value1);
        };

        // Ordinamento dei valori in ordine decrescente
        List<String> keys = new ArrayList<>(resultMap.keySet());
        keys.sort(valueComparator);

        // Prendi le prime 50 chiavi associate ai valori più grandi
        List<String> topKeys = keys.subList(0, Math.min(50, keys.size()));

        // Creazione del file contenente le 50 parole più presenti negli articoli
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (String key : topKeys) {
                writer.write(key + " " + resultMap.get(key));
                writer.newLine();
            }
            System.out.println("\nThe 50 keys that occur most often within the articles analyzed have been printed in the file: 'output.txt'");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

        // Pulisco la mappa
        resultMap.clear();

    }


    /**
     * Conta tutte le parole presenti in un articolo tenendo poi traccia di quante volte una parole compare in più articoli, non considerando le stopWords
     *
     * @param file_path Percorso del file su cui eseguire il conteggio delle parole
     */
    protected static void WordsCounter(String file_path) {

        Scanner sc = new Scanner(file_path);
        // Serve per non contare i caratteri speciali alla fine di una parola
        sc.useDelimiter("[^a-zA-Z]+");

        // Verifico quante volte una parola compare in un articolo
        while (sc.hasNext()) {

            String tmp = sc.next();

            tmp = tmp.toLowerCase();

            if (sortedMap.containsKey(tmp)) {

                int val = sortedMap.get(tmp);
                sortedMap.replace(tmp, val + 1);

            } else sortedMap.put(tmp, 1);
        }

        // Elimino dalla mappa le stopWords
        for (String stopWord : stopWords)
            sortedMap.remove(stopWord);

        // Mi faccio restituire un array contenente tutte le parole presenti in un articolo
        String[] temp = sortedMap.keySet().toArray(new String[0]);

        // Inserisco tutte le parole presenti in un articolo in una nuova mappa che terrà traccia di quante volte una parole compare in più articoli
        for (String word_in_map : temp) {
            if (resultMap.containsKey(word_in_map)) {

                int val = resultMap.get(word_in_map);
                resultMap.replace(word_in_map, val + 1);

            } else resultMap.put(word_in_map, 1);
        }

        // Pulisco la mappa dalle parole che mi sono salvato per rendere più efficente l'iterazione successiva
        sortedMap.clear();

        sc.close();


    }


    /**
     * Invoca un metodo specifico all'interno di una classe data.
     *
     * @param clazz       La classe in cui cercare il metodo.
     * @param method_name Il nome del metodo da invocare.
     * @throws RuntimeException se si verificano errori durante l'invocazione del metodo.
     */
    public static void invokeMethod(Class<?> clazz, String method_name) {
        // Dichiarazione della variabile method.
        Method method;
        try {
            // Ottiene il metodo specificato dalla classe.
            method = clazz.getDeclaredMethod(method_name);
            try {
                // Invoca il metodo sulla classe specificata.
                method.invoke(clazz);
            } catch (IllegalAccessException | InvocationTargetException e) {
                // Se si verificano errori durante l'invocazione del metodo, viene sollevata un'eccezione RuntimeException.
                throw new RuntimeException(e);
            }
        } catch (NoSuchMethodException e) {
            // Se il metodo specificato non viene trovato nella classe, viene sollevata un'eccezione RuntimeException.
            throw new RuntimeException(e);
        }
    }


    /**
     * Richiede l'inserimento di un input corrispondente al tipo di file da cui estrarre i termini:
     * <i>C: .json files generated from .csv files</i>
     * <i>J: .json files generated from TheGuardianApi downloaded articles</i>
     */
    private static void ExtractionSource() {
        Scanner sc = new Scanner(System.in);
        boolean condition;

        try {

            do {

                System.out.println("What kind of files do you want to use?");
                System.out.println("'C' --> .json files generated from .csv files");
                System.out.println("'J' --> .json files generated from TheGuardianApi downloaded articles");

                String in = sc.nextLine();
                System.out.println("----------------------------------------------------------------------");
                String path;

                if (in.length() == 0) throw new StringIndexOutOfBoundsException();
                else if (in.length() > 1) throw new NotACharacterException();
                else {

                    char ch = in.toUpperCase().charAt(0);

                    switch (ch) {

                        case 'C':
                            condition = true;
                            path = "src/main/csv_to_json";
                            Tools.Extraction(path);
                            break;
                        case 'J':
                            condition = true;
                            path = "src/main/json_source";
                            Tools.Extraction(path);
                            break;
                        default:
                            condition = false;
                            System.out.println("The entered character doesn't match any possible choice. Retry.\n");
                            break;
                    }
                }
            } while (!condition);

        } catch (NotACharacterException n) {
            System.out.println("You have just entered a String. Retry.");
            ExtractionSource();
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("You have to digit a character. Retry.");
            while (sc.hasNext())
                sc.next();
            ExtractionSource();
        }
    }


    /**
     * Questo metodo gestisce il download e l'estrazione dei dati.
     * Richiama il metodo `Extraction()` passando come argomento il risultato del metodo `DownloadSource()`.
     */
    private static void DownloadExtraction() {
        // Richiama il metodo DownloadSource() per ottenere la fonte di download dei dati
        String source = DownloadSource();

        // Richiama il metodo Extraction() passando la fonte di download come argomento
        Extraction(source);
    }


    /**
     * Richiede l'inserimento di un input corrispondente al tipo di file di cui si vuole effettuare il download:
     * <i>C: .json files generated from .csv files</i>
     * <i>J: .json files generated from TheGuardianApi downloaded articles</i>
     *
     * @return Percorso file da dove effettuare il download
     */
    private static String DownloadSource() {
        Scanner sc = new Scanner(System.in);
        String path = "";
        boolean condition;

        try {

            do {

                System.out.println("What kind of file do you want to download?");
                System.out.println("'C' --> .json files generated from .csv files");
                System.out.println("'J' --> .json files generated from TheGuardianApi downloaded articles");

                String in = sc.nextLine();
                System.out.println("----------------------------------------------------------------------");

                if (in.length() == 0) throw new StringIndexOutOfBoundsException();
                else if (in.length() > 1) throw new NotACharacterException();
                else {

                    char ch = in.toUpperCase().charAt(0);

                    switch (ch) {

                        case 'C':
                            condition = true;
                            path = "src/main/csv_to_json";
                            Tools.Download("CSV");
                            break;
                        case 'J':
                            condition = true;
                            path = "src/main/json_source";
                            Tools.Download("JSON");
                            break;
                        default:
                            condition = false;
                            System.out.println("The entered character doesn't match any possible choice. Retry.\n");
                            break;
                    }
                }
            } while (!condition);

        } catch (NotACharacterException n) {
            System.out.println("You have just entered a String. Retry.");
            DownloadSource();
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("You have to digit a character. Retry.");
            DownloadSource();
        }

        return path;
    }


    /**
     * Questo metodo esegue un ciclo che permette all'utente di selezionare un'azione da eseguire.
     * L'utente può digitare una delle opzioni disponibili per continuare o digitare "quit" per terminare il ciclo.
     * Dopo ogni selezione, viene richiesto all'utente di premere un tasto per procedere.
     * Infine, lo Scanner utilizzato per l'input viene chiuso.
     */
    public static void Runner() {
        // Variabile per controllare se continuare o interrompere il ciclo
        boolean keep_doing = true;

        // Creazione di uno Scanner per l'input dell'utente
        Scanner sc = new Scanner(System.in);

        while (keep_doing) {
            // Pulisce la finestra della shell o del terminale
            System.out.print("\u001B[H\u001B[2J");

            // Richiede all'utente di digitare una delle opzioni disponibili
            String keeping = InputHandler.ask("Digit one of the following characters to select the action", false, true, sc);

            // Verifica se l'utente ha digitato "quit" per terminare il ciclo
            if (keeping.equals("quit"))
                keep_doing = false;

            // Richiede all'utente di premere un tasto per procedere
            System.out.println("Premere un tasto per procedere");
            sc.nextLine();
        }

        // Chiude lo Scanner utilizzato per l'input
        sc.close();
    }


}