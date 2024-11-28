package sistemaBancario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

class Banco {
    // Foram usadas listas pois não têm tamanho definido
    private List<Usuario> usuarios = new ArrayList<>();
    private static List<Conta> contas = new ArrayList<>();
    private Scanner entrada = new Scanner(System.in);
	private Cliente usuario;
    private static final String USUARIOS_FILE = "usuarios.txt";

    public Banco() {
        carregarUsuarios();
    }

    public void iniciarSistema() {
        System.out.println("Bem-vindo ao Sistema Bancário!");
        boolean sair = false;
        // Menu de opções
        while (!sair) {
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Fazer Login");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    Usuario usuarioLogado = fazerLogin();
                    if (usuarioLogado != null) {
                        usuarioLogado.acessarSistema();
                    }
                    break;
                case 3:
                    salvarUsuario();
                    sair = true;
                    System.out.println("Obrigado por usar o Sistema Bancário!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarUsuario() {
        System.out.print("Digite o nome completo do usuário: ");
        String nome = entrada.nextLine();
        System.out.print("Digite um nome de usuário: ");
        String usuario = entrada.nextLine();
        System.out.print("Digite a senha: ");
        String senha = entrada.nextLine();
        long cpf;
        // Validação de CPF
        do {
            System.out.print("Digite o CPF (somente números): ");
            cpf = entrada.nextLong();
        } while (!validarCpf(cpf));
        entrada.nextLine(); // Consumir nova linha
        System.out.print("Digite o endereço: ");
        String endereco = entrada.nextLine();
        System.out.print("Digite o telefone: ");
        String telefone = entrada.nextLine();
        System.out.print("Digite o tipo de conta (1 - Gerente, 3 - Cliente): ");
        int tipo = entrada.nextInt();
        // Criação da Conta
        String numeroConta = String.format("%06d", (int) (Math.random() * 1000000));
        Conta conta = new ContaCorrentePrincipal(numeroConta, 0, 0);
        contas.add(conta);

        if (tipo == 1) {
            // Novo Gerente
            Gerente gerente = new Gerente(nome, senha, String.valueOf(cpf), endereco, telefone);
            usuarios.add(gerente);
        } else if (tipo == 3) {
            // Novo Cliente
            Cliente cliente = new Cliente(nome, senha, String.valueOf(cpf), endereco, telefone, conta);
            usuarios.add(cliente);
        } else {
            // número errado
            System.out.println("Tipo de conta inválido. Cadastro cancelado.");
            return;
        }
        // Chama o método de salvar usuario
        salvarUsuario();
        System.out.println("Usuário cadastrado com sucesso!");
    }
    // Validação CPF
    private boolean validarCpf(long cpf) {
        long DI, DV, i, quebra1 = 0, quebra2 = 0, soma1 = 0, soma2 = 0, resto1 = 0, resto2 = 0, DV1 = 0, DV2 = 0, AUX = 0;
        DI = cpf / 100;
        DV = cpf % 100;

        for (i = 2; i <= 10; i++) {
            quebra1 = (DI % 10) * i;
            DI = DI / 10;
            soma1 = soma1 + quebra1;
        }

        resto1 = soma1 % 11;
        DV1 = 11 - resto1;

        if (DV1 == 10 || DV1 == 11) {
            DV1 = 0;
        }
        soma1 = 0;
        DI = cpf / 100;
        AUX = DI * 10 + DV1;

        for (i = 2; i <= 11; i++) {
            quebra2 = (AUX % 10) * i;
            AUX = AUX / 10;
            soma2 = soma2 + quebra2;
        }

        resto2 = soma2 % 11;
        DV2 = 11 - resto2;

        if (DV2 == 10 || DV2 == 11) {
            DV2 = 0;
        }

        return (DV1 * 10 + DV2) == DV;
    }

    private Usuario fazerLogin() {
        System.out.print("Digite o nome do usuário: ");
        String nome = entrada.nextLine();
        System.out.print("Digite a senha do usuário: ");
        String senha = entrada.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome) && usuario.getSenha().equals(senha)) {
                System.out.println("Login realizado com sucesso!");
                return usuario;
            }
        }
        System.out.println("Usuário ou senha incorretos.");
        return null;
    }

    private void carregarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 8) {
                    int tipo = Integer.parseInt(dados[5]);
                    String numeroConta = dados[6];
                    double saldo = Double.parseDouble(dados[7]);
                    Conta conta = new ContaCorrentePrincipal(numeroConta, saldo, 0);
                    contas.add(conta);

                    switch (tipo) {
                        case 1:
                            Gerente gerente = new Gerente(dados[0], dados[1], dados[2], dados[3], dados[4]);
                            usuarios.add(gerente);
                            break;
                        case 3:
                            Cliente cliente = new Cliente(dados[0], dados[1], dados[2], dados[3], dados[4], conta);
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

    private void salvarUsuario() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USUARIOS_FILE, true))) {
            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente) usuario;
                bw.write(cliente.getNome() + "," + cliente.getSenha() + "," + cliente.getCpf() + "," + cliente.getEndereco() + "," + cliente.getTelefone() + "," + cliente.getTipo() + "," + cliente.getConta().getNumeroConta() + "," + cliente.getConta().getSaldo());
            } else {
                bw.write(usuario.getNome() + "," + usuario.getSenha() + "," + usuario.getCpf() + "," + usuario.getEndereco() + "," + usuario.getTelefone() + "," + usuario.getTipo() + ",,");
            }
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public static List<Conta> getContas() {
        return contas;
    }
}
