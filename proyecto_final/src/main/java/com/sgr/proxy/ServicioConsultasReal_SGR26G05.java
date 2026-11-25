// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.sgr.actorFamiliar.ParticipanteSistema_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.nucleoFamiliar.NucleoFamiliar_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.persistencia.RegistroNacional_SGR26G05;
import com.sgr.ranking.RankingToxicidad_SGR26G05;

/**
 * Implementación real del servicio de consultas sobre el {@link RegistroNacional_SGR26G05} (RD-2).
 */
public class ServicioConsultasReal_SGR26G05 implements ServicioConsultas_SGR26G05 {

    private final RegistroNacional_SGR26G05 registro;

    /**
     * Crea el servicio real respaldado por el registro nacional.
     *
     * @param registro registro nacional de datos
     */
    public ServicioConsultasReal_SGR26G05(RegistroNacional_SGR26G05 registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro nacional es obligatorio");
        }
        this.registro = registro;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public List<Suegra_SGR26G05> consultarRanking(ParticipanteSistema_SGR26G05 solicitante) {
        RankingToxicidad_SGR26G05 ranking = new RankingToxicidad_SGR26G05(registro.getSuegras());
        return ranking.getSuegrasOrdenadas();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public List<ConflictoDomestico_SGR26G05> consultarHistorialNucleo(
            ParticipanteSistema_SGR26G05 solicitante,
            NucleoFamiliar_SGR26G05 nucleo) {
        if (nucleo == null) {
            throw new IllegalArgumentException("El núcleo familiar no puede ser nulo");
        }
        return nucleo.getHistorialConflictos();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public List<ConflictoDomestico_SGR26G05> consultarConflictosAsignados(ParticipanteSistema_SGR26G05 solicitante) {
        if (solicitante == null) {
            return Collections.emptyList();
        }
        List<ConflictoDomestico_SGR26G05> asignados = new ArrayList<>();
        for (ConflictoDomestico_SGR26G05 conflicto : registro.getConflictos()) {
            if (conflicto.getMediadorAsignado() != null
                    && conflicto.getMediadorAsignado().getId().equals(solicitante.getId())) {
                asignados.add(conflicto);
            }
        }
        return Collections.unmodifiableList(asignados);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Double> consultarEstadisticasMediacion(ParticipanteSistema_SGR26G05 solicitante) {
        return registro.getSistemaMediacion().calcularTasaExitoPorTipo();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public List<DescendientePolitico_SGR26G05> consultarActoresEnFuga(ParticipanteSistema_SGR26G05 solicitante) {
        return registro.obtenerActoresEnFuga();
    }
}
