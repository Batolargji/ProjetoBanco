package sistemaBancario;

class ContaPoupanca extends Conta {
    public ContaPoupanca(String numeroConta, double saldoInicial) {
        super(numeroConta, saldoInicial);
    }

    @Override
    public int getTipo() {
        return 2;
    }

    @Override
    public void exibirDetalhesConta() {
        System.out.println("Conta Poupança - Número: " + numeroConta + ", Saldo: " + saldo);
    }
}
