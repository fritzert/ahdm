package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.mef.sitfis.seguridad.application.command.ActualizarRolGrupoApplicationCommand;
import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoApplicationCommand;
import pe.mef.sitfis.seguridad.application.command.EliminarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.ObtenerRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;
import pe.mef.sitfis.seguridad.application.mapper.RolGrupoDomainMapper;
import pe.mef.sitfis.seguridad.application.port.inbound.ActualizarRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.CrearRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.EliminarRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRolGrupoPorGrupoIdUseCase;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarRolGrupoPorGrupoIdPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerRolGrupoPort;
import pe.mef.sitfis.seguridad.application.query.ListarRolGrupoApplicationQuery;
import pe.mef.sitfis.seguridad.domain.aggregate.RolGrupoAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.id.GrupoId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAdjuntarArchivoValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAsignarRecursosValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagConsultaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarBandejaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarEtapaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagOperacionValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagRestriccionValue;
import pe.mef.sitfis.seguridad.domain.command.ActualizarRolGrupoDomainCommand;
import pe.mef.sitfis.seguridad.domain.command.CrearRolGrupoDomainCommand;

@Slf4j
@RequiredArgsConstructor
public class RolGrupoService implements
    ListarRolGrupoPorGrupoIdUseCase,
    CrearRolGrupoUseCase,
    ActualizarRolGrupoUseCase,
    EliminarRolGrupoUseCase {

  private final ObtenerRolGrupoPort obtenerRolGrupoPort;
  private final BuscarRolGrupoPorGrupoIdPort buscarRolGrupoPorGrupoIdPort;
  private final GuardarRolGrupoPort guardarRolGrupoPort;
  private final ActualizarRolGrupoPort actualizarRolGrupoPort;
  private final EliminarRolGrupoPort eliminarRolGrupoPort;
  private final RolGrupoDomainMapper domainMapper;
//  private final ValidarRolGrupoService validarRolGrupoService;

  @Override
  public List<RolGrupoDto> listarPorGrupoId(ListarRolGrupoApplicationQuery query) {
    var domainQuery = domainMapper.toDomainQuery(query);
    return buscarRolGrupoPorGrupoIdPort.buscarPorGrupoId(domainQuery);
  }

  @Override
  public RolGrupoInfoDto crear(CrearRolGrupoApplicationCommand command) {
    var domainCommand = new CrearRolGrupoDomainCommand(
        RolId.de(command.rolId()),
        GrupoId.de(command.grupoId()),
        new RolGrupoFlagRestriccionValue(command.flagRestriccion()),
        new RolGrupoFlagConsultaValue(command.flagConsulta()),
        new RolGrupoFlagOperacionValue(command.flagOperacion()),
        new RolGrupoFlagAsignarRecursosValue(command.flagAsignarRecursos()),
        new RolGrupoFlagEnviarBandejaValue(command.flagEnviarBandeja()),
        new RolGrupoFlagEnviarEtapaValue(command.flagEnviarEtapa()),
        new RolGrupoFlagAdjuntarArchivoValue(command.flagAdjuntarArchivo())
    );

    var nuevoAgregado = RolGrupoAggregate.crear(domainCommand);

    // 2. Validaciones de dominio
    //validarRolGrupoService.validarCreacion(nuevoAgregado);

    // 3. Validaciones del aggregate
    nuevoAgregado.validarCreacion();

    // 4. Guardar aggregate
    var agregadoGuardado = guardarRolGrupoPort.guardar(domainCommand);

    // 5. Retornar DTO
    return construirInfoDto(agregadoGuardado);
  }

  @Override
  public RolGrupoInfoDto actualizar(Long id, ActualizarRolGrupoApplicationCommand command) {
    var obtenerCommand = new ObtenerRolGrupoCommand(id);
    var rolGrupoExistente = obtenerRolGrupoPort.obtenerPorId(obtenerCommand);

    var actualizarCommand = new ActualizarRolGrupoDomainCommand(
        rolGrupoExistente.getId(),
        RolId.de(command.rolId()),
        GrupoId.de(command.grupoId()),
        new RolGrupoFlagRestriccionValue(command.flagRestriccion()),
        new RolGrupoFlagConsultaValue(command.flagConsulta()),
        new RolGrupoFlagOperacionValue(command.flagOperacion()),
        new RolGrupoFlagAsignarRecursosValue(command.flagAsignarRecursos()),
        new RolGrupoFlagEnviarBandejaValue(command.flagEnviarBandeja()),
        new RolGrupoFlagEnviarEtapaValue(command.flagEnviarEtapa()),
        new RolGrupoFlagAdjuntarArchivoValue(command.flagAdjuntarArchivo())
    );

    // 2. Crear nuevo aggregate con cambios (lógica de dominio)
    var rolGrupoActualizado = RolGrupoAggregate.actualizar(actualizarCommand);

    // 3. Validaciones de dominio
    //validarRolGrupoService.validarActualizacion(rolGrupoActualizado, rolGrupoExistente);

    // 4. Validaciones del aggregate
    //rolGrupoActualizado.validarActualizacion();

    // 5. Actualizar aggregate
    var agregadoActualizado = actualizarRolGrupoPort.actualizar(actualizarCommand);

    // 6. Retornar DTO
    return construirInfoDto(agregadoActualizado);
  }

  @Override
  public void eliminar(EliminarRolGrupoCommand command) {
//    try {
    // 1. Obtener aggregate del dominio
    var obtenerCommand = new ObtenerRolGrupoCommand(command.id());
//    RolGrupoAggregate rolGrupoAggregate = obtenerRolGrupoPort.obtenerPorId(obtenerCommand);
    var rolGrupoAggregate = obtenerRolGrupoPort.obtenerPorId(obtenerCommand);

    var id = command.id();
//    RolGrupoAggregate rolGrupoAggregate = obtenerRolGrupoPort.obtenerPorId(id);

    // TODO: DTO DEBO CONVERTIRLO EN UN rolGrupoAggregate PARA LAS VALIDACIONES
    // 2. Validaciones de dominio
//    validarRolGrupoService.validarEliminacion(rolGrupoAggregate);

    // 3. Validaciones del aggregate
//    rolGrupoAggregate.validarEliminacion();

    // 4. Transformar a ApplicationCommand para el puerto
    var eliminarCommand = new EliminarRolGrupoCommand(id);

    // 5. Ejecutar la eliminación física
    eliminarRolGrupoPort.eliminar(eliminarCommand);

    log.info("RolGrupo con ID {} eliminado exitosamente", id);

//    } catch (RolGrupoNoPuedeEliminarseDomainException e) {
//      log.warn("No se puede eliminar RolGrupo {}: {}", id, e.getMessage());
//      throw new BusinessException("No se puede eliminar: " + e.getMessage());
//
//    } catch (RolGrupoInvalidoDomainException e) {
//      log.error("RolGrupo inválido {}: {}", id, e.getMessage());
//      throw new BusinessException("Estado inválido: " + e.getMessage());
//
//    } catch (EntityNotFoundException e) {
//      log.error("RolGrupo no encontrado: {}", id);
//      throw e;
//
//    } catch (Exception e) {
//      log.error("Error inesperado al eliminar RolGrupo {}: {}", id, e.getMessage());
//      throw new BusinessException("Error interno del sistema");
//    }
  }

  private RolGrupoInfoDto construirInfoDto(RolGrupoAggregate aggregate) {
    return new RolGrupoInfoDto(
        aggregate.getId().valor(),
        aggregate.getRolId().valor(),
        aggregate.getGrupoId().valor()
    );
  }

}