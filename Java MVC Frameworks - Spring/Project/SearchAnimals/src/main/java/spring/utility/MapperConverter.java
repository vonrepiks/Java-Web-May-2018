package spring.utility;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperConverter {

    private ModelMapper modelMapper;

    public MapperConverter() {
        this.modelMapper = new ModelMapper();
    }

    public  <S, D> D convert(S source, Class<D> destinationClass) {
        return this.modelMapper.map(source, destinationClass);
    }
}