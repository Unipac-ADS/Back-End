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

    @Column(nullable = false, name = "ano_de_inicio_curso")
    private String anoDeInicioCurso;

    @Column(name = "ano_de_conclusao_curso")
    private String anoDeConclusaoCurso;

    @Column(nullable = false)
    private String experiencia;

    @Column(nullable = false, name = "info_adicionais")
    private String infoAdicionais;

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

    @Column(name = "file_curriculo")
    private String fileCurriculo;

    public void update (Long id, AlunoDetalhes alunoDetalhes) {
        this.id = id;
        this.aluno = alunoDetalhes.getAluno();
        this.turma = alunoDetalhes.getTurma();
        this.anoDeInicioCurso = alunoDetalhes.getAnoDeInicioCurso();
        this.anoDeConclusaoCurso = alunoDetalhes.getAnoDeConclusaoCurso();
        this.experiencia = alunoDetalhes.getExperiencia();
        this.infoAdicionais = alunoDetalhes.getInfoAdicionais();
        this.deficiencia = alunoDetalhes.getDeficiencia();
        this.sobre = alunoDetalhes.getSobre();
        this.linkedin = alunoDetalhes.getLinkedin();
        this.github = alunoDetalhes.getGithub();
        this.instagram = alunoDetalhes.getInstagram();
        this.twitter = alunoDetalhes.getTwitter();
        this.facebook = alunoDetalhes.getFacebook();
        this.fileCurriculo = alunoDetalhes.getFileCurriculo();
    }

}