
package com.trabalhofacul;

import java.time.LocalDate;
import java.util.List;

import com.trabalhofacul.Repository.AcademicoRepository;
import com.trabalhofacul.models.Aluno;
import com.trabalhofacul.models.Curso;
import com.trabalhofacul.models.Disciplina;
import com.trabalhofacul.models.Matricula;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("academico");
        EntityManager em = emf.createEntityManager();

        AcademicoRepository repository = new AcademicoRepository();
        repository.setEm(em);

        em.getTransaction().begin();

        // ==============================
        // CRIANDO CURSO
        // ==============================
        Curso curso = new Curso();
        curso.setNome("Análise e Desenvolvimento de Sistemas");

        repository.salvarCurso(curso);

        // ==============================
        // CRIANDO ALUNO
        // ==============================
        Aluno aluno = new Aluno();
        aluno.setNome("Rayssa");
        aluno.setRa("123456");
        aluno.setCurso(curso);

        repository.salvarAluno(aluno);

        // ==============================
        // 4.1 - INCLUIR DISCIPLINA
        // ==============================
        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Programação Avançada");
        disciplina.setCargaHoraria(80);
        disciplina.setCurso(curso);

        repository.salvarDisciplina(disciplina);

        // ==============================
        // 4.2 - INCLUIR MATRÍCULA
        // ==============================
        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setDataMatricula(LocalDate.now());

        repository.salvarMatricula(matricula);

        em.getTransaction().commit();

        // ==============================
        // 4.3 - LISTAR DISCIPLINAS DE UM CURSO
        // ==============================
        System.out.println("\nDISCIPLINAS DO CURSO:");

        List<Disciplina> disciplinasCurso = repository.listarDisciplinasPorCurso(curso.getId());

        for (Disciplina d : disciplinasCurso) {
            System.out.println("- " + d.getNome());
        }

        // ==============================
        // 4.4 - LISTAR DISCIPLINAS DE UM ALUNO
        // ==============================
        System.out.println("\nDISCIPLINAS DO ALUNO:");

        List<Disciplina> disciplinasAluno = repository.listarDisciplinasPorAluno(aluno.getId());

        for (Disciplina d : disciplinasAluno) {
            System.out.println("- " + d.getNome());
        }

        // ==============================
        // 4.5 - LISTAR ALUNOS DE UMA DISCIPLINA
        // ==============================
        System.out.println("\nALUNOS DA DISCIPLINA:");

        List<Aluno> alunosDisciplina = repository.listarAlunosPorDisciplina(disciplina.getId());

        for (Aluno a : alunosDisciplina) {
            System.out.println("- " + a.getNome());
        }

        em.close();
        emf.close();
    }
}
```

# persistence.xml

Crie o arquivo em:

```text
src/main/resources/META-INF/persistence.xml
```

Conteúdo:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence
             https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="3.0">

    <persistence-unit name="academico">

        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

        <!-- ENTIDADES -->
        <class>com.trabalhofacul.models.Aluno</class>
        <class>com.trabalhofacul.models.Curso</class>
        <class>com.trabalhofacul.models.Disciplina</class>
        <class>com.trabalhofacul.models.Matricula</class>

        <properties>

            <!-- CONEXÃO MYSQL -->
            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/academico"/>
            <property name="jakarta.persistence.jdbc.user" value="root"/>
            <property name="jakarta.persistence.jdbc.password" value="1234"/>

            <!-- HIBERNATE -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>

        </properties>

    </persistence-unit>

</persistence>
```

# Dependências Maven

Caso esteja usando Maven, adicione no `pom.xml`:

```xml
<dependencies>

    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.4.4.Final</version>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>

    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.1.0</version>
```
