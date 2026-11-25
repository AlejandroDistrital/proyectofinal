// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.sgr.actorFamiliar.ActorFamiliar_SGR26G05;
import com.sgr.actorFamiliar.ParticipanteSistema_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.excepciones.AccesoDenegadoExcepcion_SGR26G05;
import com.sgr.nucleoFamiliar.NucleoFamiliar_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Proxy inverso del Ministerio de Armonía Familiar que verifica permisos de acceso
 * según el rol del participante (SUEGRA, NUERA, YERNO o MEDIADOR) conforme a RD-2
 * y la matriz definida en la Entrega 1.
 */
public class ProxyAccesoMAF_SGR26G05 implements ServicioConsultas_SGR26G05 {

    private static final String ROL_MEDIADOR = "MEDIADOR";
    private static final String ROL_SUEGRA = "SUEGRA";
    private static final String ROL_NUERA = "NUERA";
    private static final String ROL_YERNO = "YERNO";

    private final ServicioConsultas_SGR26G05 servicioReal;

    /**
     * Construye el proxy inverso envolviendo el servicio real de consultas.
     *
     * @param servicioReal implementación real del servicio de consultas
     */
    public ProxyAccesoMAF_SGR26G05(ServicioConsultas_SGR26G05 servicioReal) {
        if (servicioReal == null) {
            throw new IllegalArgumentException("El servicio real es obligatorio");
        }
        this.servicioReal = servicioReal;
    }



    /**
     * {@inheritDoc}
     * Solo mediadores y suegras tienen permiso para consultar el ranking nacional.
     */
    @Override
    public List<Suegra_SGR26G05> consultarRanking(ParticipanteSistema_SGR26G05 solicitante) {
        String rol = validarSolicitante(solicitante, "Ranking nacional de toxicidad");
        if (!ROL_MEDIADOR.equals(rol) && !ROL_SUEGRA.equals(rol)) {
            throw new AccesoDenegadoExcepcion_SGR26G05(rol, "Ranking nacional de toxicidad");
        }
        return servicioReal.consultarRanking(solicitante);
    }



    /**
     * {@inheritDoc}
     * Suegras, nueras y yernos solo consultan su propio núcleo familiar; los mediadores
     * acceden únicamente a los conflictos del núcleo que tengan asignados a su gestión.
     */
    @Override
    public List<ConflictoDomestico_SGR26G05> consultarHistorialNucleo(
            ParticipanteSistema_SGR26G05 solicitante,
            NucleoFamiliar_SGR26G05 nucleo) {
        String rol = validarSolicitante(solicitante, "Historial de núcleo familiar");
        if (nucleo == null) {
            throw new IllegalArgumentException("El núcleo familiar no puede ser nulo");
        }
        if (ROL_MEDIADOR.equals(rol)) {
            List<ConflictoDomestico_SGR26G05> delNucleo =
                    servicioReal.consultarHistorialNucleo(solicitante, nucleo);
            List<ConflictoDomestico_SGR26G05> asignadosEnNucleo = new ArrayList<>();
            for (ConflictoDomestico_SGR26G05 conflicto : delNucleo) {
                if (conflicto.getMediadorAsignado() != null
                        && conflicto.getMediadorAsignado().getId().equals(solicitante.getId())) {
                    asignadosEnNucleo.add(conflicto);
                }
            }
            if (asignadosEnNucleo.isEmpty()) {
                throw new AccesoDenegadoExcepcion_SGR26G05(
                        rol, "Núcleo " + nucleo.getIdNucleo() + " sin conflictos asignados al mediador");
            }
            return Collections.unmodifiableList(asignadosEnNucleo);
        }
        if (solicitante instanceof ActorFamiliar_SGR26G05 actor
                && nucleo.getIdNucleo().equals(actor.getIdNucleoAsignado())) {
            return servicioReal.consultarHistorialNucleo(solicitante, nucleo);
        }
        throw new AccesoDenegadoExcepcion_SGR26G05(rol, "Historial del núcleo " + nucleo.getIdNucleo());
    }



    /**
     * {@inheritDoc}
     * Solo un mediador puede consultar la lista de conflictos asignados a su gestión.
     */
    @Override
    public List<ConflictoDomestico_SGR26G05> consultarConflictosAsignados(ParticipanteSistema_SGR26G05 solicitante) {
        String rol = validarSolicitante(solicitante, "Conflictos asignados a gestión");
        if (!ROL_MEDIADOR.equals(rol)) {
            throw new AccesoDenegadoExcepcion_SGR26G05(rol, "Conflictos asignados a gestión");
        }
        return servicioReal.consultarConflictosAsignados(solicitante);
    }



    /**
     * {@inheritDoc}
     * Solo el rol MEDIADOR tiene acceso a las estadísticas internas de mediación.
     */
    @Override
    public Map<String, Double> consultarEstadisticasMediacion(ParticipanteSistema_SGR26G05 solicitante) {
        String rol = validarSolicitante(solicitante, "Estadísticas de mediación");
        if (!ROL_MEDIADOR.equals(rol)) {
            throw new AccesoDenegadoExcepcion_SGR26G05(rol, "Estadísticas de mediación");
        }
        return servicioReal.consultarEstadisticasMediacion(solicitante);
    }



    /**
     * {@inheritDoc}
     * Mediadores, nueras y yernos pueden consultar el estado de fuga doméstica;
     * las suegras tienen restringido el acceso al censo de actores en fuga doméstica.
     */
    @Override
    public List<DescendientePolitico_SGR26G05> consultarActoresEnFuga(ParticipanteSistema_SGR26G05 solicitante) {
        String rol = validarSolicitante(solicitante, "Listado de fuga doméstica");
        if (!ROL_MEDIADOR.equals(rol) && !ROL_NUERA.equals(rol) && !ROL_YERNO.equals(rol)) {
            throw new AccesoDenegadoExcepcion_SGR26G05(rol, "Listado de fuga doméstica");
        }
        return servicioReal.consultarActoresEnFuga(solicitante);
    }



    private String validarSolicitante(ParticipanteSistema_SGR26G05 solicitante, String recurso) {
        if (solicitante == null) {
            throw new AccesoDenegadoExcepcion_SGR26G05("ANONIMO", recurso);
        }
        String rol = solicitante.obtenerRolConsulta();
        if (rol == null || rol.isBlank()) {
            throw new AccesoDenegadoExcepcion_SGR26G05("DESCONOCIDO", recurso);
        }
        return rol.trim().toUpperCase();
    }
}
