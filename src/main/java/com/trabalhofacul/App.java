
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