package dao;

import factory.ConnectionFactory;
import factory.SQLLogger;
import model.Matricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    // Método para Inserir
    public void salvar(Matricula matricula) {
        String sql = "INSERT INTO Matricula (Aluno_idAluno, Turma_idTurma) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula.getAlunoIdAluno());
            stmt.setInt(2, matricula.getTurmaIdTurma());

            stmt.executeUpdate();
            SQLLogger.log("INSERT INTO Matricula (Aluno_idAluno, Turma_idTurma) VALUES (" + matricula.getAlunoIdAluno() + ", " + matricula.getTurmaIdTurma() + ")");
            System.out.println("Aluno matriculado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao realizar matrícula. Verifique se os IDs do Aluno e da Turma existem: " + e.getMessage());
        }
    }

    // Método para Listar Todas
    public List<Matricula> listarTodas() {
        String sql = "SELECT * FROM Matricula";
        List<Matricula> matriculas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Matricula matricula = new Matricula(
                        rs.getInt("Aluno_idAluno"),
                        rs.getInt("Turma_idTurma"),
                        rs.getTimestamp("data_matricula")
                );
                matriculas.add(matricula);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar matrículas: " + e.getMessage());
        }
        return matriculas;
    }

    // Método para Atualizar
    public void atualizar(int idAluno, int idTurmaAntiga, int idTurmaNova) {
        String sql = "UPDATE Matricula SET Turma_idTurma = ? WHERE Aluno_idAluno = ? AND Turma_idTurma = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTurmaNova);
            stmt.setInt(2, idAluno);
            stmt.setInt(3, idTurmaAntiga);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                SQLLogger.log("UPDATE Matricula SET Turma_idTurma = " + idTurmaNova + " WHERE Aluno_idAluno = " + idAluno + " AND Turma_idTurma = " + idTurmaAntiga);
                System.out.println("Matrícula transferida com sucesso!");
            } else {
                System.out.println("Nenhuma matrícula encontrada com esses dados para transferir.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar matrícula: " + e.getMessage());
        }
    }

    // Método para Deletar
    public void deletar(int idAluno, int idTurma) {
        String sql = "DELETE FROM Matricula WHERE Aluno_idAluno = ? AND Turma_idTurma = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            stmt.setInt(2, idTurma);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                SQLLogger.log("DELETE FROM Matricula WHERE Aluno_idAluno = " + idAluno + " AND Turma_idTurma = " + idTurma);
                System.out.println("Matrícula removida do sistema com sucesso!");
            } else {
                System.out.println("Nenhuma matrícula encontrada com esses IDs.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar matrícula: " + e.getMessage());
        }
    }

    // Busca por atributo
    public List<Matricula> buscarPorAluno(int idAlunoBusca) {
        String sql = "SELECT * FROM Matricula WHERE Aluno_idAluno = ?";
        List<Matricula> matriculas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlunoBusca);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Matricula matricula = new Matricula(
                            rs.getInt("Aluno_idAluno"),
                            rs.getInt("Turma_idTurma"),
                            rs.getTimestamp("data_matricula")
                    );
                    matriculas.add(matricula);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar matrículas do aluno: " + e.getMessage());
        }
        return matriculas;
    }
}