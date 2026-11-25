// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.mediacion;

import java.io.Serializable;

import java.time.LocalDate;

import com.sgr.mediacion.mediadores.Mediador_SGR26G05;

/**
 * Resultado inmutable de una sesión de mediación doméstica (RD-1, RNF-2).
 * Todos sus atributos son {@code final} y carece de setters; cualquier rectificación
 * administrativa genera una nueva instancia sin alterar la original.
 */
public final class ResultadoMediacion_SGR26G05 implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String VEREDICTO_ACUERDO = "acuerdo";
    private static final String VEREDICTO_FRACASO = "fracaso";

    private final String id;
    private final String veredicto;
    private final LocalDate fecha;
    private final Mediador_SGR26G05 mediadorResponsable;
    private final String idConflicto;

    /**
     * Crea un resultado de mediación inmutable validando todos sus campos.
     *
     * @param id                  identificador único del resultado
     * @param veredicto           veredicto emitido (por ejemplo "acuerdo" o "fracaso")
     * @param fecha               fecha del veredicto
     * @param mediadorResponsable mediador que condujo la mediación
     * @param idConflicto         identificador del conflicto mediado
     */
    public ResultadoMediacion_SGR26G05(
            String id,
            String veredicto,
            LocalDate fecha,
            Mediador_SGR26G05 mediadorResponsable,
            String idConflicto) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El identificador del resultado no puede estar vacío");
        }
        if (veredicto == null || veredicto.isBlank()) {
            throw new IllegalArgumentException("El veredicto no puede estar vacío");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha del resultado es obligatoria");
        }
        if (mediadorResponsable == null) {
            throw new IllegalArgumentException("El mediador responsable es obligatorio");
        }
        if (idConflicto == null || idConflicto.isBlank()) {
            throw new IllegalArgumentException("El identificador del conflicto es obligatorio");
        }
        this.id = id.trim();
        this.veredicto = veredicto.trim().toLowerCase();
        this.fecha = fecha;
        this.mediadorResponsable = mediadorResponsable;
        this.idConflicto = idConflicto.trim();
    }



    /**
     * Retorna el identificador único del resultado de mediación.
     *
     * @return identificador del resultado
     */
    public String getId() {
        return id;
    }



    /**
     * Retorna el veredicto registrado para la mediación.
     *
     * @return veredicto de la mediación
     */
    public String getVeredicto() {
        return veredicto;
    }



    /**
     * Retorna la fecha en que se emitió el resultado.
     *
     * @return fecha del resultado
     */
    public LocalDate getFecha() {
        return fecha;
    }



    /**
     * Retorna el mediador responsable de la sesión de mediación.
     *
     * @return mediador responsable
     */
    public Mediador_SGR26G05 getMediadorResponsable() {
        return mediadorResponsable;
    }



    /**
     * Retorna el identificador del conflicto asociado a este resultado.
     *
     * @return identificador del conflicto
     */
    public String getIdConflicto() {
        return idConflicto;
    }



    /**
     * Indica si el resultado corresponde a un acuerdo exitoso.
     *
     * @return true si el veredicto es "acuerdo"
     */
    public boolean esAcuerdo() {
        return VEREDICTO_ACUERDO.equalsIgnoreCase(veredicto);
    }



    /**
     * Retorna la constante textual oficial para veredicto de acuerdo.
     *
     * @return texto "acuerdo"
     */
    public static String obtenerVeredictoAcuerdo() {
        return VEREDICTO_ACUERDO;
    }



    /**
     * Retorna la constante textual oficial para veredicto de fracaso.
     *
     * @return texto "fracaso"
     */
    public static String obtenerVeredictoFracaso() {
        return VEREDICTO_FRACASO;
    }



    /**
     * Rectifica administrativamente el veredicto retornando una nueva instancia inmutable
     * sin modificar el objeto actual (RNF-2, RD-1).
     *
     * @param nuevoVeredicto nuevo veredicto corregido
     * @return nueva instancia de {@link ResultadoMediacion_SGR26G05} con el veredicto actualizado
     */
    public ResultadoMediacion_SGR26G05 rectificarVeredicto(String nuevoVeredicto) {
        return new ResultadoMediacion_SGR26G05(
                this.id,
                nuevoVeredicto,
                this.fecha,
                this.mediadorResponsable,
                this.idConflicto);
    }
}
