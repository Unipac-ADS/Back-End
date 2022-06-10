package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "empresa", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = {"description"})
@Builder
@Data
public class Empresa extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_empresa_id", referencedColumnName = "id")
    private TipoEmpresa tipoEmpresa;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @Column(nullable = false, name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(nullable = false)
    private Long cnpj;

    @Column(nullable = false)
    private Long telefone;

    @Column(nullable = false)
    private String email;

    public void update(Long id, Empresa empresa) {
        this.id = id;
        //this.tipoEmpresa = empresa.getTipoEmpresa();
        //this.usuario = empresa.getUsuario();
       // this.endereco = empresa.getEndereco();
        this.nomeFantasia = empresa.getNomeFantasia();
        this.razaoSocial = empresa.getRazaoSocial();
        this.cnpj = empresa.getCnpj();
        this.telefone = empresa.getTelefone();
        this.email = empresa.getEmail();
    }

}
