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
}
;String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
    } else {
        return this.replace(reallyDo, replaceWith);
    }
};
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
var linefeed = "\\n";
var carriageReturn = "\\r";
function transferHybridsTemplate(str, data) {
	var result = "";
	var line = "";
	for (var i = 0; i < str.length; i++) {
		var c = str.charAt(i);
		if (c === '\n') {
			// append raw string
			line += "\\n";
			result += "p.push('" + line + "');";
			line = "";
			continue;
		} else if (c === '\r') {
            // append raw string
            line += "\\r";
            result += "p.push('" + line + "');";
            line = "";
            continue;
        } else {
			// '/*@@@#SCRIPT-BEGIN#'
			if (c === '/' && str.length > i + 18) {
				if ("/*@@@#SCRIPT-BEGIN#" === str.substring(i, i + 19)) {
					result += "p.push('" + line + "', '/*@@@#SCRIPT-BEGIN#');";
					line = "";
					var finishScript = false;
					var codeBlock = "p.push('";
					var rawCode = "";
					for (var j = 19; i + j < str.length; j++) {
						var cc = str.charAt(i + j);
						if (cc === '\n') {
							// append raw string
							line += "\\n";
							rawCode += "\\n";
							result += "p.push('" + line + "');";
							line = "";
							continue;
						} else if (cc == '\r') {
							// append raw string
							line += "\\r";
							rawCode += "\\r";
							result += "p.push('" + line + "');";
							line = "";
							continue;
						} else if (cc === '#' && str.length > i + j + 16) {
							// '#SCRIPT-END#@@@*/'
							if ("#SCRIPT-END#@@@*/" === str.substring(i + j, i + j + 17)) {
								finishScript = true;
								codeBlock += "','" + rawCode + "');";
								result += "p.push('" + line + "');";
								var indent = line.replaceAll(/\S/, " ");
								line = "";
								rawCode = "";
								result += "p.push('#SCRIPT-END#@@@*/"
								        + "//@@@#AUTO-GEN-BEGIN#');" + codeBlock
								        + "p.push('" + linefeed + indent + "//@@@#AUTO-GEN-END#');";
								i += j + 16;
								break;
							}
						} else if (cc === '<') {
							var cc1 = str.charAt(i + j + 1);
							if (cc1 === '%') {
								var assign = false;
								var statement = "";
								var baseIndex = i + j + 2;
								line += "<%";
								if (str.charAt(baseIndex) === '=') {
									assign = true;
									baseIndex++;
									line += "=";
								}
								for (var k = baseIndex; k < str.length; k++ ) {
									var codeChar = str.charAt(k);
									if (codeChar === '\n') {
										// append raw string
										line += "\\n";
										result += "p.push('" + line + "');";
										line = "";
										// Script code
										statement += codeChar;
										continue;
									} else if (codeChar == '\r') {
										// append raw string
										line += "\\r";
										result += "p.push('" + line + "');";
										line = "";
										// Script code
										statement += codeChar;
										continue;
									} else if (codeChar === '%' && str.length > k + 1 && str.charAt(k + 1) === '>') {
										// end of script code
										line += "%>";
										j = k - i + 1;
										if (!assign) {
											statement += "p.push('";
										}
										break;
									}
									line += codeChar;
									// Script code
									statement += codeChar;
								}
								//codeBlock +=
								if (assign) {
									codeBlock += "','" + rawCode + "'," + statement + ",'";
								} else {
									codeBlock += "','" + rawCode + "');" + statement;
								}
								rawCode = "";
								continue;
							}
						}
						line += cc;
						rawCode += cc;
					}
					if (finishScript) {
						continue;
					} else {
						throw "error, missing '#SCRIPT-END#@@@*/'";
					}
				} else if ("//@@@#AUTO-GEN-BEGIN#" === str.substring(i, i + 21)) {
					var close = false;
					for (var j = 21; i + j < str.length; j++) {
						var cc = str.charAt(i + j);
						if (cc === '/' && str.length > i + j + 18) {
							// '//@@@#AUTO-GEN-END#'
							if ("//@@@#AUTO-GEN-END#" === str.substring(i + j, i + j + 19)) {
								close = true;
								i += j + 18;
								break;
							}
						}
					}
					if (close) {
						continue;
					} else {
						throw "error, missing '//@@@#AUTO-GEN-END#'";
					}
				}
			}
			line += c;
		}
	}
	result += "p.push('" + line + "');";
	return "var p=[];with(obj){" + result + "}return p.join('');";
}
;function parseHybridsTemplate(str, data) {
	var fn = new Function("obj", "" + transferHybridsTemplate(str, data));
	return data != undefined ? fn.call(data, data) : fn;
}
;function clearAutoGenBlock(str) {
    var result = "";
	for (var i = 0; i < str.length; i++) {
		var c = str.charAt(i);
		if (c === '/' && str.length > i + 20 && "//@@@#AUTO-GEN-BEGIN#" === str.substring(i, i + 21)) {
            var close = false;
            for (var j = 21; i + j < str.length; j++) {
                var cc = str.charAt(i + j);
                if (cc === '/' && str.length > i + j + 18) {
                    // '//@@@#AUTO-GEN-END#'
                    if ("//@@@#AUTO-GEN-END#" === str.substring(i + j, i + j + 19)) {
                        close = true;
                        i += j + 18;
                        break;
                    }
                }
            }
            if (close) {
                continue;
            } else {
                throw "error, missing '//@@@#AUTO-GEN-END#'";
            }
		}
		result += c;
	}
    return result;
};
function transfer(str, data) {
	var result = "";
	var line = "";
	for (var i = 0; i < str.length; i++) {
		var c = str.charAt(i);
		if (c === '\n') {
			line += "\\n";
			result += line + "');p.push('";
			line = "";
			continue;
		} else if (c === '\r') {
            line += "\\r";
            result += line + "');p.push('";
            line = "";
            continue;
        } else if (c === '<') {
            var c1 = str.charAt(i + 1);
            if (c1 === '%') {
                var assign = false;
                var statement = "";
                var baseIndex = i + 2;
                result += line + "');"
                line = "";
                if (str.charAt(baseIndex) === '=') {
                    assign = true;
                    baseIndex++;
                    result += ";p.push('" + line + "','";
                    line = "";
                }
                for (var k = baseIndex; k < str.length; k++) {
                    var codeChar = str.charAt(k);
                    if (codeChar === '%' && str.length > k + 1 && str.charAt(k + 1) === '>') {
                        // end of script code
                        i = k + 1;
                        break;
                    }
                    statement += codeChar;
                }
                if (assign) {
                    result += "'," + statement + ");";
                } else {
                    result += statement;
                }
                result += ";p.push('";
                continue;
            }
		}
		line += c;
	}
	result += "');p.push('" + line;
	return "var p=[];with(obj){p.push('" + result + "');}return p.join('');";
};
function parseTemplate(str, data) {
	var fn = new Function("obj", "" + transfer(str, data));
	return data != undefined ? fn.call(data, data) : fn;
};