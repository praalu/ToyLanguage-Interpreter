package view;

import controller.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PrgState;
import model.statement.IStmt;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public class MainWindow {
    private TextField nrPrgState;
    private TableView<Map.Entry<Integer, IValue>> heapTable;
    private ListView<String> outView;
    private ListView<String> fileTableView;
    private ListView<Integer> prgStateIdView;
    private TableView<Map.Entry<String, IValue>> symTableView;
    private ListView<String> exeStackView;
    private Button runOneStepB;

    private Controller controller;

    public MainWindow(Controller controller) {
        this.controller = controller;
    }

    private void populateSymTable(PrgState prg) {
        symTableView.getItems().clear();
        Map<String, IValue> symTable = prg.getSymTable().getContent();
        symTableView.getItems().addAll(symTable.entrySet());
    }

    private void populateExeStack(PrgState prg) {
        exeStackView.getItems().clear();
        List<IStmt> stack = prg.getExeStack().getStack();
        for (int i = stack.size() - 1; i >= 0; i--)
            exeStackView.getItems().add(stack.get(i).toString());
    }

    private void refresh() {
        List<PrgState> prgList = controller.getRepo().getPrgList();
        nrPrgState.setText("Number of PrgState: " + prgList.size());

        heapTable.getItems().clear();
        if (!prgList.isEmpty()) {
            Map<Integer, IValue> heap = prgList.get(0).getHeapTable().getContent();
            heapTable.getItems().addAll(heap.entrySet());
        }

        outView.getItems().clear();
        if (!prgList.isEmpty()) {
            List<IValue> out = prgList.get(0).getOut().getList();
            for (IValue value : out)
                outView.getItems().add(value.toString());
        }

        fileTableView.getItems().clear();
        if (!prgList.isEmpty()) {
            Map<StringValue, BufferedReader> fileTable = prgList.get(0).getFileTable().getContent();
            for (StringValue fileName : fileTable.keySet())
                fileTableView.getItems().add(fileName.toString());
        }

        prgStateIdView.getItems().clear();
        for (PrgState prgState : prgList)
            prgStateIdView.getItems().add(prgState.getId());

        if (!prgList.isEmpty()) {
            // Selectam automat primul ID daca nu e nimic selectat sau pastram selectia curenta daca e valid
            if (prgStateIdView.getSelectionModel().getSelectedItem() == null) {
                prgStateIdView.getSelectionModel().select(0);
            }
            // Actualizam symTable si ExeStack pentru procesul selectat curent
            // Nota: Logica de aici e simplificata, ideal ar fi sa luam ID-ul selectat curent
            int id = prgStateIdView.getSelectionModel().getSelectedItem();
            PrgState currentPrg = null;
            for(PrgState prg : prgList){
                if(prg.getId() == id){
                    currentPrg = prg;
                    break;
                }
            }
            if(currentPrg != null){
                populateSymTable(currentPrg);
                populateExeStack(currentPrg);
            }
        }
    }

    public void show() {
        Stage stage = new Stage();

        // Layout principal vertical
        VBox mainLayout = new VBox();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setSpacing(10);

        // --- 1. TextField Numar Program States ---
        nrPrgState = new TextField();
        nrPrgState.setEditable(false);
        nrPrgState.setText("Number of PrgState: 0");

        // --- 2. Initializare Componente ---

        // Heap Table
        heapTable = new TableView<>();
        TableColumn<Map.Entry<Integer, IValue>, Integer> keyColHeap = new TableColumn<>("Address");
        keyColHeap.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey()));
        TableColumn<Map.Entry<Integer, IValue>, String> valueColHeap = new TableColumn<>("Value");
        valueColHeap.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().toString()));
        heapTable.getColumns().add(keyColHeap);
        heapTable.getColumns().add(valueColHeap);
        heapTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Out View
        outView = new ListView<>();

        // File Table View
        fileTableView = new ListView<>();

        // PrgState IDs
        prgStateIdView = new ListView<>();

        // Sym Table
        symTableView = new TableView<>();
        TableColumn<Map.Entry<String, IValue>, String> nameColSym = new TableColumn<>("Variable Name");
        nameColSym.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getKey()));
        TableColumn<Map.Entry<String, IValue>, String> valColSym = new TableColumn<>("Value");
        valColSym.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().toString()));
        symTableView.getColumns().add(nameColSym);
        symTableView.getColumns().add(valColSym);
        symTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Exe Stack
        exeStackView = new ListView<>();

        // Run Button
        runOneStepB = new Button("Run One Step");
        runOneStepB.setStyle("-fx-background-color: #953caf; -fx-text-fill: #e3afec; -fx-font-weight: bold;");

        // --- 3. Construire Layout (Asezare in pagina) ---

        // RANDUL DE SUS: Heap, Out, FileTable
        HBox topRow = new HBox();
        topRow.setSpacing(10);

        // Containere individuale cu Label pentru fiecare (VBox mic pentru fiecare element)
        VBox heapBox = new VBox(new Label("Heap Table"), heapTable);
        VBox outBox = new VBox(new Label("Output List"), outView);
        VBox fileBox = new VBox(new Label("File Table"), fileTableView);

        HBox.setHgrow(heapBox, Priority.ALWAYS);
        HBox.setHgrow(outBox, Priority.ALWAYS);
        HBox.setHgrow(fileBox, Priority.ALWAYS);

        topRow.getChildren().addAll(heapBox, outBox, fileBox);

        // RANDUL DE JOS: IDs, SymTable, ExeStack
        HBox bottomRow = new HBox();
        bottomRow.setSpacing(10);

        VBox idBox = new VBox(new Label("PrgState IDs"), prgStateIdView);
        VBox symBox = new VBox(new Label("Symbol Table"), symTableView);
        VBox exeBox = new VBox(new Label("Execution Stack"), exeStackView);

        HBox.setHgrow(idBox, Priority.ALWAYS);
        HBox.setHgrow(symBox, Priority.ALWAYS);
        HBox.setHgrow(exeBox, Priority.ALWAYS);

        bottomRow.getChildren().addAll(idBox, symBox, exeBox);


        mainLayout.getChildren().addAll(nrPrgState, topRow, bottomRow, runOneStepB);
        mainLayout.setAlignment(Pos.CENTER);

        VBox.setVgrow(topRow, Priority.ALWAYS);
        VBox.setVgrow(bottomRow, Priority.ALWAYS);


        prgStateIdView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldId, newId) -> {
                    if (newId == null) return;
                    List<PrgState> prgList = controller.getRepo().getPrgList();
                    for (PrgState prg : prgList)
                        if (prg.getId() == newId) {
                            populateSymTable(prg);
                            populateExeStack(prg);
                            break;
                        }
                }
        );

        runOneStepB.setOnAction(e -> {
            try {
                controller.oneStepForAllPrg();
                refresh();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.showAndWait();
            }
        });

        stage.setTitle("Toy Language Interpreter");
        stage.setScene(new Scene(mainLayout, 900, 700)); // Am marit putin fereastra default
        stage.show();

        refresh();
    }
}