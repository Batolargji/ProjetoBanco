package sistemaBancario;

abstract class Conta {
    protected double saldo;
    protected String numeroConta;
    protected double limitechequeespacial;

    public Conta(String numeroConta, double saldoInicial) {
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de " + valor + " realizado. Saldo atual: " + saldo);
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            String mensagem = "Saque de " + valor + " realizado. Saldo atual: " + saldo;
            RegistroUtils.registrarMovimentacao(numeroConta, mensagem);
            System.out.println(mensagem);
            return true;
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
            return false;
        }
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }
    public double getLimitechequeespacial() {
        return limitechequeespacial;
    }
    public abstract int getTipo();

    public abstract void exibirDetalhesConta();
    
    
}
