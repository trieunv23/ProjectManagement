package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ProjectPreview;
import com.gui.projectmanagement.functions.Time;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProjectItemController implements Initializable {

    @FXML
    private Label budget;

    @FXML
    private Label creator;

    @FXML
    private Label start_date;

    @FXML
    private Label end_date;

    @FXML
    private Label status ;

    @FXML
    private Label manager;

    @FXML
    private Label member_cnt;

    @FXML
    private Label position;

    @FXML
    private Label status2 ;

    @FXML
    private Label task_cnt;

    @FXML
    private Label name_project ;

    @FXML
    private Circle status_circle ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setProject(ProjectPreview pp) {
        if (pp == null) {
            return;
        }
        name_project.setText(pp.getName());
        if (pp.getStatus().equals("In Progress")) {
            status_circle.setStyle("-fx-fill: #0000ff; -fx-stroke: transparent;");
        } else if (status.equals("Completed")) {
            status_circle.setStyle("-fx-fill: ##32cd32; -fx-stroke: transparent;");
        } else if (status.equals("Pause")) {
            status_circle.setStyle("-fx-fill: #808080; -fx-stroke: transparent;");
        }
        status.setText(pp.getStatus());
        position.setText(pp.getClient_role());
        start_date.setText("  " + pp.getStart_date());
        end_date.setText("  " + pp.getEnd_date());
        LocalDate end_date = Time.timeConversion(pp.getEnd_date()) ;
        if (end_date.isBefore(LocalDate.now())){
            status2.setText("  Delay");
        } else {
            status2.setText("  Normal");
        }
        // creator.setText(pp.getCreator().getFull_name());
        manager.setText(pp.getManager().getFull_name());
        member_cnt.setText(String.valueOf(pp.getNum_members()));
        task_cnt.setText(String.valueOf(pp.getNumber_tasks()));
        budget.setText(pp.getBudget());
    }


}
