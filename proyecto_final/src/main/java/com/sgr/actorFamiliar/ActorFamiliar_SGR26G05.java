// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar;

import java.io.Serializable;

/**
 * Clase base abstracta que representa a todo actor familiar registrado en el
 * Ministerio de Armonía Familiar (RF-01).
 */
public abstract class ActorFamiliar_SGR26G05 implements Serializable, ParticipanteSistema_SGR26G05 {

    private static final long serialVersionUID = 1L;
    private static final int PACIENCIA_MINIMA = 0;
    private static final int PACIENCIA_MAXIMA = 100;

    private String id;
    private String nombre;
    private int edad;
    private Parentesco_SGR26G05 parentesco;
    private int aniosConvivencia;
    private int paciencia;
    private String idNucleoAsignado;

    /**
     * Construye un actor familiar validando todos sus atributos obligatorios.
     *
     * @param id               identificador único del actor
     * @param nombre           nombre completo del actor
     * @param edad             edad en años (mayor o igual a 0)
     * @param parentesco       parentesco oficial (SUEGRA, NUERA o YERNO)
     * @param aniosConvivencia años de convivencia (mayor o igual a 0)
     * @param paciencia        nivel de paciencia inicial (0 a 100)
     */
    protected ActorFamiliar_SGR26G05(
            String id,
            String nombre,
            int edad,
            Parentesco_SGR26G05 parentesco,
            int aniosConvivencia,
            int paciencia) {
        asignarId(id);
        asignarNombre(nombre);
        asignarEdad(edad);
        asignarParentesco(parentesco);
        asignarAniosConvivencia(aniosConvivencia);
        asignarPaciencia(paciencia);
    }



    private void asignarId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El identificador del actor no puede ser vacío");
        }
        this.id = id.trim();
    }



    private void asignarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del actor no puede ser vacío");
        }
        this.nombre = nombre.trim();
    }



    private void asignarEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa: " + edad);
        }
        this.edad = edad;
    }



    private void asignarParentesco(Parentesco_SGR26G05 parentesco) {
        if (parentesco == null) {
            throw new IllegalArgumentException("El parentesco es obligatorio");
        }
        this.parentesco = parentesco;
    }



    private void asignarAniosConvivencia(int aniosConvivencia) {
        if (aniosConvivencia < 0) {
            throw new IllegalArgumentException("Los años de convivencia no pueden ser negativos: " + aniosConvivencia);
        }
        this.aniosConvivencia = aniosConvivencia;
    }



    private void asignarPaciencia(int paciencia) {
        if (paciencia < PACIENCIA_MINIMA || paciencia > PACIENCIA_MAXIMA) {
            throw new IllegalArgumentException("La paciencia debe estar entre 0 y 100: " + paciencia);
        }
        this.paciencia = paciencia;
    }



    /**
     * Retorna el identificador único del actor familiar.
     *
     * @return identificador único
     */
    @Override
    public String getId() {
        return id;
    }



    /**
     * Asigna el identificador único del actor validando que no sea vacío.
     *
     * @param id identificador único
     */
    public void setId(String id) {
        asignarId(id);
    }



    /**
     * Retorna el nombre completo del actor familiar.
     *
     * @return nombre completo
     */
    @Override
    public String getNombre() {
        return nombre;
    }



    /**
     * Asigna el nombre completo del actor validando que no sea nulo ni en blanco.
     *
     * @param nombre nombre completo
     */
    public void setNombre(String nombre) {
        asignarNombre(nombre);
    }



    /**
     * Retorna la edad del actor familiar.
     *
     * @return edad en años
     */
    public int getEdad() {
        return edad;
    }



    /**
     * Asigna la edad del actor validando que no sea negativa.
     *
     * @param edad edad en años
     */
    public void setEdad(int edad) {
        asignarEdad(edad);
    }



    /**
     * Retorna el parentesco oficial del actor familiar.
     *
     * @return parentesco
     */
    public Parentesco_SGR26G05 getParentesco() {
        return parentesco;
    }



    /**
     * Asigna el parentesco del actor validando que no sea nulo.
     *
     * @param parentesco parentesco oficial
     */
    public void setParentesco(Parentesco_SGR26G05 parentesco) {
        asignarParentesco(parentesco);
    }



    /**
     * Retorna los años de convivencia del actor.
     *
     * @return años de convivencia
     */
    public int getAniosConvivencia() {
        return aniosConvivencia;
    }



    /**
     * Asigna los años de convivencia validando que no sean negativos.
     *
     * @param aniosConvivencia años de convivencia
     */
    public void setAniosConvivencia(int aniosConvivencia) {
        asignarAniosConvivencia(aniosConvivencia);
    }



    /**
     * Retorna el nivel de paciencia actual del actor (0 a 100).
     *
     * @return nivel de paciencia
     */
    public int getPaciencia() {
        return paciencia;
    }



    /**
     * Asigna el nivel de paciencia validando el rango oficial [0, 100].
     *
     * @param paciencia nivel de paciencia entre 0 y 100
     */
    public void setPaciencia(int paciencia) {
        asignarPaciencia(paciencia);
    }



    /**
     * Retorna el identificador del núcleo familiar al que pertenece este actor.
     *
     * @return identificador del núcleo asignado o null si aún no pertenece a ninguno
     */
    public String getIdNucleoAsignado() {
        return idNucleoAsignado;
    }



    /**
     * Asigna el identificador del núcleo familiar asegurando exclusividad por composición.
     *
     * @param idNucleoAsignado identificador del núcleo familiar propietario
     */
    public void setIdNucleoAsignado(String idNucleoAsignado) {
        if (this.idNucleoAsignado != null
                && idNucleoAsignado != null
                && !this.idNucleoAsignado.equals(idNucleoAsignado)) {
            throw new IllegalStateException(
                    "El actor " + nombre + " ya pertenece al núcleo " + this.idNucleoAsignado);
        }
        this.idNucleoAsignado = idNucleoAsignado;
    }



    /**
     * Retorna el rol de consulta del actor según su parentesco para el proxy inverso.
     *
     * @return nombre del parentesco o rol
     */
    @Override
    public String obtenerRolConsulta() {
        return parentesco != null ? parentesco.name() : "DESCONOCIDO";
    }



    /**
     * Describe el rol y perfil específico de este actor dentro del núcleo familiar.
     *
     * @return descripción polimórfica del rol
     */
    public abstract String describirRol();
}
