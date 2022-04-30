package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "aluno_detalhes", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class AlunoDetalhes  extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id" ,referencedColumnName = "id" )
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id" ,referencedColumnName = "id" )
    private Turma turma;

    @Column(nullable = false)
    private String ano_de_inicio_curso;

    @Column
    private String ano_de_conclusao_curso;

    @Column(nullable = false)
    private String experiencia;

    @Column(nullable = false)
    private String info_adicionais;

    @Column(nullable = false)
    private Integer deficiencia;

    @Column
    private String sobre;

    @Column
    private String linkedin;

    @Column
    private String github;

    @Column
    private String instagram;

    @Column
    private String twitter;

    @Column
    private String facebook;

    @Column
    private String file_curriculo;

}