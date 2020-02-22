package br.gov.pb.der.controleveiculosder.utils;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author victorqueiroga
 */
public class Funcoes {

	public static String stripTags(String htmlString) {
		if (isValidStringValue(htmlString))
			return htmlString.replaceAll("\\<.*?\\>", "");
		return "---";
	}

	public static boolean isValidStringValue(String param) {
		return (param != null && !param.trim().equals(""));
	}

	public static boolean isValidNumericParameter(Double number) {
		return (number != null && number > 0);
	}

	public static boolean isValidLongParameter(Long number) {
		return (number != null && number > 0);
	}

	public static boolean isValidIntegerParameter(Integer number) {
		return (number != null && number > 0);
	}

	public static String cryptPasswordSha256(String pass) {
		return DigestUtils.sha256Hex(pass);
	}

	/**
	 * formata valor retirando o ponto e substituindo por um caractere vazio e a
	 * vírgula por um ponto, dessa forma converte o valor em string para double
	 *
	 * @param valor
	 * @return
	 * @throws NumberFormatException
	 */
	public static Double formatMoedaFloat(String valor) throws NumberFormatException {
		Double teste = Double.parseDouble(valor.replaceAll("\\.", "").replaceAll(",", "."));
		// MessageService.informationMessage("valor formatado: " + teste,
		// "teste", null);
		return teste;
	}

	public static String formatMoedaFloat(double valor) throws NumberFormatException {
		String teste = "" + valor;
		teste = teste.replaceAll("\\.", ",");
		// MessageService.informationMessage("valor formatado: " + teste,
		// "teste", null);
		return teste;
	}

	public static String formatMoedaToString(double money) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(money);
		return moneyString;
	}

	/**
	 * type 1 - Integer; 2 - Long
	 *
	 * @param s
	 * @param type
	 * @return
	 */
	public static Serializable getCodFromString(String s, int type) {
		Serializable id = null;
		if (s != null) {
			int index = s.indexOf('-');
			String part = s.substring(0, index);
			try {
				switch (type) {
				case 1:
					id = Integer.parseInt(part);
					break;
				case 2:
					id = Long.parseLong(part);
					break;
				}
			} catch (NumberFormatException ex) {
				System.err.println("Impossível pegar código da string");
			}
		}
		return id;
	}

	public static boolean isInArray(int num, int[] array) {
		for (int i : array) {
			if (num == i) {
				return true;
			}
		}
		return false;

	}

	public static String noHyphen(String teste) {
		return teste.replace("-", "");
	}

	public static String generateRandomPassword(int qtdeMaximaCaracteres) {
		String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z", "@", "$", "#", "*" };

		StringBuilder senha = new StringBuilder();

		for (int i = 0; i < qtdeMaximaCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.length);
			senha.append(caracteres[posicao]);
		}

		return senha.toString();
	}

	public static String inSQLString(List<String> lista) {
		String in = "(";
		for (String text : lista) {
			in += text + ",";
		}
		if (lista.size() > 0) {
			in = in.substring(0, in.length() - 1);
		}
		in += ")";
		return in;

	}

	public static String[] toComboList(List<String> dados) {
		String[] arr;
		if (dados.size() > 0) {
			arr = new String[dados.size() + 1];
		} else {
			arr = new String[1];
		}
		arr[0] = "Selecione";
		int i = 1;
		for (String nome : dados) {
			arr[i] = nome;
			i++;
		}
		return arr;
	}

	public static boolean validar(String email) {
		boolean isEmailIdValid = true;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			isEmailIdValid = matcher.matches();

		}
		return isEmailIdValid;
	}

	public static String toLowerCaseForLike(String value) {
		return "%" + value.toLowerCase(new Locale(Parametros.APP_LANGUAGE, Parametros.APP_COUNTRY)) + "%";
	}

	public static String toLowerCaseForStartingWith(String value) {
		return value.toLowerCase(new Locale(Parametros.APP_LANGUAGE, Parametros.APP_COUNTRY)) + "%";
	}

	public static String toLowerCase(String value) {
		return value.toLowerCase(new Locale(Parametros.APP_LANGUAGE, Parametros.APP_COUNTRY));
	}

	public static boolean verificarUltimaPosicaoCursor(JTextField field) {
		if (field.getCaretPosition() > field.getText().length() - 1) {
			return true;
		}
		return false;
	}

	public static String formatarTelefone(String tel) {
		String telefone = tel.replaceAll("[^0-9]", "");
		return telefone;
	}

	public static String getTelefoneFormatado(String tel) {
		String telefone = "(" + tel.substring(0, 2) + ")" + tel.substring(2);
		return telefone;
	}

	public static String formatarPlaca(String placa) {
		String telefone = placa.replace("-", "");
		return telefone;
	}

	public static String tableStyle() {
		return "<style>table { border-collapse: collapse;} " + "table, th, td {border: 1px solid #ddd;} "
				+ "tr:nth-child(even) {background-color: #f2f2f2;} " + "th, td { padding: 5px; text-align: left;} "
				+ "th { background-color: #4CAF50; color: white;}" + "</style>";
	}

}
