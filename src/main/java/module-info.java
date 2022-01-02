module com.traptricker.etherminetrackerjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.seleniumhq.selenium.api;
    requires io.github.bonigarcia.webdrivermanager;
    requires org.seleniumhq.selenium.chrome_driver;
    requires com.opencsv;
    requires org.seleniumhq.selenium.support;

    opens com.traptricker.etherminetrackerjavafx to javafx.fxml;
    exports com.traptricker.etherminetrackerjavafx;
}