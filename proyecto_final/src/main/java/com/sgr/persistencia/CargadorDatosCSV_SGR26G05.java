// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.persistencia;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.tipo.Nuera_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.tipo.Yerno_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.actorFamiliar.suegra.tipo.SuegraClasica_SGR26G05;
import com.sgr.actorFamiliar.suegra.tipo.SuegraDigital_SGR26G05;
import com.sgr.actorFamiliar.suegra.tipo.SuegraFinanciera_SGR26G05;
import com.sgr.actorFamiliar.suegra.tipo.SuegraOmnipresente_SGR26G05;
import com.sgr.excepciones.CategoriaInvalidaExcepcion_SGR26G05;
import com.sgr.excepciones.TransicionInvalidaExcepcion_SGR26G05;
import com.sgr.mediacion.ResultadoMediacion_SGR26G05;
import com.sgr.mediacion.SistemaMediacion_SGR26G05;
import com.sgr.mediacion.mediadores.MediadorAutoritario_SGR26G05;
import com.sgr.mediacion.mediadores.MediadorDiplomatico_SGR26G05;
import com.sgr.mediacion.mediadores.MediadorTerapeutico_SGR26G05;
import com.sgr.mediacion.mediadores.Mediador_SGR26G05;
import com.sgr.nucleoFamiliar.NucleoFamiliar_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.EstadoConflicto_SGR26G05;

/**
 * Carga el archivo CSV de semilla asignado y construye los objetos del sistema (RF-11).
 */
public class CargadorDatosCSV_SGR26G05 {

    private static final String SEPARADOR = ";";
    private static final double TASA_DIPLOMATICO = 0.70;
    private static final double TASA_AUTORITARIO = 0.65;
    private static final double TASA_TERAPEUTICO = 0.75;

    private CargadorDatosCSV_SGR26G05() {
    }



    /**
     * Busca el archivo CSV de semilla en la carpeta {@code datos/}.
     *
     * @param nombreArchivo nombre del archivo CSV
     * @return archivo encontrado
     * @throws IOException si no se encuentra el archivo
     */
    public static File localizarArchivoSemilla(String nombreArchivo) throws IOException {
        String[] rutas = {"datos/" + nombreArchivo, "../datos/" + nombreArchivo, nombreArchivo};
        for (int i = 0; i < rutas.length; i++) {
            File archivo = new File(rutas[i]);
            if (archivo.exists() && archivo.isFile()) {
                return archivo;
            }
        }
        throw new IOException("No se encontró el archivo de semilla: " + nombreArchivo);
    }



