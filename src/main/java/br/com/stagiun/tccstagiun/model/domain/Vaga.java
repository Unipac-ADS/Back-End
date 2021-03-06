package br.com.stagiun.tccstagiun.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    ///teste
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, name = "data_oferta_inicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private String dataOfertaInicio;

    @Column(name = "data_oferta_fim")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private String dataOfertaFim;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "cargo_store_id", referencedColumnName = "id")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    public void update(Long id, Vaga vaga) {
        this.id = id;
        this.amount = vaga.getAmount();;
        this.dataOfertaInicio = vaga.getDataOfertaInicio();
        this.dataOfertaFim = vaga.getDataOfertaFim();
        this.nome = vaga.getNome();
        this.cargo = vaga.getCargo();
        this.empresa = vaga.getEmpresa();
    }

}
