package sistemaBancario;

class ContaCorrentePrincipal extends Conta {
    private double limiteChequeEspecial;

    public ContaCorrentePrincipal(String numeroConta, double saldoInicial, double limiteChequeEspecial) {
        super(numeroConta, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public boolean sacar(double valor) {
        if (valor > 0 && valor <= (saldo + limiteChequeEspecial)) {
            saldo -= valor;
            if (saldo < 0) {
                System.out.println("Saque de " + valor + " realizado utilizando cheque especial. Saldo atual: " + saldo + ", limite disponível: " + (limiteChequeEspecial + saldo));
            } else {
                System.out.println("Saque de " + valor + " realizado. Saldo atual: " + saldo);
            }
            return true;
        } else {
            System.out.println("Saldo insuficiente, incluindo limite de cheque especial.");
            return false;
        }
    }

    @Override
    public int getTipo() {
        return 1;
    }

    @Override
    public void exibirDetalhesConta() {
        System.out.println("Conta Corrente Principal - Número: " + numeroConta + ", Saldo: " + saldo + ", Limite Cheque Especial: " + limiteChequeEspecial);
    }
}