    /**
     * Lee el CSV y construye suegras, descendientes, núcleos, conflictos y mediaciones.
     *
     * @param archivoCsv archivo CSV separado por punto y coma
     * @return registro nacional completo
     * @throws IOException                        si falla la lectura
     * @throws CategoriaInvalidaExcepcion_SGR26G05 si un conflicto tiene categoría inválida
     * @throws TransicionInvalidaExcepcion_SGR26G05 si un conflicto tiene una transición inválida
     */
    public static RegistroNacional_SGR26G05 cargarDesdeCSV(File archivoCsv)
            throws IOException, CategoriaInvalidaExcepcion_SGR26G05, TransicionInvalidaExcepcion_SGR26G05 {
        ArrayList<String[]> filas = leerFilas(archivoCsv);
        ArrayList<Suegra_SGR26G05> listaSuegras = new ArrayList<>();
        HashMap<String, Suegra_SGR26G05> suegrasPorId = new HashMap<>();
        ArrayList<String> idsNucleo = new ArrayList<>();
        HashMap<String, String> nucleoASuegra = new HashMap<>();
        HashMap<String, ArrayList<DescendientePolitico_SGR26G05>> descendientesPorNucleo = new HashMap<>();
        ArrayList<DescendientePolitico_SGR26G05> todosDescendientes = new ArrayList<>();

        for (int i = 0; i < filas.size(); i++) {
            String[] cols = filas.get(i);
            String tipo = col(cols, 0);
            if ("SUEGRA".equalsIgnoreCase(tipo)) {
                Suegra_SGR26G05 suegra = crearSuegra(cols);
                listaSuegras.add(suegra);
                suegrasPorId.put(suegra.getId(), suegra);
            } else if ("NUCLEO".equalsIgnoreCase(tipo)) {
                String idNucleo = col(cols, 1);
                idsNucleo.add(idNucleo);
                nucleoASuegra.put(idNucleo, col(cols, 7));
                descendientesPorNucleo.put(idNucleo, new ArrayList<>());
            } else if ("ACTOR".equalsIgnoreCase(tipo)) {
                DescendientePolitico_SGR26G05 actor = crearDescendiente(cols);
                todosDescendientes.add(actor);
                String idNucleo = col(cols, 7);
                descendientesPorNucleo.get(idNucleo).add(actor);
            }
        }

        ArrayList<NucleoFamiliar_SGR26G05> listaNucleos = new ArrayList<>();
        HashMap<String, NucleoFamiliar_SGR26G05> nucleosPorId = new HashMap<>();
        for (int i = 0; i < idsNucleo.size(); i++) {
            String idNucleo = idsNucleo.get(i);
            NucleoFamiliar_SGR26G05 nucleo = new NucleoFamiliar_SGR26G05(
                    idNucleo,
                    suegrasPorId.get(nucleoASuegra.get(idNucleo)),
                    descendientesPorNucleo.get(idNucleo));
            listaNucleos.add(nucleo);
            nucleosPorId.put(idNucleo, nucleo);
        }

        ArrayList<ConflictoDomestico_SGR26G05> conflictos = new ArrayList<>();
        HashMap<String, ConflictoDomestico_SGR26G05> conflictosPorId = new HashMap<>();
        for (int i = 0; i < filas.size(); i++) {
            String[] cols = filas.get(i);
            if ("CONFLICTO".equalsIgnoreCase(col(cols, 0))) {
                ConflictoDomestico_SGR26G05 conflicto = procesarConflicto(cols, nucleosPorId);
                conflictos.add(conflicto);
                conflictosPorId.put(conflicto.getId(), conflicto);
            }
        }

        SistemaMediacion_SGR26G05 sistemaMediacion = crearSistemaMediacion();
        for (int i = 0; i < filas.size(); i++) {
            String[] cols = filas.get(i);
            if ("MEDIACION".equalsIgnoreCase(col(cols, 0))) {
                registrarMediacion(cols, sistemaMediacion, conflictosPorId);
            }
        }

        return new RegistroNacional_SGR26G05(
                listaSuegras, todosDescendientes, listaNucleos, conflictos, sistemaMediacion);
    }



