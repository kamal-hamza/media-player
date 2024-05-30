module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.tika.core;
    requires org.apache.tika.parser.audiovideo;
    requires java.xml;
    requires javafx.media;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}