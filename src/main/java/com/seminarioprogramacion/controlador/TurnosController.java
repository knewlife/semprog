package com.seminarioprogramacion.controlador;

import com.seminarioprogramacion.dto.EspecialidadDTO;
import com.seminarioprogramacion.dto.MecanicoDTO;
import com.seminarioprogramacion.dto.ServicioDTO;
import com.seminarioprogramacion.dto.TitularDTO;
import com.seminarioprogramacion.dto.TurnoDTO;
import com.seminarioprogramacion.dto.VehiculoDTO;
import com.seminarioprogramacion.main.App;
import com.seminarioprogramacion.modelo.Especialidad;
import com.seminarioprogramacion.modelo.Mecanico;
import com.seminarioprogramacion.modelo.Turno;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ikukl
 */
public class TurnosController implements Initializable {

    @FXML
    private Button btnAsignarTurno;
    @FXML
    private Button btnEliminarTurno;
    @FXML
    private Button brnAsistencia;
    @FXML
    private Button btnFichaMecanica;
    @FXML
    private Button btnImprimirComprobante;
    @FXML
    private Button bttnFiltrar;
    @FXML
    private ComboBox combobox_especialidades;
    @FXML
    private ComboBox combobox_mecanicos;
    @FXML
    private TableView<TurnoDTO> tbViewTurnos;

    @FXML
    private TableColumn<TurnoDTO, String> colAsistencia;

    @FXML
    private TableColumn<TurnoDTO, Date> colFecha;

    @FXML
    private TableColumn<TurnoDTO, LocalTime> colHorario;

    @FXML
    private TableColumn<MecanicoDTO, String> colMecanico;//Ver porque a lo mejor es Turno como primer parámetro

    @FXML
    private TableColumn<ServicioDTO, String> colServicio;

    @FXML
    private TableColumn<TitularDTO, String> colTitular;

    @FXML
    private TableColumn<VehiculoDTO, String> colVehiculo;

    @FXML
    private DatePicker dtpFecha;
    @FXML
    private Button btnlimpiar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Especialidad
            Especialidad especialidad = new Especialidad();
            List<EspecialidadDTO> especialidades = especialidad.listar();
            combobox_especialidades.getItems().addAll(especialidades);

            //Mecanicos
            Mecanico mecanico = new Mecanico();
            List<MecanicoDTO> mecanicos = mecanico.listar();
            combobox_mecanicos.getItems().addAll(mecanicos);

            //Listar valores
            bttnFiltrar_OnClick();

            //codigo para probar turno en consola
            /*
        Turno turno = new Turno();
        LocalDate fecha = LocalDate.now();
        MecanicoDTO meca = mecanicos.get(0);
        List<TurnoDTO> lista1 = turno.listar(meca,fecha);
        
        for(TurnoDTO turno1: lista1){
            System.out.print(turno1.getDia_atencion());
            System.out.println(turno1.getHora_atencion());
            System.out.println(turno1.getAsistencia());
            System.out.println(turno1.getMecanico());
        }
        
        //codigo para probar turno por mecanico en consola
        List<TurnoDTO> lista2 = turno.listarPorMecanico(2); //id 2
        for(TurnoDTO turno2: lista2){
            System.out.print(turno2.getDia_atencion());
            System.out.println(turno2.getHora_atencion());
            System.out.println(turno2.getAsistencia());
        }   
             */
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btnlimpiar_OnClick() throws IOException {
        dtpFecha.setValue(null);
        combobox_mecanicos.setValue(null);
        //combobox_especialidades.setValue(null);
        bttnFiltrar_OnClick();

    }

