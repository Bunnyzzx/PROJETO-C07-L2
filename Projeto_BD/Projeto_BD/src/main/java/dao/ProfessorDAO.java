package dao;

import factory.ConnectionFactory;
import factory.SQLLogger;
import model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    // Método para Inserir
    public void salvar(Professor professor) {
        String sql = "INSERT INTO Professor (nome, especialidade, email) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEspecialidade());
            stmt.setString(3, professor.getEmail());

            stmt.executeUpdate();
            SQLLogger.log("INSERT INTO Professor (nome, especialidade, email) VALUES ('" + professor.getNome() + "', '" + professor.getEspecialidade() + "', '" + professor.getEmail() + "')");
            System.out.println("Professor cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar professor: " + e.getMessage());
        }
    }

    // Método para Listar Todos
    public List<Professor> listarTodos() {
        String sql = "SELECT * FROM Professor";
        List<Professor> professores = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("idProfessor"),
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getString("email")
                );
                professores.add(professor);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar professores: " + e.getMessage());
        }
        return professores;
    }

    // Método para Atualizar
    public void atualizar(Professor professor) {
        String sql = "UPDATE Professor SET nome = ?, especialidade = ?, email = ? WHERE idProfessor = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEspecialidade());
            stmt.setString(3, professor.getEmail());
            stmt.setInt(4, professor.getIdProfessor());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                SQLLogger.log("UPDATE Professor SET nome = '" + professor.getNome() + "', especialidade = '" + professor.getEspecialidade() + "', email = '" + professor.getEmail() + "' WHERE idProfessor = " + professor.getIdProfessor());
                System.out.println("Professor atualizado com sucesso!");
            } else {
                System.out.println("Nenhum professor encontrado com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar professor: " + e.getMessage());
        }
    }

    // Método para Deletar
    public void deletar(int idProfessor) {
        String sql = "DELETE FROM Professor WHERE idProfessor = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfessor);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                SQLLogger.log("DELETE FROM Professor WHERE idProfessor = " + idProfessor);
                System.out.println("Professor deletado com sucesso!");
            } else {
                System.out.println("Nenhum professor encontrado com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar professor: " + e.getMessage());
        }
    }

    // Busca por atributo
    public List<Professor> buscarPorNome(String nomeBusca) {
        String sql = "SELECT * FROM Professor WHERE nome LIKE ?";
        List<Professor> professores = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nomeBusca + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Professor professor = new Professor(
                            rs.getInt("idProfessor"),
                            rs.getString("nome"),
                            rs.getString("especialidade"),
                            rs.getString("email")
                    );
                    professores.add(professor);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar professor: " + e.getMessage());
        }
        return professores;
    }
}