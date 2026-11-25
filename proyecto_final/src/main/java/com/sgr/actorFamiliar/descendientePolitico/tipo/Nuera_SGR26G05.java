// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar.descendientePolitico.tipo;

import com.sgr.actorFamiliar.Parentesco_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;

/**
 * Representa una nuera dentro del núcleo familiar con control de resiliencia (RF-01, RF-10).
 */
public class Nuera_SGR26G05 extends DescendientePolitico_SGR26G05 {

    private static final long serialVersionUID = 1L;

    /**
     * Construye una nuera con sus datos completos y nivel inicial de resiliencia.
     *
     * @param id               identificador único
     * @param nombre           nombre completo
     * @param edad             edad en años
     * @param aniosConvivencia años de convivencia
     * @param paciencia        nivel de paciencia inicial (0 a 100)
     * @param nivelResiliencia nivel de resiliencia inicial (0 a 100)
     */
    public Nuera_SGR26G05(
            String id,
            String nombre,
            int edad,
            int aniosConvivencia,
            int paciencia,
            int nivelResiliencia) {
        super(id, nombre, edad, Parentesco_SGR26G05.NUERA, aniosConvivencia, paciencia, nivelResiliencia);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String describirRol() {
        return "Nuera del núcleo familiar (resiliencia actual: " + getNivelResiliencia() + ")";
    }
}
