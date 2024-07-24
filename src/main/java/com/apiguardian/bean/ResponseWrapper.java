package com.apiguardian.bean;

/**
 * Classe wrapper per l'oggetto di risposta della API Guardian Content
 *
 * @see <a target=_blank href="https://github.com/matarrese/content-api-the-guardian">Content API The Guardian</a> (per maggiori informazioni sul client java per interfacciarsi all'API)
 */
public class ResponseWrapper {

    private Response response;

    /**
     * Ritorna l'oggetto di risposta della API Guardian Content
     *
     * @return ggetto di risposta
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Inizializza l'oggetto di risposta della API Guardian Content
     *
     * @param response oggetto di risposta
     */
    public void setResponse(final Response response) {
        this.response = response;
    }

    /**
     * Costruttore della classe ResponseWrapper
     *
     * @param response oggetto di risposta
     */
    public ResponseWrapper(final Response response) {
        this.response = response;
    }

    /**
     * Costruttore di default della classe ResponseWrapper
     */
    public ResponseWrapper() {
    }
}
