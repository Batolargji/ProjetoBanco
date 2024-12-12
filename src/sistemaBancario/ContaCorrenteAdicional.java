package sistemaBancario;

public class ContaCorrenteAdicional extends Conta {
    private double limite;
    private String numeroPai;
    private double saldo = 0;
    public ContaCorrenteAdicional(String numeroConta, double limite, String numeroPai  ) {
        super(numeroConta, 0.0);
        this.numeroPai = numeroPai;
        this.limite = limite;
    }

    public boolean sacar(double valor) {
        Conta conta = Banco.buscarContaPorId(numeroPai);
        saldo = conta.getSaldo();
        if (valor > 0 && valor <= limite && valor <= saldo) {
            saldo -= valor;
            limite -= valor;
            RegistroUtils.registrarMovimentacao(numeroConta, "Saque de " + valor + " realizado.");
            RegistroUtils.registrarMovimentacao(numeroPai, "Saque de " + valor + " realizado pelo dependente.");
            System.out.println("Saque de " + valor + " realizado. Limite atual: " + limite);
            return true;
        }
        else if (valor < 0 && valor > (limite)) {
            System.out.println("Você excedeu o seu limite nenhuma operação foi realizado.");
            return false;
        }
        else {
            System.out.println("Você excedeu o saldo da conta principal ou inseriu um valor inválido, nenhuma operação foi realizada.");
            return false;
        }
    }

    public String getNumeroPai() {
        return numeroPai;
    }

    public void setNumeroPai(String numeroPai) {
        this.numeroPai = numeroPai;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public int getTipo() {
        return 3;
    }

    public void exibirDetalhesConta() {
        System.out.println("Conta Corrente Adicional - Número: " + numeroConta + ", Limite: " + limite);
    }
}
