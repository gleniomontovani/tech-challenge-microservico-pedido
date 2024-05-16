# language: pt

Funcionalidade: API Microserviço de Pedidos

	Cenário: Fazer Checkout Fake de Pedido
		Quando submeter uma nova requisição de criação de pedido
		Então o pedido é registrado com sucesso
		
	Cenário: Buscar um pedido existente
		Dado que o pedido já foi cadastrado
		Quando requisitar a busca de um pedido pelo número
		Então o pedido é exibido com sucesso
		
	Cenário: Listar produtos de um pedido
		Dado que o pedido já foi cadastrado
		Quando requisitar a lista dos produtos de um pedido
		Então os produtos desse pedido é exibido com sucesso	
	
	Cenário: Listar todos pedido ativos
		Quando requisitar a lista de pedidos ativos
		Então os pedidos ativos serão exibidos com sucesso
		
	Cenário: Atualizar um pedido existente
		Dado que o pedido já foi cadastrado
		Quando requisitar um alteração do pedido
		Então o pedido é atualizado com sucesso
