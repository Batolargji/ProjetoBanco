package sistemaBancario;

import java.util.Scanner;

class Gerente extends Usuario {
    private Scanner entrada = new Scanner(System.in);

    public Gerente(String nome, String nomeUsuario, String senha, String cpf, String endereco, String telefone) {
        super(nome, nomeUsuario, senha, cpf, endereco, telefone );
    }

    @Override
    public int getTipo() {
        return 1;
    }

    @Override
    public void acessarSistema() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\nBem-vindo, Gerente: " + nome);
            System.out.println("1. Cadastrar Novo Cliente");
            System.out.println("2. Manipular Limite de Cheque Especial");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    cadastrarNovoCliente();
                    break;
                case 2:
                    manipularLimiteChequeEspecial();
                    break;
                case 3:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarNovoCliente() {
        System.out.print("Nome do cliente: ");
        String nome = entrada.nextLine();
        System.out.print("Nome de usuário: ");
        String nomeUsuario = entrada.nextLine();
        System.out.print("Senha do cliente: ");
        String senha = entrada.nextLine();
        System.out.print("CPF do cliente (sem caracteres especiais): ");
        String cpf = entrada.nextLine();

        while (!ValidadorCPF.validarCPF(cpf)) {
            System.out.println("CPF inválido. Por favor, insira um CPF válido.");
            cpf = entrada.nextLine();
        }

        System.out.print("Endereço do cliente: ");
        String endereco = entrada.nextLine();
        System.out.print("Telefone do cliente: ");
        String telefone = entrada.nextLine();

        Cliente novoCliente = new Cliente(nome, nomeUsuario, senha, cpf, endereco, telefone);
        Banco.adicionarUsuario(novoCliente);

        System.out.println("Cliente cadastrado com sucesso!");
        System.out.println("Agora vamos criar as contas do cliente.");

        // Definir se o cliente terá conta corrente, poupança ou ambas
        System.out.print("Deseja criar uma conta corrente para o cliente? (s/n): ");
        String opcaoCorrente = entrada.nextLine();
        if (opcaoCorrente.equalsIgnoreCase("s")) {
            System.out.print("Limite de cheque especial para a conta corrente: ");
            double limiteChequeEspecial = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            String idContaCorrente = String.valueOf(Utils.gerarIdAleatorio());
            ContaCorrentePrincipal novaContaCorrente = new ContaCorrentePrincipal(idContaCorrente, 0.0, limiteChequeEspecial);
            Banco.adicionarConta(novaContaCorrente);
            System.out.println("Conta corrente criada com sucesso. ID: " + idContaCorrente);
        }

        System.out.print("Deseja criar uma conta poupança para o cliente? (s/n): ");
        String opcaoPoupanca = entrada.nextLine();
        if (opcaoPoupanca.equalsIgnoreCase("s")) {
            String idContaPoupanca = String.valueOf(Utils.gerarIdAleatorio());
            ContaPoupanca novaContaPoupanca = new ContaPoupanca(idContaPoupanca, 0.0);
            Banco.adicionarConta(novaContaPoupanca);
            System.out.println("Conta poupança criada com sucesso. ID: " + idContaPoupanca);
        }
    }

    private void manipularLimiteChequeEspecial() {
        System.out.print("Informe o ID da conta corrente para alterar o limite: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta instanceof ContaCorrentePrincipal) {
            System.out.print("Informe o novo valor do limite de cheque especial: ");
            double novoLimite = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            ((ContaCorrentePrincipal) conta).setLimiteChequeEspecial(novoLimite);
            System.out.println("Limite de cheque especial alterado com sucesso.");
        } else {
            System.out.println("Conta não encontrada ou não é do tipo Conta Corrente.");
        }
    }
}

