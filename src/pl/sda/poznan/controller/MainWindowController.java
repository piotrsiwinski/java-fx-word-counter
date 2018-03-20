package pl.sda.poznan.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class MainWindowController {

  @FXML
  private BorderPane mainBorderPane;

  @FXML
  private TextArea resultTextArea;

  public void readFromFile(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    File choosedFile = fileChooser.showOpenDialog(mainBorderPane.getScene().getWindow());

    try {
      List<String> strings = Files.readAllLines(Paths.get(choosedFile.getAbsolutePath()));
      Map<String, Long> collect = strings.stream()
          .flatMap(line -> Stream.of(line.split("\\s|\\.|\\, |\\. ")))
          .map(String::toLowerCase)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      StringBuilder sb = new StringBuilder();
      collect.forEach((k, v) -> sb.append(k).append("----->").append(v).append("\n"));
      resultTextArea.setText(sb.toString());

    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("File not found");
      alert.setContentText("File doesn't exists. Choose correct file");
      alert.showAndWait();
    }

  }
}
