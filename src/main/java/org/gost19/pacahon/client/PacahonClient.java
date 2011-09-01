package org.gost19.pacahon.client;

import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Poller;

public class PacahonClient
{
	boolean zmq_msg_non_block_read_mode = false;

	private final static int REQUEST_TIMEOUT = 2500; //  msecs, (> 1000!)
	private final static int REQUEST_RETRIES = 50; //  Before we abandon

	private ZMQ.Context ctx;
	private ZMQ.Socket socket;
	private JSONParser jp;
	private String connectTo;

	public PacahonClient(String _connectTo)
	{
		String zmq_msg_read_mode = System.getProperty("zmq_msg_read_mode");
		if (zmq_msg_read_mode != null && zmq_msg_read_mode.equals("nonblock"))
			zmq_msg_non_block_read_mode = true;

		connectTo = _connectTo;

		if (connectTo == null)
			connectTo = "tcp://localhost:5555";

		ctx = ZMQ.context(1);
		socket = ctx.socket(ZMQ.REQ);
		//		socket.setLinger(0);
		System.out.println("I: connect to server [" + connectTo + "]...");
		socket.connect(connectTo);
		System.out.println("Ok");

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

		//		socket.send(msg.getBytes(), 0);
		//		byte[] rr = socket.recv(0);
		//		String result = new String(rr, "UTF-8");

		String result = send_recv(msg);

		//		JSONArray aa = (JSONArray) jp.parse(result);
		//		aa.size();

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

	public synchronized boolean remove_subject(String ticket, String subject, String from) throws Exception
	{
		UUID msg_uuid = UUID.randomUUID();

		String msg = "{\n" + "\"@\" : \"msg:M" + msg_uuid + "\", \n" + "\"a\" : \"msg:Message\",\n"
				+ "\"msg:sender\" : \"" + from + "\",\n \"msg:ticket\" : \"" + ticket + "\",\n"
				+ "\"msg:reciever\" : \"pacahon\",\n" + "\"msg:command\" : \"remove\",\n" + "\"msg:args\" :\n" + "{\n"
				+ "\"rdf:subject\" : \"" + subject + "\"\n" + "}\n" + "}";

		String result = send_recv(msg);

		int pos = result.indexOf("ok");
		if (pos > 0)
		{
			return true;
		}

		return false;
	}

	private String send_recv(String msg) throws Exception
	{
		if (zmq_msg_non_block_read_mode == false)
		{

			socket.send(msg.getBytes("UTF-8"), 0);
			byte[] rr = socket.recv(0);
			String result = new String(rr, "UTF-8");
			return result;
		} else
		{
			int retries_left = REQUEST_RETRIES;

			while (retries_left > 0)
			{
				socket.send(msg.getBytes(), 0);

				boolean expect_reply = true;
				while (expect_reply)
				{
					//  Initialize poll set
					Poller items = ctx.poller(Poller.POLLIN);
					items.register(socket);
					long rc = items.poll(REQUEST_TIMEOUT * 1000);

					if (rc == -1)
						break;

					//  Poll socket for a reply, with timeout
					//	            zmq::pollitem_t items[] = { { *client, 0, ZMQ_POLLIN, 0 } };
					//	            zmq::poll (&items[0], 1, REQUEST_TIMEOUT * 1000);

					//  If we got a reply, process it
					if (items.pollin(0))
					{
						//  We got a reply from the server, must match sequence
						byte[] rr = socket.recv(0);

						String result = new String(rr, "UTF-8");
						return result;
					} else
					{
						retries_left = retries_left - 1;

						if (retries_left == 0)
						{
							System.out.println("E: server seems to be offline, abandoning");
							expect_reply = false;
							break;
						} else
						{
							System.out.println("W: no response from server [" + connectTo + "], retrying...");
							//  Old socket will be confused; close it and open a new one
							items.unregister(socket);
							socket.close();

							System.out.println("sleep 1s");
							Thread.sleep(1000);

							socket = ctx.socket(ZMQ.REQ);
							socket.connect(connectTo);
							//						socket.setLinger(0);

							//  Send request again, on new socket
							socket.send(msg.getBytes(), 0);
						}

					}
				}
			}
		}

		return null;
	}

	public synchronized boolean put(String ticket, JSONArray data, String from) throws Exception
	{
		UUID msg_uuid = UUID.randomUUID();

		String args = JSONArray.toJSONString(data);

		String msg = "{\n \"@\" : \"msg:M" + msg_uuid + "\", \n \"a\" : \"msg:Message\",\n" + "\"msg:sender\" : \""
				+ from + "\",\n \"msg:ticket\" : \"" + ticket
				+ "\", \"msg:reciever\" : \"pacahon\",\n \"msg:command\" : \"put\",\n \"msg:args\" :\n" + args + "\n}";

		// отправляем
		//		socket.send(msg.getBytes(), 0);
		//		byte[] rr = socket.recv(0);
		//		String result = new String(rr, "UTF-8");	

		String result = send_recv(msg);

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
				+ "\", \"msg:reciever\" : \"pacahon\",\n \"msg:command\" : \"get\",\n \"msg:args\" :\n" + args + "\n}";

		return msg;
	}

	public synchronized JSONArray send(String ticket, String msg, String from) throws Exception
	{
		// отправляем
		//		msg = new String (msg.getBytes("UTF-8"), "UTF-8");

		String result = send_recv(msg);

		//		socket.send(msg.getBytes("UTF-8"), 0);
		//		byte[] rr = socket.recv(0);
		//		String result = new String(rr, "UTF-8");	

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

		String result = send_recv(msg);

		//		socket.send(msg.getBytes("UTF-8"), 0);
		//		byte[] rr = socket.recv(0);
		//		String result = new String(rr, "UTF-8");	

		// проверяем все ли ок
		int pos = result.indexOf("msg:status");

		if (pos > 0 && result.indexOf("ok", pos) > 0)
		{
			if (result.charAt(0) == '[')
			{
				JSONArray res = (JSONArray) jp.parse(result);

				JSONObject oo = (JSONObject) res.get(0);
				JSONArray roo = (JSONArray) oo.get("msg:result");
				if (roo != null)
					return roo;
			} else
			{
				JSONObject oo = (JSONObject) jp.parse(result);
				JSONArray roo = (JSONArray) oo.get("msg:result");
				if (roo != null)
					return roo;
			}
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
