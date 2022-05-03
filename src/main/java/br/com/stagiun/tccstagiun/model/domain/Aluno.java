package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "aluno", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class Aluno extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id" ,referencedColumnName = "id" )
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "endereco_id" ,referencedColumnName = "id" )
    private Endereco endereco;

    @Column(nullable = false)
    private String nome;

    @Column
    private String matricula;

    @Column(nullable = false)
    private Integer cpf;

    @Column(nullable = false)
    private Integer telefone;

    @Column
    private String email;

    @Column(name = "curriculo_de_aluno", nullable = true)
    private String curriculo;

    public void update(Long id, Aluno aluno) {
        this.id = id;
        this.usuario = aluno.getUsuario();
        this.endereco = aluno.getEndereco();
        this.nome = aluno.getNome();
        this.matricula = aluno.getMatricula();
        this.cpf = aluno.getCpf();
        this.telefone = aluno.getTelefone();
        this.email = aluno.getEmail();
        this.curriculo = aluno.getCurriculo();
    }
}
