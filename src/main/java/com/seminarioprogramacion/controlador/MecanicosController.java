/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminarioprogramacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Win10
 */
public class MecanicosController {

    @FXML
    private Button btnguardar;
    @FXML
    private Button btncancelar;
    

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

    @FXML
    private void cancelar() throws IOException {
        //Cerrar esta ventana
        ((Stage) btncancelar.getScene().getWindow()).close();
    }
    
    
    @FXML
    private void guardar() throws IOException {
        //Cerrar esta ventana
        ((Stage) btnguardar.getScene().getWindow()).close();
    }
    

}
