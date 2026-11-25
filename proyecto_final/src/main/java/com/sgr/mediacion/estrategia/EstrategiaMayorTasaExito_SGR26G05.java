// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.mediacion.estrategia;

import java.util.List;

import com.sgr.mediacion.mediadores.Mediador_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Estrategia que asigna el mediador con mayor tasa de éxito configurada (RD-6).
 */
public class EstrategiaMayorTasaExito_SGR26G05 implements EstrategiaAsignacionMediador_SGR26G05 {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Mediador_SGR26G05 seleccionarMediador(
            List<Mediador_SGR26G05> pool,
            ConflictoDomestico_SGR26G05 conflicto) {
        if (pool == null || pool.isEmpty()) {
            throw new IllegalStateException("El pool de mediadores está vacío");
        }
        Mediador_SGR26G05 mejor = pool.get(0);
        for (Mediador_SGR26G05 candidato : pool) {
            if (candidato.getTasaExito() > mejor.getTasaExito()) {
                mejor = candidato;
            }
        }
        return mejor;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String obtenerNombreEstrategia() {
        return "Mayor Tasa de Éxito";
    }
}
