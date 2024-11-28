package sistemaBancario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cliente extends Usuario {
    private Scanner entrada = new Scanner(System.in);
    private Conta conta;
    private List<Conta> contasAdicionais = new ArrayList<>();

    public Cliente(String nome, String senha, String cpf, String endereco, String telefone, Conta conta) {
        super(nome, senha, cpf, endereco, telefone);
        this.conta = conta;
    }

    public Conta getConta() {
        return conta;
    }

    public void adicionarContaAdicional(Conta conta) {
        contasAdicionais.add(conta);
    }

    public List<Conta> getContasAdicionais() {
        return contasAdicionais;
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
            System.out.println("1. Visualizar Saldo");
            System.out.println("2. Realizar Transferência");
            System.out.println("3. Realizar Depósito");
            System.out.println("4. Realizar Saque");
            System.out.println("5. Visualizar Limite de Cheque Especial");
            System.out.println("6. Listar Tipo de Conta");
            System.out.println("7. Visualizar Contas Adicionais");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    System.out.println("Saldo atual: " + conta.getSaldo());
                    break;
                case 2:
                    realizarTransferencia();
                    break;
                case 3:
                    realizarDeposito();
                    break;
                case 4:
                    realizarSaque();
                    break;
                case 5:
                    if (conta instanceof ContaCorrentePrincipal) {
                        ContaCorrentePrincipal contaCorrente = (ContaCorrentePrincipal) conta;
                        System.out.println("Limite de Cheque Especial: " + contaCorrente.getLimiteChequeEspecial());
                    } else {
                        System.out.println("Esta conta não possui limite de cheque especial.");
                    }
                    break;
                case 6:
                    System.out.println("Tipo de conta: " + (conta instanceof ContaCorrentePrincipal ? "Conta Corrente" : "Conta Poupança"));
                    break;
                case 7:
                    visualizarContasAdicionais();
                    break;
                case 8:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void realizarTransferencia() {
        System.out.print("Digite o número da conta de destino: ");
        String numeroContaDestino = entrada.nextLine();
        System.out.print("Digite o valor a ser transferido: ");
        double valor = entrada.nextDouble();
        entrada.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = entrada.nextLine();

        if (!senha.equals(this.senha)) {
            System.out.println("Senha incorreta. Operação cancelada.");
            return;
        }

        for (Conta c : Banco.getContas()) {
            if (c.getNumeroConta().equals(numeroContaDestino)) {
                if (conta.sacar(valor)) {
                    c.depositar(valor);
                    System.out.println("Transferência realizada com sucesso.");
                } else {
                    System.out.println("Saldo insuficiente.");
                }
                return;
            }
        }

        System.out.println("Conta de destino não encontrada.");
    }

    private void realizarDeposito() {
        System.out.print("Digite o valor a ser depositado: ");
        double valor = entrada.nextDouble();
        entrada.nextLine();
        conta.depositar(valor);
    }

    private void realizarSaque() {
        System.out.print("Digite o valor a ser sacado: ");
        double valor = entrada.nextDouble();
        entrada.nextLine();
        if (conta.sacar(valor)) {
            System.out.println("Saque realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    private void visualizarContasAdicionais() {
        if (contasAdicionais.isEmpty()) {
            System.out.println("Nenhuma conta adicional encontrada.");
        } else {
            System.out.println("Contas adicionais:");
            for (Conta c : contasAdicionais) {
                c.exibirDetalhesConta();
            }
        }
    }
}

