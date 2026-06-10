package dao;

import factory.ConnectionFactory;
import model.Turma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    // Método para Inserir
    public void salvar(Turma turma) {
        String sql = "INSERT INTO Turma (nome, ano, Sala_idSala) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getAno());
            stmt.setInt(3, turma.getSalaIdSala());

            stmt.executeUpdate();
            System.out.println("Turma cadastrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar turma. Certifique-se de que o ID da Sala existe: " + e.getMessage());
        }
    }

    // Método para Listar Todas
    public List<Turma> listarTodas() {
        String sql = "SELECT * FROM Turma";
        List<Turma> turmas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Turma turma = new Turma(
                        rs.getInt("idTurma"),
                        rs.getString("nome"),
                        rs.getInt("ano"),
                        rs.getInt("Sala_idSala")
                );
                turmas.add(turma);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar turmas: " + e.getMessage());
        }
        return turmas;
    }

    // Método para Atualizar
    public void atualizar(Turma turma) {
        String sql = "UPDATE Turma SET nome = ?, ano = ?, Sala_idSala = ? WHERE idTurma = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getAno());
            stmt.setInt(3, turma.getSalaIdSala());
            stmt.setInt(4, turma.getIdTurma());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Turma atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma turma encontrada com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar turma: " + e.getMessage());
        }
    }

    // Método para Deletar
    public void deletar(int idTurma) {
        String sql = "DELETE FROM Turma WHERE idTurma = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTurma);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Turma deletada com sucesso!");
            } else {
                System.out.println("Nenhuma turma encontrada com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar turma. Lembre-se de que turmas com alunos matriculados ou grades curriculares ativas não podem ser excluídas diretamente: " + e.getMessage());
        }
    }

    // Busca por atributo
    public List<Turma> buscarPorNome(String nomeBusca) {
        String sql = "SELECT * FROM Turma WHERE nome LIKE ?";
        List<Turma> turmas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nomeBusca + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Turma turma = new Turma(
                            rs.getInt("idTurma"),
                            rs.getString("nome"),
                            rs.getInt("ano"),
                            rs.getInt("Sala_idSala")
                    );
                    turmas.add(turma);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma: " + e.getMessage());
        }
        return turmas;
    }
}