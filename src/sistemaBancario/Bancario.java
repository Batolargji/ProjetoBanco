package sistemaBancario;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

class Bancario extends Usuario {
    private Scanner entrada = new Scanner(System.in);

    public Bancario(String nome, String senha, String cpf, String telefone, String email, String rua, String numero, String bairro, String cidade, String uf) {
        super(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
    }

    @Override
    public int getTipo() {
        return 2;
    }

    @Override
    public void acessarSistema() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\nBem-vindo, Bancário: " + nome);
            System.out.println("1. Visualizar Todas as Contas");
            System.out.println("2. Depositar em Conta de Cliente");
            System.out.println("3. Transferir Entre Contas de Clientes");
            System.out.println("4. Realizar Saque em Conta de Cliente");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    visualizarTodasAsContas();
                    break;
                case 2:
                    try {
                        depositarNaConta();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    transferirEntreContas();
                    break;
                case 4:
                    sacarParaCliente();
                    break;
                case 5:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void visualizarTodasAsContas() {
        System.out.println("Listando todas as contas:");
        Banco.listarTodasAsContas();
    }

    private void depositarNaConta() throws IOException {
        System.out.print("Informe o ID da conta para depósito: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta != null) {
            System.out.print("Informe o valor do depósito: ");
            double valor = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso.");
            RegistroUtils.carregarSaldoConta(conta);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void transferirEntreContas() {
        System.out.print("Informe o ID da conta de origem: ");
        String idContaOrigem = entrada.nextLine();
        Conta contaOrigem = Banco.buscarContaPorId(idContaOrigem);

        if (contaOrigem != null) {
            System.out.print("Informe o ID da conta de destino: ");
            String idContaDestino = entrada.nextLine();
            Conta contaDestino = Banco.buscarContaPorId(idContaDestino);

            if (contaDestino != null) {
                System.out.print("Informe o valor da transferência: ");
                double valor = entrada.nextDouble();
                entrada.nextLine(); // Consumir nova linha

                if (contaOrigem.sacar(valor)) {
                    contaDestino.depositar(valor);
                    System.out.println("Transferência realizada com sucesso.");
                } else {
                    System.out.println("Saldo insuficiente na conta de origem.");
                }
            } else {
                System.out.println("Conta de destino não encontrada.");
            }
        } else {
            System.out.println("Conta de origem não encontrada.");
        }
    }

    private void sacarParaCliente() {
        System.out.print("Informe o ID da conta para saque: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta != null) {
            System.out.print("Informe o valor do saque: ");
            double valor = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            if (conta.sacar(valor)) {
                System.out.println("Saque realizado com sucesso.");
                try {
                    RegistroUtils.carregarSaldoConta(conta);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Saldo insuficiente para realizar o saque.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }
    
    public void visualizarSaldosClientes() {
        System.out.println("Exibindo saldos de todas as contas:");
        Banco.listarTodasAsContas();
    }
    private void visualizarContasPorCpf() {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Informe o CPF do cliente: ");
        String cpf = entrada.nextLine();

        System.out.println("Visualizando contas cadastradas para o CPF: " + cpf);
        List<Conta> contasDoCliente = Banco.buscarContasPorCpf(cpf);

        if (contasDoCliente.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada para o CPF informado.");
        } else {
            for (Conta conta : contasDoCliente) {
                conta.exibirDetalhesConta();
            }
        }
    }
}

