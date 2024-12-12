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
            RegistroUtils.registrarMovimentacao(numeroConta, "Depósito: R$ " + valor + Banco.registrarContaExtrato(numeroConta));
            System.out.println("Depósito de R$ " + valor + " realizado. Saldo atual: R$ " + saldo);
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            RegistroUtils.registrarMovimentacao(numeroConta, "Saque: R$ " + valor + Banco.registrarContaExtrato(numeroConta));
            System.out.println("Saque de R$ " + valor + " realizado. Saldo atual: R$ " + saldo);
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
    /*public double getLimitechequeespacial() {
        return limitechequeespacial;
    }*/
    public abstract int getTipo();

    public abstract void exibirDetalhesConta();

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public void setLimitechequeespacial(double limitechequeespacial) {
		this.limitechequeespacial = limitechequeespacial;
	}
    
    
    
    
}
