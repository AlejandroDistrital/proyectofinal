// POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
package com.sgr.ui;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.sgr.actorFamiliar.descendientePolitico.DescendientePolitico_SGR26G05;
import com.sgr.actorFamiliar.suegra.Suegra_SGR26G05;
import com.sgr.excepciones.CategoriaInvalidaExcepcion_SGR26G05;
import com.sgr.excepciones.TransicionInvalidaExcepcion_SGR26G05;
import com.sgr.nucleoFamiliar.NucleoFamiliar_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.ConflictoDomestico_SGR26G05;
import com.sgr.nucleoFamiliar.conflictos.EstadoConflicto_SGR26G05;
import com.sgr.persistencia.CargadorDatosCSV_SGR26G05;
import com.sgr.persistencia.RegistroNacional_SGR26G05;
import com.sgr.ranking.RankingToxicidad_SGR26G05;

/**
 * Interfaz gráfica JavaFX simplificada conforme al requisito RNF-1:
 * ranking en un BarChart horizontal, diagrama de estados por conflicto con efecto fade
 * y dashboard con estadísticas por núcleo familiar.
 */
public class InterfazGrafica_SGR26G05 extends Application {

    private static final String ARCHIVO_SEMILLA = "datos_prueba_SGR26G05-03.csv";
    private static final double DURACION_FADE_MS = 600.0;

    private RegistroNacional_SGR26G05 registro;
    private BarChart<Number, String> graficoRanking;
    private Label etiquetaGanadora;
    private ComboBox<String> selectorConflicto;
    private ComboBox<EstadoConflicto_SGR26G05> selectorEstadoDestino;
    private Pane lienzoEstados;
    private Label etiquetaDetalleConflicto;
    private FlowPane contenedorNucleos;

    /**
     * Construye y muestra la ventana principal con las tres vistas requeridas en RNF-1.
     *
     * @param primaryStage ventana principal de JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        cargarDatosIniciales();

        TabPane pestanas = new TabPane(
                new Tab("Ranking (BarChart)", construirPanelRanking()),
                new Tab("Diagrama de Estados", construirPanelDiagramaEstados()),
                new Tab("Dashboard por Núcleo", construirPanelDashboard()));
        pestanas.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        BorderPane raiz = new BorderPane(pestanas);
        raiz.setPadding(new Insets(10));
        actualizarVistas(false);

        primaryStage.setTitle("MAF Suegristán — SGR26G05");
        primaryStage.setScene(new Scene(raiz, 980, 640));
        primaryStage.show();
    }



    /**
     * Inicia la aplicación JavaFX desde el método principal.
     *
     * @param args argumentos de línea de comandos
     */
    public static void lanzar(String[] args) {
        launch(args);
    }



    private void cargarDatosIniciales() {
        try {
            File archivoCsv = CargadorDatosCSV_SGR26G05.localizarArchivoSemilla(ARCHIVO_SEMILLA);
            this.registro = CargadorDatosCSV_SGR26G05.cargarDesdeCSV(archivoCsv);
        } catch (IOException | CategoriaInvalidaExcepcion_SGR26G05 | TransicionInvalidaExcepcion_SGR26G05 ex) {
            this.registro = new RegistroNacional_SGR26G05();
            mostrarError("Error de carga", ex.getMessage());
        }
    }



    private BorderPane construirPanelRanking() {
        etiquetaGanadora = new Label();
        etiquetaGanadora.setFont(Font.font("System", FontWeight.BOLD, 14));
        etiquetaGanadora.setPadding(new Insets(0, 0, 8, 0));

        NumberAxis ejeX = new NumberAxis();
        ejeX.setLabel("ICD");
        CategoryAxis ejeY = new CategoryAxis();
        ejeY.setLabel("Suegra");

        graficoRanking = new BarChart<>(ejeX, ejeY);
        graficoRanking.setTitle("Ranking de Toxicidad por ICD");
        graficoRanking.setLegendVisible(false);
        graficoRanking.setAnimated(false);

        BorderPane panel = new BorderPane(graficoRanking);
        panel.setTop(etiquetaGanadora);
        panel.setPadding(new Insets(10));
        return panel;
    }



