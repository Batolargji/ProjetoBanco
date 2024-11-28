package sistemaBancario;

import java.util.Scanner;

class Gerente extends Usuario {
    private Scanner entrada = new Scanner(System.in);

    public Gerente(String nome, String senha, String cpf, String endereco, String telefone) {
        super(nome, senha, cpf, endereco, telefone);
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
            System.out.println("1. Autorizar Transferência");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    autorizarTransferencia();
                    break;
                case 2:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void autorizarTransferencia() {
        System.out.print("Digite o número da conta de origem: ");
        String numeroContaOrigem = entrada.nextLine();
        System.out.print("Digite o número da conta de destino: ");
        String numeroContaDestino = entrada.nextLine();
        System.out.print("Digite o valor a ser transferido: ");
        double valor = entrada.nextDouble();
        entrada.nextLine();

        Conta contaOrigem = null;
        Conta contaDestino = null;

        for (Conta c : Banco.getContas()) {
            if (c.getNumeroConta().equals(numeroContaOrigem)) {
                contaOrigem = c;
            }
            if (c.getNumeroConta().equals(numeroContaDestino)) {
                contaDestino = c;
            }
        }

        if (contaOrigem != null && contaDestino != null) {
            if (contaOrigem.sacar(valor)) {
                contaDestino.depositar(valor);
                System.out.println("Transferência autorizada e realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else {
            System.out.println("Conta de origem ou destino não encontrada.");
        }
    }
}
