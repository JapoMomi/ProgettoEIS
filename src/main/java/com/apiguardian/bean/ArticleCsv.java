package com.apiguardian.bean;

/**
 * Classe che permette di memorizzare il contenuto di un file .CSV strutturato nel seguente modo:
 *
 * <ul>
 * <li> Identifier </li>
 * <li> Source_Set </li>
 * <li> Title </li>
 * <li> Body </li>
 * <li> Url </li>
 * <li> Date </li>
 * <li> Source </li>
 * </ul>
 *  Inoltre implementa i metodi per farsi restituire i sopracitati campi, i metodi per inizializzarli ed il toString:
 * <br>Get
 * <ul>
 * <li> {@link #getTitle()}</li>
 * <li> {@link #getBody()}  </li>
 * </ul>
 * Set
 * <ul>
 * <li> {@link #setIdentifier(String)}  </li>
 * <li> {@link #setSource_Set(String)}  </li>
 * <li> {@link #setTitle(String)}  </li>
 * <li> {@link #setBody(String)}  </li>
 * <li> {@link #setUrl(String)}  </li>
 * <li> {@link #setDate(String)}  </li>
 * <li> {@link #setSource(String)}  </li>
 * </ul>
 * toString
 * <ul>
 * <li> {@link #toString()}  </li>
 * </ul>
 *
 * @author Caporin Edoardo / Defend Enrico / Ferro Riccardo / Momesso Jacopo
 */
public class ArticleCsv {

    String Identifier;
    String Source_Set;
    String Title;
    String Body;
    String Url;
    String Date;
    String Source;

    /**
     * Costruttore della classe {@link ArticleCsv}
     *
     * @param identifier campo Identifier del file .CSV
     * @param source_Set campo Source_Set del file .CSV
     * @param title      campo Title del file .CSV
     * @param body       campo Body del file .CSV
     * @param url        campo Url del file .CSV
     * @param date       campo Date del file .CSV
     * @param source     campo Source del file .CSV
     */
    public ArticleCsv(
            final String identifier,
            final String source_Set,
            final String title,
            final String body,
            final String url,
            final String date,
            final String source) {
        Identifier = identifier;
        Source_Set = source_Set;
        Title = title;
        Body = body;
        Url = url;
        Date = date;
        Source = source;
    }

    /**
     * Inizializza Identifier
     *
     * @param identifier campo Identifier del file .CSV
     */
    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    /**
     * Inizializza source_Set
     *
     * @param source_Set campo source_Set del file .CSV
     */
    public void setSource_Set(String source_Set) {
        Source_Set = source_Set;
    }

    /**
     * Inizializza Title
     *
     * @param title campo Title del file .CSV
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Inizializza Body
     *
     * @param body campo Body del file .CSV
     */
    public void setBody(String body) {
        Body = body;
    }

    /**
     * Inizializza Url
     *
     * @param url campo Url del file .CSV
     */
    public void setUrl(String url) {
        Url = url;
    }

    /**
     * Inizializza Date
     *
     * @param date campo Date del file .CSV
     */
    public void setDate(String date) {
        Date = date;
    }

    /**
     * Inizializza Source
     *
     * @param source campo Source del file .CSV
     */
    public void setSource(String source) {
        Source = source;
    }

    /**
     * Ritorna Title
     *
     * @return Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Ritorna Body
     *
     * @return Body
     */
    public String getBody() {
        return Body;
    }

    /**
     * Ritorna una stringa che rappresenta il contenuto di {@link ArticleCsv}
     *
     * @return una stringa che rappresenta il contenuto di {@link ArticleCsv}
     */
    @Override
    public String toString() {
        return "ArticleCsv{" +
                "Identifier='" + Identifier + '\'' +
                ", Source_Set='" + Source_Set + '\'' +
                ", Title='" + Title + '\'' +
                ", Body='" + Body + '\'' +
                ", Url='" + Url + '\'' +
                ", Date='" + Date + '\'' +
                ", Source='" + Source + '\'' +
                '}';
    }
}
