package br.com.stagiun.tccstagiun.mocks;

import br.com.stagiun.tccstagiun.model.domain.*;

import java.math.BigDecimal;

public class DomainMockFactory {

    public static DomainMockFactory getDomainMockFactory() {
        return new DomainMockFactory();
    }

    public Pais getPais() {
        return Pais.builder().descricao("Brasil").build();
    }

    public Pais getPais2() {
        return Pais.builder().descricao("USA").build();
    }

    public Pais getPais3() {
        return Pais.builder().descricao("Mexico").build();
    }

    public Estado getEstado() {
        return Estado.builder().descricao("Minas Gerais").pais(getPais()).build();
    }

    public Estado getEstado2() {
        return Estado.builder().descricao("São Paulo").pais(getPais()).build();
    }

    public Estado getEstado3() {
        return Estado.builder().pais(getPais()).descricao("Parana").build();
    }

    public Cidade getCidade() {
        return Cidade.builder().descricao("Ubelandia").estado(getEstado()).build();
    }

    public Cidade getCidade2() {
        return Cidade.builder().descricao("Uberaba").estado(getEstado()).build();
    }

    public Cidade getCidade3() {
        return Cidade.builder().descricao("Araguari").estado(getEstado()).build();
    }

    public Cidade getCidadeUberlandia() {
        return Cidade.builder().id(1L).descricao
                ("Uberlandia").build();
    }

    public Cidade getCidadeUberaba() {
        return Cidade.builder().id(2L).descricao
                ("Uberaba").build();
    }

    public Cidade getCidadeAraguar() {
        return Cidade.builder().id(3L).descricao
                ("Araguar").build();
    }

    public Bairro getBairro() {
        return Bairro.builder().descricao("Centro").cidade(getCidadeUberlandia()).build();
    }

    public Bairro getBairro2() {
        return Bairro.builder().descricao("Centro").cidade(getCidadeUberaba()).build();
    }

    public Bairro getBairro3() {
        return Bairro.builder().descricao("Centro").cidade(getCidadeAraguar()).build();
    }

    public Cep getCep() {
        return Cep.builder().id(1L).descricao
                ("Uberlandia").build();
    }

    public Endereco getEndereco() {
        return Endereco.builder().id(1l)
                .rua("Afonso Pena")
                .tipo("Av")
                .numero("323")
                .cep(getCep()).build();
    }

    public Faculdade getFaculdade() {
        return Faculdade.builder().id(1L).nome("Unipac").build();
    }

    public Curso getCurso() {
        return Curso.builder().id(1L).descricao("Analise de Sistema").faculdade(getFaculdade()).build();
    }

    public Turma getTurma() {
        return Turma.builder().id(1l).curso(getCurso()).periodo(1).descricao("3B").build();
    }

    public Cargo getCargo() {
        return Cargo.builder().id(1L).descricao("Unipac").areaAtuacao("mpmpmp").beneficios("nao tem").competenciasDesejadas("Nenhuma").experiencia("Junior3223").salario(BigDecimal.valueOf(2999.00)).build();
    }

    public Aluno getAluno() {
        return Aluno.builder().nome("Brasil").build();
    }

    public Aluno getAluno2() {
        return Aluno.builder().nome("Antonio").build();
    }

    public Aluno getAluno3() {
        return Aluno.builder().nome("Lua").build();
    }

    public TipoEmpresa getTipoEmpresa() {
        return TipoEmpresa.builder().id(1L).descricao("Tecnologia").build();
    }

    public Usuario getUsuario() {
        return Usuario.builder().id(1L).nome("root").email("root@localhos.com").senha("123456").build();
    }

    public Empresa getEmpresa() {
        return Empresa.builder().id(1L).tipoEmpresa(getTipoEmpresa()).usuario(getUsuario()).endereco(getEndereco()).nomeFantasia("Empresa X").razaoSocial("Empresa X 23").cnpj(341423L).telefone(3242343L).email("rogerio@fon.com").build();
    }

    public Vaga getVaga() {
        return Vaga.builder()
                .id(1L)
                .nome("dfasdfasdf2")
                .amount(333.00)
                .dataOfertaInicio("2001-01-01 12:45:09")
                .dataOfertaFim("2001-01-01 12:45:09")
                .cargo(getCargo())
                .empresa(getEmpresa()).
                build();
    }

    public AlunoDetalhe getAlunoDetalhe() {
        return AlunoDetalhe
                .builder()
                .id(1L)
                .turma(getTurma())
                .aluno(getAluno())
                .anoDeInicioCurso("2001")
                .anoDeConclusaoCurso("2002")
                .experiencia("experiencia")
                .infoAdicionais("info adincionais")
                .deficiencia(1)
                .sobre("fdafdas")
                .sobre("Sobre")
                .linkedin("linkedin_user")
                .github("github_user")
                .instagram("instagram_user")
                .twitter("twitter_user")
                .facebook("facebook_user")
                .fileCurriculo("fdsafsd")
                .build();
    }

    public Dica getDica() {
        return Dica.builder()
                .id(1L)
                .titulo("dica 1")
                .descricao("dica 1")
                .linksUteis("links udeis")
                .dataPublicacao("2001-01-01")
                .cargo(getCargo())
                .build();
    }

    public Idioma getIdioma() {
        return Idioma
                .builder()
                .id(1L)
                .nome("Ingles")
                .aluno(getAluno())
                .build();
    }

    public Habilidade getHabilidade() {
        return Habilidade
                .builder()
                .id(1l)
                .nome("Musica")
                .aluno(getAluno())
                .build();
    }

    public AplicacaoAlunoVaga getAplicacaoAlunoVaga() {
        return AplicacaoAlunoVaga.builder()
                .id(1L)
                .dataAplicacao("2021:09:01")
                .vaga(getVaga())
                .aluno(getAluno())
                .build();
    }
}