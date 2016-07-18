;function getDBType(type) {
	if (type == "byte[]") {
		return "BLOB";
	}
	
	if (type=="short" || type=="Short") {
		return "SHORT";
	}
	
	if (type=="boolean" || type=="Boolean") {
		return "INTEGER";
	}
	
	if (type=="int" || type=="Integer") {
		return "INTEGER";
	}
	
	if (type=="float" || type=="Float") {
		return "FLOAT";
	}
	
	if (type=="double" || type=="Double") {
		return "DOUBLE";
	}
	
	if (type=="long" || type=="Long") {
		return "LONG";
	}
	
	if (type=="String" || type=="string") {
		return "TEXT";
	}
	
	throw "UNSUPPORT_TYPE:" + type;
};	
;function genGetTypeMethod(type) {
	if (type == "byte[]") {
		return "getBytes";
	}
	
	if (type=="short" || type=="Short") {
		return "getShort";
	}
	
	if (type=="boolean" || type=="Boolean") {
		return "getInt";
	}
	
	if (type=="int" || type=="Integer") {
		return "getInt";
	}
	
	if (type=="float" || type=="Float") {
		return "getFloat";
	}
	
	if (type=="double" || type=="Double") {
		return "getDouble";
	}
	
	if (type=="long" || type=="Long") {
		return "getLong";
	}
	
	if (type=="String" || type=="string") {
		return "getString";
	}
	
	throw "UNSUPPORT_TYPE:" + type;
};
;function genSetTypeMethod(type) {
	if (type == "byte[]") {
		return "putBytes";
	}
	
	if (type=="short" || type=="Short") {
		return "putShort";
	}
	
	if (type=="boolean" || type=="Boolean") {
		return "putInt";
	}
	
	if (type=="int" || type=="Integer") {
		return "putInt";
	}
	
	if (type=="float" || type=="Float") {
		return "putFloat";
	}
	
	if (type=="double" || type=="Double") {
		return "putDouble";
	}
	
	if (type=="long" || type=="Long") {
		return "putLong";
	}
	
	if (type=="String" || type=="string") {
		return "putString";
	}
	
	throw "UNSUPPORT_TYPE:" + type;
};			
;function genGetDBTypeMethod(type) {
	if (type == "byte[]") {
		return "getBlob";
	}
	
	if (type=="short" || type=="Short") {
		return "getShort";
	}
	
	if (type=="boolean" || type=="Boolean") {
		return "getInt";
	}
	
	if (type=="int" || type=="Integer") {
		return "getInt";
	}
	
	if (type=="float" || type=="Float") {
		return "getFloat";
	}
	
	if (type=="double" || type=="Double") {
		return "getDouble";
	}
	
	if (type=="long" || type=="Long") {
		return "getLong";
	}
	
	if (type=="String" || type=="string") {
		return "getString";
	}
	
	throw "UNSUPPORT_TYPE:" + type;
};
function isProto(type) {
	type = type.toLowerCase();
	return type == "proto" || type == "protobuf"; 
};
String.prototype.hashCode = function(){
    var hash = 0, i, c;
    if (this.length == 0) return hash;
    for (i = 0, l = this.length; i < l; i++) {
        c  = this.charCodeAt(i);
        hash  = ((hash<<5)-hash)+c;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
};
String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};
String.prototype.toCamel = function() {
	return this.substr(0,1).toUpperCase() + this.substr(1);
};