    private BorderPane construirPanelDiagramaEstados() {
        selectorConflicto = new ComboBox<>();
        for (ConflictoDomestico_SGR26G05 conflicto : registro.getConflictos()) {
            selectorConflicto.getItems().add(conflicto.getId());
        }
        if (!selectorConflicto.getItems().isEmpty()) {
            selectorConflicto.getSelectionModel().selectFirst();
        }
        selectorConflicto.setOnAction(e -> dibujarDiagramaEstados(false));

        selectorEstadoDestino = new ComboBox<>();
        selectorEstadoDestino.getItems().addAll(EstadoConflicto_SGR26G05.values());
        selectorEstadoDestino.getSelectionModel().select(EstadoConflicto_SGR26G05.activo);

        Button btnTransicionar = new Button("Aplicar Transición");
        btnTransicionar.setOnAction(e -> aplicarTransicionConFade());

        HBox barra = new HBox(
                10,
                new Label("Conflicto:"),
                selectorConflicto,
                new Label("Nuevo estado:"),
                selectorEstadoDestino,
                btnTransicionar);
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setPadding(new Insets(0, 0, 10, 0));

        lienzoEstados = new Pane();
        lienzoEstados.setPrefSize(900, 340);
        lienzoEstados.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ced4da;");

        etiquetaDetalleConflicto = new Label();
        etiquetaDetalleConflicto.setPadding(new Insets(8, 0, 0, 0));

        BorderPane panel = new BorderPane(lienzoEstados);
        panel.setTop(barra);
        panel.setBottom(etiquetaDetalleConflicto);
        panel.setPadding(new Insets(10));
        return panel;
    }



    private ScrollPane construirPanelDashboard() {
        contenedorNucleos = new FlowPane(12, 12);
        contenedorNucleos.setPadding(new Insets(10));
        ScrollPane scroll = new ScrollPane(contenedorNucleos);
        scroll.setFitToWidth(true);
        return scroll;
    }



    private void actualizarVistas(boolean animarFade) {
        actualizarRankingChart();
        dibujarDiagramaEstados(animarFade);
        actualizarDashboardNucleos();
    }



    private void actualizarRankingChart() {
        RankingToxicidad_SGR26G05 ranking = new RankingToxicidad_SGR26G05(registro.getSuegras());
        ranking.determinarGanadora();
        etiquetaGanadora.setText(ranking.getMensajeResultado());

        XYChart.Series<Number, String> serie = new XYChart.Series<>();
        List<Suegra_SGR26G05> ordenadas = ranking.getSuegrasOrdenadas();
        for (int i = ordenadas.size() - 1; i >= 0; i--) {
            Suegra_SGR26G05 suegra = ordenadas.get(i);
            serie.getData().add(new XYChart.Data<>(ranking.obtenerICD(suegra), suegra.getNombre()));
        }
        graficoRanking.getData().clear();
        graficoRanking.getData().add(serie);
    }



    private void dibujarDiagramaEstados(boolean animarFade) {
        lienzoEstados.getChildren().clear();
        ConflictoDomestico_SGR26G05 conflicto = registro.buscarConflictoPorId(selectorConflicto.getValue());
        if (conflicto == null) {
            etiquetaDetalleConflicto.setText("No hay conflictos disponibles.");
            return;
        }

        agregarLinea(150, 150, 220, 150);
        agregarLinea(330, 150, 400, 150);
        agregarLinea(510, 130, 620, 95);
        agregarLinea(510, 170, 620, 210);
        agregarLinea(275, 180, 620, 210);

        EstadoConflicto_SGR26G05 actual = conflicto.getEstado();
        List<EstadoConflicto_SGR26G05> historial = conflicto.getHistorialEstados();
        Map<EstadoConflicto_SGR26G05, StackPane> nodos = new EnumMap<>(EstadoConflicto_SGR26G05.class);
        nodos.put(EstadoConflicto_SGR26G05.latente, crearNodo(EstadoConflicto_SGR26G05.latente, 40, 120, actual, historial));
        nodos.put(EstadoConflicto_SGR26G05.activo, crearNodo(EstadoConflicto_SGR26G05.activo, 220, 120, actual, historial));
        nodos.put(EstadoConflicto_SGR26G05.en_mediacion, crearNodo(EstadoConflicto_SGR26G05.en_mediacion, 400, 120, actual, historial));
        nodos.put(EstadoConflicto_SGR26G05.resuelto, crearNodo(EstadoConflicto_SGR26G05.resuelto, 620, 65, actual, historial));
        nodos.put(EstadoConflicto_SGR26G05.cronico, crearNodo(EstadoConflicto_SGR26G05.cronico, 620, 180, actual, historial));
        lienzoEstados.getChildren().addAll(nodos.values());

        if (animarFade && nodos.containsKey(actual)) {
            FadeTransition fade = new FadeTransition(Duration.millis(DURACION_FADE_MS), nodos.get(actual));
            fade.setFromValue(0.15);
            fade.setToValue(1.0);
            fade.setCycleCount(2);
            fade.setAutoReverse(true);
            fade.play();
        }

        etiquetaDetalleConflicto.setText(String.format(
                "%s | Categoría: %s | Intensidad: %d | Estado: %s | Historial: %s",
                conflicto.getId(),
                conflicto.getCategoria(),
                conflicto.getIntensidad(),
                conflicto.getEstado(),
                conflicto.getHistorialEstados()));
    }



