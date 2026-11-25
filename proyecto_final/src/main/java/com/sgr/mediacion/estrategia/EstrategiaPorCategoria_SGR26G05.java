// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.mediacion.estrategia;

import java.util.List;

import com.sgr.mediacion.mediadores.MediadorAutoritario_SGR26G05;
import com.sgr.mediacion.mediadores.MediadorDiplomatico_SGR26G05;
import com.sgr.mediacion.mediadores.MediadorTerapeutico_SGR26G05;
import com.sgr.mediacion.mediadores.Mediador_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ValidadorConflicto_SGR26G05;

/**
 * Estrategia que elige el mediador según la categoría del conflicto (RD-6).
 */
public class EstrategiaPorCategoria_SGR26G05 implements EstrategiaAsignacionMediador_SGR26G05 {

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
        String categoria = "";
        if (conflicto != null) {
            categoria = ValidadorConflicto_SGR26G05.normalizarCategoria(conflicto.getCategoria());
        }
        for (int i = 0; i < pool.size(); i++) {
            Mediador_SGR26G05 mediador = pool.get(i);
            if (esAdecuadoParaCategoria(mediador, categoria)) {
                return mediador;
            }
        }
        return pool.get(0);
    }



    private boolean esAdecuadoParaCategoria(Mediador_SGR26G05 mediador, String categoria) {
        if ("financiero".equals(categoria) || "territorial".equals(categoria)) {
            return mediador instanceof MediadorAutoritario_SGR26G05;
        }
        if ("cria de nietos".equals(categoria) || "gastronomico".equals(categoria)) {
            return mediador instanceof MediadorTerapeutico_SGR26G05;
        }
        return mediador instanceof MediadorDiplomatico_SGR26G05;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String obtenerNombreEstrategia() {
        return "Afinidad por Categoría";
    }
}
