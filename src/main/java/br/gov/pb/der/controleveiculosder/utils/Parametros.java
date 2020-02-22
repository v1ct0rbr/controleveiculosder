package br.gov.pb.der.controleveiculosder.utils;

public class Parametros {

	public static final String SEC_KEY = "rickmartinbolinha";

	public static String PATH_PUBLIC = "public";
	public static final String PATH_ADMIN = "admin";
	public static final String PATH_COMUM = "comum";
	public static final String PATH_MENU = "menu";
	public static final String PATH_RELATORIOS = "reports/";
	public static final String PATH_SUB_REPORT = PATH_RELATORIOS + "subreport/";
	////////////////////////////////////////////////////////////////////////
	public static final String CHARSET = "UTF-8";

	/**
	 * EXPRESSÕES REGULARES
	 */
	public static final String REGEX_TELEFONE_MOVEL = "^[0-9]{11}$";
	public static final String REGEX_TELEFONE_FIXO = "^[0-9]{10}$";
	public static final String REGEX_DATE = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

	/**
	 * Esquemas do BD
	 */
	public static final String SCH_SECURITY = "security";
	public static final String SCH_DEROS = "deros";
	public static final String SCH_CONTROLEVEICULOSDER = "controleveiculosder";

	/////////////////////////////////////////////////////////////////////////
	/**
	 * Configurações do Banco de Dados
	 */
	public static final String DB_TYPE_POSTGRES = "POSTGRES";
	public static final String DB_TYPE_FIREBIRD = "FIREBIRD";
	public static final String BD_NAME = "der";
	public static final String BD_HOST = "localhost";
	public static final String BD_CONN_DRIVER_CLASS = "org.postgresql.Driver";
	public static final String BD_CONN_TYPE = "postgresql";
	public static final String BD_CONN_PORT = "5432";
	public static final String BD_CONN_USER = "der";
	public static final String BD_CONN_PASS = "der12.ditc13";
	public static final String BD_CONN_ADMIN_USER = "postgres";
	public static final String BD_CONN_ADMIN_PASS = "vtgd65aoty";
	public static final String BD_CONN_URL = "jdbc:" + BD_CONN_TYPE + "://" + BD_HOST + ":" + BD_CONN_PORT + "/"
			+ BD_NAME;
	public static final String BD_CONN_URL_NO_BD = "jdbc:" + BD_CONN_TYPE + "://" + BD_HOST + ":" + BD_CONN_PORT + "/";
	public static final String DB_TYPE = DB_TYPE_POSTGRES;
	public static final String DB_SEQUENCE_STRATEGY = "org.hibernate.id.enhanced.SequenceStyleGenerator";
	/**
	 * 
	 */
	public static final String APP_LANGUAGE = "pt";
	public static final String APP_COUNTRY = "BR";
	/**
	 * Parametrosa da Empresa
	 */
	public static final String EMPRESA_LOGO = "logo_empresa.jpg";
	public static final String EMPRESA_NOME = "Departamento de Estradas e Rodagens da Paraíba";
	public static final String EMPRESA_SETOR = "Divisão de Tecnologia da Informação";
	public static final String EMPRESA_ENDERECO = "Av. Ministro José Américo de almeida s/n";
	public static final String EMPRESA_LOCALIDADE = "João Pessoa, Paraíba";
	public static final String EMPRESA_TELEFONE = "(083) 3216-2800	(083) 3216-2819	(Fax)0800-083-0789";
	public static final String EMPRESA_EMAIL = "victorqueiroga@der.pb.gov.br";
	public static final String EMPRESA_SITE = "http://www.der.pb.gov.br";

}
