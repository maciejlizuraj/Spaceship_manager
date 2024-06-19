package com.example.finalprojectnodatabase;

import com.almasb.fxgl.minigames.circuitbreaker.CircuitBreakerMiniGameKt;
import com.example.classes.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ManageRoute extends Application {

    public ManageRoute() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Ship ship = NoLifeSupportShip.constructor("Dawnbreaker", 10000, ShipType.NoProtection, null, "GLaDOS");
        Galaxy peacefulGalaxy1 = Galaxy.peacefulGalaxyConstructor("Galaxy A", "A", LocalDate.now());
        Galaxy peacefulGalaxy2 = Galaxy.peacefulGalaxyConstructor("Galaxy B", "BB", LocalDate.now());
        Galaxy peacefulGalaxy3 = Galaxy.peacefulGalaxyConstructor("Galaxy C", "CCC", LocalDate.now());
        Galaxy dangerousGalaxy1 = Galaxy.dangerousGalaxyConstructor("Galaxy D", "DDD", true, 5);
        Galaxy dangerousGalaxy2 = Galaxy.dangerousGalaxyConstructor("Galaxy E", "E-E", true, 5);
        Galaxy dangerousGalaxy3 = Galaxy.dangerousGalaxyConstructor("Galaxy F", "FF", true, 5);

        Cargo.addRegisteredOwner("Aperture");
        Cargo cargo1 = Cargo.constructor("T", 10, "Aperture");
        Cargo cargo2 = Cargo.constructor("T", 20, "Aperture");
        Cargo cargo3 = Cargo.constructor("T", 30, "Aperture");
        Cargo cargo4 = Cargo.constructor("T", 40, "Aperture");
        Cargo cargo5 = Cargo.constructor("T", 50, "Aperture");
        Cargo cargo6 = Cargo.constructor("T", 60, "Aperture");
        Cargo cargo7 = Cargo.constructor("T", 40, "Aperture");

        peacefulGalaxy1.addCargo(cargo1);
        peacefulGalaxy2.addCargo(cargo2);
        peacefulGalaxy3.addCargo(cargo3);
        dangerousGalaxy1.addCargo(cargo4);
        dangerousGalaxy2.addCargo(cargo5);
        dangerousGalaxy3.addCargo(cargo6);
        dangerousGalaxy3.addCargo(cargo7);

        ship.addCargo(cargo1);
        ship.addCargo(cargo2);
        ship.addCargo(cargo3);
        ship.addCargo(cargo4);
        ship.addCargo(cargo5);
        ship.addCargo(cargo6);
        ship.addCargo(cargo7);

        TextField currentTargetTextField = new TextField();
        currentTargetTextField.setEditable(false);
        currentTargetTextField.setText(getTargetText(ship));

        Button removeTargetButton = new Button("Remove current target");
        removeTargetButton.setOnAction(actionEvent -> {
            ship.setGalaxy(null);
            currentTargetTextField.setText(getTargetText(ship));
        });

        Map<Galaxy, Set<Cargo>> map = ship.getGalaxyToCargo();

        Set<Galaxy> reachableGalaxies = new HashSet<>();
        Set<Galaxy> unreachableGalaxies = new HashSet<>();

        for (Galaxy galaxy : map.keySet()) {
            if (ship.canGoToGalaxy(galaxy)) reachableGalaxies.add(galaxy);
            else unreachableGalaxies.add(galaxy);
        }

        // Create the data for the tables
        ObservableList<Galaxy> reachableCargoData = FXCollections.observableArrayList(reachableGalaxies);
        ObservableList<Galaxy> unreachableCargoData = FXCollections.observableArrayList(unreachableGalaxies);

        Button removeAllUnreachableButton = new Button("Remove all unreachable cargo");
        removeAllUnreachableButton.setOnAction(actionEvent -> {
            ship.unloadAllUnreachableCargo();
            unreachableCargoData.removeIf(galaxy -> !ship.canGoToGalaxy(galaxy));
        });

        // Reachable galaxies table
        TableView<Galaxy> reachableGalaxiesTable = new TableView<>();
        TableColumn<Galaxy, String> galaxyCodeColumn1 = new TableColumn<>("Code");
        galaxyCodeColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGalaxyCode()));

        TableColumn<Galaxy, String> numberOfCargoColumn1 = new TableColumn<>("# of cargo");
        numberOfCargoColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(getNumberOfCargo(map, cellData.getValue()))));

        TableColumn<Galaxy, String> totalMassColumn1 = new TableColumn<>("Total mass");
        totalMassColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(getTotalMassOfCargo(map, cellData.getValue()))));

        TableColumn<Galaxy, Void> setTargetColumn = new TableColumn<>("Set target");

        Callback<TableColumn<Galaxy, Void>, TableCell<Galaxy, Void>> reachableCellFactory = new Callback<>() {
            @Override
            public TableCell<Galaxy, Void> call(final TableColumn<Galaxy, Void> param) {
                final TableCell<Galaxy, Void> cell = new TableCell<Galaxy, Void>() {

                    private final Button btn = new Button("target");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Galaxy galaxy = getTableView().getItems().get(getIndex());
                            ship.setGalaxy(galaxy);
                            currentTargetTextField.setText(ship.getGalaxy().getGalaxyCode());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        setTargetColumn.setCellFactory(reachableCellFactory);

        reachableGalaxiesTable.getColumns().addAll(galaxyCodeColumn1, numberOfCargoColumn1, totalMassColumn1, setTargetColumn);
        reachableGalaxiesTable.setItems(reachableCargoData);
        reachableGalaxiesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Unreachable galaxies table
        TableView<Galaxy> unreachableGalaxiesTable = new TableView<>();
        TableColumn<Galaxy, String> galaxyCodeColumn2 = new TableColumn<>("Code");
        galaxyCodeColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGalaxyCode()));

        TableColumn<Galaxy, String> numberOfCargoColumn2 = new TableColumn<>("# of cargo");
        numberOfCargoColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(getNumberOfCargo(map, cellData.getValue()))));

        TableColumn<Galaxy, String> totalMassColumn2 = new TableColumn<>("Total mass");
        totalMassColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(getTotalMassOfCargo(map, cellData.getValue()))));

        TableColumn<Galaxy, Void> unloadCargo = new TableColumn<>("Unload cargo");

        Callback<TableColumn<Galaxy, Void>, TableCell<Galaxy, Void>> unreachableCellFactory = new Callback<>() {
            @Override
            public TableCell<Galaxy, Void> call(final TableColumn<Galaxy, Void> param) {
                final TableCell<Galaxy, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Unload");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Galaxy galaxy = getTableView().getItems().get(getIndex());
                            // Handle the delete action
                            getTableView().getItems().remove(galaxy);
                            ship.unloadCargoGoingTo(galaxy);
                            System.out.println(ship.getCargoSet());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        unloadCargo.setCellFactory(unreachableCellFactory);

        unreachableGalaxiesTable.getColumns().addAll(galaxyCodeColumn2, numberOfCargoColumn2, totalMassColumn2, unloadCargo);
        unreachableGalaxiesTable.setItems(unreachableCargoData);

        unreachableGalaxiesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        // Grid pane for layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(new Label("Reachable galaxies"), 2, 1);
        gridPane.add(reachableGalaxiesTable, 2, 2);
        gridPane.add(new Label("Unreachable galaxies"), 3, 1);
        gridPane.add(unreachableGalaxiesTable, 3, 2);
        gridPane.add(new Label("Current target:"), 0, 1);
        gridPane.add(currentTargetTextField, 1, 1);
        gridPane.add(removeTargetButton, 0, 2);
        gridPane.add(removeAllUnreachableButton,3,3);

        Scene scene = new Scene(gridPane, 1000, 600);
        primaryStage.setTitle("Manage " + ship.getName() + "'s Route and Cargo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getTargetText(Ship ship) {
        Galaxy galaxy = ship.getGalaxy();
        if (galaxy == null) return new String("No target");
        return galaxy.getGalaxyCode();
    }

    public int getNumberOfCargo(Map<Galaxy, Set<Cargo>> map, Galaxy galaxy) {
        return map.get(galaxy).size();
    }


    public int getTotalMassOfCargo(Map<Galaxy, Set<Cargo>> map, Galaxy galaxy) {
        int totalMass = 0;
        for (Cargo cargo : map.get(galaxy)) {
            totalMass += cargo.getMass();
        }
        return totalMass;
    }
}