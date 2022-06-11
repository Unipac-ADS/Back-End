package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "curso", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class Curso extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "faculdade_id", referencedColumnName = "id")
    private Faculdade faculdade;

    public void update(Long id, Curso curso) {
        this.id = id;
        this.descricao = curso.getDescricao();
        this.faculdade = curso.getFaculdade();
    }

}
