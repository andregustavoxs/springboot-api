# springboot-api

<img width="1710" alt="Screenshot 2024-10-01 at 9 50 48 AM" src="https://github.com/user-attachments/assets/ddb098b2-7d7b-4855-a397-23732fbc5637">

Este projeto é sobre um CRUD de produtos. A aplicação segue a arquitetura RESTful e implementa os seguintes conceitos:

- **Verbos HTTP**: São utilizados para definir as operações que podem ser realizadas nos recursos. Por exemplo:

  - *@PostMapping* para criar um novo produto.
 
  - *@GetMapping* para recuperar produtos.
 
  - *@PutMapping* para atualizar um produto existente.
 
  - *@DeleteMapping* para deletar um produto.


- **HATEOAS (Hypermedia as the Engine of Application State)**: É um princípio de REST que adiciona links aos recursos retornados, permitindo a navegação entre eles.

- **DTO (Data Transfer Object)**: Utilizado para transferir dados entre as camadas da aplicação. No caso, `ProductRecordDto` é usado para receber os dados do produto nas requisições.

- **Validação**: Utiliza o pacote `jakarta.validation` para validar os dados recebidos nas requisições.
