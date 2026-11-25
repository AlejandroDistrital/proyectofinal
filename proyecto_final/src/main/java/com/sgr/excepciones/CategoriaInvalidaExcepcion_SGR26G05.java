// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.excepciones;

/**
 * Excepción lanzada cuando se intenta registrar o asignar una categoría de conflicto
 * que no pertenece al catálogo oficial de Suegristán (RF-02).
 */
public class CategoriaInvalidaExcepcion_SGR26G05 extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Construye la excepción con la categoría rechazada y la fecha del reporte.
     *
     * @param categoriaRechazada categoría no válida recibida
     * @param fechaReporte       fecha asociada al reporte del conflicto
     */
    public CategoriaInvalidaExcepcion_SGR26G05(String categoriaRechazada, String fechaReporte) {
        super("Categoría inválida '" + categoriaRechazada + "' en fecha de reporte: " + fechaReporte);
    }
}
