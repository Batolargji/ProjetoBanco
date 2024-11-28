package sistemaBancario;

abstract class Usuario {
    protected String nome;
    protected String senha;
    protected String cpf;
    protected String endereco;
    protected String telefone;
    // Construtor
    public Usuario(String nome, String senha, String cpf, String endereco, String telefone) {
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public abstract int getTipo();
    public abstract void acessarSistema();
}
