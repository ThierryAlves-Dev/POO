package CRUDNUVEM.atv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Banco{

	// ===== CREDENCIAIS DO RAILWAY =====
	// Host, porta e senha removidos por segurança, pois o repositório é público.
	private static final String HOST = "[REMOVIDO POR SEGURANÇA]";
	private static final String PORTA = "[REMOVIDA POR SEGURANÇA]";
	private static final String BANCO = "railway";
	private static final String USUARIO = "root";
	private static final String SENHA = "[REMOVIDA POR SEGURANÇA]";

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		String url = "jdbc:mysql://" + HOST + ":" + PORTA + "/" + BANCO;

		try {
			Connection conexao = DriverManager.getConnection(url, USUARIO, SENHA);
			System.out.println("Conectado ao Railway!");

			// Cria a tabela caso ainda não exista no banco do Railway
			Statement criarTabela = conexao.createStatement();
			criarTabela.executeUpdate(
					"CREATE TABLE IF NOT EXISTS produto (" +
					"codigo INT, " +
					"nome VARCHAR(100) NOT NULL, " +
					"preco DECIMAL(10,2) NOT NULL)"
			);

			int opcao = -1;

			while (opcao != 0) {

				System.out.println(
						"1 - Inserir | " +
						"2 - Listar | " +
						"3 - Atualizar | " +
						"4 - Deletar | " +
						"0 - Sair"
				);

				opcao = sc.nextInt();
				sc.nextLine();

				switch (opcao) {

				// INSERIR
				case 1:
					System.out.print("Nome do produto : ");
					String nome = sc.nextLine();

					System.out.print("Código do produto : ");
					int codigo = sc.nextInt();

					System.out.print("Preço do produto : ");
					float preco = sc.nextFloat();
					sc.nextLine();

					String sql = "INSERT INTO produto (nome, codigo, preco) VALUES (?,?,?)";
					PreparedStatement stmt = conexao.prepareStatement(sql);
					stmt.setString(1, nome);
					stmt.setInt(2, codigo);
					stmt.setFloat(3, preco);
					stmt.executeUpdate();

					System.out.println("Produto inserido com sucesso!!!");
					break;

				// CONSULTAR
				case 2:
					System.out.println("Digite o código do produto : ");
					int codigo2 = sc.nextInt();
					sc.nextLine();

					String sql2 = "SELECT * FROM produto WHERE codigo = ?";
					PreparedStatement stmt2 = conexao.prepareStatement(sql2);
					stmt2.setInt(1, codigo2);

					ResultSet rs = stmt2.executeQuery();
					boolean encontrou = false;

					while (rs.next()) {
						encontrou = true;
						System.out.println(
								"Nome Produto: " + rs.getString("nome")
								+ " | Preço Produto: " + rs.getFloat("preco")
								+ " | Código Produto: " + rs.getInt("codigo")
						);
					}

					if (!encontrou) {
						System.out.println("Produto não encontrado!!!");
					}
					break;

				// ATUALIZAR PREÇO
				case 3:
					System.out.println("Digite o código do Produto que deseja atualizar o preço : ");
					int codigobusca = sc.nextInt();

					System.out.println("Digite o NOVO PREÇO : ");
					float novopreco = sc.nextFloat();
					sc.nextLine();

					String sqlupd = "UPDATE produto SET preco = ? WHERE codigo = ?";
					PreparedStatement stmtUpd = conexao.prepareStatement(sqlupd);
					stmtUpd.setFloat(1, novopreco);
					stmtUpd.setInt(2, codigobusca);

					int linhasAfetadas = stmtUpd.executeUpdate();

					if (linhasAfetadas > 0) {
						System.out.println("Preço Atualizado!!!");
					} else {
						System.out.println("Produto não encontrado!!!");
					}
					break;

				// DELETAR
				case 4:
					System.out.println("Digite o Código do Produto que deseja deletar");
					int codigoDel = sc.nextInt();
					sc.nextLine();

					String sqlDel = "DELETE FROM produto WHERE codigo = ?";
					PreparedStatement stmtDel = conexao.prepareStatement(sqlDel);
					stmtDel.setInt(1, codigoDel);

					int removidos = stmtDel.executeUpdate();

					if (removidos > 0) {
						System.out.println("Produto removido!");
					} else {
						System.out.println("Produto não encontrado!!!");
					}
					break;

				// SAIR
				case 0:
					System.out.println("Programa Finalizado!");
					break;

				default:
					System.out.println("Opção inválida!");
				}
			}

			conexao.close();

		} catch (Exception e) {
			System.out.println("Erro de Cadastro " + e.getMessage());
		}

		sc.close();
	}
}