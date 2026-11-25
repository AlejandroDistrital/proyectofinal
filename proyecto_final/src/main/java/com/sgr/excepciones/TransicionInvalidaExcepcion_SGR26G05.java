// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.excepciones;

import com.sgr.nucleoFamiliar.conflictos.EstadoConflicto_SGR26G05;

/**
 * Excepción lanzada cuando se intenta ejecutar una transición de estado no permitida
 * en la máquina de estados de un conflicto doméstico (RF-07).
 */
public class TransicionInvalidaExcepcion_SGR26G05 extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Construye la excepción indicando el conflicto y los estados origen y destino.
     *
     * @param idConflicto   identificador del conflicto
     * @param estadoOrigen  estado actual del conflicto
     * @param estadoDestino estado destino rechazado
     */
    public TransicionInvalidaExcepcion_SGR26G05(
            String idConflicto,
            EstadoConflicto_SGR26G05 estadoOrigen,
            EstadoConflicto_SGR26G05 estadoDestino) {
        super("Transición inválida en conflicto " + idConflicto + ": de " + estadoOrigen + " a " + estadoDestino);
    }
}
