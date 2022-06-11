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
    public static final String GET_CARRERAS_BY_ID_EVENTO = "select e.id as id_evento, carreras.id, carreras.titulo, carreras.descripcion, carreras.img, carreras.km, carreras.ciudad, carreras.precio, carreras.enlace_compra, carreras.evento as id_evento from carreras join eventos e on e.id = carreras.evento where carreras.evento = ?";
    public static final String GET_CARRERA_BY_ID = "select carreras.id, carreras.titulo, carreras.descripcion, carreras.img, carreras.km, carreras.ciudad, carreras.precio, carreras.enlace_compra, carreras.evento as id_evento from carreras where id = ?";

    public static final String GET_CARRERAS_BY_USER = "select carreras.id as id, carreras.titulo, carreras.descripcion, carreras.img, km, ciudad, precio, enlace_compra, evento, usuario, id_carrera, p.nombre as provincia, tc.nombre as tipo from carreras join carreras_favoritas cf on carreras.id = cf.id_carrera join eventos e on e.id = carreras.evento join tipo_carrera tc on e.tipo = tc.id join provincia p on e.provincia = p.id_provincia where cf.usuario = ?";

    public static final String INSERTAR_CARRERAS = "insert into carreras(titulo, descripcion, img, km, ciudad, precio, enlace_compra, evento) VALUES (?,?,?,?,?,?,?,?)";
    public static final String GET_ALL_CARRERAS= "select * from carreras";

    //provincias
    public static final String FILTROS_PROVINCIAS = "select * from provincia";

    //tipo carreras
    public static final String FILTROS_TIPO_CARRERA = "select * from tipo_carrera";

    public static final String DELETE_FROM_CARRERAS_WHERE_ID = "Delete from carreras where id = ?";
    public static final String DELETE_FROM_CARRERAS_FAVORITAS_WHERE_ID_CARRERA_AND_USUARIO = "delete from carreras_favoritas where id_carrera = ? and usuario = ?";
    public static final String SELECT_FROM_CARRERAS_FAVORITAS_WHERE_USUARIO_AND_ID_CARRERA = "select * from carreras_favoritas where usuario=? and id_carrera=?";
    public static final String INSERT_INTO_CARRERAS_FAVORITAS_USUARIO_ID_CARRERA_VALUES = "insert into carreras_favoritas (usuario, id_carrera) VALUES (?,?)";
    public static final String DELETE_FROM_EVENTOS_WHERE_ID = "Delete from eventos where id = ?";
    public static final String SELECT_COUNT_FROM_CARRERAS_FAVORITAS_WHERE_USUARIO = "select count(*) from carreras_favoritas where usuario = ?";
    public static final String DELETE_FROM_USUARIOS_WHERE_USERNAME = "Delete from usuarios where username = ?";
    public static final String DELETE_FROM_CARRERAS_FAVORITAS_WHERE_USUARIO = "Delete from carreras_favoritas where usuario = ?";
    public static final String SELECT_USERNAME_PESO_TIPO_FROM_USUARIOS_JOIN_TIPO_USUARIO_TU_ON_TU_ID_USUARIOS_TIPO_USER = "select username,peso,tipo from usuarios join tipo_usuario tu on tu.id = usuarios.tipo_user";
    public static final String SELECT_FROM_USUARIOS_WHERE_USERNAME = "select * from usuarios where username = ?";
    public static final String INSERT_INTO_USUARIOS_USERNAME_PASS_PESO_TIPO_USER_VALUES = "insert into usuarios (username, pass, peso, tipo_user) VALUES (?,?,?,?)";

    //constantesnoquerys
    public static final String CARRERAS_NO_ENCONTRADAS = "Carreras no encontradas";
    public static final String PROBLEMA_AL_INSERTAR_CARRERA = "PROBLEMA AL INSERTAR CARRERA";
    public static final String CARRERA_CON_ID = "Carrera con ID: ";
    public static final String ELIMINADO = " ELIMINADO";
    public static final String CARRERA_NO_ENCONTRADA = "Carrera no encontrada";
    public static final String CARRERA_ELIMINADA = "Carrera eliminada";
    public static final String CARRERAS_FAVORITAS_NO_ENCONTRADAS = "Carreras Favoritas no encontradas";
    public static final String PROBLEMA_AL_INSERTAR_CARRERA_FAVORITA = "PROBLEMA AL INSERTAR CARRERA FAVORITA";
    public static final String PROBLEMA_AL_INSERTAR_CARRERA_FAVORITA_ESE_USER_YA_TIENE_ESA_CARRERA = "PROBLEMA AL INSERTAR CARRERA FAVORITA / ESE USER YA TIENE ESA CARRERA";
    public static final String CARRERAS_FAVORITAS_NO_ENCONTRADAS_ = "Carreras Favoritas no encontradas";
    public static final String EVENTOS_NO_ENCONTRADOS = "Eventos no encontrados";
    public static final String PROBLEMA_AL_INSERTAR_EVENTO = "PROBLEMA AL INSERTAR EVENTO";
    public static final String EVENTO_CON_ID = "Evento con ID: ";
    public static final String ELIMINADO_ = " ELIMINADO";
    public static final String EVENTOS_NO_ENCONTRADO = "Eventos no encontrado";
    public static final String PROVINCIAS_NO_ENCONTRADAS = "Provincias no encontradas";
    public static final String TIPOS_DE_CARRERA_NO_ENCONTRADOS = "Tipos de Carrera no encontrados";
    public static final String FALTAN_PARAMETROS = "FALTAN PARAMETROS";
    public static final String EL_USUARIO_YA_EXISTE = "EL USUARIO YA EXISTE";
    public static final String NO_HAY_USUARIO = "No hay usuario";
    public static final String USUARIO_INCORRECTO = "Usuario incorrecto";
    public static final String USUARIOS_NO_ENCONTRADOS = "Usuarios no encontrados";
    public static final String USUARIO_CON_USERNAME = "Usuario con username: ";
    public static final String NO_EXISTE = "no existe";
    public static final String USERNAME_NO_ENCONTRADO = "Username no encontrado";
}
