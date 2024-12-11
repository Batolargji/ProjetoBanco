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
                        System.out.println("Usuário logado como: " + usuarioLogado.getClass().getSimpleName());
                        usuarioLogado.acessarSistema();
                    }
                    break;
                case 2:
                    salvarUsuarios();
                    salvarContas();
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

        // Cria o usuário com base no tipo
        Usuario novoUsuario = null;
        switch (tipo) {
            case "Gerente":
                novoUsuario = new Gerente("Nome Exemplo", "senha", cpf, "telefone", "email@exemplo.com", "Rua Exemplo", "123", "Bairro Exemplo", "Cidade Exemplo", "UF");
                break;
            case "Bancário":
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

    public static void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        RegistroUtils.criarArquivoUsuario(usuario);
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

    private void carregarContas() {
        // Implementação para carregar contas
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
                    if (tipo.equals("Gerente")) {
                        usuario = new Gerente(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
                    } else if (tipo.equals("Bancário")) {
                        usuario = new Bancario(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
                    } else {
                        continue;
                    }
                    usuarios.add(usuario);
                } catch (IOException e) {
                    System.out.println("Erro ao carregar usuário: " + e.getMessage());
                }
            }
        }
    }

    public void salvarUsuarios() {
        // Implementação para salvar usuários
    }

    public void salvarContas() {
        // Implementação para salvar contas
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

    private Usuario buscarUsuarioPorCpf(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }
        return null;
    }
}