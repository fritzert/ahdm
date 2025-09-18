package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ActualizarRolGrupoRequest(
    Long id,
    RolRecord rolId,
    GrupoRecord grupoId,

    @Min(value = 0, message = "[flagConsulta] solo puede ser 0 o 1")
    @Max(value = 1, message = "[flagConsulta] solo puede ser 0 o 1")
    int flagRestriccion,

    @Min(value = 0, message = "[flagConsulta] solo puede ser 0 o 1")
    @Max(value = 1, message = "[flagConsulta] solo puede ser 0 o 1")
    int flagConsulta,

    @Min(value = 0, message = "[flagOperacion] solo puede ser 0 o 1")
    @Max(value = 1, message = "[flagOperacion] solo puede ser 0 o 1")
    int flagOperacion,

    @Min(value = 0, message = "[flagAsignarRecursos] solo puede ser 0 o 1")
    @Max(value = 1, message = "[flagAsignarRecursos] solo puede ser 0 o 1")
    int flagAsignarRecursos,

    @Min(value = 0, message = "[flagEnviarBandeja] solo puede ser 0 o 1")
    @Max(value = 1, message = "[flagEnviarBandeja] solo puede ser 0 o 1")
    int flagEnviarBandeja,

    @Min(value = 0, message = "[flagEnviarEtapa] solo puede ser 0 o 1")
    @Max(value = 1, message = "[flagEnviarEtapa] solo puede ser 0 o 1")
    int flagEnviarEtapa,

    @Min(value = 0, message = "[flagAdjuntarArchivo] solo puede ser 0 o 1")
    @Max(value = 1, message = "[flagAdjuntarArchivo] solo puede ser 0 o 1")
    int flagAdjuntarArchivo) {

  public record GrupoRecord(
      @NotNull(message = "[grupoId] No debe ser nulo")
      Long id) {

  }

  public record RolRecord(
      @NotNull(message = "[rolId] No debe ser nulo")
      Long id) {

  }
}
