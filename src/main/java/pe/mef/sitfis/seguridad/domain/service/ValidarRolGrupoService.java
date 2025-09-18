package pe.mef.sitfis.seguridad.domain.service;

import lombok.RequiredArgsConstructor;
import pe.mef.sitfis.seguridad.application.port.outbound.VerificarListaRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.VerificarUsuariosAsignadosPort;
import pe.mef.sitfis.seguridad.domain.aggregate.RolGrupoAggregate;
import pe.mef.sitfis.seguridad.domain.exception.RolGrupoNoPuedeEliminarseDomainException;

@RequiredArgsConstructor
public class ValidarRolGrupoService {

  private final VerificarUsuariosAsignadosPort verificarUsuariosAsignadosPort;
  private final VerificarListaRolGrupoPort verificarListaRolGrupoPort;

  /**
   * Valida reglas de negocio específicas para eliminación de RolGrupo.
   */
  public boolean puedeEliminarRolGrupo(RolGrupoAggregate rolGrupo) {
    if (rolGrupo == null) {
      return false;
    }

    // Aquí puedes agregar más reglas de negocio:
    // - Verificar si tiene usuarios asignados
    // - Verificar si tiene permisos críticos
    // - Verificar dependencias con otros módulos
    // Regla 1: No se puede eliminar si tiene usuarios asignados
    boolean tieneUsuariosAsignados = verificarUsuariosAsignadosPort
        .existenUsuariosAsignados(rolGrupo.getId().valor());

    if (tieneUsuariosAsignados) {
      return false;
    }

    // Regla 2: No se puede eliminar si tiene permisos/listas asignadas
    boolean tieneListasAsignadas = verificarListaRolGrupoPort
        .existenListasAsignadas(rolGrupo.getId().valor());

    if (tieneListasAsignadas) {
      return false;
    }

    // Regla 3: No se puede eliminar si es un rol crítico del sistema
    if (esRolCritico(rolGrupo)) {
      return false;
    }

    // Regla 4: No se puede eliminar si tiene restricciones activas
    if (tieneRestriccionesActivas(rolGrupo)) {
      return false;
    }

    return rolGrupo.puedeSerEliminado();
  }

  /**
   * Ejecuta validaciones pre-eliminación con mensajes específicos.
   */
  public void validarEliminacion(RolGrupoAggregate rolGrupo) {
    if (rolGrupo == null) {
      throw new RolGrupoNoPuedeEliminarseDomainException(
          "El RolGrupo no existe");
    }

    // Validación 1: Usuarios asignados
    boolean tieneUsuariosAsignados = verificarUsuariosAsignadosPort
        .existenUsuariosAsignados(rolGrupo.getId().valor());

    if (tieneUsuariosAsignados) {
      throw new RolGrupoNoPuedeEliminarseDomainException(
          "No se puede eliminar el RolGrupo porque tiene usuarios asignados. " +
              "Primero debe reasignar o eliminar los usuarios asociados.");
    }

    // Validación 2: Listas/permisos asignados
    boolean tieneListasAsignadas = verificarListaRolGrupoPort
        .existenListasAsignadas(rolGrupo.getId().valor());

    if (tieneListasAsignadas) {
      throw new RolGrupoNoPuedeEliminarseDomainException(
          "No se puede eliminar el RolGrupo porque tiene permisos/menús asignados. " +
              "Primero debe eliminar todas las asignaciones de permisos.");
    }

    // Validación 3: Rol crítico del sistema
    if (esRolCritico(rolGrupo)) {
      throw new RolGrupoNoPuedeEliminarseDomainException(
          "No se puede eliminar un RolGrupo crítico del sistema. " +
              "Este rol es necesario para el funcionamiento del sistema.");
    }

    // Validación 4: Restricciones activas
    if (tieneRestriccionesActivas(rolGrupo)) {
      throw new RolGrupoNoPuedeEliminarseDomainException(
          "No se puede eliminar el RolGrupo porque tiene restricciones activas. " +
              "Un rol con restricciones no puede ser eliminado por seguridad.");
    }
  }

  /**
   * Verifica si es un rol crítico del sistema (ej: Administrador).
   */
  private boolean esRolCritico(RolGrupoAggregate rolGrupo) {
    // IDs de roles críticos que no pueden eliminarse
    Long[] rolesCriticos = {1L, 2L}; // Ej: 1=Admin, 2=SuperAdmin

    return java.util.Arrays.asList(rolesCriticos)
        .contains(rolGrupo.getRolId().valor());
  }

  /**
   * Verifica si tiene restricciones activas.
   */
  private boolean tieneRestriccionesActivas(RolGrupoAggregate rolGrupo) {
    return rolGrupo.getFlagRestriccion().valor() == 1;
  }
}
