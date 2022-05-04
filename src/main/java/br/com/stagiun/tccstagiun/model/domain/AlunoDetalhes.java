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

    public void update (Long id, AlunoDetalhes alunoDetalhes) {
        this.id = id;
        this.aluno = alunoDetalhes.getAluno();
        this.turma = alunoDetalhes.getTurma();
        this.ano_de_inicio_curso = alunoDetalhes.getAno_de_inicio_curso();
        this.ano_de_conclusao_curso = alunoDetalhes.getAno_de_conclusao_curso();
        this.experiencia = alunoDetalhes.getExperiencia();
        this.info_adicionais = alunoDetalhes.getInfo_adicionais();
        this.deficiencia = alunoDetalhes.getDeficiencia();
        this.sobre = alunoDetalhes.getSobre();
        this.linkedin = alunoDetalhes.getLinkedin();
        this.github = alunoDetalhes.getGithub();
        this.instagram = alunoDetalhes.getInstagram();
        this.twitter = alunoDetalhes.getTwitter();
        this.facebook = alunoDetalhes.getFacebook();
        this.file_curriculo = alunoDetalhes.getFile_curriculo();
    }

}