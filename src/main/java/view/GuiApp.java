package view;

import controller.Controller;
import exception.MyException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.PrgState;
import model.adt.*;
import model.statement.IStmt;
import repository.IRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class GuiApp extends Application {

    @Override
    public void start(Stage stage) {

        List<IStmt> programs = new ArrayList<>();
        programs.add(Example.getEx1());
        programs.add(Example.getEx2());
        programs.add(Example.getEx3());
        programs.add(Example.getEx4());
        programs.add(Example.getEx5());
        programs.add(Example.getEx6());
        programs.add(Example.getEx7());
        programs.add(Example.getEx8());
        programs.add(Example.getEx9());
        programs.add(Example.getEx10());

        ListView<String> listView = new ListView<>();
        for (IStmt stmt : programs) {
            listView.getItems().add(stmt.toString());
        }

        listView.setOnMouseClicked(event -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index < 0) return;

            IStmt selectedProgram = programs.get(index);

            try {
                selectedProgram.typecheck(new MyDictionary<>());

                PrgState prg = new PrgState(
                        new MyStack<>(),
                        new MyDictionary<>(),
                        new MyList<>(),
                        new MyDictionary<>(),
                        new MyHeap(),
                        selectedProgram
                );

                IRepository repo = new Repository(prg, "log.txt");
                repo.addPrg(prg);
                Controller controller = new Controller(repo);

                new MainWindow(controller).show();

            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });

        BorderPane root = new BorderPane();
        root.setCenter(listView);

        stage.setTitle("Select a program");
        stage.setScene(new Scene(root, 800, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
