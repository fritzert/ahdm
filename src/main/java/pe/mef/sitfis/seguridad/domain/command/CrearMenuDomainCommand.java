package pe.mef.sitfis.seguridad.domain.command;

import pe.mef.sitfis.seguridad.domain.aggregate.value.MenuNombreValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.MenuOrdenValue;

public record CrearMenuDomainCommand(
    MenuNombreValue nombre,
    MenuOrdenValue orden
) {

  public CrearMenuDomainCommand {
    if (nombre == null || orden == null) {
      throw new IllegalArgumentException("Todos los campos son obligatorios");
    }
  }
}
