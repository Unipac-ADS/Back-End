package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "cep", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class Cep extends IdModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "bairro_id", referencedColumnName = "id")
    private Bairro bairro;

}
