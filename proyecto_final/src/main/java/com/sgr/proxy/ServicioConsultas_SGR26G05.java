// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.proxy;

import java.util.List;
import java.util.Map;

import com.sgr.actorFamiliar.ParticipanteSistema_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.nucleoFamiliar.NucleoFamiliar_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Interfaz de consultas protegida por el patrón Proxy inverso según el rol del participante (RD-2).
 */
public interface ServicioConsultas_SGR26G05 {

    /**
     * Consulta el ranking nacional de toxicidad por ICD.
     *
     * @param solicitante participante que realiza la consulta
     * @return lista ordenada descendentemente de suegras
     */
    List<Suegra_SGR26G05> consultarRanking(ParticipanteSistema_SGR26G05 solicitante);



    /**
     * Consulta el historial de conflictos de un núcleo familiar específico.
     *
     * @param solicitante participante que realiza la consulta
     * @param nucleo      núcleo familiar a consultar
     * @return lista inmutable de conflictos accesibles del núcleo
     */
    List<ConflictoDomestico_SGR26G05> consultarHistorialNucleo(
            ParticipanteSistema_SGR26G05 solicitante,
            NucleoFamiliar_SGR26G05 nucleo);



    /**
     * Consulta los conflictos asignados a la gestión del mediador solicitante (E1 Sección 4.5).
     *
     * @param solicitante mediador que realiza la consulta
     * @return lista inmutable de conflictos asignados al mediador
     */
    List<ConflictoDomestico_SGR26G05> consultarConflictosAsignados(ParticipanteSistema_SGR26G05 solicitante);



    /**
     * Consulta las estadísticas de éxito por tipo de mediador.
     *
     * @param solicitante participante que realiza la consulta
     * @return mapa con tasa de éxito por tipo de mediador
     */
    Map<String, Double> consultarEstadisticasMediacion(ParticipanteSistema_SGR26G05 solicitante);



    /**
     * Consulta los actores actualmente acogidos a fuga doméstica.
     *
     * @param solicitante participante que realiza la consulta
     * @return lista de descendientes en fuga doméstica
     */
    List<DescendientePolitico_SGR26G05> consultarActoresEnFuga(ParticipanteSistema_SGR26G05 solicitante);
}
