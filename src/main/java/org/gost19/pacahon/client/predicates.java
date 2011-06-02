package org.gost19.pacahon.client;

import java.util.HashMap;
import java.util.Map;

public class predicates
{
	// //////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String xsd = "http://www.w3.org/2001/XMLSchema#";
	public static final String dc = "http://purl.org/dc/elements/1.1/";
	public static final String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static final String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	public static final String owl = "http://www.w3.org/2002/07/owl#";
	public static final String swrc = "http://swrc.ontoware.org/ontology#";
	public static final String docs = "http://gost19.org/docs#";
	public static final String gost19 = "http://gost19.org/base#";
	public static final String auth = "http://gost19.org/auth#";
	public static final String zdb = "http://user-db#";
	public static final String user_onto = "http://user-onto.org#";
	public static final String msg = "http://gost19.org/message#";
	public static final String query = "http://gost19.org/query#";

	public final static String all_prefixs = "@prefix rdf: <" + rdf + "> .\n" + "@prefix rdfs: <" + rdfs + "> .\n"
			+ "@prefix xsd: <" + xsd + "> .\n" + "@prefix msg: <" + msg + "> .\n" + "@prefix auth: <" + auth + "> .\n"
			+ "@prefix zdb: <" + zdb + "> .\n" + "@prefix gost19: <" + gost19 + "> .\n" + "@prefix swrc: <" + swrc
			+ "> .\n" + "@prefix docs: <" + docs + "> .\n";

	public static final String ns_f_user_onto = "http://user-onto.org";

	public static final String _xsd = "xsd:";
	public static final String _dc = "dc:";
	public static final String _rdf = "rdf:";
	public static final String _rdfs = "rdfs:";
	public static final String _owl = "owl:";
	public static final String _swrc = "swrc:";

	public static final String _docs = "docs:";
	public static final String _gost19 = "gost19:";
	public static final String _auth = "auth:";
	public static final String _zdb = "zdb:";
	public static final String _query = "query:";
	
	public static final String _user_onto = "user_onto";

	public static final String f_user_onto = "http://user-onto.org";
	public static final String f_swrc = "http://swrc.ontoware.org/ontology-07";
	public static final String f_zdb = "http://user-db";

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String owl__Ontology = owl + "Ontology";
	public static final String owl__imports = owl + "imports";
	public static final String owl__Restriction = owl + "Restriction";
	public static final String owl__onProperty = owl + "onProperty";
	public static final String owl__allValuesFrom = owl + "allValuesFrom";
	public static final String owl__someValuesFrom = owl + "someValuesFrom";
	public static final String owl__maxCardinality = owl + "maxCardinality";
	public static final String owl__minCardinality = owl + "minCardinality";
	public static final String owl__hasValue = owl + "hasValue";

	public static final String xsd__string = xsd + "string";
	public static final String xsd__boolean = xsd + "boolean";
	public static final String xsd__date = xsd + "date";
	public static final String xsd__decimal = xsd + "decimal";
	public static final String xsd__integer = xsd + "integer";
	public static final String xsd__nonNegativeInteger = xsd + "nonNegativeInteger";

	public static final String rdf__type = rdf + "type";
	public static final String rdf__subject = rdf + "subject";
	public static final String rdf__predicate = rdf + "predicate";
	public static final String rdf__object = rdf + "object";
	public static final String rdf__Statement = rdf + "Statement";

	public static final String rdfs__subClassOf = rdfs + "subClassOf";
	public static final String rdfs__Class = rdfs + "Class";
	public static final String rdfs__label = rdfs + "label";

	public static final String dc__creator = dc + "creator";
	public static final String dc__identifier = dc + "identifier";
	public static final String dc__subject = dc + "subject";
	public static final String dc__title = dc + "title";
	public static final String dc__type = dc + "type";
	public static final String dc__description = dc + "description";
	public static final String dc__date = dc + "date";

    public static final String dc__dateSubmitted = dc + "dateSubmitted";
	public static final String dc__modified = dc + "modified";

	public static final String swrc__Department = swrc + "Department";
	public static final String swrc__Person = swrc + "Person";
	public static final String swrc__name = swrc + "name";
	public static final String swrc__lastName = swrc + "lastName";
	public static final String swrc__firstName = swrc + "firstName";
	public static final String swrc__organization = swrc + "organization";
	public static final String swrc__Organization = swrc + "Organization";
	public static final String swrc__email = swrc + "email";
	public static final String swrc__phone = swrc + "phone";
	public static final String swrc__photo = swrc + "photo";
	public static final String swrc__endDate = swrc + "endDate";
	public static final String swrc__startDate = swrc + "startDate";
	public static final String swrc__keywords = swrc + "keywords";
	public static final String swrc__number = swrc + "number";
	public static final String swrc__note = swrc + "note";

	public static final String gost19__middlename = gost19 + "middlename";
	public static final String gost19__externalIdentifer = gost19 + "externalIdentifer";

	public static final String docs__Group = docs + "Group";
	public static final String docs__Document = docs + "Document";
	public static final String docs__FileDescription = docs + "FileDescription";
	public static final String docs__file = docs + "file";
	public static final String docs__tumbnail = docs + "tumbnail";
	public static final String docs__unit = docs + "unit";
	public static final String docs__department_card = docs + "department_card";
	public static final String docs__parentUnit = docs + "parentUnit";
	public static final String docs__employee_card = docs + "employee_card";
	public static final String docs__employee = docs + "employee";
	public static final String docs__organization_card = docs + "organization_card";
	public static final String docs__pager = docs + "pager";
	public static final String docs__carbon_copy = docs + "carbon_copy";
	public static final String docs__dateInterval = docs + "dateInterval";
	public static final String docs__contractor = docs + "contractor";
	public static final String docs__link = docs + "link";
	public static final String docs__content = docs + "content";
	public static final String docs__from = docs + "from";
	public static final String docs__to = docs + "to";
	public static final String docs__position = docs + "position";
	public static final String docs__active = docs + "active";

	public static final String auth__login = auth + "login";
	public static final String auth__credential = auth + "credential";
	public static final String auth__Authenticated = auth + "Authenticated";

	public static final String query__get = query + "get";
	public static final String query__all_predicates = query + "all_predicates";

	public static final String msg__Message = msg + "Message";

	private static Map<String, String> prefixs = new HashMap<String, String>();

	public static Map<String, String> getPrefixs()
	{
		if (prefixs.isEmpty())
		{
			prefixs.put("rdf", rdf);
			prefixs.put("rdfs", rdfs);
			prefixs.put("msg", msg);
			prefixs.put("zdb", zdb);
			prefixs.put("swrc", swrc);
			prefixs.put("docs", docs);
			prefixs.put("gost19", gost19);
			prefixs.put("query", query);
			prefixs.put("auth", auth);
		}

		return prefixs;
	}

}
