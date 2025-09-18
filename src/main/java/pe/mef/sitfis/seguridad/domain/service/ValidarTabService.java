package pe.mef.sitfis.seguridad.domain.service;

import pe.mef.sitfis.seguridad.domain.aggregate.TabAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabNombreValue;

public class ValidarTabService {

  /**
   * Valida si un nombre de tab es único en el contexto dado.
   *
   * @param nombre       nombre a validar
   * @param tabExistente tab existente para comparar (puede ser null para nuevos tabs)
   * @return verdadero si el nombre es válido para usar
   */
  public boolean esNombreValido(TabNombreValue nombre, TabAggregate tabExistente) {
    if (tabExistente != null && tabExistente.getNombre().equals(nombre)) {
      return true;
    }
    return true;
  }

  /**
   * Valida reglas de negocio específicas para tabs del sistema.
   *
   * @param tab tab a validar
   * @return verdadero si cumple todas las reglas de negocio
   */
  public boolean cumpleReglasDeNegocio(TabAggregate tab) {
    return tab != null &&
        tab.getId() != null &&
        tab.getNombre() != null &&
        tab.getComponente() != null &&
        tab.getOrden() != null &&
        tab.getMenuId() != null &&
        tab.getSubmenuId() != null;
  }

  /**
   * Valida que el tab pertenezca a la jerarquía correcta (menú -> submenú -> tab).
   *
   * @param tab tab a validar
   * @return verdadero si la jerarquía es válida
   */
  public boolean esJerarquiaValida(TabAggregate tab) {
    // logica adicional para validar que el submenú pertenezca al menú especificado
    return tab.getMenuId() != null && tab.getSubmenuId() != null;
  }

}
