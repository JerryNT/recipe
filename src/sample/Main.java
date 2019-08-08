package sample;

import java.io.EOFException;
import java.io.FileInputStream;
import java.sql.*;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import org.controlsfx.control.CheckComboBox;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

////password health_or_hedonism  asd13368989012
public class Main extends Application implements EventHandler<ActionEvent> {
    static final String databasePrefix ="test_health_hedo"; // need to change
    static final String netID ="root"; // Please enter your netId
    static final String hostName ="localhost"; //washington.uww.edu
    static final String databaseURL ="jdbc:mysql://"+hostName+"/"+databasePrefix+"?autoReconnect=true&useSSL=false&serverTimezone=CST";
    static final String password="asd13368989012"; // please enter your own password
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    static PreparedStatement preparedStmt = null;
    public static void main(String[] args) {
        try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              System.out.println("databaseURL"+ databaseURL);
            connection = DriverManager.getConnection(databaseURL, netID, password);
            System.out.println("Successfully connected to the database");
            launch(args);
         }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        finally {
            try {
            connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Welcome to the recipes selection");
        first(primaryStage);

    }

    public void first(Stage primaryStage) throws Exception {
        // Button setup
           Button login = new Button();
           login.setText("Log in");
           Button sign_up = new Button();
           sign_up.setText("Sign up");
           login.setOnAction(this);
           sign_up.setOnAction(this);
           
           
           //TODO: exception
           
           sign_up.setOnAction(e->sign_upButton(primaryStage));


           // Define the root for the scene
           VBox root = new VBox(); // The overall layout of the scene
           root.setPadding(new Insets(350, 0, 0, 0));
           
           // The first part, title HBox
           HBox titleHBox = new HBox(20);
           titleHBox.setPadding(new Insets(0, 0, 0, 250));
           // TODO: Set style to the HBox

           Text leftText = new Text("Healthnism");
           leftText.setFont(Font.font("Verdana", 40));
           // TODO:set style to the left text
           titleHBox.getChildren().add(leftText);

           Text midText = new Text("or");
           midText.setFont(Font.font("Verdana", 40));
           // TODO:set style to the left text
           titleHBox.getChildren().add(midText);

           Text rightText = new Text("Hedonism");
           rightText.setFont(Font.font("Verdana", 40));
           // TODO:set style to the left text
           titleHBox.getChildren().add(rightText);

           // After all components have added to the titleHBox, add the finished HBox to the root VBox
           root.getChildren().add(titleHBox);
          

           //Setting Vbox, and put two Hbox
           HBox UserHBox = new HBox();
           UserHBox.setSpacing(20);
           HBox PasswordHBox = new HBox();
           PasswordHBox.setSpacing(20);
           
           // Username
           Label name = new Label("Username");

           // Username input
           TextField nameInput = new TextField("Type in your username");

           // Password
           Label password = new Label("Password");

           // Password input
           TextField passwordInput = new TextField("Type in your password");
           login.setOnAction(e->loginButton(primaryStage,nameInput.getText(), passwordInput.getText()));
           
           UserHBox.getChildren().addAll(name, nameInput, login);
           UserHBox.setPadding(new Insets(20,0,0,300));
           PasswordHBox.getChildren().addAll(password, passwordInput, sign_up);
           PasswordHBox.setPadding(new Insets(10,0,0,300));

           root.getChildren().addAll(UserHBox, PasswordHBox);
                   
           Scene loginScene = new Scene(root,1000,1000); // Set the layout to the scene
           primaryStage.setScene(loginScene);
           primaryStage.setResizable(true);
           primaryStage.show();
       }
    
    public void second(Stage primaryStage) throws Exception {
        // Set a Vbox as the general structure
        VBox generalStructure = new VBox();

        // Set a Hbox to put the title in
        Text title = new Text("Which one do you prefer today?");
        title.setFont(Font.font("Verdana", 40));
        HBox hb = new HBox(20);
        hb.setPadding(new Insets(200, 0, 50, 0));
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(title);
        generalStructure.getChildren().add(hb);

        // Set a Hbox for the buttons of healthnism and hedonism
        HBox HH = new HBox();
        HH.setSpacing(20);

        Text or = new Text("OR");
        Label HealthnismButton = new Label("Healthnism");
        Label HedonismButton = new Label("Hedonism");
        HealthnismButton.setFont(Font.font("Verdana", 40));
        HedonismButton.setFont(Font.font("Verdana", 40));
        or.setFont(Font.font("Verdana", 40));

        Button healthnism = new Button();
        healthnism.setOnAction(e -> healthnismButton(primaryStage));
        healthnism.setStyle("-fx-font-size:32");
        healthnism.setText("Healthnism");
        healthnism.setTextFill(Color.GREEN);
        healthnism.setMinSize(200, 150);
        healthnism.setPadding(new Insets(100, 20, 100, 20));
        Button hedonism = new Button();
        hedonism.setOnAction(e -> hedonismButton(primaryStage));
        hedonism.setStyle("-fx-font-size:32");
        hedonism.setText("Hedonism");
        hedonism.setTextFill(Color.RED);
        hedonism.setMinSize(200, 150);
        hedonism.setPadding(new Insets(100, 20, 100, 20));

        HH.getChildren().addAll(healthnism, or, hedonism);
        HH.setAlignment(Pos.CENTER);
        generalStructure.getChildren().add(HH);

        // Another Hbox for My favorite button
        HBox Fhb = new HBox();
        Fhb.setPadding(new Insets(100, 0, 0, 0));
        Fhb.setSpacing(20);
        Fhb.setAlignment(Pos.CENTER);
        Button favorite = new Button();
        favorite.setStyle("-fx-font-size:20");
        favorite.setText("My favorite");
        favorite.setTextFill(Color.BLUE);
        favorite.setMinSize(300, 100);
        favorite.setOnAction(e -> favoriteButton(primaryStage));
        Fhb.getChildren().add(favorite);
        generalStructure.getChildren().add(Fhb);

        Scene Selection = new Scene(generalStructure, 1000, 1000);
        primaryStage.setScene(Selection);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

//Third scene
    public void third(Stage primaryStage) throws Exception {
        // Using borderpane
        BorderPane bp = new BorderPane();

        // Put title in a Hbox
        HBox Thb = new HBox();
        Thb.setAlignment(Pos.CENTER);
        Text title = new Text("Welcome to our healthnism recipes");
        title.setFont(Font.font("Verdana", 40));
        Thb.getChildren().add(title);
        bp.setTop(Thb);

        // My favorite, Hedonism and logout button by using Vbox
        VBox Vfb = new VBox();

        Button favorite = new Button();
        favorite.setStyle("-fx-font-size:20");
        favorite.setText("My favorite");
        favorite.setTextFill(Color.BLUE);
        favorite.setMinSize(150, 100);
        favorite.setOnAction(e -> favoriteButton(primaryStage));

        Button hedonism = new Button();
        hedonism.setStyle("-fx-font-size:20");
        hedonism.setText("Hedonism");
        hedonism.setTextFill(Color.BLUE);
        hedonism.setMinSize(150, 100);
        hedonism.setOnAction(e -> hedonismButton(primaryStage));

        Button logout = new Button();
        logout.setStyle("-fx-font-size:20");
        logout.setText("Logout");
        logout.setTextFill(Color.BLUE);
        logout.setMinSize(150, 100);
        logout.setOnAction(e -> logoutButton(primaryStage));

        Vfb.getChildren().addAll(favorite, hedonism, logout);
        Vfb.setPadding(new Insets(100, 0, 0, 0));
        bp.setLeft(Vfb);

        // Setting calories using Vbox

        VBox calories = new VBox();

        HBox CB1 = new HBox();
        CheckBox cb1 = new CheckBox();
        cb1.setText("High(700 Kcal)");
        CB1.getChildren().add(cb1);
        CB1.setPadding(new Insets(50, 0, 0, 0));

        HBox CB2 = new HBox();
        CheckBox cb2 = new CheckBox();
        cb2.setText("Medium(500 Kcal)");
        CB2.getChildren().add(cb2);
        CB2.setPadding(new Insets(50, 0, 0, 0));

        HBox CB3 = new HBox();
        CheckBox cb3 = new CheckBox();
        cb3.setText("Low(300 Kcal)");
        CB3.getChildren().add(cb3);
        CB3.setPadding(new Insets(50, 0, 0, 0));

        HBox sp = new HBox();
        Label spec = new Label("Specific: ");
        TextField Spec = new TextField();
        sp.getChildren().addAll(spec, Spec);
        sp.setSpacing(20);
        sp.setPadding(new Insets(50, 0, 0, 0));

        HBox thb = new HBox();
        Text cal = new Text("Calories");
        thb.getChildren().add(cal);
        cal.setFont(Font.font("Verdana", 30));
        calories.getChildren().addAll(thb, CB1, CB2, CB3, sp);
        calories.setPadding(new Insets(100, 0, 0, 200));
        bp.setCenter(calories);

        // Setting another Vbox for lk and dislk
        VBox right = new VBox();

        // Inserting like
        VBox lk = new VBox();
        Label like = new Label("What specificly do you want: ");
        TextField Like = new TextField("Type in here");
        lk.getChildren().addAll(like, Like);
        lk.setPadding(new Insets(0, 0, 50, 0));

        // Inserting dislike
        VBox dislk = new VBox();
        Label dislike = new Label("Is there anything you do not want: ");
        TextField Dislike = new TextField("Type in here");
        dislk.getChildren().addAll(dislike, Dislike);

        // create the data to show in the CheckComboBox
        final ObservableList<String> strings = FXCollections.observableArrayList();
        for (int i = 0; i <= 100; i++) {
            strings.add("Item " + i);
        }

        // Create the checkComboBox for category
        VBox category = new VBox();
        Label categoryLabel = new Label("Category");
        final CheckComboBox<String> lkCategoryCheckComboBox = new CheckComboBox<String>(strings);
        category.getChildren().add(categoryLabel);
        category.getChildren().add(lkCategoryCheckComboBox);
        //category.setPadding(new Insets(100,0,0,0));
        
        
        //Create the dislike category checkbox
        VBox category2 = new VBox();
        Label categoryLabel2 = new Label("dislike Category");
        final CheckComboBox<String> dislkCategoryCheckComboBox = new CheckComboBox<String>(strings);
        category2.getChildren().add(categoryLabel2);
        category2.getChildren().add(dislkCategoryCheckComboBox);
        category2.setPadding(new Insets(50,0,0,0));
        

        // and listen to the relevant events (e.g. when the selected indices or
        // selected items change).
        lkCategoryCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                System.out.println(lkCategoryCheckComboBox.getCheckModel().getCheckedItems());
            }
        });
        
        
        //Adding a text field for typing in the recipes' name and it does not have to be full name
        dislkCategoryCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                System.out.println(dislkCategoryCheckComboBox.getCheckModel().getCheckedItems());
            }
        });
        
        //Put a textfiled to search by the recipe's name
        HBox titleSearch = new HBox();
        TextField tf = new TextField("Name of recipe");
        titleSearch.getChildren().add(tf);
        titleSearch.setPadding(new Insets(30,0,0,0));
        calories.getChildren().add(titleSearch);
        

        // Put search button in a hbox
        Button search = new Button();
        search.setMinSize(400, 100);
        HBox searchBox = new HBox();
        search.setText("Search");
        searchBox.getChildren().add(search);
        searchBox.setPadding(new Insets(-430, 0, 0, 250));
        search.setOnAction(e -> searchButton(primaryStage));
        bp.setBottom(searchBox);

        // putting those sub box into the general one
        right.getChildren().addAll(lk, dislk, category,category2);
        right.setPadding(new Insets(100, 50, 0, 0)); 
        right.setSpacing(80);
        bp.setRight(right);

        Scene InHealthnism = new Scene(bp, 1000, 1000);
        primaryStage.setScene(InHealthnism);
        primaryStage.setResizable(true);
        primaryStage.show();

    }

