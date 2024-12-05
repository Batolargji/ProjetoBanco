package sistemaBancario;

import java.util.Scanner;

class Cliente extends Usuario {
    private Scanner entrada = new Scanner(System.in);

    public Cliente(String nome, String nomeUsuario, String senha, String cpf, String endereco, String telefone) {
        super(nome,nomeUsuario, senha, cpf, endereco, telefone);
    }

    @Override
    public int getTipo() {
        return 3;
    }

    @Override
    public void acessarSistema() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\nBem-vindo, Cliente: " + nome);
            System.out.println("1. Depositar em Conta");
            System.out.println("2. Transferir Entre Contas");
            System.out.println("3. Sacar de Conta");
            System.out.println("4. Cadastrar Dependente");
            System.out.println("5. Visualizar Limite de Cheque Especial");
            System.out.println("6. Visualizar Contas Cadastradas");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    depositarEmContaPropria();
                    break;
                case 2:
                    transferirEntreContas();
                    break;
                case 3:
                    sacarDeConta();
                    break;
                case 4:
                    cadastrarDependente();
                    break;
                case 5:
                    visualizarLimiteChequeEspecial();
                    break;
                case 6:
                    visualizarContasCadastradas();
                    break;
                case 7:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void depositarEmContaPropria() {
        System.out.print("Informe o ID da sua conta para depósito: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta != null) {
            System.out.print("Informe o valor do depósito: ");
            double valor = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void transferirEntreContas() {
        System.out.print("Informe o ID da sua conta de origem: ");
        String idContaOrigem = entrada.nextLine();
        Conta contaOrigem = Banco.buscarContaPorId(idContaOrigem);

        if (contaOrigem != null) {
            System.out.print("Informe o valor da transferência: ");
            double valor = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            System.out.print("Informe o ID da conta de destino: ");
            String idContaDestino = entrada.nextLine();
            Conta contaDestino = Banco.buscarContaPorId(idContaDestino);

            if (contaDestino != null) {
                System.out.print("Confirme sua senha: ");
                String senha = entrada.nextLine();

                if (this.senha.equals(senha)) {
                    if (contaOrigem.sacar(valor)) {
                        contaDestino.depositar(valor);
                        System.out.println("Transferência realizada com sucesso.");
                    } else {
                        System.out.println("Saldo insuficiente na conta de origem.");
                    }
                } else {
                    System.out.println("Senha incorreta. Transferência não autorizada.");
                }
            } else {
                System.out.println("Conta de destino não encontrada.");
            }
        } else {
            System.out.println("Conta de origem não encontrada.");
        }
    }

    private void sacarDeConta() {
        System.out.print("Informe o ID da sua conta para saque: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta != null) {
            System.out.print("Informe o valor do saque: ");
            double valor = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            System.out.print("Confirme sua senha: ");
            String senha = entrada.nextLine();

            if (this.senha.equals(senha)) {
                if (conta.sacar(valor)) {
                    System.out.println("Saque realizado com sucesso.");
                } else {
                    System.out.println("Saldo insuficiente para realizar o saque.");
                }
            } else {
                System.out.println("Senha incorreta. Saque não autorizado.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void cadastrarDependente() {
        System.out.print("Nome Completo do dependente: ");
        String nomeDependente = entrada.nextLine();
        System.out.print("Nome de usuário do dependente: ");
        String usuarioDependente = entrada.nextLine();
        System.out.print("CPF do dependente (sem caracteres especiais): ");
        String cpfDependente = entrada.nextLine();

        while (!ValidadorCPF.validarCPF(cpfDependente)) {
            System.out.println("CPF inválido. Por favor, insira um CPF válido.");
            cpfDependente = entrada.nextLine();
        }

        System.out.print("Telefone do dependente: ");
        String telefone = entrada.nextLine();
        System.out.print("Endereço do dependente: ");
        String endereco = entrada.nextLine();
        System.out.print("Deseja definir um limite de cheque especial para a conta do dependente? (s/n): ");
        String opcaoLimite = entrada.nextLine();
        double limiteChequeEspecial = 0.0;

        if (opcaoLimite.equalsIgnoreCase("s")) {
            System.out.print("Informe o valor do limite de cheque especial: ");
            limiteChequeEspecial = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha
        }

        Cliente dependente = new Cliente(nomeDependente,usuarioDependente, this.senha, cpfDependente, endereco, telefone);
        Banco.adicionarUsuario(dependente);

        String idContaCorrente = String.valueOf(Utils.gerarIdAleatorio());
        ContaCorrentePrincipal novaContaDependente = new ContaCorrentePrincipal(idContaCorrente, 0.0, limiteChequeEspecial);
        Banco.adicionarConta(novaContaDependente);
        System.out.println("Dependente cadastrado com sucesso. ID da conta corrente: " + idContaCorrente);
    }

    private void visualizarLimiteChequeEspecial() {
        System.out.print("Informe o ID da sua conta corrente: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta instanceof ContaCorrentePrincipal) {
            double limite = ((ContaCorrentePrincipal) conta).getLimiteChequeEspecial();
            System.out.println("Seu limite de cheque especial é: " + limite);
        } else {
            System.out.println("Conta não encontrada ou não é do tipo Conta Corrente.");
        }
    }

    private void visualizarContasCadastradas() {
        System.out.println("Visualizando suas contas cadastradas:");
        for (Conta conta : Banco.contas) {
            if (conta.getNumeroConta().startsWith(cpf.substring(0, 3))) { // Aqui utilizamos os primeiros 3 dígitos do CPF para identificar as contas do cliente
                conta.exibirDetalhesConta();
            }
        }
    }
}

