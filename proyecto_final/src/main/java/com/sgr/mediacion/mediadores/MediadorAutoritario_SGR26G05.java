// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.mediacion.mediadores;

import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Mediador autoritario especializado en imposición de acuerdos vinculantes (RF-08).
 */
public class MediadorAutoritario_SGR26G05 extends Mediador_SGR26G05 {

    private static final long serialVersionUID = 1L;
    private static final double BONIFICACION_ALTA_INTENSIDAD = 0.05;
    private static final int UMBRAL_ALTA_INTENSIDAD = 7;

    /**
     * Construye un mediador autoritario con tasa de éxito configurable.
     *
     * @param id        identificador único
     * @param nombre    nombre completo
     * @param tasaExito tasa base de éxito [0.0, 1.0]
     */
    public MediadorAutoritario_SGR26G05(String id, String nombre, double tasaExito) {
        super(id, nombre, tasaExito);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected double calcularProbabilidadAjustada(ConflictoDomestico_SGR26G05 conflicto) {
        if (conflicto.getIntensidad() >= UMBRAL_ALTA_INTENSIDAD) {
            return Math.min(1.0, getTasaExito() + BONIFICACION_ALTA_INTENSIDAD);
        }
        return getTasaExito();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String obtenerTipoMediador() {
        return "Autoritario";
    }
}
