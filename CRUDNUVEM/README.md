# CRUDNUVEM

**Aluno:** Thierry Alves Mota Justino
**RA:** 0023636

CRUD de produtos em Java com JDBC e banco MySQL hospedado no Railway.

## Funcionalidades

1. Inserir produto
2. Listar (consultar por código)
3. Atualizar preço
4. Deletar produto

## Detalhes

- Classe principal: `CRUDNUVEM.atv.Banco`
- Conector: `mysql-connector-j-26.10.0.jar` (pasta `lib`)
- Tabela: `produto (codigo, nome, preco)`, criada automaticamente na primeira execução

## Como executar

1. Abra a pasta `CRUDNUVEM` no VS Code.
2. Confira se o `.jar` da pasta `lib` aparece em **Referenced Libraries**.
3. Informe o host, a porta e a senha do banco no topo da classe `Banco`. Esses dados foram removidos do código por segurança, pois o repositório é público.
4. Rode a classe `Banco`.
