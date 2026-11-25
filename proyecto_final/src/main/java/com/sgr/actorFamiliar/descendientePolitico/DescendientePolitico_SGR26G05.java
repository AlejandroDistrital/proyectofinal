// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar.descendientePolitico;

import com.sgr.actorFamiliar.ActorFamiliar_SGR26G05;
import com.sgr.actorFamiliar.Parentesco_SGR26G05;

/**
 * Clase abstracta base para yernos y nueras que gestiona la resiliencia y el estado
 * constitucional de fuga doméstica (RF-10).
 */
public abstract class DescendientePolitico_SGR26G05 extends ActorFamiliar_SGR26G05 {

    private static final long serialVersionUID = 1L;
    private static final int RESILIENCIA_MINIMA = 0;
    private static final int RESILIENCIA_MAXIMA = 100;
    private static final int DESCUENTO_CONFLICTO_ACTIVO = 5;
    private static final int RECUPERACION_CONFLICTO_RESUELTO = 10;
    private static final int UMBRAL_FUGA_DOMESTICA = 0;
    private static final int RECUPERACION_MINIMA_SALIR_FUGA = 20;

    private int nivelResiliencia;
    private boolean fugaDomestica;

    /**
     * Construye un descendiente político validando su resiliencia inicial (0 a 100).
     *
     * @param id               identificador único
     * @param nombre           nombre completo
     * @param edad             edad en años
     * @param parentesco       parentesco (NUERA o YERNO)
     * @param aniosConvivencia años de convivencia
     * @param paciencia        nivel de paciencia inicial (0 a 100)
     * @param nivelResiliencia nivel de resiliencia inicial (0 a 100)
     */
    protected DescendientePolitico_SGR26G05(
            String id,
            String nombre,
            int edad,
            Parentesco_SGR26G05 parentesco,
            int aniosConvivencia,
            int paciencia,
            int nivelResiliencia) {
        super(id, nombre, edad, parentesco, aniosConvivencia, paciencia);
        asignarNivelResiliencia(nivelResiliencia);
    }



    private void asignarNivelResiliencia(int nivelResiliencia) {
        if (nivelResiliencia < RESILIENCIA_MINIMA || nivelResiliencia > RESILIENCIA_MAXIMA) {
            throw new IllegalArgumentException(
                    "El nivel de resiliencia debe estar entre 0 y 100: " + nivelResiliencia);
        }
        this.nivelResiliencia = nivelResiliencia;
        if (this.nivelResiliencia <= UMBRAL_FUGA_DOMESTICA) {
            this.fugaDomestica = true;
        }
    }



    /**
     * Retorna el nivel actual de resiliencia (0 a 100).
     *
     * @return nivel de resiliencia
     */
    public int getNivelResiliencia() {
        return nivelResiliencia;
    }



    /**
     * Asigna el nivel de resiliencia validando el rango [0, 100] y actualizando
     * el estado de fuga doméstica si alcanza el umbral 0.
     *
     * @param nivelResiliencia nuevo nivel de resiliencia
     */
    public void setNivelResiliencia(int nivelResiliencia) {
        asignarNivelResiliencia(nivelResiliencia);
    }



    /**
     * Indica si el actor se encuentra actualmente en estado de fuga doméstica.
     *
     * @return true si está en fuga doméstica
     */
    public boolean isFugaDomestica() {
        return fugaDomestica;
    }



    /**
     * Descuenta 5 unidades de resiliencia por la activación de un conflicto nuevo.
     * Si la resiliencia llega a 0, activa la fuga doméstica.
     */
    public void registrarConflictoActivo() {
        if (fugaDomestica) {
            throw new IllegalStateException(
                    "El actor " + getNombre() + " está en fuga doméstica y no participa en conflictos nuevos");
        }
        this.nivelResiliencia = Math.max(RESILIENCIA_MINIMA, this.nivelResiliencia - DESCUENTO_CONFLICTO_ACTIVO);
        if (this.nivelResiliencia <= UMBRAL_FUGA_DOMESTICA) {
            this.fugaDomestica = true;
        }
    }



    /**
     * Recupera 10 unidades de resiliencia al resolverse un conflicto sin superar 100.
     * Levanta la fuga doméstica cuando la resiliencia alcanza al menos 20 unidades.
     */
    public void registrarConflictoResuelto() {
        this.nivelResiliencia = Math.min(RESILIENCIA_MAXIMA, this.nivelResiliencia + RECUPERACION_CONFLICTO_RESUELTO);
        if (this.fugaDomestica && this.nivelResiliencia >= RECUPERACION_MINIMA_SALIR_FUGA) {
            this.fugaDomestica = false;
        }
    }



    /**
     * Verifica si el actor está habilitado para participar en nuevos conflictos.
     *
     * @return true si no está en fuga doméstica
     */
    public boolean puedeParticiparEnConflictoNuevo() {
        return !fugaDomestica;
    }
}
