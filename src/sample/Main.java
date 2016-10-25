package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Pane battle = new Pane();
        Scene scene = new Scene(battle, 550, 430);

        Text message = new Text("Your message");
        message.setLayoutX(200);
        message.setLayoutY(20);
        message.setFill(Color.FIREBRICK);
        message.setFont(Font.font("Arial", FontPosture.ITALIC, 16));

        Text message_out = new Text();
        message_out.setLayoutX(160);
        message_out.setLayoutY(235);
        message_out.setFill(Color.FIREBRICK);
        message_out.setFont(Font.font("Arial", FontPosture.ITALIC, 16));

        TextField str = new TextField();
        str.setLayoutX(0);
        str.setLayoutY(30);
        str.setPrefWidth(550);
        str.setPrefHeight(120);

        Text str_out = new Text();
        str_out.setLayoutX(10);
        str_out.setLayoutY(255);
        str_out.setFill(Color.FIREBRICK);
        str_out.setFont(Font.font("Arial", FontPosture.ITALIC, 14));

        Button Ok = new Button();
        Ok.setText("Ok");
        Ok.setPrefWidth(70);
        Ok.setPrefHeight(30);
        Ok.setLayoutX(290);
        Ok.setLayoutY(170);

        Button Clear = new Button();
        Clear.setText("Delete");
        Clear.prefHeight(30);
        Clear.setPrefWidth(70);
        Clear.setLayoutX(140);
        Clear.setLayoutY(170);

        Button Exit = new Button();
        Exit.setText("Exit");
        Exit.setPrefWidth(70);
        Exit.setPrefHeight(30);
        Exit.setLayoutX(240);
        Exit.setLayoutY(390);

        Clear.setOnAction(event->{
            str.clear();
        });

        Exit.setOnAction(event->{
            primaryStage.close();
        });

        Ok.setOnAction(event->{
            String mes;
            mes = str.getText();

            byte[] valuesDefault = mes.getBytes();
            int size = valuesDefault.length + (valuesDefault.length)*3 + 2;
            byte [] changeDefault = new byte[size];

            changeDefault[0] = 126;
            int data_size = 0;
            int num_element = 1;
            for(int i =0; i < valuesDefault.length; num_element++, i++) {

                if (valuesDefault[i] == 125 || valuesDefault[i] == 126) {
                        changeDefault[num_element] = 125;
                        num_element = num_element + 1;
                        changeDefault[num_element] = valuesDefault[i];
                        if (data_size == 5) {
                            num_element = num_element+1;
                            changeDefault[num_element] = 126;
                            num_element = num_element + 1;
                            changeDefault[num_element] = 126;
                            data_size = 0;
                        }
                } else {
                    if (data_size == 5) {
                        changeDefault[num_element] = 126;
                        num_element = num_element + 1;
                        changeDefault[num_element] = 126;
                        data_size = 0;
                        i = i - 1;
                    } else {
                        data_size++;
                        changeDefault[num_element] = valuesDefault[i];
                    }
                }
            }

            changeDefault[num_element] = 126;
            num_element = num_element + 1;

            byte [] processed_byte = new byte[num_element];
            int packages = 1;

            for(int i = 0; i < num_element; i++)
            {
                processed_byte[i] = changeDefault[i];
                int a = i - 1;
                if(changeDefault[i] == 126 ) {
                    if (a >= 0) {
                        if (changeDefault[a] != 125) {
                            packages++;
                        }
                    }
                }
            }

            message_out.setText("Output data");
            String result;
            result = "Message in Byte " + Arrays.toString(valuesDefault);
            result+= "\n\nEncryption message in Byte " + Arrays.toString(processed_byte);
            result+= "\n\nEncryption message " + new String(processed_byte);
            result+= "\n\nNumber of packages " + packages/2;

         /*  CONSOL
          //output.setText(Arrays.toString(valuesDefault));
            System.out.println(Arrays.toString(valuesDefault));
            //output.setText(Arrays.toString(changeDefault));
            System.out.println(Arrays.toString(changeDefault));
            System.out.println(Arrays.toString(processed_byte) + packages);
            //String result = new String(changeDefault);
            System.out.println("Result = " + result + num_element ); // del
        */
            str_out.setText(result);
        });

        battle.getChildren().addAll(message, str, Ok, Clear, str_out, message_out, Exit);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Byte stuffing");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
