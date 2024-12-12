package sistemaBancario;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class RegistroUtils {
    private static final String PASTA_USUARIOS = "usuarios/";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void registrarMovimentacao(String cpf, String mensagem) {
        cpf = Banco.buscarCpfporIDConta(cpf);
        String nomeArquivo = PASTA_USUARIOS + cpf + ".txt"; // Usa o CPF do usuário para localizar o arquivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            String dataHora = LocalDateTime.now().format(FORMATTER);
            bw.write(dataHora + ": " + mensagem);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao registrar movimentação: " + e.getMessage());
        }
    }

    public static void carregarSaldoConta(Conta conta) throws IOException {
        String cpf = Banco.buscarCpfporIDConta(conta.getNumeroConta());
        String arquivo = "usuarios/" + cpf + ".txt";
        Path path = Paths.get(arquivo);
        List<String> linhas = Files.readAllLines(path);
        if (conta.getTipo() == 1) {
            ContaCorrentePrincipal conta2 = (ContaCorrentePrincipal) conta;
            String novoSaldo = "Saldo da conta corrente: " + conta.getSaldo();
            linhas.remove(8);
            linhas.add(8,novoSaldo);
            Files.write(path, linhas);
            carregarLimiteChequeEspecial(conta2);
        }
        else if (conta.getTipo() == 2) {
            String novoSaldo = "Saldo da conta poupanca: " + conta.getSaldo();
            System.out.println(linhas);
            linhas.remove(11);
            linhas.add(11,novoSaldo);
            Files.write(path, linhas);
        }
    }
    public static void carregarLimiteChequeEspecial(ContaCorrentePrincipal conta) throws IOException {
        String cpf = Banco.buscarCpfporIDConta(conta.getNumeroConta());
        String arquivo = "usuarios/" + cpf + ".txt";
        Path path = Paths.get(arquivo);
        List<String> linhas = Files.readAllLines(path);
        if (conta.getTipo() == 1) {
            String novoCheque = "Limite do cheque especial: " + conta.getLimiteChequeEspecial();
            linhas.remove(9);
            linhas.add(9,novoCheque);
            Files.write(path, linhas);
        }
    }

    public static void criarArquivoUsuario(Usuario usuario, ContaCorrentePrincipal contaCorrente, ContaPoupanca contaPoupanca) {
        String nomeArquivo = PASTA_USUARIOS + usuario.getCpf() + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            bw.write("[Informações do Usuário]");
            bw.newLine();
            bw.write("Nome: " + usuario.getNome());
            bw.newLine();
            bw.write("CPF: " + usuario.getCpf());
            bw.newLine();
            bw.write("Senha: " + usuario.getSenha());
            bw.newLine();
            bw.write("Telefone: " + usuario.getTelefone());
            bw.newLine();
            // Concatenação dos atributos para formar o endereço completo
            bw.write("Endereço: " + usuario.getRua() + ", " + usuario.getNumero() + ", " + usuario.getBairro() + ", " + usuario.getCidade() + "-" + usuario.getUf());
            bw.newLine();
            bw.write("Tipo de Usuário: " + usuario.getTipo());
            bw.newLine();
            bw.write("ID da Conta Corrente: " + contaCorrente.getNumeroConta());
            bw.newLine();
            bw.write("Saldo da conta corrente: " + contaCorrente.getSaldo());
            bw.newLine();
            bw.write("Limite do cheque especial: " + contaCorrente.getLimiteChequeEspecial());
            bw.newLine();
            bw.write("ID da Conta Poupança: " + contaPoupanca.getNumeroConta());
            bw.newLine();
            bw.write("Saldo da conta poupança: " + contaPoupanca.getSaldo());
            bw.newLine();
            bw.write("[Histórico de Movimentação]");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de usuário: " + e.getMessage());
        }
    }
    
    public static void registrarDependente(String cpfTitular, Dependente dependente, String idContaDependente) {
        String arquivoTitular = "usuarios/" + cpfTitular + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTitular, true))) {
            writer.write("[Dependente Adicionado]");
            writer.newLine();
            writer.write("Nome: " + dependente.getNome());
            writer.newLine();
            writer.write("CPF: " + dependente.getCpf());
            writer.newLine();
            writer.write("Senha: " + dependente.getSenha());
            writer.newLine();
            writer.write("Telefone: " + dependente.getTelefone());
            writer.newLine();
            writer.write("E-mail: " + dependente.getEmail());
            writer.newLine();
            // Aqui foi utilizada a concatenação correta do endereço
            writer.write("Endereço: " + dependente.getRua() + ", " + dependente.getNumero() + ", " + dependente.getBairro() + ", " + dependente.getCidade() + "-" + dependente.getUf());
            writer.newLine();
            writer.write("ID da Conta Corrente: " + idContaDependente);
            writer.newLine();
            writer.write("----------------------------------");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao registrar dependente: " + e.getMessage());
        }
    }
}