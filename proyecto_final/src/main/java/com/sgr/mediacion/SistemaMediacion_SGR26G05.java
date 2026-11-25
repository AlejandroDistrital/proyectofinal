// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.mediacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.io.Serializable;

import com.sgr.excepciones.TransicionInvalidaExcepcion_SGR26G05;
import com.sgr.mediacion.estrategia.EstrategiaAsignacionMediador_SGR26G05;
import com.sgr.mediacion.estrategia.EstrategiaMayorTasaExito_SGR26G05;
import com.sgr.mediacion.mediadores.Mediador_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Administra el pool de mediadores, el registro de resultados y la estrategia
 * intercambiable de asignación de mediadores (RF-08, RD-6).
 */
public class SistemaMediacion_SGR26G05 implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ArrayList<Mediador_SGR26G05> poolMediadores;
    private final ArrayList<ResultadoMediacion_SGR26G05> historialResultados;
    private EstrategiaAsignacionMediador_SGR26G05 estrategiaAsignacion;

    /**
     * Inicializa el sistema de mediación con una estrategia por defecto
     * ({@link EstrategiaMayorTasaExito_SGR26G05}).
     */
    public SistemaMediacion_SGR26G05() {
        this(new EstrategiaMayorTasaExito_SGR26G05());
    }



    /**
     * Inicializa el sistema de mediación con una estrategia específica.
     *
     * @param estrategiaAsignacion estrategia inicial de asignación
     */
    public SistemaMediacion_SGR26G05(EstrategiaAsignacionMediador_SGR26G05 estrategiaAsignacion) {
        this.poolMediadores = new ArrayList<>();
        this.historialResultados = new ArrayList<>();
        asignarEstrategia(estrategiaAsignacion);
    }



    private void asignarEstrategia(EstrategiaAsignacionMediador_SGR26G05 estrategiaAsignacion) {
        if (estrategiaAsignacion == null) {
            throw new IllegalArgumentException("La estrategia de asignación no puede ser nula");
        }
        this.estrategiaAsignacion = estrategiaAsignacion;
    }



    /**
     * Retorna una vista inmutable del pool de mediadores disponibles.
     *
     * @return lista inmutable de mediadores
     */
    public List<Mediador_SGR26G05> getPoolMediadores() {
        return Collections.unmodifiableList(poolMediadores);
    }



    /**
     * Agrega un mediador al pool si no está registrado previamente.
     *
     * @param mediador mediador a incorporar al pool
     */
    public void registrarMediador(Mediador_SGR26G05 mediador) {
        if (mediador == null) {
            throw new IllegalArgumentException("El mediador no puede ser nulo");
        }
        if (!poolMediadores.contains(mediador)) {
            poolMediadores.add(mediador);
        }
    }



    /**
     * Retorna la estrategia de asignación actualmente activa.
     *
     * @return estrategia activa
     */
    public EstrategiaAsignacionMediador_SGR26G05 getEstrategiaAsignacion() {
        return estrategiaAsignacion;
    }



    /**
     * Cambia el algoritmo de asignación de mediadores en tiempo de ejecución (RD-6).
     *
     * @param estrategiaAsignacion nueva estrategia de asignación
     */
    public void setEstrategiaAsignacion(EstrategiaAsignacionMediador_SGR26G05 estrategiaAsignacion) {
        asignarEstrategia(estrategiaAsignacion);
    }



    /**
     * Retorna una vista inmutable del historial de resultados de mediación registrados.
     *
     * @return lista inmutable de resultados
     */
    public List<ResultadoMediacion_SGR26G05> getHistorialResultados() {
        return Collections.unmodifiableList(historialResultados);
    }



    /**
     * Selecciona un mediador usando la estrategia activa, media el conflicto y registra
     * el resultado inmutable generado.
     *
     * @param conflicto conflicto doméstico a mediar
     * @return resultado de la mediación ejecutada
     * @throws TransicionInvalidaExcepcion_SGR26G05 si el estado del conflicto impide mediar
     */
    public ResultadoMediacion_SGR26G05 mediarConflicto(ConflictoDomestico_SGR26G05 conflicto)
            throws TransicionInvalidaExcepcion_SGR26G05 {
        if (poolMediadores.isEmpty()) {
            throw new IllegalStateException("No hay mediadores registrados en el pool");
        }
        Mediador_SGR26G05 seleccionado = estrategiaAsignacion.seleccionarMediador(poolMediadores, conflicto);
        ResultadoMediacion_SGR26G05 resultado = seleccionado.mediar(conflicto);
        historialResultados.add(resultado);
        return resultado;
    }



    /**
     * Registra un resultado histórico (por ejemplo cargado desde CSV) y actualiza los
     * contadores del mediador responsable.
     *
     * @param resultado resultado histórico de mediación
     */
    public void registrarResultadoHistorico(ResultadoMediacion_SGR26G05 resultado) {
        if (resultado == null) {
            throw new IllegalArgumentException("El resultado de mediación no puede ser nulo");
        }
        registrarMediador(resultado.getMediadorResponsable());
        historialResultados.add(resultado);
    }



    /**
     * Calcula la tasa de éxito observada por cada tipo de mediador que haya tenido
     * al menos una mediación registrada (RF-12c).
     *
     * @return mapa ordenado de tipo de mediador a tasa de éxito en [0.0, 1.0]
     */
    public Map<String, Double> calcularTasaExitoPorTipo() {
        Map<String, int[]> conteosPorTipo = new LinkedHashMap<>();
        for (ResultadoMediacion_SGR26G05 resultado : historialResultados) {
            String tipo = resultado.getMediadorResponsable().obtenerTipoMediador();
            int[] conteo = conteosPorTipo.computeIfAbsent(tipo, k -> new int[2]);
            conteo[0]++;
            if (resultado.esAcuerdo()) {
                conteo[1]++;
            }
        }
        Map<String, Double> tasas = new LinkedHashMap<>();
        for (Map.Entry<String, int[]> entrada : conteosPorTipo.entrySet()) {
            int total = entrada.getValue()[0];
            int exitos = entrada.getValue()[1];
            if (total > 0) {
                tasas.put(entrada.getKey(), (double) exitos / total);
            }
        }
        return tasas;
    }



    /**
     * Busca el primer mediador disponible cuyo tipo coincida con el indicado, o null si no existe.
     *
     * @param tipoMediador tipo de mediador (Diplomatico, Autoritario, Terapeutico)
     * @return mediador encontrado o null
     */
    public Mediador_SGR26G05 buscarPorTipo(String tipoMediador) {
        if (tipoMediador == null) {
            return null;
        }
        for (Mediador_SGR26G05 mediador : poolMediadores) {
            if (mediador.obtenerTipoMediador().equalsIgnoreCase(tipoMediador.trim())) {
                return mediador;
            }
        }
        return null;
    }
}
