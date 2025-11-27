module org.jpacrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;

    // Adicionado para o Hibernate:
    requires org.hibernate.orm.core;

    // Permite o Hibernate ler o pacote 'org.model' onde está o 'Usuario'
    opens org.model to org.hibernate.orm.core;
    opens org.jpacrud to javafx.fxml;
    exports org.jpacrud;
}