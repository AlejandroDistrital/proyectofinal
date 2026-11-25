// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.nucleoFamiliar.conflictos;

import java.util.ArrayList;

import java.time.LocalDate;

import com.sgr.excepciones.CategoriaInvalidaExcepcion_SGR26G05;

/**
 * Valida que la categoría de un conflicto pertenezca al catálogo oficial (RF-02, RD-3).
 */
public class ValidadorConflicto_SGR26G05 {

    private static final ArrayList<String> CATEGORIAS_OFICIALES = crearCatalogo();

    private ValidadorConflicto_SGR26G05() {
    }



    private static ArrayList<String> crearCatalogo() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("gastronomico");
        lista.add("territorial");
        lista.add("ceremonial");
        lista.add("financiero");
        lista.add("tecnologico");
        lista.add("vestimentario");
        lista.add("cria de nietos");
        return lista;
    }



    /**
     * Normaliza el texto de una categoría quitando tildes, guiones y mayúsculas.
     *
     * @param categoria categoría original
     * @return texto normalizado
     */
    public static String normalizarCategoria(String categoria) {
        if (categoria == null) {
            return "";
        }
        String limpio = categoria.trim().toLowerCase();
        limpio = limpio.replace("á", "a");
        limpio = limpio.replace("é", "e");
        limpio = limpio.replace("í", "i");
        limpio = limpio.replace("ó", "o");
        limpio = limpio.replace("ú", "u");
        limpio = limpio.replace("-", " ");
        limpio = limpio.replace("_", " ");
        return limpio;
    }



    /**
     * Verifica si una categoría pertenece al catálogo oficial.
     *
     * @param categoria categoría a revisar
     * @return true si es válida
     */
    public static boolean esCategoriaValida(String categoria) {
        String normalizada = normalizarCategoria(categoria);
        for (int i = 0; i < CATEGORIAS_OFICIALES.size(); i++) {
            if (CATEGORIAS_OFICIALES.get(i).equals(normalizada)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Valida la categoría y lanza {@link CategoriaInvalidaExcepcion_SGR26G05} si no es oficial.
     *
     * @param categoria    categoría del conflicto
     * @param fechaReporte fecha del reporte
     * @return categoría recortada si es válida
     * @throws CategoriaInvalidaExcepcion_SGR26G05 si la categoría no existe en el catálogo
     */
    public static String validarCategoria(String categoria, LocalDate fechaReporte)
            throws CategoriaInvalidaExcepcion_SGR26G05 {
        if (!esCategoriaValida(categoria)) {
            String fecha = (fechaReporte != null) ? fechaReporte.toString() : "sin fecha";
            throw new CategoriaInvalidaExcepcion_SGR26G05(categoria, fecha);
        }
        return categoria.trim();
    }
}
