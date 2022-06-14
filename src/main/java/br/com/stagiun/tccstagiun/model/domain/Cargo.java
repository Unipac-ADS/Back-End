package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cargo", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = {"description"})
@Builder
@Data
public class Cargo extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column
    private String experiencia;

    @Column(name = "area_atuacao")
    private String areaAtuacao;

    @Column
    private String beneficios;

    @Column
    private BigDecimal salario;

    @Column(name = "habilidades_desejadas")
    private String habilidadesDesejadas;

    @Column(name = "competencias_desejadas")
    private String competenciasDesejadas;

    public void update(Long id, Cargo cargo) {
        this.id = id;
        this.descricao = cargo.getDescricao();
        this.experiencia = cargo.getExperiencia();
        this.areaAtuacao = cargo.getAreaAtuacao();
        this.beneficios = cargo.getBeneficios();
        this.salario = cargo.getSalario();
        this.habilidadesDesejadas = cargo.getHabilidadesDesejadas();
        this.competenciasDesejadas = cargo.getCompetenciasDesejadas();
    }

}
