package pe.mef.sitfis.seguridad.domain.aggregate;

import pe.mef.sitfis.seguridad.domain.aggregate.id.GrupoId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolGrupoId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAdjuntarArchivoValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAsignarRecursosValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagConsultaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarBandejaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarEtapaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagOperacionValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagRestriccionValue;
import pe.mef.sitfis.seguridad.domain.exception.RolGrupoInvalidoDomainException;

public class RolGrupoAggregate {

  private final RolGrupoId id;
  private final RolId rolId;
  private final GrupoId grupoId;
  private final RolGrupoFlagRestriccionValue flagRestriccion;
  private final RolGrupoFlagConsultaValue flagConsulta;
  private final RolGrupoFlagOperacionValue flagOperacion;
  private final RolGrupoFlagAsignarRecursosValue flagAsignarRecursos;
  private final RolGrupoFlagEnviarBandejaValue flagEnviarBandeja;
  private final RolGrupoFlagEnviarEtapaValue flagEnviarEtapa;
  private final RolGrupoFlagAdjuntarArchivoValue flagAdjuntarArchivo;

  /**
   * Constructor para crear una nueva instancia del agregado RolGrupo.
   */
  public RolGrupoAggregate(RolGrupoId id, RolId rolId, GrupoId grupoId,
      RolGrupoFlagRestriccionValue flagRestriccion, RolGrupoFlagConsultaValue flagConsulta,
      RolGrupoFlagOperacionValue flagOperacion,
      RolGrupoFlagAsignarRecursosValue flagAsignarRecursos,
      RolGrupoFlagEnviarBandejaValue flagEnviarBandeja,
      RolGrupoFlagEnviarEtapaValue flagEnviarEtapa,
      RolGrupoFlagAdjuntarArchivoValue flagAdjuntarArchivo) {
    this.id = id;
    this.rolId = rolId;
    this.grupoId = grupoId;
    this.flagRestriccion = flagRestriccion;
    this.flagConsulta = flagConsulta;
    this.flagOperacion = flagOperacion;
    this.flagAsignarRecursos = flagAsignarRecursos;
    this.flagEnviarBandeja = flagEnviarBandeja;
    this.flagEnviarEtapa = flagEnviarEtapa;
    this.flagAdjuntarArchivo = flagAdjuntarArchivo;
    validarReglasDeNegocio();
  }

  /**
   * Valida las reglas de negocio básicas del aggregate.
   */
  private void validarReglasDeNegocio() {
    if (rolId == null) {
      throw new IllegalArgumentException("El ID del Rol no puede ser nulo");
    }
    if (grupoId == null) {
      throw new IllegalArgumentException("El ID del Grupo no puede ser nulo");
    }
    if (flagRestriccion == null) {
      throw new IllegalArgumentException("El flag de restricción no puede ser nulo");
    }
    if (flagConsulta == null) {
      throw new IllegalArgumentException("El flag de consulta no puede ser nulo");
    }
    if (flagOperacion == null) {
      throw new IllegalArgumentException("El flag de operación no puede ser nulo");
    }
    if (flagAsignarRecursos == null) {
      throw new IllegalArgumentException("El flag de asignar recursos no puede ser nulo");
    }
    if (flagEnviarBandeja == null) {
      throw new IllegalArgumentException("El flag de enviar a bandeja no puede ser nulo");
    }
    if (flagEnviarEtapa == null) {
      throw new IllegalArgumentException("El flag de enviar a etapa no puede ser nulo");
    }
    if (flagAdjuntarArchivo == null) {
      throw new IllegalArgumentException("El flag de adjuntar archivo no puede ser nulo");
    }
  }

  /**
   * Valida reglas para creación de nuevo RolGrupo.
   */
  public void validarCreacion() {
    validarReglasDeNegocio();

    // Validaciones específicas de creación
    if (id != null) {
      throw new RolGrupoInvalidoDomainException(
          "Un nuevo RolGrupo no debe tener ID asignado");
    }

    validarConsistenciaDeFlags();
    validarReglasDeNegocioEspecificas();
  }

  /**
   * Valida reglas para actualización de RolGrupo existente.
   */
  public void validarActualizacion() {
    validarReglasDeNegocio();

    // Validaciones específicas de actualización
    if (id == null) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo existente debe tener ID");
    }

