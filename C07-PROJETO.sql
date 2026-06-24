-- Caio de Castro Yarouhas - 863 - GES
-- Paulo Eduardo Costa - 2184 - GEC
-- Paulo Henrique Martins - 2239 - GEC

DROP DATABASE IF EXISTS escola_db;
CREATE DATABASE escola_db;
USE escola_db;
 
 -- Tabelas
CREATE TABLE Sala (
idSala INT NOT NULL AUTO_INCREMENT,
numero VARCHAR(45) NOT NULL,
capacidade INT NOT NULL,
PRIMARY KEY (idSala)
);
 
CREATE TABLE Professor (
idProfessor INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(45) NOT NULL,
especialidade VARCHAR(45),
email VARCHAR(45) UNIQUE,
PRIMARY KEY (idProfessor)
);
 
CREATE TABLE Aluno (
idAluno INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(45) NOT NULL,
idade INT,
email VARCHAR(100) UNIQUE,
PRIMARY KEY (idAluno)
);
 
CREATE TABLE Turma (
idTurma INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(45) NOT NULL,
ano INT NOT NULL,
Sala_idSala INT NOT NULL,
PRIMARY KEY (idTurma),
FOREIGN KEY (Sala_idSala) REFERENCES Sala (idSala)
);
 
CREATE TABLE Disciplina (
idDisciplina INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(45) NOT NULL,
carga_horaria INT NOT NULL,
Professor_idProfessor INT NOT NULL,
PRIMARY KEY (idDisciplina),
FOREIGN KEY (Professor_idProfessor) REFERENCES Professor (idProfessor)
);
 
CREATE TABLE Grade_Curricular (
Turma_idTurma INT NOT NULL,
Disciplina_idDisciplina INT NOT NULL,
PRIMARY KEY (Turma_idTurma, Disciplina_idDisciplina),
FOREIGN KEY (Turma_idTurma) REFERENCES Turma (idTurma),
FOREIGN KEY (Disciplina_idDisciplina) REFERENCES Disciplina (idDisciplina)
);
 
CREATE TABLE Matricula (
Aluno_idAluno INT NOT NULL,
Turma_idTurma INT NOT NULL,
data_matricula DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (Aluno_idAluno, Turma_idTurma),
FOREIGN KEY (Aluno_idAluno) REFERENCES Aluno (idAluno),
FOREIGN KEY (Turma_idTurma) REFERENCES Turma (idTurma)
);

-- Salas
INSERT INTO Sala (numero, capacidade) VALUES
('101', 30),
('102', 25),
('201', 40),
('202', 35),
('LAB1', 20);

-- Professores
INSERT INTO Professor (nome, especialidade, email) VALUES
('Carlos Andrade','Matemática','carlos.andrade@escola.com'),
('Ana Lima','Português','ana.lima@escola.com'),
('Roberto Souza','História','roberto.souza@escola.com'),
('Fernanda Costa','Ciências','fernanda.costa@escola.com'),
('Marcos Pereira','Inglês','marcos.pereira@escola.com');

-- Alunos
INSERT INTO Aluno (nome, idade, email) VALUES
('Lucas Oliveira',15,'lucas.oliveira@aluno.com'),
('Mariana Santos',16,'mariana.santos@aluno.com'),
('João Pedro Silva',15,'joao.silva@aluno.com'),
('Beatriz Rocha',17,'beatriz.rocha@aluno.com'),
('Rafael Mendes',16,'rafael.mendes@aluno.com'),
('Camila Torres',15,'camila.torres@aluno.com');

-- Turmas
INSERT INTO Turma (nome, ano, Sala_idSala) VALUES
('1º Ano A', 2026, 1),
('1º Ano B', 2026, 2),
('2º Ano A', 2026, 3),
('2º Ano B', 2026, 4),
('3º Ano A', 2026, 5);

-- Disciplinas
INSERT INTO Disciplina (nome, carga_horaria, Professor_idProfessor) VALUES
('Matemática',80,1),
('Português',80,2),
('História',60,3),
('Ciências',60,4),
('Inglês',40,5);

-- Grade Curricular
INSERT INTO Grade_Curricular (Turma_idTurma, Disciplina_idDisciplina) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 4), (2, 5),
(3, 2), (3, 3), (3, 4),
(4, 1), (4, 5),
(5, 2), (5, 3);

-- Matrículas
INSERT INTO Matricula (Aluno_idAluno, Turma_idTurma, data_matricula) VALUES
(1,1,'2026-02-01 08:00:00'),
(2,1,'2026-02-01 08:30:00'),
(3,2,'2026-02-02 09:00:00'),
(4,3,'2026-02-02 10:00:00'),
(5,4,'2026-02-03 08:00:00'),
(6,5,'2026-02-03 09:00:00');

-- Usuários
DROP USER IF EXISTS 'coordenador'@'%';
DROP USER IF EXISTS 'secretaria'@'%';

CREATE USER 'coordenador'@'%' IDENTIFIED BY '1234';
CREATE USER 'secretaria'@'%'  IDENTIFIED BY '5678';

-- Role
DROP ROLE IF EXISTS 'adm';
CREATE ROLE 'adm';
GRANT SELECT ON escola_db.* TO 'adm';
GRANT INSERT ON escola_db.* TO 'adm';
GRANT UPDATE ON escola_db.* TO 'adm';
GRANT 'adm' TO 'coordenador'@'%', 'secretaria'@'%';
ALTER USER 'coordenador'@'%' DEFAULT ROLE 'adm';
ALTER USER 'secretaria'@'%'  DEFAULT ROLE 'adm';


-- Procedure Lista todos os alunos matriculados em uma turma específica
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alunos_por_turma$$
CREATE PROCEDURE sp_alunos_por_turma(IN p_idTurma INT)
BEGIN
SELECT
a.idAluno,
a.nome AS aluno,
a.email,
t.nome AS turma,
m.data_matricula
FROM Matricula m
JOIN Aluno a ON a.idAluno = m.Aluno_idAluno
JOIN Turma t ON t.idTurma = m.Turma_idTurma
WHERE m.Turma_idTurma = p_idTurma
ORDER BY a.nome;
END$$

-- Function Retorna o total de alunos matriculados em uma turma
DROP FUNCTION IF EXISTS fn_total_alunos_turma$$
CREATE FUNCTION fn_total_alunos_turma(p_idTurma INT)
RETURNS INT
DETERMINISTIC
BEGIN
RETURN (SELECT COUNT(*) FROM Matricula WHERE Turma_idTurma = p_idTurma);
END$$


-- TRIGGER registra a data de atualização
DROP TRIGGER IF EXISTS tg_data_matricula$$
CREATE TRIGGER tg_data_matricula
BEFORE INSERT ON Matricula
FOR EACH ROW
BEGIN
IF NEW.data_matricula IS NULL THEN
SET NEW.data_matricula = NOW();
END IF;
END$$

-- VIEW  Visão geral das turmas com professor, sala e total de alunos
DROP VIEW IF EXISTS vw_resumo_turmas$$
CREATE VIEW vw_resumo_turmas AS
SELECT
t.idTurma,
t.nome AS turma,
t.ano,
s.numero AS sala,
s.capacidade,
fn_total_alunos_turma(t.idTurma) AS total_alunos
FROM Turma t
JOIN Sala s ON s.idSala = t.Sala_idSala$$
DELIMITER ;


SELECT * FROM vw_resumo_turmas;
CALL sp_alunos_por_turma(1);
SELECT fn_total_alunos_turma(1) AS total_alunos_turma1;