    @FXML
    private void bttnFiltrar_OnClick() throws IOException {
        tbViewTurnos.getItems().clear(); //Limpia tableView  
        MecanicoDTO mecanico = (MecanicoDTO) combobox_mecanicos.getSelectionModel().getSelectedItem();
        LocalDate fechaDtp = dtpFecha.getValue();

        Turno turno = new Turno(); //Modelo
        List<TurnoDTO> turnos;
        if (mecanico != null) {
            if (fechaDtp != null) {
                turnos = turno.listar(mecanico, fechaDtp);
            } else {
                turnos = turno.listar(mecanico);
            }
        } else {
            if (fechaDtp != null) {
                turnos = turno.listar(fechaDtp);
            } else {
                turnos = turno.listar();
            }
        }

        colFecha.setCellValueFactory(new PropertyValueFactory<>("dia_atencion"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("hora_atencion"));
        colAsistencia.setCellValueFactory(t -> {
            boolean vf = t.getValue().getAsistencia();
            String valor = vf ? "SI" : "NO";
            return new ReadOnlyStringWrapper(valor);
        });
        colMecanico.setCellValueFactory(new PropertyValueFactory<>("Mecanico"));
        colServicio.setCellValueFactory(new PropertyValueFactory<>("Servicio"));
        colTitular.setCellValueFactory(new PropertyValueFactory<>("Titular"));
        colVehiculo.setCellValueFactory(new PropertyValueFactory<>("Vehiculo"));
        ObservableList<TurnoDTO> listObsevable = FXCollections.observableList(turnos);
        tbViewTurnos.setItems(listObsevable);
        //dtpFecha.setValue(null);
    }

    @FXML
    private void combobox_especialidad_change() throws IOException {
        EspecialidadDTO especilidad = (EspecialidadDTO) combobox_especialidades.getSelectionModel().getSelectedItem();

        combobox_mecanicos.getItems().clear();
        //Para la lista de titulares
        Mecanico mecanico = new Mecanico(); //Modelo
        List<MecanicoDTO> mecanicos = mecanico.listar(especilidad); //Listar mecanicos por especialidad 
        combobox_mecanicos.getItems().addAll(mecanicos); //Llena combobox       
    }

    @FXML
    private void switchToAsignarTurno() throws IOException {
        //App.setRoot("testui",400,600);

        //Cerrar esta ventana
        //((Stage) menup.getScene().getWindow()).close();
        Stage wd = (Stage) btnlimpiar.getScene().getWindow();

        App.newWindow("AsignarTurno", ((Stage) btnAsignarTurno.getScene().getWindow()), "Asignar Turno");

        wd.close();
        wd.show();
        btnlimpiar_OnClick();
    }

    @FXML
    private void eliminarTurno(ActionEvent event) {

        String mensaje = "¿Está seguro que quiere eliminar este Turno?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Turno turno = new Turno();

                TablePosition pos = tbViewTurnos.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                // Item here is the table view type:
                TurnoDTO item = tbViewTurnos.getItems().get(row);

                if (item != null) {
                    if (!turno.borrar(item.getId_turno())) {

                        Alert alert2 = new Alert(AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText("Error al eliminar");
                        alert2.setContentText("Ocurrio un error al eliminar.");
                        alert2.showAndWait();

                    } else {
                        try {
                            bttnFiltrar_OnClick();
                        } catch (IOException ex) {
                            Logger.getLogger(TurnosController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        });
    }

    @FXML
    private void confirmarAsistencia(ActionEvent event) {
        String mensaje = "¿El titular asistió al turno seleccionado?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(response -> {
            if (response != ButtonType.CANCEL) {
                
                boolean asistencia = (response == ButtonType.YES);
                
                Turno turno = new Turno();

                TablePosition pos = tbViewTurnos.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                // Item here is the table view type:
                TurnoDTO item = tbViewTurnos.getItems().get(row);

                if (item != null) {
                    if (!turno.modificarAsistencia(item.getId_turno(),asistencia)) {

                        Alert alert2 = new Alert(AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText("Error al actualizar");
                        alert2.setContentText("Ocurrio un error al actualizar.");
                        alert2.showAndWait();

                    } else {
                        try {
                            bttnFiltrar_OnClick();
                        } catch (IOException ex) {
                            Logger.getLogger(TurnosController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        }
        );
    }

    @FXML
    private void imprimirComprobante(ActionEvent event) throws IOException {
        String mensaje = "¿Desea imprimir el comprobante?";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
    }

    @FXML
    private void imprimirFichaMecanica(ActionEvent event) throws IOException {
        String mensaje = "¿Desea imprimir la ficha mecánica?";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
    }
}
