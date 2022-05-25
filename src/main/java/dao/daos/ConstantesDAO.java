package dao.daos;

public class ConstantesDAO {

    //eventos
    public static final String GET_ALL_EVENTOS = "select eventos.id, eventos.titulo, eventos.descripcion, eventos.img, eventos.pag_oficial_evento, eventos.fecha, p.nombre as provincia, tc.nombre as tipo from eventos join provincia p on eventos.provincia = p.id_provincia join tipo_carrera tc on tc.id = eventos.tipo";
    public static final String EVENTOS_FILTRADOS_POR_TIPO_CARRERA = "select eventos.id,eventos.titulo, eventos.descripcion, eventos.img, eventos.pag_oficial_evento, eventos.fecha, p.nombre as provincia, tc.nombre as tipo from eventos join provincia p on eventos.provincia = p.id_provincia join tipo_carrera tc on tc.id = eventos.tipo WHERE tc.nombre = ?";
    public static final String EVENTOS_FILTRADOS_POR_PROVINCIA = "select eventos.id,eventos.titulo, eventos.descripcion, eventos.img, eventos.pag_oficial_evento, eventos.fecha, p.nombre as provincia, tc.nombre as tipo from eventos join provincia p on eventos.provincia = p.id_provincia join tipo_carrera tc on tc.id = eventos.tipo WHERE p.nombre = ?";
    public static final String EVENTOS_FILTRADOS_POR_PROVINCIA_y_TIPO = "select eventos.id,eventos.titulo, eventos.descripcion, eventos.img, eventos.pag_oficial_evento, eventos.fecha, p.nombre as provincia, tc.nombre as tipo from eventos join provincia p on eventos.provincia = p.id_provincia join tipo_carrera tc on tc.id = eventos.tipo WHERE p.nombre = ? AND tc.nombre = ?";
    public static final String GET_EVENTO_BY_ID = "select eventos.id, eventos.titulo, eventos.descripcion, eventos.img, eventos.pag_oficial_evento, eventos.fecha, p.nombre as provincia, tc.nombre as tipo from eventos join provincia p on eventos.provincia = p.id_provincia join tipo_carrera tc on tc.id = eventos.tipo where eventos.id = ?";

    public static final String INSERT_EVENTOS ="insert into eventos (titulo, descripcion, img, tipo, provincia, pag_oficial_evento, fecha) VALUES (?,?,?,?,?,?,?)";

    //carreras

    //public static final String GET_ALL_CARRERAS = "select carreras.titulo, carreras.descripcion, carreras.img, carreras.km, carreras.ciudad, carreras.precio, carreras.pag_oficial_evento, carreras.enlace_compra, carreras.fecha, p.nombre as \"provincia\", tc.nombre as \"tipo\" from carreras join provincia p on p.id_provincia = carreras.provincia join tipo_carrera tc on tc.id = carreras.tipo";
    //public static final String GET_ULTIMAS_CARRERAS_ADD_5 = "select carreras.titulo, carreras.descripcion, carreras.img, carreras.km, carreras.ciudad, carreras.precio, carreras.pag_oficial_evento, carreras.enlace_compra, carreras.fecha, p.nombre as \"provincia\", tc.nombre as \"tipo\" from carreras join provincia p on p.id_provincia = carreras.provincia join tipo_carrera tc on tc.id = carreras.tipo order by carreras.id desc limit 5";
    //public static final String FILTRADAS_POR_TIPO_CARRERA = "select carreras.titulo, carreras.descripcion, carreras.img, carreras.km, carreras.ciudad, carreras.precio, carreras.pag_oficial_evento, carreras.enlace_compra, carreras.fecha, p.nombre as \"provincia\", tc.nombre as \"tipo\" from carreras join provincia p on p.id_provincia = carreras.provincia join tipo_carrera tc on tc.id = carreras.tipo where tc.nombre = ?";
    //public static final String FILTRADAS_POR_PROVINCIA = "select carreras.titulo, carreras.descripcion, carreras.img, carreras.km, carreras.ciudad, carreras.precio, carreras.pag_oficial_evento, carreras.enlace_compra, carreras.fecha, p.nombre as \"provincia\", tc.nombre as \"tipo\" from carreras join provincia p on p.id_provincia = carreras.provincia join tipo_carrera tc on tc.id = carreras.tipo where p.nombre = ?";

    public static final String GET_CARRERAS_BY_ID_EVENTO = "select e.id as id_evento, carreras.id, carreras.titulo, carreras.descripcion, carreras.img, carreras.km, carreras.ciudad, carreras.precio, carreras.enlace_compra, carreras.evento as id_evento from carreras join eventos e on e.id = carreras.evento where carreras.evento = ?";
    public static final String GET_CARRERA_BY_ID = "select carreras.id, carreras.titulo, carreras.descripcion, carreras.img, carreras.km, carreras.ciudad, carreras.precio, carreras.enlace_compra, carreras.evento as id_evento from carreras where id = ?";

    public static final String GET_CARRERAS_BY_USER = "select carreras.id as id, carreras.titulo, carreras.descripcion, carreras.img, km, ciudad, precio, enlace_compra, evento, usuario, id_carrera, p.nombre as provincia, tc.nombre as tipo from carreras join carreras_favoritas cf on carreras.id = cf.id_carrera join eventos e on e.id = carreras.evento join tipo_carrera tc on e.tipo = tc.id join provincia p on e.provincia = p.id_provincia where cf.usuario = ?";

    public static final String INSERTAR_CARRERAS = "insert into carreras(titulo, descripcion, img, km, ciudad, precio, enlace_compra, evento) VALUES (?,?,?,?,?,?,?,?)";
    public static final String GET_ALL_CARRERAS= "select * from carreras";

    //provincias

    public static final String FILTROS_PROVINCIAS = "select nombre from provincia";

    //tipo carreras

    public static final String FILTROS_TIPO_CARRERA = "select nombre from tipo_carrera";

}
