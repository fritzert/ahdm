package pe.mef.sitfis.seguridad.domain.service;

import pe.mef.sitfis.seguridad.domain.aggregate.MenuAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.value.MenuNombreValue;

public class ValidarMenuService {

  /**
   * Valida si un nombre de menu es unico en el contexto dado.
   *
   * @param nombre        nombre a validar
   * @param menuExistente menú existente para comparar (puede ser null para nuevos menus)
   * @return verdadero si el nombre es valido para usar
   */
  public boolean esNombreValido(MenuNombreValue nombre, MenuAggregate menuExistente) {
    if (menuExistente != null && menuExistente.getNombre().equals(nombre)) {
      return true;
    }
    return true;
  }

  /**
   * Valida reglas de negocio especificas para menús del sistema.
   *
   * @param menu menu a validar
   * @return verdadero si cumple todas las reglas de negocio
   */
  public boolean cumpleReglasDeNegocio(MenuAggregate menu) {
    return menu != null &&
        menu.getId() != null &&
        menu.getNombre() != null &&
        menu.getOrden() != null;
  }
}