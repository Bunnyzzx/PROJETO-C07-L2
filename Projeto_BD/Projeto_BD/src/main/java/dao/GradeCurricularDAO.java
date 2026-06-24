package dao;

import factory.ConnectionFactory;
import factory.SQLLogger;
import model.GradeCurricular;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeCurricularDAO {

    // Método para Inserir
    public void salvar(GradeCurricular grade) {
        String sql = "INSERT INTO Grade_Curricular (Turma_idTurma, Disciplina_idDisciplina) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, grade.getTurmaIdTurma());
            stmt.setInt(2, grade.getDisciplinaIdDisciplina());

            stmt.executeUpdate();
            SQLLogger.log("INSERT INTO Grade_Curricular (Turma_idTurma, Disciplina_idDisciplina) VALUES (" + grade.getTurmaIdTurma() + ", " + grade.getDisciplinaIdDisciplina() + ")");
            System.out.println("Disciplina vinculada à turma com sucesso na Grade Curricular!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar na grade curricular: " + e.getMessage());
        }
    }

    // Método para Listar Todas
    public List<GradeCurricular> listarTodas() {
        String sql = "SELECT * FROM Grade_Curricular";
        List<GradeCurricular> grades = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GradeCurricular grade = new GradeCurricular(
                        rs.getInt("Turma_idTurma"),
                        rs.getInt("Disciplina_idDisciplina")
                );
                grades.add(grade);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar a grade curricular: " + e.getMessage());
        }
        return grades;
    }

    // Método para Atualizar
    public void atualizar(int idTurma, int idDisciplinaAntiga, int idDisciplinaNova) {
        String sql = "UPDATE Grade_Curricular SET Disciplina_idDisciplina = ? WHERE Turma_idTurma = ? AND Disciplina_idDisciplina = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDisciplinaNova);
            stmt.setInt(2, idTurma);
            stmt.setInt(3, idDisciplinaAntiga);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                SQLLogger.log("UPDATE Grade_Curricular SET Disciplina_idDisciplina = " + idDisciplinaNova + " WHERE Turma_idTurma = " + idTurma + " AND Disciplina_idDisciplina = " + idDisciplinaAntiga);
                System.out.println("Grade curricular atualizada com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com esses IDs para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar grade curricular: " + e.getMessage());
        }
    }

    // Método para Deletar
    public void deletar(int idTurma, int idDisciplina) {
        String sql = "DELETE FROM Grade_Curricular WHERE Turma_idTurma = ? AND Disciplina_idDisciplina = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTurma);
            stmt.setInt(2, idDisciplina);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                SQLLogger.log("DELETE FROM Grade_Curricular WHERE Turma_idTurma = " + idTurma + " AND Disciplina_idDisciplina = " + idDisciplina);
                System.out.println("Vínculo deletado da Grade Curricular com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com esses IDs.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar da grade curricular: " + e.getMessage());
        }
    }

    // Busca por atributo
    public List<GradeCurricular> buscarPorTurma(int idTurmaBusca) {
        String sql = "SELECT * FROM Grade_Curricular WHERE Turma_idTurma = ?";
        List<GradeCurricular> grades = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTurmaBusca);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    GradeCurricular grade = new GradeCurricular(
                            rs.getInt("Turma_idTurma"),
                            rs.getInt("Disciplina_idDisciplina")
                    );
                    grades.add(grade);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar grade curricular: " + e.getMessage());
        }
        return grades;
    }
}