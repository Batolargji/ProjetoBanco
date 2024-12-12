package sistemaBancario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Banco {
    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Conta> contas = new ArrayList<>();
    private Scanner entrada = new Scanner(System.in);
    private static final String PASTA_USUARIOS = "usuarios/";

    public Banco() {
        criarPastaUsuarios();
        carregarUsuarios();
    }

    public void iniciarSistema() {
        System.out.println("Bem-vindo ao Sistema Bancário!");
        boolean sair = false;
        while (!sair) {
            System.out.println("1. Fazer Login");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    Usuario usuarioLogado = fazerLogin();
                    if (usuarioLogado != null) {
                        System.out.println("Usuário logado como: " + usuarioLogado.getClass().getSimpleName());
                        usuarioLogado.acessarSistema();
                    }
                    break;
                case 2:
                    sair = true;
                    System.out.println("Obrigado por usar o Sistema Bancário!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private Usuario fazerLogin() {
        System.out.print("Digite o CPF do usuário: ");
        String cpf = entrada.nextLine();
        System.out.print("Digite a senha do usuário: ");
        String senha = entrada.nextLine();

        String arquivoUsuario = PASTA_USUARIOS + cpf + ".txt";
        File arquivo = new File(arquivoUsuario);

        if (arquivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                String tipo = "";
                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("Senha: ") && linha.substring(7).equals(senha)) {
                        System.out.println("Login realizado com sucesso!");
                    }
                    if (linha.startsWith("Tipo de Usuário: ")) {
                        tipo = linha.substring(17);
                    }
                }
                return criarUsuario(tipo, cpf);
            } catch (IOException e) {
                System.out.println("Erro ao ler arquivo do usuário: " + e.getMessage());
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }
        return null;
    }

    private Usuario criarUsuario(String tipo, String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }
        
        Usuario novoUsuario = null;
        switch (tipo) {
        	case "3":
            novoUsuario = new Cliente("Nome Exemplo", "senha", cpf, "telefone", "email@exemplo.com", "Rua Exemplo", "123", "Bairro Exemplo", "Cidade Exemplo", "UF");
            	break;
            case "1":
                novoUsuario = new Gerente("Nome Exemplo", "senha", cpf, "telefone", "email@exemplo.com", "Rua Exemplo", "123", "Bairro Exemplo", "Cidade Exemplo", "UF");
                break;
            case "2":
                novoUsuario = new Bancario("Nome Exemplo", "senha", cpf, "telefone", "email@exemplo.com", "Rua Exemplo", "123", "Bairro Exemplo", "Cidade Exemplo", "UF");
                break;
            default:
                System.out.println("Tipo de usuário desconhecido: " + tipo);
        }

        if (novoUsuario != null) {
            usuarios.add(novoUsuario);
        }

        return novoUsuario;
    }

    public static void adicionarUsuario(Usuario usuario, ContaCorrentePrincipal contaCorrente, ContaPoupanca contaPoupanca) {
        usuarios.add(usuario);
        RegistroUtils.criarArquivoUsuario(usuario,contaCorrente,contaPoupanca);
        System.out.println("Usuário adicionado ao sistema.");
    }

    public static void adicionarConta(Conta conta) {
        contas.add(conta);
        System.out.println("Conta adicionada ao sistema.");
    }

    public static void listarTodasAsContas() {
        for (Conta conta : contas) {
            conta.exibirDetalhesConta();
        }
    }
    
    /*public static Conta buscarContaPorId(String idConta) {
        Scanner input = new Scanner(System.in);
        System.out.print("Digite o CPF do titular da conta: ");
        String cpf = input.nextLine().replaceAll("[^\\d]", ""); // Remove caracteres especiais do CPF
        String arquivoUsuario = PASTA_USUARIOS + cpf + ".txt";
        File arquivo = new File(arquivoUsuario);

        if (arquivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("ID da Conta Corrente: ")) {
                        String id = linha.substring("ID da Conta Corrente: ".length()).trim();
                        if (id.equals(idConta)) {
                            return criarContaCorrente(idConta);
                        }
                    } else if (linha.startsWith("ID da Conta Poupança: ")) {
                        String id = linha.substring("ID da Conta Poupança: ".length()).trim();
                        if (id.equals(idConta)) {
                            return criarContaPoupanca(idConta);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo de contas. " + e.getMessage());
            }
        } else {
            System.out.println("Conta não encontrada para o CPF informado.");
        }
        return null;
    }*/

   /* public static void carregarSaldoConta(String idConta, String cpf) {
    	 Scanner input = new Scanner(System.in);
         String arquivo = "usuarios/" + cpf + ".txt";
         System.out.println("Entrei aqui. ");
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Saldo da conta corrente: ")) {
                	ContaCorrentePrincipal conta = (ContaCorrentePrincipal) criarContaCorrente(idConta);
                    double novoSaldo = Double.parseDouble(linha);
                    conta.saldo = novoSaldo;
                } else if (linha.startsWith("Saldo da conta poupança: ")) {
                	ContaPoupanca conta = (ContaPoupanca) criarContaPoupanca(idConta);
                    double novoSaldo = Double.parseDouble(linha);
                    conta.saldo = novoSaldo;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar saldo da conta: " + e.getMessage());
        }
    }*/
    
    /*public static Conta buscarContaPorId(String idConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta().equals(idConta)) {
                carregarSaldoConta(conta); // Atualiza o saldo ao buscar a conta
                return conta;
            }
        }
        System.out.println("Conta não encontrada para o ID fornecido.");
        return null;
    }*/
    
    public static Conta buscarContaPorId(String idConta) {
        File pasta = new File(PASTA_USUARIOS);
        if (!pasta.exists() || !pasta.isDirectory()) {
            System.out.println("Pasta de usuários não encontrada.");
            return null;
        }

        for (File arquivo : pasta.listFiles()) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                String cpf = "";
                String idCorrente = "";
                String idPoupanca = "";
                double saldoCorrente = 0;
                double saldoPoupanca = 0;
                double limiteChequeEspecial = 0;

                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("CPF: ")) cpf = linha.substring(5).trim();
                    else if (linha.startsWith("ID da Conta Corrente: ")) idCorrente = linha.substring(22).trim();
                    else if (linha.startsWith("Saldo da conta corrente: ")) saldoCorrente = Double.parseDouble(linha.substring(25).trim());
                    else if (linha.startsWith("Limite do cheque especial: ")) limiteChequeEspecial = Double.parseDouble(linha.substring(27).trim());
                    else if (linha.startsWith("ID da Conta Poupança: ")) idPoupanca = linha.substring(21).trim();
                    else if (linha.startsWith("Saldo da conta poupança: ")) saldoPoupanca = Double.parseDouble(linha.substring(24).trim());
                }

                if (idCorrente.equals(idConta)) {
                    ContaCorrentePrincipal contaCorrente = new ContaCorrentePrincipal(idCorrente, saldoCorrente, limiteChequeEspecial);
                    contaCorrente.setNumeroConta(cpf); // Associa o CPF ao número da conta
                    return contaCorrente;
                }

                if (idPoupanca.equals(idConta)) {
                    ContaPoupanca contaPoupanca = new ContaPoupanca(idPoupanca, saldoPoupanca);
                    contaPoupanca.setNumeroConta(cpf); // Associa o CPF ao número da conta
                    return contaPoupanca;
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Erro ao processar o arquivo: " + e.getMessage());
            }
        }

        System.out.println("Conta não encontrada.");
        return null;
    }



    
    private static void carregarSaldoConta(Conta conta) {
        String arquivo = "usuarios/" + conta.getNumeroConta() + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Saldo da conta corrente: ") || linha.startsWith("Saldo da conta poupança: ")) {
                    String[] partes = linha.split(": ");
                    if (partes.length == 2) {
                        double novoSaldo = Double.parseDouble(partes[1]);
                        conta.saldo = novoSaldo;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar saldo da conta: " + e.getMessage());
        }
    }
    
    public static void carregarSaldoConta(Conta conta, String cpf) {
        String arquivo = "usuarios/" + cpf + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Saldo da conta corrente: ") || linha.startsWith("Saldo da conta poupança: ")) {
                    String[] partes = linha.split(": ");
                    if (partes.length == 2) {
                        double novoSaldo = Double.parseDouble(partes[1]);
                        conta.saldo = novoSaldo;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar saldo da conta: " + e.getMessage());
        }
    }
    
    private static Conta criarContaCorrente(String idConta) {
		for (Conta conta : contas) {
			if (conta.getNumeroConta().equals(idConta)) {
				return conta;
			}	
		}
		
		Conta novaConta = null;
		novaConta = new ContaCorrentePrincipal("idExemplo", 0, 0);
		if (novaConta != null) {
			contas.add(novaConta);
		}
		return novaConta;
	}
    
    private static Conta criarContaPoupanca(String idConta) {
		for (Conta conta : contas) {
			if (conta.getNumeroConta().equals(idConta)) {
				return conta;
			}	
		}
		Conta novaConta = null;
		novaConta = new ContaPoupanca("idExemplo", 0);
		if (novaConta != null) {
			contas.add(novaConta);
		}
		return novaConta;
	}
    
    

    public void avancarTempo(int meses) {
        for (Conta conta : contas) {
            if (conta instanceof ContaPoupanca) {
                for (int i = 0; i < meses; i++) {
                    ((ContaPoupanca) conta).aplicarRendimento();
                }
            }
        }
        System.out.println("Tempo avançado em " + meses + " meses para todas as contas poupança.");
    }

    private void carregarUsuarios() {
        File pasta = new File(PASTA_USUARIOS);
        if (pasta.exists() && pasta.isDirectory()) {
            for (File arquivo : pasta.listFiles()) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String nome = "", senha = "", cpf = "", rua = "", numero = "", bairro = "", cidade = "", uf = "", telefone = "", email = "", tipo = "";
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        if (linha.startsWith("Nome: ")) nome = linha.substring(6);
                        else if (linha.startsWith("Senha: ")) senha = linha.substring(7);
                        else if (linha.startsWith("CPF: ")) cpf = linha.substring(5);
                        else if (linha.startsWith("Rua: ")) rua = linha.substring(5);
                        else if (linha.startsWith("Número: ")) numero = linha.substring(8);
                        else if (linha.startsWith("Bairro: ")) bairro = linha.substring(8);
                        else if (linha.startsWith("Cidade: ")) cidade = linha.substring(8);
                        else if (linha.startsWith("UF: ")) uf = linha.substring(4);
                        else if (linha.startsWith("Telefone: ")) telefone = linha.substring(10);
                        else if (linha.startsWith("E-mail: ")) email = linha.substring(8);
                        else if (linha.startsWith("Tipo de Usuário: ")) tipo = linha.substring(17);
                    }
                    
                    Usuario usuario;
                    
                    if (tipo.equals("1")) {
                        usuario = new Gerente(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
                    } else if (tipo.equals("2")) {
                        usuario = new Bancario(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
                    } else if (tipo.equals("3")) {
                        usuario = new Cliente(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
                    }
                    else {
                        continue;
                    } 
                    usuarios.add(usuario);
                } catch (IOException e) {
                    System.out.println("Erro ao carregar usuário: " + e.getMessage());
                }
            }
        }
    }
    
    private void carregarClientes() {
        File pasta = new File(PASTA_USUARIOS);
        if (pasta.exists() && pasta.isDirectory()) {
            for (File arquivo : pasta.listFiles()) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String idContaCorrente = "", idContaPoupanca = "", nome = "", senha = "", cpf = "", rua = "", numero = "", bairro = "", cidade = "", uf = "", telefone = "", email = "", tipo = "";
                    double saldoCorrente = 0, saldoPoupanca = 0, limiteChequeEspecial = 0;
                    String linha, saldoCorrenteS = "", saldoPoupancaS = "", limiteChequeEspecialS = "";
                    while ((linha = br.readLine()) != null) {
                        if (linha.startsWith("Nome: ")) nome = linha.substring(6);
                        else if (linha.startsWith("Senha: ")) senha = linha.substring(7);
                        else if (linha.startsWith("CPF: ")) cpf = linha.substring(5);
                        else if (linha.startsWith("Rua: ")) rua = linha.substring(5);
                        else if (linha.startsWith("Número: ")) numero = linha.substring(8);
                        else if (linha.startsWith("Bairro: ")) bairro = linha.substring(8);
                        else if (linha.startsWith("Cidade: ")) cidade = linha.substring(8);
                        else if (linha.startsWith("UF: ")) uf = linha.substring(4);
                        else if (linha.startsWith("Telefone: ")) telefone = linha.substring(10);
                        else if (linha.startsWith("E-mail: ")) email = linha.substring(8);
                        else if (linha.startsWith("Tipo de Usuário: ")) tipo = linha.substring(17);
                        else if (linha.startsWith("Saldo da Conta Corrente: ")) saldoCorrenteS = linha.substring(10);
                        else if (linha.startsWith("Saldo da Conta Poupança: ")) saldoPoupancaS = linha.substring(10);
                        else if (linha.startsWith("Limite do Cheque Especial: ")) limiteChequeEspecialS = linha.substring(10);
                        else if (linha.startsWith("ID da Conta Corrente: ")) idContaCorrente = linha.substring(10);
                        else if (linha.startsWith("ID da Conta Poupança: ")) idContaPoupanca = linha.substring(10);
                    }
                    
                    saldoCorrente = Double.parseDouble(saldoCorrenteS);
                    saldoPoupanca = Double.parseDouble(saldoPoupancaS);
                    limiteChequeEspecial = Double.parseDouble(limiteChequeEspecialS);
                    
                    Usuario usuario;
                    Conta contaPoupanca = new ContaPoupanca(idContaPoupanca, saldoPoupanca);
                    ContaCorrentePrincipal contaCorrente = new ContaCorrentePrincipal(idContaCorrente, saldoCorrente, limiteChequeEspecial);
                    
                    contas.add(contaCorrente);
                    contas.add(contaPoupanca);
                    
                    if (tipo.equals("3")) {
                        usuario = new Cliente(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
                    }
                    else {
                        continue;
                    } 
                    usuarios.add(usuario);
                } catch (IOException e) {
                    System.out.println("Erro ao carregar usuário: " + e.getMessage());
                }
            }
        }
    }

    private void criarPastaUsuarios() {
        File pasta = new File(PASTA_USUARIOS);
        if (!pasta.exists()) {
            if (pasta.mkdir()) {
                System.out.println("Pasta de usuários criada com sucesso.");
            } else {
                System.out.println("Erro ao criar a pasta de usuários.");
            }
        }
    }
}