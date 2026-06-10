package main;

import dao.*;
import model.*;
import model.GradeCurricular;
import dao.GradeCurricularDAO;
import model.Matricula;
import dao.MatriculaDAO;
import dao.RelatorioDAO;



import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlunoDAO alunoDao = new AlunoDAO();
        ProfessorDAO professorDao = new ProfessorDAO();
        SalaDAO salaDao = new SalaDAO();
        TurmaDAO turmaDao = new TurmaDAO();
        DisciplinaDAO disciplinaDao = new DisciplinaDAO();
        GradeCurricularDAO gradeDao = new GradeCurricularDAO();
        MatriculaDAO matriculaDao = new MatriculaDAO();
        RelatorioDAO relatorioDao = new RelatorioDAO();

        int opcao = -1;

        System.out.println("=======================================");
        System.out.println("   SISTEMA DE GERENCIAMENTO ESCOLAR    ");
        System.out.println("=======================================");

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Tabela do Aluno");
            System.out.println("2 - Tabela do Professor");
            System.out.println("3 - Tabela da Sala");
            System.out.println("4 - Tabela da Turma");
            System.out.println("5 - Tabela da Disciplina");
            System.out.println("6 - Tabela da Grade Curricular");
            System.out.println("7 - Tabela Matricula");
            System.out.println("8 - Relatórios (JOINs)");
            System.out.println("0 - Sair do Sistema");

            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1:
                    int opcaoAluno = -1;
                    while (opcaoAluno != 0) {
                        System.out.println("\n--- MENU DA TABELA ALUNO ---");
                        System.out.println("1 - Cadastrar Novo Aluno");
                        System.out.println("2 - Listar Todos os Alunos");
                        System.out.println("3 - Consultar Alunos por Turma");
                        System.out.println("4 - Atualizar Aluno");
                        System.out.println("5 - Deletar Aluno");
                        System.out.println("6 - Buscar Aluno por Nome");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");

                        try {
                            opcaoAluno = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida! Digite apenas números.");
                            continue;
                        }

                        switch (opcaoAluno) {
                            case 1:
                                System.out.println("\n[ NOVO CADASTRO ]");
                                System.out.print("Nome: ");
                                String nome = scanner.nextLine();
                                System.out.print("Idade: ");
                                int idadem = Integer.parseInt(scanner.nextLine());
                                System.out.print("Email: ");
                                String email = scanner.nextLine();

                                Aluno novoAluno = new Aluno(nome, idadem, email);
                                alunoDao.salvar(novoAluno);
                                break;

                            case 2:
                                System.out.println("\n[ ALUNOS CADASTRADOS ]");
                                var alunos = alunoDao.listarTodos();
                                if (alunos.isEmpty()) {
                                    System.out.println("Nenhum aluno encontrado.");
                                } else {
                                    for (Aluno a : alunos) {
                                        System.out.printf("ID: %d | Nome: %-20s | Idade: %d | Email: %s\n",
                                                a.getIdAluno(), a.getNome(), a.getIdade(), a.getEmail());
                                    }
                                }
                                break;

                            case 3:
                                System.out.println("\n[ CONSULTAR TURMA ]");
                                System.out.print("Digite o ID da Turma: ");
                                int idTurma = Integer.parseInt(scanner.nextLine());
                                System.out.println("\nBuscando dados da Stored Procedure...");
                                alunoDao.listarAlunosPorTurma(idTurma);
                                break;

                            case 4:
                                System.out.println("\n[ ATUALIZAR ALUNO ]");
                                System.out.print("Digite o ID do Aluno que deseja atualizar: ");
                                int idAtualizar = Integer.parseInt(scanner.nextLine());
                                System.out.print("Novo Nome: ");
                                String novoNome = scanner.nextLine();
                                System.out.print("Nova Idade: ");
                                int novaIdade = Integer.parseInt(scanner.nextLine());
                                System.out.print("Novo Email: ");
                                String novoEmail = scanner.nextLine();

                                Aluno alunoAtualizado = new Aluno(idAtualizar, novoNome, novaIdade, novoEmail);
                                alunoDao.atualizar(alunoAtualizado);
                                break;

                            case 5:
                                System.out.println("\n[ DELETAR ALUNO ]");
                                System.out.print("Digite o ID do Aluno que deseja deletar: ");
                                int idDeletar = Integer.parseInt(scanner.nextLine());
                                alunoDao.deletar(idDeletar);
                                break;

                            case 6:
                                System.out.println("\n[ BUSCAR POR NOME ]");
                                System.out.print("Digite o nome ou parte dele: ");
                                String nomeBusca = scanner.nextLine();
                                var alunosEncontrados = alunoDao.buscarPorNome(nomeBusca);

                                if (alunosEncontrados.isEmpty()) {
                                    System.out.println("Nenhum aluno encontrado.");
                                } else {
                                    for (Aluno a : alunosEncontrados) {
                                        System.out.printf("ID: %d | Nome: %-20s | Idade: %d | Email: %s\n",
                                                a.getIdAluno(), a.getNome(), a.getIdade(), a.getEmail());
                                    }
                                }
                                break;

                            case 0:
                                System.out.println("\nEncerrando o programa... Feito!");
                                break;

                            default:
                                System.out.println("Opção inexistente. Tente novamente.");
                                break;
                        }
                    }
                    opcao = -1;
                    break;

                case 2:
                    int opcaoProf = -1;
                    while (opcaoProf != 0) {
                        System.out.println("\n--- MENU DA TABELA PROFESSOR ---");
                        System.out.println("1 - Cadastrar Novo Professor");
                        System.out.println("2 - Listar Todos os Professores");
                        System.out.println("3 - Atualizar Professor");
                        System.out.println("4 - Deletar Professor");
                        System.out.println("5 - Buscar Professor por Nome");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");

                        try {
                            opcaoProf = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida! Digite apenas números.");
                            continue;
                        }

                        switch (opcaoProf) {
                            case 1:
                                System.out.println("\n[ NOVO CADASTRO DE PROFESSOR ]");
                                System.out.print("Nome: ");
                                String nome = scanner.nextLine();
                                System.out.print("Especialidade: ");
                                String especialidade = scanner.nextLine();
                                System.out.print("Email: ");
                                String email = scanner.nextLine();

                                Professor novoProf = new Professor(nome, especialidade, email);
                                professorDao.salvar(novoProf);
                                break;

                            case 2:
                                System.out.println("\n[ PROFESSORES CADASTRADOS ]");
                                var professores = professorDao.listarTodos();
                                if (professores.isEmpty()) {
                                    System.out.println("Nenhum professor encontrado.");
                                } else {
                                    for (Professor p : professores) {
                                        System.out.printf("ID: %d | Nome: %-20s | Especialidade: %-15s | Email: %s\n",
                                                p.getIdProfessor(), p.getNome(), p.getEspecialidade(), p.getEmail());
                                    }
                                }
                                break;

                            case 3:
                                System.out.println("\n[ ATUALIZAR PROFESSOR ]");
                                System.out.print("Digite o ID do Professor que deseja atualizar: ");
                                int idAtualizar = Integer.parseInt(scanner.nextLine());
                                System.out.print("Novo Nome: ");
                                String novoNome = scanner.nextLine();
                                System.out.print("Nova Especialidade: ");
                                String novaEspecialidade = scanner.nextLine();
                                System.out.print("Novo Email: ");
                                String novoEmail = scanner.nextLine();

                                Professor profAtualizado = new Professor(idAtualizar, novoNome, novaEspecialidade, novoEmail);
                                professorDao.atualizar(profAtualizado);
                                break;

                            case 4:
                                System.out.println("\n[ DELETAR PROFESSOR ]");
                                System.out.print("Digite o ID do Professor que deseja deletar: ");
                                int idDeletar = Integer.parseInt(scanner.nextLine());
                                professorDao.deletar(idDeletar);
                                break;

                            case 5:
                                System.out.println("\n[ BUSCAR PROFESSOR POR NOME ]");
                                System.out.print("Digite o nome ou parte dele: ");
                                String nomeBusca = scanner.nextLine();
                                var profsEncontrados = professorDao.buscarPorNome(nomeBusca);

                                if (profsEncontrados.isEmpty()) {
                                    System.out.println("Nenhum professor encontrado.");
                                } else {
                                    for (Professor p : profsEncontrados) {
                                        System.out.printf("ID: %d | Nome: %-20s | Especialidade: %-15s | Email: %s\n",
                                                p.getIdProfessor(), p.getNome(), p.getEspecialidade(), p.getEmail());
                                    }
                                }
                                break;

                            case 0:
                                System.out.println("\nVoltando ao Menu Principal...");
                                break;

                            default:
                                System.out.println("Opção inexistente. Tente novamente.");
                                break;
                        }
                    }


                    opcao = -1;
                    break;

                case 3:
                    int opcaoSala = -1;
                    while (opcaoSala != 0) {
                        System.out.println("\n--- MENU DA TABELA SALA ---");
                        System.out.println("1 - Cadastrar Nova Sala");
                        System.out.println("2 - Listar Todas as Salas");
                        System.out.println("3 - Atualizar Sala");
                        System.out.println("4 - Deletar Sala");
                        System.out.println("5 - Buscar Sala por Número");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");

                        try {
                            opcaoSala = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida! Digite apenas números.");
                            continue;
                        }

                        switch (opcaoSala) {
                            case 1:
                                System.out.println("\n[ NOVO CADASTRO DE SALA ]");
                                System.out.print("Número da Sala (ex: 101, LAB1): ");
                                String numero = scanner.nextLine();
                                System.out.print("Capacidade (apenas números): ");
                                int capacidade = Integer.parseInt(scanner.nextLine());

                                Sala novaSala = new Sala(numero, capacidade);
                                salaDao.salvar(novaSala);
                                break;

                            case 2:
                                System.out.println("\n[ SALAS CADASTRADAS ]");
                                var salas = salaDao.listarTodas();
                                if (salas.isEmpty()) {
                                    System.out.println("Nenhuma sala encontrada.");
                                } else {
                                    for (Sala s : salas) {
                                        System.out.printf("ID: %-4d | Número: %-10s | Capacidade: %d alunos\n",
                                                s.getIdSala(), s.getNumero(), s.getCapacidade());
                                    }
                                }
                                break;

                            case 3:
                                System.out.println("\n[ ATUALIZAR SALA ]");
                                System.out.print("Digite o ID da Sala que deseja atualizar: ");
                                int idAtualizar = Integer.parseInt(scanner.nextLine());
                                System.out.print("Novo Número: ");
                                String novoNumero = scanner.nextLine();
                                System.out.print("Nova Capacidade: ");
                                int novaCapacidade = Integer.parseInt(scanner.nextLine());

                                Sala salaAtualizada = new Sala(idAtualizar, novoNumero, novaCapacidade);
                                salaDao.atualizar(salaAtualizada);
                                break;

                            case 4:
                                System.out.println("\n[ DELETAR SALA ]");
                                System.out.print("Digite o ID da Sala que deseja deletar: ");
                                int idDeletar = Integer.parseInt(scanner.nextLine());
                                salaDao.deletar(idDeletar);
                                break;

                            case 5:
                                System.out.println("\n[ BUSCAR SALA POR NÚMERO ]");
                                System.out.print("Digite o número da sala ou parte dele: ");
                                String numeroBusca = scanner.nextLine();
                                var salasEncontradas = salaDao.buscarPorNumero(numeroBusca);

                                if (salasEncontradas.isEmpty()) {
                                    System.out.println("Nenhuma sala encontrada com esse número.");
                                } else {
                                    for (Sala s : salasEncontradas) {
                                        System.out.printf("ID: %-4d | Número: %-10s | Capacidade: %d alunos\n",
                                                s.getIdSala(), s.getNumero(), s.getCapacidade());
                                    }
                                }
                                break;

                            case 0:
                                System.out.println("\nVoltando ao Menu Principal...");
                                break;

                            default:
                                System.out.println("Opção inexistente. Tente novamente.");
                                break;
                        }
                    }

                    opcao = -1;
                    break;


                case 4:
                    int opcaoTurma = -1;
                    while (opcaoTurma != 0) {
                        System.out.println("\n--- MENU DA TABELA TURMA ---");
                        System.out.println("1 - Cadastrar Nova Turma");
                        System.out.println("2 - Listar Todas as Turmas");
                        System.out.println("3 - Atualizar Turma");
                        System.out.println("4 - Deletar Turma");
                        System.out.println("5 - Buscar Turma por Nome");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");

                        try {
                            opcaoTurma = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida! Digite apenas números.");
                            continue;
                        }

                        switch (opcaoTurma) {
                            case 1:
                                System.out.println("\n[ NOVO CADASTRO DE TURMA ]");
                                System.out.print("Nome da Turma (ex: 1º Ano A): ");
                                String nome = scanner.nextLine();
                                System.out.print("Ano: ");
                                int ano = Integer.parseInt(scanner.nextLine());
                                System.out.print("ID da Sala vinculada: ");
                                int idSala = Integer.parseInt(scanner.nextLine());

                                Turma novaTurma = new Turma(nome, ano, idSala);
                                turmaDao.salvar(novaTurma);
                                break;

                            case 2:
                                System.out.println("\n[ TURMAS CADASTRADAS ]");
                                var turmas = turmaDao.listarTodas();
                                if (turmas.isEmpty()) {
                                    System.out.println("Nenhuma turma encontrada.");
                                } else {
                                    for (Turma t : turmas) {
                                        System.out.printf("ID: %-4d | Nome: %-15s | Ano: %d | ID Sala: %d\n",
                                                t.getIdTurma(), t.getNome(), t.getAno(), t.getSalaIdSala());
                                    }
                                }
                                break;

                            case 3:
                                System.out.println("\n[ ATUALIZAR TURMA ]");
                                System.out.print("Digite o ID da Turma que deseja atualizar: ");
                                int idAtualizar = Integer.parseInt(scanner.nextLine());
                                System.out.print("Novo Nome da Turma: ");
                                String novoNome = scanner.nextLine();
                                System.out.print("Novo Ano: ");
                                int novoAno = Integer.parseInt(scanner.nextLine());
                                System.out.print("Novo ID da Sala vinculada: ");
                                int novoIdSala = Integer.parseInt(scanner.nextLine());

                                Turma turmaAtualizada = new Turma(idAtualizar, novoNome, novoAno, novoIdSala);
                                turmaDao.atualizar(turmaAtualizada);
                                break;

                            case 4:
                                System.out.println("\n[ DELETAR TURMA ]");
                                System.out.print("Digite o ID da Turma que deseja deletar: ");
                                int idDeletar = Integer.parseInt(scanner.nextLine());
                                turmaDao.deletar(idDeletar);
                                break;

                            case 5:
                                System.out.println("\n[ BUSCAR TURMA POR NOME ]");
                                System.out.print("Digite o nome da turma ou parte dele: ");
                                String nomeBusca = scanner.nextLine();
                                var turmasEncontradas = turmaDao.buscarPorNome(nomeBusca);

                                if (turmasEncontradas.isEmpty()) {
                                    System.out.println("Nenhuma turma encontrada com esse nome.");
                                } else {
                                    for (Turma t : turmasEncontradas) {
                                        System.out.printf("ID: %-4d | Nome: %-15s | Ano: %d | ID Sala: %d\n",
                                                t.getIdTurma(), t.getNome(), t.getAno(), t.getSalaIdSala());
                                    }
                                }
                                break;

                            case 0:
                                System.out.println("\nVoltando ao Menu Principal...");
                                break;

                            default:
                                System.out.println("Opção inexistente. Tente novamente.");
                                break;
                        }
                    }

                    opcao = -1;
                    break;

                case 5:
                    int opcaoDisciplina = -1;
                    while (opcaoDisciplina != 0) {
                        System.out.println("\n--- MENU DA TABELA DISCIPLINA ---");
                        System.out.println("1 - Cadastrar Nova Disciplina");
                        System.out.println("2 - Listar Todas as Disciplinas");
                        System.out.println("3 - Atualizar Disciplina");
                        System.out.println("4 - Deletar Disciplina");
                        System.out.println("5 - Buscar Disciplina por Nome");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");

                        try {
                            opcaoDisciplina = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida! Digite apenas números.");
                            continue;
                        }

                        switch (opcaoDisciplina) {
                            case 1:
                                System.out.println("\n[ NOVO CADASTRO DE DISCIPLINA ]");
                                System.out.print("Nome da Disciplina (ex: Matemática): ");
                                String nome = scanner.nextLine();
                                System.out.print("Carga Horária: ");
                                int carga = Integer.parseInt(scanner.nextLine());
                                System.out.print("ID do Professor vinculado: ");
                                int idProf = Integer.parseInt(scanner.nextLine());

                                Disciplina novaDisciplina = new Disciplina(nome, carga, idProf);
                                disciplinaDao.salvar(novaDisciplina);
                                break;

                            case 2:
                                System.out.println("\n[ DISCIPLINAS CADASTRADAS ]");
                                var disciplinas = disciplinaDao.listarTodas();
                                if (disciplinas.isEmpty()) {
                                    System.out.println("Nenhuma disciplina encontrada.");
                                } else {
                                    for (Disciplina d : disciplinas) {
                                        System.out.printf("ID: %-4d | Nome: %-15s | Carga: %-3dh | ID Prof: %d\n",
                                                d.getIdDisciplina(), d.getNome(), d.getCargaHoraria(), d.getProfessorIdProfessor());
                                    }
                                }
                                break;

                            case 3:
                                System.out.println("\n[ ATUALIZAR DISCIPLINA ]");
                                System.out.print("Digite o ID da Disciplina que deseja atualizar: ");
                                int idAtualizar = Integer.parseInt(scanner.nextLine());
                                System.out.print("Novo Nome: ");
                                String novoNome = scanner.nextLine();
                                System.out.print("Nova Carga Horária: ");
                                int novaCarga = Integer.parseInt(scanner.nextLine());
                                System.out.print("Novo ID do Professor vinculado: ");
                                int novoIdProf = Integer.parseInt(scanner.nextLine());

                                Disciplina discAtualizada = new Disciplina(idAtualizar, novoNome, novaCarga, novoIdProf);
                                disciplinaDao.atualizar(discAtualizada);
                                break;

                            case 4:
                                System.out.println("\n[ DELETAR DISCIPLINA ]");
                                System.out.print("Digite o ID da Disciplina que deseja deletar: ");
                                int idDeletar = Integer.parseInt(scanner.nextLine());
                                disciplinaDao.deletar(idDeletar);
                                break;

                            case 5:
                                System.out.println("\n[ BUSCAR DISCIPLINA POR NOME ]");
                                System.out.print("Digite o nome da disciplina ou parte dele: ");
                                String nomeBusca = scanner.nextLine();
                                var discEncontradas = disciplinaDao.buscarPorNome(nomeBusca);

                                if (discEncontradas.isEmpty()) {
                                    System.out.println("Nenhuma disciplina encontrada com esse nome.");
                                } else {
                                    for (Disciplina d : discEncontradas) {
                                        System.out.printf("ID: %-4d | Nome: %-15s | Carga: %-3dh | ID Prof: %d\n",
                                                d.getIdDisciplina(), d.getNome(), d.getCargaHoraria(), d.getProfessorIdProfessor());
                                    }
                                }
                                break;

                            case 0:
                                System.out.println("\nVoltando ao Menu Principal...");
                                break;

                            default:
                                System.out.println("Opção inexistente. Tente novamente.");
                                break;
                        }
                    }

                    opcao = -1;
                    break;

                case 6:
                    int opcaoGrade = -1;
                    while (opcaoGrade != 0) {
                        System.out.println("\n--- MENU DA GRADE CURRICULAR ---");
                        System.out.println("1 - Vincular Disciplina a uma Turma");
                        System.out.println("2 - Listar Toda a Grade Curricular");
                        System.out.println("3 - Atualizar Disciplina de uma Turma");
                        System.out.println("4 - Deletar Vínculo da Grade");
                        System.out.println("5 - Buscar Grade por ID da Turma");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");

                        try {
                            opcaoGrade = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida! Digite apenas números.");
                            continue;
                        }

                        switch (opcaoGrade) {
                            case 1:
                                System.out.println("\n[ NOVA ASSOCIAÇÃO NA GRADE ]");
                                System.out.print("ID da Turma: ");
                                int idTurma = Integer.parseInt(scanner.nextLine());
                                System.out.print("ID da Disciplina: ");
                                int idDisc = Integer.parseInt(scanner.nextLine());

                                GradeCurricular novaGrade = new GradeCurricular(idTurma, idDisc);
                                gradeDao.salvar(novaGrade);
                                break;

                            case 2:
                                System.out.println("\n[ GRADE CURRICULAR CADASTRADA ]");
                                var grades = gradeDao.listarTodas();
                                if (grades.isEmpty()) {
                                    System.out.println("Nenhum registro encontrado.");
                                } else {
                                    for (GradeCurricular g : grades) {
                                        System.out.printf("ID Turma: %d | ID Disciplina: %d\n",
                                                g.getTurmaIdTurma(), g.getDisciplinaIdDisciplina());
                                    }
                                }
                                break;

                            case 3:
                                System.out.println("\n[ ATUALIZAR GRADE CURRICULAR ]");
                                System.out.print("Digite o ID da Turma que será atualizada: ");
                                int idTurmaAtualizar = Integer.parseInt(scanner.nextLine());
                                System.out.print("Digite o ID da Disciplina ANTIGA que deseja remover: ");
                                int idDiscAntiga = Integer.parseInt(scanner.nextLine());
                                System.out.print("Digite o ID da Disciplina NOVA que vai entrar no lugar: ");
                                int idDiscNova = Integer.parseInt(scanner.nextLine());

                                gradeDao.atualizar(idTurmaAtualizar, idDiscAntiga, idDiscNova);
                                break;

                            case 4:
                                System.out.println("\n[ DELETAR DA GRADE CURRICULAR ]");
                                System.out.print("Digite o ID da Turma: ");
                                int idTurmaDel = Integer.parseInt(scanner.nextLine());
                                System.out.print("Digite o ID da Disciplina que será removida dessa turma: ");
                                int idDiscDel = Integer.parseInt(scanner.nextLine());

                                gradeDao.deletar(idTurmaDel, idDiscDel);
                                break;

                            case 5:
                                System.out.println("\n[ BUSCAR GRADE POR TURMA ]");
                                System.out.print("Digite o ID da Turma: ");
                                int idTurmaBusca = Integer.parseInt(scanner.nextLine());
                                var gradesEncontradas = gradeDao.buscarPorTurma(idTurmaBusca);

                                if (gradesEncontradas.isEmpty()) {
                                    System.out.println("Nenhum registro encontrado para essa turma.");
                                } else {
                                    for (GradeCurricular g : gradesEncontradas) {
                                        System.out.printf("ID Turma: %d | ID Disciplina: %d\n",
                                                g.getTurmaIdTurma(), g.getDisciplinaIdDisciplina());
                                    }
                                }
                                break;

                            case 0:
                                System.out.println("\nVoltando ao Menu Principal...");
                                break;

                            default:
                                System.out.println("Opção inexistente. Tente novamente.");
                                break;
                        }
                    }

                    opcao = -1;
                    break;

                case 7:
                    int opcaoMatricula = -1;
                    while (opcaoMatricula != 0) {
                        System.out.println("\n--- MENU DE MATRÍCULAS ---");
                        System.out.println("1 - Matricular Aluno em uma Turma");
                        System.out.println("2 - Listar Todas as Matrículas");
                        System.out.println("3 - Transferir Aluno de Turma (Atualizar)");
                        System.out.println("4 - Cancelar Matrícula (Deletar)");
                        System.out.println("5 - Buscar Matrículas por ID do Aluno");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");

                        try {
                            opcaoMatricula = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida! Digite apenas números.");
                            continue;
                        }

                        switch (opcaoMatricula) {
                            case 1:
                                System.out.println("\n[ NOVA MATRÍCULA ]");
                                System.out.print("ID do Aluno: ");
                                int idAluno = Integer.parseInt(scanner.nextLine());
                                System.out.print("ID da Turma: ");
                                int idTurma = Integer.parseInt(scanner.nextLine());

                                Matricula novaMatricula = new Matricula(idAluno, idTurma);
                                matriculaDao.salvar(novaMatricula);
                                break;

                            case 2:
                                System.out.println("\n[ MATRÍCULAS REGISTRADAS ]");
                                var lista = matriculaDao.listarTodas();
                                if (lista.isEmpty()) {
                                    System.out.println("Nenhuma matrícula encontrada.");
                                } else {
                                    for (Matricula m : lista) {
                                        System.out.printf("ID Aluno: %-4d | ID Turma: %-4d | Data: %s\n",
                                                m.getAlunoIdAluno(), m.getTurmaIdTurma(), m.getDataMatricula());
                                    }
                                }
                                break;

                            case 3:
                                System.out.println("\n[ TRANSFERIR ALUNO ]");
                                System.out.print("Digite o ID do Aluno: ");
                                int idAluTrans = Integer.parseInt(scanner.nextLine());
                                System.out.print("Digite o ID da Turma ATUAL: ");
                                int idTurAntiga = Integer.parseInt(scanner.nextLine());
                                System.out.print("Digite o ID da NOVA Turma destino: ");
                                int idTurNova = Integer.parseInt(scanner.nextLine());

                                matriculaDao.atualizar(idAluTrans, idTurAntiga, idTurNova);
                                break;

                            case 4:
                                System.out.println("\n[ CANCELAR MATRÍCULA ]");
                                System.out.print("Digite o ID do Aluno: ");
                                int idAluDel = Integer.parseInt(scanner.nextLine());
                                System.out.print("Digite o ID da Turma: ");
                                int idTurDel = Integer.parseInt(scanner.nextLine());

                                matriculaDao.deletar(idAluDel, idTurDel);
                                break;

                            case 5:
                                System.out.println("\n[ BUSCAR MATRÍCULAS POR ALUNO ]");
                                System.out.print("Digite o ID do Aluno: ");
                                int idAluBusca = Integer.parseInt(scanner.nextLine());
                                var encontradas = matriculaDao.buscarPorAluno(idAluBusca);

                                if (encontradas.isEmpty()) {
                                    System.out.println("Nenhuma matrícula ativa para este aluno.");
                                } else {
                                    for (Matricula m : encontradas) {
                                        System.out.printf("ID Aluno: %-4d | ID Turma: %-4d | Data: %s\n",
                                                m.getAlunoIdAluno(), m.getTurmaIdTurma(), m.getDataMatricula());
                                    }
                                }
                                break;

                            case 0:
                                System.out.println("\nVoltando ao Menu Principal...");
                                break;

                            default:
                                System.out.println("Opção inexistente. Tente novamente.");
                                break;
                        }
                    }

                    opcao = -1;
                    break;

                case 8:
                    int opcaoRelatorio = -1;
                    while (opcaoRelatorio != 0) {
                        System.out.println("\n--- MENU DE RELATÓRIOS ---");
                        System.out.println("1 - Relatório de Turmas e Salas");
                        System.out.println("2 - Relatório de Disciplinas e Professores");
                        System.out.println("3 - Relatório da Grade Curricular Completa");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha um relatório: ");

                        try {
                            opcaoRelatorio = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Opção inválida! Digite apenas números.");
                            continue;
                        }

                        switch (opcaoRelatorio) {
                            case 1:
                                relatorioDao.relatorioTurmasESalas();
                                break;
                            case 2:
                                relatorioDao.relatorioDisciplinasEProfessores();
                                break;
                            case 3:
                                relatorioDao.relatorioGradeCurricularCompleta();
                                break;
                            case 0:
                                System.out.println("\nVoltando ao Menu Principal...");
                                break;
                            default:
                                System.out.println("Opção inexistente.");
                                break;
                        }
                    }

                    opcao = -1;
                    break;

                case 0:
                    System.out.println("\nEncerrando o programa... Feito!");
                    break;

                default:
                    System.out.println("Opção inexistente. Tente novamente.");
                    break;
            }
        }
        scanner.close();
    }
}