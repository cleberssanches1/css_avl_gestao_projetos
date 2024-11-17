create schema gestao_projetos;

-- gestao_projetos.tb_cargo definição

-- Drop table

-- DROP TABLE gestao_projetos.tb_cargo;

CREATE TABLE gestao_projetos.tb_cargo (
	cd_cargo serial4 NOT NULL,
	ts_modificacao timestamp(6) NULL,
	ds_cargo varchar(255) NULL,
	nm_cargo varchar(255) NULL,
	st_cargo varchar(255) NULL,
	CONSTRAINT tb_cargo_pkey PRIMARY KEY (cd_cargo)
);


-- gestao_projetos.tb_status_projeto definição

-- Drop table

-- DROP TABLE gestao_projetos.tb_status_projeto;

CREATE TABLE gestao_projetos.tb_status_projeto (
	cd_status serial4 NOT NULL,
	ts_modificacao timestamp(6) NULL,
	ds_status varchar(255) NULL,
	CONSTRAINT tb_status_projeto_pkey PRIMARY KEY (cd_status)
);


-- gestao_projetos.tb_colaborador definição

-- Drop table

-- DROP TABLE gestao_projetos.tb_colaborador;

CREATE TABLE gestao_projetos.tb_colaborador (
	cd_cargo int4 NULL,
	cd_colaborador serial4 NOT NULL,
	dt_inicio_colaborador date NULL,
	ts_modificacao timestamp(6) NULL,
	cd_cpf varchar(255) NULL,
	nm_colaborador varchar(255) NULL,
	st_colaborador varchar(255) NULL,
	CONSTRAINT tb_colaborador_pkey PRIMARY KEY (cd_colaborador),
	CONSTRAINT fk2okwjnd1ahgy8117s43np2o6n FOREIGN KEY (cd_cargo) REFERENCES gestao_projetos.tb_cargo(cd_cargo)
);


-- gestao_projetos.tb_projeto definição

-- Drop table

-- DROP TABLE gestao_projetos.tb_projeto;

CREATE TABLE gestao_projetos.tb_projeto (
	cd_gerente_responsavel int4 NULL,
	cd_projeto serial4 NOT NULL,
	cd_status int4 NULL,
	dt_inicio_projeto date NULL,
	dt_previsao_termino_projeto date NULL,
	dt_real_termino_projeto date NULL,
	vr_orcamento_total numeric(38, 2) NULL,
	ts_modificacao timestamp(6) NULL,
	ds_projeto varchar(255) NULL,
	nm_projeto varchar(255) NULL,
	CONSTRAINT tb_projeto_pkey PRIMARY KEY (cd_projeto),
	CONSTRAINT fk29ulpljp14j0u4a03cenfxpbq FOREIGN KEY (cd_status) REFERENCES gestao_projetos.tb_status_projeto(cd_status),
	CONSTRAINT fkliyexjsl80wwhsa3oepf7scyx FOREIGN KEY (cd_gerente_responsavel) REFERENCES gestao_projetos.tb_colaborador(cd_colaborador)
);

-- Criar a sequence para a tabela tb_cargo
CREATE SEQUENCE gestao_projetos.seq_tb_cargo
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

-- Alterar a tabela para usar a sequence
ALTER TABLE gestao_projetos.tb_cargo
ALTER COLUMN cd_cargo SET DEFAULT nextval('gestao_projetos.seq_tb_cargo');


-- Criar a sequence para a tabela tb_status_projeto
CREATE SEQUENCE gestao_projetos.seq_tb_status_projeto
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

-- Alterar a tabela para usar a sequence
ALTER TABLE gestao_projetos.tb_status_projeto
ALTER COLUMN cd_status SET DEFAULT nextval('gestao_projetos.seq_tb_status_projeto');


-- Criar a sequence para a tabela tb_colaborador
CREATE SEQUENCE gestao_projetos.seq_tb_colaborador
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

-- Alterar a tabela para usar a sequence
ALTER TABLE gestao_projetos.tb_colaborador
ALTER COLUMN cd_colaborador SET DEFAULT nextval('gestao_projetos.seq_tb_colaborador');


-- Criar a sequence para a tabela tb_projeto
CREATE SEQUENCE gestao_projetos.seq_tb_projeto
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

-- Alterar a tabela para usar a sequence
ALTER TABLE gestao_projetos.tb_projeto
ALTER COLUMN cd_projeto SET DEFAULT nextval('gestao_projetos.seq_tb_projeto');



