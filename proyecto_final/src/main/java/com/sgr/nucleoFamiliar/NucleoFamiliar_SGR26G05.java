// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.nucleoFamiliar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.Serializable;

import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.excepciones.CategoriaInvalidaExcepcion_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.EstadoConflicto_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ValidadorConflicto_SGR26G05;

/**
 * Representa un núcleo familiar compuesto estrictamente por una suegra, entre 1 y 4
 * yernos o nueras no compartidos y su historial de conflictos (RF-04, RD-3).
 */
public class NucleoFamiliar_SGR26G05 implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int MIN_DESCENDIENTES = 1;
    private static final int MAX_DESCENDIENTES = 4;
    private static final double PESO_SUEGRA = 0.7;
    private static final double ESCALA_RESILIENCIA_A_ICD = 10.0;
    private static final double RESILIENCIA_MAXIMA = 100.0;

    private String idNucleo;
    private Suegra_SGR26G05 suegra;
    private final ArrayList<DescendientePolitico_SGR26G05> descendientes;
    private final ArrayList<ConflictoDomestico_SGR26G05> historialConflictos;

    /**
     * Construye un núcleo familiar validando la composición exclusiva de exactamente una suegra
     * y entre 1 y 4 nueras o yernos.
     *
     * @param idNucleo      identificador único del núcleo familiar
     * @param suegra        única suegra titular del núcleo
     * @param descendientes lista de entre 1 y 4 nueras o yernos
     */
    public NucleoFamiliar_SGR26G05(
            String idNucleo,
            Suegra_SGR26G05 suegra,
            List<DescendientePolitico_SGR26G05> descendientes) {
        asignarIdNucleo(idNucleo);
        this.descendientes = new ArrayList<>();
        this.historialConflictos = new ArrayList<>();
        validarComposicionCompleta(suegra, descendientes);
        asignarSuegra(suegra);
        asignarDescendientes(descendientes);
    }



    private void validarComposicionCompleta(
            Suegra_SGR26G05 suegraCandidata,
            List<DescendientePolitico_SGR26G05> descendientesCandidatos) {
        if (suegraCandidata == null) {
            throw new IllegalArgumentException("Un núcleo familiar debe tener exactamente una suegra");
        }
        if (descendientesCandidatos == null
                || descendientesCandidatos.size() < MIN_DESCENDIENTES
                || descendientesCandidatos.size() > MAX_DESCENDIENTES) {
            int cantidad = descendientesCandidatos == null ? 0 : descendientesCandidatos.size();
            throw new IllegalArgumentException(
                    "Un núcleo familiar debe tener entre 1 y 4 nueras/yernos; recibidos: " + cantidad);
        }
        if (suegraCandidata.getIdNucleoAsignado() != null
                && !suegraCandidata.getIdNucleoAsignado().equals(this.idNucleo)) {
            throw new IllegalStateException(
                    "La suegra " + suegraCandidata.getNombre() + " ya pertenece a otro núcleo");
        }
        for (DescendientePolitico_SGR26G05 descendiente : descendientesCandidatos) {
            if (descendiente == null) {
                throw new IllegalArgumentException("Ningún descendiente del núcleo puede ser nulo");
            }
            if (descendiente.getIdNucleoAsignado() != null
                    && !descendiente.getIdNucleoAsignado().equals(this.idNucleo)) {
                throw new IllegalStateException(
                        "El descendiente " + descendiente.getNombre() + " ya pertenece a otro núcleo");
            }
        }
    }



    private void asignarIdNucleo(String idNucleo) {
        if (idNucleo == null || idNucleo.isBlank()) {
            throw new IllegalArgumentException("El identificador del núcleo no puede estar vacío");
        }
        this.idNucleo = idNucleo.trim();
    }



    private void asignarSuegra(Suegra_SGR26G05 suegra) {
        if (suegra == null) {
            throw new IllegalArgumentException("Un núcleo familiar debe tener exactamente una suegra");
        }
        suegra.setIdNucleoAsignado(this.idNucleo);
        this.suegra = suegra;
    }



    private void asignarDescendientes(List<DescendientePolitico_SGR26G05> nuevosDescendientes) {
        if (nuevosDescendientes == null
                || nuevosDescendientes.size() < MIN_DESCENDIENTES
                || nuevosDescendientes.size() > MAX_DESCENDIENTES) {
            int cantidad = nuevosDescendientes == null ? 0 : nuevosDescendientes.size();
            throw new IllegalArgumentException(
                    "Un núcleo familiar debe tener entre 1 y 4 nueras/yernos; recibidos: " + cantidad);
        }
        for (DescendientePolitico_SGR26G05 descendiente : nuevosDescendientes) {
            if (descendiente == null) {
                throw new IllegalArgumentException("Ningún descendiente del núcleo puede ser nulo");
            }
            descendiente.setIdNucleoAsignado(this.idNucleo);
        }
        this.descendientes.clear();
        this.descendientes.addAll(nuevosDescendientes);
    }



    /**
     * Retorna el identificador del núcleo familiar.
     *
     * @return identificador del núcleo
     */
    public String getIdNucleo() {
        return idNucleo;
    }



    /**
     * Asigna el identificador del núcleo validando que no sea vacío.
     *
     * @param idNucleo identificador único
     */
    public void setIdNucleo(String idNucleo) {
        asignarIdNucleo(idNucleo);
    }



    /**
     * Retorna la suegra titular del núcleo familiar.
     *
     * @return suegra del núcleo
     */
    public Suegra_SGR26G05 getSuegra() {
        return suegra;
    }



    /**
     * Asigna la suegra del núcleo validando exclusividad por composición.
     *
     * @param suegra suegra titular (no nula y sin otro núcleo propietario)
     */
    public void setSuegra(Suegra_SGR26G05 suegra) {
        asignarSuegra(suegra);
    }



    /**
     * Retorna una vista inmutable de los descendientes políticos del núcleo.
     *
     * @return lista inmutable de yernos y nueras
     */
    public List<DescendientePolitico_SGR26G05> getDescendientes() {
        return Collections.unmodifiableList(descendientes);
    }



    /**
     * Reemplaza la lista de descendientes validando la cardinalidad [1, 4] y exclusividad.
     *
     * @param nuevosDescendientes lista con entre 1 y 4 descendientes
     */
    public void setDescendientes(List<DescendientePolitico_SGR26G05> nuevosDescendientes) {
        asignarDescendientes(nuevosDescendientes);
    }



    /**
     * Retorna una vista inmutable del historial de conflictos del núcleo.
     *
     * @return lista inmutable de conflictos
     */
    public List<ConflictoDomestico_SGR26G05> getHistorialConflictos() {
        return Collections.unmodifiableList(historialConflictos);
    }



    /**
     * Registra un conflicto en el núcleo validando su categoría antes del registro (RD-3)
     * y vinculándolo a la suegra titular.
     *
     * @param conflicto conflicto doméstico a registrar
     * @throws CategoriaInvalidaExcepcion_SGR26G05 si la categoría no pertenece al catálogo oficial
     */
    public void registrarConflicto(ConflictoDomestico_SGR26G05 conflicto)
            throws CategoriaInvalidaExcepcion_SGR26G05 {
        if (conflicto == null) {
            throw new IllegalArgumentException("El conflicto no puede ser nulo");
        }
        ValidadorConflicto_SGR26G05.validarCategoria(conflicto.getCategoria(), conflicto.getFechaReporte());
        if (!historialConflictos.contains(conflicto)) {
            historialConflictos.add(conflicto);
            suegra.registrarConflicto(conflicto);
        }
    }



    /**
     * Calcula el ICD agregado del núcleo ponderando el ICD de la suegra titular
     * y el desgaste promedio de resiliencia de sus descendientes.
     *
     * @return índice agregado de conflictividad del núcleo
     */
    public double calcularICDAgregado() {
        double icdSuegra = suegra.calcularICD();
        double sumaDesgaste = 0.0;
        for (DescendientePolitico_SGR26G05 descendiente : descendientes) {
            sumaDesgaste += (RESILIENCIA_MAXIMA - descendiente.getNivelResiliencia()) / ESCALA_RESILIENCIA_A_ICD;
        }
        double desgastePromedio = sumaDesgaste / descendientes.size();
        return (PESO_SUEGRA * icdSuegra) + ((1.0 - PESO_SUEGRA) * desgastePromedio);
    }



    /**
     * Cuenta cuántos conflictos del núcleo se encuentran actualmente en un estado dado.
     *
     * @param estado estado a contabilizar
     * @return número de conflictos en ese estado
     */
    public long contarConflictosPorEstado(EstadoConflicto_SGR26G05 estado) {
        long conteo = 0;
        for (ConflictoDomestico_SGR26G05 conflicto : historialConflictos) {
            if (conflicto.getEstado() == estado) {
                conteo++;
            }
        }
        return conteo;
    }
}
