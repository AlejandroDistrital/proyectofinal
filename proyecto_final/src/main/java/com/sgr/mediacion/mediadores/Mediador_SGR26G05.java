// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.mediacion.mediadores;

import java.util.Random;

import java.io.Serializable;

import java.time.LocalDate;

import com.sgr.actorFamiliar.ParticipanteSistema_SGR26G05;
import com.sgr.excepciones.TransicionInvalidaExcepcion_SGR26G05;
import com.sgr.mediacion.ResultadoMediacion_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.EstadoConflicto_SGR26G05;

/**
 * Clase base abstracta para los mediadores polimórficos del sistema (RF-08).
 */
public abstract class Mediador_SGR26G05 implements Serializable, ParticipanteSistema_SGR26G05 {

    private static final long serialVersionUID = 1L;
    private static final double TASA_MINIMA = 0.0;
    private static final double TASA_MAXIMA = 1.0;
    private static int secuenciaResultado = 1;

    private String id;
    private String nombre;
    private double tasaExito;
    private final Random generadorAleatorio;

    /**
     * Construye un mediador con tasa de éxito configurable entre 0.0 y 1.0.
     *
     * @param id        identificador único del mediador
     * @param nombre    nombre completo del mediador
     * @param tasaExito probabilidad de éxito configurada [0.0, 1.0]
     */
    protected Mediador_SGR26G05(String id, String nombre, double tasaExito) {
        asignarId(id);
        asignarNombre(nombre);
        asignarTasaExito(tasaExito);
        this.generadorAleatorio = new Random();
    }



    private void asignarId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El identificador del mediador no puede estar vacío");
        }
        this.id = id.trim();
    }



    private void asignarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del mediador no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }



    private void asignarTasaExito(double tasaExito) {
        if (tasaExito < TASA_MINIMA || tasaExito > TASA_MAXIMA) {
            throw new IllegalArgumentException("La tasa de éxito debe estar entre 0.0 y 1.0: " + tasaExito);
        }
        this.tasaExito = tasaExito;
    }



    /**
     * Retorna el identificador único del mediador.
     *
     * @return identificador
     */
    @Override
    public String getId() {
        return id;
    }



    /**
     * Asigna el identificador único del mediador.
     *
     * @param id identificador no vacío
     */
    public void setId(String id) {
        asignarId(id);
    }



    /**
     * Retorna el nombre del mediador.
     *
     * @return nombre del mediador
     */
    @Override
    public String getNombre() {
        return nombre;
    }



    /**
     * Asigna el nombre del mediador validando que no esté vacío.
     *
     * @param nombre nombre completo
     */
    public void setNombre(String nombre) {
        asignarNombre(nombre);
    }



    /**
     * Retorna la tasa de éxito configurada para este mediador.
     *
     * @return tasa de éxito entre 0.0 y 1.0
     */
    public double getTasaExito() {
        return tasaExito;
    }



    /**
     * Configura la tasa de éxito validando el rango [0.0, 1.0].
     *
     * @param tasaExito tasa de éxito
     */
    public void setTasaExito(double tasaExito) {
        asignarTasaExito(tasaExito);
    }



    /**
     * Media un conflicto doméstico: lo lleva a estado {@code en_mediacion}, aplica el
     * protocolo específico del mediador y emite un {@link ResultadoMediacion_SGR26G05}.
     * Un fracaso mantiene el conflicto en {@code en_mediacion} (no lo marca resuelto).
     *
     * @param conflicto conflicto doméstico a mediar
     * @return resultado inmutable de la mediación
     * @throws TransicionInvalidaExcepcion_SGR26G05 si el conflicto no puede pasar a mediación
     */
    public ResultadoMediacion_SGR26G05 mediar(ConflictoDomestico_SGR26G05 conflicto)
            throws TransicionInvalidaExcepcion_SGR26G05 {
        if (conflicto == null) {
            throw new IllegalArgumentException("El conflicto a mediar no puede ser nulo");
        }
        if (conflicto.getEstado() == EstadoConflicto_SGR26G05.activo) {
            conflicto.transicionarA(EstadoConflicto_SGR26G05.en_mediacion);
        } else if (conflicto.getEstado() != EstadoConflicto_SGR26G05.en_mediacion) {
            throw new TransicionInvalidaExcepcion_SGR26G05(
                    conflicto.getId(),
                    conflicto.getEstado(),
                    EstadoConflicto_SGR26G05.en_mediacion);
        }
        conflicto.setMediadorAsignado(this);
        boolean acuerdo = evaluarAcuerdo(conflicto);
        String veredicto = acuerdo
                ? ResultadoMediacion_SGR26G05.obtenerVeredictoAcuerdo()
                : ResultadoMediacion_SGR26G05.obtenerVeredictoFracaso();
        if (acuerdo) {
            conflicto.transicionarA(EstadoConflicto_SGR26G05.resuelto);
        }
        String idResultado = "MD-" + (secuenciaResultado++);
        return new ResultadoMediacion_SGR26G05(idResultado, veredicto, LocalDate.now(), this, conflicto.getId());
    }



    private boolean evaluarAcuerdo(ConflictoDomestico_SGR26G05 conflicto) {
        if (tasaExito <= TASA_MINIMA) {
            return false;
        }
        if (tasaExito >= TASA_MAXIMA) {
            return true;
        }
        return generadorAleatorio.nextDouble() < calcularProbabilidadAjustada(conflicto);
    }



    /**
     * Ajusta la probabilidad de éxito según la especialidad polimórfica del mediador.
     *
     * @param conflicto conflicto evaluado
     * @return probabilidad acotada al intervalo [0.0, 1.0]
     */
    protected double calcularProbabilidadAjustada(ConflictoDomestico_SGR26G05 conflicto) {
        return tasaExito;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String obtenerRolConsulta() {
        return "MEDIADOR";
    }



    /**
     * Retorna el nombre oficial del tipo de mediador para reportes y clasificación.
     *
     * @return tipo de mediador
     */
    public abstract String obtenerTipoMediador();
}
