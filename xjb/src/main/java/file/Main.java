package file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Main {
	public static void main(String[] args) throws Exception {
		List<String> lines = FileUtils
				.readLines(new File("C:\\Users\\geovane.santos\\Desktop\\FuncionarioAdmissaoPreliminarResource.txt"));
		String campo;
		String tipo;
		String resource = lines.get(0).replaceAll("<complexType name=\"", "").replaceAll("\\W", "");
		lines.remove(0);
		List<String> xjb = new ArrayList<String>();
		for (String line : lines) {
			if (!line.contains("<element")) {
				continue;
			}
			campo = campo(line);
			tipo = tipo(line);

			xjb.add("\t\t" + node(resource, campo));
			xjb.add("\t\t\t" + targetField());
			xjb.add(notNull(resource));
			if (tipo.equalsIgnoreCase("int") || tipo.equalsIgnoreCase("long") || tipo.equalsIgnoreCase("double")) {
				xjb.add(max(resource));
			}
			if (tipo.equalsIgnoreCase("string")) {
				xjb.add(length(resource));
			}
			if (tipo.equalsIgnoreCase("decimal")) {
				xjb.add(length(resource));
			}
			if (tipo.contains(":")) {
				xjb.add(valid(resource));
			}
			xjb.add("\t\t\t" + closeAnnotate());
			xjb.add("\t\t" + closeBinding());
		}
		for (String string : xjb) {
			System.out.println(string);
		}
	}

	private static String valid(String resource) {
		StringBuilder valid = new StringBuilder();
		valid.append("\t\t\t\t<annox:annotate annox:class=\"javax.validation.Valid\"");
		valid.append("\n");
		valid.append("\t\t\t\t\tname=\"" + resource + "\"");
		valid.append("\n");

		valid.append("\t\t\t\t\tgroups=\"br.com.contmatic.contador.groups.Put");
		valid.append("\n");
		valid.append("\t\t\t\t\t\tbr.com.contmatic.contador.groups.Post\"/>");

		return valid.toString();
	}

	private static String tipo(String line) {
		line = line.split("<element name=\"")[1];
		int count;
		for (count = 0; count <= line.length(); count++) {
			if (line.charAt(count) == '\"') {
				break;
			}
		}
		line = line.substring(count, line.length());

		return line.replaceAll("\\s|\"|\\=|/+|>|(type)|(min)|(Occurs)|0", "");
	}

	public static String campo(String line) {
		line = line.split("<element name=\"")[1];
		int count;
		for (count = 0; count < line.length(); count++) {
			if (line.charAt(count) == '\"') {
				break;
			}
		}
		return line.substring(0, count);
	}

	public static String notNull(String resource) {
		StringBuilder notNull = new StringBuilder();

		notNull.append("\t\t\t\t<annox:annotate annox:class=\"javax.validation.constraints.NotNull\"");
		notNull.append("\n");
		notNull.append("\t\t\t\t\tname=\"" + resource + "\"");
		notNull.append("\n");
		notNull.append("\t\t\t\t\tgroups=\"br.com.contmatic.contador.groups.Put\"");
		notNull.append("\n");
		notNull.append("\t\t\t\t\t\tbr.com.contmatic.contador.groups.Post\"");
		notNull.append("\n");
		notNull.append("\t\t\t\t\tmessage=/>");

		return notNull.toString();
	}

	public static String node(String resource, String campo) {
		return "<jxb:bindings node=\"xs:complexType[@name='" + resource + "']/xs:sequence/xs:element[@name='" + campo
				+ "']\">";
	}

	public static String targetField() {
		return "<annox:annotate target=\"field\">";
	}

	public static String closeBinding() {
		return "</jxb:bindings>";
	}

	public static String closeAnnotate() {
		return "</annox:annotate>";
	}

	public static String max(String resource) {
		StringBuilder max = new StringBuilder();
		max.append("\t\t\t\t<annox:annotate annox:class=\"javax.validation.constraints.Max\"");
		max.append("\n");
		max.append("\t\t\t\t\tname=\"" + resource + "\"");
		max.append("\n");
		max.append("\t\t\t\t\tvalue=");
		max.append("\n");
		max.append("\t\t\t\t\tgroups=\"br.com.contmatic.contador.groups.Put");
		max.append("\n");
		max.append("\t\t\t\t\t\tbr.com.contmatic.contador.groups.Post\"");
		max.append("\n");
		max.append("\t\t\t\t\tmessage=/>");
		return max.toString();
	}

	public static String length(String resource) {
		StringBuilder length = new StringBuilder();
		length.append("\t\t\t\t<annox:annotate annox:class=\"org.hibernate.validator.constraints.Length\"");
		length.append("\n");
		length.append("\t\t\t\t\tname=\"" + resource + "\"");
		length.append("\n");
		length.append("\t\t\t\t\tmax=");
		length.append("\n");
		length.append("\t\t\t\t\tgroups=\"br.com.contmatic.contador.groups.Put");
		length.append("\n");
		length.append("\t\t\t\t\t\tbr.com.contmatic.contador.groups.Post\"");
		length.append("\n");
		length.append("\t\t\t\t\tmessage=/>");
		return length.toString();
	}

	public static String decimalMax(String resource) {
		StringBuilder decimalMax = new StringBuilder();
		decimalMax.append("\t\t\t\t<annox:annotate annox:class=\"javax.validation.constraints.DecimalMax\"");
		decimalMax.append("\n");
		decimalMax.append("\t\t\t\t\tname=\"" + resource + "\"");
		decimalMax.append("\n");
		decimalMax.append("\t\t\t\t\tvalue=");
		decimalMax.append("\n");
		decimalMax.append("\t\t\t\t\tgroups=\"br.com.contmatic.contador.groups.Put");
		decimalMax.append("\n");
		decimalMax.append("\t\t\t\t\t\tbr.com.contmatic.contador.groups.Post\"");
		decimalMax.append("\n");
		decimalMax.append("\t\t\t\t\tmessage=/>");
		return decimalMax.toString();
	}
}
