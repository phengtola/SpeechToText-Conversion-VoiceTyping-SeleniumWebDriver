package com.cbnu.cgac;

import com.cbnu.cgac.repository.CategoryFile;
import com.cbnu.cgac.repository.MediaFile;
import com.cbnu.cgac.repository.MediaFileRepository;
import com.cbnu.cgac.selenium.SeleniumWebDriver;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.application.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public class Main extends Application {

    private static  String MUSIC_DIR = "C:\\Users\\Tola\\Downloads\\Music";
    private String VIDEO_DIR;

    public static final String TAG_COLUMN_NAME = "No";

    public static final String VALUE_COLUMN_NAME = "Title";

    public static final List<String> SUPPORTED_FILE_EXTENSIONS = Arrays.asList(".mp3", ".m4a" , ".wav" , ".MP4" , ".mp4" );

    static int playingId = 0;

    public static final int FILE_EXTENSION_LEN = 3;


    final Label currentlyPlaying = new Label();
    final ProgressBar progress = new ProgressBar();
    final TableView<Map> metadataTable = new TableView<>();
    private ChangeListener<Duration> progressChangeListener;
    private MapChangeListener<String, Object> metadataChangeListener;
    private Button btPrevious = new Button("Pre");
    private Button btTitle = new Button("Title");

    private TextField txCategoryId = new TextField();

    private String currentPlayingTitle = "";
    private String userEmail;

    private  File[] listOfFiles;


    private SeleniumWebDriver googleChromeRemoting= new SeleniumWebDriver();
    private WebDriver driver = null;


    private MediaFileRepository mediaFileRepository = null;



    public static void main(String[] args) throws Exception { launch(args); }

    public void start(final Stage stage) throws Exception {

        //MultiThread Start's here
        new Thread(){
            @Override
            public void run(){
                try {
                    mediaFileRepository = new MediaFileRepository();
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.err.println("Error on Thread Sleep");
                }
            }
        }.start();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Media File");
        alert.setHeaderText("Please select media files folder!");
        alert.setContentText("File Types: .mp3, .wav, .mp4");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory =
                    directoryChooser.showDialog(stage);
            if(selectedDirectory == null){
                //txTextFiled.setText("No Directory selected");
                MUSIC_DIR = "No Directory selected";
            }else{
                //txTextFiled.setText(selectedDirectory.getAbsolutePath());
                MUSIC_DIR = selectedDirectory.getAbsolutePath();
            }
        } else {
            // ... user chose CANCEL or closed the dialog
            Platform.exit();
            System.exit(0);
        }





        googleChromeRemoting = new SeleniumWebDriver();

        stage.setTitle("Speech To Text - CGAC 2017");
        //stage.setFullScreen(false);
        //stage.setMaximized(true);
        stage.setMinWidth(900.0);
        stage.setMinHeight(600.0);

        // determine the source directory for the playlist (either the first argument to the program or a default).
        final List<String> params = getParameters().getRaw();
        final File dir = (params.size() > 0)
                ? new File(params.get(0))
                : new File(MUSIC_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Cannot find audio source directory: " + dir + " please supply a directory as a command line argument");
            Platform.exit();
            return;
        }

        // create some media players.
        final List<MediaPlayer> players = new ArrayList<>();
        for (String file : dir.list(new FilenameFilter() {
            @Override public boolean accept(File dir, String name) {
                for (String ext: SUPPORTED_FILE_EXTENSIONS) {
                    if (name.endsWith(ext)) {
                        return true;
                    }
                }

                return false;
            }
        })) players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
        if (players.isEmpty()) {
            System.out.println("No audio found in " + dir);
            Platform.exit();
            return;
        }

        listOfFiles = dir.listFiles();





        // create a view to show the mediaplayers.



        final MediaView mediaView = new MediaView(players.get(0));
        mediaView.setFitWidth(400);

        final Button skip = new Button("Next");  skip.setDisable(true);
        final Button play = new Button("Start"); play.setDisable(true);
        final Button browse = new Button("Browse"); browse.setDisable(true);
        final Button googleDocs = new Button("Open Google Docs");
        final TextField machineName = new TextField();


        final TextField txTextFiled = new TextField();
        txTextFiled.setText(MUSIC_DIR);

        // play each audio file in turn.
        for (int i = 0; i < players.size(); i++) {

            final MediaPlayer player     = players.get(i);
            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            final int nextPlay =  i+1;

            player.setOnEndOfMedia(new Runnable() {
                @Override public void run() {

                    System.out.println("Finished " + player.getTotalDuration().toMinutes() );

                    googleChromeRemoting.stopViceTyping(driver);

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //WebElement e = driver.findElement (By.className ("kit-page-content-wrapper"));
                    //e.getText();


                   // List<WebElement> lstWebElement = (List<WebElement>) driver.findElements (By.className ("kit-page-content-wrapper"));//new ArrayList<WebElement>();
                    //WebDriverWait wait = new WebDriverWait(driver, 20);

                   // one page has 1 so it has many elements
                    //int page = 1;
                    //for( WebElement e: lstWebElement){
                    //   System.out.println(page + "=====CONTNET======" +  e.getText());
                    //   wait.until(ExpectedConditions.elementToBeClickable(e));
                    //    e.getText();
                    //    page++;
                    //}

                    System.out.println(currentPlayingTitle +" | "+ driver.getCurrentUrl());

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                // do your work
                                System.out.println("Insert into database");
                                if(mediaFileRepository == null){
                                    mediaFileRepository = new MediaFileRepository();
                                }



                                mediaFileRepository.saveGeneratedMediaFIle(new MediaFile(playingId+"_"+currentPlayingTitle, driver.getCurrentUrl(), "", "1", userEmail , Integer.parseInt(txCategoryId.getText()), machineName.getText().trim() , player.getTotalDuration().toMinutes()));
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                System.err.println("Error on Thread Sleep");
                            }
                        }
                    }.start();




                    player.currentTimeProperty().removeListener(progressChangeListener);
                    player.getMedia().getMetadata().removeListener(metadataChangeListener);
                    player.stop();

                    System.out.println(nextPlay+ " - " +  players.size());
                    if(nextPlay < players.size()){



                        /*
                        if(driver != null){
                            googleChromeRemoting.clickVoiceTypingOneTime(driver);
                        }
                        */
                        if(driver != null){

                            //driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL ,Keys.ENTER));
                            //googleChromeRemoting.clickVoiceTypingOneTime(driver);
                            // try {
                            //    Thread.sleep(3000);                 //1000 milliseconds is one second.
                            //} catch(InterruptedException ex) {
                            //   Thread.currentThread().interrupt();
                            //}

                            googleChromeRemoting.ceateNewDocs(driver);


                        }




                        System.out.println("nextPlay < players.size : next video will play");
                        mediaView.setMediaPlayer(nextPlayer);
                        nextPlayer.play();

                        googleChromeRemoting.setGoogleDocsTitle(driver,playingId+"."+currentPlayingTitle);
                        //driver.findElement(By.className("docs-title-input")).sendKeys(playingId+"."+currentPlayingTitle);


                        googleChromeRemoting.clickVoiceTypingOneTime(driver);




                    }else{
                        System.out.println("nextPlay > players.size : finished");
                        return;
                    }




                }
            });



            /*
            player.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    System.out.println(player.getCurrentTime().toMillis());

                }
            });
            */


            /*
            player.currentTimeProperty().addListener((observableValue, oldDuration, newDuration) -> {
                System.out.println(newDuration.toMillis() + " VS " +player.getTotalDuration().toMillis());
                if(newDuration.toMillis() <=  player.getTotalDuration().toMillis()-10000){
                    try {
                        //sending the actual Thread of execution to sleep X milliseconds
                        Thread.sleep(20000);
                    } catch(Exception e) {
                        System.out.println("Exception : "+e.getMessage());
                    }
                    System.out.println(newDuration.toMillis() + " VS " +player.getTotalDuration().toMillis());
                    System.out.println("Player:" + observableValue + " | Changed from playing at: " + oldDuration + " to play at " + newDuration);
                   // googleChromeRemoting.startVoiceTyping(driver);

                }

            });
            */


        }



        // New window
        googleDocs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                // Create the custom dialog.
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Google Accout");
                dialog.setHeaderText("Login to your google account");

