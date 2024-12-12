package sistemaBancario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ContaPoupanca extends Conta {
    private int mesesPassados;

    public ContaPoupanca(String numeroConta, double saldoInicial) {
        super(numeroConta, saldoInicial);
        this.mesesPassados = 0;
    }

    // Método para avançar o tempo e aplicar rendimento
    public void aplicarRendimento() {
        double rendimento = saldo * 0.01; // 1% de rendimento
        saldo += rendimento;
        mesesPassados++;
        RegistroUtils.registrarMovimentacao(numeroConta, "Rendimento mensal aplicado. Novo saldo: R$ " + saldo);
        System.out.println("Rendimento aplicado. Novo saldo: " + saldo + ". Meses passados: " + mesesPassados);
    }

    @Override
    public int getTipo() {
        return 2;
    }

    @Override
    public void exibirDetalhesConta() {
        System.out.println("Conta Poupança - Número: " + numeroConta + ", Saldo: " + saldo + ", Meses Passados: " + mesesPassados);
    }
}