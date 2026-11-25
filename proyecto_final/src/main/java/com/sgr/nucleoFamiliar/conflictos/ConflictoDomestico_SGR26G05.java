// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.nucleoFamiliar.conflictos;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

import java.time.LocalDate;

import com.sgr.actorFamiliar.ActorFamiliar_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.excepciones.CategoriaInvalidaExcepcion_SGR26G05;
import com.sgr.excepciones.TransicionInvalidaExcepcion_SGR26G05;
import com.sgr.mediacion.mediadores.Mediador_SGR26G05;

/**
 * Representa un conflicto doméstico con su máquina de estados, actores e intensidad (RF-02, RF-05, RF-07).
 */
public class ConflictoDomestico_SGR26G05 implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int INTENSIDAD_MINIMA = 1;
    private static final int INTENSIDAD_MAXIMA = 10;

    private String id;
    private LocalDate fechaReporte;
    private String categoria;
    private int intensidad;
    private String descripcion;
    private final ArrayList<ActorFamiliar_SGR26G05> actoresInvolucrados;
    private EstadoConflicto_SGR26G05 estado;
    private Mediador_SGR26G05 mediadorAsignado;
    private final ArrayList<EstadoConflicto_SGR26G05> historialEstados;
    private boolean pasoDeResueltoACronico;
    private LocalDate fechaReincidencia;

    /**
     * Crea un conflicto en estado latente validando su categoría e intensidad.
     *
     * @param id           identificador único
     * @param fechaReporte fecha de reporte
     * @param categoria    categoría del catálogo oficial
     * @param intensidad   intensidad de 1 a 10
     * @param descripcion  descripción del conflicto
     * @throws CategoriaInvalidaExcepcion_SGR26G05 si la categoría no es válida
     */
    public ConflictoDomestico_SGR26G05(
            String id,
            LocalDate fechaReporte,
            String categoria,
            int intensidad,
            String descripcion) throws CategoriaInvalidaExcepcion_SGR26G05 {
        asignarId(id);
        asignarFechaReporte(fechaReporte);
        asignarCategoria(categoria);
        asignarIntensidad(intensidad);
        asignarDescripcion(descripcion);
        this.actoresInvolucrados = new ArrayList<>();
        this.historialEstados = new ArrayList<>();
        this.estado = EstadoConflicto_SGR26G05.latente;
        this.historialEstados.add(EstadoConflicto_SGR26G05.latente);
    }



    private void asignarId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El identificador del conflicto no puede estar vacío");
        }
        this.id = id.trim();
    }



    private void asignarFechaReporte(LocalDate fechaReporte) {
        if (fechaReporte == null) {
            throw new IllegalArgumentException("La fecha de reporte es obligatoria");
        }
        this.fechaReporte = fechaReporte;
    }



    private void asignarCategoria(String categoria) throws CategoriaInvalidaExcepcion_SGR26G05 {
        this.categoria = ValidadorConflicto_SGR26G05.validarCategoria(categoria, this.fechaReporte);
    }



    private void asignarIntensidad(int intensidad) {
        if (intensidad < INTENSIDAD_MINIMA || intensidad > INTENSIDAD_MAXIMA) {
            throw new IllegalArgumentException("La intensidad debe estar entre 1 y 10: " + intensidad);
        }
        this.intensidad = intensidad;
    }



    private void asignarDescripcion(String descripcion) {
        this.descripcion = (descripcion != null) ? descripcion.trim() : "";
    }



    /**
     * Retorna el identificador único del conflicto.
     *
     * @return identificador
     */
    public String getId() {
        return id;
    }



    /**
     * Asigna el identificador del conflicto.
     *
     * @param id identificador no vacío
     */
    public void setId(String id) {
        asignarId(id);
    }



    /**
     * Retorna la fecha de reporte del conflicto.
     *
     * @return fecha de reporte
     */
    public LocalDate getFechaReporte() {
        return fechaReporte;
    }



    /**
     * Asigna la fecha de reporte del conflicto.
     *
     * @param fechaReporte fecha no nula
     */
    public void setFechaReporte(LocalDate fechaReporte) {
        asignarFechaReporte(fechaReporte);
    }



    /**
     * Retorna la categoría del conflicto.
     *
     * @return categoría
     */
    public String getCategoria() {
        return categoria;
    }



    /**
     * Cambia la categoría validándola previamente con {@link ValidadorConflicto_SGR26G05} (RD-3).
     *
     * @param categoria nueva categoría
     * @throws CategoriaInvalidaExcepcion_SGR26G05 si la categoría no pertenece al catálogo
     */
    public void setCategoria(String categoria) throws CategoriaInvalidaExcepcion_SGR26G05 {
        asignarCategoria(categoria);
    }



    /**
     * Retorna la intensidad del conflicto (1 a 10).
     *
     * @return intensidad
     */
    public int getIntensidad() {
        return intensidad;
    }



    /**
     * Asigna la intensidad validando el rango [1, 10] (RF-05).
     *
     * @param intensidad intensidad entre 1 y 10
     */
    public void setIntensidad(int intensidad) {
        asignarIntensidad(intensidad);
    }



    /**
     * Retorna la descripción del conflicto.
     *
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }



    /**
     * Asigna la descripción del conflicto.
     *
     * @param descripcion texto descriptivo
     */
    public void setDescripcion(String descripcion) {
        asignarDescripcion(descripcion);
    }



    /**
     * Retorna la lista de actores involucrados en el conflicto.
     *
     * @return lista de actores
     */
    public List<ActorFamiliar_SGR26G05> getActoresInvolucrados() {
        return new ArrayList<>(actoresInvolucrados);
    }



    /**
     * Agrega un actor al conflicto verificando que no esté en fuga doméstica (RF-10).
     *
     * @param actor actor familiar a vincular
     */
    public void agregarActorInvolucrado(ActorFamiliar_SGR26G05 actor) {
        if (actor == null) {
            throw new IllegalArgumentException("El actor involucrado no puede ser nulo");
        }
        if (actor instanceof DescendientePolitico_SGR26G05) {
            DescendientePolitico_SGR26G05 descendiente = (DescendientePolitico_SGR26G05) actor;
            if (descendiente.isFugaDomestica()) {
                throw new IllegalStateException(
                        "El actor " + actor.getNombre() + " está en fuga doméstica y no participa en conflictos nuevos");
            }
        }
        if (!actoresInvolucrados.contains(actor)) {
            actoresInvolucrados.add(actor);
        }
    }



    /**
     * Retorna el estado actual del conflicto.
     *
     * @return estado actual
     */
    public EstadoConflicto_SGR26G05 getEstado() {
        return estado;
    }



    /**
     * Retorna el mediador asignado al conflicto.
     *
     * @return mediador asignado o null
     */
    public Mediador_SGR26G05 getMediadorAsignado() {
        return mediadorAsignado;
    }



    /**
     * Asigna un mediador responsable al conflicto.
     *
     * @param mediadorAsignado mediador asignado
     */
    public void setMediadorAsignado(Mediador_SGR26G05 mediadorAsignado) {
        if (mediadorAsignado == null) {
            throw new IllegalArgumentException("El mediador asignado no puede ser nulo");
        }
        this.mediadorAsignado = mediadorAsignado;
    }



    /**
     * Retorna el historial de estados recorridos por el conflicto.
     *
     * @return lista de estados
     */
    public List<EstadoConflicto_SGR26G05> getHistorialEstados() {
        return new ArrayList<>(historialEstados);
    }



    /**
     * Cambia el estado del conflicto usando la fecha actual y actualizando resiliencia.
     *
     * @param nuevoEstado estado destino
     * @throws TransicionInvalidaExcepcion_SGR26G05 si la transición no está permitida
     */
    public void transicionarA(EstadoConflicto_SGR26G05 nuevoEstado)
            throws TransicionInvalidaExcepcion_SGR26G05 {
        transicionarA(nuevoEstado, LocalDate.now(), true);
    }



    /**
     * Cambia el estado del conflicto validando la máquina de estados (RF-07).
     *
     * @param nuevoEstado           estado destino
     * @param fechaTransicion       fecha de la transición
     * @param actualizarResiliencia indica si afecta la resiliencia de yernos y nueras
     * @throws TransicionInvalidaExcepcion_SGR26G05 si la transición es inválida
     */
    public void transicionarA(
            EstadoConflicto_SGR26G05 nuevoEstado,
            LocalDate fechaTransicion,
            boolean actualizarResiliencia) throws TransicionInvalidaExcepcion_SGR26G05 {
        if (!esTransicionValida(this.estado, nuevoEstado)) {
            throw new TransicionInvalidaExcepcion_SGR26G05(id, estado, nuevoEstado);
        }
        this.estado = nuevoEstado;
        this.historialEstados.add(nuevoEstado);
        if (actualizarResiliencia) {
            aplicarEfectoResiliencia(nuevoEstado);
        }
    }



    private boolean esTransicionValida(EstadoConflicto_SGR26G05 origen, EstadoConflicto_SGR26G05 destino) {
        if (destino == null) {
            return false;
        }
        if (origen == EstadoConflicto_SGR26G05.latente && destino == EstadoConflicto_SGR26G05.activo) {
            return true;
        }
        if (origen == EstadoConflicto_SGR26G05.activo
                && (destino == EstadoConflicto_SGR26G05.en_mediacion || destino == EstadoConflicto_SGR26G05.cronico)) {
            return true;
        }
        if (origen == EstadoConflicto_SGR26G05.en_mediacion
                && (destino == EstadoConflicto_SGR26G05.resuelto || destino == EstadoConflicto_SGR26G05.cronico)) {
            return true;
        }
        return false;
    }



    private void aplicarEfectoResiliencia(EstadoConflicto_SGR26G05 nuevoEstado) {
        for (int i = 0; i < actoresInvolucrados.size(); i++) {
            ActorFamiliar_SGR26G05 actor = actoresInvolucrados.get(i);
            if (actor instanceof DescendientePolitico_SGR26G05) {
                DescendientePolitico_SGR26G05 descendiente = (DescendientePolitico_SGR26G05) actor;
                if (nuevoEstado == EstadoConflicto_SGR26G05.activo && !descendiente.isFugaDomestica()) {
                    descendiente.registrarConflictoActivo();
                } else if (nuevoEstado == EstadoConflicto_SGR26G05.resuelto) {
                    descendiente.registrarConflictoResuelto();
                }
            }
        }
    }



    /**
     * Registra una recaída histórica administrativa de resuelto a crónico para evaluar RF-09.
     *
     * @param fecha fecha de la recaída
     */
    public void registrarReincidenciaHistorica(LocalDate fecha) {
        this.pasoDeResueltoACronico = true;
        this.fechaReincidencia = fecha;
    }



    /**
     * Indica si este conflicto tuvo una reincidencia desde la fecha límite dada (RF-09).
     *
     * @param fechaLimite inicio de la ventana de 6 meses
     * @return true si cuenta como reincidencia reciente
     */
    public boolean esReincidenteDesde(LocalDate fechaLimite) {
        if (!pasoDeResueltoACronico || fechaReincidencia == null) {
            return false;
        }
        return fechaLimite == null || !fechaReincidencia.isBefore(fechaLimite);
    }
}