// Set the icon (must be included in the project).
                //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
                ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField username = new TextField();
                username.setPromptText("Email");
                PasswordField password = new PasswordField();
                password.setPromptText("Password");

                grid.add(new Label("Email:"), 0, 0);
                grid.add(username, 1, 0);
                grid.add(new Label("Password:"), 0, 1);
                grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
                username.textProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(newValue.trim().isEmpty());
                });

                dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
                Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        return new Pair<>(username.getText(), password.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(usernamePassword -> {
                    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

                    userEmail = usernamePassword.getKey();

                    googleDocs.setText("Chrome is being controlled.");
                    driver = googleChromeRemoting.openGogleDocs(usernamePassword.getKey() ,usernamePassword.getValue());
                    googleDocs.setDisable(true);

                });


                play.setDisable(false);


                /*

                Label secondLabel = new Label("Hello");

                Button btOpenGoogleDocs = new Button("Open Google Docs");
                Button btStartVoiceTyping = new Button("Start Voice Typing");

                btOpenGoogleDocs.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Hello World!");
                        driver = googleChromeRemoting.openGogleDocs();
                    }

                });

                btStartVoiceTyping.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        googleChromeRemoting.startVoiceTyping(driver);
                    }
                });


                final HBox btnBox = new HBox(10);
                btnBox.setAlignment(Pos.CENTER);
                btnBox.getChildren().setAll(btOpenGoogleDocs,btStartVoiceTyping);

                final VBox content = new VBox(10);

                content.getChildren().setAll(
                        btnBox
                );

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.setStyle("-fx-background-color: #F0F8FF; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
                secondaryLayout.getChildren().add(content);

                Scene secondScene = new Scene(secondaryLayout, 600, 250);

                Stage secondStage = new Stage();
                secondStage.setTitle("Remote Google Docs");
                secondStage.setScene(secondScene);


                secondStage.show();

                */

            }
        });

        // allow the user to skip a track.
        skip.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                try {
                    final MediaPlayer curPlayer = mediaView.getMediaPlayer();
                    curPlayer.currentTimeProperty().removeListener(progressChangeListener);
                    curPlayer.getMedia().getMetadata().removeListener(metadataChangeListener);
                    curPlayer.stop();

                    MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
                    mediaView.setMediaPlayer(nextPlayer);
                    nextPlayer.play();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btPrevious.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                try {
                    final MediaPlayer curPlayer = mediaView.getMediaPlayer();
                    curPlayer.currentTimeProperty().removeListener(progressChangeListener);
                    curPlayer.getMedia().getMetadata().removeListener(metadataChangeListener);
                    curPlayer.stop();
                    if(playingId > 1){
                        playingId = playingId - 2;
                    }
                    MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) - 1) % players.size() );
                    mediaView.setMediaPlayer(nextPlayer);
                    nextPlayer.play();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                googleChromeRemoting.setGoogleDocsTitle(driver,playingId+"."+currentPlayingTitle);
            }
        });

        // allow the user to play or pause a track.
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                if("Start".equals(play.getText())){









                    //if(driver != null){


                    //}

                    //try {
                    //    Thread.sleep(10000);                 //1000 milliseconds is one second.
                    //} catch(InterruptedException ex) {
                     //   Thread.currentThread().interrupt();
                    //}
                    //googleChromeRemoting.startVoiceTyping(driver);

                    /*
                    try {
                        //sending the actual Thread of execution to sleep X milliseconds
                        Thread.sleep(3000);
                    } catch(Exception e) {
                        System.out.println("Exception : "+e.getMessage());
                    }
                       Start
                      */

                    // Start check voice typing button every 30 seconds
                    googleChromeRemoting.startVoiceTyping(driver);

                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Start Playing!");

                    mediaView.setMediaPlayer(players.get(0));
                    mediaView.getMediaPlayer().play();
                    play.setText("Pause");
                    skip.setDisable(false);


                    // Set google docs titile
                    //driver.findElement(By.className("docs-title-input")).sendKeys(playingId+"."+currentPlayingTitle);
                    googleChromeRemoting.setGoogleDocsTitle(driver,playingId+"."+currentPlayingTitle);
                   // System.out.println("currentPlayingTitle " + currentPlayingTitle);



                }else if ("Pause".equals(play.getText())) {
                    mediaView.getMediaPlayer().pause();
                    play.setText("Play");
                } else {
                    mediaView.getMediaPlayer().play();
                    play.setText("Pause");
                }
            }
        });

        // display the name of the currently playing track.
        mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
            @Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
                setCurrentlyPlaying(newPlayer);
            }
        });



        browse.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory =
                        directoryChooser.showDialog(stage);

                if(selectedDirectory == null){
                    txTextFiled.setText("No Directory selected");
                }else{
                    txTextFiled.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });


        // start playing the first track.
        /*
        mediaView.setMediaPlayer(players.get(0));
        mediaView.getMediaPlayer().play();
        setCurrentlyPlaying(mediaView.getMediaPlayer());
        */
        setCurrentlyPlaying(mediaView.getMediaPlayer());

        // silly invisible button used as a template to get the actual preferred size of the Pause button.
        Button invisiblePause = new Button("Pause");
        invisiblePause.setVisible(false);
        play.prefHeightProperty().bind(invisiblePause.heightProperty());
        play.prefWidthProperty().bind(invisiblePause.widthProperty());






        // add a metadataTable for meta data display
        metadataTable.setStyle("-fx-font-size: 13px;");

        TableColumn<Map, String> tagColumn = new TableColumn<>(TAG_COLUMN_NAME);
        tagColumn.setPrefWidth(150);
        TableColumn<Map, Object> valueColumn = new TableColumn<>(VALUE_COLUMN_NAME);
        valueColumn.setPrefWidth(400);

        tagColumn.setCellValueFactory(new MapValueFactory<>(TAG_COLUMN_NAME));
        valueColumn.setCellValueFactory(new MapValueFactory<>(VALUE_COLUMN_NAME));

        metadataTable.setEditable(true);
        metadataTable.getSelectionModel().setCellSelectionEnabled(true);
        metadataTable.getColumns().setAll(tagColumn, valueColumn);
        valueColumn.setCellFactory(new Callback<TableColumn<Map, Object>, TableCell<Map, Object>>() {
            @Override
            public TableCell<Map, Object> call(TableColumn<Map, Object> column) {
                return new TableCell<Map, Object>() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            if (item instanceof String) {
                                setText((String) item);
                                //setText("Test 99");
                                setGraphic(null);
                            } else if (item instanceof Integer) {
                                setText(Integer.toString((Integer) item));
                                setGraphic(null);
                            } else if (item instanceof Image) {
                                setText(null);
                                ImageView imageView = new ImageView((Image) item);
                                imageView.setFitWidth(200);
                                imageView.setPreserveRatio(true);
                                setGraphic(imageView);
                            } else {
                                setText("N/A");
                                setGraphic(null);
                            }
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        // layout the scene.
        final StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: #F0F8FF; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");



        final HBox browseRow = new HBox(10);
        browseRow.setAlignment(Pos.CENTER);
        browseRow.getChildren().setAll(googleDocs,browse,txTextFiled);
        txTextFiled.setMaxWidth(Double.MAX_VALUE);
        txTextFiled.setEditable(false);
        browseRow.setHgrow(txTextFiled, Priority.ALWAYS);

        final HBox categoryIdRow = new HBox(10);
        Label labelCateory = new Label("Category id");
        Label labelMachine = new Label("Machine");
        categoryIdRow.setAlignment(Pos.BASELINE_LEFT);
        categoryIdRow.getChildren().setAll(labelCateory,txCategoryId ,labelMachine,machineName );

        final HBox progressReport = new HBox(10);
        progressReport.setAlignment(Pos.CENTER);
        progressReport.getChildren().setAll(play,btPrevious,skip,btTitle, progress,   mediaView   );



        final VBox content = new VBox(10);
        content.getChildren().setAll(
                browseRow,
                categoryIdRow,
                currentlyPlaying,
                progressReport,
                metadataTable
        );

        layout.getChildren().addAll(
                invisiblePause,
                content
        );
        progress.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progress, Priority.ALWAYS);
        VBox.setVgrow(metadataTable, Priority.ALWAYS);

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    /** sets the currently playing label to the label of the new media player and updates the progress monitor. */
    private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
        newPlayer.seek(Duration.ZERO);

        playingId++;

        progress.setProgress(0);
        progressChangeListener = new ChangeListener<Duration>() {
            @Override public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
            }
        };
        newPlayer.currentTimeProperty().addListener(progressChangeListener);

        String source = newPlayer.getMedia().getSource();
        //source = source.substring(0, source.length() - FILE_EXTENSION_LEN);
        source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
        currentlyPlaying.setText("Now Playing: " + playingId + ". " + source);
        currentPlayingTitle = source;
        setMetaDataDisplay(newPlayer.getMedia().getMetadata());


    }

    private void setMetaDataDisplay(ObservableMap<String, Object> metadata) {
        metadataTable.getItems().setAll(convertMetadataToTableData(metadata));
        metadataChangeListener = new MapChangeListener<String, Object>() {
            @Override
            public void onChanged(Change<? extends String, ?> change) {
                metadataTable.getItems().setAll(convertMetadataToTableData(metadata));
            }
        };
        metadata.addListener(metadataChangeListener);
    }

    private ObservableList<Map> convertMetadataToTableData(ObservableMap<String, Object> metadata) {
        ObservableList<Map> allData = FXCollections.observableArrayList();

        if(listOfFiles.length > 0) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName());

                    Map<String, Object> dataRow = new HashMap<>();
                    dataRow.put(TAG_COLUMN_NAME,   i+1);
                    dataRow.put(VALUE_COLUMN_NAME, listOfFiles[i].getName());
                    allData.add(dataRow);

                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
        }
            /*
        for (String key: metadata.keySet()) {
            Map<String, Object> dataRow = new HashMap<>();

            dataRow.put(TAG_COLUMN_NAME,   key);
            dataRow.put(VALUE_COLUMN_NAME, metadata.get(key));

            allData.add(dataRow);
        }
    */
        return allData;
    }

    /** @return a MediaPlayer for the given source which will report any errors it encounters */
    private MediaPlayer createPlayer(String mediaSource) {
        final Media media = new Media(mediaSource);
        final MediaPlayer player = new MediaPlayer(media);
        player.setOnError(new Runnable() {
            @Override public void run() {
                System.out.println("Media error occurred: " + player.getError());
            }
        });
        return player;
    }
}