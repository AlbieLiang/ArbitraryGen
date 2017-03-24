;function firstChar2UpperCase(str) {
	if (str === null || typeof str !== 'string' || str.length == 0) {
		return "";
	}
	var r = str.substring(0, 1).toUpperCase();
	if (str.length > 1) {
		r += str.substring(1, str.length);
	}
	return r;
};

function firstChar2LowerCase(str) {
	if (str === null || typeof str !== 'string' || str.length == 0) {
		return "";
	}
	var r = str.substring(0,1).toLowerCase();
	if (str.length > 1) {
		r += str.substring(1, str.length);
	}
	return r;
};

function getTypeDefaultValue(type, def) {
	if (typeof type !== 'string') {
		return 'null';
	}
	if (def !== undefined && def != 'null') {
		return def;
	}
	if (type == "byte[]") {
		return "null";
	}
	if (type == "short" || type == "Short") {
		return "0";
	}
	if (type == "boolean" || type == "Boolean") {
		return "false";
	}
	if (type == "int" || type == "Integer") {
		return "0";
	}
	if (type == "float" || type == "Float") {
		return "0";
	}
	if (type == "double" || type == "Double") {
		return "0";
	}
	if (type == "long" || type == "Long") {
		return "0";
	}
	if (type == "String" || type == "string") {
		return "null";
	}
	return null;
};