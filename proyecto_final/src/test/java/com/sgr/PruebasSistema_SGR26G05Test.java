// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sgr.actorFamiliar.ActorFamiliar_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.tipo.Nuera_SGR26G05;
import com.sgr.actorFamiliar.descendientePolitico.tipo.Yerno_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.actorFamiliar.suegra.tipo.SuegraClasica_SGR26G05;
import com.sgr.actorFamiliar.suegra.tipo.SuegraDigital_SGR26G05;
import com.sgr.actorFamiliar.suegra.tipo.SuegraFinanciera_SGR26G05;
import com.sgr.actorFamiliar.suegra.tipo.SuegraOmnipresente_SGR26G05;
import com.sgr.excepciones.AccesoDenegadoExcepcion_SGR26G05;
import com.sgr.excepciones.CategoriaInvalidaExcepcion_SGR26G05;
import com.sgr.excepciones.TransicionInvalidaExcepcion_SGR26G05;
import com.sgr.mediacion.ResultadoMediacion_SGR26G05;
import com.sgr.mediacion.SistemaMediacion_SGR26G05;
import com.sgr.mediacion.estrategia.EstrategiaMayorTasaExito_SGR26G05;
import com.sgr.mediacion.estrategia.EstrategiaPorCategoria_SGR26G05;
import com.sgr.mediacion.mediadores.MediadorAutoritario_SGR26G05;
import com.sgr.mediacion.mediadores.MediadorDiplomatico_SGR26G05;
import com.sgr.mediacion.mediadores.MediadorTerapeutico_SGR26G05;
import com.sgr.nucleoFamiliar.NucleoFamiliar_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.EstadoConflicto_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ValidadorConflicto_SGR26G05;
import com.sgr.persistencia.CargadorDatosCSV_SGR26G05;
import com.sgr.persistencia.Persistencia_SGR26G05;
import com.sgr.persistencia.RegistroNacional_SGR26G05;
import com.sgr.proxy.ProxyAccesoMAF_SGR26G05;
import com.sgr.proxy.ServicioConsultasReal_SGR26G05;
import com.sgr.proxy.ServicioConsultas_SGR26G05;
import com.sgr.ranking.RankingToxicidad_SGR26G05;
import com.sgr.reportes.GeneradorReportes_SGR26G05;

/**
 * Pruebas unitarias JUnit 5 del sistema SGR26G05.
 */
public class PruebasSistema_SGR26G05Test {

    /**
     * Prueba 1 obligatoria: rechazo de categorías fuera del catálogo oficial (RF-02, RD-3).
     */
    @Test
    public void rechazarCategoriaFueraDeCatalogo() throws Exception {
        LocalDate fecha = LocalDate.of(2026, 5, 10);
        CategoriaInvalidaExcepcion_SGR26G05 ex = assertThrows(
                CategoriaInvalidaExcepcion_SGR26G05.class,
                () -> ValidadorConflicto_SGR26G05.validarCategoria("diplomático", fecha));
        assertTrue(ex.getMessage().contains("diplomático"));
        assertTrue(ex.getMessage().contains("2026-05-10"));

        ConflictoDomestico_SGR26G05 valido =
                new ConflictoDomestico_SGR26G05("CF-OK", fecha, "ceremonial", 4, "Boda real");
        assertEquals("ceremonial", valido.getCategoria());
    }



    /**
     * Prueba 2 obligatoria: inmutabilidad efectiva de ResultadoMediacion_SGR26G05 (RD-1, RNF-2).
     */
    @Test
    public void verificarInmutabilidadResultadoMediacion() {
        MediadorTerapeutico_SGR26G05 mediador = new MediadorTerapeutico_SGR26G05("M1", "Dra. Clara", 0.75);
        LocalDate fecha = LocalDate.of(2026, 4, 20);
        ResultadoMediacion_SGR26G05 original =
                new ResultadoMediacion_SGR26G05("MD-01", "acuerdo", fecha, mediador, "CF001");

        ResultadoMediacion_SGR26G05 rectificado = original.rectificarVeredicto("fracaso");
        assertEquals("acuerdo", original.getVeredicto());
        assertEquals("fracaso", rectificado.getVeredicto());
        assertNotEquals(original, rectificado);
        assertEquals(original.getFecha(), rectificado.getFecha());
        assertEquals(original.getMediadorResponsable(), rectificado.getMediadorResponsable());
    }



