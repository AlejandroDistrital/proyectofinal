// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.Serializable;

import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;

/**
 * Ordena las suegras de forma descendente por ICD y determina la ganadora
 * o informa si hay empate en el primer puesto (RF-06).
 */
public class RankingToxicidad_SGR26G05 implements Comparable<RankingToxicidad_SGR26G05>, Serializable {

    private static final long serialVersionUID = 1L;
    private static final double MARGEN_EMPATE = 0.0001;

    private final ArrayList<Suegra_SGR26G05> suegrasOrdenadas;
    private String mensajeResultado;

    /**
     * Construye el ranking ordenando las suegras de mayor a menor ICD.
     *
     * @param suegras lista de suegras del período
     */
    public RankingToxicidad_SGR26G05(List<Suegra_SGR26G05> suegras) {
        this.suegrasOrdenadas = new ArrayList<>();
        if (suegras != null) {
            for (int i = 0; i < suegras.size(); i++) {
                Suegra_SGR26G05 suegra = suegras.get(i);
                if (suegra != null) {
                    this.suegrasOrdenadas.add(suegra);
                }
            }
        }
        Collections.sort(this.suegrasOrdenadas);
        construirMensajeResultado();
    }



    private void construirMensajeResultado() {
        if (suegrasOrdenadas.isEmpty()) {
            this.mensajeResultado = "No hay suegras registradas.";
            return;
        }
        if (tieneEmpate()) {
            double maximo = suegrasOrdenadas.get(0).calcularICD();
            String nombres = "";
            List<Suegra_SGR26G05> empatadas = calcularEmpatadas();
            for (int i = 0; i < empatadas.size(); i++) {
                if (i > 0) {
                    nombres += ", ";
                }
                nombres += empatadas.get(i).getNombre();
            }
            this.mensajeResultado = "EMPATE en el primer puesto con ICD "
                    + String.format("%.2f", maximo) + ": " + nombres;
        } else {
            Suegra_SGR26G05 ganadora = suegrasOrdenadas.get(0);
            this.mensajeResultado = "Ganadora de la Orden del Rodillo de Oro: "
                    + ganadora.getNombre()
                    + " (ICD: " + String.format("%.2f", ganadora.calcularICD()) + ")";
        }
    }



    private boolean tieneEmpate() {
        if (suegrasOrdenadas.size() < 2) {
            return false;
        }
        double primero = suegrasOrdenadas.get(0).calcularICD();
        double segundo = suegrasOrdenadas.get(1).calcularICD();
        return Math.abs(primero - segundo) < MARGEN_EMPATE;
    }



    private List<Suegra_SGR26G05> calcularEmpatadas() {
        ArrayList<Suegra_SGR26G05> empatadas = new ArrayList<>();
        if (suegrasOrdenadas.isEmpty()) {
            return empatadas;
        }
        double maximo = suegrasOrdenadas.get(0).calcularICD();
        for (int i = 0; i < suegrasOrdenadas.size(); i++) {
            Suegra_SGR26G05 suegra = suegrasOrdenadas.get(i);
            if (Math.abs(suegra.calcularICD() - maximo) < MARGEN_EMPATE) {
                empatadas.add(suegra);
            } else {
                break;
            }
        }
        return empatadas;
    }



    /**
     * Retorna la lista de suegras ordenada de forma descendente por ICD.
     *
     * @return lista ordenada
     */
    public List<Suegra_SGR26G05> getSuegrasOrdenadas() {
        return new ArrayList<>(suegrasOrdenadas);
    }



    /**
     * Retorna el ICD de una suegra.
     *
     * @param suegra suegra a consultar
     * @return valor de ICD
     */
    public double obtenerICD(Suegra_SGR26G05 suegra) {
        return (suegra != null) ? suegra.calcularICD() : 0.0;
    }



    /**
     * Retorna la suegra con mayor ICD global, o null si hay empate en el primer puesto.
     *
     * @return suegra ganadora única o null ante empate
     */
    public Suegra_SGR26G05 determinarGanadora() {
        if (suegrasOrdenadas.isEmpty() || hayEmpatePrimerPuesto()) {
            System.out.println(mensajeResultado);
            return null;
        }
        return suegrasOrdenadas.get(0);
    }



    /**
     * Indica si dos o más suegras empatan en el primer lugar.
     *
     * @return true si hay empate
     */
    public boolean hayEmpatePrimerPuesto() {
        return tieneEmpate();
    }



    /**
     * Retorna la lista de suegras empatadas en la primera posición.
     *
     * @return lista de suegras empatadas
     */
    public List<Suegra_SGR26G05> obtenerEmpatadasPrimerPuesto() {
        return calcularEmpatadas();
    }



    /**
     * Retorna el mensaje con la ganadora o el aviso de empate.
     *
     * @return mensaje del resultado
     */
    public String getMensajeResultado() {
        return mensajeResultado;
    }



    /**
     * Retorna el mayor ICD del ranking.
     *
     * @return ICD máximo o 0.0 si está vacío
     */
    public double obtenerIcdMaximo() {
        if (suegrasOrdenadas.isEmpty()) {
            return 0.0;
        }
        return suegrasOrdenadas.get(0).calcularICD();
    }



    /**
     * Compara dos rankings por su ICD máximo de forma descendente (RF-06).
     *
     * @param otro otro ranking a comparar
     * @return entero negativo si este ranking tiene mayor ICD máximo
     */
    @Override
    public int compareTo(RankingToxicidad_SGR26G05 otro) {
        if (otro == null) {
            return -1;
        }
        return Double.compare(otro.obtenerIcdMaximo(), this.obtenerIcdMaximo());
    }
}
