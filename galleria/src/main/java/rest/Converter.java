package rest;

public interface Converter<T, R> {

	public R dtoFromEntity(T g);

	public T entityFromDTO(R g_dto);

}
