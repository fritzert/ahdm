package pe.mef.sitfis.seguridad.domain.query;

public record PaginaDomainQuery(
    int pagina,
    int tamanio,
    String ordenadoPor,
    String direccion
) {

  public PaginaDomainQuery {
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
