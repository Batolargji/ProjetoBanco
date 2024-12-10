package sistemaBancario;

public class ContaCorrenteAdicional extends Conta {
    private double limite;
    private String numeroPrincipal;

    public ContaCorrenteAdicional(String numeroConta, double saldo, String numeroPrincipal, double limite  ) {
        super(numeroConta, saldo);
        this.numeroPrincipal = numeroPrincipal;
        this.limite = limite;
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= (limite)) {
            saldo -= valor;
            limite -= valor;
            System.out.println("Saque de " + valor + " realizado. Saldo atual: " + limite);
            return true;
        }
        else if (valor < 0 && valor > (limite)) {
            System.out.println("Você excedeu o seu limite nenhuma operação foi realizado.");
            return false;
        }
        else {
            System.out.println("Você excedeu o saldo da conta principal, nenhuma operação foi realizada.");
            return false;
        }
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

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public int getTipo() {
        return 3;
    }

    public void exibirDetalhesConta() {
        System.out.println("Conta Corrente Adicional - Número: " + numeroConta + ", Limite: " + limite);
    }
}