    validarConsistenciaDeFlags();
    validarReglasDeNegocioEspecificas();
  }

  /**
   * Valida reglas para eliminación de RolGrupo.
   */
  public void validarEliminacion() {
    // Validación 1: Estado del aggregate
    if (!esEstadoValido()) {
      throw new RolGrupoInvalidoDomainException(
          "El RolGrupo no está en un estado válido para ser eliminado");
    }

    // Validación 2: Consistencia de datos
    if (!tieneIdsValidos()) {
      throw new RolGrupoInvalidoDomainException(
          "El RolGrupo tiene IDs inválidos y no puede ser eliminado");
    }

    // Validación 3: Flags críticos
    if (tieneFlagsCriticosActivos()) {
      throw new RolGrupoInvalidoDomainException(
          "El RolGrupo tiene configuraciones críticas activas que impiden su eliminación");
    }

    // Validación 4: Integridad referencial interna
    if (!esIntegridadReferencialValida()) {
      throw new RolGrupoInvalidoDomainException(
          "El RolGrupo no cumple con la integridad referencial requerida");
    }
  }

  /**
   * Valida consistencia entre flags según reglas de negocio.
   */
  private void validarConsistenciaDeFlags() {
    // Regla de negocio: Si tiene restricción activa, debe tener consulta
    if (flagRestriccion.valor() == 1 && flagConsulta.valor() == 0) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo con restricción activa debe tener permisos de consulta");
    }

    // Regla de negocio: Si puede operar, debe poder consultar
    if (flagOperacion.valor() == 1 && flagConsulta.valor() == 0) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo con permisos de operación debe tener permisos de consulta");
    }

    // Regla de negocio: Si puede asignar recursos, debe poder operar
    if (flagAsignarRecursos.valor() == 1 && flagOperacion.valor() == 0) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo que puede asignar recursos debe tener permisos de operación");
    }

    // Regla de negocio: Si puede enviar a bandeja, debe poder operar
    if (flagEnviarBandeja.valor() == 1 && flagOperacion.valor() == 0) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo que puede enviar a bandeja debe tener permisos de operación");
    }

    // Regla de negocio: Si puede enviar a etapa, debe poder operar
    if (flagEnviarEtapa.valor() == 1 && flagOperacion.valor() == 0) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo que puede enviar a etapa debe tener permisos de operación");
    }

    // Regla de negocio: Si puede adjuntar archivos, debe poder operar
    if (flagAdjuntarArchivo.valor() == 1 && flagOperacion.valor() == 0) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo que puede adjuntar archivos debe tener permisos de operación");
    }
  }

  /**
   * Valida reglas de negocio específicas del dominio.
   */
  private void validarReglasDeNegocioEspecificas() {
    // Regla: Un RolGrupo no puede tener el mismo ID para rol y grupo
    if (rolId.valor().equals(grupoId.valor())) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo no puede tener el mismo identificador para rol y grupo");
    }

    // Regla: Debe tener al menos un permiso activo
    if (noTienePermisosActivos()) {
      throw new RolGrupoInvalidoDomainException(
          "Un RolGrupo debe tener al menos un permiso activo");
    }
  }

  /**
   * Verifica si el aggregate puede ser eliminado según sus propias reglas internas.
   */
  public boolean puedeSerEliminado() {
    return esEstadoValido() &&
        tieneIdsValidos() &&
        !tieneFlagsCriticosActivos() &&
        esIntegridadReferencialValida();
  }

  /**
   * Verifica si el aggregate está en estado válido.
   */
  private boolean esEstadoValido() {
    return rolId != null && grupoId != null;
  }

  /**
   * Verifica si tiene flags críticos que impiden eliminación.
   */
  private boolean tieneFlagsCriticosActivos() {
    // Si tiene operaciones críticas activas, no se puede eliminar
    return flagOperacion.valor() == 1 &&
        flagAsignarRecursos.valor() == 1 &&
        flagRestriccion.valor() == 1;
  }

  /**
   * Verifica si los IDs son válidos.
   */
  private boolean tieneIdsValidos() {
    try {
      return rolId.valor() > 0 && grupoId.valor() > 0;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Verifica integridad referencial interna.
   */
  private boolean esIntegridadReferencialValida() {
    return !rolId.valor().equals(grupoId.valor());
  }

  /**
   * Verifica si no tiene permisos activos.
   */
  private boolean noTienePermisosActivos() {
    return flagConsulta.valor() == 0 &&
        flagOperacion.valor() == 0 &&
        flagAsignarRecursos.valor() == 0 &&
        flagEnviarBandeja.valor() == 0 &&
        flagEnviarEtapa.valor() == 0 &&
        flagAdjuntarArchivo.valor() == 0;
  }

  /**
   * Obtiene información sobre por qué no se puede eliminar.
   */
  public String obtenerRazonNoEliminable() {
    if (!esEstadoValido()) {
      return "El RolGrupo no está en estado válido";
    }

    if (tieneFlagsCriticosActivos()) {
      return "Tiene configuraciones críticas activas";
    }

    if (!tieneIdsValidos()) {
      return "Los identificadores no son válidos";
    }

    if (!esIntegridadReferencialValida()) {
      return "No cumple con la integridad referencial";
    }

    return "No se puede determinar la razón";
  }

  /**
   * Verifica si el RolGrupo tiene permisos específicos.
   */
  public boolean tienePermiso(String tipoPermiso) {
    return switch (tipoPermiso.toLowerCase()) {
      case "consulta" -> flagConsulta.valor() == 1;
      case "operacion" -> flagOperacion.valor() == 1;
      case "asignar_recursos" -> flagAsignarRecursos.valor() == 1;
      case "enviar_bandeja" -> flagEnviarBandeja.valor() == 1;
      case "enviar_etapa" -> flagEnviarEtapa.valor() == 1;
      case "adjuntar_archivo" -> flagAdjuntarArchivo.valor() == 1;
      case "restriccion" -> flagRestriccion.valor() == 1;
      default -> false;
    };
  }

  /**
   * Verifica si es un RolGrupo de solo lectura.
   */
  public boolean esSoloLectura() {
    return flagConsulta.valor() == 1 &&
        flagOperacion.valor() == 0 &&
        flagAsignarRecursos.valor() == 0 &&
        flagEnviarBandeja.valor() == 0 &&
        flagEnviarEtapa.valor() == 0 &&
        flagAdjuntarArchivo.valor() == 0;
  }

  /**
   * Verifica si es un RolGrupo con permisos completos.
   */
  public boolean tienePermisosCompletos() {
    return flagConsulta.valor() == 1 &&
        flagOperacion.valor() == 1 &&
        flagAsignarRecursos.valor() == 1 &&
        flagEnviarBandeja.valor() == 1 &&
        flagEnviarEtapa.valor() == 1 &&
        flagAdjuntarArchivo.valor() == 1;
  }

  // Getters
  public RolGrupoId getId() {
    return id;
  }

  public RolId getRolId() {
    return rolId;
  }

  public GrupoId getGrupoId() {
    return grupoId;
  }

  public RolGrupoFlagRestriccionValue getFlagRestriccion() {
    return flagRestriccion;
  }

  public RolGrupoFlagConsultaValue getFlagConsulta() {
    return flagConsulta;
  }

  public RolGrupoFlagOperacionValue getFlagOperacion() {
    return flagOperacion;
  }

  public RolGrupoFlagAsignarRecursosValue getFlagAsignarRecursos() {
    return flagAsignarRecursos;
  }

  public RolGrupoFlagEnviarBandejaValue getFlagEnviarBandeja() {
    return flagEnviarBandeja;
  }

  public RolGrupoFlagEnviarEtapaValue getFlagEnviarEtapa() {
    return flagEnviarEtapa;
  }

  public RolGrupoFlagAdjuntarArchivoValue getFlagAdjuntarArchivo() {
    return flagAdjuntarArchivo;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    RolGrupoAggregate that = (RolGrupoAggregate) obj;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "RolGrupoAggregate{" +
        "id=" + id +
        ", rolId=" + rolId +
        ", grupoId=" + grupoId +
        ", flagRestriccion=" + flagRestriccion.valor() +
        ", flagConsulta=" + flagConsulta.valor() +
        ", flagOperacion=" + flagOperacion.valor() +
        '}';
  }
}