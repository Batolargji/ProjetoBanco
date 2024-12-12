package sistemaBancario;
import java.util.Scanner;
public class Dependente extends Usuario {
    private String cpfpai;
    public Dependente(String nome, String senha, String cpf, String cpfpai, String telefone, String email, String rua, String numero, String bairro, String cidade, String uf) {
        super(nome, senha, cpf, telefone, email, rua, numero, bairro, cidade, uf);
        this.cpfpai = cpfpai;
    }

    @Override
    public int getTipo() {
        return 4;
    }


    public String getCpfpai() {
        return cpfpai;
    }

    public void setCpfpai(String cpfpai) {
        this.cpfpai = cpfpai;
    }

    @Override
    public void acessarSistema() {
        boolean sair = false;
        while (!sair) {
            Scanner entrada = new Scanner(System.in);
            System.out.println("\nBem-vindo, Cliente dependente: " + nome);
            System.out.println("1. Sacar de Conta");
            System.out.println("2. Sair");
            int opcao = entrada.nextInt();
            entrada.nextLine();
            switch (opcao) {
                case 1:
                    sacarDeConta();
                    break;
                case 2:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    private void sacarDeConta() {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Informe o ID da sua conta para saque: ");
        String idConta = entrada.nextLine();
        Conta conta = Banco.buscarContaPorId(idConta);

        if (conta != null) {
            System.out.print("Informe o valor do saque: ");
            double valor = entrada.nextDouble();
            entrada.nextLine(); // Consumir nova linha

            System.out.print("Confirme sua senha: ");
            String senha = entrada.nextLine();

            if (this.senha.equals(senha)) {
                if (conta.sacar(valor, true)) {
                    System.out.println("Saque realizado com sucesso.");

                } else {
                    System.out.println("Saldo insuficiente para realizar o saque.");
                }
            } else {
                System.out.println("Senha incorreta. Saque não autorizado.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }
    
    
}