    /**
     * Prueba 3 obligatoria: cálculo del ICD de SuegraOmnipresente_SGR26G05 (RF-09).
     */
    @Test
    public void calcularICDSuegraOmnipresenteConDatosConocidos() throws Exception {
        SuegraOmnipresente_SGR26G05 suegra =
                new SuegraOmnipresente_SGR26G05("SG-T1", "Doña Prueba", 70, 15, 50);
        assertEquals(0.0, suegra.calcularICD(), 0.001);

        LocalDate hoy = LocalDate.of(2026, 6, 1);
        ConflictoDomestico_SGR26G05 c1 = new ConflictoDomestico_SGR26G05("C1", hoy, "gastronomico", 4, "D1");
        ConflictoDomestico_SGR26G05 c2 = new ConflictoDomestico_SGR26G05("C2", hoy, "territorial", 6, "D2");
        ConflictoDomestico_SGR26G05 c3 = new ConflictoDomestico_SGR26G05("C3", hoy, "financiero", 8, "D3");
        c1.transicionarA(EstadoConflicto_SGR26G05.activo, hoy, false);
        c2.transicionarA(EstadoConflicto_SGR26G05.activo, hoy, false);
        c3.transicionarA(EstadoConflicto_SGR26G05.activo, hoy, false);

        suegra.registrarConflicto(c1);
        suegra.registrarConflicto(c2);
        suegra.registrarConflicto(c3);
        assertEquals(6.0, suegra.calcularICD(hoy), 0.001);

        ConflictoDomestico_SGR26G05 c4 = new ConflictoDomestico_SGR26G05("C4", hoy, "ceremonial", 6, "D4");
        c4.transicionarA(EstadoConflicto_SGR26G05.activo, hoy, false);
        c4.transicionarA(EstadoConflicto_SGR26G05.cronico, hoy, false);
        c4.registrarReincidenciaHistorica(hoy.minusMonths(2));
        suegra.registrarConflicto(c4);

        assertEquals(6.6, suegra.calcularICD(hoy), 0.001);
    }



    /**
     * Prueba 4 obligatoria: transiciones válidas e inválidas de ConflictoDomestico_SGR26G05 (RF-07, RF-05).
     */
    @Test
    public void verificarTransicionesValidasEInvalidasConflicto() throws Exception {
        ConflictoDomestico_SGR26G05 conflicto = new ConflictoDomestico_SGR26G05(
                "CF-EST", LocalDate.of(2026, 3, 15), "tecnologico", 7, "Grupo de chat");

        assertThrows(IllegalArgumentException.class, () -> conflicto.setIntensidad(11));
        assertThrows(
                TransicionInvalidaExcepcion_SGR26G05.class,
                () -> conflicto.transicionarA(EstadoConflicto_SGR26G05.resuelto));
        assertEquals(EstadoConflicto_SGR26G05.latente, conflicto.getEstado());

        conflicto.transicionarA(EstadoConflicto_SGR26G05.activo);
        conflicto.transicionarA(EstadoConflicto_SGR26G05.en_mediacion);
        conflicto.transicionarA(EstadoConflicto_SGR26G05.resuelto);
        assertEquals(EstadoConflicto_SGR26G05.resuelto, conflicto.getEstado());

        assertThrows(
                TransicionInvalidaExcepcion_SGR26G05.class,
                () -> conflicto.transicionarA(EstadoConflicto_SGR26G05.cronico));
        assertEquals(EstadoConflicto_SGR26G05.resuelto, conflicto.getEstado());
    }



    /**
     * Prueba 5 obligatoria: decremento y recuperación de nivelResiliencia en Yerno_SGR26G05 (RF-10).
     */
    @Test
    public void verificarDecrementoYRecuperacionResilienciaYerno() {
        Yerno_SGR26G05 yerno = new Yerno_SGR26G05("YN-01", "Carlos Yerno", 32, 4, 50, 12);

        yerno.registrarConflictoActivo();
        assertEquals(7, yerno.getNivelResiliencia());
        assertFalse(yerno.isFugaDomestica());

        yerno.registrarConflictoActivo();
        assertEquals(2, yerno.getNivelResiliencia());

        yerno.registrarConflictoActivo();
        assertEquals(0, yerno.getNivelResiliencia());
        assertTrue(yerno.isFugaDomestica());
        assertThrows(IllegalStateException.class, yerno::registrarConflictoActivo);

        yerno.registrarConflictoResuelto();
        assertEquals(10, yerno.getNivelResiliencia());
        assertTrue(yerno.isFugaDomestica());

        yerno.registrarConflictoResuelto();
        assertEquals(20, yerno.getNivelResiliencia());
        assertFalse(yerno.isFugaDomestica());
    }



