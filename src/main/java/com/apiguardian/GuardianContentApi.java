package com.apiguardian;

import com.apiguardian.bean.Response;
import com.apiguardian.bean.ResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe per l'interazione con l'API Guardian Content
 *
 * @see <a target=_blank href="https://github.com/matarrese/content-api-the-guardian">Content API The Guardian</a> (per maggiori informazioni sul client java per interfacciarsi all'API)
 */
public class GuardianContentApi {

    static {
// Only one time
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
//          System.out.println(value+" - "+valueType.getName());
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private final static String TARGET_URL = "https://content.guardianapis.com/search?page-size=100&";
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String apiKey;
    private String section;
    private String tag;
    private Date toDate;
    private Date fromDate;

    /**
     * Costruttore della classe GuardianContentApi
     *
     * @param apiKey chiave API per l'accesso all'API Guardian Content
     */
    public GuardianContentApi(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Inizializza la sezione per la richiesta dei contenuti
     *
     * @param section sezione dei contenuti
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * Inizializza la data di inizio per la richiesta dei contenuti
     *
     * @param date data di inizio
     */
    public void setFromDate(Date date) {
        this.fromDate = date;
    }

    /**
     * Inizializza la data di fine per la richiesta dei contenuti
     *
     * @param date data di fine
     */
    public void setToDate(Date date) {
        this.toDate = date;
    }

    /**
     * Ottiene i contenuti dalla API Guardian Content senza specificare una query
     *
     * @return oggetto Response contenente i risultati della richiesta
     * @throws UnirestException se si verifica un errore nella richiesta HTTP
     */
    public Response getContent() throws UnirestException {
        return getContent(null);
    }

    /**
     * Ritorna tag
     *
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Inizializza tag
     *
     * @param tag tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Ottiene i contenuti dalla API Guardian Content
     *
     * @param query query per filtrare i contenuti
     * @return oggetto Response contenente i risultati della richiesta
     * @throws UnirestException se si verifica un errore nella richiesta HTTP
     */
    public Response getContent(String query) throws UnirestException {

        HttpRequest request = Unirest.get(TARGET_URL)
                .queryString("api-key", apiKey)
                .header("accept", "application/json");
        if (query != null && !query.isEmpty()) {
            request.queryString("q", query);
        }

        if (section != null && !section.isEmpty()) {
            request.queryString("section", section);
        }

        if (tag != null && !tag.isEmpty()) {
            request.queryString("tag", tag);
        }

        if (fromDate != null) {
            request.queryString("from-date", dateFormat.format(fromDate));
        }
        if (toDate != null) {
            request.queryString("to-date", dateFormat.format(toDate));
        }

        request.queryString("show-fields", "all");

        HttpResponse<ResponseWrapper> response = request.asObject(ResponseWrapper.class);
        return response.getBody().getResponse();

    }
}
