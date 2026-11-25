// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Guarda y carga el estado completo del sistema en archivos {@code .dat}
 * usando {@link ObjectOutputStream} y {@link ObjectInputStream} (RF-11).
 */
public class Persistencia_SGR26G05 {

    private Persistencia_SGR26G05() {
    }



    /**
     * Serializa el registro nacional en un archivo binario {@code .dat}.
     *
     * @param registro estado completo del sistema
     * @param archivo  archivo destino
     * @throws IOException si ocurre un error al escribir
     */
    public static void guardarSistema(RegistroNacional_SGR26G05 registro, File archivo) throws IOException {
        if (registro == null || archivo == null) {
            throw new IllegalArgumentException("El registro y el archivo son obligatorios");
        }
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            salida.writeObject(registro);
        }
    }



    /**
     * Deserializa el registro nacional desde un archivo {@code .dat}.
     *
     * @param archivo archivo binario guardado
     * @return registro nacional recuperado
     * @throws IOException            si ocurre un error de lectura
     * @throws ClassNotFoundException si falta alguna clase al deserializar
     */
    public static RegistroNacional_SGR26G05 cargarSistema(File archivo)
            throws IOException, ClassNotFoundException {
        if (archivo == null || !archivo.exists()) {
            throw new IOException("No existe el archivo: " + archivo);
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
            return (RegistroNacional_SGR26G05) entrada.readObject();
        }
    }
}
