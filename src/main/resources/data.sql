INSERT INTO SIS_VAGA.PAIS (ID, DESCRICAO, ULTIMA_ALTERACAO) VALUES (1, 'Brasil', '2022-06-11 09:00:18.656628');
INSERT INTO SIS_VAGA.ESTADO (ID, DESCRICAO, ULTIMA_ALTERACAO, PAIS_ID) VALUES (1, 'Minas Gerais', '2022-06-11 09:00:18.656628', 1);
INSERT INTO SIS_VAGA.CIDADE (ID, DESCRICAO, ULTIMA_ALTERACAO, ESTADO_ID) VALUES (1, 'Uberlândia', '2022-06-11 09:00:18.656628', 1);
INSERT INTO SIS_VAGA.BAIRRO (ID, DESCRICAO, ULTIMA_ALTERACAO, CIDADE_ID) VALUES (1, 'Centro', '2022-06-11 09:00:18.656628', 1);
INSERT INTO SIS_VAGA.CEP (ID, DESCRICAO, ULTIMA_ALTERACAO, BAIRRO_ID) VALUES (1, '38400464', '2022-06-11 09:00:18.656628', 1);
INSERT INTO SIS_VAGA.ENDERECO (ID, NUMERO, RUA, TIPO, ULTIMA_ALTERACAO, CEP_ID) VALUES (1, 'Av', 'Joao Naves', 111, '2022-06-11 09:00:18.656628', 1);



