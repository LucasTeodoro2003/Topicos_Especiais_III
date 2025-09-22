create table aluno (
    id SERIAL primary key,
    nome text not null,
    idade int not null
);

insert into aluno (nome, idade) values ('Rafael', 44);
insert into aluno (nome, idade) values ('Ana', 42);
insert into aluno (nome, idade) values ('Pedro', 18);
insert into aluno (nome, idade) values ('Maria', 16); 

select * from aluno