// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.actorFamiliar;

/**
 * Contrato común para participantes del sistema autorizados a realizar consultas
 * a través del proxy inverso según su rol (RD-2).
 */
public interface ParticipanteSistema_SGR26G05 {

    /**
     * Retorna el identificador único del participante.
     *
     * @return identificador del participante
     */
    String getId();



    /**
     * Retorna el nombre completo del participante.
     *
     * @return nombre del participante
     */
    String getNombre();



    /**
     * Retorna el rol del participante para verificación de permisos en el proxy.
     *
     * @return nombre del rol (SUEGRA, NUERA, YERNO o MEDIADOR)
     */
    String obtenerRolConsulta();
}
