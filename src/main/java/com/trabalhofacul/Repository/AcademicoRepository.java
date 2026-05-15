package com.trabalhofacul.Repository;

import java.util.List;

import com.trabalhofacul.models.Aluno;
import com.trabalhofacul.models.Curso;
import com.trabalhofacul.models.Disciplina;
import com.trabalhofacul.models.Matricula;

import jakarta.persistence.EntityManager;
import lombok.Data;

@Data
public class AcademicoRepository {
    private EntityManager em;
    
      public void salvarCurso(Curso curso) {
        em.persist(curso);
    }

    public void salvarAluno(Aluno aluno) {
        em.persist(aluno);
    }

    public void salvarDisciplina(Disciplina disciplina) {
        em.persist(disciplina);
    }

    public void salvarMatricula(Matricula matricula) {
        em.persist(matricula);
    }

    public Curso buscarCursoPorId(Long id) {
        return em.find(Curso.class, id);
    }

    public Aluno buscarAlunoPorId(Long id) {
        return em.find(Aluno.class, id);
    }

    public Disciplina buscarDisciplinaPorId(Long id) {
        return em.find(Disciplina.class, id);
    }

    public List<Disciplina> listarDisciplinasPorCurso(Long idCurso) {
        return em.createQuery(
                "SELECT d FROM Disciplina d WHERE d.curso.id = :idCurso",
                Disciplina.class
        )
        .setParameter("idCurso", idCurso)
        .getResultList();
    }

    public List<Disciplina> listarDisciplinasPorAluno(Long idAluno) {
        return em.createQuery(
                "SELECT m.disciplina FROM Matricula m WHERE m.aluno.id = :idAluno",
                Disciplina.class
        )
        .setParameter("idAluno", idAluno)
        .getResultList();
    }

    public List<Aluno> listarAlunosPorDisciplina(Long idDisciplina) {
        return em.createQuery(
                "SELECT m.aluno FROM Matricula m WHERE m.disciplina.id = :idDisciplina",
                Aluno.class
        )
        .setParameter("idDisciplina", idDisciplina)
        .getResultList();
    }
}
