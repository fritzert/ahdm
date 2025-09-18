package pe.mef.sitfis.seguridad.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.mef.sitfis.seguridad.application.mapper.MenuDomainMapper;
import pe.mef.sitfis.seguridad.application.mapper.SubMenuDomainMapper;
import pe.mef.sitfis.seguridad.application.mapper.TabDomainMapper;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRestriccionesPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarGrupoPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarRolGrupoPorGrupoIdPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarRolPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarTabPorMenuSubmenuPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarTabPorParametrosPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarListaRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarListaRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarGrupoPaginadoReportePort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarMenuPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarRolPaginadoReportePort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarSubmenuPorMenuIdPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaPorMenuGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaRolGrupoPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaRolGrupoReportePort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerRolPort;
import pe.mef.sitfis.seguridad.application.service.GrupoService;
import pe.mef.sitfis.seguridad.application.service.ListaRolGrupoService;
import pe.mef.sitfis.seguridad.application.service.MenuService;
import pe.mef.sitfis.seguridad.application.service.RolGrupoService;
import pe.mef.sitfis.seguridad.application.service.RolService;
import pe.mef.sitfis.seguridad.application.service.SubMenuService;
import pe.mef.sitfis.seguridad.application.service.TabService;

@Configuration
public class SpringConfig {

  @Bean
  public MenuService menuService(ListarMenuPort listarMenuPort) {
    return new MenuService(listarMenuPort, MenuDomainMapper.INSTANCE);
  }

  @Bean
  public SubMenuService subMenuService(ListarSubmenuPorMenuIdPort listarSubmenuPorMenuIdPort) {
    return new SubMenuService(listarSubmenuPorMenuIdPort, SubMenuDomainMapper.INSTANCE);
  }

  @Bean
  public TabService tabService(
      BuscarTabPorMenuSubmenuPort buscarTabPorMenuSubmenuPort,
      BuscarTabPorParametrosPaginadoPort paginadoPort) {
    return new TabService(buscarTabPorMenuSubmenuPort, paginadoPort, TabDomainMapper.INSTANCE);
  }

  @Bean
  public RolGrupoService rolGrupoService(
      ObtenerRolGrupoPort obtenerRolGrupoPort,
      BuscarRolGrupoPorGrupoIdPort buscarRolGrupoPorGrupoIdPort,
      GuardarRolGrupoPort guardarRolGrupoPort,
      ActualizarRolGrupoPort actualizarRolGrupoPort,
      EliminarRolGrupoPort eliminarRolGrupoPort) {
    return new RolGrupoService(obtenerRolGrupoPort, buscarRolGrupoPorGrupoIdPort,
        guardarRolGrupoPort,
        actualizarRolGrupoPort, eliminarRolGrupoPort);
  }

  @Bean
  public RolService rolService(
      ListarRolPort listarRolPort,
      ListarRolPaginadoReportePort paginadoPort,
      BuscarRolPaginadoPort buscarRolPaginadoPort,
      ObtenerRolPort obtenerRolPort,
      GuardarRolPort guardarRolPort,
      ActualizarRolPort actualizarRolPort,
      EliminarRolPort eliminarRolPort, ListarRestriccionesPort listarRestriccionesPort) {
    return new RolService(listarRolPort, paginadoPort, buscarRolPaginadoPort, obtenerRolPort,
        guardarRolPort, actualizarRolPort, eliminarRolPort, listarRestriccionesPort);
  }

  @Bean
  public GrupoService grupoService(
      ListarGrupoPort listarGrupoPort,
      ListarGrupoPaginadoReportePort listarGrupoPaginadoReportePort,
      BuscarGrupoPaginadoPort buscarGrupoPaginadoPort,
      ObtenerGrupoPort obtenerGrupoPort,
      GuardarGrupoPort guardarGrupoPort,
      ActualizarGrupoPort actualizarGrupoPort,
      EliminarGrupoPort eliminarGrupoPort) {
    return new GrupoService(listarGrupoPort, listarGrupoPaginadoReportePort,
        buscarGrupoPaginadoPort,
        obtenerGrupoPort, guardarGrupoPort, actualizarGrupoPort, eliminarGrupoPort);
  }

  @Bean
  public ListaRolGrupoService listaRolGrupoService(
      ObtenerListaRolGrupoReportePort reportePort,
      ObtenerListaRolGrupoPaginadoPort paginadoPort,
      ObtenerListaPorMenuGrupoPort menuGrupoPort,
      GuardarListaRolGrupoPort guardarListaRolGrupoPort,
      EliminarListaRolGrupoPort eliminarListaRolGrupoPort) {
    return new ListaRolGrupoService(reportePort, paginadoPort,
        menuGrupoPort, guardarListaRolGrupoPort, eliminarListaRolGrupoPort);
  }

}
