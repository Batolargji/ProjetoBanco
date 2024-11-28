package sistemaBancario;

class Conta {
    protected double saldo;
    protected String numeroConta;
    // Construtor
    public Conta(String numeroConta, double saldoInicial) {
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de " + valor + " realizado.");
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            return true;
        } else {
            return false;
        }
    }
    // Exibe os dados da conta
    public void exibirDetalhesConta() {
        System.out.println("Conta Número: " + numeroConta + ", Saldo: " + saldo);
    }
}
