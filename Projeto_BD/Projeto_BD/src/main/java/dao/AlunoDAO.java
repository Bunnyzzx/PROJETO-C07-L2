package dao;

import factory.ConnectionFactory;
import model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    // Método para Inserir
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO Aluno (nome, idade, email) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getEmail());

            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
        }
    }

    // Método para Listar Todos
    public List<Aluno> listarTodos() {
        String sql = "SELECT * FROM Aluno";
        List<Aluno> alunos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("idAluno"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("email")
                );
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    // Método para chamar a Procedure
    public void listarAlunosPorTurma(int idTurma) {
        String sql = "{CALL sp_alunos_por_turma(?)}";

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, idTurma);

            try (ResultSet rs = cstmt.executeQuery()) {
                System.out.printf("%-5s | %-25s | %-30s\n", "ID", "Nome do Aluno", "E-mail");
                System.out.println("------------------------------------------------------------------");
                while (rs.next()) {
                    System.out.printf("%-5d | %-25s | %-30s\n",
                            rs.getInt("idAluno"),
                            rs.getString("aluno"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao executar procedure: " + e.getMessage());
        }
    }

    // Método para Atualizar
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE Aluno SET nome = ?, idade = ?, email = ? WHERE idAluno = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getEmail());
            stmt.setInt(4, aluno.getIdAluno());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Aluno atualizado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    // Método para Deletar
    public void deletar(int idAluno) {
        String sql = "DELETE FROM Aluno WHERE idAluno = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno deletado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
        }
    }

    // Busca por atributo
    public List<Aluno> buscarPorNome(String nomeBusca) {
        String sql = "SELECT * FROM Aluno WHERE nome LIKE ?";
        List<Aluno> alunos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Adiciona os % para buscar qualquer parte do nome
            stmt.setString(1, "%" + nomeBusca + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno(
                            rs.getInt("idAluno"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("email")
                    );
                    alunos.add(aluno);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
        }
        return alunos;
    }
}