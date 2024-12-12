package sistemaBancario;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroUtils {
    private static final String PASTA_USUARIOS = "usuarios/";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void registrarMovimentacao(String cpf, String mensagem) {
        String nomeArquivo = PASTA_USUARIOS + cpf + ".txt"; // Usa o CPF do usuário para localizar o arquivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            String dataHora = LocalDateTime.now().format(FORMATTER);
            bw.write(dataHora + ": " + mensagem);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao registrar movimentação: " + e.getMessage());
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