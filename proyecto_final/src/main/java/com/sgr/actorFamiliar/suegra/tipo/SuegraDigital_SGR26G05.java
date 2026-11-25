// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar.suegra.tipo;

import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;

/**
 * Suegra digital: ejerce presión mediante mensajería instantánea y redes sociales (RF-03).
 */
public class SuegraDigital_SGR26G05 extends Suegra_SGR26G05 {

    private static final long serialVersionUID = 1L;

    /**
     * Construye una suegra digital con sus atributos completos.
     *
     * @param id               identificador único
     * @param nombre           nombre completo
     * @param edad             edad en años
     * @param aniosConvivencia años de convivencia
     * @param paciencia        nivel de paciencia inicial (0 a 100)
     */
    public SuegraDigital_SGR26G05(
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
        return "Digital";
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String ejecutarTacticaDomestica() {
        return getNombre() + " envía cadenas nocturnas al grupo familiar y deja indirectas en estados.";
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String describirRol() {
        return "Suegra Digital (mensajería y redes sociales)";
    }
}
