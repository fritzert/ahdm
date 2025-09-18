package pe.mef.sitfis.seguridad.application.query;

import java.util.List;

public record Pagina<T>(
    List<T> contenido,
    int paginaActual,
    int totalPaginas,
    long totalElementos) {

}
