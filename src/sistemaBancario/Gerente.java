package sistemaBancario;

import java.io.IOException;
import java.util.Scanner;

class Gerente extends Usuario {
    private Scanner entrada = new Scanner(System.in);

    public Gerente(String nome, String senha, String cpf, String telefone, String email, String rua, String numero, String bairro, String cidade, String uf) {
        super(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
    }

    @Override
    public int getTipo() {
        return 1;
    }

    @Override
    public void acessarSistema() {
        boolean sair = false;
        while (!sair) {
        	System.out.println("Gerente acessando o sistema...");
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
        System.out.print("Senha do cliente: ");
        String senha = entrada.nextLine();
        System.out.print("CPF do cliente (sem caracteres especiais): ");
        String cpf = entrada.nextLine();

        while (!ValidadorCPF.validarCPF(cpf)) {
            System.out.println("CPF inválido. Por favor, insira um CPF válido.");
            cpf = entrada.nextLine();
        }

        System.out.print("Telefone do cliente: ");
        String telefone = entrada.nextLine();
        System.out.print("E-mail do cliente: ");
        String email = entrada.nextLine();
        System.out.print("Rua: ");
        String rua = entrada.nextLine();
        System.out.print("Número: ");
        String numero = entrada.nextLine();
        System.out.print("Bairro: ");
        String bairro = entrada.nextLine();
        System.out.print("Cidade: ");
        String cidade = entrada.nextLine();
        System.out.print("UF: ");
        String uf = entrada.nextLine();

        Cliente novoCliente = new Cliente(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);

        System.out.println("Cliente cadastrado com sucesso!");
            System.out.print("Defina um limite de cheque especial para a conta corrente: ");
            double limiteChequeEspecial = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha
            String idContaCorrente = String.valueOf(Utils.gerarIdAleatorio());
            ContaCorrentePrincipal novaContaCorrente = new ContaCorrentePrincipal(idContaCorrente, 0.0, limiteChequeEspecial);
            System.out.println("Conta corrente criada com sucesso. ID: " + idContaCorrente);  
            String idContaPoupanca = String.valueOf(Utils.gerarIdAleatorio());
            ContaPoupanca novaContaPoupanca = new ContaPoupanca(idContaPoupanca, 0.0);
            System.out.println("Conta poupança criada com sucesso. ID: " + idContaPoupanca);
            Banco.adicionarUsuario(novoCliente, novaContaCorrente, novaContaPoupanca);
            Banco.adicionarConta(novaContaCorrente);
            Banco.adicionarConta(novaContaPoupanca);
            RegistroUtils.registrarCriacao(cpf, "Conta Corrente criada. ID: " + idContaCorrente);
            RegistroUtils.registrarCriacao(cpf, "Conta Poupança criada. ID: " + idContaPoupanca);
            acessarSistema();
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
            ContaCorrentePrincipal conta2 = (ContaCorrentePrincipal) conta;
            try {
                RegistroUtils.carregarLimiteChequeEspecial(conta2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Limite de cheque especial alterado com sucesso.");
        } else {
            System.out.println("Conta não encontrada ou não é do tipo Conta Corrente.");
        }
    }
}

