// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.excepciones;

/**
 * Excepción lanzada por el proxy inverso cuando un participante intenta consultar
 * información para la cual su rol no tiene autorización (RD-2).
 */
public class AccesoDenegadoExcepcion_SGR26G05 extends SecurityException {

    private static final long serialVersionUID = 1L;

    /**
     * Construye la excepción indicando el rol del solicitante y el recurso denegado.
     *
     * @param rolSolicitante    rol del participante que intentó la consulta
     * @param recursoSolicitado operación o recurso solicitado
     */
    public AccesoDenegadoExcepcion_SGR26G05(String rolSolicitante, String recursoSolicitado) {
        super("Acceso denegado para el rol '" + rolSolicitante + "' al recurso: " + recursoSolicitado);
    }
}
