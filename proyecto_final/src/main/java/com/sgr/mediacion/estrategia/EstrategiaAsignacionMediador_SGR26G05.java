// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.mediacion.estrategia;

import java.util.List;

import java.io.Serializable;

import com.sgr.mediacion.mediadores.Mediador_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Interfaz Strategy para la selección dinámica del mediador adecuado ante un conflicto (RD-6).
 */
public interface EstrategiaAsignacionMediador_SGR26G05 extends Serializable {

    /**
     * Selecciona un mediador del pool disponible para atender el conflicto indicado.
     *
     * @param pool      lista de mediadores disponibles
     * @param conflicto conflicto doméstico que requiere mediación
     * @return mediador seleccionado
     */
    Mediador_SGR26G05 seleccionarMediador(
            List<Mediador_SGR26G05> pool,
            ConflictoDomestico_SGR26G05 conflicto);



    /**
     * Retorna el nombre descriptivo de la estrategia de asignación.
     *
     * @return nombre de la estrategia
     */
    String obtenerNombreEstrategia();
}
