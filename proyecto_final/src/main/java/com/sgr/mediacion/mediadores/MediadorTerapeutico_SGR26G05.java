// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.mediacion.mediadores;

import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Mediador terapéutico especializado en sesiones de terapia familiar sistémica (RF-08).
 */
public class MediadorTerapeutico_SGR26G05 extends Mediador_SGR26G05 {

    private static final long serialVersionUID = 1L;
    private static final double BONIFICACION_TERAPIA_FAMILIAR = 0.05;

    /**
     * Construye un mediador terapéutico con tasa de éxito configurable.
     *
     * @param id        identificador único
     * @param nombre    nombre completo
     * @param tasaExito tasa base de éxito [0.0, 1.0]
     */
    public MediadorTerapeutico_SGR26G05(String id, String nombre, double tasaExito) {
        super(id, nombre, tasaExito);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected double calcularProbabilidadAjustada(ConflictoDomestico_SGR26G05 conflicto) {
        if (conflicto.getActoresInvolucrados().size() > 1) {
            return Math.min(1.0, getTasaExito() + BONIFICACION_TERAPIA_FAMILIAR);
        }
        return getTasaExito();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String obtenerTipoMediador() {
        return "Terapeutico";
    }
}
