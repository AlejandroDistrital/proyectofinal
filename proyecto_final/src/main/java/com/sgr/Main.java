// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr;

import java.util.Arrays;

import java.io.File;
import java.io.IOException;

import com.sgr.excepciones.CategoriaInvalidaExcepcion_SGR26G05;
import com.sgr.excepciones.TransicionInvalidaExcepcion_SGR26G05;
import com.sgr.persistencia.CargadorDatosCSV_SGR26G05;
import com.sgr.persistencia.Persistencia_SGR26G05;
import com.sgr.persistencia.RegistroNacional_SGR26G05;
import com.sgr.ranking.RankingToxicidad_SGR26G05;
import com.sgr.reportes.GeneradorReportes_SGR26G05;
import com.sgr.ui.InterfazGrafica_SGR26G05;

/**
 * Punto de entrada principal del Sistema de Gestión de Conflictos Domésticos de Suegristán.
 * Carga los datos de semilla, ejecuta la persistencia binaria, genera los cuatro informes
 * de texto plano y lanza la interfaz gráfica JavaFX salvo que se indique {@code --cli}.
 */
public class Main {

    private static final String ARCHIVO_SEMILLA = "datos_prueba_SGR26G05-03.csv";
    private static final String RUTA_DAT = "salida/sgr26g05_estado.dat";
    private static final String RUTA_REPORTES = "salida/reportes";

    /**
     * Ejecuta el flujo completo del sistema y lanza opcionalmente la interfaz JavaFX.
     *
     * @param args argumentos de consola ({@code --cli} omite la apertura de ventana JavaFX)
     */
    public static void main(String[] args) {
        try {
            File csv = CargadorDatosCSV_SGR26G05.localizarArchivoSemilla(ARCHIVO_SEMILLA);
            RegistroNacional_SGR26G05 registro = CargadorDatosCSV_SGR26G05.cargarDesdeCSV(csv);

            File archivoDat = new File(RUTA_DAT);
            Persistencia_SGR26G05.guardarSistema(registro, archivoDat);
            RegistroNacional_SGR26G05 recargado = Persistencia_SGR26G05.cargarSistema(archivoDat);

            GeneradorReportes_SGR26G05 generador = new GeneradorReportes_SGR26G05(recargado);
            generador.generarTodosLosReportes(new File(RUTA_REPORTES));

            RankingToxicidad_SGR26G05 ranking = new RankingToxicidad_SGR26G05(recargado.getSuegras());
            System.out.println(generador.construirReporteRanking());
            System.out.println("Ganadora determinada: "
                    + (ranking.determinarGanadora() != null ? ranking.determinarGanadora().getNombre() : "Empate"));
            System.out.println("Persistencia verificada en: " + archivoDat.getPath());
            System.out.println("Reportes generados en: " + RUTA_REPORTES);

            boolean modoCli = args != null && Arrays.asList(args).contains("--cli");
            if (!modoCli) {
                InterfazGrafica_SGR26G05.lanzar(args);
            }
        } catch (IOException | ClassNotFoundException
                | CategoriaInvalidaExcepcion_SGR26G05 | TransicionInvalidaExcepcion_SGR26G05 ex) {
            System.err.println("Error ejecutando SGR26G05: " + ex.getMessage());
            System.exit(1);
        }
    }
}
