package org.gost19.pacahon.client;

import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.zeromq.ZMQ;

public class PacahonClient
{
	ZMQ.Context ctx;
	ZMQ.Socket socket;
	JSONParser jp;

	public PacahonClient(String connectTo)
	{
		if (connectTo == null)
			connectTo = "tcp://172.17.4.64:5555";

		ctx = ZMQ.context(1);
		socket = ctx.socket(ZMQ.REQ);

		socket.connect(connectTo);

		jp = new JSONParser();
	}

	public String get_ticket(String login, String credential, String from) throws Exception
	{
		String ticket = null;

		UUID msg_uuid = UUID.randomUUID();

		String msg = "{\n" + "\"@\" : \"msg:M" + msg_uuid + "\", \n" + "\"a\" : \"msg:Message\",\n"
				+ "\"msg:sender\" : \"" + from + "\",\n" + "\"msg:reciever\" : \"pacahon\",\n"
				+ "\"msg:command\" : \"get_ticket\",\n" + "\"msg:args\" :\n" + "{\n"
				+ "\"auth:login\" : \"" + login + "\",\n" + "\"auth:credential\" : \"" + credential + "\"\n" + "}\n"
				+ "}";

		// "{msg:M" + msg_uuid.toString() + "\n" + "rdf:type msg:Message ;\n" + "msg:sender \"" + from
		// + "\" ;\n" + "msg:reciever \"pacahon\" ;\n" + "msg:command \"get_ticket\" ;\n" + "msg:args\n" + "[\n"
		// + "auth:login \"" + login + "\" ;\n" + "auth:credential \"" + credential + "\" ;\n" + "] .\0";

		socket.send(msg.getBytes(), 0);

		byte[] rr = socket.recv(0);

		String result = new String(rr);

		JSONArray aa = (JSONArray) jp.parse(result);

		aa.size();

		int pos = result.indexOf("auth:ticket");
		if (pos > 0)
		{
			int start_pos = result.indexOf("\"", pos) + 1;
			int end_pos = result.indexOf("\"", start_pos);
			ticket = result.substring(start_pos, end_pos);
		}

		return ticket;
	}

	/*
	 * public boolean put(String ticket, Model data, String from) { UUID msg_uuid = UUID.randomUUID();
	 * 
	 * // преобразуем data в строку ByteArrayOutputStream baos = new ByteArrayOutputStream(); RDFWriter w =
	 * data.getWriter("N3"); w.write(data, baos, ""); String str_data = baos.toString();
	 * 
	 * // отрезаем префиксы int last_prefix = str_data.lastIndexOf("@prefix"); if (last_prefix > 0) { last_prefix =
	 * str_data.indexOf("> .", last_prefix); str_data = str_data.substring(last_prefix + 3); }
	 * 
	 * // меняем ["] на [\"] str_data = str_data.replaceAll("\"", "\\\\\"");
	 * 
	 * // model.write(baos, "N3");
	 * 
	 * String msg = "msg:M" + msg_uuid.toString() + "\n" + "rdf:type msg:Message ;\n" + "msg:sender \"" + from +
	 * "\" ;\n" + "msg:ticket \"" + ticket + "\" ;\n" + "msg:reciever \"pacahon\" ;\n" + "msg:command \"put\" ;\n" +
	 * "msg:args\n" + "\"\"\"" + str_data + "\"\"\" .\0";
	 * 
	 * // отправляем socket.send(msg.getBytes(), 0);
	 * 
	 * byte[] rr = socket.recv(0);
	 * 
	 * String result = new String(rr);
	 * 
	 * // проверяем все ли ок int pos = result.indexOf("msg:status"); if (pos > 0 && result.indexOf("ok", pos) > 0)
	 * return true;
	 * 
	 * return false; }
	 * 
	 * public Model get(String ticket, Model arg, String from) throws Exception { UUID msg_uuid = UUID.randomUUID();
	 * 
	 * // преобразуем data в строку ByteArrayOutputStream baos = new ByteArrayOutputStream(); RDFWriter w =
	 * arg.getWriter("N3"); w.write(arg, baos, ""); String str_data = baos.toString();
	 * 
	 * // отрезаем префиксы int last_prefix = str_data.lastIndexOf("@prefix"); if (last_prefix > 0) { last_prefix =
	 * str_data.indexOf("> .", last_prefix); str_data = str_data.substring(last_prefix + 3); }
	 * 
	 * // меняем ["] на [\"] str_data = str_data.replaceAll("\"", "\\\\\"");
	 * 
	 * // model.write(baos, "N3");
	 * 
	 * String msg = "msg:M" + msg_uuid.toString() + "\n" + "rdf:type msg:Message ;\n" + "msg:sender \"" + from +
	 * "\" ;\n" + "msg:ticket \"" + ticket + "\" ;\n" + "msg:reciever \"pacahon\" ;\n" + "msg:command \"get\" ;\n" +
	 * "msg:args\n" + "\"\"\"" + str_data + "\"\"\" .\0";
	 * 
	 * // отправляем socket.send(msg.getBytes(), 0);
	 * 
	 * byte[] rr = socket.recv(0);
	 * 
	 * String result = new String(rr, "UTF-8");
	 * 
	 * // проверяем все ли ок int pos = result.indexOf("msg:status"); if (pos > 0 && result.indexOf("ok", pos) > 0) {
	 * pos = result.indexOf("msg:result"); if (pos > 0) { int start = result.indexOf("\"\"\"", pos); int stop =
	 * result.indexOf("\"\"\"", start + 3);
	 * 
	 * result = result.substring(start + 3, stop);
	 * 
	 * result = result.replaceAll("\\\\\"", "\"");
	 * 
	 * Model message = ModelFactory.createDefaultModel();
	 * 
	 * result = predicates.all_prefixs + result; StringReader sr = new StringReader(result); RDFReader r =
	 * message.getReader("N3"); String baseURI = ""; r.read(message, sr, baseURI); sr.close(); return message; }
	 * 
	 * }
	 * 
	 * return null; }
	 */

	public static void main(String[] args) throws Exception
	{
		PacahonClient pc = new PacahonClient(null);
		String ticket = pc.get_ticket("admin", "QL0AFWMIX8NRZTKeof9cXsvbvu8=", "test");
		ticket = ticket;
	}
}
