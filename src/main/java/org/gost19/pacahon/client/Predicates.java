package org.gost19.pacahon.client;

public class Predicates
{
	// //////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String xsd = "xsd:";
	public static final String dc = "dc:";
	public static final String rdf = "rdf:";
	public static final String rdfs = "rdfs:";
	public static final String owl = "owl:";
	public static final String swrc = "swrc:";
	public static final String docs = "docs:";
	public static final String gost19 = "gost19:";
	public static final String auth = "auth:";
	public static final String zdb = "zdb:";
	public static final String user_onto = "User_onto:";
	public static final String msg = "msg:";
	public static final String query = "query:";

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

	public static final String rdf__type = "a";
	public static final String rdf__subject = rdf + "subject";
	public static final String rdf__predicate = rdf + "predicate";
	public static final String rdf__object = rdf + "object";
	public static final String rdf__Statement = rdf + "Statement";

	public static final String rdfs__subClassOf = rdfs + "subClassOf";
	public static final String rdfs__Class = rdfs + "Class";
	public static final String rdfs__label = rdfs + "label";
	public static final String rdfs__comment = rdfs + "comment"; 

	public static final String dc__creator = dc + "creator";
	public static final String dc__identifier = dc + "identifier";
	public static final String dc__subject = dc + "subject";
	public static final String dc__title = dc + "title";
	public static final String dc__type = dc + "type";
	public static final String dc__description = dc + "description";
	public static final String dc__date = dc + "date";
	public static final String dc__hasPart = dc + "hasPart"; 

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

	public static final String gost19__middleName = gost19 + "middleName";
	public static final String gost19__externalIdentifer = gost19 + "externalIdentifer";
	public static final String gost19__tag = gost19 + "tag";
	public static final String gost19__synchronize = gost19 + "synchronize";
	public static final String gost19__internal_phone = gost19 + "internal_phone";
	public static final String gost19__mobile = gost19 + "mobile";
	public static final String gost19__work_mobile = gost19 + "work_mobile";
	public static final String gost19__volatile = gost19 + "volatile";	
	public static final String gost19__representation = gost19 + "representation"; 

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
	public static final String docs__unit_card = docs + "unit_card"; 	
	public static final String docs__actual = docs + "actual";
	public static final String docs__kindOf = docs + "kindOf"; 

	public static final String auth__login = auth + "login";
	public static final String auth__credential = auth + "credential";
	public static final String auth__Authenticated = auth + "Authenticated";

	public static final String query__get = query + "get";
	public static final String query__any = query + "any";
	public static final String query__all_predicates = query + "all_predicates";
	public static final String query__fulltext = query  + "fulltext";
	public static final String query__get_reifed = query + "get_reifed"; 

	public static final String msg__Message = msg + "Message";

}
