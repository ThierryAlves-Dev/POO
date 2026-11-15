import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Calculadora {

	private JFrame frame;
	private JTextField JTFResultado;
	private double valor1 = 0;
	private String op = "";
	private boolean novoNumero = true;

	private static final int MARGEM = 25;
	private static final int TOPO = 110;
	private static final int LARGURA = 65;
	private static final int ALTURA = 55;
	private static final int ESPACO = 10;

	private static final Color COR_FUNDO = new Color(243, 230, 240);
	private static final Color COR_NUMERO = new Color(110, 40, 95);
	private static final Color COR_OPERACAO = new Color(0x260220);
	private static final Color COR_TEXTO = Color.WHITE;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					Calculadora window = new Calculadora();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Calculadora() {
		initialize();
	}

	// Métodos das operações

	private double adicao(double a, double b) {
		return a + b;
	}

	private double subtracao(double a, double b) {
		return a - b;
	}

	private double multiplicacao(double a, double b) {
		return a * b;
	}

	private double divisao(double a, double b) {
		return a / b;
	}

	private double potencia(double base, double expoente) {
		return Math.pow(base, expoente);
	}

	// Métodos de apoio

	private JButton criarBotao(String texto, int coluna, int linha, ActionListener acao) {
		return criarBotao(texto, coluna, linha, 1, 1, acao);
	}

	private JButton criarBotao(String texto, int coluna, int linha, int colunas, int linhas, ActionListener acao) {
		int x = MARGEM + coluna * (LARGURA + ESPACO);
		int y = TOPO + linha * (ALTURA + ESPACO);
		int largura = colunas * LARGURA + (colunas - 1) * ESPACO;
		int altura = linhas * ALTURA + (linhas - 1) * ESPACO;

		JButton botao = new JButton(texto);
		botao.addActionListener(acao);
		botao.setFont(new Font("Arial", Font.BOLD, 16));
		botao.setBounds(x, y, largura, altura);
		if ("0123456789.".contains(texto)) {
			botao.setBackground(COR_NUMERO);
		} else {
			botao.setBackground(COR_OPERACAO);
		}
		botao.setForeground(COR_TEXTO);
		botao.setFocusPainted(false);
		frame.getContentPane().add(botao);
		return botao;
	}

	private void digitar(String d) {
		if (novoNumero) {
			JTFResultado.setText(d);
			novoNumero = false;
		} else {
			JTFResultado.setText(JTFResultado.getText() + d);
		}
	}

	private void digitarPonto() {
		if (novoNumero) {
			JTFResultado.setText("0.");
			novoNumero = false;
		} else if (!JTFResultado.getText().contains(".")) {
			JTFResultado.setText(JTFResultado.getText() + ".");
		}
	}

	private void escolherOperacao(String novaOp) {
		String texto = JTFResultado.getText();
		if (texto.isEmpty() || texto.equals("Erro")) {
			return;
		}
		if (!op.isEmpty() && !novoNumero) {
			calcular();
			if (JTFResultado.getText().equals("Erro")) {
				return;
			}
		}
		valor1 = Double.parseDouble(JTFResultado.getText());
		op = novaOp;
		novoNumero = true;
	}

	private void calcular() {
		String texto = JTFResultado.getText();
		if (op.isEmpty() || texto.isEmpty() || texto.equals("Erro")) {
			return;
		}

		double valor2 = Double.parseDouble(texto);
		double resultado = 0;

		if (op.equals("Adicao")) {
			resultado = adicao(valor1, valor2);
		} else if (op.equals("Subtracao")) {
			resultado = subtracao(valor1, valor2);
		} else if (op.equals("Multiplicacao")) {
			resultado = multiplicacao(valor1, valor2);
		} else if (op.equals("Potencia")) {
			resultado = potencia(valor1, valor2);
		} else if (op.equals("Divisao")) {
			if (valor2 == 0) {
				JTFResultado.setText("Erro");
				op = "";
				novoNumero = true;
				return;
			}
			resultado = divisao(valor1, valor2);
		}

		if (resultado == (long) resultado) {
			JTFResultado.setText(String.valueOf((long) resultado));
		} else {
			JTFResultado.setText(String.valueOf(resultado));
		}
		valor1 = resultado;
		op = "";
		novoNumero = true;
	}

	private void limpar() {
		JTFResultado.setText("");
		valor1 = 0;
		op = "";
		novoNumero = true;
	}

	// Janela

	private void initialize() {
		frame = new JFrame("Thierry Alves Mota Justino - RA 0023636");
		frame.setBounds(100, 100, 356, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(COR_FUNDO);

		JTFResultado = new JTextField();
		JTFResultado.setBackground(Color.WHITE);
		JTFResultado.setForeground(COR_OPERACAO);
		JTFResultado.setBounds(MARGEM, 20, 290, 70);
		JTFResultado.setFont(new Font("Arial", Font.BOLD, 28));
		JTFResultado.setHorizontalAlignment(JTextField.RIGHT);
		JTFResultado.setEditable(false);
		frame.getContentPane().add(JTFResultado);
		JTFResultado.setColumns(10);

		criarBotao("C", 0, 0, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		criarBotao("^", 1, 0, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolherOperacao("Potencia");
			}
		});
		criarBotao("/", 2, 0, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolherOperacao("Divisao");
			}
		});
		criarBotao("*", 3, 0, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolherOperacao("Multiplicacao");
			}
		});

		criarBotao("7", 0, 1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("7");
			}
		});
		criarBotao("8", 1, 1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("8");
			}
		});
		criarBotao("9", 2, 1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("9");
			}
		});
		criarBotao("-", 3, 1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolherOperacao("Subtracao");
			}
		});

		criarBotao("4", 0, 2, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("4");
			}
		});
		criarBotao("5", 1, 2, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("5");
			}
		});
		criarBotao("6", 2, 2, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("6");
			}
		});
		criarBotao("+", 3, 2, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolherOperacao("Adicao");
			}
		});

		criarBotao("1", 0, 3, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("1");
			}
		});
		criarBotao("2", 1, 3, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("2");
			}
		});
		criarBotao("3", 2, 3, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("3");
			}
		});
		criarBotao("=", 3, 3, 1, 2, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcular();
			}
		});

		criarBotao("0", 0, 4, 2, 1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitar("0");
			}
		});
		criarBotao(".", 2, 4, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				digitarPonto();
			}
		});
	}
}