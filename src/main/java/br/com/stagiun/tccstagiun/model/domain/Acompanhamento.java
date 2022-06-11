package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "acompanhamento", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = {"description"})
@Builder
@Data
public class Acompanhamento extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dataAplicacao;

    @ManyToOne
    @JoinColumn(name = "aplicacao_aluno_vaga_id", referencedColumnName = "id")
    private AplicacaoAlunoVaga aplicacaoAlunoVaga;

    public void update(Long id, Acompanhamento acompanhamento) {
        this.id = id;
        this.dataAplicacao = acompanhamento.getDataAplicacao();
        this.aplicacaoAlunoVaga = acompanhamento.getAplicacaoAlunoVaga();
    }

}
