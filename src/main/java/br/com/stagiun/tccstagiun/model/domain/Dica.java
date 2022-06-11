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

    @Column(name = "links_uteis")
    private String linksUteis;

    @Column(nullable = true, name = "data_publicacao")
    private String dataPublicacao;

    @ManyToOne
    @JoinColumn(name = "cargo_store_id",referencedColumnName = "id")
    private Cargo cargo;

    public void update(Long id, Dica dica) {
        this.id = id;
        this.titulo = dica.getTitulo();
        this.descricao = dica.getDescricao();
        this.linksUteis = dica.getLinksUteis();
        this.dataPublicacao = dica.getDataPublicacao();
        this.cargo = dica.getCargo();
    }

  }
