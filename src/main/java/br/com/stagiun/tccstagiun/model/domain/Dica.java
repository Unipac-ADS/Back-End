package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "dica", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class Dica extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column
    private String links_uteis;

    @Column(nullable = true)
    private String data_publicacao;

    @ManyToOne
    @JoinColumn(name = "cargo_store_id",referencedColumnName = "id")
    private Cargo cargo;

  }
