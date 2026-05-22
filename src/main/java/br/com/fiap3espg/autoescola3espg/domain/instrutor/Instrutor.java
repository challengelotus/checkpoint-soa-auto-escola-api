package br.com.fiap3espg.autoescola3espg.domain.instrutor;

import br.com.fiap3espg.autoescola3espg.domain.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Instrutor")
@Table(name = "instrutores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id") // gerar hash apenas no id
public class Instrutor {
    @Id // definir chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gerar autoincremento
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cnh;

    @Enumerated(EnumType.STRING) // pois é um enum que virá String
    private Especialidade especialidade;

    @Embedded // pois é uma entidade que virá String
    private Endereco endereco;
    private boolean ativo = true;

    public Instrutor(DadosCadastroInstrutor dados){ // construtor para receber a dto inteira
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cnh = dados.cnh();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoInstrutor dados) {
        if(dados.nome() != null && !dados.nome().isBlank()) {
            this.nome = dados.nome();
        }
        if(dados.email() != null && !dados.email().isBlank()) {
            this.email = dados.email();
        }
        if(dados.telefone() != null && !dados.telefone().isBlank()) {
            this.telefone = dados.telefone();
        }
        if(dados.especialidade() != null) {
            this.especialidade = dados.especialidade();
        }
        if(dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
