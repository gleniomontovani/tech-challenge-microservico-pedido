package br.com.postech.techchallenge.microservico.pedido.configuration;

import java.util.function.Supplier;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;

import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.model.response.ClienteResponse;

class NullableMapper extends ModelMapper{

	@Override
	public <D> D map(Object source, Class<D> destinationType) {
		if(source == null) {
			return null;
		}
		return super.map(source, destinationType);
	}
}

@Provider
public class ModelMapperConfiguration implements ContextResolver<ModelMapper> {

	@Override
	public ModelMapper getContext(Class<?> type) {
		return getModelMapper();
	}

	public static ModelMapper getModelMapper() {
		ModelMapper modelMapper = new NullableMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setFieldMatchingEnabled(true);
		modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
		modelMapper.addConverter(converterStringToString());
		mapEntityToResponse(modelMapper);
		
		return modelMapper;
	}
	
	private static Converter<String, String> converterStringToString() {
		return new AbstractConverter<String, String>() {
			protected String convert(String source) {
				return source == null ? null : source.trim();
			}
		};
	}
	
	private static void mapEntityToResponse(final ModelMapper modelMapper) {
		modelMapper.createTypeMap(Cliente.class, ClienteResponse.class).addMappings(mapper -> {
			  mapper.map(src -> src.getId(), ClienteResponse::setNumero);
		});
	}
	
	public static <S, D> Converter <S, D> converterWithDestinationSupplier(Supplier<? extends D> supplier) {
	    return ctx -> ctx.getMappingEngine().map(ctx.create(ctx.getSource(), supplier.get()));
	}
}
