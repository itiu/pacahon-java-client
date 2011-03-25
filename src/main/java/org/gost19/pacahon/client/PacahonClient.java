package org.gost19.pacahon.client;

import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

	public synchronized String get_ticket(String login, String credential, String from) throws Exception
	{
		String ticket = null;

		UUID msg_uuid = UUID.randomUUID();

		String msg = "{\n" + "\"@\" : \"msg:M" + msg_uuid + "\", \n" + "\"a\" : \"msg:Message\",\n"
				+ "\"msg:sender\" : \"" + from + "\",\n" + "\"msg:reciever\" : \"pacahon\",\n"
				+ "\"msg:command\" : \"get_ticket\",\n" + "\"msg:args\" :\n" + "{\n" + "\"auth:login\" : \"" + login
				+ "\",\n" + "\"auth:credential\" : \"" + credential + "\"\n" + "}\n" + "}";

		socket.send(msg.getBytes(), 0);

		byte[] rr = socket.recv(0);

		String result = new String(rr);

		JSONArray aa = (JSONArray) jp.parse(result);

		aa.size();

		String auth_tag = "auth:ticket";

		int pos = result.indexOf(auth_tag);
		if (pos > 0)
		{
			int start_pos = result.indexOf("\"", pos + auth_tag.length() + 1) + 1;
			int end_pos = result.indexOf("\"", start_pos + 1);
			ticket = result.substring(start_pos, end_pos);
		}

		return ticket;
	}

	public synchronized boolean put(String ticket, JSONArray data, String from)
	{
		UUID msg_uuid = UUID.randomUUID();

		String args = JSONArray.toJSONString(data);

		String msg = "{\n \"@\" : \"msg:M" + msg_uuid + "\", \n \"a\" : \"msg:Message\",\n" + "\"msg:sender\" : \""
				+ from + "\",\n \"msg:ticket\" : \"" + ticket
				+ "\", \"msg:reciever\" : \"pacahon\",\n \"msg:command\" : \"put\",\n \"msg:args\" :\n" + args + "}";

		// отправляем
		socket.send(msg.getBytes(), 0);

		byte[] rr = socket.recv(0);

		String result = new String(rr);

		// проверяем все ли ок
		int pos = result.indexOf("msg:status");
		if (pos > 0 && result.indexOf("ok", pos) > 0)
			return true;

		return false;
	}

	public String get_command_as_string(String ticket, JSONObject data, String from)
	{
		UUID msg_uuid = UUID.randomUUID();

		String args = JSONObject.toJSONString(data);

		String msg = "{\n \"@\" : \"msg:M" + msg_uuid + "\", \n \"a\" : \"msg:Message\",\n" + "\"msg:sender\" : \""
				+ from + "\",\n \"msg:ticket\" : \"" + ticket
				+ "\", \"msg:reciever\" : \"pacahon\",\n \"msg:command\" : \"get\",\n \"msg:args\" :\n" + args + "}";

		return msg;
	}

	public synchronized JSONArray send(String ticket, String msg, String from) throws Exception
	{
		// отправляем
		//		msg = new String (msg.getBytes(), "UTF-8");

		socket.send(msg.getBytes("UTF-8"), 0);

		byte[] rr = socket.recv(0);

		String result = new String(rr, "UTF-8");

		// проверяем все ли ок
		int pos = result.indexOf("msg:status");

		if (pos > 0 && result.indexOf("ok", pos) > 0)
		{
			JSONArray res = (JSONArray) jp.parse(result);
			JSONObject oo = (JSONObject) res.get(0);
			JSONArray roo = (JSONArray) oo.get("msg:result");
			if (roo != null)
				return roo;
		}

		return null;
	}

	public synchronized JSONArray get(String ticket, JSONObject data, String from) throws Exception
	{
		String msg = get_command_as_string(ticket, data, from);

		// отправляем
		//		msg = new String (msg.getBytes(), "UTF-8");

		socket.send(msg.getBytes("UTF-8"), 0);

		byte[] rr = socket.recv(0);

		String result = new String(rr, "UTF-8");

		// проверяем все ли ок
		int pos = result.indexOf("msg:status");

		if (pos > 0 && result.indexOf("ok", pos) > 0)
		{
			JSONArray res = (JSONArray) jp.parse(result);
			JSONObject oo = (JSONObject) res.get(0);
			JSONArray roo = (JSONArray) oo.get("msg:result");
			if (roo != null)
				return roo;
		}

		return null;
	}

	public static void main(String[] args) throws Exception
	{
		PacahonClient pc = new PacahonClient(null);
		String ticket = pc.get_ticket("admin", "QL0AFWMIX8NRZTKeof9cXsvbvu8=", "test");

		JSONParser jp = new JSONParser();
		String sss = "[{ \"@\" : \"auth:admin3\", \"a\" : \"auth:Authenticated\", \"rdfs:label\" : \"admin1 - админская учетка\",  \"auth:login\" : \"admin3\", \"auth:credential\" : \"QL0AFWMIX8NRZTKeof9cXsvbvu8=\" }]";
		JSONArray aa = (JSONArray) jp.parse(sss);

		sss = "{ \"@\" : \"auth:subject12\", \"auth:login\" : \"query:get\", \"rdfs:label\" : \"query:get\"}";
		JSONObject oa = (JSONObject) jp.parse(sss);

		JSONArray raa = pc.get(ticket, oa, "test");
		raa.toString();

	}
}
