package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.mef.sitfis.seguridad.application.command.ActualizarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.EliminarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.GuardarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.ObtenerRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;
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
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolGrupoId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAdjuntarArchivoValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAsignarRecursosValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagConsultaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarBandejaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarEtapaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagOperacionValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagRestriccionValue;

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
//  private final ValidarRolGrupoService validarRolGrupoService;

  @Override
  public List<RolGrupoDto> listarPorGrupoId(ListarRolGrupoApplicationQuery query) {
    return buscarRolGrupoPorGrupoIdPort.buscarPorGrupoId(query);
  }

  @Override
  public RolGrupoInfoDto crear(CrearRolGrupoCommand command) {
    log.info("Creando rolGrupo con datos: {}", command);
    // 1. Crear aggregate manualmente
    RolGrupoAggregate nuevoRolGrupo = new RolGrupoAggregate(
//        null, // ID se asigna al guardar
        RolGrupoId.nuevo(),
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

    // 2. Validaciones de dominio
    //validarRolGrupoService.validarCreacion(nuevoRolGrupo);

    // 3. Validaciones del aggregate
    //nuevoRolGrupo.validarCreacion();

    // 4. Transformar a ApplicationCommand para el puerto
    var guardarCommand = new GuardarRolGrupoCommand(
        command.rolId(),
        command.grupoId(),
        command.flagRestriccion(),
        command.flagConsulta(),
        command.flagOperacion(),
        command.flagAsignarRecursos(),
        command.flagEnviarBandeja(),
        command.flagEnviarEtapa(),
        command.flagAdjuntarArchivo()
    );

    // 5. El puerto recibe ApplicationCommand
//    RolGrupoAggregate agregadoGuardado = guardarRolGrupoPort.guardar(guardarCommand);

    // 6. Retornar DTO
//    return new RolGrupoInfoDto(
//        agregadoGuardado.getId().valor(),
//        agregadoGuardado.getRolId().valor(),
//        agregadoGuardado.getGrupoId().valor()
//    );

//    var dto = new CrearRolGrupoDto(
//        command.rolId(),
//        command.grupoId(),
//        command.flagRestriccion(),
//        command.flagConsulta(),
//        command.flagOperacion(),
//        command.flagAsignarRecursos(),
//        command.flagEnviarBandeja(),
//        command.flagEnviarEtapa(),
//        command.flagAdjuntarArchivo()
//    );
//    return guardarRolGrupoPort.guardar(guardarCommand);
    return guardarRolGrupoPort.guardar(command);
  }

  @Override
  public RolGrupoInfoDto actualizar(Long id, ActualizarRolGrupoCommand command) {
    // 1. Obtener aggregate existente
    var obtenerCommand = new ObtenerRolGrupoCommand(id);
//    RolGrupoAggregate rolGrupoExistente = obtenerRolGrupoPort.obtenerPorId(obtenerCommand);
    var dto = obtenerRolGrupoPort.obtenerPorId(obtenerCommand);

    // 2. Crear nuevo aggregate con cambios (lógica de dominio)
    RolGrupoAggregate rolGrupoActualizado = new RolGrupoAggregate(
//        rolGrupoExistente.getId(),
        RolGrupoId.de(dto.id()),
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

    // 3. Validaciones de dominio
    //validarRolGrupoService.validarActualizacion(rolGrupoActualizado, rolGrupoExistente);

    // 4. Validaciones del aggregate
    //rolGrupoActualizado.validarActualizacion();

    // 5. Transformar a ApplicationCommand para el puerto
    var actualizarCommand = new ActualizarRolGrupoCommand(
        id,
        command.rolId(),
        command.grupoId(),
        command.flagRestriccion(),
        command.flagConsulta(),
        command.flagOperacion(),
        command.flagAsignarRecursos(),
        command.flagEnviarBandeja(),
        command.flagEnviarEtapa(),
        command.flagAdjuntarArchivo()
    );

    // 6. El puerto recibe ApplicationCommand
//    RolGrupoAggregate agregadoActualizado = actualizarRolGrupoPort.actualizar(actualizarCommand);

//    // 7. Retornar DTO
//    return new RolGrupoInfoDto(
//        agregadoActualizado.getId().valor(),
//        agregadoActualizado.getRolId().valor(),
//        agregadoActualizado.getGrupoId().valor()
//    );

    return actualizarRolGrupoPort.actualizar(actualizarCommand);
  }

  @Override
  public void eliminar(EliminarRolGrupoCommand command) {
//    try {
    // 1. Obtener aggregate del dominio
    var obtenerCommand = new ObtenerRolGrupoCommand(command.id());
//    RolGrupoAggregate rolGrupoAggregate = obtenerRolGrupoPort.obtenerPorId(obtenerCommand);
    var dto = obtenerRolGrupoPort.obtenerPorId(obtenerCommand);

    var id = command.id();
//    RolGrupoAggregate rolGrupoAggregate = obtenerRolGrupoPort.obtenerPorId(id);

    // TODO: DTO DEBO CONVERTIRLO EN UN rolGrupoAggregate PARA LAS VALIDACIONES
    // 2. Validaciones de dominio
//    validarRolGrupoService.validarEliminacion(rolGrupoAggregate);

    // 3. Validaciones del aggregate
//    rolGrupoAggregate.validarEliminacion();

    // 4. Transformar a ApplicationCommand para el puerto
    var eliminarCommand = new EliminarRolGrupoCommand(id);

    // 4. Ejecutar la eliminación física
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

}