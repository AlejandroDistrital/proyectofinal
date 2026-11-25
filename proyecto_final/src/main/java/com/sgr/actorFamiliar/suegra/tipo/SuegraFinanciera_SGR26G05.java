// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar.suegra.tipo;

import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;

/**
 * Suegra financiera: ejerce control mediante préstamos familiares y herencias
 * condicionadas (RF-03).
 */
public class SuegraFinanciera_SGR26G05 extends Suegra_SGR26G05 {

    private static final long serialVersionUID = 1L;

    /**
     * Construye una suegra financiera con sus atributos completos.
     *
     * @param id               identificador único
     * @param nombre           nombre completo
     * @param edad             edad en años
     * @param aniosConvivencia años de convivencia
     * @param paciencia        nivel de paciencia inicial (0 a 100)
     */
    public SuegraFinanciera_SGR26G05(
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
        return "Financiera";
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String ejecutarTacticaDomestica() {
        return getNombre() + " revisa el pagaré familiar y recuerda las cláusulas de la herencia condicionada.";
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String describirRol() {
        return "Suegra Financiera (préstamos y herencias condicionadas)";
    }
}
