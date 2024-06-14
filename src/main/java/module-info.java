module com.suikagame.suika_game {
	requires javafx.controls;
	requires javafx.fxml;
	requires jdk.jfr;
	
	
	opens com.suikagame.suika_game to javafx.fxml;
	exports com.suikagame.suika_game;
}