// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar.suegra.tipo;

import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;

/**
 * Suegra clásica: opera de forma presencial mediante tácticas de cocina y
 * comentarios pasivo-agresivos (RF-03).
 */
public class SuegraClasica_SGR26G05 extends Suegra_SGR26G05 {

    private static final long serialVersionUID = 1L;

    /**
     * Construye una suegra clásica con sus atributos completos.
     *
     * @param id               identificador único
     * @param nombre           nombre completo
     * @param edad             edad en años
     * @param aniosConvivencia años de convivencia
     * @param paciencia        nivel de paciencia inicial (0 a 100)
     */
    public SuegraClasica_SGR26G05(
            String id,
            String nombre,
            int edad,
            int aniosConvivencia,
            int paciencia) {
        super(id, nombre, edad, aniosConvivencia, paciencia);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String obtenerTipoSuegra() {
        return "Clasica";
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String ejecutarTacticaDomestica() {
        return getNombre() + " inspecciona la cocina presencialmente y emite un comentario pasivo-agresivo.";
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String describirRol() {
        return "Suegra Clásica (intervención presencial y culinaria)";
    }
}
