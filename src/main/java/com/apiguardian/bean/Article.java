package com.apiguardian.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Classe che permette di memorizzare il contenuto di un articolo fornito in formato .json strutturato nel seguente modo:
 *
 * <ul>
 * <li> id </li>
 * <li> type </li>
 * <li> sectionId </li>
 * <li> sectionName </li>
 * <li> webPublicationDate </li>
 * <li> webTitle </li>
 * <li> webUrl </li>
 * <li> apiUrl </li>
 * <li> isHosted </li>
 * <li> pillarId </li>
 * <li> pillarName </li>
 * <li> trailText </li>
 * <li> bodyText </li>
 * </ul>
 * Inoltre implementa i metodi per farsi restituire i sopracitati campi, i metodi per inizializzarli ed il toString:
 * <br>Get
 * <ul>
 * <li> {@link #getId()}</li>
 * <li> {@link #getType()}  </li>
 * <li> {@link #getSectionId()}  </li>
 * <li> {@link #getSectionName()}  </li>
 * <li> {@link #getWebPublicationDate()} ()}  </li>
 * <li> {@link #getWebTitle()}  </li>
 * <li> {@link #getWebUrl()}  </li>
 * <li> {@link #getApiUrl()}  </li>
 * <li> {@link #getIsHosted()}  </li>
 * <li> {@link #getPillarId()}  </li>
 * <li> {@link #getPillarName()}  </li>
 * <li> {@link #getTrailText()}  </li>
 * <li> {@link #getBodyText()}  </li>
 * </ul>
 * Set
 * <ul>
 * <li> {@link #setId(String)}  </li>
 * <li> {@link #setType(String)}  </li>
 * <li> {@link #setSectionId(String)}  </li>
 * <li> {@link #setSectionName(String)}  </li>
 * <li> {@link #setWebPublicationDate(String)}  </li>
 * <li> {@link #setWebTitle(String)}  </li>
 * <li> {@link #setWebUrl(String)}  </li>
 * <li> {@link #setApiUrl(String)}  </li>
 * <li> {@link #setIsHosted(boolean)}  </li>
 * <li> {@link #setPillarId(String)}  </li>
 * <li> {@link #setPillarName(String)}  </li>
 * <li> {@link #setTrailText(String)}  </li>
 * <li> {@link #setBodyText(String)}  </li>
 * </ul>
 * toString
 * <ul>
 * <li> {@link #toString()}  </li>
 * </ul>
 *
 * @see Response
 * @see <br><a target=_blank href="https://github.com/matarrese/content-api-the-guardian">Content API The Guardian</a> (per maggiori informazioni sul client java per interfacciarsi all'API)
 *
 */
public class Article {
    String id;
    String type;
    String sectionId;
    String sectionName;
    String webPublicationDate;
    String webTitle;
    String webUrl;
    String apiUrl;
    boolean isHosted;
    String pillarId;
    String pillarName;
    String trailText;
    String bodyText;

    /**
     * Costruttore della classe {@link Article}
     *
     * @param id                 campo id del formato .json
     * @param type               campo type del formato .json
     * @param sectionId          campo sectionId del formato .json
     * @param sectionName        campo sectionId del formato .json
     * @param webPublicationDate campo webPublicationDate del formato .json
     * @param webTitle           campo webTitle del formato .json
     * @param webUrl             campo webUrl del formato .json
     * @param apiUrl             campo apiUrl del formato .json
     * @param isHosted           campo isHosted del formato .json
     * @param pillarId           campo isHosted del formato .json
     * @param pillarName         campo pillarName del formato .json
     */
    public Article(
            final String id,
            final String type,
            final String sectionId,
            final String sectionName,
            final String webPublicationDate,
            final String webTitle,
            final String webUrl,
            final String apiUrl,
            final boolean isHosted,
            final String pillarId,
            final String pillarName
    ) {
        this.id = id;
        this.type = type;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.apiUrl = apiUrl;
        this.isHosted = isHosted;
        this.pillarId = pillarId;
        this.pillarName = pillarName;
    }

    /**
     * Costruttore di default della classe {@link Article}
     */
    public Article() {
    }

    /**
     * Ritorna l'id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Inizializza id
     *
     * @param id id dell'articolo
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Ritorna il type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Inizializza type
     *
     * @param type type dell'articolo
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Ritorna sectionId
     *
     * @return sectionId
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * Inizializza sectionId
     *
     * @param sectionId sectionId dell'articolo
     */
    public void setSectionId(final String sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Ritorna sectionName
     *
     * @return sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * Inizializza sectionName
     *
     * @param sectionName sectionName dell'articolo
     */
    public void setSectionName(final String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * Ritorna webPubblicationDate
     *
     * @return webPubblicationDate
     */
    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    /**
     * Inizializza webPubblicationDate
     *
     * @param webPublicationDate webPublicationDate dell'articolo
     */
    public void setWebPublicationDate(final String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    /**
     * Ritorna webTitle
     *
     * @return webTitle
     */
    public String getWebTitle() {
        return webTitle;
    }

    /**
     * Inizializza webTitle
     *
     * @param webTitle webTitle dell'articolo
     */
    public void setWebTitle(final String webTitle) {
        this.webTitle = webTitle;
    }

    /**
     * Ritorna webUrl
     *
     * @return webUrl
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * Inizializza webUrl
     *
     * @param webUrl webUrl dell'articolo
     */
    public void setWebUrl(final String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     * Ritorna apiUrl
     *
     * @return apiUrl
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Inizializza apiUrl
     *
     * @param apiUrl apiUrl dell'articolo
     */
    public void setApiUrl(final String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * Ritorna isHosted
     *
     * @return isHosted
     */
    public boolean getIsHosted() {
        return isHosted;
    }

    /**
     * Inizializza isHosted
     *
     * @param isHosted isHosted dell'articolo
     */
    public void setIsHosted(final boolean isHosted) {
        this.isHosted = isHosted;
    }

    /**
     * Ritorna pillarId
     *
     * @return pillarId
     */
    public String getPillarId() {
        return pillarId;
    }

    /**
     * Inizializza pillarId
     *
     * @param pillarId pillarId dell'articolo
     */
    public void setPillarId(final String pillarId) {
        this.pillarId = pillarId;
    }

    /**
     * Ritorna pillarName
     *
     * @return pillarName
     */
    public String getPillarName() {
        return pillarName;
    }

    /**
     * Inizializza pillarName
     *
     * @param pillarName pillarName dell'articolo
     */
    public void setPillarName(final String pillarName) {
        this.pillarName = pillarName;
    }

    /**
     * Ritorna trailText
     *
     * @return trailText
     */
    public String getTrailText() {
        return trailText;
    }

    /**
     * Inizializza trailText
     *
     * @param trailText trailText dell'articolo
     */
    public void setTrailText(String trailText) {
        this.trailText = trailText;
    }

    /**
     * Ritorna bodyText
     *
     * @return bodyText
     */
    public String getBodyText() {
        return bodyText;
    }

    /**
     * Inizializza bodyText
     *
     * @param bodyText testo dell'articolo
     */
    public void setBodyText(final String bodyText) {
        this.bodyText = bodyText;
    }

    /**
     * Inizializza bodyText e webTitle
     *
     * @param fields mappa contenente i fields di un articolo
     */
    @JsonProperty("fields")
    private void unpackNested(Map<String, Object> fields) {
        this.bodyText = (String) fields.get("bodyText");
        this.webTitle = (String) fields.get("webTitle");
    }

    /**
     * Ritorna una stringa che rappresenta il contenuto di {@link Article}
     *
     * @return una stringa che rappresenta il contenuto di {@link Article}
     */
    @Override
    public String toString() {
        return "\nArticle{" +
                "\n\nid='" + id + '\'' +
                ",\n\ntype='" + type + '\'' +
                ",\n\nsectionId='" + sectionId + '\'' +
                ",\n\nsectionName='" + sectionName + '\'' +
                ",\n\nwebPublicationDate='" + webPublicationDate + '\'' +
                ",\n\nwebTitle='" + webTitle + '\'' +
                ",\n\nwebUrl='" + webUrl + '\'' +
                ",\n\napiUrl='" + apiUrl + '\'' +
                ",\n\ntrailText='" + trailText + '\'' +
                ",\n\nbodyText='" + bodyText + '\'' +
                ",\n\nisHosted='" + isHosted + '\'' +
                ",\n\npillarId='" + pillarId + '\'' +
                ",\n\npillarName='" + pillarName + '\'' +
                "\n\n" + '}';
    }
}