    /**
     * Verifica polimorfismo de suegras, validación de paciencia y Liskov (RF-01, RF-03, RD-4).
     */
    @Test
    public void verificarPolimorfismoSuegrasYPaciencia() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SuegraClasica_SGR26G05("SG-X", "Doña Invalida", 65, 20, 101));

        ArrayList<Suegra_SGR26G05> suegras = new ArrayList<>();
        suegras.add(new SuegraClasica_SGR26G05("S1", "Clásica", 65, 20, 60));
        suegras.add(new SuegraDigital_SGR26G05("S2", "Digital", 58, 10, 70));
        suegras.add(new SuegraFinanciera_SGR26G05("S3", "Financiera", 62, 15, 50));
        suegras.add(new SuegraOmnipresente_SGR26G05("S4", "Omnipresente", 72, 25, 40));

        for (int i = 0; i < suegras.size(); i++) {
            ActorFamiliar_SGR26G05 actor = suegras.get(i);
            assertNotNull(actor.describirRol());
            assertNotNull(suegras.get(i).ejecutarTacticaDomestica());
        }
    }



    /**
     * Verifica la composición de NucleoFamiliar_SGR26G05 (RF-04).
     */
    @Test
    public void verificarComposicionNucleoFamiliar() {
        SuegraDigital_SGR26G05 suegra = new SuegraDigital_SGR26G05("SG-N1", "Doña Blanca", 65, 10, 50);
        Nuera_SGR26G05 n1 = new Nuera_SGR26G05("N1", "Ana", 30, 3, 60, 60);
        Nuera_SGR26G05 n2 = new Nuera_SGR26G05("N2", "Luz", 28, 2, 70, 70);

        assertThrows(IllegalArgumentException.class, () -> new NucleoFamiliar_SGR26G05("NF0", null, List.of(n1)));
        assertThrows(IllegalArgumentException.class, () -> new NucleoFamiliar_SGR26G05("NF0", suegra, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new NucleoFamiliar_SGR26G05("NF5", suegra, List.of(
                new Yerno_SGR26G05("Y1", "Y1", 30, 1, 50, 50),
                new Yerno_SGR26G05("Y2", "Y2", 30, 1, 50, 50),
                new Yerno_SGR26G05("Y3", "Y3", 30, 1, 50, 50),
                new Yerno_SGR26G05("Y4", "Y4", 30, 1, 50, 50),
                new Yerno_SGR26G05("Y5", "Y5", 30, 1, 50, 50))));

        NucleoFamiliar_SGR26G05 nucleo = new NucleoFamiliar_SGR26G05("NF-OK", suegra, List.of(n1, n2));
        assertEquals(2, nucleo.getDescendientes().size());
    }



    /**
     * Verifica orden descendente y detección de empate en RankingToxicidad_SGR26G05 (RF-06).
     */
    @Test
    public void verificarRankingYEmpate() throws Exception {
        SuegraClasica_SGR26G05 s1 = new SuegraClasica_SGR26G05("S1", "Siete", 60, 10, 50);
        ConflictoDomestico_SGR26G05 c1 = new ConflictoDomestico_SGR26G05("C1", LocalDate.now(), "financiero", 7, "P1");
        c1.transicionarA(EstadoConflicto_SGR26G05.activo, LocalDate.now(), false);
        s1.registrarConflicto(c1);

        SuegraDigital_SGR26G05 s2 = new SuegraDigital_SGR26G05("S2", "NueveComaCinco", 60, 10, 50);
        ConflictoDomestico_SGR26G05 c2a = new ConflictoDomestico_SGR26G05("C2A", LocalDate.now(), "financiero", 9, "P2");
        ConflictoDomestico_SGR26G05 c2b = new ConflictoDomestico_SGR26G05("C2B", LocalDate.now(), "ceremonial", 10, "P3");
        c2a.transicionarA(EstadoConflicto_SGR26G05.activo, LocalDate.now(), false);
        c2b.transicionarA(EstadoConflicto_SGR26G05.activo, LocalDate.now(), false);
        s2.registrarConflicto(c2a);
        s2.registrarConflicto(c2b);

        RankingToxicidad_SGR26G05 ranking = new RankingToxicidad_SGR26G05(List.of(s1, s2));
        assertEquals(s2, ranking.determinarGanadora());

        SuegraClasica_SGR26G05 e1 = new SuegraClasica_SGR26G05("E1", "Empate1", 60, 10, 50);
        SuegraFinanciera_SGR26G05 e2 = new SuegraFinanciera_SGR26G05("E2", "Empate2", 60, 10, 50);
        ConflictoDomestico_SGR26G05 ce1 = new ConflictoDomestico_SGR26G05("CE1", LocalDate.now(), "financiero", 8, "E");
        ConflictoDomestico_SGR26G05 ce2 = new ConflictoDomestico_SGR26G05("CE2", LocalDate.now(), "financiero", 8, "E");
        ce1.transicionarA(EstadoConflicto_SGR26G05.activo, LocalDate.now(), false);
        ce2.transicionarA(EstadoConflicto_SGR26G05.activo, LocalDate.now(), false);
        e1.registrarConflicto(ce1);
        e2.registrarConflicto(ce2);

        RankingToxicidad_SGR26G05 empate = new RankingToxicidad_SGR26G05(List.of(e1, e2));
        assertTrue(empate.hayEmpatePrimerPuesto());
        assertNull(empate.determinarGanadora());
    }



    /**
     * Verifica Strategy en tiempo de ejecución y fallo de mediación (RF-08, RD-6).
     */
    @Test
    public void verificarStrategyDinamicoYMediacion() throws Exception {
        SistemaMediacion_SGR26G05 sistema = new SistemaMediacion_SGR26G05(new EstrategiaPorCategoria_SGR26G05());
        sistema.registrarMediador(new MediadorDiplomatico_SGR26G05("MD1", "Dip", 0.0));
        sistema.registrarMediador(new MediadorAutoritario_SGR26G05("MD2", "Aut", 1.0));

        ConflictoDomestico_SGR26G05 c1 = new ConflictoDomestico_SGR26G05(
                "CF-STR", LocalDate.now(), "ceremonial", 5, "Protocolo");
        c1.transicionarA(EstadoConflicto_SGR26G05.activo, LocalDate.now(), false);

        ResultadoMediacion_SGR26G05 fracaso = sistema.mediarConflicto(c1);
        assertEquals("fracaso", fracaso.getVeredicto());
        assertEquals(EstadoConflicto_SGR26G05.en_mediacion, c1.getEstado());

        sistema.setEstrategiaAsignacion(new EstrategiaMayorTasaExito_SGR26G05());
        ResultadoMediacion_SGR26G05 acuerdo = sistema.mediarConflicto(c1);
        assertEquals("acuerdo", acuerdo.getVeredicto());
        assertEquals(EstadoConflicto_SGR26G05.resuelto, c1.getEstado());
    }



    /**
     * Verifica permisos por rol en ProxyAccesoMAF_SGR26G05 (RD-2).
     */
    @Test
    public void verificarProxyAccesoPorRol() throws Exception {
        SuegraClasica_SGR26G05 suegra = new SuegraClasica_SGR26G05("S1", "Doña Rosa", 65, 20, 50);
        SuegraDigital_SGR26G05 otraSuegra = new SuegraDigital_SGR26G05("S2", "Doña Clara", 60, 15, 60);
        Yerno_SGR26G05 yerno = new Yerno_SGR26G05("Y1", "Juan", 30, 5, 70, 70);
        Nuera_SGR26G05 otraNuera = new Nuera_SGR26G05("N2", "Luisa", 29, 4, 80, 80);
        NucleoFamiliar_SGR26G05 nucleo = new NucleoFamiliar_SGR26G05("NF1", suegra, List.of(yerno));
        NucleoFamiliar_SGR26G05 otroNucleo = new NucleoFamiliar_SGR26G05("NF2", otraSuegra, List.of(otraNuera));
        MediadorDiplomatico_SGR26G05 mediador = new MediadorDiplomatico_SGR26G05("M1", "Diplomático", 0.8);

        ConflictoDomestico_SGR26G05 cAsignado = new ConflictoDomestico_SGR26G05(
                "CF-A", LocalDate.now(), "ceremonial", 5, "Asignado");
        cAsignado.setMediadorAsignado(mediador);
        nucleo.registrarConflicto(cAsignado);

        RegistroNacional_SGR26G05 registro = new RegistroNacional_SGR26G05(
                List.of(suegra, otraSuegra),
                List.of(yerno, otraNuera),
                List.of(nucleo, otroNucleo),
                List.of(cAsignado),
                new SistemaMediacion_SGR26G05());
        ServicioConsultas_SGR26G05 proxy =
                new ProxyAccesoMAF_SGR26G05(new ServicioConsultasReal_SGR26G05(registro));

        assertDoesNotThrow(() -> proxy.consultarRanking(suegra));
        assertThrows(AccesoDenegadoExcepcion_SGR26G05.class, () -> proxy.consultarActoresEnFuga(suegra));
        assertThrows(AccesoDenegadoExcepcion_SGR26G05.class, () -> proxy.consultarRanking(yerno));
        assertDoesNotThrow(() -> proxy.consultarHistorialNucleo(yerno, nucleo));
        assertEquals(1, proxy.consultarConflictosAsignados(mediador).size());
        assertThrows(AccesoDenegadoExcepcion_SGR26G05.class,
                () -> proxy.consultarHistorialNucleo(mediador, otroNucleo));
    }



    /**
     * Verifica carga CSV completa, persistencia .dat y los 4 reportes de texto (RF-11, RF-12).
     */
    @Test
    public void verificarPersistenciaYReportesConCSV(@TempDir Path tempDir) throws Exception {
        File csv = CargadorDatosCSV_SGR26G05.localizarArchivoSemilla("datos_prueba_SGR26G05-03.csv");
        RegistroNacional_SGR26G05 cargado = CargadorDatosCSV_SGR26G05.cargarDesdeCSV(csv);
        assertEquals(10, cargado.getSuegras().size());
        assertEquals(8, cargado.getDescendientes().size());
        assertEquals(4, cargado.getNucleos().size());
        assertEquals(35, cargado.getConflictos().size());
        assertEquals(6, cargado.getSistemaMediacion().getHistorialResultados().size());

        File archivoDat = tempDir.resolve("estado.dat").toFile();
        Persistencia_SGR26G05.guardarSistema(cargado, archivoDat);
        RegistroNacional_SGR26G05 recargado = Persistencia_SGR26G05.cargarSistema(archivoDat);

        RankingToxicidad_SGR26G05 r1 = new RankingToxicidad_SGR26G05(cargado.getSuegras());
        RankingToxicidad_SGR26G05 r2 = new RankingToxicidad_SGR26G05(recargado.getSuegras());
        assertEquals(r1.determinarGanadora().getId(), r2.determinarGanadora().getId());

        GeneradorReportes_SGR26G05 reportes = new GeneradorReportes_SGR26G05(recargado);
        List<File> archivos = reportes.generarTodosLosReportes(tempDir.resolve("reportes").toFile());
        assertEquals(4, archivos.size());
        assertTrue(Files.readString(archivos.get(0).toPath(), StandardCharsets.UTF_8).contains("Dona Blanca"));
        assertTrue(Files.readString(archivos.get(1).toPath(), StandardCharsets.UTF_8).contains("CF001"));
        assertTrue(Files.readString(archivos.get(2).toPath(), StandardCharsets.UTF_8).contains("Diplomatico"));
        assertTrue(Files.readString(archivos.get(3).toPath(), StandardCharsets.UTF_8).contains("AF03"));
    }



    /**
     * Verifica el funcionamiento con 5000 suegras, 15000 descendientes y 100000 conflictos (RD-5).
     */
    @Test
    public void verificarEscalaMasiva() throws Exception {
        long inicio = System.currentTimeMillis();
        ArrayList<Suegra_SGR26G05> suegras = new ArrayList<>();
        LocalDate fecha = LocalDate.of(2026, 5, 1);

        for (int i = 0; i < 5000; i++) {
            SuegraDigital_SGR26G05 suegra = new SuegraDigital_SGR26G05("SG-" + i, "Suegra " + i, 60, 10, 50);
            ArrayList<DescendientePolitico_SGR26G05> desc = new ArrayList<>();
            desc.add(new Yerno_SGR26G05("Y-" + i + "-1", "Y1", 30, 2, 80, 80));
            desc.add(new Nuera_SGR26G05("N-" + i + "-2", "N2", 29, 2, 80, 80));
            desc.add(new Yerno_SGR26G05("Y-" + i + "-3", "Y3", 31, 3, 80, 80));
            NucleoFamiliar_SGR26G05 nucleo = new NucleoFamiliar_SGR26G05("NF-" + i, suegra, desc);
            for (int j = 0; j < 20; j++) {
                ConflictoDomestico_SGR26G05 c = new ConflictoDomestico_SGR26G05(
                        "CF-" + i + "-" + j, fecha, "gastronomico", (j % 10) + 1, "Escala");
                c.transicionarA(EstadoConflicto_SGR26G05.activo, fecha, false);
                nucleo.registrarConflicto(c);
            }
            suegras.add(suegra);
        }

        RankingToxicidad_SGR26G05 ranking = new RankingToxicidad_SGR26G05(suegras);
        long duracion = System.currentTimeMillis() - inicio;
        assertEquals(5000, ranking.getSuegrasOrdenadas().size());
        assertTrue(duracion < 5000);
    }
}
