var linefeed = "\\n";
var carriageReturn = "\\r";

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
 	    return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
	} else {  
		return this.replace(reallyDo, replaceWith);  
	}  
}
;function transfer(str, data) {
	var result = "";
	var line = "";
// 	str = str.replaceAll("x0a", "\n");
// 	str = str.replaceAll("x0d", "\r");
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
;function parseTemplate(str, data) {
	var fn = new Function("obj", "" + transfer(str, data));
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