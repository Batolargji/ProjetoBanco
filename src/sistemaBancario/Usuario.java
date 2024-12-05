package sistemaBancario;

abstract class Usuario {
    protected String nome;
    protected String senha;
    protected String cpf;
    protected String endereco;
    protected String telefone;
    protected String nomeUsuario;

    public Usuario(String nome, String nomeUsuario, String senha, String cpf, String endereco, String telefone) {
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public abstract int getTipo();

    public abstract void acessarSistema();

    public Object getNome() {
        // TODO Auto-generated method stub
        return nome;
    }

    public Object getCpf() {
        // TODO Auto-generated method stub
        return cpf;
    }

    public Object getEndereco() {
        // TODO Auto-generated method stub
        return endereco;
    }

    public Object getTelefone() {
        // TODO Auto-generated method stub
        return telefone;
    }

}
