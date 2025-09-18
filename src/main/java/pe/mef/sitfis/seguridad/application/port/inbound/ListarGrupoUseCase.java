package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.GrupoComboDto;

public interface ListarGrupoUseCase {

  List<GrupoComboDto> listarCombo();
}
