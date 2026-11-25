// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar.suegra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sgr.actorFamiliar.ActorFamiliar_SGR26G05;
import com.sgr.actorFamiliar.Parentesco_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.EstadoConflicto_SGR26G05;

/**
 * Clase base abstracta para todas las suegras registradas en Suegristán (RF-03, RF-09).
 */
public abstract class Suegra_SGR26G05 extends ActorFamiliar_SGR26G05 implements Comparable<Suegra_SGR26G05> {

    private static final long serialVersionUID = 1L;

    private final ArrayList<ConflictoDomestico_SGR26G05> conflictos;

    /**
     * Construye una suegra validando sus datos familiares y paciencia inicial.
     *
     * @param id               identificador único
     * @param nombre           nombre completo
     * @param edad             edad en años
     * @param aniosConvivencia años de convivencia
     * @param paciencia        nivel de paciencia inicial (0 a 100)
     */
    protected Suegra_SGR26G05(
            String id,
            String nombre,
            int edad,
            int aniosConvivencia,
            int paciencia) {
        super(id, nombre, edad, Parentesco_SGR26G05.SUEGRA, aniosConvivencia, paciencia);
        this.conflictos = new ArrayList<>();
    }



    /**
     * Retorna una vista inmutable de los conflictos asociados a esta suegra.
     *
     * @return lista inmutable de conflictos
     */
    public List<ConflictoDomestico_SGR26G05> getConflictos() {
        return Collections.unmodifiableList(conflictos);
    }



    /**
     * Asocia un conflicto doméstico a esta suegra para el cálculo de su ICD.
     *
     * @param conflicto conflicto doméstico a registrar
     */
    public void registrarConflicto(ConflictoDomestico_SGR26G05 conflicto) {
        if (conflicto == null) {
            throw new IllegalArgumentException("El conflicto no puede ser nulo");
        }
        if (!conflictos.contains(conflicto)) {
            conflictos.add(conflicto);
        }
    }



    /**
     * Calcula el promedio base de intensidades de los conflictos en estado activo o crónico.
     *
     * @return promedio de intensidades o 0.0 si no tiene conflictos activos ni crónicos
     */
    protected final double calcularPromedioActivosYCronicos() {
        int sumaIntensidades = 0;
        int cantidadRelevantes = 0;
        for (ConflictoDomestico_SGR26G05 conflicto : conflictos) {
            EstadoConflicto_SGR26G05 estado = conflicto.getEstado();
            if (estado == EstadoConflicto_SGR26G05.activo || estado == EstadoConflicto_SGR26G05.cronico) {
                sumaIntensidades += conflicto.getIntensidad();
                cantidadRelevantes++;
            }
        }
        if (cantidadRelevantes == 0) {
            return 0.0;
        }
        return (double) sumaIntensidades / cantidadRelevantes;
    }



    /**
     * Calcula el Índice de Conflictividad Doméstica (ICD) promediando la intensidad
     * de sus conflictos en estado activo o crónico (RF-09).
     *
     * @return valor del ICD (0.0 si no tiene conflictos activos ni crónicos)
     */
    public double calcularICD() {
        return calcularPromedioActivosYCronicos();
    }



    /**
     * Retorna la cantidad total de conflictos registrados para esta suegra.
     *
     * @return número total de conflictos
     */
    public int getCantidadTotalConflictos() {
        return conflictos.size();
    }



    /**
     * Compara dos suegras para ordenarlas de forma descendente según su ICD (RF-06).
     *
     * @param otra otra suegra a comparar
     * @return entero negativo si esta suegra tiene mayor ICD que la otra
     */
    @Override
    public int compareTo(Suegra_SGR26G05 otra) {
        if (otra == null) {
            return -1;
        }
        return Double.compare(otra.calcularICD(), this.calcularICD());
    }



    /**
     * Retorna el subtipo oficial de suegra.
     *
     * @return nombre del subtipo
     */
    public abstract String obtenerTipoSuegra();



    /**
     * Ejecuta la táctica doméstica característica de este subtipo de suegra (RF-03).
     *
     * @return descripción de la táctica ejecutada
     */
    public abstract String ejecutarTacticaDomestica();
}
