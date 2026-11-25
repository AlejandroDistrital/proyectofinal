// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar.suegra.tipo;

import java.time.LocalDate;

import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Suegra omnipresente: combina estrategias presenciales, digitales y financieras,
 * aplicando el factor de reincidencia en el cálculo de su ICD (RF-03, RF-09).
 */
public class SuegraOmnipresente_SGR26G05 extends Suegra_SGR26G05 {

    private static final long serialVersionUID = 1L;
    private static final double COEFICIENTE_REINCIDENCIA = 0.1;
    private static final int MESES_VENTANA_REINCIDENCIA = 6;

    /**
     * Construye una suegra omnipresente con sus atributos completos.
     *
     * @param id               identificador único
     * @param nombre           nombre completo
     * @param edad             edad en años
     * @param aniosConvivencia años de convivencia
     * @param paciencia        nivel de paciencia inicial (0 a 100)
     */
    public SuegraOmnipresente_SGR26G05(
            String id,
            String nombre,
            int edad,
            int aniosConvivencia,
            int paciencia) {
        super(id, nombre, edad, aniosConvivencia, paciencia);
    }



    /**
     * Calcula el ICD multiplicando el promedio de conflictos activos y crónicos por
     * el factor de reincidencia (1 + 0.1 * r) usando la fecha actual como corte (RF-09).
     *
     * @return ICD ajustado por reincidencia
     */
    @Override
    public double calcularICD() {
        return calcularICD(LocalDate.now());
    }



    /**
     * Calcula el ICD aplicando el factor de reincidencia respecto a una fecha de corte dada.
     *
     * @param fechaCorte fecha de referencia para evaluar los últimos 6 meses
     * @return ICD ajustado por reincidencia
     */
    public double calcularICD(LocalDate fechaCorte) {
        double icdBase = calcularPromedioActivosYCronicos();
        if (icdBase == 0.0) {
            return 0.0;
        }
        LocalDate limite = (fechaCorte != null ? fechaCorte : LocalDate.now())
                .minusMonths(MESES_VENTANA_REINCIDENCIA);
        int reincidencias = 0;
        for (ConflictoDomestico_SGR26G05 conflicto : getConflictos()) {
            if (conflicto.esReincidenteDesde(limite)) {
                reincidencias++;
            }
        }
        double factorReincidencia = 1.0 + (COEFICIENTE_REINCIDENCIA * reincidencias);
        return icdBase * factorReincidencia;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String obtenerTipoSuegra() {
        return "Omnipresente";
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String ejecutarTacticaDomestica() {
        return getNombre() + " coordina visita sorpresa, mensaje en redes y recordatorio financiero simultáneos.";
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String describirRol() {
        return "Suegra Omnipresente (combinación presencial, digital y financiera)";
    }
}
