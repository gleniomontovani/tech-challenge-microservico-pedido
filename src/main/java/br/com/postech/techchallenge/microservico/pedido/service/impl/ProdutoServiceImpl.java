package br.com.postech.techchallenge.microservico.pedido.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.postech.techchallenge.microservico.pedido.comum.converts.CategoriaParaInteiroConverter;
import br.com.postech.techchallenge.microservico.pedido.comum.enums.CategoriaEnum;
import br.com.postech.techchallenge.microservico.pedido.configuration.ModelMapperConfiguration;
import br.com.postech.techchallenge.microservico.pedido.entity.Produto;
import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pedido.model.request.ProdutoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ProdutoResponse;
import br.com.postech.techchallenge.microservico.pedido.repository.ProdutoJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.service.ProdutoService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();
    private final ProdutoJpaRepository produtoJpaRepository;

    @Override
    public List<ProdutoResponse> findByCategoria(Integer idCategoria) {
    	CategoriaEnum categoria = CategoriaEnum.get(idCategoria);
    	MAPPER.typeMap(Produto.class, ProdutoResponse.class)
		.addMappings(mapperA -> mapperA.using(new CategoriaParaInteiroConverter())
				.map(Produto::getCategoria, ProdutoResponse::setCategoria));

        return MAPPER.map(produtoJpaRepository.findByCategoria(categoria),
                new TypeToken<List<ProdutoResponse>>() {
                }.getType());
    }

    @Override
    public ProdutoResponse findById(Long id) {
    	MAPPER.typeMap(Produto.class, ProdutoResponse.class)
		.addMappings(mapperA -> mapperA.using(new CategoriaParaInteiroConverter())
				.map(Produto::getCategoria, ProdutoResponse::setCategoria));
    	
        Produto produto = produtoJpaRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado!"));

        return MAPPER.map(produto, ProdutoResponse.class);
    }

    @Override
    public ProdutoResponse save(ProdutoRequest produtoRequest) throws BusinessException {	
        var produto = MAPPER.map(produtoRequest, Produto.class);
        produto.setCategoria(CategoriaEnum.get(produtoRequest.categoria()));
        
        validateImagesProduto(produto);
        produto = produtoJpaRepository.save(produto);

    	MAPPER.typeMap(Produto.class, ProdutoResponse.class)
			.addMappings(mapperA -> mapperA.using(new CategoriaParaInteiroConverter())
					.map(Produto::getCategoria, ProdutoResponse::setCategoria));
        
        return MAPPER.map(produto, ProdutoResponse.class);
    }


    @Override
    public ProdutoResponse atualizar(Long id, ProdutoRequest produtoRequest) {
        var produto = MAPPER.map(produtoRequest, Produto.class);
        validateImagesProduto(produto);

        produtoJpaRepository.findById(id).
                orElseThrow(() -> new BusinessException("Produto não encontrado!"));

        produto = produtoJpaRepository.save(produto);
        
        MAPPER.typeMap(Produto.class, ProdutoResponse.class)
		.addMappings(mapperA -> mapperA.using(new CategoriaParaInteiroConverter())
				.map(Produto::getCategoria, ProdutoResponse::setCategoria));

        return MAPPER.map(produto, ProdutoResponse.class);
    }

    @Override
    public void deleteById(Long id) {
		produtoJpaRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado!"));
    	
        produtoJpaRepository.deleteById(id);
    }

    private void validateImagesProduto(Produto produto) {
        Optional.ofNullable(produto.getImagens())
                .orElseThrow(() -> new BusinessException("É obrigatório informar pelo menos uma imgem para o produto!"))
                .stream()
                .filter(img -> Objects.isNull(img.getProduto()))
                .forEach(img -> {
                    img.setProduto(produto);
                });
    }
}
