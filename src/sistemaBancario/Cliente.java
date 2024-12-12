package sistemaBancario;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

class Cliente extends Usuario {
    private Scanner entrada = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Cliente(String nome, String senha, String cpf, String telefone, String email, String rua, String numero, String bairro, String cidade, String uf) {
        super(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
    }

    @Override
    public int getTipo() {
        return 3;
    }

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
                    try {
                        depositarEmContaPropria();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    transferirEntreContas();
                    break;
                case 3:
                    sacarDeConta();
                    break;
               /* case 4:
                    cadastrarDependente();
                    break;*/
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

    /*private void depositarEmContaPropria() {
    	System.out.print("Informe o ID da sua conta para depósito: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta != null) { // Certifique-se de que a conta não é nula
            System.out.print("Informe o valor do depósito: ");
            double valor = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso.");
            RegistroUtils.registrarMovimentacao(cpf, "Depósito realizado no valor de: " + valor);
        } else {
            System.out.println("Erro: Conta não encontrada. Operação cancelada.");
        }
    }*/
    
    private void depositarEmContaPropria() throws IOException {
        System.out.print("Informe o ID da sua conta para depósito: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta != null) {
            System.out.print("Informe o valor do depósito: ");
            double valor = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            conta.depositar(valor);

            // Usa o CPF associado ao arquivo do usuário para registrar a movimentação
             // O CPF foi associado no método buscarContaPorId
            RegistroUtils.carregarSaldoConta(conta);
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
                        RegistroUtils.registrarMovimentacao(idContaDestino, "Transferência realizada para a conta " + idContaDestino + "no valor de: " + valor);
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
                    RegistroUtils.registrarMovimentacao(idConta, "Saque realizado no valor de: " + valor);
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

   /* private void cadastrarDependente() {
        System.out.print("Nome Completo do dependente: ");
        String nomeDependente = entrada.nextLine();
        System.out.print("CPF do dependente (sem caracteres especiais): ");
        String cpfDependente = entrada.nextLine();

        while (!ValidadorCPF.validarCPF(cpfDependente)) {
            System.out.println("CPF inválido. Por favor, insira um CPF válido.");
            cpfDependente = entrada.nextLine();
        }

        System.out.print("Telefone do dependente: ");
        String telefone = entrada.nextLine();
        System.out.print("E-mail do dependente: ");
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

        Dependente dependente = new Dependente(nomeDependente, senha, cpfDependente, telefone, email, rua, numero, bairro, cidade, uf);
        Banco.adicionarUsuario(dependente);

        String idContaCorrente = String.valueOf(Utils.gerarIdAleatorio());
        ContaCorrentePrincipal novaContaDependente = new ContaCorrentePrincipal(idContaCorrente, 0.0, 0.0);
        Banco.adicionarConta(novaContaDependente);

        RegistroUtils.registrarDependente(this.cpf, dependente, idContaCorrente);

        System.out.println("Dependente cadastrado com sucesso. ID da conta corrente: " + idContaCorrente);
        
        Dependente dependente1 = new Dependente(nomeDependente, senha, cpfDependente, telefone, email, rua, numero, bairro, cidade, uf);
        Banco.adicionarUsuario(dependente1);

        String idContaCorrente1 = String.valueOf(Utils.gerarIdAleatorio());
        ContaCorrentePrincipal novaContaDependente1 = new ContaCorrentePrincipal(idContaCorrente1, 0.0, 0.0);
        Banco.adicionarConta(novaContaDependente1);

        // Registrar dados do dependente no arquivo do titular
        RegistroUtils.registrarDependente(this.cpf, dependente1, idContaCorrente1);

        System.out.println("Dependente cadastrado com sucesso. ID da conta corrente: " + idContaCorrente1);

    }*/

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
        // Use o CPF do cliente atual (this)
        List<Conta> contasDoCliente = Banco.buscarContasPorCpf(this.getCpf());

        if (contasDoCliente.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            for (Conta conta : contasDoCliente) {
                conta.exibirDetalhesConta(); // Exibe os detalhes de cada conta
            }
        }
    }

    public void visualizarSaldos() {
        System.out.println("Exibindo saldos para o cliente: " + nome);
        for (Conta conta : Banco.contas) {
            if (conta.getNumeroConta().startsWith(cpf)) {
                conta.exibirDetalhesConta();
            }
        }
    }
}


