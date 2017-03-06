;function transfer(str, data) {
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