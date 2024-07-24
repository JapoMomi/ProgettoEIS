package com.apiguardian.bean;

/**
 * Classe che permette di memorizzare il contenuto di una risposta fornita dalla TheGuardianApi strutturata nel seguente modo:
 *
 * <ul>
 * <li> status </li>
 * <li> userTier </li>
 * <li> total </li>
 * <li> startIndex </li>
 * <li> pageSize </li>
 * <li> currentPage </li>
 * <li> pages </li>
 * <li> orderBy </li>
 * <li> results </li>
 * </ul>
 * Inoltre implementa i metodi per farsi restituire i sopracitati campi, i metodi per inizializzarli ed il toString:
 * <br>Get
 * <ul>
 * <li> {@link #getStatus()}</li>
 * <li> {@link #getUserTier()}  </li>
 * <li> {@link #getTotal()}  </li>
 * <li> {@link #getStartIndex()}  </li>
 * <li> {@link #getPageSize()} ()}  </li>
 * <li> {@link #getCurrentPage()}  </li>
 * <li> {@link #getPages()}  </li>
 * <li> {@link #getOrderBy()}  </li>
 * <li> {@link #getResults()}  </li>
 * </ul>
 * Set
 * <ul>
 * <li> {@link #setStatus(String)}  </li>
 * <li> {@link #setUserTier(String)}  </li>
 * <li> {@link #setTotal(int)}  </li>
 * <li> {@link #setStartIndex(int)}  </li>
 * <li> {@link #setPageSize(int)}  </li>
 * <li> {@link #setCurrentPage(int)}  </li>
 * <li> {@link #setPages(int)}  </li>
 * <li> {@link #setResults(Article[])}  </li>
 * </ul>
 * toString
 * <ul>
 * <li> {@link #toString()}  </li>
 * </ul>
 *
 * @see Article
 * @see ResponseWrapper
 * @see <br><a href="https://open-platform.theguardian.com/documentation/search">API Documentation</a> (per maggiori informazioni sull'API)
 * @see <br><a target=_blank href="https://github.com/matarrese/content-api-the-guardian">Content API The Guardian</a> (per maggiori informazioni sul client java per interfacciarsi all'API)
 */
public class Response {

  String status;
  String userTier;
  int total;
  int startIndex;
  int pageSize;
  int currentPage;
  int pages;
  String orderBy;
  Article[] results;

  public Response() {
  }

  public Response(
          final String status,
          final String userTier,
          final int total,
          final int startIndex,
          final int pageSize,
          final int currentPage,
          final int pages,
          final String orderBy,
          final Article[] results
  ) {
    this.status = status;
    this.userTier = userTier;
    this.total = total;
    this.startIndex = startIndex;
    this.pageSize = pageSize;
    this.currentPage = currentPage;
    this.pages = pages;
    this.orderBy = orderBy;
    this.results = results;
  }

  /**
   * Ritorna status
   *
   * @return status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Inizializza status
   *
   * @param status stato della risposta dell'API
   */
  public void setStatus(final String status) {
    this.status = status;
  }

  /**
   * Ritorna userTier
   *
   * @return userTier
   */
  public String getUserTier() {
    return userTier;
  }

  /**
   * Inizializza userTier
   *
   * @param userTier licenza d'uso
   */
  public void setUserTier(final String userTier) {
    this.userTier = userTier;
  }

  /**
   * Ritorna total
   *
   * @return total
   */
  public int getTotal() {
    return total;
  }

  /**
   * Inizializza total
   *
   * @param total numero di risultati disponibili per la ricerca
   */
  public void setTotal(final int total) {
    this.total = total;
  }

  /**
   * Ritorna startIndex
   *
   * @return startIndex
   */
  public int getStartIndex() {
    return startIndex;
  }

  /**
   * Inizializza startIndex
   *
   * @param startIndex indice d'inizio
   */
  public void setStartIndex(final int startIndex) {
    this.startIndex = startIndex;
  }

  /**
   * Ritorna pageSize
   *
   * @return pageSize
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Inizializza pageSize
   *
   * @param pageSize numero di articoli presenti in una risposta
   */
  public void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * Ritorna currentPage
   *
   * @return currentPage
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * Inizializza currentPage
   *
   * @param currentPage numero della pagina che sto visualizzando
   */
  public void setCurrentPage(final int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * Ritorna pages
   *
   * @return pages
   */
  public int getPages() {
    return pages;
  }

  /**
   * Inizializza pages
   *
   * @param pages numero totale di pagine nella risposta
   */
  public void setPages(final int pages) {
    this.pages = pages;
  }

  /**
   * Ritorna orderBy
   *
   * @return orderBy
   */
  public String getOrderBy() {
    return orderBy;
  }

  /**
   * Inizializza orderBy
   *
   * @param orderBy criterio di ordinamento
   */
  public void setOrderBy(final String orderBy) {
    this.orderBy = orderBy;
  }

  /**
   * Ritorna results
   *
   * @return results
   */
  public Article[] getResults() {
    return results;
  }

  /**
   * Inizializza l'array di results
   *
   * @param results array contenente gli articoli di una risposta
   */
  public void setResults(final Article[] results) {
    this.results = results;
  }

  /**
   * Ritorna la lunghezza dell'array results
   *
   * @return results.length
   */
  public int getLength() { return results.length; }

}
