package dao;

import factory.ConnectionFactory;
import model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    // Método para Inserir
    public void salvar(Disciplina disciplina) {
        String sql = "INSERT INTO Disciplina (nome, carga_horaria, Professor_idProfessor) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setInt(3, disciplina.getProfessorIdProfessor()); // Chave Estrangeira

            stmt.executeUpdate();
            System.out.println("Disciplina cadastrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar disciplina. Certifique-se de que o ID do Professor existe: " + e.getMessage());
        }
    }

    // Método para Listar Todas
    public List<Disciplina> listarTodas() {
        String sql = "SELECT * FROM Disciplina";
        List<Disciplina> disciplinas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Disciplina disciplina = new Disciplina(
                        rs.getInt("idDisciplina"),
                        rs.getString("nome"),
                        rs.getInt("carga_horaria"),
                        rs.getInt("Professor_idProfessor")
                );
                disciplinas.add(disciplina);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar disciplinas: " + e.getMessage());
        }
        return disciplinas;
    }

    // Método para Atualizar
    public void atualizar(Disciplina disciplina) {
        String sql = "UPDATE Disciplina SET nome = ?, carga_horaria = ?, Professor_idProfessor = ? WHERE idDisciplina = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setInt(3, disciplina.getProfessorIdProfessor());
            stmt.setInt(4, disciplina.getIdDisciplina());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Disciplina atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar disciplina: " + e.getMessage());
        }
    }

    // Método para Deletar
    public void deletar(int idDisciplina) {
        String sql = "DELETE FROM Disciplina WHERE idDisciplina = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDisciplina);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Disciplina deletada com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar disciplina. Turmas que dependem dessa disciplina (Grade Curricular) podem impedir a exclusão: " + e.getMessage());
        }
    }

    //Busca por atributo
    public List<Disciplina> buscarPorNome(String nomeBusca) {
        String sql = "SELECT * FROM Disciplina WHERE nome LIKE ?";
        List<Disciplina> disciplinas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nomeBusca + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Disciplina disciplina = new Disciplina(
                            rs.getInt("idDisciplina"),
                            rs.getString("nome"),
                            rs.getInt("carga_horaria"),
                            rs.getInt("Professor_idProfessor")
                    );
                    disciplinas.add(disciplina);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar disciplina: " + e.getMessage());
        }
        return disciplinas;
    }
}