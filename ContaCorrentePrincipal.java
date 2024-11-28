package sistemaBancario;

class ContaCorrentePrincipal extends Conta {
    private double limiteChequeEspecial;
    // Construtor
    public ContaCorrentePrincipal(String numeroConta, double saldoInicial, double limiteChequeEspecial) {
        super(numeroConta, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }
    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    @Override
    public void exibirDetalhesConta() {
        // Exibe os dados da conta
        System.out.println("Conta Corrente Principal - Número: " + numeroConta + ", Saldo: " + saldo + ", Limite Cheque Especial: " + limiteChequeEspecial);
    }
}
