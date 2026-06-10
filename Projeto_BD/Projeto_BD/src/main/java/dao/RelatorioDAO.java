package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioDAO {

    public void relatorioTurmasESalas() {
        String sql = "SELECT t.nome AS Turma, t.ano, s.numero AS Sala, s.capacidade " +
                "FROM Turma t " +
                "JOIN Sala s ON t.Sala_idSala = s.idSala";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- RELATÓRIO: TURMAS E SALAS ---");
            while (rs.next()) {
                System.out.printf("Turma: %-10s | Ano: %d | Sala: %-5s | Capacidade: %d alunos\n",
                        rs.getString("Turma"), rs.getInt("ano"),
                        rs.getString("Sala"), rs.getInt("capacidade"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public void relatorioDisciplinasEProfessores() {
        String sql = "SELECT d.nome AS Disciplina, d.carga_horaria, p.nome AS Professor " +
                "FROM Disciplina d " +
                "JOIN Professor p ON d.Professor_idProfessor = p.idProfessor";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- RELATÓRIO: DISCIPLINAS E PROFESSORES ---");
            while (rs.next()) {
                System.out.printf("Disciplina: %-15s | Carga: %-3dh | Professor: %s\n",
                        rs.getString("Disciplina"), rs.getInt("carga_horaria"), rs.getString("Professor"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public void relatorioGradeCurricularCompleta() {
        String sql = "SELECT t.nome AS Turma, d.nome AS Disciplina " +
                "FROM Grade_Curricular gc " +
                "JOIN Turma t ON gc.Turma_idTurma = t.idTurma " +
                "JOIN Disciplina d ON gc.Disciplina_idDisciplina = d.idDisciplina " +
                "ORDER BY t.nome";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- RELATÓRIO: GRADE CURRICULAR COMPLETA ---");
            while (rs.next()) {
                System.out.printf("Turma: %-10s | Disciplina: %s\n",
                        rs.getString("Turma"), rs.getString("Disciplina"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}