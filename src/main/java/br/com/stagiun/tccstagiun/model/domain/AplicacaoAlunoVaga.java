package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "aplicacao_aluno_vaga", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class AplicacaoAlunoVaga extends IdModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "data_aplicacao")
    private String dataAplicacao;

    @ManyToOne
    @JoinColumn(name = "vaga_id", referencedColumnName = "id")
    private Vaga vaga;

    @ManyToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    private Aluno aluno;

    public void update(Long id, AplicacaoAlunoVaga aplicacaoAlunoVaga) {
        this.id = id;
        this.dataAplicacao = aplicacaoAlunoVaga.getDataAplicacao();
        this.vaga = aplicacaoAlunoVaga.getVaga();
        this.aluno = aplicacaoAlunoVaga.getAluno();
    }
}