    private void agregarLinea(double x1, double y1, double x2, double y2) {
        Line linea = new Line(x1, y1, x2, y2);
        linea.setStroke(Color.GRAY);
        linea.setStrokeWidth(2.0);
        lienzoEstados.getChildren().add(linea);
    }



    private StackPane crearNodo(
            EstadoConflicto_SGR26G05 estado,
            double x,
            double y,
            EstadoConflicto_SGR26G05 actual,
            List<EstadoConflicto_SGR26G05> historial) {
        Rectangle caja = new Rectangle(110, 56);
        caja.setArcWidth(12);
        caja.setArcHeight(12);
        caja.setStrokeWidth(2.0);
        boolean activo = estado == actual;
        boolean visitado = historial.contains(estado);
        caja.setFill(activo ? Color.web("#d9534f") : (visitado ? Color.web("#5bc0de") : Color.web("#e9ecef")));
        caja.setStroke(activo ? Color.DARKRED : (visitado ? Color.DARKBLUE : Color.GRAY));

        Label texto = new Label(estado.name());
        texto.setFont(Font.font("System", FontWeight.BOLD, 12));
        texto.setTextFill(activo || visitado ? Color.WHITE : Color.BLACK);

        StackPane nodo = new StackPane(caja, texto);
        nodo.setLayoutX(x);
        nodo.setLayoutY(y);
        return nodo;
    }



    private void actualizarDashboardNucleos() {
        contenedorNucleos.getChildren().clear();
        for (NucleoFamiliar_SGR26G05 nucleo : registro.getNucleos()) {
            Label titulo = new Label(nucleo.getIdNucleo() + " — " + nucleo.getSuegra().getNombre());
            titulo.setFont(Font.font("System", FontWeight.BOLD, 13));

            Label resumen = new Label(String.format(
                    "ICD suegra: %.2f | ICD núcleo: %.2f | Activos: %d | En mediación: %d | Resueltos: %d | Crónicos: %d",
                    nucleo.getSuegra().calcularICD(),
                    nucleo.calcularICDAgregado(),
                    nucleo.contarConflictosPorEstado(EstadoConflicto_SGR26G05.activo),
                    nucleo.contarConflictosPorEstado(EstadoConflicto_SGR26G05.en_mediacion),
                    nucleo.contarConflictosPorEstado(EstadoConflicto_SGR26G05.resuelto),
                    nucleo.contarConflictosPorEstado(EstadoConflicto_SGR26G05.cronico)));
            resumen.setWrapText(true);

            VBox tarjeta = new VBox(5, titulo, resumen);
            tarjeta.setPrefWidth(300);
            tarjeta.setPadding(new Insets(10));
            tarjeta.setStyle("-fx-background-color: white; -fx-border-color: #adb5bd; -fx-border-radius: 6;");
            for (DescendientePolitico_SGR26G05 d : nucleo.getDescendientes()) {
                String estadoFuga = d.isFugaDomestica() ? " [FUGA]" : "";
                tarjeta.getChildren().add(new Label(
                        d.getNombre() + " (" + d.getParentesco() + ") — Resiliencia: "
                                + d.getNivelResiliencia() + estadoFuga));
            }
            contenedorNucleos.getChildren().add(tarjeta);
        }
    }



    private void aplicarTransicionConFade() {
        ConflictoDomestico_SGR26G05 conflicto = registro.buscarConflictoPorId(selectorConflicto.getValue());
        EstadoConflicto_SGR26G05 destino = selectorEstadoDestino.getValue();
        if (conflicto == null || destino == null) {
            return;
        }
        try {
            conflicto.transicionarA(destino);
            actualizarVistas(true);
        } catch (TransicionInvalidaExcepcion_SGR26G05 ex) {
            mostrarError("Transición inválida (RF-07)", ex.getMessage());
        }
    }



    private void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
