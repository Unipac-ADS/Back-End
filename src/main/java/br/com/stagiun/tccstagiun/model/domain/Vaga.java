package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vaga", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class Vaga extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer aplicado;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String data_oferta_inicio;

    @Column
    private String data_oferta_fim;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "cargo_store_id", referencedColumnName = "id")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;
}
