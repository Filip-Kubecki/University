package com.example.systemyoperacyjnezadanie2;

import Backend.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controler implements Initializable{
    ArrayList<Request> preGeneratedData;
    boolean preGeneratedDataState = false;
    int deadlinePercentage = 20;//Zakres od 0 do 100

    @FXML LineChart<Number, Number> dataChart;
    @FXML TextField discSize;
    @FXML TextField requestSize;

    @FXML Label totalTime;
    @FXML Label allHeadMove;
    @FXML Label killedRequests;
    @FXML CheckBox preGenerate;

    @FXML TableView<tableData> dataTable;
    @FXML TableColumn<tableData, String> algName;
    @FXML TableColumn<tableData, Integer> allHeadMoves;

    @FXML
    private  void preGenerateData(ActionEvent event){
        int zgloszeniaSize = Integer.parseInt(requestSize.getText());
        int DiscSize = Integer.parseInt(discSize.getText());
        if (preGenerate.isSelected()){
            preGeneratedDataState = true;
            preGeneratedata(zgloszeniaSize, DiscSize);

            tableData FCFS = new tableData("FCFS",Algorytmy.FCFS(preGeneratedData, preGeneratedData.size(), DiscSize).getAllHeadMovements());
            tableData SSTF = new tableData("SSTF",Algorytmy.SSTF(preGeneratedData, preGeneratedData.size(), DiscSize).getAllHeadMovements());
            tableData SCAN = new tableData("SCAN",Algorytmy.SCAN(preGeneratedData, preGeneratedData.size(), DiscSize).getAllHeadMovements());
            tableData CSCAN = new tableData("CSCAN",Algorytmy.CSCAN(preGeneratedData, preGeneratedData.size(), DiscSize).getAllHeadMovements());
            tableData EDF = new tableData("EDF",Algorytmy.EDF(preGeneratedData, preGeneratedData.size(), DiscSize).getAllHeadMovements());
            tableData FDSCAN = new tableData("FDSCAN",Algorytmy.FD_SCAN(preGeneratedData, preGeneratedData.size(), DiscSize).getAllHeadMovements());
            ObservableList<tableData> tableDatas = dataTable.getItems();
            tableDatas.clear();
            tableDatas.addAll(FCFS,SSTF,SCAN,CSCAN,EDF,FDSCAN);
            dataTable.setItems(tableDatas);
        }else{
            preGeneratedDataState = false;
        }
    }
    @FXML
    private void FCFS(ActionEvent event) {
        dataChart.getData().clear();
        int zgloszeniaSize = Integer.parseInt(requestSize.getText());
        int DiscSize = Integer.parseInt(discSize.getText());
        ArrayList<Request> zgloszenia;
        GraphData allData;
        ArrayList<TimeToHeadPosition> graphData;

        if (preGeneratedDataState){
            zgloszenia = preGeneratedData;
        }else{
            zgloszenia = Generator.generatorHybrydowy(zgloszeniaSize, DiscSize,80);
        }

        allData = Algorytmy.FCFS(zgloszenia, zgloszenia.size(), DiscSize);
        graphData = allData.getTimeToHeadPositionArray();

        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number,Number>();
        //populating the series with data
        for (int i = 0; i < zgloszeniaSize; i++) {
            series.getData().add(new XYChart.Data(graphData.get(i).getTime(),graphData.get(i).getHeadPosition()));
        }
        totalTime.setText(String.valueOf(allData.getTotalTime()));
        allHeadMove.setText(String.valueOf(allData.getAllHeadMovements()));
        killedRequests.setText(String.valueOf(allData.getAmountOfKilledRequest()));
        dataChart.getData().add(series);
    }
    @FXML
    private void SSTF(ActionEvent event) {
        dataChart.getData().clear();
        int zgloszeniaSize = Integer.parseInt(requestSize.getText());
        int DiscSize = Integer.parseInt(discSize.getText());
        ArrayList<Request> zgloszenia;
        GraphData allData;
        ArrayList<TimeToHeadPosition> graphData;

        if (preGeneratedDataState){
            zgloszenia = preGeneratedData;
        }else{
            zgloszenia = Generator.generatorHybrydowy(zgloszeniaSize, DiscSize,80);
        }

        allData = Algorytmy.SSTF(zgloszenia, zgloszenia.size(), DiscSize);
        graphData = allData.getTimeToHeadPositionArray();

        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number,Number>();
        //populating the series with data
        for (int i = 0; i < zgloszeniaSize; i++) {
            series.getData().add(new XYChart.Data(graphData.get(i).getTime(),graphData.get(i).getHeadPosition()));
        }
        totalTime.setText(String.valueOf(allData.getTotalTime()));
        allHeadMove.setText(String.valueOf(allData.getAllHeadMovements()));
        killedRequests.setText(String.valueOf(allData.getAmountOfKilledRequest()));
        dataChart.getData().add(series);
    }

    @FXML
    private void SCAN(ActionEvent event) {
        dataChart.getData().clear();
        int zgloszeniaSize = Integer.parseInt(requestSize.getText());
        int DiscSize = Integer.parseInt(discSize.getText());
        ArrayList<Request> zgloszenia;
        GraphData allData;
        ArrayList<TimeToHeadPosition> graphData;

        if (preGeneratedDataState){
            zgloszenia = preGeneratedData;
        }else{
            zgloszenia = Generator.generatorHybrydowy(zgloszeniaSize, DiscSize,80);
        }

        allData = Algorytmy.SCAN(zgloszenia, zgloszenia.size(), DiscSize);
        graphData = allData.getTimeToHeadPositionArray();

        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number,Number>();
        //populating the series with data
        for (int i = 0; i < zgloszeniaSize; i++) {
            series.getData().add(new XYChart.Data(graphData.get(i).getTime(),graphData.get(i).getHeadPosition()));
        }
        totalTime.setText(String.valueOf(allData.getTotalTime()));
        allHeadMove.setText(String.valueOf(allData.getAllHeadMovements()));
        killedRequests.setText(String.valueOf(allData.getAmountOfKilledRequest()));
        dataChart.getData().add(series);
    }

    @FXML
    private void CSCAN(ActionEvent event) {
        dataChart.getData().clear();
        int zgloszeniaSize = Integer.parseInt(requestSize.getText());
        int DiscSize = Integer.parseInt(discSize.getText());
        ArrayList<Request> zgloszenia;
        GraphData allData;
        ArrayList<TimeToHeadPosition> graphData;

        if (preGeneratedDataState){
            zgloszenia = preGeneratedData;
        }else{
            zgloszenia = Generator.generatorHybrydowy(zgloszeniaSize, DiscSize,80);
        }

        allData = Algorytmy.CSCAN(zgloszenia, zgloszenia.size(), DiscSize);
        graphData = allData.getTimeToHeadPositionArray();

        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number,Number>();
        //populating the series with data
        for (int i = 0; i < zgloszeniaSize; i++) {
            series.getData().add(new XYChart.Data(graphData.get(i).getTime(),graphData.get(i).getHeadPosition()));
        }
        totalTime.setText(String.valueOf(allData.getTotalTime()));
        allHeadMove.setText(String.valueOf(allData.getAllHeadMovements()));
        killedRequests.setText(String.valueOf(allData.getAmountOfKilledRequest()));
        dataChart.getData().add(series);
    }

    @FXML
    private void EDF(ActionEvent event) {
        dataChart.getData().clear();
        int zgloszeniaSize = Integer.parseInt(requestSize.getText());
        int DiscSize = Integer.parseInt(discSize.getText());
        ArrayList<Request> zgloszenia;
        GraphData allData;
        ArrayList<TimeToHeadPosition> graphData;

        if (preGeneratedDataState){
            zgloszenia = preGeneratedData;
        }else{
            zgloszenia = Generator.generatorHybrydowy(zgloszeniaSize, DiscSize,80);
        }

        allData = Algorytmy.EDF(zgloszenia, zgloszenia.size(), DiscSize);
        graphData = allData.getTimeToHeadPositionArray();

        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number,Number>();
        //populating the series with data
        for (int i = 0; i < zgloszeniaSize-allData.getAmountOfKilledRequest(); i++) {
            series.getData().add(new XYChart.Data(graphData.get(i).getTime(),graphData.get(i).getHeadPosition()));
        }
        totalTime.setText(String.valueOf(allData.getTotalTime()));
        allHeadMove.setText(String.valueOf(allData.getAllHeadMovements()));
        killedRequests.setText(String.valueOf(allData.getAmountOfKilledRequest()));
        dataChart.getData().add(series);
    }

    @FXML
    private void FDSCAN(ActionEvent event) {
        dataChart.getData().clear();
        int zgloszeniaSize = Integer.parseInt(requestSize.getText());
        int DiscSize = Integer.parseInt(discSize.getText());
        ArrayList<Request> zgloszenia;
        GraphData allData;
        ArrayList<TimeToHeadPosition> graphData;

        if (preGeneratedDataState){
            zgloszenia = preGeneratedData;
        }else{
            zgloszenia = Generator.generatorHybrydowy(zgloszeniaSize, DiscSize,80);
        }

        allData = Algorytmy.FD_SCAN(zgloszenia, zgloszenia.size(), DiscSize);
        graphData = allData.getTimeToHeadPositionArray();

        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number,Number>();
        //populating the series with data
        for (int i = 0; i < zgloszeniaSize-allData.getAmountOfKilledRequest(); i++) {
            series.getData().add(new XYChart.Data(graphData.get(i).getTime(),graphData.get(i).getHeadPosition()));
        }

        totalTime.setText(String.valueOf(allData.getTotalTime()));
        allHeadMove.setText(String.valueOf(allData.getAllHeadMovements()));
        killedRequests.setText(String.valueOf(allData.getAmountOfKilledRequest()));
        dataChart.getData().add(series);
    }
    @FXML
    private void exit(ActionEvent event){
        Platform.exit();
    }

    public void preGeneratedata(int zgloszeniaSize,int DiscSize){
        preGeneratedData = Generator.generatorHybrydowy(zgloszeniaSize, DiscSize,80);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algName.setCellValueFactory(new PropertyValueFactory<tableData, String>("algorythmName"));
        allHeadMoves.setCellValueFactory(new PropertyValueFactory<tableData, Integer>("allHeadMovements"));
    }

}
