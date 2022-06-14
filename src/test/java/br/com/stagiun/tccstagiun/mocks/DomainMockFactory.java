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
        return Cidade.builder().descricao("Uberlândia").estado(getEstado()).build();
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
                ("48688-125").build();
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
        return Curso.builder().id(1L).descricao("Análise e Desenvolvimento de Sistemas").faculdade(getFaculdade()).build();
    }

    public Turma getTurma() {
        return Turma.builder().id(1l).curso(getCurso()).periodo(5).descricao("ADS").build();
    }

    public Cargo getCargo() {
        return Cargo.builder().id(1L).descricao("Dev Front-End").areaAtuacao("Web").beneficios("Tudo").habilidadesDesejadas("Inglês").competenciasDesejadas("HTML/CSS, JavaScript, React/Angular").experiencia("Júnior").salario(BigDecimal.valueOf(2999.00).setScale(2)).build();
    }

    public TipoEmpresa getTipoEmpresa() {
        return TipoEmpresa.builder().id(1L).descricao("Tecnologia").build();
    }

    public Usuario getUsuario() {
        return Usuario.builder().id(1L).nome("Playboyzinho").email("palyboyzinho@gmail.com").senha("Mjolnir").build();
    }

    public Empresa getEmpresa() {
        return Empresa.builder()
                .id(1L)
                .tipoEmpresa(getTipoEmpresa())
                .usuario(getUsuario())
                .endereco(getEndereco())
                .nomeFantasia("Stagiun")
                .razaoSocial("ADS-Stagiun")
                .cnpj(22244455L)
                .telefone(32324545L)
                .email("stagiun@gmail.com").build();
    }

    public Vaga getVaga() {
        return Vaga.builder()
                .id(1L)
                .nome("Vaga Desenvolvedor Java")
                .amount(333.00)
                .dataOfertaInicio("26/05/2022")
                .dataOfertaFim("30/05/2022")
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
                .titulo("Como virar um ninja em Java")
                .descricao("Aprenda o que estudar para se tornar um ninja")
                .linksUteis("links")
                .dataPublicacao("25/05/2022")
                .cargo(getCargo())
                .build();
    }

    public Idioma getIdioma() {
        return Idioma
                .builder()
                .id(1L)
                .nome("Inglês")
                .aluno(getAluno())
                .build();
    }

    public Habilidade getHabilidade() {
        return Habilidade
                .builder()
                .id(1l)
                .nome("Proativo")
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

    public AlunoDetalhe getAlunoDetalhes() {
        return AlunoDetalhe.builder()
                .anoDeInicioCurso("07/02/2020")
                .anoDeConclusaoCurso("17/07/2022")
                .experiencia("Dev Full Stack")
                .infoAdicionais("Full Stack")
                .deficiencia(0)
                .sobre("Full Stack")
                .linkedin("linkedin.com/in/thor")
                .github("github/thor")
                .instagram("Thor Filho de Odin")
                .twitter("Thor Filho de Odin")
                .fileCurriculo("CvThor")
                .build();
    }

    public Aluno getAluno() {
        return Aluno.builder()
                .nome("Maria")
                .matricula("ADS001")
                .cpf(1245683215L)
                .telefone(32564748L)
                .email("brteste@hotmail.com")
                .curriculo("cvteste1")
                .build();
    }

    public Aluno getAluno1() {
        return Aluno.builder()
                .nome("Antonio")
                .matricula("ADS001")
                .cpf(1245683215L)
                .telefone(32564748L)
                .email("brteste@hotmail.com")
                .curriculo("cvteste1")
                .build();
    }

    public Aluno getAluno2() {
        return Aluno.builder()
                .nome("José")
                .matricula("ADS001")
                .cpf(1245683215L)
                .telefone(32564748L)
                .email("brteste@hotmail.com")
                .curriculo("cvteste1")
                .build();
    }

    public Aluno getAluno3() {
        return Aluno.builder()
                .nome("Joana")
                .matricula("ADS001")
                .cpf(1245683215L)
                .telefone(32564748L)
                .email("brteste@hotmail.com")
                .curriculo("cvteste1")
                .build();
    }

    public Perfil getPerfil() {
        return Perfil.builder().id(1l).descricao("Aluno").build();
    }
}