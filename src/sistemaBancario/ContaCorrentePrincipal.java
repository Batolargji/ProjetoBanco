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
    public boolean sacar(double valor, boolean transferencia) {
        if (valor > 0 && valor <= (saldo + limiteChequeEspecial)) {
            saldo -= valor;
            if (transferencia == false) {
                if (saldo < 0) {
                    System.out.println("Saque de " + valor + " realizado utilizando cheque especial. Saldo atual: " + 0.0 + ", limite disponível: " + (limiteChequeEspecial + saldo));
                    saldo = 0;
                } else {
                    System.out.println("Saque de " + valor + " realizado. Saldo atual: " + saldo);
                }
                RegistroUtils.registrarMovimentacao(numeroConta, "Saque: R$ " + valor + Banco.registrarContaExtrato(numeroConta));
            }
            else if (valor > 0 && valor <= saldo) {
                saldo -= valor;
                return true;
            }
            else {
                return false;
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