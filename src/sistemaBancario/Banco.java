package sistemaBancario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Banco {
    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Conta> contas = new ArrayList<>();
    private Scanner entrada = new Scanner(System.in);
    private static final String CONTAS_FILE= "conta.txt";
    private static final String USUARIOS_FILE = "usuarios.txt";
    //private static final String CONPRI_FILE = "correnteprincipal.txt";
    //private static final String CONADI_FILE = "correnteadicional.txt";
    //private static final String CONPOP_FILE = "poupança.txt";

    public Banco() {
        verificarOuCriarArquivoUsuarios();
        verificarOuCriarArquivoContas();
        carregarUsuarios();
        carregarContas();
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
                        usuarioLogado.acessarSistema();
                    }
                    break;
                case 2:
                    salvarUsuarios();
                    salvarContaCorrente();
                    salvarContaPoupanca();
                    sair = true;
                    System.out.println("Obrigado por usar o Sistema Bancário!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private Usuario fazerLogin() {
        System.out.print("Digite o nome do usuário: ");
        String nomeUsuario = entrada.nextLine();
        System.out.print("Digite a senha do usuário: ");
        String senha = entrada.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getNomeUsuario().equals(nomeUsuario) && usuario.getSenha().equals(senha)) {
                System.out.println("Login realizado com sucesso!");
                return usuario;
            }
        }
        System.out.println("Usuário ou senha incorretos.");
        return null;
    }

    public static void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
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

    public static Conta buscarContaPorId(String idConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta().equals(idConta)) {
                return conta;
            }
        }
        return null;
    }

    private void carregarContas() {
        try (BufferedReader br = new BufferedReader(new FileReader(CONTAS_FILE))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    int tipo = Integer.parseInt(dados[6]);
                    // nome, senha , cpf, endereço, telefone, tipo
                    switch (tipo) {
                        case 1:
                            // gerente
                            Gerente gerente = new Gerente(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
                            usuarios.add(gerente);
                            break;
                        case 2:
                            // bancario
                            Bancario bancario = new Bancario(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
                            usuarios.add(bancario);
                            break;
                        case 3:
                            // cliente
                            Cliente cliente = new Cliente(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
                            usuarios.add(cliente);
                            break;
                        default:
                            System.out.println("Tipo de usuário inválido no arquivo.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Nenhum usuário encontrado para carregar.");
        }
    }

    private void carregarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    int tipo = Integer.parseInt(dados[6]);
                    // nome, senha , cpf, endereço, telefone, tipo
                    switch (tipo) {
                        case 1:
                            // gerente
                            Gerente gerente = new Gerente(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
                            usuarios.add(gerente);
                            break;
                        case 2:
                            // bancario
                            Bancario bancario = new Bancario(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
                            usuarios.add(bancario);
                            break;
                        case 3:
                            // cliente
                            Cliente cliente = new Cliente(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5]);
                            usuarios.add(cliente);
                            break;
                        default:
                            System.out.println("Tipo de usuário inválido no arquivo.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Nenhum usuário encontrado para carregar.");
        }
    }

    public void salvarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USUARIOS_FILE))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.getNome() + ";" + usuario.getNomeUsuario()+ ";" + usuario.getSenha() + ";" + usuario.getCpf() + ";" + usuario.getEndereco() + ";" + usuario.getTelefone() + ";" + usuario.getTipo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public void salvarContaPoupanca() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONTAS_FILE))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.getNomeUsuario() + ";");
            }
            for (Conta conta : contas) {
                bw.write(conta.getNumeroConta() + ";" + conta.getSaldo()+ ";" + conta.getTipo() + ";");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar Contas Poupanças7: " + e.getMessage());
        }
    }

    public void salvarContaCorrente() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONTAS_FILE))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.getNomeUsuario() + ";");
            }
            for (Conta contaCorrentePrincipal : contas) {
                bw.write(contaCorrentePrincipal.getNumeroConta() + ";" + contaCorrentePrincipal.getSaldo()+ ";" + contaCorrentePrincipal.getTipo() + ";" + contaCorrentePrincipal.getLimitechequeespacial() + ";");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar Contas Correntes: " + e.getMessage());
        }
    }

    // Criação do arquivo usuario
    private void verificarOuCriarArquivoUsuarios() {
        File arquivo = new File(USUARIOS_FILE);
        if (!arquivo.exists()) {
            try {
                if (arquivo.createNewFile()) {
                    // Criação de um gerente admin genérico para poder criar outros usuários
                    System.out.println("Arquivo de usuários não encontrado. Criando arquivo com usuário Admin...");
                    Gerente gerenteInicial = new Gerente("Admin", "Admin", "12345", "000.000.000-00", "Rua Inicial", "0000-0000");
                    usuarios.add(gerenteInicial);
                    salvarUsuarios();
                }
            } catch (IOException e) {
                System.out.println("Erro ao criar arquivo de usuários: " + e.getMessage());
            }
        }
    }

    // Criação do arquivo Contas
    private void verificarOuCriarArquivoContas() {
        File arquivo = new File(CONTAS_FILE);
        if (!arquivo.exists()) {
            try {
                if (arquivo.createNewFile()) {
                    System.out.println("Arquivo de contas não encontrado. Criando arquivo das contas...");
                }
            } catch (IOException e) {
                System.out.println("Erro ao criar arquivo das contas: " + e.getMessage());
            }
        }
    }


}

