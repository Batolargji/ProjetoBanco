package sistemaBancario;

abstract class Usuario {
    protected String nome;
    protected String senha;
    protected String cpf;
    protected String telefone;
    protected String email;
    protected String rua;
    protected String numero;
    protected String bairro;
    protected String cidade;
    protected String uf;

    public Usuario(String nome, String senha, String cpf, String telefone, String email, String rua, String numero, String bairro, String cidade, String uf) {
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public abstract int getTipo();

    public abstract void acessarSistema();

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }
}