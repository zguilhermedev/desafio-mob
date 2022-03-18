CREATE TABLE IF NOT EXISTS public.setor (
	id bigserial NOT NULL,
	descricao varchar(255) NULL,
	CONSTRAINT setor_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.colaborador (
	id bigserial NOT NULL,
	cpf varchar(255) NULL,
	data_nascimento timestamp NULL,
	email varchar(255) NULL,
	nome varchar(255) NULL,
	telefone varchar(255) NULL,
	setor_id int8 NULL,
	CONSTRAINT colaborador_pkey PRIMARY KEY (id)
);

ALTER TABLE public.colaborador ADD CONSTRAINT fk_colaborador_setor FOREIGN KEY (setor_id) REFERENCES public.setor(id);