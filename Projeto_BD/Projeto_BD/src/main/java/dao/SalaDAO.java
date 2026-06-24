package dao;

import factory.ConnectionFactory;
import factory.SQLLogger;
import model.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    // Método para Inserir
    public void salvar(Sala sala) {
        String sql = "INSERT INTO Sala (numero, capacidade) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getNumero());
            stmt.setInt(2, sala.getCapacidade());

            stmt.executeUpdate();
            SQLLogger.log("INSERT INTO Sala (numero, capacidade) VALUES ('" + sala.getNumero() + "', " + sala.getCapacidade() + ")");
            System.out.println("Sala cadastrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar sala: " + e.getMessage());
        }
    }

    // Método para Listar Todas
    public List<Sala> listarTodas() {
        String sql = "SELECT * FROM Sala";
        List<Sala> salas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sala sala = new Sala(
                        rs.getInt("idSala"),
                        rs.getString("numero"),
                        rs.getInt("capacidade")
                );
                salas.add(sala);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar salas: " + e.getMessage());
        }
        return salas;
    }

    // Método para Atualizar
    public void atualizar(Sala sala) {
        String sql = "UPDATE Sala SET numero = ?, capacidade = ? WHERE idSala = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getNumero());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala.getIdSala());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                SQLLogger.log("UPDATE Sala SET numero = '" + sala.getNumero() + "', capacidade = " + sala.getCapacidade() + " WHERE idSala = " + sala.getIdSala());
                System.out.println("Sala atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma sala encontrada com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar sala: " + e.getMessage());
        }
    }

    // Método para Deletar
    public void deletar(int idSala) {
        String sql = "DELETE FROM Sala WHERE idSala = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSala);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                SQLLogger.log("DELETE FROM Sala WHERE idSala = " + idSala);
                System.out.println("Sala deletada com sucesso!");
            } else {
                System.out.println("Nenhuma sala encontrada com esse ID.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar sala: " + e.getMessage());
        }
    }

    // Busca por atributo
    public List<Sala> buscarPorNumero(String numeroBusca) {
        String sql = "SELECT * FROM Sala WHERE numero LIKE ?";
        List<Sala> salas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Adiciona os % para buscar qualquer correspondência (ex: "LAB" acha "LAB1")
            stmt.setString(1, "%" + numeroBusca + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Sala sala = new Sala(
                            rs.getInt("idSala"),
                            rs.getString("numero"),
                            rs.getInt("capacidade")
                    );
                    salas.add(sala);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar sala: " + e.getMessage());
        }
        return salas;
    }
}