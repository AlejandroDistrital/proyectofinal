// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.nucleoFamiliar.conflictos;

/**
 * Estados posibles en el ciclo de vida de un conflicto doméstico (RF-07).
 */
public enum EstadoConflicto_SGR26G05 {
    latente,
    activo,
    en_mediacion,
    resuelto,
    cronico;



    /**
     * Convierte un texto del CSV o de usuario al estado correspondiente del conflicto.
     *
     * @param texto representación textual del estado
     * @return constante de {@link EstadoConflicto_SGR26G05}
     */
    public static EstadoConflicto_SGR26G05 desdeTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El estado del conflicto no puede estar vacío");
        }
        String normalizado = texto.trim()
                .toLowerCase()
                .replace("ó", "o")
                .replace(" ", "_");
        return EstadoConflicto_SGR26G05.valueOf(normalizado);
    }
}
