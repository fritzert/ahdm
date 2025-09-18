package pe.mef.sitfis.seguridad.application.query;

public record PaginaApplicationQuery(
    int pagina,
    int tamanio,
    String ordenadoPor,
    String direccion) {

  public PaginaApplicationQuery {
    if (pagina < 0 || tamanio <= 0) {
      throw new IllegalArgumentException("Parámetros de paginación inválidos");
    }
    ordenadoPor = ordenadoPor == null ? "id" : ordenadoPor;
    direccion = direccion == null ? "desc" : direccion.toLowerCase();
  }

  public boolean esAscendente() {
    return "asc".equals(direccion);
  }
}