    private static ArrayList<String[]> leerFilas(File archivoCsv) throws IOException {
        ArrayList<String[]> filas = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(archivoCsv, StandardCharsets.UTF_8))) {
            String linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                if (!linea.isBlank()) {
                    filas.add(linea.split(SEPARADOR, -1));
                }
            }
        }
        return filas;
    }



    private static Suegra_SGR26G05 crearSuegra(String[] cols) {
        String id = col(cols, 1);
        String nombre = col(cols, 2);
        String subtipo = col(cols, 3).toLowerCase();
        int edad = Integer.parseInt(col(cols, 4));
        int anios = Integer.parseInt(col(cols, 5));
        int paciencia = Integer.parseInt(col(cols, 6));
        if ("clasica".equals(subtipo)) {
            return new SuegraClasica_SGR26G05(id, nombre, edad, anios, paciencia);
        }
        if ("digital".equals(subtipo)) {
            return new SuegraDigital_SGR26G05(id, nombre, edad, anios, paciencia);
        }
        if ("financiera".equals(subtipo)) {
            return new SuegraFinanciera_SGR26G05(id, nombre, edad, anios, paciencia);
        }
        return new SuegraOmnipresente_SGR26G05(id, nombre, edad, anios, paciencia);
    }



    private static DescendientePolitico_SGR26G05 crearDescendiente(String[] cols) {
        String id = col(cols, 1);
        String nombre = col(cols, 2);
        String subtipo = col(cols, 3).toLowerCase();
        int edad = Integer.parseInt(col(cols, 4));
        int anios = Integer.parseInt(col(cols, 5));
        int valor = Integer.parseInt(col(cols, 6));
        if ("nuera".equals(subtipo)) {
            return new Nuera_SGR26G05(id, nombre, edad, anios, valor, valor);
        }
        return new Yerno_SGR26G05(id, nombre, edad, anios, valor, valor);
    }



    private static ConflictoDomestico_SGR26G05 procesarConflicto(
            String[] cols,
            HashMap<String, NucleoFamiliar_SGR26G05> nucleosPorId)
            throws CategoriaInvalidaExcepcion_SGR26G05, TransicionInvalidaExcepcion_SGR26G05 {
        String id = col(cols, 1);
        String categoria = col(cols, 3);
        int intensidad = Integer.parseInt(col(cols, 4));
        String idNucleo = col(cols, 7);
        LocalDate fecha = LocalDate.parse(col(cols, 8));
        String historial = col(cols, 10);

        NucleoFamiliar_SGR26G05 nucleo = nucleosPorId.get(idNucleo);
        ConflictoDomestico_SGR26G05 conflicto =
                new ConflictoDomestico_SGR26G05(id, fecha, categoria, intensidad, "Incidente " + categoria);
        conflicto.agregarActorInvolucrado(nucleo.getSuegra());
        for (int i = 0; i < nucleo.getDescendientes().size(); i++) {
            DescendientePolitico_SGR26G05 d = nucleo.getDescendientes().get(i);
            if (!d.isFugaDomestica()) {
                conflicto.agregarActorInvolucrado(d);
            }
        }
        nucleo.registrarConflicto(conflicto);

        String[] pasos = historial.split(">");
        for (int i = 1; i < pasos.length; i++) {
            conflicto.transicionarA(EstadoConflicto_SGR26G05.desdeTexto(pasos[i]), fecha, true);
        }
        return conflicto;
    }



    private static SistemaMediacion_SGR26G05 crearSistemaMediacion() {
        SistemaMediacion_SGR26G05 sistema = new SistemaMediacion_SGR26G05();
        sistema.registrarMediador(new MediadorDiplomatico_SGR26G05("MED-DIP-01", "Lord Pacto", TASA_DIPLOMATICO));
        sistema.registrarMediador(new MediadorAutoritario_SGR26G05("MED-AUT-01", "Magistrada Firme", TASA_AUTORITARIO));
        sistema.registrarMediador(new MediadorTerapeutico_SGR26G05("MED-TER-01", "Dra. Armonía", TASA_TERAPEUTICO));
        return sistema;
    }



    private static void registrarMediacion(
            String[] cols,
            SistemaMediacion_SGR26G05 sistema,
            HashMap<String, ConflictoDomestico_SGR26G05> conflictosPorId) {
        String id = col(cols, 1);
        String tipoMediador = col(cols, 3);
        String veredicto = col(cols, 6);
        String idConflicto = col(cols, 7);
        LocalDate fecha = LocalDate.parse(col(cols, 8));

        Mediador_SGR26G05 mediador = sistema.buscarPorTipo(tipoMediador);
        sistema.registrarResultadoHistorico(
                new ResultadoMediacion_SGR26G05(id, veredicto, fecha, mediador, idConflicto));
        ConflictoDomestico_SGR26G05 conflicto = conflictosPorId.get(idConflicto);
        if (conflicto != null && conflicto.getMediadorAsignado() == null) {
            conflicto.setMediadorAsignado(mediador);
        }
    }



    private static String col(String[] cols, int indice) {
        if (indice >= cols.length || cols[indice] == null) {
            return "";
        }
        return cols[indice].trim();
    }
}
