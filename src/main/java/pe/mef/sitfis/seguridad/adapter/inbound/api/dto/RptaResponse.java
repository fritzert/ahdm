package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.List;

public record RptaResponse(List<?> data,
                           int currentPage,
                           Long totalItems,
                           int totalPages) {

}