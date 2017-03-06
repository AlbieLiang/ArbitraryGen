;function parseTemplate(str, data) {
	var code =
		str
		.replace(/[\r\t\n]/g, " ")           /* 把所有的回车、换行和制表符 替换成空格(代码被整理成单行) */
		.split("<%").join("\t")              /* 将脚本的开始标签换成制表符（当前只有这种情况下才会出现\t字符）*/
		.replace(/((^|%>)[^\t]*)'/g, "$1\r") /* */
		.replace(/\t=(.*?)%>/g, "',$1,'")    /* 将数值直接赋值语句，*/
		.split("\t").join("');")             /* */
		.split("%>").join("p.push('")        /* */
		.split("\r").join("\\'");            /* */
	var fn = new Function("obj", "var p=[];with(obj){p.push('" + code + "');}return p.join('');");
	return data != undefined ? fn.call(data, data) : fn;
};