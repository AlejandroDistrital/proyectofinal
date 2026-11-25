// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.reportes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.nucleoFamiliar.NucleoFamiliar_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.persistencia.RegistroNacional_SGR26G05;
import com.sgr.ranking.RankingToxicidad_SGR26G05;

/**
 * Genera los cuatro informes en texto plano exigidos por el MAF (RF-12).
 */
public class GeneradorReportes_SGR26G05 {

    private static final String ARCHIVO_A = "reporte_a_ranking_toxicidad.txt";
    private static final String ARCHIVO_B = "reporte_b_historial_nucleos.txt";
    private static final String ARCHIVO_C = "reporte_c_estadisticas_mediacion.txt";
    private static final String ARCHIVO_D = "reporte_d_actores_fuga_domestica.txt";

    private final RegistroNacional_SGR26G05 registro;

    /**
     * Crea el generador de reportes asociado al registro nacional.
     *
     * @param registro registro nacional con los datos del sistema
     */
    public GeneradorReportes_SGR26G05(RegistroNacional_SGR26G05 registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro nacional es obligatorio");
        }
        this.registro = registro;
    }



    /**
     * Escribe los cuatro archivos de informe en la carpeta indicada.
     *
     * @param carpetaDestino directorio de salida
     * @return lista con los cuatro archivos creados
     * @throws IOException si falla la escritura en disco
     */
    public List<File> generarTodosLosReportes(File carpetaDestino) throws IOException {
        if (!carpetaDestino.exists()) {
            carpetaDestino.mkdirs();
        }
        ArrayList<File> archivos = new ArrayList<>();
        archivos.add(guardarTexto(new File(carpetaDestino, ARCHIVO_A), construirReporteRanking()));
        archivos.add(guardarTexto(new File(carpetaDestino, ARCHIVO_B), construirReporteNucleos()));
        archivos.add(guardarTexto(new File(carpetaDestino, ARCHIVO_C), construirReporteMediacion()));
        archivos.add(guardarTexto(new File(carpetaDestino, ARCHIVO_D), construirReporteFuga()));
        return archivos;
    }



    /**
     * Construye el informe (a): ranking nacional de toxicidad por ICD.
     *
     * @return texto del informe (a)
     */
    public String construirReporteRanking() {
        RankingToxicidad_SGR26G05 ranking = new RankingToxicidad_SGR26G05(registro.getSuegras());
        StringBuilder sb = new StringBuilder();
        sb.append("=== INFORME (A): RANKING NACIONAL DE TOXICIDAD ===\n");
        sb.append(ranking.getMensajeResultado()).append("\n\n");
        List<Suegra_SGR26G05> lista = ranking.getSuegrasOrdenadas();
        for (int i = 0; i < lista.size(); i++) {
            Suegra_SGR26G05 s = lista.get(i);
            sb.append(i + 1).append(". [").append(s.getId()).append("] ")
                    .append(s.getNombre()).append(" (").append(s.obtenerTipoSuegra())
                    .append(") | ICD: ").append(String.format("%.2f", s.calcularICD()))
                    .append(" | Conflictos: ").append(s.getCantidadTotalConflictos()).append("\n");
        }
        return sb.toString();
    }



    /**
     * Construye el informe (b): historial de conflictos por núcleo familiar.
     *
     * @return texto del informe (b)
     */
    public String construirReporteNucleos() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INFORME (B): HISTORIAL POR NÚCLEO FAMILIAR ===\n\n");
        List<NucleoFamiliar_SGR26G05> nucleos = registro.getNucleos();
        for (int i = 0; i < nucleos.size(); i++) {
            NucleoFamiliar_SGR26G05 n = nucleos.get(i);
            sb.append("Núcleo ").append(n.getIdNucleo())
                    .append(" | Suegra: ").append(n.getSuegra().getNombre())
                    .append(" | ICD agregado: ").append(String.format("%.2f", n.calcularICDAgregado()))
                    .append("\n");
            List<ConflictoDomestico_SGR26G05> historial = n.getHistorialConflictos();
            for (int j = 0; j < historial.size(); j++) {
                ConflictoDomestico_SGR26G05 c = historial.get(j);
                sb.append("  - ").append(c.getId()).append(" | ").append(c.getFechaReporte())
                        .append(" | ").append(c.getCategoria())
                        .append(" | Int: ").append(c.getIntensidad())
                        .append(" | Estado: ").append(c.getEstado()).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }



    /**
     * Construye el informe (c): tasa de éxito por tipo de mediador.
     *
     * @return texto del informe (c)
     */
    public String construirReporteMediacion() {
        Map<String, Double> tasas = registro.getSistemaMediacion().calcularTasaExitoPorTipo();
        StringBuilder sb = new StringBuilder();
        sb.append("=== INFORME (C): ESTADÍSTICAS DE MEDIACIÓN ===\n\n");
        sb.append("Total mediaciones: ")
                .append(registro.getSistemaMediacion().getHistorialResultados().size()).append("\n\n");
        for (Map.Entry<String, Double> entrada : tasas.entrySet()) {
            sb.append("- ").append(entrada.getKey()).append(": ")
                    .append(String.format("%.2f", entrada.getValue() * 100.0)).append("% de éxito\n");
        }
        return sb.toString();
    }



    /**
     * Construye el informe (d): actores en estado de fuga doméstica.
     *
     * @return texto del informe (d)
     */
    public String construirReporteFuga() {
        List<DescendientePolitico_SGR26G05> enFuga = registro.obtenerActoresEnFuga();
        StringBuilder sb = new StringBuilder();
        sb.append("=== INFORME (D): ACTORES EN FUGA DOMÉSTICA ===\n\n");
        if (enFuga.isEmpty()) {
            sb.append("No hay actores en fuga doméstica.\n");
        } else {
            for (int i = 0; i < enFuga.size(); i++) {
                DescendientePolitico_SGR26G05 a = enFuga.get(i);
                sb.append("- [").append(a.getId()).append("] ").append(a.getNombre())
                        .append(" (").append(a.getParentesco()).append(")")
                        .append(" | Resiliencia: ").append(a.getNivelResiliencia()).append("\n");
            }
        }
        return sb.toString();
    }



    private File guardarTexto(File archivo, String contenido) throws IOException {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(archivo, StandardCharsets.UTF_8))) {
            escritor.print(contenido);
        }
        return archivo;
    }
}
