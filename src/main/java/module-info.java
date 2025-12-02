module org.jpacrud {
    // Requerimentos de módulos
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jdk.compiler;

    // Leitor de pacotes
    opens org.model to javafx.base, org.hibernate.orm.core;
    opens org.jpacrud to javafx.fxml;
    exports org.jpacrud;
}