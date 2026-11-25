// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.persistencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.io.Serializable;

import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.mediacion.SistemaMediacion_SGR26G05;
import com.sgr.nucleoFamiliar.NucleoFamiliar_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;

/**
 * Contenedor serializable con el estado completo del Ministerio de Armonía Familiar:
 * actores, suegras, descendientes, núcleos, conflictos y sistema de mediación (RF-11).
 */
public class RegistroNacional_SGR26G05 implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ArrayList<Suegra_SGR26G05> suegras;
    private final ArrayList<DescendientePolitico_SGR26G05> descendientes;
    private final ArrayList<NucleoFamiliar_SGR26G05> nucleos;
    private final ArrayList<ConflictoDomestico_SGR26G05> conflictos;
    private final HashMap<String, Suegra_SGR26G05> indiceSuegras;
    private final HashMap<String, DescendientePolitico_SGR26G05> indiceDescendientes;
    private final HashMap<String, NucleoFamiliar_SGR26G05> indiceNucleos;
    private final HashMap<String, ConflictoDomestico_SGR26G05> indiceConflictos;
    private final SistemaMediacion_SGR26G05 sistemaMediacion;

    /**
     * Inicializa un registro nacional vacío listo para carga o registro.
     */
    public RegistroNacional_SGR26G05() {
        this.suegras = new ArrayList<>();
        this.descendientes = new ArrayList<>();
        this.nucleos = new ArrayList<>();
        this.conflictos = new ArrayList<>();
        this.indiceSuegras = new HashMap<>();
        this.indiceDescendientes = new HashMap<>();
        this.indiceNucleos = new HashMap<>();
        this.indiceConflictos = new HashMap<>();
        this.sistemaMediacion = new SistemaMediacion_SGR26G05();
    }



    /**
     * Construye un registro nacional con las colecciones y sistema de mediación dados.
     *
     * @param suegras          lista de suegras
     * @param descendientes    lista de yernos y nueras
     * @param nucleos          lista de núcleos familiares
     * @param conflictos       lista de conflictos domésticos
     * @param sistemaMediacion sistema de mediación configurado
     */
    public RegistroNacional_SGR26G05(
            List<Suegra_SGR26G05> suegras,
            List<DescendientePolitico_SGR26G05> descendientes,
            List<NucleoFamiliar_SGR26G05> nucleos,
            List<ConflictoDomestico_SGR26G05> conflictos,
            SistemaMediacion_SGR26G05 sistemaMediacion) {
        this.suegras = new ArrayList<>(suegras != null ? suegras : Collections.emptyList());
        this.descendientes = new ArrayList<>(descendientes != null ? descendientes : Collections.emptyList());
        this.nucleos = new ArrayList<>(nucleos != null ? nucleos : Collections.emptyList());
        this.conflictos = new ArrayList<>(conflictos != null ? conflictos : Collections.emptyList());
        this.indiceSuegras = new HashMap<>();
        this.indiceDescendientes = new HashMap<>();
        this.indiceNucleos = new HashMap<>();
        this.indiceConflictos = new HashMap<>();
        for (Suegra_SGR26G05 s : this.suegras) {
            this.indiceSuegras.put(s.getId(), s);
        }
        for (DescendientePolitico_SGR26G05 d : this.descendientes) {
            this.indiceDescendientes.put(d.getId(), d);
        }
        for (NucleoFamiliar_SGR26G05 n : this.nucleos) {
            this.indiceNucleos.put(n.getIdNucleo(), n);
        }
        for (ConflictoDomestico_SGR26G05 c : this.conflictos) {
            this.indiceConflictos.put(c.getId(), c);
        }
        this.sistemaMediacion = sistemaMediacion != null ? sistemaMediacion : new SistemaMediacion_SGR26G05();
    }



    /**
     * Retorna la lista inmutable de suegras registradas.
     *
     * @return lista de suegras
     */
    public List<Suegra_SGR26G05> getSuegras() {
        return Collections.unmodifiableList(suegras);
    }



    /**
     * Retorna la lista inmutable de yernos y nueras registrados.
     *
     * @return lista de descendientes políticos
     */
    public List<DescendientePolitico_SGR26G05> getDescendientes() {
        return Collections.unmodifiableList(descendientes);
    }



    /**
     * Retorna la lista inmutable de núcleos familiares registrados.
     *
     * @return lista de núcleos familiares
     */
    public List<NucleoFamiliar_SGR26G05> getNucleos() {
        return Collections.unmodifiableList(nucleos);
    }



    /**
     * Retorna la lista inmutable de conflictos domésticos registrados.
     *
     * @return lista de conflictos
     */
    public List<ConflictoDomestico_SGR26G05> getConflictos() {
        return Collections.unmodifiableList(conflictos);
    }



    /**
     * Retorna el sistema de mediación del registro nacional.
     *
     * @return sistema de mediación
     */
    public SistemaMediacion_SGR26G05 getSistemaMediacion() {
        return sistemaMediacion;
    }



    /**
     * Retorna todos los descendientes que actualmente están en fuga doméstica.
     *
     * @return lista de actores en fuga doméstica
     */
    public List<DescendientePolitico_SGR26G05> obtenerActoresEnFuga() {
        List<DescendientePolitico_SGR26G05> enFuga = new ArrayList<>();
        for (DescendientePolitico_SGR26G05 descendiente : descendientes) {
            if (descendiente.isFugaDomestica()) {
                enFuga.add(descendiente);
            }
        }
        return Collections.unmodifiableList(enFuga);
    }



    /**
     * Busca una suegra por su identificador en tiempo constante O(1) (RD-5).
     *
     * @param id identificador único de la suegra
     * @return suegra encontrada o null si no existe
     */
    public Suegra_SGR26G05 buscarSuegraPorId(String id) {
        return id != null ? indiceSuegras.get(id.trim()) : null;
    }



    /**
     * Busca un descendiente político por su identificador en tiempo constante O(1) (RD-5).
     *
     * @param id identificador único del actor
     * @return descendiente encontrado o null si no existe
     */
    public DescendientePolitico_SGR26G05 buscarDescendientePorId(String id) {
        return id != null ? indiceDescendientes.get(id.trim()) : null;
    }



    /**
     * Busca un núcleo familiar por su identificador en tiempo constante O(1) (RD-5).
     *
     * @param idNucleo identificador único del núcleo
     * @return núcleo encontrado o null si no existe
     */
    public NucleoFamiliar_SGR26G05 buscarNucleoPorId(String idNucleo) {
        return idNucleo != null ? indiceNucleos.get(idNucleo.trim()) : null;
    }



    /**
     * Busca un conflicto doméstico por su identificador en tiempo constante O(1) (RD-5).
     *
     * @param idConflicto identificador único del conflicto
     * @return conflicto encontrado o null si no existe
     */
    public ConflictoDomestico_SGR26G05 buscarConflictoPorId(String idConflicto) {
        return idConflicto != null ? indiceConflictos.get(idConflicto.trim()) : null;
    }
}