//fourth scene   
    public void fourth(Stage primaryStage) throws Exception {
        // Using borderpane
        BorderPane bp = new BorderPane();

        // Put title in a Hbox
        HBox Thb = new HBox();
        Thb.setAlignment(Pos.CENTER);
        Text title = new Text("Welcome to our hedonism recipes");
        title.setFont(Font.font("Verdana", 40));
        Thb.getChildren().add(title);
        bp.setTop(Thb);

        // My favorite, Hedonism and logout button by using Vbox
        VBox Vfb = new VBox();

        Button favorite = new Button();
        favorite.setStyle("-fx-font-size:20");
        favorite.setText("My favorite");
        favorite.setTextFill(Color.BLUE);
        favorite.setMinSize(150, 100);
        favorite.setOnAction(e -> favoriteButton(primaryStage));

        Button health = new Button();
        health.setStyle("-fx-font-size:20");
        health.setText("Healthnism");
        health.setTextFill(Color.BLUE);
        health.setMinSize(150, 100);
        health.setOnAction(e -> healthnismButton(primaryStage));

        Button logout = new Button();
        logout.setStyle("-fx-font-size:20");
        logout.setText("Logout");
        logout.setTextFill(Color.BLUE);
        logout.setMinSize(150, 100);
        logout.setOnAction(e -> logoutButton(primaryStage));

        Vfb.getChildren().addAll(favorite, health, logout);
        Vfb.setPadding(new Insets(100, 0, 0, 0));
        bp.setLeft(Vfb);

        // Setting calories using Vbox

        VBox calories = new VBox();

        HBox CB1 = new HBox();
        CheckBox cb1 = new CheckBox();
        cb1.setText("High(2000 Kcal)");
        CB1.getChildren().add(cb1);
        CB1.setPadding(new Insets(50, 0, 0, 0));

        HBox CB2 = new HBox();
        CheckBox cb2 = new CheckBox();
        cb2.setText("Medium(1800 Kcal)");
        CB2.getChildren().add(cb2);
        CB2.setPadding(new Insets(50, 0, 0, 0));

        HBox CB3 = new HBox();
        CheckBox cb3 = new CheckBox();
        cb3.setText("Low(1500 Kcal)");
        CB3.getChildren().add(cb3);
        CB3.setPadding(new Insets(50, 0, 0, 0));

        HBox sp = new HBox();
        Label spec = new Label("Specific: ");
        TextField Spec = new TextField();
        sp.getChildren().addAll(spec, Spec);
        sp.setSpacing(20);
        sp.setPadding(new Insets(50, 0, 0, 0));

        HBox thb = new HBox();
        Text cal = new Text("Calories");
        thb.getChildren().add(cal);
        cal.setFont(Font.font("Verdana", 30));
        calories.getChildren().addAll(thb, CB1, CB2, CB3, sp);
        calories.setPadding(new Insets(100, 0, 0, 200));
        bp.setCenter(calories);

        // Setting another Vbox for lk and dislk
        VBox right = new VBox();

        // Inserting like
        VBox lk = new VBox();
        Label like = new Label("What specificly do you want: ");
        TextField Like = new TextField("Type in here");
        lk.getChildren().addAll(like, Like);
        lk.setPadding(new Insets(0, 0, 50, 0));

        // Inserting dislike
        VBox dislk = new VBox();
        Label dislike = new Label("Is there anything you do not want: ");
        TextField Dislike = new TextField("Type in here");
        dislk.getChildren().addAll(dislike, Dislike);

        // create the data to show in the CheckComboBox
        final ObservableList<String> strings = FXCollections.observableArrayList();
        for (int i = 0; i <= 100; i++) {
            strings.add("Item " + i);
        }

        // Create the checkComboBox for category
        VBox category = new VBox();
        Label categoryLabel = new Label("Category");
        final CheckComboBox<String> checkComboBox = new CheckComboBox<String>(strings);
        category.getChildren().add(categoryLabel);
        category.getChildren().add(checkComboBox);

        //Create the dislike category checkbox
        VBox category2 = new VBox();
        Label categoryLabel2 = new Label("dislike Category");
        final CheckComboBox<String> dislkCategoryCheckComboBox = new CheckComboBox<String>(strings);
        category2.getChildren().add(categoryLabel2);
        category2.getChildren().add(dislkCategoryCheckComboBox);
        category2.setPadding(new Insets(50,0,0,0));

        // and listen to the relevant events (e.g. when the selected indices or
        // selected items change).

        checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                System.out.println(checkComboBox.getCheckModel().getCheckedItems());
            }
        });
        
        //put a text filed so that users can type in the recipe's name
        HBox titleSearch = new HBox();
        TextField tf = new TextField("Name of recipe");
        titleSearch.getChildren().add(tf);
        titleSearch.setPadding(new Insets(30,0,0,0));
        calories.getChildren().add(titleSearch);

        // Put search button in a hbox
        Button search = new Button();
        search.setMinSize(400, 100);
        HBox searchBox = new HBox();
        search.setText("Search");
        searchBox.getChildren().add(search);
        searchBox.setPadding(new Insets(-430, 0, 0, 250));
        search.setOnAction(e -> searchButton(primaryStage));
        bp.setBottom(searchBox);

        // putting those sub box into the general one
        right.getChildren().addAll(lk, dislk, category,category2);
        right.setPadding(new Insets(100, 50, 0, 0));
        right.setSpacing(80);
        bp.setRight(right);

        Scene InHealthnism = new Scene(bp, 1000, 1000);
        primaryStage.setScene(InHealthnism);
        primaryStage.setResizable(true);
        primaryStage.show();

    }

    public void fifth(Stage primaryStage) throws Exception {
        HBox hbox = new HBox();
        Button favorite = new Button("My Favorite");
        favorite = new Button();
        favorite.setOnAction(e -> favoriteButton(primaryStage));
        favorite.setStyle("-fx-font-size:20");
        favorite.setText("favorite");
        favorite.setTextFill(Color.BLUE);
        favorite.setMinSize(150, 100);
        Button back = new Button("Back");
        back.setStyle("-fx-font-size:20");
        back.setTextFill(Color.BLUE);
        back.setMinSize(150, 100);
        back.setOnAction(e -> backButton(primaryStage));

        // left part two button
        VBox left = new VBox();
        left.setPadding(new Insets(20, 20, 0, 0));
        left.getChildren().add(favorite);
        left.getChildren().add(back);
        left.setSpacing(10);

        // mid part
        VBox mid = new VBox();
        mid.setPadding(new Insets(20, 20, 0, 0));
        mid.setSpacing(10);
        mid.setMaxHeight(500);
        Text result = new Text("Result");
        result.setTextAlignment(TextAlignment.CENTER);
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList("Single", "Double", "Suite", "Family App");
        list.setItems(items);
        mid.getChildren().add(result);
        mid.getChildren().add(list);

        // right part
        VBox right = new VBox();
        right.setSpacing(10);
        right.setMaxHeight(500);
        right.setMinWidth(500);
        right.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        mid.setPadding(new Insets(20, 20, 0, 0));
        Text recipe = new Text("Detail here");
        recipe.prefHeight(490);
        recipe.prefWidth(400);
        right.getChildren().add(recipe);
        list.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    recipe.setText(new_val);
                });

        // Love button
        Button like = new Button();
        like.setMinHeight(50);
        like.setMinWidth(50);
        like.setText("like");
        VBox likeBox = new VBox();
        likeBox.setPadding(new Insets(10, 0, 0, 0));
        likeBox.getChildren().add(like);

        hbox.getChildren().add(left);
        hbox.getChildren().add(mid);
        hbox.getChildren().add(right);
        hbox.getChildren().add(likeBox);
        Scene scene = new Scene(hbox, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //My favorite scene
    public void sixth(Stage primaryStage) throws Exception {
        HBox hbox = new HBox();
        Button back = new Button("Back");
        back.setStyle("-fx-font-size:20");
        back.setTextFill(Color.BLUE);
        back.setMinSize(150, 100);
        back.setOnAction(e -> backButton(primaryStage));

        // left part two button
        VBox left = new VBox();
        left.setPadding(new Insets(20, 20, 0, 0));
        left.getChildren().add(back);
        left.setSpacing(10);

        // mid part
        VBox mid = new VBox();
        mid.setPadding(new Insets(20, 20, 0, 0));
        mid.setSpacing(10);
        mid.setMaxHeight(500);
        Text result = new Text("Result");
        result.setTextAlignment(TextAlignment.CENTER);
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList("Single", "Double", "Suite", "Family App");
        list.setItems(items);
        mid.getChildren().add(result);
        mid.getChildren().add(list);

        // right part
        VBox right = new VBox();
        right.setSpacing(10);
        right.setMaxHeight(500);
        right.setMinWidth(500);
        right.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        mid.setPadding(new Insets(20, 20, 0, 0));
        TextField recipe = new TextField("Detail here");
        recipe.setEditable(false);
        recipe.prefHeight(490);
        recipe.prefWidth(400);
        right.getChildren().add(recipe);
        list.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    recipe.setText(new_val);
                });

        // Like button
        Button dislike = new Button();
        dislike.setMinHeight(50);
        dislike.setMinWidth(50);
        dislike.setText("dislike");
        dislike.setOnAction(e -> dislikeButton(primaryStage));
        VBox likeBox = new VBox();
        likeBox.setPadding(new Insets(10, 0, 0, 0));
        likeBox.getChildren().add(dislike);

        hbox.getChildren().add(left);
        hbox.getChildren().add(mid);
        hbox.getChildren().add(right);
        hbox.getChildren().add(likeBox);
        Scene scene = new Scene(hbox, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //the register scene
    public void seventh(Stage primaryStage) throws Exception {
        // Button setup
           Button register = new Button();
           register.setText("Register");
           Button back = new Button();
           back.setText("Back");
           register.setOnAction(this);
           back.setOnAction(this);
           
           
           //TODO: exception
           
           back.setOnAction(e->seventhBackButton(primaryStage));
           
           
           
           // Define the root for the scene
           VBox root = new VBox(); // The overall layout of the scene
           root.setPadding(new Insets(350, 0, 0, 0));
           
           // The first part, title HBox
           HBox titleHBox = new HBox(20);
           titleHBox.setPadding(new Insets(0, 0, 0, 250));
           // TODO: Set style to the HBox

           Text leftText = new Text("Healthnism");
           leftText.setFont(Font.font("Verdana", 40));
           // TODO:set style to the left text
           titleHBox.getChildren().add(leftText);

           Text midText = new Text("or");
           midText.setFont(Font.font("Verdana", 40));
           // TODO:set style to the left text
           titleHBox.getChildren().add(midText);

           Text rightText = new Text("Hedonism");
           rightText.setFont(Font.font("Verdana", 40));
           // TODO:set style to the left text
           titleHBox.getChildren().add(rightText);

           // After all components have added to the titleHBox, add the finished HBox to the root VBox
           root.getChildren().add(titleHBox);
          

           //Setting Vbox, and put two Hbox
           HBox UserHBox = new HBox();
           UserHBox.setSpacing(20);
           HBox PasswordHBox = new HBox();
           PasswordHBox.setSpacing(20);
           
           // Username
           Label name = new Label("Username");

           // Username input
           TextField nameInput = new TextField("Type in your username");

           // Password
           Label password = new Label("Password");

           // Password input
           TextField passwordInput = new TextField("Type in your password");
           
           register.setOnAction(e->{
               try {
                   registerButton(primaryStage,nameInput.getText(),passwordInput.getText());
               } catch (Exception e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
               }
           });
           
           UserHBox.getChildren().addAll(name, nameInput, register);
           UserHBox.setPadding(new Insets(20,0,0,300));
           PasswordHBox.getChildren().addAll(password, passwordInput, back);
           PasswordHBox.setPadding(new Insets(10,0,0,300));

           root.getChildren().addAll(UserHBox, PasswordHBox);
                   
           Scene registerScene = new Scene(root,1000,1000); // Set the layout to the scene
           primaryStage.setScene(registerScene);
           primaryStage.setResizable(true);
           primaryStage.show();

       }

    private Object sevenBackButton(Stage primaryStage) {
        // TODO Auto-generated method stub
        return null;
    }


    public void loginButton(Stage stage, String username, String password) {
        try {
            String sqlQuery = "select * from user where username = ? and password = ?";
            System.out.println(sqlQuery);
            preparedStmt = connection.prepareStatement(sqlQuery);
            preparedStmt.setString(1, "shun");
            preparedStmt.setString(1, "123");
            resultSet = preparedStmt.executeQuery();

            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            for (int i=1; i<= columns; i++) {
                System.out.print(metaData.getColumnName(i)+"\t");
            }

            System.out.println();


            while (resultSet.next()) {

                for (int i=1; i<= columns; i++) {
                    System.out.print(resultSet.getObject(i)+"\t\t");
                }
                System.out.println();
            }
            second(stage);
        } catch (Exception e) {
            return;
        }
        ;

    }

    public void sign_upButton(Stage stage) {
        try {
            seventh(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void healthnismButton(Stage stage) {
        try {
            third(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void hedonismButton(Stage stage) {
        try {
            fourth(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void favoriteButton(Stage stage) {
        try {
            sixth(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void logoutButton(Stage stage) {
        try {
            first(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void searchButton(Stage stage) {
        try {
            fifth(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void backButton(Stage stage) {
        try {
            second(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void likeButton(Stage stage) {
        return;
    }

    public void dislikeButton(Stage stage) {
        return;
    }

    public void registerButton(Stage stage, String username, String password) {

        try {
         String sqlQuery = " insert into user (username, password)"
           + " values (?, ?)";
         // create the mysql insert preparedstatement
         preparedStmt = connection.prepareStatement(sqlQuery);
         preparedStmt.setString (1, username);
         preparedStmt.setString (2, password);
         // execute the preparedstatement
         preparedStmt.execute();
   
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
    }

    public void seventhBackButton(Stage stage) {
        try {
            first(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    @Override
    public void handle(ActionEvent event) {
        // TODO Auto-generated method stub

    }

}
